package fiit.stuba.sk.ehsnr;

public class SystemoveNastavenia {
    private int pocetHlasujucich;
    private String nazovZakona;
    private String informacieZakona;
    private int casovyLimit;

    // Gettery a settery
    public void setPocetHlasujucich(int pocet) {
        if (pocet >= 10 && pocet <= 200) {
            this.pocetHlasujucich = pocet;
        } else {
            throw new IllegalArgumentException("Počet hlasujúcich musí byť v rozmedzí 10 - 200.");
        }
    }

    public int getPocetHlasujucich() {
        return pocetHlasujucich;
    }

    public void setNazovZakona(String nazov) {
        this.nazovZakona = nazov;
    }

    public String getNazovZakona() {
        return nazovZakona;
    }

    public void setInformacieZakona(String informacie) {
        this.informacieZakona = informacie;
    }

    public String getInformacieZakona() {
        return informacieZakona;
    }

    public void setCasovyLimit(int limit) {
        this.casovyLimit = limit;
    }

    public int getCasovyLimit() {
        return casovyLimit;
    }
}
