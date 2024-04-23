package fiit.stuba.sk.ehsnr;

import javafx.animation.AnimationTimer;

public class TimerService {
    private AnimationTimer timer;
    private long startTime;
    private int durationInSeconds;

    public void startTimer(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
        startTime = System.currentTimeMillis();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateTimer();
            }
        };
        timer.start();
    }

    private void updateTimer() {
        long elapsedMillis = System.currentTimeMillis() - startTime;
        long remainingSeconds = durationInSeconds - elapsedMillis / 1000;
        if (remainingSeconds <= 0) {
            timer.stop();
        }
        // Aktualizuj GUI prvkov časovača, ak je potrebné
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }
}
