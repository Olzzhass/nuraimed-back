package kz.med.nuraimedback.repository;

import kz.med.nuraimedback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u.* FROM users u WHERE u.is_active=true AND u.username=:username", nativeQuery = true )
    Optional<User> findByUsernameAndIsActive(@Param("username") String username);
}
