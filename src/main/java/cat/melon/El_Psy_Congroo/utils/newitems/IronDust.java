package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice.ExactChoice;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("deprecation")
public class IronDust extends NewItem {
    final ItemStack item = this.getItemStack();
    Random random = ThreadLocalRandom.current();

    public IronDust(Init instance) {
        super(instance, Material.LIGHT_GRAY_DYE, "item.iron_dust", 2);
    }

    @Override
    public void onRegister() {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(), "iron_dust"), new ItemStack(Material.IRON_NUGGET), new ExactChoice(item), 0.7F, 400);
        Bukkit.addRecipe(furnaceRecipe);
        getInstance().getLogger().info("Recipe "+furnaceRecipe.getKey()+" has been loaded.");
        FurnaceRecipe overrideRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(),"iron_ore"),new ItemStack(Material.IRON_NUGGET,4),Material.IRON_ORE,0.7F,1200);
        Bukkit.addRecipe(overrideRecipe);
        getInstance().getLogger().info("Recipe "+overrideRecipe.getKey()+" has been loaded.");
    }

    @EventHandler(ignoreCancelled = true)
    public void onIronOreBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;
        
        if (event.getBlock().getType() != Material.IRON_ORE)
            return;
        event.setDropItems(false);
        int amount = 0;
        int rand = random.nextInt(9);
        switch (event.getPlayer().getInventory().getItemInMainHand().getType()) {
            case WOODEN_PICKAXE:
                amount =rand < 4 ? 0 : 1; // 40% nothing, 60% one
                break;
            case STONE_PICKAXE:
                amount = rand < 2 ? 0 : (rand > 7 ? 2 : 1); // 20% nothing, 60% one, 20% two
                break;
            case IRON_PICKAXE:
                amount = 1 + rand < 6 ? 0 : 1; // 1 + 60% extra one
                break;
            case DIAMOND_PICKAXE:
                amount = 2 + rand < 2 ? 0 : (rand > 7 ? 2 : 1); // 2 + 20% nothing, 60% one, 20% two
                break;
            default:
                break;
        }

        if (amount < 1) {
            event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.ITEM_SHIELD_BREAK, 1F, 1F);
            return;
        }

        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), this.getItemStack(amount));
    }
}