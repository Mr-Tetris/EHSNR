package fiit.stuba.sk.ehsnr;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class HlasovaciSystemController {
    private Sedenie sedenie;
    private HlasovaciSystem hlasovaciSystem;
    private HlasovaciSystemGUI gui;
    private VotingController votingController;  // Pridanie inštancie VotingController

    public HlasovaciSystemController(Sedenie sedenie, HlasovaciSystem hlasovaciSystem, HlasovaciSystemGUI gui, VotingController votingController) {
        this.sedenie = sedenie;
        this.hlasovaciSystem = hlasovaciSystem;
        this.gui = gui;
        this.votingController = votingController;
    }

    public void pridajNavrh(Navrh navrh) {
        sedenie.pridajNavrhNaAgendu(navrh);
    }

    public List<Navrh> getNavrhyNaAgendu() {
        return sedenie.getNavrhyNaAgende();
    }

    public void zacniHlasovanie(Navrh navrh) {
        sedenie.zahajSedenie(navrh);
    }



    // Metóda pre hlasovanie "ZA"
    public void voteFor(Navrh navrh, Label resultLabel, TimerService timerService) {
        if (hlasovaciSystem.isHlasovanieBezi()) {
            hlasovaciSystem.pripocitajHlas(navrh.getNazov(), "za");
            resultLabel.setText("Hlasovali ste: ZA");
            timerService.continueTimer(); // pokračuje v časovači
        }
    }

    public void voteAgainst(Navrh navrh, Label resultLabel, TimerService timerService) {
        if (hlasovaciSystem.isHlasovanieBezi()) {
            hlasovaciSystem.pripocitajHlas(navrh.getNazov(), "proti");
            resultLabel.setText("Hlasovali ste: PROTI");
            timerService.continueTimer(); // pokračuje v časovači
        }
    }

    public void abstain(Navrh navrh, Label resultLabel, TimerService timerService) {
        if (hlasovaciSystem.isHlasovanieBezi()) {
            hlasovaciSystem.pripocitajHlas(navrh.getNazov(), "abstain");
            resultLabel.setText("Zdržali ste sa hlasovania");
            timerService.continueTimer(); // pokračuje v časovači
        }
    }


    public void finalizeVoting(String nazov, Label resultLabel) {
        votingController.finalizeVoting(nazov, resultLabel, (Boolean passed, Vysledok vysledok) -> {
            Platform.runLater(() -> {
                if (passed) {
                    resultLabel.setText("Zákon bol schválený.");
                } else {
                    resultLabel.setText("Zákon nebol schválený.");
                }
                // Tu už nie je potrebné volanie showResultsWindow, predpokladáme, že sa zobrazuje v finalizeVoting vo VotingController.
            });
        });
    }

    public List<Navrh> refreshProposalsList() {
        return sedenie.getNavrhyNaAgende();
    }
}

