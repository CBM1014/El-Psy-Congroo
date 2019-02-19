package cat.melon.el_psy_congroo.utils.newitems;

import cat.melon.el_psy_congroo.Init;
import cat.melon.el_psy_congroo.utils.NewItem;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import com.google.common.collect.Lists;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EnchantedBookSoulBind extends NewItem {
    final ItemStack item = this.getItemStack("§e附魔书", Lists.newArrayList("§7灵魂绑定 I"), 1, false);
    Random random = ThreadLocalRandom.current();

    public EnchantedBookSoulBind(Init instance) {
        super(instance, Material.ENCHANTED_BOOK, "item.soul_bind", 8);
    }

    @Override
    public void onRegister() {
        
    }
    
    @Override
    public ItemStack getSampleItem() {
        return item;
    }
}