package com.aasc.erp.carrier.dhlws;

  /**
   * AascDHLShipWebService.java  is a java class for DHL carrier where in you call DHL webservice.
   * @version   1
   * @author   G S Shekar
   * @History
   * 
   * Date         Resource       Changes	
   *----------------------------------------------------------------------------------------------------	
   * 17/11/2015   Shiva G        Modified code for using the Global Schema for DHL Shipping		
   */

import com.aasc.erp.bean.AascIntlCommodityInfo;
import com.aasc.erp.bean.AascIntlInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.util.AascLogger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;

import java.util.HashMap;

import java.io.OutputStreamWriter;

import java.text.DecimalFormat;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;

public class

DHLShipWebService {

    private static Logger logger = AascLogger.getLogger("DHLShipWebService");
    private String response="";
    private String timeStampStr="";
    private String deliveryId = "";
    AascIntlInfo aascIntlInfo = null;
    private String intlAccountNumberStr = "";
    private String intlPayerType = "";
    
    LinkedList commodityList;
    public DHLShipWebService() {
    }
    
    private LinkedList packageInfo = 
        null; // Delivery Package Information Object      
    
    
public String getShipInfo(HashMap hm, String ipAddress, int portNo, LinkedList shipPackageInfo,String intlFlag) {
        logger.info("enter getshipinfo in aascdhl ship ws");
        String response = "";
        String accessCode = "";
        String dhlRequest = "";
        String dhlResponce = "";
        String dhlLabel = "";
        String labelXML = "";
        
        AascIntlInfo aascIntlInfo = (AascIntlInfo)hm.get("aascIntlInfo");
        
        if(aascIntlInfo == null)
            aascIntlInfo = new AascIntlInfo();
            
        commodityList = aascIntlInfo.getIntlCommodityInfo();
        
        StringBuffer request = buildShipmentRequest(hm, shipPackageInfo,intlFlag);
        //dhlpassward
        String outputFile = nullStringToSpace(hm.get("outputFile"));
        timeStampStr = nullStringToSpace(hm.get("timeStampStr"));
        this.deliveryId = nullStringToSpace(hm.get("deliveryId"));
        String requestFilePath = outputFile;
        try {
        
        //write basic request here 
         try {
             writeOutputFile(request.toString(), requestFilePath +deliveryId+"_"+timeStampStr+ "_Shipment_request.xml");
         } catch (Exception fileNotFoundException) {
             logger.severe("Exception::"+fileNotFoundException.getMessage());
         }
        
            response = 
                    sendSOAPRequest(request.toString(), false,
                                    ipAddress, 
                                    requestFilePath);
            
            
            try {
                writeOutputFile(response, requestFilePath +deliveryId+"_"+timeStampStr+ "_Shipment_response.xml");
            } catch (Exception fileNotFoundException) {
                logger.severe("Exception::"+fileNotFoundException.getMessage());
            }
            
            logger.info("shipment response---- > "+response);
              
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } finally {
            return response;
        }
    }
    
    
    
    private StringBuffer buildShipmentRequest(HashMap hm, LinkedList shipPackageInfo,String intlFlag) {
        
        StringBuffer shipMentRequest = new StringBuffer();
        logger.info("****************************************************************************************");
        logger.info("HashMap: " + hm);
        logger.info("****************************************************************************************");
  
        String siteId = nullStringToSpace(hm.get("siteId")).trim();
        String dhlPassword = nullStringToSpace(hm.get("dhlPassword")).trim();
        
        String shipDate = nullStringToSpace(hm.get("shipDate")).trim();
        
        String senderAccountNumber = 
            nullStringToSpace(hm.get("senderAccountNumber")).trim();
        String isDutiable     = "N";
        String receiverAccountNumber = 
            nullStringToSpace(hm.get("receiverAccountNumber")).trim();
        String shipperAccountNumber = 
            nullStringToSpace(hm.get("shipperAccountNumber")).trim();
        String ref1n2 = 
            nullStringToSpace(hm.get("ref1n2")).trim();    
        String shipToAddressCity = 
            nullStringToSpace(hm.get("shipToAddressCity")).trim();
        String shipToAddressState = 
            nullStringToSpace(hm.get("shipToAddressState")).trim();
        String shipToAddressPostalCode = 
            nullStringToSpace(hm.get("shipToAddressPostalCode")).trim();
        String shipToAddressLine1 = 
            nullStringToSpace(hm.get("shipToAddressLine1")).trim();
        String shipToAddressLine2 = 
            nullStringToSpace(hm.get("shipToAddressLine2")).trim();
        String shipToAddressLine3 = 
            nullStringToSpace(hm.get("shipToAddressLine3")).trim();
            
        String shipFromAddressPostalCode = 
            nullStringToSpace(hm.get("shipFromAddressPostalCode")).trim();     
       String shipFromAddressCity = 
            nullStringToSpace(hm.get("shipFromAddressCity")).trim();
        String shipFromAddressState = 
            nullStringToSpace(hm.get("shipFromAddressState")).trim();
        String shipFromCountry = 
            nullStringToSpace(hm.get("shipFromCountry")).trim();     
       String SenderEmail = 
            nullStringToSpace(hm.get("SenderEmail")).trim();     
        String shipFromPersonName = 
             nullStringToSpace(hm.get("shipFromPersonName")).trim();
        String shipFromPhoneNumber1 = nullStringToSpace(hm.get("shipFromPhoneNumber1")).trim();
        String shipFromCode = nullStringToSpace(hm.get("shipFromCode")).trim();
        
        String shipToCompanyName = 
            nullStringToSpace(hm.get("shipToCompanyName")).trim();
        String shipToCountry = 
            nullStringToSpace(hm.get("shipToCountry")).trim();
        String shipToContactPersonName = 
            nullStringToSpace(hm.get("shipToContactPersonName")).trim();
        String shipToContactPhoneNumber = 
            nullStringToSpace(hm.get("shipToContactPhoneNumber")).trim();
        String shipToCode = 
            nullStringToSpace(hm.get("shipToCode")).trim();
            
        String shipFromCompanyName = 
            nullStringToSpace(hm.get("shipFromCompanyName")).trim();

        String shipFromAddressLine1 = 
            nullStringToSpace(hm.get("shipFromAddressLine1")).trim();
        String shipFromAddressLine2 = 
            nullStringToSpace(hm.get("shipFromAddressLine2")).trim();
        String recipientEmailAddress1 = 
            nullStringToSpace(hm.get("recipientEmailAddress1")).trim();
        
        
        String currencyCode = 
            nullStringToSpace(hm.get("currencyCode")).trim();
        String termsOfTrade = 
            nullStringToSpace(hm.get("termsOfTrade")).trim();
            
        String carrierPayMethod = 
            nullStringToSpace(hm.get("carrierPayMethod")).trim();
        String pkgCount = 
            nullStringToSpace(hm.get("pkgCount")).trim();
        String deliveryId = 
            nullStringToSpace(hm.get("deliveryId")).trim();
        String totalVolume = 
            nullStringToSpace(hm.get("totalVolume")).trim();
        String totalInscuredValue = 
            nullStringToSpace(hm.get("totalInscuredValue")).trim();
        String globalProductName = 
            nullStringToSpace(hm.get("globalProductName")).trim();
        String localProductName = 
            nullStringToSpace(hm.get("localProductName")).trim();
        String option = 
            nullStringToSpace(hm.get("option")).trim();
        String shipToCountryFullName = 
            nullStringToSpace(hm.get("shipToCountryFullName")).trim();
        String shipFromCountryFullName = 
            nullStringToSpace(hm.get("shipFromCountryFullName")).trim();
//        String transactionalCurrCode = 
//            nullStringToSpace(hm.get("transactionalCurrCode")).trim();
        String labelFormat = 
            nullStringToSpace(hm.get("labelFormat")).trim();
        String labelStockType = nullStringToSpace(hm.get("labelStockOrientation")).trim();            
        String dhlContent = 
            nullStringToSpace(hm.get("dhlContent")).trim(); 
        String dhlRegionCode = nullStringToSpace(hm.get("dhlRegionCode")).trim();            
        double totalWeight = (Double)hm.get("totalWeight");
        Double totalDeclareValue = 0.00;
        DecimalFormat df = new DecimalFormat("0.00##");
        
        intlAccountNumberStr = nullStringToSpace(hm.get("intlAccountNumberStr")).trim();    
        intlPayerType = nullStringToSpace(hm.get("intlPayerType")).trim();    
//        String result = df.format(100.1);
        
        timeStampStr = nullStringToSpace(hm.get("messageReference")).trim();
        String messgageTime = nullStringToSpace(hm.get("messgageTime")).trim();
        String messageReference = nullStringToSpace(hm.get("messageReference")).trim();
        
        String uom = "";
        String dimensionUnit = "";
        int numberOfPiece;
            
        ListIterator packageInfoIterator = shipPackageInfo.listIterator();
        ListIterator packageInfoIterator1 = shipPackageInfo.listIterator();
        numberOfPiece =  shipPackageInfo.size();
        
        logger.info("numberOfPiece  : "+numberOfPiece);
        while (packageInfoIterator.hasNext())
        {
        AascShipmentPackageInfo aascPackageBean =(AascShipmentPackageInfo)packageInfoIterator.next();
            
            logger.info("getUOM "+aascPackageBean.getUom());
            logger.info("get dimension unit "+aascPackageBean.getDimensionUnits());
            uom = aascPackageBean.getUom();
            dimensionUnit = aascPackageBean.getDimensionUnits();
            
            totalDeclareValue = totalDeclareValue+aascPackageBean.getPackageDeclaredValue();
            if(totalDeclareValue > 0)
                isDutiable ="Y";
                
        }
        
        
        if(uom.equalsIgnoreCase("LB")) 
            uom = "L";
        else
            uom = "K";
        
        if(dimensionUnit.equalsIgnoreCase("CM")) 
            dimensionUnit = "C";
        else if (dimensionUnit.equalsIgnoreCase("IN"))
            dimensionUnit = "I";
            
            
        try {

            shipMentRequest.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"); 
            shipMentRequest.append("<req:ShipmentRequest xsi:schemaLocation=\"http://www.dhl.com ship-val-global-req.xsd\" schemaVersion=\"1.0\" xmlns:req=\"http://www.dhl.com\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" ); 
            shipMentRequest.append("<Request>\n" ); 
            
            shipMentRequest.append("<ServiceHeader>\n" ); 
            shipMentRequest.append("<MessageTime>"+messgageTime+"</MessageTime>\n" ); 
            shipMentRequest.append("<MessageReference>"+messageReference+"</MessageReference>\n" ); 
            shipMentRequest.append("<SiteID>"+siteId+"</SiteID>\n" ); 
            shipMentRequest.append("<Password>"+dhlPassword+"</Password>\n" ); 
            shipMentRequest.append("</ServiceHeader>\n" ); 
            
            shipMentRequest.append("</Request>\n" );
            shipMentRequest.append("<RegionCode>"+dhlRegionCode+"</RegionCode>\n");
                        
            shipMentRequest.append("<RequestedPickupTime>Y</RequestedPickupTime>\n" ); 
            shipMentRequest.append("<NewShipper>N</NewShipper>\n" ); // hard code
            shipMentRequest.append("<LanguageCode>en</LanguageCode>\n" ); // hard code
            shipMentRequest.append("<PiecesEnabled>Y</PiecesEnabled>\n");//needed for label 
            
            shipMentRequest.append("<Billing>\n" ); 
            
            if(!"S".equalsIgnoreCase(carrierPayMethod)){ //if payment type not sender
                shipMentRequest.append("<ShipperAccountNumber>"+shipperAccountNumber+"</ShipperAccountNumber>\n" ); 
            }
            else{
                shipMentRequest.append("<ShipperAccountNumber>"+senderAccountNumber+"</ShipperAccountNumber>\n" ); 
            }
            
            shipMentRequest.append("<ShippingPaymentType>"+carrierPayMethod+"</ShippingPaymentType>\n" );
            
            shipMentRequest.append("<BillingAccountNumber>"+senderAccountNumber+"</BillingAccountNumber>\n" );
            
            if("Y".equalsIgnoreCase(isDutiable)){
                shipMentRequest.append("<DutyPaymentType>"+intlPayerType+"</DutyPaymentType>\n" ); 
            }
            else{
                shipMentRequest.append("<DutyPaymentType>"+"R"+"</DutyPaymentType>\n" );
				}
            
             if("Y".equalsIgnoreCase(intlFlag) && "Y".equalsIgnoreCase(isDutiable)){//is international 
//              if(!"R".equalsIgnoreCase(intlPayerType)){
                if("S".equalsIgnoreCase(intlPayerType) || "T".equalsIgnoreCase(intlPayerType)){
                    shipMentRequest.append("<DutyAccountNumber>"+intlAccountNumberStr+"</DutyAccountNumber>\n" ); 
                }
             }
                
            shipMentRequest.append("</Billing>\n" ); 
//            }
                        
            shipMentRequest.append("<Consignee>\n" ); 
            shipMentRequest.append("<CompanyName>"+shipToCompanyName+"</CompanyName>\n" ); 
            shipMentRequest.append("<AddressLine>"+shipToAddressLine1+"</AddressLine>\n" );
            shipMentRequest.append("<AddressLine>"+shipToAddressLine2+"</AddressLine>\n" ); 
            shipMentRequest.append("<AddressLine>"+shipToAddressLine3+"</AddressLine>\n" ); 
            shipMentRequest.append("<City>"+shipToAddressCity+"</City>\n" ); 
            shipMentRequest.append("<Division>"+shipToAddressState+"</Division>\n" ); 
            shipMentRequest.append("<PostalCode>"+shipToAddressPostalCode+"</PostalCode>\n" ); 
            shipMentRequest.append("<CountryCode>"+shipToCountry+"</CountryCode>\n" ); 
            shipMentRequest.append("<CountryName>"+shipToCountryFullName+"</CountryName>\n" ); 
            //shipMentRequest.append("<FederalTaxId>RFTD10222124893</FederalTaxId>\n" ); 
            shipMentRequest.append("<Contact>\n" ); 
            shipMentRequest.append("<PersonName>"+shipToContactPersonName+"</PersonName>\n" ); 
            shipMentRequest.append("<PhoneNumber>"+shipToContactPhoneNumber+"</PhoneNumber>\n" ); 
            
            shipMentRequest.append("</Contact>\n" ); 
            shipMentRequest.append("</Consignee>\n" ); 
            
       
            if("Y".equalsIgnoreCase(isDutiable)){
            
                shipMentRequest.append("<Dutiable>\n" ); 
                shipMentRequest.append("<DeclaredValue>"+df.format(totalDeclareValue)+"</DeclaredValue>\n" ); 
                shipMentRequest.append("<DeclaredCurrency>"+currencyCode+"</DeclaredCurrency>\n" ); 
                shipMentRequest.append("<TermsOfTrade>"+termsOfTrade+"</TermsOfTrade>");
                shipMentRequest.append("</Dutiable>\n" ); 
                
            }
                       
 if(commodityList == null)
                commodityList = new LinkedList();
            
            ListIterator commodityListItr = commodityList.listIterator();
            numberOfPiece =  commodityList.size();
            
//            
//            shipMentRequest.append("<Commodity>\n" + 
//            "		<CommodityCode>HS# 6110300097</CommodityCode>\n" + 
//            "		<CommodityName>HS</CommodityName>\n" + 
//            "	</Commodity>\n");
            
            
            
            
            logger.info("numberOfcommodity  : "+numberOfPiece);
            logger.info("IntlShipFlag :::::::"+intlFlag+":::::");
            
            if("Y".equalsIgnoreCase(isDutiable)){
                if("Y".equalsIgnoreCase(intlFlag))
                {
                   if(numberOfPiece > 0){
                    
                        shipMentRequest.append("<ExportDeclaration>\n");
                        int itr = 0;
                       AascIntlCommodityInfo aascIntlCommodityInfo = null;
                        while (commodityListItr.hasNext())
                        {
                            aascIntlCommodityInfo  = 
                                 (AascIntlCommodityInfo)commodityListItr.next();
                                 
                            shipMentRequest.append("<ExportLineItem>\n");
                            shipMentRequest.append("<LineNumber>"+nullStringToSpace(aascIntlCommodityInfo.getCommodityNumber())+"</LineNumber>\n");
                            shipMentRequest.append("<Quantity>"+nullStringToSpace(aascIntlCommodityInfo.getQuantity())+"</Quantity>\n");
                            shipMentRequest.append("<QuantityUnit>"+nullStringToSpace(aascIntlCommodityInfo.getQuantityUnits())+"</QuantityUnit>\n"); 
                            shipMentRequest.append("<Description>"+nullStringToSpace(aascIntlCommodityInfo.getDescription())+"</Description>\n");
                            shipMentRequest.append("<Value>"+nullStringToSpace(aascIntlCommodityInfo.getCustomsValue())+"</Value>\n");
                            shipMentRequest.append("<CommodityCode>"+nullStringToSpace(aascIntlCommodityInfo.getHarmonizedCode())+"</CommodityCode>\n");
                            shipMentRequest.append("</ExportLineItem>\n");
                            
                        }
                        shipMentRequest.append("</ExportDeclaration>\n");
                   }  
                }
            }
                       
                        /*"	<Commodity>\n" + 
                        "		<CommodityCode>1</CommodityCode>\n" + 
                        "		<CommodityName>cm</CommodityName>\n" + 
                        "	</Commodity>\n" + */
                       
                       /* "	<Dutiable>\n" + 
                        "		<DeclaredValue>2000.00</DeclaredValue>\n" + 
                        "		<DeclaredCurrency>USD</DeclaredCurrency>\n" + 
                        "		<ScheduleB>3002905110</ScheduleB>\n" + 
                        "		<ExportLicense>D123456</ExportLicense>\n" + 
                        "		<ShipperEIN>1111111111</ShipperEIN>\n" + 
                        "		<ShipperIDType>S</ShipperIDType>\n" + 
                        "		<ConsigneeIDType>S</ConsigneeIDType>\n" + 
                        "		<ImportLicense>Text</ImportLicense>\n" + 
                        "		<ConsigneeEIN>Text</ConsigneeEIN>\n" + 
                        "		<TermsOfTrade>DDU</TermsOfTrade>\n" + 
                        "	</Dutiable>\n" + */
                      
                        /*"	<ExportDeclaration>\n" + 
                        "		<InterConsignee>String</InterConsignee>\n" + 
                        "		<IsPartiesRelation>N</IsPartiesRelation>\n" + 
                        "		<ECCN>EAR99</ECCN>\n" + 
                        "		<SignatureName>String</SignatureName>\n" + 
                        "		<SignatureTitle>String</SignatureTitle>\n" + 
                        "		<ExportReason>S</ExportReason>\n" + 
                        "		<ExportReasonCode>P</ExportReasonCode>\n" + 
                        "		<SedNumber>FTSR</SedNumber>\n" + 
                        "		<SedNumberType>F</SedNumberType>\n" + 
                        "		<MxStateCode>St</MxStateCode>\n" + 
                        "		<ExportLineItem>\n" + 
                        "			<LineNumber>200</LineNumber>\n" + 
                        "			<Quantity>32</Quantity>\n" + 
                        "			<QuantityUnit>String</QuantityUnit>\n" + 
                        "			<Description>String</Description>\n" + 
                        "			<Value>200.00</Value>\n" + 
                        "			<IsDomestic>Y</IsDomestic>\n" + 
                        "			<ScheduleB>3002905110</ScheduleB>\n" + 
                        "			<ECCN>EAR99</ECCN>\n" + 
                        "			<Weight>\n" + 
                        "				<Weight>100.0</Weight>\n" + 
                        "				<WeightUnit>L</WeightUnit>\n" + 
                        "			</Weight>\n" + 
                        "			<License>\n" + 
                        "				<LicenseNumber>D123456</LicenseNumber>\n" + 
                        "				<ExpiryDate>2011-09-29</ExpiryDate>\n" + 
                        "			</License>\n" + 
                        "			<LicenseSymbol>String</LicenseSymbol>\n" + 
                        "		</ExportLineItem>\n" + 
                        "	</ExportDeclaration>\n" + */
                       
            shipMentRequest.append("<Reference>\n" ); 
            shipMentRequest.append("<ReferenceID>"+ref1n2+"</ReferenceID>\n" );
            //shipMentRequest.append("<ReferenceType>St</ReferenceType>\n" ); //need to discuss
            shipMentRequest.append("</Reference>\n" ); 
                     
            shipMentRequest.append("<ShipmentDetails>\n" );
            
            numberOfPiece =  shipPackageInfo.size();
            
            shipMentRequest.append("<NumberOfPieces>"+numberOfPiece+"</NumberOfPieces>\n" ); 
            
            shipMentRequest.append("<Pieces>\n" ); 
            int packageNum=1;
            while (packageInfoIterator1.hasNext())
            {
               AascShipmentPackageInfo aascPackageBean = 
                   (AascShipmentPackageInfo)packageInfoIterator1.next();
                   
//            logger.info("getPackageLength "+aascPackageBean.getPackageLength());
//            logger.info("getPackageWidth "+aascPackageBean.getPackageWidth());
//            logger.info("getPackageHeight"+aascPackageBean.getPackageHeight());
//            logger.info("getWeight "+aascPackageBean.getWeight());
//            logger.info("getpackageSequence "+aascPackageBean.getPackageNumber());
            
            shipMentRequest.append("<Piece>\n" ); 
            shipMentRequest.append("<PieceID>"+packageNum+"</PieceID>\n" ); 
            //shipMentRequest.append("<PackageType>EE</PackageType>\n" ); 
            shipMentRequest.append("<Weight>"+nullStringToSpace(aascPackageBean.getWeight())+"</Weight>\n" ); 
            shipMentRequest.append("<Width>"+(int)aascPackageBean.getPackageWidth()+"</Width>\n" ); 
            shipMentRequest.append("<Height>"+(int)aascPackageBean.getPackageHeight()+"</Height>\n" ); 
            //shipMentRequest.append("<DimWeight>1200.0</DimWeight>\n" ); 
            //(int)Double.parseDouble((String)aascPackageBean.getpac.getPackageWidth())
            shipMentRequest.append("<Depth>"+(int)aascPackageBean.getPackageLength()+"</Depth>\n" ); 

            
            //shipMentRequest.append("<PieceContents> PieceContents </PieceContents>");
            shipMentRequest.append("</Piece>\n" ); 
            packageNum++;
            }
            shipMentRequest.append("</Pieces>\n" ); 
                        
            shipMentRequest.append("<Weight>"+totalWeight+"</Weight>\n" ); 
            shipMentRequest.append("<WeightUnit>"+uom+"</WeightUnit>\n" ); 
            
            shipMentRequest.append("<GlobalProductCode>"+globalProductName+"</GlobalProductCode>\n" ); 
            shipMentRequest.append("<LocalProductCode>"+localProductName+"</LocalProductCode>\n" ); 
            shipMentRequest.append("<Date>"+shipDate+"</Date>\n" ); 
            shipMentRequest.append("<Contents>"+dhlContent+"</Contents>\n" ); 
            shipMentRequest.append("<DimensionUnit>"+dimensionUnit+"</DimensionUnit>\n" );
            shipMentRequest.append("<PackageType>EE</PackageType>\n");
            shipMentRequest.append("<IsDutiable>"+isDutiable+"</IsDutiable>\n" ); 
             shipMentRequest.append("<CurrencyCode>"+currencyCode+"</CurrencyCode>\n" );
            shipMentRequest.append("</ShipmentDetails>\n" ); 
                      
            shipMentRequest.append("<Shipper>\n" ); 
            shipMentRequest.append("<ShipperID>"+senderAccountNumber+"</ShipperID>\n" );//Shipper Account number may be used 
            shipMentRequest.append("<CompanyName>"+shipFromCompanyName+"</CompanyName>\n" ); 
            shipMentRequest.append("<RegisteredAccount>"+senderAccountNumber+"</RegisteredAccount>\n" );//Shipper Account number may be used 
            shipMentRequest.append("<AddressLine>"+shipFromAddressLine1+"</AddressLine>\n" ); 
            shipMentRequest.append("<AddressLine>"+shipFromAddressLine2+"</AddressLine>\n" ); 
            shipMentRequest.append("<City>"+shipFromAddressCity+"</City>\n" ); 
            shipMentRequest.append("<Division>"+shipFromAddressState+"</Division>\n" ); 
            //shipMentRequest.append("<DivisionCode>AZ</DivisionCode>\n" ); 
            shipMentRequest.append("<PostalCode>"+shipFromAddressPostalCode+"</PostalCode>\n" ); 
            shipMentRequest.append("<CountryCode>"+shipFromCountry+"</CountryCode>\n" ); 
            shipMentRequest.append("<CountryName>"+shipFromCountryFullName+"</CountryName>\n" ); 
            //shipMentRequest.append("<FederalTaxId>SFTD10222124893</FederalTaxId>\n" ); 
            shipMentRequest.append("<Contact>\n" ); 
            shipMentRequest.append("<PersonName>"+shipFromPersonName+"</PersonName>\n" ); 
            shipMentRequest.append("<PhoneNumber>"+shipFromPhoneNumber1+"</PhoneNumber>\n" ); 
            
            shipMentRequest.append("</Contact>\n" ); 
            shipMentRequest.append("</Shipper>\n" );
            
            //added SpecialServiceType if item is dutialble
            if("Y".equalsIgnoreCase(isDutiable)){
                shipMentRequest.append("<SpecialService>\n" ); 
                shipMentRequest.append("<SpecialServiceType>DD</SpecialServiceType> \n" ); 
                shipMentRequest.append("</SpecialService>\n" ); 
            }
            
            shipMentRequest.append("<EProcShip>N</EProcShip>\n" ); //default to N, to generate waybill
            shipMentRequest.append("<LabelImageFormat>"+labelFormat+"</LabelImageFormat>\n");
            shipMentRequest.append("<RequestArchiveDoc>Y</RequestArchiveDoc>");
            shipMentRequest.append("<Label>");
            shipMentRequest.append("<LabelTemplate>"+labelStockType+"</LabelTemplate>");
            shipMentRequest.append("</Label>");
            shipMentRequest.append("</req:ShipmentRequest>");
             
            //System.out.println("shipMentRequest -> "+shipMentRequest);

        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } finally {
            return shipMentRequest;
        }
    }
    
    

    
    public String sendSOAPRequest(String request, boolean httpsFlag, 
                                  String hName, String requestFilePath) {
        
        
        URL aspPage;
        InputStream isXMLResponse;
        String response = "";
        String httpOrHttps = "http";
        logger.info("XML Request: " + request);
        logger.info("hName --- > "+hName);
        logger.info("---------------------------calling DHL Server ---------------------------------");
        
        String clientRequestXML = request;//new String(buffer);
         //System.out.println("request : "+clientRequestXML);
        try{
            
        /* Preparing the URL and opening connection to the server*/
        URL servletURL = null;
        servletURL = new URL(hName);

        HttpURLConnection servletConnection = null;
        servletConnection = (HttpURLConnection)servletURL.openConnection();
        servletConnection.setDoOutput(true);  // to allow us to write to the URL
        servletConnection.setDoInput(true);
        servletConnection.setUseCaches(false); 
                                        servletConnection.setRequestMethod("POST");


        servletConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");       
        String len = Integer.toString(clientRequestXML.getBytes().length);
        //System.out.println("length " + len);
        servletConnection.setRequestProperty("Content-Length", len);
        //servletConnection.setRequestProperty("Content-Language", "en-US");  

        /*Code for sending data to the server*/
        DataOutputStream dataOutputStream;
        dataOutputStream = new DataOutputStream(servletConnection.getOutputStream());
        
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
                                        servletConnection.setReadTimeout(10000);
                                        servletConnection.connect();
                                        OutputStreamWriter wr = new OutputStreamWriter(servletConnection.getOutputStream());
                                        wr.write(clientRequestXML);
                                        wr.flush();
                                        wr.close();
                                        
        byte[] dataStream = clientRequestXML.getBytes();
        dataOutputStream.write(dataStream);  //Writing data to the servlet
        dataOutputStream.flush();
        dataOutputStream.close();

         /*Code for getting and processing response from DHL's servlet*/
        InputStream inputStream = null;
        inputStream = servletConnection.getInputStream();
        StringBuffer response1 = new StringBuffer();
        int printResponse;

        //Reading the response into StringBuffer
        while ((printResponse=inputStream.read())!=-1) 
        {
            response1.append((char) printResponse);
        }
        inputStream.close();
                                        //System.out.println();
        //System.out.println("response 1--> "+response1.toString());
        response = response1.toString();
        }
        catch(MalformedURLException mfURLex)
        {
            logger.severe("MalformedURLException "+mfURLex.getMessage());
        }
        catch(IOException e)
        {
        logger.severe("IOException "+e.getMessage());
        //e.printStackTrace();
        }
        
        return response;
    }
    private void writeOutputFile(String str, String file) throws Exception {
        FileOutputStream fout = new FileOutputStream(file);
        try {
            fout.write(str.getBytes());
        } catch (Exception ex) {
            logger.severe("Exception::"+ex.getMessage());
        } finally {
            try {
                fout.close();
            } catch (Exception ex) {
                logger.severe("Exception::"+ex.getMessage());
            }

        }


    }
    
    //Shiva added below method as formatter is removed
     /**Returns String value of the object or a space.
      * @param object Object of type Object class 
      * @return String equivalent of the object
      */
     public String nullStringToSpace(Object object) {
         String spcStr = "";
         if (object == null) {
             return spcStr;
         } else {
             return object.toString();
         }
     }

}
