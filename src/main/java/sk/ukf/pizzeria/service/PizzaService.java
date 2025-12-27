package sk.ukf.pizzeria.service;

import sk.ukf.pizzeria.entity.Pizza;
import sk.ukf.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    // Získanie všetkých aktívnych pízz pre menu
    public List<Pizza> getAllActivePizzas() {
        return pizzaRepository.findByAktivnaTrue();
    }

    // Vyhľadávanie pízz (Searchbar)
    public List<Pizza> searchPizzas(String query) {
        return pizzaRepository.findByNazovContainingIgnoreCaseOrPopisContainingIgnoreCase(query, query);
    }
}
