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
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

import moe.kira.personal.PersonalAPI;

@SuppressWarnings("deprecation")
public class DifficultyUpdater implements Listener {
    private static final String CONFIG_KEY = "AGENDA_EL_PSY_CONGROO_DIFFICULTY_CONFIG_FOR_USERNAME_LENGTH_LIMIT_THIS_MUST_BE_SO_LONG_";
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            switch (event.getEntity().getWorld().getEnvironment()) {
                case NORMAL:
                    event.setDamage(event.getDamage() * 1.1);
                    event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 2);
                    break;
                case NETHER:
                    event.setDamage(event.getDamage() * 1.5);
                    event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 3);
                    break;
                case THE_END:
                    if (PersonalAPI.of(CONFIG_KEY).getBoolean("dragon_death", false)) {
                        event.setDamage(event.getDamage() * 2);
                        event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 4);
                    } else {
                        switch (event.getDamager().getType()) {
                            case SHULKER_BULLET:
                            case SHULKER:
                            case ENDERMITE:
                                event.setDamage(event.getDamage() * 10);
                                event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 10);
                            default:
                                event.setDamage(event.getDamage() * 2);
                                event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 4);
                        }
                    }
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
    
    @EventHandler(ignoreCancelled = true)
    public void onPrepare(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.ENDER_DRAGON)
            PersonalAPI.of(CONFIG_KEY).set("dragon_death", true);
    }
}
