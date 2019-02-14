package cat.melon.el_psy_congroo.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import moe.kira.personal.PersonalAPI;
import moe.kira.poi.Poi;

public class CommandLocale implements Command, PlayerOnly {
    @Override
    public void run(CommandSender sender, String... labels) {
        if (labels.length == 0 || !(sender instanceof Player))
            return;
        // Update locale setting
        YamlConfiguration personal = PersonalAPI.of(sender);
        personal.set("el_psy_congroo.locale", labels[0]);
        // Notify by playing sound
        Poi.get(personal).play((Player) sender);
    }
}
