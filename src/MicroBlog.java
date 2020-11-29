import java.sql.Timestamp;
import java.util.*;
import java.util.regex.*;

public class MicroBlog implements SocialNetwork {
    /*
        Representation Invariant : users != null && for all keys in users ==> ((key != null) && (key.matches("[a-zA-Z_0-9]{5,15}") == true))
                                   && users.keySet().equals(followers.keySet()) && for all keys of users ==> (users.get(key).contains(key) == false)
                                   && followers != null && for all keys of followers ==> (followers.get(key) >= 0)
                                   && feed != null && for all keys of feed ==> (feed.get(key).getId().equals(key) == true)
                                   && for all keys in feed ==>
                                        (feed.get(key).getText.matches("#LIKE_[0-9]+") ==>
                                          ((feed.containsKey(Integer.parseInt("[0-9]+")) == true) && (feed.get(Integer.parseInt("[0-9]+")).getText().matches("#LIKE_[0-9]+") == false)
                                          && (users.get(feed.get(key).getAuthor()).contains(feed.get(Integer.parseInt("[0-9]+")).getAuthor()) == true)))
                                   && for all keys in feed ==>
                                        (for all @user_mention in feed.get(key).getText() ==>
                                          ((users.contains(user_mention) == true) && (users.get(feed.get(key).getAuthor()).contains(user_mention) == true)))
                                   && mentioned != null && users.keySet().containsAll(mentioned) == true

        Abstraction function : AF(users) = Insieme di coppie <utente, insieme delle persone da lui seguite>
                               AF(followers) = Insieme di coppie <utente, numero di followers>
                               AF(feed) = Insieme di coppie <id, post> dove post.getId() == id
                               AF(mentioned) = Sottoinsieme di utenti, che sono stati menzionati nei post
     */

    protected Map<String, Set<String>> users;
    private Map<String, Integer> followers;
    protected Map<Integer, Post> feed;
    protected Set<String> mentioned;

    // Crea un nuovo MicroBlog vuoto
    public MicroBlog() {
        users = new HashMap<String, Set<String>>();
        followers = new HashMap<String, Integer>();
        feed = new HashMap<Integer, Post>();
        mentioned = new HashSet<String>();
    }
    /*
       @MODIFIES : users, followers, feed, mentioned
       @EFFECTS : Initialize users, followers, feed, mentioned
     */

