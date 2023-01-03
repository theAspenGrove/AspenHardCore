package net.mov51.aspenhardcore.util.dayCounting;

import java.util.Timer;

import static net.mov51.aspenhardcore.AspenHardCore.configHelper;
import static net.mov51.aspenhardcore.AspenHardCore.logHelper;

public class HourDayCounter {
    Timer watchTimer = new Timer();
    private final WatchTimeTask watchTimeTask = new WatchTimeTask();
    Timer hourTimer = new Timer();
    private boolean isRunning = false;
    public HourDayCounter(){

    }
    public void start(){
        watchTimer.schedule(this.watchTimeTask, 0, configHelper.getTimeCheckFrequency());
        hourTimer.schedule(new HourDayTask(), (getPeriod() - getPassedTime()), getPeriod());
        isRunning = true;
    }
    public void stop(){
        this.watchTimer.cancel();
        this.hourTimer.cancel();
        isRunning = false;
    }
    private static class WatchTimeTask extends java.util.TimerTask{
        protected long passedTime = configHelper.getSavedPassedTime();
        @Override
        public void run() {
            this.passedTime = this.passedTime + configHelper.getTimeCheckFrequency();
        }
    }
    private static class HourDayTask extends java.util.TimerTask {
        @Override
        public void run() {
            logHelper.sendLogInfo("New Day");
        }
    }
    private long getPeriod(){
        return configHelper.getHoursToDay() * 3600000L;
    }

    public long getPassedTime() {
        logHelper.sendLogInfo("Passed Time: " + watchTimeTask.passedTime);
        return this.watchTimeTask.passedTime;
    }
    public boolean isRunning(){
        return isRunning;
    }
}
