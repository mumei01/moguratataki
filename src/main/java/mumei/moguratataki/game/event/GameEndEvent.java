package mumei.moguratataki.game.event;

import mumei.moguratataki.team.Team;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nullable;

public class GameEndEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Team winnerTeam;

    public GameEndEvent(@Nullable Team winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Nullable
    public Team getWinnerTeam() {
        return winnerTeam;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
