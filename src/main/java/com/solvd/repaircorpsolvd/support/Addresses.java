package com.solvd.repaircorpsolvd.support;

import java.util.ArrayList;
import java.util.List;

// Class to simulate GoogleMap request
public class Addresses {

    private static final List<Address> ALLOWED_ADDRESSES = new ArrayList<>();

    private Addresses() {
    }

    public static List<Address> getAllowedAddresses() {
        return ALLOWED_ADDRESSES;
    }

    public static void addAddress(Address address) {
        ALLOWED_ADDRESSES.add(address);
    }

    public static void addAddresses(List<Address> addressesToAdd) {
        ALLOWED_ADDRESSES.addAll(addressesToAdd);
    }

    public static void removeAddress(int index) {
        ALLOWED_ADDRESSES.remove(index);
    }

    // Simulate the googlmap request
    public static void addressExists(Address address) throws AddressNotFoundException {
        Address foundAddress = ALLOWED_ADDRESSES.stream()
                .filter(curAddress -> curAddress.equals(address))
                .findFirst()
                .orElse(null);

        if (foundAddress == null) {
            throw new AddressNotFoundException("Address not found " + address.getFullAddress());
        }

    }

}
