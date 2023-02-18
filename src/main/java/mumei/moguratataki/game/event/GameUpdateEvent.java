package mumei.moguratataki.game.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameUpdateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public GameUpdateEvent() {
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
