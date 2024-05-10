package fiit.stuba.sk.ehsnr.AL;
/**
 * Trieda Uzivatel reprezentuje účastníka hlasovania v hlasovacom systéme, s možnosťou sledovania, či už užívateľ hlasoval.
 */
public class Uzivatel {
    private String meno;
    private boolean uzHlasoval;
    /**
     * Vytvorí nového užívateľa so špecifikovaným menom.
     * @param meno Meno užívateľa.
     */
    public Uzivatel(String meno) {
        this.meno = meno;
        this.uzHlasoval = false;
    } //konštruktor
    /**
     * Označí užívateľa ako hlasujúceho. Ak užívateľ už hlasoval, vyvolá výnimku.
     * @throws IllegalStateException Ak užívateľ už hlasoval, metóda vyvolá túto výnimku.
     */
    public void hlasuj() {
        if (uzHlasoval) { //ak už hlasoval
            throw new IllegalStateException("Tento užívateľ už hlasoval.");
        }
        uzHlasoval = true;
    }
}
