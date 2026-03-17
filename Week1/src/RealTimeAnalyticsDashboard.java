import java.util.*;

class Event {
    String url;
    String userId;
    String source;

    Event(String url, String userId, String source) {
        this.url = url;
        this.userId = userId;
        this.source = source;
    }
}

public class RealTimeAnalyticsDashboard {

    static HashMap<String, Integer> pageViews = new HashMap<>();
    static HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    static HashMap<String, Integer> trafficSources = new HashMap<>();

    public static void processEvent(Event e) {

        pageViews.put(e.url, pageViews.getOrDefault(e.url, 0) + 1);

        uniqueVisitors.putIfAbsent(e.url, new HashSet<>());
        uniqueVisitors.get(e.url).add(e.userId);

        trafficSources.put(e.source,
                trafficSources.getOrDefault(e.source, 0) + 1);
    }

    public static void getDashboard() {

        System.out.println("\nDASHBOARD\n");

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(pageViews.entrySet());

        System.out.println("Top Pages:");

        int rank = 1;

        while (!pq.isEmpty() && rank <= 10) {

            Map.Entry<String, Integer> entry = pq.poll();
            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(rank + ". " + url + " - " +
                    views + " views (" + unique + " unique)");

            rank++;
        }

        System.out.println("\nTraffic Sources:");

        int total = 0;
        for (int v : trafficSources.values()) total += v;

        for (Map.Entry<String, Integer> entry : trafficSources.entrySet()) {

            double percent = (entry.getValue() * 100.0) / total;

            System.out.println(entry.getKey() + ": " +
                    String.format("%.1f", percent) + "%");
        }
    }

    public static void main(String[] args) throws Exception {

        processEvent(new Event("/article/breaking-news", "user_123", "Google"));
        processEvent(new Event("/article/breaking-news", "user_456", "Facebook"));
        processEvent(new Event("/sports/championship", "user_789", "Direct"));
        processEvent(new Event("/sports/championship", "user_222", "Google"));
        processEvent(new Event("/article/breaking-news", "user_123", "Direct"));
        processEvent(new Event("/sports/championship", "user_333", "Google"));
        processEvent(new Event("/tech/ai-update", "user_999", "Facebook"));

        Thread.sleep(5000);

        getDashboard();
    }
}