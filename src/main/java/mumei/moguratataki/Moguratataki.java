package mumei.moguratataki;

import mumei.moguratataki.config.MoguraConfig;
import mumei.moguratataki.game.GameControl;
import mumei.moguratataki.listener.*;
import mumei.moguratataki.team.Team;
import mumei.moguratataki.team.TeamHandler;
import mumei.moguratataki.team.listener.TeamFakeApplyListener;
import mumei.moguratataki.utility.CommandUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class Moguratataki extends JavaPlugin {
    public static Commands commands;
    public static GameControl gameControl;
    private static Moguratataki plugin;
    private static MoguraConfig config;

    public static Moguratataki getplugin() {
        return plugin;
    }

    public static MoguraConfig getMoguraConfig() {
        return config;
    }

    public static GameControl getGameControl() {
        return gameControl;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        config = new MoguraConfig(this);

        gameControl = new GameControl(3, 30);
        new TeamHandler().initialize();
        commands = new Commands();

        for (MoguratatakiTeam team : MoguratatakiTeam.values()) {
            team.getTeam().setPrefix("[" + team.getDisplayName() + "]");
        }

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
        SPEC("spec", "待機者");

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
}
