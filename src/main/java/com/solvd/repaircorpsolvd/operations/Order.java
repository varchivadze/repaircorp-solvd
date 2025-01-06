package com.solvd.repaircorpsolvd.operations;

import com.solvd.repaircorpsolvd.staff.Customer;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Order {

    // we do not change ID
    protected final long ID;
    protected LocalDateTime createTime;
    protected boolean complete;
    protected Customer customer;

    protected Order(long id) {
        this.ID = id;
        createTime = LocalDateTime.now();
    }

    protected long getID() {
        return ID;
    }

    protected LocalDateTime getCreateTime() {
        return createTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public abstract void setComplete();

    public abstract void setIncomplete();

    public boolean getComplete() {
        return complete;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, createTime);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Order order = (Order) object;
        return Objects.equals(ID, order.ID) &&
                Objects.equals(createTime, order.createTime);
    }
}
