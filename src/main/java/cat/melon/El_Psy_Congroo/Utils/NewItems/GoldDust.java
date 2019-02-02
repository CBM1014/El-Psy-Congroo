package cat.melon.El_Psy_Congroo.Utils.NewItems;

import cat.melon.El_Psy_Congroo.Init;
import cat.melon.El_Psy_Congroo.Utils.NewItem;
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

public class GoldDust extends NewItem {
    final ItemStack item = this.getItem();
    Random random = new Random();

    public GoldDust(Init instance) {
        super(instance, Material.GUNPOWDER, "item.gold_dust", 2);
    }

    @Override
    public void onRegister() {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(), "gold_dust"), new ItemStack(Material.GOLD_NUGGET), Material.GLOWSTONE_DUST, 0.7F, 600);
        Bukkit.addRecipe(furnaceRecipe);
    }

    @EventHandler
    public void onIronOreBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.GOLD_ORE) {
            event.setDropItems(false);
        }
        int amount = 0;
        switch (event.getPlayer().getInventory().getItemInMainHand().getType()) {
            case STONE_PICKAXE:
                amount = random.nextInt(1);
            case IRON_PICKAXE:
                amount = 1 + random.nextInt(1);
            case DIAMOND_PICKAXE:
                amount = 1 + random.nextInt(2);
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
