package mumei.moguratataki.Game.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public GameStartEvent(){
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}