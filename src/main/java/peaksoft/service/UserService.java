package peaksoft.service;

import peaksoft.entities.User;

public interface UserService {
    User findUserById(Long userId);
    void save (User user);

    User findByEmailOrUserName(String emailOrUsername);

    boolean isUsernameOrEmailUnique(String username, String email);
    void update(User user);
    void delete(User user);
}
