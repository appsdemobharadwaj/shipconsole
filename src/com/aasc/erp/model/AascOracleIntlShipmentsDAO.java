 package com.aasc.erp.model;
/*
 * @(#)AascOracleIntlShipmentsDAO.java        28-JAN-2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.ol
 */
 
import com.aasc.erp.bean.AascAddressInfo;
import com.aasc.erp.bean.AascIntlCommodityInfo;
import com.aasc.erp.bean.AascIntlHeaderInfo;
import com.aasc.erp.bean.AascIntlInfo;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.util.AascLogger;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.Types;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

/**
 * AascOracleIntlShipmentsDAO class.
 * @author 	Y Pradeep
 * @version 1
 * @since
 * 28/01/2015   Y Pradeep    Added this file for Internationa Shipping.
 * 11/02/2015   Y Pradeep    Modified datatype of getIntlImporterNames, getIntlBrokerNames from ArrayList to HashMap.
 * 16/02/2015   Y Pradeep    Removed printStackTrace exception message.
 * 18/02/2015   Y Pradeep    Added code java doc to all methods.
 * 10/03/2015   Y Pradeep    Modifed code to save date field properly.
 * 13/03/2015   Y Pradeep    Modified doulble field from to get values from db in doubleValue format.
 * 20/03/2015   Y Pradeep    Modified code at getCountryLookups() and getCurrencyCodeLookups() methods to return HashMap instead of LinkedList.
 * 31/03/2015   Y Pradeep    Modified code to save empty details for exportLicenseExpiryDate field at FedEx commodity level. Bug #2783.
 * 27/10/2015   G S Shekar   Added Terms of Trade for DHL
 * 04/11/2015   Mahesh V     Added code for declaration statement to print on the commercial invoice
 * 06/11/2015   Shiva G      Modified code for the issue 3925
 */

public class AascOracleIntlShipmentsDAO implements AascIntlShipmentsDAO {

    private int opStatus; // holds the opStatus out parameter value of the  procedure
    private String errorStatus; // holds the errorStatus out parameter value of the procedure   
    private AascIntlHeaderInfo aascIntlHeaderInfo;
    private AascIntlCommodityInfo aascIntlCommodityInfo;
    private int userId; // holds the value return by getUserId() method
    private int shipmentId; //indicates shipment Id for international shipments
    private String voidFlag = ""; // indicates cancellation of a delivery for international shipments
    private String intlPayerType = ""; //indicates payment type for international shipments
    private String intlAccountNumber = ""; //indicates International shipment account number
     private String intlMaskAccountNumber = ""; //indicates International shipment account number
    private String intlCountryCode = ""; //indicates country code for international shipments
    private String intlTermsOfSale = ""; //indicates Terms of sale for international shipments
    private String intlTotalCustomsValue = ""; //indicates total customs value for international shipments
    private double intlFreightCharge; //indicates freight charges need to be paid by the customer for international shipments.
    private double intlInsuranceCharge; //indicates insurance charges for international shipments. 
    private double intlTaxMiscellaneousCharge; //indicates tax miscellaneous charges for international shipments
    private String intlPurpose = ""; //indicates Commercial Invoice purpose for international shipments
    private String intlSedNumber = ""; //indicates International Shipper Export Declaration Number
    private String intlSedType = ""; //indicates International Shipper Export Declaration Type
    private String intlExemptionNumber = ""; //indicates International Exemption Number
    private String intlDocument = ""; //indicates International document
    private String intlNafta = ""; //indicates International NAFTA document
    private String intlComments = ""; //indicates comments for international shipments
    private String intlCustomerInvoiceNumber = ""; //indicates Customer Invoice Number for international shipments
    private int commodityNumber; //indicates commodity number of international shipments
    private int numberOfPieces = 1; //indicates number of pieces for each commodity item
    private String description = ""; //indicates description of item
    private String countryOfManufacture = ""; //indicates country of manufacture of item
    private String harmonizedCode = ""; //indicates harmonized code of item
    private double weight; //indicates weight of item
    private String quantity = ""; //indicates quantity of item
    private String quantityUnits = ""; //indicates units of quantity
    private String unitPrice = ""; //indicates unit price of item
    private String customsValue = ""; //indicates customs value of item
    private String exportLicenseNumber = ""; //indicates export license number of the shipper
    private String exportLicenseExpiryDate; //indicates export license Expiry Date
    private String intlPurchaseOrderNumber = ""; //indicates Customer Purchase order Number for international shipments
    private double intlDiscount; //indicates Discount allowed for international shipments
    private String intlDeclarationStmt = ""; //indicates Declaration for international shipments
    private String intlShipFromTaxId = ""; //indicates Ship From Tax Id for international shipments
    private String intlShipToTaxId = ""; //indicates Ship To Tax Id for international shipments
    private String intlExportDate; // indicates Export Date for international shipments
    private String intlBlanketPeriodBeginDate; // indicates Blanket period Begin Date for international shipments
    private String intlBlanketPeriodEndDate; // indicates Blanket period End Date for international shipments
    private String intlCurrencyCode;
    private String intlInvoiceDate;
    private String intlExportCarrier;

    private String sedPointOfOrigin;
    private String sedModeOfTransport;
    private String sedExportDate;
    private String sedExportingCarrier;
    private String sedInBondCode;
    private String sedEntryNumber;
    private String sedLoadingPier;
    private String sedPortOfExport;
    private String sedPortOfUnloading;
    private String sedCarrierIdentificationCode;
    private String sedContainerized;
    private String sedHazardiousMaterial;
    private String sedRoutedExportTransaction;
    private String sedLicenceExceptionCode;
    private String sedECCN;
    private String sedLicenceNumber;
    private String sedLicenceDate;
    private String sedPartiesToTran;
    private String sedCompanyName;
    private String sedAddressLine1;
    private String sedCity;
    private String sedStateProvinceCode;
    private String sedPostalCode;
    private String sedCountryCode;
    AascAddressInfo sedAddressInfo = null;
    AascAddressInfo soldToAddressInfo = null;
    AascAddressInfo forwardAgentInfo = null;
    AascAddressInfo intermediateConsigneeInfo = null;
    private String commercialInv;
    private String usCertOrigin;
    private String naftaCertOrigin;
    private String shippersExportDecl;
    private String soldToCompanyName;
    private String soldToAddressLine1;
    private String soldToAddressLine2;
    private String soldToCity;
    private String soldToStateProvinceCode;
    private String soldToPostalCode;
    private String soldToCountryCode;
    private String soldToTaxId;
    private String isShipToSameAsSoldTo;

    private String shipFromAttention = "";
    private String shipFromPhone = "";
    private String soldToAttention = "";
    private String soldToPhone = "";
    private String invoiceCurrencyCode = "";
    private double invoiceValue = 0.0;

    private String preferenceCriteria = ""; //indicates Preference Criteria 
    private String producer = ""; //indicates Producer of the item
    private String rvcCalculationMethod = ""; //indicates RVC Calculation Method
    private String tariffCode = ""; //indicates Tariff Code
    private String netCostPeriodBeginDate; // NetCostPeriod Begin Date
    private String netCostPeriodEndDate; // NetCostPeriod End Date
    private double sedTotalValue; //indicates sedTotalValue
    private String scheduleBNumber; //indicates scheduleBNumber
    private String scheduleBQty; //indicates scheduleBQty
    private String scheduleBUOM; //indicates scheduleBUOM
    private String exportType; //indicates exportType
    private String packageWeightUom = "";

    private String companyName;
    private String addressLine1;
    private String city;
    private String stateProvinceCode;
    private String postalCode;
    private String countryCode;
    private String taxId;

    private String fAgentCompanyName;
    private String fAgentAddressLine1;
    private String fAgentCity;
    private String fAgentStateProvinceCode;
    private String fAgentPostalCode;
    private String fAgentCountryCode;
    private String fTaxId;

    private String iConsigneeCompanyName;
    private String iConsigneeAddressLine1;
    private String iConsigneeCity;
    private String iConsigneeStateProvinceCode;
    private String iConsigneePostalCode;
    private String iConsigneeCountryCode;

    private String generateCI;
    private String declarationStmt;
    private String importerName;
    private String importerCompName;
    private String importerPhoneNum;
    private String importerAddress1;
    private String importerAddress2;
    private String importerCity;
    private String importerState;
    private String importerPostalCode;
    private String importerCountryCode;
    private String impIntlSedNumber;
    private String impIntlSedType;

    private String recIntlSedNumber;
    private String recIntlSedType;

    private String brokerName;
    private String brokerCompName;
    private String brokerPhoneNum;
    private String brokerAddress1;
    private String brokerAddress2;
    private String brokerCity;
    private String brokerState;
    private String brokerPostalCode;
    private String brokerCountryCode;

    private String packageListEnclosed;
    private int shippersLoadAndCount;
    private String bookingConfirmationNumber;
    
    //Mahesh Added these two fields for Stamps.com International shipments header data
    private String stampsLicenseNumber = "";
    private String stampsCertificateNumber = "";
    private String otherDescribe = "";

    SimpleJdbcCall simpleJdbcCall;

    private HashMap upsLookUpValues = null;
    Connection connection = null;
    AascOracleDAOFactory connectionFactory = new AascOracleDAOFactory();
    HashMap<String, ?> map1;
    private String orderNumber;
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy");
    java.util.Date date = null;
    private String termsOfTrade; //Shiva added for DHL
    
    public AascOracleIntlShipmentsDAO() {
    }
    
    private static Logger logger = AascLogger.getLogger("AascOracleIntlShipmentsDAO");

    /**
     * This method can replace the null values with nullString
     * @return String that is ""
     * @param obj of type Object
     */
    String nullStrToSpc(Object obj) {
        String spcStr = "";
        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }
    
    /** This method is used to get Country code and Country Names.
     * @return HashMap
     */
     public HashMap getCountryLookups(){
        logger.info("Enter in to the getCountryLookups() method");
        HashMap countryDetailHashMap = new HashMap();
        String countryCode = "";
        String countryName = "";

        try{
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_country_lookups")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("aasc_get_country_lookups", OracleTypes.CURSOR));
                                                           
            Map<String, Object> out = simpleJdbcCall.execute();
            
//            logger.info("After Execute");
//            logger.info("out ::: " + out.toString());
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            errorStatus = String.valueOf(out.get("op_error_status"));
            Iterator it = ((ArrayList)out.get("OP_COUNTRY_LOOKUPS")).iterator();
            
            while (it.hasNext()) {
                
                map1 = (HashMap<String, ?>)it.next();
    
                countryCode = nullStrToSpc(String.valueOf(map1.get("COUNTRY_CODE")));
                countryName = nullStrToSpc(String.valueOf(map1.get("COUNTRY_NAME")));    
                
                countryDetailHashMap.put(countryCode, countryName);
            }
        } catch(Exception e){
             logger.severe("Exception e : "+e.getMessage());
//             e.printStackTrace();
        }
        logger.info("Exit from getCountryLookups() method");
        
        return countryDetailHashMap;
    }

