package kz.med.nuraimedback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.med.nuraimedback.model.dto.DirectionRequestDto;
import kz.med.nuraimedback.model.dto.DirectionResponseDto;
import kz.med.nuraimedback.model.dto.DirectionNamesResponseDto;
import kz.med.nuraimedback.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/directions")
@RequiredArgsConstructor
@Tag(name = "Directions", description = "API для управления направлениями медицинских услуг")
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping
    @Operation(summary = "Получить список всех направлений",
            description = "Возвращает постраничный список всех направлений")
    public ResponseEntity<Page<DirectionResponseDto>> getAllDirections(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(directionService.getAllDirections(page, size));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать новое направление",
            description = "Создает новое медицинское направление. Требует роль ADMIN.",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<DirectionResponseDto> createDirection(@ModelAttribute DirectionRequestDto dto) throws IOException {
        return ResponseEntity.ok(directionService.createDirection(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить направление по ID",
            description = "Возвращает подробную информацию о направлении")
    public ResponseEntity<DirectionResponseDto> getDirection(@PathVariable Long id) {
        return ResponseEntity.ok(directionService.getDirection(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить направление",
            description = "Обновляет существующее медицинское направление. Требует роль ADMIN.",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<DirectionResponseDto> updateDirection(
            @PathVariable Long id,
            @ModelAttribute DirectionRequestDto dto
    ) throws IOException {
        return ResponseEntity.ok(directionService.updateDirection(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить направление",
            description = "Удаляет медицинское направление по ID. Требует роль ADMIN.",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> deleteDirection(@PathVariable Long id) {
        directionService.deleteDirection(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/navbar")
    @Operation(summary = "Получить названия направлений для навигации",
            description = "Возвращает упрощенный список направлений для отображения в навигационном меню")
    public ResponseEntity<List<DirectionNamesResponseDto>> getAllNamesForNavbar(
            @RequestParam(defaultValue = "ru") String languageCode
    ) {
        return ResponseEntity.ok(directionService.getAllNamesForNavbar(languageCode));
    }
}
