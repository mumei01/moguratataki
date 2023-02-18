package mumei.moguratataki.game.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameUpdateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final int time;

    public GameUpdateEvent(int time) {
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
