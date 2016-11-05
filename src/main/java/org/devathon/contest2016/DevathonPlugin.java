package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.machine.Machine;

import java.util.ArrayList;
import java.util.List;

public class DevathonPlugin extends JavaPlugin {

    private static DevathonPlugin INSTANCE;
    private final List<Machine> machines = new ArrayList<>();

    @Override
    public void onEnable() {
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        INSTANCE = null;
    }

    public static DevathonPlugin get() {
        return INSTANCE;
    }

}

