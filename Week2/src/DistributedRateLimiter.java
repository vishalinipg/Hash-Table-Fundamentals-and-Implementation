import java.util.*;

class TokenBucket {

    int tokens;
    int maxTokens;
    double refillRate;
    long lastRefillTime;

    TokenBucket(int maxTokens, double refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }

    synchronized boolean allowRequest() {

        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }

        return false;
    }

    private void refill() {

        long now = System.currentTimeMillis();

        double seconds = (now - lastRefillTime) / 1000.0;

        int newTokens = (int) (seconds * refillRate);

        if (newTokens > 0) {
            tokens = Math.min(maxTokens, tokens + newTokens);
            lastRefillTime = now;
        }
    }
}

public class DistributedRateLimiter {

    static HashMap<String, TokenBucket> clients = new HashMap<>();

    static final int MAX_REQUESTS = 1000;
    static final double REFILL_RATE = 1000.0 / 3600.0;

    public static synchronized String checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId,
                new TokenBucket(MAX_REQUESTS, REFILL_RATE));

        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {

            return "Allowed (" + bucket.tokens + " requests remaining)";
        }

        return "Denied (0 requests remaining, retry later)";
    }

    public static void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clients.get(clientId);

        if (bucket == null) {
            System.out.println("Client not found");
            return;
        }

        int used = bucket.maxTokens - bucket.tokens;

        long resetTime = bucket.lastRefillTime + 3600 * 1000;

        System.out.println("{used: " + used +
                ", limit: " + bucket.maxTokens +
                ", reset: " + resetTime + "}");
    }

    public static void main(String[] args) {

        String client = "abc123";

        System.out.println("checkRateLimit(clientId=\"abc123\") → "
                + checkRateLimit(client));

        System.out.println("checkRateLimit(clientId=\"abc123\") → "
                + checkRateLimit(client));

        System.out.println("checkRateLimit(clientId=\"abc123\") → "
                + checkRateLimit(client));

        System.out.print("getRateLimitStatus(\"abc123\") → ");
        getRateLimitStatus(client);
    }
}