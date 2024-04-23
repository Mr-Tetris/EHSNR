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
        setupTimer(timerLabel, timeLimit);

        layout.getChildren().addAll(new Label("Informácie o zákone:"), infoArea, timerLabel);

        Scene scene = new Scene(layout, 300, 200);
        stage.setTitle("Hlasovanie o zákone: " + title);
        stage.setScene(scene);
        stage.show();
    }

    public void displayResults(String title, Map<String, Vysledok> results, Stage stage) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        results.forEach((law, result) -> {
            layout.getChildren().add(new Label(law + " výsledky: Za - " + result.getPocetZa() +
                    ", Proti - " + result.getPocetProti() + ", Zdržalo sa - " + result.getPocetZdrzaloSa()));
        });

        Scene scene = new Scene(layout, 300, 300);
        stage.setTitle("Výsledky hlasovania: " + title);
        stage.setScene(scene);
        stage.show();
    }

    private void setupTimer(Label timerLabel, int timeLimit) {
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
                }
            }
        };
        timer.start();
    }
}
