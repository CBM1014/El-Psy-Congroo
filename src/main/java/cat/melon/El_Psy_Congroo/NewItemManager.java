package cat.melon.el_psy_congroo;

import cat.melon.el_psy_congroo.utils.NewItem;
import cat.melon.el_psy_congroo.utils.lib.CraftingUtil;
import cat.melon.el_psy_congroo.utils.newitems.DiamondDust;
import cat.melon.el_psy_congroo.utils.newitems.EnchantedBookSoulBind;
import cat.melon.el_psy_congroo.utils.newitems.GoldDust;
import cat.melon.el_psy_congroo.utils.newitems.GreenApple;
import cat.melon.el_psy_congroo.utils.newitems.IronDust;
import cat.melon.el_psy_congroo.utils.newitems.PreDiamondDust;
import cat.melon.el_psy_congroo.utils.newitems.QuartzDust;
import cat.melon.el_psy_congroo.utils.newitems.StoneDust;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.tr7zw.itemnbtapi.NBTItem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import com.google.common.collect.Lists;

public class NewItemManager implements Listener {
    //Happy New Year 2019!!!!!!!!!!!!!!!!!
    private static final Map<String, NewItem> newItemMap = new HashMap<>();
    Init instance;

    public NewItemManager(Init instance) {
        this.instance = instance;
        
        CraftingUtil.initialize(instance);
        
        try {
            this.registerNewItems(new GreenApple(instance),
                                     new StoneDust(instance),
                                     new QuartzDust(instance),
                                     new IronDust(instance),
                                     new GoldDust(instance),
                                     new DiamondDust(instance), new PreDiamondDust(instance),
                                     new EnchantedBookSoulBind(instance));
            // do not forgot prefix! (item.)
            // the order is important!
        } catch (DuplicateRegisterListenerException e) {
            e.printStackTrace();
        }
        
        overrideVanillaExactly();
    }

    public static NewItem getItem(String key){
        return newItemMap.get(key);
    }

