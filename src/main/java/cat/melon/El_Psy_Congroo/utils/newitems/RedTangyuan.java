package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RedTangyuan extends NewItem {
    final ItemStack item = this.getItemStack("§c团圆元宵", 1, true);

    public RedTangyuan(Init instance) {
        super(instance, Material.MAGMA_CREAM, "item.red_tangyuan", 8);
    }

    @Override
    public void onRegister() {
        
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onEat(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR)
            return;
        
        if (event.getItem() == null)
            return;
        
        if (event.getItem().isSimilar(item)) {
            for (ItemStack itemStack : event.getPlayer().getInventory())
                if (itemStack != null && itemStack.isSimilar(item))
                    if (itemStack.getAmount() < 2) {
                        event.getPlayer().getInventory().remove(item);
                        break;
                    } else {
                        itemStack.setAmount(itemStack.getAmount() - 1);
                        break;
                    }
            
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_BURP, 2F, 1F);
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 7 * 20, 0));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9 * 20, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 12 * 20, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 12 * 20, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 19 * 20, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 64 * 20, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 64 * 20, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 64 * 20, 1));
            event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 4);
        }
    }
    
    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}