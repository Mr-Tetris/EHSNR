package fiit.stuba.sk.ehsnr;

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
    public void odstranNavrh(String lawName) throws NoVoteException {
        sedenie.ukonciSedenie(lawName);  // Toto zavolá metódu na ukončenie hlasovania a odstránenie návrhu
    }

    public List<Navrh> getNavrhyNaAgendu() {
        return sedenie.getNavrhyNaAgende();
    }

    public void zacniHlasovanie(Navrh navrh) {
        sedenie.zahajSedenie(navrh);
    }

    public void voteFor(Navrh navrh, Label resultLabel, TimerService timerService) {
        if (hlasovaciSystem.isHlasovanieBezi()) {
            hlasovaciSystem.pripocitajHlas(navrh.getNazov(), "za");
            resultLabel.setText("Hlasovali ste: ZA");
            timerService.continueTimer();
        }
    }

    public void voteAgainst(Navrh navrh, Label resultLabel, TimerService timerService) {
        if (hlasovaciSystem.isHlasovanieBezi()) {
            hlasovaciSystem.pripocitajHlas(navrh.getNazov(), "proti");
            resultLabel.setText("Hlasovali ste: PROTI");
            timerService.continueTimer();
        }
    }

    public void abstain(Navrh navrh, Label resultLabel, TimerService timerService) {
        if (hlasovaciSystem.isHlasovanieBezi()) {
            hlasovaciSystem.pripocitajHlas(navrh.getNazov(), "abstain");
            resultLabel.setText("Zdržali ste sa hlasovania");
            timerService.continueTimer();
        }
    }

    public void finalizeVoting(String nazov, Label resultLabel) throws NoVoteException {
        votingController.finalizeVoting(nazov, resultLabel, (Boolean passed, Vysledok vysledok) -> {
            Platform.runLater(() -> {
                if (passed) {
                    resultLabel.setText("Zákon bol schválený.");
                } else {
                    resultLabel.setText("Zákon nebol schválený.");
                }
            });
        });
    }

    public List<Navrh> refreshProposalsList() {
        return sedenie.getNavrhyNaAgende();
    }
}

