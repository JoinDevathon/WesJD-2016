package org.devathon.contest2016.machine.runner;

import org.apache.commons.lang.Validate;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.machine.Machine;
import org.devathon.contest2016.machine.MachineBlock;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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
                final Class<?> clazz = classLoader.loadClass(block.getName());
                Validate.isTrue(clazz.isAssignableFrom(AbstractMachineHandler.class), "All classes in the machines dir must be machine handlers.");
                blocks.put(block, (AbstractMachineHandler) clazz.newInstance());
            }
        } catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

        final Set<MachineBlock> keySet = blocks.keySet();
        indexes = keySet.toArray(new MachineBlock[keySet.size()]);

        EXECUTOR_SERVICE.execute(() -> {
            
        });
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

}
