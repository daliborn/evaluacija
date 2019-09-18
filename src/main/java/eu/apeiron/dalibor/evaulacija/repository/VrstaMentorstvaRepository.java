package eu.apeiron.dalibor.evaulacija.repository;
import eu.apeiron.dalibor.evaulacija.domain.VrstaMentorstva;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VrstaMentorstva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VrstaMentorstvaRepository extends JpaRepository<VrstaMentorstva, Long> {

}
