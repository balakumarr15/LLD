package prototype;

import java.util.HashMap;
import java.util.Map;

public class UserPrototypeRegistryImpl implements UserPrototypeRegistry {
    private final Map<String, User> prototypes = new HashMap<>();

    @Override
    public void addPrototype(String type, User prototype) {
        prototypes.put(type, prototype);
    }

    @Override
    public User getPrototype(String type) {
        return prototypes.get(type);
    }

    @Override
    public User clone(String type) {
        User prototype = prototypes.get(type);
        if (prototype == null) {
            return null;
        }
        return prototype.clone();
    }
}
