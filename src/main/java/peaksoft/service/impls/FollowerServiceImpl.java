package peaksoft.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entities.Follower;
import peaksoft.entities.User;
import peaksoft.repository.FollowerRepository;
import peaksoft.service.FollowerService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final FollowerRepository followerRepository;
    @Override
    public List<User> search(String usernameOrFullName) {
        return followerRepository.search(usernameOrFullName);
    }

    @Override
    public void subscribe(User subscribeUser, User userBeingSubscribedTo) {
        try {
            Follower userBeingSubscribedToFollower = getFollowerByUserId(userBeingSubscribedTo.getId());
            Follower subscribeUserFollower = getFollowerByUserId(subscribeUser.getId());
            boolean isSubscribed = isUserSubscribed(subscribeUser, userBeingSubscribedTo);
            if(isSubscribed) {
                userBeingSubscribedToFollower.getSubscribers().remove(subscribeUser);
                subscribeUserFollower.getSubscriptions().remove(userBeingSubscribedTo);
            } else {
                userBeingSubscribedToFollower.getSubscribers().add(subscribeUser);
                subscribeUserFollower.getSubscriptions().add(userBeingSubscribedTo);
            }
            followerRepository.update(userBeingSubscribedToFollower);
            followerRepository.update(subscribeUserFollower);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllSubscribersByUserId(Long userId) {
        try {
            return followerRepository.getAllSubscribersByUserId(userId);
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> getAllSubscriptionsByUserId(Long userId) {
        try {
            return followerRepository.getAllSubscriptionsByUserId(userId);
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Follower getFollowerByUserId(Long userId) {
        try {
            return followerRepository.getFollowerByUserId(userId);
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isUserSubscribed(User subscribeUser, User userBeingSubscribedTo) {
        Follower userBeingSubscribedToFollower = getFollowerByUserId(userBeingSubscribedTo.getId());
        return userBeingSubscribedToFollower.getSubscribers().contains(subscribeUser);
    }

}
