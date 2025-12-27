package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Tag")
public class Tag extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nazov;

    public Tag() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNazov() { return nazov; }
    public void setNazov(String nazov) { this.nazov = nazov; }
}
