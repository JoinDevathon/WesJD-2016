package org.devathon.contest2016.machine.runner;

import org.apache.commons.lang.Validate;
import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.machine.Machine;
import org.devathon.contest2016.machine.MachineBlock;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Matrix {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private final LinkedHashMap<MachineBlock, AbstractMachineHandler> blocks = new LinkedHashMap<>();
    private final MachineBlock[] indexes;
    private int currentIndex;

    public Matrix(Machine machine) {
        final File machineHandlers = new File(DevathonPlugin.get().getMachinesDir(), machine.getName());
        Validate.isTrue(machineHandlers.exists(), "Machine doesn't exist on file, how did you get here?");

        try {
            final ClassLoader classLoader = new URLClassLoader(new URL[] { machineHandlers.toURI().toURL() });
            for(MachineBlock block : machine.getBlocks()) {
                final Class<?> clazz = classLoader.loadClass(block.getType());
                Validate.isTrue(clazz.isAssignableFrom(AbstractMachineHandler.class), "All classes in the machines dir must be machine handlers.");
                blocks.put(block, (AbstractMachineHandler) clazz.newInstance());
            }
        } catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

        final Set<MachineBlock> keySet = blocks.keySet();
        indexes = keySet.toArray(new MachineBlock[keySet.size()]);

        final MatrixRunner runner = new MatrixRunner();
        EXECUTOR_SERVICE.execute(() -> Arrays.stream(indexes).forEach(block -> {
            try {
                final AbstractMachineHandler handler = blocks.get(block);
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
        return indexes[currentIndex-1];
    }

    public MachineBlock getCurrent() {
        return indexes[currentIndex];
    }

    public boolean hasNext() {
        return (blocks.size() <= (currentIndex+1));
    }

    public MachineBlock getNext() {
        return indexes[currentIndex+1];
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
