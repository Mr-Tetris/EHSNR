package fiit.stuba.sk.ehsnr;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;

public class TimerService {
    private AnimationTimer timer;
    private long startTime;
    private long endTime;
    private long remainingSeconds;

    // Interface pre callback, ktorý umožňuje aktualizáciu GUI a spracovanie výsledkov
    public interface TimerCallback {
        void onTick(long remainingSeconds);
        void onFinish();
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
                    // Ak vypršal čas, zastavíme časovač a vyvoláme onFinish
                    timer.stop();
                    remainingSeconds = 0; // Uistíme sa, že zostávajúce sekundy nie sú záporné
                    if (callback != null) {
                        Platform.runLater(callback::onFinish);
                    }
                } else {
                    // Inak pokračujeme v aktualizácii
                    if (callback != null) {
                        Platform.runLater(() -> callback.onTick(remainingSeconds));
                    }
                }
            }
        };
        timer.start();
    }

    // Metóda na pokračovanie časovača bez reštartovania
    public void continueTimer() {
        if (timer != null && remainingSeconds > 0) {
            // Ak timer beží a čas ešte nevypršal, len pokračujeme
            startTime = System.currentTimeMillis();
            endTime = startTime + remainingSeconds * 1000;
            timer.start();
        }
    }

    // Zastavenie časovača
    public void stopTimer() {
        if (timer != null) {
            timer.stop();
            remainingSeconds = 0;
        }
    }

    // Získanie zostávajúcich sekúnd
    public long getRemainingSeconds() {
        return remainingSeconds;
    }
}
