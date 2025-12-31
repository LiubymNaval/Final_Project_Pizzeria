package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Rola")
public class Rola extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Názov roly je povinný")
    @Pattern(regexp = "^ROLE_[A-Z]+$", message = "Rola musí začínať na 'ROLE_' a obsahovať len veľké písmená")
    private String nazov; // ROLE_ADMIN, ROLE_ZAKAZNIK...

    public Rola() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNazov() { return nazov; }
    public void setNazov(String nazov) { this.nazov = nazov; }
}