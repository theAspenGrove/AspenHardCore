package net.mov51.aspenhardcore.events;

import net.mov51.aspenhardcore.util.PlayPeriod;
import net.mov51.aspenhardcore.util.PlayPeriodForDay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static net.mov51.aspenhardcore.AspenHardCore.*;

public class PlayerLeftEvent implements Listener {
    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event){
        Player p = event.getPlayer();
        //get the time the player joined
        long joinedTime = joinedPlayers.get(p.getUniqueId()).getJoinedTime();
        //get the current time the event fired at
        long leftTime = p.getLocation().getWorld().getFullTime();
        //get the time the player was online by subtracting the time they joined from the time they left
        long timePlayed = leftTime - joinedTime;
        //log the time the player was online to console
        logHelper.sendLogInfo("Player left event fired"); //todo remove debug message
        //todo log to database
        logHelper.sendLogInfo("Player joined at: " + joinedTime);
        logHelper.sendLogInfo("Player left at: " + leftTime);
        logHelper.sendLogInfo("Player was online for: " + timePlayed);
        if(playPeriods.containsKey(p.getUniqueId())){
            playPeriods.get(p.getUniqueId()).addPlayPeriod(new PlayPeriod(joinedTime,leftTime,p));
        } else {
            playPeriods.put(p.getUniqueId(),new PlayPeriodForDay(new PlayPeriod(joinedTime,leftTime,p)));
        }
        logHelper.sendLogInfo("Player has played for: " + playPeriods.get(p.getUniqueId()).sumOfTimePlayed());
        //remove player from joinedPlayers
        joinedPlayers.remove(p.getUniqueId());
    }
}
