package com.solvd.repaircorpsolvd.company;

import com.solvd.repaircorpsolvd.staff.Employee;
import com.solvd.repaircorpsolvd.support.Address;
import com.solvd.repaircorpsolvd.support.AddressNotFoundException;
import com.solvd.repaircorpsolvd.support.NegativeValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RepCorp extends Building {

    private String name;
    private BigDecimal initCapital;
    private final List<RepairService> services;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepCorp.class);

    public RepCorp(String corpName, BigDecimal initCapital, Address address, double area, BigDecimal rentCost) throws AddressNotFoundException, NegativeValueException {
        super(address, area, rentCost);
        this.name = corpName;
        this.initCapital = initCapital;
        services = new ArrayList<>();
    }

    public String getCorpName() {
        return name;
    }

    public void setCorpName(String corpName) {
        this.name = corpName;
    }

    public BigDecimal getInitCapital() {
        return initCapital;
    }

    public void setInitCapital(BigDecimal initCapital) {
        this.initCapital = initCapital;
    }

    public List<RepairService> getServices() {
        return services;
    }

    public void addService(RepairService repairService) {
        services.add(repairService);
    }

    public void closeService(RepairService repairService) {
        services.remove(repairService);
        if (repairService != null) {
            repairService.getEmployees().stream()
                    .filter(Employee::getHired)
                    .forEach(Employee::fire);
        }
    }

    public int getTotalRepaired() {

        return services.stream()
                .mapToInt(RepairService::getTotalRepaired)
                .sum();
    }

    @Override
    public String toString() {
        return "\nCompany " + name + "\nCapital " + initCapital + "\nLocated " + address + "Services\n";
    }

    public void processRent(Rentable rentable, BigDecimal cost) {
        rentable.rent(cost);
        LOGGER.info("Just rented new building with cost {}", cost);
    }

    public void stopRent(Rentable rentable) {
        rentable.vacate();
        LOGGER.info("Just vacated building");
    }
}
