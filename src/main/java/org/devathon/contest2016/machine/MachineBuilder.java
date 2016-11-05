package org.devathon.contest2016.machine;

import java.util.ArrayList;
import java.util.List;

public class MachineBuilder {

    private final String type;
    private final List<MachineBlock> blocks = new ArrayList<>();
    private String name;

    public MachineBuilder(String type) {
        this.type = type;
    }

    public boolean hasName() {
        return name != null;
    }

    public MachineBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MachineBuilder add(MachineBlock block) {
        blocks.add(block);
        return this;
    }

    public Machine build() {
        return new Machine(type, name, blocks);
    }

}
