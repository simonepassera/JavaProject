import java.sql.Timestamp;
import java.util.*;
import java.util.regex.*;

public class MicroBlogReport extends MicroBlog implements SocialNetworkReport {
    /*
        Representation Invariant : reports != null && words != null && default_words != null
                                   && words.contains("") == false
                                   && for all i != j : 0 <= i,j < words.size() ==>
                                                     (words.get(i).equals(words.get(j)) == false)
                                   && for all i : 0 <= i < reports.size() ==>
                                                ((feed.containsKey(reports.get(i).getId()) == true)
                                                && (EXISTS string in words | (reports.get(i).getText() contains string)))
        Abstraction function : AF(reports) = Insieme dei post segnalati dal social network
                               AF(words) = Lista di parole offensive
     */

    private Set<Post> reports;
    private Set<String> words;
    private String default_words = "Idrogeno,Interprete,Canada,Poligono,Fantasma,Freccette";

    // Crea un nuovo MicroBlogReport vuoto
    public MicroBlogReport()
    {
        super();

        reports = new HashSet<Post>();
        words = new HashSet<String>(Arrays.asList(default_words.split(",")));
    }
    /*
       @MODIFIES : users, followers, feed, mentioned, reports, words
       @EFFECTS : Initialize users, followers, feed, mentioned, reports, words
     */

    // Crea un nuovo MicroBlogReport inizializzato con la lista di post ps (parametro del costruttore)
    public MicroBlogReport(List<Post> ps) throws NullPointerException, IllegalArgumentException, MentionException, LikeException
    {
        super(ps);

        reports = new HashSet<Post>();
        words = new HashSet<String>(Arrays.asList(default_words.split(",")));

        reports.addAll(containing(new ArrayList<String>(words)));
    }
    /*
       @REQUIRES : ps != null && ps.isEmpty() == false && ps.contains(null) == false
                   && for all i : 0 <= i < ps.size() ==>
                                (((ps.get(i).getText().contains("@User") == true) ==> (ps.get(i).getAuthor().equals("User") == false))
                                && (ps.get(i).getTimestamp().before(current_time) == true)
                                && ((ps.get(i).getText().matches("#LIKE_[0-9]+") == true) ==>
                                             (EXISTS post in ps | post.getText().matches("#LIKE_[0-9]+") == false
                                                                  && post.getAuthor().equals(ps.get(i).getAuthor()) == false
                                                                  && post.getId().equals(Integer.parseInt("[0-9]+")) == true
                                                                  && ps.get(i).getTimestamp().after(post.getTimestamp()) == true)))
       @THROWS : NullPointerException, IllegalArgumentException, MentionException, LikeException
       @MODIFIES : users, followers, feed, mentioned, reports, words
       @EFFECTS : Initialize users, followers, feed, mentioned inferred from ps list && Initialize reports, words
     */

    // Restituisce i post segnalati dal social network, valutati con la lista di parole
    // offensive presenti in quel momento
    public List<Post> getReports() {
        return new ArrayList<Post>(this.reports);
    }
    /*
       @RETURN : reports
     */

    // Aggiunge le parole presenti nella lista words (parametro del metodo)
    // alla lista di parole offensive, presente nel social network
    public void addWords(List<String> words) throws NullPointerException, IllegalArgumentException {
        if(words == null || words.contains(null)) throw new NullPointerException();
        if(words.isEmpty()) throw new IllegalArgumentException("List is empty");
        if(words.contains("")) throw new IllegalArgumentException("List contains empty string");

        this.words.addAll(words);
        this.reports.addAll(containing(words));
    }
    /*
       @REQUIRES : words != null && words.isEmpty() == false && words.contains(null) == false
                   && words.contains("") == false
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : words, reports
       @EFFECTS : this.words.addAll(words) && this.reports.addAll(containing(words))
     */

    // Sostituisce la lista di parole offensive presenti nel social network,
    // con la lista words (parametro del metodo)
    public void changeWords(List<String> words) throws NullPointerException, IllegalArgumentException {
        if(words == null || words.contains(null)) throw new NullPointerException();
        if(words.isEmpty()) throw new IllegalArgumentException("List is empty");
        if(words.contains("")) throw new IllegalArgumentException("List contains empty string");

        Set<String> set_words = new HashSet<String>();

        for(String w : words)
        {
            if(set_words.contains(w)) throw new IllegalArgumentException("List contains duplicates");
            set_words.add(w);
        }

        this.words = set_words;
        this.reports.clear();
        this.reports.addAll(containing(words));
    }
    /*
       @REQUIRES : words != null && words.isEmpty() == false && words.contains(null) == false
                   && words.contains("") == false && for all i != j : 0 <= i,j < words.size() ==> (words.get(i).equals(words.get(j)) == false)
       @THROWS : NullPointerException, IllegalArgumentException
       @MODIFIES : words, reports
       @EFFECTS : this.words = words && this.reports = Set of posts in feed | (for all i : 0 <= i < Set.size() ==> (EXISTS string in words | (Set.get(i).getText() contains string)))
     */

    @Override
    // Aggiunge un post creato da username, e se il testo del post contiene
    // almeno una parola offensiva, il post viene segnalato dal social network
    public void addPost(String username, String text) throws NullPointerException, UsernameException, LikeException, MentionException, PostException {
        if(username == null || text == null) throw new NullPointerException();
        if(!users.containsKey(username)) throw new UsernameException("Username " + username + " not exists");
        if(text.isEmpty()) throw new PostException("The text of " + username + " is empty");
        if(text.length() > 140) throw new PostException("The text of " + username + " is too long (max 140 characters)");

        if(text.matches("#LIKE_[0-9]+"))
        {
            int id = Integer.parseInt(text.substring(6));

            if(!feed.containsKey(id)) throw new LikeException("Post " + id + " not exists");
            if(feed.get(id).getText().matches("#LIKE_[0-9]+")) throw new LikeException("Post " + id + " is a like");
            if(!users.get(username).contains(feed.get(id).getAuthor())) throw new LikeException("You can't like " + id);
        }
        else
        {
            Pattern mention = Pattern.compile("@([a-zA-Z_0-9]{5,15})");
            Matcher m = mention.matcher(text);
            String u;
            Set<String> mentioned = new HashSet<String>();

            while(m.find())
            {
                u = m.group(1);
                mentioned.add(u);

                if(!users.containsKey(u)) throw new MentionException("Username mentioned" + u + "not exists");
                if(!users.get(username).contains(u)) throw new MentionException("You can't mention" + u);
            }

            this.mentioned.addAll(mentioned);
        }

        Post post = new Message(username, text, new Timestamp(System.currentTimeMillis()));
        feed.put(post.getId(), post);

        StringBuilder regex = new StringBuilder();

        for(String w : words)
        {
            regex.append(w).append("|");
        }

        Matcher words_list = Pattern.compile(regex.deleteCharAt(regex.length()-1).toString()).matcher(text);

        if(words_list.find()) reports.add(post);
    }
    /*
       @REQUIRES : username != null && text != null && users.containsKey(username) == true
       @THROWS : NullPointerException, UsernameException, LikeException, MentionException, PostException
       @MODIFIES : feed, mentioned, reports
       @EFFECTS : feed.put(new_post.getId(), new_post) && (reports.add(post) if EXISTS string in words | (post.getText() contains string))
     */
}
