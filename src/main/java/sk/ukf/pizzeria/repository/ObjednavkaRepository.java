package sk.ukf.pizzeria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ukf.pizzeria.entity.Objednavka;
import sk.ukf.pizzeria.model.StavObjednavky;

import java.util.List;

@Repository
public interface ObjednavkaRepository extends JpaRepository<Objednavka, Long> {

    List<Objednavka> findAllByStavIn(List<StavObjednavky> stavy);

    Page<Objednavka> findAllByPouzivatelEmailOrderByCreatedAtDesc(String email, Pageable pageable);

    boolean existsByStav(StavObjednavky stav);

    Page<Objednavka> findAllByOrderByCreatedAtDesc(Pageable pageable);

}