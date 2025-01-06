package com.solvd.repaircorpsolvd.company;

import java.math.BigDecimal;

public interface Rentable {

    void rent(BigDecimal cost);

    void vacate();
}
