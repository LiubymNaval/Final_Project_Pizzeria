package sk.ukf.pizzeria.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Pizza_Velkost")
public class PizzaVelkost extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Veľkosť musí byť priradená ku konkrétnej pizze")
    @ManyToOne
    @JoinColumn(name = "Pizza_ID")
    private Pizza pizza;

    @NotBlank(message = "Názov veľkosti je povinný (napr. 33cm)")
    @Size(max = 20, message = "Názov veľkosti je príliš dlhý")
    @Column(name = "nazov_velkosti")
    private String nazovVelkosti; // "33cm", "40cm"

    @NotNull(message = "Cena musí byť zadaná")
    @DecimalMin(value = "0.00", inclusive = false, message = "Cena musí byť väčšia ako nula")
    @Digits(integer = 8, fraction = 2, message = "Cena má nesprávny formát")
    @Column(precision = 10, scale = 2)
    private BigDecimal cena;

    public PizzaVelkost() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public String getNazovVelkosti() {
        return nazovVelkosti;
    }

    public void setNazovVelkosti(String nazovVelkosti) {
        this.nazovVelkosti = nazovVelkosti;
    }

    public BigDecimal getCena() { return cena; }
    public void setCena(BigDecimal cena) { this.cena = cena; }
}
