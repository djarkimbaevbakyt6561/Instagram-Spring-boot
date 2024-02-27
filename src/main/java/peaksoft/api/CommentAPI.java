package peaksoft.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entities.Comment;
import peaksoft.entities.Like;
import peaksoft.entities.User;
import peaksoft.service.CommentService;
import peaksoft.service.LikeService;
import peaksoft.service.PostService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentAPI {
    private final CommentService commentService;
    private final PostService postService;
    private final LikeService likeService;

    @GetMapping
    public String commentPage(@RequestParam("postId") Long postId, Model model, HttpSession session) {
        List<Comment> allComments = commentService.findAllByPostId(postId);
        User user = (User) session.getAttribute("user");
        Map<Long, Long> likesCountMap = allComments.stream()
                .collect(Collectors.toMap(Comment::getId, comment -> comment.getLikes().stream()
                        .filter(Like::isLike)
                        .count()));
        model.addAttribute("comments", allComments);
        model.addAttribute("user", user);
        model.addAttribute("likesCountMap", likesCountMap);
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

    @GetMapping("/comment-by-id")
    public String commentByIdPage(@RequestParam("commentId") Long commentId, Model model, HttpSession session) {
        Comment commentById = commentService.findById(commentId);
        User user = (User) session.getAttribute("user");
        Like like = likeService.getLikeByCommentIdAndUserId(user.getId(), commentId);
        if (like != null) {
            model.addAttribute("like", like);
        } else {
            Like newLike = new Like();
            newLike.setLike(false);
            newLike.setUser(user);
            newLike.setComment(commentById);
            likeService.save(newLike);
            model.addAttribute("like", newLike);
        }

        model.addAttribute("comment", commentById);
        return "comment/comment-by-id";
    }

    @PostMapping("/like/{commentId}")
    public String like(@PathVariable("commentId") Long commentId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        likeService.changeIsLikeByUserIdAndCommentId(user.getId(), commentId);
        return "redirect:/comments/comment-by-id?commentId=" + commentId;
    }
    @GetMapping("/deleteComment/{commentId}")
    public String deletePost(@PathVariable Long commentId){
        Comment commentById = commentService.findById(commentId);
        commentService.deleteById(commentId);
        return "redirect:/comments?postId=" + commentById.getPost().getId();
    }
}
