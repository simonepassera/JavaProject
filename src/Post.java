import java.sql.Timestamp;

public interface Post {
    /*
        Overview : Tipo non modificabile di un generico messaggio contenente al massimo 140 caratteri,
                   utilizzato all' interno della rete sociale MicroBlog.
                   Un post è definito da un insieme di informazioni :
                   id -> identificatore univoco del post (numero naturale)
                   author -> utente univoco della rete sociale che ha scritto il post
                             (il nome utente è compreso tra 5 e 15 caratteri, può contenere caratteri
                              maiuscoli, minuscoli, numeri e sottolineato)
                   text -> testo (massimo 140 caratteri) del post
                   timestamp -> data e ora di invio del post

        Typical element : <ID, AUTHOR, TEXT, TIMESTAMP>
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
}
