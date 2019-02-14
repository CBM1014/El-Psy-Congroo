package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.NewItemManager;
import cat.melon.el_psy_congroo.utils.NewItem;
import cat.melon.el_psy_congroo.utils.lib.RecipesOverwriter;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class IronDust extends NewItem {
    final ItemStack item = this.getItemStack("§7铁砂");
    Random random = ThreadLocalRandom.current();

    public IronDust(Init instance) {
        super(instance, Material.LIGHT_GRAY_DYE, "item.iron_dust", 2);
    }

    @Override
    public void onRegister() {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(), "iron_dust"), new ItemStack(Material.IRON_NUGGET), RecipesOverwriter.overwriteIngredient(item), 0.7F, 400);
        Bukkit.addRecipe(furnaceRecipe);
        getInstance().getLogger().info("Recipe "+furnaceRecipe.getKey()+" has been loaded.");
        FurnaceRecipe overrideRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(),"iron_ore"),new ItemStack(Material.IRON_NUGGET,4),Material.IRON_ORE,0.7F,1200);
        Bukkit.addRecipe(overrideRecipe);
        getInstance().getLogger().info("Recipe "+overrideRecipe.getKey()+" has been loaded.");
    }

    @EventHandler(ignoreCancelled = true)
    public void onOreBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;
        if (event.getBlock().getType() != Material.IRON_ORE)
            return;
        
        event.setDropItems(false);
        
        if (random.nextInt(100) < 3) {
            event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.ITEM_SHIELD_BREAK, 1F, 1F);
            return;
        }
        
        int amount = 0;
        int rand = random.nextInt(9);
        int stoneDustAmount = 0;
        switch (event.getPlayer().getInventory().getItemInMainHand().getType()) {
            case WOODEN_PICKAXE:
                amount = rand < 4 ? 0 : 1; // 40% nothing, 60% one
                stoneDustAmount = 1;
                break;
            case STONE_PICKAXE:
                amount = rand < 2 ? 0 : (rand > 7 ? 2 : 1); // 20% nothing, 60% one, 20% two
                stoneDustAmount = 2;
                break;
            case IRON_PICKAXE:
                amount = 1 + rand < 6 ? 0 : 1; // 1 + 60% extra one
                stoneDustAmount = 2;
                break;
            case DIAMOND_PICKAXE:
                amount = 2 + rand < 2 ? 0 : (rand > 7 ? 2 : 1); // 2 + 20% nothing, 60% one, 20% two
                stoneDustAmount = 1;
                break;
            default:
                break;
        }

        if (amount < 1) {
            event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.ITEM_SHIELD_BREAK, 1F, 1F);
            return;
        }

        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), this.getItemStack("§7铁砂", amount));
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), NewItemManager.getItem("item.stone_dust").getItemStack("§7碎石子", stoneDustAmount));
    }

    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}