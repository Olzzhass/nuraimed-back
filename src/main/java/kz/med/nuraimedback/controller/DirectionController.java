package kz.med.nuraimedback.controller;

import kz.med.nuraimedback.model.dto.DirectionRequestDto;
import kz.med.nuraimedback.model.dto.DirectionResponseDto;
import kz.med.nuraimedback.model.dto.DirectionNamesResponseDto;
import kz.med.nuraimedback.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/directions")
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping
    public ResponseEntity<Page<DirectionResponseDto>> getAllDirections(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(directionService.getAllDirections(page, size));
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DirectionResponseDto> createDirection(@ModelAttribute DirectionRequestDto dto) throws IOException {
        return ResponseEntity.ok(directionService.createDirection(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectionResponseDto> getDirection(@PathVariable Long id) {
        return ResponseEntity.ok(directionService.getDirection(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DirectionResponseDto> updateDirection(
            @PathVariable Long id,
            @ModelAttribute DirectionRequestDto dto
    ) throws IOException {
        return ResponseEntity.ok(directionService.updateDirection(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirection(@PathVariable Long id) {
        directionService.deleteDirection(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/navbar")
    public ResponseEntity<List<DirectionNamesResponseDto>> getAllNamesForNavbar(
            @RequestParam(defaultValue = "ru") String languageCode
    ) {
        return ResponseEntity.ok(directionService.getAllNamesForNavbar(languageCode));
    }
}
