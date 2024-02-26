package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.enums.Gender;

@Entity
@Table(name = "user_infos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@SequenceGenerator(
        name = "base_gen",
        sequenceName = "user_info_seq",
        allocationSize = 1
)
public class UserInfo extends BaseEntity{
    @Column(name = "full_name")
    private String fullName;
    private String biography;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 1000)
    private String image;
    @OneToOne
    private User user;
}
