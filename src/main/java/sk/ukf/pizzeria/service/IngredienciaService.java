package sk.ukf.pizzeria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ukf.pizzeria.entity.Ingrediencia;
import sk.ukf.pizzeria.exception.ObjectNotFoundException;
import sk.ukf.pizzeria.repository.IngredienciaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class IngredienciaService {

    @Autowired
    private IngredienciaRepository repository;

    public List<Ingrediencia> getAll() {
        return repository.findAll();
    }

    public Optional<Ingrediencia> getByName(String nazov) {
        return repository.findByNazov(nazov);
    }

    public void save(Ingrediencia ing) {
        if (repository.existsByNazov(ing.getNazov())) {
            throw new IllegalArgumentException("Ingrediencia '" + ing.getNazov() + "' už v zozname existuje");
        }
        repository.save(ing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Ingrediencia> findAllById(List<Long> ids) {
        return repository.findAllById(ids);
    }
}
