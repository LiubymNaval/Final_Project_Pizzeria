package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Polozka_Kosika")
public class PolozkaKosika extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Pouzivatel_ID")
    private Pouzivatel pouzivatel;

    @ManyToOne
    @JoinColumn(name = "Pizza_Velkost_ID")
    private PizzaVelkost pizzaVelkost;

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