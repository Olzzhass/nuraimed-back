package kz.med.nuraimedback.repository;

import kz.med.nuraimedback.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
