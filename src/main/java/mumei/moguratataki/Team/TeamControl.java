package mumei.moguratataki.Team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * チームを操作するAPI
 * @author HIYU1576
 */
public class TeamControl {

    /**
     * チーム設定を定義します
     */
    public enum TeamSetting {
        ALLOW_FRIENDLY_FIRE,
        CAN_SEE_FRIENDLY_INVISIBLES
    }

    /**
     * メインスコアボードを取得
     * @return メインスコアボード
     */
    @NotNull
    public Scoreboard getMainBoard() { return Bukkit.getScoreboardManager().getMainScoreboard(); }

    /**
     * チームを作成します
     * @param teamName
     * @return チームを作成できたか否か
     */
    public boolean createTeam(String teamName) {
        Scoreboard mainBoard = getMainBoard();
        if (mainBoard.getTeam(teamName) != null) return false;

        mainBoard.registerNewTeam(teamName);
        return true;
    }

    /**
     * チーム名からチームインスタンスを取得します
     * @param teamName
     * @return チームが存在する場合はチームインスタンス 存在しない場合は null を返します
     */
    public Team getTeam(String teamName) {
        Scoreboard mainBoard = getMainBoard();
        Team team = mainBoard.getTeam(teamName);
        return team;
    }

    /**
     * チームを削除します
     * @param teamName
     * @return チームを削除できたか否か
     */
    public boolean deleteTeam(String teamName) {
        Scoreboard mainBoard = getMainBoard();
        Team team = mainBoard.getTeam(teamName);
        if (team == null) return false;

        team.unregister();
        return true;
    }

    /**
     * チーム設定を変更します
     * @param teamName
     * @param teamSetting
     * @param value
     * @return チーム設定の変更を行えたか否か
     */
    public boolean modifyTeamSetting(String teamName, TeamSetting teamSetting, boolean value) {
        Team team = getTeam(teamName);
        if (team == null) return false;

        switch (teamSetting) {
            case ALLOW_FRIENDLY_FIRE: {
                if (team.allowFriendlyFire() == value) return false;
                team.setAllowFriendlyFire(value);
                break;
            }
            case CAN_SEE_FRIENDLY_INVISIBLES: {
                if (team.canSeeFriendlyInvisibles() == value) return false;
                team.setCanSeeFriendlyInvisibles(value);
                break;
            }
        }

        return true;
    }

    /**
     * チーム設定を取得します
     * @param teamName
     * @param teamSetting
     * @return 項目が有効か否か
     */
    public boolean getTeamSetting(String teamName, TeamSetting teamSetting) {
        Team team = getTeam(teamName);
        if (team == null) return false;

        switch (teamSetting) {
            case ALLOW_FRIENDLY_FIRE: { return team.allowFriendlyFire(); }
            case CAN_SEE_FRIENDLY_INVISIBLES: { return team.canSeeFriendlyInvisibles(); }
        }

        return false;
    }

    /**
     * チームのオプションを変更します
     * @param teamName
     * @param teamOption
     * @param teamOptionStatus
     * @return チームのオプションを変更できたか否か (既に同じ値の場合は false が返されます)
     */
    public boolean modifyTeamOption(String teamName, Team.Option teamOption, Team.OptionStatus teamOptionStatus) {
        Team team = getTeam(teamName);
        if (team == null || getTeamOption(teamName, teamOption) == teamOptionStatus) return false;

        team.setOption(teamOption, teamOptionStatus);
        return true;
    }

    /**
     * チームのオプションステータスを取得します
     * @param teamName
     * @param teamOption
     * @return チームオプションステータス
     */
    public Team.OptionStatus getTeamOption(String teamName, Team.Option teamOption) {
        Team team = getTeam(teamName);
        if (team == null) return null;
        return team.getOption(teamOption);
    }

    /**
     * プレイヤーをチームに所属させます
     * @param player
     * @param teamName
     * @return チームに所属させれたか否か
     */
    public boolean joinPlayerTeam(Player player, String teamName) {
        String playerName = player.getName();
        Team team = getTeam(teamName);
        if (team == null || team.hasEntry(playerName)) return false;

        team.addEntry(playerName);
        return true;
    }

    /**
     * エンティティをチームに所属させます
     * @param entity
     * @param teamName
     * @return チームに所属させれたか否か
     */
    public boolean joinEntityTeam(Entity entity, String teamName) {
        Team team = getTeam(teamName);
        if (team == null || team.hasEntity(entity)) return false;

        team.addEntity(entity);
        return true;
    }

    /**
     * プレイヤーをチームから脱退させます
     * @param player
     * @param teamName
     * @return チームから脱退させれたか否か
     */
    public boolean leavePlayerTeam(Player player, String teamName) {
        String playerName = player.getName();
        Team team = getTeam(teamName);
        if (team == null || !team.hasEntry(playerName)) return false;

        team.removeEntry(playerName);
        return true;
    }

    /**
     * エンティティをチームから脱退させます
     * @param entity
     * @param teamName
     * @return チームから脱退させれたか否か
     */
    public boolean leaveEntityTeam(Entity entity, String teamName) {
        Team team = getTeam(teamName);
        if (team == null || !team.hasEntity(entity)) return false;

        team.removeEntity(entity);
        return true;
    }

    /**
     * プレイヤーが所属するチームを取得します
     * @param player
     * @return チームインスタンス
     */
    public Team getPlayerTeam(Player player) { return getEntityTeam(player); }

    /**
     * エンティティが所属するチームを取得します
     * @param entity
     * @return チームインスタンス
     */
    public Team getEntityTeam(Entity entity) {
        Scoreboard mainBoard = getMainBoard();
        Team team = mainBoard.getEntityTeam(entity);

        return team;
    }

    /**
     * チームに所属しているプレイヤーを取得します
     * @param teamName
     * @return プレイヤー配列
     */
    public List<Player> getTeamPlayers(String teamName) {
        List<Entity> entities = getTeamEntities(teamName);
        if (entities == null) return null;

        Stream<Player> players = entities.stream().filter(e -> e instanceof Player).map(p -> (Player) p);
        return players.collect(Collectors.toList());
    }

    /**
     * チームに所属しているエンティティを取得します
     * @param teamName
     * @return エンティティ配列
     */
    public List<Entity> getTeamEntities(String teamName) {
        Team team = getTeam(teamName);
        if (team == null) return null;

        List<Entity> entities = team.getEntries()
                .stream()
                .map(m -> {
                    UUID uuid = parseUUID(m);
                    try {
                        return Bukkit.getEntity(uuid);
                    } catch (IllegalArgumentException e) {
                        return Bukkit.getPlayerExact(m);
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return entities;
    }

    /**
     * 文字配列をUUIDへ解析します
     * @param uuidString
     * @return UUIDインスタンス
     */
    private UUID parseUUID(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) { return null; }
    }


}
