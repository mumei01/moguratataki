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

    private final Set<Player> outingPlayers = new HashSet<>();
    private final Set<Player> coolDownPlayers = new HashSet<>();

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event) {
        final Player player = event.getPlayer();

        if (!Moguratataki.getGameControl().isFixedStarted()) return;
        if (!Util.isInMoguraTeam(player)) return;
        if (outingPlayers.contains(player)) return;

        if (!player.getLocation().clone().add(0, -1, 0).getBlock().getType().equals(Material.PURPLE_WOOL)) return;
        final Block aboveBlock = Util.getAboveBlock(player.getLocation(), Material.GLASS);
        if (aboveBlock == null) return;

        final Location location = aboveBlock.getLocation().clone();

        location.add(0.5, 1, 0.5);
        location.setDirection(player.getLocation().getDirection());
        player.teleport(location);

        player.removePotionEffect(PotionEffectType.INVISIBILITY);

        outingPlayers.add(player);
        addCoolDownPlayer(player);
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        final Player player = event.getPlayer();

        if (!Moguratataki.getGameControl().isFixedStarted()) return;
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

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 100000, false, false, false));

        player.setRemainingAir(player.getRemainingAir() - 5);

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
    public void onGameStart(GameStartEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setMaximumAir(300);
        }
        for (Player player : Moguratataki.MoguratatakiTeam.MOGURA.getTeam().getPlayers()) {
            player.setRemainingAir(299);
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        for (Player player : Moguratataki.MoguratatakiTeam.MOGURA.getTeam().getPlayers()) {
            player.removePotionEffect(PotionEffectType.INVISIBILITY);

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
        if (!Moguratataki.getGameControl().isStarted()) return;
        if (!(event.getEntity() instanceof Player)) return;
        final Player player = (Player) event.getEntity();
        if (!Moguratataki.MoguratatakiTeam.MOGURA.getTeam().getPlayers().contains(player)) return;

        if (outingPlayers.contains(player)) {
            if (player.getRemainingAir() >= player.getMaximumAir()) return;
            event.setAmount(player.getRemainingAir() + 3);
            return;
        }
        if (player.getRemainingAir() < -20) {
            player.damage(2);
            return;
        }
        event.setAmount(player.getRemainingAir() - 2);
    }

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

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!Moguratataki.getGameControl().isStarted()) return;

        final Player eventPlayer = event.getPlayer();

        if (Moguratataki.MoguratatakiTeam.MOGURA.getTeam().hasPlayer(eventPlayer)) {
            eventPlayer.setGameMode(GameMode.SPECTATOR);

            if (outingPlayers.contains(eventPlayer)) {
                outingPlayers.remove(eventPlayer);
                final Player killer = eventPlayer.getKiller();
                event.deathMessage(Component.text(eventPlayer.getName() + "が" + (killer == null ? "" : killer.getDisplayName()) + "に殺されました"));
            } else {
                event.deathMessage(Component.text(eventPlayer.getName() + "が地中で溺れました"));
            }
            Moguratataki.MoguratatakiTeam.SPEC.getTeam().addPlayer(eventPlayer);

            final Set<Player> leftMoguraPlayers = Moguratataki.MoguratatakiTeam.MOGURA.getTeam().getPlayers();

            if (leftMoguraPlayers.size() <= 0) {
                Moguratataki.getGameControl().stop();
                Bukkit.broadcast(Component.text(("もぐらが全滅しました。プレイヤーの勝利です。")));
                Bukkit.getPluginManager().callEvent(new GameEndEvent(Moguratataki.MoguratatakiTeam.PLAYER.getTeam()));
                return;
            }

            Bukkit.broadcastMessage("残りのもぐらは" + leftMoguraPlayers.size() + "人です。");
        }

        outingPlayers.remove(eventPlayer);
    }
}
