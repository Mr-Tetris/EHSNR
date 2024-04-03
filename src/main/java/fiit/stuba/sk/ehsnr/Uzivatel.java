package fiit.stuba.sk.ehsnr;

public class Uzivatel {
    private String meno;
    private boolean uzHlasoval;

    public Uzivatel(String meno) {
        this.meno = meno;
        this.uzHlasoval = false;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public boolean isUzHlasoval() {
        return uzHlasoval;
    }

    public void hlasuj() {
        if (uzHlasoval) {
            throw new IllegalStateException("Tento užívateľ už hlasoval.");
        }
        uzHlasoval = true;
    }


    public void resetujHlasovanie() {
        uzHlasoval = false;
    }
}
