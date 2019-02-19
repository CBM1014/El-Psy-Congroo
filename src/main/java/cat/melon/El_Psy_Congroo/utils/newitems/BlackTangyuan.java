package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class BlackTangyuan extends NewItem {
    final ItemStack item = this.getItemStack("§c可颂元宵");

    public BlackTangyuan(Init instance) {
        super(instance, Material.CHORUS_FRUIT, "item.black_tangyuan", 10);
    }

    @Override
    public void onRegister() {
        
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCunsumeChorusFruit(PlayerItemConsumeEvent event) {
        if (event.getItem().isSimilar(item)) {
            event.setCancelled(true); // cancel portal
            
            event.getPlayer().getInventory().remove(item);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_BURP, 2F, 1F);
        }
    }
    
    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}