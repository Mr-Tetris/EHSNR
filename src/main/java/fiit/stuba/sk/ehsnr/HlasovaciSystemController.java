package fiit.stuba.sk.ehsnr;

import javafx.scene.control.Label;

import java.util.List;

public class HlasovaciSystemController {
    private Sedenie sedenie;
    private HlasovaciSystem hlasovaciSystem;

    public HlasovaciSystemController(Sedenie sedenie) {
        this.sedenie = sedenie;
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
}

