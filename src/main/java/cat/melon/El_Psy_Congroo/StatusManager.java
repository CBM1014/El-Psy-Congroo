package cat.melon.El_Psy_Congroo;

import cat.melon.El_Psy_Congroo.Player.Player;
import cat.melon.El_Psy_Congroo.ScheduledTasks.EnviromentCamera;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class StatusManager implements Listener {
    private Init instance;
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private EnviromentCamera enviromentCamera;

    public StatusManager(Init instance){
        this.instance = instance;
        for(org.bukkit.entity.Player x: Bukkit.getOnlinePlayers()){
            playerArrayList.add(new Player(x));
        }
        enviromentCamera = new EnviromentCamera(playerArrayList);
        enviromentCamera.runTaskTimer(instance,0L,1L);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        playerArrayList.add(new Player(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        playerArrayList.remove(new Player(event.getPlayer()));
    }

}
