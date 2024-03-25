package fiit.stuba.sk.ehsnr;

public class SystemoveNastavenia {
    private String pravidlaHlasovania;
    private int casoveLimity;

    public void nastavPravidla(String pravidla) {
        this.pravidlaHlasovania = pravidla;
    }
    public void nastavCasoveLimity(int limity) {
        this.casoveLimity = limity;
    }
    // Gettery a settery
}

