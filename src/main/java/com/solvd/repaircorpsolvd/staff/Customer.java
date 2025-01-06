package com.solvd.repaircorpsolvd.staff;

import com.solvd.repaircorpsolvd.resources.Device;
import com.solvd.repaircorpsolvd.support.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

// Customer is customer and can cover any type of customer - no need to have many of them like I did for staff
public final class Customer extends Person {

    private Device device;
    private double discount;
    private static final Logger LOGGER = LoggerFactory.getLogger(Customer.class);

    public Customer(String name, String surname, int age) {
        super(name, surname, age, IdGenerator.createId());
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public void notifyPerson(String remark) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            LOGGER.warn("\nCan't call as customer did not give a number with\n");
        } else {
            LOGGER.info("\nMake a call to customer -> {}  with info {} \n", phoneNumber, remark);
            return;
        }
        if (email == null || email.isEmpty()) {
            LOGGER.warn("\nCan't send e-mail as customer did not give an address\n");
        } else {
            LOGGER.info("\nSend e-mail to customer -> {} with info {} \n", email, remark);
            return;
        }
        if (address == null || address.isEmpty()) {
            LOGGER.warn("\nThere is no any customer's contacts, did you forget to ask for it???\n");
        } else {
            LOGGER.info("\nSend a list to customer using address -> {}  with info {} \n", address, remark);
        }
    }

    @Override
    public String toString() {
        String output = "\nCustomer info\nID " + id + "\n" + getBaseInfo() + "\nDiscount " + discount + "\n";
        LOGGER.info(output);
        return output;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getId(), device, discount);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Customer customer = (Customer) object;
        return Objects.equals(name, customer.getName()) &&
                Objects.equals(surname, customer.getSurname()) &&
                Objects.equals(id, customer.getId()) &&
                Objects.equals(device, customer.getDevice()) &&
                Objects.equals(discount, customer.getDiscount());

    }
}
