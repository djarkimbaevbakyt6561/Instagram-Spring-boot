package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "likes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@SequenceGenerator(
        name = "base_gen",
        sequenceName = "like_seq",
        allocationSize = 1
)
public class Like extends BaseEntity {
    private boolean isLike;
    @ManyToOne
    @JoinTable(
            name = "post_like",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "like_id")
    )
    private Post post;
    @OneToOne
    private User user;
    @ManyToOne
    @JoinTable(
            name = "comment_like",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "like_id")
    )
    private Comment comment;
}
