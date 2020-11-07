import java.util.*;

public interface SocialNetwork {
    /*
        Overview : Tipo modificabile di una componente software che gestisce e analizza
                   la rete sociale MicroBlog. Una persona nella rete sociale è rappresentata e
                   identificata in modo univoco dal nome. Gli utenti della rete sociale
                   non possono seguire se stessi.

        Typical element :

     */

    // Crea un nuovo utente e l' insieme delle persone da lui seguite nella rete sociale,
    // restituisce il proprio username
    String addUser(String username, Set<String> following);

    // Crea un nuovo utente nella rete sociale, restituisce il proprio username
    String addUser(String username);

    // Aggiunge ad username un nuovo follower
    void addFollower(String username, String follower);

    // Aggiunge un post creato da username
    void addPost(String username, Post post);

    // Inserisce nel post identificato univocamente da id un cuore appartenente ad username
    Boolean addHeart(String username, Integer id);

    // Restituisce la rete sociale derivata dalla lista di post
    Map<String, Set<String>> guessFollowers(List<Post> ps);

    // Restituisce gli utenti più influenti delle rete sociale, ovvero quelli che hanno
    // un numero maggiore di “follower”
    List<String> influencers();

    // Restituisce l’insieme degli utenti menzionati (inclusi) nei post presenti nella rete sociale
    Set<String> getMentionedUsers();

    // Restituisce l’insieme degli utenti menzionati (inclusi) nella lista di post
    Set<String> getMentionedUsers(List<Post> ps);

    // Restituisce la lista dei post effettuati dall’utente nella rete sociale
    // il cui nome è dato dal parametro username
    List<Post> writtenBy(String username);

    // Restituisce la lista dei post effettuati dall’utente
    // il cui nome è dato dal parametro username presenti nella lista ps
    List<Post> writtenBy(List<Post> ps, String username);

    // Restituisce la lista dei post presenti nella rete sociale che includono
    // almeno una delle parole presenti nella lista delle parole argomento del metodo
    List<Post> containing(List<String> words);
}