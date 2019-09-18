package eu.apeiron.dalibor.evaulacija.repository;
import eu.apeiron.dalibor.evaulacija.domain.ZavrsniRad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ZavrsniRad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZavrsniRadRepository extends JpaRepository<ZavrsniRad, Long> {

}
