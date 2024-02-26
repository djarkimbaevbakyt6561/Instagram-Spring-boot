package peaksoft.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entities.Image;
import peaksoft.entities.Post;
import peaksoft.entities.User;
import peaksoft.service.PostService;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class PostAPI {
    private final PostService postService;

    @GetMapping("/addPost")
    public String addPostPage() {
        return "post/add-post";
    }


    @PostMapping("/addPost")
    public String addPost(@RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam("image") String imageString,
                          HttpSession session,
                          Model model) {
        User user = (User) session.getAttribute("user");
        Post post = new Post();
        post.setUser(user);
        post.setTitle(title);
        post.setDescription(description);
        Image image = new Image();
        image.setImageURL(imageString);
        image.setPost(post);
        post.setImage(image);
        if (!image.getImageURL().isEmpty() || !post.getTitle().isEmpty()) {
            postService.save(post);
            return "redirect:/profile?userId=" + user.getId();
        } else {
            model.addAttribute("error", "Photo or title is empty");
            return "post/add-post";
        }
    }

    @GetMapping("/editPost")
    public String editPostPage(@RequestParam("postId") Long postId, Model model) {
        Post postById = postService.getPostById(postId);
        model.addAttribute("postById", postById);
        return "post/edit-post";
    }

    @PostMapping("/editPost")
    public String editPost(@RequestParam("postId") Long postId,
                           @RequestParam("title") String title,
                           @RequestParam("description") String description) {
        Post postById = postService.getPostById(postId);
        postById.setDescription(description);
        postById.setTitle(title);
        postService.update(postById);
        return "redirect:/profile?userId=" + postById.getUser().getId();
    }
    @GetMapping("/deletePost/{postId}")
    public String deletePost(@PathVariable Long postId){
        Post postById = postService.getPostById(postId);
        postService.delete(postById);
        return "redirect:/profile?userId=" + postById.getUser().getId();
    }
}
