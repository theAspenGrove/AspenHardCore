package net.mov51.aspenhardcore.events;

import net.mov51.aspenhardcore.util.JoinedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static net.mov51.aspenhardcore.AspenHardCore.joinedPlayers;

public class PlayerJoinedEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        p.getLocation().getWorld().getFullTime();
        joinedPlayers.put(p.getUniqueId(), new JoinedPlayer(p.getLocation().getWorld().getFullTime()));
    }
}
