package cat.melon.el_psy_congroo.player;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Player {
    private org.bukkit.entity.Player player;
    private Map<Status, Double> playerStatus = new HashMap<>();
    private Picture snapshot = new Picture(this);
    private boolean plus1sMode = false;

    public Player(org.bukkit.entity.Player player) {
        this.player = player;
    }

    public UUID getUUID() {
        return player.getUniqueId();
    }

    public double getPlayerState(Status status) {
        return playerStatus.get(status);
    }

    public Location getLocation(){
        return player.getLocation();
    }

    public void setPlus1sMode(boolean plus1sMode) {
        this.plus1sMode = plus1sMode;
    }

    public boolean isPlus1sMode(){
        return plus1sMode;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        } else {
            return this.getUUID().toString().equalsIgnoreCase(((Player) obj).getUUID().toString());
        }
    }

    @Override
    public int hashCode() {
        return this.getUUID().hashCode();
    }

    public Picture getPicture() {
        return snapshot;
    }
    
    public int getTemperature() {
        return 0;
    }
}
