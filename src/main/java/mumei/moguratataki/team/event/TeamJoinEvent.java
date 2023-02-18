package mumei.moguratataki.team.event;

import mumei.moguratataki.team.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public class TeamJoinEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Team team;
    private final Player player;
    public TeamJoinEvent(@Nonnull Team team, @Nonnull Player player) {
        this.team = team;
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Team getTeam() {
        return team;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}