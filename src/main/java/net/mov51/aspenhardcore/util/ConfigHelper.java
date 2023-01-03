package net.mov51.aspenhardcore.util;

import net.mov51.aspenhardcore.events.NewMinecraftDay;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import static net.mov51.aspenhardcore.AspenHardCore.*;

public class ConfigHelper {
    private final int hoursToDay;
    private final String dayMethod;
    private final long ticksToGameDay;
    private final long timeCheckFrequency;
    private final World world;
    public ConfigHelper(Plugin plugin){
        plugin.saveDefaultConfig();
        this.hoursToDay = plugin.getConfig().getInt("hours-to-a-day");
        this.dayMethod = plugin.getConfig().getString("day-method");
        String worldName = plugin.getConfig().getString("world") == null ? "world" : plugin.getConfig().getString("world");
        assert worldName != null;
        this.world = plugin.getServer().getWorld(worldName);
        this.ticksToGameDay = plugin.getConfig().getInt("ticks-in-a-game-day");
        this.timeCheckFrequency = plugin.getConfig().getInt("time-check-frequency");
        if (world == null){
            System.out.println("World not found!");
        }
    }
    public int getHoursToDay(){
        return hoursToDay;
    }
    public String getDayMethod(){
        return dayMethod;
    }
    public Long getTicksToGameDay(){
        return ticksToGameDay;
    }
    public Long getTimeCheckFrequency(){
        return timeCheckFrequency;
    }
    public World getWorld(){
        return world;
    }
    public void setMethod(){
        switch (dayMethod){
            case "hours":
                System.out.println("Hours");
                hourDayCounter.start();
                break;
            case "game-day":
                System.out.println("Game Day");
                plugin.getServer().getPluginManager().registerEvents(new NewMinecraftDay(), plugin);
                minecraftDayCounter.start();
                break;
            case "command":
                break;
            default:
                System.out.println("Invalid day method!");
        }
    }


}
