package org.devathon.contest2016.machine.runner;

public abstract class AbstractMachineHandler {

    public abstract long getWait();

    public abstract void onUse(Matrix matrix);

}
