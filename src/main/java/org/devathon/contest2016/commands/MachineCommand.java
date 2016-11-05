package org.devathon.contest2016.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.devathon.contest2016.util.MessageUtils;

public class MachineCommand extends AbstractCommand {

    @Override
    public void onCmd(Player player, String[] args) {
        if(args.length == 0) {
            MessageUtils.sendMessage(player,
                    ChatColor.RED + "Welcome to WesJD's Devathon Machines plugin!",
                    ChatColor.DARK_AQUA + "Commands:",
                    formatSub("create - Start work on building your own machine"),
                    formatSub("run <name> - Run an already created machine")
            );
        } else if(args[0].equalsIgnoreCase("create")) {

        } else if(args[0].equalsIgnoreCase("run")) {
            if(args.length > 1) {

            }
            player.sendMessage(ChatColor.RED + "You must supply a valid machine name to run!");
            //TODO - Add valid machine names to run??
        }
    }

    private String formatSub(String ending) {
        return ChatColor.YELLOW + "/machine " + ending;
    }

}
