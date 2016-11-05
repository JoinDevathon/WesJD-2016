package org.devathon.contest2016.machine;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.devathon.contest2016.DevathonPlugin;

public class MachineBlock {

    private final String name;
    private final Material material;
    private final Location location;

    public MachineBlock(String name, Material material, Location location) {
        this.name = name;
        this.material = material;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public void build() {
        final Block block = location.getBlock();
        block.setType(material);
        block.setMetadata("machine_name", new FixedMetadataValue(DevathonPlugin.get(), name));
    }

}
