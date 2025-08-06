package kz.med.nuraimedback.controller;

import kz.med.nuraimedback.model.dto.DoctorRequestDto;
import kz.med.nuraimedback.model.dto.DoctorResponseDto;
import kz.med.nuraimedback.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<Page<DoctorResponseDto>> getAllDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(doctorService.getAllDoctors(page, size));
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DoctorResponseDto> createDoctor(@ModelAttribute DoctorRequestDto dto) throws IOException {
        return ResponseEntity.ok(doctorService.createDoctor(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctor(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctor(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DoctorResponseDto> updateDoctor(
            @PathVariable Long id,
            @ModelAttribute DoctorRequestDto dto
    ) throws IOException {
        return ResponseEntity.ok(doctorService.updateDoctor(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
