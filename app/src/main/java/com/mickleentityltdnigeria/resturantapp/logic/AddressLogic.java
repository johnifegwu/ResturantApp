package com.mickleentityltdnigeria.resturantapp.logic;

import com.mickleentityltdnigeria.resturantapp.dalc.Dalc;
import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.extensions.AddressChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.List;

public class AddressLogic {

    public Event<AddressChangedHandler> addressChanged = new Event<AddressChangedHandler>();

    public AddressLogic() {
    }

    public void AddAddress(Address address) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        Dalc.Address().AddAddress(address);
        for (AddressChangedHandler listener : addressChanged.listeners())
        {
            listener.invoke(address.getContactAddress());
        }
    }

    public void UpdateAddress(Address address) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        Dalc.Address().UpdateAddress(address);
        for (AddressChangedHandler listener : addressChanged.listeners())
        {
            listener.invoke(address.getContactAddress());
        }
    }

    public void DeleteAddress(String addressID) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        Dalc.Address().DeleteAddress(addressID);
    }

    public List<Address> getAddresses(String userID) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        return Dalc.Address().getAddresses(userID);
    }

    public List<Address> getAddressesByType(String userID, String addressType) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        return Dalc.Address().getAddressesByType(userID, addressType);
    }

}
