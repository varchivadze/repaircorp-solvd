package com.solvd.repaircorpsolvd.resources;

public enum NetworkType {

    THREE_G(42, 3, 75),
    FOUR_G(1000, 30, 30),
    FIVE_G(4000, 300, 10);

    private final Integer maxSpeedMb;
    private final Integer medianSpeedMb;
    private final Integer delayMs;

    NetworkType(Integer maxSpeedMb, Integer medianSpeedMb, Integer delayMs) {
        this.maxSpeedMb = maxSpeedMb;
        this.medianSpeedMb = medianSpeedMb;
        this.delayMs = delayMs;
    }

    public Integer getMaxSpeedMb() {
        return maxSpeedMb;
    }

    public Integer getMedianSpeedMb() {
        return medianSpeedMb;
    }

    public Integer getDelayMs() {
        return delayMs;
    }
}
