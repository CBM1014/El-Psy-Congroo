package cat.melon.el_psy_congroo.eventlisteners;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import cat.melon.el_psy_congroo.Init;
import com.destroystokyo.paper.event.entity.EnderDragonFireballHitEvent;
import com.destroystokyo.paper.event.entity.EnderDragonShootFireballEvent;
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
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@SuppressWarnings("deprecation")
public class DifficultyUpdater implements Listener {
    Init instance;
    private Location endMainIslandLocation = new Location(Bukkit.getWorld("world_the_end"), 0D, 68D, 0D);
    private final Random rand = ThreadLocalRandom.current();
    //TODO  Compilation failure: no suitable method found for of(java.lang.String,java.lang.String) in line 56,95,100.

    public DifficultyUpdater(Init instance) {
        this.instance = instance;
    }

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
                    if (PersonalAPI.of("ElPsyCongroo", "dragon").getBoolean("dragon_death", false)) {
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
            PersonalAPI.of("ElPsyCongroo", "dragon").set("dragon_death", true);
    }

    @EventHandler
    public void onDragonRespwan(EnderDragonChangePhaseEvent event) {
        PersonalAPI.of("ElPsyCongroo", "dragon").set("dragon_death", false);
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
        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
            event.setCancelled(true);
    }

    @EventHandler
    public void onSkeletonShoot(EntityShootBowEvent event) {
        if (event.getEntityType() != EntityType.SKELETON)
            return;
        if ((event.getProjectile().getType() == EntityType.ARROW)) {
            Vector v = event.getProjectile().getVelocity();
            TippedArrow e = (TippedArrow) (event.getProjectile().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.TIPPED_ARROW));
            e.addCustomEffect(randomNegativeEffect(), false);
            e.setVelocity(v);
            event.getProjectile().remove();
        } else if ((event.getProjectile().getType() == EntityType.TIPPED_ARROW)) {
            ((TippedArrow) (event.getProjectile())).addCustomEffect(randomNegativeEffect(), false);
        }

    }

    @EventHandler
    public void onDragonFireballHit(EnderDragonFireballHitEvent event) {
        /*Collection<LivingEntity> collection = event.getTargets();
        boolean isHitAC = true;
        if (collection != null && !collection.isEmpty()) {
            for (LivingEntity x : collection) {
                if(x.getType()!=EntityType.AREA_EFFECT_CLOUD)
                    isHitAC=false;
            }
        }
        if(isHitAC){
            event.setCancelled(true);
            return;
        }*/

        event.getAreaEffectCloud().addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 0), false);
        event.getAreaEffectCloud().addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2), false);
        Collection<LivingEntity> col = event.getTargets();
        if (col != null && !col.isEmpty()) {
            for (LivingEntity x : col) {
                if (x.getType() == EntityType.PLAYER) {
                    Location loc = x.getLocation().clone();
                    loc.setY(loc.getY() + 3);
                    loc.setX(loc.getBlockY() + (rand.nextInt(10) - 5));
                    loc.setZ(loc.getBlockY() + (rand.nextInt(10) - 5));
                    x.getWorld().spawnEntity(loc, EntityType.VEX);
                    x.setFireTicks(200);
                }
            }
        }
    }

    @EventHandler
    public void onDragonFireballShoot(EnderDragonShootFireballEvent event) {
        for (int i = 0; i < 8; i++) {
            Location loc = event.getFireball().getLocation().clone();
            loc.setY(loc.getY() - rand.nextInt(10));
            loc.setX(loc.getBlockX() + (rand.nextInt(70) - 35));
            loc.setZ(loc.getBlockZ() + (rand.nextInt(70) - 35));
            DragonFireball fireball = (DragonFireball) event.getEntity().getWorld().spawnEntity(loc, EntityType.DRAGON_FIREBALL);
            fireball.setDirection(event.getFireball().getDirection());
        }
    }

    @EventHandler
    public void onDragonChangePhase(EnderDragonChangePhaseEvent event) {
        //silly mistake here.
        //before programming, please ensure that you can see XYZ clearly. It almost take me an hour to find the issue.
        //Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"[EnderDragonChangePhaseEvent]"+event.getCurrentPhase().name()+" -> "+event.getNewPhase().name());
        if (event.getNewPhase() == EnderDragon.Phase.SEARCH_FOR_BREATH_ATTACK_TARGET) {
            //Bukkit.broadcastMessage("run into on SEARCH_FOR_BREATH_ATTACK_TARGET");
            Location loc = event.getEntity().getLocation();
            for (int i = 0; i < 6; i++) {
                Location loc1 = loc.clone();
                loc1.setY(loc1.getY()+15);
                int ran = rand.nextInt(20);
                //Bukkit.broadcastMessage("random number: "+ran);
                //Bukkit.broadcastMessage(ChatColor.GOLD+loc1.toString());
                loc1.setX(loc1.getBlockX() + (ran - 10));
                loc1.setZ(loc1.getBlockZ() + (ran - 10));
                loc1.setY(loc1.getBlockY() + (ran - 10));
                //Bukkit.broadcastMessage(ChatColor.RED+loc1.toString());
                Entity pan = event.getEntity().getWorld().spawnEntity(loc1, EntityType.PHANTOM);
                //Bukkit.broadcastMessage("Entity "+pan.getType().name()+" "+pan.getUniqueId()+" spawned at: "+loc1.toString());
            }
        }
        if (event.getNewPhase() == EnderDragon.Phase.STRAFING) {
            //Bukkit.broadcastMessage("run into on STRAFING");
            Location loc = event.getEntity().getLocation();
            //Bukkit.broadcastMessage(loc.toString());
            for (int i = 0; i < 3; i++) {
                Location loc1 = loc.clone();
                loc1.setX(loc.getBlockX() + (rand.nextInt(60) - 30));
                loc1.setZ(loc.getBlockZ() + (rand.nextInt(60) - 30));
                loc1.setY(loc.getBlockY() + (rand.nextInt(10) - 5));
                Entity pan = event.getEntity().getWorld().spawnEntity(loc1, EntityType.PHANTOM);
            }
        }
    }

    @EventHandler
    public void noDamageTickCanceller(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            if (event.getCause() == EntityDamageEvent.DamageCause.LAVA||event.getCause()== EntityDamageEvent.DamageCause.FIRE)
                ((LivingEntity) (event.getEntity())).setNoDamageTicks(7);
            else
                if(event.getEntity() instanceof Player)
                Bukkit.broadcastMessage("DamageCause: "+event.getCause().name());
                ((LivingEntity) (event.getEntity())).setNoDamageTicks(0);
        }
    }

    @EventHandler
    public void onPhantomAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PHANTOM)
            return;
        if (!event.getDamager().getWorld().getName().equalsIgnoreCase("world_the_end"))
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
                event.getDamager().getLocation().createExplosion(2.2F, true, true);
            }
            event.getDamager().getLocation().createExplosion(2.2F, true, false);
        }
        event.getDamager().remove();
    }

    /*
    @EventHandler
    public void onFrameBreak(HangingBreakByEntityEvent event) {
        //Do nothing if not a player
        if (event.getEntity().getType() == EntityType.ITEM_FRAME) {
            if (event.getEntity().getWorld().getName().equalsIgnoreCase("world_the_end")) {
                if((((ItemFrame)(event.getEntity())).getItem().getType()==Material.ELYTRA)){
                    if (!PersonalAPI.of("ElPsyCongroo", "dragon").getBoolean("dragon_death", false)){
                        event.setCancelled(true);
                    }
                }
            }
        }

    }

    @EventHandler
    public  void  onElytraDrop(EntityDamageByEntityEvent event){
        if(event.getEntityType()!=EntityType.ITEM_FRAME)
            return;
        if(!PersonalAPI.of("ElPsyCongroo", "dragon").getBoolean("dragon_death", false)){

        }
    }*/

    @EventHandler
    public void onBedExplode(BlockExplodeEvent event) {
        //Bukkit.broadcastMessage(ChatColor.DARK_AQUA+"[BlockExplodeEvent] Block:"+event.getBlock().getType().name());
        //TODO event.getBlock().getType() is AIR here.
        if (event.getBlock().getType() == Material.AIR) {
            if (event.getBlock().getWorld().getEnvironment() == Environment.THE_END) {
                Location loc = event.getBlock().getLocation().clone();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 15; i++) {
                            Location loc1 = loc.clone();
                            loc1.setX(loc.getBlockX() + (rand.nextInt(10) - 5));
                            loc1.setZ(loc.getBlockZ() + (rand.nextInt(10) - 5));
                            loc1.setY(loc.getBlockY() + (rand.nextInt(10) - 5));
                            event.getBlock().getWorld().spawnEntity(loc1, EntityType.VEX);
                        }
                    }
                }.runTaskLater(instance, 1);
            }
        }
    }

    private PotionEffect randomNegativeEffect() {
        int i = rand.nextInt(5);
        PotionEffectType type = i == 0 ? PotionEffectType.BLINDNESS : i == 1 ? PotionEffectType.SLOW : i == 2 ? PotionEffectType.POISON : i == 3 ? PotionEffectType.HUNGER : i == 4 ? PotionEffectType.WEAKNESS : PotionEffectType.WITHER;
        return new PotionEffect(type, 30, 0, false, false, false);
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
