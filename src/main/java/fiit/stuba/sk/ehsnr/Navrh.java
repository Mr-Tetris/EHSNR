package fiit.stuba.sk.ehsnr;

public abstract class Navrh {
    protected String nazov;
    protected String popis;
    protected String stav;

    public Navrh(String nazov, String popis) {
        this.nazov = nazov;
        this.popis = popis;
        this.stav = "Predložený";
    }

    public String getNazov() {
        return nazov;
    }

    public String getPopis() {
        return popis;
    }

    public abstract void aktualizujStavNaZakladeHlasovania(Vysledok vysledok);

}