    private void registerNewItems(NewItem... items) throws DuplicateRegisterListenerException {
        for (NewItem x : items) {
            if (x.register()) {
                newItemMap.put(x.getNamePath(), x);
                instance.getLogger().info("Custom item "+ x.getNamePath()+" loaded.");
            } else {
                throw new DuplicateRegisterListenerException("This item has been registered its EventListener.");
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onCraft(CraftItemEvent event){
        // This is a basic protection in case of mistakes
        // The proper way is to override the vanilla recipe
        for(ItemStack x : event.getInventory().getMatrix()){
            if (x == null)
                return;
            NBTItem tmpni = new NBTItem(x);
            if(tmpni.getString("agendaItem")!=null){
                event.setCancelled(true);
            }
        }
    }

    public class DuplicateRegisterListenerException extends Exception {
        private static final long serialVersionUID = 1L;

        public DuplicateRegisterListenerException(String msg) {
            super(msg);
        }
    }
    
    /**
     * Override vanilla recipes with exact choices
     */
    public void overrideVanillaExactly() {
        List<Recipe> exactRecipes = Lists.newArrayList();
        
        //org.bukkit.inventory.RecipeChoice.ExactChoice choiceExactVanilla = new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(getType()));
        
        //ItemStack dummyItem = getSampleItem();
        
        Iterator<Recipe> it = Bukkit.recipeIterator();
        while (it.hasNext()) {
            Recipe recipe = it.next();
            //boolean changed = false;
            
            if (recipe.getResult().getType() == Material.COAL_BLOCK) { // Bukkit bug?
                ShapedRecipe coalBlock = new ShapedRecipe(new NamespacedKey(instance, "coal_block"), new ItemStack(Material.COAL_BLOCK));
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
                ShapedRecipe coalBlock = new ShapedRecipe(new NamespacedKey(instance, "fire_charge"), new ItemStack(Material.FIRE_CHARGE));
                coalBlock.shape("   ","xy "," z ");
                coalBlock.setIngredient('x', Material.BLAZE_POWDER);
                coalBlock.setIngredient('y', Material.COAL);
                coalBlock.setIngredient('z', Material.GUNPOWDER);
                
                ItemStack coal = new ItemStack(Material.BLAZE_POWDER);
                ItemStack coal_ = new ItemStack(Material.COAL);
                ItemStack coal__ = new ItemStack(Material.GUNPOWDER);
                CraftingUtil.addRecipe(coalBlock).setItemstack(3, coal)
                                                 .setItemstack(4, coal_)
                                                 .setItemstack(7, coal__);
                
                ShapedRecipe coalBlock2 = new ShapedRecipe(new NamespacedKey(instance, "fire_charge2"), new ItemStack(Material.FIRE_CHARGE));
                coalBlock2.shape("xy "," z ","   ");
                coalBlock2.setIngredient('x', Material.BLAZE_POWDER);
                coalBlock2.setIngredient('y', Material.COAL);
                coalBlock2.setIngredient('z', Material.GUNPOWDER);
                
                CraftingUtil.addRecipe(coalBlock).setItemstack(0, coal)
                                                 .setItemstack(1, coal_)
                                                 .setItemstack(4, coal__);
                
                /* Bugggggggggy
                Bukkit.getScheduler().runTask(instance, () -> {
                    ShapelessRecipe coalBlock = new ShapelessRecipe(new NamespacedKey(instance, "fire_charge"), new ItemStack(Material.FIRE_CHARGE));
                    coalBlock.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.COAL)));
                    coalBlock.addIngredient(Material.BLAZE_POWDER);
                    coalBlock.addIngredient(Material.GUNPOWDER);
                    Bukkit.addRecipe(coalBlock);
                });
                */
                
                continue;
            } else if (recipe.getResult().getType() == Material.GLOWSTONE) { // Bukkit bug?
                ShapedRecipe coalBlock = new ShapedRecipe(new NamespacedKey(instance, "glowstone"), new ItemStack(Material.GLOWSTONE));
                coalBlock.shape("   ","xx ","xx ");
                coalBlock.setIngredient('x', Material.GLOWSTONE_DUST);
                
                ItemStack coal = new ItemStack(Material.GLOWSTONE_DUST);
                CraftingUtil.addRecipe(coalBlock).setItemstack(3, coal)
                                                 .setItemstack(4, coal)
                                                 .setItemstack(6, coal)
                                                 .setItemstack(7, coal);
                
                ShapedRecipe coalBlock2 = new ShapedRecipe(new NamespacedKey(instance, "glowstone2"), new ItemStack(Material.GLOWSTONE));
                coalBlock2.shape("xx ","xx ","   ");
                coalBlock2.setIngredient('x', Material.GLOWSTONE_DUST);
                
                CraftingUtil.addRecipe(coalBlock2).setItemstack(0, coal)
                                                  .setItemstack(1, coal)
                                                  .setItemstack(3, coal)
                                                  .setItemstack(4, coal);
                
                /* Bugggggggggy
                Bukkit.getScheduler().runTask(instance, () -> {
                    ShapelessRecipe glowStone = new ShapelessRecipe(new NamespacedKey(instance, "glowstone"), new ItemStack(Material.GLOWSTONE));
                    glowStone.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.GLOWSTONE_DUST)));
                    glowStone.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.GLOWSTONE_DUST)));
                    glowStone.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.GLOWSTONE_DUST)));
                    glowStone.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.GLOWSTONE_DUST)));
                    Bukkit.addRecipe(glowStone);
                });
                */
                
                continue;
            } else if (recipe.getResult().getType() == Material.SEA_LANTERN) { // Bukkit bug?
                ShapedRecipe coalBlock = new ShapedRecipe(new NamespacedKey(instance, "sea_latern"), new ItemStack(Material.SEA_LANTERN));
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
                ShapedRecipe coalBlock = new ShapedRecipe(new NamespacedKey(instance, "torch"), new ItemStack(Material.TORCH));
                coalBlock.shape("   "," x "," y ");
                coalBlock.setIngredient('x', Material.COAL);
                coalBlock.setIngredient('y', Material.STICK);
                
                ItemStack crystal = new ItemStack(Material.COAL);
                ItemStack shard = new ItemStack(Material.STICK);
                CraftingUtil.addRecipe(coalBlock).setItemstack(4, crystal)
                                                 .setItemstack(7, shard);
                
                continue;
            } else if (recipe.getResult().getType() == Material.SPECTRAL_ARROW) { // Bukkit bug?
                ShapedRecipe coalBlock = new ShapedRecipe(new NamespacedKey(instance, "spectral_arrow"), new ItemStack(Material.SPECTRAL_ARROW));
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
            } else if (recipe.getResult().getType() == Material.CLAY) { // Bukkit bug?
                ShapedRecipe coalBlock = new ShapedRecipe(new NamespacedKey(instance, "clay"), new ItemStack(Material.CLAY));
                coalBlock.shape("   ","xx ","xx ");
                coalBlock.setIngredient('x', Material.CLAY_BALL);
                
                ItemStack coal = new ItemStack(Material.CLAY_BALL);
                CraftingUtil.addRecipe(coalBlock).setItemstack(3, coal)
                                                 .setItemstack(4, coal)
                                                 .setItemstack(6, coal)
                                                 .setItemstack(7, coal);
                
                ShapedRecipe coalBlock2 = new ShapedRecipe(new NamespacedKey(instance, "clay2"), new ItemStack(Material.CLAY));
                coalBlock2.shape("xx ","xx ","   ");
                coalBlock2.setIngredient('x', Material.CLAY_BALL);
                
                CraftingUtil.addRecipe(coalBlock2).setItemstack(0, coal)
                                                  .setItemstack(1, coal)
                                                  .setItemstack(3, coal)
                                                  .setItemstack(4, coal);
                
                /* Bugggggggggy
                Bukkit.getScheduler().runTask(instance, () -> {
                    ShapelessRecipe glowStone = new ShapelessRecipe(new NamespacedKey(instance, "clay"), new ItemStack(Material.CLAY));
                    glowStone.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.CLAY_BALL)));
                    glowStone.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.CLAY_BALL)));
                    glowStone.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.CLAY_BALL)));
                    glowStone.addIngredient(new org.bukkit.inventory.RecipeChoice.ExactChoice(new ItemStack(Material.CLAY_BALL)));
                    Bukkit.addRecipe(glowStone);
                });
                */
                
                continue;
            } if (recipe.getResult().getType() == Material.IRON_INGOT) { // Bukkit bug?
                    ShapedRecipe coalBlock = new ShapedRecipe(new NamespacedKey(instance, "iron_ingot"), new ItemStack(Material.IRON_INGOT));
                    coalBlock.shape("xxx","xxx","xxx");
                    coalBlock.setIngredient('x', Material.IRON_NUGGET);
                    
                    ItemStack coal = new ItemStack(Material.IRON_NUGGET);
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
