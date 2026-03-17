import java.util.*;

public class PlagiarismDetector {

    static HashMap<String, Set<String>> ngramIndex = new HashMap<>();
    static HashMap<String, List<String>> documentNgrams = new HashMap<>();

    static final int N = 5;

    public static List<String> generateNgrams(String text) {

        String[] words = text.toLowerCase().split("\\s+");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < N; j++) {
                sb.append(words[i + j]).append(" ");
            }

            ngrams.add(sb.toString().trim());
        }

        return ngrams;
    }

    public static void addDocument(String docId, String text) {

        List<String> ngrams = generateNgrams(text);

        documentNgrams.put(docId, ngrams);

        for (String gram : ngrams) {

            ngramIndex.putIfAbsent(gram, new HashSet<>());

            ngramIndex.get(gram).add(docId);
        }
    }

    public static void analyzeDocument(String docId) {

        List<String> grams = documentNgrams.get(docId);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : grams) {

            if (ngramIndex.containsKey(gram)) {

                for (String otherDoc : ngramIndex.get(gram)) {

                    if (!otherDoc.equals(docId)) {

                        matchCount.put(otherDoc,
                                matchCount.getOrDefault(otherDoc, 0) + 1);
                    }
                }
            }
        }

        System.out.println("Extracted " + grams.size() + " n-grams");

        for (Map.Entry<String, Integer> entry : matchCount.entrySet()) {

            String otherDoc = entry.getKey();
            int matches = entry.getValue();

            double similarity = (matches * 100.0) / grams.size();

            System.out.println("Found " + matches +
                    " matching n-grams with \"" + otherDoc + "\"");

            System.out.println("Similarity: " +
                    String.format("%.1f", similarity) + "% " +
                    (similarity > 50 ? "(PLAGIARISM DETECTED)" :
                            similarity > 10 ? "(suspicious)" : ""));
        }
    }

    public static void main(String[] args) {

        String essay089 =
                "machine learning algorithms are widely used in modern artificial intelligence applications";

        String essay092 =
                "machine learning algorithms are widely used in modern artificial intelligence applications and research";

        String essay123 =
                "machine learning algorithms are widely used in modern artificial intelligence applications for solving problems";

        addDocument("essay_089.txt", essay089);
        addDocument("essay_092.txt", essay092);
        addDocument("essay_123.txt", essay123);

        System.out.println("analyzeDocument(\"essay_123.txt\")");

        analyzeDocument("essay_123.txt");
    }
}