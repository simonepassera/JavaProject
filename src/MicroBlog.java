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
                                        (feed.get(key).getText.matches("#LIKE_[0-9]+")) ==>
                                          feed.containsKey(Integer.parseInt("[0-9]+")) == true) && (users.get(feed.get(key).getAuthor).contains(feed.get(Integer.parseInt("[0-9]+")).getAuthor()) == true)))
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
        if(users.containsKey(username)) throw new UsernameException("Username" + username + "already exists");
        if(!username.matches("[a-zA-Z_0-9]{5,15}")) throw new UsernameException("Username" + username + "illegal format");

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
        if(!users.containsKey(username)) throw new UsernameException("Username" + username + "not exists");
        if(username.equals(follower)) throw new FollowerException("You can't follow yourself");
        if(!users.containsKey(follower)) throw new FollowerException("Follower" + follower + "not exists");

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
        if(!users.containsKey(username)) throw new UsernameException("Username" + username + "not exists");
        if(text.isEmpty()) throw new PostException("The text of" + username + "is empty");
        if(text.length() > 140) throw new PostException("The text of" + username + "is too long (max 140 characters)");

        if(text.matches("#LIKE_[0-9]+"))
        {
            int id = Integer.parseInt(text.substring(6));

            if(!feed.containsKey(id)) throw new LikeException("Post" + id + "not exists");
            if(!users.get(username).contains(feed.get(id).getAuthor())) throw new LikeException("You can't like" + id);
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
         List<Map.Entry<String, Integer>> followers = new ArrayList<>(this.followers.entrySet());
         followers.sort(Map.Entry.comparingByValue());

         List<String> users = new ArrayList<String>();
         for(Map.Entry<String, Integer> entry : followers)
         {
             users.add(entry.getKey());
         }

         return users;
    }
    /*
       @RETURN : keys of followers order by values
     */

    // Restituisce l’insieme degli utenti menzionati (inclusi) nei post presenti nella rete sociale
    public Set<String> getMentionedUsers() {
        return null;
    }

    // Restituisce la lista dei post effettuati dall’utente nella rete sociale
    // il cui nome è dato dal parametro username
    public List<Post> writtenBy(String username) {
        return null;
    }

    // Restituisce la lista dei post presenti nella rete sociale che includono
    // almeno una delle parole presenti nella lista delle parole argomento del metodo
    public List<Post> containing(List<String> words) {
        return null;
    }

    // ********************
    // ** STATIC METHODS **
    // ********************

    // Restituisce la rete sociale derivata dalla lista di post appartenenti al
    // social network, analizzando i mi piace e le persone menzionate
    public static Map<String, Set<String>> guessFollowers(List<Post> ps){
        return null;
    }
    /*
       @REQUIRES : ps != null && POSTS.containsAll(ps)
       @THROWS : NullPointerException, IllegalArgumentException
       @RETURN : Sottoinsieme di USERS
     */

    // Restituisce l’insieme degli utenti menzionati (inclusi) nella lista di post
    public static Set<String> getMentionedUsers(List<Post> ps){
        return null;
    }
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */

    // Restituisce la lista dei post effettuati dall’utente
    // il cui nome è dato dal parametro username presenti nella lista ps
    public static List<Post> writtenBy(List<Post> ps, String username) {
        return null;
    }
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */
}
