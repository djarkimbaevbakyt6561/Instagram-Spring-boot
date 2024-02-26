package peaksoft.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import peaksoft.entities.Like;
import peaksoft.entities.Post;
import peaksoft.entities.User;
import peaksoft.service.LikeService;
import peaksoft.service.PostService;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class LikeAPI {
    private final LikeService likeService;
    private final PostService postService;

    @GetMapping
    public String getPostPage(@RequestParam("postId") Long postId, Model model, HttpSession session) {
        Post post = postService.getPostById(postId);
        User user = (User) session.getAttribute("user");
        Like like = likeService.getLikeByPostIdAndUserId(user.getId(), postId);
        if (like != null) {
            model.addAttribute("like", like);
        } else {
            Like newLike = new Like();
            newLike.setLike(false);
            newLike.setUser(user);
            newLike.setPost(post);
            likeService.save(newLike);
            model.addAttribute("like", newLike);

        }
        model.addAttribute("post", post);
        return "post/post-by-id";
    }

    @PostMapping("/like")
    public String like(@RequestParam("postId") Long postId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        likeService.changeIsLikeByUserIdAndPostId(user.getId(), postId);
        return "redirect:/post?postId=" + postId;
    }
}
