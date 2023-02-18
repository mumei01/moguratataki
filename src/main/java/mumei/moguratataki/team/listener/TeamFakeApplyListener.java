package mumei.moguratataki.team.listener;

import mumei.moguratataki.Moguratataki;
import mumei.moguratataki.game.event.GameStartEvent;
import mumei.moguratataki.team.event.TeamJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class TeamFakeApplyListener implements Listener {
    @EventHandler
    public void onGameStart(GameStartEvent event) {
        for (Moguratataki.MoguratatakiTeam team : Moguratataki.MoguratatakiTeam.values()) {
            for (Player player : team.getTeam().getPlayers()) {
                Bukkit.getPluginManager().callEvent(new TeamJoinEvent(team.getTeam(), player));
            }
        }
    }
}
