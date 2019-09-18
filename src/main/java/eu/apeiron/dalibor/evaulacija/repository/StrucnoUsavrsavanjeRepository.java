package eu.apeiron.dalibor.evaulacija.repository;
import eu.apeiron.dalibor.evaulacija.domain.StrucnoUsavrsavanje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StrucnoUsavrsavanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrucnoUsavrsavanjeRepository extends JpaRepository<StrucnoUsavrsavanje, Long> {

}
