package mumei.moguratataki.listener;

import mumei.moguratataki.game.event.PreGameUpdateEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class PreGameUIListener implements Listener {
    @EventHandler
    public void onPreGameUpdate(PreGameUpdateEvent event) {
        Bukkit.broadcast(Component.text(event.getTime() + "秒前"));
        Bukkit.getServer().showTitle(Title.title(Component.text(""), Component.text(event.getTime() + "秒前")));
    }
}
