package kz.med.nuraimedback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctor_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"doctor_id", "language_code"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "language_code", nullable = false, length = 2)
    private String languageCode;

    private String description;
    private String education;
    private String experience;
    private String specialization;
}
