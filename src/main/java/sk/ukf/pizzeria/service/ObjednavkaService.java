package sk.ukf.pizzeria.service;

import sk.ukf.pizzeria.entity.Objednavka;
import sk.ukf.pizzeria.model.StavObjednavky;
import sk.ukf.pizzeria.repository.ObjednavkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObjednavkaService {

    @Autowired
    private ObjednavkaRepository objednavkaRepository;

    // Vytvorenie objednávky
    @Transactional
    public Objednavka createOrder(Objednavka objednavka) {
        objednavka.setStav(StavObjednavky.CAKAJUCA); // Automaticky pri vytvorení
        return objednavkaRepository.save(objednavka);
    }

    // Zmena stavu objednávky (pre kuchára a kuriéra)
    @Transactional
    public void changeStatus(Long orderId, StavObjednavky newStatus) {
        Objednavka objednavka = objednavkaRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Objednávka nenájdená"));

        objednavka.setStav(newStatus);
        objednavkaRepository.save(objednavka);
    }
}
