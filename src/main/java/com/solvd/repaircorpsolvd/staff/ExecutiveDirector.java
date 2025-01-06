package com.solvd.repaircorpsolvd.staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public final class ExecutiveDirector extends Employee implements SalarySettable, BonusSettable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutiveDirector.class);

    public ExecutiveDirector(String name, String surname, int age, JobPosition position, String phoneNumber) {
        super(name, surname, age, position, phoneNumber);
    }

    @Override
    public void setSalary(Employee employee, BigDecimal salary) {
        if (!(employee instanceof ExecutiveDirector)) {
            LOGGER.info("ExecutiveDirector set salary to {} SALARY {}", employee, salary);
            employee.setSalary(salary);
        } else {
            LOGGER.warn("ExecutiveDirector - you can't set salary for yourself");
        }
    }

    @Override
    public void setBonus(Employee employee, BigDecimal bonus) {
        if (!(employee instanceof ExecutiveDirector)) {
            LOGGER.info("ExecutiveDirector set bonus to {} BONUS {}", employee, bonus);
            employee.setBonus(bonus);
        } else {
            LOGGER.warn("ExecutiveDirector - Can't set bonus for yourself");
        }

    }

    @Override
    public String toString() {
        String output = "\nExecutiveDirector info\nID " + id + "\n" + getBaseInfo();
        LOGGER.info(output);
        return output;
    }
}
