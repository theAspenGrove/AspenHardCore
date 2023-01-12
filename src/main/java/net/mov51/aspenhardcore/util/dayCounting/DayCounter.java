package net.mov51.aspenhardcore.util.dayCounting;

import org.bukkit.Bukkit;

import java.util.UUID;

import static net.mov51.aspenhardcore.AspenHardCore.*;

public class DayCounter {
    private static long day = 0;
    public static void newDay(){
        logHelper.sendLogInfo("---New Day!---");
        day++;
        //todo sum up all the time played by each player
        //todo reset the playPeriods
        //iterate over PlayPeriodForDay
        for(UUID uuid : playPeriods.keySet()){
            //iterate over PlayPeriod
            logHelper.sendLogInfo(Bukkit.getOfflinePlayer(uuid).getName() + " has played for " + playPeriods.get(uuid).sumOfTimePlayed() + " ticks in the last day.");
            playPeriods.get(uuid).clearPlayPeriods();
        }


        if(hourDayCounter.isRunning()){
            logHelper.sendLogInfo("Resetting passed time");
            hourDayCounter.resetPassedTime();
        }
        if(minecraftDayCounter.isRunning()){
            logHelper.sendLogInfo("resetting passed time");
            minecraftDayCounter.resetPassedTime();
        }
    }
    public static long getDay(){
        return day;
    }
}
