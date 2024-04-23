package fiit.stuba.sk.ehsnr;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;

public class HlasovaciSystem {
<<<<<<< HEAD
    //Zapuzdrenie - privatne premenne
    private Map<String, Vysledok> vysledkyHlasovania; // Mapa názvov návrhov na výsledky hlasovania
=======
    private Map<String, Vysledok> vysledkyHlasovania;
>>>>>>> b58ae528e91d20cd1af008ad374f4ea85fd969ba
    private SystemoveNastavenia nastavenia;
    private boolean hlasovanieBezi;
    private Random random;

    public HlasovaciSystem(SystemoveNastavenia nastavenia) {
        this.nastavenia = nastavenia;
        this.vysledkyHlasovania = new HashMap<>();
        this.hlasovanieBezi = false;
        this.random = new Random();
    }

    public synchronized void zacniHlasovanie(List<Navrh> navrhyNaAgende) throws IllegalStateException {
        if (hlasovanieBezi) {
            throw new IllegalStateException("Hlasovanie už bolo zahájené.");
        }
        hlasovanieBezi = true;
        generujNahodneHlasy(navrhyNaAgende);
    }

    private void generujNahodneHlasy(List<Navrh> navrhyNaAgende) {
        for (Navrh navrh : navrhyNaAgende) {
            int pocetHlasujucich = nastavenia.getPocetHlasujucich() - 1; // -1 pre hlas správcu
            Vysledok vysledok = vygenerujVysledky(pocetHlasujucich);
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

    public synchronized void ukonciHlasovanie() throws IllegalStateException {
        if (!hlasovanieBezi) {
            throw new IllegalStateException("Hlasovanie nebolo zahájené.");
        }
        hlasovanieBezi = false;
    }

    public synchronized Map<String, Vysledok> getVysledkyHlasovania() {
        return new HashMap<>(vysledkyHlasovania);
    }

    public boolean isHlasovanieBezi() {
        return hlasovanieBezi;
    }

    public void resetujHlasovanie() {
        vysledkyHlasovania.clear();
    }
}
