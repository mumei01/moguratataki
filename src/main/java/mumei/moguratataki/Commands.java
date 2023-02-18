package mumei.moguratataki;

import mumei.moguratataki.game.GameControl;
import mumei.moguratataki.team.Team;
import mumei.moguratataki.utility.CommandUtil;
import mumei.moguratataki.utility.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Commands {
    public static JavaPlugin plugin = Moguratataki.getplugin();
    public static GameControl gameControl = Moguratataki.getGameControl();

    public Commands() {
        CommandUtil.register("moguratataki", new Start());
        CommandUtil.register("moguratataki", new Stop());
        CommandUtil.register("moguratataki", new Team_tp());
        CommandUtil.register("moguratataki", new Set_pretime());
        CommandUtil.register("moguratataki", new Set_time());
        CommandUtil.register("moguratataki", new Set_team());
    }


    private final static class Start extends Command {
        public Start() {
            super("start_game");
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
            Set<Player> players;
            if (!(sender.isOp())) {
                sender.sendMessage("権限ありません。");
                return true;
            }
            gameControl.start();

            //すぺくチームの処理
            players = new Team("spec").getPlayers();
            for (Player player : players) {
                player.setGameMode(GameMode.SPECTATOR);
            }

            //playerチームの処理
            CustomItem item = new CustomItem(Material.STONE_AXE, "ハンマー");
            item.setOnClick(event -> {


            });

            players = new Team("player").getPlayers();
            for (Player player : players) {
                player.sendMessage("プレイヤー");
                player.getInventory().setItemInMainHand(item.getAsItemStack());
                player.setGameMode(GameMode.ADVENTURE);
            }
            //もぐらチームの処理
            players = new Team("mogura").getPlayers();
            for (Player player : players) {
                player.setGameMode(GameMode.ADVENTURE);
            }


            return true;
        }
    }

    private final static class Stop extends Command {
        public Stop() {
            super("stop_game");
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

            if (!(sender.isOp())) {
                sender.sendMessage("権限ありません。");
                return true;
            }
            gameControl.stop();
            return true;
        }
    }

    private final static class Team_tp extends Command {
        public Team_tp() {
            super("tm_tp");
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
            if (!(sender.isOp())) {
                sender.sendMessage("権限ありません。");
                return true;
            }

            Player tp_for = plugin.getServer().getPlayer(args[1]);


            //○○さんにtp
            if (args.length == 3) {
                Location from_loc = plugin.getServer().getPlayer(args[2]).getLocation();
                Set<Player> players;
                switch (args[0]) {
                    case "mogura":
                        players = new Team("mogura").getPlayers();
                        for (Player player : players) {
                            player.teleport(from_loc);
                        }
                        sender.sendMessage(tp_for.getName() + "さんにmoguraチームをtpさせました。");
                        break;
                    case "player":
                        players = new Team("player").getPlayers();
                        for (Player player : players) {
                            player.teleport(from_loc);
                        }
                        sender.sendMessage(tp_for.getName() + "さんにプレイヤーチームをtpさせました。");
                        break;
                    case "spec":
                        players = new Team("spec").getPlayers();
                        for (Player player : players) {
                            player.teleport(from_loc);
                        }
                        sender.sendMessage(tp_for.getName() + "さんにスペクチームをtpさせました。");
                        break;

                    default:
                        sender.sendMessage("チーム又はプレイヤーが見つかりません。");
                        break;
                }
            }

            //senderにtp
            if (args.length == 2) {
                Location from_loc = plugin.getServer().getPlayer(sender.getName()).getLocation();
                Set<Player> players;
                switch (args[0]) {
                    case "mogura":
                        players = new Team("mogura").getPlayers();
                        for (Player player : players) {
                            player.teleport(from_loc);
                        }
                        sender.sendMessage(tp_for + "さんにmoguraチームをtpさせました。");
                        break;
                    case "player":
                        players = new Team("player").getPlayers();
                        for (Player player : players) {
                            player.teleport(from_loc);
                        }
                        sender.sendMessage(tp_for + "さんにプレイヤーチームをtpさせました。");
                        break;
                    case "spec":
                        players = new Team("spec").getPlayers();
                        for (Player player : players) {
                            player.teleport(from_loc);
                        }
                        sender.sendMessage(tp_for + "さんにスペクチームをtpさせました。");
                        break;

                    default:
                        sender.sendMessage("チーム又はプレイヤーが見つかりません。");
                        break;
                }
            }
            return true;
        }
    }

    private final static class Set_pretime extends Command {
        public Set_pretime() {
            super("set_pretime");
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
            if (!(sender.isOp())) {
                sender.sendMessage("権限ありません。");
                return true;
            }

            if (args.length == 1) {
                int value = Integer.parseInt(args[0]);
                gameControl.setPreGameTime(value);
                sender.sendMessage("プリタイムを" + value + "秒に設定しました。");
            } else {
                sender.sendMessage("プリタイムの設定に失敗しました。" + args.length);
            }


            return true;
        }
    }

    private final static class Set_time extends Command {
        public Set_time() {
            super("set_time");
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
            if (!(sender.isOp())) {
                sender.sendMessage("権限ありません。");
                return true;
            }

            if (args.length == 1) {
                int value = Integer.parseInt(args[0]);
                gameControl.setGameTime(value);
                sender.sendMessage("ゲーム時間を" + value + "秒に設定しました。");
            } else {
                sender.sendMessage("ゲーム時間の設定に失敗しました。");
            }
            return true;
        }
    }

    private final static class Set_team extends Command {
        private final List<String> teams = new ArrayList<>();

        public Set_team() {
            super("set_team");
            for (Moguratataki.MoguratatakiTeam team : Moguratataki.MoguratatakiTeam.values()) {
                teams.add(team.getName());
            }
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
            if (!(sender.isOp())) {
                sender.sendMessage("権限ありません。");
                return true;
            }
            if (args.length <= 2) {
                sender.sendMessage("コマンド例 /set_team <チーム名> <プレイヤー名>...");
                return true;
            }
            if (!teams.contains(args[0])) {
                sender.sendMessage("コマンド例 /set_team <チーム名> <プレイヤー名>...");
                sender.sendMessage("§c指定されたチームは存在しましせん");
                sender.sendMessage("チーム一覧 " + teams);
                return true;
            }
            final Team team = new Team(args[0]);
            for (int i = 0; i < args.length; i++) {
                if (i <= 1) continue;
                final Player player = Bukkit.getPlayer(args[i]);
                if (player == null) {
                    sender.sendMessage("\"" + args[i] + "\" のプレイヤーは見つかりませんでした");
                    continue;
                }
                team.addPlayer(player);
            }
            sender.sendMessage("適用されました");
            return true;
        }

        @Override
        public @Nonnull List<String> tabComplete(@Nonnull CommandSender sender, @Nonnull String alias, @Nonnull String[] args) throws IllegalArgumentException {
            final List<String> teams = new ArrayList<>();
            for (Moguratataki.MoguratatakiTeam team : Moguratataki.MoguratatakiTeam.values()) {
                teams.add(team.getName());
            }
            if (args.length <= 1) return teams;
            return super.tabComplete(sender, alias, args);
        }
    }
}
