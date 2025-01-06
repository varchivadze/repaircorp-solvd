package com.solvd.repaircorpsolvd.staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;

public final class TeamLead extends Employee implements BonusSettable {

    private String department;
    private int maxSubordinates;
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamLead.class);

    public TeamLead(String name, String surname, int age, JobPosition position, String phoneNumber, String department) {
        super(name, surname, age, position, phoneNumber);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getMaxSubordinates() {
        return maxSubordinates;
    }

    public void setMaxSubordinates(int maxSubordinates) {
        this.maxSubordinates = maxSubordinates;
    }

    @Override
    public void setBonus(Employee employee, BigDecimal bonus) {
        if (employee == null) {
            LOGGER.info("TeamLead -> Employee cannot be null");
            return;
        }
        if (employee instanceof RepairTechnician repairTechnician) {
            if (repairTechnician.getDepartment().equals(department)) {
                if (bonus.compareTo(new BigDecimal(500)) > 0) {
                    LOGGER.warn("TeamLead -> Bonus can not be more than 500");
                    return;
                }
                LOGGER.info("TeamLead set bonus to {} BONUS {}", employee, bonus);
                employee.setBonus(bonus);
            } else {
                LOGGER.warn("TeamLead -> The department does not match or employee is not RepairTechnician");
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, department, id, maxSubordinates);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        TeamLead teamLead = (TeamLead) object;
        return Objects.equals(name, teamLead.getName()) &&
                Objects.equals(surname, teamLead.getSurname()) &&
                Objects.equals(department, teamLead.getDepartment()) &&
                Objects.equals(maxSubordinates, teamLead.getMaxSubordinates()) &&
                Objects.equals(id, teamLead.getId());

    }
}
