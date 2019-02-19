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

public class RedTangyuan extends NewItem {
    final ItemStack item = this.getItemStack("§c团圆元宵", 1, true);

    public RedTangyuan(Init instance) {
        super(instance, Material.MAGMA_CREAM, "item.red_tangyuan", 8);
    }

    @Override
    public void onRegister() {
        
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEat(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR)
            return;
        
        if (event.getItem() == null)
            return;
        
        if (event.getItem().isSimilar(item)) {
            event.getPlayer().getInventory().remove(item);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_BURP, 2F, 1F);
        }
    }
    
    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}