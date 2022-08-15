import java.io.File;

public class Lambda {
    public static void main(String[] args) {
        File file = new File("C:\\User\\Koyaanisqatsi\\IdeaProjects\\p1\\src");
        String list[] = file.list();
        for (String s : list) {
            System.out.println(s);
        }
    }
}
