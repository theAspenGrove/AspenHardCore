package net.mov51.aspenhardcore;

import net.mov51.aspenhardcore.util.ConfigHelper;
import net.mov51.aspenhardcore.util.dayCounting.HourDayCounter;
import net.mov51.aspenhardcore.util.dayCounting.MinecraftDayCounter;
import org.bukkit.plugin.java.JavaPlugin;

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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
