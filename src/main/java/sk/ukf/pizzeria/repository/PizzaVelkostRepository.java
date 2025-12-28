package sk.ukf.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ukf.pizzeria.entity.Pizza;
import sk.ukf.pizzeria.entity.PizzaVelkost;

@Repository
public interface PizzaVelkostRepository extends JpaRepository<PizzaVelkost, Long> {
    void deleteAllByPizza(Pizza pizza);
}