package fiit.stuba.sk.ehsnr;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.animation.AnimationTimer;

public class VotingDialog {
    public void displayResults(String lawName, boolean passed, Vysledok vysledok, Stage stage) {
        stage.close();  // Close the voting window

        Stage resultsStage = new Stage();
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        String resultText = passed ? "Zákon bol schválený." : "Zákon nebol schválený.";
        Label finalResultLabel = new Label(resultText);
        Label zaLabel = new Label("Počet hlasov ZA: " + vysledok.getPocetZa());
        Label protiLabel = new Label("Počet hlasov PROTI: " + vysledok.getPocetProti());
        Label zdrzalSaLabel = new Label("Počet zdržalo sa: " + vysledok.getPocetZdrzaloSa());

        layout.getChildren().addAll(finalResultLabel, zaLabel, protiLabel, zdrzalSaLabel);
        Scene scene = new Scene(layout, 350, 250);
        resultsStage.setTitle("Výsledky hlasovania o zákone: " + lawName);
        resultsStage.setScene(scene);
        resultsStage.show();
    }
}

