package fiit.stuba.sk.ehsnr;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        Scanner scanner = new Scanner(System.in);
=======
>>>>>>> b58ae528e91d20cd1af008ad374f4ea85fd969ba
        SystemoveNastavenia nastavenia = new SystemoveNastavenia();
        HlasovaciSystem hlasovaciSystem = new HlasovaciSystem(nastavenia);
        Sedenie sedenie = new Sedenie(hlasovaciSystem, nastavenia);

        launchGUI(nastavenia, sedenie);
    }

<<<<<<< HEAD
            switch (volba) {
                case "1":
                    System.out.print("Zadajte počet hlasujúcich (10 - 200): ");
                    int pocetHlasujucich = Integer.parseInt(scanner.nextLine());
                    while (pocetHlasujucich < 10 || pocetHlasujucich > 200) {
                        System.out.println("Neplatný počet hlasujúcich. Zadajte hodnotu v rozmedzí 10 - 200.");
                        pocetHlasujucich = Integer.parseInt(scanner.nextLine());
                    }
                    nastavenia.setPocetHlasujucich(pocetHlasujucich);

                    System.out.print("Zadajte názov zákona: ");
                    String nazovZakona = scanner.nextLine();
                    nastavenia.setNazovZakona(nazovZakona);

                    System.out.println("Zadajte informácie o zákone (pre ukončenie vložte prázdny riadok): ");
                    StringBuilder informacie = new StringBuilder();
                    String riadok;
                    while (!(riadok = scanner.nextLine()).isEmpty()) {
                        informacie.append(riadok).append("\n");
                    }
                    nastavenia.setInformacieZakona(informacie.toString());

                    System.out.print("Zadajte časový limit v sekundách (min. 20 sekúnd): ");
                    int casovyLimit = Integer.parseInt(scanner.nextLine());
                    while (casovyLimit < 20) {
                        System.out.println("Neplatný časový limit. Časový limit musí byť aspoň 20 sekúnd.");
                        casovyLimit = Integer.parseInt(scanner.nextLine());
                    }
                    nastavenia.setCasovyLimit(casovyLimit);


                    ZakonNavrh zakonNavrh = new ZakonNavrh(nazovZakona, informacie.toString());
                    sedenie.pridajNavrhNaAgendu(zakonNavrh);

                    System.out.println("Hlasovanie bolo nastavené.");
                    break;

                case "2":
                    if (sedenie.getNavrhyNaAgende().isEmpty()) {
                        System.out.println("Nie sú žiadne návrhy na agende.");
                        break;
                    }

                    System.out.println("Vyberte návrh, o ktorom chcete hlasovať:");
                    int index = 1;
                    for (Navrh navrh : sedenie.getNavrhyNaAgende()) {
                        System.out.println(index++ + ". " + navrh.getNazov());
                    }

                    int vyberIndex = Integer.parseInt(scanner.nextLine());
                    if (vyberIndex < 1 || vyberIndex > sedenie.getNavrhyNaAgende().size()) {
                        System.out.println("Neplatný výber.");
                        break;
                    }

                    Navrh vybranyNavrh = sedenie.getNavrhyNaAgende().get(vyberIndex - 1);
                    System.out.println("Návrh zákona: " + vybranyNavrh.getNazov());
                    System.out.println("Informácie o zákone: " + vybranyNavrh.getPopis());

                    sedenie.pridajNavrhNaAgendu(vybranyNavrh);
                    sedenie.zahajSedenie();

                    System.out.println("Hlasovanie sa začalo.");
                    System.out.println("1. Hlasovať za");
                    System.out.println("2. Hlasovať proti");
                    System.out.println("3. Zdržať sa hlasovania");
                    System.out.println("4. Pozastaviť hlasovanie");
                    System.out.print("Zadajte vašu voľbu: ");
                    String hlasovanieVolba = scanner.nextLine();


                    System.out.println("Pre ukončenie hlasovania stlačte 'u'.");
                    while (!scanner.nextLine().equalsIgnoreCase("u")) {
                        System.out.println("Neplatný príkaz. Pre ukončenie hlasovania stlačte 'u'.");
                    }
                    sedenie.ukonciSedenie();
                    break;
                case "3":
                    System.out.println("Program bol ukončený.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Neplatná voľba, skúste znova.");
                    break;
            }
        }
=======
    private static void launchGUI(SystemoveNastavenia nastavenia, Sedenie sedenie) {
        // Táto metóda by spustila GUI aplikáciu, ktorá by mala metódy na nastavenie a spustenie hlasovania
        // Tento kód predpokladá, že existuje GUI trieda, ktorá riadi logiku GUI a môže vyzerať napríklad takto:
        javafx.application.Application.launch(HlasovaciSystemGUI.class);
>>>>>>> b58ae528e91d20cd1af008ad374f4ea85fd969ba
    }
}
