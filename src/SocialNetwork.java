import java.util.*;

public interface SocialNetwork {
    /*
        Overview : Tipo modificabile della componente software che gestisce e analizza
                   la rete sociale MicroBlog.

        Typical element : <USERS, POSTS>
                          USERS -> insieme di coppie <utente, insieme delle persone da lui seguite>
                          POSTS -> insieme di Post <id, utente, testo, data e ora di pubblicazione>
     */

    // Crea un nuovo utente nella rete sociale, restituisce il proprio username
    String addUser(String username) throws NullPointerException, UsernameException;
    /*
       @REQUIRES : username != null && USERS.contains(username) == false &&
                   username.matches("[a-zA-Z_0-9]{5,15}") == true
       @THROWS : NullPointerException, UsernameException
       @MODIFIES : USERS
       @EFFECTS : <USERS>_post = <USERS>_pre U <username, ()>
       @RETURN : username
     */

    // Aggiunge ad username un follower
    void addFollower(String username, String follower) throws NullPointerException, UsernameException, FollowerException;
    /*
       @REQUIRES : username != null && follower != null && username.equals(follower) == false
                   && USERS.contains(follower) == true && USERS.contains(username) == true
       @THROWS : NullPointerException, UsernameException, FollowerException
       @MODIFIES : USERS
       @EFFECTS : <USERS>_post = <USERS>_pre U <follower, username>
     */

    // Aggiunge un post creato da username
    Integer addPost(String username, String text) throws NullPointerException, UsernameException, LikeException, MentionException, PostException;
    /*
       @REQUIRES : username != null && text != null && USERS.contains(username) == true && 0 < text.length() <= 140
                   && text.equals("#LIKE_id") ==> ((POSTS.contains(id) == true) && <username, following.contains(POSTS.get(id).getAuthor()) == true>)
                   && for all @user_mention in text ==> ((USERS.contains(user_mention) == true) && <username, following.contains(user_mention) == true>)
       @THROWS : NullPointerException, UsernameException, LikeException, MentionException, PostException
       @MODIFIES : POSTS
       @EFFECTS : <POSTS>_post = <POSTS>_pre U <unique_id, username, text, current_time>
       @RETURN : unique_id in <unique_id, username, text, current_time>
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
       @RETURN : Set of USERS | for all i : 0 <= i < Set.size() ==> (EXISTS post in POSTS | (post.getText().contains("@Set.get(i)") == true))
     */

    // Restituisce la lista dei post effettuati dall’utente nella rete sociale
    // il cui nome è dato dal parametro username
    List<Post> writtenBy(String username) throws NullPointerException, UsernameException;
    /*
       @REQUIRES : username != null && USERS.contains(username) == true
       @THROWS : NullPointerException, UsernameException
       @RETURN : List of POSTS | for all i : 0 <= i < List.size() ==> (List.get(i).getAuthor().equals(username) == true)
     */

    // Restituisce la lista dei post presenti nella rete sociale che includono
    // almeno una delle parole presenti nella lista delle parole argomento del metodo
    List<Post> containing(List<String> words) throws NullPointerException, IllegalArgumentException;
    /*
       @REQUIRES : words != null && words.isEmpty() == false && words.contains(null) == false
                   && words.contains("") == false
       @THROWS : NullPointerException, IllegalArgumentException
       @RETURN : List of POSTS | for all i : 0 <= i < List.size() ==> (EXISTS string in words | (List.get(i).getText() contains string))
     */
}