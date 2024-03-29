package peaksoft.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entities.Post;
import peaksoft.entities.User;
import peaksoft.repository.PostRepository;
import peaksoft.service.PostService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> getAllPostsByUserId(Long userId) {
        try {
            List<Post> posts = postRepository.getAllPostsByUserId(userId);
            Collections.reverse(posts);
            return posts;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Post> getAllPostsOfSubsAndOwnByUserId(Long userId) {
        try {
            List<User> subscribedUsers = postRepository.getAllSubsByUserId(userId);
            List<Post> ownPosts = getAllPostsByUserId(userId);

            List<Post> postsOfSubscribedUsers = new ArrayList<>(subscribedUsers.stream()
                    .flatMap(user -> getAllPostsByUserId(user.getId()).stream())
                    .toList());
            Collections.reverse(postsOfSubscribedUsers);
            ownPosts.addAll(postsOfSubscribedUsers);
            return ownPosts;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Post getPostById(Long postId) {
        try {
            return postRepository.findById(postId).orElseThrow(Exception::new);
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void update(Post post) {
        try {
            postRepository.update(post);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Post post) {
        try {
            postRepository.delete(post);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
