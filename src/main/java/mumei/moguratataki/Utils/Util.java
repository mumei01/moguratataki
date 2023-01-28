package mumei.moguratataki.Utils;

import mumei.moguratataki.Moguratataki;
import mumei.moguratataki.Team.Team;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Util {

    public static Boolean is_no_move = false;

    public static Boolean getIs_no_move(){return is_no_move;}
    public static void setIs_no_move(Boolean flag){is_no_move = flag;}

    public static void hidePlayerFrom(Player targetPlayer, Collection<Player> from) {
        for (Player player : from) {
            player.hidePlayer(Moguratataki.getplugin(), targetPlayer);
        }
    }

    public static void showPlayerFrom(Player targetPlayer, Collection<Player> from) {
        for (Player player : from) {
            player.showPlayer(Moguratataki.getplugin(), targetPlayer);
        }
    }

    @Nullable
    public static Block getAboveBlock(Location location, Material blockType) {
        while (!location.getBlock().getType().equals(blockType) && location.getBlockY() < 319) {
            location.add(0, 1, 0);
        }

        return location.getBlock().getType().equals(blockType) ? location.getBlock() : null;
    }

    @Nullable
    public static Block getBelowBlock(Location location, Material blockType) {
        while (!location.getBlock().getType().equals(blockType) && location.getBlockY() > -64) {
            location.add(0, -1, 0);
        }

        return location.getBlock().getType().equals(blockType) ? location.getBlock() : null;
    }

    public static boolean isInMoguraTeam(Player player) {
        return new Team("mogura").getPlayers().contains(player);
    }

    public static boolean isInPlayerTeam(Player player) {
        return new Team("player").getPlayers().contains(player);
    }

    public static boolean isInSpecTeam(Player player) {
        return new Team("spec").getPlayers().contains(player);
    }
}
