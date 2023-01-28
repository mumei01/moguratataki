package mumei.moguratataki.Game;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GamePointHandler {
    private Map<Player, Integer> points = new HashMap<>();

    public GamePointHandler() {
    }

    public int getPoints(Player player) {
        return points.getOrDefault(player, 0);
    }

    public void setPoints(Player player, int point) {
        points.put(player, point);
    }

    public void reset() {
        points = new HashMap<>();
    }
}