    // Crea un nuovo MicroBlog inizializzato con la lista di post ps (parametro del costruttore)
    public MicroBlog(List<Post> ps) throws NullPointerException, IllegalArgumentException, MentionException, LikeException
    {
        this();

        if(ps == null) throw new NullPointerException("ps == null");
        if(ps.contains(null)) throw new NullPointerException("ps.contains(null)");
        if(ps.isEmpty()) throw new IllegalArgumentException("List is empty");

        Timestamp current_time = new Timestamp(System.currentTimeMillis());

        Map<String, Set<Post>> like = new HashMap<String, Set<Post>>();
        Map<Integer, Post> messages = new HashMap<Integer, Post>();

        Pattern mention = Pattern.compile("@([a-zA-Z_0-9]{5,15})");
        Matcher m = mention.matcher("");

        String text;
        String author;

        Set<String> set_mentioned;
        Set<String> set_following;
        Set<Post> set_like;

        for(Post post : ps)
        {
            if(post.getTimestamp().after(current_time)) throw new IllegalArgumentException("Timestamp of post " + post.getId() + " is after current time");

            if(!feed.containsKey(post.getId()))
            {
                author = post.getAuthor();
                text = post.getText();

                if(text.matches("#LIKE_[0-9]+"))
                {
                    if(like.containsKey(author))
                    {
                        set_like = like.get(author);
                    }
                    else
                    {
                        set_like = new HashSet<Post>();
                    }

                    set_like.add(post);
                    like.put(author, set_like);
                }
                else
                {
                    messages.put(post.getId(), post);
                    set_mentioned = new HashSet<String>();
                    m.reset(text);

                    while(m.find())
                    {
                        if(m.group(1).equals(author)) throw new MentionException("Illegal mention");
                        set_mentioned.add(m.group(1));
                    }

                    this.mentioned.addAll(set_mentioned);

                    if(this.users.containsKey(author))
                    {
                        set_following = this.users.get(author);
                        set_following.addAll(set_mentioned);
                        this.users.put(author, set_following);
                    }
                    else
                    {
                        this.users.put(author, set_mentioned);
                    }
                }

                feed.put(post.getId(), post);
            }
        }

        Integer id;

        for(Map.Entry<String, Set<Post>> entry : like.entrySet())
        {
            for(Post like_post : entry.getValue())
            {
                id = Integer.parseInt(like_post.getText().substring(6));

                if(!messages.containsKey(id)) throw new LikeException("Liked post does not exist : id =" + id);
                if(messages.get(id).getAuthor().equals(entry.getKey())) throw new LikeException("Illegal like : id =" + like_post.getId());
                if(like_post.getTimestamp().before(messages.get(id).getTimestamp()))  throw new LikeException("Timestamp of like " + like_post.getId() + " is before the timestamp of liked post " + id);

                if(this.users.containsKey(entry.getKey()))
                {
                    set_following = this.users.get(entry.getKey());
                }
                else
                {
                    set_following = new HashSet<String>();
                }

                set_following.add(messages.get(id).getAuthor());
                this.users.put(entry.getKey(), set_following);
            }
        }

        Integer num;

        for(Map.Entry<String, Set<String>> entry : this.users.entrySet())
        {
            for(String followed : entry.getValue())
            {
                if(this.followers.containsKey(followed))
                {
                    num = this.followers.get(followed);
                    num += 1;
                }
                else
                {
                    num = 1;
                }

                this.followers.put(followed, num);
            }
        }
    }
    /*
       @REQUIRES : ps != null && ps.isEmpty() == false && ps.contains(null) == false
                   && for all i : 0 <= i < ps.size() ==>
                                (((ps.get(i).getText().contains("@User") == true) ==> (ps.get(i).getAuthor().equals("User") == false))
                                && (ps.get(i).getTimestamp().before(current_time) == true)
                                && ((ps.get(i).getText().matches("#LIKE_[0-9]+") == true) ==>
                                             (EXISTS post in ps | post.getText().matches("#LIKE_[0-9]+") == false
                                                                  && post.getAuthor().equals(ps.get(i).getAuthor()) == false
                                                                  && post.getId().equals(Integer.parseInt("[0-9]+")) == true
                                                                  && ps.get(i).getTimestamp().after(post.getTimestamp()) == true)))
       @THROWS : NullPointerException, IllegalArgumentException, MentionException, LikeException
       @MODIFIES : users, followers, feed, mentioned
       @EFFECTS : Initialize users, followers, feed, mentioned inferred from ps list
     */

    // Crea un nuovo utente nella rete sociale, restituisce il proprio username
    public String addUser(String username) throws NullPointerException, UsernameException {
        if(username == null) throw new NullPointerException("username == null");
        if(users.containsKey(username)) throw new UsernameException("Username " + username + " already exists");
        if(!username.matches("[a-zA-Z_0-9]{5,15}")) throw new UsernameException("Username " + username + " illegal format");

        users.put(username, new HashSet<String>());
        followers.put(username, 0);

        return username;
    }
    /*
       @REQUIRES : username != null && users.containsKey(username) == false
       @THROWS : NullPointerException, UsernameException
       @MODIFIES : users, followers
       @EFFECTS : users.put(username, new HashSet<String>()) && followers.put(username, 0)
       @RETURN : username
     */

