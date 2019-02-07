package cat.melon.el_psy_congroo.eventlisteners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public class EnchantmentSet implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPrepare(PrepareItemEnchantEvent event) {
        if (event.getItem().getDurability() != event.getItem().getType().getMaxDurability())
            event.setCancelled(true);
    }
}
