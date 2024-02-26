package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.entities.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Query("SELECT ui FROM UserInfo ui WHERE ui.user.id = (:userId)")
    UserInfo findUserInfoByUserId(Long userId);
    @Transactional
    @Modifying
    @Query("UPDATE UserInfo ui SET ui.biography = :#{#userInfo.biography}, ui.gender = :#{#userInfo.gender}, ui.fullName = :#{#userInfo.fullName}, ui.image = :#{#userInfo.image} WHERE ui.id = :#{#userInfo.id}")
    void update(UserInfo userInfo);

}
