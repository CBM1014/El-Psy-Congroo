package cat.melon.el_psy_congroo.commands;

import org.bukkit.command.CommandSender;

public interface Command {
    void run(CommandSender sender, String... labels);
}
