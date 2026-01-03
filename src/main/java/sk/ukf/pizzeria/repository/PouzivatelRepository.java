package sk.ukf.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.ukf.pizzeria.entity.Pouzivatel;

import java.util.List;
import java.util.Optional;

@Repository
public interface PouzivatelRepository extends JpaRepository<Pouzivatel, Long> {

    Optional<Pouzivatel> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM Pouzivatel u WHERE u.email NOT LIKE 'deleted_%'")
    List<Pouzivatel> findAllExceptDeleted();
}