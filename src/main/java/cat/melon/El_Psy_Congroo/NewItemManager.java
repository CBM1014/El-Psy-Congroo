package cat.melon.El_Psy_Congroo;

import cat.melon.El_Psy_Congroo.Utils.NewItem;
import cat.melon.El_Psy_Congroo.Utils.NewItems.GreenApple;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewItemManager {
    Map<String, NewItem> newItemMap = new HashMap<>();
    Init instance;

    public NewItemManager(Init instance) {
        this.instance = instance;

        this.registerNewItems(new GreenApple(instance));

    }

    private void registerNewItems(NewItem item) {
        newItemMap.put(item.getNamePath(), item);
    }

    private void registerNewItems(NewItem... items) {
        for (NewItem x : items) {
            newItemMap.put(x.getNamePath(), x);
        }
    }
}
