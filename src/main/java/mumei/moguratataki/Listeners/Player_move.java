package mumei.moguratataki.Listeners;

import mumei.moguratataki.Utils.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Player_move implements Listener {
    @EventHandler
    public void Player_move_event(PlayerMoveEvent e){


        if (Util.getIs_no_move()){
            e.getPlayer().teleport(e.getFrom().setDirection(e.getTo().getDirection()));
        }
    }
}
