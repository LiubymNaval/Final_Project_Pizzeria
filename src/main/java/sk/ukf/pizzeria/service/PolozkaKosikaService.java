package sk.ukf.pizzeria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ukf.pizzeria.entity.PizzaVelkost;
import sk.ukf.pizzeria.entity.PolozkaKosika;
import sk.ukf.pizzeria.entity.Pouzivatel;
import sk.ukf.pizzeria.exception.ObjectNotFoundException;
import sk.ukf.pizzeria.repository.PizzaVelkostRepository;
import sk.ukf.pizzeria.repository.PolozkaKosikaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PolozkaKosikaService {

    @Autowired
    private PolozkaKosikaRepository polozkaKosikaRepository;

    @Autowired
    private PizzaVelkostRepository pizzaVelkostRepository;

    public List<PolozkaKosika> getCartItems(Pouzivatel user) {
        return polozkaKosikaRepository.findAllByPouzivatel(user);
    }

    public void addItemToCart(Pouzivatel user, Long pizzaId, Long velkostId, Integer mnozstvo) {

        if (mnozstvo == null || mnozstvo < 1) {
            throw new IllegalArgumentException("Množstvo musí byť aspoň 1");
        }

        if (mnozstvo > 50) {
            throw new IllegalArgumentException("Maximálne množstvo pre jeden prídavok je 50 kusov");
        }

        if (!user.isAktivny()) {
            throw new IllegalStateException("Váš účet je zablokovaný");
        }


        PizzaVelkost velkost = pizzaVelkostRepository.findById(velkostId)
                .orElseThrow(() -> new ObjectNotFoundException("PizzaVelkost", velkostId));

        if (!velkost.getPizza().isAktivna()) {
            throw new IllegalStateException("Táto pizza už nie je v ponuke");
        }


        Optional<PolozkaKosika> existingItem = polozkaKosikaRepository
                .findByPouzivatelAndPizzaVelkost_Id(user, velkostId);

        if (existingItem.isPresent()) {
            PolozkaKosika item = existingItem.get();
            int noveMnozstvo = item.getMnozstvo() + mnozstvo;
            if (noveMnozstvo > 100) {
                throw new IllegalArgumentException("V košíku nemôžete mať viac ako 100 kusov z jednej pizze");
            }
            item.setMnozstvo(item.getMnozstvo() + mnozstvo);
            polozkaKosikaRepository.save(item);
        } else {
            PolozkaKosika newItem = new PolozkaKosika();
            newItem.setPouzivatel(user);
            newItem.setMnozstvo(mnozstvo);

            newItem.setPizzaVelkost(pizzaVelkostRepository.findById(velkostId)
                    .orElseThrow(() -> new ObjectNotFoundException("PizzaVelkost", velkostId)));

            polozkaKosikaRepository.save(newItem);
        }
    }

    public void removeItem(Long id) {
        polozkaKosikaRepository.deleteById(id);
    }

    public void clearCart(Pouzivatel user) {
        List<PolozkaKosika> items = getCartItems(user);
        polozkaKosikaRepository.deleteAll(items);
    }
}
