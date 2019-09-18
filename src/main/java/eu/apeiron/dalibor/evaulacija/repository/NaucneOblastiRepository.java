package eu.apeiron.dalibor.evaulacija.repository;
import eu.apeiron.dalibor.evaulacija.domain.NaucneOblasti;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NaucneOblasti entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaucneOblastiRepository extends JpaRepository<NaucneOblasti, Long> {

}
