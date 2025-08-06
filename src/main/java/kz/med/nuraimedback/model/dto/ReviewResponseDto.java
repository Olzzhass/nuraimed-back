package kz.med.nuraimedback.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDto {
    private Long id;
    private String name;
    private String phone;
    private String message;
    private LocalDateTime createdAt;
}
