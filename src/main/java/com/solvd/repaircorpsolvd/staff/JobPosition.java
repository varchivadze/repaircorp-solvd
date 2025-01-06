package com.solvd.repaircorpsolvd.staff;

import java.math.BigDecimal;

public enum JobPosition {

    MANAGER(new BigDecimal("4500"), new BigDecimal("7500")),
    REPAIR_MASTER(new BigDecimal("2500"), new BigDecimal("4500")),
    ACCOUNTANT(new BigDecimal("3500"), new BigDecimal("7000")),
    DELIVERY(new BigDecimal("2500"), new BigDecimal("4500")),
    TEAMLEAD(new BigDecimal("5500"), new BigDecimal("9500")),
    EXECUTIVEDIRECTOR(new BigDecimal("14500"), new BigDecimal("17500"));

    private final BigDecimal minSalary;
    private final BigDecimal maxSalary;

    JobPosition(BigDecimal minSalary, BigDecimal maxSalary) {
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public BigDecimal getMinSalary() {
        return minSalary;
    }

    public BigDecimal getMaxSalary() {
        return maxSalary;
    }
}
