import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MicroBlog implements SocialNetwork {
    /*
        Representation Invariant :

        for all i : 0 <= i < hearts.size() ==> (hearts.get(i) != null)
        && for all i != j : 0 <= i, j < hearts.size() ==> (hearts.get(i) != hearts.get(j))

        Abstraction function :  AF(hearts) = insieme di followers appartenenti alla rete sociale che hanno espresso
                                             un apprezzamento positivo al post (equivalente ad un Like)
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
            if(set_following.contains(s)) throw new IllegalArgumentException();
            set_following.add(s);
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

    public void addFollower(String username, String follower) {

    }

    public void addFollower(String username, List<String> followers) {

    }

    public void addPost(String username, Post post) {

    }

    public void addPost(String username, List<Post> post) {

    }

    public void addHeart(String username, Integer id) {

    }

    public void addHeart(String username, List<Integer> id) {

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
