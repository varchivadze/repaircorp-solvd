package com.solvd.repaircorpsolvd.operations;

import com.solvd.repaircorpsolvd.staff.Employee;

public class RepairType {

    private Employee master;
    private String damage;
    private String comments;

    public RepairType() {
        // empty constructor to extend later
    }

    public Employee getMaster() {
        return master;
    }

    public void setMaster(Employee master) {
        this.master = master;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
