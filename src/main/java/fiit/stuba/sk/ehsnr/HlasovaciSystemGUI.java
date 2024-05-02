package fiit.stuba.sk.ehsnr;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;


import java.util.List;
import java.util.Optional;

public class HlasovaciSystemGUI extends Application {
    private Sedenie sedenie;
    private SystemoveNastavenia nastavenia;
    private HlasovaciSystemController controller;
    private Button btnStartVoting; // Tlačidlo teraz ako atribút triedy
    private TimerService timerService;
    private VotingController votingController;
    private VBox resultsLayout;

    @Override
    public void init() {
        nastavenia = new SystemoveNastavenia();
        HlasovaciSystem hlasovaciSystem = new HlasovaciSystem(nastavenia);
        sedenie = new Sedenie(hlasovaciSystem, nastavenia);
        VotingDialog votingDialog = new VotingDialog();
        votingController = new VotingController(hlasovaciSystem, votingDialog, this);
        controller = new HlasovaciSystemController(sedenie, hlasovaciSystem, this, votingController);  // Predajte VotingController
        timerService = new TimerService();
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Elektronický Hlasovací Systém Národnej Rady");

        Label welcomeLabel = new Label("Vitajte v Elektronickom Hlasovacom Systéme Národnej Rady");
        Button btnAddProposal = new Button("Pridať návrh");
        Button btnSettings = new Button("Nastavenia");
        Button btnExit = new Button("Ukončiť");

        btnStartVoting = new Button("Začať hlasovanie");
        btnStartVoting.setDisable(true);
        checkInitialVotingSetup();
        btnStartVoting.setOnAction(e -> showVotingOptions(primaryStage));
        btnAddProposal.setOnAction(e -> {
            Stage newStage = new Stage(); // Vytvorí nový Stage ak je potrebné
            handleAddProposal(newStage); // Prenesie existujúcu alebo novú scénu
            newStage.show(); // Zobrazí okno, ak je to nové
        });


        btnSettings.setOnAction(e -> handleSettings(primaryStage));  // Pridávame primaryStage pre možné použitie v nastaveniach
        btnExit.setOnAction(e -> primaryStage.close());

        VBox layout = new VBox(10, welcomeLabel, btnStartVoting, btnAddProposal, btnSettings, btnExit);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        checkVotingEligibility();  // Skontrolujte, či sú splnené požiadavky pre začatie hlasovania
    }

    private void checkVotingEligibility() {
        boolean isEligible = nastavenia.getPocetHlasujucich() > 0 && nastavenia.getCasovyLimit() > 0 && !controller.getNavrhyNaAgendu().isEmpty();
        btnStartVoting.setDisable(!isEligible);
    }


    private void handleSettings(Stage parentStage) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Nastavenia Hlasovania");
        dialog.setHeaderText("Nastavte parametre hlasovania");

