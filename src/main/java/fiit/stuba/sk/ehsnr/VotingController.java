package fiit.stuba.sk.ehsnr;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.function.BiConsumer;

public class VotingController {
    private HlasovaciSystem hlasovaciSystem;
    private VotingDialog votingDialog;
    private HlasovaciSystemGUI gui;

    public VotingController(HlasovaciSystem system, VotingDialog dialog, HlasovaciSystemGUI gui) {
        this.hlasovaciSystem = system;
        this.votingDialog = dialog;
        this.gui = gui;
    }

    public void initiateVoting(Stage stage, String lawName, String details, int voters, int timeLimit) {
        hlasovaciSystem.setPocetHlasujucich(voters);
        hlasovaciSystem.setCasovyLimit(timeLimit);
        hlasovaciSystem.zacniHlasovanie(new ZakonNavrh(lawName, details));
        votingDialog.showVotingDialog(stage, lawName, details, timeLimit, this);
    }

    public void finalizeVoting(String lawName, Label resultLabel, BiConsumer<Boolean, Vysledok> resultHandler) {
        if (!hlasovaciSystem.isHlasovanieBezi()) {
            Platform.runLater(() -> resultLabel.setText("Hlasovanie nebolo zahájené alebo už bolo ukončené."));
            return;
        }
        hlasovaciSystem.ukonciHlasovanie(lawName);
        boolean lawPassed = hlasovaciSystem.evaluateLaw(lawName);
        Vysledok vysledok = hlasovaciSystem.getVysledkyHlasovania().get(lawName);
        Platform.runLater(() -> {
            resultHandler.accept(lawPassed, vysledok);
            gui.showResultsWindow(lawName, lawPassed, vysledok, new Stage());
        });
    }

    public void recordVote(String voteType, String lawName, Label resultLabel) {
        hlasovaciSystem.pripocitajHlas(lawName, voteType);
        resultLabel.setText("Váš hlas: " + voteType);
    }

}
