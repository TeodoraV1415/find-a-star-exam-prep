package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Star;
import softuni.exam.models.enums.StarType;

import java.util.Optional;
import java.util.Set;

// TODO:
@Repository
public interface StarRepository extends JpaRepository<Star, Long> {

    Optional<Star> findByName(String name);
//
//    @Query("SELECT s.name, s.lightYears, s.description, s.constellation.name " +
//            "FROM Star s " +
//            "WHERE s.starType = 'Red Giant' AND s.observed = false " +
//            "ORDER BY s.lightYears ASC")
    Set<Star> findAllByStarTypeAndObserversIsNullOrderByLightYearsAsc(StarType type);


}
