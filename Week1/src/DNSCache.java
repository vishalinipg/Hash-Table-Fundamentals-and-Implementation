import java.util.*;

class DNSEntry {
    String domain;
    String ipAddress;
    long timestamp;
    long expiryTime;

    DNSEntry(String domain, String ipAddress, int ttl) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.timestamp = System.currentTimeMillis();
        this.expiryTime = this.timestamp + ttl * 1000;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class DNSCache {

    private final int MAX_SIZE = 5;

    private LinkedHashMap<String, DNSEntry> cache =
            new LinkedHashMap<String, DNSEntry>(16, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                    return size() > MAX_SIZE;
                }
            };

    int hits = 0;
    int misses = 0;
    double totalLookupTime = 0;
    int lookupCount = 0;

    public synchronized String resolve(String domain) {

        long start = System.nanoTime();

        if (cache.containsKey(domain)) {

            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {

                hits++;

                long end = System.nanoTime();
                double time = (end - start) / 1_000_000.0;

                totalLookupTime += time;
                lookupCount++;

                return "Cache HIT → " + entry.ipAddress + " (retrieved in " + String.format("%.2f", time) + " ms)";
            } else {

                cache.remove(domain);
                System.out.println("Cache EXPIRED → Query upstream");
            }
        }

        misses++;

        String ip = queryUpstreamDNS(domain);

        DNSEntry entry = new DNSEntry(domain, ip, 3); // TTL = 3 seconds (for demo)

        cache.put(domain, entry);

        long end = System.nanoTime();
        double time = (end - start) / 1_000_000.0;

        totalLookupTime += time;
        lookupCount++;

        return "Cache MISS → Query upstream → " + ip + " (TTL: 3s)";
    }

    private String queryUpstreamDNS(String domain) {

        Random r = new Random();

        return "172.217.14." + (200 + r.nextInt(20));
    }

    public void getCacheStats() {

        int total = hits + misses;

        double hitRate = total == 0 ? 0 : ((double) hits / total) * 100;

        double avgLookup = lookupCount == 0 ? 0 : totalLookupTime / lookupCount;

        System.out.println("Hit Rate: " + String.format("%.2f", hitRate) +
                "%, Avg Lookup Time: " + String.format("%.2f", avgLookup) + " ms");
    }

    public static void main(String[] args) throws Exception {

        DNSCache dns = new DNSCache();

        System.out.println("resolve(\"google.com\") → " + dns.resolve("google.com"));

        System.out.println("resolve(\"google.com\") → " + dns.resolve("google.com"));

        Thread.sleep(4000); // wait for expiry

        System.out.println("resolve(\"google.com\") → " + dns.resolve("google.com"));

        dns.getCacheStats();
    }
}