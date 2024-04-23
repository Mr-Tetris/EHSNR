package fiit.stuba.sk.ehsnr;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.util.Arrays;

public class HlasovaciSystemGUI extends Application {
    private TextField txtNazovZakona, txtPocetHlasujucich, txtCasovyLimit;
    private TextArea txtInformacieZakona;
    private VotingController controller;

    @Override
    public void init() {
        SystemoveNastavenia nastavenia = new SystemoveNastavenia();
        HlasovaciSystem hlasovaciSystem = new HlasovaciSystem(nastavenia);
        VotingDialog votingDialog = new VotingDialog();
        controller = new VotingController(hlasovaciSystem, votingDialog);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Elektronický Hlasovací Systém");
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        txtNazovZakona = new TextField();
        txtInformacieZakona = new TextArea();
        txtPocetHlasujucich = new TextField();
        txtCasovyLimit = new TextField();
        Button btnZahaj = new Button("Zahájiť hlasovanie");
        btnZahaj.setOnAction(e -> handleStartVoting(primaryStage));
        Button btnUkoncit = new Button("Ukončiť hlasovanie");
        btnUkoncit.setOnAction(e -> controller.finalizeVoting(primaryStage));

        layout.getChildren().addAll(
                new Label("Názov zákona:"), txtNazovZakona,
                new Label("Informácie o zákone:"), txtInformacieZakona,
                new Label("Počet hlasujúcich:"), txtPocetHlasujucich,
                new Label("Časový limit (v sekundách):"), txtCasovyLimit,
                btnZahaj, btnUkoncit
        );

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleStartVoting(Stage stage) {
        String nazovZakona = txtNazovZakona.getText();
        String informacieZakona = txtInformacieZakona.getText();
        int pocetHlasujucich = Integer.parseInt(txtPocetHlasujucich.getText());
        int casovyLimit = Integer.parseInt(txtCasovyLimit.getText());
        controller.initiateVoting(stage, nazovZakona, informacieZakona, pocetHlasujucich, casovyLimit);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
