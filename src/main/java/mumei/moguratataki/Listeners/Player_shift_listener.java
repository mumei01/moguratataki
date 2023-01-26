package mumei.moguratataki.Listeners;

import mumei.moguratataki.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.Timer;
import java.util.TimerTask;


public class Player_shift_listener implements Listener {

    @EventHandler
    public void PlayerShiftevent(PlayerToggleSneakEvent e){

            Player player = e.getPlayer();
            //地面にったてるか
            if (((Entity)player).isOnGround()){

                Location loc = player.getLocation();
                loc.setY(loc.getY()-1);
                Block block = loc.getBlock();
                if (block.getType() == Material.PURPLE_WOOL){

                    //クールダウン処理
                    Timer timer = new Timer(false);
                    TimerTask task = new TimerTask() {

                        @Override
                        public void run() {
                            timer.cancel();
                        }
                    };
                    timer.schedule(task, 1000);
                    return;
                }
                player.teleport(loc);
                Utils.setIs_no_move(false);
            }









    }
}
