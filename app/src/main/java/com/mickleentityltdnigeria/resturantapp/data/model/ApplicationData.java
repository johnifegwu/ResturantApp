package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class ApplicationData  implements Serializable {

    public String ID;
    public String companyName;
    public String companyAddress;
    public String companyCity;
    public String companyZipCode;
    public String companyState;
    public String companyCountry;
    public String companyCurrencyCode;
    public String micklePayWalletID;
    public String contactPerson;
    public String contactPhone;
    public String contactEmail;

    public ApplicationData() {
    }

    public ApplicationData(String ID, String companyName, String companyAddress, String companyCity, String companyZipCode, String companyState, String companyCountry, String companyCurrencyCode, String micklePayWalletID, String contactPerson, String contactPhone, String contactEmail) {
        this.ID = ID;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyCity = companyCity;
        this.companyZipCode = companyZipCode;
        this.companyState = companyState;
        this.companyCountry = companyCountry;
        this.companyCurrencyCode = companyCurrencyCode;
        this.micklePayWalletID = micklePayWalletID;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
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
    public String getCompanyName() {
        return companyName;
    }

    @Exclude
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Exclude
    public String getCompanyAddress() {
        return companyAddress;
    }

    @Exclude
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    @Exclude
    public String getCompanyCity() {
        return companyCity;
    }

    @Exclude
    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    @Exclude
    public String getCompanyZipCode() {
        return companyZipCode;
    }

    @Exclude
    public void setCompanyZipCode(String companyZipCode) {
        this.companyZipCode = companyZipCode;
    }

    @Exclude
    public String getCompanyState() {
        return companyState;
    }

    @Exclude
    public void setCompanyState(String companyState) {
        this.companyState = companyState;
    }

    @Exclude
    public String getCompanyCountry() {
        return companyCountry;
    }

    @Exclude
    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    @Exclude
    public String getCompanyCurrencyCode() {
        return companyCurrencyCode;
    }

    @Exclude
    public void setCompanyCurrencyCode(String companyCurrencyCode) {
        this.companyCurrencyCode = companyCurrencyCode;
    }

    @Exclude
    public String getMicklePayWalletID() {
        return micklePayWalletID;
    }

    @Exclude
    public void setMicklePayWalletID(String micklePayWalletID) {
        this.micklePayWalletID = micklePayWalletID;
    }

    @Exclude
    public String getContactPerson() {
        return contactPerson;
    }

    @Exclude
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Exclude
    public String getContactPhone() {
        return contactPhone;
    }

    @Exclude
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Exclude
    public String getContactEmail() {
        return contactEmail;
    }

    @Exclude
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
