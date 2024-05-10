package fiit.stuba.sk.ehsnr.AL;

import java.util.List;

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
