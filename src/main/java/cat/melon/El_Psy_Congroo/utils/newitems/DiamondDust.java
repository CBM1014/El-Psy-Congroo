package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice.ExactChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("deprecation")
public class DiamondDust extends NewItem {
    final ItemStack item = this.getItem();
    ShapedRecipe diamond;
    ThreadLocalRandom random = ThreadLocalRandom.current();

    public DiamondDust(Init instance) {
        super(instance, Material.PRISMARINE_CRYSTALS, "§b钻石砂", 4);
    }

    @Override
    public void onRegister() {
        diamond = new ShapedRecipe(new NamespacedKey(getInstance(), "diamond_dust"),new ItemStack(Material.DIAMOND));
        diamond.shape(" y ","yyy","yyy");
        diamond.setIngredient('y', new ExactChoice(item));
        Bukkit.addRecipe(diamond);
        
        Iterator<Recipe> it = Bukkit.recipeIterator();
        while (it.hasNext()) {
            if (it.next().getResult().getType() == Material.SEA_LANTERN)
                it.remove();
        }
        
        ShapedRecipe overrideCrystals = new ShapedRecipe(new NamespacedKey(getInstance(), "override_prismarine_crystals"),new ItemStack(Material.SEA_LANTERN));
        overrideCrystals.shape("xyx","yyy","xyx");
        overrideCrystals.setIngredient('x', Material.PRISMARINE_SHARD);
        overrideCrystals.setIngredient('y', new ExactChoice(new ItemStack(Material.PRISMARINE_CRYSTALS)));
        Bukkit.addRecipe(overrideCrystals);
        
        //getInstance().getLogger().info("Recipe "+diamond.getKey()+" has been loaded.");
    }
    /*
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
    */
}
