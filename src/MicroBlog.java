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

    private Map<String, Set<String>> users;
    private Map<String, Integer> followers;
    private Map<Integer, Post> feed;
    private Set<String> mentioned;

    // Crea un nuovo utente nella rete sociale, restituisce il proprio username
    public String addUser(String username) throws NullPointerException, UsernameException {
        if(username == null) throw new NullPointerException();
        if(users.containsKey(username)) throw new UsernameException("Username " + username + " already exists");
        if(!username.matches("[a-zA-Z_0-9]{5,15}")) throw new UsernameException("Username " + username + " illegal format");

        users.put(username, null);

        return username;
    }
    /*
       @REQUIRES : username != null && users.containsKey(username) == false
       @THROWS : NullPointerException, UsernameException
       @MODIFIES : users
       @EFFECTS : users.put(username, null)
       @RETURN : username
     */

    // Aggiunge ad username un follower
    public void addFollower(String username, String follower) throws NullPointerException, UsernameException, FollowerException {
        if(username == null || follower == null) throw new NullPointerException();
        if(!users.containsKey(username)) throw new UsernameException("Username " + username + " not exists");
        if(username.equals(follower)) throw new FollowerException("You can't follow yourself");
        if(!users.containsKey(follower)) throw new FollowerException("Follower " + follower + " not exists");

        Set<String> set_following;

        if(users.get(follower) == null)
        {
            set_following = new HashSet<String>();
        }
        else
        {
            set_following = users.get(follower);
        }

        set_following.add(username);
        users.put(follower, set_following);
    }
    /*
       @REQUIRES : username != null && follower != null && users.containsKey(username) == true
                   && users.containsKey(follower) == true
       @THROWS : NullPointerException, UsernameException, FollowerException
       @MODIFIES : users
       @EFFECTS : users.put(follower, username)
     */

    // Aggiunge un post creato da username
    public void addPost(String username, String text) throws NullPointerException, UsernameException, LikeException, MentionException, PostException {
        if(username == null || text == null) throw new NullPointerException();
        if(!users.containsKey(username)) throw new UsernameException("Username " + username + " not exists");
        if(text.isEmpty()) throw new PostException("The text of " + username + " is empty");
        if(text.length() > 140) throw new PostException("The text of " + username + " is too long (max 140 characters)");

        if(text.matches("#LIKE_[0-9]+"))
        {
            int id = Integer.parseInt(text.substring(6));

            if(!feed.containsKey(id)) throw new LikeException("Post " + id + " not exists");
            if(feed.get(id).getText().matches("#LIKE_[0-9]+")) throw new LikeException("Post " + id + " is a like");
            if(!users.get(username).contains(feed.get(id).getAuthor())) throw new LikeException("You can't like " + id);
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

                if(!users.containsKey(u)) throw new MentionException("Username mentioned" + u + "not exists");
                if(!users.get(username).contains(u)) throw new MentionException("You can't mention" + u);
            }

            this.mentioned.addAll(mentioned);
        }

        Post post = new Message(username, text, new Timestamp(System.currentTimeMillis()));
        feed.put(post.getId(), post);
    }
    /*
       @REQUIRES : username != null && text != null && users.containsKey(username) == true
       @THROWS : NullPointerException, UsernameException, LikeException, MentionException, PostException
       @MODIFIES : feed, mentioned
       @EFFECTS : feed.put(new_post.getId(), new_post)
     */

    // Restituisce gli utenti più influenti delle rete sociale, ovvero quelli che hanno
    // un numero maggiore di “follower”
    public List<String> influencers() {
         List<Map.Entry<String, Integer>> followers = new ArrayList<Map.Entry<String, Integer>>(this.followers.entrySet());
         followers.sort(Map.Entry.comparingByValue());

         List<String> users = new ArrayList<String>();
         for(Map.Entry<String, Integer> entry : followers)
         {
             users.add(entry.getKey());
         }

         return users;
    }
    /*
       @RETURN : followers.keySet() order by values
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
        if(username == null) throw new NullPointerException();
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
        if(words == null || words.contains(null)) throw new NullPointerException();
        if(words.isEmpty() || words.contains("")) throw new IllegalArgumentException();

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
        if(ps == null || ps.contains(null)) throw new NullPointerException();
        if(ps.isEmpty()) throw new IllegalArgumentException();

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
                    if(!messages.get(id).getAuthor().equals(entry.getKey()) && like_post.getTimestamp().compareTo(messages.get(id).getTimestamp()) > 0)
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
                                                 && post1.getTimestamp().compareTo(post2.getTimestamp()) > 0))))
     */

    // Restituisce l’insieme degli utenti menzionati (inclusi) nella lista di post
    public static Set<String> getMentionedUsers(List<Post> ps) throws NullPointerException, IllegalArgumentException {
        if(ps == null || ps.contains(null)) throw new NullPointerException();
        if(ps.isEmpty()) throw new IllegalArgumentException();

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
        if(ps == null || ps.contains(null) || username == null) throw new NullPointerException();
        if(ps.isEmpty()) throw new IllegalArgumentException();
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
}
