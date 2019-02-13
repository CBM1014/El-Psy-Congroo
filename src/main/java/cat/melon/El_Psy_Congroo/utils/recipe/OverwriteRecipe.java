package cat.melon.el_psy_congroo.utils.recipe;

import org.bukkit.inventory.ItemStack;

/**
 * Represents some type of crafting recipe.
 */
public interface OverwriteRecipe {

    /**
     * Get the result of this recipe.
     *
     * @return The result stack
     */
    ItemStack getResult();
}
