package org.devathon.contest2016.machine;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.machine.runner.AbstractMachineHandler;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MachineBuilder {

    private static final LoadingCache<String, List<AbstractMachineHandler>> MACHINE_BLOCKS_CACHE = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .maximumSize(100)
            .build(new CacheLoader<String, List<AbstractMachineHandler>>() {
                @Override
                public List<AbstractMachineHandler> load(String type) throws Exception {
                    final List<AbstractMachineHandler> ret = new ArrayList<>();
                    final File machineHandlers = new File(DevathonPlugin.get().getMachinesDir(), type);
                    Validate.isTrue(machineHandlers.exists(), "Machine type doesn't exist");

                    try {
                        final ClassLoader classLoader = new URLClassLoader(new URL[] { machineHandlers.toURI().toURL() });
                        final File[] classes = machineHandlers.listFiles();
                        Validate.notNull(classes, "Unable to load machine block types.");
                        for(File classFile : classes) {
                            final Class<?> clazz = classLoader.loadClass(classFile.getName().replace(".class", ""));
                            Validate.isTrue(clazz.isAssignableFrom(AbstractMachineHandler.class), "All classes in the machines dir must be machine handlers.");
                            ret.add((AbstractMachineHandler) clazz.newInstance());
                        }
                    } catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                    return ret;
                }
            });

    private final String type;
    private final List<MachineBlock> blocks = new ArrayList<>();
    private String name;

    private final List<AbstractMachineHandler> handlers;

    public MachineBuilder(String type) {
        this.type = type;

        try {
            this.handlers = MACHINE_BLOCKS_CACHE.get(type);
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<AbstractMachineHandler> getHandlers() {
        return handlers;
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

    public MachineBuilder addBlock(String type, Location location) {
        blocks.add(new MachineBlock(type, handlers.stream().filter(clazz -> clazz.getClass().getSimpleName().equalsIgnoreCase(type)).findFirst().orElse(null), location));
        return this;
    }

    public Machine build() {
        //TODO - Add a hologram to show the machine name??
        blocks.forEach(MachineBlock::create);
        return new Machine(type, name, blocks);
    }

}
