package mumei.moguratataki.listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import mumei.moguratataki.Moguratataki;
import mumei.moguratataki.game.event.GameEndEvent;
import mumei.moguratataki.game.event.GameStartEvent;
import mumei.moguratataki.utility.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class GameListener implements Listener {
    @EventHandler
    public void onPlayerSpawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();

        if (!Moguratataki.getGameControl().isStarted()) return;
        if (!Moguratataki.MoguratatakiTeam.MOGURA.getTeam().hasPlayer(player)) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                player.setRemainingAir(player.getRemainingAir() - 5);
            }
        }.runTaskLater(Moguratataki.getplugin(), 20);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!Moguratataki.getGameControl().isStarted()) return;

        final Set<Player> leftMoguraPlayers = Moguratataki.MoguratatakiTeam.MOGURA.getTeam().getPlayers();

        if (leftMoguraPlayers.size() == 0) {
            Moguratataki.getGameControl().stop();
            Bukkit.broadcast(Component.text(("もぐらが全滅しました。プレイヤーの勝利です。")));
            Bukkit.getPluginManager().callEvent(new GameEndEvent(Moguratataki.MoguratatakiTeam.PLAYER.getTeam()));
            return;
        }

        Bukkit.broadcastMessage("残りのもぐらは" + leftMoguraPlayers.size() + "人です。");
    }
}
