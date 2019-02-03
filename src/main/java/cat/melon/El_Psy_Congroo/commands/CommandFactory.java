package cat.melon.el_psy_congroo.commands;

import java.util.Map;

import cat.melon.el_psy_congroo.utils.CaseInsensitiveMap;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;

public class CommandFactory implements Listener {
	// Happy new year @ 2019 command the world
    public final static Map<String, Command> commands = new CaseInsensitiveMap<Command>();
    
    public static void registry(Plugin plugin) {
        put("/locale", new CommandLocale());
        
        Bukkit.getPluginManager().registerEvents(new CommandFactory(), plugin);
    }
    
    public static void put(String name, Command cmd) {
        commands.put(name, cmd);
    }
    
    public static void put(String name, String alias, Command cmd) {
        commands.put(name, cmd);
        commands.put(alias, cmd);
    }
    
    public static void put(String name, String alias, String another, Command cmd) {
        commands.put(name, cmd);
        commands.put(alias, cmd);
        commands.put(another, cmd);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void processCommands(PlayerCommandPreprocessEvent evt) {
        if (evt.isCancelled() || evt.isAsynchronous()) return;
        
        Command command;
        String message = evt.getMessage();
        while (!((command = CommandFactory.commands.get(message)) instanceof Command)) {
            String substring = StringUtils.substringBeforeLast(message, " ");
            if (substring.equals(message) | (message = substring).isEmpty())
                return;
        }
        
        command.run(evt.getPlayer(), StringUtils.substringAfter(evt.getMessage(), message));
        evt.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void processCommands(ServerCommandEvent evt) {
        if (evt.isCancelled() || evt.isAsynchronous()) return;
        
        Command command;
        String message = evt.getCommand();
        while (!((command = CommandFactory.commands.get(message)) instanceof Command)) {
            String substring = StringUtils.substringBeforeLast(message, " ");
            if (substring.equals(message) | (message = substring).isEmpty())
                return;
        }
        
        command.run(evt.getSender(), StringUtils.substringAfter(evt.getCommand(), message));
        evt.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void processCommands(RemoteServerCommandEvent evt) {
        if (evt.isCancelled() || evt.isAsynchronous()) return;
        
        Command command;
        String message = evt.getCommand();
        while (!((command = CommandFactory.commands.get(message)) instanceof Command)) {
            String substring = StringUtils.substringBeforeLast(message, " ");
            if (substring.equals(message) | (message = substring).isEmpty())
                return;
        }
        
        command.run(evt.getSender(), StringUtils.substringAfter(evt.getCommand(), message));
        evt.setCancelled(true);
    }
}
