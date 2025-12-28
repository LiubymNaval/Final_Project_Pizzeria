package sk.ukf.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ukf.pizzeria.entity.Ingrediencia;

import java.util.Optional;

@Repository
public interface IngredienciaRepository extends JpaRepository<Ingrediencia, Long> {
    Optional<Ingrediencia> findByNazov(String nazov);
}
