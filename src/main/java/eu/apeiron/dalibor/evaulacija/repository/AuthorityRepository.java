package eu.apeiron.dalibor.evaulacija.repository;

import eu.apeiron.dalibor.evaulacija.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
