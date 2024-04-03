package fiit.stuba.sk.ehsnr;

public class ZakonNavrh extends Navrh {
    //Dedenie konštruktora z triedy Navrh
    //Polymorfizmus - referenciu typu Navrh môžeme priradiť objekt typu ZakonNavrh
    public ZakonNavrh(String nazov, String popis) {
        super(nazov, popis);
    }

    @Override
    public void aktualizujStavNaZakladeHlasovania(Vysledok vysledok) {
        if (vysledok.isSchvaleny()) {
            this.stav = "Schválený";
        } else {
            this.stav = "Zamietnutý";
        }
    }
}
