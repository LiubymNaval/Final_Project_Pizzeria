package sk.ukf.pizzeria.service;

import sk.ukf.pizzeria.dto.RegistraciaDto;
import sk.ukf.pizzeria.entity.Pouzivatel;
import sk.ukf.pizzeria.repository.PouzivatelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PouzivatelService {

    @Autowired
    private PouzivatelRepository pouzivatelRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Registrácia nového používateľa
    public void registerUser(RegistraciaDto dto) {
        Pouzivatel user = new Pouzivatel();
        user.setMeno(dto.getMeno());
        user.setEmail(dto.getEmail());

        // Zašifrovanie hesla (Silné heslo podľa zadania)
        user.setHeslo(passwordEncoder.encode(dto.getHeslo()));
        user.setAktivny(true);

        pouzivatelRepository.save(user);
    }

    // Zmena hesla
    public void changePassword(Long userId, String newPassword) {
        Pouzivatel user = pouzivatelRepository.findById(userId).orElseThrow();
        user.setHeslo(passwordEncoder.encode(newPassword));
        pouzivatelRepository.save(user);
    }
}
