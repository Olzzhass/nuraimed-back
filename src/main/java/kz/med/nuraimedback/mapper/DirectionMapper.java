package kz.med.nuraimedback.mapper;

import kz.med.nuraimedback.model.Direction;
import kz.med.nuraimedback.model.DirectionTranslation;
import kz.med.nuraimedback.model.dto.DirectionRequestDto;
import kz.med.nuraimedback.model.dto.DirectionResponseDto;
import kz.med.nuraimedback.model.dto.DirectionTranslationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DirectionMapper {
    DirectionResponseDto toDto(Direction direction);
    DirectionTranslationDto toDto(DirectionTranslation translation);

    List<DirectionTranslationDto> toTranslationDtoList(List<DirectionTranslation> translations);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "directionImage", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Direction toEntity(DirectionRequestDto dto);
}
