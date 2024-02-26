    package peaksoft.entities;

    import jakarta.persistence.*;
    import lombok.*;

    @Entity
    @Table(name = "images")
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    @SequenceGenerator(
            name = "base_gen",
            sequenceName = "image_seq",
            allocationSize = 1
    )
    public class Image extends BaseEntity{
        @Column(length = 1000)
        private String imageURL;
        @OneToOne
        private Post post;
    }
