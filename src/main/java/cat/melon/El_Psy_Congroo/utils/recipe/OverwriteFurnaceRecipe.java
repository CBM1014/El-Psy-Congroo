package cat.melon.el_psy_congroo.utils.recipe;

import com.google.common.base.Preconditions;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * Represents a smelting recipe.
 */
public class OverwriteFurnaceRecipe implements OverwriteRecipe, Keyed {
    private final NamespacedKey key;
    private ItemStack output;
    private ItemStack ingredient;
    private float experience;
    private int cookingTime;
    private String group = "";

    @Deprecated
    public OverwriteFurnaceRecipe(ItemStack result, Material source) {
        this(NamespacedKey.randomKey(), result, source, 0, 0, 200);
    }

    @Deprecated
    public OverwriteFurnaceRecipe(ItemStack result, MaterialData source) {
        this(NamespacedKey.randomKey(), result, source.getItemType(), source.getData(), 0, 200);
    }

    @Deprecated
    public OverwriteFurnaceRecipe(ItemStack result, MaterialData source, float experience) {
        this(NamespacedKey.randomKey(), result, source.getItemType(), source.getData(), experience, 200);
    }

    @Deprecated
    public OverwriteFurnaceRecipe(ItemStack result, Material source, int data) {
        this(NamespacedKey.randomKey(), result, source, data, 0, 200);
    }

    /**
     * Create a furnace recipe to craft the specified ItemStack.
     *
     * @param key The unique recipe key
     * @param result The item you want the recipe to create.
     * @param source The input material.
     * @param experience The experience given by this recipe
     * @param cookingTime The cooking time (in ticks)
     */
    public OverwriteFurnaceRecipe(NamespacedKey key, ItemStack result, Material source, float experience, int cookingTime) {
        this(key, result, source, 0, experience, cookingTime);
    }

    @Deprecated
    public OverwriteFurnaceRecipe(NamespacedKey key, ItemStack result, Material source, int data, float experience, int cookingTime) {
        this.key = key;
        this.output = new ItemStack(result);
        this.ingredient = new ItemStack(source, 1, (short) data);
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    /**
     * Sets the input of this furnace recipe.
     *
     * @param input The input material.
     * @return The changed recipe, so you can chain calls.
     */
    public OverwriteFurnaceRecipe setInput(MaterialData input) {
        return setInput(input.getItemType(), input.getData());
    }

    /**
     * Sets the input of this furnace recipe.
     *
     * @param input The input material.
     * @return The changed recipe, so you can chain calls.
     */
    public OverwriteFurnaceRecipe setInput(Material input) {
        return setInput(input, 0);
    }

    /**
     * Sets the input of this furnace recipe.
     *
     * @param input The input material.
     * @param data The data value. (Note: This is currently ignored by the
     *     CraftBukkit server.)
     * @return The changed recipe, so you can chain calls.
     * @deprecated Magic value
     */
    @Deprecated
    public OverwriteFurnaceRecipe setInput(Material input, int data) {
        this.ingredient = new ItemStack(input, 1, (short) data);
        return this;
    }

    /**
     * Get the input material.
     *
     * @return The input material.
     */
    public ItemStack getInput() {
        return this.ingredient.clone();
    }

    /**
     * Get the result of this recipe.
     *
     * @return The resulting stack.
     */
    public ItemStack getResult() {
        return output.clone();
    }

    /**
     * Sets the experience given by this recipe.
     *
     * @param experience the experience level
     */
    public void setExperience(float experience) {
        this.experience = experience;
    }

    /**
     * Get the experience given by this recipe.
     *
     * @return experience level
     */
    public float getExperience() {
        return experience;
    }

    /**
     * Set the cooking time for this recipe in ticks.
     *
     * @param cookingTime new cooking time
     */
    public void setCookingTime(int cookingTime) {
        Preconditions.checkArgument(cookingTime >= 0, "cookingTime must be >= 0");
        this.cookingTime = cookingTime;
    }

    /**
     * Get the cooking time for this recipe in ticks.
     *
     * @return cooking time
     */
    public int getCookingTime() {
        return cookingTime;
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

    /**
     * Get the group of this recipe. Recipes with the same group may be grouped
     * together when displayed in the client.
     *
     * @return recipe group. An empty string denotes no group. May not be null.
     */
    public String getGroup() {
        return group;
    }

    /**
     * Set the group of this recipe. Recipes with the same group may be grouped
     * together when displayed in the client.
     *
     * @param group recipe group. An empty string denotes no group. May not be
     * null.
     */
    public void setGroup(String group) {
        Preconditions.checkArgument(group != null, "group");
        this.group = group;
    }
}
