package fiit.stuba.sk.ehsnr.AL;
/**
 * Trieda SystemoveNastavenia uchováva konfiguračné nastavenia pre hlasovací systém, ako sú počet hlasujúcich a časový limit pre hlasovanie.
 */
public class SystemoveNastavenia {
    // Zapúzdrenie private premenných a pre nich public gettre a settre
    private int pocetHlasujucich;
    private int casovyLimit;
    /**
     * Získava aktuálny počet hlasujúcich registrovaných v systéme.
     * @return Počet hlasujúcich.
     */
    public int getPocetHlasujucich() {
        return pocetHlasujucich;
    }
    /**
     * Nastavuje počet hlasujúcich v systéme.
     * @param pocetHlasujucich Nový počet hlasujúcich.
     */
    public void setPocetHlasujucich(int pocetHlasujucich) {
        this.pocetHlasujucich = pocetHlasujucich;
    }
    /**
     * Získava nastavený časový limit pre trvanie hlasovania v sekundách.
     * @return Časový limit pre hlasovanie.
     */
    public int getCasovyLimit() {
        return casovyLimit;
    }
    /**
     * Nastavuje časový limit pre hlasovanie.
     * @param casovyLimit Nový časový limit v sekundách.
     */
    public void setCasovyLimit(int casovyLimit) {
        this.casovyLimit = casovyLimit;
    }
}
