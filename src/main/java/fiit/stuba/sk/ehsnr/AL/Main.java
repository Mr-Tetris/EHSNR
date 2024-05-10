package fiit.stuba.sk.ehsnr.AL;


import fiit.stuba.sk.ehsnr.GUI.HlasovaciSystemGUI;
/**
 * Hlavná trieda aplikácie, ktorá spúšťa grafické používateľské rozhranie pre hlasovací systém.
 */
public class Main {
    /**
     * Vstupný bod aplikácie. Inicializuje potrebné zložky systému a spúšťa grafické rozhranie.
     * @param args Argumenty príkazového riadku, ktoré môžu byť použité na konfiguráciu aplikácie.
     */
    public static void main(String[] args) {
        SystemoveNastavenia nastavenia = new SystemoveNastavenia();
        HlasovaciSystem hlasovaciSystem = new HlasovaciSystem(nastavenia);
        Sedenie sedenie = new Sedenie(hlasovaciSystem, nastavenia);
        launchGUI(nastavenia, sedenie);
    }
    /**
     * Spustí grafické používateľské rozhranie hlasovacieho systému.
     * @param nastavenia Objekt obsahujúci systémové nastavenia aplikácie.
     * @param sedenie Objekt obsahujúci informácie o aktuálne prebiehajúcom hlasovacom zasadnutí.
     */
    private static void launchGUI(SystemoveNastavenia nastavenia, Sedenie sedenie) {
        javafx.application.Application.launch(HlasovaciSystemGUI.class);
    } // launchGUI
}
