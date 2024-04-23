package fiit.stuba.sk.ehsnr;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SystemoveNastavenia nastavenia = new SystemoveNastavenia();
        HlasovaciSystem hlasovaciSystem = new HlasovaciSystem(nastavenia);
        Sedenie sedenie = new Sedenie(hlasovaciSystem, nastavenia);

        launchGUI(nastavenia, sedenie);
    }

    private static void launchGUI(SystemoveNastavenia nastavenia, Sedenie sedenie) {
        // Táto metóda by spustila GUI aplikáciu, ktorá by mala metódy na nastavenie a spustenie hlasovania
        // Tento kód predpokladá, že existuje GUI trieda, ktorá riadi logiku GUI a môže vyzerať napríklad takto:
        javafx.application.Application.launch(HlasovaciSystemGUI.class);
    }
}
