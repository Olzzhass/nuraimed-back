package kz.med.nuraimedback.mapper;

import kz.med.nuraimedback.model.Review;
import kz.med.nuraimedback.model.dto.ReviewRequestDto;
import kz.med.nuraimedback.model.dto.ReviewResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toEntity(ReviewRequestDto dto);

    ReviewResponseDto toDto(Review entity);
}
