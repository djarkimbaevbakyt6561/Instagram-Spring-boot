package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "followers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@SequenceGenerator(
        name = "base_gen",
        sequenceName = "follower_seq",
        allocationSize = 1
)
public class Follower extends BaseEntity {
    @OneToMany
    @JoinTable(
            name = "follower_subscribers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id")
    )
    private List<User> subscribers = new ArrayList<>();
    @OneToMany
    @JoinTable(
            name = "follower_subscriptions",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id")
    )
    private List<User> subscriptions = new ArrayList<>();

    @OneToOne
    private User user;
}
