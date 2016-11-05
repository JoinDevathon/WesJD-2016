package org.devathon.contest2016.machine.runner;

public abstract class AbstractMachineHandler {

    public abstract void setup(LocalizedMatrix matrix);

    public abstract void handle(Matrix matrix);

}
