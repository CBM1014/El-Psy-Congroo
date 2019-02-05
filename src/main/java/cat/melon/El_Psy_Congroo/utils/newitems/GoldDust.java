package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice.ExactChoice;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("deprecation")
public class GoldDust extends NewItem {
    final ItemStack item = this.getItemStack();
    ThreadLocalRandom random = ThreadLocalRandom.current();

    public GoldDust(Init instance) {
        super(instance, Material.GLOWSTONE_DUST, "item.gold_dust", 3);
    }

    @Override
    public void onRegister() {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(), "gold_dust"), new ItemStack(Material.GOLD_NUGGET), new ExactChoice(item), 0.7F, 600);
        Bukkit.addRecipe(furnaceRecipe);
        getInstance().getLogger().info("Recipe "+furnaceRecipe.getKey()+" has been loaded.");
        FurnaceRecipe overrideRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(),"gold_ore"),new ItemStack(Material.GOLD_NUGGET,3),Material.GOLD_ORE,0.7F,1800);
        Bukkit.addRecipe(overrideRecipe);
        getInstance().getLogger().info("Recipe "+overrideRecipe.getKey()+" has been loaded.");
    }

    @EventHandler
    public void onIronOreBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;
        
        if (event.getBlock().getType() != Material.GOLD_ORE)
            return;
        event.setDropItems(false);
        int amount = 0;
        switch (event.getPlayer().getInventory().getItemInMainHand().getType()) {
            case STONE_PICKAXE:
                amount = random.nextInt(1);
                break;
            case IRON_PICKAXE:
                amount = 1 + random.nextInt(1);
                break;
            case DIAMOND_PICKAXE:
                amount = 1 + random.nextInt(2);
                break;
            default:
                break;
        }

        if (amount == 0)
            return;

        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), this.getItemStack(amount));
    }
}
