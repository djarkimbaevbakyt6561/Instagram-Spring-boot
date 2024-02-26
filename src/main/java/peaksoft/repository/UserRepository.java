package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = (:emailOrUsername) OR u.username = (:emailOrUsername)")
    User findByEmailOrUserName(String emailOrUsername);

    @Query("SELECT u FROM User u WHERE u.username = (:username) OR u.email = (:email)")
    List<User> listOfUsersWhereIsUsernameOrEmailUnique(String username, String email);

    @Query("UPDATE User u SET u.email = :#{#user.email}, u.phoneNumber = :#{#user.phoneNumber}, u.password = :#{#user.password}, u.username = :#{#user.username} WHERE u.id = :#{#user.id}")
    @Modifying
    @Transactional
    void update(User user);
}
