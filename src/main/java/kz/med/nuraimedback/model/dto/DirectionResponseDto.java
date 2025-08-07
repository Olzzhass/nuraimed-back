package kz.med.nuraimedback.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectionResponseDto {
    private Long id;
    private byte[] directionImage;
    private List<DirectionTranslationDto> translations;
}