    // Aggiunge ad username un follower
    public void addFollower(String username, String follower) throws NullPointerException, UsernameException, FollowerException {
        if(username == null || follower == null) throw new NullPointerException("One or more parameters are null");
        if(!users.containsKey(username)) throw new UsernameException("Username " + username + " not exists");
        if(username.equals(follower)) throw new FollowerException("You can't follow yourself");
        if(!users.containsKey(follower)) throw new FollowerException("Follower " + follower + " not exists");

        Set<String> set_following = users.get(follower);
        set_following.add(username);
        users.put(follower, set_following);
        followers.put(username, followers.get(username) + 1);
    }
    /*
       @REQUIRES : username != null && follower != null && users.containsKey(username) == true
                   && users.containsKey(follower) == true
       @THROWS : NullPointerException, UsernameException, FollowerException
       @MODIFIES : users, followers
       @EFFECTS : users.put(follower, (old_set_following.add(username))) && followers.put(username, (old_num_followers + 1))
     */

    // Aggiunge un post creato da username
    public Integer addPost(String username, String text) throws NullPointerException, UsernameException, LikeException, MentionException, PostException {
        if(username == null || text == null) throw new NullPointerException("One or more parameters are null");
        if(!users.containsKey(username)) throw new UsernameException("Username " + username + " not exists");
        if(text.isEmpty()) throw new PostException("The text of " + username + " is empty");
        if(text.length() > 140) throw new PostException("The text of " + username + " is too long (max 140 characters)");

        if(text.matches("#LIKE_[0-9]+"))
        {
            int id = Integer.parseInt(text.substring(6));

            if(!feed.containsKey(id)) throw new LikeException("Post " + id + " not exists");
            if(feed.get(id).getText().matches("#LIKE_[0-9]+")) throw new LikeException("Post " + id + " is a like");
            if(!users.get(username).contains(feed.get(id).getAuthor())) throw new LikeException("You can't like post " + id);
        }
        else
        {
            Pattern mention = Pattern.compile("@([a-zA-Z_0-9]{5,15})");
            Matcher m = mention.matcher(text);
            String u;
            Set<String> mentioned = new HashSet<String>();

            while(m.find())
            {
                u = m.group(1);
                mentioned.add(u);

                if(!users.containsKey(u)) throw new MentionException("Username mentioned " + u + " not exists");
                if(!users.get(username).contains(u)) throw new MentionException("You can't mention " + u);
            }

            this.mentioned.addAll(mentioned);
        }

        Post post = new Message(username, text, new Timestamp(System.currentTimeMillis()));
        feed.put(post.getId(), post);

        return post.getId();
    }
    /*
       @REQUIRES : username != null && text != null && users.containsKey(username) == true
       @THROWS : NullPointerException, UsernameException, LikeException, MentionException, PostException
       @MODIFIES : feed, mentioned
       @EFFECTS : feed.put(new_post.getId(), new_post)
       @RETURN : new_post.getId()
     */

    // Restituisce gli utenti più influenti delle rete sociale, ovvero quelli che hanno
    // un numero maggiore di “follower”
    public List<String> influencers() {
         List<Map.Entry<String, Integer>> followers = new ArrayList<Map.Entry<String, Integer>>(this.followers.entrySet());
         followers.sort(Map.Entry.comparingByValue());

         List<String> users = new ArrayList<String>();

         for(int i = followers.size() - 1; i >= 0; i--)
         {
             users.add(followers.get(i).getKey());
         }

         return users;
    }
    /*
       @RETURN : followers.keySet() order by values (descending order)
     */

    // Restituisce l’insieme degli utenti menzionati (inclusi) nei post presenti nella rete sociale
    public Set<String> getMentionedUsers() {
        return new HashSet<String>(mentioned);
    }
    /*
       @RETURN : mentioned
     */

    // Restituisce la lista dei post effettuati dall’utente nella rete sociale
    // il cui nome è dato dal parametro username
    public List<Post> writtenBy(String username) throws NullPointerException, UsernameException {
        if(username == null) throw new NullPointerException("username == null");
        if(!users.containsKey(username)) throw new UsernameException("Username " + username + " not exists");

        List<Post> messages = new ArrayList<Post>();

        for(Map.Entry<Integer, Post> entry : feed.entrySet())
        {
            if(entry.getValue().getAuthor().equals(username))
            {
                messages.add(entry.getValue());
            }
        }

        return messages;
    }
    /*
       @REQUIRES : username != null && users.contains(username) == true
       @THROWS : NullPointerException, UsernameException
       @RETURN : List of posts in feed | for all i : 0 <= i < List.size() ==> (List.get(i).getAuthor().equals(username) == true)
     */

