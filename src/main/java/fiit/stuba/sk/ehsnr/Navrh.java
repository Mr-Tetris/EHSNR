package fiit.stuba.sk.ehsnr;

public abstract class Navrh {
    protected String nazov;
    protected String popis;
    protected String stav; // môže byť napríklad "Predložený", "Schválený", "Zamietnutý" atď.

    public Navrh(String nazov, String popis) {
        this.nazov = nazov;
        this.popis = popis;
        this.stav = "Predložený"; // predvolený stav pri vytvorení návrhu
    }

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

    public abstract void aktualizujStavNaZakladeHlasovania(Vysledok vysledok);

    // Ďalšie metódy alebo abstraktné metódy, ktoré budú potrebné pre prácu s návrhom
}
