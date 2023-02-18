package mumei.moguratataki.game;

import mumei.moguratataki.Moguratataki;
import mumei.moguratataki.game.event.GameEndEvent;
import mumei.moguratataki.game.event.GameStartEvent;
import mumei.moguratataki.game.event.GameUpdateEvent;
import mumei.moguratataki.game.event.PreGameUpdateEvent;
import mumei.moguratataki.listener.*;
import mumei.moguratataki.utility.Timer;
import mumei.moguratataki.utility.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;


public class GameControl {

    private boolean started, fixedStarted;
    private int preGameTime, gameTime;
    private Timer preGameTimer, gameTimer;

    public GameControl(int preGameTime, int gameTime) {
        if (preGameTime < 0) throw new IllegalArgumentException("ゲーム準備時間は0秒以上にする必要があるよ");
        if (gameTime < 0) throw new IllegalArgumentException("ゲーム時間は0秒以上にする必要があるよ");
        this.preGameTime = preGameTime;
        this.gameTime = gameTime;

        Util.registerEvents(
                Moguratataki.getplugin(),
                new GameEndListener(),
                new GameListener(),
                new GameUIListener(),
                new MoguraListener(),
                new PlayerListener(),
                new PreGameUIListener(),
                new SpectatorListener()
        );
    }

    public void start() {
        if (started) stop();
        preGameTimer = new Timer(preGameTime);
        preGameTimer.setOnUpdate(() -> {
            Bukkit.getPluginManager().callEvent(new PreGameUpdateEvent(preGameTimer.getCurrentTime()));
        });
        preGameTimer.setOnEnd(() -> {
            gameTimer.start();
            fixedStarted = true;
            Bukkit.getPluginManager().callEvent(new GameStartEvent(false));
        });
        preGameTimer.start();
        Bukkit.getPluginManager().callEvent(new GameStartEvent(true));

        gameTimer = new Timer(gameTime);
        gameTimer.setOnUpdate(() -> {
            Bukkit.getPluginManager().callEvent(new GameUpdateEvent(gameTimer.getCurrentTime()));
        });
        gameTimer.setOnEnd(() -> {
            if (!started) return;
            fixedStarted = false;
            started = false;
            Bukkit.getPluginManager().callEvent(new GameEndEvent(Moguratataki.MoguratatakiTeam.MOGURA.getTeam()));
        });

        started = true;
    }

    public void stop() {
        if (preGameTimer != null) preGameTimer.stop();
        if (gameTimer != null) gameTimer.stop();
        started = false;
        fixedStarted = false;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isFixedStarted() {
        return fixedStarted;
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
