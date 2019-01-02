package cat.melon.El_Psy_Congroo.EventListeners;

import cat.melon.El_Psy_Congroo.Init;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Refrigerator implements Listener {
    Init instance;

    public Refrigerator(Init instance) {
        this.instance = instance;
    }

    @EventHandler
    public void allEvents(Event event) {
        if (instance.getStatus() == 1) {
            if (event instanceof Cancellable) {
                ((Cancellable) event).setCancelled(true);
            }
        }
    }
}
