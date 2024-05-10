package fiit.stuba.sk.ehsnr.Controller;

import fiit.stuba.sk.ehsnr.AL.*;
import fiit.stuba.sk.ehsnr.GUI.HlasovaciSystemGUI;
import javafx.application.Platform;
import javafx.scene.control.Label;
import java.util.List;

public class HlasovaciSystemController {
    private Sedenie sedenie;
    private HlasovaciSystem hlasovaciSystem;
    private HlasovaciSystemGUI gui;
    private VotingController votingController;

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

    public void voteFor(Navrh navrh, Label resultLabel, TimerService timerService) {
        if (hlasovaciSystem.isHlasovanieBezi()) {
            hlasovaciSystem.pripocitajHlas(navrh.getNazov(), "za"); // pridaj hlas
            resultLabel.setText("Hlasovali ste: ZA"); // zobraz výsledok hlasovania
            timerService.continueTimer(); // pokračuj v časovači
        }
    }

    public void voteAgainst(Navrh navrh, Label resultLabel, TimerService timerService) {
        if (hlasovaciSystem.isHlasovanieBezi()) {
            hlasovaciSystem.pripocitajHlas(navrh.getNazov(), "proti"); // pridaj hlas
            resultLabel.setText("Hlasovali ste: PROTI");// zobraz výsledok hlasovania
            timerService.continueTimer();// pokračuj v časovači
        }
    }

    public void abstain(Navrh navrh, Label resultLabel, TimerService timerService) {
        if (hlasovaciSystem.isHlasovanieBezi()) {
            hlasovaciSystem.pripocitajHlas(navrh.getNazov(), "abstain"); // pridaj hlas
            resultLabel.setText("Zdržali ste sa hlasovania"); // zobraz výsledok hlasovania
            timerService.continueTimer();// pokračuj v časovači
        }
    }

    public void finalizeVoting(String nazov, Label resultLabel) throws NoVoteException {
        votingController.finalizeVoting(nazov, resultLabel, (Boolean passed, Vysledok vysledok) -> {
            Platform.runLater(() -> { // spusti vlákno na zobrazenie výsledku
                if (passed) { // ak bol zákon schválený
                    resultLabel.setText("Zákon bol schválený.");
                } else {
                    resultLabel.setText("Zákon nebol schválený.");
                }
            });
        });
    }

}

