package peaksoft.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import peaksoft.entities.Comment;
import peaksoft.entities.User;
import peaksoft.service.CommentService;
import peaksoft.service.PostService;

import java.util.List;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentAPI {
    private final CommentService commentService;
    private final PostService postService;

    @GetMapping
    public String commentPage(@RequestParam("postId") Long postId, Model model) {
        List<Comment> allComments = commentService.findAllByPostId(postId);
        model.addAttribute( "comments", allComments);
        model.addAttribute("postId", postId);
        return "comment/comments-page";
    }

    @PostMapping
    public String saveComment(@RequestParam("postId") Long postId, @RequestParam("comment") String commentString, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Comment comment = new Comment();
        comment.setComment(commentString);
        comment.setPost(postService.getPostById(postId));
        comment.setUser(user);
        commentService.save(comment);
        return "redirect:/comments?postId=" + postId;
    }

}
