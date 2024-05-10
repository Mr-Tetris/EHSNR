package fiit.stuba.sk.ehsnr.AL;
/**
 * Trieda Vysledok reprezentuje výsledky hlasovania za konkrétny návrh alebo zákon. Uchováva počty hlasov za, proti a zdržaných sa.
 */
public class Vysledok {
    private int pocetZa;
    private int pocetProti;
    private int pocetZdrzaloSa;
    private boolean schvaleny;
    /**
     * Konštruktor pre objekt Vysledok, ktorý inicializuje počty hlasov za, proti a zdržaných sa na zadané hodnoty.
     * @param pocetZa Počet hlasov za návrh.
     * @param pocetProti Počet hlasov proti návrhu.
     * @param pocetZdrzaloSa Počet zdržaných sa hlasov.
     */
    public Vysledok(int pocetZa, int pocetProti, int pocetZdrzaloSa) {
        this.pocetZa = pocetZa;
        this.pocetProti = pocetProti;
        this.pocetZdrzaloSa = pocetZdrzaloSa;
        this.schvaleny = false;
    }
    /**
     * Zvýši počet hlasov za o jeden a vyhodnotí aktuálne výsledky hlasovania.
     */

    public void pripocitajZa() {
        pocetZa++; //pripočítanie hlasu za
        vyhodnot();
    }
    /**
     * Zvýši počet hlasov proti o jeden a vyhodnotí aktuálne výsledky hlasovania.
     */
    public void pripocitajProti() {
        pocetProti++; //pripočítanie hlasu proti
        vyhodnot();
    }
    /**
     * Zvýši počet zdržaných sa hlasov o jeden a vyhodnotí aktuálne výsledky hlasovania.
     */
    public void pripocitajZdrzaloSa() {
        pocetZdrzaloSa++; //pripočítanie hlasu zdržal sa
        vyhodnot();
    }
    /**
     * Vyhodnotí, či bol návrh schválený na základe aktuálneho počtu hlasov. Návrh je schválený, ak je počet hlasov za viac ako polovica všetkých platných hlasov.
     * @return Vráti true, ak je počet hlasov za väčší ako polovica všetkých platných hlasov; inak vráti false.
     */
    public boolean vyhodnot() {
        int celkovyPocet = pocetZa + pocetProti; //celkový počet hlasujúcich
        return celkovyPocet > 0 && pocetZa > celkovyPocet / 2; //ak bol zákon schválený
    }

    public int getPocetZa() {
        return pocetZa;
    }

    public int getPocetProti() {
        return pocetProti;
    }

    public int getPocetZdrzaloSa() {
        return pocetZdrzaloSa;
    }

    public boolean isSchvaleny() {
        return schvaleny;
    }
    /**
     * Vráti reťazcovú reprezentáciu objektu Vysledok, obsahujúcu počty hlasov za, proti a zdržaných sa.
     * @return Formátovaný reťazec reprezentujúci výsledky hlasovania.
     */
    @Override
    public String toString() {
        return "Vysledok{" +
                "pocetZa=" + pocetZa +
                ", pocetProti=" + pocetProti +
                ", pocetZdrzaloSa=" + pocetZdrzaloSa +
                ", schvaleny=" + schvaleny +
                '}';
    }
}


