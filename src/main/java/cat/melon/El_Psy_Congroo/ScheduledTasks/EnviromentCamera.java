package cat.melon.El_Psy_Congroo.ScheduledTasks;

import cat.melon.El_Psy_Congroo.Player.Player;
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

    }
}
