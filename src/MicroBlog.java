import java.util.*;

public class MicroBlog implements SocialNetwork {
    /*
        Representation Invariant : users != null && for all keys of users ==> (users.get(key).contains(key) == false)
                                   &&

        Abstraction function : AF(users) = Insieme di coppie <utente, insieme delle persone da lui seguite>
     */

    private Map<String, Set<String>> users;
    private Map<String, Integer> followers;

    // Crea un nuovo utente e l' insieme delle persone da lui seguite nella rete sociale,
    // restituisce il proprio username
    public String addUser(String username, List<String> following) throws NullPointerException, IllegalArgumentException {
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
    public String addUser(String username) throws NullPointerException, IllegalArgumentException {
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
    public void addFollower(String username, String follower) throws NullPointerException, IllegalArgumentException {
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
    public void addFollower(String username, List<String> followers) throws NullPointerException, IllegalArgumentException {
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

    // Aggiunge un post creato da username
    public void addPost(String username, Post post) {

    }

    // Aggiunge un insieme di post creati da username
    public void addPost(String username, List<Post> post) {

    }

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
       @RETURN : followers | for all i : 0 <= i < followers.size()-1 ==> (followers.get(i).followers() >= List.get(i+1).followers())
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

    // ** STATIC METHODS **

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
