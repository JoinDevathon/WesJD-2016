package org.devathon.contest2016.machine;

import java.util.Collections;
import java.util.List;

public class Machine {

    private final String type;
    private final String name;
    private final List<MachineBlock> blocks;

    public Machine(String type, String name, List<MachineBlock> blocks) {
        this.type = type;
        this.name = name;
        this.blocks = blocks;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<MachineBlock> getBlocks() {
        return Collections.unmodifiableList(blocks);
    }

}
