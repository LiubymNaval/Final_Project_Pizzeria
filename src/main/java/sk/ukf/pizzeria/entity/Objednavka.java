package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;
import sk.ukf.pizzeria.model.StavObjednavky;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Objednavka")
public class Objednavka extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Pouzivatel_ID")
    private Pouzivatel pouzivatel;

    @Enumerated(EnumType.STRING)
    private StavObjednavky stav; // "čakajúca", "pripravuje sa", "doručená"

    @Column(name = "celkova_cena", precision = 10, scale = 2)
    private BigDecimal celkovaCena;

    private String adresa;

    @Column(columnDefinition = "TEXT")
    private String poznamka;

    @OneToMany(mappedBy = "objednavka", cascade = CascadeType.ALL)
    private java.util.List<PolozkaObjednavky> polozky;

    public Objednavka() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pouzivatel getPouzivatel() {
        return pouzivatel;
    }

    public void setPouzivatel(Pouzivatel pouzivatel) {
        this.pouzivatel = pouzivatel;
    }

    public StavObjednavky getStav() {
        return stav;
    }

    public void setStav(StavObjednavky stav) {
        this.stav = stav;
    }

    public BigDecimal getCelkovaCena() { return celkovaCena; }
    public void setCelkovaCena(BigDecimal celkovaCena) { this.celkovaCena = celkovaCena; }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public List<PolozkaObjednavky> getPolozky() {
        return polozky;
    }

    public void setPolozky(List<PolozkaObjednavky> polozky) {
        this.polozky = polozky;
    }
    public String getPoznamka() { return poznamka; }
    public void setPoznamka(String poznamka) { this.poznamka = poznamka; }
}
