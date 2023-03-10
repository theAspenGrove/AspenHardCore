package net.mov51.aspenhardcore;

import net.mov51.aspenhardcore.commands.NewDayCommand;
import net.mov51.aspenhardcore.events.PlayerJoinedEvent;
import net.mov51.aspenhardcore.events.PlayerLeftEvent;
import net.mov51.aspenhardcore.util.ConfigHelper;
import net.mov51.aspenhardcore.util.database.DatabaseConnector;
import net.mov51.aspenhardcore.util.dayCounting.HourDayCounter;
import net.mov51.aspenhardcore.util.dayCounting.MinecraftDayCounter;
import net.mov51.periderm.logs.AspenLogHelper;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class AspenHardCore extends JavaPlugin {

    public static ConfigHelper configHelper;
    public static AspenHardCore plugin;

    public static MinecraftDayCounter minecraftDayCounter;
    public static HourDayCounter hourDayCounter;
    public static AspenLogHelper logHelper;

    @Override
    public void onEnable() {
        // register logger
        logHelper = new AspenLogHelper(this.getLogger(), this.getDescription().getName());
        // Plugin startup logic
        plugin = this;
        configHelper = new ConfigHelper(plugin);
        minecraftDayCounter = new MinecraftDayCounter(plugin);
        hourDayCounter = new HourDayCounter();
        configHelper.setMethod();
        //register newDayCommand
        Objects.requireNonNull(getCommand("newday")).setExecutor(new NewDayCommand());
        //register events
        getServer().getPluginManager().registerEvents(new PlayerJoinedEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeftEvent(), this);
        DatabaseConnector.connect();
        DatabaseConnector.makeTables();
        //get and verify world
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logHelper.sendLogInfo("Stopping Enabled Day Counters...");
        if(hourDayCounter.isRunning()){
            hourDayCounter.stop();
        }
        if(minecraftDayCounter.isRunning()){
            minecraftDayCounter.stop();
        }
        logHelper.sendLogInfo("Saving all play periods...");

        logHelper.sendLogInfo("Closing Database Connection...");
        DatabaseConnector.close();
        logHelper.sendLogInfo("AspenHardCore has been disabled!");
    }
}
