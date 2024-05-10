package fiit.stuba.sk.ehsnr.AL;

import java.util.List;
/**
 * Trieda Sedenie reprezentuje kontext sedenia alebo zasadnutia, v rámci ktorého prebiehajú rôzne hlasovania.
 * Umožňuje spravovanie agendy návrhov na hlasovanie a zahajovanie alebo ukončovanie jednotlivých hlasovaní.
 */
public class Sedenie {
    private HlasovaciSystem hlasovaciSystem;
    private SystemoveNastavenia nastavenia;
    /**
     * Konštruktor pre triedu Sedenie, ktorý inicializuje sedenie s daným hlasovacím systémom a systémovými nastaveniami.
     * @param hlasovaciSystem Referencia na inštanciu HlasovaciSystem, ktorý spravuje hlasovanie.
     * @param nastavenia Inštancia SystemoveNastavenia obsahujúca konfigurácie pre hlasovanie.
     */
    public Sedenie(HlasovaciSystem hlasovaciSystem, SystemoveNastavenia nastavenia) {
        this.hlasovaciSystem = hlasovaciSystem;
        this.nastavenia = nastavenia;
    }
    /**
     * Vracia zoznam návrhov pripravených na agende na hlasovanie.
     * @return Zoznam návrhov na agende.
     */
    public List<Navrh> getNavrhyNaAgende() {
        return hlasovaciSystem.getNavrhyNaAgende();
    }

    /**
     * Pridá návrh na agendu hlasovania.
     * @param navrh Objekt Navrh, ktorý má byť pridaný na agendu.
     */
    public void pridajNavrhNaAgendu(Navrh navrh) {
        hlasovaciSystem.pridajNavrh(navrh);
    }
    /**
     * Zaháji hlasovanie pre konkrétny návrh.
     * @param vybranyNavrh Návrh, pre ktorý má byť hlasovanie zahájené.
     */
    public void zahajSedenie(Navrh vybranyNavrh) {
        hlasovaciSystem.zacniHlasovanie(vybranyNavrh);
    }
    /**
     * Ukončí hlasovanie pre konkrétny zákon a odstráni návrh z agendy.
     * @param lawName Názov zákona, pre ktorý má byť hlasovanie ukončené.
     * @throws NoVoteException Výnimka, ktorá sa vyvolá, ak hlasovanie nebolo úspešné alebo ak nie všetci účastníci hlasovali.
     */
    public void ukonciSedenie(String lawName) throws NoVoteException {
        hlasovaciSystem.ukonciHlasovanie(lawName);
        odstranNavrhNaAgende(lawName);
        System.out.println("Hlasovanie pre zákon '" + lawName + "' bolo ukončené.");
    }
    /**
     * Odstráni návrh zo zoznamu návrhov na agende.
     * @param lawName Názov zákona, ktorý má byť odstránený z agendy.
     */
    public void odstranNavrhNaAgende(String lawName) {
        hlasovaciSystem.odstranNavrh(lawName);
        System.out.println("Návrh '" + lawName + "' bol odstránený z agendy.");
    }
}
