package sk.ukf.pizzeria.dto;

import jakarta.validation.constraints.*;

public class RegistraciaDto {

    @NotBlank(message = "Meno nesmie byť prázdne")
    private String meno;

    @Email(message = "Zadajte platný email")
    private String email;

    @Size(min = 8, message = "Heslo musí mať aspoň 8 znakov")
    private String heslo;

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
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
}
