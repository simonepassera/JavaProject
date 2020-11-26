import java.util.*;
public interface SocialNetworkReport extends SocialNetwork {
    /*
        Overview : Estensione del tipo di dato modificabile SocialNetwork, che permette di
                   segnalare contenuti offensivi presenti nei post della rete sociale.
                   Un post può ricevere una segnalazione se contiene almeno una delle parole
                   giudicate offensive, all' interno del campo di testo.

        Typical element : <REPORTS, WORDS>
                          REPORTS -> insieme dei post segnalati dal social network
                          WORDS -> lista di parole offensive
     */

    // Restituisce i post segnalati dal social network, valutati con la lista di parole
    // offensive presenti in quel momento
    public List<Post> getReports();
    /*
       @RETURN : List of posts in REPORTS | for all i : 0 <= i < List.size() ==> (EXISTS string in WORDS | (List.get(i).getText() contains string))
     */

    // Aggiunge le parole presenti nella lista words (parametro del metodo)
    // alla lista di parole offensive, presente nel social network
    public void addWords(List<String> words);
    /*
       @REQUIRES : words != null && words.isEmpty() == false && words.contains(null) == false
                   && words.contains("") == false
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : WORDS
       @EFFECTS : <WORDS>_post = <WORDS>_pre U words
     */

    // Sostituisce la lista di parole offensive presenti nel social network,
    // con la lista words (parametro del metodo)
    public void changeWords(List<String> words);
    /*
       @REQUIRES : words != null && words.isEmpty() == false && words.contains(null) == false
                   && words.contains("") == false && for all i != j : 0 <= i,j < words.size() ==> (words.get(i).equals(words.get(j)) == false)
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : WORDS
       @EFFECTS : <WORDS>_post = words
     */
}
