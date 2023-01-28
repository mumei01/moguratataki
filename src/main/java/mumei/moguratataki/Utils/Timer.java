package mumei.moguratataki.Utils;

import mumei.moguratataki.Moguratataki;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public final class Timer {
    private final int time;
    private int currentTime;
    private Runnable onUpdate = null, onEnd = null;

    private BukkitTask timerTask;


    public Timer(int time) {
        this.time = time;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setOnUpdate(Runnable onUpdate) {
        this.onUpdate = onUpdate;
    }

    public void setOnEnd(Runnable onEnd) {
        this.onEnd = onEnd;
    }

    public void start() {
        if (timerTask != null && !timerTask.isCancelled()) timerTask.cancel();
        currentTime = time;

        timerTask = new BukkitRunnable() {
            @Override
            public void run() {
                currentTime--;
                if (currentTime <= 0) {
                    this.cancel();
                    onEnd.run();
                    return;
                }
                onUpdate.run();
            }
        }.runTaskTimer(Moguratataki.getplugin(), 20, 20);
    }

    public void stop() {
        currentTime = 0;
    }
}
