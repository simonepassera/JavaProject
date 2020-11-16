import java.sql.Timestamp;

public class Message implements Post {
    /*
        Representation Invariant : author != null && text != null && timestamp != null
                                   && author.length() != 0 && 0 < text.length() <= 140

        Abstraction function :  AF(id) = identificatore univoco del post
                                AF(author) = utente univoco della rete sociale che ha scritto il post
                                AF(text) = testo (massimo 140 caratteri) del post, in cui è possibile menzionare
                                           altri utenti appartenenti alla rete sociale con l' uso del simbolo (@)
                                           ad es @Nome1 @Nome2 ...
                                           Inoltre un utente può mettere un mi piace ad un post, scrivendo #LIKE_id
                                           dove id è l' identificatore univoco del post
                                AF(timestamp) = data e ora di invio del post
     */

    static int id_generator = 0;

    private int id;
    private String author;
    private String text;
    private Timestamp timestamp;

    // Crea un messaggio
    public Message(String author, String text, Timestamp timestamp) throws NullPointerException, IllegalArgumentException {
        if(author == null || text == null || timestamp == null)
        {
            throw new NullPointerException();
        }

        if(author.isEmpty() || text.isEmpty() || text.length() > 140)
        {
            throw new IllegalArgumentException();
        }

        this.id = ++id_generator;
        this.author = author;
        this.text = text;
        this.timestamp = (Timestamp) timestamp.clone();
    }
    /*
       @REQUIRES : id != null && author != null && text != null && timestamp != null
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : id, author, text
       @EFFECTS :  Inizializza id, author, text con i rispettivi parametri
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
}
