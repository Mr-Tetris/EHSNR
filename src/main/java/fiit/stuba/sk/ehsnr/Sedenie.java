package fiit.stuba.sk.ehsnr;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sedenie {
    private List<Navrh> navrhyNaAgende;
    private HlasovaciSystem hlasovaciSystem;
    private Scanner scanner;

    public Sedenie(HlasovaciSystem hlasovaciSystem, Scanner scanner) {
        this.navrhyNaAgende = new ArrayList<>();
        this.hlasovaciSystem = hlasovaciSystem;
        this.scanner = scanner; // Pridaný Scanner pre čítanie vstupu od užívateľa
    }

    public void pridajNavrhNaAgendu(Navrh navrh) {
        navrhyNaAgende.add(navrh);
    }

    public void zahajSedenie() {
        System.out.println("Zahajuje sa hlasovacie sedenie s nasledujúcimi návrhmi:");
        for (Navrh navrh : navrhyNaAgende) {
            System.out.println(" - " + navrh.getNazov());
        }
        if (!navrhyNaAgende.isEmpty()) {
            hlasovaciSystem.zacniHlasovanie(navrhyNaAgende); // Tu bola opravená chýbajúca argumentácia
        } else {
            System.out.println("Nie sú žiadne návrhy na agende.");
        }
    }

    public void ukonciSedenie() {
        hlasovaciSystem.ukonciHlasovanie();

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

        // Resetovanie hlasovacieho systému a sedenia pre novú session
        hlasovaciSystem.resetujHlasovanie();
        navrhyNaAgende.clear();
    }

    public List<Navrh> getNavrhyNaAgende() {
        return new ArrayList<>(navrhyNaAgende);
    }
}



