package peaksoft.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entities.Comment;
import peaksoft.repository.CommentRepository;
import peaksoft.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        try {
            return commentRepository.findAllByPostId(postId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Comment findById(Long commentId) {
        try {
            return commentRepository.findById(commentId).orElseThrow(Exception::new);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteById(Long commentId) {
        try {
            commentRepository.deleteById(commentId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
