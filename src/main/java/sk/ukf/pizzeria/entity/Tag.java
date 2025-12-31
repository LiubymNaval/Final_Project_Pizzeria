package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Tag")
public class Tag extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Názov tagu je povinný")
    @Size(min = 2, max = 30, message = "Tag musí mať 2 až 30 znakov")
    private String nazov;

    public Tag() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNazov() { return nazov; }
    public void setNazov(String nazov) { this.nazov = nazov; }
}
