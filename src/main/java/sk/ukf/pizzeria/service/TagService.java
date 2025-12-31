package sk.ukf.pizzeria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.ukf.pizzeria.entity.Tag;
import sk.ukf.pizzeria.exception.ObjectNotFoundException;
import sk.ukf.pizzeria.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Tag", id));
    }

    public Optional<Tag> getTagByName(String nazov) {
        return tagRepository.findByNazov(nazov);
    }

    @Transactional
    public void saveTag(Tag tag) {
        if (tagRepository.existsByNazov(tag.getNazov())) {
            throw new IllegalArgumentException("Tag s názvom '" + tag.getNazov() + "' už existuje");
        }
        tagRepository.save(tag);
    }

    @Transactional
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
