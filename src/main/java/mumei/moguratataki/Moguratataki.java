package mumei.moguratataki;

import mumei.moguratataki.Listeners.Player_jump;
import mumei.moguratataki.Listeners.Player_move;
import mumei.moguratataki.Listeners.Player_shift_listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Moguratataki extends JavaPlugin {
    private static JavaPlugin plugin;
    public static Player_shift_listener pl_sh_listener;
    public static Player_jump pl_jp_ilstener;
    public static Player_move pl_move_listener;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        pl_jp_ilstener = new Player_jump();
        pl_sh_listener = new Player_shift_listener();
        pl_move_listener = new Player_move();

        getServer().getPluginManager().registerEvents(pl_sh_listener,this);
        getServer().getPluginManager().registerEvents(pl_jp_ilstener,this);
        getServer().getPluginManager().registerEvents(pl_move_listener,this);

        getLogger().info("もぐらたたきが有効になった。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("もぐらたたきが無効になった。");
    }

    public static JavaPlugin getplugin(){return plugin;}
}
