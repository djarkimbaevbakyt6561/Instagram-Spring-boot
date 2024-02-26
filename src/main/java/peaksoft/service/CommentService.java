package peaksoft.service;

import peaksoft.entities.Comment;

import java.util.List;

public interface CommentService {
    void save(Comment comment);
    List<Comment> findAllByPostId(Long postId);
    void deleteById(Long commentId);
}
