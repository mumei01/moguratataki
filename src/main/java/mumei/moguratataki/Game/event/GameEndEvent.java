package mumei.moguratataki.Game.event;

import mumei.moguratataki.Team.Team;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class GameEndEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private final Team winnerTeam;

    public GameEndEvent(@Nonnull Team winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