        ButtonType saveButton = new ButtonType("Uložiť", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        TextField pocetField = new TextField();
        pocetField.setPromptText("Počet hlasujúcich");
        TextField casField = new TextField();
        casField.setPromptText("Časový limit (sekundy)");

        grid.add(new Label("Počet hlasujúcich:"), 0, 0);
        grid.add(pocetField, 1, 0);
        grid.add(new Label("Časový limit (sekundy):"), 0, 1);
        grid.add(casField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                return new Pair<>(pocetField.getText(), casField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(settings -> {
            int pocet = Integer.parseInt(settings.getKey());
            int cas = Integer.parseInt(settings.getValue());
            nastavenia.setPocetHlasujucich(pocet);
            nastavenia.setCasovyLimit(cas);
            checkVotingEligibility();  // Aktualizácia stavu tlačidla po zatvorení nastavení
        });
    }


    public void handleAddProposal(Stage currentStage) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Pridanie Návrhu");
        dialog.setHeaderText("Zadajte názov a informácie o novom návrhu");

        ButtonType addButton = new ButtonType("Pridať", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Zrušiť", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        TextField nazovField = new TextField();
        TextArea infoArea = new TextArea();

        grid.add(new Label("Názov zákona:"), 0, 0);
        grid.add(nazovField, 1, 0);
        grid.add(new Label("Informácie o zákone:"), 0, 1);
        grid.add(infoArea, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                return new Pair<>(nazovField.getText(), infoArea.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(nazovInfoPair -> {
            ZakonNavrh novyNavrh = new ZakonNavrh(nazovInfoPair.getKey(), nazovInfoPair.getValue());
            controller.pridajNavrh(novyNavrh);
            updateResultsDisplay();  // Aktualizácia zobrazenia výsledkov
            showConfirmationDialog(currentStage, "Návrh bol úspešne pridaný.");  // Zobrazí potvrdzovacie okno
        });

        if (!result.isPresent()) {
            currentStage.close();  // Zatvorenie aktuálneho okna, ak sa stlačí cancel alebo sa okno zatvorí
        }
    }


    public void showConfirmationDialog(Stage stage, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Potvrdenie");
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType closeButton = new ButtonType("Zavrieť");
        alert.getButtonTypes().setAll(closeButton);

        alert.showAndWait();
    }
    private void checkInitialVotingSetup() {
        // Kontrola, či sú k dispozícii všetky potrebné informácie na spustenie hlasovania
        boolean isReadyToStartVoting = !controller.getNavrhyNaAgendu().isEmpty() &&
                nastavenia.getPocetHlasujucich() > 0 &&
                nastavenia.getCasovyLimit() > 0;
        btnStartVoting.setDisable(!isReadyToStartVoting);
    }

    public void showVotingInterface(Navrh vybranyNavrh) {
        // Zavolanie metódy na začatie hlasovania
        controller.zacniHlasovanie(vybranyNavrh);  // Toto začne hlasovanie a nastaví isHlasovanieBezi na true

        Stage stage = new Stage();
        stage.setTitle("Hlasovanie: " + vybranyNavrh.getNazov());

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label infoLabel = new Label(vybranyNavrh.getPopis());
        Label casLabel = new Label("Zostáva: 0 sekúnd");  // Inicializácia s prednastavenou hodnotou
        Label resultLabel = new Label();

        Button btnVoteFor = new Button("Hlasovať ZA");
        Button btnVoteAgainst = new Button("Hlasovať PROTI");
        Button btnAbstain = new Button("Zdržať sa hlasovania");

        // Inicializácia a spustenie TimerService, ak je potrebné
        if (timerService != null) {
            timerService.startTimer(nastavenia.getCasovyLimit(), new TimerService.TimerCallback() {
                @Override
                public void onTick(long remainingSeconds) {
                    Platform.runLater(() -> casLabel.setText("Zostáva: " + remainingSeconds + " sekúnd"));
                }

                @Override
                public void onFinish() {
                    Platform.runLater(() -> {
                        casLabel.setText("Čas na hlasovanie vypršal");
                        disableVotingButtons(btnVoteFor, btnVoteAgainst, btnAbstain);
                        // Zavoláme finalizeVoting na controlleri, ktorý už sa postará o zvyšok logiky
                        controller.finalizeVoting(vybranyNavrh.getNazov(), resultLabel);
                    });
                }
            });
        } else {
            casLabel.setText("Chyba: Časovač nie je dostupný");
        }

        btnVoteFor.setOnAction(event -> controller.voteFor(vybranyNavrh, resultLabel, timerService));
        btnVoteAgainst.setOnAction(event -> controller.voteAgainst(vybranyNavrh, resultLabel, timerService));
        btnAbstain.setOnAction(event -> controller.abstain(vybranyNavrh, resultLabel, timerService));

        layout.getChildren().addAll(infoLabel, casLabel, btnVoteFor, btnVoteAgainst, btnAbstain, resultLabel);

        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
        stage.showAndWait();  // Zobrazenie okna a čakanie na zatvorenie
    }




    // Pomocná metóda na zakázanie tlačidiel na hlasovanie
    private void disableVotingButtons(Button... buttons) {
        for (Button button : buttons) {
            button.setDisable(true);
        }
    }






    private void showVotingOptions(Stage primaryStage) {
        List<Navrh> navrhy = controller.getNavrhyNaAgendu();
        if (navrhy.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Nie sú k dispozícii žiadne návrhy na hlasovanie.").showAndWait();
            return;
        }

        Dialog<Navrh> dialog = new Dialog<>();
        dialog.setTitle("Vyberte Návrh na Hlasovanie");
        dialog.setHeaderText("Vyberte návrh na hlasovanie:");

        ListView<Navrh> listView = new ListView<>();
        listView.getItems().addAll(navrhy);
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Navrh item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getNazov());
            }
        });

        dialog.getDialogPane().setContent(listView);

        ButtonType okButtonType = new ButtonType("Začať Hlasovanie", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType ->
                buttonType == okButtonType ? listView.getSelectionModel().getSelectedItem() : null
        );

        Optional<Navrh> result = dialog.showAndWait();
        result.ifPresent(navrh -> {
            primaryStage.close(); // Zatvorte hlavné okno pred zobrazením hlasovacieho rozhrania
            showVotingInterface(navrh); // Tu sa zavolá metóda pre zobrazenie hlasovacieho rozhrania
        });
    }


