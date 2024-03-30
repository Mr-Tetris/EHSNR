package fiit.stuba.sk.ehsnr;

public class Vysledok {
    private int pocetZa;
    private int pocetProti;
    private int pocetZdrzaloSa;
    private boolean schvaleny;

    public Vysledok(int pocetZa, int pocetProti, int pocetZdrzaloSa) {
        this.pocetZa = pocetZa;
        this.pocetProti = pocetProti;
        this.pocetZdrzaloSa = pocetZdrzaloSa;
        this.schvaleny = vyhodnot();
    }

    private boolean vyhodnot() {
        // Schvaľuje návrh, ak počet hlasov "za" je viac ako 50% zo všetkých platných hlasov
        int celkovyPocet = pocetZa + pocetProti + pocetZdrzaloSa;
        return celkovyPocet > 0 && pocetZa > (celkovyPocet / 2);
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
}


