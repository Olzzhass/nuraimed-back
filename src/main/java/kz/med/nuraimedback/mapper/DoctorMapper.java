package kz.med.nuraimedback.mapper;

import kz.med.nuraimedback.model.Doctor;
import kz.med.nuraimedback.model.DoctorTranslation;
import kz.med.nuraimedback.model.dto.DoctorRequestDto;
import kz.med.nuraimedback.model.dto.DoctorResponseDto;
import kz.med.nuraimedback.model.dto.DoctorTranslationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorResponseDto toDto(Doctor doctor);
    DoctorTranslationDto toDto(DoctorTranslation translation);

    List<DoctorTranslationDto> toTranslationDtoList(List<DoctorTranslation> translations);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileImage", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Doctor toEntity(DoctorRequestDto dto);
}
