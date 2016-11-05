package org.devathon.contest2016.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.util.MessageUtils;

public class MachineCommand extends AbstractCommand implements Listener {

    @Override
    public void onCmd(Player player, String[] args) {
        if(args.length == 0) {
            MessageUtils.sendMessage(player,
                    ChatColor.RED + "Welcome to WesJD's Devathon Machines plugin!",
                    ChatColor.DARK_AQUA + "Commands:",
                    formatSub("create - Create a new machine"),
                    formatSub("run <name> - Run an already created machine by name"),
                    formatSub("destroy [name] - Enter destroy mode or destroy a machine by name")
            );
        } else if(args[0].equalsIgnoreCase("create")) {
            DevathonPlugin.get().getCreationHandler().startFor(player);
        } else if(args[0].equalsIgnoreCase("block")) {
            if(DevathonPlugin.get().getCreationHandler().isCreating(player)) {

            } else player.sendMessage(ChatColor.RED + "You can't get machine blocks when you aren't creating!");
        } else if(args[0].equalsIgnoreCase("run")) {
            if(args.length > 1) {

            }
            player.sendMessage(ChatColor.RED + "You must supply a valid machine name to run! You can choose from");
            //TODO - Add valid machine names to run??
        } else if(args[0].equalsIgnoreCase("destroy")) {

        }
    }

    private String formatSub(String ending) {
        return ChatColor.YELLOW + "/machine " + ending;
    }

}
