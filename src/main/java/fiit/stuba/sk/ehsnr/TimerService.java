package fiit.stuba.sk.ehsnr;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TimerService {
    private AnimationTimer timer;
    private long startTime;
    private long endTime;
    private long remainingSeconds;

    public interface TimerCallback {
        void onTick(long remainingSeconds);
        void onFinish() throws NoVoteException;
    }

    private TimerCallback callback;

    // Spustenie časovača s daným trvaním a callbackom
    public void startTimer(int durationInSeconds, TimerCallback callback) {
        this.callback = callback;
        this.remainingSeconds = durationInSeconds;
        startTime = System.currentTimeMillis();
        endTime = startTime + durationInSeconds * 1000;

        if (timer != null) {
            timer.stop();  // Zastavíme predchádzajúci časovač ak existuje
        }

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedMillis = System.currentTimeMillis() - startTime;
                remainingSeconds = (endTime - System.currentTimeMillis()) / 1000;
                if (remainingSeconds <= 0) {
                    timer.stop();
                    remainingSeconds = 0;
                    try {
                        Platform.runLater(() -> {
                            if (callback != null) {
                                try {
                                    callback.onFinish();
                                } catch (NoVoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (callback != null) {
                        Platform.runLater(() -> callback.onTick(remainingSeconds));
                    }
                }
            }
        };
        timer.start();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
            remainingSeconds = 0;
        }
    }

    public void continueTimer() {
        if (timer != null && remainingSeconds > 0) {
            startTime = System.currentTimeMillis();
            endTime = startTime + remainingSeconds * 1000;
            timer.start();
        }
    }

    public long getRemainingSeconds() {
        return remainingSeconds;
    }
}
