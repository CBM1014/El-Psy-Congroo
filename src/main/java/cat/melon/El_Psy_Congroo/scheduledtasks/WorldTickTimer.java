package cat.melon.el_psy_congroo.scheduledtasks;

import cat.melon.el_psy_congroo.SeasonManager;
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
