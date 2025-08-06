package kz.med.nuraimedback.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequestDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private MultipartFile profileImage;
    private List<DoctorTranslationDto> translations;
}
