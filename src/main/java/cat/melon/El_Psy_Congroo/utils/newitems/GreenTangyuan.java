package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class GreenTangyuan extends NewItem {
    final ItemStack item = this.getItemStack("§a糖心元宵");

    public GreenTangyuan(Init instance) {
        super(instance, Material.SLIME_BALL, "item.green_tangyuan", 11);
    }

    @Override
    public void onRegister() {
        
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onThrowSnowball(PlayerItemConsumeEvent event) {
        if (event.getItem().isSimilar(item)) {
            event.setCancelled(true);
            
            event.getPlayer().getInventory().remove(item);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_BURP, 2F, 1F);
        }
    }
    
    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}