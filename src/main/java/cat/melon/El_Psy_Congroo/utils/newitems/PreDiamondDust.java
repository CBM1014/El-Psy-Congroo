package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.NewItemManager;
import cat.melon.el_psy_congroo.utils.NewItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice.ExactChoice;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("deprecation")
public class PreDiamondDust extends NewItem {
    final ItemStack item = this.getItemStack("§6钻石原矿");
    ThreadLocalRandom random = ThreadLocalRandom.current();

    public PreDiamondDust(Init instance) {
        super(instance, Material.COAL, "item.pre_diamond_dust", 5);
    }

    @Override
    public void onRegister() {
        NamespacedKey key = new NamespacedKey(this.getInstance(), "pre_diamond_dust");
        NewItem newItemDust = NewItemManager.getItem("item.diamond_dust");
        ItemStack itemDust = newItemDust.getItemStack("§b钻石砂");
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(key, itemDust, item.getType(), 0.7F, 2400);
        Bukkit.addRecipe(furnaceRecipe);
        getInstance().getLogger().info("Recipe "+furnaceRecipe.getKey()+" has been loaded.");
    }

    @EventHandler(ignoreCancelled = true)
    public void onOreBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;
        if (event.getBlock().getType() != Material.DIAMOND_ORE)
            return;
        
        event.setDropItems(false);
        
        if (random.nextInt(100) < 5) {
            event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.ITEM_SHIELD_BREAK, 1F, 1F);
            return;
        }
        
        int amount = 0;
        int stoneDustAmount = 1;
        switch (event.getPlayer().getInventory().getItemInMainHand().getType()) {
            case IRON_PICKAXE:
                amount = 1 + random.nextInt(2);
                stoneDustAmount = 2;
                break;
            case DIAMOND_PICKAXE:
                amount = 1 + random.nextInt(3);
                stoneDustAmount = 1;
                break;
            default:
                break;
        }

        if (amount == 0)
            return;

        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), this.getItemStack("§6钻石原矿", amount));
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), NewItemManager.getItem("item.stone_dust").getItemStack("§7碎石子", stoneDustAmount));
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onBurn(FurnaceBurnEvent event) {
        if (event.getFuel().isSimilar(item))
            event.setCancelled(true);
    }

    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}
