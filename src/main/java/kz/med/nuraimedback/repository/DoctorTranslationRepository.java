package kz.med.nuraimedback.repository;

import kz.med.nuraimedback.model.DoctorTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

public interface DoctorTranslationRepository extends JpaRepository<DoctorTranslation, Long> {
    @Modifying
    void deleteByDoctorId(@Param("doctorId") Long doctorId);
}