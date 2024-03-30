package fiit.stuba.sk.ehsnr;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SystemoveNastavenia nastavenia = new SystemoveNastavenia(); // Táto trieda by mala obsahovať predvolené hodnoty alebo metódy na ich nastavenie
        HlasovaciSystem hlasovaciSystem = new HlasovaciSystem(nastavenia);
        Sedenie sedenie = new Sedenie(hlasovaciSystem, scanner);

        while (true) {
            System.out.println("\nVitajte v Elektronickom Hlasovacom Systéme Národnej Rady!");
            System.out.println("1. Nastaviť hlasovanie");
            System.out.println("2. Spustiť hlasovanie");
            System.out.println("3. Ukončiť");
            System.out.print("Zadajte voľbu: ");
            String volba = scanner.nextLine();

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

                    // Po nastavení parametrov pridáme návrh zákona do agendy sedenia
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

                    // Pred hlasovaním je potrebné návrhy z agenda odstrániť alebo ich inak spracovať
                    sedenie.pridajNavrhNaAgendu(vybranyNavrh); // Pridanie vybraného návrhu späť do agendy, ak je to potrebné
                    sedenie.zahajSedenie(); // Spustenie hlasovania pre vybraný návrh

                    System.out.println("Hlasovanie sa začalo.");
                    System.out.println("1. Hlasovať za");
                    System.out.println("2. Hlasovať proti");
                    System.out.println("3. Zdržať sa hlasovania");
                    System.out.println("4. Pozastaviť hlasovanie");
                    System.out.print("Zadajte vašu voľbu: ");
                    String hlasovanieVolba = scanner.nextLine();
                    // Spracovanie volby užívateľa (toto bude závisieť na vašej implementácii HlasovaciSystem)

                    // Po spracovaní volby užívateľa:
                    System.out.println("Pre ukončenie hlasovania stlačte 'u'.");
                    while (!scanner.nextLine().equalsIgnoreCase("u")) {
                        System.out.println("Neplatný príkaz. Pre ukončenie hlasovania stlačte 'u'.");
                    }
                    sedenie.ukonciSedenie();
                    break;
                case "3":
                    System.out.println("Program bol ukončený.");
                    scanner.close();
                    return; // Ukončí program
                default:
                    System.out.println("Neplatná voľba, skúste znova.");
                    break;
            }
        }
    }
}

