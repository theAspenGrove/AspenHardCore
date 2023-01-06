package net.mov51.aspenhardcore.util.dayCounting;

import static net.mov51.aspenhardcore.AspenHardCore.*;

public class DayCounter {
    public static void newDay(){
        logHelper.sendLogInfo("---New Day!---");
        if(hourDayCounter.isRunning()){
            logHelper.sendLogInfo("Resetting passed time");
            hourDayCounter.resetPassedTime();
        }
        if(minecraftDayCounter.isRunning()){
            logHelper.sendLogInfo("resetting passed time");
            minecraftDayCounter.resetPassedTime();
        }
    }
}
