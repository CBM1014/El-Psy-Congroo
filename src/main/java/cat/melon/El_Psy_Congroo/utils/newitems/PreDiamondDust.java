package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.RecipeChoice.ExactChoice;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("deprecation")
public class PreDiamondDust extends NewItem {
    final ItemStack item = this.getItem();
    ShapedRecipe diamond;
    ThreadLocalRandom random = ThreadLocalRandom.current();

    public PreDiamondDust(Init instance) {
        super(instance, Material.COAL, "§b钻石原矿", 5);
    }

    @Override
    public void onRegister() {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(new NamespacedKey(this.getInstance(), "pre_diamond_dust"), new DiamondDust(getInstance()).getItem(), new ExactChoice(item), 0.7F, 2400);
        Bukkit.addRecipe(furnaceRecipe);
    }

    @EventHandler
    public void onIronOreBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.DIAMOND_ORE)
            return;
        event.setDropItems(false);
        int amount = 0;
        switch (event.getPlayer().getInventory().getItemInMainHand().getType()) {
            case IRON_PICKAXE:
                amount = 1 + random.nextInt(2);
                break;
            case DIAMOND_PICKAXE:
                amount = 1 + random.nextInt(3);
                break;
            default:
                break;
        }

        if (amount == 0)
            return;

        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), this.getItem(amount));
    }
}
