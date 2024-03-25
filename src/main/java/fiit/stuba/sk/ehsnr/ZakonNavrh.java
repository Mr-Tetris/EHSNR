package fiit.stuba.sk.ehsnr;

public class ZakonNavrh {
    private String nazov;
    private String popis;
    private String stav;  // Napríklad "Predložený", "V hlasovaní", "Prijatý"
    private String text;

    public void aktualizujStav(String novyStav) {
        this.stav = novyStav;
    }
}

