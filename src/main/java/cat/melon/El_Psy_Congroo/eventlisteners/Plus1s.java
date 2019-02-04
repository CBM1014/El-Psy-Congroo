package cat.melon.el_psy_congroo.eventlisteners;

import cat.melon.el_psy_congroo.Init;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Plus1s implements Listener {
    private Init instance;

    public Plus1s(Init instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerNearDeath(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            if (((Player) event.getEntity()).getHealth() - event.getFinalDamage() < 0) {
                if(!instance.getStatusManager().getPlayer(event.getEntity().getUniqueId()).isPlus1sMode()){
                    event.setCancelled(true);
                    ((Player) event.getEntity()).setHealth(0.01);
                    ((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 0, true), true);
                    ((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1, true), true);
                    ((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0, true), true);
                    instance.getStatusManager().getPlayer(event.getEntity().getUniqueId()).setPlus1sMode(true);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (instance.getStatusManager().getPlayer(event.getEntity().getUniqueId()).isPlus1sMode()) {
                                ((Player) event.getEntity()).damage(10000);
                            }
                            ((Player) event.getEntity()).removePotionEffect(PotionEffectType.ABSORPTION);
                        }
                    }.runTaskLater(instance, 200L);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        instance.getStatusManager().getPlayer(event.getEntity().getUniqueId()).setPlus1sMode(false);
    }

    @EventHandler
    public void onPlayerEatGoldenApple(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.GOLDEN_APPLE || event.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE)
            if (instance.getStatusManager().getPlayer(event.getPlayer().getUniqueId()).isPlus1sMode())
                removePlus1sMode(event.getPlayer());
    }

    private void removePlus1sMode(HumanEntity player){
        instance.getStatusManager().getPlayer(player.getUniqueId()).setPlus1sMode(false);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.removePotionEffect(PotionEffectType.ABSORPTION);
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.setHealth(player.getHealth()+1);
    }





    /*public boolean isFood(Material material) {
        switch (material) {
            case APPLE:
            case SOUL_SAND:
            case BREAD:
            case PORKCHOP:
            case COOKED_PORKCHOP:
            case GOLDEN_APPLE:
            case ENCHANTED_GOLDEN_APPLE:
            case COOKED_COD:
            case COD:
            case TROPICAL_FISH:
            case PUFFERFISH:
            case SALMON:
            case COOKED_SALMON:
            case COOKIE:
            case MELON_SLICE:
            case DRIED_KELP:
            case BEEF:
            case COOKED_BEEF:
            case CHICKEN:
            case COOKED_CHICKEN:
            case ROTTEN_FLESH:
            case SPIDER_EYE:
            case CARROT:
            case POTATO:
            case BAKED_POTATO:
            case POISONOUS_POTATO:
            case PUMPKIN_PIE:
            case RABBIT:
            case COOKED_RABBIT:
            case RABBIT_STEW:
            case MUTTON:
            case COOKED_MUTTON:
            case BEETROOT:
            case BEETROOT_SOUP:
                return true;
            default:
                return false;
        }
    }
    */
}
