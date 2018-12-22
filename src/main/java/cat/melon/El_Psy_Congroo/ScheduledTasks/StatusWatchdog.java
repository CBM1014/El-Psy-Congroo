package cat.melon.El_Psy_Congroo.ScheduledTasks;

import cat.melon.El_Psy_Congroo.Init;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class StatusWatchdog extends BukkitRunnable {
    Init instance;

    public StatusWatchdog(Init instance){
        this.instance = instance;
    }

    @Override
    public void run(){
        if(!Bukkit.getOnlinePlayers().isEmpty()){
            instance.setStatus(1);
        }else{
            instance.setStatus(0);
        }
    }
}
