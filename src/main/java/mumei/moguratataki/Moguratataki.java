package mumei.moguratataki;

import mumei.moguratataki.Config.MoguraConfig;
import mumei.moguratataki.Game.GameControl;
import mumei.moguratataki.Listeners.GameListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Moguratataki extends JavaPlugin {
    private static Moguratataki plugin;
    private static MoguraConfig config;
    public static Commands commands;
    public static GameControl gameControl;


    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        config = new MoguraConfig(this);

        gameControl = new GameControl(10, 30);
        commands = new Commands();

        getServer().getPluginManager().registerEvents(new GameListener(),this);

        getLogger().info("もぐらたたきが有効になった。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("もぐらたたきが無効になった。");
    }

    public static Moguratataki getplugin() { return plugin; }
    public static MoguraConfig getMoguraConfig() { return config; }
    public static GameControl getGameControl() { return gameControl; }
}
