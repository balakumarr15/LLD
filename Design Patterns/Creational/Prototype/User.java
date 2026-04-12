package prototype;

public class User implements ObjectClonable {
    private int userId;
    private String username;
    private String email;
    private String displayName;
    private int age;

    public User(int userId, String username, String email, String displayName, int age) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.displayName = displayName;
        this.age = age;
    }

    @Override
    public User clone() {
        return new User(userId, username, email, displayName, age);
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
