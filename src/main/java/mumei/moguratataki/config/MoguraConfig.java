package mumei.moguratataki.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public final class MoguraConfig extends ConfigFile {
    public MoguraConfig(Plugin plugin) {
        super(plugin, "config.yml");

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public <T> T getValue(@Nonnull Key<T> key) {
        return (T) getConfig().getObject("config." + key.getName(), Object.class, key.getDefault());
    }

    public <T> void setValue(@Nonnull Key<T> key, @Nonnull T value) {
        getConfig().set("config." + key.getName(), value);
    }

    public interface Key<T> {
        Key<Integer> PRE_GAME_TIME = new Key<Integer>() {
            @Override
            public String getName() {
                return "pre-game-time";
            }

            @Override
            public Integer getDefault() {
                return 3;
            }
        };
        Key<Integer> GAME_TIME = new Key<Integer>() {
            @Override
            public String getName() {
                return "game-time";
            }

            @Override
            public Integer getDefault() {
                return 30;
            }
        };
        Key<Integer> MOGURA_COOLDOWN_TICK = new Key<Integer>() {
            @Override
            public String getName() {
                return "mogura-cooldown-tick";
            }

            @Override
            public Integer getDefault() {
                return 40;
            }
        };
        Key<Location> MOGURA_INIT_LOCATION = new Key<Location>() {
            @Override
            public String getName() {
                return "mogura-init-location";
            }

            @Override
            public Location getDefault() {
                return new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
            }
        };
        Key<Location> PLAYER_INIT_LOCATION = new Key<Location>() {
            @Override
            public String getName() {
                return "PLAYER-init-location";
            }

            @Override
            public Location getDefault() {
                return new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
            }
        };
        Key<Location> SPECTATOR_INIT_LOCATION = new Key<Location>() {
            @Override
            public String getName() {
                return "spectator-init-location";
            }

            @Override
            public Location getDefault() {
                return new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
            }
        };

        String getName();

        T getDefault();
    }
}