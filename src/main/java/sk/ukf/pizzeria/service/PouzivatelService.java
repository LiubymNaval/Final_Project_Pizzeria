package sk.ukf.pizzeria.service;

import org.springframework.transaction.annotation.Transactional;
import sk.ukf.pizzeria.dto.ProfilDto;
import sk.ukf.pizzeria.dto.RegistraciaDto;
import sk.ukf.pizzeria.entity.Pouzivatel;
import sk.ukf.pizzeria.entity.Rola;
import sk.ukf.pizzeria.repository.PouzivatelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sk.ukf.pizzeria.repository.RolaRepository;

import java.util.Collections;
import java.util.List;

@Service
public class PouzivatelService {

    @Autowired
    private PouzivatelRepository pouzivatelRepository;

    @Autowired
    private RolaRepository rolaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Registrácia nového používateľa
    @Transactional
    public void registerUser(RegistraciaDto dto) {
        Pouzivatel user = new Pouzivatel();
        user.setMeno(dto.getMeno());
        user.setPriezvisko(dto.getPriezvisko());
        user.setEmail(dto.getEmail());
        user.setHeslo(passwordEncoder.encode(dto.getHeslo()));
        user.setAktivny(true);

        Rola zakladnaRola = rolaRepository.findByNazov("ROLE_ZAKAZNIK")
                .orElseThrow(() -> new RuntimeException("Chyba: Rola ROLE_ZAKAZNIK neexistuje!"));
        user.setRoly(Collections.singleton(zakladnaRola));

        pouzivatelRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        pouzivatelRepository.deleteById(userId);
    }

    public Pouzivatel findByEmail(String email) {
        return pouzivatelRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Používateľ s emailom " + email + " nebol nájdený."));
    }

    @Transactional
    public void updateProfile(String currentEmail, ProfilDto dto) {
        Pouzivatel user = findByEmail(currentEmail);
        user.setMeno(dto.getMeno());
        user.setPriezvisko(dto.getPriezvisko());
        user.setTelefon(dto.getTelefon());
        pouzivatelRepository.save(user);
    }

    @Transactional
    public void updatePassword(String email, String newPassword) {
        Pouzivatel user = findByEmail(email);
        user.setHeslo(passwordEncoder.encode(newPassword));
        pouzivatelRepository.save(user);
    }

    public List<Pouzivatel> getAllUsers() {
        return pouzivatelRepository.findAll();
    }

    @Transactional
    public void changeUserRole(Long userId, String roleName) {
        Pouzivatel user = pouzivatelRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Používateľ nebol nájdený"));
        Rola newRola = rolaRepository.findByNazov(roleName)
                .orElseThrow(() -> new RuntimeException("Rola nebola nájdená"));

        user.getRoly().clear();
        user.getRoly().add(newRola);
        pouzivatelRepository.save(user);
    }
}
