package eu.apeiron.dalibor.evaulacija.repository;
import eu.apeiron.dalibor.evaulacija.domain.Nastavnik;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Nastavnik entity.
 */
@Repository
public interface NastavnikRepository extends JpaRepository<Nastavnik, Long> {

    @Query(value = "select distinct nastavnik from Nastavnik nastavnik left join fetch nastavnik.zavrsniRadovis left join fetch nastavnik.projektis left join fetch nastavnik.mentorskiRads left join fetch nastavnik.aktivnostis left join fetch nastavnik.ostaliPodacis",
        countQuery = "select count(distinct nastavnik) from Nastavnik nastavnik")
    Page<Nastavnik> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct nastavnik from Nastavnik nastavnik left join fetch nastavnik.zavrsniRadovis left join fetch nastavnik.projektis left join fetch nastavnik.mentorskiRads left join fetch nastavnik.aktivnostis left join fetch nastavnik.ostaliPodacis")
    List<Nastavnik> findAllWithEagerRelationships();

    @Query("select nastavnik from Nastavnik nastavnik left join fetch nastavnik.zavrsniRadovis left join fetch nastavnik.projektis left join fetch nastavnik.mentorskiRads left join fetch nastavnik.aktivnostis left join fetch nastavnik.ostaliPodacis where nastavnik.id =:id")
    Optional<Nastavnik> findOneWithEagerRelationships(@Param("id") Long id);

}
