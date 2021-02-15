package com.mickleentityltdnigeria.resturantapp.logic;

import com.mickleentityltdnigeria.resturantapp.dalc.Dalc;
import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.List;

public class AddressLogic {

    public AddressLogic() {
    }

    public void AddAddress(Address address) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        Dalc.Address().AddAddress(address);
    }

    public void UpdateAddress(Address address) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        Dalc.Address().UpdateAddress(address);
    }

    public void DeleteAddress(int addressID) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        Dalc.Address().DeleteAddress(addressID);
    }

    public List<Address> getAddresses(int userID) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        return Dalc.Address().getAddresses(userID);
    }

    public List<Address> getAddressesByType(int userID, String addressType) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        return Dalc.Address().getAddressesByType(userID, addressType);
    }

}
