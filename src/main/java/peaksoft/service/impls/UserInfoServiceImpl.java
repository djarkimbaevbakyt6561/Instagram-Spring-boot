package peaksoft.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entities.UserInfo;
import peaksoft.repository.UserInfoRepository;
import peaksoft.service.UserInfoService;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findUserInfoByUserId(Long userId) {
        try {
            return userInfoRepository.findUserInfoByUserId(userId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void update(UserInfo userInfo) {
        try {
            userInfoRepository.update(userInfo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
