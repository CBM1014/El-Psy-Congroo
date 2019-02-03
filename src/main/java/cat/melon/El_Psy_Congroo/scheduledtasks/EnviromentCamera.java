package cat.melon.el_psy_congroo.scheduledtasks;

import cat.melon.el_psy_congroo.player.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class EnviromentCamera extends BukkitRunnable {
    private ArrayList<Player> playerArrayList;
    int index=0;
    public EnviromentCamera(ArrayList<Player> playerArrayList) {
        this.playerArrayList = playerArrayList;
    }

    @Override
    public void run() {
        if(playerArrayList.isEmpty()){
            return;
        }
        if(index>playerArrayList.size()){
            index = 0;
        }
        Player tmp = playerArrayList.get(index);
        //run ansyc task here
    }

}
