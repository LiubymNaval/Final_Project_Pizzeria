package sk.ukf.pizzeria.service;

import org.springframework.transaction.annotation.Transactional;
import sk.ukf.pizzeria.entity.Pizza;
import sk.ukf.pizzeria.entity.PizzaVelkost;
import sk.ukf.pizzeria.exception.ObjectNotFoundException;
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
        if (query == null || query.trim().isEmpty()) {
            return getAllActivePizzas();
        }
        return pizzaRepository.searchByAllCriteria(query.trim());
    }

    @Transactional
    public void savePizzaWithSizes(Pizza pizza, BigDecimal cenaMala, BigDecimal cenaStredna, BigDecimal cenaVelka) {
        Pizza savedPizza = pizzaRepository.save(pizza);
        List<PizzaVelkost> existingSizes = pizzaVelkostRepository.findAllByPizza(savedPizza);

        updateOrCreateSize(savedPizza, existingSizes, "33cm", cenaMala);
        updateOrCreateSize(savedPizza, existingSizes, "40cm", cenaStredna);
        updateOrCreateSize(savedPizza, existingSizes, "50cm", cenaVelka);
    }

    private void updateOrCreateSize(Pizza pizza, List<PizzaVelkost> existingSizes, String name, BigDecimal price) {
        PizzaVelkost size = existingSizes.stream()
                .filter(s -> s.getNazovVelkosti().equals(name))
                .findFirst()
                .orElse(new PizzaVelkost());

        size.setPizza(pizza);
        size.setNazovVelkosti(name);
        size.setCena(price);
        pizzaVelkostRepository.save(size);
    }

    public Pizza getPizzaById(Long id) {
        return pizzaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pizza", id));
    }

    @Transactional
    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public Pizza getPizzaBySlug(String slug) {
        return pizzaRepository.findBySlug(slug)
                .orElseThrow(() -> new ObjectNotFoundException("Pizza so slugom", slug));
    }
}
