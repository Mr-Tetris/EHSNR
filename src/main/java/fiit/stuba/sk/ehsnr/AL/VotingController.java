package fiit.stuba.sk.ehsnr.AL;

import fiit.stuba.sk.ehsnr.GUI.HlasovaciSystemGUI;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.function.BiConsumer;

public class VotingController {
    private TimerService timerService;
    private HlasovaciSystem hlasovaciSystem;
    private HlasovaciSystemGUI gui;

    public VotingController(HlasovaciSystem system, HlasovaciSystemGUI gui) {
        this.hlasovaciSystem = system;
        this.gui = gui;
        this.timerService = new TimerService();
    }

    public void finalizeVoting(String lawName, Label resultLabel, BiConsumer<Boolean, Vysledok> resultHandler) throws NoVoteException {
        if (!hlasovaciSystem.isHlasovanieBezi()) {
            Platform.runLater(() -> resultLabel.setText("Hlasovanie nebolo zahájené alebo už bolo ukončené."));
            return;
        }

        hlasovaciSystem.generujZvysokHlasov(lawName); // vygeneruj zvyšok hlasov
        hlasovaciSystem.ukonciHlasovanie(lawName); // ukonči hlasovanie

        if (!hlasovaciSystem.vsetciHlasovali()) {
            throw new NoVoteException("Hlasovanie bolo neúspešné z dôvodu neodhlasovania plného počtu hlasujúcich.");
        } // ak nie všetci hlasovali, hlasovanie bolo neúspešné

        boolean lawPassed = hlasovaciSystem.evaluateLaw(lawName); // vyhodnot zákon
        Vysledok vysledok = hlasovaciSystem.getVysledkyHlasovania().get(lawName); // získaj výsledok hlasovania

        Platform.runLater(() -> {
            resultHandler.accept(lawPassed, vysledok);
            gui.showResultsWindow(lawName, lawPassed, vysledok, new Stage());
        }); // zobraz výsledky hlasovania
    }
}
