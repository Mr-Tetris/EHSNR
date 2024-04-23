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
    private int pocetHlasujucich; // Premenná pre ukladanie počtu hlasujúcich
    private int casovyLimit;     // Premenná pre ukladanie časového limitu hlasovania

    public HlasovaciSystem(SystemoveNastavenia nastavenia) {
        this.nastavenia = nastavenia;
        this.vysledkyHlasovania = new HashMap<>();
        this.hlasovanieBezi = false;
        this.random = new Random();
        this.navrhyNaAgende = new ArrayList<>();
        this.pocetHlasujucich = 0;  // inicializácia na 0
        this.casovyLimit = 0;       // inicializácia na 0
    }

    // Settery pre počet hlasujúcich a časový limit
    public void setPocetHlasujucich(int pocet) {
        this.pocetHlasujucich = pocet;
    }

    public void setCasovyLimit(int limit) {
        this.casovyLimit = limit;
    }

    public synchronized void zacniHlasovanie(List<Navrh> navrhy) {
        if (hlasovanieBezi) {
            throw new IllegalStateException("Hlasovanie už bolo zahájené.");
        }
        this.navrhyNaAgende = navrhy;
        hlasovanieBezi = true;
        generujNahodneHlasy();
    }

    public synchronized void ukonciHlasovanie() {
        if (!hlasovanieBezi) {
            throw new IllegalStateException("Hlasovanie nebolo zahájené.");
        }
        hlasovanieBezi = false;
    }


    private void generujNahodneHlasy() {
        for (Navrh navrh : navrhyNaAgende) {
            Vysledok vysledok = vygenerujVysledky(this.pocetHlasujucich - 1);  // Odpočítanie 1 pre hlas správcu
            vysledkyHlasovania.put(navrh.getNazov(), vysledok);
        }
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
        int totalVotes = vysledok.getPocetZa() + vysledok.getPocetProti() + vysledok.getPocetZdrzaloSa();
        return vysledok.getPocetZa() > totalVotes / 2;
    }
    public void pripocitajHlas(String lawName, String voteType) {
        Vysledok vysledok = vysledkyHlasovania.get(lawName);
        if (vysledok == null) {
            throw new IllegalStateException("Hlasovanie pre tento zákon ešte nebolo zahájené.");
        }
        if ("za".equals(voteType)) {
            vysledok.pripocitajZa();
        } else if ("proti".equals(voteType)) {
            vysledok.pripocitajProti();
        } else if ("abstain".equals(voteType)) {
            vysledok.pripocitajZdrzaloSa();
        }
    }


    public Map<String, Vysledok> getVysledkyHlasovania() {
        return new HashMap<>(vysledkyHlasovania);
    }

    public boolean isHlasovanieBezi() {
        return hlasovanieBezi;
    }

    public void resetujHlasovanie() {
        vysledkyHlasovania.clear();
        hlasovanieBezi = false;
    }
}
