package com.solvd.repaircorpsolvd.staff;

import java.util.Objects;

public abstract class Person {

    protected long id;
    protected String name;
    protected String surname;
    protected int age;
    protected String phoneNumber;
    protected String email;
    protected String address;

    protected Person(String name, String surname, int age, long id) {
        this.id = id;
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age can not be less 0 or more 150");
        }
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    // prevent from adding new info to child method as base info is base info, not full
    public final String getBaseInfo() {
        return "Name " + name + " Surname " + surname + " Age " + age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected abstract void notifyPerson(String remark);

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Person person = (Person) object;
        return Objects.equals(name, person.getName()) &&
                Objects.equals(surname, person.getSurname()) &&
                Objects.equals(id, person.getId());

    }
}



