package fiit.stuba.sk.ehsnr;

public class SystemoveNastavenia {
    private int pocetHlasujucich;
    private String nazovZakona;
    private String informacieZakona;
    private int casovyLimit;

    public int getPocetHlasujucich() {
        return pocetHlasujucich;
    }

    public void setPocetHlasujucich(int pocetHlasujucich) {
        if (pocetHlasujucich < 10 || pocetHlasujucich > 200) {
            throw new IllegalArgumentException("Počet hlasujúcich musí byť medzi 10 a 200.");
        }
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
        if (casovyLimit <= 0) {
            throw new IllegalArgumentException("Časový limit musí byť kladné číslo.");
        }
        this.casovyLimit = casovyLimit;
    }
}
