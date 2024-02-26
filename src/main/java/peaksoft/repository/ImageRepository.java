package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.entities.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
