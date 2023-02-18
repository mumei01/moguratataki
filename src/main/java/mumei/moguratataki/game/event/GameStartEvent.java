package mumei.moguratataki.game.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final boolean pre;

    public GameStartEvent(boolean isPre) {
        this.pre = isPre;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public boolean isPre() {
        return pre;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}