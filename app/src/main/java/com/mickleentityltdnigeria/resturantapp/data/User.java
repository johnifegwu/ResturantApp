package com.mickleentityltdnigeria.resturantapp.data;

import java.io.Serializable;

public class User implements Serializable {

    private int userID;
    private String userName;
    private String passWord;
    private String firstName;
    private String middleName;
    private String lastName;
    private String eMail;
    private String mobilePhone;
    private String contactAddress;
    private String city;
    private String zipCode;
    private String state;
    private String country;

    public User(){}

    public User(int userID, String userName, String passWord, String firstName, String middleName,
                String lastName, String eMail, String mobilePhone, String contactAddress, String city, String zipCode, String state, String country){
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.mobilePhone = mobilePhone;
        this.contactAddress = contactAddress;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.country = country;

    }

   public void setUserID(int userID){
        this.userID = userID;
   }
    public int getUserID(){
        return this.userID;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return this.userName;
    }

    public void setPassWord(String passWord){
        this.passWord = passWord;
    }
    public String getPassWord(){
        return this.passWord;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){
        return this.firstName;
    }

    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }
    public String getMiddleName(){
        return this.middleName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){
        return this.lastName;
    }

    public void setEMail(String eMail){
        this.eMail = eMail;
    }
    public String getEMail(){
        return this.eMail;
    }

    public void setMobilePhone(String mobilePhone){
        this.mobilePhone = mobilePhone;
    }
    public String getMobilePhone(){
        return this.mobilePhone;
    }

    public void setContactAddress(String contactAddress){
        this.contactAddress = contactAddress;
    }
    public String getContactAddress(){
        return this.contactAddress;
    }

    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }

    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }
    public String getZipCode(){
        return this.zipCode;
    }

    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }

    public void setCountry(String country){
        this.country = country;
    }
    public String getCountry(){
        return this.country;
    }
}
