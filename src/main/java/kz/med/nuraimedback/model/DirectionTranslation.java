package kz.med.nuraimedback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "direction_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"direction_id", "language_code"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectionTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @Column(name = "language_code", nullable = false, length = 2)
    private String languageCode; // kz/ru

    private String title; // Название направления
    private String description; // Чем занимается это направление (отделение)
    private String offerDetails; // Что мы предлагаем?
}
