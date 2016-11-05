package org.devathon.contest2016.listeners;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.machine.MachineBuilder;
import org.devathon.contest2016.util.MessageUtils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreationHandler implements Listener {

    private final Map<UUID, MachineBuilder> creating = new HashMap<>();

    public void startFor(Player player) {
        player.setMetadata("old_inventory", new FixedMetadataValue(DevathonPlugin.get(), player.getInventory()));
        player.getInventory().clear();

        final File[] directories = DevathonPlugin.get().getMachinesDir().listFiles(File::isDirectory);
        Validate.notNull(directories, "Unable to read machines.");

        final StringBuilder sb = new StringBuilder(ChatColor.GREEN.toString()).append("What machine type do you want? ");
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
    }

    public boolean isCreating(Player player) {
        return creating.containsKey(player.getUniqueId());
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

                final File[] classes = new File(DevathonPlugin.get().getMachinesDir(), builder.getType()).listFiles();
                Validate.notNull(classes, "Unable to read blocks classes.");

                final StringBuilder sb = new StringBuilder();
                Arrays.stream(classes).forEach(clazz -> {
                    sb.append(clazz.getName());
                    sb.append(", ");
                });
                sb.deleteCharAt(sb.length()-2);

                MessageUtils.sendMessage(player,
                        ChatColor.GREEN + "Woohoo! We're almost done.",
                        ChatColor.GREEN + "All we have to do now is actually build the machine.",
                        ChatColor.GREEN + "You can do " + ChatColor.YELLOW + " /machine block <type> " + ChatColor.GREEN + "to get a machine block to place.",
                        ChatColor.GREEN + "You have chosen the " + ChatColor.YELLOW + builder.getType() + ChatColor.GREEN + "type, so here are your choices for machine block types:",
                        ChatColor.YELLOW + sb.toString().trim()
                );
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

    }

}
