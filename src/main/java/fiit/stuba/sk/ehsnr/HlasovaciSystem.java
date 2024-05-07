package fiit.stuba.sk.ehsnr;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class HlasovaciSystem {
    private Map<String, Vysledok> vysledkyHlasovania;
    private SystemoveNastavenia nastavenia;
    private boolean hlasovanieBezi;
    private Random random;
    private List<Navrh> navrhyNaAgende;
    private int pocetHlasujucich;
    private int casovyLimit;
    private Uzivatel uzivatel;

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

    public void setPocetHlasujucich(int pocet) {
        this.pocetHlasujucich = pocet;
    }

    public synchronized void zacniHlasovanie(Navrh navrh) {
        if (hlasovanieBezi) {
            throw new IllegalStateException("Hlasovanie už bolo zahájené.");
        }
        this.navrhyNaAgende.clear();
        this.navrhyNaAgende.add(navrh);
        this.hlasovanieBezi = true;
        System.out.println("Hlasovanie o návrhu '" + navrh.getNazov() + "' bolo začaté.");
    }

    public void ukonciHlasovanie(String lawName) throws NoVoteException {
        Vysledok vysledok = vysledkyHlasovania.get(lawName);
        if (vysledok == null) {
            throw new IllegalStateException("Hlasovanie pre tento zákon ešte nebolo zahájené.");
        }
        generujZvysokHlasov(lawName);
        if (!vsetciHlasovali()) {
            throw new NoVoteException("Hlasovanie bolo neúspešné z dôvodu neodhlasovania plného počtu hlasujúcich.");
        }
        this.hlasovanieBezi = false;
        odstranNavrh(lawName);
        System.out.println("Hlasovanie o zákone '" + lawName + "' bolo ukončené.");
    }

    public boolean vsetciHlasovali() {
        setPocetHlasujucich(nastavenia.getPocetHlasujucich());
        return vysledkyHlasovania.values().stream().allMatch(v ->
                (v.getPocetZa() + v.getPocetProti() + v.getPocetZdrzaloSa()) == pocetHlasujucich);
    }

    public void generujZvysokHlasov(String lawName) throws NoVoteException {
        Vysledok vysledok = vysledkyHlasovania.get(lawName);
        if (vysledok == null) {
            throw new NoVoteException("Hlasovanie bolo neúspešné z dôvodu neodhlasovania plného počtu hlasujúcich.");
        }

        int pocetAktualnychHlasov = vysledok.getPocetZa() + vysledok.getPocetProti() + vysledok.getPocetZdrzaloSa();
        int celkovyPocetHlasujucich = nastavenia.getPocetHlasujucich();
        int zostavajuciHlasy = celkovyPocetHlasujucich - pocetAktualnychHlasov;

        Vysledok dodatocneHlasy = vygenerujVysledky(zostavajuciHlasy);
        for (int i = 0; i < dodatocneHlasy.getPocetZa(); i++) {
            vysledok.pripocitajZa();
        }
        for (int i = 0; i < dodatocneHlasy.getPocetProti(); i++) {
            vysledok.pripocitajProti();
        }
        for (int i = 0; i < dodatocneHlasy.getPocetZdrzaloSa(); i++) {
            vysledok.pripocitajZdrzaloSa();
        }

        vysledok.vyhodnot();
        vysledkyHlasovania.put(lawName, vysledok);
    }

    private Vysledok vygenerujVysledky(int pocetHlasujucich) {
        int pocetZa = 0, pocetProti = 0, pocetZdrzaloSa = 0;
        for (int i = 0; i < pocetHlasujucich; i++) {
            int hlas = random.nextInt(3);
            if (hlas == 0) pocetZa++;
            else if (hlas == 1) pocetProti++;
            else pocetZdrzaloSa++;
        }
        return new Vysledok(pocetZa, pocetProti, pocetZdrzaloSa);
    }

    public boolean evaluateLaw(String lawName) {
        Vysledok vysledok = vysledkyHlasovania.get(lawName);
        if (vysledok == null) {
            throw new IllegalStateException("Hlasovanie pre tento zákon nebolo zahájené.");
        }
        int totalVotes = vysledok.getPocetZa() + vysledok.getPocetProti();
        return vysledok.getPocetZa() > totalVotes / 2;
    }

    public void pripocitajHlas(String lawName, String voteType) {
        if (!hlasovanieBezi) {
            throw new IllegalStateException("Hlasovanie pre tento zákon ešte nezačalo.");
        }

        Vysledok vysledok = vysledkyHlasovania.get(lawName);
        if (vysledok == null) {
            vysledok = new Vysledok(0, 0, 0);
            vysledkyHlasovania.put(lawName, vysledok);
        }

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

        uzivatel.hlasuj();
    }

    public void odstranNavrh(String lawName) {
        navrhyNaAgende.removeIf(navrh -> navrh.getNazov().equals(lawName));
    }

    public void pridajNavrh(Navrh navrh) {
        this.navrhyNaAgende.add(navrh);
    }

    public List<Navrh> getNavrhyNaAgende() {
        return new ArrayList<>(navrhyNaAgende);
    }

    public Map<String, Vysledok> getVysledkyHlasovania() {
        return new HashMap<>(vysledkyHlasovania);
    }

    public boolean isHlasovanieBezi() {
        return hlasovanieBezi;
    }

    public int getPocetHlasujucich() {
        return this.pocetHlasujucich;
    }
}
