import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String root = "src/main/java";
        ObsceneWordDumper swearWordDumper = new ObsceneWordDumper(
                "totallyCurseSongLyrics.txt",
                root,
                root);
        swearWordDumper.process();
    }
}
