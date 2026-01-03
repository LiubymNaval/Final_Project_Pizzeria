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
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;
import java.io.InputStream;

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
        if (pizza.getId() == null && pizzaRepository.existsByNazov(pizza.getNazov())) {
            throw new IllegalArgumentException("Pizza s týmto názvom už existuje");
        }
        if (cenaMala == null || cenaStredna == null || cenaVelka == null) {
            throw new IllegalArgumentException("Všetky ceny musia byť zadané");
        }
        if (cenaVelka.compareTo(cenaMala) <= 0) {
            throw new IllegalArgumentException("Cena veľkej pizze musí byť vyššia ako malej");
        }

        if (pizza.getId() != null) {
            Pizza existingPizza = pizzaRepository.findById(pizza.getId()).orElse(null);
            if (existingPizza != null) {
                pizza.setVelkosti(existingPizza.getVelkosti());
            }
        }

        if (pizza.getVelkosti() == null) {
            pizza.setVelkosti(new ArrayList<>());
        }

        updateSizeInList(pizza, "33cm", cenaMala);
        updateSizeInList(pizza, "40cm", cenaStredna);
        updateSizeInList(pizza, "50cm", cenaVelka);

        pizzaRepository.save(pizza);
    }

    private void updateSizeInList(Pizza pizza, String name, BigDecimal price) {
        Optional<PizzaVelkost> existingSize = pizza.getVelkosti().stream()
                .filter(s -> name.equals(s.getNazovVelkosti()))
                .findFirst();

        if (existingSize.isPresent()) {
            existingSize.get().setCena(price);
        } else {
            PizzaVelkost newSize = new PizzaVelkost();
            newSize.setPizza(pizza);
            newSize.setNazovVelkosti(name);
            newSize.setCena(price);
            pizza.getVelkosti().add(newSize);
        }
    }

    public Pizza getPizzaById(Long id) {
        return pizzaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pizza", id));
    }

    @Transactional
    public void deletePizza(Long id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pizza", id));
        pizza.setAktivna(false);
        pizza.setSlug("deleted_" + System.currentTimeMillis() + "_" + pizza.getSlug());
        pizzaRepository.save(pizza);
    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public Pizza getPizzaBySlug(String slug) {
        Pizza pizza = pizzaRepository.findBySlug(slug)
                .orElseThrow(() -> new ObjectNotFoundException("Pizza so slugom", slug));

        if (!pizza.isAktivna()) {
            throw new ObjectNotFoundException("Pizza bola odstránená z ponuky", slug);
        }

        return pizza;
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
    public List<Pizza> getAllForAdmin() {
        return pizzaRepository.findAllExceptDeleted();
    }
}
