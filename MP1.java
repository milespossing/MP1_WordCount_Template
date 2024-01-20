import java.io.*;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP1 {
    Random generator;
    String userName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    public MP1(String userName) {
        this.userName = userName;
    }


    public Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        long longSeed = Long.parseLong(this.userName);
        this.generator = new Random(longSeed);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public String[] process() throws Exception {
        String[] topItems = new String[20];
        Integer[] indexes = getIndexes();

        Scanner in = new Scanner(System.in);

        Map<String, Integer> counts = new HashMap<>();

        List<String> lines = new ArrayList<>();

        while (in.hasNext()) {
            lines.add(in.nextLine());
        }

        for (Integer index : indexes) {

            String line = lines.get(index);
            StringTokenizer tokenizer = new StringTokenizer(line, delimiters);
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().toLowerCase();
                if (Arrays.asList(stopWordsArray).contains(token)) {
                    continue;
                }
                if (counts.containsKey(token)) {
                    counts.put(token, counts.get(token) + 1);
                } else {
                    counts.put(token, 1);
                }
            }
        }

        Queue<Map.Entry<String, Integer>> maxQueue = new PriorityQueue<>(
                (entry1, entry2) -> {
                    int valueCompare = entry2.getValue().compareTo(entry1.getValue());
                    if (valueCompare != 0) {
                        return valueCompare;
                    } else {
                        return entry1.getKey().compareTo(entry2.getKey());
                    }
                }
        );

        maxQueue.addAll(counts.entrySet());

        for (int i = 0; i < 20; i++) {
            Map.Entry<String, Integer> entry = maxQueue.poll();
            assert entry != null;
            topItems[i] = entry.getKey();
        }

        return topItems;
    }

    public static void main(String args[]) throws Exception {
        if (args.length < 1) {
            System.out.println("missing the argument");
        } else {
            String userName = args[0];
            MP1 mp = new MP1(userName);
            String[] topItems = mp.process();

            for (String item : topItems) {
                System.out.println(item);
            }
        }
    }

}
