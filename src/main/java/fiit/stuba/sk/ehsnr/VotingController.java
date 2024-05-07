package fiit.stuba.sk.ehsnr;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.function.BiConsumer;

public class VotingController {
    private TimerService timerService;
    private HlasovaciSystem hlasovaciSystem;
    private VotingDialog votingDialog;
    private HlasovaciSystemGUI gui;

    public VotingController(HlasovaciSystem system, VotingDialog dialog, HlasovaciSystemGUI gui) {
        this.hlasovaciSystem = system;
        this.votingDialog = dialog;
        this.gui = gui;
        this.timerService = new TimerService();
    }

    public void finalizeVoting(String lawName, Label resultLabel, BiConsumer<Boolean, Vysledok> resultHandler) throws NoVoteException {
        if (!hlasovaciSystem.isHlasovanieBezi()) {
            Platform.runLater(() -> resultLabel.setText("Hlasovanie nebolo zahájené alebo už bolo ukončené."));
            return;
        }

        hlasovaciSystem.generujZvysokHlasov(lawName);
        hlasovaciSystem.ukonciHlasovanie(lawName);

        if (!hlasovaciSystem.vsetciHlasovali()) {
            throw new NoVoteException("Hlasovanie bolo neúspešné z dôvodu neodhlasovania plného počtu hlasujúcich.");
        }

        boolean lawPassed = hlasovaciSystem.evaluateLaw(lawName);
        Vysledok vysledok = hlasovaciSystem.getVysledkyHlasovania().get(lawName);

        Platform.runLater(() -> {
            resultHandler.accept(lawPassed, vysledok);
            gui.showResultsWindow(lawName, lawPassed, vysledok, new Stage());
        });
    }
}
