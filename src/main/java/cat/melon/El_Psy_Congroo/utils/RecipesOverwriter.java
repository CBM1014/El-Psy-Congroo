package cat.melon.el_psy_congroo.utils;

import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Lists;

public class RecipesOverwriter implements Listener {
    private final Plugin pluginInstance;
    private final List<ExactRecipe> recipes = Lists.newArrayList();
    
    public RecipesOverwriter(Plugin plugin) {
        pluginInstance = plugin;
    }

    @EventHandler
    private void onCraft(PrepareItemCraftEvent event) {
        Recipe prepareRecipe = event.getRecipe();
        
        if (prepareRecipe instanceof ShapedRecipe) {
            ShapedRecipe shapedPrepareRecipe = (ShapedRecipe) prepareRecipe;
            
            $recipe:
            for (ExactRecipe recipe : recipes) {
                // as type
                if (!(recipe instanceof ShapedRecipe))
                    continue;
                
                ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                // as key
                if (!shapedPrepareRecipe.getKey().equals(shapedRecipe.getKey()))
                    continue;
                
                // as result
                if (!prepareRecipe.getResult().equals(recipe.getResult()))
                    continue;
                
                // as shape
                String[] shapes = shapedRecipe.getShape();
                String[] prepareShapes = shapedPrepareRecipe.getShape();
                for (int i = 0; i < prepareShapes.length; i++) {
                    if (!prepareShapes[i].equals(shapes[i]))
                        continue $recipe;
                }
                
                Map<Character, ItemStack> ingredientMap = shapedRecipe.getIngredientMap();
                ItemStack[] matrix = event.getInventory().getMatrix();
                // as ingredient
                for (int i = 0; i < shapes.length; i++) {
                    char[] chars = shapes[i].toCharArray();
                    $char:
                    for (int o = 0; o < chars.length; o++) {
                        char character = chars[o];
                        ItemStack itemStackAt = ingredientMap.get(character);
                        if ((itemStackAt == null && character != ' ') || (itemStackAt != null && character == ' ')) // TODO test required
                            continue $char;
                        
                        int prepareMatrixIndex = i * 3 + o;
                        if (!itemStackAt.equals(matrix[prepareMatrixIndex]))
                            continue $recipe;
                    }
                }
                
                // matches the current recipe
                return;
            }
            
            event.getInventory().setResult(new ItemStack(Material.AIR));
            
        } else if (prepareRecipe instanceof ShapelessRecipe) {
            
        }
    }
    
}