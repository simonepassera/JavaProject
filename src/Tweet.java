import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Tweet implements Post{
    /*
        Representation Invariant : id != null && author != null && text != null && timestamp != null && hearts != null
                                   && author.length() != 0 && 0 < text.length() <= 140 &&
                                   for all i : 0 <= i < hearts.size() ==> (hearts.get(i) != null)
                                   && for all i != j : 0 <= i, j < hearts.size() ==> (hearts.get(i) != hearts.get(j))

        Abstraction function :  AF(id) = identificatore univoco del post
                                AF(author) = utente univoco della rete sociale che ha scritto il post
                                AF(text) = testo (massimo 140 caratteri) del post, in cui è possibile menzionare
                                           altri utenti appartenenti alla rete sociale con l' uso del simbolo (@)
                                           ad es @Nome1 @Nome2 ...
                                AF(timestamp) = data e ora di invio del post
                                AF(hearts) = insieme di followers appartenenti alla rete sociale che hanno espresso
                                             un apprezzamento positivo al post (equivalente ad un Like)
     */

    private Integer id;
    private String author;
    private String text;
    private Timestamp timestamp;
    private List<String> hearts;

    // Crea un tweet
    public Tweet(Integer id, String author, String text, Timestamp timestamp, List<String> hearts){
        if(id == null || author == null || text == null || timestamp == null || hearts == null)
        {
            throw new NullPointerException();
        }

        if(author.length() == 0 || text.length() == 0 || text.length() > 140 || hearts.contains(null))
        {
            throw new IllegalArgumentException();
        }

        List<String> h = new ArrayList<String>();

        for(String s : hearts)
        {
            if(h.contains(s)) throw new IllegalArgumentException();
            h.add(s);
        }

        this.hearts = h;
        this.id = id;
        this.author = author;
        this.text = text;
        this.timestamp = (Timestamp) timestamp.clone();
    }
    /*
       @REQUIRES : id != null && author != null && text != null && timestamp != null && hearts != null
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : id, author, text, hearts
       @EFFECTS :  Inizializza id, author, text, hearts con i rispettivi parametri
     */

    // Restituisce l' identificatore univoco del post
    public Integer getId(){
        return id;
    }
    // @EFFECTS : Restituisce l' id contenuto in <id, author, text, timestamp, hearts>

    // Restituisce il nome utente dell' autore del post
    public String getAuthor(){
        return author;
    }
    // @EFFECTS : Restituisce author contenuto in <id, author, text, timestamp, hearts>

    // Restituisce il corpo del messaggio contenuto nel post
    public String getText(){
        return text;
    }
    // @EFFECTS : Restituisce text contenuto in <id, author, text, timestamp, hearts>

    // Restituisce data e ora di invio del post
    public Timestamp getTimestamp(){
        return timestamp;
    }
    // @EFFECTS : Restituisce timestamp contenuto in <id, author, text, timestamp, hearts>

    // Restituisce l' insieme degli utenti a cui piace il post
    public List<String> getHearts(){
        return new ArrayList<String>(hearts);
    }
    // @EFFECTS : Restituisce hearts contenuto in <id, author, text, timestamp, hearts>
}
