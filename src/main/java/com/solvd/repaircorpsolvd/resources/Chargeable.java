package com.solvd.repaircorpsolvd.resources;

public interface Chargeable {

    void charge();

    void stopCharge();

    boolean onCharging();
}
