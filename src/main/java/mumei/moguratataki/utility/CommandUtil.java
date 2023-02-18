package mumei.moguratataki.utility;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CommandUtil {
    private static List<Command> commands = new ArrayList<>();

    public static void register(@Nonnull String fallbackPrefix, @Nonnull Command command) {
        Bukkit.getCommandMap().register(fallbackPrefix, command);
        commands.add(command);
    }

    public static void unregisterAll() {
        for (Command command : commands) {
            command.unregister(Bukkit.getCommandMap());
        }
        commands = new ArrayList<>();
    }
}
