package kz.med.nuraimedback.controller;

import kz.med.nuraimedback.model.dto.DirectionRequestDto;
import kz.med.nuraimedback.model.dto.DirectionResponseDto;
import kz.med.nuraimedback.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/directions")
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService DirectionService;

    @GetMapping
    public ResponseEntity<Page<DirectionResponseDto>> getAllDirections(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(DirectionService.getAllDirections(page, size));
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DirectionResponseDto> createDirection(@ModelAttribute DirectionRequestDto dto) throws IOException {
        return ResponseEntity.ok(DirectionService.createDirection(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectionResponseDto> getDirection(@PathVariable Long id) {
        return ResponseEntity.ok(DirectionService.getDirection(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DirectionResponseDto> updateDirection(
            @PathVariable Long id,
            @ModelAttribute DirectionRequestDto dto
    ) throws IOException {
        return ResponseEntity.ok(DirectionService.updateDirection(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirection(@PathVariable Long id) {
        DirectionService.deleteDirection(id);
        return ResponseEntity.noContent().build();
    }
}
