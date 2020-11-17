import java.util.*;

public interface SocialNetwork {
    /*
        Overview : Tipo modificabile di una componente software che gestisce e analizza
                   la rete sociale MicroBlog. Una persona nella rete sociale è rappresentata e
                   identificata in modo univoco dal nome. Gli utenti della rete sociale
                   non possono seguire se stessi ed ognuno può seguire un numero indefinito
                   di utenti. Un utente può mettere un mi piace ad un post oppure menzionare un utente, solo se
                   segue il creatore del post o l' utente menzionato.

        Typical element : <USERS, POSTS>
                          USERS insieme di coppie <utente, insieme delle persone da lui seguite>
                          POSTS è l' insieme di post nella rete sociale
     */

    // Crea un nuovo utente e l' insieme delle persone da lui seguite nella rete sociale,
    // restituisce il proprio username
    String addUser(String username, List<String> following) throws NullPointerException, IllegalArgumentException;
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
    String addUser(String username) throws NullPointerException, IllegalArgumentException;
    /*
       @REQUIRES : username != null && USERS.contains(username) == false
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : USERS
       @EFFECTS : <USERS>_post = <USERS>_pre U <username, () >
       @RETURN : username
     */

    // Aggiunge ad username un follower
    void addFollower(String username, String follower) throws NullPointerException, IllegalArgumentException;
    /*
       @REQUIRES : username != null && follower != null && username.equals(follower) == false
                   && USERS.contains(follower) == true && USERS.contains(username) == true
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : USERS
       @EFFECTS : <USERS>_post = <USERS>_pre U <follower, username>
     */

    // Aggiunge ad username un insieme di followers
    void addFollower(String username, List<String> followers) throws NullPointerException, IllegalArgumentException;
    /*
       @REQUIRES : username != null && followers != null && USERS.contains(username) == true
                   && USERS.containsAll(followers) == true && for all i != j : 0 <= i, j < followers.size() ==> (followers.get(i) != followers.get(j))
                   && for all i : 0 <= i < followers.size() ==> (username.equals(followers.get(i)))
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : USERS
       @EFFECTS : <USERS>_post = <USERS>_pre U [ for all i : 0 <= i < followers.size() ==> <followers.get(i), username> ]
     */

    // Aggiunge un post creato da username
    void addPost(String username, Post post);
    /*
       @REQUIRES : username != null && post != null && USERS.contains(username) == true
                   && username.equals(post.getAuthor()) == true && post.getTimestamp().compareTo(currentTime) <= 0
                   && post.getText().containsAll(#LIKE_id) ==> (POSTS.contains(id) == true) && <username, following.contains(POSTS.get(id).getAuthor()) == true>
                   && post.getText().containsAll(@user_mention) ==> (USERS.contains(user_mention) == true) && <username, following.contains(user_mention == true)>
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : POSTS
       @EFFECTS : <POSTS>_post = <POSTS>_pre U post
     */

    // Aggiunge un insieme di post creati da username
    void addPost(String username, List<Post> posts);
    /*
       @REQUIRES : username != null && posts != null && USERS.contains(username) == true
                   && for all i != j : 0 <= i, j < posts.size() ==> (posts.get(i) != posts.get(j))
                   && for all i : 0 <= i < posts.size() ==> (username.equals(posts.get(i).getAuthor()) == true)
                   && for all i : 0 <= i < posts.size() ==> (posts.get(i).getTimestamp().compareTo(currentTime) <= 0)
                   && for all i : 0 <= i < posts.size() ==> (posts.get(i).getText().containsAll(#LIKE_id) ==> (POSTS.contains(id) == true) && <username, following.contains(POSTS.get(id).getAuthor()) == true>)
                   && for all i : 0 <= i < posts.size() ==> (posts.get(i).getText().containsAll(@user_mention) ==> (USERS.contains(user_mention) == true) && <username, following.contains(user_mention == true)>)
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : POSTS
       @EFFECTS : <POSTS>_post = <POSTS>_pre U [ for all i : 0 <= i < posts.size() ==> posts.get(i) ]
     */

    // Restituisce gli utenti più influenti delle rete sociale, ovvero quelli che hanno
    // un numero maggiore di “follower”
    List<String> influencers();
    /*
       @RETURN : List of USERS | for all i : 0 <= i < USERS.size()-1 ==> (List.get(i).followers() >= List.get(i+1).followers())
     */

    // Restituisce l’insieme degli utenti menzionati (inclusi) nei post presenti nella rete sociale
    Set<String> getMentionedUsers();
    /*
       @RETURN : Set of USERS | for all i : 0 <= i < Set.size() ==> (POSTS.getText.contains(@Set.get(i)) == true)
     */

    // Restituisce la lista dei post effettuati dall’utente nella rete sociale
    // il cui nome è dato dal parametro username
    List<Post> writtenBy(String username);
    /*
       @REQUIRES : username != null && USERS.contains(username) == true
       @THROWS : NullPointerException, IllegalArgumentException
       @RETURN : List of POSTS | for all i : 0 <= i < List.size() ==> (List.get(i).getAuthor().equals(username) == true)
     */

    // Restituisce la lista dei post presenti nella rete sociale che includono
    // almeno una delle parole presenti nella lista delle parole argomento del metodo
    List<Post> containing(List<String> words);
    /*
       @REQUIRES : words != null
       @THROWS : NullPointerException
       @RETURN : List of POSTS | for all i : 0 <= i < List.size() ==> (EXISTS string in words | (List.get(i).getText().contains(string) == true))
     */
}