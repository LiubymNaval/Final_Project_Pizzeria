package sk.ukf.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sk.ukf.pizzeria.entity.Pizza;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    boolean existsByNazov(String nazov);

    @Query("SELECT DISTINCT p FROM Pizza p " +
            "LEFT JOIN p.tagy t " +
            "LEFT JOIN p.ingrediencie i " +
            "WHERE p.aktivna = true AND (" +
            "LOWER(p.nazov) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.popis) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(t.nazov) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(i.nazov) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Pizza> searchByAllCriteria(@Param("query") String query);

    List<Pizza> findByAktivnaTrue();

    @Query("SELECT p FROM Pizza p JOIN p.tagy t WHERE t.nazov = :tagName")
    List<Pizza> findByTagName(@Param("tagName") String tagName);

    Optional<Pizza> findBySlug(String slug);
}