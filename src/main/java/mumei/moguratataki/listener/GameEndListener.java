package mumei.moguratataki.listener;

import mumei.moguratataki.Moguratataki;
import mumei.moguratataki.game.event.GameEndEvent;
import mumei.moguratataki.team.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

public final class GameEndListener implements Listener {
    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        final Team winnerTeam = event.getWinnerTeam();

        Bukkit.broadcast(Component.text("ゲームオーバー!!").color(TextColor.color(47, 149, 255)));
        if (winnerTeam == null) {
            // 引き分けの時
            Bukkit.broadcast(Component.text("勝ったチーム: 引き分けー").color(TextColor.color(226, 85, 74)));
        } else {
            // どちらかが勝利の時
            Bukkit.broadcast(Component.text("勝ったチーム: ").append(winnerTeam.getTeam().displayName()).color(TextColor.color(226, 85, 74)));
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            Moguratataki.MoguratatakiTeam.SPEC.getTeam().addPlayer(player);
            player.setGameMode(GameMode.ADVENTURE);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }
}
