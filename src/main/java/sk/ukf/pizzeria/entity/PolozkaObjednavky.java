package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Polozka_Objednavky")
public class PolozkaObjednavky extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Položka musí byť priradená k objednávke")
    @ManyToOne
    @JoinColumn(name = "Objednavka_ID")
    private Objednavka objednavka;

    @Min(value = 1, message = "Množstvo musí byť aspoň 1")
    private int mnozstvo;

    @NotBlank(message = "Archívny názov nesmie byť prázdny")
    @Column(name = "archivny_nazov")
    private String archivnyNazov;

    @NotNull(message = "Archívna cena musí byť zadaná")
    @DecimalMin(value = "0.00", message = "Archívna cena nemôže byť záporná")
    @Column(name = "archivna_cena", precision = 10, scale = 2)
    private BigDecimal archivnaCena;

    public PolozkaObjednavky() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Objednavka getObjednavka() { return objednavka; }
    public void setObjednavka(Objednavka objednavka) { this.objednavka = objednavka; }

    public int getMnozstvo() { return mnozstvo; }
    public void setMnozstvo(int mnozstvo) { this.mnozstvo = mnozstvo; }

    public String getArchivnyNazov() { return archivnyNazov; }
    public void setArchivnyNazov(String archivnyNazov) { this.archivnyNazov = archivnyNazov; }

    public BigDecimal getArchivnaCena() { return archivnaCena; }
    public void setArchivnaCena(BigDecimal archivnaCena) { this.archivnaCena = archivnaCena; }
}
