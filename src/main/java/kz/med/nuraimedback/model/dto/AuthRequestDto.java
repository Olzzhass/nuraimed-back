package kz.med.nuraimedback.model.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String username;
    private String password;
}
