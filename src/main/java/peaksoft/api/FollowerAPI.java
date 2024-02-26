package peaksoft.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import peaksoft.entities.User;
import peaksoft.service.FollowerService;
import peaksoft.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class FollowerAPI {
    private final FollowerService followerService;
    private final UserService userService;
    @PostMapping("/subscribe")
    public String subscribe(HttpSession session, @RequestParam("userId") Long userId) {
        User subscribeUser = (User) session.getAttribute("user");
        followerService.subscribe(subscribeUser, userService.findUserById(userId));
        return "redirect:/profile?userId=" + userId;
    }
    @GetMapping("/follows")
    public String follows(@RequestParam("userId") Long userId, @RequestParam("isFollow") boolean isFollow, Model model) {
        List<User> followers = null;
        List<User> subscriptions = null;
        if (isFollow) {
            followers = followerService.getAllSubscribersByUserId(userId);
        } else {
            subscriptions = followerService.getAllSubscriptionsByUserId(userId);
        }
        model.addAttribute("userId", userId);
        model.addAttribute("isFollow", isFollow);
        model.addAttribute("subscribers", followers);
        model.addAttribute("subscriptions", subscriptions);
        return "follow/follows";
    }
}
