package eu.apeiron.dalibor.evaulacija.repository;
import eu.apeiron.dalibor.evaulacija.domain.Kandidat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Kandidat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KandidatRepository extends JpaRepository<Kandidat, Long> {

}
