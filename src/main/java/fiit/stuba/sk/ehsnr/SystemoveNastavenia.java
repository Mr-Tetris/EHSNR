package fiit.stuba.sk.ehsnr;

public class SystemoveNastavenia {
    private int pocetHlasujucich;
    private String nazovZakona;
    private String informacieZakona;
    private int casovyLimit;

    // Gettery a settery pre každý atribút
    public int getPocetHlasujucich() {
        return pocetHlasujucich;
    }

    public void setPocetHlasujucich(int pocetHlasujucich) {
        this.pocetHlasujucich = pocetHlasujucich;
    }

    public String getNazovZakona() {
        return nazovZakona;
    }

    public void setNazovZakona(String nazovZakona) {
        this.nazovZakona = nazovZakona;
    }

    public String getInformacieZakona() {
        return informacieZakona;
    }

    public void setInformacieZakona(String informacieZakona) {
        this.informacieZakona = informacieZakona;
    }

    public int getCasovyLimit() {
        return casovyLimit;
    }

    public void setCasovyLimit(int casovyLimit) {
        this.casovyLimit = casovyLimit;
    }
}
