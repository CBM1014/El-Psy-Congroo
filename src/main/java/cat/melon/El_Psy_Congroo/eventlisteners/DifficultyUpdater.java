package cat.melon.el_psy_congroo.eventlisteners;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.EntityPotionEffectEvent.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

import moe.kira.personal.PersonalAPI;

@SuppressWarnings("deprecation")
public class DifficultyUpdater implements Listener {
    private static final String CONFIG_KEY = "AGENDA_EL_PSY_CONGROO_DIFFICULTY_CONFIG_FOR_USERNAME_LENGTH_LIMIT_THIS_MUST_BE_SO_LONG_";
    //hhhh what's this↑
    private Location endMainIslandLocation = new Location(Bukkit.getWorld("world_the_end"), 0D, 68D, 0D);
    private final Random rand = ThreadLocalRandom.current();

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
                        event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 3);
                    } else {
                        switch (event.getDamager().getType()) {
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

    @EventHandler(ignoreCancelled = true)
    public void onRedstone(BlockRedstoneEvent event) {
        if (event.getBlock().getWorld().getEnvironment() == Environment.THE_END)
            event.setNewCurrent(rand.nextBoolean() ? 0 : event.getNewCurrent());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPrepare(PrepareItemEnchantEvent event) {
        if (event.getItem().getDurability() != 0)
            event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPrepare(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.ENDER_DRAGON)
            PersonalAPI.of(CONFIG_KEY).set("dragon_death", true);
    }

    @EventHandler
    public void onDragonRespwan(EnderDragonChangePhaseEvent event) {
        PersonalAPI.of(CONFIG_KEY).set("dragon_death", false);
    }

    @EventHandler
    public void onPhantomDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.PHANTOM)
            return;
        if (event.getEntity().getWorld().getEnvironment() != Environment.THE_END)
            return;

        event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.VEX);
    }

    @EventHandler
    public void antiFallDamageForPhantomAndVexes(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PHANTOM && event.getEntityType() != EntityType.VEX)
            return;
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
            event.setCancelled(true);
    }

    @EventHandler
    public void onDragonChangePhase(EnderDragonChangePhaseEvent event) {
        if (event.getNewPhase() == EnderDragon.Phase.LAND_ON_PORTAL) {
            Random ran = new Random();
            Location loc = event.getEntity().getLocation().clone();
            loc.setY(loc.getBlockY() + 8);
            for (int i = 0; i < 12; i++) {
                Location loc1 = loc.clone();
                loc1.setX(loc.getBlockY() + (ran.nextInt(10) - 5));
                loc1.setZ(loc.getBlockY() + (ran.nextInt(10) - 5));
                loc1.setY(loc.getBlockY() + (ran.nextInt(6) - 3));
                event.getEntity().getWorld().spawnEntity(loc1, EntityType.PHANTOM);
            }
        }
    }

    @EventHandler
    public void onPhantomAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PHANTOM)
            return;
        if (event.getDamager().getWorld().getName().equalsIgnoreCase("world_the_end"))
            return;

        /*boolean isNeatEnderCristal = false;
        for (Entity x : event.getDamager().getNearbyEntities(7.5, 7.5, 7.5)) {
            if (x.getType() == EntityType.ENDER_CRYSTAL) {
                isNeatEnderCristal = true;
            }
        }*/
        if (!event.getDamager().getLocation().getNearbyEntitiesByType(EnderCrystal.class, 7.5).isEmpty()) {
            TNTPrimed tnt = (TNTPrimed) event.getDamager().getWorld().spawnEntity(event.getDamager().getLocation(), EntityType.PRIMED_TNT);
            tnt.setFuseTicks(0);
        } else {
            if (event.getDamager().getLocation().getWorld().getName().equals(endMainIslandLocation.getWorld().getName())
                    && event.getDamager().getLocation().distance(endMainIslandLocation) > 300) {
                event.getDamager().getLocation().createExplosion(4.0F, true, true);
            }
            event.getDamager().getLocation().createExplosion(4.0F, true, false);
        }
        event.getDamager().remove();
    }

    @EventHandler
    public void onBedExplode(BlockExplodeEvent event) {
        if (isBed(event.getBlock().getType())){
           if(event.getBlock().getWorld().getEnvironment() == Environment.THE_END){
               Location loc = event.getBlock().getLocation().clone();
               for (int i = 0; i < 12; i++) {
                   Location loc1 = loc.clone();
                   loc1.setX(loc.getBlockY() + (rand.nextInt(10) - 5));
                   loc1.setZ(loc.getBlockY() + (rand.nextInt(10) - 5));
                   loc1.setY(loc.getBlockY() + (rand.nextInt(10) - 5));
                   event.getBlock().getWorld().spawnEntity(loc1, EntityType.PHANTOM);
               }
           }
        }
    }

    private boolean isBed(Material material) {
        switch (material) {
            case RED_BED:
            case BLUE_BED:
            case CYAN_BED:
            case GRAY_BED:
            case LIME_BED:
            case PINK_BED:
            case BLACK_BED:
            case BROWN_BED:
            case GREEN_BED:
            case WHITE_BED:
            case ORANGE_BED:
            case PURPLE_BED:
            case YELLOW_BED:
            case MAGENTA_BED:
            case LIGHT_BLUE_BED:
            case LIGHT_GRAY_BED:
                return true;
            default:
                return false;
        }
    }
}
