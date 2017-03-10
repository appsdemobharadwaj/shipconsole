/*

 * @AascERPImportOrdHREC.java        24/12/2014

 * Copyright (c) 2014-15 Apps Associates Pvt. Ltd.

 * All rights reserved.

 */

/**

 * AascERPImportOrdHREC class extends Action class. AascERPImportOrdHREC is a bean class used to hold data for imported orders.

 * @author Jagadish Jain

 * @creation 24/12/2014
  
 * @History
  
 * 24/12/2014   Jagadish Jain   Added required to hold import order data.

 * 11/05/2015   Suman G         Added address3 and email fields. 
 
 */


package com.aasc.erp.bean;

public class AascERPImportOrdHREC {
      String orderNumber;  
      String customerName;
      String contactName;
      String address1;
      String address2;
      String address3;
      String city;
      String state;
      String postalCode;
      String countryCode;
      String phoneNumber;
      String shipFromLocation;
      String shipToLocation;
      String companyName;
      String shipMethodMeaning;
      String carrierPayCode;
      String carrierAccountNumber;
      String refernce1;
      String refernce2;
      String email;
    
    
    public AascERPImportOrdHREC() {
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress2() {
        return address2;
    }
    
    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress3() {
        return address3;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setShipFromLocation(String shipFromLocation) {
        this.shipFromLocation = shipFromLocation;
    }

    public String getShipFromLocation() {
        return shipFromLocation;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setShipMethodMeaning(String shipMethodMeaning) {
        this.shipMethodMeaning = shipMethodMeaning;
    }

    public String getShipMethodMeaning() {
        return shipMethodMeaning;
    }

    public void setCarrierPayCode(String carrierPayCode) {
        this.carrierPayCode = carrierPayCode;
    }

    public String getCarrierPayCode() {
        return carrierPayCode;
    }

    public void setCarrierAccountNumber(String carrierAccountNumber) {
        this.carrierAccountNumber = carrierAccountNumber;
    }

    public String getCarrierAccountNumber() {
        return carrierAccountNumber;
    }

    public void setRefernce1(String refernce1) {
        this.refernce1 = refernce1;
    }

    public String getRefernce1() {
        return refernce1;
    }

    public void setRefernce2(String refernce2) {
        this.refernce2 = refernce2;
    }

    public String getRefernce2() {
        return refernce2;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
    
    public void setShipToLocation(String shipToLocation) {
            this.shipToLocation = shipToLocation;
        }

        public String getShipToLocation() {
            return shipToLocation;
        }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
