package sk.ukf.pizzeria.entity;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;

@Entity
@Table(name = "Polozka_Kosika")
public class PolozkaKosika extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Položka košíka musí patriť používateľovi")
    @ManyToOne
    @JoinColumn(name = "Pouzivatel_ID")
    private Pouzivatel pouzivatel;

    @NotNull(message = "Položka košíka musí odkazovať na konkrétnu pizzu a veľkosť")
    @ManyToOne
    @JoinColumn(name = "Pizza_Velkost_ID")
    private PizzaVelkost pizzaVelkost;

    @Min(value = 1, message = "Množstvo musí byť aspoň 1")
    @Max(value = 50, message = "Maximálne množstvo jednej položky je 50 kusov")
    private int mnozstvo;

    public PolozkaKosika() {}

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

    public PizzaVelkost getPizzaVelkost() {
        return pizzaVelkost;
    }

    public void setPizzaVelkost(PizzaVelkost pizzaVelkost) {
        this.pizzaVelkost = pizzaVelkost;
    }

    public int getMnozstvo() {
        return mnozstvo;
    }

    public void setMnozstvo(int mnozstvo) {
        this.mnozstvo = mnozstvo;
    }
}