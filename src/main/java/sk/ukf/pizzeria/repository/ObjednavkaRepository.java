package sk.ukf.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ukf.pizzeria.entity.Objednavka;
import sk.ukf.pizzeria.model.StavObjednavky;

import java.util.List;

@Repository
public interface ObjednavkaRepository extends JpaRepository<Objednavka, Long> {

    List<Objednavka> findByStav(StavObjednavky stav);

    List<Objednavka> findByPouzivatelIdOrderByCreatedAtDesc(Long userId);
}