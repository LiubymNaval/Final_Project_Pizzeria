package sk.ukf.pizzeria.service;

import org.springframework.transaction.annotation.Transactional;
import sk.ukf.pizzeria.dto.ProfilDto;
import sk.ukf.pizzeria.dto.RegistraciaDto;
import sk.ukf.pizzeria.entity.Pouzivatel;
import sk.ukf.pizzeria.entity.Rola;
import sk.ukf.pizzeria.exception.ObjectNotFoundException;
import sk.ukf.pizzeria.repository.PouzivatelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sk.ukf.pizzeria.repository.RolaRepository;

import java.util.Collections;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;
import java.io.InputStream;


@Service
public class PouzivatelService {

    @Autowired
    private PouzivatelRepository pouzivatelRepository;

    @Autowired
    private RolaRepository rolaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PolozkaKosikaService polozkaKosikaService;

    // Registrácia nového používateľa
    @Transactional
    public Pouzivatel registerUser(RegistraciaDto dto) {
        if (dto.getEmail().toLowerCase().startsWith("deleted_")) {
            throw new IllegalArgumentException("Email nesmie začínať na 'deleted_'");
        }
        if (pouzivatelRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Používateľ s týmto emailom už existuje");
        }
        Pouzivatel user = new Pouzivatel();
        user.setMeno(dto.getMeno());
        user.setPriezvisko(dto.getPriezvisko());
        user.setEmail(dto.getEmail());
        user.setHeslo(passwordEncoder.encode(dto.getHeslo()));
        user.setAktivny(true);
        user.setTelefon(dto.getTelefon());

        Rola zakladnaRola = rolaRepository.findByNazov("ROLE_ZAKAZNIK")
                .orElseThrow(() -> new ObjectNotFoundException("Rola", "ROLE_ZAKAZNIK"));
        user.setRoly(Collections.singleton(zakladnaRola));

        return pouzivatelRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        Pouzivatel user = pouzivatelRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Používateľ", userId));
        user.setAktivny(false);
        String deletedPrefix = "deleted_" + System.currentTimeMillis() + "_";
        user.setEmail(deletedPrefix + user.getEmail());
        polozkaKosikaService.clearCart(user);
        pouzivatelRepository.save(user);
    }

    public Pouzivatel findByEmail(String email) {
        return pouzivatelRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("Používateľ s emailom", email));
    }

    @Transactional
    public void updateProfile(String currentEmail, ProfilDto dto) {
        Pouzivatel user = findByEmail(currentEmail);
        user.setMeno(dto.getMeno());
        user.setPriezvisko(dto.getPriezvisko());
        user.setTelefon(dto.getTelefon());
        user.setObrazokUrl(dto.getObrazokUrl());
        pouzivatelRepository.save(user);
    }

    @Transactional
    public void updatePassword(String email, String newPassword) {
        Pouzivatel user = findByEmail(email);

        if (newPassword == null || newPassword.length() < 8) {
            throw new IllegalArgumentException("Heslo musí mať aspoň 8 znakov");
        }
        user.setHeslo(passwordEncoder.encode(newPassword));
        pouzivatelRepository.save(user);
    }

    @Transactional
    public void changeUserRole(Long userId, String roleName) {
        Pouzivatel user = pouzivatelRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Používateľ", userId));

        if (user.getEmail().equals("navall@gmail.sk") && !roleName.equals("ROLE_ADMIN")) {
            throw new IllegalStateException("Nemôžete zmeniť rolu hlavnému administrátorovi");
        }

        Rola newRola = rolaRepository.findByNazov(roleName)
                .orElseThrow(() -> new ObjectNotFoundException("Rola", roleName));

        user.getRoly().clear();
        user.getRoly().add(newRola);
        pouzivatelRepository.save(user);
    }
    @Transactional
    public void toggleUserStatus(Long userId) {
        Pouzivatel user = pouzivatelRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Používateľ", userId));
        
        user.setAktivny(!user.isAktivny());
        pouzivatelRepository.save(user);
    }

    public String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String uploadDir = "src/main/resources/static/uploads/";

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return "/uploads/" + fileName;
    }

    public List<Pouzivatel> getAllForAdmin() {
        return pouzivatelRepository.findAllExceptDeleted();
    }
}
