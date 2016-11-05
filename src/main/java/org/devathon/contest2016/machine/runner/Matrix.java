package org.devathon.contest2016.machine.runner;

import org.devathon.contest2016.machine.Machine;
import org.devathon.contest2016.machine.MachineBlock;

import java.util.List;

public class Matrix {

    private final List<MachineBlock> blocks;
    private int currentIndex;

    public Matrix(Machine machine) {
        this.blocks = machine.getBlocks();
    }

    public boolean hasPrevious() {
        return ((blocks.size()-1) - (currentIndex-1) >= 0);
    }

    public MachineBlock getPrevious() {
        return blocks.get(currentIndex-1);
    }

    public MachineBlock getCurrent() {
        return blocks.get(currentIndex);
    }

    public boolean hasNext() {
        return (blocks.size() <= (currentIndex+1));
    }

    public MachineBlock getNext() {
        return blocks.get(currentIndex+1);
    }

}
