package fiit.stuba.sk.ehsnr;

public abstract class Navrh {
    protected String nazov;
    protected String popis;
    protected String stav; // Napríklad "Predložený", "V hlasovaní", "Prijatý"

    public Navrh(String nazov, String popis, String stav) {
        this.nazov = nazov;
        this.popis = popis;
        this.stav = stav;
    }

    public abstract void aktualizujStav(String novyStav);

    // Gettery a settery
    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public String getStav() {
        return stav;
    }

    public void setStav(String stav) {
        this.stav = stav;
    }
}
