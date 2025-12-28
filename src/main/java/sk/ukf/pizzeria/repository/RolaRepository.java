package sk.ukf.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ukf.pizzeria.entity.Rola;
import java.util.Optional;

@Repository
public interface RolaRepository extends JpaRepository<Rola, Long> {

    Optional<Rola> findByNazov(String nazov);
}
