package net.mov51.aspenhardcore;

import net.mov51.aspenhardcore.commands.NewDayCommand;
import net.mov51.aspenhardcore.events.PlayerJoinedEvent;
import net.mov51.aspenhardcore.events.PlayerLeftEvent;
import net.mov51.aspenhardcore.util.ConfigHelper;
import net.mov51.aspenhardcore.util.JoinedPlayer;
import net.mov51.aspenhardcore.util.PlayPeriodForDay;
import net.mov51.aspenhardcore.util.database.DatabaseConnector;
import net.mov51.aspenhardcore.util.dayCounting.HourDayCounter;
import net.mov51.aspenhardcore.util.dayCounting.MinecraftDayCounter;
import net.mov51.periderm.logs.AspenLogHelper;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class AspenHardCore extends JavaPlugin {

    public static ConfigHelper configHelper;
    public static AspenHardCore plugin;

    public static MinecraftDayCounter minecraftDayCounter;
    public static HourDayCounter hourDayCounter;
    public static AspenLogHelper logHelper;

    public static HashMap<UUID, JoinedPlayer> joinedPlayers = new HashMap<>();
    public static HashMap<UUID, PlayPeriodForDay> playPeriods = new HashMap<>();

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
        logHelper.sendLogInfo("AspenHardCore has been disabled!");
    }
}
