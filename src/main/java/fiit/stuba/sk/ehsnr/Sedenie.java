package fiit.stuba.sk.ehsnr;

import java.util.List;
import java.util.ArrayList;

public class Sedenie {
    private HlasovaciSystem hlasovaciSystem;
    private SystemoveNastavenia nastavenia;

    public Sedenie(HlasovaciSystem hlasovaciSystem, SystemoveNastavenia nastavenia) {
        this.hlasovaciSystem = hlasovaciSystem;
        this.nastavenia = nastavenia;
    }
    public List<Navrh> getNavrhyNaAgende() {
        return hlasovaciSystem.getNavrhyNaAgende();
    }


    public void pridajNavrhNaAgendu(Navrh navrh) {
        hlasovaciSystem.pridajNavrh(navrh);  // Asumujeme, že hlasovací systém môže spravovať svoje návrhy
    }

    public void zahajSedenie(Navrh vybranyNavrh) {
        hlasovaciSystem.zacniHlasovanie(vybranyNavrh);  // Prenesú sa všetky potrebné informácie
        System.out.println("Hlasovanie o zakone '" + vybranyNavrh.getNazov() + "' začína.");
    }

    public void ukonciSedenie(String lawName) throws NoVoteException {
        hlasovaciSystem.ukonciHlasovanie(lawName);  // Prenos názvu zákona alebo identifikátora
        System.out.println("Hlasovanie pre zákon '" + lawName + "' bolo ukončené.");
    }
}
