package cat.melon.El_Psy_Congroo.Commands;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import moe.kira.personal.PersonalAPI;

public class CommandLocale implements Command, PlayerOnly {
    @Override
    public void run(CommandSender sender, String... labels) {
        if (labels.length == 0)
            return;
        // Update locale setting
        PersonalAPI.of(sender).set("el_psy_congroo.locale", labels[0]);
        // Notify by playing sound
        ((Player) sender).playSound(((Player) sender).getEyeLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 10F, 1F);
    }
}
