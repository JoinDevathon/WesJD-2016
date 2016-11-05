package org.devathon.contest2016.machine.runner;

import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.machine.Machine;
import org.devathon.contest2016.machine.MachineBlock;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Matrix {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private final List<MachineBlock> blocks;
    private int currentIndex;

    public Matrix(Machine machine) {
        this.blocks = machine.getBlocks();

        final MatrixRunner runner = new MatrixRunner();
        EXECUTOR_SERVICE.execute(() -> blocks.forEach(block -> {
            try {
                final AbstractMachineHandler handler = block.getHandler();
                runner.setCurrent(handler);
                runner.runTask(DevathonPlugin.get());
                Thread.sleep(handler.getWait());
                currentIndex++;
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }));
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

    private class MatrixRunner extends BukkitRunnable {

        private AbstractMachineHandler current;

        @Override
        public void run() {
            current.onUse(Matrix.this);
        }

        public void setCurrent(AbstractMachineHandler current) {
            this.current = current;
        }

    }

}
