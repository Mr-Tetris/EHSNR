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
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        TextArea infoArea = new TextArea(info);
        infoArea.setEditable(false);

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

        layout.getChildren().addAll(new Label("Informácie o zákone:"), infoArea, timerLabel, btnVoteYes, btnVoteNo, btnAbstain, voteResultLabel);

        Scene scene = new Scene(layout, 400, 400);
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
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        results.forEach((law, result) -> {
            String status = result.isSchvaleny() ? "Schválený" : "Neschválený";
            layout.getChildren().add(new Label(law + " výsledky: Za - " + result.getPocetZa() +
                    ", Proti - " + result.getPocetProti() + ", Zdržalo sa - " + result.getPocetZdrzaloSa() +
                    ". Stav: " + status));
        });

        Scene scene = new Scene(layout, 300, 300);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }


}

