package org.devathon.contest2016.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.listeners.CreationHandler;
import org.devathon.contest2016.machine.Machine;
import org.devathon.contest2016.machine.runner.Matrix;
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
            final CreationHandler handler = DevathonPlugin.get().getCreationHandler();
            if(handler.isCreating(player)) {
                if(args.length > 1) {

                }
                final StringBuilder sb = new StringBuilder();
                
            } else player.sendMessage(ChatColor.RED + "You can't get machine blocks when you aren't creating!");
        } else if(args[0].equalsIgnoreCase("run")) {
            if(args.length > 1) {
                final Machine machine = DevathonPlugin.get().getMachines().stream().filter(mach -> mach.getName().equalsIgnoreCase(args[1])).findFirst().orElse(null);
                if(machine != null) {
                    new Matrix(machine, player).start();
                    return;
                }
            }

            final StringBuilder sb = new StringBuilder();
            DevathonPlugin.get().getMachines().forEach(machine -> {
                sb.append(machine.getName());
                sb.append(", ");
            });
            sb.deleteCharAt(sb.length()-2);
            MessageUtils.sendMessage(player,
                    ChatColor.RED + "You must supply a valid machine name to run! You can choose from:",
                    ChatColor.YELLOW + sb.toString().trim()
            );
        } else if(args[0].equalsIgnoreCase("destroy")) {

        }
    }

    private String formatSub(String ending) {
        return ChatColor.YELLOW + "/machine " + ending;
    }

}
