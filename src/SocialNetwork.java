import java.util.*;

public interface SocialNetwork {
    /*
        Overview : Tipo modificabile di una componente software che gestisce e analizza
                   la rete sociale MicroBlog. Una persona nella rete sociale è rappresentata e
                   identificata in modo univoco dal nome. Gli utenti della rete sociale
                   non possono seguire se stessi ed ognuno può seguire un numero indefinito
                   di utenti. Un utente può mettere un cuore ad un post se e solo se segue
                   il creatore del post.

        Typical element : <USERS, POSTS>
                          USERS insieme di coppie <utenti, insieme delle persone da lui seguite>
                          POSTS è l' insieme di post nella rete sociale
     */

    // Crea un nuovo utente e l' insieme delle persone da lui seguite nella rete sociale,
    // restituisce il proprio username
    String addUser(String username, List<String> following);
    /*
       @REQUIRES : username != null && following != null &&
                   USERS.contains(username) == false && USERS.containsAll(following) == true
                   && for all i != j : 0 <= i, j < following.size() ==> (following.get(i) != following.get(j))
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES :  USERS
       @EFFECTS : <USERS>_post = <USERS>_pre U <username, following>
       @RETURN : username
     */

    // Crea un nuovo utente nella rete sociale, restituisce il proprio username
    String addUser(String username);
    /*
       @REQUIRES : username != null && USERS.contains(username) == false
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : USERS
       @EFFECTS : <USERS>_post = <USERS>_pre U <username, () >
       @RETURN : username
     */

    // Aggiunge ad username un follower
    void addFollower(String username, String follower);
    /*
       @REQUIRES : username != null && follower != null && USERS.contains(follower) == true
                   && USERS.contains(username) == true
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : USERS
       @EFFECTS : <USERS>_post = <USERS>_pre U <follower, username>
     */

    // Aggiunge ad username un insieme di followers
    void addFollower(String username, List<String> followers);
    /*
       @REQUIRES : username != null && followers != null && USERS.contains(username) == true
                   && USERS.containsAll(followers) == true && for all i != j : 0 <= i, j < followers.size() ==> (followers.get(i) != followers.get(j))
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : USERS
       @EFFECTS : <USERS>_post = <USERS>_pre U [ for all i : 0 <= i < followers.size() ==> <followers.get(i), username> ]
     */

    // Aggiunge un post creato da username
    void addPost(String username, Post post);
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */

    // Aggiunge un insieme di post creati da username
    void addPost(String username, List<Post> post);
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */

    // Inserisce nel post identificato univocamente da id un cuore appartenente ad username
    void addHeart(String username, Integer id);
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */

    // Inserisce nell' insieme dei post identificati univocamente da id
    // un cuore appartenente ad username
    void addHeart(String username, List<Integer> id);
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */

    // Restituisce la rete sociale derivata dalla lista di post
    Map<String, Set<String>> guessFollowers(List<Post> ps);
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */

    // Restituisce gli utenti più influenti delle rete sociale, ovvero quelli che hanno
    // un numero maggiore di “follower”
    List<String> influencers();
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */

    // Restituisce l’insieme degli utenti menzionati (inclusi) nei post presenti nella rete sociale
    Set<String> getMentionedUsers();
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */

    // Restituisce l’insieme degli utenti menzionati (inclusi) nella lista di post
    Set<String> getMentionedUsers(List<Post> ps);
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */

    // Restituisce la lista dei post effettuati dall’utente nella rete sociale
    // il cui nome è dato dal parametro username
    List<Post> writtenBy(String username);
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */

    // Restituisce la lista dei post effettuati dall’utente
    // il cui nome è dato dal parametro username presenti nella lista ps
    List<Post> writtenBy(List<Post> ps, String username);
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */

    // Restituisce la lista dei post presenti nella rete sociale che includono
    // almeno una delle parole presenti nella lista delle parole argomento del metodo
    List<Post> containing(List<String> words);
    /*
       @REQUIRES :
       @THROWS :
       @MODIFIES :
       @EFFECTS :
     */
}