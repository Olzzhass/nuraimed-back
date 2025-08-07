package kz.med.nuraimedback.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectionTranslationDto {
    private String languageCode;
    private String title;
    private String description;
    private String offerDetails;
}
