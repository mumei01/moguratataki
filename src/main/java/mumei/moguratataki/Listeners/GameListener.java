package mumei.moguratataki.Listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import mumei.moguratataki.Game.GameEndEvent;
import mumei.moguratataki.Moguratataki;
import mumei.moguratataki.Team.Team;
import mumei.moguratataki.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class GameListener implements Listener {

    private final Set<Player> outingPlayers = new HashSet<>();
    private final Set<Player> coolDownPlayers = new HashSet<>();

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event) {
        final Player player = event.getPlayer();

        if (!Moguratataki.getGameControl().isStarted()) return;
        if (!Util.isInMoguraTeam(player)) return;
        if (outingPlayers.contains(player)) return;

        if (!player.getLocation().clone().add(0, -1, 0).getBlock().getType().equals(Material.PURPLE_WOOL)) return;
        final Block aboveBlock = Util.getAboveBlock(player.getLocation(), Material.GLASS);
        if (aboveBlock == null) return;

        final Location location = aboveBlock.getLocation().clone();

        location.add(0.5, 1, 0.5);
        location.setDirection(player.getLocation().getDirection());
        player.teleport(location);

        Util.showPlayerFrom(player, new Team("player").getPlayers());

        outingPlayers.add(player);
        addCoolDownPlayer(player);
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        final Player player = event.getPlayer();
        player.setRemainingAir(1);

        if (!Moguratataki.getGameControl().isStarted()) return;
        if (!event.isSneaking()) return;
        if (!Util.isInMoguraTeam(player)) return;
        if (!outingPlayers.contains(player)) return;
        if (isCoolDown(player)) return;

        final Block belowBlock = Util.getBelowBlock(player.getLocation(), Material.PURPLE_WOOL);
        if (belowBlock == null) return;

        final Location location = belowBlock.getLocation().clone();

        location.add(0.5, 1, 0.5);
        location.setDirection(player.getLocation().getDirection());
        player.teleport(location);

        Util.hidePlayerFrom(player, new Team("player").getPlayers());

        outingPlayers.remove(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        final Location location = event.getFrom().setDirection(event.getTo().getDirection());

        if (!Moguratataki.getGameControl().isStarted()) return;
        if (outingPlayers.contains(player)) event.setTo(location);
    }

    @EventHandler
    public void onGameEndEvent(GameEndEvent event) {
        for (Player player : new Team("mogura").getPlayers()) {
            Util.showPlayerFrom(player, new Team("player").getPlayers());

            if (!outingPlayers.contains(player)) return;
            final Block belowBlock = Util.getBelowBlock(player.getLocation(), Material.PURPLE_WOOL);
            if (belowBlock == null) return;

            final Location location = belowBlock.getLocation().clone();

            location.add(0.5, 1, 0.5);
            location.setDirection(player.getLocation().getDirection());
            player.teleport(location);

            outingPlayers.remove(player);
        }
    }

    @EventHandler
    public void onEntityAirChange(EntityAirChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        final Player player = (Player) event.getEntity();


    }

    private void addCoolDownPlayer(Player player) {
        coolDownPlayers.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                coolDownPlayers.remove(player);
            }
        }.runTaskLater(Moguratataki.getplugin(), 40);
    }

    private boolean isCoolDown(Player player) {
        return coolDownPlayers.contains(player);
    }
}
