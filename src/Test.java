import java.sql.Timestamp;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("***************");
        System.out.println("** Test Post **");
        System.out.println("***************");

        System.out.println("\n-- CONSTRUCTOR --");

        System.out.print("\nPost post = new Message(author:null, text:null, timestamp:null) -> ");
        try {
            Post post = new Message(null, null, null);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nPost post = new Message(author:\"S!mone\", text:\"Testo del post\", new Timestamp(System.currentTimeMillis())) -> ");
        try {
            Post post = new Message("S!mone", "Testo del post", new Timestamp(System.currentTimeMillis()));
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nPost post = new Message(author:\"Simone\", text:\"\", new Timestamp(System.currentTimeMillis())) -> ");
        try {
            Post post = new Message("Simone", "", new Timestamp(System.currentTimeMillis()));
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nPost post = new Message(author:\"Simone\", text:\"Lorem ipsum dolor sit amet\nconsectetur adipiscing elit. Donec at nisl est. Suspendisse nunc eros,\negestas non odio in, elementum molestie est.\", new Timestamp(System.currentTimeMillis())) -> ");
        try {
            Post post = new Message("Simone", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.Donec at nisl est. Suspendisse nunc eros, egestas non odio in, elementum molestie est.", new Timestamp(System.currentTimeMillis()));
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n************************");
        System.out.println("** Test SocialNetwork **");
        System.out.println("************************");

        System.out.println("\n-- CONSTRUCTOR --");

        System.out.print("\nSocialNetwork sn = new MicroBlog(ps:null) -> ");
        try {
            SocialNetwork sn = new MicroBlog(null);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "ps.add(new Message(\"Simone\", \"Testo del post\", new Timestamp(System.currentTimeMillis())));\n" + "ps.add(null);\n" + "SocialNetwork sn = new MicroBlog(ps) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            ps.add(new Message("Simone", "Testo del post", new Timestamp(System.currentTimeMillis())));
            ps.add(null);
            SocialNetwork sn = new MicroBlog(ps);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog(new ArrayList<Post>()) -> ");
        try {
            SocialNetwork sn = new MicroBlog(new ArrayList<Post>());
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "Calendar c = new GregorianCalendar(2022, 10, 4);\n" + "ps.add(new Message(\"Simone\", \"Testo del post\", new Timestamp(c.getTimeInMillis())));\n" + "SocialNetwork sn = new MicroBlog(ps) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            Calendar c = new GregorianCalendar(2022, 10, 4);
            ps.add(new Message("Simone", "Testo del post", new Timestamp(c.getTimeInMillis())));
            SocialNetwork sn = new MicroBlog(ps);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "ps.add(new Message(\"Simone\", \"Testo del post in cui menziono me stesso @Simone\", new Timestamp(System.currentTimeMillis())));\n" + "SocialNetwork sn = new MicroBlog(ps) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            Calendar c = new GregorianCalendar(2019, 10, 4);
            ps.add(new Message("Simone", "Testo del post in cui menziono me stesso @Simone", new Timestamp(c.getTimeInMillis())));
            SocialNetwork sn = new MicroBlog(ps);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "Calendar c = new GregorianCalendar(2019, 10, 4);\n" + "ps.add(new Message(\"Simone\", \"#LIKE_33\", new Timestamp(c.getTimeInMillis())));\n" + "SocialNetwork sn = new MicroBlog(ps) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            Calendar c = new GregorianCalendar(2019, 10, 4);
            ps.add(new Message("Simone", "#LIKE_33", new Timestamp(c.getTimeInMillis())));
            SocialNetwork sn = new MicroBlog(ps);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "Calendar c1 = new GregorianCalendar(2018, 10, 4);\n" + "Post p1 = new Message(\"Simone\", \"Testo del post che riceverà un mi piace da me stesso\", new Timestamp(c1.getTimeInMillis()));\n" + "Calendar c2 = new GregorianCalendar(2019, 07, 23);\n" + "Post p2 =  new Message(\"Simone\", \"#LIKE_\" + p1.getId(), new Timestamp(c2.getTimeInMillis()));\n" + "ps.add(p1); ps.add(p2);\n" + "SocialNetwork sn = new MicroBlog(ps) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            Calendar c1 = new GregorianCalendar(2018, 10, 4);
            Post p1 = new Message("Simone", "Testo del post che riceverà un mi piace da me stesso", new Timestamp(c1.getTimeInMillis()));
            Calendar c2 = new GregorianCalendar(2019, 07, 23);
            Post p2 =  new Message("Simone", "#LIKE_" + p1.getId(), new Timestamp(c2.getTimeInMillis()));
            ps.add(p1); ps.add(p2);
            SocialNetwork sn = new MicroBlog(ps);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "Calendar c1 = new GregorianCalendar(2016, 10, 4);\n" + "Post p1 = new Message(\"Francesco\", \"Testo del post che verrà pubblicato dopo aver ricevuto un mi piace\", new Timestamp(c1.getTimeInMillis()));\n" + "Calendar c2 = new GregorianCalendar(2015, 07, 23);\n" + "Post p2 =  new Message(\"Simone\", \"#LIKE_\" + p1.getId(), new Timestamp(c2.getTimeInMillis()));\n" + "ps.add(p1); ps.add(p2);\n" + "SocialNetwork sn = new MicroBlog(ps); -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            Calendar c1 = new GregorianCalendar(2016, 10, 4);
            Post p1 = new Message("Francesco", "Testo del post che verrà pubblicato dopo aver ricevuto un mi piace", new Timestamp(c1.getTimeInMillis()));
            Calendar c2 = new GregorianCalendar(2015, 07, 23);
            Post p2 =  new Message("Simone", "#LIKE_" + p1.getId(), new Timestamp(c2.getTimeInMillis()));
            ps.add(p1); ps.add(p2);
            SocialNetwork sn = new MicroBlog(ps);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n-- addUser --");

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(username:null) -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser(null);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Simone\");\n" + "sn.addUser(\"username:Simone\") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Simone");
            sn.addUser("Simone");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"username:Fa;bio\") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Fa;bio");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n-- addFollower --");

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addFollower(username:\"Fabio\", follower:null) -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addFollower("Fabio", null);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addFollower(username:\"Fabio\", follower\"Simone\") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addFollower("Fabio", "Simone");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Fabio\");\n" + "sn.addFollower(username:\"Fabio\", follower:\"Simone\") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Fabio");
            sn.addFollower("Fabio", "Simone");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Fabio\");\n" + "sn.addFollower(username:\"Fabio\", follower:\"Fabio\") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Fabio");
            sn.addFollower("Fabio", "Fabio");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n-- addPost --");

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addPost(username:\"Fabio\", text:null) -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addPost("Fabio", null);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addPost(username:\"Simone\", text:\"Testo del post\") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addPost("Simone", "Testo del post");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Simone\");\n" + "sn.addPost(username:\"Simone\", text:\"\") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Simone");
            sn.addPost("Simone", "");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Simone\");\n" + "sn.addPost(username:\"Simone\", text:\"Lorem ipsum dolor sit amet\nconsectetur adipiscing elit. Donec at nisl est. Suspendisse nunc eros,\negestas non odio in, elementum molestie est.\") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Simone");
            sn.addPost("Simone", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.Donec at nisl est. Suspendisse nunc eros, egestas non odio in, elementum molestie est.");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Simone\");\n" + "sn.addPost(username:\"Simone\", text:\"#LIKE_32\") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Simone");
            sn.addPost("Simone", "#LIKE_32");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Simone\");\n" + "sn.addUser(\"Paolo\");\n" + "Integer id_s = sn.addPost(\"Simone\", \"Testo del post di simone (id_s = 8)\");\n" + "sn.addPost(username:\"Paolo\", text:\"#LIKE_\" + id_s) -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Simone");
            sn.addUser("Paolo");
            Integer id_s = sn.addPost("Simone", "Testo del post di simone (id_s = 8)");
            sn.addPost("Paolo", "#LIKE_" + id_s);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Simone\");\n" + "sn.addUser(\"Paolo\");\n" + "sn.addFollower(\"Simone\", \"Paolo\");\n" + "Integer id_s = sn.addPost(\"Simone\", \"Testo del post di simone\");\n" + "Integer id_like = sn.addPost(\"Paolo\", \"#LIKE_\" + id_s);\n" + "sn.addPost(username:\"Simone\", text:\"#LIKE_\" + id_like) -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Simone");
            sn.addUser("Paolo");
            sn.addFollower("Simone", "Paolo");
            Integer id_s = sn.addPost("Simone", "Testo del post di simone");
            Integer id_like = sn.addPost("Paolo", "#LIKE_" + id_s);
            sn.addPost("Simone", "#LIKE_" + id_like);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Simone\");\n" + "sn.addPost(username:\"Simone\", text:\"Testo del post di simone dove menziono @Paolo\") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Simone");
            sn.addPost("Simone", "Testo del post di simone dove menziono @Paolo");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Simone\");\n" + "sn.addUser(\"Paolo\");\n" + "sn.addPost(username:\"Simone\", text:\"Testo del post di simone dove menziono @Paolo\") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Simone");
            sn.addUser("Paolo");
            sn.addPost("Simone", "Testo del post di simone dove menziono @Paolo");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n-- influencers --");

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Aldo58\");\n" + "sn.addUser(\"Giovanni3\");\n" + "sn.addUser(\"Giacomo\");\n" + "sn.addFollower(\"Aldo58\", \"Giovanni3\");\n" + "sn.addFollower(\"Aldo58\", \"Giacomo\");\n" + "sn.addFollower(\"Giacomo\", \"Aldo58\");\n" + "for(String name : sn.influencers()) System.out.print(name + \" \") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Aldo58");
            sn.addUser("Giovanni3");
            sn.addUser("Giacomo");
            sn.addFollower("Aldo58", "Giovanni3");
            sn.addFollower("Aldo58", "Giacomo");
            sn.addFollower("Giacomo", "Aldo58");
            for(String name : sn.influencers()) System.out.print(name + " ");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n\n-- getMentionedUsers --");

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Simone\");\n" + "sn.addUser(\"Francesco\");\n" + "sn.addUser(\"Giacomo\");\n" + "sn.addFollower(\"Francesco\", \"Simone\");\n" + "sn.addFollower(\"Giacomo\", \"Simone\");\n" + "sn.addPost(\"Simone\", \"Testo del post di simone dove menziono @Giacomo e @Francesco\");\n" + "for(String name : sn.getMentionedUsers()) System.out.print(name + \" \") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Simone");
            sn.addUser("Francesco");
            sn.addUser("Giacomo");
            sn.addFollower("Francesco", "Simone");
            sn.addFollower("Giacomo", "Simone");
            sn.addPost("Simone", "Testo del post di simone dove menziono @Giacomo e @Francesco");
            for(String name : sn.getMentionedUsers()) System.out.print(name + " ");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n\n-- writtenBy --");

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.writtenBy(username:null) -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.writtenBy(null);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.writtenBy(username:\"Simone\") -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.writtenBy("Simone");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Simone\");\n" + "sn.addPost(\"Simone\", \"Testo1 del post di simone\");\n" + "sn.addPost(\"Simone\", \"Testo2 del post di simone\");\n" + "Message.printPost(sn.writtenBy(username:\"Simone\")) -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Simone");
            sn.addPost("Simone", "Testo1 del post di simone");
            sn.addPost("Simone", "Testo2 del post di simone");
            Message.printPost(sn.writtenBy("Simone"));
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n\n-- containing --");

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.containing(words:null) -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.containing(null);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "List<String> w = new ArrayList<String>();\n" + "sn.containing(w) -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            List<String> w = new ArrayList<String>();
            sn.containing(w);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "List<String> w = new ArrayList<String>();\n" + "w.add(null);\n" + "sn.containing(w) -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            List<String> w = new ArrayList<String>();
            w.add(null);
            sn.containing(w);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "List<String> w = new ArrayList<String>();\n" + "w.add(\"\");\n" + "sn.containing(w) -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            List<String> w = new ArrayList<String>();
            w.add("");
            sn.containing(w);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetwork sn = new MicroBlog();\n" + "sn.addUser(\"Simone\");\n" + "sn.addUser(\"Francesco\");\n" + "sn.addPost(\"Simone\", \"Testo del post di simone, gelato\");\n" + "sn.addPost(\"Francesco\", \"Testo del post di francesco\");\n" + "List<String> w = new ArrayList<String>(); w.add(\"gelato\"); w.add(\"francesco\");\n" + "Message.printPost(sn.containing(w)) -> ");
        try {
            SocialNetwork sn = new MicroBlog();
            sn.addUser("Simone");
            sn.addUser("Francesco");
            sn.addPost("Simone", "Testo del post di simone, gelato");
            sn.addPost("Francesco", "Testo del post di francesco");
            List<String> w = new ArrayList<String>(); w.add("gelato"); w.add("francesco");
            Message.printPost(sn.containing(w));
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n\n-- STATIC guessFollowers --");

        System.out.print("\nMicroBlog.guessFollowers(ps:null) -> ");
        try {
            MicroBlog.guessFollowers(null);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "MicroBlog.guessFollowers(ps) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            MicroBlog.guessFollowers(ps);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "ps.add(null);\n" + "MicroBlog.guessFollowers(ps) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            ps.add(null);
            MicroBlog.guessFollowers(ps);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "Post p1 = new Message(\"Francesco\", \"Testo del post di francesco, menziono @Paolo\", new Timestamp(new GregorianCalendar(2016, 10, 4).getTimeInMillis()));\n" + "Post p2 =  new Message(\"Simone\", \"#LIKE_\" + p1.getId(), new Timestamp(new GregorianCalendar(2018, 07, 23).getTimeInMillis()));\n" + "ps.add(p1); ps.add(p2);\n" + "MicroBlog.printNetwork(MicroBlog.guessFollowers(ps)) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            Post p1 = new Message("Francesco", "Testo del post di francesco, menziono @Paolo", new Timestamp(new GregorianCalendar(2016, 10, 4).getTimeInMillis()));
            Post p2 =  new Message("Simone", "#LIKE_" + p1.getId(), new Timestamp(new GregorianCalendar(2018, 07, 23).getTimeInMillis()));
            ps.add(p1); ps.add(p2);
            MicroBlog.printNetwork(MicroBlog.guessFollowers(ps));
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n\n-- STATIC getMentionedUsers --");

        System.out.print("\nMicroBlog.getMentionedUsers(ps:null) -> ");
        try {
            MicroBlog.getMentionedUsers(null);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "MicroBlog.getMentionedUsers(ps) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            MicroBlog.getMentionedUsers(ps);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "ps.add(null);\n" + "MicroBlog.getMentionedUsers(ps) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            ps.add(null);
            MicroBlog.getMentionedUsers(ps);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "Post p1 = new Message(\"Francesco\", \"Menziono @Francesco, ciao @Paolo e nome corto @Luca\", new Timestamp(new GregorianCalendar(2016, 10, 4).getTimeInMillis()));\n" + "Post p2 =  new Message(\"Simone\", \"#LIKE_\" + p1.getId(), new Timestamp(new GregorianCalendar(2018, 07, 23).getTimeInMillis()));\n" + "ps.add(p1); ps.add(p2);\n" + "for(String name : MicroBlog.getMentionedUsers(ps)) System.out.print(name + \" \") -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            Post p1 = new Message("Francesco", "Menziono @Francesco, ciao @Paolo e nome corto @Luca", new Timestamp(new GregorianCalendar(2016, 10, 4).getTimeInMillis()));
            Post p2 =  new Message("Simone", "#LIKE_" + p1.getId(), new Timestamp(new GregorianCalendar(2018, 07, 23).getTimeInMillis()));
            ps.add(p1); ps.add(p2);
            for(String name : MicroBlog.getMentionedUsers(ps)) System.out.print(name + " ");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n\n-- STATIC writtenBy --");

        System.out.print("\nMicroBlog.writtenBy(ps:null, username:\"Simone\") -> ");
        try {
            MicroBlog.writtenBy(null, "Simone");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "MicroBlog.writtenBy(ps, username:null) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            MicroBlog.writtenBy(ps, null);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "ps.add(null);\n" + "MicroBlog.writtenBy(ps, username:\"Simone\") -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            ps.add(null);
            MicroBlog.writtenBy(ps, "Simone");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "MicroBlog.writtenBy(ps, username:\"Simone\") -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            MicroBlog.writtenBy(ps, "Simone");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "Post p1 = new Message(\"Simone\", \"Testo del post di Simone\", new Timestamp(new GregorianCalendar(2012, 05, 23).getTimeInMillis()));\n" + "ps.add(p1);\n" + "MicroBlog.writtenBy(ps, username:\"S!mone?\") -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            Post p1 = new Message("Simone", "Testo del post di Simone", new Timestamp(new GregorianCalendar(2012, 05, 23).getTimeInMillis()));
            ps.add(p1);
            MicroBlog.writtenBy(ps, "S!mone?");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "Post p1 = new Message(\"Francesco\", \"Testo del post di francesco\", new Timestamp(new GregorianCalendar(2016, 10, 4).getTimeInMillis()));\n" + "Post p2 = new Message(\"Simone\", \"Testo del post di Simone\", new Timestamp(new GregorianCalendar(2015, 05, 4).getTimeInMillis()));\n" + "Post p3 = new Message(\"Simone\", \"Like_44\", new Timestamp(new GregorianCalendar(2013, 06, 14).getTimeInMillis()));\n" + "ps.add(p1); ps.add(p2); ps.add(p3);\n" + "Message.printPost(MicroBlog.writtenBy(ps, username:\"Simone\")) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            Post p1 = new Message("Francesco", "Testo del post di francesco", new Timestamp(new GregorianCalendar(2016, 10, 4).getTimeInMillis()));
            Post p2 = new Message("Simone", "Testo del post di Simone", new Timestamp(new GregorianCalendar(2015, 05, 4).getTimeInMillis()));
            Post p3 = new Message("Simone", "#LIKE_44", new Timestamp(new GregorianCalendar(2013, 06, 14).getTimeInMillis()));
            ps.add(p1); ps.add(p2); ps.add(p3);
            Message.printPost(MicroBlog.writtenBy(ps, "Simone"));
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n\n******************************");
        System.out.println("** Test SocialNetworkReport **");
        System.out.println("******************************");

        System.out.println("\n-- CONSTRUCTOR | getReports | getWords --");

        System.out.print("\nSocialNetworkReport snr = new MicroBlogReport();\n" + "Message.printPost(snr.getReports()) -> \n" + "for(String name : snr.getWords()) System.out.print(name + \" \") -> ");
        try {
            SocialNetworkReport snr = new MicroBlogReport();
            Message.printPost(snr.getReports());
            for(String name : snr.getWords()) System.out.print(name + " ");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\n\nList<Post> ps = new ArrayList<Post>();\n" + "Post p1 = new Message(\"Francesco\", \"Post di Francesco, @Paolo tira le freccette\", new Timestamp(new GregorianCalendar(2016, 10, 4).getTimeInMillis()));\n" + "Post p2 =  new Message(\"Simone\", \"#LIKE_\" + p1.getId(), new Timestamp(new GregorianCalendar(2018, 07, 23).getTimeInMillis()));\n" + "ps.add(p1); ps.add(p2);\n" + "SocialNetworkReport snr = new MicroBlogReport(ps);\n" + "Message.printPost(snr.getReports()) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            Post p1 = new Message("Francesco", "Post di Francesco, @Paolo tira le freccette", new Timestamp(new GregorianCalendar(2016, 10, 4).getTimeInMillis()));
            Post p2 =  new Message("Simone", "#LIKE_" + p1.getId(), new Timestamp(new GregorianCalendar(2018, 07, 23).getTimeInMillis()));
            ps.add(p1); ps.add(p2);
            SocialNetworkReport snr = new MicroBlogReport(ps);
            Message.printPost(snr.getReports());
            System.out.print("\nfor(String name : snr.getWords()) System.out.print(name + \" \") -> ");
            for(String name : snr.getWords()) System.out.print(name + " ");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n\n-- addWords --");

        System.out.print("\nSocialNetworkReport snr = new MicroBlogReport();\n" + "snr.addWords(words:null) -> ");
        try {
            SocialNetworkReport snr = new MicroBlogReport();
            snr.addWords(null);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetworkReport snr = new MicroBlogReport();\n" + "List<String> w = new ArrayList<String>();\n" + "snr.addWords(w) -> ");
        try {
            SocialNetworkReport snr = new MicroBlogReport();
            List<String> w = new ArrayList<String>();
            snr.addWords(w);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetworkReport snr = new MicroBlogReport();\n" + "List<String> w = new ArrayList<String>();\n" + "w.add(null);\n" + "snr.addWords(w) -> ");
        try {
            SocialNetworkReport snr = new MicroBlogReport();
            List<String> w = new ArrayList<String>();
            w.add(null);
            snr.addWords(w);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetworkReport snr = new MicroBlogReport();\n" + "List<String> w = new ArrayList<String>();\n" + "w.add(\"\");\n" + "snr.addWords(w) -> ");
        try {
            SocialNetworkReport snr = new MicroBlogReport();
            List<String> w = new ArrayList<String>();
            w.add("");
            snr.addWords(w);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "Post p1 = new Message(\"Francesco\", \"Post di Francesco, @Paolo tira le freccette\", new Timestamp(new GregorianCalendar(2016, 10, 4).getTimeInMillis()));\n" + "Post p2 = new Message(\"Simone\", \"#LIKE_\" + p1.getId(), new Timestamp(new GregorianCalendar(2018, 07, 23).getTimeInMillis()));\n" + "Post p3 = new Message(\"Marco\", \"Il linguaggio ocaml\", new Timestamp(new GregorianCalendar(2017, 07, 24).getTimeInMillis()));\n" + "ps.add(p1); ps.add(p2); ps.add(p3);\n" + "SocialNetworkReport snr = new MicroBlogReport(ps);\n" + "Message.printPost(snr.getReports()) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            Post p1 = new Message("Francesco", "Post di Francesco, @Paolo tira le freccette", new Timestamp(new GregorianCalendar(2016, 10, 4).getTimeInMillis()));
            Post p2 =  new Message("Simone", "#LIKE_" + p1.getId(), new Timestamp(new GregorianCalendar(2018, 07, 23).getTimeInMillis()));
            Post p3 =  new Message("Marco", "Il linguaggio ocaml", new Timestamp(new GregorianCalendar(2017, 07, 24).getTimeInMillis()));
            ps.add(p1); ps.add(p2); ps.add(p3);
            SocialNetworkReport snr = new MicroBlogReport(ps);
            Message.printPost(snr.getReports());
            System.out.print("\nfor(String name : snr.getWords()) System.out.print(name + \" \") -> ");
            for(String name : snr.getWords()) System.out.print(name + " ");
            List<String> w = new ArrayList<String>(); w.add("ocaml");
            snr.addWords(w);
            System.out.print("\nList<String> w = new ArrayList<String>(); w.add(\"ocaml\");\n" + "snr.addWords(w);" + "\nMessage.printPost(snr.getReports()) -> ");
            Message.printPost(snr.getReports());
            System.out.print("\nfor(String name : snr.getWords()) System.out.print(name + \" \") -> ");
            for(String name : snr.getWords()) System.out.print(name + " ");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n\n-- changeWords --");

        System.out.print("\nSocialNetworkReport snr = new MicroBlogReport();\n" + "snr.changeWords(words:null) -> ");
        try {
            SocialNetworkReport snr = new MicroBlogReport();
            snr.changeWords(null);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetworkReport snr = new MicroBlogReport();\n" + "List<String> w = new ArrayList<String>();\n" + "snr.changeWords(w) -> ");
        try {
            SocialNetworkReport snr = new MicroBlogReport();
            List<String> w = new ArrayList<String>();
            snr.changeWords(w);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetworkReport snr = new MicroBlogReport();\n" + "List<String> w = new ArrayList<String>();\n" + "w.add(null);\n" + "snr.changeWords(w) -> ");
        try {
            SocialNetworkReport snr = new MicroBlogReport();
            List<String> w = new ArrayList<String>();
            w.add(null);
            snr.changeWords(w);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nSocialNetworkReport snr = new MicroBlogReport();\n" + "List<String> w = new ArrayList<String>();\n" + "w.add(\"\");\n" + "snr.changeWords(w) -> ");
        try {
            SocialNetworkReport snr = new MicroBlogReport();
            List<String> w = new ArrayList<String>();
            w.add("");
            snr.changeWords(w);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.print("\nList<Post> ps = new ArrayList<Post>();\n" + "Post p1 = new Message(\"Francesco\", \"Post di Francesco, @Paolo tira le freccette\", new Timestamp(new GregorianCalendar(2016, 10, 4).getTimeInMillis()));\n" + "Post p2 = new Message(\"Simone\", \"Buongiorno a tutti\", new Timestamp(new GregorianCalendar(2018, 07, 23).getTimeInMillis()));\n" + "Post p3 = new Message(\"Marco\", \"Il linguaggio ocaml\", new Timestamp(new GregorianCalendar(2017, 07, 24).getTimeInMillis()));\n" + "ps.add(p1); ps.add(p2); ps.add(p3);\n" + "SocialNetworkReport snr = new MicroBlogReport(ps);\n" + "Message.printPost(snr.getReports()) -> ");
        try {
            List<Post> ps = new ArrayList<Post>();
            Post p1 = new Message("Francesco", "Post di Francesco, @Paolo tira le freccette", new Timestamp(new GregorianCalendar(2016, 10, 4).getTimeInMillis()));
            Post p2 =  new Message("Simone", "Buongiorno a tutti", new Timestamp(new GregorianCalendar(2018, 07, 23).getTimeInMillis()));
            Post p3 =  new Message("Marco", "Il linguaggio ocaml", new Timestamp(new GregorianCalendar(2017, 07, 24).getTimeInMillis()));
            ps.add(p1); ps.add(p2); ps.add(p3);
            SocialNetworkReport snr = new MicroBlogReport(ps);
            Message.printPost(snr.getReports());
            System.out.print("\nfor(String name : snr.getWords()) System.out.print(name + \" \") -> ");
            for(String name : snr.getWords()) System.out.print(name + " ");
            List<String> w = new ArrayList<String>(); w.add("ocaml"); w.add("tutti");
            snr.changeWords(w);
            System.out.print("\nList<String> w = new ArrayList<String>(); w.add(\"ocaml\"); w.add(\"tutti\");\n" + "snr.changeWords(w);" + "\nMessage.printPost(snr.getReports()) -> ");
            Message.printPost(snr.getReports());
            System.out.print("\nfor(String name : snr.getWords()) System.out.print(name + \" \") -> ");
            for(String name : snr.getWords()) System.out.print(name + " ");
        } catch(Exception e) { System.out.println(e.getMessage()); }

        System.out.println("\n\n-- addPost --");

        System.out.print("\nSocialNetworkReport snr = new MicroBlogReport();\n" + "List<String> w = new ArrayList<String>(); w.add(\"ocaml\");\n" + "snr.addWords(w);\n" + "snr.addUser(\"Simone\");\n" + "Message.printPost(snr.getReports()) -> ");
        try {
            SocialNetworkReport snr = new MicroBlogReport();
            List<String> w = new ArrayList<String>(); w.add("ocaml");
            snr.addWords(w);
            snr.addUser("Simone");
            Message.printPost(snr.getReports());
            System.out.print("\nfor(String name : snr.getWords()) System.out.print(name + \" \") -> ");
            for(String name : snr.getWords()) System.out.print(name + " ");
            snr.addPost("Simone", "Linguaggio di programmazione ocaml");
            System.out.print("\nsnr.addPost(\"Simone\", \"Linguaggio di programmazione ocaml\");" + "\nMessage.printPost(snr.getReports()) -> ");
            Message.printPost(snr.getReports());
            System.out.print("\nfor(String name : snr.getWords()) System.out.print(name + \" \") -> ");
            for(String name : snr.getWords()) System.out.print(name + " ");
        } catch(Exception e) { System.out.println(e.getMessage()); }
    }
}
