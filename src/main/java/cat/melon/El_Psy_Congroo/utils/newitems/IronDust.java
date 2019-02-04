package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class IronDust extends NewItem {
    final ItemStack item = this.getItem();
    Random random = ThreadLocalRandom.current();

    public IronDust(Init instance) {
        super(instance, Material.GUNPOWDER, "item.iron_dust", 2);
    }

    @Override
    public void onRegister() {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(), "iron_dust"), new ItemStack(Material.IRON_NUGGET), Material.GUNPOWDER, 0.7F, 220);
        Bukkit.addRecipe(furnaceRecipe);
    }

    @EventHandler(ignoreCancelled = true)
    public void onIronOreBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.IRON_ORE) {
            event.setDropItems(false);
        }
        
        int amount = 0;
        int rand = random.nextInt(9);
        switch (event.getPlayer().getInventory().getItemInMainHand().getType()) {
            case WOODEN_PICKAXE:
                amount =rand < 4 ? 0 : 1; // 40% nothing, 60% one
                break;
            case STONE_PICKAXE:
                amount = rand < 2 ? 0 : (rand > 7 ? 2 : 1); // 20% nothing, 60% one, 20% two
                break;
            case IRON_PICKAXE:
                amount = 1 + rand < 6 ? 0 : 1; // 1 + 60% extra one
                break;
            case DIAMOND_PICKAXE:
                amount = 2 + rand < 2 ? 0 : (rand > 7 ? 2 : 1); // 2 + 20% nothing, 60% one, 20% two
                break;
            default:
                break;
        }

        if (amount < 1) {
            event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.ITEM_SHIELD_BREAK, 1F, 1F);
            return;
        }

        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), this.getItem(amount));
    }

    @EventHandler
    private void furnaceCanceller(FurnaceSmeltEvent event) {
        if (event.getSource() != null)
            if (event.getSource().getType() == item.getType())
                if (!event.getSource().isSimilar(item))
                    event.setCancelled(true);
    }

    @EventHandler
    private void furnaceCanceller(FurnaceBurnEvent event) {
        Furnace furnace = (Furnace) event.getBlock().getState();
        if (furnace != null)
            if (furnace.getInventory() != null)
                if (furnace.getInventory().getSmelting() != null)
                    if (furnace.getInventory().getSmelting().getType() == item.getType())
                        if (!furnace.getInventory().getSmelting().isSimilar(item))
                            event.setCancelled(true);
    }
}
