package net.mov51.aspenhardcore.util;

import net.mov51.aspenhardcore.events.TimeSkip;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

import static net.mov51.aspenhardcore.AspenHardCore.*;

public class ConfigHelper {
    private final int hoursToDay;
    private final String dayMethod;
    private final long ticksToGameDay;
    private final long timeCheckFrequency;
    private long savedPassedGameTime;
    private long savedPassedServerTime;
    public ConfigHelper(Plugin plugin){
        //get config values
        Configuration config = plugin.getConfig();
        //The number of hours in a day
        //should never be 0 but if it is, set it to the default of 24
        this.hoursToDay = config.getInt("hours-to-a-day") != 0 ? config.getInt("hours-to-a-day") : 24;
        //The method used to count "days"
        this.dayMethod = config.getString("day-method") != null ? config.getString("day-method") : "hours";
        //The number of ticks in a game day
        //should never be 0, but if it is, set it to the default of 24000
        this.ticksToGameDay = config.getInt("ticks-in-a-game-day") != 0 ? config.getInt("ticks-in-a-game-day") : 24000;
        //The number of ticks between each time check
        //Should never be 0, but if it is, set it to the default of 600
        this.timeCheckFrequency = config.getInt("time-check-frequency") != 0 ? config.getInt("time-check-frequency") : 600;
        //The number of ticks passed in the game for the game time "day" counter
        this.savedPassedGameTime = config.getLong("passed-game-time");
        //The number of ticks passed in the server for the server time "day" counter
        this.savedPassedServerTime = config.getLong("passed-server-time");
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
        this.savedPassedServerTime = time;
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
        this.savedPassedGameTime = time;
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
                logHelper.sendLogInfo("Using hours to  \"day\" method.");
                logHelper.sendLogInfo("Every " + this.getHoursToDay() + " hours is a \"day\".");
                //start the minecraft day counter when the configuration is set to hours
                hourDayCounter.start();
                break;
            case "game-day":
                logHelper.sendLogInfo("Using game day to \"day\" method.");
                logHelper.sendLogInfo("Every minecraft day will ce counted as a \"day\".");
                //register the timeSkip event for *only* the minecraft day counter
                plugin.getServer().getPluginManager().registerEvents(new TimeSkip(), plugin);
                //start the minecraft day counter when the configuration value is set to game-day
                minecraftDayCounter.start();
                break;
            case "command":
                logHelper.sendLogInfo("Using command to \"day\" method.");
                logHelper.sendLogInfo("Every time the /newday command is run, a \"day\" will be counted.");
                //don't start any counters or event listeners when the configuration is set to hours.
                //the day counter will be started manually by the /day command.
                break;
            default:
                logHelper.sendLogSevere("Invalid day method!");
                logHelper.sendLogSevere("Please check your config.yml");
        }
    }


}
