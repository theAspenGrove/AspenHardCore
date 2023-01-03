package net.mov51.aspenhardcore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;

import static net.mov51.aspenhardcore.AspenHardCore.minecraftDayCounter;

public class TimeSkip implements Listener {
    @EventHandler
    public void onTimeSkip(TimeSkipEvent event){
        System.out.println(event.getWorld().getGameTime());
        minecraftDayCounter.compareTime(event.getWorld().getFullTime(),event.getWorld(), event.getSkipAmount());
    }
}
