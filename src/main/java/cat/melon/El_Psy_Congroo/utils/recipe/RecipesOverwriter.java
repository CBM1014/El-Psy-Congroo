package cat.melon.el_psy_congroo.utils.recipe;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
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
    private final List<OverwriteRecipe> recipes = Lists.newArrayList();
    
    public RecipesOverwriter(Plugin plugin) {
        pluginInstance = plugin;
    }
    
    public static OverwriteRecipe overwriteRecipe(Recipe bukkitRecipe) {
        if (bukkitRecipe instanceof ShapedRecipe) {
            OverwriteShapedRecipe shapedRecipe = new OverwriteShapedRecipe(((ShapedRecipe) bukkitRecipe).getKey(), bukkitRecipe.getResult());
            
            shapedRecipe.shape(((ShapedRecipe) bukkitRecipe).getShape());
            shapedRecipe.setGroup(((ShapedRecipe) bukkitRecipe).getGroup());
            for (Entry<Character, ItemStack> entry : ((ShapedRecipe) bukkitRecipe).getIngredientMap().entrySet()) {
                shapedRecipe.setIngredient(entry.getKey(), entry.getValue());
            }
            
            return shapedRecipe;
        } else if (bukkitRecipe instanceof ShapelessRecipe) {
            OverwriteShapelessRecipe shapelessRecipe = new OverwriteShapelessRecipe(((ShapelessRecipe) bukkitRecipe).getKey(), bukkitRecipe.getResult());
            
            shapelessRecipe.setGroup(((ShapelessRecipe) bukkitRecipe).getGroup());
            for (ItemStack ingredient : ((ShapelessRecipe) bukkitRecipe).getIngredientList()) {
                shapelessRecipe.addIngredient(ingredient);
            }
            
            return shapelessRecipe;
        }
        throw new UnsupportedOperationException("Recipe Type: " + bukkitRecipe.getClass().getSimpleName());
    }
    
    public void overwriteVanillaRecipes(ItemStack... ingrendients) {
        Iterator<Recipe> it = Bukkit.recipeIterator();
        
        $recipe:
        while (it.hasNext()) {
            Recipe vanillaRecipe = it.next();
            
            for (ItemStack vanillaIngredient : ((ShapedRecipe) vanillaRecipe).getIngredientMap().values()) {
                for (ItemStack ingrendient : ingrendients) {
                    if (vanillaIngredient.getType() == ingrendient.getType() && !vanillaIngredient.isSimilar(ingrendient)) {
                        recipes.add(overwriteRecipe(vanillaRecipe));
                        continue $recipe;
                    }
                }
            }
        }
    }

    @EventHandler
    private void onCraft(PrepareItemCraftEvent event) {
        Recipe prepareRecipe = event.getRecipe();
        
        if (prepareRecipe instanceof ShapedRecipe) {
            ShapedRecipe shapedPrepareRecipe = (ShapedRecipe) prepareRecipe;
            
            $recipe:
            for (OverwriteRecipe recipe : recipes) {
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