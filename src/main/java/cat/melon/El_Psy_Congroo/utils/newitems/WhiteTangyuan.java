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

public class WhiteTangyuan extends NewItem {
    final ItemStack item = this.getItemStack("§6白元宵", 1, true);

    public WhiteTangyuan(Init instance) {
        super(instance, Material.SNOWBALL, "item.white_tangyuan", 9);
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
            event.setCancelled(true);
            for (ItemStack itemStack : event.getPlayer().getInventory())
                if (itemStack != null && itemStack.isSimilar(item))
                    if (itemStack.getAmount() < 2)
                        event.getPlayer().getInventory().remove(item);
                    else
                        itemStack.setAmount(itemStack.getAmount() - 1);
            
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_BURP, 2F, 1F);
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 7 * 20, 0));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 5 * 20, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 5 * 20, 0));
            event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 2);
        }
    }
    
    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}