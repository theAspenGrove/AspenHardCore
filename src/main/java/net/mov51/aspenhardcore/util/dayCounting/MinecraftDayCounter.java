package net.mov51.aspenhardcore.util.dayCounting;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static net.mov51.aspenhardcore.AspenHardCore.configHelper;

public class MinecraftDayCounter {
    private long worldTime;
    private long passedTime;
    private final Plugin plugin;
    public MinecraftDayCounter(Plugin plugin){
        this.plugin = plugin;
    }

    public void start(){
        new BukkitRunnable() {
            @Override
            public void run() {
                addTime(configHelper.getTicksToGameDay());
                if(passedTime > configHelper.getTicksToGameDay()){
                    passedTime = 0;
                    newDay();
                }
            }
        }.runTaskTimer(this.plugin, 0, configHelper.getTimeCheckFrequency());
    }
    public void newDay(){
        System.out.println("DayCounter: New Day!");
    }
    private void addTime(long time){
        passedTime = passedTime + time;
    }
    public void compareTime(long time, World world, long skipAmount){
        if(time >= (worldTime - 24000)){
            System.out.println(skipAmount);
            addTime(skipAmount);
            System.out.println(worldTime + 24000);
            worldTime = world.getGameTime();
        }
    }
}