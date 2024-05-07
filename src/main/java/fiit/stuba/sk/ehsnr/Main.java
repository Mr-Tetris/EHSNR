package fiit.stuba.sk.ehsnr;


public class Main {
    public static void main(String[] args) {
        SystemoveNastavenia nastavenia = new SystemoveNastavenia();
        HlasovaciSystem hlasovaciSystem = new HlasovaciSystem(nastavenia);
        Sedenie sedenie = new Sedenie(hlasovaciSystem, nastavenia);
        launchGUI(nastavenia, sedenie);
    }

    private static void launchGUI(SystemoveNastavenia nastavenia, Sedenie sedenie) {
        javafx.application.Application.launch(HlasovaciSystemGUI.class);
    }
}
