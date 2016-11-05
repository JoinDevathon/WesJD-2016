package org.devathon.contest2016.machine;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.devathon.contest2016.DevathonPlugin;

public class MachineBlock {

    private final Machine machine;
    private final String type;
    private final Material material;
    private final Location location;

    MachineBlock(Machine machine, String type, Material material, Location location) {
        this.machine = machine;
        this.type = type;
        this.material = material;
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public Material getMaterial() {
        return material;
    }

    public void create() {
        final Block block = location.getBlock();
        block.setType(material);
        block.setMetadata("machine_type", new FixedMetadataValue(DevathonPlugin.get(), machine.getType()));
        block.setMetadata("machine_block_type", new FixedMetadataValue(DevathonPlugin.get(), type));
    }

}
