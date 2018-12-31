package cat.melon.El_Psy_Congroo.Commands;

import org.bukkit.command.CommandSender;

public interface Command {
    void run(CommandSender sender, String... labels);
}
