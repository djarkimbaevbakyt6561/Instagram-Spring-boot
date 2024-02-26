package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@SequenceGenerator(
        name = "base_gen",
        sequenceName = "post_seq",
        allocationSize = 1
)
public class Post extends BaseEntity {
    private String title;
    private String description;
    private LocalDate createdAt;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Comment> comments;
    @OneToMany(mappedBy = "post", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Like> likes;
    @OneToOne(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Image image;
    @PrePersist
    public void preSave(){
        this.createdAt = LocalDate.now();
    }
}
