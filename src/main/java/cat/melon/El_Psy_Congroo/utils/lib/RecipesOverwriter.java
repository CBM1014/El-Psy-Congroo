package cat.melon.el_psy_congroo.utils.lib;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Lists;

public class RecipesOverwriter implements Listener {
    private final Plugin pluginInstance;
    private final List<ItemStack> items = Lists.newArrayList();
    
    public RecipesOverwriter(Plugin plugin) {
        pluginInstance = plugin;
    }
    
    public void overwriteVanillaRecipes(Collection<ItemStack> ingrendients) {
        items.addAll(ingrendients);
    }
    
    /**
     * <b>Not valid for shapeless recipes</b>
     * @param itemStack
     * @return
     */
    @SuppressWarnings("deprecation")
    public static RecipeChoice overwriteIngredient(ItemStack itemStack) {
        return new RecipeChoice.ExactChoice(itemStack);
    }
    
    /*
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
            
            if (((Keyed) vanillaRecipe).getKey().getNamespace().equals(pluginInstance.getName().toLowerCase(Locale.ROOT)))
                continue;
            
            Iterable<ItemStack> vanillaIngredients = 
                    vanillaRecipe instanceof ShapedRecipe ? ((ShapedRecipe) vanillaRecipe).getIngredientMap().values() :
                    vanillaRecipe instanceof ShapelessRecipe ? ((ShapelessRecipe) vanillaRecipe).getIngredientList() : Lists.newArrayList();
            
            for (ItemStack vanillaIngredient : vanillaIngredients) {
                for (ItemStack ingrendient : ingrendients) {
                    if (vanillaIngredient == null || ingrendient == null) // no need to test air exactly
                        continue;
                    
                    if (vanillaIngredient.getType() == ingrendient.getType() && !vanillaIngredient.isSimilar(ingrendient)) {
                        recipes.add(overwriteRecipe(vanillaRecipe));
                        continue $recipe;
                    }
                }
            }
        }
    }
    */

    @EventHandler
    private void onCraft(PrepareItemCraftEvent event) {
        if (Objects.isNull(event.getRecipe()))
            return;
        
        Recipe prepareRecipe = event.getRecipe();
        
        // ignore our custom recipes
        // test choice type would be better, but much slower
        if (((Keyed) prepareRecipe).getKey().getNamespace().equals(pluginInstance.getName().toLowerCase(Locale.ROOT)))
            return;
        
        for (ItemStack item : items) {
            ItemStack[] matrix = event.getInventory().getMatrix();
            
            for (int i = 0; i < matrix.length; i++) {
                ItemStack itemStackAt = matrix[i];
                if (itemStackAt == null)
                    continue;
                
                if (itemStackAt.isSimilar(item)) {
                    event.getInventory().setResult(new ItemStack(Material.AIR));
                    return; // time to say goodbye, event
                }
            }
        }
        
        /*
        // as type
        if (!(recipe instanceof OverwriteShapedRecipe))
            continue;
        
        OverwriteShapedRecipe shapedRecipe = (OverwriteShapedRecipe) recipe;
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
                    if ((itemStackAt == null && character != ' ') || (itemStackAt != null && character == ' ')) // test required
                        continue $char;
                    
                    int prepareMatrixIndex = i * 3 + o;
                    if (!itemStackAt.equals(matrix[prepareMatrixIndex]))
                        continue $recipe;
                }
        }
        
        // matches the current recipe
        return;
        */
    }
    
}