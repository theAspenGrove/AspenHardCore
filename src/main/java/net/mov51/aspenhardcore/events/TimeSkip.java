package net.mov51.aspenhardcore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;

import static net.mov51.aspenhardcore.AspenHardCore.logHelper;
import static net.mov51.aspenhardcore.AspenHardCore.minecraftDayCounter;

public class TimeSkip implements Listener {
    @EventHandler
    public void onTimeSkip(TimeSkipEvent event){
        logHelper.sendLogInfo("Time skip event fired");
        minecraftDayCounter.setPassedGameTime(event.getWorld().getFullTime());
    }
}
