package proxy;

public class Application implements Server {
    @Override
    public String handleRequest(String url) {
        return url + " - Success";
    }
}
