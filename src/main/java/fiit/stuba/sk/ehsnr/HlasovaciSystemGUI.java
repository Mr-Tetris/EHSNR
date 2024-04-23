package fiit.stuba.sk.ehsnr;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HlasovaciSystemGUI extends Application {
    private SystemoveNastavenia nastavenia;
    private Sedenie sedenie;
    private TextField txtNazovZakona;
    private TextArea txtInformacieZakona;
    private TextField txtPocetHlasujucich;
    private TextField txtCasovyLimit;

    @Override
    public void init() {
        nastavenia = new SystemoveNastavenia();
        HlasovaciSystem hlasovaciSystem = new HlasovaciSystem(nastavenia);
        sedenie = new Sedenie(hlasovaciSystem, nastavenia);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Elektronický Hlasovací Systém");
        VBox layout = new VBox(10);

        txtNazovZakona = new TextField();
        txtInformacieZakona = new TextArea();
        txtPocetHlasujucich = new TextField();
        txtCasovyLimit = new TextField();

        Button btnZahaj = new Button("Zahájiť hlasovanie");
        btnZahaj.setOnAction(e -> handleStartVoting());

        Button btnUkoncit = new Button("Ukončiť hlasovanie");
        btnUkoncit.setOnAction(e -> sedenie.ukonciSedenie());

        layout.getChildren().addAll(new Label("Názov zákona:"), txtNazovZakona,
                new Label("Informácie o zákone:"), txtInformacieZakona,
                new Label("Počet hlasujúcich:"), txtPocetHlasujucich,
                new Label("Časový limit (v sekundách):"), txtCasovyLimit,
                btnZahaj, btnUkoncit);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleStartVoting() {
        String nazovZakona = txtNazovZakona.getText();
        String informacieZakona = txtInformacieZakona.getText();
        int pocetHlasujucich = Integer.parseInt(txtPocetHlasujucich.getText());
        int casovyLimit = Integer.parseInt(txtCasovyLimit.getText());
        sedenie.zahajSedenie(nazovZakona, informacieZakona, pocetHlasujucich, casovyLimit);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
