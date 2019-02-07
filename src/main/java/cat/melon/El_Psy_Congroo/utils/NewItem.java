package cat.melon.el_psy_congroo.utils;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.lib.CraftingUtil;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class NewItem implements Listener {
    private boolean basic = true;
    private final Material type;
    private final int modelNumber;
    private final String namePath;
    private List<String> lore;
    Map<Enchantment, Integer> enchantments;
    ItemFlag[] itemFlags;
    
    public abstract ItemStack getSampleItem();

    private boolean isRegistered = false;
    private Init instance;

    public NewItem(Init instance, Material type, String namePath, int modelNumber){
        this.instance = instance;
        this.type = type;
        this.modelNumber = modelNumber;
        this.namePath = namePath;
    }

    public NewItem(Init instance, Material type, String namePath, int modelNumber, List<String> lore, Map<Enchantment, Integer> enchantments, ItemFlag... itemFlags) {
        basic = false;
        this.instance = instance;
        this.type = type;
        this.modelNumber = modelNumber;
        this.namePath = namePath;
        this.lore = lore!=null ? lore : new ArrayList<>();
        this.enchantments = enchantments != null ? enchantments : new HashMap<>();
        this.itemFlags = itemFlags != null ? itemFlags : new ItemFlag[0];
    }

    public ItemStack getItemStack(String displayName, int amount) {
        ItemStack tmpis = new ItemStack(type, amount);
        ItemMeta meta = tmpis.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLocalizedName(namePath); //TODO set LocalizedName here(will provide in the resourcepack) //but idk how to do it((
        tmpis.setItemMeta(meta);

        if(!basic){
            tmpis.setLore(lore);
            tmpis.addEnchantments(enchantments);
            tmpis.addItemFlags(itemFlags);
        }
        NBTItem tmpni = new NBTItem(tmpis);
        tmpni.setInteger("CustomModelData", modelNumber);
        tmpni.setString("agendaItem", namePath);
        return tmpni.getItem();
    }

    public ItemStack getItemStack(String displayName) {
        return this.getItemStack(displayName, 1);
    }

    public Material getType() {
        return type;
    }

    public int getModelNumber() {
        return modelNumber;
    }

    public String getNamePath() {
        return namePath;
    }

    public List<String> getLore() {
        return lore;
    }

    public Init getInstance(){
        return instance;
    }

    public boolean register() {
        if (!isRegistered) {
            this.onRegister();
            Bukkit.getServer().getPluginManager().registerEvents(this, instance);
            isRegistered = true;
            return true;
        } else {
            return false;
        }

    }

    public void onRegister(){}
    
    /**
     * Override vanilla recipes with exact choices
     */
    @SuppressWarnings("deprecation")
    public void overrideVanillaExactly() {
        List<Recipe> exactRecipes = Lists.newArrayList();
        
        //org.bukkit.inventory.RecipeChoice.ExactChoice choiceExactVanilla = new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(getType()));
        
        //ItemStack dummyItem = getSampleItem();
        
        Iterator<Recipe> it = Bukkit.recipeIterator();
        while (it.hasNext()) {
            Recipe recipe = it.next();
            //boolean changed = false;
            
            if (recipe.getResult().getType() == Material.COAL_BLOCK) { // Bukkit bug?
                ShapedRecipe coalBlock = new ShapedRecipe(new NamespacedKey(getInstance(), "coal_block"), new ItemStack(Material.COAL_BLOCK));
                coalBlock.shape("xxx","xxx","xxx");
                coalBlock.setIngredient('x', Material.COAL);
                
                ItemStack coal = new ItemStack(Material.COAL);
                CraftingUtil.addRecipe(coalBlock).setItemstack(0, coal)
                                                 .setItemstack(1, coal)
                                                 .setItemstack(2, coal)
                                                 .setItemstack(3, coal)
                                                 .setItemstack(4, coal)
                                                 .setItemstack(5, coal)
                                                 .setItemstack(6, coal)
                                                 .setItemstack(7, coal)
                                                 .setItemstack(8, coal);
                
                continue;
            } else if (recipe.getResult().getType() == Material.FIRE_CHARGE) { // Bukkit bug?
                it.remove();
                
                Bukkit.getScheduler().runTask(instance, () -> {
                    ShapelessRecipe coalBlock = new ShapelessRecipe(new NamespacedKey(getInstance(), "fire_charge"), new ItemStack(Material.FIRE_CHARGE));
                    coalBlock.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.COAL)));
                    coalBlock.addIngredient(Material.BLAZE_POWDER);
                    coalBlock.addIngredient(Material.GUNPOWDER);
                    Bukkit.addRecipe(coalBlock);
                });
                
                continue;
            } else if (recipe.getResult().getType() == Material.GLOWSTONE) { // Bukkit bug?
                it.remove();
                
                Bukkit.getScheduler().runTask(instance, () -> {
                    ShapelessRecipe glowStone = new ShapelessRecipe(new NamespacedKey(getInstance(), "glowstone"), new ItemStack(Material.GLOWSTONE));
                    glowStone.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.GLOWSTONE_DUST)));
                    glowStone.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.GLOWSTONE_DUST)));
                    glowStone.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.GLOWSTONE_DUST)));
                    glowStone.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.GLOWSTONE_DUST)));
                    Bukkit.addRecipe(glowStone);
                });
                
                continue;
            } else if (recipe.getResult().getType() == Material.SEA_LANTERN) { // Bukkit bug?
                ShapedRecipe coalBlock = new ShapedRecipe(new NamespacedKey(getInstance(), "sea_latern"), new ItemStack(Material.SEA_LANTERN));
                coalBlock.shape("yxy","xxx","yxy");
                coalBlock.setIngredient('x', Material.PRISMARINE_CRYSTALS);
                coalBlock.setIngredient('y', Material.PRISMARINE_SHARD);
                
                ItemStack crystal = new ItemStack(Material.PRISMARINE_CRYSTALS);
                ItemStack shard = new ItemStack(Material.PRISMARINE_SHARD);
                CraftingUtil.addRecipe(coalBlock).setItemstack(0, shard)
                                                 .setItemstack(1, crystal)
                                                 .setItemstack(2, shard)
                                                 .setItemstack(3, crystal)
                                                 .setItemstack(4, crystal)
                                                 .setItemstack(5, crystal)
                                                 .setItemstack(6, shard)
                                                 .setItemstack(7, crystal)
                                                 .setItemstack(8, shard);
                
                continue;
            } else if (recipe.getResult().getType() == Material.TORCH) { // Bukkit bug?
                ShapedRecipe coalBlock = new ShapedRecipe(new NamespacedKey(getInstance(), "torch"), new ItemStack(Material.TORCH));
                coalBlock.shape("   "," x "," y ");
                coalBlock.setIngredient('x', Material.COAL);
                coalBlock.setIngredient('y', Material.STICK);
                
                ItemStack crystal = new ItemStack(Material.COAL);
                ItemStack shard = new ItemStack(Material.STICK);
                CraftingUtil.addRecipe(coalBlock).setItemstack(4, crystal)
                                                 .setItemstack(7, shard);
                
                continue;
            } else if (recipe.getResult().getType() == Material.SPECTRAL_ARROW) { // Bukkit bug?
                ShapedRecipe coalBlock = new ShapedRecipe(new NamespacedKey(getInstance(), "spectral_arrow"), new ItemStack(Material.SPECTRAL_ARROW));
                coalBlock.shape(" x ","xyx"," x ");
                coalBlock.setIngredient('x', Material.GLOWSTONE_DUST);
                coalBlock.setIngredient('y', Material.ARROW);
                
                ItemStack crystal = new ItemStack(Material.GLOWSTONE_DUST);
                ItemStack shard = new ItemStack(Material.ARROW);
                CraftingUtil.addRecipe(coalBlock).setItemstack(1, crystal)
                                                 .setItemstack(3, crystal)
                                                 .setItemstack(5, crystal)
                                                 .setItemstack(7, crystal)
                                                 .setItemstack(4, shard);
                
                continue;
            }
            
            // Bugggggggggggggggy
            /*
            if (recipe instanceof ShapedRecipe) {
                ShapedRecipe shapedRecipe = ((ShapedRecipe) recipe);
                for (Entry<Character, RecipeChoice> entry : ((ShapedRecipe) recipe).getChoiceMap().entrySet()) {
                    if (entry.getValue() == null)
                        continue;
                    
                    if (entry.getValue().test(dummyItem)) {
                        changed = true;
                        Bukkit.getLogger().warning("processing: " + entry.getValue().getItemStack().getType());
                        shapedRecipe.setIngredient(entry.getKey(), choiceExactVanilla);
                    }
                }
                
                if (changed) {
                    Bukkit.getLogger().warning("renewed shaped recipe: " + recipe.getResult().getType());
                    it.remove();
                    exactRecipes.add(shapedRecipe);
                }
                
                continue;
            } else if (recipe instanceof ShapelessRecipe) {
                ShapelessRecipe shapelessRecipe = ((ShapelessRecipe) recipe);
                for (RecipeChoice choice : ((ShapelessRecipe) recipe).getChoiceList()) {
                    if (choice == null)
                        continue;
                    
                    if (choice.test(dummyItem)) {
                        changed = true;
                        Bukkit.getLogger().warning("processing: " + choice.getItemStack().getType());
                        shapelessRecipe.removeIngredient(getType()).addIngredient(choiceExactVanilla);
                    }
                }
                
                if (changed) {
                    Bukkit.getLogger().warning("renewed shapeless recipe: " + recipe.getResult().getType());
                    it.remove();
                    exactRecipes.add(shapelessRecipe);
                }
                
                continue;
            } else if (recipe instanceof FurnaceRecipe) {
                FurnaceRecipe furnaceRecipe = ((FurnaceRecipe) recipe);
                if (furnaceRecipe.getInputChoice() == null)
                    break;
                
                if (furnaceRecipe.getInputChoice().test(dummyItem)) {
                    Bukkit.getLogger().warning("renewed furnace: " + recipe.getResult().getType());
                    it.remove();
                    exactRecipes.add(furnaceRecipe.setInputChoice(choiceExactVanilla));
                }
                
                continue;
            } else if (recipe instanceof MerchantRecipe) {
                MerchantRecipe merchantRecipe = ((MerchantRecipe) recipe);
                Material material = merchantRecipe.getResult().getType();
                switch (material) {
                    case DIAMOND:
                    case IRON_INGOT:
                    case GOLD_INGOT:
                    case EMERALD:
                        it.remove();
                    default:
                        break;
                }
            } else {
                throw new UnsupportedOperationException("Unknown Recipe Type: " + recipe.getClass().getSimpleName());
            }*/
        }
        
        for (Recipe override : exactRecipes)
            Bukkit.addRecipe(override);
    }
}
