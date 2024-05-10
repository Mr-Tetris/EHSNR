package fiit.stuba.sk.ehsnr.AL;

public class SystemoveNastavenia {
    // Zapúzdrenie private premenných a pre nich public gettre a settre
    private int pocetHlasujucich;
    private int casovyLimit;

    public int getPocetHlasujucich() {
        return pocetHlasujucich;
    }

    public void setPocetHlasujucich(int pocetHlasujucich) {
        this.pocetHlasujucich = pocetHlasujucich;
    }

    public int getCasovyLimit() {
        return casovyLimit;
    }

    public void setCasovyLimit(int casovyLimit) {
        this.casovyLimit = casovyLimit;
    }
}
