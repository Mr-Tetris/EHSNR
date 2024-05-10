package fiit.stuba.sk.ehsnr.AL;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
/**
 * Trieda HlasovaciSystem zastrešuje logiku pre správu hlasovacích procesov.
 */
public class HlasovaciSystem {
    private Map<String, Vysledok> vysledkyHlasovania;
    private SystemoveNastavenia nastavenia;
    private boolean hlasovanieBezi;
    private Random random;
    private List<Navrh> navrhyNaAgende;
    private int pocetHlasujucich;
    private int casovyLimit;
    private Uzivatel uzivatel;

    /**
     * Konštruktor pre inicializáciu hlasovacieho systému s danými nastaveniami.
     * @param nastavenia Objekt obsahujúci konfiguračné nastavenia systému.
     */
    public HlasovaciSystem(SystemoveNastavenia nastavenia) {
        this.nastavenia = nastavenia;
        this.vysledkyHlasovania = new HashMap<>();
        this.hlasovanieBezi = false;
        this.random = new Random();
        this.navrhyNaAgende = new ArrayList<>();
        this.pocetHlasujucich = 0;
        this.casovyLimit = 0;
        this.uzivatel = new Uzivatel("default");
    }
    /**
     * Nastaví počet hlasujúcich v systéme.
     * @param pocet Celkový počet hlasujúcich, ktorí môžu hlasovať.
     */
    public void setPocetHlasujucich(int pocet) {
        this.pocetHlasujucich = pocet;
    }
    /**
     * Začne hlasovanie pre daný návrh.
     * @param navrh Návrh, pre ktorý sa má začať hlasovanie.
     * @throws IllegalStateException Ak hlasovanie už bolo zahájené.
     */
    public synchronized void zacniHlasovanie(Navrh navrh) {
        if (hlasovanieBezi) {
            throw new IllegalStateException("Hlasovanie už bolo zahájené.");
        }
        this.navrhyNaAgende.clear();
        this.navrhyNaAgende.add(navrh);
        this.hlasovanieBezi = true;
        System.out.println("Hlasovanie o návrhu '" + navrh.getNazov() + "' bolo začaté.");
    }
    /**
     * Ukončí hlasovanie pre daný zákon a vyhodnotí výsledky.
     * @param lawName Názov zákona, pre ktorý sa má ukončiť hlasovanie.
     * @throws NoVoteException Ak nebol zadaný žiaden hlas.
     * @throws IllegalStateException Ak hlasovanie pre daný zákon nebolo zahájené.
     */
    public void ukonciHlasovanie(String lawName) throws NoVoteException {
        Vysledok vysledok = vysledkyHlasovania.get(lawName); // získaj výsledok hlasovania
        if (vysledok == null) {
            throw new IllegalStateException("Hlasovanie pre tento zákon ešte nebolo zahájené.");
        } // ak neexistuje výsledok, hlasovanie nebolo zahájené
        generujZvysokHlasov(lawName); // vygeneruj zvyšok hlasov
        if (!vsetciHlasovali()) {
            throw new NoVoteException("Hlasovanie bolo neúspešné z dôvodu neodhlasovania plného počtu hlasujúcich.");
        } // ak nie všetci hlasovali, hlasovanie bolo neúspešné
        this.hlasovanieBezi = false;
        odstranNavrh(lawName); // odstráň návrh z agendy
        System.out.println("Hlasovanie o zákone '" + lawName + "' bolo ukončené.");
    }
    /**
     * Skontroluje, či všetci registrovaní hlasujúci odovzdali svoj hlas.
     * @return true, ak všetci hlasujúci zúčastnení na hlasovaní odovzdali hlas, inak false.
     */
    public boolean vsetciHlasovali() {
        setPocetHlasujucich(nastavenia.getPocetHlasujucich()); // nastav počet hlasujúcich
        return vysledkyHlasovania.values().stream().allMatch(v ->
                (v.getPocetZa() + v.getPocetProti() + v.getPocetZdrzaloSa()) == pocetHlasujucich); // vráť true, ak všetci hlasovali
    }
    /**
     * Vygeneruje hlasovacie výsledky pre zvyšok hlasujúcich, ktorí ešte nehlasovali.
     * @param lawName Názov zákona, pre ktorý sa hlasovanie generuje.
     * @throws NoVoteException Ak neexistujú žiadne čiastočné hlasovacie výsledky na doplnenie.
     */
    public void generujZvysokHlasov(String lawName) throws NoVoteException {
        Vysledok vysledok = vysledkyHlasovania.get(lawName); // získaj výsledok hlasovania
        if (vysledok == null) {
            throw new NoVoteException("Hlasovanie bolo neúspešné z dôvodu neodhlasovania plného počtu hlasujúcich.");
        }

        int pocetAktualnychHlasov = vysledok.getPocetZa() + vysledok.getPocetProti() + vysledok.getPocetZdrzaloSa();
        int celkovyPocetHlasujucich = nastavenia.getPocetHlasujucich();
        int zostavajuciHlasy = celkovyPocetHlasujucich - pocetAktualnychHlasov;

        Vysledok dodatocneHlasy = vygenerujVysledky(zostavajuciHlasy); // vygeneruj výsledky pre zostávajúce hlasy
        for (int i = 0; i < dodatocneHlasy.getPocetZa(); i++) {
            vysledok.pripocitajZa(); // pripočítaj hlasy
        }
        for (int i = 0; i < dodatocneHlasy.getPocetProti(); i++) {
            vysledok.pripocitajProti(); // pripočítaj hlasy
        }
        for (int i = 0; i < dodatocneHlasy.getPocetZdrzaloSa(); i++) {
            vysledok.pripocitajZdrzaloSa(); // pripočítaj hlasy
        }

        vysledok.vyhodnot(); // vyhodnoť výsledky
        vysledkyHlasovania.put(lawName, vysledok); // ulož výsledky
    }
    /**
     * Vygeneruje hlasovacie výsledky pre daný počet hlasujúcich.
     * @param pocetHlasujucich Počet hlasujúcich, pre ktorých majú byť hlasovacie výsledky vygenerované.
     * @return Objekt Vysledok s vygenerovanými hlasmi.
     */
    private Vysledok vygenerujVysledky(int pocetHlasujucich) {
        int pocetZa = 0, pocetProti = 0, pocetZdrzaloSa = 0;
        for (int i = 0; i < pocetHlasujucich; i++) {
            int hlas = random.nextInt(3);
            if (hlas == 0) pocetZa++;
            else if (hlas == 1) pocetProti++;
            else pocetZdrzaloSa++;
        } // vygeneruj náhodné hlasy
        return new Vysledok(pocetZa, pocetProti, pocetZdrzaloSa); // vráť výsledky
    }
    /**
     * Vyhodnotí, či bol zákon schválený na základe hlasovacích výsledkov.
     * @param lawName Názov zákona, ktorý sa hodnotí.
     * @return true, ak bol zákon schválený, inak false.
     * @throws IllegalStateException Ak hlasovanie pre daný zákon nebolo zahájené.
     */
    public boolean evaluateLaw(String lawName) {
        Vysledok vysledok = vysledkyHlasovania.get(lawName); // získaj výsledok hlasovania
        if (vysledok == null) {
            throw new IllegalStateException("Hlasovanie pre tento zákon nebolo zahájené.");
        } // ak neexistuje výsledok, hlasovanie nebolo zahájené
        int totalVotes = vysledok.getPocetZa() + vysledok.getPocetProti();
        return vysledok.getPocetZa() > totalVotes / 2; // vráť true, ak bol zákon schválený
    }
    /**
     * Pripočíta hlas k existujúcim hlasovacím výsledkom pre daný zákon.
     * @param lawName Názov zákona, pre ktorý sa hlas pripočítava.
     * @param voteType Typ hlasu (za, proti, abstain).
     * @throws IllegalStateException Ak hlasovanie pre daný zákon ešte nezačalo.
     */
    public void pripocitajHlas(String lawName, String voteType) {
        if (!hlasovanieBezi) {
            throw new IllegalStateException("Hlasovanie pre tento zákon ešte nezačalo.");
        } // ak hlasovanie nebeží, hlasovanie ešte nezačalo

        Vysledok vysledok = vysledkyHlasovania.get(lawName); // získaj výsledok hlasovania
        if (vysledok == null) {
            vysledok = new Vysledok(0, 0, 0);
            vysledkyHlasovania.put(lawName, vysledok);
        } // ak neexistuje výsledok, vytvor nový

        switch (voteType) {
            case "za":
                vysledok.pripocitajZa();
                break;
            case "proti":
                vysledok.pripocitajProti();
                break;
            case "abstain":
                vysledok.pripocitajZdrzaloSa();
                break;
        }

        uzivatel.hlasuj(); // označ užívateľa ako hlasujúceho
    }
    /**
     * Odstráni návrh z agendy hlasovania.
     * @param lawName Názov zákona, ktorý sa má odstrániť z agendy.
     */
    public void odstranNavrh(String lawName) {
        navrhyNaAgende.removeIf(navrh -> navrh.getNazov().equals(lawName));
    } // odstráň návrh z agendy
    /**
     * Pridá návrh do agendy hlasovania.
     * @param navrh Návrh, ktorý sa má pridať.
     */
    public void pridajNavrh(Navrh navrh) {
        this.navrhyNaAgende.add(navrh);
    } // pridaj návrh na agendu
    /**
     * Vráti zoznam návrhov, ktoré sú aktuálne na agende hlasovania.
     * @return Zoznam návrhov na agende.
     */
    public List<Navrh> getNavrhyNaAgende() {
        return new ArrayList<>(navrhyNaAgende);
    } // vráť návrhy na agende
    /**
     * Vráti mapu hlasovacích výsledkov pre všetky zákony.
     * @return Mapa hlasovacích výsledkov.
     */
    public Map<String, Vysledok> getVysledkyHlasovania() {
        return new HashMap<>(vysledkyHlasovania);
    } // vráť výsledky hlasovania
    /**
     * Skontroluje, či hlasovanie aktuálne prebieha.
     * @return true, ak hlasovanie prebieha, inak false.
     */
    public boolean isHlasovanieBezi() {
        return hlasovanieBezi;
    }
    /**
     * Vráti aktuálny počet hlasujúcich registrovaných v systéme.
     * @return Počet hlasujúcich.
     */
    public int getPocetHlasujucich() {
        return this.pocetHlasujucich;
    }
}
