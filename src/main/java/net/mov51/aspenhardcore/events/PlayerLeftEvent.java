package net.mov51.aspenhardcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static net.mov51.aspenhardcore.AspenHardCore.*;
import static net.mov51.aspenhardcore.util.database.DatabaseConnector.*;
import static net.mov51.aspenhardcore.util.dayCounting.DayCounter.getDay;

public class PlayerLeftEvent implements Listener {
    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event){
        Player p = event.getPlayer();
        finishPlayPeriod(p.getUniqueId(),p.getLocation().getWorld().getFullTime());
        //log the time the player was online to console
        logHelper.sendLogInfo("Player left event fired"); //todo remove debug message
        //todo log to database
        logHelper.sendLogInfo("Player joined at: " + getLastPlayPeriod(p.getUniqueId()).getJoinedTime());
        logHelper.sendLogInfo("Player left at: " + getLastPlayPeriod(p.getUniqueId()).getLeftTime());
        logHelper.sendLogInfo("Player was online for: " + getLastPlayPeriod(p.getUniqueId()).getTimePlayed());

        logHelper.sendLogInfo("Player has played for: " + getSumOfTimeDay(p.getUniqueId(),getDay()) + " ticks in the last day.");
    }
}
