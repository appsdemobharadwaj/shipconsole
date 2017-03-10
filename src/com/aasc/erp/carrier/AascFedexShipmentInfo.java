 /*
       * @(#)AascFedexShipmentInfo.java        
       * Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.
    /   * All rights reserved./
        
 */
 package com.aasc.erp.carrier;

 import com.aasc.erp.bean.AascShipmentHeaderInfo;
 import com.aasc.erp.bean.AascShipmentOrderInfo;
 import com.aasc.erp.bean.AascShipmentPackageInfo;

 import com.aasc.erp.bean.AascProfileOptionsBean;
 import com.aasc.erp.bean.AascShipMethodInfo;
 import com.aasc.erp.util.AascLogger;

 import java.io.FileOutputStream;
 import java.io.StringReader;


import java.sql.Date;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
 import java.util.LinkedList;
 import java.util.ListIterator;
 
 import java.util.logging.Logger;

 import oracle.xml.parser.v2.DOMParser;
 import oracle.xml.parser.v2.XMLDocument;
 import oracle.xml.parser.v2.XMLElement;

 
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;
 import org.w3c.dom.Element;

 import org.xml.sax.InputSource;


 
  




 /**
  * AascFedexShipmentInfo Class is used to parse the shipment response from
  * fedex and set the parsed data(shipment cost and tracking numbers)
  * to header bean and package bean.
  * @author      
  * @version - 1
  * @since
  * 
  * 19/01/2015      Suman Gunda         Removed unnecessary comments in history and code.
  * 02/01/2015      Y Pradeep           Modified name of Commercial Invoice documnet.
  * 02/02/2015      Eshwari M           Added code related to transitTime and delivertDate for Carrier SLA Report.
  * 28/01/2015      Suman Gunda         Removed unnecessary code.
  * 09/07/2015      Suman G             Added code to work for new Fedex response.
  * 24/08/2015      N Srisha            Added code for displaying warning message from Fedex response bug #3461
  * 18/11/2015      Shiva G             Modified code to fix the issue #3860 
  */

 public class
 AascFedexShipmentInfo {
     private static Logger logger = 
         AascLogger.getLogger("AascFedexShipmentInfo"); // Logger object for issuing logging requests
     private String displayMessage = ""; // String containing status of response
     private String errorDescription = ""; // String containing error message  
     private StringReader reader = 
         null; // String reader object which takes String as input and converts it into character stream 
     private InputSource input = 
         null; // This object is used to encapsulate character stream into single object
     private DOMParser parser = 
         null; // This object is used to parse xml document 
     private XMLDocument xmlDocument = null; // This encapsulates xml document
     private XMLElement root = null; // This encapsulates xml element 
     private Node node = null; // It represents single node in dom tree

     private LinkedList packageList = 
         null; // Linked list containing package bean objects
     private int numOfPackages = 
         0; // Indicates  number of packages in the delivery
     private String errorCode = 
         ""; // indicates error code in unsuccessful xml response      
     private String trackingNumber = 
         ""; // indicates parsed tracking number of package in the fedex shipment response
     
     private String rtnTrackingNumber = "";
     
     private String totalShipmentCharges = 
         "0.00"; // indicates total shipment charges     
     private String totalShipmentListCharges = 
         "0.00"; // indicates total shipment charges    
     private String wayBillNumber = 
         ""; // indicates parsed waybill number of delivery in the fedex shipment response
     private String softErrorType = 
         ""; // holds soft error(or warning) type returned in response    

     private String label = ""; // holds parsed label from response

     private String commercialInvoice = "";
     private byte[] parsedLabel; // holds decode label
     
      private byte[] parsedCI;
     private FileOutputStream fout = 
         null; // file output stream to write label to specified file

     private String labelPath = ""; // contains output file path

     private AascShipmentHeaderInfo aascShipmentHeaderInfo = 
         null; // Delivery header info bean object
     private AascShipmentPackageInfo aascShipmentPackageInfo = 
         null; // Delivery  package info bean object

     private AascShipmentPackageInfo aascShipPkgInfo = null;

     private String masterTrackingNumber = "";
     private String masterFormId = "";
     private HashMap hashMap = new HashMap();
     private String labelFormatExtension = "";
     private int packageCnt = 0;
     double surCharges = 0;
     double listsurCharges = 0;

     String codTrackingNum = "";
     private String baseCharge = "0.00"; //Indicates baseCharge
     private String listBaseCharge = "0.00";
     private String totalDiscount = "0.00"; //Indicates totalDiscount
     private String totalListDiscount = "0.00";
     private int carrierId;

     private String HazMatCharges = "0.00";
     private String listHazMatCharges = "0.00";
     private String sCharges = "0.00";
     private String sListCharges = "0.00";
     
     private String op900Label= "";
     private byte[] parsedOp900Label;

     private String transitTime = ""; 
     private Date deliveryDate;
          
     private String DocumentID = "";
     private String DocumentType = "";

     /** default constructor. */
     public AascFedexShipmentInfo() {
     }

     public static void main(String[] args) {
         String res = 
             "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><soapenv:Body><v9:ProcessShipmentReply xmlns:v9=\"http://fedex.com/ws/ship/v9\"><v9:HighestSeverity xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">SUCCESS</v9:HighestSeverity><v9:Notifications xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><v9:Severity>SUCCESS</v9:Severity><v9:Source>ship</v9:Source><v9:Code>0000</v9:Code><v9:Message>Success</v9:Message><v9:LocalizedMessage>Success</v9:LocalizedMessage></v9:Notifications><v9:TransactionDetail xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><v9:CustomerTransactionId>183191</v9:CustomerTransactionId></v9:TransactionDetail><v9:Version xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><v9:ServiceId>ship</v9:ServiceId><v9:Major>9</v9:Major><v9:Intermediate>0</v9:Intermediate><v9:Minor>0</v9:Minor></v9:Version><v9:CompletedShipmentDetail><v9:UsDomestic xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">false</v9:UsDomestic><v9:CarrierCode xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">FDXE</v9:CarrierCode><v9:ServiceTypeDescription xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">INTL ** 2DAY **</v9:ServiceTypeDescription><v9:PackagingDescription xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">YOUR_PACKAGING</v9:PackagingDescription><v9:RoutingDetail xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><v9:UrsaPrefixCode>S2</v9:UrsaPrefixCode><v9:UrsaSuffixCode>YRMX </v9:UrsaSuffixCode><v9:OriginLocationId>GBRA </v9:OriginLocationId><v9:OriginServiceArea>A1</v9:OriginServiceArea><v9:DestinationLocationId>YRMX </v9:DestinationLocationId><v9:DestinationServiceArea>O2</v9:DestinationServiceArea><v9:DestinationLocationStateOrProvinceCode>AB</v9:DestinationLocationStateOrProvinceCode><v9:AstraPlannedServiceLevel>O2</v9:AstraPlannedServiceLevel><v9:AstraDescription>INTL ** 2DAY **</v9:AstraDescription><v9:PostalCode>T0M0L0</v9:PostalCode><v9:StateOrProvinceCode>AB</v9:StateOrProvinceCode><v9:CountryCode>CA</v9:CountryCode><v9:AirportId>YYC</v9:AirportId></v9:RoutingDetail><v9:ShipmentRating xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><v9:ActualRateType>PAYOR_ACCOUNT_SHIPMENT</v9:ActualRateType><v9:EffectiveNetDiscount><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:EffectiveNetDiscount><v9:ShipmentRateDetails><v9:RateType>PAYOR_ACCOUNT_SHIPMENT</v9:RateType><v9:RateScale>0000000</v9:RateScale><v9:RateZone>US001O</v9:RateZone><v9:PricingCode>ACTUAL</v9:PricingCode><v9:RatedWeightMethod>ACTUAL</v9:RatedWeightMethod><v9:CurrencyExchangeRate><v9:FromCurrency>USD</v9:FromCurrency><v9:IntoCurrency>USD</v9:IntoCurrency><v9:Rate>1.0</v9:Rate></v9:CurrencyExchangeRate><v9:DimDivisor>139</v9:DimDivisor><v9:DimDivisorType>COUNTRY</v9:DimDivisorType><v9:FuelSurchargePercent>11.0</v9:FuelSurchargePercent><v9:TotalBillingWeight><v9:Units>LB</v9:Units><v9:Value>10.0</v9:Value></v9:TotalBillingWeight><v9:TotalBaseCharge><v9:Currency>USD</v9:Currency><v9:Amount>99.12</v9:Amount></v9:TotalBaseCharge><v9:TotalFreightDiscounts><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:TotalFreightDiscounts><v9:TotalNetFreight><v9:Currency>USD</v9:Currency><v9:Amount>99.12</v9:Amount></v9:TotalNetFreight><v9:TotalSurcharges><v9:Currency>USD</v9:Currency><v9:Amount>37.54</v9:Amount></v9:TotalSurcharges><v9:TotalNetFedExCharge><v9:Currency>USD</v9:Currency><v9:Amount>136.66</v9:Amount></v9:TotalNetFedExCharge><v9:TotalTaxes><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:TotalTaxes><v9:TotalNetCharge><v9:Currency>USD</v9:Currency><v9:Amount>136.66</v9:Amount></v9:TotalNetCharge><v9:TotalRebates><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:TotalRebates><v9:Surcharges><v9:SurchargeType>OUT_OF_DELIVERY_AREA</v9:SurchargeType><v9:Description>Out of delivery area</v9:Description><v9:Amount><v9:Currency>USD</v9:Currency><v9:Amount>24.0</v9:Amount></v9:Amount></v9:Surcharges><v9:Surcharges><v9:SurchargeType>FUEL</v9:SurchargeType><v9:Description>Fuel</v9:Description><v9:Amount><v9:Currency>USD</v9:Currency><v9:Amount>13.54</v9:Amount></v9:Amount></v9:Surcharges></v9:ShipmentRateDetails><v9:ShipmentRateDetails><v9:RateType>RATED_ACCOUNT_SHIPMENT</v9:RateType><v9:RateScale>0000000</v9:RateScale><v9:RateZone>US001O</v9:RateZone><v9:PricingCode>ACTUAL</v9:PricingCode><v9:RatedWeightMethod>ACTUAL</v9:RatedWeightMethod><v9:CurrencyExchangeRate><v9:FromCurrency>USD</v9:FromCurrency><v9:IntoCurrency>USD</v9:IntoCurrency><v9:Rate>1.0</v9:Rate></v9:CurrencyExchangeRate><v9:DimDivisor>139</v9:DimDivisor><v9:DimDivisorType>COUNTRY</v9:DimDivisorType><v9:FuelSurchargePercent>11.0</v9:FuelSurchargePercent><v9:TotalBillingWeight><v9:Units>LB</v9:Units><v9:Value>10.0</v9:Value></v9:TotalBillingWeight><v9:TotalBaseCharge><v9:Currency>USD</v9:Currency><v9:Amount>99.12</v9:Amount></v9:TotalBaseCharge><v9:TotalFreightDiscounts><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:TotalFreightDiscounts><v9:TotalNetFreight><v9:Currency>USD</v9:Currency><v9:Amount>99.12</v9:Amount></v9:TotalNetFreight><v9:TotalSurcharges><v9:Currency>USD</v9:Currency><v9:Amount>37.54</v9:Amount></v9:TotalSurcharges><v9:TotalNetFedExCharge><v9:Currency>USD</v9:Currency><v9:Amount>136.66</v9:Amount></v9:TotalNetFedExCharge><v9:TotalTaxes><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:TotalTaxes><v9:TotalNetCharge><v9:Currency>USD</v9:Currency><v9:Amount>136.66</v9:Amount></v9:TotalNetCharge><v9:TotalRebates><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:TotalRebates><v9:Surcharges><v9:SurchargeType>OUT_OF_DELIVERY_AREA</v9:SurchargeType><v9:Description>Out of delivery area</v9:Description><v9:Amount><v9:Currency>USD</v9:Currency><v9:Amount>24.0</v9:Amount></v9:Amount></v9:Surcharges><v9:Surcharges><v9:SurchargeType>FUEL</v9:SurchargeType><v9:Description>Fuel</v9:Description><v9:Amount><v9:Currency>USD</v9:Currency><v9:Amount>13.54</v9:Amount></v9:Amount></v9:Surcharges></v9:ShipmentRateDetails><v9:ShipmentRateDetails><v9:RateType>PAYOR_LIST_SHIPMENT</v9:RateType><v9:RateScale>0000000</v9:RateScale><v9:RateZone>US001O</v9:RateZone><v9:PricingCode>ACTUAL</v9:PricingCode><v9:RatedWeightMethod>ACTUAL</v9:RatedWeightMethod><v9:CurrencyExchangeRate><v9:FromCurrency>USD</v9:FromCurrency><v9:IntoCurrency>USD</v9:IntoCurrency><v9:Rate>1.0</v9:Rate></v9:CurrencyExchangeRate><v9:DimDivisor>139</v9:DimDivisor><v9:DimDivisorType>COUNTRY</v9:DimDivisorType><v9:FuelSurchargePercent>11.0</v9:FuelSurchargePercent><v9:TotalBillingWeight><v9:Units>LB</v9:Units><v9:Value>10.0</v9:Value></v9:TotalBillingWeight><v9:TotalBaseCharge><v9:Currency>USD</v9:Currency><v9:Amount>99.12</v9:Amount></v9:TotalBaseCharge><v9:TotalFreightDiscounts><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:TotalFreightDiscounts><v9:TotalNetFreight><v9:Currency>USD</v9:Currency><v9:Amount>99.12</v9:Amount></v9:TotalNetFreight><v9:TotalSurcharges><v9:Currency>USD</v9:Currency><v9:Amount>37.54</v9:Amount></v9:TotalSurcharges><v9:TotalNetFedExCharge><v9:Currency>USD</v9:Currency><v9:Amount>136.66</v9:Amount></v9:TotalNetFedExCharge><v9:TotalTaxes><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:TotalTaxes><v9:TotalNetCharge><v9:Currency>USD</v9:Currency><v9:Amount>136.66</v9:Amount></v9:TotalNetCharge><v9:TotalRebates><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:TotalRebates><v9:Surcharges><v9:SurchargeType>OUT_OF_DELIVERY_AREA</v9:SurchargeType><v9:Description>Out of delivery area</v9:Description><v9:Amount><v9:Currency>USD</v9:Currency><v9:Amount>24.0</v9:Amount></v9:Amount></v9:Surcharges><v9:Surcharges><v9:SurchargeType>FUEL</v9:SurchargeType><v9:Description>Fuel</v9:Description><v9:Amount><v9:Currency>USD</v9:Currency><v9:Amount>13.54</v9:Amount></v9:Amount></v9:Surcharges></v9:ShipmentRateDetails><v9:ShipmentRateDetails><v9:RateType>RATED_LIST_SHIPMENT</v9:RateType><v9:RateScale>0000000</v9:RateScale><v9:RateZone>US001O</v9:RateZone><v9:PricingCode>ACTUAL</v9:PricingCode><v9:RatedWeightMethod>ACTUAL</v9:RatedWeightMethod><v9:CurrencyExchangeRate><v9:FromCurrency>USD</v9:FromCurrency><v9:IntoCurrency>USD</v9:IntoCurrency><v9:Rate>1.0</v9:Rate></v9:CurrencyExchangeRate><v9:DimDivisor>139</v9:DimDivisor><v9:DimDivisorType>COUNTRY</v9:DimDivisorType><v9:FuelSurchargePercent>11.0</v9:FuelSurchargePercent><v9:TotalBillingWeight><v9:Units>LB</v9:Units><v9:Value>10.0</v9:Value></v9:TotalBillingWeight><v9:TotalBaseCharge><v9:Currency>USD</v9:Currency><v9:Amount>99.12</v9:Amount></v9:TotalBaseCharge><v9:TotalFreightDiscounts><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:TotalFreightDiscounts><v9:TotalNetFreight><v9:Currency>USD</v9:Currency><v9:Amount>99.12</v9:Amount></v9:TotalNetFreight><v9:TotalSurcharges><v9:Currency>USD</v9:Currency><v9:Amount>37.54</v9:Amount></v9:TotalSurcharges><v9:TotalNetFedExCharge><v9:Currency>USD</v9:Currency><v9:Amount>136.66</v9:Amount></v9:TotalNetFedExCharge><v9:TotalTaxes><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:TotalTaxes><v9:TotalNetCharge><v9:Currency>USD</v9:Currency><v9:Amount>136.66</v9:Amount></v9:TotalNetCharge><v9:TotalRebates><v9:Currency>USD</v9:Currency><v9:Amount>0.0</v9:Amount></v9:TotalRebates><v9:Surcharges><v9:SurchargeType>OUT_OF_DELIVERY_AREA</v9:SurchargeType><v9:Description>Out of delivery area</v9:Description><v9:Amount><v9:Currency>USD</v9:Currency><v9:Amount>24.0</v9:Amount></v9:Amount></v9:Surcharges><v9:Surcharges><v9:SurchargeType>FUEL</v9:SurchargeType><v9:Description>Fuel</v9:Description><v9:Amount><v9:Currency>USD</v9:Currency><v9:Amount>13.54</v9:Amount></v9:Amount></v9:Surcharges></v9:ShipmentRateDetails></v9:ShipmentRating><v9:IneligibleForMoneyBackGuarantee xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">false</v9:IneligibleForMoneyBackGuarantee><v9:ExportComplianceStatement xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">NO EEI 30.36</v9:ExportComplianceStatement><v9:CompletedPackageDetails xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><v9:SequenceNumber>1</v9:SequenceNumber><v9:TrackingIds><v9:TrackingIdType>EXPRESS</v9:TrackingIdType><v9:FormId>0430</v9:FormId><v9:TrackingNumber>794794181778</v9:TrackingNumber></v9:TrackingIds><v9:GroupNumber>0</v9:GroupNumber><v9:Barcodes><v9:BinaryBarcodes><v9:Type>COMMON_2D</v9:Type><v9:Value>Wyk+HjAxHTAyVDBNMEwwHTEyNB0wMx0wMDAwMDAwMDAwMDAwNDMwHUZERR01MTAwODcwNDYdMTUwHR0xLzEdMTAuMExCHU4dTGFuZSA0HUNhbWJyaWRnZR1BQh0gHjA2HTEwWkVJTzA1HTMxWjQwMjY0Nzk5MzEwMDAwMDEwMDAwMDAwMDAwMDA0MzA1HTMyWh0xNFoqKlRFU1QgTEFCRUwgLSBETyBOT1QgU0hJUCoqHTE1WjEwMDAwMTYxNR0yOFodMjZaNDQ3YxwdHgQ=</v9:Value></v9:BinaryBarcodes><v9:StringBarcodes><v9:Type>ASTRA</v9:Type><v9:Value>40264799310000017947941817784305</v9:Value></v9:StringBarcodes></v9:Barcodes><v9:AstraLabelElements><v9:Number>2</v9:Number><v9:Content>TRK#</v9:Content></v9:AstraLabelElements><v9:AstraLabelElements><v9:Number>3</v9:Number><v9:Content>0430</v9:Content></v9:AstraLabelElements><v9:AstraLabelElements><v9:Number>5</v9:Number><v9:Content>S2 YRMX </v9:Content></v9:AstraLabelElements><v9:AstraLabelElements><v9:Number>7</v9:Number><v9:Content>40264799310000017947941817784305</v9:Content></v9:AstraLabelElements><v9:AstraLabelElements><v9:Number>8</v9:Number><v9:Content>50DG1/0CB0/7EFB</v9:Content></v9:AstraLabelElements><v9:AstraLabelElements><v9:Number>10</v9:Number><v9:Content>7947 9418 1778</v9:Content></v9:AstraLabelElements><v9:AstraLabelElements><v9:Number>12</v9:Number><v9:Content>O2</v9:Content></v9:AstraLabelElements><v9:AstraLabelElements><v9:Number>13</v9:Number><v9:Content>INTL ** 2DAY **</v9:Content></v9:AstraLabelElements><v9:AstraLabelElements><v9:Number>15</v9:Number><v9:Content>T0M 0L0</v9:Content></v9:AstraLabelElements><v9:AstraLabelElements><v9:Number>16</v9:Number><v9:Content>AB-CA</v9:Content></v9:AstraLabelElements><v9:AstraLabelElements><v9:Number>17</v9:Number><v9:Content>YYC</v9:Content></v9:AstraLabelElements><v9:Label><v9:Type>OUTBOUND_LABEL</v9:Type><v9:ShippingDocumentDisposition>RETURNED</v9:ShippingDocumentDisposition><v9:Resolution>203</v9:Resolution><v9:CopiesToPrint>1</v9:CopiesToPrint><v9:Parts><v9:DocumentPartSequenceNumber>1</v9:DocumentPartSequenceNumber><v9:Image>XlhBXkNGLDAsMCwwXlBSMTJeTUQzMF5QVzgwMF5QT05eQ0kxM15MSDAsMjAKXkZPMTIsMTM5XkdCNzUzLDIsMl5GUwpeRk8xMiw0MDVeR0I3NzcsMiwyXkZTCl5GTzEyLDY5NF5HQjc3NywyLDJeRlMKXkZPNDY0LDheR0IyLDEyOSwyXkZTCl5GTzMyLDEwXkFkTiwwLDBeRldOXkZIXkZET1JJR0lOIElEOiBHQlJBXkZTCl5GTzIyNCwxMF5BZE4sMCwwXkZXTl5GSF5GRCgxMjMpIDQ1Ni03ODkwXkZTCl5GTzMyLDI4XkFkTiwwLDBeRldOXkZIXkZEXkZTCl5GTzMyLDQ2XkFkTiwwLDBeRldOXkZIXkZEQVBQU15GUwpeRk8zMiw2NF5BZE4sMCwwXkZXTl5GSF5GRDM5MyBCRVJLRUxFWSBTVFJFRVReRlMKXkZPMzIsODJeQWROLDAsMF5GV05eRkheRkRTVFJFRVQyXkZTCl5GTzQ3OCwxMF5BZE4sMCwwXkZXTl5GSF5GRFNISVAgREFURTogMzBNQVkxMV5GUwpeRk80NzgsNDZeQWROLDAsMF5GV05eRkheRkRDQUQ6IDEwMDAwMTYxNS9XU1hJMjUwMF5GUwpeRk84LDE1MV5BME4sMjEsMjFeRldOXkZIXkZEVE9eRlMKXkZPMzcsMjMzXkEwTiwzOCwzOF5GV05eRkheRkRMQU5FIDReRlMKXkZPMzcsMjc1XkEwTiwzOCwzOF5GV05eRkheRkQqKlRFU1QgTEFCRUwgLSBETyBOT1QgU0hJUCoqXkZTCl5GTzM3LDMxN15BME4sNDMsNDBeRldOXkZIXkZEQ0FNQlJJREdFIEFCIFQwTTBMMF5GUwpeRk83MDgsMzIwXkEwTiwzNSw0NV5GV05eRkheRkQoQ0EpXkZTCl5GTzI4LDc0N15BME4sMjQsMjReRldOXkZIXkZEVFJLI15GUwpeRk8yOCw4MDVeQTBOLDI3LDMyXkZXTl5GSF5GRF5GUwpeRk8xMzYsNzE3XkEwTiwyNywzNl5GV05eRkheRkReRlMKXkZPMzIsMTAwXkFkTiwwLDBeRldOXkZIXkZEQk9TVE9OLCBNQSAwMjExNiBVU15GUwpeRk8zMiwxMTheQWROLDAsMF5GV05eRkheRkRTSUdOOiBeRlMKXkZPNDc4LDEwMF5BZE4sMCwwXkZXTl5GSF5GREJJTEwgU0VOREVSXkZTCl5GTzQ3OCwxMTheQTBOLDE1LDE1XkZXTl5GSF5GRE5PIEVFSSAzMC4zNl5GUwpeRk8zNywxNDleQTBOLDM4LDM4XkZXTl5GSF5GRF5GUwpeRk8zNSwzNTleQTBOLDIxLDIxXkZXTl5GSF5GRCgxMjMpIDQ1Ni03ODkwXkZTCl5GTzM3LDE5MV5BME4sMzgsMzheRldOXkZIXkZEU0FNXzVGQ1VTVF81RkNBTkFEQV5GUwpeRk82NzcsNDc4XkdCMTA0LDEwLDEwXkZTCl5GTzY3Nyw0ODheR0IxMCwxMTIsMTBeRlMKXkZPNzcxLDQ4OF5HQjEwLDExMiwxMF5GUwpeRk82NzcsNjAwXkdCMTA0LDEwLDEwXkZTCl5GTzY1Miw0MTZeQTBOLDQzLDU4XkZXTl5GSF5GREZlZEV4XkZTCl5GTzcwOCw0NTVeQTBOLDE5LDI2XkZXTl5GSF5GREV4cHJlc3NeRlMKXkZPNjk3LDQ5Nl5BME4sMTI4LDEzN15GV05eRkheRkRFXkZTCl5GTzEwLDY1M15BME4sMTMsMTNeRldOXkZIXkZEVGhlc2UgY29tbW9kaXRpZXMsIHRlY2hub2xvZ3ksIG9yIHNvZnR3YXJlIHdlcmUgZXhwb3J0ZWQgZnJvbV5GUwpeRk8xMCw2NjdeQTBOLDEzLDEzXkZXTl5GSF5GRHRoZSBVbml0ZWQgU3RhdGVzIGluIGFjY29yZGFuY2Ugd2l0aCB0aGUgZXhwb3J0IGFkbWluaXN0cmF0aW9uXkZTCl5GTzEwLDY4MV5BME4sMTMsMTNeRldOXkZIXkZEcmVndWxhdGlvbnMuIERpdmVyc2lvbiBjb250cmFyeSB0byBVUyBsYXcgaXMgcHJvaGliaXRlZC5eRlMKXkZPNzg1LDQ4MF5BME4sMTMsMTheRldCXkZIXkZESjExMTUxMTAyMjUwMTI1XkZTCl5GTzIxLDQyNV5CWTIsMl5CN04sMTAsNSwxNF5GSF5GV05eRkheRkRbKT5fMUUwMV8xRDAyVDBNMEwwXzFEMTI0XzFEMDNfMUQwMDAwMDAwMDAwMDAwNDMwXzFERkRFXzFENTEwMDg3MDQ2XzFEMTUwXzFEXzFEMS8xXzFEMTAuMExCXzFETl8xRExhbmUgNF8xRENhbWJyaWRnZV8xREFCXzFEIF8xRTA2XzFEMTBaRUlPMDVfMUQzMVo0MDI2NDc5OTMxMDAwMDAxMDAwMDAwMDAwMDAwNDMwNV8xRDMyWl8xRDE0WioqVEVTVCBMQUJFTCAtIERPIE5PVCBTSElQKipfMUQxNVoxMDAwMDE2MTVfMUQyOFpfMUQyNlo0NDdjXzFDXzFEXzFFXzA0XkZTCl5GTzI4LDg0Ml5BME4sMTA3LDk2XkZXTl5GSF5GRFMyIFlSTVggXkZTCl5GTzQ5NCw4OTBeQTBOLDQzLDQzXkZXTl5GSF5GRF5GUwpeRk83OTEsMTIwXkFiTiwxMSw3XkZXQl5GSF5GRDUwREcxLzBDQjAvN0VGQl5GUwpeRk85NSw3NTFeQTBOLDUzLDQwXkZXTl5GSF5GRDc5NDcgOTQxOCAxNzc4XkZTCl5GTzQwOSw3MDBeQTBOLDUxLDM4XkZXTl5GSF5GQjM5MCwsLFIsXkZEICAgICAgICAgICAgICAgICBPMl5GUwpeRk80MDksNzUyXkEwTiw1MSwzOF5GV05eRkheRkIzOTAsLCxSLF5GRCAgICBJTlRMICoqIDJEQVkgKipeRlMKXkZPNDEzLDgwNF5BME4sNDAsNDBeRldOXkZIXkZCMzg2LCwsUixeRkQgICAgICAgICAgICAgICAgXkZTCl5GTzQ5NSw4NDZeQTBOLDQ0LDQ0XkZXTl5GSF5GQjI5OCwsLFIsXkZEICAgVDBNIDBMMF5GUwpeRk81NzQsOTA2XkEwTiwyNCwyNF5GV05eRkheRkIxMjAsLCxSLF5GRCBBQi1DQV5GUwpeRk82OTUsODkwXkEwTiw0Myw0M15GV05eRkheRkIxMDAsLCxSLF5GRFlZQ15GUwpeRk8zOSw5MzJeQTBOLDI3LDMyXkZXTl5GSF5GRF5GUwpeRk83NSw5OTNeQlkzLDJeQkNOLDIwMCxOLE4sTixOXkZXTl5GRD47NDAyNjQ3OTkzMTAwMDAwMTAwMDAwMDAwMDAwMDQzMDVeRlMKXkZPNDc4LDI4XkFkTiwwLDBeRldOXkZIXkZEQUNUV0dUOiAxMC4wIExCXkZTCl5GTzQ3OCw2NF5BZE4sMCwwXkZXTl5GSF5GRERJTVM6IDd4N3g3IElOXkZTCl5GTzMyOCwzNjReQWJOLDExLDdeRldOXkZIXkZEUkVGOiBERUwjIDE4MzE5MSAgU08jIDYwNTY5XkZTCl5GTzM4LDM3OF5BYk4sMTEsN15GV05eRkheRkRJTlY6IF5GUwpeRk8zOCwzOTJeQWJOLDExLDdeRldOXkZIXkZEUE86IF5GUwpeRk80MjgsMzkyXkFiTiwxMSw3XkZXTl5GSF5GRERFUFQ6IF5GUwpeRk8yNSw3NjheR0I1OCwxLDFeRlMKXkZPMjUsNzY4XkdCMSwyNiwxXkZTCl5GTzgzLDc2OF5HQjEsMjYsMV5GUwpeRk8yNSw3OTReR0I1OCwxLDFeRlMKXkZPMzEsNzc0XkFkTiwwLDBeRldOXkZIXkZEMDQzMF5GUwpeUFExCl5YWgpeWEFeQ0YsMCwwLDBeUFIxMl5NRDMwXlBXODAwXlBPTl5DSTEzXkxIMCwyMApeRk8wLDE0Nl5HQjgwMCwyLDJeRlMKXkZPMCw0MDNeR0I4MDAsMiwyXkZTCl5GTzAsOTIzXkdCODAwLDIsMl5GUwpeRk8wLDk5Nl5HQjgwMCwyLDJeRlMKXkZPMzUsN15BZE4sMCwwXkZXTl5GSF5GRE9SSUdJTiBJRDogR0JSQSAoMTIzKSA0NTYtNzg5MF5GUwpeRk8zNSwyNl5BZE4sMCwwXkZXTl5GSF5GRF5GUwpeRk8zNSw0Nl5BZE4sMCwwXkZXTl5GSF5GREFQUFNeRlMKXkZPMzUsNjZeQWROLDAsMF5GV05eRkheRkQzOTMgQmVya2VsZXkgU3RyZWV0XkZTCl5GTzM1LDg3XkFkTiwwLDBeRldOXkZIXkZEc3RyZWV0Ml5GUwpeRk8zNSwxMDdeQWROLDAsMF5GV05eRkheRkRCb3N0b24sIE1BIDAyMTE2XkZTCl5GTzM1LDEyN15BZE4sMCwwXkZXTl5GSF5GRFVOSVRFRCBTVEFURVMsIFVTXkZTCl5GTzQ5MCw3XkFkTiwwLDBeRldOXkZIXkZEU2hpcCBEYXRlOiAzME1BWTExXkZTCl5GTzQ5MCw0Nl5BZE4sMCwwXkZXTl5GSF5GRENBRDogMTAwMDAxNjE1L1dTWEkyNTAwXkZTCl5GTzEwLDE1N15BME4sMjAsMTheRldOXkZIXkZEVE9eRlMKXkZPNDMsMTkzXkEwTiwyNSwyN15GV05eRkheRkRzYW1fNUZjdXN0XzVGY2FuYWRhXkZTCl5GTzQzLDIyOF5BME4sMjUsMjdeRldOXkZIXkZETGFuZSA0XkZTCl5GTzQzLDI2M15BME4sMjUsMjdeRldOXkZIXkZEKipURVNUIExBQkVMIC0gRE8gTk9UIFNISVAqKl5GUwpeRk81NTAsMzA1XkEwTiwzNSw0NV5GV05eRkheRkQoQ0EpXkZTCl5GTzUwMCw0MTNeQTBOLDUwLDU1XkZXTl5GSF5GRFMyIFlSTVggXkZTCl5GTzQ5MCw0NjVeQTBOLDI1LDM1XkZXTl5GSF5GRF5GUwpeRk80OTAsNDkzXkEwTiwyNSwyN15GV05eRkheRkRQS0cgVFlQRTogQ1VTVE9NRVJeRlMKXkZPMzUsNTQxXkFkTiwwLDBeRldOXkZIXkZEVFJLI15GUwpeRk8zNjAsNTQxXkFkTiwwLDBeRldOXkZIXkZERm9ybV5GUwpeRk8zNSw2MTheQTBOLDM1LDM1XkZXTl5GSF5GRElOVEwgKiogMkRBWSAqKl5GUwpeRk8xNiw2NzFeQWROLDAsMF5GV05eRkheRkRSRUY6IERlbCMgMTgzMTkxICBTTyMgNjA1NjleRlMKXkZPNDMsMTU4XkEwTiwyNSwyN15GV05eRkheRkReRlMKXkZPNjE1LDE1Nl5BZE4sMCwwXkZXTl5GSF5GRCgxMjMpIDQ1Ni03ODkwXkZTCl5GTzY3MCwyNjheR0IxMDUsMTAsMTBeRlMKXkZPNjcwLDI3OF5HQjEwLDExMiwxMF5GUwpeRk83NjUsMjc4XkdCMTAsMTEyLDEwXkZTCl5GTzY3MCwzOTBeR0IxMDUsMTAsMTBeRlMKXkZPNDc3LDBeR0IyLDE0NiwyXkZTCl5GTzY1MCwxODNeQTBOLDUwLDU1XkZXTl5GSF5GREZlZEV4XkZTCl5GTzcxNSwyMjZeQWROLDAsMF5GV05eRkheRkRFeHByZXNzXkZTCl5GTzY5MCwyODZeQTBOLDEzMCwxMzBeRldOXkZIXkZERV5GUwpeRk81NzUsMzc1XkEwTiwyMCwzNV5GV05eRkheRkRBV0JeRlMKXkZPMTYsNjkxXkFkTiwwLDBeRldOXkZIXkZEREVTQzE6IHRlc3QxMjNeRlMKXkZPMTYsNzExXkFkTiwwLDBeRldOXkZIXkZEREVTQzI6IF5GUwpeRk8xNiw3MzFeQWROLDAsMF5GV05eRkheRkRERVNDMzogXkZTCl5GTzE2LDc1MV5BZE4sMCwwXkZXTl5GSF5GRERFU0M0OiBeRlMKXkZPOTUsNTQzXkEwTiwyNSwyN15GV05eRkheRkQwMDAwIDAwMDAgMDAwMF5GUwpeRk8zNjAsNTYxXkFkTiwwLDBeRldOXkZIXkZEMDQzMF5GUwpeRk8zMCw0MjheQlkyLDNeQktOLE4sMTAwLE4sTixCLEReRldOXkZEPjtCMDAwMDAwMDAwMDAwMDQzMEReRlMKXkZPMTYsNzcxXkFkTiwwLDBeRldOXkZIXkZERUVJOiBOTyBFRUkgMzAuMzZeRlMKXkZPNDUwLDkzNl5BZE4sMCwwXkZXTl5GSF5GRFNJR046IF5GUwpeRk8xNiw5NTZeQWROLDAsMF5GV05eRkheRkRDQVJSSUFHRSBWQUxVRTogIF5GUwpeRk8xNiw5MzZeQWROLDAsMF5GV05eRkheRkRDT1VOVFJZIE1GRzogVVMgICBeRlMKXkZPMTYsOTc2XkFkTiwwLDBeRldOXkZIXkZEQ1VTVE9NUyBWQUxVRTogMTAuMDAgVVNEXkZTCl5GTzQ1MCw5NTZeQWROLDAsMF5GV05eRkheRkRUL0M6IFMgNTEwMDg3MDQ2XkZTCl5GTzQ1MCw5NzZeQWROLDAsMF5GV05eRkheRkREL1Q6IFMgNTEwMDg3MDQ2XkZTCl5GTzE2LDEwMTFeQTBOLDE1LDE1XkZXTl5GSF5GRFRoZXNlIGNvbW1vZGl0aWVzLCB0ZWNobm9sb2d5LCBvciBzb2Z0d2FyZSB3ZXJlIGV4cG9ydGVkIGZyb20gdGhlXkZTCl5GTzE2LDEwMzFeQTBOLDE1LDE1XkZXTl5GSF5GRFVuaXRlZCBTdGF0ZXMgaW4gYWNjb3JkYW5jZSB3aXRoIHRoZSBleHBvcnQgYWRtaW5pc3RyYXRpb25eRlMKXkZPMTYsMTA1MV5BME4sMTUsMTVeRldOXkZIXkZEcmVndWxhdGlvbnMuIERpdmVyc2lvbiBjb250cmFyeSB0byBVUyBsYXcgaXMgcHJvaGliaXRlZC5eRlMKXkZPMTYsMTA3MV5BME4sMTUsMTVeRldOXkZIXkZEVGhlIE1vbnRyZWFsIG9yIFdhcnNhdyBDb252ZW50aW9uIG1heSBhcHBseSBhbmQgd2lsbCBnb3Zlcm4gYW5kIGluIG1vc3ReRlMKXkZPMTYsMTA5MV5BME4sMTUsMTVeRldOXkZIXkZEY2FzZXMgbGltaXQgdGhlIGxpYWJpbGl0eSBvZiBGZWRlcmFsIEV4cHJlc3MgZm9yIGxvc3Mgb3IgZGVsYXkgb2Ygb3JeRlMKXkZPMTYsMTExMV5BME4sMTUsMTVeRldOXkZIXkZEZGFtYWdlIHRvIHlvdXIgc2hpcG1lbnQuIFN1YmplY3QgdG8gdGhlIGNvbmRpdGlvbnMgb2YgdGhlXkZTCl5GTzE2LDExMzFeQTBOLDE1LDE1XkZXTl5GSF5GRGNvbnRyYWN0IG9uIHRoZSByZXZlcnNlLl5GUwpeRk83ODMsMjU4XkEwTiwxNSwxNV5GV0JeRkheRkRKMTExNTExMDIyNTIwMjVeRlMKXkZPMTUsMTE2NV5BME4sMjAsMzVeRldOXkZIXkZET1JJR0lOIEFXQiBDT1BZIC0gUExFQVNFIFBMQUNFIElOIFBPVUNIXkZTCl5GTzQ5MCwyNl5BZE4sMCwwXkZXTl5GSF5GREFjdFdndDogMTAuMCBMQl5GUwpeRk80OTAsODZeQWROLDAsMF5GV05eRkheRkREaW1zOiA3eDd4NyBJTl5GUwpeRk80OTAsMTI2XkFkTiwwLDBeRldOXkZIXkZERUlOL1ZBVDogXkZTCl5GTzQzLDI5Nl5BME4sMzAsMzBeRldOXkZIXkZEQ2FtYnJpZGdlLCBBQiBUME0wTDBeRlMKXkZPMjQwLDU5MF5BME4sMjUsMjdeRldOXkZIXkZEMSBvZiAxXkZTCl5QUTEKXlhaCg==</v9:Image></v9:Parts></v9:Label><v9:SignatureOption>SERVICE_DEFAULT</v9:SignatureOption></v9:CompletedPackageDetails></v9:CompletedShipmentDetail></v9:ProcessShipmentReply></soapenv:Body></soapenv:Envelope>";

         res = res.replaceAll("<soapenv:", "<");
         res = res.replaceAll("<env:", "<");
         res = res.replaceAll("<v9:", "<");
         res = res.replaceAll("</v9:", "</");
         res = res.replaceAll("</soapenv:", "</");

     }

     /** This method can replace the null values with nullString.
      * @return String that is ""
      * @param obj of type Object
      */
     String nullStrToSpc(Object obj) {
         String spcStr = "";

         if (obj == null) {
             return spcStr;
         } else {
             return obj.toString();
         }
     }

     /** method used to covert the request string to contain require format.
      * @param  str String
      * @return String
      * */
     private String escape(String str) {
         String result = null; // replace(str, "&", "&amp;");

         while (str.indexOf("&") != -1) {
             str = replace(str, "&", "&amp;");
         }
         result = str;
         while (result.indexOf("-") != -1) {
             result = replace(result, "-", "");
         }
         while (result.indexOf(",") != -1) {
             result = replace(result, ",", "");
         }
         return result;
     }

     /** method used to replace a substring of a string with another substring.
      * @param str String, from String, to String
      * @return String
      * */
     private String replace(String str, String from, String to) {
         int index = str.indexOf(from);

         if (index == -1) {
             return str;
         } else {
             int endIndex = index + from.length();

             return str.substring(0, index) + to + str.substring(endIndex);
         }
     }



     HashMap parseWebServiceResponse(String shipmentResponse, 
                                     AascShipmentOrderInfo aascShipmentOrderInfo, 
                                     AascShipMethodInfo aascShipMethodInfo, 
                                     AascProfileOptionsBean aascProfileOptionsInfo, 
                                     String pkgSequenceNum, String chkReturnlabel,String cloudLabelPath) {
         logger.info("Entered parseWebServiceResponse for shipment");
         shipmentResponse = shipmentResponse.replaceAll("<SOAP-ENV:", "<");
         shipmentResponse = shipmentResponse.replaceAll("<ns:", "<");
         shipmentResponse = shipmentResponse.replaceAll("</ns:", "</");
         shipmentResponse = shipmentResponse.replaceAll("</SOAP-ENV:", "</");
         shipmentResponse = shipmentResponse.replaceAll("<soapenv:", "<");
         shipmentResponse = shipmentResponse.replaceAll("<env:", "<");
         shipmentResponse = shipmentResponse.replaceAll("<v12:", "<");  
         shipmentResponse = shipmentResponse.replaceAll("</v12:", "</"); 
         shipmentResponse = shipmentResponse.replaceAll("</soapenv:", "</");
         shipmentResponse = shipmentResponse.replaceAll("xmlns\\s*=","xmlns:v12=");

         labelPath=cloudLabelPath;
         try {
             aascShipmentHeaderInfo = aascShipmentOrderInfo.getShipmentHeaderInfo();

             carrierId = aascShipmentHeaderInfo.getCarrierId();


             packageList = aascShipmentOrderInfo.getShipmentPackageInfo();
             numOfPackages = packageList.size();

             // Get an instance of the parser
             parser = new DOMParser();

             // create InputSource from string containing xml response
             reader = new StringReader(shipmentResponse);

             input = new InputSource(reader);

             // Set various parser options: validation off, warnings shown
             parser.setValidationMode(false);
             parser.showWarnings(true);

             // Parse the document.
             logger.info("before passing the xml document to the parser");
             parser.parse(input);
             logger.info("after passing the xml document to the parser");

             // Obtain the document.
             xmlDocument = parser.getDocument();

             // Print or extract document fields
             root = (XMLElement)xmlDocument.getDocumentElement();

             String nonDiscountedCost = "";
             // nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost());

             String severity = "";
             try {
                 severity = AascXmlParser.getValue(root, "Severity");
             } catch (Exception ex) {
                 logger.severe("error getting severity: " + ex.getMessage());
             }
             if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                 try {
                     trackingNumber = 
                             AascXmlParser.getValue(root, "TrackingNumber");
                 } catch (Exception e) {
                     trackingNumber = "";
                 }

             }

             if ((chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                 node = 
 root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/TrackingIds/TrackingNumber/text()");
                 try {
                     rtnTrackingNumber = nullStrToSpc(node.getNodeValue());
                 } catch (Exception e) {
                     rtnTrackingNumber = "";
                 }
             }
             String intFlag = 
                 nullStrToSpc(aascShipmentHeaderInfo.getInternationalFlag());

             if ((trackingNumber != null && !trackingNumber.equals("")) || 
                 (rtnTrackingNumber != null && !rtnTrackingNumber.equals(""))) {
                 if (intFlag.equalsIgnoreCase("Y")) {
                     parse123("");
                     aascShipmentHeaderInfo.setDocumentID(DocumentID);
                     Node labelNode = 
                         root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/Label/Parts/Image/text()");
                     label = nullStrToSpc(labelNode.getNodeValue());
                 } else {
                     node = 
 root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/PackageRating");
                     try {
                         NodeList PackageRatingList = node.getChildNodes();

                         for (int s = 0; s < PackageRatingList.getLength(); 
                              s++) {
                             Node childNode = PackageRatingList.item(s);
                             if (childNode.getNodeName().equalsIgnoreCase("PackageRateDetails")) {
                                 NodeList PackageRateDetailsList = 
                                     childNode.getChildNodes();

                                 String listOrAccount = "";
                                 for (int i = 0; 
                                      i < PackageRateDetailsList.getLength(); 
                                      i++) {

                                     Node childNodeLevel2 = 
                                         PackageRateDetailsList.item(i);
                                     if (childNodeLevel2.getLocalName().equalsIgnoreCase("RateType")) {
                                         Element firstNameElement = 
                                             (Element)PackageRateDetailsList.item(i);
                                         NodeList textFNList = 
                                             firstNameElement.getChildNodes();
                                         String nodeValue = 
                                             ((textFNList.item(0)).getNodeValue()).trim();
                                         listOrAccount = nodeValue;
                                     }

                                     // basecharge start
                                     if (childNodeLevel2.getLocalName().equalsIgnoreCase("BaseCharge")) {

                                         NodeList NetChargeNodeList = 
                                             childNodeLevel2.getChildNodes();
                                         for (int j = 0; 
                                              j < NetChargeNodeList.getLength(); 
                                              j++) {
                                             Node childNodeLevel3 = 
                                                 NetChargeNodeList.item(j);
                                             if (childNodeLevel3.getLocalName().equalsIgnoreCase("Amount")) {
                                                 Element firstNameElement = 
                                                     (Element)NetChargeNodeList.item(j);
                                                 NodeList textFNList = 
                                                     firstNameElement.getChildNodes();
                                                 String nodeValue = 
                                                     ((textFNList.item(0)).getNodeValue()).trim();
                                                 if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_PACKAGE")) {
                                                     baseCharge = nodeValue;
                                                 } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_PACKAGE")) {
                                                     listBaseCharge = nodeValue;
                                                 }
                                             }
                                         }
                                     }// basecharge end
                                     
                                     // TotalFreightDiscounts start
                                     if (childNodeLevel2.getLocalName().equalsIgnoreCase("TotalFreightDiscounts")) {

                                         NodeList NetChargeNodeList = 
                                             childNodeLevel2.getChildNodes();
                                         for (int j = 0; 
                                              j < NetChargeNodeList.getLength(); 
                                              j++) {
                                             Node childNodeLevel3 = 
                                                 NetChargeNodeList.item(j);
                                             if (childNodeLevel3.getLocalName().equalsIgnoreCase("Amount")) {
                                                 Element firstNameElement = 
                                                     (Element)NetChargeNodeList.item(j);
                                                 NodeList textFNList = 
                                                     firstNameElement.getChildNodes();
                                                 String nodeValue = 
                                                     ((textFNList.item(0)).getNodeValue()).trim();
                                                 if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_PACKAGE")) {
                                                     totalDiscount = nodeValue;
                                                 } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_PACKAGE")) {
                                                     totalListDiscount = 
                                                             nodeValue;
                                                 }
                                             }
                                         }
                                     }// TotalFreightDiscounts end

                                     // net charge start
                                     if (childNodeLevel2.getLocalName().equalsIgnoreCase("NetCharge")) {

                                         NodeList NetChargeNodeList = 
                                             childNodeLevel2.getChildNodes();
                                         for (int j = 0; 
                                              j < NetChargeNodeList.getLength(); 
                                              j++) {
                                             Node childNodeLevel3 = 
                                                 NetChargeNodeList.item(j);
                                             if (childNodeLevel3.getLocalName().equalsIgnoreCase("Amount")) {
                                                 Element firstNameElement = 
                                                     (Element)NetChargeNodeList.item(j);
                                                 NodeList textFNList = 
                                                     firstNameElement.getChildNodes();
                                                 String nodeValue = 
                                                     ((textFNList.item(0)).getNodeValue()).trim();
                                                 if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_PACKAGE")) {
                                                     totalShipmentCharges = 
                                                             nodeValue;
                                                 } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_PACKAGE")) {
                                                     totalShipmentListCharges = 
                                                             nodeValue;
                                                 }
                                             }
                                         }
                                     }
                                     // net charge end

                                     //  Dangerous Goods Shipping Charges for FDXE 
                                     if (childNodeLevel2.getLocalName().equalsIgnoreCase("Surcharges")) {
                                         String surchargesType = "";

                                         NodeList NetChargeNodeList = 
                                             childNodeLevel2.getChildNodes();
                                         for (int j = 0; 
                                              j < NetChargeNodeList.getLength(); 
                                              j++) {
                                             Node childNodeLevel3 = 
                                                 NetChargeNodeList.item(j);

                                             if (childNodeLevel3.getLocalName().equalsIgnoreCase("SurchargeType")) {
                                                 Element firstNameElement = 
                                                     (Element)NetChargeNodeList.item(j);
                                                 NodeList textFNList = 
                                                     firstNameElement.getChildNodes();
                                                 surchargesType = 
                                                         ((textFNList.item(0)).getNodeValue()).trim();
                                             }

                                             if (childNodeLevel3.getLocalName().equalsIgnoreCase("Amount")) {

                                                 NodeList AmountNodeList = 
                                                     childNodeLevel3.getChildNodes();
                                                 for (int j1 = 0; 
                                                      j1 < AmountNodeList.getLength(); 
                                                      j1++) {
                                                     Node childNodeLevel4 = 
                                                         AmountNodeList.item(j1);
                                                     if (childNodeLevel4.getLocalName().equalsIgnoreCase("Amount")) {
                                                         Element firstNameElement = 
                                                             (Element)AmountNodeList.item(j1);
                                                         NodeList textFNList = 
                                                             firstNameElement.getChildNodes();
                                                         String nodeValue = 
                                                             ((textFNList.item(0)).getNodeValue()).trim();
                                                         if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_PACKAGE")) {
                                                             if (surchargesType.equalsIgnoreCase("DANGEROUS_GOODS") || 
                                                                 surchargesType.equalsIgnoreCase("HAZARDOUS_MATERIAL")) {
                                                                 HazMatCharges = 
                                                                         nodeValue;
                                                             }
                                                         } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_PACKAGE")) {
                                                             if (surchargesType.equalsIgnoreCase("DANGEROUS_GOODS") || 
                                                                 surchargesType.equalsIgnoreCase("HAZARDOUS_MATERIAL")) {
                                                                 listHazMatCharges = 
                                                                         nodeValue;
                                                             }

                                                         }


                                                     }
                                                 }
                                             }
                                         }
                                     }

                                     // total surcharge start
                                     if (childNodeLevel2.getLocalName().equalsIgnoreCase("TotalSurcharges")) {

                                         NodeList NetChargeNodeList = 
                                             childNodeLevel2.getChildNodes();
                                         for (int j = 0; 
                                              j < NetChargeNodeList.getLength(); 
                                              j++) {
                                             Node childNodeLevel3 = 
                                                 NetChargeNodeList.item(j);
                                             if (childNodeLevel3.getLocalName().equalsIgnoreCase("Amount")) {
                                                 Element firstNameElement = 
                                                     (Element)NetChargeNodeList.item(j);
                                                 NodeList textFNList = 
                                                     firstNameElement.getChildNodes();
                                                 String nodeValue = 
                                                     ((textFNList.item(0)).getNodeValue()).trim();
                                                 if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_PACKAGE")) {
                                                     sCharges = nodeValue;
                                                 } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_PACKAGE")) {
                                                     sListCharges = nodeValue;
                                                 }
                                             }
                                         }
                                     }
                                     // total surcharge end
                                 }
                             }
                         }

                     } catch (Exception e) {
                         logger.severe("Exception::"+e.getMessage());
                     }
                     Node labelNode = 
                         root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/Label/Parts/Image/text()");
                     label = nullStrToSpc(labelNode.getNodeValue());

                 }


                 String trackingNumber1 = "";
                 if ((chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                     logger.info("chkReturnlabel is PRINTRETURNLABEL");
                     ListIterator pkgIterator = packageList.listIterator();
                     while (pkgIterator.hasNext()) {
                         aascShipPkgInfo = 
                                 (AascShipmentPackageInfo)pkgIterator.next();
	 
                         if (aascShipPkgInfo.getTrackingNumber().length() > 1) {
                            continue;
                          }     
                                               
                         if (aascShipPkgInfo.getPackageSequence().equalsIgnoreCase(pkgSequenceNum)) {
                             trackingNumber1 = 
                                     aascShipPkgInfo.getTrackingNumber();
                             trackingNumber1 = trackingNumber1 + "_ReturnLabel";
                         }
                     }
                 }

                 if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {

                     node = 
 root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/TrackingIds/TrackingNumber/text()");
                     wayBillNumber = nullStrToSpc(node.getNodeValue());
                     trackingNumber = wayBillNumber;
                 }

                 if (label != null && !label.equals("")) {

                     parsedLabel = AascBase64.decode(label);

                     if (labelPath != null && !labelPath.equals("")) {
                         try {
                             if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                                 fout = 
 new FileOutputStream(labelPath + trackingNumber + labelFormatExtension);
                                 fout.write(parsedLabel);
                                 fout.close();
                             } else {
                                 fout = 
 new FileOutputStream(labelPath + rtnTrackingNumber +"_ReturnLabel"+ labelFormatExtension);
                                 fout.write(parsedLabel);
                                 fout.close();
                             }
                         }
                         catch (Exception e) {
                             logger.severe("file not found or error in closing the file output stream" + 
                                           e.getMessage());
                         }
                     } // end of if(labelPath!=null && !labelPath.equals(""))
                     else {
                         logger.severe("labelPath is null");
                     } // end of else of if(labelPath!=null)
                 } // if(label!=null&&label!="")
                 else {
                     logger.severe("label is not found");
                 } // end of else of if(labelPath!=null && !labelPath.equals("")) 

                  //Hazmat Op900 - Parsing Hazmat Op900 label Tag
                   Node op900LabelNode = 
                       root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/PackageDocuments/Parts/Image/text()");
                  if (op900LabelNode != null && !op900LabelNode.equals("")) {
                      op900Label = nullStrToSpc(op900LabelNode.getNodeValue());
                      if (op900Label != null && !op900Label.equals("")) {
                          parsedOp900Label = AascBase64.decode(op900Label);
                          if (labelPath != null && !labelPath.equals("")) {
                              try {
                                      fout = new FileOutputStream(labelPath + trackingNumber + "_Op900");
                                      fout.write(parsedOp900Label);
                                      fout.close();
                              }
                              catch (Exception e) {
                                  logger.severe("file not found or error in closing the file output stream" + 
                                                e.getMessage());
                              }
                          } // end of if(labelPath!=null && !labelPath.equals(""))
                          else {
                              logger.severe("labelPath is null");
                          } // end of else of if(labelPath!=null)
                      } // if(op900Label!=null&&op900Label!="")
                      else {
                          logger.severe("Op900 Label is not found");
                      } // end of else
                  } // if(op900LabelNode!=null&&op900LabelNode!="")
                  else {
                      logger.severe("Op900 Label Node is not having value");
                  }
                  
                  
                  if ("Y".equalsIgnoreCase(intFlag)) {
                      logger.info("insdie commercial invoice generates");
                 try{
                      Node CINode = root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/ShipmentDocuments/Parts/Image/text()");
                      commercialInvoice = nullStrToSpc(CINode.getNodeValue());
                      
                      
                 }catch(Exception e){
                     logger.info("No commercial invoice is generated");
                     commercialInvoice="";
                 }
                      if (commercialInvoice != null && !commercialInvoice.equals("")) {
                          
                          parsedCI = AascBase64.decode(commercialInvoice);

                          if (labelPath != null && !labelPath.equals("")) {
                              String labelPathIntlDoc = labelPath + "intlDocs/";
                                          
                              String orderNumber = aascShipmentHeaderInfo.getOrderNumber();
                              java.util.Date shippedDate = aascShipmentHeaderInfo.getShipmentDate();
                              String shippedDateStr = shippedDate.toString().replaceAll("-", "_");
                                        
                              String fdxeCiIntlDoc = orderNumber + "_Shipment_" + shippedDateStr + "_FDXECI.pdf";

                              try {
                                      fout = new FileOutputStream(labelPathIntlDoc + fdxeCiIntlDoc);
                                      fout.write(parsedCI);
                                      fout.close();
                                  } 
                              catch (Exception e) {
                                  logger.severe("file not found or error in closing the file output stream" + e.getMessage());
                              }
                          }
                      }
                  }


                 node = 
 root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/TrackingIds/FormId/text()");
    String resCarrierCode =  root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/CarrierCode/text()").getNodeValue();  //Shiva added for issue 3860
                  
                  if (!resCarrierCode.equalsIgnoreCase("FDXG")) {
                     masterFormId = nullStrToSpc(node.getNodeValue());
                 }


                 try {
                     if (resCarrierCode.equalsIgnoreCase("FDXG")) {
                         node = 
    root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/OperationalDetail/TransitTime/text()");
                         transitTime = nullStrToSpc(node.getNodeValue());
    
                         HashMap<String, Integer> hm = 
                             new HashMap<String, Integer>();
    
                         hm.put("ONE", 1);
                         hm.put("TWO", 2);
                         hm.put("THREE", 3);
                         hm.put("FOUR", 4);
                         hm.put("FIVE", 5);
                         hm.put("SIX", 6);
                         hm.put("SEVEN", 7);
                         hm.put("EIGHT", 8);
                         hm.put("NINE", 9);
                         hm.put("TEN", 10);
                         hm.put("ELEVEN", 11);
                         hm.put("TWELVE", 12);
                         hm.put("THIRTEEN", 13);
                         hm.put("FOURTEEN", 14);
                         hm.put("FIFTTEEN", 15);
        
                         String numberTransitTime = 
                             transitTime.substring(0, transitTime.lastIndexOf('_'));
                         logger.info("Only Transit time in words-----" + numberTransitTime);
    
                         int numberTransitTime1 = 0;
                         if (hm.containsKey(numberTransitTime) == true) {                    
                             numberTransitTime1 = hm.get(numberTransitTime);
                         }
                         
                         Date shippedDate = aascShipmentHeaderInfo.getShipmentDate();
    
                         Calendar cal = new GregorianCalendar();
                         try {
                             for (int i = 1; i <= numberTransitTime1; i++) {
                                
                                 cal.setTime(shippedDate);
                                 for (int promise_days = i; promise_days != 0; 
                                      promise_days--) {
                                     cal.add(Calendar.DATE, 1);
                                  
                                     if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
                                         cal.add(Calendar.DATE, 2);
                                     }
    
                                     // uncomment the below code if saturday shipping is allowed. 
                                     /*
                                            if (cal.get(Calendar.DAY_OF_WEEK)==1) {
                                                       cal.add(Calendar.DATE, 1);
                                                     } */
    
                                 }
                                 java.util.Date utilDate = cal.getTime();
                                 deliveryDate = new Date(utilDate.getTime());
                             }
                         } catch (Exception e) {
                             logger.severe("Exception message:" + e);
                         }
                     } else {
                         node = 
    root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/OperationalDetail/DeliveryDate/text()");
                         String deliveryDateStr = node.getNodeValue();
                         DateFormat formatter = 
                             new SimpleDateFormat("yyyy-MM-dd");
                         java.util.Date date_temp = 
                             formatter.parse(deliveryDateStr);
                         deliveryDate = new Date(date_temp.getTime());
                     }
                 } catch (Exception e) {
                     logger.severe("Exception message:" + e);
                 }
                 //End
                      
                 ListIterator packageListIterator = packageList.listIterator();

                 packageCnt = packageList.size();

                 while (packageListIterator.hasNext()) {
                     aascShipmentPackageInfo = 
                             (AascShipmentPackageInfo)packageListIterator.next();
                     if (aascShipmentPackageInfo.getTrackingNumber().length() > 1 )//&& (chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL") && aascPackageInfo.getRtnTrackingNumber().length()>1) ) {
                     {                     
                         if(!chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))
                        {
                        continue;
                        }
                                              
                   }    
                     if (aascShipmentPackageInfo.getPackageSequence().equals("1") && 
                         pkgSequenceNum.equals("1")) {

                         if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                             aascShipmentHeaderInfo.setWayBill(trackingNumber);
                             aascShipmentPackageInfo.setTrackingNumber(trackingNumber);
                             aascShipmentPackageInfo.setMasterTrackingNumber(trackingNumber);
                             
                             
                         }
                         else {
                             aascShipmentPackageInfo.setRtnTrackingNumber(rtnTrackingNumber);
                             
                         }
                         
                         aascShipmentPackageInfo.setMasterFormID(masterFormId);
                         aascShipmentHeaderInfo.setMasterFormId(masterFormId);
                         

                         codTrackingNum = 
                                 AascXmlParser.getValue(root, "CodReturnTrackingNumber");
                         aascShipmentPackageInfo.setCodTrackingNum(codTrackingNum);
                         aascShipmentHeaderInfo.setCodTrackingNumber(codTrackingNum);


                         if (!totalShipmentListCharges.equals("") && 
                             totalShipmentListCharges != null) {
                             if(!chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))
                             {                             
                             aascShipmentPackageInfo.setPkgListNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));
                             logger.info("aascShipmentPackageInfo.getPkgListNetCharge==" + 
                                         aascShipmentPackageInfo.getPkgListNetCharge());
                         }
                         }
                         if (!totalShipmentCharges.equals("") && 
                             totalShipmentCharges != null) {
                             if(!chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))
                             {
                             aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentCharges)));
                             logger.info("aascShipmentPackageInfo.getPkgDiscountNetCharge==" + 
                                         aascShipmentPackageInfo.getPkgDiscountNetCharge());
                         }
                         }
                         if (nullStrToSpc(aascProfileOptionsInfo.getAcctNumNegotiatedFlag()).equalsIgnoreCase("Y")) { // added on 8/4/10
                             if (nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost()).equalsIgnoreCase("Y")) // Discounted Rates are considered
                             {
                                 logger.info("Discounted rates are considered");


                                 if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                                 {
                                     logger.info("Setting values For Non Return shipment");
                                     if (!totalShipmentCharges.equals("") && 
                                         totalShipmentCharges != null) {
                                         aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentCharges)));
                                     }
                                     if (!baseCharge.equals("") && 
                                         baseCharge != null) {
                                         aascShipmentPackageInfo.setBaseCharge(Double.parseDouble(escape(baseCharge)));
                                     }
                                     if (!totalDiscount.equals("") && 
                                         totalDiscount != null) {
                                         aascShipmentPackageInfo.setTotalDiscount(Double.parseDouble(escape(totalDiscount)));
                                     }
                                     if (!HazMatCharges.equals("") && 
                                         HazMatCharges != null) {
                                         aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(escape(HazMatCharges)));
                                     }
                                 } //end of if for Non Return 
                                 else //For Return shipment
                                 {
                                     logger.info("Setting values For Return shipment");
                                     if (!totalShipmentCharges.equals("") && 
                                         totalShipmentCharges != null) {
                                         aascShipmentPackageInfo.setRtnShipmentCost(Double.parseDouble(escape(totalShipmentCharges)));

                                     }

                                     if (!baseCharge.equals("") && 
                                         baseCharge != null) {
                                         aascShipmentPackageInfo.setRtnBaseChrge(Double.parseDouble(escape(baseCharge)));
                                     }
                                     if (!totalDiscount.equals("") && 
                                         totalDiscount != null) {
                                         aascShipmentPackageInfo.setRtnTotalDiscount(Double.parseDouble(escape(totalDiscount)));
                                     }
                                     if (!HazMatCharges.equals("") && 
                                         HazMatCharges != null) {
                                         aascShipmentPackageInfo.setRtnHazMatCharges(Double.parseDouble(escape(HazMatCharges)));
                                     }
                                 } //end of else for Return Shipment
                             } else { // List Published Rates are considered       
                                 logger.info("Published rates are considered");
                                 if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                                 {
                                     logger.info("Setting values For Non Return shipment");

                                     if (!totalShipmentListCharges.equals("") && 
                                         totalShipmentListCharges != null) {
                                         aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                         aascShipmentPackageInfo.setPkgListNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));
                                     }

                                     if (!listBaseCharge.equals("") && 
                                         listBaseCharge != null) {
                                         aascShipmentPackageInfo.setBaseCharge(Double.parseDouble(escape(listBaseCharge)));
                                     }
                                     if (!totalListDiscount.equals("") && 
                                         totalListDiscount != null) {
                                         aascShipmentPackageInfo.setTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                     }
                                     if (!listHazMatCharges.equals("") && 
                                         listHazMatCharges != null) {
                                         aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                     }
                                 } //end of if for Non Return
                                 else //For Return shipment
                                 {
                                     logger.info("Setting values For Return shipment");
                                     if (!totalShipmentListCharges.equals("") && 
                                         totalShipmentListCharges != null) {
                                         aascShipmentPackageInfo.setRtnShipmentCost(Double.parseDouble(escape(totalShipmentListCharges)));

                                     }

                                     if (!listBaseCharge.equals("") && 
                                         listBaseCharge != null) {
                                         aascShipmentPackageInfo.setRtnBaseChrge(Double.parseDouble(escape(listBaseCharge)));
                                     }
                                     if (!totalListDiscount.equals("") && 
                                         totalListDiscount != null) {
                                         aascShipmentPackageInfo.setRtnTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                     }
                                     if (!listHazMatCharges.equals("") && 
                                         listHazMatCharges != null) {
                                         aascShipmentPackageInfo.setRtnHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                     }
                                 } //end of else for Return Shipment
                             }
                         } else { 

                             logger.info("A is False and ND is False");

                             // List Published Rates are considered       
                             logger.info("Published rates are considered");

                             if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                             {
                                 logger.info("Setting values For Non Return shipment");

                                 if (!totalShipmentListCharges.equals("") && 
                                     totalShipmentListCharges != null) {
                                     aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                     aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));

                                 }


                                 if (!listBaseCharge.equals("") && 
                                     listBaseCharge != null) {
                                     aascShipmentPackageInfo.setBaseCharge(Double.parseDouble(escape(listBaseCharge)));
                                 }
                                 if (!totalListDiscount.equals("") && 
                                     totalListDiscount != null) {
                                     aascShipmentPackageInfo.setTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                 }
                                 if (!listHazMatCharges.equals("") && 
                                     listHazMatCharges != null) {
                                     aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                 }
                             } //end of if for Non Return
                             else //For Return shipment
                             {
                                 logger.info("Setting values For Return shipment");
                                 if (!totalShipmentListCharges.equals("") && 
                                     totalShipmentListCharges != null) {
                                     aascShipmentPackageInfo.setRtnShipmentCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                 }

                                 if (!listBaseCharge.equals("") && 
                                     listBaseCharge != null) {
                                     aascShipmentPackageInfo.setRtnBaseChrge(Double.parseDouble(escape(listBaseCharge)));
                                 }
                                 if (!totalListDiscount.equals("") && 
                                     totalListDiscount != null) {
                                     aascShipmentPackageInfo.setRtnTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                 }
                                 if (!listHazMatCharges.equals("") && 
                                     listHazMatCharges != null) {
                                     aascShipmentPackageInfo.setRtnHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                 }
                             } //end of else for Return Shipment
                         }


                         try {
                             surCharges = Double.parseDouble(sCharges);
                         } catch (Exception e) {
                             surCharges = 0.0;
                         }
                         try {
                             listsurCharges = Double.parseDouble(sListCharges);
                         } catch (Exception e) {
                             listsurCharges = 0.0;
                         }
                         if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                         {
                         if (nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost()).equalsIgnoreCase("Y")) // Discounted Rates are considered
                         {
                             aascShipmentPackageInfo.setSurCharges(surCharges);
                         } else {
                             aascShipmentPackageInfo.setSurCharges(listsurCharges);
                         }
                         }

                         
                         hashMap.put("masterTrkNum", trackingNumber);
                         hashMap.put("masterFormId", masterFormId);
                         hashMap.put("codTrackingNum", codTrackingNum);

                     } else {
                         if (aascShipmentPackageInfo.getPackageSequence().equals("1")) {
                             masterTrackingNumber = 
                                     aascShipmentPackageInfo.getTrackingNumber();

                             codTrackingNum = 
                                     aascShipmentPackageInfo.getCodTrackingNum();

                             hashMap.put("masterTrkNum", 
                                         aascShipmentPackageInfo.getTrackingNumber());
                             hashMap.put("masterFormId", 
                                         aascShipmentPackageInfo.getMasterFormID());

                             hashMap.put("codTrackingNum", codTrackingNum);


                         }


                         if (aascShipmentPackageInfo.getPackageSequence().equals(pkgSequenceNum) && 
                             !pkgSequenceNum.equals("1")) {

                             if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {

                                 aascShipmentPackageInfo.setMasterTrackingNumber(masterTrackingNumber);
                                 aascShipmentPackageInfo.setMasterFormID(masterFormId);
                                 aascShipmentPackageInfo.setTrackingNumber(trackingNumber);
                                 
                                 

                             }
                             else {

                                 aascShipmentPackageInfo.setRtnTrackingNumber(rtnTrackingNumber);
                                 
                                 
                             }
                             

                             aascShipmentPackageInfo.setCodTrackingNum(codTrackingNum);

                             if (!totalShipmentListCharges.equals("") && 
                                 totalShipmentListCharges != null) {
                                 if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                                 {
                                 aascShipmentPackageInfo.setPkgListNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));
                                 
                             }
                             }

                             if (!totalShipmentCharges.equals("") && 
                                 totalShipmentCharges != null) {
                                 if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                                 {
                                 aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentCharges)));
                                 
                             }
                             }

                             if (nullStrToSpc(aascProfileOptionsInfo.getAcctNumNegotiatedFlag()).equalsIgnoreCase("Y")) { //Added 8/0/10
                                 if (nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost()).equalsIgnoreCase("Y")) // Discounted Rates are considered
                                 {
                                     logger.info("Discounted rates are considered");
                                     if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                                     {
                                         logger.info("Setting values For Non Return shipment");
                                         if (!totalShipmentCharges.equals("") && 
                                             totalShipmentCharges != null) {
                                             aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentCharges)));

                                         }

                                         if (!baseCharge.equals("") && 
                                             baseCharge != null) {
                                             aascShipmentPackageInfo.setBaseCharge(Double.parseDouble(escape(baseCharge)));
                                         }
                                         if (!totalDiscount.equals("") && 
                                             totalDiscount != null) {
                                             aascShipmentPackageInfo.setTotalDiscount(Double.parseDouble(escape(totalDiscount)));
                                         }
                                         if (!HazMatCharges.equals("") && 
                                             HazMatCharges != null) {
                                             aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(escape(HazMatCharges)));
                                         }
                                     } //end of if for Non Return 
                                     else //For Return shipment
                                     {
                                         logger.info("Setting values For Return shipment");
                                         if (!totalShipmentCharges.equals("") && 
                                             totalShipmentCharges != null) {
                                             aascShipmentPackageInfo.setRtnShipmentCost(Double.parseDouble(escape(totalShipmentCharges)));
//                                             aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentCharges)));
//                                             aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentCharges)));
                                         }
                                         if (!baseCharge.equals("") && 
                                             baseCharge != null) {
                                             aascShipmentPackageInfo.setRtnBaseChrge(Double.parseDouble(escape(baseCharge)));
                                         }
                                         if (!totalDiscount.equals("") && 
                                             totalDiscount != null) {
                                             aascShipmentPackageInfo.setRtnTotalDiscount(Double.parseDouble(escape(totalDiscount)));
                                         }
                                         if (!HazMatCharges.equals("") && 
                                             HazMatCharges != null) {
                                             aascShipmentPackageInfo.setRtnHazMatCharges(Double.parseDouble(escape(HazMatCharges)));
                                         }
                                     } //end of else for Return Shipment
                                 } else { // List Published Rates are considered  
                                     logger.info("Published rates are considered");
                                     if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                                     {
                                         logger.info("Setting values For Non Return shipment");
                                         if (!totalShipmentListCharges.equals("") && 
                                             totalShipmentListCharges != null) {
                                             aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));

                                         }

                                         if (!listBaseCharge.equals("") && 
                                             listBaseCharge != null) {
                                             aascShipmentPackageInfo.setBaseCharge(Double.parseDouble(escape(listBaseCharge)));
                                         }
                                         if (!totalListDiscount.equals("") && 
                                             totalListDiscount != null) {
                                             aascShipmentPackageInfo.setTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                         }
                                         if (!listHazMatCharges.equals("") && 
                                             listHazMatCharges != null) {
                                             aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                         }
                                     } //end of if for Non Return
                                     else //For Return shipment
                                     {
                                         logger.info("Setting values For Return shipment");
                                         if (!totalShipmentListCharges.equals("") && 
                                             totalShipmentListCharges != null) {
                                             aascShipmentPackageInfo.setRtnShipmentCost(Double.parseDouble(escape(totalShipmentListCharges)));
//                                             aascShipmentPackageInfo.setPkgListNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));
//                                             aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                         }
                                         if (!listBaseCharge.equals("") && 
                                             listBaseCharge != null) {
                                             aascShipmentPackageInfo.setRtnBaseChrge(Double.parseDouble(escape(listBaseCharge)));
                                         }
                                         if (!totalListDiscount.equals("") && 
                                             totalListDiscount != null) {
                                             aascShipmentPackageInfo.setRtnTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                         }
                                         if (!listHazMatCharges.equals("") && 
                                             listHazMatCharges != null) {
                                             aascShipmentPackageInfo.setRtnHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                         }
                                     } //end of else for Return Shipment

                                 }

                                 
                             } else { 

                                 // List Published Rates are considered       
                                 logger.info("Published rates are considered");

                                 if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                                 {
                                     logger.info("Setting values For Non Return shipment");

                                     if (!totalShipmentListCharges.equals("") && 
                                         totalShipmentListCharges != null) {
                                         aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                         aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));

                                     }


                                     if (!listBaseCharge.equals("") && 
                                         listBaseCharge != null) {
                                         aascShipmentPackageInfo.setBaseCharge(Double.parseDouble(escape(listBaseCharge)));
                                     }
                                     if (!totalListDiscount.equals("") && 
                                         totalListDiscount != null) {
                                         aascShipmentPackageInfo.setTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                     }
                                     if (!listHazMatCharges.equals("") && 
                                         listHazMatCharges != null) {
                                         aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                     }
                                 } //end of if for Non Return
                                 else //For Return shipment
                                 {
                                     logger.info("Setting values For Return shipment");
                                     if (!totalShipmentListCharges.equals("") && 
                                         totalShipmentListCharges != null) {
                                         aascShipmentPackageInfo.setRtnShipmentCost(Double.parseDouble(escape(totalShipmentListCharges)));
//                                         aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));
//                                         aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));

                                     }

                                     if (!listBaseCharge.equals("") && 
                                         listBaseCharge != null) {
                                         aascShipmentPackageInfo.setRtnBaseChrge(Double.parseDouble(escape(listBaseCharge)));
                                     }
                                     if (!totalListDiscount.equals("") && 
                                         totalListDiscount != null) {
                                         aascShipmentPackageInfo.setRtnTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                     }
                                     if (!listHazMatCharges.equals("") && 
                                         listHazMatCharges != null) {
                                         aascShipmentPackageInfo.setRtnHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                     }
                                 } //end of else for Return Shipment
                             }
                             
                             if (!sCharges.equals("") && sCharges != null) {
                                 surCharges = Double.parseDouble(sCharges);
                             }
                             try {
                                 listsurCharges = 
                                         Double.parseDouble(sListCharges);
                             } catch (Exception e) {
                                 listsurCharges = 0.0;
                             }
                             if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL")))
                             {
                             if (nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost()).equalsIgnoreCase("Y")) // Discounted Rates are considered
                             {
                                 aascShipmentPackageInfo.setSurCharges(surCharges);
                             } else {
                                 aascShipmentPackageInfo.setSurCharges(listsurCharges);
                             }
                            }

                         }
                     }
                     aascShipmentPackageInfo.setTransitTime(transitTime);
                     aascShipmentPackageInfo.setDeliveryDate(deliveryDate);
              
                 }


                 if (resCarrierCode.equalsIgnoreCase("FDXG") && 
                     (packageCnt == Integer.parseInt(pkgSequenceNum))) {
                     if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                         if (nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost()).equalsIgnoreCase("Y")) // Discounted Rates are considered
                         {

                             if (packageCnt == 1) {
                                 node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/DiscountedCharges/NetCharge/text()");
                             } else {
                                 node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/DiscountedCharges/ShipmentNetCharge/text()");
                             }
                             String testNode = "";
                             try {
                                 if (node.getNodeValue() != null && 
                                     !(node.getNodeValue()).equals("")) {
                                     testNode = "true";
                                 }
                             } catch (Exception e) {
                                 testNode = "";
                             }
                             if (!testNode.equalsIgnoreCase("")) {
                                 

                             }
                         } else {
                             try {
                                 if (packageCnt == 1) {
                                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/ListCharges/NetCharge/text()");
                                 } else {
                                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/ListCharges/ShipmentNetCharge/text()");
                                 }
                                 String testNode = "";
                                 try {
                                     if (node.getNodeValue() != null && 
                                         !(node.getNodeValue()).equals("")) {
                                         testNode = "true";
                                     }
                                 } catch (Exception e) {
                                     testNode = "";
                                 }

                                 logger.info("***totalDiscount***:" + 
                                             totalDiscount);

                             } catch (Exception e) {
                                 logger.info("/FDXShipReply/EstimatedCharges/ListCharges/ShipmentNetCharge/text() tag is missing" + 
                                             e.getMessage());
                                 totalDiscount = "0.00";
                             }
                         }
                     }
                 }

                 logger.info("after getting tracking numbers and setting it to package information bean");
                
                  
                  int pkgSequenceNumInt=Integer.parseInt(pkgSequenceNum);  
                  
                 // set display message    // Srisha added code for displaying warning message from Fedex response bug #3461
                 node = root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/Notifications/Severity/text()");
                 displayMessage = nullStrToSpc(node.getNodeValue());
                 logger.info("displayMessage"+displayMessage);
                 
                 if("WARNING".equalsIgnoreCase(displayMessage)){
                     node = root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/Notifications/Message/text()");
                     String warningMsg = nullStrToSpc(node.getNodeValue());
                     logger.info("Warning Msg "+warningMsg);
                     hashMap.put("warningMsg", warningMsg);
                 }
                  //Code ends here
