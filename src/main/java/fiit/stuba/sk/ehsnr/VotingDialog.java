package fiit.stuba.sk.ehsnr;

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
                    controller.finalizeVoting(stage, lawName, voteResultLabel);
                    this.stop();
                }
            }
        };
        timer.start();
    }
    public void displayResults(String title, Map<String, Vysledok> results, Stage stage) {
        VBox layout = new VBox(10);  // Upravený vertikálny rozostup
        layout.setAlignment(Pos.CENTER);

        results.forEach((law, result) -> {
            // Vytvorenie kontajnera pre jednotlivé zákony
            VBox lawLayout = new VBox(5);
            lawLayout.setAlignment(Pos.CENTER);

            String status = result.isSchvaleny() ? "Schválený" : "Neschválený";
            Label lawLabel = new Label(law + " výsledky:");
            Label resultsLabel = new Label("Za - " + result.getPocetZa() +
                    ", Proti - " + result.getPocetProti() + ", Zdržalo sa - " + result.getPocetZdrzaloSa());
            Label statusLabel = new Label("Stav: " + status);

            // Pridanie prvkov do kontajnera pre zákon
            lawLayout.getChildren().addAll(lawLabel, resultsLabel, statusLabel);

            // Pridanie kontajnera do hlavného layoutu
            layout.getChildren().add(lawLayout);
            layout.getChildren().add(new Separator());  // Separátor medzi zákonmi
        });

        Scene scene = new Scene(layout, 400, 600);  // Upravené rozmery okna podľa potreby
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }



}

