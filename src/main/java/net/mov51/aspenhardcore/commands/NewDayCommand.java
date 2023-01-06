package net.mov51.aspenhardcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.mov51.aspenhardcore.util.dayCounting.DayCounter.newDay;

public class NewDayCommand implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(sender.hasPermission("aspenhardcore.newday") || !(sender instanceof Player)){
            newDay();
            return true;
        }else {
            sender.sendMessage("You do not have permission to use this command!");
            return false;
        }
    }
}
