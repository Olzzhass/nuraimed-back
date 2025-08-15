package kz.med.nuraimedback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.med.nuraimedback.model.dto.AuthRequestDto;
import kz.med.nuraimedback.model.dto.AuthResponseDto;
import kz.med.nuraimedback.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API для аутентификации пользователей")
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/login")
    @Operation(summary = "Вход в систему",
            description = "Аутентификация пользователя по логину и паролю")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Обновить токен",
            description = "Обновление JWT токена",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<AuthResponseDto> refresh(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.refresh(token.substring(7)));
    }

    @PostMapping("/logout")
    @Operation(summary = "Выход из системы",
            description = "Деактивация текущего JWT токена",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token.substring(7));
        return ResponseEntity.ok().build();
    }
}
