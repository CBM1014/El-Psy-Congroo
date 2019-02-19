package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlackTangyuan extends NewItem {
    final ItemStack item = this.getItemStack("§5可颂元宵", 1, true);

    public BlackTangyuan(Init instance) {
        super(instance, Material.CHORUS_FRUIT, "item.black_tangyuan", 10);
    }

    @Override
    public void onRegister() {
        
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCunsumeChorusFruit(PlayerItemConsumeEvent event) {
        if (event.getItem().isSimilar(item)) {
            event.setCancelled(true);
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
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10 * 20, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 10 * 20, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 22 * 20, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 8 * 20, 0));
            event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 2);
        }
    }
    
    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}