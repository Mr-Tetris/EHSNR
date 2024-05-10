package fiit.stuba.sk.ehsnr.AL;
/**
 * Trieda ZakonNavrh rozširuje abstraktnú triedu Navrh a je špecificky určená pre legislatívne návrhy alebo zákony.
 */
public class ZakonNavrh extends Navrh {
    //Dedenie konštruktora z triedy Navrh
    //Polymorfizmus - referenciu typu Navrh môžeme priradiť objekt typu ZakonNavrh
    /**
     * Konštruktor pre ZakonNavrh, ktorý inicializuje názov a popis návrhu zákona.
     * @param nazov Názov návrhu zákona.
     * @param popis Detailný popis návrhu zákona.
     */
    public ZakonNavrh(String nazov, String popis) {
        super(nazov, popis);
    }
    /**
     * Aktualizuje stav návrhu zákona na základe výsledkov hlasovania. Nastaví stav na 'Schválený' ak bol návrh prijatý,
     * inak nastaví stav na 'Zamietnutý'.
     * @param vysledok Objekt Vysledok obsahujúci počty hlasov za a proti návrhu zákona.
     */
    @Override
    public void aktualizujStavNaZakladeHlasovania(Vysledok vysledok) {
        if (vysledok.isSchvaleny()) {
            this.stav = "Schválený";
        } else {
            this.stav = "Zamietnutý";
        }
    } //metóda na aktualizáciu stavu na základe výsledku hlasovania
}
