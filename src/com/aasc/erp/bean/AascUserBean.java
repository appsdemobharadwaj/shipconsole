package com.aasc.erp.bean;
/*========================================================================+
@(#)AascUserBean.java 05/08/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/
/**
  * AascUserBean is a  bean class that contains getXXX() and setXX() methods for
    User related details,clientID,LocationID,RoleID,ActionType  
  * @author      N Srisha
  * @version 1.0
  * @since
  * 
  * @History
  * 
  *   05-Jan-2014   Eshwari M   Merged Sunanda code : Added comments
  *   10-Jun-2015   Suman G     Added createdByUser to fix #2962.
  *   24-Nov-2015   Y Pradeep   Added locationName variable. Bug #4025.
  **/

public class AascUserBean {
    
    private String firstName;
    private String lastName;
    
    private String userName;
    private String password;
    private int locationId;
    private String locationName = "";
    private String emailAddress;
    private String alternateEmailAddress;
    private String status="";
    private int role;
    int clientID;
    int userID;
    String actionType="";
    private int createdByUser;
    private String addressBookLevel = "";

    
    public AascUserBean() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setAlternateEmailAddress(String alternateEmailAddress) {
        this.alternateEmailAddress = alternateEmailAddress;
    }

    public String getAlternateEmailAddress() {
        return alternateEmailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setCreatedByUser(int createdByUser) {
        this.createdByUser = createdByUser;
    }

    public int getCreatedByUser() {
        return createdByUser;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setAddressBookLevel(String addressBookLevel) {
        this.addressBookLevel = addressBookLevel;
    }

    public String getAddressBookLevel() {
        return addressBookLevel;
    }
}
