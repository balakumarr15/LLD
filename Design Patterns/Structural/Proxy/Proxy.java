package proxy;

import java.util.HashMap;
import java.util.Map;

public class Proxy implements Server {
    private final Server app;
    private final int maxRequests;
    private final Map<String, Integer> rateLimitMap = new HashMap<>();

    public Proxy(Server app, int maxRequests) {
        this.app = app;
        this.maxRequests = maxRequests;
    }

    @Override
    public String handleRequest(String url) throws RateLimitException {
        int count = rateLimitMap.getOrDefault(url, 0) + 1;
        rateLimitMap.put(url, count);

        if (count > maxRequests) {
            throw new RateLimitException("Rate limit exceeded for " + url);
        }
        return app.handleRequest(url);
    }
}
