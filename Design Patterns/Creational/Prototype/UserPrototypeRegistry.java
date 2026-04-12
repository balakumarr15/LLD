package prototype;

public interface UserPrototypeRegistry {
    void addPrototype(String type, User prototype);
    User getPrototype(String type);
    User clone(String type);
}
