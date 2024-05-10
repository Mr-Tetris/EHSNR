package fiit.stuba.sk.ehsnr.AL;


import fiit.stuba.sk.ehsnr.GUI.HlasovaciSystemGUI;

public class Main {
    public static void main(String[] args) {
        SystemoveNastavenia nastavenia = new SystemoveNastavenia();
        HlasovaciSystem hlasovaciSystem = new HlasovaciSystem(nastavenia);
        Sedenie sedenie = new Sedenie(hlasovaciSystem, nastavenia);
        launchGUI(nastavenia, sedenie);
    }

    private static void launchGUI(SystemoveNastavenia nastavenia, Sedenie sedenie) {
        javafx.application.Application.launch(HlasovaciSystemGUI.class);
    } // launchGUI
}
