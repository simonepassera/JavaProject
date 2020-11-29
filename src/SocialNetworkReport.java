import java.util.*;

public interface SocialNetworkReport extends SocialNetwork {
    /*
        Overview : Estensione del tipo di dato modificabile SocialNetwork, che permette di
                   segnalare contenuti offensivi presenti nei post della rete sociale.
                   Un post può ricevere una segnalazione se contiene almeno una delle parole
                   giudicate offensive, all' interno del campo di testo.
                   Il social viene inizializzato di default con la lista di parole offensive
                   default_words = [idrogeno, interprete, canada, poligono, fantasma, freccette]

        Typical element : <USERS, POSTS, REPORTS, WORDS>
                          REPORTS -> insieme dei post segnalati dal social network
                          WORDS -> lista di parole offensive
     */

    // Restituisce i post segnalati dal social network, valutati con la lista di parole
    // offensive presenti in quel momento
    public List<Post> getReports();
    /*
       @RETURN : List of posts in REPORTS | for all i : 0 <= i < List.size() ==> (EXISTS string in WORDS | (List.get(i).getText() contains string))
     */

    // Restituisce la lista di parole offensive presenti nel social network usate per segnalare i post
    public List<String> getWords();
    /*
       @RETURN : WORDS
     */

    // Aggiunge le parole presenti nella lista words (parametro del metodo)
    // alla lista di parole offensive, presente nel social network
    public void addWords(List<String> words) throws NullPointerException, IllegalArgumentException;
    /*
       @REQUIRES : words != null && words.isEmpty() == false && words.contains(null) == false
                   && words.contains("") == false
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : WORDS, REPORTS
       @EFFECTS : <WORDS>_post = <WORDS>_pre U words && REPORTS_post = REPORTS_pre U (Set of posts in POSTS | for all i : 0 <= i < Set.size() ==> (EXISTS string in words | (Set.get(i).getText() contains string)))
     */

    // Sostituisce la lista di parole offensive presenti nel social network,
    // con la lista words (parametro del metodo)
    public void changeWords(List<String> words) throws NullPointerException, IllegalArgumentException;
    /*
       @REQUIRES : words != null && words.isEmpty() == false && words.contains(null) == false
                   && words.contains("") == false && for all i != j : 0 <= i,j < words.size() ==> (words.get(i).equals(words.get(j)) == false)
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : WORDS
       @EFFECTS : <WORDS>_post = words && REPORTS_post = (Set of posts in POSTS | for all i : 0 <= i < Set.size() ==> (EXISTS string in words | (Set.get(i).getText() contains string)))
     */
}
