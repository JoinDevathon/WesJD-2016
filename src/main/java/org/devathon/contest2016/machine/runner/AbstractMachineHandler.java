package org.devathon.contest2016.machine.runner;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public abstract class AbstractMachineHandler {

    private final Material material;
    private final MaterialData materialData;
    private final long wait;

    public AbstractMachineHandler(Material material, MaterialData materialData, long wait) {
        Validate.isTrue(wait > 0, "Wait time must be at least one.");
        this.material = material;
        this.materialData = materialData;
        this.wait = wait;
    }

    public abstract void onUse(Matrix matrix);

    public Material getMaterial() {
        return material;
    }

    public MaterialData getMaterialData() {
        return materialData;
    }

    public long getWait() {
        return wait;
    }

}
