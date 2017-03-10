// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.stampsws.proxy;


public class CarrierPickup implements java.io.Serializable {
    protected com.aasc.erp.carrier.stampsws.proxy.Credentials credentials;
    protected java.lang.String authenticator;
    protected java.lang.String firstName;
    protected java.lang.String lastName;
    protected java.lang.String company;
    protected java.lang.String address;
    protected java.lang.String suiteOrApt;
    protected java.lang.String city;
    protected java.lang.String state;
    protected java.lang.String ZIP;
    protected java.lang.String ZIP4;
    protected java.lang.String phoneNumber;
    protected java.lang.String phoneExt;
    protected java.lang.Integer numberOfExpressMailPieces;
    protected java.lang.Integer numberOfPriorityMailPieces;
    protected java.lang.Integer numberOfInternationalPieces;
    protected java.lang.Integer numberOfOtherPieces;
    protected int totalWeightOfPackagesLbs;
    protected com.aasc.erp.carrier.stampsws.proxy.CarrierPickupLocationV1 packageLocation;
    protected java.lang.String specialInstruction;
    
    public CarrierPickup() {
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.Credentials getCredentials() {
        return credentials;
    }
    
    public void setCredentials(com.aasc.erp.carrier.stampsws.proxy.Credentials credentials) {
        this.credentials = credentials;
    }
    
    public java.lang.String getAuthenticator() {
        return authenticator;
    }
    
    public void setAuthenticator(java.lang.String authenticator) {
        this.authenticator = authenticator;
    }
    
    public java.lang.String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }
    
    public java.lang.String getLastName() {
        return lastName;
    }
    
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }
    
    public java.lang.String getCompany() {
        return company;
    }
    
    public void setCompany(java.lang.String company) {
        this.company = company;
    }
    
    public java.lang.String getAddress() {
        return address;
    }
    
    public void setAddress(java.lang.String address) {
        this.address = address;
    }
    
    public java.lang.String getSuiteOrApt() {
        return suiteOrApt;
    }
    
    public void setSuiteOrApt(java.lang.String suiteOrApt) {
        this.suiteOrApt = suiteOrApt;
    }
    
    public java.lang.String getCity() {
        return city;
    }
    
    public void setCity(java.lang.String city) {
        this.city = city;
    }
    
    public java.lang.String getState() {
        return state;
    }
    
    public void setState(java.lang.String state) {
        this.state = state;
    }
    
    public java.lang.String getZIP() {
        return ZIP;
    }
    
    public void setZIP(java.lang.String ZIP) {
        this.ZIP = ZIP;
    }
    
    public java.lang.String getZIP4() {
        return ZIP4;
    }
    
    public void setZIP4(java.lang.String ZIP4) {
        this.ZIP4 = ZIP4;
    }
    
    public java.lang.String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(java.lang.String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public java.lang.String getPhoneExt() {
        return phoneExt;
    }
    
    public void setPhoneExt(java.lang.String phoneExt) {
        this.phoneExt = phoneExt;
    }
    
    public java.lang.Integer getNumberOfExpressMailPieces() {
        return numberOfExpressMailPieces;
    }
    
    public void setNumberOfExpressMailPieces(java.lang.Integer numberOfExpressMailPieces) {
        this.numberOfExpressMailPieces = numberOfExpressMailPieces;
    }
    
    public java.lang.Integer getNumberOfPriorityMailPieces() {
        return numberOfPriorityMailPieces;
    }
    
    public void setNumberOfPriorityMailPieces(java.lang.Integer numberOfPriorityMailPieces) {
        this.numberOfPriorityMailPieces = numberOfPriorityMailPieces;
    }
    
    public java.lang.Integer getNumberOfInternationalPieces() {
        return numberOfInternationalPieces;
    }
    
    public void setNumberOfInternationalPieces(java.lang.Integer numberOfInternationalPieces) {
        this.numberOfInternationalPieces = numberOfInternationalPieces;
    }
    
    public java.lang.Integer getNumberOfOtherPieces() {
        return numberOfOtherPieces;
    }
    
    public void setNumberOfOtherPieces(java.lang.Integer numberOfOtherPieces) {
        this.numberOfOtherPieces = numberOfOtherPieces;
    }
    
    public int getTotalWeightOfPackagesLbs() {
        return totalWeightOfPackagesLbs;
    }
    
    public void setTotalWeightOfPackagesLbs(int totalWeightOfPackagesLbs) {
        this.totalWeightOfPackagesLbs = totalWeightOfPackagesLbs;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.CarrierPickupLocationV1 getPackageLocation() {
        return packageLocation;
    }
    
    public void setPackageLocation(com.aasc.erp.carrier.stampsws.proxy.CarrierPickupLocationV1 packageLocation) {
        this.packageLocation = packageLocation;
    }
    
    public java.lang.String getSpecialInstruction() {
        return specialInstruction;
    }
    
    public void setSpecialInstruction(java.lang.String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }
}
