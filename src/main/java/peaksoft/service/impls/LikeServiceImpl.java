package peaksoft.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entities.Like;
import peaksoft.repository.LikeRepository;
import peaksoft.service.LikeService;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    @Override
    public void save(Like like) {
        likeRepository.save(like);
    }

    @Override
    public Like getLikeByPostIdAndUserId(Long userId, Long postId) {
        try {
            return likeRepository.getLikeByPostIdAndUserId(userId, postId);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Like getLikeByCommentIdAndUserId(Long userId, Long commentId) {
        try {
            return likeRepository.getLikeByCommentIdAndUserId(userId, commentId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void changeIsLikeByUserIdAndCommentId(Long userId, Long commentId) {
        try {
            Like like = getLikeByCommentIdAndUserId(userId, commentId);
            likeRepository.changeIsLikeById(like.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void changeIsLikeByUserIdAndPostId(Long userId, Long postId) {
        try {
            Like like = getLikeByPostIdAndUserId(userId, postId);
            likeRepository.changeIsLikeById(like.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
