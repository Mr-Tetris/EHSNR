package fiit.stuba.sk.ehsnr;

import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Map;

public class VotingController {
    private HlasovaciSystem hlasovaciSystem;
    private VotingDialog votingDialog;

    public VotingController(HlasovaciSystem system, VotingDialog dialog) {
        this.hlasovaciSystem = system;
        this.votingDialog = dialog;
    }

    public void initiateVoting(Stage stage, String lawName, String details, int voters, int timeLimit) {
        hlasovaciSystem.setPocetHlasujucich(voters);
        hlasovaciSystem.setCasovyLimit(timeLimit);
        hlasovaciSystem.zacniHlasovanie(new ZakonNavrh(lawName, details));
        votingDialog.showVotingDialog(stage, lawName, details, timeLimit, this);
    }

    public void finalizeVoting(Stage stage, String lawName, Label resultLabel) {
        if (!hlasovaciSystem.isHlasovanieBezi()) {
            resultLabel.setText("Hlasovanie nebolo zahájené alebo už bolo ukončené.");
            return;
        }
        hlasovaciSystem.ukonciHlasovanie(lawName);
        boolean lawPassed = hlasovaciSystem.evaluateLaw(lawName);
        resultLabel.setText(lawPassed ? "Zákon bol schválený." : "Zákon nebol schválený.");
        votingDialog.displayResults("Finalizácia hlasovania: " + lawName, hlasovaciSystem.getVysledkyHlasovania(), stage);
    }


    public void recordVote(String voteType, String lawName, Label resultLabel) {
        System.out.println("Hlas typu '" + voteType + "' bol zaznamenaný pre zákon: " + lawName);
        hlasovaciSystem.pripocitajHlas(lawName, voteType);  // Pridanie hlasu do systému
        resultLabel.setText("Váš hlas: " + voteType);
    }

}



