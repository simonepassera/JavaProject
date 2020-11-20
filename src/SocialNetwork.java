import java.util.*;

public interface SocialNetwork {
    /*
        Overview : Tipo modificabile di una componente software che gestisce e analizza
                   la rete sociale MicroBlog. Una persona nella rete sociale è rappresentata e
                   identificata in modo univoco dal nome. Gli utenti della rete sociale
                   non possono seguire se stessi ed ognuno può seguire un numero indefinito
                   di utenti. Un utente può mettere un mi piace ad un post oppure menzionare un utente, solo se
                   segue il creatore del post o l' utente menzionato. Per possibile menzionare altri utenti, appartenenti alla
                   rete sociale, in un post, con l' uso del simbolo (@) ad es @Nome1 @Nome2 ...
                   Quando un utente mette un mi piace ad un post, viene codificato all' interno della rete sociale
                   con un post in cui il campo di testo è formato da "#LIKE_id" dove id è l' identificatore univoco del post.

        Typical element : <USERS, POSTS>
                          USERS insieme di coppie <utente, insieme delle persone da lui seguite>
                          POSTS insieme di post <id, utente, testo, timestamp>
     */

    // Crea un nuovo utente nella rete sociale, restituisce il proprio username
    String addUser(String username) throws NullPointerException, IllegalArgumentException;
    /*
       @REQUIRES : username != null && USERS.contains(username) == false &&
                   username.matches("[a-zA-Z_0-9]{5,15}") == true
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : USERS
       @EFFECTS : <USERS>_post = <USERS>_pre U <username, () >
       @RETURN : username
     */

    // Crea un nuovo utente e l' insieme delle persone da lui seguite nella rete sociale,
    // restituisce il proprio username
    String addUser(String username, List<String> following) throws NullPointerException, IllegalArgumentException;
    /*
       @REQUIRES : username != null && username.matches("[a-zA-Z_0-9]{5,15}") == true && following != null &&
                   USERS.contains(username) == false && USERS.containsAll(following) == true
                   && for all i != j : 0 <= i, j < following.size() ==> (following.get(i) != following.get(j))
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES :  USERS
       @EFFECTS : <USERS>_post = <USERS>_pre U <username, following>
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
    void addPost(String username, String text) throws NullPointerException, IllegalArgumentException;
    /*
       @REQUIRES : username != null && text != null && USERS.contains(username) == true
                   && text.equals("#LIKE_id") ==> ((POSTS.contains(id) == true) && <username, following.contains(POSTS.get(id).getAuthor()) == true>)
                   && for all @user_mention in text ==> ((USERS.contains(user_mention) == true) && <username, following.contains(user_mention == true)>)
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : POSTS
       @EFFECTS : <POSTS>_post = <POSTS>_pre U <unique_id, username, text, current_time>
     */

    // Aggiunge un insieme di post creati da username
    void addPost(String username, List<String> texts) throws NullPointerException, IllegalArgumentException;
    /*
       @REQUIRES : username != null && texts != null && USERS.contains(username) == true
                   && for all i != j : 0 <= i, j < texts.size() ==> (texts.get(i) != texts.get(j))
                   && for all i : 0 <= i < texts.size() ==> (texts.get(i).equals("#LIKE_id") ==> ((POSTS.contains(id) == true) && <username, following.contains(POSTS.get(id).getAuthor()) == true>))
                   && for all i : 0 <= i < texts.size() ==> (for all @user_mention in texts.get(i) ==> ((USERS.contains(user_mention) == true) && <username, following.contains(user_mention == true)>))
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : POSTS
       @EFFECTS : <POSTS>_post = <POSTS>_pre U [ for all i : 0 <= i < texts.size() ==> <unique_id, username, texts.get(i), current_time> ]
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
    List<Post> writtenBy(String username) throws NullPointerException, IllegalArgumentException;
    /*
       @REQUIRES : username != null && USERS.contains(username) == true
       @THROWS : NullPointerException, IllegalArgumentException
       @RETURN : List of POSTS | for all i : 0 <= i < List.size() ==> (List.get(i).getAuthor().equals(username) == true)
     */

    // Restituisce la lista dei post presenti nella rete sociale che includono
    // almeno una delle parole presenti nella lista delle parole argomento del metodo
    List<Post> containing(List<String> words) throws NullPointerException;
    /*
       @REQUIRES : words != null
       @THROWS : NullPointerException
       @RETURN : List of POSTS | for all i : 0 <= i < List.size() ==> (EXISTS string in words | (List.get(i).getText().contains(string) == true))
     */
}