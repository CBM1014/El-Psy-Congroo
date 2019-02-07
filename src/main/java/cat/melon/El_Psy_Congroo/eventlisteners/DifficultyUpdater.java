package cat.melon.el_psy_congroo.eventlisteners;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

@SuppressWarnings("deprecation")
public class DifficultyUpdater implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            switch (event.getEntity().getWorld().getEnvironment()) {
                case NORMAL:
                    event.setDamage(event.getDamage() * 1.1);
                    event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 3);
                    break;
                case NETHER:
                    event.setDamage(event.getDamage() * 1.5);
                    event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 3.5);
                    break;
                case THE_END:
                    event.setDamage(event.getDamage() * 3);
                    event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 5);
                    break;
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerPotion(EntityPotionEffectEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            if (event.getAction() == Action.ADDED || event.getAction() == Action.CHANGED)
                if (event.getCause() == Cause.ATTACK)
                    ((Player) event.getEntity()).addPotionEffect(event.getNewEffect().withDuration(event.getNewEffect().getDuration() * 2), true);
        }
    }
    
    private final Random rand = ThreadLocalRandom.current();
    
    @EventHandler(ignoreCancelled = true)
    public void onRedstone(BlockRedstoneEvent event) {
        if (event.getBlock().getWorld().getEnvironment() == Environment.THE_END)
            event.setNewCurrent(rand.nextBoolean() ? 0 : event.getNewCurrent());
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPrepare(PrepareItemEnchantEvent event) {
        if (event.getItem().getDurability() != event.getItem().getType().getMaxDurability())
            event.setCancelled(true);
    }
}
