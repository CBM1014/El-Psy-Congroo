package cat.melon.el_psy_congroo;

import cat.melon.el_psy_congroo.utils.NewItem;
import cat.melon.el_psy_congroo.utils.lib.RecipesOverwriter;
import cat.melon.el_psy_congroo.utils.newitems.BlackTangyuan;
import cat.melon.el_psy_congroo.utils.newitems.DiamondDust;
import cat.melon.el_psy_congroo.utils.newitems.EnchantedBookSoulBind;
import cat.melon.el_psy_congroo.utils.newitems.GoldDust;
import cat.melon.el_psy_congroo.utils.newitems.GreenApple;
import cat.melon.el_psy_congroo.utils.newitems.GreenTangyuan;
import cat.melon.el_psy_congroo.utils.newitems.IronDust;
import cat.melon.el_psy_congroo.utils.newitems.PreDiamondDust;
import cat.melon.el_psy_congroo.utils.newitems.QuartzDust;
import cat.melon.el_psy_congroo.utils.newitems.RedTangyuan;
import cat.melon.el_psy_congroo.utils.newitems.StoneDust;
import cat.melon.el_psy_congroo.utils.newitems.WhiteTangyuan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tr7zw.itemnbtapi.NBTItem;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import com.google.common.collect.Lists;

public class NewItemManager implements Listener {
    //Happy New Year 2019!!!!!!!!!!!!!!!!!
    private static final Map<String, NewItem> newItemMap = new HashMap<>();
    Init instance;

    public NewItemManager(Init instance) {
        this.instance = instance;
        
        //CraftingUtil.initialize(instance);
        RecipesOverwriter overwriter = new RecipesOverwriter(instance);
        
        try {
            this.registerNewItems(new GreenApple(instance),
                                     new StoneDust(instance),
                                     new QuartzDust(instance),
                                     new IronDust(instance),
                                     new GoldDust(instance),
                                     new DiamondDust(instance), new PreDiamondDust(instance),
                                     new EnchantedBookSoulBind(instance),
                                     new WhiteTangyuan(instance),
                                     new GreenTangyuan(instance),
                                     new BlackTangyuan(instance),
                                     new RedTangyuan(instance));
            // do not forgot prefix! (item.)
            // the order is important!
        } catch (DuplicateRegisterListenerException e) {
            e.printStackTrace();
        }
        
        List<ItemStack> sampleItems = Lists.newArrayList();
        for (NewItem item : newItemMap.values())
            sampleItems.add(item.getSampleItem());
        overwriter.overwriteVanillaRecipes(sampleItems);
        overwriter.overwriteVanillaRecipes(sampleItems);
        
        Bukkit.getPluginManager().registerEvents(overwriter, instance);
        //overrideVanillaExactly();
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
}
