package fiit.stuba.sk.ehsnr;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;


public class HlasovaciSystem {
    //Zapuzdrenie - privatne premenne
    private Map<String, Vysledok> vysledkyHlasovania; // Mapa názvov návrhov na výsledky hlasovania
    private SystemoveNastavenia nastavenia;
    private boolean hlasovanieBezi;
    private Random random;

    public HlasovaciSystem(SystemoveNastavenia nastavenia) {
        this.nastavenia = nastavenia;
        this.vysledkyHlasovania = new HashMap<>();
        this.hlasovanieBezi = false;
        this.random = new Random();
    }

    public synchronized void zacniHlasovanie(List<Navrh> navrhyNaAgende) {
        if (hlasovanieBezi) {
            throw new IllegalStateException("Hlasovanie už bolo zahájené.");
        }
        hlasovanieBezi = true;
        generujNahodneHlasy(navrhyNaAgende);
    }

    private void generujNahodneHlasy(List<Navrh> navrhyNaAgende) {
        for (Navrh navrh : navrhyNaAgende) {
            int pocetZa = 0;
            int pocetProti = 0;
            int pocetZdrzaloSa = 0;
            int pocetHlasujucich = nastavenia.getPocetHlasujucich() - 1; // -1 pre hlas správcu

            for (int i = 0; i < pocetHlasujucich; i++) {
                int hlas = random.nextInt(3);
                if (hlas == 0) pocetZa++;
                else if (hlas == 1) pocetProti++;
                else pocetZdrzaloSa++;
            }

            Vysledok vysledok = new Vysledok(pocetZa, pocetProti, pocetZdrzaloSa);
            vysledkyHlasovania.put(navrh.getNazov(), vysledok);
        }
    }

    public synchronized void ukonciHlasovanie() {
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
    }
}




