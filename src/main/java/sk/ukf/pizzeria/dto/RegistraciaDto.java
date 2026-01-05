package sk.ukf.pizzeria.dto;

import jakarta.validation.constraints.*;

public class RegistraciaDto {

    @NotBlank(message = "Meno nesmie byť prázdne")
    @Size(min = 2, max = 50, message = "Meno musí mať 2 až 50 znakov")
    private String meno;

    @NotBlank(message = "Priezvisko nesmie byť prázdne")
    @Size(min = 2, max = 50, message = "Priezvisko musí mať 2 až 50 znakov")
    private String priezvisko;

    @NotBlank(message = "Email nesmie byť prázdny")
    @Email(message = "Zadajte platnú emailovú adresu")
    private String email;

    @NotBlank(message = "Telefónne číslo je povinné")
    @Pattern(regexp= "^\\+421\\d{9}$", message= "Telefónne číslo musí začínať +421 a obsahovať presne 9 číslic za ním")
    private String telefon;

    @NotBlank(message = "Heslo nesmie byť prázdne")
    @Size(min = 8, max = 25, message = "Heslo musí mať 8 až 25 znakov")
    private String heslo;

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
}
