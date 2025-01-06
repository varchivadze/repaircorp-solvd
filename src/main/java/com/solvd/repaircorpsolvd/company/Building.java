package com.solvd.repaircorpsolvd.company;

import com.solvd.repaircorpsolvd.support.Address;
import com.solvd.repaircorpsolvd.support.AddressNotFoundException;
import com.solvd.repaircorpsolvd.support.Addresses;
import com.solvd.repaircorpsolvd.support.NegativeValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public abstract class Building implements Rentable {

    protected Address address;
    protected double area;
    protected BigDecimal rentCost;
    protected boolean rentalStatus;
    private static final Logger LOGGER = LoggerFactory.getLogger(Building.class);

    protected Building() {
    }

    protected Building(Address address, double area, BigDecimal rentCost) throws AddressNotFoundException, NegativeValueException {

        Addresses.addressExists(address);
        this.address = address;
        if (area < 0) {
            throw new NegativeValueException("Area cant be less than 0");
        }
        if (rentCost.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeValueException("Rental cost cant be less than 0");
        }
        this.area = area;
        this.rentCost = rentCost;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        try {
            Addresses.addressExists(address);
            this.address = address;
        } catch (AddressNotFoundException e) {
            LOGGER.error(e.getMessage());
        } finally {
            LOGGER.error("The current address is {}", this.address);
        }

    }

    public BigDecimal getRentCost() {
        return rentCost;
    }

    public void setRentCost(BigDecimal rentCost) {
        this.rentCost = rentCost;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public boolean getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(boolean rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    @Override
    public void rent(BigDecimal cost) {
        this.rentCost = cost;
        this.rentalStatus = true;
        LOGGER.info("Building is now rented for {}", cost);
    }

    @Override
    public void vacate() {
        this.rentalStatus = false;
        this.rentCost = BigDecimal.ZERO;
        LOGGER.info("Building at is now vacated.");
    }
}
