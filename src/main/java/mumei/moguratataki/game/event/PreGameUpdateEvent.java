package mumei.moguratataki.game.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PreGameUpdateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final int time;

    public PreGameUpdateEvent(int time) {
        this.time = time;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public int getTime() {
        return time;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
