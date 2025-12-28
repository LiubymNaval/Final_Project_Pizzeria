package sk.ukf.pizzeria.dto;

public class ProfilDto {
    private String meno;
    private String priezvisko;
    private String email;
    private String telefon;

    public ProfilDto() {}

    public String getMeno() { return meno; }
    public String getPriezvisko() { return priezvisko; }
    public String getEmail() { return email; }
    public String getTelefon() { return telefon; }

    public void setMeno(String meno) { this.meno = meno; }
    public void setPriezvisko(String priezvisko) { this.priezvisko = priezvisko; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
}
