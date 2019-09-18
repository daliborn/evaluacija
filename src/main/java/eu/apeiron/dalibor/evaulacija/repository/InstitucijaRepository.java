package eu.apeiron.dalibor.evaulacija.repository;
import eu.apeiron.dalibor.evaulacija.domain.Institucija;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Institucija entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstitucijaRepository extends JpaRepository<Institucija, Long> {

}
