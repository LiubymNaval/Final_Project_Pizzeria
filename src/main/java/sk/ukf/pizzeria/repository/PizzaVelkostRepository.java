package sk.ukf.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ukf.pizzeria.entity.Pizza;
import sk.ukf.pizzeria.entity.PizzaVelkost;
import java.util.List;

@Repository
public interface PizzaVelkostRepository extends JpaRepository<PizzaVelkost, Long> {
    List<PizzaVelkost> findAllByPizza(Pizza pizza);
    void deleteAllByPizza(Pizza pizza);
}