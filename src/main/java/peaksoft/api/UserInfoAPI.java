package peaksoft.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entities.Like;
import peaksoft.entities.Post;
import peaksoft.entities.User;
import peaksoft.entities.UserInfo;
import peaksoft.enums.Gender;
import peaksoft.service.FollowerService;
import peaksoft.service.PostService;
import peaksoft.service.UserInfoService;
import peaksoft.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserInfoAPI {
    private final UserInfoService userInfoService;
    private final UserService userService;
    private final FollowerService followerService;
    private final PostService postService;

    @GetMapping
    private String userProfile(@RequestParam("userId") Long userId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        UserInfo userInfoByUserId = userInfoService.findUserInfoByUserId(userId);
        User user = userService.findUserById(userId);
        boolean isThisMyProfile = userId.equals(loggedInUser.getId());
        List<Post> postList = postService.getAllPostsByUserId(userId);
        model.addAttribute("userInfo", userInfoByUserId);
        model.addAttribute("user", user);
        Map<Long, Long> likesCountMap = postList.stream()
                .collect(Collectors.toMap(Post::getId, post -> post.getLikes().stream()
                        .filter(Like::isLike)
                        .count()));
        model.addAttribute("postList", postList);
        model.addAttribute("likesCountMap", likesCountMap);
        model.addAttribute("isThisMyProfile", isThisMyProfile);
        model.addAttribute("isUserSubscribed", followerService.isUserSubscribed(loggedInUser, userService.findUserById(userId)));
        model.addAttribute("countOfSubscribers", followerService.getAllSubscribersByUserId(userId).size());
        model.addAttribute("countOfSubscriptions", followerService.getAllSubscriptionsByUserId(userId).size());
        return "profile/profile-page";
    }

    @GetMapping("/edit")
    public String editProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        UserInfo userInfo = userInfoService.findUserInfoByUserId(user.getId());
        model.addAttribute("userInfo", userInfo);
        return "profile/edit-profile";
    }

    @PostMapping("/edit")
    public String saveProfileChanges(HttpSession session,
                                     @RequestParam("fullName") String fullName,
                                     @RequestParam("gender") String gender,
                                     @RequestParam("biography") String biography,
                                     @RequestParam("image") String image) {
        User user = (User) session.getAttribute("user");
        UserInfo userInfo = userInfoService.findUserInfoByUserId(user.getId());
        userInfo.setFullName(fullName);
        userInfo.setBiography(biography);
        userInfo.setGender(Gender.valueOf(gender));
        userInfo.setImage(image);
        userInfoService.update(userInfo);
        return "redirect:/profile?userId=" + user.getId();

    }

    @GetMapping("/delete")
    public String deleteProfile(HttpSession session) {
        User user = (User) session.getAttribute("user");
        userService.delete(user);
        session.invalidate();
        return "redirect:/";
    }
}
