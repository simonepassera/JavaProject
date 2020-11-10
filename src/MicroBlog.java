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
    String addUser(String username, Set<String> following){
        if(username == null || following == null) throw new NullPointerException();
        if(users.containsKey(username)) throw new IllegalArgumentException();

        for(String u : following)
        {
            if(!users.containsKey(u)) throw new IllegalArgumentException();
        }

        users.put(username, following);

        return username;
    }
    /*
       @REQUIRES : username != null && following != null &&
                   for all i : 0 <= i < following.size() ==> users.containsKey(following.get(i)) == true
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES :  users
       @EFFECTS : users.put(username, following)
       @RETURN : username
     */

    // Crea un nuovo utente nella rete sociale, restituisce il proprio username
    String addUser(String username){
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
}
