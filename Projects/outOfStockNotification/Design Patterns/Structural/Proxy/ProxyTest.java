package proxy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProxyTest {

    @Test
    void testApplicationHandleRequest() throws RateLimitException {
        Application app = new Application();
        assertEquals("http://example.com - Success", app.handleRequest("http://example.com"));
    }

    @Test
    void testProxyAllowsRequestsWithinLimit() throws RateLimitException {
        Proxy proxy = new Proxy(new Application(), 3);

        for (int i = 1; i <= 3; i++) {
            String result = proxy.handleRequest("http://example.com");
            assertEquals("http://example.com - Success", result);
        }
    }

    @Test
    void testProxyBlocksAfterRateLimit() throws RateLimitException {
        Proxy proxy = new Proxy(new Application(), 2);

        proxy.handleRequest("http://example.com");
        proxy.handleRequest("http://example.com");

        assertThrows(RateLimitException.class, () -> {
            proxy.handleRequest("http://example.com");
        });
    }

    @Test
    void testProxyRateLimitIsPerURL() throws RateLimitException {
        Proxy proxy = new Proxy(new Application(), 1);

        proxy.handleRequest("http://example.com");
        proxy.handleRequest("http://other.com");

        assertThrows(RateLimitException.class, () -> {
            proxy.handleRequest("http://example.com");
        });
    }

    @Test
    void testProxyImplementsServer() throws RateLimitException {
        Server server = new Proxy(new Application(), 5);
        String result = server.handleRequest("http://example.com");
        assertEquals("http://example.com - Success", result);
    }
}
