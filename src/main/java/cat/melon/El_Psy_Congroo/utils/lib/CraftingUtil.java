package cat.melon.el_psy_congroo.utils.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class CraftingUtil implements Listener {
    
    private static CraftingUtil p;
    protected static Plugin plugin;
    
    private static List<CraftingRecipe> recipies = new ArrayList<CraftingRecipe>();
    
    /**
     * Call this in your onEnable. This allows for the events to work.
     *
     * @param javaPlugin
     *  : Your main class.
     */
    public static void initialize(Plugin javaPlugin) {
        p = new CraftingUtil();
        Bukkit.getPluginManager().registerEvents(p, javaPlugin);
        plugin = javaPlugin;
    }
    
    /**
     * This adds a special recipe to the list. Make sure you register your
     * recipe before you add it to the list.
     *
     * @param sr
     *  your ShapedRecipe
     * @return the CraftingRecipe instance.
     */
    public static CraftingRecipe addRecipe(Recipe sr) {
        CraftingRecipe cr = p.new CraftingRecipe(sr);
        recipies.add(cr);
        return cr;
    }
    
    /**
     * Removes a special recipe from the list.
     *
     * @param the
     *  Crafting Recipe instace.
     */
    public static void removeRecipe(CraftingRecipe cr) {
        recipies.remove(cr);
    }
    
    @EventHandler
    private void onCraft(PrepareItemCraftEvent event) {
        for (CraftingRecipe recipe : recipies) {
            if (event.getRecipe() == null)
                return;
            
            if (event.getRecipe().getResult()
                    .equals(recipe.getRecipe().getResult())
                    && isShape(event.getInventory(),
                            (ShapedRecipe) recipe.getRecipe())) {
                for (int slot = 0; slot < recipe.getIngredients().length; slot++) {
                    if (recipe.getIngredients()[slot] != null) {
                        if (!itemstacksSim(
                                event.getInventory().getMatrix()[slot],
                                recipe.getIngredients()[slot])) {
                            event.getInventory().setResult(
                                    new ItemStack(Material.AIR));
                            break;
                        }
                    }
                }
                break;
            }
        }
    }
    
    @SuppressWarnings("deprecation")
    private boolean itemstacksSim(ItemStack i1, ItemStack i2) {
        if (i1.getType() == i2.getType())
            if (i1.getDurability() == i2.getDurability())
                if (i1.hasItemMeta() && i2.hasItemMeta()) {
                    if ((!i1.getItemMeta().hasDisplayName() && !i2
                            .getItemMeta().hasDisplayName())
                            || (i1.getItemMeta().hasDisplayName()
                                    && i2.getItemMeta().hasDisplayName() && i1
                                    .getItemMeta().getDisplayName()
                                    .equals(i2.getItemMeta().getDisplayName())))
                        if ((!i1.getItemMeta().hasLore() && !i2.getItemMeta()
                                .hasLore())
                                || (i1.getItemMeta().hasLore()
                                        && i2.getItemMeta().hasLore() && i1
                                        .getItemMeta().getLore()
                                        .equals(i2.getItemMeta().getLore())))
                            return true;
                } else if (!(i1.hasItemMeta()) && !(i2.hasItemMeta())) {
                    return true;
                }
        return false;
    }
    
    private boolean isShape(CraftingInventory inv, ShapedRecipe sr) {
        ItemStack[] mat = inv.getMatrix();
        ItemStack[] mat2 = new ItemStack[9];
        String[] str = sr.getShape();
        for (int i = 0; i < 9; i++) {
            for (Entry<Character, ItemStack> e : sr.getIngredientMap()
                    .entrySet()) {
                if (mat[i] == null)
                    mat[i] = new ItemStack(Material.AIR);
                
                if (str[i / 3].charAt(i % 3) == ' ') {
                    mat2[i] = new ItemStack(Material.AIR);
                } else if (str[i / 3].charAt(i % 3) == e.getKey()) {
                    mat2[i] = e.getValue();
                }
                
                if (mat2[i] == null)
                    mat2[i] = new ItemStack(Material.AIR);
                
                if (mat[i].getType() != mat2[i].getType())
                    return false;
            }
        }
        return true;
    }
    
    public class CraftingRecipe {
        
        private Recipe sr;
        private ItemStack[] ing = new ItemStack[9];
        
        public CraftingRecipe(Recipe sr) {
            this.sr = sr;
        }
        
        /**
         * This is how you specify if an object
         *
         * @param slot
         * @param is
         * @return
         */
        public CraftingRecipe setItemstack(int slot, ItemStack is) {
            ing[slot] = is;
            return this;
        }
        
        public CraftingRecipe setItemstack(int row, int col, ItemStack is) {
            ing[(row * 3) + col] = is;
            return this;
        }
        
        public ItemStack[] getIngredients() {
            return ing;
        }
        
        public Recipe getRecipe() {
            return sr;
        }
    }
}