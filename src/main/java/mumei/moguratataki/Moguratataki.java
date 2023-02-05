package mumei.moguratataki;

import mumei.moguratataki.Config.MoguraConfig;
import mumei.moguratataki.Game.GameControl;
import mumei.moguratataki.Listeners.GameListener;
import mumei.moguratataki.Team.Team;
import mumei.moguratataki.Utils.CommandUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

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

        gameControl = new GameControl(3, 30);
        commands = new Commands();

        for (MoguratatakiTeam team : MoguratatakiTeam.values()) {
            team.getTeam().setPrefix("[" + team.getDisplayName() + "]");
        }

        getServer().getPluginManager().registerEvents(new GameListener(),this);

        getLogger().info("もぐらたたきが有効になった。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        CommandUtil.unregisterAll();
        getLogger().info("もぐらたたきが無効になった。");
    }

    public enum MoguratatakiTeam {
        MOGURA("mogura", "もぐら"),
        PLAYER("player", "プレイヤー"),
        SPRC("sprc", "待機者");

        private final String name, displayName;

        MoguratatakiTeam(String name, String displayName) {
            this.name = name;
            this.displayName = displayName;
        }

        public String getName() {
            return name;
        }

        public String getDisplayName() {
            return displayName;
        }

        public Team getTeam() {
            return new Team(name);
        }
    }

    public static Moguratataki getplugin() { return plugin; }
    public static MoguraConfig getMoguraConfig() { return config; }
    public static GameControl getGameControl() { return gameControl; }
}
