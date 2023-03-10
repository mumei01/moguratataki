package mumei.moguratataki.listener;

import mumei.moguratataki.Moguratataki;
import mumei.moguratataki.config.MoguraConfig;
import mumei.moguratataki.team.Team;
import mumei.moguratataki.team.event.TeamJoinEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener {
    @EventHandler
    public void onTeamJoin(TeamJoinEvent event) {
        final Team team = event.getTeam();

        if (!team.equals(Moguratataki.MoguratatakiTeam.MOGURA.getTeam())) return;

        final Player player = event.getPlayer();

        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(Moguratataki.getMoguraConfig().getValue(MoguraConfig.Key.PLAYER_INIT_LOCATION));
    }
}
