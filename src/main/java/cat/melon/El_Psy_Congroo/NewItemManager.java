package cat.melon.el_psy_congroo;

import cat.melon.el_psy_congroo.utils.NewItem;
import cat.melon.el_psy_congroo.utils.newitems.DiamondDust;
import cat.melon.el_psy_congroo.utils.newitems.GoldDust;
import cat.melon.el_psy_congroo.utils.newitems.GreenApple;

import java.util.HashMap;
import java.util.Map;

import cat.melon.el_psy_congroo.utils.newitems.IronDust;
import cat.melon.el_psy_congroo.utils.newitems.PreDiamondDust;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class NewItemManager implements Listener{
    //Happy New Year 2019!!!!!!!!!!!!!!!!!
    Map<String, NewItem> newItemMap = new HashMap<>();
    Init instance;

    public NewItemManager(Init instance) {
        this.instance = instance;
        try {
            this.registerNewItems(new GreenApple(instance)/*,new IronDust(instance),new GoldDust(instance),new PreDiamondDust(instance),new DiamondDust(instance)*/);
            //TODO NPE in PreDiamondDust
        } catch (DuplicateRegisterListenerException e) {
            e.printStackTrace();
        }

    }

    public NewItem getItem(String key){
        return newItemMap.get(key);
    }

    private void registerNewItems(NewItem item) throws DuplicateRegisterListenerException {
        if (item.register()) {
            newItemMap.put(item.getNamePath(), item);
            instance.getLogger().info("Custom item "+ item.getNamePath()+" loaded.");
        } else {
            throw new DuplicateRegisterListenerException("This item has been registered its EventListener.");
        }

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
        for(ItemStack x : event.getInventory().getMatrix()){
            if (x == null)
                return;
            NBTItem tmpni = new NBTItem(x);
            if(tmpni.getString("agendaItem")!=null){
                event.setCancelled(true);
            }
        }
    }

    class DuplicateRegisterListenerException extends Exception {
        public DuplicateRegisterListenerException(String msg) {
            super(msg);
        }
    }
}
