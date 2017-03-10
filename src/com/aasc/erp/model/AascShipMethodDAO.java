/*
 * @(#)AascShipMethodDAO.java        16/01/2006
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.model;

import com.aasc.erp.bean.AascShipMethodInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;



/**
 * Interface that contains method to retreive ship method information. 
 * @author 	
 * @version - 1
 * modified 
 *         15/12/2014  Y Pradeep         Modified alls methods from basic JDBC call's to Spring JDBC call's.
 *         20/12/2014  Y Pradeep         Removed histry comments from SC Cloud.
 *         25/02/2015  Suman G           Added getFreightShopDetails() method to get carrier codes with user defined meaning.
 */
public interface


AascShipMethodDAO {

    /** abstract method to retreive ship method information.
     * @return aascShipMethodInfo AascShipMethodInfo bean 
     * object containing ship method information
     */
    public abstract AascShipMethodInfo getShipMethodInfo(LinkedList sessionList);
    
    /** abstract method to save ship method information.
     * @return List  
     * @param shipmethodlist LinkedList
     */
    public abstract List saveShipMethodInfo(LinkedList shipmethodlist);

    /** abstract method to save ship method information.
     * @return HashMap  
     * @param carrierCode int
     * @param lookupCode String
     */
    public HashMap getLookUpValues(int carrierCode, String lookupCode, String carrierMode);

    /** abstract method to save ship method information.
     * @return HashMap  
     * @param lookupCode String
     */
    public HashMap getLookUpDetails(String lookupCode);

    /** abstract method to save ship method information.
     * @return HashMap  
     * @param carrierId int, clientId int
     */
    public abstract LinkedList getShipMethodMappingInfo(int carrierId, int clientId);

    /** abstract method to save ship method information.
     * @return HashMap  
     * @param carrierId int, clientId int, locationId int, shipMethodIndex int and userDefinedShipMethod String
     */
    public int deleteShipMethod(int clientid,int carrierId,int locationId,int shipMethodIndex, String userDefinedShipMethod );
    
    /** abstract method to get carrier service level and user defined shipmethod.
     * @return LinkedList  
     * @param clientIdInt int
     * @param locationID int
     * @param intlFlag String
     */
    public LinkedList getFreightShopDetails(int clientIdInt, int locationID, String intlFlag);
    
}
