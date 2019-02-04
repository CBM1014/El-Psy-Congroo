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

import java.util.concurrent.ThreadLocalRandom;

public class GoldDust extends NewItem {
    final ItemStack item = this.getItem();
    ThreadLocalRandom random = ThreadLocalRandom.current();

    public GoldDust(Init instance) {
        super(instance, Material.GUNPOWDER, "item.gold_dust", 2);
    }

    @Override
    public void onRegister() {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(), "gold_dust"), new ItemStack(Material.GOLD_NUGGET), Material.GLOWSTONE_DUST, 0.7F, 600);
        Bukkit.addRecipe(furnaceRecipe);
        getInstance().getLogger().info("Recipe "+furnaceRecipe.getKey()+" has been loaded.");
        FurnaceRecipe overrideRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(),"gold_ore"),new ItemStack(Material.GOLD_NUGGET),Material.GOLD_ORE,0.7F,1800);
        Bukkit.addRecipe(overrideRecipe);
        getInstance().getLogger().info("Recipe "+overrideRecipe.getKey()+" has been loaded.");
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
                break;
            case IRON_PICKAXE:
                amount = 1 + random.nextInt(1);
                break;
            case DIAMOND_PICKAXE:
                amount = 1 + random.nextInt(2);
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
