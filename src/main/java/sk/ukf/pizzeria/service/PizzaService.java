package sk.ukf.pizzeria.service;

import org.springframework.transaction.annotation.Transactional;
import sk.ukf.pizzeria.entity.Pizza;
import sk.ukf.pizzeria.entity.PizzaVelkost;
import sk.ukf.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ukf.pizzeria.repository.PizzaVelkostRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private PizzaVelkostRepository pizzaVelkostRepository;

    // Získanie všetkých aktívnych pízz pre menu
    public List<Pizza> getAllActivePizzas() {
        return pizzaRepository.findByAktivnaTrue();
    }

    // Vyhľadávanie pízz (Searchbar)
    public List<Pizza> searchPizzas(String query) {
        return pizzaRepository.findByNazovContainingIgnoreCaseOrPopisContainingIgnoreCase(query, query);
    }

    @Transactional
    public void savePizzaWithSizes(Pizza pizza, BigDecimal cenaMala, BigDecimal cenaStredna, BigDecimal cenaVelka) {
        Pizza savedPizza = pizzaRepository.save(pizza);
        pizzaVelkostRepository.deleteAllByPizza(savedPizza);
        createSize(savedPizza, "33cm", cenaMala);
        createSize(savedPizza, "40cm", cenaStredna);
        createSize(savedPizza, "50cm", cenaVelka);
    }

    private void createSize(Pizza pizza, String name, BigDecimal price) {
        PizzaVelkost pv = new PizzaVelkost();
        pv.setPizza(pizza);
        pv.setNazovVelkosti(name);
        pv.setCena(price);
        pizzaVelkostRepository.save(pv);
    }

    public Pizza getPizzaById(Long id) {
        return pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza s ID " + id + " nebola nájdená"));
    }

    @Transactional
    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }
}
