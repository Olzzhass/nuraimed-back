package kz.med.nuraimedback.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private byte[] profileImage;
    private List<DoctorTranslationDto> translations;
}
