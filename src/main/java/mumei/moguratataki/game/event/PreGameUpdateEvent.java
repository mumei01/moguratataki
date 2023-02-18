package mumei.moguratataki.game.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PreGameUpdateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public PreGameUpdateEvent() {
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
