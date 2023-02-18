package mumei.moguratataki.listener;

import mumei.moguratataki.game.event.GameUpdateEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameUIListener implements Listener {
    @EventHandler
    public void onGameUpdate(GameUpdateEvent event) {
        Bukkit.getServer().sendActionBar(Component.text("⌚ " + event.getTime()));
    }
}
