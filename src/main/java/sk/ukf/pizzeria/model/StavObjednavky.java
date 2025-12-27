package sk.ukf.pizzeria.model;

public enum StavObjednavky {
    CAKAJUCA("Čakajúca"),
    PRIPRAVUJE_SA("Pripravuje sa"),
    PRIPRAVENA("Pripravená na odber"),
    DORUCUJE_SA("Doručuje sa"),
    DORUCENA("Doručená"),
    ZRUSENA("Zrušená");

    private final String displayValue;

    StavObjednavky(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