    /** This method is used to save international page header details and returns opstatus.
     * @param userId
     * @param aascIntlHeaderInfo
     * @param carrierCode
     * @param clientId
     * @param locationId
     * @return int
     */
    public int saveIntlShipmentDetails(int userId, AascIntlHeaderInfo aascIntlHeaderInfo, int carrierCode, int clientId, int locationId) {
        logger.info("Entered saveIntlShipmentDetails()");
        sedAddressInfo = aascIntlHeaderInfo.getSedAddressInfo();
        soldToAddressInfo = aascIntlHeaderInfo.getSoldToAddressInfo();
        intermediateConsigneeInfo = aascIntlHeaderInfo.getIntermediateConsigneeInfo();
        forwardAgentInfo = aascIntlHeaderInfo.getForwardAgentInfo();
        java.util.Date date = null;
        java.sql.Date sqlIntlExportDate = null ;
        java.sql.Date sqlsedLicenceDate = null;
        java.sql.Date sqlSedExportDate = null;
        java.sql.Date sqlIntlInvoiceDate = null;
        java.sql.Date sqlIntlBlanketPeriodEndDate = null;
        java.sql.Date sqlIntlBlanketPeriodBeginDate = null;
        
        try{
            this.aascIntlHeaderInfo = aascIntlHeaderInfo; // assingning the value of aascDeliveryInfo 
            this.userId = userId; // assigning the value of userId argument to the instance variable
            orderNumber = aascIntlHeaderInfo.getOrderNumber();
            shipmentId = aascIntlHeaderInfo.getShipmentId();
            voidFlag = aascIntlHeaderInfo.getVoidFlag();
            intlPayerType = aascIntlHeaderInfo.getIntlPayerType();
            intlAccountNumber = aascIntlHeaderInfo.getIntlAccountNumber();
          //  System.out.println("intlAccountNumber::"+intlAccountNumber);
            intlMaskAccountNumber = aascIntlHeaderInfo.getIntlMaskAccountNumber();
          //  System.out.println("intlMaskAccountNumber::"+intlMaskAccountNumber);
             
            intlCountryCode = aascIntlHeaderInfo.getIntlCountryCode();
            intlTermsOfSale = aascIntlHeaderInfo.getIntlTermsOfSale();
            intlTotalCustomsValue = aascIntlHeaderInfo.getIntlTotalCustomsValue();
            intlFreightCharge = aascIntlHeaderInfo.getIntlFreightCharge();
            intlInsuranceCharge = aascIntlHeaderInfo.getIntlInsuranceCharge();
            intlTaxMiscellaneousCharge = aascIntlHeaderInfo.getIntlTaxMiscellaneousCharge();
            intlPurpose = aascIntlHeaderInfo.getIntlPurpose();
            intlSedNumber = aascIntlHeaderInfo.getIntlSedNumber();
            intlSedType = aascIntlHeaderInfo.getIntlSedType();
            intlExemptionNumber = aascIntlHeaderInfo.getIntlExemptionNumber();
            intlDocument = aascIntlHeaderInfo.getIntlDocument();
            intlNafta = aascIntlHeaderInfo.getIntlNafta();
            intlComments = aascIntlHeaderInfo.getIntlComments();
            intlCustomerInvoiceNumber = aascIntlHeaderInfo.getIntlCustomerInvoiceNumber();
            intlShipFromTaxId = aascIntlHeaderInfo.getIntlShipFromTaxId();
            intlShipToTaxId = aascIntlHeaderInfo.getIntlShipToTaxId();
            intlPurchaseOrderNumber = aascIntlHeaderInfo.getIntlPurchaseOrderNumber();
            intlDiscount = aascIntlHeaderInfo.getIntlDiscount();
            intlDeclarationStmt = aascIntlHeaderInfo.getIntlDeclarationStmt();
            
            intlExportDate = aascIntlHeaderInfo.getIntlExportDate();
            if(!"".equalsIgnoreCase(intlExportDate) && intlExportDate != null){
                try {
                    date = sdf1.parse(intlExportDate);
                } catch (ParseException e) {
                    date = new java.util.Date() ;
                    logger.severe("Exception at intlExportDate e : "+e.getMessage());
                }
                sqlIntlExportDate = new java.sql.Date(date.getTime()); 
            } 
            
            intlExportCarrier = aascIntlHeaderInfo.getIntlExportCarrier();
            
            intlBlanketPeriodBeginDate = aascIntlHeaderInfo.getIntlBlanketPeriodBeginDate();
            if(!"".equalsIgnoreCase(intlBlanketPeriodBeginDate) && intlBlanketPeriodBeginDate != null){
                try {
                    date = sdf1.parse(intlBlanketPeriodBeginDate);
                } catch (ParseException e) {
                    date = new java.util.Date() ;
                    logger.severe("Exception at intlBlanketPeriodBeginDate e : "+e.getMessage());
                }
                sqlIntlBlanketPeriodBeginDate = new java.sql.Date(date.getTime());  
            }
            
            intlBlanketPeriodEndDate = aascIntlHeaderInfo.getIntlBlanketPeriodEndDate();
            if(!"".equalsIgnoreCase(intlBlanketPeriodEndDate) && intlBlanketPeriodEndDate != null){
                try {
                    date = sdf1.parse(intlBlanketPeriodEndDate);
                } catch (ParseException e) {
                    date = new java.util.Date() ;
                    logger.severe("Exception at intlBlanketPeriodEndDate e : "+e.getMessage());
                }
                sqlIntlBlanketPeriodEndDate = new java.sql.Date(date.getTime());  
            }
            
            intlInvoiceDate = aascIntlHeaderInfo.getIntlInvoiceDate();
            if(!"".equalsIgnoreCase(intlInvoiceDate) && intlInvoiceDate != null){
                try {
                    date = sdf2.parse(intlInvoiceDate);
                } catch (ParseException e) {
                    date = new java.util.Date() ;
                    logger.severe("Exception at intlInvoiceDate e : "+e.getMessage());
                }
                sqlIntlInvoiceDate = new java.sql.Date(date.getTime());  
            }
            
            intlCurrencyCode = aascIntlHeaderInfo.getIntlCurrencyCode();
            sedPointOfOrigin = aascIntlHeaderInfo.getSedPointOfOrigin();
            sedModeOfTransport = aascIntlHeaderInfo.getSedModeOfTransport();

            sedExportDate = aascIntlHeaderInfo.getSedExportDate();
            if(!"".equalsIgnoreCase(sedExportDate) && sedExportDate != null){
                try {
                    date = sdf1.parse(sedExportDate);
                } catch (ParseException e) {
                    date = new java.util.Date() ;
                    logger.severe("Exception at sedExportDate e : "+e.getMessage());
                }
                sqlSedExportDate = new java.sql.Date(date.getTime());
            } 
            
            sedExportingCarrier = aascIntlHeaderInfo.getSedExportingCarrier();
            sedInBondCode = aascIntlHeaderInfo.getSedInBondCode();
            sedEntryNumber = aascIntlHeaderInfo.getSedEntryNumber();
            if(sedEntryNumber == null)
            {
                sedEntryNumber = "";
            }
            sedLoadingPier = aascIntlHeaderInfo.getSedLoadingPier();
            sedPortOfExport = aascIntlHeaderInfo.getSedPortOfExport();
            sedPortOfUnloading = aascIntlHeaderInfo.getSedPortOfUnloading();
            sedCarrierIdentificationCode = aascIntlHeaderInfo.getSedCarrierIdentificationCode();
            sedContainerized = aascIntlHeaderInfo.getSedContainerized();
            sedHazardiousMaterial = aascIntlHeaderInfo.getSedHazardiousMaterial();
            sedRoutedExportTransaction = aascIntlHeaderInfo.getSedRoutedExportTransaction();
            sedLicenceExceptionCode = aascIntlHeaderInfo.getSedLicenceExceptionCode();
            sedECCN = aascIntlHeaderInfo.getSedECCN();
            sedLicenceNumber = aascIntlHeaderInfo.getSedLicenceNumber();
            
            sedLicenceDate = aascIntlHeaderInfo.getSedLicenceDate();
            if(!"".equalsIgnoreCase(sedLicenceDate) && sedLicenceDate != null){
                try {
                    date = sdf1.parse(sedLicenceDate);
                } catch (ParseException e) {
                    date = new java.util.Date() ;
                    logger.severe("Exception at sedLicenceDate e : "+e.getMessage());
                }
                sqlsedLicenceDate = new java.sql.Date(date.getTime());
            }            
            
            sedPartiesToTran = aascIntlHeaderInfo.getSedPartiesToTran();
            sedCompanyName = sedAddressInfo.getCompanyName();
            sedAddressLine1 = sedAddressInfo.getAddressLine1();
            sedCity = sedAddressInfo.getCity();
            sedStateProvinceCode = sedAddressInfo.getStateProvinceCode();
            sedPostalCode = sedAddressInfo.getPostalCode();
            sedCountryCode = sedAddressInfo.getCountryCode();
            commercialInv = aascIntlHeaderInfo.getCommercialInv();
            usCertOrigin = aascIntlHeaderInfo.getUsCertOrigin();
            naftaCertOrigin = aascIntlHeaderInfo.getNaftaCertOrigin();
            shippersExportDecl = aascIntlHeaderInfo.getShippersExportDecl();
            soldToCompanyName = soldToAddressInfo.getCompanyName();
            soldToAddressLine1 = soldToAddressInfo.getAddressLine1();
            soldToAddressLine2 = soldToAddressInfo.getAddressLine2();
            soldToCity = soldToAddressInfo.getCity();
            soldToStateProvinceCode = soldToAddressInfo.getStateProvinceCode();
            soldToPostalCode = soldToAddressInfo.getPostalCode();
            soldToCountryCode = soldToAddressInfo.getCountryCode();
            soldToTaxId = aascIntlHeaderInfo.getSoldToTaxId();
            isShipToSameAsSoldTo = aascIntlHeaderInfo.getIsShipToSameAsSoldTo();
            shipFromAttention = aascIntlHeaderInfo.getShipFromAttention();
            shipFromPhone = aascIntlHeaderInfo.getShipFromPhone();
            soldToAttention = aascIntlHeaderInfo.getSoldToAttention();
            soldToPhone = aascIntlHeaderInfo.getSoldToPhone();
            invoiceCurrencyCode = aascIntlHeaderInfo.getInvoiceCurrencyCode();
            invoiceValue = aascIntlHeaderInfo.getInvoiceValue();
            
            fAgentCompanyName = forwardAgentInfo.getCompanyName();
            fAgentAddressLine1 = forwardAgentInfo.getAddressLine1();
            fAgentCity = forwardAgentInfo.getCity();
            fAgentStateProvinceCode = forwardAgentInfo.getStateProvinceCode();
            fAgentPostalCode = forwardAgentInfo.getPostalCode();
            fAgentCountryCode = forwardAgentInfo.getCountryCode();
            fTaxId = forwardAgentInfo.getTaxId();
            iConsigneeCompanyName = intermediateConsigneeInfo.getCompanyName();
            iConsigneeAddressLine1 = intermediateConsigneeInfo.getAddressLine1();
            iConsigneeCity = intermediateConsigneeInfo.getCity();
            iConsigneeStateProvinceCode = intermediateConsigneeInfo.getStateProvinceCode();
            iConsigneePostalCode = intermediateConsigneeInfo.getPostalCode();
            iConsigneeCountryCode = intermediateConsigneeInfo.getCountryCode();
            packageListEnclosed = aascIntlHeaderInfo.getPackingListEnclosed();
            shippersLoadAndCount = aascIntlHeaderInfo.getShippersLoadAndCount();
            bookingConfirmationNumber = aascIntlHeaderInfo.getBookingConfirmationNumber();
            declarationStmt = aascIntlHeaderInfo.getDeclarationStmt();
            importerName = aascIntlHeaderInfo.getImporterName();
            importerCompName = aascIntlHeaderInfo.getImporterCompName();
            importerPhoneNum = aascIntlHeaderInfo.getImporterPhoneNum();
            importerAddress1 = aascIntlHeaderInfo.getImporterAddress1();
            importerAddress2 = aascIntlHeaderInfo.getImporterAddress2();
            importerCity = aascIntlHeaderInfo.getImporterCity();
            importerState = aascIntlHeaderInfo.getImporterState();
            importerPostalCode = aascIntlHeaderInfo.getImporterPostalCode();
            importerCountryCode = aascIntlHeaderInfo.getImporterCountryCode();
            impIntlSedNumber = aascIntlHeaderInfo.getImpIntlSedNumber();
            impIntlSedType = aascIntlHeaderInfo.getImpIntlSedType();
            generateCI = aascIntlHeaderInfo.getGenerateCI();
            recIntlSedNumber = aascIntlHeaderInfo.getRecIntlSedNumber();
            recIntlSedType = aascIntlHeaderInfo.getRecIntlSedType();
            brokerName = aascIntlHeaderInfo.getBrokerName();
            brokerCompName = aascIntlHeaderInfo.getBrokerCompName();
            brokerPhoneNum = aascIntlHeaderInfo.getBrokerPhoneNum();
            brokerAddress1 = aascIntlHeaderInfo.getBrokerAddress1();
            brokerAddress2 = aascIntlHeaderInfo.getBrokerAddress2();
            brokerCity = aascIntlHeaderInfo.getBrokerCity();
            brokerState = aascIntlHeaderInfo.getBrokerState();
            brokerPostalCode = aascIntlHeaderInfo.getBrokerPostalCode();
            brokerCountryCode = aascIntlHeaderInfo.getBrokerCountryCode();
            termsOfTrade = aascIntlHeaderInfo.getTermsOfTrade();
            logger.info("termsOfTrade::::"+termsOfTrade);
            
            stampsLicenseNumber = aascIntlHeaderInfo.getStampsLicenseNumber();
            logger.info("stampsLicenseNumber::"+stampsLicenseNumber);

            stampsCertificateNumber = aascIntlHeaderInfo.getStampsCertificateNumber();
            logger.info("stampsCertificateNumber::"+stampsCertificateNumber);

            otherDescribe = aascIntlHeaderInfo.getOtherDescribe();
            logger.info("otherDescribe::"+otherDescribe);

            DataSource datasource = connectionFactory.createDataSource();   // returns datasource
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("save_intl_shipments")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
                                                           
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_order_number", orderNumber)
                                                                        .addValue("ip_shipment_id", shipmentId)
                                                                        .addValue("ip_void_flag", voidFlag)
                                                                        .addValue("ip_intl_payertype", intlPayerType)
                                                                        .addValue("ip_intl_acc_no", intlAccountNumber)
                                                                        .addValue("ip_intl_country_code", intlCountryCode)
                                                                        .addValue("ip_intl_terms_of_sale", intlTermsOfSale)
                                                                        .addValue("ip_intl_total_customs_value", intlTotalCustomsValue)
                                                                        .addValue("ip_intl_freight_charge", intlFreightCharge)
                                                                        .addValue("ip_intl_insurance_charge", intlInsuranceCharge)
                                                                        .addValue("ip_intl_tax_misce_chg", intlTaxMiscellaneousCharge)
                                                                        .addValue("ip_intl_purpose", intlPurpose)
                                                                        .addValue("ip_intl_sed_no", intlSedNumber)
                                                                        .addValue("ip_intl_sed_type", intlSedType)
                                                                        .addValue("ip_intl_exemption_no", intlExemptionNumber)
                                                                        .addValue("ip_intl_document", intlDocument)
                                                                        .addValue("ip_intl_nafta", intlNafta)
                                                                        .addValue("ip_intl_comments", intlComments)
                                                                        .addValue("ip_intl_cust_invoice_no", intlCustomerInvoiceNumber)
                                                                        .addValue("ip_ship_from_tax_id", intlShipFromTaxId)
                                                                        .addValue("ip_ship_to_tax_id", intlShipToTaxId)
                                                                        .addValue("ip_po_number", intlPurchaseOrderNumber)
                                                                        .addValue("ip_discount", intlDiscount)
                                                                        .addValue("ip_decl_stmt", intlDeclarationStmt)
                                                                        .addValue("ip_export_date", sqlIntlExportDate, Types.DATE)
                                                                        .addValue("ip_export_carrier", intlExportCarrier)
                                                                        .addValue("ip_blanket_date_begin", sqlIntlBlanketPeriodBeginDate, Types.DATE)
                                                                        .addValue("ip_blanket_date_end", sqlIntlBlanketPeriodEndDate, Types.DATE)
                                                                        .addValue("ip_invoice_date", sqlIntlInvoiceDate, Types.DATE)
                                                                        .addValue("ip_currency_code", intlCurrencyCode)
                                                                        .addValue("ip_point_of_origin", sedPointOfOrigin)
                                                                        .addValue("ip_mode_of_transport", sedModeOfTransport)
                                                                        .addValue("ip_sed_export_date", sqlSedExportDate, Types.DATE)
                                                                        .addValue("ip_sed_exporting_carrier", sedExportingCarrier)
                                                                        .addValue("ip_in_bond_code", sedInBondCode)
                                                                        .addValue("ip_entry_number", sedEntryNumber)
                                                                        .addValue("ip_loading_pier", sedLoadingPier)
                                                                        .addValue("ip_port_of_export", sedPortOfExport)
                                                                        .addValue("ip_port_of_unloading", sedPortOfUnloading)
                                                                        .addValue("ip_carrier_identification_code", sedCarrierIdentificationCode)
                                                                        .addValue("ip_containerized", sedContainerized)
                                                                        .addValue("ip_hazardious_material", sedHazardiousMaterial)
                                                                        .addValue("ip_routed_export_transaction", sedRoutedExportTransaction)
                                                                        .addValue("ip_licence_exception_code", sedLicenceExceptionCode)
                                                                        .addValue("ip_eccn", sedECCN)
                                                                        .addValue("ip_licence_number", sedLicenceNumber)
                                                                        .addValue("ip_licence_date", sqlsedLicenceDate, Types.DATE)
                                                                        .addValue("ip_parties_to_tran", sedPartiesToTran)
                                                                        .addValue("ip_user_id", userId)
                                                                        .addValue("ip_company_name", sedCompanyName)
                                                                        .addValue("ip_address1", sedAddressLine1)
                                                                        .addValue("ip_city", sedCity)
                                                                        .addValue("ip_state_provience_code", sedStateProvinceCode)
                                                                        .addValue("ip_postal_code", sedPostalCode)
                                                                        .addValue("ip_country_code", sedCountryCode)
                                                                        .addValue("ip_commercial_inv", commercialInv)
                                                                        .addValue("ip_us_cert_origin", usCertOrigin)
                                                                        .addValue("ip_nafta_cert_origin", naftaCertOrigin)
                                                                        .addValue("ip_shippers_export_decl", shippersExportDecl)
                                                                        .addValue("ip_soldto_company_name", soldToCompanyName)
                                                                        .addValue("ip_soldto_address1", soldToAddressLine1)
                                                                        .addValue("ip_soldto_address2", soldToAddressLine2)
                                                                        .addValue("ip_soldto_city", soldToCity)
                                                                        .addValue("ip_soldto_state_provience_code", soldToStateProvinceCode)
                                                                        .addValue("ip_soldto_postal_code", soldToPostalCode)
                                                                        .addValue("ip_soldto_country_code", soldToCountryCode)
                                                                        .addValue("ip_soldto_tax_id", soldToTaxId)
                                                                        .addValue("ip_is_shipto_same_as_soldto", isShipToSameAsSoldTo)
                                                                        .addValue("ip_ship_from_phone_number", shipFromPhone)
                                                                        .addValue("ip_ship_from_attension_name", shipFromAttention)
                                                                        .addValue("ip_sold_to_attension_name", soldToAttention)
                                                                        .addValue("ip_sold_to_phone_number", soldToPhone)
                                                                        .addValue("ip_invoice_currency_code", invoiceCurrencyCode)
                                                                        .addValue("ip_invoice_monitory_value", invoiceValue)
                                                                        .addValue("ip_carrier_code", carrierCode)
                                                                        .addValue("ip_fa_company_name", fAgentCompanyName)
                                                                        .addValue("ip_fa_address1", fAgentAddressLine1)
                                                                        .addValue("ip_fa_city", fAgentCity)
                                                                        .addValue("ip_fa_state_provience_code", fAgentStateProvinceCode)
                                                                        .addValue("ip_fa_postal_code", fAgentPostalCode)
                                                                        .addValue("ip_fa_country_code", fAgentCountryCode)
                                                                        .addValue("ip_ic_company_name", iConsigneeCompanyName)
                                                                        .addValue("ip_ic_address1", iConsigneeAddressLine1)
                                                                        .addValue("ip_ic_city", iConsigneeCity)
                                                                        .addValue("ip_ic_state_provience_code", iConsigneeStateProvinceCode)
                                                                        .addValue("ip_ic_postal_code", iConsigneePostalCode)
                                                                        .addValue("ip_ic_country_code", iConsigneeCountryCode)
                                                                        .addValue("ip_fa_tax_id", fTaxId)
                                                                        .addValue("ip_PACKING_LIST_ENCLOSED", packageListEnclosed)
                                                                        .addValue("ip_shippers_Load_And_Count", shippersLoadAndCount)
                                                                        .addValue("Ip_Booking_Confirmation_Number", bookingConfirmationNumber)
                                                                        .addValue("Ip_Declaration_Stmt", declarationStmt)
                                                                        .addValue("Ip_Importer_Name", importerName)
                                                                        .addValue("Ip_Importer_Comp_Name", importerCompName)
                                                                        .addValue("Ip_Importer_Phone_Num", importerPhoneNum)
                                                                        .addValue("Ip_Importer_Address1", importerAddress1)
                                                                        .addValue("Ip_Importer_Address2", importerAddress2)
                                                                        .addValue("Ip_Importer_City", importerCity)
                                                                        .addValue("Ip_Importer_State", importerState)
                                                                        .addValue("Ip_Importer_Postal_Code", importerPostalCode)
                                                                        .addValue("Ip_Importer_Country_Code", importerCountryCode)
                                                                        .addValue("Ip_Imp_Intl_Sed_Number", impIntlSedNumber)
                                                                        .addValue("Ip_Imp_Intl_Sed_Type", impIntlSedType)
                                                                        .addValue("Ip_Rec_Intl_Sed_Number", recIntlSedNumber)
                                                                        .addValue("ip_rec_intl_sed_type", recIntlSedType)
                                                                        .addValue("ip_generate_ci", generateCI)
                                                                        .addValue("Ip_Broker_Name", brokerName)
                                                                        .addValue("Ip_Broker_Comp_Name", brokerCompName)
                                                                        .addValue("Ip_Broker_Phone_Num", brokerPhoneNum)
                                                                        .addValue("Ip_Broker_Address1", brokerAddress1)
                                                                        .addValue("Ip_Broker_Address2", brokerAddress2)
                                                                        .addValue("Ip_Broker_City", brokerCity)
                                                                        .addValue("Ip_Broker_State", brokerState)
                                                                        .addValue("Ip_Broker_Postal_Code", brokerPostalCode)
                                                                        .addValue("IP_BROKER_COUNTRY_CODE", brokerCountryCode)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId)
                                                                        .addValue("Ip_Stamps_License_Number", stampsLicenseNumber)
                                                                        .addValue("Ip_Stamps_Certificate_Number", stampsCertificateNumber)
                                                                        .addValue("IP_OTHER_DESCRIBE", otherDescribe)
                                                                        .addValue("IP_TERMS_OF_TRADE", termsOfTrade)
                                                                        .addValue("ip_intl_mask_acc_no", intlMaskAccountNumber);
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("Out == "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            
            errorStatus = String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.save_intl_importer_detail opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("Exit from saveIntlShipmentDetails method");
        
        return opStatus;
    }

    /** This method is used to insert or update importer details into respective database table for feature use.
     * @param aascIntlHeaderInfo
     * @param carrierCode
     * @param clientId
     * @param locationId
     * @return int
     */
     public int addIntlImporterDetail(AascIntlHeaderInfo aascIntlHeaderInfo, int carrierCode, int clientId, int locationId) {
        logger.info("Entered addIntlImporterDetail method");
        try{
        
            this.aascIntlHeaderInfo = aascIntlHeaderInfo; // assingning the value of aascIntlCommodityInfo 

            if(carrierCode == 110 || carrierCode == 111){
                impIntlSedNumber = aascIntlHeaderInfo.getImpIntlSedNumber();
                impIntlSedType = aascIntlHeaderInfo.getImpIntlSedType();
                importerAddress1 = aascIntlHeaderInfo.getImporterAddress1();
                importerAddress2 = aascIntlHeaderInfo.getImporterAddress2();
                importerCity = aascIntlHeaderInfo.getImporterCity();
                importerCompName = aascIntlHeaderInfo.getImporterCompName();
                importerCountryCode = aascIntlHeaderInfo.getImporterCountryCode();
                importerName = aascIntlHeaderInfo.getImporterName();
                importerPhoneNum = aascIntlHeaderInfo.getImporterPhoneNum();
                importerPostalCode = aascIntlHeaderInfo.getImporterPostalCode();
                importerState = aascIntlHeaderInfo.getImporterState();
            } else if (carrierCode == 100){
                AascAddressInfo aascSoldToAddressInfo = aascIntlHeaderInfo.getSoldToAddressInfo();
                impIntlSedNumber = aascIntlHeaderInfo.getSoldToTaxId();
                impIntlSedType = aascIntlHeaderInfo.getImpIntlSedType();
                importerAddress1 = aascSoldToAddressInfo.getAddressLine1();
                importerAddress2 = aascIntlHeaderInfo.getImporterAddress2();
                importerCity = aascSoldToAddressInfo.getCity();
                importerCompName = aascSoldToAddressInfo.getCompanyName();
                importerCountryCode = aascSoldToAddressInfo.getCountryCode();
                importerName = aascIntlHeaderInfo.getSoldToAttention();
                importerPhoneNum = aascIntlHeaderInfo.getSoldToPhone();
                importerPostalCode = aascSoldToAddressInfo.getPostalCode();
                importerState = aascSoldToAddressInfo.getStateProvinceCode();
            }
            
            DataSource datasource = connectionFactory.createDataSource();   // returns datasource
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("save_intl_importer_detail")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
                                                           
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("Ip_Importer_Name", importerName)
                                                                        .addValue("Ip_Importer_Comp_Name", importerCompName)
                                                                        .addValue("Ip_Importer_Phone_Num", importerPhoneNum)
                                                                        .addValue("Ip_Importer_Address1", importerAddress1)
                                                                        .addValue("Ip_Importer_Address2", importerAddress2)
                                                                        .addValue("Ip_Importer_City", importerCity)
                                                                        .addValue("Ip_Importer_State", importerState)
                                                                        .addValue("Ip_Importer_Postal_Code", importerPostalCode)
                                                                        .addValue("Ip_Importer_Country_Code", importerCountryCode)
                                                                        .addValue("Ip_Imp_Intl_Sed_Number", impIntlSedNumber)
                                                                        .addValue("Ip_Imp_Intl_Sed_Type", impIntlSedType)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("Out == "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            
            errorStatus = String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.save_intl_importer_detail opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
            
            if (opStatus == 120) {
                logger.info("Intl Importer details  are saved successfully");
            } else {
                logger.info("Intl Importer details not saved");
            }
            
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
            opStatus = 122;
//            e.printStackTrace();
        }
        logger.info("Exit from addIntlImporterDetail method");
        
        return opStatus;
    }

    /** This method is used to insert or update broker details into respective database table for feature use.
     * @param aascIntlHeaderInfo
     * @param clientId
     * @param locationId
     * @return int
     */
     public int addIntlBrokerDetail(AascIntlHeaderInfo aascIntlHeaderInfo, int clientId, int locationId) {
        logger.info("Entered addIntlBrokerDetail method");
        try{
            
            this.aascIntlHeaderInfo = aascIntlHeaderInfo; // assingning the value of aascIntlCommodityInfo 
            brokerAddress1 = aascIntlHeaderInfo.getBrokerAddress1();
            brokerAddress2 = aascIntlHeaderInfo.getBrokerAddress2();
            brokerCity = aascIntlHeaderInfo.getBrokerCity();
            brokerCompName = aascIntlHeaderInfo.getBrokerCompName();
            brokerCountryCode = aascIntlHeaderInfo.getBrokerCountryCode();
            brokerName = aascIntlHeaderInfo.getBrokerName();
            brokerPhoneNum = aascIntlHeaderInfo.getBrokerPhoneNum();
            brokerPostalCode = aascIntlHeaderInfo.getBrokerPostalCode();
            brokerState = aascIntlHeaderInfo.getBrokerState();
            
            DataSource datasource = connectionFactory.createDataSource();   // returns datasource
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("save_intl_broker_detail")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
                                                           
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("Ip_broker_Name", brokerName)
                                                                        .addValue("Ip_broker_Comp_Name", brokerCompName)
                                                                        .addValue("Ip_broker_Phone_Num", brokerPhoneNum)
                                                                        .addValue("Ip_broker_Address1", brokerAddress1)
                                                                        .addValue("Ip_broker_Address2", brokerAddress2)
                                                                        .addValue("Ip_broker_City", brokerCity)
                                                                        .addValue("Ip_broker_State", brokerState)
                                                                        .addValue("Ip_broker_Postal_Code", brokerPostalCode)
                                                                        .addValue("Ip_broker_Country_Code", brokerCountryCode)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("Out == "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            
            errorStatus = String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.save_intl_broker_detail opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
            
            if (opStatus == 120) {
                logger.info("Intl broker details  are saved successfully");
            } else {
                logger.info("Intl broker details not saved");
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
            opStatus = 122;
//            e.printStackTrace();
        }
        logger.info("Exit from addIntlBrokerDetail method");
        
        return opStatus;
    }

    /** This method is used to get international commodity details based on selected commodity name and set it in AascIntlInfo bean.
     * @param orderNumber
     * @param clientId
     * @param locationId
     * @return AascIntlInfo
     */
     public AascIntlInfo getIntlCommodityLineDetails(String orderNumber, int clientId, int locationId) {
    
        logger.info("enter in to the getIntlCommodityLineDetails() method");
        
        AascIntlCommodityInfo aascIntlCommodityInfo = null;
        AascAddressInfo aascAddressInfo = null;
        AascIntlInfo aascIntlInfo = null;
        try{
            DataSource datasource = connectionFactory.createDataSource();   // returns datasource
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_intl_commodity_details")
                                                           .declareParameters(new SqlOutParameter("aasc_intl_commodity_details", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
                                                           
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_order_number", orderNumber)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("Out == "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            
            errorStatus = String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.get_intl_commodity_details opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
            if(opStatus == 120){
                Iterator intlCommodityDetailsIt = ((ArrayList)out.get("OP_COMMODITY_DETAILS")).iterator();
                
                LinkedList intlLinkedList = new LinkedList();
                aascIntlInfo = new AascIntlInfo();
                
                while(intlCommodityDetailsIt.hasNext()){
                
                    map1 = (HashMap<String, ?>)intlCommodityDetailsIt.next();
                    aascIntlCommodityInfo = new AascIntlCommodityInfo();
                    aascAddressInfo = new AascAddressInfo();
                    
                    aascIntlCommodityInfo.setCommodityNumber(((BigDecimal)map1.get("commodity_no")).intValue());
                    aascIntlCommodityInfo.setNumberOfPieces(((BigDecimal)map1.get("number_of_pieces")).intValue());
                    aascIntlCommodityInfo.setDescription(nullStrToSpc(String.valueOf(map1.get("description"))));
                    aascIntlCommodityInfo.setCountryOfManufacture(nullStrToSpc(String.valueOf(map1.get("country_of_manufacture"))));
                    aascIntlCommodityInfo.setHarmonizedCode(nullStrToSpc(String.valueOf(map1.get("harmonized_code"))));
                    aascIntlCommodityInfo.setWeight(((BigDecimal)map1.get("weight")).doubleValue());
                    aascIntlCommodityInfo.setQuantity(nullStrToSpc(String.valueOf(map1.get("quantity"))));
                    aascIntlCommodityInfo.setQuantityUnits((String.valueOf(map1.get("quantity_units"))));
                    aascIntlCommodityInfo.setUnitPrice(nullStrToSpc(String.valueOf(map1.get("unitprice"))));
                    aascIntlCommodityInfo.setCustomsValue(nullStrToSpc(String.valueOf(map1.get("customs_value"))));
                    aascIntlCommodityInfo.setExportLicenseNumber(nullStrToSpc(String.valueOf(map1.get("export_license_number"))));
                    aascIntlCommodityInfo.setExportLicenseExpiryDate(nullStrToSpc(String.valueOf(map1.get("export_license_exp_dt"))));
                    aascIntlCommodityInfo.setProducer(nullStrToSpc(String.valueOf(map1.get("producer"))));
                    aascIntlCommodityInfo.setTariffCode(nullStrToSpc(String.valueOf(map1.get("tariff_code"))));
                    aascIntlCommodityInfo.setPreferenceCriteria(nullStrToSpc(String.valueOf(map1.get("preference_criteria"))));
                    aascIntlCommodityInfo.setRvcCalculationMethod(nullStrToSpc(String.valueOf(map1.get("rvc_cal_method"))));
                    aascIntlCommodityInfo.setSedTotalValue(((BigDecimal)map1.get("sed_total_value")).doubleValue());
                    aascIntlCommodityInfo.setScheduleBUOM(String.valueOf(map1.get("scheduleb_uom")));
                    aascIntlCommodityInfo.setNetCostPeriodBeginDate(nullStrToSpc(String.valueOf(map1.get("net_cost_period_begin_date"))));
                    aascIntlCommodityInfo.setNetCostPeriodEndDate(nullStrToSpc(String.valueOf(map1.get("net_cost_period_end_date"))));
                    aascIntlCommodityInfo.setScheduleBNumber(nullStrToSpc(String.valueOf(map1.get("scheduleb_number"))));
                    aascIntlCommodityInfo.setExportType(nullStrToSpc(String.valueOf(map1.get("export_type"))));
                    aascIntlCommodityInfo.setScheduleBQty(nullStrToSpc(String.valueOf(map1.get("scheduleb_qty"))));
                    aascAddressInfo.setCompanyName(nullStrToSpc(String.valueOf(map1.get("company_name"))));
                    aascAddressInfo.setAddressLine1(nullStrToSpc(String.valueOf(map1.get("address1"))));
                    aascAddressInfo.setCity(nullStrToSpc(String.valueOf(map1.get("city"))));
                    aascAddressInfo.setCountryCode(nullStrToSpc(String.valueOf(map1.get("country_code"))));
                    aascAddressInfo.setPostalCode(nullStrToSpc(String.valueOf(map1.get("postal_code"))));
                    aascAddressInfo.setStateProvinceCode(nullStrToSpc(String.valueOf(map1.get("state_provience_code"))));
                    aascAddressInfo.setTaxId(nullStrToSpc(String.valueOf(map1.get("nafta_tax_id"))));
                    aascIntlCommodityInfo.setPackageWeightUom(nullStrToSpc(String.valueOf(map1.get("pkg_weight_uom"))));
                    aascIntlCommodityInfo.setCarrierCode(Integer.parseInt(String.valueOf(map1.get("carrier_code"))));                        
                    
                    aascIntlCommodityInfo.setAascAddressInfo(aascAddressInfo);
                            
                    intlLinkedList.add(aascIntlCommodityInfo);
                }
                
                try {
                    aascIntlInfo.setIntlCommodityInfo(intlLinkedList);
                } catch (Exception e) {
                    logger.severe("Got exception in setting IntlCommodityInfo bean to aascIntlInfo bean" + e.getMessage());
                }
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("exit from  the getIntlCommodityLineDetails() method");
        
        return aascIntlInfo;
    }

    /** This method is used to delete added commodity of that order number and returns opstatus.
     * @param commodityNumber
     * @param orderNumber
     * @param clientId
     * @param locationId
     * @return int
     */
     public int deleteIntlCommodityDetails(int commodityNumber, String orderNumber, int clientId, int locationId) {
        
        logger.info("Entered deleteIntlCommodityDetails() method");
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("delete_commodity_details")
                                                           .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_commodity_no", commodityNumber)
                                                                        .addValue("ip_order_number", orderNumber)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out = "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
            
            errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.delete_commodity_details procedure opStatus value =" + opStatus);
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("Exit from  the deleteIntlCommodityDetails() method");
        return opStatus;
    }

    /** This method is used to get international commodity details based on selected commodity name and set it to AascIntlCommodityInfo bean.
     * @param commodityNumber
     * @param orderNumber
     * @param clientId
     * @param locationId
     * @return AascIntlCommodityInfo
     */
     public AascIntlCommodityInfo getIntlCommodityDetails(int commodityNumber, String orderNumber, int clientId, int locationId) {
    
        logger.info("Entered getIntlCommodityDetails() method");
        AascIntlCommodityInfo aascIntlCommodityInfo = null;
        AascAddressInfo aascAddressInfo = null;
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_intl_commodity_line")
                                                           .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("aasc_intl_commodity_details", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_commodity_no", commodityNumber)
                                                                        .addValue("ip_order_number", orderNumber)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out = "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
            
            errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
                    
            Iterator intlCommodityDetailsIt = ((ArrayList)out.get("OP_COMMODITY_DETAILS")).iterator();
            
            while(intlCommodityDetailsIt.hasNext()){
                
                map1 = (HashMap<String, ?>)intlCommodityDetailsIt.next();
                aascIntlCommodityInfo = new AascIntlCommodityInfo();
                aascAddressInfo = new AascAddressInfo();
                
                aascIntlCommodityInfo.setCommodityNumber(((BigDecimal)map1.get("commodity_no")).intValue());
                aascIntlCommodityInfo.setNumberOfPieces(((BigDecimal)map1.get("number_of_pieces")).intValue());
                aascIntlCommodityInfo.setDescription(nullStrToSpc(String.valueOf(map1.get("description"))));
                aascIntlCommodityInfo.setCountryOfManufacture(nullStrToSpc(String.valueOf(map1.get("country_of_manufacture"))));
                aascIntlCommodityInfo.setHarmonizedCode(nullStrToSpc(String.valueOf(map1.get("harmonized_code"))));
                aascIntlCommodityInfo.setWeight(((BigDecimal)map1.get("weight")).doubleValue());
                aascIntlCommodityInfo.setQuantity(nullStrToSpc(String.valueOf(map1.get("quantity"))));
                aascIntlCommodityInfo.setQuantityUnits((String.valueOf(map1.get("quantity_units"))));
                aascIntlCommodityInfo.setUnitPrice(nullStrToSpc(String.valueOf(map1.get("unitprice"))));
                aascIntlCommodityInfo.setCustomsValue(nullStrToSpc(String.valueOf(map1.get("customs_value"))));
                aascIntlCommodityInfo.setExportLicenseNumber(nullStrToSpc(String.valueOf(map1.get("export_license_number"))));
                aascIntlCommodityInfo.setExportLicenseExpiryDate(nullStrToSpc(String.valueOf(map1.get("export_license_exp_dt"))));
                aascIntlCommodityInfo.setProducer(nullStrToSpc(String.valueOf(map1.get("producer"))));
                aascIntlCommodityInfo.setTariffCode(nullStrToSpc(String.valueOf(map1.get("tariff_code"))));
                aascIntlCommodityInfo.setPreferenceCriteria(nullStrToSpc(String.valueOf(map1.get("preference_criteria"))));
                aascIntlCommodityInfo.setRvcCalculationMethod(nullStrToSpc(String.valueOf(map1.get("rvc_cal_method"))));
                aascIntlCommodityInfo.setSedTotalValue(((BigDecimal)map1.get("sed_total_value")).doubleValue());
                aascIntlCommodityInfo.setScheduleBUOM(String.valueOf(map1.get("scheduleb_uom")));
                aascIntlCommodityInfo.setNetCostPeriodBeginDate(nullStrToSpc(String.valueOf(map1.get("net_cost_period_begin_date"))));
                aascIntlCommodityInfo.setNetCostPeriodEndDate(nullStrToSpc(String.valueOf(map1.get("net_cost_period_end_date"))));
                aascIntlCommodityInfo.setScheduleBNumber(nullStrToSpc(String.valueOf(map1.get("scheduleb_number"))));
                aascIntlCommodityInfo.setExportType(nullStrToSpc(String.valueOf(map1.get("export_type"))));
                aascIntlCommodityInfo.setScheduleBQty(nullStrToSpc(String.valueOf(map1.get("scheduleb_qty"))));
                aascIntlCommodityInfo.setPackageWeightUom(nullStrToSpc(String.valueOf(map1.get("pkg_weight_uom"))));
                aascIntlCommodityInfo.setCarrierCode(Integer.parseInt(String.valueOf(map1.get("carrier_code"))));
                
                aascAddressInfo.setCompanyName(nullStrToSpc(String.valueOf(map1.get("company_name"))));
                aascAddressInfo.setAddressLine1(nullStrToSpc(String.valueOf(map1.get("address1"))));
                aascAddressInfo.setCity(nullStrToSpc(String.valueOf(map1.get("city"))));
                aascAddressInfo.setCountryCode(nullStrToSpc(String.valueOf(map1.get("country_code"))));
                aascAddressInfo.setPostalCode(nullStrToSpc(String.valueOf(map1.get("postal_code"))));
                aascAddressInfo.setStateProvinceCode(nullStrToSpc(String.valueOf(map1.get("state_provience_code"))));
                aascAddressInfo.setTaxId(nullStrToSpc(String.valueOf(map1.get("nafta_tax_id"))));
                
                aascIntlCommodityInfo.setAascAddressInfo(aascAddressInfo);
                
                
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("exit from  the getIntlCommodityDetails() method");
        
        return aascIntlCommodityInfo;
    }

    /** This method is used to save international commodity details into respective table and returns opstatus.
     * @param aascIntlCommodityInfo
     * @param carrierCode
     * @param clientId
     * @param locationId
     * @return int
     */
     public int addIntlCommodityItemDetail(AascIntlCommodityInfo aascIntlCommodityInfo, int carrierCode, int clientId, int locationId) {
    
        logger.info("Entered addIntlCommodityItemDetail method");
        
        this.aascIntlCommodityInfo = aascIntlCommodityInfo; // assingning the value of aascIntlCommodityInfo 
        
        countryOfManufacture = aascIntlCommodityInfo.getCountryOfManufacture();
        customsValue = aascIntlCommodityInfo.getCustomsValue();
        numberOfPieces = aascIntlCommodityInfo.getNumberOfPieces();
        quantity = aascIntlCommodityInfo.getQuantity();
        quantityUnits = aascIntlCommodityInfo.getQuantityUnits();
        unitPrice = aascIntlCommodityInfo.getUnitPrice();
        description = aascIntlCommodityInfo.getDescription();
        weight = aascIntlCommodityInfo.getWeight();
        harmonizedCode = aascIntlCommodityInfo.getHarmonizedCode();
        exportLicenseNumber = aascIntlCommodityInfo.getExportLicenseNumber();
        
        exportLicenseExpiryDate = aascIntlCommodityInfo.getExportLicenseExpiryDate();
        java.sql.Date sqlExportLicenseExpiryDate = null;
        if(!"".equalsIgnoreCase(exportLicenseExpiryDate) && exportLicenseExpiryDate != null){
            try {
                date = sdf1.parse(exportLicenseExpiryDate);
            } catch (ParseException e) {
                date = new java.util.Date() ;
                logger.severe("Exception at exportLicenseExpiryDate e : "+e.getMessage());
            }
            sqlExportLicenseExpiryDate = new java.sql.Date(date.getTime());  
        }
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("save_intl_comm_item_detail")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_country_of_manufacture", countryOfManufacture)
                                                                        .addValue("ip_customs_value", customsValue)
                                                                        .addValue("ip_number_of_pieces", numberOfPieces)
                                                                        .addValue("ip_quantity", quantity)
                                                                        .addValue("ip_quantity_units", quantityUnits)
                                                                        .addValue("ip_unitprice", unitPrice)
                                                                        .addValue("ip_description", description)
                                                                        .addValue("ip_weight", weight)
                                                                        .addValue("ip_harmonized_code", harmonizedCode)
                                                                        .addValue("ip_export_license_number", exportLicenseNumber)
                                                                        .addValue("ip_export_license_exp_dt", sqlExportLicenseExpiryDate, Types.DATE)
                                                                        .addValue("ip_carrier_code", carrierCode)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out = "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.save_intl_comm_item_detail opStatus:" +opStatus + "\t errorStatus:" + errorStatus);
            if (opStatus == 120) {
                logger.info("Intl Commodity details  are saved successfully");
            } else {
                logger.info("Intl Commodity details not saved");
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("Exit from addIntlCommodityItemDetail() method");
        
        return opStatus;
    }

    /** This method is used to save international commodity details into respective table and returns opstatus.
     * @param userId
     * @param aascIntlCommodityInfo
     * @param carrierCode
     * @param clientId
     * @param locationId
     * @return int
     */
     public int saveIntlCommodityDetails(int userId, AascIntlCommodityInfo aascIntlCommodityInfo, int carrierCode, int clientId, int locationId) {
        
        logger.info("Entered saveIntlCommodityDetails method");
        AascAddressInfo aascAddressInfo = null;
        this.aascIntlCommodityInfo = aascIntlCommodityInfo; // assingning the value of aascIntlCommodityInfo 
        this.userId = userId; // assigning the value of userId argument to the instance variable
        java.util.Date date = null;
        java.sql.Date sqlExportLicenseExpiryDate = null;
        java.sql.Date sqlNetCostPeriodBeginDate = null;
        java.sql.Date sqlNetCostPeriodEndDate = null;
        try{
        

            aascAddressInfo = aascIntlCommodityInfo.getAascAddressInfo();
            if(aascAddressInfo == null){
                aascAddressInfo = new AascAddressInfo(); 
            }
            commodityNumber = aascIntlCommodityInfo.getCommodityNumber();
            orderNumber = aascIntlCommodityInfo.getOrderNumber();
            shipmentId = aascIntlCommodityInfo.getShipmentId();
            voidFlag = aascIntlCommodityInfo.getVoidFlag();
            numberOfPieces = aascIntlCommodityInfo.getNumberOfPieces();
            description = aascIntlCommodityInfo.getDescription();
            countryOfManufacture = aascIntlCommodityInfo.getCountryOfManufacture();
            harmonizedCode = aascIntlCommodityInfo.getHarmonizedCode();
            weight = aascIntlCommodityInfo.getWeight();
            quantity = aascIntlCommodityInfo.getQuantity();
            logger.info("quantity  "+quantity);
            if ("".equalsIgnoreCase(quantity) || quantity == null){
                quantity = "0";
            }
            quantityUnits = aascIntlCommodityInfo.getQuantityUnits();
            unitPrice = aascIntlCommodityInfo.getUnitPrice();
            customsValue = aascIntlCommodityInfo.getCustomsValue();
            exportLicenseNumber = aascIntlCommodityInfo.getExportLicenseNumber();
            
            exportLicenseExpiryDate = aascIntlCommodityInfo.getExportLicenseExpiryDate();
            if(!"".equalsIgnoreCase(exportLicenseExpiryDate) && exportLicenseExpiryDate != null){
                try {
                    date = sdf1.parse(exportLicenseExpiryDate);
                } catch (ParseException e) {
                    date = new java.util.Date() ;
                    logger.severe("Exception at exportLicenseExpiryDate e : "+e.getMessage());
                }
                sqlExportLicenseExpiryDate = new java.sql.Date(date.getTime());  
            }
            
            producer = aascIntlCommodityInfo.getProducer();
            tariffCode = aascIntlCommodityInfo.getTariffCode();
            preferenceCriteria = aascIntlCommodityInfo.getPreferenceCriteria();
            rvcCalculationMethod = aascIntlCommodityInfo.getRvcCalculationMethod();
            sedTotalValue = aascIntlCommodityInfo.getSedTotalValue();
            scheduleBUOM = aascIntlCommodityInfo.getScheduleBUOM();
            
            netCostPeriodBeginDate = aascIntlCommodityInfo.getNetCostPeriodBeginDate();
            if(!"".equalsIgnoreCase(netCostPeriodBeginDate) && netCostPeriodBeginDate != null){
                try {
                    date = sdf1.parse(netCostPeriodBeginDate);
                } catch (ParseException e) {
                    date = new java.util.Date() ;
                    logger.severe("Exception at netCostPeriodBeginDate e : "+e.getMessage());
                }
                sqlNetCostPeriodBeginDate = new java.sql.Date(date.getTime());  
            }
            
            netCostPeriodEndDate = aascIntlCommodityInfo.getNetCostPeriodEndDate();
            if(!"".equalsIgnoreCase(netCostPeriodEndDate) && netCostPeriodEndDate != null){
                try {
                    date = sdf1.parse(netCostPeriodEndDate);
                } catch (ParseException e) {
                    date = new java.util.Date() ;
                    logger.severe("Exception at netCostPeriodEndDate e : "+e.getMessage());
                    
                }
                sqlNetCostPeriodEndDate = new java.sql.Date(date.getTime());  
            }
            scheduleBNumber = aascIntlCommodityInfo.getScheduleBNumber();
            exportType = aascIntlCommodityInfo.getExportType();
            scheduleBQty = aascIntlCommodityInfo.getScheduleBQty();
            // addressId = aascAddressInfo.getAddressId(); 
            companyName = aascAddressInfo.getCompanyName();
            addressLine1 = aascAddressInfo.getAddressLine1();
            city = aascAddressInfo.getCity();
            stateProvinceCode = aascAddressInfo.getStateProvinceCode();
            postalCode = aascAddressInfo.getPostalCode();
            countryCode = aascAddressInfo.getCountryCode();
            taxId = aascAddressInfo.getTaxId();
            packageWeightUom = aascIntlCommodityInfo.getPackageWeightUom();
            
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("save_intl_commodity_details")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_commodity_no", commodityNumber)
                                                                        .addValue("ip_order_number", orderNumber)
                                                                        .addValue("ip_shipment_id", shipmentId)
                                                                        .addValue("ip_void_flag", voidFlag)
                                                                        .addValue("ip_no_of_pkgs_per_commodity", numberOfPieces)
                                                                        .addValue("ip_product_desc", description)
                                                                        .addValue("ip_country_manufacture", countryOfManufacture)
                                                                        .addValue("ip_harmonized_code", harmonizedCode)
                                                                        .addValue("ip_pkg_wt", weight)
                                                                        .addValue("ip_no_of_units", quantity)
                                                                        .addValue("ip_uom", quantityUnits)
                                                                        .addValue("ip_price_per_unit", unitPrice)
                                                                        .addValue("ip_customs_value", customsValue)
                                                                        .addValue("ip_export_license_number", exportLicenseNumber)
                                                                        .addValue("ip_export_license_exp_dt", sqlExportLicenseExpiryDate, Types.DATE)
                                                                        .addValue("ip_user_id", userId)
                                                                        .addValue("ip_producer", producer)
                                                                        .addValue("ip_tariff_code", tariffCode)
                                                                        .addValue("ip_preference_criteria", preferenceCriteria)
                                                                        .addValue("ip_rvc_cal_method", rvcCalculationMethod)
                                                                        .addValue("ip_sed_total_value", sedTotalValue)
                                                                        .addValue("ip_scheduleb_uom", scheduleBUOM)
                                                                        .addValue("ip_netcostperiod_begin_date", sqlNetCostPeriodBeginDate, Types.DATE)
                                                                        .addValue("ip_netcostperiod_end_date", sqlNetCostPeriodEndDate, Types.DATE)
                                                                        .addValue("ip_scheduleb_number", scheduleBNumber)
                                                                        .addValue("ip_export_type", exportType)
                                                                        .addValue("ip_scheduleb_qty", scheduleBQty)
                                                                        .addValue("ip_company_name", companyName)
                                                                        .addValue("ip_address1", addressLine1)
                                                                        .addValue("ip_city", city)
                                                                        .addValue("ip_state_provience_code", stateProvinceCode)
                                                                        .addValue("ip_postal_code", postalCode)
                                                                        .addValue("ip_country_code", countryCode)
                                                                        .addValue("ip_pkg_weight_uom", packageWeightUom)
                                                                        .addValue("ip_carrier_code", carrierCode)
                                                                        .addValue("ip_nafta_tax_id", taxId)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out = "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.save_intl_commodity_details opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
    
            if (opStatus == 120) {
                logger.info("Intl Commodity details  are saved successfully");
            } else {
                logger.info("Intl Commodity details not saved");
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("Exit from saveIntlCommodityDetails() method");
        
        return opStatus;
    }

    /** This method is used to get Commodity descriptions and display them in drop down list in international page and set it to ArrayList.
     * @param clientId
     * @param locationId
     * @return ArrayList
     */
     public ArrayList<String> getIntlCommodityLineItems(int clientId, int locationId) {
        logger.info("Enter in to the getIntlCommodityLineItems() method");
        ArrayList<String> lineItems = new ArrayList<String>();
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_intl_commodity_line_items")
                                                           .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("aasc_get_intl_comm_item_detail", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out = "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
            
            errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.get_intl_commodity_line_items opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
            
            if(opStatus == 0){
                Iterator intlCommodityLineIt = ((ArrayList)out.get("OP_INTL_COMMODITY_LINE_ITEMS")).iterator();
                while(intlCommodityLineIt.hasNext()){
                    
                    map1 = (HashMap<String, ?>)intlCommodityLineIt.next();
                    
                    lineItems.add(String.valueOf(map1.get("description")));
                }
            }
        }catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("Exit from getIntlCommodityLineItems() method");
        
        return lineItems;
    }

    /** This method is used to get Importer Names and display them in drop down list in international page and set it to a HashMap.
     * @param clientId
     * @param locationId
     * @return HashMap
     */
     public HashMap getIntlImporterNames(int clientId, int locationId) {
    
        logger.info("Enter in to the getIntlImporterNames() method");
        HashMap importerNames = new HashMap();
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_intl_importer_names")
                                                           .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("aasc_get_intl_importer_names", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out = "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
            
            errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.get_intl_importer_names opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
            
            if(opStatus == 0){
                Iterator intlCommodityLineIt = ((ArrayList)out.get("OP_INTL_IMPORTER_NAMES")).iterator();
                while(intlCommodityLineIt.hasNext()){
                    
                    map1 = (HashMap<String, ?>)intlCommodityLineIt.next();
                    
                    importerNames.put(map1.get("IMPORTER_NAME"), map1.get("IMPORTER_NAME"));
                }
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("Exit from getIntlImporterNames() method");
        
        return importerNames;
    }

    /**  This method is used to get Broker Names and display them in drop down list in international page and set it to a HashMap.
     * @param clientId
     * @param locationId
     * @return HashMap
     */
     public HashMap getIntlBrokerNames(int clientId, int locationId) {
    
        logger.info("Enter in to the getIntlBrokerNames() method");
        
        HashMap brokerNames = new HashMap();
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_intl_broker_names")
                                                           .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("aasc_get_intl_broker_names", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out = "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
            
            errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.get_intl_broker_names opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
            
            if(opStatus == 0){
                Iterator intlCommodityLineIt = ((ArrayList)out.get("OP_INTL_BROKER_NAMES")).iterator();
                while(intlCommodityLineIt.hasNext()){
                    
                    map1 = (HashMap<String, ?>)intlCommodityLineIt.next();
                    
                    brokerNames.put(map1.get("BROKER_NAME"), map1.get("BROKER_NAME"));
                }
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("Exit from getIntlBrokerNames() method");
        
        return brokerNames;
    }

    /** This method is used to delete all saved international header details.
     * @param orderNumber
     * @param clientId
     * @param locationId
     * @return int
     */
     public int deleteIntlShipments(String orderNumber, int clientId, int locationId) {
    
        logger.info("enter in to the deleteIntlShipments() method");
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("delete_intl_shipment")
                                                           .declareParameters(new SqlOutParameter("OP_STATUS", Types.VARCHAR));
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_order_number", orderNumber)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("out ==== " + out.toString());
            
            opStatus =Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
            logger.info("aasc_intl_shipment_pkg.delete_intl_shipment procedure opStatus value =" + opStatus);
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("exit from  the deleteIntlShipments() method");
        
        return opStatus;
    }

    /** This method is used to save international page header details and set it to AascIntlHeaderInfo bean.
     * @param orderNumber
     * @param clientId
     * @param locationId
     * @return AascIntlHeaderInfo
     */
     public AascIntlHeaderInfo getIntlHeaderDetails(String orderNumber, int clientId, int locationId) {
        logger.info("enter in to the getIntlHeaderDetails() method");
        aascIntlHeaderInfo = null;
        AascAddressInfo sedAddressInfo = null;
        AascAddressInfo soldToAddressInfo = null;
        AascAddressInfo forwardAgentInfo = null;
        AascAddressInfo iConsigneeInfo = null;
        AascIntlInfo aascIntlInfo = null;
        try{ 
            
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_intl_shipments")
                                                           .declareParameters(new SqlOutParameter("aasc_intl_header_details", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
                                                           
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_order_number", orderNumber)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
            
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out  = "+out.toString());
                
                opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
                
                errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
                
            logger.info("aasc_intl_shipment_pkg.get_intl_shipments opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
            
            if(opStatus == 120){
                aascIntlInfo = new AascIntlInfo();
                Iterator intlHeaderDetailsIt = ((ArrayList)out.get("OP_INTL_HEADER_DETAILS")).iterator();
                while(intlHeaderDetailsIt.hasNext()){
                    aascIntlHeaderInfo = new AascIntlHeaderInfo();
                    soldToAddressInfo = new AascAddressInfo();
                    sedAddressInfo = new AascAddressInfo();
                    forwardAgentInfo = new AascAddressInfo();
                    iConsigneeInfo = new AascAddressInfo();
                    map1 = (HashMap<String, ?>)intlHeaderDetailsIt.next();
                    
                    aascIntlHeaderInfo.setIntlPayerType(nullStrToSpc(String.valueOf(map1.get("INTL_PAYERTYPE"))));
                    aascIntlHeaderInfo.setIntlAccountNumber(nullStrToSpc(String.valueOf(map1.get("INTL_ACC_NO"))));
                    aascIntlHeaderInfo.setIntlMaskAccountNumber(nullStrToSpc(String.valueOf(map1.get("INTL_MASK_ACC_NO"))));
                    aascIntlHeaderInfo.setIntlCountryCode(nullStrToSpc(String.valueOf(map1.get("INTL_COUNTRY_CODE"))));
                    aascIntlHeaderInfo.setIntlTermsOfSale(nullStrToSpc(String.valueOf(map1.get("INTL_TERMS_OF_SALE"))));
                    aascIntlHeaderInfo.setIntlTotalCustomsValue(nullStrToSpc(String.valueOf(map1.get("INTL_TOTAL_CUSTOMS_VALUE"))));
                    aascIntlHeaderInfo.setIntlFreightCharge(((BigDecimal)map1.get("INTL_FREIGHT_CHARGE")).doubleValue());
                    aascIntlHeaderInfo.setIntlInsuranceCharge(((BigDecimal)map1.get("INTL_INSURANCE_CHARGE")).doubleValue());
                    aascIntlHeaderInfo.setIntlTaxMiscellaneousCharge(((BigDecimal)map1.get("INTL_TAX_MISCE_CHG")).doubleValue());
                    aascIntlHeaderInfo.setIntlPurpose(nullStrToSpc(String.valueOf(map1.get("INTL_PURPOSE"))));
                    aascIntlHeaderInfo.setIntlSedNumber(nullStrToSpc(String.valueOf(map1.get("INTL_SED_NO"))));
                    aascIntlHeaderInfo.setIntlSedType(nullStrToSpc(String.valueOf(map1.get("INTL_SED_TYPE"))));
                    aascIntlHeaderInfo.setIntlExemptionNumber(nullStrToSpc(String.valueOf(map1.get("INTL_EXEMPTION_NO"))));
                    aascIntlHeaderInfo.setIntlComments(nullStrToSpc(String.valueOf(map1.get("INTL_COMMENTS"))));
                    aascIntlHeaderInfo.setIntlCustomerInvoiceNumber(nullStrToSpc(String.valueOf(map1.get("INTL_CUST_INVOICE_NO"))));
                    aascIntlHeaderInfo.setIntlShipFromTaxId(nullStrToSpc(String.valueOf(map1.get("INTL_SHIP_FROM_TAX_ID"))));
                    aascIntlHeaderInfo.setIntlPurchaseOrderNumber(nullStrToSpc(String.valueOf(map1.get("INTL_PO_NUMBER"))));
                    aascIntlHeaderInfo.setIntlDiscount(((BigDecimal)map1.get("INTL_DISCOUNT")).doubleValue());
                    aascIntlHeaderInfo.setIntlDeclarationStmt(nullStrToSpc(String.valueOf(map1.get("INTL_DECL_STMT"))));
                    aascIntlHeaderInfo.setIntlShipToTaxId(nullStrToSpc(String.valueOf(map1.get("INTL_SHIP_TO_TAX_ID"))));
                    aascIntlHeaderInfo.setIntlExportDate(nullStrToSpc(String.valueOf(map1.get("INTL_EXPORT_DATE"))));
                    aascIntlHeaderInfo.setIntlExportCarrier(nullStrToSpc(String.valueOf(map1.get("INTL_EXPORT_CARRIER"))));
                    aascIntlHeaderInfo.setIntlBlanketPeriodBeginDate(nullStrToSpc(String.valueOf(map1.get("INTL_BLANKET_PERIOD_BEGIN_DATE"))));
                    aascIntlHeaderInfo.setIntlBlanketPeriodEndDate(nullStrToSpc(String.valueOf(map1.get("INTL_BLANKET_PERIOD_END_DATE"))));
                    aascIntlHeaderInfo.setIntlInvoiceDate(nullStrToSpc(String.valueOf(map1.get("INVOICE_DATE"))));
                    aascIntlHeaderInfo.setIntlCurrencyCode(nullStrToSpc(String.valueOf(map1.get("CURRENCY_CODE"))));
                    aascIntlHeaderInfo.setCommercialInv(nullStrToSpc(String.valueOf(map1.get("COMMERCIAL_INV"))));
                    aascIntlHeaderInfo.setUsCertOrigin(nullStrToSpc(String.valueOf(map1.get("US_CERT_ORIGIN"))));
                    aascIntlHeaderInfo.setNaftaCertOrigin(nullStrToSpc(String.valueOf(map1.get("NAFTA_CERT_ORIGIN"))));
                    aascIntlHeaderInfo.setShippersExportDecl(nullStrToSpc(String.valueOf(map1.get("SHIPPERS_EXPORT_DECL"))));
                    aascIntlHeaderInfo.setSoldToTaxId(nullStrToSpc(String.valueOf(map1.get("SOLD_TO_TAX_ID"))));
                    aascIntlHeaderInfo.setIsShipToSameAsSoldTo(nullStrToSpc(String.valueOf(map1.get("IS_SHIPTO_SAME_AS_SOLDTO"))));
                    aascIntlHeaderInfo.setTermsOfTrade(nullStrToSpc(String.valueOf(map1.get("TERMS_OF_TRADE"))));                    
                    soldToAddressInfo.setCompanyName(nullStrToSpc(String.valueOf(map1.get("SOLD_TO_COMPANY_NAME"))));
                    soldToAddressInfo.setAddressLine1(nullStrToSpc(String.valueOf(map1.get("SOLD_TO_ADDRESS1"))));
                    soldToAddressInfo.setAddressLine2(nullStrToSpc(String.valueOf(map1.get("SOLD_TO_ADDRESS2"))));
                    soldToAddressInfo.setCity(nullStrToSpc(String.valueOf(map1.get("SOLD_TO_CITY"))));
                    soldToAddressInfo.setStateProvinceCode(nullStrToSpc(String.valueOf(map1.get("SOLD_TO_STATE_PROVIENCE_CODE"))));
                    soldToAddressInfo.setPostalCode(nullStrToSpc(String.valueOf(map1.get("SOLD_TO_POSTAL_CODE"))));
                    soldToAddressInfo.setCountryCode(nullStrToSpc(String.valueOf(map1.get("SOLD_TO_COUNTRY_CODE"))));
                    
                    aascIntlHeaderInfo.setSedPointOfOrigin(nullStrToSpc(String.valueOf(map1.get("POINT_OF_ORIGIN"))));
                    aascIntlHeaderInfo.setSedModeOfTransport(nullStrToSpc(String.valueOf(map1.get("MODE_OF_TRANSPORT"))));
                    aascIntlHeaderInfo.setSedExportDate(nullStrToSpc(String.valueOf(map1.get("EXPORT_DATE"))));
                    aascIntlHeaderInfo.setSedExportingCarrier(nullStrToSpc(String.valueOf(map1.get("EXPORTING_CARRIER"))));
                    aascIntlHeaderInfo.setSedInBondCode(nullStrToSpc(String.valueOf(map1.get("IN_BOND_CODE"))));
                    aascIntlHeaderInfo.setSedEntryNumber(nullStrToSpc(String.valueOf(map1.get("ENTRY_NUMBER"))));
                    aascIntlHeaderInfo.setSedLoadingPier(nullStrToSpc(String.valueOf(map1.get("LOADING_PIER"))));
                    aascIntlHeaderInfo.setSedPortOfExport(nullStrToSpc(String.valueOf(map1.get("PORT_OF_EXPORT"))));
                    aascIntlHeaderInfo.setSedPortOfUnloading(nullStrToSpc(String.valueOf(map1.get("PORT_OF_UNLOADING"))));
                    aascIntlHeaderInfo.setSedCarrierIdentificationCode(nullStrToSpc(String.valueOf(map1.get("CARRIER_IDENTIFICATION_CODE"))));
                    aascIntlHeaderInfo.setSedContainerized(nullStrToSpc(String.valueOf(map1.get("CONTAINERIZED"))));
                    aascIntlHeaderInfo.setSedHazardiousMaterial(nullStrToSpc(String.valueOf(map1.get("HAZARDIOUS_MATERIAL"))));
                    aascIntlHeaderInfo.setSedRoutedExportTransaction(nullStrToSpc(String.valueOf(map1.get("ROUTED_EXPORT_TRANSACTION"))));
                    aascIntlHeaderInfo.setSedLicenceExceptionCode(nullStrToSpc(String.valueOf(map1.get("LICENCE_EXCEPTION_CODE"))));
                    aascIntlHeaderInfo.setSedECCN(nullStrToSpc(String.valueOf(map1.get("ECCN"))));
                    aascIntlHeaderInfo.setSedLicenceNumber(nullStrToSpc(String.valueOf(map1.get("LICENCE_NUMBER"))));
                    aascIntlHeaderInfo.setSedLicenceDate(nullStrToSpc(String.valueOf(map1.get("LICENCE_DATE"))));
                    
                    sedAddressInfo.setCompanyName(nullStrToSpc(String.valueOf(map1.get("COMPANY_NAME"))));
                    sedAddressInfo.setAddressLine1(nullStrToSpc(String.valueOf(map1.get("ADDRESS1"))));
                    sedAddressInfo.setCity(nullStrToSpc(String.valueOf(map1.get("CITY"))));
                    sedAddressInfo.setStateProvinceCode(nullStrToSpc(String.valueOf(map1.get("STATE_PROVIENCE_CODE"))));
                    sedAddressInfo.setPostalCode(nullStrToSpc(String.valueOf(map1.get("POSTAL_CODE"))));
                    sedAddressInfo.setCountryCode(nullStrToSpc(String.valueOf(map1.get("COUNTRY_CODE"))));
                    
                    aascIntlHeaderInfo.setSedPartiesToTran(nullStrToSpc(String.valueOf(map1.get("PARTIES_TO_TRAN"))));
                    aascIntlHeaderInfo.setShipFromPhone(nullStrToSpc(String.valueOf(map1.get("SHIP_FROM_PHONE_NUMBER"))));
                    aascIntlHeaderInfo.setShipFromAttention(nullStrToSpc(String.valueOf(map1.get("SHIP_FROM_ATTENSION_NAME"))));
                    aascIntlHeaderInfo.setSoldToAttention(nullStrToSpc(String.valueOf(map1.get("SOLD_TO_ATTENSION_NAME"))));
                    aascIntlHeaderInfo.setSoldToPhone(nullStrToSpc(String.valueOf(map1.get("SOLD_TO_PHONE_NUMBER"))));
                    aascIntlHeaderInfo.setInvoiceCurrencyCode(nullStrToSpc(String.valueOf(map1.get("INVOICE_CURRENCY_CODE"))));
                    aascIntlHeaderInfo.setInvoiceValue(Integer.parseInt(String.valueOf(map1.get("INVOICE_MONITORY_VALUE"))));
                    aascIntlHeaderInfo.setCarrierCode(Integer.parseInt(String.valueOf(map1.get("CARRIER_CODE"))));
                    
                    //Added By Narasimha Earla for forward agent and intermediate consignee
                    forwardAgentInfo.setCompanyName(nullStrToSpc(String.valueOf(map1.get("FA_ASA_COMPANY_NAME"))));
                    forwardAgentInfo.setAddressLine1(nullStrToSpc(String.valueOf(map1.get("FA_ASA_ADDRESS1"))));
                    forwardAgentInfo.setCity(nullStrToSpc(String.valueOf(map1.get("FA_ASA_CITY"))));
                    forwardAgentInfo.setStateProvinceCode(nullStrToSpc(String.valueOf(map1.get("FA_ASA_STATE_PROVIENCE_CODE"))));
                    forwardAgentInfo.setPostalCode(nullStrToSpc(String.valueOf(map1.get("FA_ASA_POSTAL_CODE"))));
                    forwardAgentInfo.setCountryCode(nullStrToSpc(String.valueOf(map1.get("FA_ASA_COUNTRY_CODE"))));
                    
                    iConsigneeInfo.setCompanyName(nullStrToSpc(String.valueOf(map1.get("IC_ASA_COMPANY_NAME"))));
                    iConsigneeInfo.setAddressLine1(nullStrToSpc(String.valueOf(map1.get("IC_ASA_ADDRESS1"))));
                    iConsigneeInfo.setCity(nullStrToSpc(String.valueOf(map1.get("IC_ASA_CITY"))));
                    iConsigneeInfo.setStateProvinceCode(nullStrToSpc(String.valueOf(map1.get("IC_ASA_STATE_PROVIENCE_CODE"))));
                    iConsigneeInfo.setPostalCode(nullStrToSpc(String.valueOf(map1.get("IC_ASA_POSTAL_CODE"))));
                    iConsigneeInfo.setCountryCode(nullStrToSpc(String.valueOf(map1.get("IC_ASA_COUNTRY_CODE"))));
                    
                    forwardAgentInfo.setTaxId(nullStrToSpc(String.valueOf(map1.get("FA_TAX_ID"))));
                    
                    aascIntlHeaderInfo.setForwardAgentInfo(forwardAgentInfo);
                    aascIntlHeaderInfo.setIntermediateConsigneeInfo(iConsigneeInfo);
                    aascIntlHeaderInfo.setSedAddressInfo(sedAddressInfo);
                    aascIntlHeaderInfo.setSoldToAddressInfo(soldToAddressInfo);
                    
                    aascIntlHeaderInfo.setPackingListEnclosed(nullStrToSpc(String.valueOf(map1.get("PACKING_LIST_ENCLOSED"))));
                    aascIntlHeaderInfo.setShippersLoadAndCount(Integer.parseInt(String.valueOf(map1.get("SHIPPERS_LOAD_AND_COUNT"))));
                    aascIntlHeaderInfo.setBookingConfirmationNumber(nullStrToSpc(String.valueOf(map1.get("BOOKING_CONFIRMATION_NUMBER"))));
                    aascIntlHeaderInfo.setDeclarationStmt(nullStrToSpc(String.valueOf(map1.get("DECLARATION_STMT "))));
                    aascIntlHeaderInfo.setImporterName(nullStrToSpc(String.valueOf(map1.get("IMPORTER_NAME"))));
                    aascIntlHeaderInfo.setImporterCompName(nullStrToSpc(String.valueOf(map1.get("IMPORTER_COMP_NAME"))));
                    aascIntlHeaderInfo.setImporterPhoneNum(nullStrToSpc(String.valueOf(map1.get("IMPORTER_PHONE_NUM"))));
                    aascIntlHeaderInfo.setImporterAddress1(nullStrToSpc(String.valueOf(map1.get("IMPORTER_ADDRESS1"))));
                    aascIntlHeaderInfo.setImporterAddress2(nullStrToSpc(String.valueOf(map1.get("IMPORTER_ADDRESS2"))));
                    aascIntlHeaderInfo.setImporterCity(nullStrToSpc(String.valueOf(map1.get("IMPORTER_CITY"))));
                    aascIntlHeaderInfo.setImporterState(nullStrToSpc(String.valueOf(map1.get("IMPORTER_STATE"))));
                    aascIntlHeaderInfo.setImporterPostalCode(nullStrToSpc(String.valueOf(map1.get("IMPORTER_POSTAL_CODE"))));
                    aascIntlHeaderInfo.setImporterCountryCode(nullStrToSpc(String.valueOf(map1.get("IMPORTER_COUNTRY_CODE"))));
                    aascIntlHeaderInfo.setImpIntlSedNumber(nullStrToSpc(String.valueOf(map1.get("IMP_INTL_SED_NUMBER"))));
                    aascIntlHeaderInfo.setImpIntlSedType(nullStrToSpc(String.valueOf(map1.get("IMP_INTL_SED_TYPE"))));
                    aascIntlHeaderInfo.setRecIntlSedNumber(nullStrToSpc(String.valueOf(map1.get("REC_INTL_SED_NUMBER"))));
                    aascIntlHeaderInfo.setRecIntlSedType(nullStrToSpc(String.valueOf(map1.get("REC_INTL_SED_TYPE"))));
                    aascIntlHeaderInfo.setGenerateCI(nullStrToSpc(String.valueOf(map1.get("GENERATE_CI"))));
                    
                    aascIntlHeaderInfo.setBrokerName(nullStrToSpc(String.valueOf(map1.get("BROKER_NAME"))));
                    aascIntlHeaderInfo.setBrokerCompName(nullStrToSpc(String.valueOf(map1.get("BROKER_COMP_NAME"))));
                    aascIntlHeaderInfo.setBrokerPhoneNum(nullStrToSpc(String.valueOf(map1.get("BROKER_PHONE_NUM"))));
                    aascIntlHeaderInfo.setBrokerAddress1(nullStrToSpc(String.valueOf(map1.get("BROKER_ADDRESS1"))));
                    aascIntlHeaderInfo.setBrokerAddress2(nullStrToSpc(String.valueOf(map1.get("BROKER_ADDRESS2"))));
                    aascIntlHeaderInfo.setBrokerCity(nullStrToSpc(String.valueOf(map1.get("BROKER_CITY"))));
                    aascIntlHeaderInfo.setBrokerState(nullStrToSpc(String.valueOf(map1.get("BROKER_STATE"))));
                    aascIntlHeaderInfo.setBrokerPostalCode(nullStrToSpc(String.valueOf(map1.get("BROKER_POSTAL_CODE"))));
                    aascIntlHeaderInfo.setBrokerCountryCode(nullStrToSpc(String.valueOf(map1.get("BROKER_COUNTRY_CODE"))));
                    
                    aascIntlHeaderInfo.setStampsLicenseNumber(nullStrToSpc(String.valueOf(map1.get("STAMPS_LICENSE_NUMBER"))));
                    aascIntlHeaderInfo.setStampsCertificateNumber(nullStrToSpc(String.valueOf(map1.get("STAMPS_CERTIFICATE_NUMBER"))));
                    aascIntlHeaderInfo.setOtherDescribe(nullStrToSpc(String.valueOf(map1.get("OTHER_DESCRIBE"))));


                    
                    aascIntlHeaderInfo.setOrderNumber(orderNumber);
                    
                }
                
            }
        }catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        
        logger.info("exit from  the getIntlHeaderDetails() method");
        
        return aascIntlHeaderInfo;
    }

    /** This method is used to get additional comments, declaration statement and international tax id.
     * @param clientId
     * @param locationId
     * @return AascIntlHeaderInfo
     */
     public AascIntlHeaderInfo getCustomIntlHeaderDetails(int clientId, int locationId) {
        
        logger.info("Entered getCustomIntlHeaderDetails method");
        
        aascIntlHeaderInfo = null;
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_custom_intl_header_detail")
                                                           .declareParameters(new SqlOutParameter("aasc_intl_header_details", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
                                                           
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out = "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
            
            errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.get_intl_shipments opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
            
            if(opStatus == 120){
                Iterator custIntlHeaderDetailsIt = ((ArrayList)out.get("OP_CUSTON_INTL_HEADER_DETAIL")).iterator();
                while(custIntlHeaderDetailsIt.hasNext()){
                    aascIntlHeaderInfo = new AascIntlHeaderInfo();
                    map1 = (HashMap<String, ?>)custIntlHeaderDetailsIt.next();
                    if("".equalsIgnoreCase(nullStrToSpc(aascIntlHeaderInfo.getIntlComments())))
                    {
                        aascIntlHeaderInfo.setIntlComments(nullStrToSpc(String.valueOf(map1.get("ADDITIONAL_COMMENTS"))));
                    }
                    
                    if("".equalsIgnoreCase(nullStrToSpc(aascIntlHeaderInfo.getDeclarationStmt())))
                    {
                        aascIntlHeaderInfo.setDeclarationStmt(nullStrToSpc(String.valueOf(map1.get("DECLARATION_STMT"))));
                    }
                    
                       
                    if("".equalsIgnoreCase(nullStrToSpc(aascIntlHeaderInfo.getIntlSedNumber())))
                    {
                        aascIntlHeaderInfo.setIntlSedNumber(nullStrToSpc(String.valueOf(map1.get("intl_tax_id"))));
                    }    
                        
                    
                }
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }        
        logger.info("Exit from getCustomIntlHeaderDetails method");
        
        return aascIntlHeaderInfo;
    }

    /** This method is used to get both international header details and international commodity details and set it to AascIntlInfo bean.
     * @param orderNumber
     * @param clientId
     * @param locationId
     * @return AascIntlInfo
     */
     public AascIntlInfo getIntlDetails(String orderNumber, int clientId, int locationId) {
    
        logger.info("enter in to the getIntlDetails() method");
        
        AascIntlHeaderInfo aascIntlHeaderInfo = null;
        AascIntlCommodityInfo aascIntlCommodityInfo = null;
        AascAddressInfo aascAddressInfo = null;
        AascIntlInfo aascIntlInfo = null;
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_intl_details")
                                                           .declareParameters(new SqlOutParameter("aasc_intl_header_details", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("aasc_intl_commodity_details", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
                                                           
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_order_number", orderNumber)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out = "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
            
            errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.get_intl_details opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
            
            aascIntlInfo = new AascIntlInfo();
            Iterator intlHeaderDetailsIt = ((ArrayList)out.get("OP_INTL_HEADER_DETAILS")).iterator();
            while(intlHeaderDetailsIt.hasNext()){
            
                map1 = (HashMap<String, ?>)intlHeaderDetailsIt.next();
                
                aascIntlHeaderInfo = new AascIntlHeaderInfo();
                soldToAddressInfo = new AascAddressInfo();
                forwardAgentInfo = new AascAddressInfo();
                intermediateConsigneeInfo = new AascAddressInfo();
                sedAddressInfo = new AascAddressInfo();
                
                aascIntlHeaderInfo.setIntlPayerType(nullStrToSpc(String.valueOf(map1.get("intl_payertype"))));
                aascIntlHeaderInfo.setIntlAccountNumber(nullStrToSpc(String.valueOf(map1.get("intl_acc_no"))));
                aascIntlHeaderInfo.setIntlMaskAccountNumber(nullStrToSpc(String.valueOf(map1.get("INTL_MASK_ACC_NO"))));
                aascIntlHeaderInfo.setIntlCountryCode(nullStrToSpc(String.valueOf(map1.get("intl_country_code"))));
                aascIntlHeaderInfo.setIntlTermsOfSale(nullStrToSpc(String.valueOf(map1.get("intl_terms_of_sale"))));
                aascIntlHeaderInfo.setIntlTotalCustomsValue(nullStrToSpc(String.valueOf(map1.get("intl_total_customs_value"))));
                aascIntlHeaderInfo.setIntlFreightCharge(((BigDecimal)map1.get("intl_freight_charge")).doubleValue());
                aascIntlHeaderInfo.setIntlInsuranceCharge(((BigDecimal)map1.get("intl_insurance_charge")).doubleValue());
                aascIntlHeaderInfo.setIntlTaxMiscellaneousCharge(((BigDecimal)map1.get("intl_tax_misce_chg")).doubleValue());
                aascIntlHeaderInfo.setIntlPurpose(nullStrToSpc(String.valueOf(map1.get("intl_purpose"))));
                aascIntlHeaderInfo.setIntlSedNumber(nullStrToSpc(String.valueOf(map1.get("intl_sed_no"))));
                aascIntlHeaderInfo.setIntlSedType(nullStrToSpc(String.valueOf(map1.get("intl_sed_type"))));
                aascIntlHeaderInfo.setIntlExemptionNumber(nullStrToSpc(String.valueOf(map1.get("intl_exemption_no"))));
                aascIntlHeaderInfo.setIntlComments(nullStrToSpc(String.valueOf(map1.get("intl_comments"))));
                aascIntlHeaderInfo.setIntlCustomerInvoiceNumber(nullStrToSpc(String.valueOf(map1.get("intl_cust_invoice_no"))));
                aascIntlHeaderInfo.setIntlShipFromTaxId(nullStrToSpc(String.valueOf(map1.get("intl_ship_from_tax_id"))));
                aascIntlHeaderInfo.setIntlPurchaseOrderNumber(nullStrToSpc(String.valueOf(map1.get("intl_po_number"))));
                aascIntlHeaderInfo.setIntlDiscount(((BigDecimal)map1.get("intl_discount")).doubleValue());
                aascIntlHeaderInfo.setIntlDeclarationStmt(nullStrToSpc(String.valueOf(map1.get("intl_decl_stmt"))));
                aascIntlHeaderInfo.setIntlShipToTaxId(nullStrToSpc(String.valueOf(map1.get("intl_ship_to_tax_id"))));
                aascIntlHeaderInfo.setIntlExportDate(nullStrToSpc(String.valueOf(map1.get("intl_export_date"))));
                aascIntlHeaderInfo.setIntlExportCarrier(nullStrToSpc(String.valueOf(map1.get("intl_export_carrier"))));
                aascIntlHeaderInfo.setIntlBlanketPeriodBeginDate(nullStrToSpc(String.valueOf(map1.get("intl_blanket_period_begin_date"))));
                aascIntlHeaderInfo.setIntlBlanketPeriodEndDate(nullStrToSpc(String.valueOf(map1.get("intl_blanket_period_end_date"))));
                aascIntlHeaderInfo.setIntlInvoiceDate(nullStrToSpc(String.valueOf(map1.get("invoice_date"))));
                aascIntlHeaderInfo.setIntlCurrencyCode(nullStrToSpc(String.valueOf(map1.get("currency_code"))));
                aascIntlHeaderInfo.setCommercialInv(nullStrToSpc(String.valueOf(map1.get("commercial_inv"))));
                aascIntlHeaderInfo.setUsCertOrigin(nullStrToSpc(String.valueOf(map1.get("us_cert_origin"))));
                aascIntlHeaderInfo.setNaftaCertOrigin(nullStrToSpc(String.valueOf(map1.get("nafta_cert_origin"))));
                aascIntlHeaderInfo.setShippersExportDecl(nullStrToSpc(String.valueOf(map1.get("shippers_export_decl"))));
                aascIntlHeaderInfo.setSoldToTaxId(nullStrToSpc(String.valueOf(map1.get("sold_to_tax_id"))));
                aascIntlHeaderInfo.setIsShipToSameAsSoldTo(nullStrToSpc(String.valueOf(map1.get("is_shipto_same_as_soldto"))));
                
                soldToAddressInfo.setCompanyName(nullStrToSpc(String.valueOf(map1.get("company_name"))));
                soldToAddressInfo.setAddressLine1(nullStrToSpc(String.valueOf(map1.get("address1"))));
                soldToAddressInfo.setAddressLine2(nullStrToSpc(String.valueOf(map1.get("address2"))));
                soldToAddressInfo.setCity(nullStrToSpc(String.valueOf(map1.get("city"))));
                soldToAddressInfo.setStateProvinceCode(nullStrToSpc(String.valueOf(map1.get("state_provience_code"))));
                soldToAddressInfo.setPostalCode(nullStrToSpc(String.valueOf(map1.get("postal_code"))));
                soldToAddressInfo.setCountryCode(nullStrToSpc(String.valueOf(map1.get("country_code"))));
                
                aascIntlHeaderInfo.setSedPointOfOrigin(nullStrToSpc(String.valueOf(map1.get("point_of_origin"))));
                aascIntlHeaderInfo.setSedModeOfTransport(nullStrToSpc(String.valueOf(map1.get("mode_of_transport"))));
                aascIntlHeaderInfo.setSedExportDate(nullStrToSpc(String.valueOf(map1.get("export_date"))));
                aascIntlHeaderInfo.setSedExportingCarrier(nullStrToSpc(String.valueOf(map1.get("exporting_carrier"))));
                aascIntlHeaderInfo.setSedInBondCode(nullStrToSpc(String.valueOf(map1.get("in_bond_code"))));
                aascIntlHeaderInfo.setSedEntryNumber(nullStrToSpc(String.valueOf(map1.get("entry_number"))));
                aascIntlHeaderInfo.setSedLoadingPier(nullStrToSpc(String.valueOf(map1.get("loading_pier"))));
                aascIntlHeaderInfo.setSedPortOfExport(nullStrToSpc(String.valueOf(map1.get("port_of_export"))));
                aascIntlHeaderInfo.setSedPortOfUnloading(nullStrToSpc(String.valueOf(map1.get("port_of_unloading"))));
                aascIntlHeaderInfo.setSedCarrierIdentificationCode(nullStrToSpc(String.valueOf(map1.get("carrier_identification_code"))));
                aascIntlHeaderInfo.setSedContainerized(nullStrToSpc(String.valueOf(map1.get("containerized"))));
                aascIntlHeaderInfo.setSedHazardiousMaterial(nullStrToSpc(String.valueOf(map1.get("hazardious_material"))));
                aascIntlHeaderInfo.setSedRoutedExportTransaction(nullStrToSpc(String.valueOf(map1.get("routed_export_transaction"))));
                aascIntlHeaderInfo.setSedLicenceExceptionCode(nullStrToSpc(String.valueOf(map1.get("licence_exception_code"))));
                aascIntlHeaderInfo.setSedECCN(nullStrToSpc(String.valueOf(map1.get("eccn"))));
                aascIntlHeaderInfo.setSedLicenceNumber(nullStrToSpc(String.valueOf(map1.get("licence_number"))));
                aascIntlHeaderInfo.setSedLicenceDate(nullStrToSpc(String.valueOf(map1.get("licence_date"))));
                
                sedAddressInfo.setCompanyName(nullStrToSpc(String.valueOf(map1.get("company_name"))));
                sedAddressInfo.setAddressLine1(nullStrToSpc(String.valueOf(map1.get("address1"))));
                sedAddressInfo.setCity(nullStrToSpc(String.valueOf(map1.get("city"))));
                sedAddressInfo.setStateProvinceCode(nullStrToSpc(String.valueOf(map1.get("state_provience_code"))));
                sedAddressInfo.setPostalCode(nullStrToSpc(String.valueOf(map1.get("postal_code"))));
                sedAddressInfo.setCountryCode(nullStrToSpc(String.valueOf(map1.get("country_code"))));
                
                aascIntlHeaderInfo.setSedPartiesToTran(nullStrToSpc(String.valueOf(map1.get("parties_to_tran"))));
                aascIntlHeaderInfo.setShipFromPhone(nullStrToSpc(String.valueOf(map1.get("ship_from_phone_number"))));
                aascIntlHeaderInfo.setShipFromAttention(nullStrToSpc(String.valueOf(map1.get("ship_from_attension_name"))));
                aascIntlHeaderInfo.setSoldToAttention(nullStrToSpc(String.valueOf(map1.get("sold_to_attension_name"))));
                aascIntlHeaderInfo.setSoldToPhone(nullStrToSpc(String.valueOf(map1.get("sold_to_phone_number"))));
                aascIntlHeaderInfo.setInvoiceCurrencyCode(nullStrToSpc(String.valueOf(map1.get("invoice_currency_code"))));
                aascIntlHeaderInfo.setInvoiceValue(Integer.parseInt(String.valueOf(map1.get("invoice_monitory_value"))));
                aascIntlHeaderInfo.setCarrierCode(Integer.parseInt(String.valueOf(map1.get("carrier_code"))));
                
                //Added By Narasimha Earla for forward agent and intermediate consignee
                forwardAgentInfo.setCompanyName(nullStrToSpc(String.valueOf(map1.get("company_name"))));
                forwardAgentInfo.setAddressLine1(nullStrToSpc(String.valueOf(map1.get("address1"))));
                forwardAgentInfo.setCity(nullStrToSpc(String.valueOf(map1.get("city"))));
                forwardAgentInfo.setStateProvinceCode(nullStrToSpc(String.valueOf(map1.get("state_provience_code"))));
                forwardAgentInfo.setPostalCode(nullStrToSpc(String.valueOf(map1.get("postal_code"))));
                forwardAgentInfo.setCountryCode(nullStrToSpc(String.valueOf(map1.get("country_code"))));
                
                intermediateConsigneeInfo.setCompanyName(nullStrToSpc(String.valueOf(map1.get("company_name"))));
                intermediateConsigneeInfo.setAddressLine1(nullStrToSpc(String.valueOf(map1.get("address1"))));
                intermediateConsigneeInfo.setCity(nullStrToSpc(String.valueOf(map1.get("city"))));
                intermediateConsigneeInfo.setStateProvinceCode(nullStrToSpc(String.valueOf(map1.get("state_provience_code"))));
                intermediateConsigneeInfo.setPostalCode(nullStrToSpc(String.valueOf(map1.get("postal_code"))));
                intermediateConsigneeInfo.setCountryCode(nullStrToSpc(String.valueOf(map1.get("country_code"))));
                
                forwardAgentInfo.setTaxId(nullStrToSpc(String.valueOf(map1.get("fa_tax_id"))));
                
                aascIntlHeaderInfo.setForwardAgentInfo(forwardAgentInfo);
                aascIntlHeaderInfo.setIntermediateConsigneeInfo(intermediateConsigneeInfo);
                aascIntlHeaderInfo.setSedAddressInfo(sedAddressInfo);
                aascIntlHeaderInfo.setSoldToAddressInfo(soldToAddressInfo);
                
                aascIntlHeaderInfo.setPackingListEnclosed(nullStrToSpc(String.valueOf(map1.get("PACKING_LIST_ENCLOSED"))));
                aascIntlHeaderInfo.setShippersLoadAndCount(Integer.parseInt(String.valueOf(map1.get("shippers_Load_And_Count"))));
                aascIntlHeaderInfo.setBookingConfirmationNumber(nullStrToSpc(String.valueOf(map1.get("Booking_Confirmation_Number"))));
                aascIntlHeaderInfo.setDeclarationStmt(nullStrToSpc(String.valueOf(map1.get("Declaration_Stmt "))));
                aascIntlHeaderInfo.setImporterName(nullStrToSpc(String.valueOf(map1.get("Importer_Name"))));
                aascIntlHeaderInfo.setImporterCompName(nullStrToSpc(String.valueOf(map1.get("Importer_Comp_Name"))));
                aascIntlHeaderInfo.setImporterPhoneNum(nullStrToSpc(String.valueOf(map1.get("Importer_Phone_Num"))));
                aascIntlHeaderInfo.setImporterAddress1(nullStrToSpc(String.valueOf(map1.get("Importer_Address1"))));
                aascIntlHeaderInfo.setImporterAddress2(nullStrToSpc(String.valueOf(map1.get("Importer_Address2"))));
                aascIntlHeaderInfo.setImporterCity(nullStrToSpc(String.valueOf(map1.get("Importer_City"))));
                aascIntlHeaderInfo.setImporterState(nullStrToSpc(String.valueOf(map1.get("Importer_State"))));
                aascIntlHeaderInfo.setImporterPostalCode(nullStrToSpc(String.valueOf(map1.get("Importer_Postal_Code"))));
                aascIntlHeaderInfo.setImporterCountryCode(nullStrToSpc(String.valueOf(map1.get("importer_country_code"))));
                aascIntlHeaderInfo.setImpIntlSedNumber(nullStrToSpc(String.valueOf(map1.get("imp_intl_sed_number"))));
                aascIntlHeaderInfo.setImpIntlSedType(nullStrToSpc(String.valueOf(map1.get("imp_intl_sed_type"))));
                aascIntlHeaderInfo.setRecIntlSedNumber(nullStrToSpc(String.valueOf(map1.get("rec_intl_sed_number"))));
                aascIntlHeaderInfo.setRecIntlSedType(nullStrToSpc(String.valueOf(map1.get("rec_intl_sed_type"))));
                aascIntlHeaderInfo.setGenerateCI(nullStrToSpc(String.valueOf(map1.get("generate_ci"))));
                aascIntlHeaderInfo.setBrokerName(nullStrToSpc(String.valueOf(map1.get("Broker_Name"))));
                aascIntlHeaderInfo.setBrokerCompName(nullStrToSpc(String.valueOf(map1.get("Broker_Comp_Name"))));
                aascIntlHeaderInfo.setBrokerPhoneNum(nullStrToSpc(String.valueOf(map1.get("Broker_Phone_Num"))));
                aascIntlHeaderInfo.setBrokerAddress1(nullStrToSpc(String.valueOf(map1.get("Broker_Address1"))));
                aascIntlHeaderInfo.setBrokerAddress2(nullStrToSpc(String.valueOf(map1.get("Broker_Address2"))));
                aascIntlHeaderInfo.setBrokerCity(nullStrToSpc(String.valueOf(map1.get("Broker_City"))));
                aascIntlHeaderInfo.setBrokerState(nullStrToSpc(String.valueOf(map1.get("Broker_State"))));
                aascIntlHeaderInfo.setBrokerPostalCode(nullStrToSpc(String.valueOf(map1.get("Broker_Postal_Code"))));
                aascIntlHeaderInfo.setBrokerCountryCode(nullStrToSpc(String.valueOf(map1.get("Broker_Country_Code"))));
                
                aascIntlHeaderInfo.setStampsLicenseNumber(nullStrToSpc(String.valueOf(map1.get("STAMPS_LICENSE_NUMBER"))));
                aascIntlHeaderInfo.setStampsCertificateNumber(nullStrToSpc(String.valueOf(map1.get("STAMPS_CERTIFICATE_NUMBER"))));
                aascIntlHeaderInfo.setOtherDescribe(nullStrToSpc(String.valueOf(map1.get("OTHER_DESCRIBE"))));
                aascIntlHeaderInfo.setTermsOfTrade(nullStrToSpc(String.valueOf(map1.get("TERMS_OF_TRADE")))); //Shiva added for the issue 3925
                
                aascIntlHeaderInfo.setOrderNumber(orderNumber);
            }
            try {
                aascIntlInfo.setIntlHeaderInfo(aascIntlHeaderInfo);
    
            } catch (Exception e) {
                logger.info("Got Exception in setting aascIntlHeaderInfo to aascIntlInfo bean" + e.getMessage());
            }
            
            LinkedList intlLinkedList = new LinkedList();
            Iterator intlCommodityDetailsIt = ((ArrayList)out.get("OP_INTL_COMMODITY_DETAILS")).iterator();
            while(intlCommodityDetailsIt.hasNext()){
            
                map1 = (HashMap<String, ?>)intlCommodityDetailsIt.next();
                
                aascIntlCommodityInfo = new AascIntlCommodityInfo();
                aascAddressInfo = new AascAddressInfo();
                
                aascIntlCommodityInfo.setCommodityNumber(((BigDecimal)map1.get("commodity_no")).intValue());
                aascIntlCommodityInfo.setNumberOfPieces(((BigDecimal)map1.get("number_of_pieces")).intValue());
                aascIntlCommodityInfo.setDescription(nullStrToSpc(String.valueOf(map1.get("description"))));
                aascIntlCommodityInfo.setCountryOfManufacture(nullStrToSpc(String.valueOf(map1.get("country_of_manufacture"))));
                aascIntlCommodityInfo.setHarmonizedCode(nullStrToSpc(String.valueOf(map1.get("harmonized_code"))));
                aascIntlCommodityInfo.setWeight(((BigDecimal)map1.get("weight")).doubleValue());
                aascIntlCommodityInfo.setQuantity(nullStrToSpc(String.valueOf(map1.get("quantity"))));
                aascIntlCommodityInfo.setQuantityUnits((String.valueOf(map1.get("quantity_units"))));
                aascIntlCommodityInfo.setUnitPrice(nullStrToSpc(String.valueOf(map1.get("unitprice"))));
                aascIntlCommodityInfo.setCustomsValue(nullStrToSpc(String.valueOf(map1.get("customs_value"))));
                aascIntlCommodityInfo.setExportLicenseNumber(nullStrToSpc(String.valueOf(map1.get("export_license_number"))));
                aascIntlCommodityInfo.setExportLicenseExpiryDate(nullStrToSpc(String.valueOf(map1.get("export_license_exp_dt"))));
                aascIntlCommodityInfo.setProducer(nullStrToSpc(String.valueOf(map1.get("producer"))));
                aascIntlCommodityInfo.setTariffCode(nullStrToSpc(String.valueOf(map1.get("tariff_code"))));
                aascIntlCommodityInfo.setPreferenceCriteria(nullStrToSpc(String.valueOf(map1.get("preference_criteria"))));
                aascIntlCommodityInfo.setRvcCalculationMethod(nullStrToSpc(String.valueOf(map1.get("rvc_cal_method"))));
                aascIntlCommodityInfo.setSedTotalValue(((BigDecimal)map1.get("sed_total_value")).doubleValue());
                aascIntlCommodityInfo.setScheduleBUOM(String.valueOf(map1.get("scheduleb_uom")));
                aascIntlCommodityInfo.setNetCostPeriodBeginDate(nullStrToSpc(String.valueOf(map1.get("net_cost_period_begin_date"))));
                aascIntlCommodityInfo.setNetCostPeriodEndDate(nullStrToSpc(String.valueOf(map1.get("net_cost_period_end_date"))));
                aascIntlCommodityInfo.setScheduleBNumber(nullStrToSpc(String.valueOf(map1.get("scheduleb_number"))));
                aascIntlCommodityInfo.setExportType(nullStrToSpc(String.valueOf(map1.get("export_type"))));
                aascIntlCommodityInfo.setScheduleBQty(nullStrToSpc(String.valueOf(map1.get("scheduleb_qty"))));
                aascIntlCommodityInfo.setPackageWeightUom(nullStrToSpc(String.valueOf(map1.get("pkg_weight_uom"))));
                aascIntlCommodityInfo.setCarrierCode(Integer.parseInt(String.valueOf(map1.get("carrier_code"))));
                
                aascAddressInfo.setCompanyName(nullStrToSpc(String.valueOf(map1.get("company_name"))));
                aascAddressInfo.setAddressLine1(nullStrToSpc(String.valueOf(map1.get("address1"))));
                aascAddressInfo.setCity(nullStrToSpc(String.valueOf(map1.get("city"))));
                aascAddressInfo.setCountryCode(nullStrToSpc(String.valueOf(map1.get("country_code"))));
                aascAddressInfo.setPostalCode(nullStrToSpc(String.valueOf(map1.get("postal_code"))));
                aascAddressInfo.setStateProvinceCode(nullStrToSpc(String.valueOf(map1.get("state_provience_code"))));
                aascAddressInfo.setTaxId(nullStrToSpc(String.valueOf(map1.get("nafta_tax_id"))));
                
                aascIntlCommodityInfo.setAascAddressInfo(aascAddressInfo);
                
                intlLinkedList.add(aascIntlCommodityInfo);
            }
            
            try {
                aascIntlInfo.setIntlCommodityInfo(intlLinkedList);
                logger.info("### Successfully set commodity values to bean ###");
            } catch (Exception e) {
                logger.info("Got Exception in setting intlLinkedList to aascIntlInfo bean" + e.getMessage());
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("exit from  the getIntlDetails() method");
        return aascIntlInfo;
    }

    /** This method is used to get commodity details based on ajax call by selecting a commodity from drop down list.
     * @param description
     * @param clientId
     * @param locationId
     * @return String
     */
     public String getIntlCommodityItemDetail(String description, int clientId, int locationId) {
    
        logger.info("Enter in to the getIntlCommodityItemDetail() method");
        String itemDetail = "";
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_intl_commodity_item_detail")
                                                           .declareParameters(new SqlOutParameter("aasc_get_intl_comm_item_detail", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));

            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_description", description)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out = "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            
            errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_intl_shipment_pkg.get_intl_commodity_item_detail opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
            
            if(opStatus == 0){
                Iterator intlCommodityItemDetailsIt = ((ArrayList)out.get("OP_INTL_COMMODITY_ITEM_DETAIL")).iterator();
                while(intlCommodityItemDetailsIt.hasNext()){
                
                    map1 = (HashMap<String, ?>)intlCommodityItemDetailsIt.next();
                    itemDetail = itemDetail+"*"+nullStrToSpc(String.valueOf(map1.get("country_of_manufacture")))+
                                            "*"+nullStrToSpc(String.valueOf(map1.get("customs_value")))+
                                            "*"+((BigDecimal)map1.get("number_of_pieces")).intValue()+
                                            "*"+((BigDecimal)map1.get("quantity")).intValue()+
                                            "*"+nullStrToSpc(String.valueOf(map1.get("quantity_units")))+
                                            "*"+nullStrToSpc(String.valueOf(map1.get("unitprice")))+
                                            "*"+nullStrToSpc(String.valueOf(map1.get("description")))+
                                            "*"+nullStrToSpc(String.valueOf(map1.get("weight")))+
                                            "*"+nullStrToSpc(String.valueOf(map1.get("harmonized_code")))+
                                            "*"+nullStrToSpc(String.valueOf(map1.get("EXPORT_LICENSE_NUMBER")))+
                                            "*"+nullStrToSpc(String.valueOf(map1.get("export_license_exp_dt")));
                    
                }
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("exit from  the getIntlCommodityItemDetail() method");
        
        return itemDetail;
    }

    /** This method is used to get importer details based on ajax call by selecting a importer name from drop down list.
     * @param importerName
     * @param clientId
     * @param locationId
     * @return String
     */
     public String getIntlImporterDetail(String importerName, int clientId, int locationId) {
        logger.info("Enter in to the getIntlImporterDetail() method");
        String importerDetail = "";
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_intl_importer_detail")
                                                           .declareParameters(new SqlOutParameter("aasc_get_intl_importer_detail", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_importer_name", importerName)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out = "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            
            errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_erp_intl_shipment_pkg.get_intl_importer_detail opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
            
            if(opStatus == 0){
                Iterator intlImporterDetailsIt = ((ArrayList)out.get("OP_INTL_IMPORTER_DETAIL")).iterator();
                while(intlImporterDetailsIt.hasNext()){
                
                    map1 = (HashMap<String, ?>)intlImporterDetailsIt.next();
                    
                    importerDetail = importerDetail+"*"+nullStrToSpc(String.valueOf(map1.get("imp_intl_sed_number")))+
                                                    "*"+nullStrToSpc(String.valueOf(map1.get("imp_intl_sed_type")))+
                                                    "*"+nullStrToSpc(String.valueOf(map1.get("importer_address1")))+
                                                    "*"+nullStrToSpc(String.valueOf(map1.get("importer_address2")))+
                                                    "*"+nullStrToSpc(String.valueOf(map1.get("importer_city")))+
                                                    "*"+nullStrToSpc(String.valueOf(map1.get("importer_comp_name")))+
                                                    "*"+nullStrToSpc(String.valueOf(map1.get("importer_country_code")))+
                                                    "*"+nullStrToSpc(String.valueOf(map1.get("importer_phone_num")))+
                                                    "*"+nullStrToSpc(String.valueOf(map1.get("importer_postal_code")))+
                                                    "*"+nullStrToSpc(String.valueOf(map1.get("IMPORTER_STATE")))+
                                                    "*"+nullStrToSpc(String.valueOf(map1.get("importer_name")));
                }
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("Exit from getIntlImporterDetail() method");
        
        return importerDetail;
    }

    /** This method is used to get broker details based on ajax call by selecting a broker name from drop down list.
     * @param brokerName
     * @param clientId
     * @param locationId
     * @return String
     */
     public String getIntlBrokerDetail(String brokerName, int clientId, int locationId) {
        logger.info("Enter in to the getIntlBrokerDetail() method");
        String brokerDetail = "";
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_intl_broker_detail")
                                                           .declareParameters(new SqlOutParameter("aasc_get_intl_broker_detail", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_broker_name", brokerName)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
                                                                        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After executing");
//            logger.info("Out = "+out.toString());
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            
            errorStatus =String.valueOf(out.get("OP_ERROR_STATUS"));
            
            logger.info("aasc_erp_intl_shipment_pkg.get_intl_broker_detail opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
            
            if(opStatus == 0){
                Iterator intlBrokerDetailsIt = ((ArrayList)out.get("OP_INTL_BROKER_DETAIL")).iterator();
                while(intlBrokerDetailsIt.hasNext()){
                
                    map1 = (HashMap<String, ?>)intlBrokerDetailsIt.next();
                    
                    brokerDetail = brokerDetail+"*"+nullStrToSpc(String.valueOf(map1.get("broker_address1")))+
                                                "*"+nullStrToSpc(String.valueOf(map1.get("broker_address2")))+
                                                "*"+nullStrToSpc(String.valueOf(map1.get("broker_city")))+
                                                "*"+nullStrToSpc(String.valueOf(map1.get("broker_comp_name")))+
                                                "*"+nullStrToSpc(String.valueOf(map1.get("broker_country_code")))+
                                                "*"+nullStrToSpc(String.valueOf(map1.get("broker_phone_num")))+
                                                "*"+nullStrToSpc(String.valueOf(map1.get("broker_postal_code")))+
                                                "*"+nullStrToSpc(String.valueOf(map1.get("BROKER_STATE")))+
                                                "*"+nullStrToSpc(String.valueOf(map1.get("BROKER_NAME")));
                }
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
        logger.info("Exit from getIntlBrokerDetail() method");
        
        return brokerDetail;
    }
    
    /** This method is used to get Currency Code's from database tables and sets it to a LinkedList
     * @return LinkedList
     */
     public HashMap getCurrencyCodeLookups(){
        logger.info("Enter in to the getCurrencyCodeLookups() method");
        HashMap currencyCodeDetailHashMap = new HashMap();
        String currencyCode = "";
        String currencyName = "";

        try {
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("get_currencycode_lookups")
                                                           .declareParameters(new SqlOutParameter("aasc_get_currencycode_lookups", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
                                                               
            Map<String, Object> out = simpleJdbcCall.execute();
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            errorStatus = String.valueOf(out.get("op_error_status"));
            if(opStatus == 0){          
                Iterator it = ((ArrayList)out.get("OP_CURRENCYCODE_LOOKUPS")).iterator();
                
                while (it.hasNext()) {
                    map1 = (HashMap<String, ?>)it.next();
                
                    currencyCode = nullStrToSpc(String.valueOf(map1.get("CURRENCY_CODE")));
                    currencyName = nullStrToSpc(String.valueOf(map1.get("CURRENCY_NAME")));
                    
                    currencyCodeDetailHashMap.put(currencyCode, currencyName);
                }
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
//            e.printStackTrace();
        }
            
        logger.info("Exit from getCountryLookups() method");
        return currencyCodeDetailHashMap;
    }
    
    /**
     * This method gets the lookup values from backend tables for international shipments
     * and sets to a HashMap.
     * @param carrierCode, lookUpCode
     * @return HashMap
     */
    public HashMap getUpsLookUpValues(int carrierCode, String lookUpCode){
        logger.info("enter in to the getUpsLookUpValues() method");

        upsLookUpValues = new HashMap();

        try {
            DataSource datasource = connectionFactory.createDataSource();
                  
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_intl_shipment_pkg")
                                                           .withProcedureName("ups_get_lookup_values")
                                                           .declareParameters(new SqlOutParameter("aasc_ups_get_lookup_details", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
                                                           
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_carrier_code", carrierCode)
                                                                        .addValue("ip_lookup_code", lookUpCode);
                                                                     
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);

            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            String error = String.valueOf(out.get("op_error_status"));

            logger.info("aasc_intl_shipment_pkg.ups_get_lookup_values opStatus:" + 
                            opStatus + "\t errorStatus:" + error);
            
            Iterator it = ((ArrayList)out.get("OP_UPS_GET_LOOKUP_DETAILS")).iterator();
            while (it.hasNext()) {
                map1 = (HashMap<String, ?>)it.next();
                    
                upsLookUpValues.put(map1.get("LOOKUP_VALUE"),map1.get("MEANING"));

            }

        } catch (Exception e) {
                  logger.severe("Exception::"+e.getMessage());
        }
              
        logger.info("Exit from getUpsLookUpValues() method");
        return upsLookUpValues;
    }
}
