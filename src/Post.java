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

        Typical element : <ID, AUTHOR, TEXT, TIMESTAMP, HEARTS>

     */

    // Restituisce l' identificatore univoco del post
    Integer getId();
    // @RETURN : ID

    // Restituisce il nome utente dell' autore del post
    String getAuthor();
    // @RETURN : AUTHOR

    // Restituisce il corpo del messaggio contenuto nel post
    String getText();
    // @RETURN : TEXT

    // Restituisce data e ora di invio del post
    Timestamp getTimestamp();
    // @RETURN : TIMESTAMP

    // Restituisce l' insieme degli utenti a cui piace il post
    List<String> getHearts();
    // @RETURN : HEARTS
}
