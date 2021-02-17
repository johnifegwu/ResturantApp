package com.mickleentityltdnigeria.resturantapp.service;

import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.logic.AddressLogic;

import java.util.List;

public class AddressService {

    public AddressLogic address = new AddressLogic();

    public AddressService() {
    }

    public void AddAddress(Address address) throws InvalidUserException {
        this.address.AddAddress(address);
    }

    public void UpdateAddress(Address address) throws InvalidUserException {
        this.address.UpdateAddress(address);
    }

    public void DeleteAddress(String addressID) throws InvalidUserException {
        this.address.DeleteAddress(addressID);
    }
    public List<Address> getAddresses(String userID) throws InvalidUserException {
        return this.address.getAddresses(userID);
    }

    public List<Address> getAddressesByType(String userID, String addressType) throws InvalidUserException {
        return this.address.getAddressesByType(userID, addressType);
    }

}
