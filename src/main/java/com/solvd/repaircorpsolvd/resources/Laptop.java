package com.solvd.repaircorpsolvd.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Laptop extends Device implements Chargeable {

    private double screenSize;
    private double batteryHours;
    private boolean camera;
    private boolean charging;
    private UsbType usbType;

    private static final Logger LOGGER = LoggerFactory.getLogger(Laptop.class);

    public Laptop(String made, String model) {
        super(made, model);
    }

    public UsbType getUsbType() {
        return usbType;
    }

    public void setUsbType(UsbType usbType) {
        this.usbType = usbType;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public double getBatteryHours() {
        return batteryHours;
    }

    public void setBatteryHours(double batteryHours) {
        this.batteryHours = batteryHours;
    }

    public boolean getCamera() {
        return camera;
    }

    public void setCamera(boolean camera) {
        this.camera = camera;
    }

    @Override
    public String toString() {
        return ("Device info\n" + getMade() + " " + getModel() + " " + batteryHours + "\nIs camera " + camera + "\nScreen Size " + screenSize + "\n");
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMade(), getModel(), screenSize, batteryHours, camera);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Laptop laptop = (Laptop) obj;
        return Double.compare(screenSize, laptop.screenSize) == 0 &&
                Double.compare(batteryHours, laptop.batteryHours) == 0 &&
                camera == laptop.camera &&
                Objects.equals(getMade(), laptop.getMade()) &&
                Objects.equals(getModel(), laptop.getModel());
    }

    @Override
    public void charge() {
        charging = true;
        LOGGER.info("{} {} is charging now", getMade(), getModel());
    }

    @Override
    public void stopCharge() {
        charging = false;
        LOGGER.info("{} {} is not charging now", getMade(), getModel());
    }

    @Override
    public boolean onCharging() {
        return charging;
    }
}
