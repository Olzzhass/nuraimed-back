package kz.med.nuraimedback.repository;

import kz.med.nuraimedback.model.DirectionTranslation;
import kz.med.nuraimedback.model.DoctorTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

public interface DirectionTranslationRepository extends JpaRepository<DirectionTranslation, Long> {
    @Modifying
    void deleteByDirectionId(@Param("doctorId") Long directionId);
}
