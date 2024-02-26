package peaksoft.service;

import peaksoft.entities.Post;

import java.util.List;

public interface PostService {
    void save(Post post);

    List<Post> getAllPostsByUserId(Long userId);

    List<Post> getAllPostsOfSubsAndOwnByUserId(Long userId);
    Post getPostById(Long postId);


    void update(Post post);

    void delete(Post post);
}
