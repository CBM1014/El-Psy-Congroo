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

public class WhiteTangyuan extends NewItem {
    final ItemStack item = this.getItemStack("§6白元宵");

    public WhiteTangyuan(Init instance) {
        super(instance, Material.SNOWBALL, "item.white_tangyuan", 9);
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
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 7, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 5, 0));
        }
    }
    
    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}