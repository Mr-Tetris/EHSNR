package fiit.stuba.sk.ehsnr.Controller;

import fiit.stuba.sk.ehsnr.AL.*;
import fiit.stuba.sk.ehsnr.GUI.HlasovaciSystemGUI;
import javafx.application.Platform;
import javafx.scene.control.Label;
import java.util.List;
/**
 * Trieda HlasovaciSystemController zabezpečuje koordináciu medzi GUI a aplikačnou logikou hlasovacieho systému.
 * Zodpovedá za spracovanie užívateľských interakcií a delegovanie akcií súvisiacich s hlasovacím procesom.
 */
public class HlasovaciSystemController {
    private Sedenie sedenie;
    private HlasovaciSystem hlasovaciSystem;
    private HlasovaciSystemGUI gui;
    private VotingController votingController;
    /**
     * Konštruktor inicializuje kontrolér s referenciami na potrebné komponenty systému.
     * @param sedenie Inštancia Sedenie pre správu legislatívnych sedení.
     * @param hlasovaciSystem Inštancia HlasovaciSystem pre správu hlasovacích operácií.
     * @param gui Inštancia HlasovaciSystemGUI pre interakciu s grafickým rozhraním.
     * @param votingController Inštancia VotingController pre správu procesu hlasovania.
     */
    public HlasovaciSystemController(Sedenie sedenie, HlasovaciSystem hlasovaciSystem, HlasovaciSystemGUI gui, VotingController votingController) {
        this.sedenie = sedenie;
        this.hlasovaciSystem = hlasovaciSystem;
        this.gui = gui;
        this.votingController = votingController;
    }
    /**
     * Pridá návrh do agendy hlasovacieho systému.
     * @param navrh Inštancia návrhu, ktorý má byť pridaný.
     */
    public void pridajNavrh(Navrh navrh) {
        sedenie.pridajNavrhNaAgendu(navrh);
    }
    /**
     * Vráti zoznam návrhov, ktoré sú aktuálne na agende.
     * @return Zoznam návrhov na agende.
     */

    public List<Navrh> getNavrhyNaAgendu() {
        return sedenie.getNavrhyNaAgende();
    }
    /**
     * Zaháji hlasovanie pre konkrétny návrh.
     * @param navrh Návrh, pre ktorý má byť hlasovanie zahájené.
     */
    public void zacniHlasovanie(Navrh navrh) {
        sedenie.zahajSedenie(navrh);
    }
    /**
     * Zaregistruje hlas za návrh.
     * @param navrh Návrh, pre ktorý sa hlasuje.
     * @param resultLabel Label pre zobrazenie výsledku hlasovania.
     * @param timerService Služba časovača pre riadenie časovania hlasovania.
     */
    public void voteFor(Navrh navrh, Label resultLabel, TimerService timerService) {
        if (hlasovaciSystem.isHlasovanieBezi()) {
            hlasovaciSystem.pripocitajHlas(navrh.getNazov(), "za"); // pridaj hlas
            resultLabel.setText("Hlasovali ste: ZA"); // zobraz výsledok hlasovania
            timerService.continueTimer(); // pokračuj v časovači
        }
    }
    /**
     * Zaregistruje hlas za návrh.
     * @param navrh Návrh, pre ktorý sa hlasuje.
     * @param resultLabel Label pre zobrazenie výsledku hlasovania.
     * @param timerService Služba časovača pre riadenie časovania hlasovania.
     */
    public void voteAgainst(Navrh navrh, Label resultLabel, TimerService timerService) {
        if (hlasovaciSystem.isHlasovanieBezi()) {
            hlasovaciSystem.pripocitajHlas(navrh.getNazov(), "proti"); // pridaj hlas
            resultLabel.setText("Hlasovali ste: PROTI");// zobraz výsledok hlasovania
            timerService.continueTimer();// pokračuj v časovači
        }
    }
    /**
     * Zaregistruje hlas za návrh.
     * @param navrh Návrh, pre ktorý sa hlasuje.
     * @param resultLabel Label pre zobrazenie výsledku hlasovania.
     * @param timerService Služba časovača pre riadenie časovania hlasovania.
     */
    public void abstain(Navrh navrh, Label resultLabel, TimerService timerService) {
        if (hlasovaciSystem.isHlasovanieBezi()) {
            hlasovaciSystem.pripocitajHlas(navrh.getNazov(), "abstain"); // pridaj hlas
            resultLabel.setText("Zdržali ste sa hlasovania"); // zobraz výsledok hlasovania
            timerService.continueTimer();// pokračuj v časovači
        }
    }
    /**
     * Finalizuje proces hlasovania, vyhodnotí výsledky a aktualizuje GUI podľa výsledkov.
     * @param nazov Názov zákona, pre ktorý sa hlasovalo.
     * @param resultLabel Label pre zobrazenie konečného výsledku hlasovania.
     * @throws NoVoteException Výnimka vyvolaná, ak nebol dosiahnutý plný počet hlasov.
     */
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

