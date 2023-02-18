package mumei.moguratataki.listener;

import mumei.moguratataki.Moguratataki;
import mumei.moguratataki.config.MoguraConfig;
import mumei.moguratataki.team.Team;
import mumei.moguratataki.team.event.TeamJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class SpectatorListener implements Listener {
    @EventHandler
    public void onTeamJoin(TeamJoinEvent event) {
        final Team team = event.getTeam();

        if (!team.getTeamName().equals(Moguratataki.MoguratatakiTeam.SPEC.getTeam().getTeamName())) return;
        final Player player = event.getPlayer();

        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(Moguratataki.getMoguraConfig().getValue(MoguraConfig.Key.SPECTATOR_INIT_LOCATION));
            }
        }.runTaskLater(Moguratataki.getplugin(), 10);
    }
}
