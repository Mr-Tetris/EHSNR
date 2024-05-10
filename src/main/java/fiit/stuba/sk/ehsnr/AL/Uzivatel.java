package fiit.stuba.sk.ehsnr.AL;

public class Uzivatel {
    private String meno;
    private boolean uzHlasoval;

    public Uzivatel(String meno) {
        this.meno = meno;
        this.uzHlasoval = false;
    } //konštruktor

    public void hlasuj() {
        if (uzHlasoval) { //ak už hlasoval
            throw new IllegalStateException("Tento užívateľ už hlasoval.");
        }
        uzHlasoval = true;
    }
}
