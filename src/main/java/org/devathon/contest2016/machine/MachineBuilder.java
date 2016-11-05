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

    public String getType() {
        return type;
    }

    public boolean hasName() {
        return name != null;
    }

    public String getName() {
        return name;
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
        //TODO - Add a hologram to show the machine name??
        blocks.forEach(MachineBlock::create);
        return new Machine(type, name, blocks);
    }

}