    // Restituisce la lista dei post presenti nella rete sociale che includono
    // almeno una delle parole presenti nella lista delle parole argomento del metodo
    public List<Post> containing(List<String> words) throws NullPointerException, IllegalArgumentException {
        if(words == null) throw new NullPointerException("words == null");
        if(words.contains(null)) throw new NullPointerException("words.contains(null)");
        if(words.isEmpty()) throw new IllegalArgumentException("List is empty");
        if(words.contains("")) throw new IllegalArgumentException("List contains empty string");

        List<Post> messages = new ArrayList<Post>();
        StringBuilder regex = new StringBuilder();

        for(String w : words)
        {
            regex.append(w).append("|");
        }

        Matcher words_list = Pattern.compile(regex.deleteCharAt(regex.length()-1).toString()).matcher("");

        for(Map.Entry<Integer, Post> entry : feed.entrySet())
        {
            words_list.reset(entry.getValue().getText());
            if(words_list.find()) messages.add(entry.getValue());
        }

        return messages;
    }
    /*
       @REQUIRES : words != null && words.isEmpty() == false && words.contains(null) == false
                   && words.contains("") == false
       @THROWS : NullPointerException, IllegalArgumentException
       @RETURN : List of posts in feed | for all i : 0 <= i < List.size() ==> (EXISTS string in words | (List.get(i).getText() contains string))
     */

    // ********************
    // ** STATIC METHODS **
    // ********************

    // Restituisce la rete sociale derivata dalla lista di post (parametro del metodo),
    // analizzando i mi piace e le persone menzionate
    public static Map<String, Set<String>> guessFollowers(List<Post> ps) throws NullPointerException, IllegalArgumentException {
        if(ps == null) throw new NullPointerException("ps == null");
        if(ps.contains(null)) throw new NullPointerException("ps.contains(null)");
        if(ps.isEmpty()) throw new IllegalArgumentException("List is empty");

        Map<String, Set<String>> network = new HashMap<String, Set<String>>();
        Map<String, Set<Post>> like = new HashMap<String, Set<Post>>();
        Map<Integer, Post> messages = new HashMap<Integer, Post>();

        Pattern mention = Pattern.compile("@([a-zA-Z_0-9]{5,15})");
        Matcher m = mention.matcher("");

        Integer id;
        String text;
        String author;

        Set<String> set_mentioned;
        Set<String> set_following;
        Set<Post> set_like;

        for(Post post : ps)
        {
            author = post.getAuthor();
            text = post.getText();

            if(text.matches("#LIKE_[0-9]+"))
            {
                if(like.containsKey(author))
                {
                    set_like = like.get(author);
                }
                else
                {
                    set_like = new HashSet<Post>();
                }

                set_like.add(post);
                like.put(author, set_like);
            }
            else
            {
                messages.put(post.getId(), post);
                set_mentioned = new HashSet<String>();
                m.reset(text);

                while(m.find())
                {
                    if(!m.group(1).equals(author)) set_mentioned.add(m.group(1));
                }

                if(network.containsKey(author))
                {
                    set_following = network.get(author);
                    set_following.addAll(set_mentioned);
                    network.put(author, set_following);
                }
                else
                {
                    network.put(author, set_mentioned);
                }
            }
        }

        for(Map.Entry<String, Set<Post>> entry : like.entrySet())
        {
            for(Post like_post : entry.getValue())
            {
                id = Integer.parseInt(like_post.getText().substring(6));

                if(messages.containsKey(id))
                {
                    if(!messages.get(id).getAuthor().equals(entry.getKey()) && like_post.getTimestamp().after(messages.get(id).getTimestamp()))
                    {
                        if(network.containsKey(entry.getKey()))
                        {
                            set_following = network.get(entry.getKey());
                        }
                        else
                        {
                            set_following = new HashSet<String>();
                        }

                        set_following.add(messages.get(id).getAuthor());
                        network.put(entry.getKey(), set_following);
                    }
                }
            }
        }

        return network;
    }
    /*
       @REQUIRES : ps != null && ps.isEmpty() == false && ps.contains(null) == false
       @THROWS : NullPointerException, IllegalArgumentException
       @RETURN : Map of <user, following> | for all keys in Map ==>
                 (Map.get(key) is Set of following | for all i : 0 <= i < Set.size() ==>
                 ((EXISTS post in ps | (post.getAuthor().equals(key) == true && post.getText().contains("@Set.get(i)") == true))
                  ||
                  (EXISTS post1, post2 in ps | (post1.getAuthor().equals(key) == true && post1.getText().matches("#LIKE_[0-9]+") == true
                                                 && post2.getText().matches("#LIKE_[0-9]+") == false && post2.getAuthor().equals(key) == false
                                                 && post2.getId().equals(Integer.parseInt("[0-9]+")) == true
                                                 && post1.getTimestamp().after(post2.getTimestamp()) == true))))
     */

