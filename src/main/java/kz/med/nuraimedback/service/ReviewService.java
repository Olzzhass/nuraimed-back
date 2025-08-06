package kz.med.nuraimedback.service;

import kz.med.nuraimedback.model.dto.ReviewRequestDto;
import kz.med.nuraimedback.model.dto.ReviewResponseDto;
import org.springframework.data.domain.Page;

public interface ReviewService {

    ReviewResponseDto createReview(ReviewRequestDto dto);

    Page<ReviewResponseDto> getAllReviews(int page, int size);

    void deleteReview(Long id);
}
