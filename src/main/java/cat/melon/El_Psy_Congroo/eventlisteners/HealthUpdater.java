package cat.melon.el_psy_congroo.eventlisteners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HealthUpdater implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDamahe(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            event.setDamage(event.getDamage() * 1.2);
        }
    }
}
