package com.solvd.repaircorpsolvd.support;

import java.util.Objects;

public class Address {

    String city;
    String code;
    String street;
    String number;
    String note;

    public Address(String city, String code, String street, String number) {
        this.city = city;
        this.code = code;
        this.street = street;
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFullAddress() {
        return city + ", " + code + ", " + street + ", " + number + ", Note -> " + note;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getCode(), getStreet(), getNumber(), getNote());
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Address address = (Address) obj;
        return Objects.equals(city, address.getCity()) &&
                Objects.equals(code, address.getCode()) &&
                Objects.equals(street, address.getStreet()) &&
                Objects.equals(number, address.getNumber()) &&
                Objects.equals(note, address.getNote());
    }
}

