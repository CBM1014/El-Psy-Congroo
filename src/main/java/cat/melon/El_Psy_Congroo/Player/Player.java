package cat.melon.El_Psy_Congroo.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Player {
    private org.bukkit.entity.Player player;
    private Map<Status, Double> playerStatus = new HashMap();

    public Player(org.bukkit.entity.Player player) {
        this.player = player;
    }

    public UUID getUUID() {
        return player.getUniqueId();
    }

    public double getPlayerState(Status status) {
        return playerStatus.get(status);
    }


}
