package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;
import sk.ukf.pizzeria.model.StavObjednavky;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Objednavka")
public class Objednavka extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Objednávka musí byť priradená používateľovi")
    @ManyToOne
    @JoinColumn(name = "Pouzivatel_ID")
    private Pouzivatel pouzivatel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kuchar_id")
    private Pouzivatel kuchar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kurier_id")
    private Pouzivatel kurier;

    @NotNull(message = "Stav objednávky musí byť určený")
    @Enumerated(EnumType.STRING)
    private StavObjednavky stav; // "čakajúca", "pripravuje sa", "doručená"

    @NotNull(message = "Celková cena musí byť vypočítaná")
    @DecimalMin(value = "0.00", message = "Cena nemôže byť záporná")
    @Column(name = "celkova_cena", precision = 10, scale = 2)
    private BigDecimal celkovaCena;

    @NotBlank(message = "Adresa doručenia je povinná")
    @Size(min = 3, max = 255, message = "Adresa musí mať 5 až 255 znakov")
    private String adresa;

    @Size(max = 500, message = "Poznámka môže mať maximálne 500 znakov")
    @Column(columnDefinition = "TEXT")
    private String poznamka;

    @NotEmpty(message = "Objednávka musí obsahovať aspoň jednu položku")
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

    public Pouzivatel getKurier() {
        return kurier;
    }

    public void setKurier(Pouzivatel kurier) {
        this.kurier = kurier;
    }

    public Pouzivatel getKuchar() {
        return kuchar;
    }

    public void setKuchar(Pouzivatel kuchar) {
        this.kuchar = kuchar;
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
