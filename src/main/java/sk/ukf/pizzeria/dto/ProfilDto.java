package sk.ukf.pizzeria.dto;
import jakarta.validation.constraints.*;

public class ProfilDto {
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
    @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "Telefónne číslo musí mať 9 až 15 číslic")
    private String telefon;

    private String obrazokUrl;

    public ProfilDto() {}

    public String getMeno() { return meno; }
    public String getPriezvisko() { return priezvisko; }
    public String getEmail() { return email; }
    public String getTelefon() { return telefon; }

    public void setMeno(String meno) { this.meno = meno; }
    public void setPriezvisko(String priezvisko) { this.priezvisko = priezvisko; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getObrazokUrl() {
        return obrazokUrl;
    }

    public void setObrazokUrl(String obrazokUrl) {
        this.obrazokUrl = obrazokUrl;
    }
}
