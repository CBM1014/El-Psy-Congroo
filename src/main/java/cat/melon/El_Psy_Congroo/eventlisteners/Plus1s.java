package cat.melon.el_psy_congroo.eventlisteners;

import cat.melon.el_psy_congroo.Init;
import moe.kira.personal.PersonalAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.WeatherType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.dscalzi.skychanger.bukkit.api.SkyChanger;

public class Plus1s implements Listener {
    private Init instance;

    public Plus1s(Init instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerNearDeath(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = ((Player) event.getEntity());
            if (player.getHealth() - event.getFinalDamage() < 0) {
                if (!instance.getStatusManager().getPlayer(event.getEntity().getUniqueId()).isPlus1sMode()) {
                    event.setCancelled(true);
                    player.setHealth(0.01);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 0, true), true);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1, true), true);
                    
                    boolean skyChanger = PersonalAPI.of(player).getBoolean("recreator.weather.sandstorm.enable", true);
                    if (skyChanger) {
                        player.setPlayerTime(1000, false);
                        player.setPlayerWeather(WeatherType.CLEAR);
                        SkyChanger.getAPI().changeSky(player, 5F);
                    } else {
                        //((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0, true), true);
                    }
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0, true), true);
                    instance.getStatusManager().getPlayer(event.getEntity().getUniqueId()).setPlus1sMode(true);
                    // for soundpack
                    Bukkit.getScheduler().runTask(instance, () -> ((Player) event.getEntity()).playSound(((Player) event.getEntity()).getLocation(), "minecraft:agenda.dying", SoundCategory.MASTER, 1000F, 1F));
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.removePotionEffect(PotionEffectType.ABSORPTION);
                            player.playSound(player.getLocation(), Sound.ENTITY_GHAST_DEATH, 1F, 1F);
                            if (instance.getStatusManager().getPlayer(player.getUniqueId()).isPlus1sMode()) {
                                player.setHealth(0);
                                player.damage(999);
                            }
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
    public void onPlayerEatGoldenApple(FoodLevelChangeEvent event) {
        if (instance.getStatusManager().getPlayer(event.getEntity().getUniqueId()).isPlus1sMode()) {
            if (event.getFoodLevel() == 20) {
                if (event.getEntity().getInventory().getItemInMainHand().getType() == Material.GOLDEN_APPLE || event.getEntity().getInventory().getItemInMainHand().getType() == Material.ENCHANTED_GOLDEN_APPLE || event.getEntity().getInventory().getItemInMainHand().getType() == Material.GOLDEN_CARROT) {
                    removePlus1sMode((Player) event.getEntity());
                }
            } else {
                if (event.getEntity().getInventory().getItemInMainHand().getType() == Material.GOLDEN_APPLE || event.getEntity().getInventory().getItemInMainHand().getType() == Material.ENCHANTED_GOLDEN_APPLE || event.getEntity().getInventory().getItemInMainHand().getType() == Material.GOLDEN_CARROT) {
                    removePlus1sMode((Player) event.getEntity());
                } else {
                    if (!event.getEntity().getInventory().getItemInMainHand().getType().isEdible()) {
                        if (event.getEntity().getInventory().getItemInOffHand().getType() == Material.GOLDEN_APPLE || event.getEntity().getInventory().getItemInOffHand().getType() == Material.ENCHANTED_GOLDEN_APPLE || event.getEntity().getInventory().getItemInMainHand().getType() == Material.GOLDEN_CARROT) {
                            removePlus1sMode((Player) event.getEntity());
                        }
                    }
                }
            }
        }
    }

    private void removePlus1sMode(Player player) {
        instance.getStatusManager().getPlayer(player.getUniqueId()).setPlus1sMode(false);
        boolean skyChanger = PersonalAPI.of(player).getBoolean("recreator.weather.sandstorm.enable", true);
        if (skyChanger) {
            player.resetPlayerTime();
            player.resetPlayerWeather();
            SkyChanger.getAPI().changeSky(player, player.getWorld().isThundering() ? 1 : 0);
        }
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.removePotionEffect(PotionEffectType.ABSORPTION);
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.removePotionEffect(PotionEffectType.SLOW);
        player.setHealth(player.getHealth() + 1.5);
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
