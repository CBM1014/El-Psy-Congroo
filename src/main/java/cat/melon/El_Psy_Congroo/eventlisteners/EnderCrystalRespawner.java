package cat.melon.el_psy_congroo.eventlisteners;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.MelonLocationLite;
import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.sun.org.apache.regexp.internal.RE;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class EnderCrystalRespawner implements Listener {
    private Init instance;
    private Map<MelonLocationLite, Integer> deadCrystalMap = new HashMap<>();
    private final int RESPAWN_DELAY = 100;

    public EnderCrystalRespawner(Init instance) {
        this.instance = instance;
        new BukkitRunnable() {
            @Override
            public void run() {
                for (MelonLocationLite x : deadCrystalMap.keySet()) {
                    deadCrystalMap.put(x, deadCrystalMap.get(x) - 1);
                    if (deadCrystalMap.get(x) < 0) {
                        respawnEnderCrystal(x);
                        deadCrystalMap.remove(x);
                    }
                }
            }
        }.runTaskTimer(instance, 0, 1);
    }

    @EventHandler
    public void crystalKeeper(EntityDamageEvent event) {
        if (event.getEntity().getType() != EntityType.ENDER_CRYSTAL)
            return;
        if (!event.getEntity().getWorld().getName().equalsIgnoreCase("world_the_end"))
            return;
        EntityDamageEvent.DamageCause cause = event.getCause();
        //Bukkit.broadcastMessage("Cause:" + cause.name());
        if (cause != EntityDamageEvent.DamageCause.ENTITY_ATTACK && cause != EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK && cause != EntityDamageEvent.DamageCause.PROJECTILE) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void crystalKeeper(EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE)
            return;
        if (event.getDamager().getType() != EntityType.ARROW && event.getDamager().getType() != EntityType.TIPPED_ARROW)
            event.setCancelled(true);
    }


    @EventHandler
    public void crystalKeeper(EntityExplodeEvent event) {
        if (event.getEntityType() != EntityType.ENDER_CRYSTAL && event.getEntityType() != EntityType.PRIMED_TNT)
            return;
        if (!event.getEntity().getWorld().getName().equalsIgnoreCase("world_the_end"))
            return;
        if (event.getEntityType() == EntityType.PRIMED_TNT) {
            boolean hasEnderCrystal = false;
            Location loc = null;
            for (Entity x : event.getEntity().getLocation().getNearbyEntitiesByType(EnderCrystal.class, 8, 8, 8)) {
                if (x.getType() == EntityType.ENDER_CRYSTAL) {
                    loc = x.getLocation();
                    hasEnderCrystal = true;
                    break;
                }
            }
            if (hasEnderCrystal) {
                event.getEntity().remove();
                tryPutLocationinMap(loc);
            }
        } else {
            tryPutLocationinMap(event.getEntity().getLocation());
        }
    }

    @EventHandler
    public void crystalKeeper(BlockExplodeEvent event){
        boolean hasEnderCrystal = false;
        Location loc = null;
        for (Entity x : event.getBlock().getLocation().getNearbyEntitiesByType(EnderCrystal.class, 8, 8, 8)) {
            if (x.getType() == EntityType.ENDER_CRYSTAL) {
                loc = x.getLocation();
                hasEnderCrystal = true;
                break;
            }
        }
        if (hasEnderCrystal) {
            tryPutLocationinMap(loc);
        }
    }
    /* //It didn't work.
    public void crystalKeeper(EntityRemoveFromWorldEvent event){
        if(event.getEntityType()!=EntityType.ENDER_CRYSTAL)
            return;
        if(!event.getEntity().getWorld().getName().equalsIgnoreCase("world_the_end"))
            return;
        Location location = event.getEntity().getLocation();
        Location loczero = location.clone();
        loczero.setX(0); loczero.setY(0);
        if(location.distance(loczero)<15||location.distance(loczero)>100)
            return;
        deadCrystalMap.put(new MelonLocationLite(event.getEntity().getLocation()),RESPAWN_DELAY);
    }*/


    private void tryPutLocationinMap(Location location) {
        MelonLocationLite loc = new MelonLocationLite(location);
        MelonLocationLite loczero = loc.clone();
        loczero.setX(0);
        loczero.setY(0);
        double res = loc.distance(loczero);
        if (res < 15 || res > 100)
            return;
        Location bukkitloc = loc.getBukkitLocation();
        bukkitloc.setY(bukkitloc.getY()-1);
        if(bukkitloc.getWorld().getBlockAt(bukkitloc).getType()!= Material.BEDROCK)
            return;
        for(MelonLocationLite x:deadCrystalMap.keySet()){
            if(x.distance(loc)<1)
                return;
        }
        deadCrystalMap.put(loc, RESPAWN_DELAY);
    }

    private void respawnEnderCrystal(MelonLocationLite location) {
        Location loc = location.getBukkitLocation(0.5, 0, 0.5);
        loc.createExplosion(4F, true, true);
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getWorld("world_the_end").spawnEntity(loc, EntityType.ENDER_CRYSTAL);
            }
        }.runTaskLater(instance, 2);
    }


}
