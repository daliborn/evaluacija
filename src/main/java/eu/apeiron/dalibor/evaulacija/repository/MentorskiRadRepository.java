package eu.apeiron.dalibor.evaulacija.repository;
import eu.apeiron.dalibor.evaulacija.domain.MentorskiRad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MentorskiRad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MentorskiRadRepository extends JpaRepository<MentorskiRad, Long> {

}
