import java.sql.Timestamp;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("***************");
        System.out.println("** Test Post **");
        System.out.println("***************");

        System.out.println("\n-- CONSTRUCTOR --");

        System.out.print("Post post = new Message(author:null, text:null, timestamp:null) -> ");
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

        System.out.print("SocialNetwork sn = new MicroBlog(ps:null) -> ");
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

        System.out.print("SocialNetwork sn = new MicroBlog();\n" + "sn.addUser(username:null) -> ");
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

        System.out.print("SocialNetwork sn = new MicroBlog();\n" + "sn.addFollower(username:\"Fabio\", follower:null) -> ");
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

        System.out.print("SocialNetwork sn = new MicroBlog();\n" + "sn.addPost(username:\"Fabio\", text:null) -> ");
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
    }
}
