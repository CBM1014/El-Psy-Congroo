package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice.ExactChoice;

import org.bukkit.inventory.ShapedRecipe;

@SuppressWarnings("deprecation")
public class DiamondDust extends NewItem {
    final ItemStack item = this.getItemStack("§b钻石砂");

    public DiamondDust(Init instance) {
        super(instance, Material.PRISMARINE_CRYSTALS, "item.diamond_dust", 4);
    }

    @Override
    public void onRegister() {
        overrideVanillaExactly();
        
        ShapedRecipe diamond = new ShapedRecipe(new NamespacedKey(getInstance(), "diamond_dust"),new ItemStack(Material.DIAMOND));
        diamond.shape(" y ","yyy","yyy");
        diamond.setIngredient('y', new ExactChoice(item));
        Bukkit.addRecipe(diamond);
        getInstance().getLogger().info("Recipe "+diamond.getKey()+" has been loaded.");
    }
    /*
    @EventHandler(ignoreCancelled = true)
    public void onOreBreak(BlockBreakEvent event) {
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
    */

    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}
