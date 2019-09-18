package eu.apeiron.dalibor.evaulacija.repository;
import eu.apeiron.dalibor.evaulacija.domain.Aktivnosti;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Aktivnosti entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AktivnostiRepository extends JpaRepository<Aktivnosti, Long> {

}
