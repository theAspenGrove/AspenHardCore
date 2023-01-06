package net.mov51.aspenhardcore.util.dayCounting;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static net.mov51.aspenhardcore.AspenHardCore.configHelper;
import static net.mov51.aspenhardcore.AspenHardCore.logHelper;
import static net.mov51.aspenhardcore.util.dayCounting.DayCounter.newDay;

public class MinecraftDayCounter {
    private static long passedGameTime;
    private final Plugin plugin;
    private boolean isRunning = false;
    public MinecraftDayCounter(Plugin plugin){
        this.plugin = plugin;
    }

    public void start(){
        passedGameTime = configHelper.getSavedPassedGameTime();
        this.isRunning = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                addTime((configHelper.getTimeCheckFrequency()));
                if(passedGameTime > configHelper.getTicksToGameDay()){
                    newDay();
                }
            }
        }.runTaskTimer(this.plugin, 0, configHelper.getTimeCheckFrequency());
    }
    public void stop(){
        logHelper.sendLogInfo("stopping game day counter...");
        this.isRunning = false;
        configHelper.savePassedGameTime(passedGameTime);
    }
    private void addTime(long time){
        passedGameTime = passedGameTime + time;
    }
    public void setPassedGameTime(long time){
        passedGameTime = time;
    }
    public boolean isRunning(){
        return isRunning;
    }
    public void resetPassedTime(){
        passedGameTime = 0;
    }
}
