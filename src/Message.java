import java.sql.Timestamp;
import java.util.List;

public class Message implements Post {
    /*
        Representation Invariant : id != null && id >= 0 && author != null && author.matches("[a-zA-Z_0-9]{5,15}") == true
                                   && text != null && 0 < text.length() <= 140 && timestamp != null

        Abstraction function :  AF(id) = identificatore univoco del post, (numero naturale)
                                AF(author) = utente univoco della rete sociale che ha scritto il post,
                                             (il nome utente è compreso tra 5 e 15 caratteri, può contenere caratteri
                                             maiuscoli, minuscoli, numeri e sottolineato)
                                AF(text) = testo (massimo 140 caratteri) del post
                                AF(timestamp) = data e ora di invio del post
     */

    static int id_generator = 0;

    private final Integer id;
    private final String author;
    private final String text;
    private final Timestamp timestamp;

    // Crea un nuovo messaggio, con author, text e timestamp (parametri del costruttore)
    public Message(String author, String text, Timestamp timestamp) throws NullPointerException, PostException {
        if(author == null || text == null || timestamp == null) throw new NullPointerException("One or more parameters are null");
        if(!author.matches("[a-zA-Z_0-9]{5,15}")) throw new PostException("Author " + author + " illegal format");
        if(text.isEmpty()) throw new PostException("The text of " + author + " is empty");
        if(text.length() > 140) throw new PostException("The text of " + author + " is too long (max 140 characters)");

        this.id = id_generator++;
        this.author = author;
        this.text = text;
        this.timestamp = (Timestamp) timestamp.clone();
    }
    /*
       @REQUIRES : author != null && text != null && timestamp != null
       @THROWS : NullPointerException, PostException
       @MODIFIES : id, author, text, timestamp
       @EFFECTS :  Initialize id, author, text, timestamp inferred from parameters
     */

    // Restituisce l' identificatore univoco del post
    public Integer getId() {
        return id;
    }
    // @RETURN : id

    // Restituisce il nome utente dell' autore del post
    public String getAuthor() {
        return author;
    }
    // @RETURN : author

    // Restituisce il corpo del messaggio contenuto nel post
    public String getText() {
        return text;
    }
    // @RETURN : text

    // Restituisce data e ora di invio del post
    public Timestamp getTimestamp() {
        return (Timestamp) timestamp.clone();
    }
    // @RETURN : timestamp

    // ********************
    // *** TEST METHODS ***
    // ********************

    public static void printPost(List<Post> ps)
    {
        for(Post p : ps)
        {
            System.out.print("<" + p.getId() + "; " + p.getAuthor() + "; " + p.getText() + "; " + p.getTimestamp().toString() + "> ");
        }
    }
}
