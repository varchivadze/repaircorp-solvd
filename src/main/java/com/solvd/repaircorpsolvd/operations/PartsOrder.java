package com.solvd.repaircorpsolvd.operations;

import com.solvd.repaircorpsolvd.support.Address;
import com.solvd.repaircorpsolvd.support.AddressNotFoundException;
import com.solvd.repaircorpsolvd.support.Addresses;
import com.solvd.repaircorpsolvd.support.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PartsOrder extends Order {

    private String warehouse;
    private Address address;
    private BigDecimal cost;
    private final Map<String, Integer> parts;
    private boolean paid;
    private static final Logger LOGGER = LoggerFactory.getLogger(PartsOrder.class);

    public PartsOrder() {
        super(IdGenerator.createId());
        parts = new HashMap<>();
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Map<String, Integer> getParts() {
        return parts;
    }

    public void addParts(String part, Integer number) {
        parts.put(part, number);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        try {
            Addresses.addressExists(address);
            this.address = address;
        } catch (AddressNotFoundException e) {
            LOGGER.info(e.getMessage());
        }
    }

    public void removePart(String part) {
        parts.remove(part);
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public void setComplete() {
        if (complete) {
            LOGGER.info("The parts order was already complete!{}", ID);
            return;
        }
        complete = true;
        LOGGER.info("The parts order is complete now {}", ID);
    }

    @Override
    public void setIncomplete() {
        if (!complete) {
            LOGGER.info("The parts order was already incomplete!{}", ID);
            return;
        }
        complete = false;
        LOGGER.info("The parts order is incomplete now {}", ID);
    }

    @Override
    public String toString() {
        StringBuilder partsString = new StringBuilder();
        if (parts != null) {
            parts.keySet().forEach(key -> partsString.append(key).append(" "));
        }
        return "Parts order info \n" + partsString + "\nWarehouse " + warehouse + "\nCost " + cost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, createTime, warehouse, address);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        PartsOrder order = (PartsOrder) object;
        return Objects.equals(ID, order.ID) &&
                Objects.equals(createTime, order.createTime) &&
                Objects.equals(warehouse, order.warehouse) &&
                Objects.equals(address, order.address);
    }
}
