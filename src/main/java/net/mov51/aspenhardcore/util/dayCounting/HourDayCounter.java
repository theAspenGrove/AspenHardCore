package net.mov51.aspenhardcore.util.dayCounting;

import java.util.Timer;

import static net.mov51.aspenhardcore.AspenHardCore.configHelper;
import static net.mov51.aspenhardcore.AspenHardCore.logHelper;
import static net.mov51.aspenhardcore.util.dayCounting.DayCounter.newDay;

public class HourDayCounter {
    Timer watchTimer = new Timer();;
    Timer hourTimer = new Timer();
    private boolean isRunning = false;
    protected static long passedServerTime;
    public HourDayCounter(){

    }
    public void start(){
        passedServerTime = configHelper.getSavedPassedServerTime();
        watchTimer.schedule(new WatchTimeTask(), 0, configHelper.getTimeCheckFrequency());
        hourTimer.schedule(new HourDayTask(), (getPeriodInHours() - getPassedTime()), getPeriodInHours());
        isRunning = true;
    }
    public void stop(){
        logHelper.sendLogInfo("stopping server day counter...");
        this.watchTimer.cancel();
        this.hourTimer.cancel();
        isRunning = false;
        configHelper.savePassedServerTime(passedServerTime);
    }
    private static class WatchTimeTask extends java.util.TimerTask{
        @Override
        public void run() {
            passedServerTime = passedServerTime + configHelper.getTimeCheckFrequency();
        }
    }
    private static class HourDayTask extends java.util.TimerTask {
        @Override
        public void run() {
            newDay();
        }
    }
    private long getPeriodInHours(){
        return configHelper.getHoursToDay() * 3600000L;
    }

    public long getPassedTime() {
        logHelper.sendLogInfo("Passed Time: " + passedServerTime);
        return passedServerTime;
    }

    public void resetPassedTime(){
        passedServerTime = 0;
    }
    public boolean isRunning(){
        return isRunning;
    }
}
