package fiit.stuba.sk.ehsnr;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.animation.AnimationTimer;

import java.util.Map;

public class VotingDialog {
    private AnimationTimer timer;

    public void showVotingDialog(Stage stage, String title, String info, int timeLimit, VotingController controller) {
        VBox layout = new VBox(15);  // Zväčšenie vertikálneho odstupu
        layout.setAlignment(Pos.CENTER);

        TextArea infoArea = new TextArea(info);
        infoArea.setEditable(false);
        infoArea.setWrapText(true);  // Zapnutie zalamovania textu

        Label timerLabel = new Label("Zostávajúci čas: " + timeLimit + " sekúnd");
        Label voteResultLabel = new Label("");  // Label pre výsledok hlasovania

        setupTimer(timerLabel, timeLimit, stage, controller, title, voteResultLabel);

        Button btnVoteYes = new Button("Hlasovať za");
        Button btnVoteNo = new Button("Hlasovať proti");
        Button btnAbstain = new Button("Zdržať sa hlasovania");

        btnVoteYes.setOnAction(e -> {
            controller.recordVote("za", title, voteResultLabel);
        });
        btnVoteNo.setOnAction(e -> {
            controller.recordVote("proti", title, voteResultLabel);
        });
        btnAbstain.setOnAction(e -> {
            controller.recordVote("abstain", title, voteResultLabel);
        });

        layout.getChildren().addAll(
                new Label("Informácie o zákone:"), infoArea,
                timerLabel,
                new Separator(),  // Pridanie separátora pre lepšie vizuálne oddelenie
                btnVoteYes, btnVoteNo, btnAbstain,
                new Separator(),  // Ďalší separátor pred výsledkom hlasovania
                voteResultLabel
        );

        Scene scene = new Scene(layout, 400, 500);  // Zväčšenie výšky okna
        stage.setTitle("Hlasovanie o zákone: " + title);
        stage.setScene(scene);
        stage.show();
    }

    private void setupTimer(Label timerLabel, int timeLimit, Stage stage, VotingController controller, String lawName, Label voteResultLabel) {
        long endTime = System.currentTimeMillis() + timeLimit * 1000;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long remainingMillis = endTime - System.currentTimeMillis();
                if (remainingMillis > 0) {
                    long remainingSeconds = remainingMillis / 1000;
                    timerLabel.setText("Zostávajúci čas: " + remainingSeconds + " sekúnd");
                } else {
                    timerLabel.setText("Hlasovanie skončilo");
                    this.stop();
                    controller.finalizeVoting(lawName, voteResultLabel, (passed, vysledok) -> {
                        Platform.runLater(() -> {
                            displayResults(lawName, passed, vysledok, stage);
                        });
                    });
                }
            }
        };
        timer.start();
    }

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