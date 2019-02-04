package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.concurrent.ThreadLocalRandom;

public class DiamondDust extends NewItem {
    final ItemStack item = this.getItem();
    ShapedRecipe diamond;
    ThreadLocalRandom random = ThreadLocalRandom.current();

    public DiamondDust(Init instance) {
        super(instance, Material.LIGHT_BLUE_DYE, "item.diamond_dust", 4);
    }

    @Override
    public void onRegister() {
        diamond = new ShapedRecipe(new NamespacedKey(this.getInstance(),"diamond_dust"),new ItemStack(Material.DIAMOND));
        diamond.shape("010","111","111");
        diamond.setIngredient('0',Material.AIR);
        diamond.setIngredient('1',this.getItem().getData());
        Bukkit.addRecipe(diamond);
        getInstance().getLogger().info("Recipe "+diamond.getKey()+" has been loaded.");
    }

    @EventHandler
    public void onIronOreBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.DIAMOND_ORE)
            return;
        event.setDropItems(false);
        int amount = 0;
        switch (event.getPlayer().getInventory().getItemInMainHand().getType()) {
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
}
