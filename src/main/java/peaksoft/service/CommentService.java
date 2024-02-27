package peaksoft.service;

import peaksoft.entities.Comment;

import java.util.List;

public interface CommentService {
    void save(Comment comment);
    List<Comment> findAllByPostId(Long postId);
    Comment findById (Long commentId);
    void deleteById(Long commentId);
}
