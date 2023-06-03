package bukkit.fr.arnaud.chestry.hub.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PreLoginEvent implements Listener {

    //@EventHandler
    public void onLogin(PlayerLoginEvent e) {

        if(!e.getHostname().split(":")[0].equalsIgnoreCase("154.49.220.16")
                || !e.getHostname().split(":")[0].equalsIgnoreCase("chestry.io") ) {
            e.setKickMessage("You're only allowed to access the server without the proper ip");
            e.setResult(PlayerLoginEvent.Result.KICK_FULL);
        }
    }
}
