package proxy;

public interface Server {
    String handleRequest(String url) throws RateLimitException;
}
