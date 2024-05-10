package fiit.stuba.sk.ehsnr.AL;
/**
 * Trieda ReferendumNavrh je špecifický typ návrhu pre hlasovanie o otázkach v referende.
 * Rozširuje abstraktnú triedu Navrh a pridáva špecifický atribút pre otázku referenda.
 */
public class ReferendumNavrh extends Navrh {
    //Dedenie konštruktora z triedy Navrh
    //Polymorfizmus - referenciu typu Navrh môžeme priradiť objekt typu ReferendumNavrh
    private String otazka;
    /**
     * Konštruktor pre triedu ReferendumNavrh, ktorý inicializuje návrh s daným názvom, popisom a otázkou referenda.
     * @param nazov Názov referenda.
     * @param popis Podrobný popis referenda.
     * @param otazka Konkrétna otázka položená v referende.
     */
    public ReferendumNavrh(String nazov, String popis, String otazka) {
        super(nazov, popis);
        this.otazka = otazka;
    } //konštruktor
    /**
     * Aktualizuje stav referendového návrhu na základe výsledku hlasovania.
     * Ak bol návrh schválený, stav sa zmení na "Schválený". Ak bol zamietnutý, stav sa zmení na "Zamietnutý".
     * @param vysledok Objekt Vysledok, ktorý obsahuje informácie o výsledku hlasovania.
     */
    @Override
    public void aktualizujStavNaZakladeHlasovania(Vysledok vysledok) {
        if (vysledok.isSchvaleny()) {
            this.stav = "Schválený";
        } else {
            this.stav = "Zamietnutý";
        }
    }
}
