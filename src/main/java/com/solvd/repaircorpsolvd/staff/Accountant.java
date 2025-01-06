package com.solvd.repaircorpsolvd.staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public final class Accountant extends Employee implements BonusSettable, SalarySettable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Accountant.class);

    public Accountant(String name, String surname, int age, JobPosition position, String phoneNumber) {
        super(name, surname, age, position, phoneNumber);
    }

    @Override
    public void setBonus(Employee employee, BigDecimal bonus) {
        if (!(employee instanceof Accountant)) {
            LOGGER.info("Accountant set bonus to {} BONUS {}", employee, bonus);
            employee.setBonus(bonus);
        } else {
            LOGGER.warn("ACCOUNTANT - Can't set bonus for yourself");
        }
    }

    @Override
    public void setSalary(Employee employee, BigDecimal salary) {
        if (!(employee instanceof Accountant)) {
            LOGGER.info("Accountant set salary to {} SALARY {}", employee, salary);
            employee.setSalary(salary);
        } else {
            LOGGER.warn("ACCOUNTANT - you can't set salary for yourself");
        }
    }

    @Override
    public String toString() {
        String output = "\nAccountant info\nID " + id + "\n" + getBaseInfo();
        LOGGER.info(output);
        return output;
    }
}
