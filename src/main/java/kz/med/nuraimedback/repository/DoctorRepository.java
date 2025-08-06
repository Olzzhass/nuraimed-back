package kz.med.nuraimedback.repository;

import kz.med.nuraimedback.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
