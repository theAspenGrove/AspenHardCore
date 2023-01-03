package net.mov51.aspenhardcore;

import net.mov51.aspenhardcore.commands.NewDayCommand;
import net.mov51.aspenhardcore.util.ConfigHelper;
import net.mov51.aspenhardcore.util.dayCounting.HourDayCounter;
import net.mov51.aspenhardcore.util.dayCounting.MinecraftDayCounter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class AspenHardCore extends JavaPlugin {

    public static ConfigHelper configHelper;
    public static AspenHardCore plugin;

    public static MinecraftDayCounter minecraftDayCounter;
    public static HourDayCounter hourDayCounter;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        configHelper = new ConfigHelper(plugin);
        minecraftDayCounter = new MinecraftDayCounter(plugin);
        hourDayCounter = new HourDayCounter();
        configHelper.setMethod();
        //register newDayCommand
        Objects.requireNonNull(getCommand("newday")).setExecutor(new NewDayCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
