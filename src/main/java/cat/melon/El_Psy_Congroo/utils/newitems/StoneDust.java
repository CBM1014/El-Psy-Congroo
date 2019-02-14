package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;
import cat.melon.el_psy_congroo.utils.lib.RecipesOverwriter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StoneDust extends NewItem {
    final ItemStack item = this.getItemStack("§7碎石子");
    Random random = ThreadLocalRandom.current();

    public StoneDust(Init instance) {
        super(instance, Material.IRON_NUGGET, "item.stone_dust", 7);
    }

    @Override
    public void onRegister() {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(), "andesite"), getItemStack("§7碎石子", 6), Material.ANDESITE, 0.7F, 300);
        Bukkit.addRecipe(furnaceRecipe);
        getInstance().getLogger().info("Recipe "+furnaceRecipe.getKey()+" has been loaded.");
        
        ShapedRecipe diamond = new ShapedRecipe(new NamespacedKey(getInstance(), "cobb_stone"), new ItemStack(Material.COBBLESTONE));
        diamond.shape("   ","xx ","xx ");
        diamond.setIngredient('x', RecipesOverwriter.overwriteIngredient(item));
        
        ShapedRecipe diamond2 = new ShapedRecipe(new NamespacedKey(getInstance(), "cobb_stone2"), new ItemStack(Material.COBBLESTONE));
        diamond2.shape("xx ", "xx ", "   ");
        diamond2.setIngredient('x', RecipesOverwriter.overwriteIngredient(item));
        
        ShapedRecipe diamond3 = new ShapedRecipe(new NamespacedKey(getInstance(), "cobb_stone3"), new ItemStack(Material.COBBLESTONE));
        diamond3.shape(" xx", " xx", "   ");
        diamond3.setIngredient('x', RecipesOverwriter.overwriteIngredient(item));
        
        ShapedRecipe diamond4 = new ShapedRecipe(new NamespacedKey(getInstance(), "cobb_stone4"), new ItemStack(Material.COBBLESTONE));
        diamond4.shape("   ", " xx", " xx");
        diamond4.setIngredient('x', RecipesOverwriter.overwriteIngredient(item));
        
        Bukkit.addRecipe(diamond);
        Bukkit.addRecipe(diamond2);
        Bukkit.addRecipe(diamond3);
        Bukkit.addRecipe(diamond4);
        getInstance().getLogger().info("Recipe "+diamond.getKey()+" has been loaded.");
    }
    
    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}