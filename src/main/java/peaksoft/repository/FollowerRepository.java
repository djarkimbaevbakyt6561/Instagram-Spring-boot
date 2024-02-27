package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entities.Follower;
import peaksoft.entities.User;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.follower WHERE u.username LIKE (:usernameOrFullName) OR u.userInfo.fullName LIKE (:usernameOrFullName)")
    List<User> search(String usernameOrFullName);
    @Query("SELECT f.subscribers FROM Follower f JOIN f.user u WHERE u.id = (:userId)")
    List<User> getAllSubscribersByUserId(Long userId);
    @Query("SELECT f.subscriptions FROM Follower f JOIN f.user u WHERE u.id = (:userId)")
    List<User> getAllSubscriptionsByUserId(Long userId);
    @Query("SELECT f FROM Follower f WHERE f.user.id = (:userId)")
    Follower getFollowerByUserId(Long userId);
    @Query("SELECT u FROM User u")
    List<User> getAllUsers();
}
