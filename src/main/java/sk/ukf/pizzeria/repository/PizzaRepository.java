package sk.ukf.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sk.ukf.pizzeria.entity.Pizza;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    List<Pizza> findByNazovContainingIgnoreCaseOrPopisContainingIgnoreCase(String nazov, String popis);

    List<Pizza> findByAktivnaTrue();

    @Query("SELECT p FROM Pizza p JOIN p.tagy t WHERE t.nazov = :tagName")
    List<Pizza> findByTagName(@Param("tagName") String tagName);
}