    public void showResultsWindow(String lawName, boolean passed, Vysledok vysledok, Stage resultsStage) {
        resultsLayout = new VBox(10);
        resultsLayout.setAlignment(Pos.CENTER);

        String resultText = passed ? "Zákon bol schválený." : "Zákon nebol schválený.";
        Label finalResultLabel = new Label(resultText);
        Label zaLabel = new Label("Počet hlasov ZA: " + vysledok.getPocetZa());
        Label protiLabel = new Label("Počet hlasov PROTI: " + vysledok.getPocetProti());
        Label zdrzalSaLabel = new Label("Počet zdržalo sa: " + vysledok.getPocetZdrzaloSa());

        resultsLayout.getChildren().addAll(finalResultLabel, zaLabel, protiLabel, zdrzalSaLabel);

        Button continueVotingButton = new Button("Pokračovať v ďalšom hlasovaní");
        Button createNewProposalButton = new Button("Vytvoriť nový návrh");
        Button exitButton = new Button("Ukončiť");

        // Akcia pre pokračovanie v hlasovaní
        continueVotingButton.setOnAction(e -> {
            resultsStage.close();
            showVotingOptions(new Stage());
        });

        // Akcia pre vytvorenie nového návrhu
        createNewProposalButton.setOnAction(e -> {
            Stage newStage = new Stage(); // Vytvorí nový Stage ak je potrebné
            handleAddProposal(newStage); // Prenesie existujúcu alebo novú scénu
            newStage.show();  // Metóda na spracovanie a zobrazenie formulára pre nový návrh
        });

        // Akcia pre ukončenie aplikácie
        exitButton.setOnAction(e -> {
            Platform.exit();
        });

        // Skontrolujeme, či sú ešte nejaké návrhy na hlasovanie
        continueVotingButton.setDisable(controller.getNavrhyNaAgendu().isEmpty());

        // Pridanie tlačítok do layoutu
        resultsLayout.getChildren().addAll(continueVotingButton, createNewProposalButton, exitButton);

        Scene scene = new Scene(resultsLayout, 350, 250);
        resultsStage.setTitle("Výsledky hlasovania o zákone: " + lawName);
        resultsStage.setScene(scene);
        resultsStage.show();
    }

    public void updateResultsDisplay() {
        if (resultsLayout != null) {
            resultsLayout.getChildren().clear();

            Label finalResultLabel = new Label("Prehľad výsledkov:");
            resultsLayout.getChildren().add(finalResultLabel);

            Button continueVotingButton = new Button("Pokračovať v ďalšom hlasovaní");
            continueVotingButton.setDisable(controller.getNavrhyNaAgendu().isEmpty());
            continueVotingButton.setOnAction(e -> {
                resultsLayout.getScene().getWindow().hide();
                showVotingOptions(new Stage());
            });

            Button createNewProposalButton = new Button("Vytvoriť nový návrh");
            createNewProposalButton.setOnAction(e -> {
                Stage stage = (Stage) resultsLayout.getScene().getWindow();
                handleAddProposal(stage);
            });

            Button exitButton = new Button("Ukončiť");
            exitButton.setOnAction(e -> Platform.exit());

            resultsLayout.getChildren().addAll(continueVotingButton, createNewProposalButton, exitButton);

            // Kontrola, či je možné zapnúť tlačidlo na spustenie hlasovania
            checkInitialVotingSetup(); // Znova skontroluje, či sú splnené všetky podmienky pre spustenie hlasovania
        } else {
            System.out.println("Výsledkový layout nebol inicializovaný.");
        }
    }











    public static void main(String[] args) {
        launch(args);
    }
}
