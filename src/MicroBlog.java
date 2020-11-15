import java.util.*;

public class MicroBlog implements SocialNetwork {
    /*
        Representation Invariant : users != null && for all keys of users ==> (users.get(key).contains(key) == false)
                                   &&

        Abstraction function : AF(users) = Insieme di coppie <utente, insieme delle persone da lui seguite>
     */

    Map<String, Set<String>> users;

    // Crea un nuovo utente e l' insieme delle persone da lui seguite nella rete sociale,
    // restituisce il proprio username
    public String addUser(String username, List<String> following){
        if(username == null || following == null) throw new NullPointerException();
        if(users.containsKey(username)) throw new IllegalArgumentException();

        for(String u : following)
        {
            if(!users.containsKey(u)) throw new IllegalArgumentException();
        }

        Set<String> set_following = new HashSet<String>();

        for(String s : following)
        {
            if(!set_following.add(s)) throw new IllegalArgumentException();
        }

        users.put(username, set_following);

        return username;
    }
    /*
       @REQUIRES : username != null && following != null &&
                   for all i : 0 <= i < following.size() ==> users.containsKey(following.get(i)) == true &&
                   for all i != j : 0 <= i, j < following.size() ==> (following.get(i) != following.get(j))
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES :  users
       @EFFECTS : users.put(username, following)
       @RETURN : username
     */

    // Crea un nuovo utente nella rete sociale, restituisce il proprio username
    public String addUser(String username){
        if(username == null) throw new NullPointerException();
        if(users.containsKey(username)) throw new IllegalArgumentException();

        users.put(username, null);

        return username;
    }
    /*
       @REQUIRES : username != null && users.containsKey(username) == false
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : users
       @EFFECTS : users.put(username, null)
       @RETURN : username
     */

    // Aggiunge ad username un follower
    public void addFollower(String username, String follower) {
        if(username == null || follower == null) throw new NullPointerException();
        if(username.equals(follower) || !users.containsKey(follower) || !users.containsKey(username)) throw new IllegalArgumentException();

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
       @REQUIRES : username != null && follower != null && username.equals(follower) == false
                   && users.containsKey(follower) == true && users.containsKey(username) == true
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : users
       @EFFECTS : users.put(follower, username)
     */


    // Aggiunge ad username un insieme di followers
    public void addFollower(String username, List<String> followers) {
        if(username == null || followers == null) throw new NullPointerException();
        if(!users.containsKey(username)) throw new IllegalArgumentException();

        Set<String> set_followers = new HashSet<String>();

        for(String s : followers)
        {
            if(!set_followers.add(s) || username.equals(s)) throw new IllegalArgumentException();
        }

        for(String s : set_followers)
        {
            addFollower(s, username);
        }
    }
    /*
       @REQUIRES : username != null && followers != null && users.containsKey(username) == true
                   && for all i : 0 <= i < followers.size() ==> (users.containsKey(followers.get(i)) == true && username.equals(followers.get(i) == false)
                   && for all i != j : 0 <= i, j < followers.size() ==> (followers.get(i) != followers.get(j))
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : users
       @EFFECTS : for all i : 0 <= i < followers.size() ==> users.put(followers.get(i), username)
     */

    public void addPost(String username, Post post) {

    }

    public void addPost(String username, List<Post> post) {

    }

    public Map<String, Set<String>> guessFollowers(List<Post> ps) {
        return null;
    }

    public List<String> influencers() {
        return null;
    }

    public Set<String> getMentionedUsers() {
        return null;
    }

    public Set<String> getMentionedUsers(List<Post> ps) {
        return null;
    }

    public List<Post> writtenBy(String username) {
        return null;
    }

    public List<Post> writtenBy(List<Post> ps, String username) {
        return null;
    }

    public List<Post> containing(List<String> words) {
        return null;
    }
}
