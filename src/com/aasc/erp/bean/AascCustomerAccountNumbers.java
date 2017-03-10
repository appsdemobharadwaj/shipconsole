package com.aasc.erp.bean;

public class AascCustomerAccountNumbers {
    public AascCustomerAccountNumbers() {
    }
    
    
    private int carrierCode;
    private int locationId;
    private String thirdParty;
    private String recipient;


    public void setCarrierCode(int carrierCode) {
        this.carrierCode = carrierCode;
    }

    public int getCarrierCode() {
        return carrierCode;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }

    public String getThirdParty() {
        return thirdParty;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }
}

