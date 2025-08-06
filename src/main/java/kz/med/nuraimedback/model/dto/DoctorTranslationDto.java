package kz.med.nuraimedback.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorTranslationDto {
    private String languageCode;
    private String description;
    private String education;
    private String experience;
    private String specialization;
}
