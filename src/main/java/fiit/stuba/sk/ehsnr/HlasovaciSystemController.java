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

    public void ukonciSedenie(String lawName) {
        hlasovaciSystem.ukonciHlasovanie(lawName);
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
            resultLabel.setText("Hlasovali ste: ZDRŽALI STE SA");
            timerService.continueTimer(); // pokračuje v časovači
        }
    }


    public void finalizeVoting(String lawName, Label resultLabel, BiConsumer<Boolean, Vysledok> resultHandler) {
        votingController.finalizeVoting(lawName, resultLabel, resultHandler);
    }


}

