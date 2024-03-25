package fiit.stuba.sk.ehsnr;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HlasovaciSystem {

    private static SystemoveNastavenia nastavenia = new SystemoveNastavenia();
    private static boolean isHlasovanieNastavene = false;
    private static boolean hlasovaniePozastavene = false;
    private static long startTime = 0;
    private static long endTime = 0;
    private static boolean uzivatelUzHlasoval = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nVitajte v Elektronickom Hlasovacom Systéme Národnej Rady!");
            System.out.println("1. Spustiť hlasovanie");
            System.out.println("2. Nastaviť hlasovanie");
            System.out.println("3. Ukončiť");

            System.out.print("Zadajte voľbu: ");
            String volba = scanner.nextLine();

            switch (volba) {
                case "1":
                    spustiHlasovanie(scanner);
                    break;
                case "2":
                    nastavHlasovanie(scanner);
                    break;
                case "3":
                    ukonci(scanner);
                    break;
                default:
                    System.out.println("Neplatná voľba, skúste znova.");
                    break;
            }
        }
    }

    private static void spustiHlasovanie(Scanner scanner) {
        if (!isHlasovanieNastavene) {
            System.out.println("Treba nastaviť hlasovanie!");
            return;
        }

        System.out.println("Názov zákona: " + nastavenia.getNazovZakona());
        System.out.println("Informácie o zákone:");
        String[] slova = nastavenia.getInformacieZakona().split(" ");
        for (int i = 0; i < slova.length; i++) {
            System.out.print(slova[i] + " ");
            if ((i + 1) % 20 == 0) System.out.println();
        }
        System.out.println("\nZačať hlasovanie? (ano/nie)");
        String start = scanner.nextLine();
        if (!"ano".equalsIgnoreCase(start)) {
            return;
        }

        System.out.println("\nHlasovanie sa začalo.");
        startTime = System.currentTimeMillis();
        endTime = startTime + nastavenia.getCasovyLimit() * 1000; // Prevod sekúnd na milisekundy

        while (System.currentTimeMillis() < endTime && !hlasovaniePozastavene) {
            zobrazMožnostiHlasovania(scanner);
            if (hlasovaniePozastavene) {
                pozastavHlasovanie(scanner);
            }
        }
        if (System.currentTimeMillis() >= endTime) {
            System.out.println("Hlasovanie skončilo.");
        }
    }

    private static void zobrazMožnostiHlasovania(Scanner scanner) {
        long remainingTime = endTime - System.currentTimeMillis();
        if (!uzivatelUzHlasoval) {
            System.out.println("Zostávajúci čas: " + TimeUnit.MILLISECONDS.toSeconds(remainingTime) + " sekúnd");
            System.out.println("1. Hlasovať za");
            System.out.println("2. Hlasovať proti");
            System.out.println("3. Zdržať sa hlasovania");
            System.out.println("4. Pozastaviť hlasovanie");
            System.out.print("Zadajte voľbu: ");
            String hlasovanieVolba = scanner.nextLine();
            switch (hlasovanieVolba) {
                case "1":
                    System.out.println("Ďakujeme za váš hlas.");
                    uzivatelUzHlasoval = true;
                    break;
                case "2":
                    System.out.println("Ďakujeme za váš hlas.");
                    uzivatelUzHlasoval = true;
                    break;
                case "3":
                    System.out.println("Ďakujeme za váš hlas.");
                    uzivatelUzHlasoval = true;
                    break;
                case "4":
                    hlasovaniePozastavene = true;
                    break;
                default:
                    System.out.println("Neplatná voľba, skúste znova.");
                    break;
            }
        } else {
            System.out.println("Ďakujeme za váš hlas. Musíte počkať do konca hlasovania.");
        }
        if (remainingTime > 0) {
            System.out.println("Zostávajúci čas: " + TimeUnit.MILLISECONDS.toSeconds(remainingTime) + " sekúnd");
        } else {
            System.out.println("Čas na hlasovanie vypršal.");
        }

    }

    private static void pozastavHlasovanie(Scanner scanner) {
        System.out.println("Hlasovanie pozastavené. Pre pokračovanie v hlasovaní stlačte 'p'.");
        String input = scanner.nextLine();
        while (!"p".equals(input)) {
            input = scanner.nextLine();
        }
        hlasovaniePozastavene = false;
        startTime = System.currentTimeMillis();
        endTime = startTime + TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
    }

    private static void ukonci(Scanner scanner) {
        System.out.println("Program bol ukončený.");
        System.exit(0);
    }

    private static void nastavHlasovanie(Scanner scanner) {
        System.out.println("Nastavenie hlasovania:");
        System.out.print("Počet hlasujúcich (10 - 200): ");
        int pocetHlasujucich = Integer.parseInt(scanner.nextLine());
        while (pocetHlasujucich < 10 || pocetHlasujucich > 200) {
            System.out.println("Neplatný počet hlasujúcich. Zadajte hodnotu v rozmedzí 10 - 200.");
            pocetHlasujucich = Integer.parseInt(scanner.nextLine());
        }
        nastavenia.setPocetHlasujucich(pocetHlasujucich);

        System.out.print("Názov zákona, o ktorom sa hlasuje: ");
        String nazovZakona = scanner.nextLine();
        nastavenia.setNazovZakona(nazovZakona);

        System.out.print("Informácie o zákone: ");
        String informacieZakona = scanner.nextLine();
        nastavenia.setInformacieZakona(informacieZakona);

        System.out.print("Časový limit na hlasovanie (v sekundách): ");
        int casovyLimit = Integer.parseInt(scanner.nextLine());
        nastavenia.setCasovyLimit(casovyLimit);

        isHlasovanieNastavene = true;
        System.out.println("Hlasovanie bolo nastavené.");
    }

}



