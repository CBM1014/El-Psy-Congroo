package cat.melon.El_Psy_Congroo.ScheduledTasks;

import cat.melon.El_Psy_Congroo.SeasonManager;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldTickTimer extends BukkitRunnable {
    SeasonManager seasonManager;

    public WorldTickTimer(SeasonManager seasonManager){
        this.seasonManager=seasonManager;
    }

    public void run() {
        if(seasonManager.getInstance().getStatus()!=0){
            seasonManager.timelPlusOne();
        }
    }
}
