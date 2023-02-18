package mumei.moguratataki.game.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PreGameUpdateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public PreGameUpdateEvent() {
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
