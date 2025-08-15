package kz.med.nuraimedback.repository;

import kz.med.nuraimedback.model.Direction;
import kz.med.nuraimedback.model.dto.DirectionNamesResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
    @Query(nativeQuery = true, value = """
                SELECT d.id, dts.title
                FROM direction_translations dts
                         JOIN direction d ON dts.direction_id = d.id
                WHERE dts.language_code = :languageCode
            """)
    List<DirectionNamesResponseDto> findDirectionsNameByLanguageCode(@Param("languageCode") String languageCode);
}
