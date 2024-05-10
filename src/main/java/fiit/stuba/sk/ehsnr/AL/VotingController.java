package fiit.stuba.sk.ehsnr.AL;

import fiit.stuba.sk.ehsnr.GUI.HlasovaciSystemGUI;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.function.BiConsumer;
/**
 * Trieda VotingController zabezpečuje riadenie procesu hlasovania, vrátane záverečného vyhodnocovania hlasov a komunikácie s GUI.
 */
public class VotingController {
    private TimerService timerService;
    private HlasovaciSystem hlasovaciSystem;
    private HlasovaciSystemGUI gui;
    /**
     * Vytvára nový kontrolér pre hlasovanie s prístupom k systému hlasovania a grafickému rozhraniu.
     * @param system Referencia na systém hlasovania, ktorý tento kontrolér bude riadiť.
     * @param gui Referencia na grafické používateľské rozhranie, cez ktoré sa zobrazujú výsledky.
     */
    public VotingController(HlasovaciSystem system, HlasovaciSystemGUI gui) {
        this.hlasovaciSystem = system;
        this.gui = gui;
        this.timerService = new TimerService();
    }
    /**
     * Ukončí hlasovanie pre špecifikovaný zákon, vyhodnotí výsledky a aktualizuje GUI s konečnými výsledkami.
     * @param lawName Názov zákona, pre ktorý sa hlasovanie ukončuje.
     * @param resultLabel GUI komponent, ktorý sa aktualizuje s výsledkom hlasovania.
     * @param resultHandler Funkcia, ktorá spracuje výsledky hlasovania a zabezpečí ich zobrazenie.
     * @throws NoVoteException Vyhodí výnimku, ak hlasovanie nebolo úspešné kvôli nedostatočnej účasti.
     */
    public void finalizeVoting(String lawName, Label resultLabel, BiConsumer<Boolean, Vysledok> resultHandler) throws NoVoteException {
        if (!hlasovaciSystem.isHlasovanieBezi()) {
            Platform.runLater(() -> resultLabel.setText("Hlasovanie nebolo zahájené alebo už bolo ukončené."));
            return;
        }

        hlasovaciSystem.generujZvysokHlasov(lawName); // vygeneruj zvyšok hlasov
        hlasovaciSystem.ukonciHlasovanie(lawName); // ukonči hlasovanie

        if (!hlasovaciSystem.vsetciHlasovali()) {
            throw new NoVoteException("Hlasovanie bolo neúspešné z dôvodu neodhlasovania plného počtu hlasujúcich.");
        } // ak nie všetci hlasovali, hlasovanie bolo neúspešné

        boolean lawPassed = hlasovaciSystem.evaluateLaw(lawName); // vyhodnot zákon
        Vysledok vysledok = hlasovaciSystem.getVysledkyHlasovania().get(lawName); // získaj výsledok hlasovania

        Platform.runLater(() -> {
            resultHandler.accept(lawPassed, vysledok);
            gui.showResultsWindow(lawName, lawPassed, vysledok, new Stage());
        }); // zobraz výsledky hlasovania
    }
}
