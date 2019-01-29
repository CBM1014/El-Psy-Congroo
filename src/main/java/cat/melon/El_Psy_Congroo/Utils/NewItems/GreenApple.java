package cat.melon.El_Psy_Congroo.Utils.NewItems;

import cat.melon.El_Psy_Congroo.Init;
import cat.melon.El_Psy_Congroo.Utils.NewItem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class GreenApple extends NewItem implements Listener {
    Random random = new Random();

    public GreenApple(Init instance) {
        super(instance, Material.APPLE, "ElNewItem.green_apple", 1);
    }

    @EventHandler
    public void onAppleDrop(LeavesDecayEvent event) {
        if (/*random.nextInt(20)==5 || */true) {
            Bukkit.broadcastMessage("drop!");
            ItemStack item = this.getItem();
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
        }
    }

    /*
    @EventHandler
    public void onAppleDrop(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR && event.getItem().getType() == Material.APPLE) {
            Bukkit.broadcastMessage("durability: " + event.getItem().getDurability());
        }
    }*/
}
