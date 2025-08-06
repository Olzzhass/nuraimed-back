package kz.med.nuraimedback.model.dto;

import lombok.Data;

@Data
public class ReviewRequestDto {
    private String name;
    private String phone;
    private String message;
}
