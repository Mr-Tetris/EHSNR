package fiit.stuba.sk.ehsnr;

public class ReferendumNavrh extends Navrh {
    //Dedenie konštruktora z triedy Navrh
    //Polymorfizmus - referenciu typu Navrh môžeme priradiť objekt typu ReferendumNavrh
    private String otazka;

    public ReferendumNavrh(String nazov, String popis, String otazka) {
        super(nazov, popis);
        this.otazka = otazka;
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
