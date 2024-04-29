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

    public synchronized void zacniHlasovanie(Navrh navrh) {
        if (hlasovanieBezi) {
            throw new IllegalStateException("Hlasovanie už bolo zahájené.");
        }
        this.navrhyNaAgende.clear();
        this.navrhyNaAgende.add(navrh);
        this.hlasovanieBezi = true;
        System.out.println("Hlasovanie o návrhu '" + navrh.getNazov() + "' bolo začaté.");
    }



    public void ukonciHlasovanie(String lawName) {
        Vysledok vysledok = vysledkyHlasovania.get(lawName);
        if (vysledok == null) {
            throw new IllegalStateException("Hlasovanie pre tento zákon ešte nebolo zahájené.");
        }
        this.hlasovanieBezi = false;
        System.out.println("Hlasovanie o zákone '" + lawName + "' bolo ukončené.");
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
    }


    private void generujZvysokHlasov(String lawName) {
        Vysledok vysledok = vysledkyHlasovania.get(lawName);
        int zostavajuciHlasy = pocetHlasujucich - 1;  // Odpočítaj prvý hlas

        for (int i = 0; i < zostavajuciHlasy; i++) {
            int hlas = random.nextInt(3);
            if (hlas == 0) vysledok.pripocitajZa();
            else if (hlas == 1) vysledok.pripocitajProti();
            else vysledok.pripocitajZdrzaloSa();
        }
    }
    public void hlasujZa(String lawName) {
        Vysledok vysledok = vysledkyHlasovania.getOrDefault(lawName, new Vysledok(0, 0, 0));
        vysledok.pripocitajZa();
        vysledkyHlasovania.put(lawName, vysledok); // Uistite sa, že zmeny sú uložené
    }

    public void hlasujProti(String lawName) {
        Vysledok vysledok = vysledkyHlasovania.getOrDefault(lawName, new Vysledok(0, 0, 0));
        vysledok.pripocitajProti();
        vysledkyHlasovania.put(lawName, vysledok);
    }

    public void hlasujZdrzSa(String lawName) {
        Vysledok vysledok = vysledkyHlasovania.getOrDefault(lawName, new Vysledok(0, 0, 0));
        vysledok.pripocitajZdrzaloSa();
        vysledkyHlasovania.put(lawName, vysledok);
    }

    public void pridajNavrh(Navrh navrh) {
        this.navrhyNaAgende.add(navrh);
    }
    public List<Navrh> getNavrhyNaAgende(){
        return new ArrayList<>(navrhyNaAgende);
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
