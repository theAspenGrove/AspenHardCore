package net.mov51.aspenhardcore.util.dayCounting;

import java.util.Timer;

import static net.mov51.aspenhardcore.AspenHardCore.configHelper;
import static net.mov51.aspenhardcore.AspenHardCore.logHelper;

public class HourDayCounter {
    public HourDayCounter(){

    }
    public void start(){
        Timer timer = new Timer();
        //runs after the first period and then every period after that
        //currently fails if the server restarts mid day
        //todo track progress on the timer and the cycle will be the remaining time on the day
        timer.schedule(new HourDayTask(), getPeriod(), getPeriod());
    }
    private static class HourDayTask extends java.util.TimerTask {
        @Override
        public void run() {
            logHelper.sendLogInfo("New Day");
        }
    }
    private static long getPeriod(){
        return configHelper.getHoursToDay() * 3600000L;
    }
}
