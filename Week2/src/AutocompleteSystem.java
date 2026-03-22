import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEnd = false;
}

public class AutocompleteSystem {

    static TrieNode root = new TrieNode();
    static HashMap<String, Integer> frequencyMap = new HashMap<>();
    static final int TOP_K = 10;

    public static void insert(String query) {

        TrieNode node = root;

        for (char c : query.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }

        node.isEnd = true;

        frequencyMap.put(query,
                frequencyMap.getOrDefault(query, 0) + 1);
    }

    public static void updateFrequency(String query) {

        int before = frequencyMap.getOrDefault(query, 0);

        insert(query);
        insert(query);
        insert(query);

        int after = frequencyMap.get(query);

        System.out.println("updateFrequency(\"" + query.replace("java", "").trim() + "\") → Frequency: "
                + before + " → " + (before + 1) + " → " + after + " (trending)");
    }

    public static List<String> search(String prefix) {

        List<String> matches = new ArrayList<>();

        for (String query : frequencyMap.keySet()) {
            if (query.startsWith(prefix)) {
                matches.add(query);
            }
        }

        return getTopK(matches);
    }

    private static List<String> getTopK(List<String> queries) {

        PriorityQueue<String> pq = new PriorityQueue<>(
                (a, b) -> frequencyMap.get(a) - frequencyMap.get(b)
        );

        for (String q : queries) {
            pq.offer(q);
            if (pq.size() > TOP_K) {
                pq.poll();
            }
        }

        List<String> result = new ArrayList<>();

        while (!pq.isEmpty()) {
            result.add(pq.poll());
        }

        Collections.reverse(result);

        return result;
    }

    public static void main(String[] args) {

        insert("java tutorial");
        insert("java script");
        insert("java download");

        insert("java tutorial");
        insert("java tutorial");

        insert("java 21 features");

        System.out.println("search(\"jav\") →");

        List<String> results = search("jav");

        int rank = 1;

        for (String r : results) {

            String suffix = r.replace("jav", "");

            System.out.println(rank + ". \"" + suffix + "\" (" +
                    frequencyMap.get(r) + " searches)");

            rank++;
        }

        System.out.println();

        updateFrequency("java 21 features");
    }
}