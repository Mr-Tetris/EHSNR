package fiit.stuba.sk.ehsnr.AL;
/**
 * Výnimka používaná na signalizáciu situácie, keď hlasovanie nie je úspešné z dôvodu nedostatočného počtu odovzdaných hlasov.
 * Táto výnimka sa vyvolá, ak nebol dosiahnutý požadovaný počet hlasov na platnosť hlasovania.
 */
public class NoVoteException extends Exception {
    /**
     * Konštruktor pre NoVoteException s poskytnutím detailnej správy o chybe.
     * @param message Správa popisujúca dôvod vyvolania výnimky.
     */
    public NoVoteException(String message) {
        super(message);
    }
}
