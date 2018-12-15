package cat.melon.El_Psy_Congroo.ScheduledTasks;

import cat.melon.El_Psy_Congroo.Utils.SeasonManager;
import cat.melon.El_Psy_Congroo.Utils.lib.Seasons;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldTickTimer extends BukkitRunnable {
    SeasonManager seasonManager;
    int tmptime = 0;

    public WorldTickTimer(SeasonManager seasonManager){
        this.seasonManager=seasonManager;
    }

    public void run() {
        tmptime = seasonManager.timelPlusOne();
        if (tmptime % 3072000 == 0) {
            seasonManager.setSeason(Seasons.getSeason(tmptime / 3072000 - 1));
        }
    }
}
