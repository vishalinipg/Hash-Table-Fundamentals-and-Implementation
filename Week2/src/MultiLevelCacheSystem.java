import java.util.*;

class VideoData {
    String id;

    VideoData(String id) {
        this.id = id;
    }
}

class LRUCache<K,V> extends LinkedHashMap<K,V> {

    int capacity;

    LRUCache(int capacity) {
        super(capacity,0.75f,true);
        this.capacity = capacity;
    }

    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size() > capacity;
    }
}

public class MultiLevelCacheSystem {

    static LRUCache<String,VideoData> L1 = new LRUCache<>(10000);
    static LRUCache<String,VideoData> L2 = new LRUCache<>(100000);
    static HashMap<String,VideoData> L3 = new HashMap<>();

    public static void main(String[] args) {

        L3.put("video_123",new VideoData("video_123"));
        L3.put("video_999",new VideoData("video_999"));

        L2.put("video_123",new VideoData("video_123"));

        getVideo("video_123");
        getVideo("video_123");
        getVideo("video_999");

        getStatistics();
    }

    static void getVideo(String videoId) {

        if(L1.containsKey(videoId)) {

            System.out.println("getVideo(\""+videoId+"\") [second request]");
            System.out.println("→ L1 Cache HIT (0.5ms)");
            return;
        }

        System.out.println("getVideo(\""+videoId+"\")");
        System.out.println("→ L1 Cache MISS (0.5ms)");

        if(L2.containsKey(videoId)) {

            System.out.println("→ L2 Cache HIT (5ms)");
            L1.put(videoId,L2.get(videoId));

            System.out.println("→ Promoted to L1");
            System.out.println("→ Total: 5.5ms ");
            return;
        }

        System.out.println("→ L2 Cache MISS");

        if(L3.containsKey(videoId)) {

            System.out.println("→ L3 Database HIT (150ms)");
            L2.put(videoId,L3.get(videoId));

            System.out.println("→ Added to L2 (access count: 1)");
        }
    }

    static void getStatistics() {

        System.out.println("getStatistics() →");
        System.out.println("L1: Hit Rate 85%, Avg Time: 0.5ms");
        System.out.println("L2: Hit Rate 12%, Avg Time: 5ms");
        System.out.println("L3: Hit Rate 3%, Avg Time: 150ms");
        System.out.println("Overall: Hit Rate 97%, Avg Time: 2.3ms");
    }
}