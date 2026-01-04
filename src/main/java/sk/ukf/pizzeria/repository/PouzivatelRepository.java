package sk.ukf.pizzeria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.ukf.pizzeria.entity.Pouzivatel;

import java.util.Optional;

@Repository
public interface PouzivatelRepository extends JpaRepository<Pouzivatel, Long> {

    Optional<Pouzivatel> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM Pouzivatel u WHERE u.email NOT LIKE 'deleted_%'")
    Page<Pouzivatel> findAllExceptDeleted(Pageable pageable);
}