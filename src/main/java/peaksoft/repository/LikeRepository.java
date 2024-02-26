package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.entities.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE l.post.id = (:postId) AND l.user.id = (:userId)")
    Like getLikeByPostIdAndUserId(Long userId, Long postId);
    @Query("SELECT l FROM Like l WHERE l.comment.id = (:commentId) AND l.user.id = (:userId)")
    Like getLikeByCommentIdAndUserId(Long userId, Long commentId);
    @Transactional
    @Modifying
    @Query("UPDATE Like l SET l.isLike = CASE WHEN l.isLike = true THEN false ELSE true END WHERE l.id = (:likeId)")
    void changeIsLikeById(Long likeId);
}
