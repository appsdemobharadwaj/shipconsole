package com.aasc.erp.bean;

/**
 * AascTrackingDetailsInfo class has the setter and getter methods to store the tracking details.
 *@author Eshwari M
 *@version 1.0
 * History
 * 20-Jan-2015  Y Pradeep            Modified auther and version, also removed commented code.
 */

public class AascTrackingDetailsInfo {
    public AascTrackingDetailsInfo() {
    }
    private String trackingNumber;
    private int shipmentId;
    private int packageId;
    private int carrierCode;
    private String carrierMode;
    private String serverIpAddress;
    private String prefix;
    private int port;
    private String userName;
    private String password;
    private String accountNumber;
    private String meterNumber;
    private String accessLicenseNumber;

    private String deliveryStatus;
    private String deliveredDate;

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setCarrierCode(int carrierCode) {
        this.carrierCode = carrierCode;
    }

    public int getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierMode(String carrierMode) {
        this.carrierMode = carrierMode;
    }

    public String getCarrierMode() {
        return carrierMode;
    }

    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setAccessLicenseNumber(String accessLicenseNumber) {
        this.accessLicenseNumber = accessLicenseNumber;
    }

    public String getAccessLicenseNumber() {
        return accessLicenseNumber;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }
}
