package org.devathon.contest2016.machine;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.machine.runner.AbstractMachineHandler;

public class MachineBlock {

    private final String machineType;
    private final AbstractMachineHandler handler;
    private final Location location;

    MachineBlock(String machineType, AbstractMachineHandler handler, Location location) {
        this.machineType = machineType;
        this.handler = handler;
        this.location = location;
    }

    public AbstractMachineHandler getHandler() {
        return handler;
    }

    public void create() {
        final Block block = location.getBlock();
        block.setType(handler.getMaterial());
        block.setData(handler.getMaterialData().getData());
        block.setMetadata("machine_type", new FixedMetadataValue(DevathonPlugin.get(), machineType));
        block.setMetadata("machine_block_type", new FixedMetadataValue(DevathonPlugin.get(), handler.getClass().getSimpleName()));
    }

}
