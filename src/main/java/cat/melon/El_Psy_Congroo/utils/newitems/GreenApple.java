package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GreenApple extends NewItem implements Listener {
    Random random = ThreadLocalRandom.current();

    public GreenApple(Init instance) {
        super(instance, Material.APPLE, "item.green_apple", 1);
    }

    @EventHandler(ignoreCancelled = true)
    public void onAppleDrop(LeavesDecayEvent event) {
        int rand = random.nextInt(100);
        if (rand >= 98) { // 2% + 0.5% (default)
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.APPLE));
        }
        if (rand < 3) { // 3% (0 inclusive)
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