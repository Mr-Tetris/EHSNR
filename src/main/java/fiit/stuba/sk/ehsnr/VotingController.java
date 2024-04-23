package fiit.stuba.sk.ehsnr;

import javafx.stage.Stage;
import java.util.Arrays;

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

        // Začíname hlasovanie s aktualizovaným zoznamom návrhov
        hlasovaciSystem.zacniHlasovanie(Arrays.asList(new ZakonNavrh(lawName, details)));

        // Zobrazujeme hlasovací dialóg s dodanými časovým limitom a kontrolérom
        votingDialog.showVotingDialog(stage, lawName, details, timeLimit, this);
    }

    public void finalizeVoting(Stage stage) {
        hlasovaciSystem.ukonciHlasovanie();
        // Získavame a zobrazujeme výsledky hlasovania
        votingDialog.displayResults("Finalizácia hlasovania", hlasovaciSystem.getVysledkyHlasovania(), stage);
    }

    public void recordVote(String voteType, String lawName) {
        // Tu by sa mohla zaznamenať volba hlasu a možno dokonca aktualizovať model
        System.out.println("Hlas typu '" + voteType + "' bol zaznamenaný pre zákon: " + lawName);
    }
}
