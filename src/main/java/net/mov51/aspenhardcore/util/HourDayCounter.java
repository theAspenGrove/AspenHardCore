package net.mov51.aspenhardcore.util;

import java.util.Timer;

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
            System.out.println("New Hour!");
        }
    }
}
