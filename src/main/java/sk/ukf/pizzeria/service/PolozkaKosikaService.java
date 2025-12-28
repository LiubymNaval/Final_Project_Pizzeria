package sk.ukf.pizzeria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ukf.pizzeria.entity.PolozkaKosika;
import sk.ukf.pizzeria.entity.Pouzivatel;
import sk.ukf.pizzeria.repository.PolozkaKosikaRepository;

import java.util.List;

@Service
public class PolozkaKosikaService {
    @Autowired
    private PolozkaKosikaRepository polozkaKosikaRepository;

    public List<PolozkaKosika> getCartItems(Pouzivatel user) {
        return polozkaKosikaRepository.findAllByPouzivatel(user);
    }
}
