package com.aasc.erp.model;
/*
 * @(#)AascIntlShipmentsDAO.java        21/02/2008
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.ol
 */
 
import com.aasc.erp.bean.AascIntlCommodityInfo;
import com.aasc.erp.bean.AascIntlHeaderInfo;

import com.aasc.erp.bean.AascIntlInfo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * AascIntlShipmentsDAO class.
 * @author 	Y Pradeep
 * @version 1
 * @since
 * 28/01/2015   Y Pradeep    Added this file for Internationa Shipping.
 * 11/02/2015   Y Pradeep    Modified datatype of getIntlImporterNames, getIntlBrokerNames from ArrayList to HashMap.
 * 18/02/2015   Y Pradeep    Added code java doc to all methods.
 * 20/03/2015   Y Pradeep    Modified code at getCountryLookups() and getCurrencyCodeLookups() methods to return HashMap instead of LinkedList.
 */

public interface AascIntlShipmentsDAO {

    /** This method is used to save international page header details and returns opstatus.
     * @param userId
     * @param aascIntlHeaderInfo
     * @param carrierCode
     * @param clientId
     * @param locationId
     * @return int
     */
    public int saveIntlShipmentDetails(int userId, AascIntlHeaderInfo aascIntlHeaderInfo, int carrierCode, int clientId, int locationId);

    /** This method is used to insert or update importer details into respective database table for feature use.
     * @param aascIntlHeaderInfo
     * @param carrierCode
     * @param clientId
     * @param locationId
     * @return int
     */
    public int addIntlImporterDetail(AascIntlHeaderInfo aascIntlHeaderInfo, int carrierCode, int clientId, int locationId);

    /** This method is used to insert or update broker details into respective database table for feature use.
     * @param aascIntlHeaderInfo
     * @param clientId
     * @param locationId
     * @return int
     */
    public int addIntlBrokerDetail(AascIntlHeaderInfo aascIntlHeaderInfo, int clientId, int locationId);

    /** This method is used to get international commodity details based on selected commodity name and set it in AascIntlInfo bean.
     * @param orderNumber
     * @param clientId
     * @param locationId
     * @return AascIntlInfo
     */
    public AascIntlInfo getIntlCommodityLineDetails(String orderNumber, int clientId, int locationId);

    /** This method is used to delete added commodity of that order number and returns opstatus.
     * @param commodityNumber
     * @param orderNumber
     * @param clientId
     * @param locationId
     * @return int
     */
    public int deleteIntlCommodityDetails(int commodityNumber, String orderNumber, int clientId, int locationId);

    /** This method is used to save international commodity details into respective table and returns opstatus.
     * @param aascIntlCommodityInfo
     * @param carrierCode
     * @param clientId
     * @param locationId
     * @return int
     */
    public int addIntlCommodityItemDetail(AascIntlCommodityInfo aascIntlCommodityInfo,int carrierCode, int clientId, int locationId);

    /** This method is used to save international commodity details into respective table and returns opstatus.
     * @param userId
     * @param aascIntlCommodityInfo
     * @param carrierCode
     * @param clientId
     * @param locationId
     * @return int
     */
    public int saveIntlCommodityDetails(int userId, AascIntlCommodityInfo aascIntlCommodityInfo, int carrierCode, int clientId, int locationId);

    /** This method is used to get Country code and Country Names.
     * @return HashMap
     */
    public HashMap getCountryLookups();

    /** This method is used to get Commodity descriptions and display them in drop down list in international page and set it to ArrayList.
     * @param clientId
     * @param locationId
     * @return ArrayList
     */
    public ArrayList<String> getIntlCommodityLineItems(int clientId, int locationId);

    /** This method is used to get Importer Names and display them in drop down list in international page and set it to a HashMap.
     * @param clientId
     * @param locationId
     * @return HashMap
     */
    public HashMap getIntlImporterNames( int clientId, int locationId);

    /**  This method is used to get Broker Names and display them in drop down list in international page and set it to a HashMap.
     * @param clientId
     * @param locationId
     * @return HashMap
     */
    public HashMap getIntlBrokerNames( int clientId, int locationId);

    /** This method is used to delete all saved international header details.
     * @param orderNumber
     * @param clientId
     * @param locationId
     * @return int
     */
    public int deleteIntlShipments(String orderNumber, int clientId, int locationId);

    /** This method is used to save international page header details and set it to AascIntlHeaderInfo bean.
     * @param orderNumber
     * @param clientId
     * @param locationId
     * @return AascIntlHeaderInfo
     */
    public AascIntlHeaderInfo getIntlHeaderDetails(String orderNumber, int clientId, int locationId);

    /** This method is used to get international commodity details based on selected commodity name and set it to AascIntlCommodityInfo bean.
     * @param commodityNumber
     * @param orderNumber
     * @param clientId
     * @param locationId
     * @return AascIntlCommodityInfo
     */
    public AascIntlCommodityInfo getIntlCommodityDetails(int commodityNumber, String orderNumber, int clientId, int locationId);

    /** This method is used to get additional comments, declaration statement and international tax id.
     * @param clientId
     * @param locationId
     * @return AascIntlHeaderInfo
     */
    public AascIntlHeaderInfo getCustomIntlHeaderDetails( int clientId, int locationId);

    /** This method is used to get both international header details and international commodity details and set it to AascIntlInfo bean.
     * @param orderNumber
     * @param clientId
     * @param locationId
     * @return AascIntlInfo
     */
    public AascIntlInfo getIntlDetails(String orderNumber, int clientId, int locationId);

    /** This method is used to get commodity details based on ajax call by selecting a commodity from drop down list.
     * @param description
     * @param clientId
     * @param locationId
     * @return String
     */
    public String getIntlCommodityItemDetail(String description, int clientId, int locationId);

    /** This method is used to get importer details based on ajax call by selecting a importer name from drop down list.
     * @param importerName
     * @param clientId
     * @param locationId
     * @return String
     */
    public String getIntlImporterDetail(String importerName, int clientId, int locationId);

    /** This method is used to get broker details based on ajax call by selecting a broker name from drop down list.
     * @param brokerName
     * @param clientId
     * @param locationId
     * @return String
     */
    public String getIntlBrokerDetail(String brokerName, int clientId, int locationId);

    /** This method is used to get Currency Code's from database tables and sets it to a LinkedList
     * @return HashMap
     */
    public HashMap getCurrencyCodeLookups();
    
    /**
     * This method gets the lookup values from backend tables for international shipments
     * and sets to a HashMap.
     * @param carrierCode, lookUpCode
     * @return HashMap
     */
    public HashMap getUpsLookUpValues(int carrierCode, String lookUpCode);
   
}
