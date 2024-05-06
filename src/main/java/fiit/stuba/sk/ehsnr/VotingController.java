package fiit.stuba.sk.ehsnr;

import javafx.application.Platform;
import javafx.scene.control.Alert;
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

    public TimerService getTimerService() {
        return timerService;
    }

    public void initiateVoting(String lawName, String details, int timeLimit, Label timerLabel, Label voteResultLabel, Stage stage) {
        timerService.startTimer(timeLimit, new TimerService.TimerCallback() {
            @Override
            public void onTick(long remainingSeconds) {
                Platform.runLater(() -> timerLabel.setText("Zostávajúci čas: " + remainingSeconds + " sekúnd"));
            }

            @Override
            public void onFinish() {
                try {
                    finalizeVoting(lawName, voteResultLabel, (Boolean passed, Vysledok vysledok) -> {
                        Platform.runLater(() -> {
                            votingDialog.displayResults(lawName, passed, vysledok, stage);
                        });
                    });
                } catch (NoVoteException e) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Hlasovanie bolo neúspešné");
                        alert.setHeaderText("Neúspešné hlasovanie");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                        votingDialog.displayResults(lawName, false, new Vysledok(0, 0, 0), stage);
                    });
                }
            }
        });
    }

    public void finalizeVoting(String lawName, Label resultLabel, BiConsumer<Boolean, Vysledok> resultHandler) throws NoVoteException {
        if (!hlasovaciSystem.isHlasovanieBezi()) {
            Platform.runLater(() -> resultLabel.setText("Hlasovanie nebolo zahájené alebo už bolo ukončené."));
            return;
        }

        hlasovaciSystem.generujZvysokHlasov(lawName);
        hlasovaciSystem.ukonciHlasovanie(lawName);

        // Skontrolovať, či všetci hlasovali
        if (!hlasovaciSystem.vsetciHlasovali()) {
            throw new NoVoteException("Hlasovanie bolo neúspešné z dôvodu neodhlasovania plného počtu hlasujúcich.");
        }

        boolean lawPassed = hlasovaciSystem.evaluateLaw(lawName);
        Vysledok vysledok = hlasovaciSystem.getVysledkyHlasovania().get(lawName);

        Platform.runLater(() -> {
            resultHandler.accept(lawPassed, vysledok);
            try {
                gui.showResultsWindow(lawName, lawPassed, vysledok, new Stage());
            } catch (NoVoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void recordVote(String voteType, String lawName, Label resultLabel) {
        hlasovaciSystem.pripocitajHlas(lawName, voteType);
        resultLabel.setText("Váš hlas: " + voteType);
    }
}
