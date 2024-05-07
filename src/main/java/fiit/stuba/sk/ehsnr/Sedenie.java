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
        hlasovaciSystem.pridajNavrh(navrh);
    }

    public void zahajSedenie(Navrh vybranyNavrh) {
        hlasovaciSystem.zacniHlasovanie(vybranyNavrh);
        System.out.println("Hlasovanie o zakone '" + vybranyNavrh.getNazov() + "' začína.");
    }

    public void ukonciSedenie(String lawName) throws NoVoteException {
        hlasovaciSystem.ukonciHlasovanie(lawName);
        odstranNavrhNaAgende(lawName);
        System.out.println("Hlasovanie pre zákon '" + lawName + "' bolo ukončené.");
    }

    public void odstranNavrhNaAgende(String lawName) {
        hlasovaciSystem.odstranNavrh(lawName);
        System.out.println("Návrh '" + lawName + "' bol odstránený z agendy.");
    }
}
