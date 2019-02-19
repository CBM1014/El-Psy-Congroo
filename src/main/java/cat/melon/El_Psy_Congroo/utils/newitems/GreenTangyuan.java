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

public class GreenTangyuan extends NewItem {
    final ItemStack item = this.getItemStack("§a糖心元宵", 1, true);

    public GreenTangyuan(Init instance) {
        super(instance, Material.SLIME_BALL, "item.green_tangyuan", 11);
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
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_BURP, 2F, 1F);
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 7 * 20, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 5 * 20, 0));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 7 * 20, 0));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 7 * 20, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 12 * 20, 0));
            event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 1);
        }
    }
    
    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}