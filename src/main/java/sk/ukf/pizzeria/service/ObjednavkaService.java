package sk.ukf.pizzeria.service;

import org.springframework.security.access.AccessDeniedException;
import sk.ukf.pizzeria.entity.Objednavka;
import sk.ukf.pizzeria.entity.PolozkaKosika;
import sk.ukf.pizzeria.entity.PolozkaObjednavky;
import sk.ukf.pizzeria.entity.Pouzivatel;
import sk.ukf.pizzeria.exception.ObjectNotFoundException;
import sk.ukf.pizzeria.model.StavObjednavky;
import sk.ukf.pizzeria.repository.ObjednavkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.ukf.pizzeria.repository.PolozkaKosikaRepository;
import sk.ukf.pizzeria.repository.PouzivatelRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ObjednavkaService {

    @Autowired
    private ObjednavkaRepository objednavkaRepository;

    @Autowired
    private PolozkaKosikaRepository polozkaKosikaRepository;

    @Transactional
    public Objednavka createOrder(Pouzivatel user, String adresa, String poznamka, List<PolozkaKosika> items) {
        if (items.isEmpty()) {
            throw new RuntimeException("Košík je prázdny!");
        }

        Objednavka objednavka = new Objednavka();
        objednavka.setPouzivatel(user);
        objednavka.setAdresa(adresa);
        objednavka.setPoznamka(poznamka);
        objednavka.setStav(StavObjednavky.CAKAJUCA);

        BigDecimal celkovaCena = BigDecimal.ZERO;
        List<PolozkaObjednavky> polozkyObjednavky = new ArrayList<>();

        for (PolozkaKosika item : items) {
            PolozkaObjednavky po = new PolozkaObjednavky();

            String menoPizze = item.getPizzaVelkost().getPizza().getNazov();
            String velkost = item.getPizzaVelkost().getNazovVelkosti();

            po.setArchivnyNazov(menoPizze + " (" + velkost + ")");

            BigDecimal cenaZaKus = item.getPizzaVelkost().getCena();
            po.setArchivnaCena(cenaZaKus);

            po.setMnozstvo(item.getMnozstvo());

            po.setObjednavka(objednavka);

            polozkyObjednavky.add(po);

            BigDecimal sumaPolozky = cenaZaKus.multiply(new BigDecimal(item.getMnozstvo()));
            celkovaCena = celkovaCena.add(sumaPolozky);
        }

        objednavka.setPolozky(polozkyObjednavky);
        objednavka.setCelkovaCena(celkovaCena);

        Objednavka savedOrder = objednavkaRepository.save(objednavka);

        polozkaKosikaRepository.deleteByPouzivatel(user);

        return savedOrder;
    }

    @Transactional
    public void cancelOrder(Long orderId, String userEmail) {
        Objednavka objednavka = objednavkaRepository.findById(orderId)
                .orElseThrow(() -> new ObjectNotFoundException("Objednávka", orderId));


        if (!objednavka.getPouzivatel().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("Nemáte oprávnenie zrušiť tento objednávku!");
        }

        if (objednavka.getStav() == StavObjednavky.CAKAJUCA) {
            objednavka.setStav(StavObjednavky.ZRUSENA);
            objednavkaRepository.save(objednavka);
        } else {
            throw new IllegalStateException("Objednávku už nie je možné zrušiť, pretože sa pripravuje.");
        }
    }

    @Transactional
    public void changeStatus(Long orderId, StavObjednavky newStatus) {
        Objednavka objednavka = objednavkaRepository.findById(orderId)
                .orElseThrow(() -> new ObjectNotFoundException("Objednávka", orderId));

        objednavka.setStav(newStatus);
        objednavkaRepository.save(objednavka);
    }

    public List<Objednavka> getAllOrders() {
        return objednavkaRepository.findAll();
    }

    public boolean checkNewOrders() {
        return objednavkaRepository.existsByStav(StavObjednavky.CAKAJUCA);
    }

    public List<Objednavka> getOrdersForKitchen() {
        return objednavkaRepository.findAllByStavIn(
                List.of(StavObjednavky.CAKAJUCA, StavObjednavky.PRIPRAVUJE_SA)
        );
    }

    public List<Objednavka> getOrdersForDelivery() {
        return objednavkaRepository.findAllByStavIn(
                List.of(StavObjednavky.PRIPRAVENA, StavObjednavky.DORUCUJE_SA)
        );
    }

    public List<Objednavka> getMyOrders(String email) {
        return objednavkaRepository.findAllByPouzivatelEmailOrderByCreatedAtDesc(email);
    }
}
