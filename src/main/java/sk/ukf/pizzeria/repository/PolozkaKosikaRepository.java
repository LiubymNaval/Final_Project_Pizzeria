package sk.ukf.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sk.ukf.pizzeria.entity.PolozkaKosika;
import sk.ukf.pizzeria.entity.Pouzivatel;
import java.util.List;

@Repository
public interface PolozkaKosikaRepository extends JpaRepository<PolozkaKosika, Long> {
    @Modifying
    @Transactional
    void deleteByPouzivatel(Pouzivatel pouzivatel);

    List<PolozkaKosika> findAllByPouzivatel(Pouzivatel pouzivatel);
}
