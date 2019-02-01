package cat.melon.El_Psy_Congroo.EventListeners;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class HealthUpdater implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        AttributeInstance maxhealth = ((Attributable) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH);
        switch (event.getEntityType()) {
            case SHEEP:
            case COW:
            case MUSHROOM_COW:
            case PIG:
                maxhealth.setBaseValue(20D);
            case ZOMBIE:
            case SKELETON:
            case CREEPER:
            case VILLAGER:
            case ZOMBIE_VILLAGER:
            case PIG_ZOMBIE:
                maxhealth.setBaseValue(30D);
            default:
                maxhealth.setBaseValue(((Attributable) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 1.35D);
        }
    }

    @EventHandler
    public void onPlayerDamahe(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            event.setDamage(event.getDamage() * 1.1);
        }
    }
}
