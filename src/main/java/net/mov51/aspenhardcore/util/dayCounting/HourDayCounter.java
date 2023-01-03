package net.mov51.aspenhardcore.util.dayCounting;

import java.util.Timer;

import static net.mov51.aspenhardcore.AspenHardCore.logHelper;

public class HourDayCounter {
    public HourDayCounter(){

    }
    public void start(){
        Timer timer = new Timer();
        timer.schedule(new HourDayTask(), 0, 3600000);
    }
    private static class HourDayTask extends java.util.TimerTask {
        @Override
        public void run() {
            logHelper.sendLogInfo("New Day");
        }
    }
}
