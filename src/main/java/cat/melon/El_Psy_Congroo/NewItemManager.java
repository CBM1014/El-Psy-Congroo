package cat.melon.El_Psy_Congroo;

import cat.melon.El_Psy_Congroo.Utils.NewItem;
import cat.melon.El_Psy_Congroo.Utils.NewItems.GreenApple;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class NewItemManager {
    //Happy New Year 2019!!!!!!!!!!!!!!!!!
    Map<String, NewItem> newItemMap = new HashMap<>();
    Init instance;

    public NewItemManager(Init instance) {
        this.instance = instance;
        try {
            Listener greenApple = new GreenApple(instance);
            Bukkit.getPluginManager().registerEvents(greenApple, instance);
            this.registerNewItems();

        } catch (DuplicateRegisterListenerException e) {
            e.printStackTrace();
        }

    }

    private void registerNewItems(NewItem item) throws DuplicateRegisterListenerException {
        if (item.registerEventListeners()) {
            newItemMap.put(item.getNamePath(), item);
        } else {
            throw new DuplicateRegisterListenerException("This item has been registered its EventListener.");
        }

    }

    private void registerNewItems(NewItem... items) throws DuplicateRegisterListenerException {
        for (NewItem x : items) {
            if (x.registerEventListeners()) {
                newItemMap.put(x.getNamePath(), x);
            } else {
                throw new DuplicateRegisterListenerException("This item has been registered its EventListener.");
            }
        }
    }

    class DuplicateRegisterListenerException extends Exception {
        public DuplicateRegisterListenerException(String msg) {
            super(msg);
        }
    }
}
