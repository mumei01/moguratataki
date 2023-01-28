package mumei.moguratataki.Config;

import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public final class MoguraConfig extends ConfigFile {
    public MoguraConfig(Plugin plugin) {
        super(plugin, "config.yml");

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public <T> T getValue(@Nonnull Key key, @Nonnull Class<T> typeClass) {
        return getConfig().getObject("config." + key.name(), typeClass);
    }

    public <T> T getValue(@Nonnull Key key, @Nonnull Class<T> typeClass, T def) {
        return getConfig().getObject("config." + key.name(), typeClass, def);
    }

    public void setValue(@Nonnull Key key, @Nonnull Object value) {
        getConfig().set("config." + key.name(), value);
    }

    public enum Key {
        PRE_GAME_TIME,
        GAME_TIME,

        MOGURA_COOLDOWN_TICK
    }
}