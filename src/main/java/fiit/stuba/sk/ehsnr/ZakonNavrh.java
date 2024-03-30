package fiit.stuba.sk.ehsnr;

public class ZakonNavrh extends Navrh {

    public ZakonNavrh(String nazov, String popis) {
        super(nazov, popis);
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
