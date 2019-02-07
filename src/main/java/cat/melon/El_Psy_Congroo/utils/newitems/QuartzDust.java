package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.RecipeChoice.ExactChoice;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("deprecation")
public class QuartzDust extends NewItem {
    final ItemStack item = this.getItemStack("§7石英碎片");
    Random random = ThreadLocalRandom.current();

    public QuartzDust(Init instance) {
        super(instance, Material.CLAY_BALL, "item.quartz_dust", 6);
    }

    @Override
    public void onRegister() {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(), "diorite"), getItemStack("§7石英碎片", 8), Material.DIORITE, 0.7F, 600);
        Bukkit.addRecipe(furnaceRecipe);
        getInstance().getLogger().info("Recipe "+furnaceRecipe.getKey()+" has been loaded.");
        
        ShapedRecipe diamond = new ShapedRecipe(new NamespacedKey(getInstance(), "quartz_dust"),new ItemStack(Material.QUARTZ_BLOCK));
        diamond.shape("  x","xxx"," x ");
        diamond.setIngredient('x', new ExactChoice(item));
        Bukkit.addRecipe(diamond);
        getInstance().getLogger().info("Recipe "+diamond.getKey()+" has been loaded.");
    }

    @Override
    public ItemStack getSampleItem() {
        return item;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onBurn(FurnaceBurnEvent event) {
        ItemStack itemStack = ((Furnace) event.getBlock().getState()).getInventory().getSmelting();
        if (itemStack.isSimilar(item))
            event.setCancelled(true);
    }
}