package net.mov51.aspenhardcore.util;

import net.mov51.aspenhardcore.events.TimeSkip;
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
    public void savePassedTime(long time){
        plugin.getConfig().set("passed-time", time);
        plugin.saveConfig();
    }
    public long getSavedPassedTime(){
        System.out.println("getting saved passed time...");
        System.out.println(plugin.getConfig().getLong("passed-time"));
        return plugin.getConfig().getLong("passed-time");
    }
    public World getWorld(){
        return world;
    }
    public void setMethod(){
        switch (this.getDayMethod()){
            case "hours":
                logHelper.sendLogInfo("Using hours to  \"day\" method");
                logHelper.sendLogInfo("Every " + this.getHoursToDay() + " hours is a \"day\"");
                hourDayCounter.start();
                break;
            case "game-day":
                logHelper.sendLogInfo("Using game day to \"day\" method");
                logHelper.sendLogInfo("Every minecraft day will ce counted as a \"day\"");
                plugin.getServer().getPluginManager().registerEvents(new TimeSkip(), plugin);
                minecraftDayCounter.start();
                break;
            case "command":
                logHelper.sendLogInfo("Using command to \"day\" method");
                logHelper.sendLogInfo("Day counter disabled");
                break;
            default:
                logHelper.sendLogSevere("Invalid day method!");
                logHelper.sendLogSevere("Please check your config.yml");
        }
    }


}
