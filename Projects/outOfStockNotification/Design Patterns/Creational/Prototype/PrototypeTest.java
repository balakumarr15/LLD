package prototype;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrototypeTest {

    @Test
    void testUserClone() {
        User original = new User(1, "johndoe", "john@example.com", "John Doe", 25);
        User cloned = original.clone();

        assertEquals(original.getUserId(), cloned.getUserId());
        assertEquals(original.getUsername(), cloned.getUsername());
        assertEquals(original.getEmail(), cloned.getEmail());
        assertEquals(original.getDisplayName(), cloned.getDisplayName());
        assertEquals(original.getAge(), cloned.getAge());
        assertNotSame(original, cloned);
    }

    @Test
    void testUserCloneIsIndependent() {
        User original = new User(1, "alice", "alice@example.com", "Alice", 30);
        User cloned = original.clone();

        cloned.setUsername("bob");
        cloned.setAge(40);

        assertNotEquals(original.getUsername(), cloned.getUsername());
        assertNotEquals(original.getAge(), cloned.getAge());
    }

    @Test
    void testRegistryAddAndClone() {
        UserPrototypeRegistry registry = new UserPrototypeRegistryImpl();

        User admin = new User(100, "admin", "admin@platform.com", "Admin User", 35);
        User member = new User(200, "member", "member@platform.com", "Regular Member", 22);

        registry.addPrototype("admin", admin);
        registry.addPrototype("member", member);

        User clonedAdmin = registry.clone("admin");
        assertNotNull(clonedAdmin);
        assertNotSame(admin, clonedAdmin);
        assertEquals("admin", clonedAdmin.getUsername());
        assertEquals(35, clonedAdmin.getAge());

        User clonedMember = registry.clone("member");
        assertNotNull(clonedMember);
        assertEquals("member@platform.com", clonedMember.getEmail());
    }

    @Test
    void testRegistryGetPrototype() {
        UserPrototypeRegistry registry = new UserPrototypeRegistryImpl();
        User user = new User(1, "test", "test@example.com", "Test", 20);
        registry.addPrototype("test", user);

        assertSame(user, registry.getPrototype("test"));
    }

    @Test
    void testRegistryCloneNonExistent() {
        UserPrototypeRegistry registry = new UserPrototypeRegistryImpl();
        assertNull(registry.clone("nonexistent"));
    }

    @Test
    void testRegistryCloneIndependence() {
        UserPrototypeRegistry registry = new UserPrototypeRegistryImpl();
        registry.addPrototype("default", new User(1, "default", "default@example.com", "Default", 25));

        User clone1 = registry.clone("default");
        User clone2 = registry.clone("default");

        clone1.setUsername("modified");
        clone1.setAge(99);

        assertEquals("default", clone2.getUsername());
        assertEquals(25, clone2.getAge());

        User original = registry.getPrototype("default");
        assertEquals("default", original.getUsername());
        assertEquals(25, original.getAge());
    }
}
