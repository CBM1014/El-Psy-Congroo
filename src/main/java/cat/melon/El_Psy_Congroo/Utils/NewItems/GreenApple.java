package cat.melon.El_Psy_Congroo.Utils.NewItems;

import cat.melon.El_Psy_Congroo.Init;
import cat.melon.El_Psy_Congroo.Utils.NewItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;

import java.util.List;
import java.util.Random;

public class GreenApple extends NewItem{
    Random random = new Random();

    public GreenApple(Init instance) {
        super(instance, Material.APPLE, "ElNewItem.green_apple", (short)1, null);
    }

    @EventHandler
    public void onAppleDrop(LeavesDecayEvent event){
        if(random.nextInt(20)==5){
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),this.getItem());
        }
    }
}
