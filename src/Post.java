import java.sql.Timestamp;
import java.util.List;

public interface Post {
    /*
        Overview : Tipo non modificabile di un messaggio contenente al massimo 140 caratteri,
                   Un post è definito da un insieme di informazioni :
                   id ->        identificatore univoco del post
                   author ->    utente univoco della rete sociale che ha scritto il post
                   text ->      testo (massimo 140 caratteri) del post, in cui è possibile menzionare
                                altri utenti appartenenti alla rete sociale con l' uso del simbolo (@)
                                ad es @Nome1 @Nome2 ...
                   timestamp -> data e ora di invio del post
                   hearts ->    insieme di followers appartenenti alla rete sociale che hanno espresso
                                un apprezzamento positivo al post (equivalente ad un Like)
        Typical element : <id, author, text, timestamp, hearts>
     */


    // Restituisce l' identificatore univoco del post
    Integer getId();
    // @EFFECTS : Restituisce l' id contenuto in <id, author, text, timestamp, hearts>


    // Restituisce il nome utente dell' autore del post
    String getAuthor();
    // @EFFECTS : Restituisce author contenuto in <id, author, text, timestamp, hearts>


    // Restituisce il corpo del messaggio contenuto nel post
    String getText();
    // @EFFECTS : Restituisce text contenuto in <id, author, text, timestamp, hearts>


    // Restituisce data e ora di invio del post
    Timestamp getTimestamp();
    // @EFFECTS : Restituisce timestamp contenuto in <id, author, text, timestamp, hearts>


    // Restituisce l' insieme degli utenti a cui piace il post
    List<String> getHearts();
    // @EFFECTS : Restituisce hearts contenuto in <id, author, text, timestamp, hearts>
}
