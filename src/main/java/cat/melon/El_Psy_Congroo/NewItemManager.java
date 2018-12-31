package cat.melon.El_Psy_Congroo;

import cat.melon.El_Psy_Congroo.Utils.NewItem;
import cat.melon.El_Psy_Congroo.Utils.NewItems.GreenApple;
import org.bukkit.Bukkit;
import java.util.List;

public class NewItemManager {
    List<NewItem> newItemList;
    Init instance;

    public NewItemManager(Init instance){
        this.instance = instance;
      newItemList.add(new GreenApple(instance));
      registerEventListeners();
    }

    private void registerEventListeners(){
        for(NewItem x:newItemList){
            Bukkit.getPluginManager().registerEvents(x,instance);
        }
    }

}
