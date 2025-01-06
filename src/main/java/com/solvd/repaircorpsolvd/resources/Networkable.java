package com.solvd.repaircorpsolvd.resources;

public interface Networkable {

    void connectNetwork();

    void disconnectNetwork();

    boolean getNetworkStatus();
}
