package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class ApplicationData  implements Serializable {

    public String ID;
    public String company_Name;
    public String company_Address;
    public String company_City;
    public String company_ZipCode;
    public String company_State;
    public String company_Country;
    public String company_CurrencyCode;
    public String MICKLE_PAY_WALLET_ID;
    public String contact_Person;
    public String contact_Phone;
    public String contact_Email;

    public ApplicationData() {
    }

    public ApplicationData(String ID, String company_Name, String company_Address, String company_City, String company_ZipCode, String company_State, String company_Country, String company_CurrencyCode, String MICKLE_PAY_WALLET_ID, String contact_Person, String contact_Phone, String contact_Email) {
        this.ID = ID;
        this.company_Name = company_Name;
        this.company_Address = company_Address;
        this.company_City = company_City;
        this.company_ZipCode = company_ZipCode;
        this.company_State = company_State;
        this.company_Country = company_Country;
        this.company_CurrencyCode = company_CurrencyCode;
        this.MICKLE_PAY_WALLET_ID = MICKLE_PAY_WALLET_ID;
        this.contact_Person = contact_Person;
        this.contact_Phone = contact_Phone;
        this.contact_Email = contact_Email;
    }

    @Exclude
    public String getID() {
        return ID;
    }

    @Exclude
    public void setID(String ID) {
        this.ID = ID;
    }

    @Exclude
    public String getCompany_Name() {
        return company_Name;
    }

    @Exclude
    public void setCompany_Name(String company_Name) {
        this.company_Name = company_Name;
    }

    @Exclude
    public String getCompany_Address() {
        return company_Address;
    }

    @Exclude
    public void setCompany_Address(String company_Address) {
        this.company_Address = company_Address;
    }

    @Exclude
    public String getCompany_City() {
        return company_City;
    }

    @Exclude
    public void setCompany_City(String company_City) {
        this.company_City = company_City;
    }

    @Exclude
    public String getCompany_ZipCode() {
        return company_ZipCode;
    }

    @Exclude
    public void setCompany_ZipCode(String company_ZipCode) {
        this.company_ZipCode = company_ZipCode;
    }

    @Exclude
    public String getCompany_State() {
        return company_State;
    }

    @Exclude
    public void setCompany_State(String company_State) {
        this.company_State = company_State;
    }

    @Exclude
    public String getCompany_Country() {
        return company_Country;
    }

    @Exclude
    public void setCompany_Country(String company_Country) {
        this.company_Country = company_Country;
    }

    @Exclude
    public String getCompany_CurrencyCode() {
        return company_CurrencyCode;
    }

    @Exclude
    public void setCompany_CurrencyCode(String company_CurrencyCode) {
        this.company_CurrencyCode = company_CurrencyCode;
    }

    @Exclude
    public String getMICKLE_PAY_WALLET_ID() {
        return MICKLE_PAY_WALLET_ID;
    }

    @Exclude
    public void setMICKLE_PAY_WALLET_ID(String MICKLE_PAY_WALLET_ID) {
        this.MICKLE_PAY_WALLET_ID = MICKLE_PAY_WALLET_ID;
    }

    @Exclude
    public String getContact_Person() {
        return contact_Person;
    }

    @Exclude
    public void setContact_Person(String contact_Person) {
        this.contact_Person = contact_Person;
    }

    @Exclude
    public String getContact_Phone() {
        return contact_Phone;
    }

    @Exclude
    public void setContact_Phone(String contact_Phone) {
        this.contact_Phone = contact_Phone;
    }

    @Exclude
    public String getContact_Email() {
        return contact_Email;
    }

    @Exclude
    public void setContact_Email(String contact_Email) {
        this.contact_Email = contact_Email;
    }
}
