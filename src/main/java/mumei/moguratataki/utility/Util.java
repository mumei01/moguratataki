package mumei.moguratataki.utility;

import mumei.moguratataki.Moguratataki;
import mumei.moguratataki.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Util {

    public static Boolean is_no_move = false;

    public static Boolean getIs_no_move() {
        return is_no_move;
    }

    public static void setIs_no_move(Boolean flag) {
        is_no_move = flag;
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
        return Moguratataki.MoguratatakiTeam.MOGURA.getTeam().getPlayers().contains(player);
    }

    public static boolean isInPlayerTeam(Player player) {
        return Moguratataki.MoguratatakiTeam.PLAYER.getTeam().getPlayers().contains(player);
    }

    public static boolean isInSpecTeam(Player player) {
        return Moguratataki.MoguratatakiTeam.SPEC.getTeam().getPlayers().contains(player);
    }

    public static void registerEvents(@Nonnull JavaPlugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }
}
