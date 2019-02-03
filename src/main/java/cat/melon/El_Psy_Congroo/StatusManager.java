package cat.melon.El_Psy_Congroo;

import cat.melon.El_Psy_Congroo.Player.Player;
import cat.melon.El_Psy_Congroo.ScheduledTasks.EnviromentCamera;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatusManager implements Listener {
    private Init instance;
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private Map<UUID,Player> playerMap = new HashMap<>();
    private EnviromentCamera enviromentCamera;

    protected StatusManager(Init instance){
        this.instance = instance;
        for(org.bukkit.entity.Player x: Bukkit.getOnlinePlayers()){
            Player tmpp = new Player(x);
            playerArrayList.add(tmpp);
            playerMap.put(x.getUniqueId(),tmpp);
        }
        enviromentCamera = new EnviromentCamera(playerArrayList);
        enviromentCamera.runTaskTimer(instance,0L,1L);
    }

    public Player getPlayer(UUID uuid){
        return playerMap.get(uuid);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player tmpp = new Player(event.getPlayer());
        playerArrayList.add(tmpp);
        playerMap.put(tmpp.getUUID(),tmpp);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player tmpp = new Player(event.getPlayer());
        playerArrayList.remove(tmpp);
        playerMap.remove(tmpp.getUUID());
    }

}