    // Restituisce l’insieme degli utenti menzionati (inclusi) nella lista di post
    public static Set<String> getMentionedUsers(List<Post> ps) throws NullPointerException, IllegalArgumentException {
        if(ps == null) throw new NullPointerException("ps == null");
        if(ps.contains(null)) throw new NullPointerException("ps.contains(null)");
        if(ps.isEmpty()) throw new IllegalArgumentException("List is empty");

        Set<String> users = new HashSet<String>();
        Pattern mention = Pattern.compile("@([a-zA-Z_0-9]{5,15})");
        Matcher m = mention.matcher("");

        for(Post post : ps)
        {
            m.reset(post.getText());

            while(m.find())
            {
                users.add(m.group(1));
            }
        }

        return users;
    }
    /*
       @REQUIRES : ps != null && ps.isEmpty() == false && ps.contains(null) == false
       @THROWS : NullPointerException, IllegalArgumentException
       @RETURN : Set of users | for all i : 0 <= i < Set.size() ==> (EXISTS post in ps | (post.getText().contains("@Set.get(i)") == true))
     */

    // Restituisce la lista dei post effettuati dall’utente
    // il cui nome è dato dal parametro username presenti nella lista ps
    public static List<Post> writtenBy(List<Post> ps, String username) throws NullPointerException, UsernameException, IllegalArgumentException {
        if(ps == null) throw new NullPointerException("ps == null");
        if(ps.contains(null)) throw new NullPointerException("ps.contains(null)");
        if(username == null) throw new NullPointerException("username == null");
        if(ps.isEmpty()) throw new IllegalArgumentException("List is empty");
        if(!username.matches("[a-zA-Z_0-9]{5,15}")) throw new UsernameException("Username " + username + " illegal format");

        List<Post> messages = new ArrayList<Post>();

        for(Post post : ps)
        {
            if(post.getAuthor().equals(username))
            {
                messages.add(post);
            }
        }

        return messages;
    }
    /*
       @REQUIRES : ps != null && username != null && ps.isEmpty() == false && ps.contains(null) == false
                   && username.matches("[a-zA-Z_0-9]{5,15}") == true
       @THROWS : NullPointerException, UsernameException, IllegalArgumentException
       @RETURN : List of posts in ps | for all i : 0 <= i < List.size() ==> (List.get(i).getAuthor().equals(username) == true)
     */

    // ********************
    // *** TEST METHODS ***
    // ********************

    public static void printNetwork(Map<String, Set<String>> nw)
    {
        for(Map.Entry<String, Set<String>> entry : nw.entrySet())
        {
            System.out.print("[" + entry.getKey() + " | <");

            boolean b = true;

            for(String following : entry.getValue())
            {
                if(b)
                {
                    System.out.print(following);
                    b = false;
                }
                else
                {
                    System.out.print("," + following);
                }
            }

            System.out.print(">] ");
        }
    }
}
