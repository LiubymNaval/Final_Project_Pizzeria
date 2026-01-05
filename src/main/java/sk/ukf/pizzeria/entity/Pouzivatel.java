package sk.ukf.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Set;

@Entity
@Table(name = "Pouzivatel")
public class Pouzivatel extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Meno nesmie byť prázdne")
    @Size(min = 2, max = 50, message = "Meno musí mať 2 až 50 znakov")
    private String meno;

    @NotBlank(message = "Priezvisko nesmie byť prázdne")
    @Size(min = 2, max = 50, message = "Priezvisko musí mať 2 až 50 znakov")
    private String priezvisko;

    @NotBlank(message = "Email nesmie byť prázdny")
    @Email(message = "Zadajte platnú emailovú adresu")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Heslo nesmie byť prázdne")
    @Size(min = 8, message = "Heslo musí mať aspoň 8 znakov")
    private String heslo;

    @NotBlank(message = "Telefónne číslo je povinné")
    @Pattern(regexp= "^\\+421\\d{9}$", message= "Telefónne číslo musí začínať +421 a obsahovať presne 9 číslic za ním")
    private String telefon;

    @Column(name = "obrazok_url")
    private String obrazokUrl;

    @Column(nullable = false)
    private boolean aktivny = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Pouzivatel_Roly",
            joinColumns = @JoinColumn(name = "Pouzivatel_ID"),
            inverseJoinColumns = @JoinColumn(name = "Rola_ID")
    )
    private java.util.Set<Rola> roly;

    public Pouzivatel() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeslo() {
        return heslo;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getObrazokUrl() {
        return obrazokUrl;
    }

    public void setObrazokUrl(String obrazokUrl) {
        this.obrazokUrl = obrazokUrl;
    }

    public Set<Rola> getRoly() {
        return roly;
    }

    public void setRoly(Set<Rola> roly) {
        this.roly = roly;
    }
    public boolean isAktivny() { return aktivny; }
    public void setAktivny(boolean aktivny) { this.aktivny = aktivny; }
}
