package se.dandel.gameon.domain.model;

public class TestUserFactory {
    public static User createUser() {
        return createUser("gurra");
    }

    public static User createUser(String username) {
        User user = new User(username);
        return user;
    }
}
