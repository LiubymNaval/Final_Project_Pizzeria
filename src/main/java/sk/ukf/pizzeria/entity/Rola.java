package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Rola")
public class Rola extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nazov; // ROLE_ADMIN, ROLE_ZAKAZNIK...

    public Rola() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNazov() { return nazov; }
    public void setNazov(String nazov) { this.nazov = nazov; }
}