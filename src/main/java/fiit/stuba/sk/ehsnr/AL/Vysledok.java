package fiit.stuba.sk.ehsnr.AL;

public class Vysledok {
    private int pocetZa;
    private int pocetProti;
    private int pocetZdrzaloSa;
    private boolean schvaleny;

    public Vysledok(int pocetZa, int pocetProti, int pocetZdrzaloSa) {
        this.pocetZa = pocetZa;
        this.pocetProti = pocetProti;
        this.pocetZdrzaloSa = pocetZdrzaloSa;
        this.schvaleny = false;
    }

    public void pripocitajZa() {
        pocetZa++; //pripočítanie hlasu za
        vyhodnot();
    }

    public void pripocitajProti() {
        pocetProti++; //pripočítanie hlasu proti
        vyhodnot();
    }

    public void pripocitajZdrzaloSa() {
        pocetZdrzaloSa++; //pripočítanie hlasu zdržal sa
        vyhodnot();
    }

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

    @Override
    public String toString() {
        return "Vysledok{" +
                "pocetZa=" + pocetZa +
                ", pocetProti=" + pocetProti +
                ", pocetZdrzaloSa=" + pocetZdrzaloSa +
                ", schvaleny=" + schvaleny +
                '}';
    } //metóda na výpis
}


