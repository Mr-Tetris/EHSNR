package fiit.stuba.sk.ehsnr;

public class ReferendumNavrh extends Navrh {
    private String otazka; // Otázka, ktorá bude položená v referende

    public ReferendumNavrh(String nazov, String popis, String otazka) {
        super(nazov, popis);
        this.otazka = otazka;
    }

    public String getOtazka() {
        return otazka;
    }

    public void setOtazka(String otazka) {
        this.otazka = otazka;
    }

    @Override
    public void aktualizujStavNaZakladeHlasovania(Vysledok vysledok) {
        // Toto je zjednodušená logika, která předpokládá, že výsledek obsahuje potřebnou logiku
        if (vysledok.isSchvaleny()) {
            this.stav = "Schválený";
        } else {
            this.stav = "Zamietnutý";
        }
    }
}
