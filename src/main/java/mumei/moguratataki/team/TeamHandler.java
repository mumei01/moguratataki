package mumei.moguratataki.team;

import mumei.moguratataki.Moguratataki;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scoreboard.Team;

public final class TeamHandler {
    public TeamHandler() {
    }

    public void initialize() {
        final Team mogura = Moguratataki.MoguratatakiTeam.MOGURA.getTeam().getTeam();
        mogura.setAllowFriendlyFire(false);
        mogura.setCanSeeFriendlyInvisibles(true);
        mogura.color(NamedTextColor.RED);
        mogura.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.FOR_OTHER_TEAMS);
        mogura.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);

        final Team player = Moguratataki.MoguratatakiTeam.SPEC.getTeam().getTeam();
        player.setAllowFriendlyFire(false);
        mogura.setCanSeeFriendlyInvisibles(true);
        mogura.color(NamedTextColor.AQUA);
        player.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.FOR_OTHER_TEAMS);
        player.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);

        final Team spec = Moguratataki.MoguratatakiTeam.SPEC.getTeam().getTeam();
        spec.setAllowFriendlyFire(false);
        mogura.setCanSeeFriendlyInvisibles(true);
        mogura.color(NamedTextColor.GRAY);
        spec.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.FOR_OTHER_TEAMS);
        spec.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
    }
}