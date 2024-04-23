package fiit.stuba.sk.ehsnr;

import java.util.ArrayList;
import java.util.List;

public class Sedenie {
<<<<<<< HEAD
    // Agregácia - obsahuje referenciu na triedu HlasovaciSystem
    private List<Navrh> navrhyNaAgende;
=======
>>>>>>> b58ae528e91d20cd1af008ad374f4ea85fd969ba
    private HlasovaciSystem hlasovaciSystem;
    private SystemoveNastavenia nastavenia;

    public Sedenie(HlasovaciSystem hlasovaciSystem, SystemoveNastavenia nastavenia) {
        this.hlasovaciSystem = hlasovaciSystem;
<<<<<<< HEAD
        this.scanner = scanner;
=======
        this.nastavenia = nastavenia;
>>>>>>> b58ae528e91d20cd1af008ad374f4ea85fd969ba
    }

    public void zahajSedenie(String nazovZakona, String informacie, int pocetHlasujucich, int casovyLimit) {
        // Nastaví parametre hlasovania pred jeho začatím
        nastavenia.setPocetHlasujucich(pocetHlasujucich);
        nastavenia.setNazovZakona(nazovZakona);
        nastavenia.setInformacieZakona(informacie);
        nastavenia.setCasovyLimit(casovyLimit);

<<<<<<< HEAD
    public void zahajSedenie() {
        System.out.println("Zahajuje sa hlasovacie sedenie s nasledujúcimi návrhmi:");
        for (Navrh navrh : navrhyNaAgende) {
            System.out.println(" - " + navrh.getNazov());
        }
        if (!navrhyNaAgende.isEmpty()) {
            hlasovaciSystem.zacniHlasovanie(navrhyNaAgende);
        } else {
            System.out.println("Nie sú žiadne návrhy na agende.");
        }
=======
        // Vytvorenie návrhu zákona a pridanie do zoznamu na hlasovanie
        ZakonNavrh navrh = new ZakonNavrh(nazovZakona, informacie);  // Používame konkrétnu triedu
        List<Navrh> navrhyNaHlasovanie = new ArrayList<>();
        navrhyNaHlasovanie.add(navrh);

        // Spustenie hlasovania s danými návrhmi
        hlasovaciSystem.zacniHlasovanie(navrhyNaHlasovanie);

        System.out.println("Hlasovanie o zakone '" + nazovZakona + "' začína s počtom hlasujúcich " + pocetHlasujucich + " a limitom " + casovyLimit + " sekúnd.");
>>>>>>> b58ae528e91d20cd1af008ad374f4ea85fd969ba
    }

    public void ukonciSedenie() {
        // Ukončenie hlasovania a vyhodnotenie výsledkov
        hlasovaciSystem.ukonciHlasovanie();
<<<<<<< HEAD

        for (Navrh navrh : navrhyNaAgende) {
            Vysledok vysledok = hlasovaciSystem.getVysledkyHlasovania().get(navrh.getNazov());
            if (vysledok != null) {
                System.out.printf("Hlasovalo za/hlasovalo proti: %d/%d\n", vysledok.getPocetZa(), vysledok.getPocetProti());
                System.out.println("Zdržalo sa hlasovania: " + vysledok.getPocetZdrzaloSa());
                System.out.println("Zákon bol " + (vysledok.isSchvaleny() ? "schválený." : "neschválený."));
            }
        }

        System.out.println("\nPre návrat do základných nastavení stlačte 's'.");
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("s")) {
            System.out.println("Neplatný vstup. Pre návrat do základných nastavení stlačte 's'.");
            input = scanner.nextLine();
        }

        hlasovaciSystem.resetujHlasovanie();
        navrhyNaAgende.clear();
    }

    public List<Navrh> getNavrhyNaAgende() {
        return new ArrayList<>(navrhyNaAgende);
=======
        System.out.println("Hlasovanie bolo ukončené.");
        // vypisVysledky();  // Implementujte podľa potreby
>>>>>>> b58ae528e91d20cd1af008ad374f4ea85fd969ba
    }
}