//                 displayMessage = "success";  
                 hashMap.put("status", displayMessage);

             } else { // ship failed       
          // Srisha modified code for displaying warning message from Fedex response bug #3461   
              node = 
              root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/Notifications/Code/text()");
                 errorCode = nullStrToSpc(node.getNodeValue());
//                 errorCode = AascXmlParser.getValue(root, "Code");
                 if (errorCode != null && !("".equals(errorCode))) {
                     node = 
                     root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/Notifications/Message/text()");
                     errorDescription = nullStrToSpc(node.getNodeValue());
//                     errorDescription = AascXmlParser.getValue(root, "Message");
                     displayMessage = 
                             errorDescription + "(errorCode:" + errorCode + ")";

                     softErrorType = AascXmlParser.getValue(root, "Type");
                     if (!softErrorType.equals("") && softErrorType != null) {
                         logger.severe("soft error type:" + softErrorType);
                     }
                 }

                 logger.severe("errorDescription:" + displayMessage);
                 hashMap.put("status", displayMessage);
             } // else
         } // end of try
         catch (Exception exp) {
             displayMessage = "Error in parsing or setting the values to bean!";
             logger.severe("Exception::"+exp.getMessage());
         }
         logger.info("Exit from parseWebServiceResponse()");
         return hashMap;

     } // end of method

     HashMap parseResponse(String shipmentResponse, 
                           AascShipmentOrderInfo aascShipmentOrderInfo, 
                           AascShipMethodInfo aascShipMethodInfo, 
                           AascProfileOptionsBean aascProfileOptionsInfo, 
                           String pkgSequenceNum,String chkReturnlabel,String cloudLabelPath) {
         logger.info("Entered parseResponse for Shipment");
         
          labelPath=cloudLabelPath;
   
         try {
             aascShipmentHeaderInfo = aascShipmentOrderInfo.getShipmentHeaderInfo();

             carrierId = aascShipmentHeaderInfo.getCarrierId();
             

             packageList = aascShipmentOrderInfo.getShipmentPackageInfo();
             numOfPackages = packageList.size();

             // Get an instance of the parser
             parser = new DOMParser();

             // create InputSource from string containing xml response
             reader = new StringReader(shipmentResponse);

             input = new InputSource(reader);

             // Set various parser options: validation off, warnings shown
             parser.setValidationMode(false);
             parser.showWarnings(true);

             // Parse the document.
             parser.parse(input);

             // Obtain the document.
             xmlDocument = parser.getDocument();

             // Print or extract document fields
             root = (XMLElement)xmlDocument.getDocumentElement();

             String nonDiscountedCost = 
                 nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost());


             if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                 trackingNumber = 
                         AascXmlParser.getValue(root, "TrackingNumber");
             }

             if ((chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                 rtnTrackingNumber = 
                         AascXmlParser.getValue(root, "TrackingNumber");
             }

             if ((trackingNumber != null && !trackingNumber.equals("")) || 
                 (rtnTrackingNumber != null && !rtnTrackingNumber.equals(""))) {

                 try {
                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/DiscountedCharges/NetCharge/text()");
                     totalShipmentCharges = nullStrToSpc(node.getNodeValue());
                     

                 } catch (Exception e) { 
                     logger.info("/FDXShipReply/EstimatedCharges/DiscountedCharges tag is missing" + 
                                 e.getMessage());
                     totalShipmentCharges = "0.00";
                 }
                 try {

                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/ListCharges/NetCharge/text()");
                     totalShipmentListCharges = 
                             nullStrToSpc(node.getNodeValue());
                     

                 } catch (Exception e) {
                     logger.info("/FDXShipReply/EstimatedCharges/ListCharges tag is missing" + 
                                 e.getMessage());

                     totalShipmentListCharges = "0.00";
                 }


                 try {
                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/DiscountedCharges/BaseCharge/text()");
                     baseCharge = nullStrToSpc(node.getNodeValue());
                     
                 } catch (Exception e) {
                     logger.info("/FDXShipReply/EstimatedCharges/DiscountedCharges/BaseCharge/text() tag is missing" + 
                                 e.getMessage());
                     baseCharge = "0.00";
                 }
                 try {
                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/ListCharges/BaseCharge/text()");
                     listBaseCharge = nullStrToSpc(node.getNodeValue());
                     

                 } catch (Exception e) {

                     logger.info("/FDXShipReply/EstimatedCharges/ListCharges/BaseCharge/text() tag is missing" + 
                                 e.getMessage());
                     listBaseCharge = "0.00";
                 }

                 try {
                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/DiscountedCharges/TotalDiscount/text()");
                     totalDiscount = nullStrToSpc(node.getNodeValue());
                     
                 } catch (Exception e) {
                     logger.info("/FDXShipReply/EstimatedCharges/DiscountedCharges/TotalDiscount/text() tag is missing" + 
                                 e.getMessage());
                     totalDiscount = "0.00";
                 }
                 try {
                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/ListCharges/TotalDiscount/text()");
                     totalListDiscount = nullStrToSpc(node.getNodeValue());
                     

                 } catch (Exception e) {
                     logger.info("/FDXShipReply/EstimatedCharges/ListCharges/TotalDiscount/text() tag is missing" + 
                                 e.getMessage());
                     totalListDiscount = "0.00";
                 }

                 
                 try {
                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/DiscountedCharges/Surcharges/DangerousGoods/text()");
                     HazMatCharges = nullStrToSpc(node.getNodeValue());
                     
                 } catch (Exception e) {
                     logger.info("/FDXShipReply/EstimatedCharges/DiscountedCharges/Surcharges/DangerousGoods/text() tag is missing" + 
                                 e.getMessage());
                     HazMatCharges = "0.00";

                 }
                 try {
                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/ListCharges/Surcharges/DangerousGoods/text()");
                     listHazMatCharges = nullStrToSpc(node.getNodeValue());
                     
                 } catch (Exception e) {
                     logger.info("/FDXShipReply/EstimatedCharges/ListCharges/Surcharges/DangerousGoods/text() tag is missing" + 
                                 e.getMessage());
                     listHazMatCharges = "0.00";

                 }

                 if (HazMatCharges.equalsIgnoreCase("") || 
                     HazMatCharges == null || HazMatCharges == "0.00") {
                     try {
                         node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/DiscountedCharges/Surcharges/HazardousMaterials/text()");
                         HazMatCharges = nullStrToSpc(node.getNodeValue());
                         

                     } catch (Exception e) {
                         logger.info("/FDXShipReply/EstimatedCharges/DiscountedCharges/Surcharges/HazardousMaterials/text() tag is missing" + 
                                     e.getMessage());
                         HazMatCharges = "0.00";

                     }
                 }
                 if (listHazMatCharges.equalsIgnoreCase("") || 
                     listHazMatCharges == null || listHazMatCharges == "0.00") {
                     try {
                         node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/ListCharges/Surcharges/HazardousMaterials/text()");
                         listHazMatCharges = nullStrToSpc(node.getNodeValue());
                         

                     } catch (Exception e) {
                         logger.info("/FDXShipReply/EstimatedCharges/ListCharges/Surcharges/HazardousMaterials/text() tag is missing" + 
                                     e.getMessage());
                         listHazMatCharges = "0.00";

                     }
                 }


                 String sCharges = "";
                 String sListCharges = "";
                 try {
                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/DiscountedCharges/TotalSurcharge/text()");
                     sCharges = nullStrToSpc(node.getNodeValue());
                     

                 } catch (Exception e) {
                     logger.info("/FDXShipReply/EstimatedCharges/DiscountedCharges/TotalSurcharge/text() tag is missing" + 
                                 e.getMessage());
                     sCharges = "0.00";
                 }
                 try {
                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/ListCharges/TotalSurcharge/text()");
                     sListCharges = nullStrToSpc(node.getNodeValue());
                     

                 } catch (Exception e) {
                     logger.info("/FDXShipReply/EstimatedCharges/ListCharges/TotalSurcharge/text() tag is missing" + 
                                 e.getMessage());
                     sListCharges = "0.00";
                 }

                 label = AascXmlParser.getValue(root, "OutboundLabel");
                 String trackingNumber1 = "";
                 if ((chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                     logger.info("chkReturnlabel is PRINTRETURNLABEL");
                     ListIterator pkgIterator = packageList.listIterator();
                     while (pkgIterator.hasNext()) {
                         aascShipPkgInfo = 
                                 (AascShipmentPackageInfo)pkgIterator.next();
                         if (aascShipPkgInfo.getPackageSequence().equalsIgnoreCase(pkgSequenceNum)) {
                             trackingNumber1 = 
                                     aascShipPkgInfo.getTrackingNumber();
                             trackingNumber1 = trackingNumber1 + "_ReturnLabel";
                         }
                     }
                 }
                 if (label != null && !label.equals("")) {

                     parsedLabel = AascBase64.decode(label);

                     if (labelPath != null && !labelPath.equals("")) {
                         try {
                             if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                                 fout = 
 new FileOutputStream(labelPath + trackingNumber + labelFormatExtension);
                                 fout.write(parsedLabel);
                                 fout.close();
                             } else {
                                 fout = 
 new FileOutputStream(labelPath + trackingNumber1 + labelFormatExtension);
                                 fout.write(parsedLabel);
                                 fout.close();
                             }
                         }
                         catch (Exception e) {
                             logger.severe("file not found or error in closing the file output stream" + 
                                           e.getMessage());
                         }
                     } // end of if(labelPath!=null && !labelPath.equals(""))
                     else {
                         logger.severe("labelPath is null");
                     } // end of else of if(labelPath!=null)
                 } // if(label!=null&&label!="")
                 else {
                     logger.severe("label is not found");
                 } // end of else of if(labelPath!=null && !labelPath.equals("")) 
                 if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                     node = 
 root.selectSingleNode("/FDXShipReply/Tracking/TrackingNumber/text()");
                     wayBillNumber = nullStrToSpc(node.getNodeValue());

                     trackingNumber = wayBillNumber;
                 }
                 masterFormId = 
                         nullStrToSpc(AascXmlParser.getValue(root, "FormID"));

                 ListIterator packageListIterator = packageList.listIterator();

                 packageCnt = packageList.size();

                 while (packageListIterator.hasNext()) {
                     aascShipmentPackageInfo = 
                             (AascShipmentPackageInfo)packageListIterator.next();
                     if (aascShipmentPackageInfo.getPackageSequence().equals("1") && 
                         pkgSequenceNum.equals("1")) {

                         if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                             aascShipmentHeaderInfo.setWayBill(trackingNumber);
                             aascShipmentPackageInfo.setTrackingNumber(trackingNumber);
                             aascShipmentPackageInfo.setMasterTrackingNumber(trackingNumber);
                         }
                         else {
                             aascShipmentPackageInfo.setRtnTrackingNumber(rtnTrackingNumber);
                         }
                         aascShipmentPackageInfo.setMasterFormID(masterFormId);
                         aascShipmentHeaderInfo.setMasterFormId(masterFormId);

                         codTrackingNum = 
                                 AascXmlParser.getValue(root, "CodReturnTrackingNumber");
                         aascShipmentPackageInfo.setCodTrackingNum(codTrackingNum);
                         aascShipmentHeaderInfo.setCodTrackingNumber(codTrackingNum);


                         if (!totalShipmentListCharges.equals("") && 
                             totalShipmentListCharges != null) {
                             aascShipmentPackageInfo.setPkgListNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));
                             
                         }

                         if (!totalShipmentCharges.equals("") && 
                             totalShipmentCharges != null) {
                             aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentCharges)));
                             
                         }
                         if (nullStrToSpc(aascProfileOptionsInfo.getAcctNumNegotiatedFlag()).equalsIgnoreCase("Y")) { // added on 8/4/10
                             if (nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost()).equalsIgnoreCase("Y")) // Discounted Rates are considered
                             {


                                 if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                                 {
                                     if (!totalShipmentCharges.equals("") && 
                                         totalShipmentCharges != null) {
                                         aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentCharges)));
                                     }
                                     if (!baseCharge.equals("") && 
                                         baseCharge != null) {
                                         aascShipmentPackageInfo.setBaseCharge(Double.parseDouble(escape(baseCharge)));
                                     }
                                     if (!totalDiscount.equals("") && 
                                         totalDiscount != null) {
                                         aascShipmentPackageInfo.setTotalDiscount(Double.parseDouble(escape(totalDiscount)));
                                     }
                                     if (!HazMatCharges.equals("") && 
                                         HazMatCharges != null) {
                                         aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(escape(HazMatCharges)));
                                     }
                                 } //end of if for Non Return 
                                 else //For Return shipment
                                 {
                                     if (!totalShipmentCharges.equals("") && 
                                         totalShipmentCharges != null) {
                                         aascShipmentPackageInfo.setRtnShipmentCost(Double.parseDouble(escape(totalShipmentCharges)));
                                         aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentCharges)));
                                         aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentCharges)));

                                     }

                                     if (!baseCharge.equals("") && 
                                         baseCharge != null) {
                                         aascShipmentPackageInfo.setRtnBaseChrge(Double.parseDouble(escape(baseCharge)));
                                     }
                                     if (!totalDiscount.equals("") && 
                                         totalDiscount != null) {
                                         aascShipmentPackageInfo.setRtnTotalDiscount(Double.parseDouble(escape(totalDiscount)));
                                     }
                                     if (!HazMatCharges.equals("") && 
                                         HazMatCharges != null) {
                                         aascShipmentPackageInfo.setRtnHazMatCharges(Double.parseDouble(escape(HazMatCharges)));
                                     }
                                 } //end of else for Return Shipment
                             } else { // List Published Rates are considered       
                                 if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                                 {

                                     if (!totalShipmentListCharges.equals("") && 
                                         totalShipmentListCharges != null) {
                                         aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                         aascShipmentPackageInfo.setPkgListNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));
                                     }

                                     if (!listBaseCharge.equals("") && 
                                         listBaseCharge != null) {
                                         aascShipmentPackageInfo.setBaseCharge(Double.parseDouble(escape(listBaseCharge)));
                                     }
                                     if (!totalListDiscount.equals("") && 
                                         totalListDiscount != null) {
                                         aascShipmentPackageInfo.setTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                     }
                                     if (!listHazMatCharges.equals("") && 
                                         listHazMatCharges != null) {
                                         aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                     }
                                 } //end of if for Non Return
                                 else //For Return shipment
                                 {
                                     if (!totalShipmentListCharges.equals("") && 
                                         totalShipmentListCharges != null) {
                                         aascShipmentPackageInfo.setRtnShipmentCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                         aascShipmentPackageInfo.setPkgListNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));
                                         aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));

                                     }

                                     if (!listBaseCharge.equals("") && 
                                         listBaseCharge != null) {
                                         aascShipmentPackageInfo.setRtnBaseChrge(Double.parseDouble(escape(listBaseCharge)));
                                     }
                                     if (!totalListDiscount.equals("") && 
                                         totalListDiscount != null) {
                                         aascShipmentPackageInfo.setRtnTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                     }
                                     if (!listHazMatCharges.equals("") && 
                                         listHazMatCharges != null) {
                                         aascShipmentPackageInfo.setRtnHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                     }
                                 } //end of else for Return Shipment
                             }
                         } else { 



                             if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                             {

                                 if (!totalShipmentListCharges.equals("") && 
                                     totalShipmentListCharges != null) {
                                     aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                     aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));

                                 }


                                 if (!listBaseCharge.equals("") && 
                                     listBaseCharge != null) {
                                     aascShipmentPackageInfo.setBaseCharge(Double.parseDouble(escape(listBaseCharge)));
                                 }
                                 if (!totalListDiscount.equals("") && 
                                     totalListDiscount != null) {
                                     aascShipmentPackageInfo.setTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                 }
                                 if (!listHazMatCharges.equals("") && 
                                     listHazMatCharges != null) {
                                     aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                 }
                             } //end of if for Non Return
                             else //For Return shipment
                             {
                                 if (!totalShipmentListCharges.equals("") && 
                                     totalShipmentListCharges != null) {
                                     aascShipmentPackageInfo.setRtnShipmentCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                     aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));
                                     aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                 }

                                 if (!listBaseCharge.equals("") && 
                                     listBaseCharge != null) {
                                     aascShipmentPackageInfo.setRtnBaseChrge(Double.parseDouble(escape(listBaseCharge)));
                                 }
                                 if (!totalListDiscount.equals("") && 
                                     totalListDiscount != null) {
                                     aascShipmentPackageInfo.setRtnTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                 }
                                 if (!listHazMatCharges.equals("") && 
                                     listHazMatCharges != null) {
                                     aascShipmentPackageInfo.setRtnHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                 }
                             } //end of else for Return Shipment
                         }


                         try {
                             surCharges = Double.parseDouble(sCharges);
                         } catch (Exception e) {
                             surCharges = 0.0;
                         }
                         try {
                             listsurCharges = Double.parseDouble(sListCharges);
                         } catch (Exception e) {
                             listsurCharges = 0.0;
                         }
                         if (nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost()).equalsIgnoreCase("Y")) // Discounted Rates are considered
                         {
                             aascShipmentPackageInfo.setSurCharges(surCharges);
                         } else {
                             aascShipmentPackageInfo.setSurCharges(listsurCharges);
                         }

                         
                         hashMap.put("masterTrkNum", trackingNumber);
                         hashMap.put("masterFormId", masterFormId);
                         hashMap.put("codTrackingNum", codTrackingNum);

                     } else {
                         if (aascShipmentPackageInfo.getPackageSequence().equals("1")) {
                             masterTrackingNumber = 
                                     aascShipmentPackageInfo.getTrackingNumber();

                             codTrackingNum = 
                                     aascShipmentPackageInfo.getCodTrackingNum();

                             hashMap.put("masterTrkNum", 
                                         aascShipmentPackageInfo.getTrackingNumber());
                             hashMap.put("masterFormId", 
                                         aascShipmentPackageInfo.getMasterFormID());

                             hashMap.put("codTrackingNum", codTrackingNum);


                         }


                         if (aascShipmentPackageInfo.getPackageSequence().equals(pkgSequenceNum) && 
                             !pkgSequenceNum.equals("1")) {

                             if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {

                                 aascShipmentPackageInfo.setMasterTrackingNumber(masterTrackingNumber);
                                 aascShipmentPackageInfo.setMasterFormID(masterFormId);
                                 aascShipmentPackageInfo.setTrackingNumber(trackingNumber);


                             }
                             else {

                                 aascShipmentPackageInfo.setRtnTrackingNumber(rtnTrackingNumber);
                             }

                             aascShipmentPackageInfo.setCodTrackingNum(codTrackingNum);
                             if (!totalShipmentListCharges.equals("") && 
                                 totalShipmentListCharges != null) {
                                 aascShipmentPackageInfo.setPkgListNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));
                             }

                             if (!totalShipmentCharges.equals("") && 
                                 totalShipmentCharges != null) {
                                 aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentCharges)));
                             }

                             if (nullStrToSpc(aascProfileOptionsInfo.getAcctNumNegotiatedFlag()).equalsIgnoreCase("Y")) { //Added 8/0/10
                                 if (nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost()).equalsIgnoreCase("Y")) // Discounted Rates are considered
                                 {
                                     if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                                     {
                                         if (!totalShipmentCharges.equals("") && 
                                             totalShipmentCharges != null) {
                                             aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentCharges)));

                                         }

                                         if (!baseCharge.equals("") && 
                                             baseCharge != null) {
                                             aascShipmentPackageInfo.setBaseCharge(Double.parseDouble(escape(baseCharge)));
                                         }
                                         if (!totalDiscount.equals("") && 
                                             totalDiscount != null) {
                                             aascShipmentPackageInfo.setTotalDiscount(Double.parseDouble(escape(totalDiscount)));
                                         }
                                         if (!HazMatCharges.equals("") && 
                                             HazMatCharges != null) {
                                             aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(escape(HazMatCharges)));
                                         }
                                     } //end of if for Non Return 
                                     else //For Return shipment
                                     {
                                         if (!totalShipmentCharges.equals("") && 
                                             totalShipmentCharges != null) {
                                             aascShipmentPackageInfo.setRtnShipmentCost(Double.parseDouble(escape(totalShipmentCharges)));
                                             aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentCharges)));
                                             aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentCharges)));
                                         }
                                         if (!baseCharge.equals("") && 
                                             baseCharge != null) {
                                             aascShipmentPackageInfo.setRtnBaseChrge(Double.parseDouble(escape(baseCharge)));
                                         }
                                         if (!totalDiscount.equals("") && 
                                             totalDiscount != null) {
                                             aascShipmentPackageInfo.setRtnTotalDiscount(Double.parseDouble(escape(totalDiscount)));
                                         }
                                         if (!HazMatCharges.equals("") && 
                                             HazMatCharges != null) {
                                             aascShipmentPackageInfo.setRtnHazMatCharges(Double.parseDouble(escape(HazMatCharges)));
                                         }
                                     } //end of else for Return Shipment
                                 } else { // List Published Rates are considered  
                                     if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                                     {
                                         if (!totalShipmentListCharges.equals("") && 
                                             totalShipmentListCharges != null) {
                                             aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));

                                         }

                                         if (!listBaseCharge.equals("") && 
                                             listBaseCharge != null) {
                                             aascShipmentPackageInfo.setBaseCharge(Double.parseDouble(escape(listBaseCharge)));
                                         }
                                         if (!totalListDiscount.equals("") && 
                                             totalListDiscount != null) {
                                             aascShipmentPackageInfo.setTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                         }
                                         if (!listHazMatCharges.equals("") && 
                                             listHazMatCharges != null) {
                                             aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                         }
                                     } //end of if for Non Return
                                     else //For Return shipment
                                     {
                                         if (!totalShipmentListCharges.equals("") && 
                                             totalShipmentListCharges != null) {
                                             aascShipmentPackageInfo.setRtnShipmentCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                             aascShipmentPackageInfo.setPkgListNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));
                                             aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                         }
                                         if (!listBaseCharge.equals("") && 
                                             listBaseCharge != null) {
                                             aascShipmentPackageInfo.setRtnBaseChrge(Double.parseDouble(escape(listBaseCharge)));
                                         }
                                         if (!totalListDiscount.equals("") && 
                                             totalListDiscount != null) {
                                             aascShipmentPackageInfo.setRtnTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                         }
                                         if (!listHazMatCharges.equals("") && 
                                             listHazMatCharges != null) {
                                             aascShipmentPackageInfo.setRtnHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                         }
                                     } //end of else for Return Shipment

                                 }

                             } else { 

                                 // List Published Rates are considered       
                                 logger.info("Published rates are considered");

                                 if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) // for Non Return 
                                 {

                                     if (!totalShipmentListCharges.equals("") && 
                                         totalShipmentListCharges != null) {
                                         aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                         aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));

                                     }


                                     if (!listBaseCharge.equals("") && 
                                         listBaseCharge != null) {
                                         aascShipmentPackageInfo.setBaseCharge(Double.parseDouble(escape(listBaseCharge)));
                                     }
                                     if (!totalListDiscount.equals("") && 
                                         totalListDiscount != null) {
                                         aascShipmentPackageInfo.setTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                     }
                                     if (!listHazMatCharges.equals("") && 
                                         listHazMatCharges != null) {
                                         aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                     }
                                 } //end of if for Non Return
                                 else //For Return shipment
                                 {
                                     if (!totalShipmentListCharges.equals("") && 
                                         totalShipmentListCharges != null) {
                                         aascShipmentPackageInfo.setRtnShipmentCost(Double.parseDouble(escape(totalShipmentListCharges)));
                                         aascShipmentPackageInfo.setPkgDiscountNetCharge(Double.parseDouble(escape(totalShipmentListCharges)));
                                         aascShipmentPackageInfo.setPkgCost(Double.parseDouble(escape(totalShipmentListCharges)));

                                     }

                                     if (!listBaseCharge.equals("") && 
                                         listBaseCharge != null) {
                                         aascShipmentPackageInfo.setRtnBaseChrge(Double.parseDouble(escape(listBaseCharge)));
                                     }
                                     if (!totalListDiscount.equals("") && 
                                         totalListDiscount != null) {
                                         aascShipmentPackageInfo.setRtnTotalDiscount(Double.parseDouble(escape(totalListDiscount)));
                                     }
                                     if (!listHazMatCharges.equals("") && 
                                         listHazMatCharges != null) {
                                         aascShipmentPackageInfo.setRtnHazMatCharges(Double.parseDouble(escape(listHazMatCharges)));
                                     }
                                 } //end of else for Return Shipment
                             }
                            
                             if (!sCharges.equals("") && sCharges != null) {
                                 surCharges = Double.parseDouble(sCharges);
                             }
                             try {
                                 listsurCharges = 
                                         Double.parseDouble(sListCharges);
                             } catch (Exception e) {
                                 listsurCharges = 0.0;
                             }
                             if (nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost()).equalsIgnoreCase("Y")) // Discounted Rates are considered
                             {
                                 aascShipmentPackageInfo.setSurCharges(surCharges);
                             } else {
                                 aascShipmentPackageInfo.setSurCharges(listsurCharges);
                             }

                         }
                     }
                     

                 }

                 String resCarrierCode =  root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/CarrierCode/text()").getNodeValue(); //Shiva added for issue 3860
                 if (resCarrierCode.equalsIgnoreCase("FDXG") && 
                     (packageCnt == Integer.parseInt(pkgSequenceNum))) {
                     
                     if (!(chkReturnlabel.equalsIgnoreCase("PRINTRETURNLABEL"))) {
                         if (nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost()).equalsIgnoreCase("Y")) // Discounted Rates are considered
                         {

                             if (packageCnt == 1) {
                                 node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/DiscountedCharges/NetCharge/text()");
                             } else {
                                 node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/DiscountedCharges/ShipmentNetCharge/text()");
                             }
                             String testNode = "";
                             try {
                                 if (node.getNodeValue() != null && 
                                     !(node.getNodeValue()).equals("")) {
                                     testNode = "true";
                                 }
                             } catch (Exception e) {
                                 testNode = "";
                             }
                             if (!testNode.equalsIgnoreCase("")) {

                             }
                         } else {
                             try {
                                 if (packageCnt == 1) {
                                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/ListCharges/NetCharge/text()");
                                 } else {
                                     node = 
 root.selectSingleNode("/FDXShipReply/EstimatedCharges/ListCharges/ShipmentNetCharge/text()");
                                 }
                                 String testNode = "";
                                 try {
                                     if (node.getNodeValue() != null && 
                                         !(node.getNodeValue()).equals("")) {
                                         testNode = "true";
                                     }
                                 } catch (Exception e) {
                                     testNode = "";
                                 }


                             } catch (Exception e) {
                                 logger.info("/FDXShipReply/EstimatedCharges/ListCharges/ShipmentNetCharge/text() tag is missing" + 
                                             e.getMessage());
                                 totalDiscount = "0.00";
                             }
                         }
                     }
                 }

              
                 displayMessage = "success";
                 hashMap.put("status", displayMessage);

             } else {    

                 errorCode = AascXmlParser.getValue(root, "Code");
                 if (errorCode != null && !errorCode.equals("")) {

                     errorDescription = AascXmlParser.getValue(root, "Message");
                     displayMessage = 
                             errorDescription + "(errorCode:" + errorCode + ")";

                     softErrorType = AascXmlParser.getValue(root, "Type");
                     if (!softErrorType.equals("") && softErrorType != null) {
                         logger.severe("soft error type:" + softErrorType);
                     }
                 }

                 logger.severe("errorDescription:" + displayMessage);
                 hashMap.put("status", displayMessage);
             } // else
         } // end of try
         catch (Exception exp) {
             displayMessage = "Error in parsing or setting the values to bean!";
             logger.severe("Exception::"+exp.getMessage());
         }
         logger.info("Exit from parseResponse()");
         return hashMap;

     } // end of method

     private void parse123(String dutiesAndTaxesFlag) {
         try {
             node = 
 root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/ShipmentRating");

             NodeList PackageRatingList = node.getChildNodes();

             for (int s = 0; s < PackageRatingList.getLength(); s++) {
                 Node childNode = PackageRatingList.item(s);

                 if (childNode.getNodeName().equalsIgnoreCase("ShipmentRateDetails")) {

                     NodeList PackageRateDetailsList = 
                         childNode.getChildNodes();

                     String listOrAccount = "";
                     for (int i = 0; i < PackageRateDetailsList.getLength(); 
                          i++) {

                         Node childNodeLevel2 = PackageRateDetailsList.item(i);
                         if (childNodeLevel2.getLocalName().equalsIgnoreCase("RateType")) {
                             Element firstNameElement = 
                                 (Element)PackageRateDetailsList.item(i);
                             NodeList textFNList = 
                                 firstNameElement.getChildNodes();
                             String nodeValue = 
                                 ((textFNList.item(0)).getNodeValue()).trim();
                             listOrAccount = nodeValue;
                         }

                         // basecharge start
                         if (childNodeLevel2.getLocalName().equalsIgnoreCase("TotalBaseCharge")) {

                             NodeList NetChargeNodeList = 
                                 childNodeLevel2.getChildNodes();

                             for (int j = 0; j < NetChargeNodeList.getLength(); 
                                  j++) {
                                 Node childNodeLevel3 = 
                                     NetChargeNodeList.item(j);
                                 if (childNodeLevel3.getLocalName().equalsIgnoreCase("Amount")) {
                                     Element firstNameElement = 
                                         (Element)NetChargeNodeList.item(j);
                                     NodeList textFNList = 
                                         firstNameElement.getChildNodes();
                                     String nodeValue = 
                                         ((textFNList.item(0)).getNodeValue()).trim();

                                     if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_SHIPMENT")) {
                                         baseCharge = nodeValue;
                                     } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_SHIPMENT")) {
                                         listBaseCharge = nodeValue;
                                     }
                                     if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_PACKAGE")) {
                                         baseCharge = nodeValue;
                                     } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_PACKAGE")) {
                                         listBaseCharge = nodeValue;
                                     }
                                     
                                 }
                             }
                         }
                        

                         // TotalFreightDiscounts start
                         if (childNodeLevel2.getLocalName().equalsIgnoreCase("TotalFreightDiscounts")) {

                             NodeList NetChargeNodeList = 
                                 childNodeLevel2.getChildNodes();
                             for (int j = 0; j < NetChargeNodeList.getLength(); 
                                  j++) {
                                 Node childNodeLevel3 = 
                                     NetChargeNodeList.item(j);
                                 if (childNodeLevel3.getLocalName().equalsIgnoreCase("Amount")) {
                                     Element firstNameElement = 
                                         (Element)NetChargeNodeList.item(j);
                                     NodeList textFNList = 
                                         firstNameElement.getChildNodes();
                                     String nodeValue = 
                                         ((textFNList.item(0)).getNodeValue()).trim();
                                     if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_SHIPMENT")) {
                                         totalDiscount = nodeValue;
                                     } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_SHIPMENT")) {
                                         totalListDiscount = nodeValue;
                                     }
                                     if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_PACKAGE")) {
                                         totalDiscount = nodeValue;
                                     } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_PACKAGE")) {
                                         totalListDiscount = nodeValue;
                                     }
                                     
                                 }
                             }
                         }
                         // TotalFreightDiscounts end    

                         // net charge start
                         if (childNodeLevel2.getLocalName().equalsIgnoreCase("TotalNetCharge")) {

                             NodeList NetChargeNodeList = 
                                 childNodeLevel2.getChildNodes();
                             for (int j = 0; j < NetChargeNodeList.getLength(); 
                                  j++) {
                                 Node childNodeLevel3 = 
                                     NetChargeNodeList.item(j);
                                 if (childNodeLevel3.getLocalName().equalsIgnoreCase("Amount")) {
                                     Element firstNameElement = 
                                         (Element)NetChargeNodeList.item(j);
                                     NodeList textFNList = 
                                         firstNameElement.getChildNodes();
                                     String nodeValue = 
                                         ((textFNList.item(0)).getNodeValue()).trim();
                                     if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_SHIPMENT")) {
                                         totalShipmentCharges = nodeValue;
                                     } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_SHIPMENT")) {
                                         totalShipmentListCharges = nodeValue;
                                     }
                                      if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_PACKAGE")) {
                                         totalShipmentCharges = nodeValue;
                                     } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_PACKAGE")) {
                                         totalShipmentListCharges = nodeValue;
                                     }
                                     
                                     
                                     
                                     
                                 }
                             }
                         }
                         // net charge end

                          // net charge with duties and taxes start  (Added in cloud by Jagadish for SCCloud 1.2)
                          logger.info("dutiesAndTaxesFlag" + dutiesAndTaxesFlag);
                          if ("Y".equalsIgnoreCase(dutiesAndTaxesFlag)) {
                              if (childNodeLevel2.getLocalName().equalsIgnoreCase("TotalNetChargeWithDutiesAndTaxes")) {

                                  NodeList NetChargeNodeList = 
                                      childNodeLevel2.getChildNodes();
                                  for (int j = 0; 
                                       j < NetChargeNodeList.getLength(); j++) {
                                      Node childNodeLevel3 = 
                                          NetChargeNodeList.item(j);
                                      if (childNodeLevel3.getLocalName().equalsIgnoreCase("Amount")) {
                                          Element firstNameElement = 
                                              (Element)NetChargeNodeList.item(j);
                                          NodeList textFNList = 
                                              firstNameElement.getChildNodes();
                                          String nodeValue = 
                                              ((textFNList.item(0)).getNodeValue()).trim();
                                          if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_SHIPMENT")) {
                                              totalShipmentCharges = nodeValue;
                                          } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_SHIPMENT")) {
                                              totalShipmentListCharges = 
                                                      nodeValue;
                                          }
                                          if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_PACKAGE")) {
                                              totalShipmentCharges = nodeValue;
                                          } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_PACKAGE")) {
                                              totalShipmentListCharges = 
                                                      nodeValue;
                                          }
                                      }
                                  }
                              }
                          }  
                         //  Dangerous Goods Shipping Charges for FDXE 
                         if (childNodeLevel2.getLocalName().equalsIgnoreCase("Surcharges")) {
                             String surchargesType = "";

                             NodeList NetChargeNodeList = 
                                 childNodeLevel2.getChildNodes();
                             for (int j = 0; j < NetChargeNodeList.getLength(); 
                                  j++) {
                                 Node childNodeLevel3 = 
                                     NetChargeNodeList.item(j);

                                 if (childNodeLevel3.getLocalName().equalsIgnoreCase("SurchargeType")) {
                                     Element firstNameElement = 
                                         (Element)NetChargeNodeList.item(j);
                                     NodeList textFNList = 
                                         firstNameElement.getChildNodes();
                                     surchargesType = 
                                             ((textFNList.item(0)).getNodeValue()).trim();
                                 }

                                 if (childNodeLevel3.getLocalName().equalsIgnoreCase("Amount")) {

                                     NodeList AmountNodeList = 
                                         childNodeLevel3.getChildNodes();
                                     for (int j1 = 0; 
                                          j1 < AmountNodeList.getLength(); 
                                          j1++) {
                                         Node childNodeLevel4 = 
                                             AmountNodeList.item(j1);
                                         if (childNodeLevel4.getLocalName().equalsIgnoreCase("Amount")) {
                                             Element firstNameElement = 
                                                 (Element)AmountNodeList.item(j1);
                                             NodeList textFNList = 
                                                 firstNameElement.getChildNodes();
                                             String nodeValue = 
                                                 ((textFNList.item(0)).getNodeValue()).trim();
                                             if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_SHIPMENT")) {
                                                 if (surchargesType.equalsIgnoreCase("DANGEROUS_GOODS") || 
                                                     surchargesType.equalsIgnoreCase("HAZARDOUS_MATERIAL")) {
                                                     HazMatCharges = nodeValue;
                                                 }
                                             } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_SHIPMENT")) {
                                                 if (surchargesType.equalsIgnoreCase("DANGEROUS_GOODS") || 
                                                     surchargesType.equalsIgnoreCase("HAZARDOUS_MATERIAL")) {
                                                     listHazMatCharges = 
                                                             nodeValue;
                                                 }

                                             }
                                             
                                             
                                            if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_PACKAGE")) {
                                                 if (surchargesType.equalsIgnoreCase("DANGEROUS_GOODS") || 
                                                     surchargesType.equalsIgnoreCase("HAZARDOUS_MATERIAL")) {
                                                     HazMatCharges = nodeValue;
                                                 }
                                             } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_PACKAGE")) {
                                                 if (surchargesType.equalsIgnoreCase("DANGEROUS_GOODS") || 
                                                     surchargesType.equalsIgnoreCase("HAZARDOUS_MATERIAL")) {
                                                     listHazMatCharges = 
                                                             nodeValue;
                                                 }

                                             }

                                         }
                                     }
                                 }
                             }
                         }

                         // total surcharge start
                         if (childNodeLevel2.getLocalName().equalsIgnoreCase("TotalSurcharges")) {

                             NodeList NetChargeNodeList = 
                                 childNodeLevel2.getChildNodes();
                             for (int j = 0; j < NetChargeNodeList.getLength(); 
                                  j++) {
                                 Node childNodeLevel3 = 
                                     NetChargeNodeList.item(j);
                                 if (childNodeLevel3.getLocalName().equalsIgnoreCase("Amount")) {
                                     Element firstNameElement = 
                                         (Element)NetChargeNodeList.item(j);
                                     NodeList textFNList = 
                                         firstNameElement.getChildNodes();
                                     String nodeValue = 
                                         ((textFNList.item(0)).getNodeValue()).trim();
                                     if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_SHIPMENT")) {
                                         sCharges = nodeValue;
                                     } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_SHIPMENT")) {
                                         sListCharges = nodeValue;
                                     }
                                     
                                     if (listOrAccount.equalsIgnoreCase("PAYOR_ACCOUNT_PACKAGE")) {
                                         sCharges = nodeValue;
                                     } else if (listOrAccount.equalsIgnoreCase("PAYOR_LIST_PACKAGE")) {
                                         sListCharges = nodeValue;
                                     }


                                     
                                     
                                 }
                             }
                         }
                         // total surcharge end
                     }
                 }
             }

                          node = 
                          root.selectSingleNode("/Envelope/Body/ProcessShipmentReply/CompletedShipmentDetail/CompletedEtdDetail");

                          if (node != null) {
                              NodeList IntlDocIdList = node.getChildNodes();

                              for (int s = 0; s < IntlDocIdList.getLength(); s++) {
                                  Node childNode = IntlDocIdList.item(s);

                                  NodeList PackageRateDetailsList = 
                                      childNode.getChildNodes();

                                  for (int i = 0; i < PackageRateDetailsList.getLength(); 
                                       i++) {

                                      Node childNodeLevel2 = IntlDocIdList.item(i);

                                      if (childNodeLevel2 != null) {
                                          if (childNodeLevel2.getLocalName().equalsIgnoreCase("UploadDocumentReferenceDetails")) {

                                              NodeList NetChargeNodeList = 
                                                  childNodeLevel2.getChildNodes();
                                              for (int j = 0; 
                                                   j < NetChargeNodeList.getLength(); j++) {
                                                  Node childNodeLevel3 = 
                                                      NetChargeNodeList.item(j);

                                                  if (childNodeLevel3.getLocalName().equalsIgnoreCase("DocumentType")) {
                                                      Element firstNameElement = 
                                                          (Element)NetChargeNodeList.item(j);

                                                      NodeList textFNList = 
                                                          firstNameElement.getChildNodes();
                                                      DocumentType = 
                                                              ((textFNList.item(0)).getNodeValue()).trim();

                                                  }

                                                  if (childNodeLevel3.getLocalName().equalsIgnoreCase("DocumentId")) {

                                                      if (DocumentType.equalsIgnoreCase("COMMERCIAL_INVOICE")) {

                                                          Element firstNameElement = 
                                                              (Element)NetChargeNodeList.item(j);

                                                          NodeList textFNList = 
                                                              firstNameElement.getChildNodes();
                                                          DocumentID = 
                                                                  ((textFNList.item(0)).getNodeValue()).trim();


                                                      }
                                                  }
                                              }
                                          }
                                      }
                                  }
                              }
                          }
         } catch (Exception e) {
             logger.severe("exception on parsing...." + e.getMessage());
         }
     }

 }

