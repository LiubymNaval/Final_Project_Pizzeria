package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Pizza")
public class Pizza extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL)
    private List<PizzaVelkost> velkosti;

    private String nazov;

    @Column(columnDefinition = "TEXT")
    private String popis;

    private String slug;

    @Column(name = "obrazok_url")
    private String obrazokUrl;

    private boolean aktivna;

    @ManyToMany
    @JoinTable(
            name = "Pizza_Ingrediencie",
            joinColumns = @JoinColumn(name = "Pizza_ID"),
            inverseJoinColumns = @JoinColumn(name = "Ingrediencia_ID")
    )
    private Set<Ingrediencia> ingrediencie;

    @ManyToMany
    @JoinTable(
            name = "Pizza_Tagy",
            joinColumns = @JoinColumn(name = "Pizza_ID"),
            inverseJoinColumns = @JoinColumn(name = "Tag_ID")
    )
    private Set<Tag> tagy;

    public Pizza() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNazov() { return nazov; }
    public void setNazov(String nazov) { this.nazov = nazov; }

    public String getPopis() { return popis; }
    public void setPopis(String popis) { this.popis = popis; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getObrazokUrl() { return obrazokUrl; }
    public void setObrazokUrl(String obrazokUrl) { this.obrazokUrl = obrazokUrl; }

    public boolean isAktivna() { return aktivna; }
    public void setAktivna(boolean aktivna) { this.aktivna = aktivna; }

    public Set<Ingrediencia> getIngrediencie() { return ingrediencie; }
    public void setIngrediencie(Set<Ingrediencia> ingrediencie) { this.ingrediencie = ingrediencie; }

    public Set<Tag> getTagy() {
        return tagy;
    }

    public void setTagy(Set<Tag> tagy) {
        this.tagy = tagy;
    }

    public List<PizzaVelkost> getVelkosti() {
        return velkosti;
    }

    public void setVelkosti(List<PizzaVelkost> velkosti) {
        this.velkosti = velkosti;
    }
}
