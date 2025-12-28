package sk.ukf.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ukf.pizzeria.entity.Objednavka;
import sk.ukf.pizzeria.model.StavObjednavky;

import java.util.List;

@Repository
public interface ObjednavkaRepository extends JpaRepository<Objednavka, Long> {

    List<Objednavka> findAllByStav(StavObjednavky stav);

    List<Objednavka> findAllByStavIn(List<StavObjednavky> stavy);

    List<Objednavka> findAllByPouzivatelEmailOrderByCreatedAtDesc(String email);

    boolean existsByStav(StavObjednavky stav);
}