package org.devathon.contest2016.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.devathon.contest2016.util.MessageUtils;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getBlock().hasMetadata("machine_type") && e.getBlock().hasMetadata("machine_block_type")) {
            e.setCancelled(true);
            MessageUtils.sendMessage(e.getPlayer(), ChatColor.RED + "That block is part of a machine!");
        }
    }

}
