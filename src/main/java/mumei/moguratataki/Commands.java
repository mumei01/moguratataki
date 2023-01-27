package mumei.moguratataki;

import mumei.moguratataki.Game.GameControl;
import mumei.moguratataki.Team.Team;
import mumei.moguratataki.Utils.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Commands {
    public static JavaPlugin plugin = Moguratataki.getplugin();
    public static GameControl gameControl = Moguratataki.getGameControl();

    public Commands(){
        Bukkit.getCommandMap().register("moguratataki", new Team_add());
        Bukkit.getCommandMap().register("moguratataki", new Team_leave());
        Bukkit.getCommandMap().register("moguratataki", new Start());
        Bukkit.getCommandMap().register("moguratataki", new Stop());
        Bukkit.getCommandMap().register("moguratataki", new Team_tp());
    }

    private final static class Team_add extends Command {
        public Team_add(){
            super("tm_add");
        }
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
            if (!(sender.isOp())){
                sender.sendMessage("権限ありません。");
                return true;
            }

            //sender.sendMessage(args[0]+" "+args[1]+args[2]);
            Player targetPlayer = plugin.getServer().getPlayer(args[1]);

            switch (args[0]){
                case "mogura":
                    new Team("mogura").addPlayer(targetPlayer);
                    Bukkit.broadcastMessage(targetPlayer.getName()+"さんがもぐらになりました。");
                    return true;
                case "player":
                    new Team("player").addPlayer(targetPlayer);
                    Bukkit.broadcastMessage(targetPlayer.getName()+"さんがプレイヤーになりました。");
                    return true;
                case "spec":
                    new Team("spec").addPlayer(targetPlayer);
                    Bukkit.broadcastMessage(targetPlayer.getName()+"さんがスぺクになりました。");
                    return true;
                default:
                    sender.sendMessage("チーム又はプレイヤーが見つかりません。");
                    return true;
            }
        }
    }

    private final static class Team_leave extends Command {
        public Team_leave(){
            super("tm_leave");
        }
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
            if (!(sender.hasPermission("op"))){
                sender.sendMessage("権限ありません。");
                return true;
            }

            Player add_player = plugin.getServer().getPlayer(args[1]);

            switch (args[0]){
                case "mogura":
                    new Team("mogura").removePlayer(add_player);
                    Bukkit.broadcastMessage(add_player.getName()+"さんがもぐらではなくなりました。");
                    break;
                case "player":
                    new Team("player").removePlayer(add_player);
                    Bukkit.broadcastMessage(add_player.getName()+"さんがプレイヤーではなくなりました。");
                    break;
                case "spec":
                    new Team("spec").removePlayer(add_player);
                    Bukkit.broadcastMessage(add_player.getName()+"さんがスぺクではなくなりました。");
                    break;
                default:
                    sender.sendMessage("チーム又はプレイヤーが見つかりません。");
                    break;
            }
            return true;
        }
    }

    private final static class Start extends Command {
        public Start(){
            super("start");
            Set<Player> players;
        }
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
            Set<Player> players;
            if (!(sender.isOp())){
                sender.sendMessage("権限ありません。");
                return true;
            }
            gameControl.start();
            players = new Team("spec").getPlayers();
            for (Player player : players) {
                player.setGameMode(GameMode.SPECTATOR);
            }
            //ここ
            CustomItem item = new CustomItem(Material.STONE_AXE,"ハンマー");

            players = new Team("player").getPlayers();
            for (Player player : players) {
                player.sendMessage("プレイヤー");
                player.setItemInHand(item);
            }



            return true;
        }
    }

    private final static class Stop extends Command {
        public Stop(){
            super("stop");
        }
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

            if (!(sender.isOp())){
                sender.sendMessage("権限ありません。");
                return true;
            }
            gameControl.stop();
            return true;
        }
    }
    
    private final static class Team_tp extends Command {
        public Team_tp(){
            super("tm_tp");
        }
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
            if (!(sender.isOp())){
                sender.sendMessage("権限ありません。");
                return true;
            }

            Player tp_for = plugin.getServer().getPlayer(args[1]);

            
            //○○さんにtp
            if (args.length == 3){
                Location from_loc = plugin.getServer().getPlayer(args[2]).getLocation();
                Set<Player> players;
                switch (args[0]){
                    case "mogura":
                        players = new Team("mogura").getPlayers();
                        for (Player player : players) {
                            player.teleport(from_loc);
                        }
                        sender.sendMessage(tp_for.getName()+"さんにmoguraチームをtpさせました。");
                        break;
                    case "player":
                        players = new Team("player").getPlayers();
                        for (Player player : players) {
                            player.teleport(from_loc);
                        }
                        sender.sendMessage(tp_for.getName()+"さんにプレイヤーチームをtpさせました。");
                        break;
                    case "spec":
                        players = new Team("spec").getPlayers();
                        for (Player player : players) {
                            player.teleport(from_loc);
                        }
                        sender.sendMessage(tp_for.getName()+"さんにスペクチームをtpさせました。");
                        break;
                        
                    default:
                        sender.sendMessage("チーム又はプレイヤーが見つかりません。");
                        break;
                }
            }

            //senderにtp
            if (args.length == 2){
                Location from_loc = plugin.getServer().getPlayer(sender.getName()).getLocation();
                Set<Player> players;
                switch (args[0]){
                    case "mogura":
                        players = new Team("mogura").getPlayers();
                        for (Player player : players) {
                            player.teleport(from_loc);
                        }
                        sender.sendMessage(tp_for+"さんにmoguraチームをtpさせました。");
                        break;
                    case "player":
                        players = new Team("player").getPlayers();
                        for (Player player : players) {
                            player.teleport(from_loc);
                        }
                        sender.sendMessage(tp_for+"さんにプレイヤーチームをtpさせました。");
                        break;
                    case "spec":
                        players = new Team("spec").getPlayers();
                        for (Player player : players) {
                            player.teleport(from_loc);
                        }
                        sender.sendMessage(tp_for+"さんにスペクチームをtpさせました。");
                        break;

                    default:
                        sender.sendMessage("チーム又はプレイヤーが見つかりません。");
                        break;
                }
            }
            return true;
        }
    }


}
