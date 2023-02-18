package mumei.moguratataki.listener;

import mumei.moguratataki.Moguratataki;
import mumei.moguratataki.team.Team;
import mumei.moguratataki.team.event.TeamJoinEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SpectatorListener implements Listener {
    @EventHandler
    public void onTeamJoin(TeamJoinEvent event) {
        final Team team = event.getTeam();

        if (!team.equals(Moguratataki.MoguratatakiTeam.SPEC.getTeam())) return;
        final Player player = event.getPlayer();

        player.setGameMode(GameMode.SPECTATOR);
    }
}
