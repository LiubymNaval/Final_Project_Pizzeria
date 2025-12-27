package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Polozka_Objednavky")
public class PolozkaObjednavky extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Objednavka_ID")
    private Objednavka objednavka;

    private int mnozstvo;

    @Column(name = "archivny_nazov")
    private String archivnyNazov;

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
