package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.entities.Post;
import peaksoft.entities.User;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.user.id = (:userId) ORDER BY p.createdAt DESC")
    List<Post> getAllPostsByUserId(Long userId);
    @Query("SELECT f.subscriptions FROM Follower f WHERE f.user.id = (:userId)")
    List<User> getAllSubsByUserId(Long userId);
    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.title = :#{#post.title}, p.description = :#{#post.description} WHERE p.id = :#{#post.id}")
    void update(Post post);
}
