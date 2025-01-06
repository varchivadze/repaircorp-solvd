package com.solvd.repaircorpsolvd.resources;

import java.util.Objects;

public class Device {

    protected String made;
    protected String model;

    public Device() {
    }

    public Device(String made, String model) {
        this.model = model;
        this.made = made;
    }

    public String getMade() {
        return made;
    }

    public void setMade(String made) {
        this.made = made;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return (made + model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMade(), getModel());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Device device = (Device) obj;
        return Objects.equals(made, device.getMade()) &&
                Objects.equals(model, device.getModel());
    }
}
