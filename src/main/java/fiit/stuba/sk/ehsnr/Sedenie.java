package fiit.stuba.sk.ehsnr;

import java.util.ArrayList;
import java.util.List;

public class Sedenie {
    private HlasovaciSystem hlasovaciSystem;
    private SystemoveNastavenia nastavenia;

    public Sedenie(HlasovaciSystem hlasovaciSystem, SystemoveNastavenia nastavenia) {
        this.hlasovaciSystem = hlasovaciSystem;
        this.nastavenia = nastavenia;
    }

    public void zahajSedenie(String nazovZakona, String informacie, int pocetHlasujucich, int casovyLimit) {
        // Nastaví parametre hlasovania pred jeho začatím
        nastavenia.setPocetHlasujucich(pocetHlasujucich);
        nastavenia.setNazovZakona(nazovZakona);
        nastavenia.setInformacieZakona(informacie);
        nastavenia.setCasovyLimit(casovyLimit);

        // Vytvorenie návrhu zákona a pridanie do zoznamu na hlasovanie
        ZakonNavrh navrh = new ZakonNavrh(nazovZakona, informacie);  // Používame konkrétnu triedu
        List<Navrh> navrhyNaHlasovanie = new ArrayList<>();
        navrhyNaHlasovanie.add(navrh);

        // Spustenie hlasovania s danými návrhmi
        hlasovaciSystem.zacniHlasovanie(navrhyNaHlasovanie);

        System.out.println("Hlasovanie o zakone '" + nazovZakona + "' začína s počtom hlasujúcich " + pocetHlasujucich + " a limitom " + casovyLimit + " sekúnd.");
    }

    public void ukonciSedenie() {
        // Ukončenie hlasovania a vyhodnotenie výsledkov
        hlasovaciSystem.ukonciHlasovanie();
        System.out.println("Hlasovanie bolo ukončené.");
        // vypisVysledky();  // Implementujte podľa potreby
    }
}
