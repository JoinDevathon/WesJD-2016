package org.devathon.contest2016.commands;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.machine.MachineBuilder;
import org.devathon.contest2016.util.MessageUtils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MachineCommand extends AbstractCommand implements Listener {

    private final Map<UUID, MachineBuilder> creating = new HashMap<>();

    @Override
    public void onCmd(Player player, String[] args) {
        if(args.length == 0) {
            MessageUtils.sendMessage(player,
                    ChatColor.RED + "Welcome to WesJD's Devathon Machines plugin!",
                    ChatColor.DARK_AQUA + "Commands:",
                    formatSub("create - Start work on building your own machine"),
                    formatSub("run <name> - Run an already created machine"),
                    formatSub("destroy [name] - Enter destroy mode or destory a machine by name")
            );
        } else if(args[0].equalsIgnoreCase("create")) {
            final StringBuilder sb = new StringBuilder(ChatColor.GREEN.toString()).append("What machine type do you want? ");
            final File[] directories = DevathonPlugin.get().getMachinesDir().listFiles(File::isDirectory);
            Validate.notNull(directories, "Unable to read machines.");
            Arrays.stream(directories).forEach(dir -> {
                sb.append(dir.getName());
                sb.append(", ");
            });
            sb.deleteCharAt(sb.length()-2);

            MessageUtils.sendMessage(player,
                    ChatColor.GREEN + "Welcome to the machine builder!",
                    sb.toString().trim()
            );
            creating.put(player.getUniqueId(), null);
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

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        if(creating.containsKey(uuid)) {
            MachineBuilder builder = creating.get(uuid);
            if(builder == null) {
                e.setCancelled(true);
                builder = new MachineBuilder(e.getMessage());
                creating.put(uuid, builder);
                MessageUtils.sendMessage(player, ChatColor.GREEN + "What should the name of your machine be?");
            } else if(!builder.hasName()) {
                e.setCancelled(true);
                builder.name(e.getMessage());
                MessageUtils.sendMessage(player,
                        ChatColor.GREEN + ""
                );
            }
        }
    }

}
