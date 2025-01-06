package com.solvd.repaircorpsolvd.staff;

import com.solvd.repaircorpsolvd.support.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;

public class Employee extends Person {

    private JobPosition position;
    private boolean hired;
    private boolean statusReady;
    private BigDecimal salary;
    private BigDecimal bonus;
    private static final Logger LOGGER = LoggerFactory.getLogger(Employee.class);

    public Employee(String name, String surname, int age, JobPosition position, String phoneNumber) {
        super(name, surname, age, IdGenerator.createId());
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.hired = false;
        this.salary = position.getMinSalary();
        bonus = BigDecimal.ZERO;
    }

    public JobPosition getPosition() {
        return position;
    }

    public void setPosition(JobPosition position) {
        this.position = position;
    }

    public void hire() {
        this.hired = true;
    }

    public void fire() {
        this.hired = false;
    }

    public boolean getHired() {
        return this.hired;
    }

    public boolean statusReady() {
        return statusReady;
    }

    public void setStatusReady(boolean statusReady) {
        this.statusReady = statusReady;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        if (this.position.getMaxSalary().compareTo(salary) < 0) {
            this.salary = this.position.getMaxSalary();
            LOGGER.warn("Could not set more than {}", this.position.getMaxSalary());
        } else if (this.position.getMinSalary().compareTo(salary) > 0) {
            this.salary = this.position.getMinSalary();
            LOGGER.warn("Could not set less than {}", this.position.getMinSalary());
        } else {
            this.salary = salary;
        }
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
        LOGGER.info("Employee {} gets bonus {}", this.surname, this.bonus);
    }

    @Override
    public void notifyPerson(String remark) {
        LOGGER.info("Send message to employee in telegram Sol group or call by -> {} with {}", phoneNumber, remark);
    }

    @Override
    public String toString() {
        return "\nEmployee info\nID " + id + "\n" + getBaseInfo() + "\nPosition " + position + "\n";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, position, id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Employee employee = (Employee) object;
        return Objects.equals(name, employee.getName()) &&
                Objects.equals(surname, employee.getSurname()) &&
                Objects.equals(position, employee.getPosition()) &&
                Objects.equals(id, employee.getId());

    }
}
