package mumei.moguratataki.Listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import mumei.moguratataki.Utils.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Player_jump implements Listener {
    @EventHandler
    public void Player_jump_Listener(PlayerJumpEvent e){
        Player player = e.getPlayer();
        Location loc = player.getLocation();
        loc.setY(loc.getY()-1);
        Block block = loc.getBlock();

        if (block.getType() == Material.PURPLE_WOOL){
            loc.setY(loc.getY()+4);
            player.teleport(loc);
            Util.setIs_no_move(true);
            
        }





    }
}
