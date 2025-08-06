package kz.med.nuraimedback.service.impl;

import kz.med.nuraimedback.mapper.ReviewMapper;
import kz.med.nuraimedback.model.Review;
import kz.med.nuraimedback.model.dto.ReviewRequestDto;
import kz.med.nuraimedback.model.dto.ReviewResponseDto;
import kz.med.nuraimedback.repository.ReviewRepository;
import kz.med.nuraimedback.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponseDto createReview(ReviewRequestDto dto) {
        Review review = reviewMapper.toEntity(dto);
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public Page<ReviewResponseDto> getAllReviews(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Review> reviewPage = reviewRepository.findAll(pageable);
        return reviewPage.map(reviewMapper::toDto);
    }


    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
