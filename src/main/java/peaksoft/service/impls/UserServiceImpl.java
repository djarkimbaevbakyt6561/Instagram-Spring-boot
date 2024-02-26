package peaksoft.service.impls;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entities.Follower;
import peaksoft.entities.User;
import peaksoft.entities.UserInfo;
import peaksoft.repository.FollowerRepository;
import peaksoft.repository.UserInfoRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final FollowerRepository followerRepository;
    @Override
    public User findUserById(Long userId) {
        try {
            return userRepository.findById(userId).orElseThrow(Exception::new);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);
        Follower follower = new Follower();
        follower.setUser(user);
        follower.setSubscribers(new ArrayList<>());
        follower.setSubscribers(new ArrayList<>());
        followerRepository.save(follower);
    }

    @Override
    public User findByEmailOrUserName(String emailOrUsername) {
        try {
            return userRepository.findByEmailOrUserName(emailOrUsername);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isUsernameOrEmailUnique(String username, String email) {
        try {
            List<User> resultList = userRepository.listOfUsersWhereIsUsernameOrEmailUnique(username, email);
            return resultList.isEmpty();
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public void update(User user) {
        try {
            userRepository.update(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
