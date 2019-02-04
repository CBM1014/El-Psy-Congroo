package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class IronDust extends NewItem {
    final ItemStack item = this.getItem();
    Random random = new Random();

    public IronDust(Init instance) {
        super(instance, Material.GUNPOWDER, "item.iron_dust", 2);
    }

    @Override
    public void onRegister() {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(), "iron_dust"), new ItemStack(Material.IRON_NUGGET), Material.GUNPOWDER, 0.7F, 220);
        Bukkit.addRecipe(furnaceRecipe);
    }

    @EventHandler
    public void onIronOreBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.IRON_ORE) {
            event.setDropItems(false);
        }
        int amount = 0;
        switch (event.getPlayer().getInventory().getItemInMainHand().getType()) {
            case WOODEN_PICKAXE:
                amount = random.nextInt(1);
                break;
            case STONE_PICKAXE:
                amount = 1 + random.nextInt(1);
                break;
            case IRON_PICKAXE:
                amount = 1 + random.nextInt(2);
                break;
            case DIAMOND_PICKAXE:
                amount = 2 + random.nextInt(1);
                break;
            default:
                break;
        }

        if (amount == 0)
            return;

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
