package org.devathon.contest2016.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements CommandExecutor {

    public abstract void onCmd(Player player, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) onCmd((Player) sender, args);
        else sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
        return false;
    }

}
