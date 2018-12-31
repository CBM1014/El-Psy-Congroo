package cat.melon.El_Psy_Congroo;

import cat.melon.El_Psy_Congroo.Init;
import cat.melon.El_Psy_Congroo.ScheduledTasks.StatusWatchdog;
import cat.melon.El_Psy_Congroo.ScheduledTasks.WorldTickTimer;
import cat.melon.El_Psy_Congroo.Utils.lib.Season;
import org.bukkit.scheduler.BukkitTask;

public class SeasonManager {
    private Init instance;
    private int time;
    private int seasontimer;
    BukkitTask seasonTimer;
    BukkitTask statusWatchDog;

    public SeasonManager(Init instance) {
        this.instance = instance;
        this.time = instance.getBukkitFileConfiguration().getInt("time", 0);
        this.time = instance.getBukkitFileConfiguration().getInt("seasons", 0);
        seasonTimer = new WorldTickTimer(this).runTaskTimer(instance, 0L, 1L);
        statusWatchDog = new StatusWatchdog(instance).runTaskTimer(instance, 0L, 1 * 20L);
    }

    public int getTime() {
        return time;
    }

    private void resetTimer() {
        time = 0;
    }

    public void timelPlusOne() {
        if (time % 3072000 == 0) {
            this.goToNextSeason();
            this.resetTimer();
        }
    }

    public void goToNextSeason() {
        seasontimer++;
        setSeason(Season.getSeason(seasontimer % 4));
    }

    private void setSeason(Season season) {
        //unfinished
        switch (season) {
            case SPRING:
                break;
            case SUMMER:
                break;
            case AUTUMN:
                break;
            case WINTER:
                break;
        }
    }

    public Init getInstance() {
        return instance;
    }
}
