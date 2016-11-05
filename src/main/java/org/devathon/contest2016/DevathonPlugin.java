package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.commands.MachineCommand;
import org.devathon.contest2016.machine.Machine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DevathonPlugin extends JavaPlugin {

    private static DevathonPlugin INSTANCE;
    private final List<Machine> machines = new ArrayList<>();
    private final File machinesDir = new File(DevathonPlugin.get().getDataFolder(), "machines");

    @Override
    public void onEnable() {
        INSTANCE = this;
        machinesDir.mkdirs();
        getCommand("machine").setExecutor(new MachineCommand());
    }

    @Override
    public void onDisable() {
        INSTANCE = null;
    }

    public File getMachinesDir() {
        return machinesDir;
    }

    public static DevathonPlugin get() {
        return INSTANCE;
    }

}

