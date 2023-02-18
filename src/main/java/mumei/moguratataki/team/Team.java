package mumei.moguratataki.team;

import mumei.moguratataki.Moguratataki;
import mumei.moguratataki.team.event.TeamJoinEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class Team {
    private final String teamName;

    public Team(@Nonnull String teamName) {
        this.teamName = teamName;
    }

    public void create() {
        getScoreboard().registerNewTeam(teamName);
    }

    public void remove() {
        getTeam().unregister();
    }

    public String getTeamName() {
        return teamName;
    }

    public void addPlayer(Player player) {
        getTeam().addPlayer(player);
        if (Moguratataki.getGameControl().isStarted())
            Bukkit.getPluginManager().callEvent(new TeamJoinEvent(this, player));
    }

    public Set<Player> getPlayers() {
        final Set<Player> players = new HashSet<>();
        for (OfflinePlayer player : getTeam().getPlayers()) {
            if (!(player instanceof Player)) continue;
            players.add((Player) player);
        }
        return players;
    }

    public void removePlayer(Player player) {
        getTeam().removePlayer(player);
    }

    public boolean hasPlayer(Player player) {
        return getTeam().hasPlayer(player);
    }

    public org.bukkit.scoreboard.Team getTeam() {
        final org.bukkit.scoreboard.Team team = getScoreboard().getTeam(teamName);
        if (team == null) {
            create();
            return getTeam();
        }
        return getScoreboard().getTeam(teamName);
    }

    public void setPrefix(String prefix) {
        getTeam().prefix(Component.text(prefix));
    }

    private Scoreboard getScoreboard() {
        return Bukkit.getScoreboardManager().getMainScoreboard();
    }
}
