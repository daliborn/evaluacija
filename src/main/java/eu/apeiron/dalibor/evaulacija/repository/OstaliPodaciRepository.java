package eu.apeiron.dalibor.evaulacija.repository;
import eu.apeiron.dalibor.evaulacija.domain.OstaliPodaci;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OstaliPodaci entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OstaliPodaciRepository extends JpaRepository<OstaliPodaci, Long> {

}
