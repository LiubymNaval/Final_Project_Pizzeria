package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Ingrediencia")
public class Ingrediencia extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Názov ingrediencie je povinný")
    @Size(min = 2, max = 50, message = "Názov musí mať 2 až 50 znakov")
    private String nazov;

    @Size(max = 100, message = "Popis alergénov je príliš dlhý")
    private String alergen;

    public Ingrediencia() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNazov() { return nazov; }
    public void setNazov(String nazov) { this.nazov = nazov; }
    public String getAlergen() { return alergen; }
    public void setAlergen(String alergen) { this.alergen = alergen; }
}
