package net.mov51.aspenhardcore.util.dayCounting;

import net.mov51.aspenhardcore.util.PlayPeriod;

import java.util.List;
import java.util.UUID;

import static net.mov51.aspenhardcore.AspenHardCore.*;
import static net.mov51.aspenhardcore.util.database.DatabaseConnector.*;

public class DayCounter {
    private static long day = 0;
    public static void newDay(){
        logHelper.sendLogInfo("---New Day!---");
        sumAllUsers();
        day++;
        startNewDayForAllPlayers(configHelper.getWorld().getFullTime());


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
    private static void startNewDayForAllPlayers(Long time){
        List<PlayPeriod> playPeriods = getAllActivePlayPeriods();
        if(playPeriods != null && playPeriods.size() > 0){
            for(PlayPeriod playPeriod : playPeriods){
                finishPlayPeriod(playPeriod.getUUID(),time);
                addNewPlayPeriod(playPeriod.getUUID(),time,getDay());
            }
        }
    }
    private static void sumAllUsers(){
        List<UUID> uuidList = getUUIDsOnDay(getDay());
        if(uuidList != null && uuidList.size() > 0){
            for(UUID uuid : uuidList){
                addNewDaySum(uuid,getDay(),getSumOfTimeDay(uuid,getDay()));
            }
        }
    }
}
