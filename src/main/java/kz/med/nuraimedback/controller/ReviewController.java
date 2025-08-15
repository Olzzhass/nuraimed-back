package kz.med.nuraimedback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.med.nuraimedback.model.dto.ReviewRequestDto;
import kz.med.nuraimedback.model.dto.ReviewResponseDto;
import kz.med.nuraimedback.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "API для управления отзывами")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Создать новый отзыв",
               description = "Создает новый отзыв. Доступно для аутентифицированных пользователей.")
    public ResponseEntity<ReviewResponseDto> create(@RequestBody ReviewRequestDto dto) {
        return ResponseEntity.ok(reviewService.createReview(dto));
    }

    @GetMapping
    @Operation(summary = "Получить список всех отзывов",
               description = "Возвращает постраничный список всех отзывов")
    public ResponseEntity<Page<ReviewResponseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(reviewService.getAllReviews(page, size));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить отзыв",
               description = "Удаляет отзыв по ID. Требует роль ADMIN.",
               security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
