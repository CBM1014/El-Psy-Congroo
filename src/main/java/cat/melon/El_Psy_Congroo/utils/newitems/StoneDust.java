package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.RecipeChoice.ExactChoice;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("deprecation")
public class StoneDust extends NewItem {
    final ItemStack item = this.getItemStack("§7碎石子");
    Random random = ThreadLocalRandom.current();

    public StoneDust(Init instance) {
        super(instance, Material.IRON_NUGGET, "item.stone_dust", 7);
    }

    @Override
    public void onRegister() {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(), "andesite"), getItemStack("§7碎石子", 3), Material.DIORITE, 0.7F, 300);
        Bukkit.addRecipe(furnaceRecipe);
        getInstance().getLogger().info("Recipe "+furnaceRecipe.getKey()+" has been loaded.");
        
        ShapelessRecipe diamond = new ShapelessRecipe(new NamespacedKey(getInstance(), "cobb_stone"), new ItemStack(Material.STONE));
        ExactChoice choice = new ExactChoice(item);
        diamond.addIngredient(choice);
        diamond.addIngredient(choice);
        diamond.addIngredient(choice);
        diamond.addIngredient(choice);
        Bukkit.addRecipe(diamond);
        getInstance().getLogger().info("Recipe "+diamond.getKey()+" has been loaded.");
    }
    
    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}