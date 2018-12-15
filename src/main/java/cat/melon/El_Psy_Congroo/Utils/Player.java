package cat.melon.El_Psy_Congroo.Utils;

import java.util.UUID;

public class Player {
    private UUID uuid;
    private int temperature = 25;
    private int humidity = 50;
    private int power = 100;
    private int thirst = 100;

    public Player(org.bukkit.entity.Player player) {
        this.uuid = player.getUniqueId();
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPower() {
        return power;
    }

    public int getThirst() {
        return thirst;
    }
}
