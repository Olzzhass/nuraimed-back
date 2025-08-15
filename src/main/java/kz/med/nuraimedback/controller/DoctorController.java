package kz.med.nuraimedback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.med.nuraimedback.model.dto.DoctorRequestDto;
import kz.med.nuraimedback.model.dto.DoctorResponseDto;
import kz.med.nuraimedback.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctors", description = "API для управления врачами")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    @Operation(summary = "Получить список всех врачей",
               description = "Возвращает постраничный список всех врачей")
    public ResponseEntity<Page<DoctorResponseDto>> getAllDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(doctorService.getAllDoctors(page, size));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать нового врача",
               description = "Создает нового врача. Требует роль ADMIN.",
               security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<DoctorResponseDto> createDoctor(@ModelAttribute DoctorRequestDto dto) throws IOException {
        return ResponseEntity.ok(doctorService.createDoctor(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить врача по ID",
               description = "Возвращает подробную информацию о враче")
    public ResponseEntity<DoctorResponseDto> getDoctor(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctor(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить информацию о враче",
               description = "Обновляет информацию о существующем враче. Требует роль ADMIN.",
               security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<DoctorResponseDto> updateDoctor(
            @PathVariable Long id,
            @ModelAttribute DoctorRequestDto dto
    ) throws IOException {
        return ResponseEntity.ok(doctorService.updateDoctor(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить врача",
               description = "Удаляет врача по ID. Требует роль ADMIN.",
               security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
