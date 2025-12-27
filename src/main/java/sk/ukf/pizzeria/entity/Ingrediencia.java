package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Ingrediencia")
public class Ingrediencia extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nazov;
    private String alergen;

    public Ingrediencia() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNazov() { return nazov; }
    public void setNazov(String nazov) { this.nazov = nazov; }
    public String getAlergen() { return alergen; }
    public void setAlergen(String alergen) { this.alergen = alergen; }
}
