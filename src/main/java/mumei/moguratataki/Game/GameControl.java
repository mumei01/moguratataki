package mumei.moguratataki.Game;

import mumei.moguratataki.Utils.Timer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;


public class GameControl {

    private boolean started;
    private int preGameTime, gameTime;
    private Timer preGameTimer, gameTimer;

    public GameControl(int preGameTime, int gameTime) {
        if (preGameTime < 0) throw new IllegalArgumentException("ゲーム準備時間は0秒以上にする必要があるよ");
        if (gameTime < 0) throw new IllegalArgumentException("ゲーム時間は0秒以上にする必要があるよ");
        this.preGameTime = preGameTime;
        this.gameTime = gameTime;
    }

    public void start() {
        if (started) stop();
        preGameTimer = new Timer(preGameTime);
        preGameTimer.setOnUpdate(() -> {
            Bukkit.broadcast(Component.text(preGameTimer.getCurrentTime() + "秒前"));
            Bukkit.getServer().showTitle(Title.title(Component.text(""), Component.text(preGameTimer.getCurrentTime() + "秒前")));
        });
        preGameTimer.setOnEnd(() -> {
            gameTimer.start();
        });
        preGameTimer.start();

        gameTimer = new Timer(gameTime);
        gameTimer.setOnUpdate(() -> {
            //ここに毎秒処理、


            Bukkit.getServer().sendActionBar(Component.text("⌚ " + gameTimer.getCurrentTime()));
        });
        gameTimer.setOnEnd(() -> {
            Bukkit.getPluginManager().callEvent(new GameEndEvent());
            started = false;
        });
        started = true;
    }

    public void stop() {
        if (preGameTimer != null) preGameTimer.stop();
        if (gameTimer != null) gameTimer.stop();
    }

    public boolean isStarted() {
        return started;
    }

    public void setPreGameTime(int preGameTime) {
        if (preGameTime < 0) throw new IllegalArgumentException("ゲーム準備時間は0秒以上にする必要があるよ");
        this.preGameTime = preGameTime;
    }

    public void setGameTime(int gameTime) {
        if (gameTime < 0) throw new IllegalArgumentException("ゲーム時間は0秒以上にする必要があるよ");
        this.gameTime = gameTime;
    }
}
