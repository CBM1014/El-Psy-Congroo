package cat.melon.El_Psy_Congroo.Utils;

import cat.melon.El_Psy_Congroo.Init;
import cat.melon.El_Psy_Congroo.ScheduledTasks.WorldTickTimer;
import cat.melon.El_Psy_Congroo.Utils.lib.Seasons;
import org.bukkit.scheduler.BukkitTask;

public class SeasonManager {
    private Init instance;
    private int time;

    public SeasonManager(Init instance){
        this.instance = instance;
        this.time = instance.getBukkitFileConfiguration().getInt("time");
        BukkitTask seasonTimer = new WorldTickTimer(this).runTaskTimer(instance,0,1);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }

    public int timelPlusOne(){
        return ++time;
    }

    public void setSeason(Seasons season){

    }
}
