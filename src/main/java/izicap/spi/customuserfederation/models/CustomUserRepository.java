package izicap.spi.customuserfederation.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserRepository {

    private final List<CustomUser> users;

    public CustomUserRepository() {
        Long created = System.currentTimeMillis();
        List<String> roles = Collections.singletonList("stoneage");
        users = Arrays.asList(
                new CustomUser("1", "Fred", "Flintstone", true, created, roles),
                new CustomUser("2", "Wilma", "Flintstone", true, created, roles),
                new CustomUser("3", "Pebbles", "Flintstone", true, created, roles),
                new CustomUser("4", "Barney", "Rubble", true, created, roles),
                new CustomUser("5", "Betty", "Rubble", true, created, Collections.emptyList()),
                new CustomUser("6", "Bam Bam", "Rubble", false, created, Collections.emptyList())
        );
    }

    public List<CustomUser> getAllUsers() {
        return users;
    }

    public int getUsersCount() {
        return users.size();
    }

    public CustomUser findUserById(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public CustomUser findUserByUsernameOrEmail(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username) || user.getEmail().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }

    public List<CustomUser> findUsers(String query) {
        return users.stream()
                .filter(user -> query.equalsIgnoreCase("*") || user.getUsername().contains(query) || user.getEmail().contains(query))
                .collect(Collectors.toList());
    }

    public boolean validateCredentials(String username, String password) {
        return findUserByUsernameOrEmail(username).getPassword().equals(password);
    }

    public boolean updateCredentials(String username, String password) {
        findUserByUsernameOrEmail(username).setPassword(password);
        return true;
    }

}
