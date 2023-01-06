package net.mov51.aspenhardcore.util;

import net.mov51.aspenhardcore.events.TimeSkip;
import org.bukkit.plugin.Plugin;

import static net.mov51.aspenhardcore.AspenHardCore.*;

public class ConfigHelper {
    private final int hoursToDay;
    private final String dayMethod;
    private final long ticksToGameDay;
    private final long timeCheckFrequency;
    private final long savedPassedGameTime;
    private final long savedPassedServerTime;
    public ConfigHelper(Plugin plugin){
        plugin.saveDefaultConfig();
        this.hoursToDay = plugin.getConfig().getInt("hours-to-a-day");
        this.dayMethod = plugin.getConfig().getString("day-method");
        String worldName = plugin.getConfig().getString("world") == null ? "world" : plugin.getConfig().getString("world");
        assert worldName != null;
        this.ticksToGameDay = plugin.getConfig().getInt("ticks-in-a-game-day");
        this.timeCheckFrequency = plugin.getConfig().getInt("time-check-frequency");
        this.savedPassedGameTime = plugin.getConfig().getLong("passed-game-time");
        this.savedPassedServerTime = plugin.getConfig().getLong("passed-server-time");
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
    public void savePassedServerTime(long time){
        plugin.getConfig().set("passed-server-time", time);
        plugin.saveConfig();
        logHelper.sendLogInfo("Saved passed game time: " + time);
    }
    public long getSavedPassedServerTime(){
        logHelper.sendLogInfo("getting saved passed time...");
        logHelper.sendLogInfo("Passed server time:" + savedPassedServerTime);
        return plugin.getConfig().getLong("passed-server-time");
    }
    public void savePassedGameTime(long time){
        plugin.getConfig().set("passed-game-time", time);
        plugin.saveConfig();
        logHelper.sendLogInfo("Saved passed game time: " + time);
    }
    public long getSavedPassedGameTime() {
        logHelper.sendLogInfo("getting saved gamed passed time...");
        logHelper.sendLogInfo("Passed game time:" + savedPassedGameTime);
        return plugin.getConfig().getLong("passed-server-time");
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
