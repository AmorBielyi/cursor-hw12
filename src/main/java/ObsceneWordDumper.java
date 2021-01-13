import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public final class ObsceneWordDumper {
    private BufferedReader reader;
    Map<String, Integer> uniqueObsceneWordDict;
    private final String destDir;
    private final String srcName;
    private String destFilePath;
    private int swearWordCount;
    private int shortWordCount;

    public ObsceneWordDumper(String srcName, String pathToSrcFile, String destDir) throws FileNotFoundException {
        reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(pathToSrcFile + "/" + srcName))));
        this.srcName = srcName;
        this.destDir = destDir;
    }

    public void fillUniqueObsceneWordDict(String word) {
        if (uniqueObsceneWordDict.containsKey(word)) {
            uniqueObsceneWordDict.put(word, uniqueObsceneWordDict.get(word) + 1);
        } else {
            uniqueObsceneWordDict.put(word, 1);
        }
    }

    public void findObsceneWords(String word) {
        if (word.equals("fuck")) {
            swearWordCount++;
            fillUniqueObsceneWordDict(word);
        }
        if (word.equals("motherfuck")) {
            swearWordCount++;
            fillUniqueObsceneWordDict(word);
        }
        if (word.equals("asshole")) {
            swearWordCount++;
            fillUniqueObsceneWordDict(word);
        }
        if (word.equals("nigga")) {
            swearWordCount++;
            fillUniqueObsceneWordDict(word);
        }
        if (word.equals("bitch")) {
            swearWordCount++;
            fillUniqueObsceneWordDict(word);
        }
        if (word.equals("asshole")) {
            swearWordCount++;
            fillUniqueObsceneWordDict(word);
        }
        if (word.equals("pussy")) {
            swearWordCount++;
            fillUniqueObsceneWordDict(word);
        }
        if (word.equals("niggas")) {
            swearWordCount++;
            fillUniqueObsceneWordDict(word);
        }
        if (word.equals("ass")) {
            swearWordCount++;
            fillUniqueObsceneWordDict(word);
        }
        if (word.equals("dick")) {
            swearWordCount++;
            fillUniqueObsceneWordDict(word);
        }
    }

    public void process() throws IOException {
        System.out.println("The Obscene Word Dumper has been started\nAnalyzing provided data...");
        uniqueObsceneWordDict = new HashMap<>();
        int wordCount = 0;
        String data;
        while ((data = reader.readLine()) != null) {
            if (!data.isEmpty()) {
                data = data.replaceAll("[,.!;\\[]]", "").toLowerCase();
                String[] words = data.split("\\s+");
                for (String word : words) {
                    if (word.length() < 3) {
                        shortWordCount++;
                    }
                    findObsceneWords(word);
                }
                wordCount += words.length;
            }

        }
        System.out.printf("Analysis has been finished\n\n**RESULT**\ntotal number of words: %d (obscene words: %d, words: %d, short words(< 3 symbols): %d)\n",
                wordCount,
                swearWordCount,
                wordCount - swearWordCount,
                shortWordCount);
        System.out.printf("%d words were excluded\n\n", shortWordCount + shortWordCount);
        if (swearWordCount > 0) {
            printTheMostFreqObsceneWord(uniqueObsceneWordDict);
            writeObsceneWords();
            readObsceneWords();
        }
    }

    public final <K, V extends Comparable<V>> void printTheMostFreqObsceneWord(Map<K, V> map) {
        System.out.println("**TOP**\nThe most frequently used obscene words (no more than 3)");
        map.entrySet().stream()
                .sorted(Map.Entry.<K, V>comparingByValue().reversed())
                .limit(3).forEach(System.out::println);
    }

    public void writeObsceneWords() throws IOException {
        destFilePath = String.format("%s/swear_words_from_%s", destDir, srcName);
        Files.deleteIfExists(Paths.get(destFilePath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(destFilePath, true));
        for (String s : uniqueObsceneWordDict.keySet())
            writer.append(s).append("\n");
        writer.close();
        System.out.printf("\n%d unique obscene words have been extracted into: %s file\n",
                uniqueObsceneWordDict.size(),
                destFilePath);
    }

    public void readObsceneWords() throws IOException {
        String data;
        reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(destFilePath))));
        int pos = 0;
        System.out.println("Reading unique extracted obscene word(s) from dump...");
        while ((data = reader.readLine()) != null) {
            pos++;
            System.out.printf("\n%d: %s", pos, data);
        }
    }
}