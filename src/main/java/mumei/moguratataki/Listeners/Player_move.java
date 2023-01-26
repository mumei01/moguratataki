package mumei.moguratataki.Listeners;

import mumei.moguratataki.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Player_move implements Listener {
    @EventHandler
    public void Player_move_event(PlayerMoveEvent e){


        if (Utils.getIs_no_move()){
            e.getPlayer().teleport(e.getFrom().setDirection(e.getTo().getDirection()));
        }
    }
}
