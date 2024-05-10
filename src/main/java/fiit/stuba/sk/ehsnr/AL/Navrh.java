package fiit.stuba.sk.ehsnr.AL;
/**
 * Abstraktná trieda reprezentujúca legislatívny návrh, ktorý môže byť predložený na hlasovanie.
 * Poskytuje základnú štruktúru a spoločné metódy pre rôzne typy návrhov.
 */
public abstract class Navrh {
    protected String nazov;
    protected String popis;
    protected String stav;
    /**
     * Konštruktor pre vytvorenie nového návrhu.
     * @param nazov Názov návrhu, ktorý identifikuje predmet hlasovania.
     * @param popis Stručný popis návrhu, ktorý poskytuje ďalšie informácie o predmete hlasovania.
     */
    public Navrh(String nazov, String popis) {
        this.nazov = nazov;
        this.popis = popis;
        this.stav = "Predložený";
    } //konštruktor
    /**
     * Vráti názov návrhu.
     * @return Názov návrhu.
     */
    public String getNazov() {
        return nazov;
    }
    /**
     * Vráti popis návrhu.
     * @return Popis návrhu.
     */
    public String getPopis() {
        return popis;
    }
    /**
     * Vráti aktuálny stav návrhu.
     * @return Stav návrhu.
     */
    public abstract void aktualizujStavNaZakladeHlasovania(Vysledok vysledok);

}
