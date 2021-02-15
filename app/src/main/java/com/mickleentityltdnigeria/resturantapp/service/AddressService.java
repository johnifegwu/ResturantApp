package com.mickleentityltdnigeria.resturantapp.service;

import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.logic.AddressLogic;

import java.util.List;

public class AddressService {

    AddressLogic address = new AddressLogic();

    public AddressService() {
    }

    public void AddAddress(Address address) throws InvalidUserException {
        this.address.AddAddress(address);
    }

    public void UpdateAddress(Address address) throws InvalidUserException {
        this.address.UpdateAddress(address);
    }

    public void DeleteAddress(int addressID) throws InvalidUserException {
        this.address.DeleteAddress(addressID);
    }
    public List<Address> getAddresses(int userID) throws InvalidUserException {
        return this.address.getAddresses(userID);
    }

    public List<Address> getAddressesByType(int userID, String addressType) throws InvalidUserException {
        return this.address.getAddressesByType(userID, addressType);
    }

}
