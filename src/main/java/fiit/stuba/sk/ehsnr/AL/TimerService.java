package fiit.stuba.sk.ehsnr.AL;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
/**
 * TimeService poskytuje funkčnosť časovača pre hlasovací systém, umožňujúc sledovanie časového limitu pre hlasovanie a spúšťanie akcií na základe uplynutia času.
 */
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
    /**
     * Spustí časovač s danou dĺžkou trvania a callback funkciou, ktorá sa vykoná pri každom tiknutí a na konci časovača.
     * @param durationInSeconds Doba trvania časovača v sekundách.
     * @param callback Callback funkcia, ktorá obsahuje metódy onTick a onFinish pre spracovanie tiknutí a ukončenia časovača.
     */
    public void startTimer(int durationInSeconds, TimerCallback callback) {
        this.callback = callback;
        this.remainingSeconds = durationInSeconds;
        startTime = System.currentTimeMillis(); // Aktuálny čas v milisekundách
        endTime = startTime + durationInSeconds * 1000; // Koncový čas v milisekundách

        if (timer != null) {
            timer.stop();  // Zastavíme predchádzajúci časovač ak existuje
        }

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedMillis = System.currentTimeMillis() - startTime; // Uplynutý čas v milisekundách
                remainingSeconds = (endTime - System.currentTimeMillis()) / 1000; // Zostávajúci čas v sekundách
                if (remainingSeconds <= 0) { // Ak už uplynul celý čas
                    timer.stop();
                    remainingSeconds = 0;
                    try { // Spustíme callback onFinish
                        Platform.runLater(() -> {
                            if (callback != null) {
                                try {
                                    callback.onFinish();
                                } catch (NoVoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e) { // Ak sa nepodarí spustiť callback onFinish
                        e.printStackTrace();
                    }
                } else { // Ak ešte neuplynul celý čas
                    if (callback != null) {
                        Platform.runLater(() -> callback.onTick(remainingSeconds)); // Spustíme callback onTick
                    }
                }
            }
        };
        timer.start();
    }

    /**
     * Pokračuje v časovaní od posledného zaznamenaného času, ak časovač ešte nevypršal.
     */
    public void continueTimer() { // Pokračovanie časovača
        if (timer != null && remainingSeconds > 0) {
            startTime = System.currentTimeMillis();
            endTime = startTime + remainingSeconds * 1000;
            timer.start();
        }
    }

}
