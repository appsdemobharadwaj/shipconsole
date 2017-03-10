 /*
  * @(#)AascFreightShop.java     19/02/2015
  * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
  * All rights reserved.
  *
  */

package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascFSCarrierServiceLevels;
import com.aasc.erp.bean.AascFSCarriersIncluded;
import com.aasc.erp.bean.AascFSResponseStatus;
import com.aasc.erp.bean.AascFreightShopInfo;
import com.aasc.erp.carrier.AascXmlParser;
import com.aasc.erp.util.AascLogger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.StringReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLElement;
import org.w3c.dom.Element;

import org.w3c.dom.Node;

import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

/**
 * AascFreightShop class is used to frame freight shop request, send request to url and parse the response.
 * @version   1
 * @author    Suman G
 * History
 * 
 *  05/03/2015    Suman G       Modified request for Get Rates
 *  13/03/2015    Suman G       Added code for show error.
 *  07/08/2015    N Srisha      Added encode(),replaceStr() methods for handling special characters in Address fields.
 *  05/11/2015    Suman G       Commented code for Stamps.
 *  21/12/2015    Y Pradeep     Modified code to set TimeStamp for Shipment date tag.
 */

public class AascFreightShop {

    static Logger logger = 
        AascLogger.getLogger(" AascFreightShop "); // logger object
        
    public AascFreightShop() {
    }
    
    private String appsUserName = "";
    private String appsPassword = "";
    private String appsAccountNumber = "";
    private String orderNumber = "";
    private LinkedList shipFromList = new LinkedList();
    private LinkedList shipToList = new LinkedList();
    private LinkedList packageDetailsList = new LinkedList();
    private java.sql.Timestamp timeStamp = null;
    private String sortRule = "";
    private String returnBestChoice = "";
    private LinkedList selectedCarriersList = new LinkedList();
    private LinkedList fedexDetailsList = new LinkedList();
    private LinkedList upsDetailsList = new LinkedList();
    private LinkedList tntDetailsList = new LinkedList();
    private LinkedList dhlDetailsList = new LinkedList();
    private LinkedList echoDetailsList = new LinkedList();
    private LinkedList stampsDetailsList = new LinkedList();
    private LinkedList uspsDetailsList = new LinkedList();
    
    private LinkedList configuredCarriers = new LinkedList();
    
    private DOMParser parser = null; // This object is used to parse xml document
    private StringReader reader = null; // String reader object which takes String as input and converts it into character stream 
    private InputSource input = null; // This object is used to encapsulate character stream into single object
    private XMLDocument xmlDocument = null; // This encapsulates xml document
    private XMLElement root = null; // This encapsulates xml element 
    private Node node = null; // It represents single node in dom tree
    private String cloudLabelPath = "";
    private String decision = "";
    private String serviceType = "";
     /**
     * This method used to initialize class level variables.
     * This is also used to call the method which is used to Web service.
     * @param appsUserName, appsPassword, appsAccountNumber String
     * @param shipFromList, shipToList, packageDetailsList LinkedList
     * @param timeStamp, sortRule, returnBestChoice, orderNumber String
     * @param selectedCarriersList, fedexDetailsList, upsDetailsList, tntDetailsList, dhlDetailsList, echoDetailsList, stampsDetailsList, uspsDetailsList, configuredCarriers  LinkedList
     * @param portNumber, URL String, decision String, serviceType String
     * @return AascFreightShopInfo it has response of web service.
     */ 
    
    public AascFreightShopInfo webServiceContact(String appsUserName,String appsPassword, String appsAccountNumber, LinkedList shipFromList, LinkedList shipToList, LinkedList packageDetailsList, java.sql.Timestamp timeStamp, String sortRule, String returnBestChoice, LinkedList selectedCarriersList, LinkedList fedexDetailsList, LinkedList upsDetailsList, LinkedList tntDetailsList, LinkedList dhlDetailsList, LinkedList echoDetailsList, LinkedList stampsDetailsList, LinkedList uspsDetailsList, String orderNumber, LinkedList configuredCarriers, String portNumber,String URL, String cloudLabelPath, String decision, String serviceType){
        this.appsUserName = appsUserName;
        this.appsPassword = appsPassword;
        this.appsAccountNumber = appsAccountNumber;
        this.orderNumber = orderNumber;
        
        this.shipFromList = shipFromList;
        this.shipToList = shipToList;
        this.packageDetailsList = packageDetailsList;
        
        this.timeStamp = timeStamp;
        this.sortRule = sortRule;
        this.returnBestChoice = returnBestChoice;
        
        this.selectedCarriersList = selectedCarriersList;
        this.fedexDetailsList = fedexDetailsList;
        this.upsDetailsList = upsDetailsList;
        this.tntDetailsList = tntDetailsList;
        this.dhlDetailsList = dhlDetailsList;
        this.echoDetailsList = echoDetailsList;
        this.stampsDetailsList = stampsDetailsList;
        this.uspsDetailsList = uspsDetailsList;
        this.configuredCarriers = configuredCarriers;
        this.cloudLabelPath = cloudLabelPath;
        this.decision = decision;
        this.serviceType = serviceType;
        String request = buildRequest();
        String timeStampStr;
        timeStampStr = 
                (new Date().toString().replaceAll(" ", "_")).replaceAll(":", 
                                                                        "_");
        try {
            writeOutputFile(request, cloudLabelPath + orderNumber+"_"+timeStampStr+"_freightshop_request.xml");
        } catch (Exception e) {
            logger.severe("Exception :"+e.getMessage());
        }
//        System.out.println(request);
        String response = sendXML(request,false,URL+":"+portNumber+"/FreightShopping/BestCarrierServices?wsdl");
        try {
            writeOutputFile(response, cloudLabelPath + orderNumber+"_"+timeStampStr+"_freightshop_response.xml");
        } catch (Exception e) {
            logger.severe("Exception :"+e.getMessage());
        }
        AascFreightShopInfo aascFreightShopInfo = parseResponse(response);
        return aascFreightShopInfo;
    }
    
    /**
    * This method used to build the freight shop request.
    * @return String framed request of freight shop.
    */ 
    
    public String buildRequest(){
        StringBuffer request = new StringBuffer();
        request.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.apps.com/\">");
        request.append("<soapenv:Header>");
            request.append("<ws:AppsAssociatesAccountNo>").append(appsAccountNumber).append("</ws:AppsAssociatesAccountNo>");
            request.append("<ws:AppsAssociatesPassword>").append(appsPassword).append("</ws:AppsAssociatesPassword>");
            request.append("<ws:AppsAssociatesUserName>").append(appsUserName).append("</ws:AppsAssociatesUserName>");
        request.append("</soapenv:Header>");
        request.append("<soapenv:Body><ws:AAFreightShopRequest>");
        request.append("<referenceNo>").append(orderNumber).append("</referenceNo>");

            //*************** Ship From Address **************************//
            String shipFrom = getShipFromDetails(shipFromList);
            request.append(shipFrom);
            
            //*************** Ship To Address **************************//
             String shipTo = getShipToDetails(shipToList);
             request.append(shipTo);
            
            //*************** Package Description **************************//
            request.append("<pkgsDescription>");
            String packageDetails = getPkgsDescription(packageDetailsList);
            request.append(packageDetails);
            request.append("</pkgsDescription>");
            java.sql.Date shipmentDate =new java.sql.Date(timeStamp.getTime());
            String timeStr = timeStamp.toString();
            timeStr = nullStrToSpc(timeStr.substring(11, timeStr.length() - 2));            
            String hr = timeStr.substring(0, 2);
            String min = timeStr.substring(3, 5);
            String sec = timeStr.substring(6, 8);
        
            String shipmentTimeStamp = shipmentDate + "T" + hr + ":" + min + ":" + sec;
            request.append("<shipmentTimeStamp>").append(shipmentTimeStamp).append("</shipmentTimeStamp>");
            request.append("<sortRule>").append(sortRule).append("</sortRule>");
            request.append("<returnBestChoice>").append(returnBestChoice).append("</returnBestChoice>");
            
            //*************** Carrier Selection **************************//
            String carriersSelected = getSelectedCarriers(selectedCarriersList);
            request.append(carriersSelected);
            
            
            //*************** Configured Services **************************//
            if("FreightShop".equalsIgnoreCase(decision)){
                request.append("<configuredCarrierServices>");
                String configuredServices = getConfiguredServices(configuredCarriers);
                request.append(configuredServices);
                request.append("</configuredCarrierServices>");
            }
            //*************** Carrier Details Start **************************//
            //============== FedEx =======================//
            String fedexDetails = getFedExDetails(fedexDetailsList);
            request.append(fedexDetails);
            
            
            //============== UPS =======================//
            String upsDetails = getUpsDetails(upsDetailsList);
            request.append(upsDetails);
            
            if("GetRates".equalsIgnoreCase(decision)){
                request.append("<ServiceCode>").append(serviceType).append("</ServiceCode>");
            }
            
            //============== TNT =======================//
            String tntDetails = getTntDetails(tntDetailsList);
            request.append(tntDetails);
            
            
            //============== DHL =======================//
            String dhlDetails = getDhlDetails(dhlDetailsList);
            request.append(dhlDetails);
            
            
            //============== Stamps =======================//
            String stampsDetails = getStampsDetails(stampsDetailsList);
            request.append(stampsDetails);
            
            
            //============== USPS =======================//
            String uspsDetails = getUspsDetails(uspsDetailsList);
            request.append(uspsDetails);
            
            
            //============== Echo =======================//
            String echoDetails = getEchoDetails(echoDetailsList);
            request.append(echoDetails);
            
            request.append("<echoItems>");
            String items = getItems();
            request.append(items);
            request.append("</echoItems>");
            request.append("<echoAccessorialArray>");
            String echoAccessorials = getAccessorials();
            request.append(echoAccessorials);
            request.append("</echoAccessorialArray>");
            request.append("<palletQty>").append("").append("</palletQty>");
            //*************** Carrier Details End**************************//
            
            //************* Hazmat Details for FedEx **********************// 
//            request.append("<isHazmatMaterialNeeded>").append("").append("</isHazmatMaterialNeeded>");
//            request.append("<specialServices>");
//            String dangarousGoods = getDangarousGoods();
//            request.append(dangarousGoods);
//            request.append("</specialServices>");
        request.append("</ws:AAFreightShopRequest></soapenv:Body>");
        request.append("</soapenv:Envelope>");
        
        return request.toString();
    }
    
    /**
    * This method used to frame ship from details.
    * @return String framed request of freight shop.
    */ 
    
    public String getShipFromDetails(LinkedList shipFromDetails){
        logger.info("Entered shipFromDetails()");
        StringBuffer str = new StringBuffer();
        Iterator it = shipFromDetails.iterator();
        while(it.hasNext()){
            str.append("<originAddressLine1>").append(encode((String)it.next())).append("</originAddressLine1>"); // Added encode function for address line1 and line2 for bug #3332 by srisha
            str.append("<originAddressLine2>").append(encode((String)it.next())).append("</originAddressLine2>");
            str.append("<originCity>").append(it.next()).append("</originCity>");
            str.append("<originState>").append(it.next()).append("</originState>");
            str.append("<originZipCode>").append(it.next()).append("</originZipCode>");
            str.append("<originCountryCode>").append(it.next()).append("</originCountryCode>");
        }
        logger.info("Exit shipFromDetails()");
        return str.toString();
    }
    
    /**
    * This method used to frame ship to details.
    * @return String framed request of freight shop.
    */ 
    public String getShipToDetails(LinkedList shipToDetails){
        logger.info("Entered shipToDetails()");
        StringBuffer str = new StringBuffer();
        Iterator it = shipToDetails.iterator();
        while(it.hasNext()){
            str.append("<destAddressLine1>").append(encode((String)it.next())).append("</destAddressLine1>"); // Added encode function for address line1 and line2 for bug #3332 by srisha
            str.append("<destAddressLine2>").append(encode((String)it.next())).append("</destAddressLine2>");
            str.append("<destCity>").append(it.next()).append("</destCity>");
            str.append("<destState>").append(it.next()).append("</destState>");
            str.append("<destZipCode>").append(it.next()).append("</destZipCode>");
            str.append("<destCountryCode>").append(it.next()).append("</destCountryCode>");
        }
        logger.info("Exit shipToDetails()");
        return str.toString();
    }
    
    /**
    * This method used to frame package details.
    * @return String framed request of freight shop.
    */
    public String getPkgsDescription(LinkedList packageDetailsList){
        StringBuffer str = new StringBuffer();
        
        Iterator it = packageDetailsList.iterator();
        int count = (Integer)it.next();
        logger.info("count::::"+count);
        for(int i=1;i<=count;i++){
            while(it.hasNext()){
                str.append("<pkgDescription>");
                str.append("<pkgDimensionsUOM>").append(it.next()).append("</pkgDimensionsUOM>");
                str.append("<pkgDimensionsVal>").append(it.next()).append("</pkgDimensionsVal>");
                str.append("<pkgWeightUOM>").append(it.next()).append("</pkgWeightUOM>");
                str.append("<pkgWeightVal>").append(it.next()).append("</pkgWeightVal>");
                str.append("</pkgDescription>");
            }
        }
        
        return str.toString();
    }
    
    /**
    * This method used to frame selected carriers.
    * @return String framed request of freight shop.
    */
    public String getSelectedCarriers(LinkedList selectedCarriersList){
        logger.info("Entered getSelectedCarriers()");
        StringBuffer str = new StringBuffer();
        Iterator it = selectedCarriersList.iterator();
        while(it.hasNext()){
            str.append("<isFedExNeeded>").append(it.next()).append("</isFedExNeeded>");
            str.append("<isUPSNeeded>").append(it.next()).append("</isUPSNeeded>");
            str.append("<isTNTNeeded>").append(it.next()).append("</isTNTNeeded>");
            str.append("<isDHLNeeded>").append(it.next()).append("</isDHLNeeded>");
            str.append("<isEchoNeeded>").append(it.next()).append("</isEchoNeeded>");
            str.append("<isStampsNeeded>").append(it.next()).append("</isStampsNeeded>");
            str.append("<isUspsNeeded>").append(it.next()).append("</isUspsNeeded>");
        }
        logger.info("Exit getSelectedCarriers()");
        return str.toString();
    }
    
    /**
    * This method used to frame fedex details.
    * @return String framed request of freight shop.
    */
    public String getFedExDetails(LinkedList fedexDetailsList){
        logger.info("Entered getFedExDetails()");
        StringBuffer str = new StringBuffer();
        Iterator it = fedexDetailsList.iterator();
        while(it.hasNext()){
            str.append("<fdxeKey>").append(it.next()).append("</fdxeKey>");
            str.append("<fdxePassword>").append(it.next()).append("</fdxePassword>");
            str.append("<fdxeAccountNumber>").append(it.next()).append("</fdxeAccountNumber>");
            str.append("<fdxeMeterNumber>").append(it.next()).append("</fdxeMeterNumber>");
        }
        logger.info("Exit getFedExDetails()");
        return str.toString();
    }
    
    /**
    * This method used to frame ups details.
    * @return String framed request of freight shop.
    */
    public String getUpsDetails(LinkedList upsDetailsList){
        logger.info("Entered getUpsDetails()");
        StringBuffer str = new StringBuffer();
        Iterator it = upsDetailsList.iterator();
        while(it.hasNext()){
            str.append("<upsUserId>").append(it.next()).append("</upsUserId>");
            str.append("<upsPassword>").append(it.next()).append("</upsPassword>");
            str.append("<upsShipperNumber>").append(it.next()).append("</upsShipperNumber>");
            str.append("<upsLicenseNumber>").append(it.next()).append("</upsLicenseNumber>");
        }
        logger.info("Exit getUpsDetails()");
        return str.toString();
    }
    
    /**
    * This method used to frame tnt details.
    * @return String framed request of freight shop.
    */
    public String getTntDetails(LinkedList tntDetailsList){
        StringBuffer str = new StringBuffer();
        Iterator it = tntDetailsList.iterator();
        while(it.hasNext()){
            str.append("<tntUsername>").append(it.next()).append("</tntUsername>");
            str.append("<tntPassword>").append(it.next()).append("</tntPassword>");
            str.append("<tntAccountNumber>").append(it.next()).append("</tntAccountNumber>");
        }
        return str.toString();
    }
    
    /**
    * This method used to frame dhl details.
    * @return String framed request of freight shop.
    */
    public String getDhlDetails(LinkedList dhlDetailsList){
        StringBuffer str = new StringBuffer();
        Iterator it = dhlDetailsList.iterator();
        while(it.hasNext()){
            str.append("<dhlSiteId>").append(it.next()).append("</dhlSiteId>");
            str.append("<dhlPassword>").append(it.next()).append("</dhlPassword>");
            str.append("<paymentAccountNumber>").append(it.next()).append("</paymentAccountNumber>");
            str.append("<isDutiable>").append(it.next()).append("</isDutiable>");
            str.append("<dhlDeclaredCurrency>").append(it.next()).append("</dhlDeclaredCurrency>");
            str.append("<dhlDeclaredValue>").append(it.next()).append("</dhlDeclaredValue>");
        }
        return str.toString();
    }
    
    /**
    * This method used to frame stamps details.
    * @return String framed request of freight shop.
    */
    public String getStampsDetails(LinkedList stampsDetailsList){
        StringBuffer str = new StringBuffer();
        Iterator it = stampsDetailsList.iterator();
        while(it.hasNext()){
            str.append("<stampsUserName>").append(it.next()).append("</stampsUserName>");
            str.append("<stampsPassword>").append(it.next()).append("</stampsPassword>");
            str.append("<stampsIntegrationID>").append(it.next()).append("</stampsIntegrationID>");
//            str.append("<stampsService>").append(it.next()).append("</stampsService>");
//            str.append("<stampspackageType>").append(it.next()).append("</stampspackageType>");
        }
        return str.toString();
    }
    
    /**
    * This method used to frame usps details.
    * @return String framed request of freight shop.
    */
    public String getUspsDetails(LinkedList uspsDetailsList){
        StringBuffer str = new StringBuffer();
        Iterator it = uspsDetailsList.iterator();
        while(it.hasNext()){
            str.append("<uspsSize>").append(it.next()).append("</uspsSize>");
            str.append("<uspsGirth>").append(it.next()).append("</uspsGirth>");
            str.append("<uspsUserID>").append(it.next()).append("</uspsUserID>");
            str.append("<uspsContainer>").append(it.next()).append("</uspsContainer>");
            str.append("<uspsService>").append(it.next()).append("</uspsService>");
            str.append("<uspsMachinable>").append(it.next()).append("</uspsMachinable>");
            str.append("<uspsFirstClassMailType>").append(it.next()).append("</uspsFirstClassMailType>");
        }
        return str.toString();
    }
    
    /**
    * This method used to frame echo details.
    * @return String framed request of freight shop.
    */
    public String getEchoDetails(LinkedList echoDetailsList){
        StringBuffer str = new StringBuffer();
        Iterator it = echoDetailsList.iterator();
        while(it.hasNext()){
            str.append("<echoUserName>").append(it.next()).append("</echoUserName>");
            str.append("<echoPassword>").append(it.next()).append("</echoPassword>");
        }
        return str.toString();
    }
    
    /**
    * This method used to frame configured carriers details.
    * @return String framed request of freight shop.
    */
    public String getConfiguredServices(LinkedList configuredCarriers){
        StringBuffer str = new StringBuffer();
        Iterator it = configuredCarriers.iterator();
        while(it.hasNext()){
            str.append("<configuredCarrierService>").append(it.next()).append("</configuredCarrierService>");
        }
        return str.toString();
    }
    
    /**
    * This method used to frame echo items details.
    * @return String framed request of freight shop.
    */
    public String getItems(){
        StringBuffer str = new StringBuffer();
        str.append("<items>");
            str.append("<itemClass>").append("").append("</itemClass>");
            str.append("<itemWeight>").append("").append("</itemWeight>");
        str.append("</items>");
        return str.toString();
    }
    
    /**
    * This method used to frame echo accessorials details.
    * @return String framed request of freight shop.
    */
    public String getAccessorials(){
        StringBuffer str = new StringBuffer();
        str.append("<accessorialArray>");
            str.append("<accessorialId>").append("").append("</accessorialId>");
            str.append("<charge>").append("").append("</charge>");
        str.append("</accessorialArray>");
        return str.toString();
    }
    
    /**
    * This method used to frame dangarous goods details.
    * @return String framed request of freight shop.
    */
    public String getDangarousGoods(){
        StringBuffer str = new StringBuffer();
        str.append("<dangarousGoodsContainer>");
            str.append("<accessibility>").append("").append("</accessibility>");
            str.append("<amount>").append("").append("</amount>");
            str.append("<containerType>").append("").append("</containerType>");
            str.append("<hazardClass>").append("").append("</hazardClass>");
            str.append("<id>").append("").append("</id>");
            str.append("<labelText>").append("").append("</labelText>");
            str.append("<numberOfContainers>").append("").append("</numberOfContainers>");
            str.append("<packingGroup>").append("").append("</packingGroup>");
            str.append("<packingInstructions>").append("").append("</packingInstructions>");
            str.append("<packingOption>").append("").append("</packingOption>");
            str.append("<properShippingName>").append("").append("</properShippingName>");
            str.append("<technicalName>").append("").append("</technicalName>");
            str.append("<units>").append("").append("</units>");
        str.append("</dangarousGoodsContainer>");
        return str.toString();
    }
    
    /**
    * This method used to send request and take response.
    * @param request String
    * @param httpsFlag boolean
    * @param hName String
    * @return String response of freight shop.
    */
    public  String sendXML(String request, boolean httpsFlag, String hName)
    {
    
        URL aspPage;
        InputStream isXMLResponse;
        String response = new String();
        String httpOrHttps = "https";

        try
        {
//            if (httpsFlag)
//            {
//                httpOrHttps = "https";
                disableSslVerification();
//            }
//            else
//            {
//                httpOrHttps = "http";
//            }

            logger.info("Posting to URL: " + hName);
          //  aspPage = new URL(httpOrHttps + "://" + hName);
          // Added below code to take protocal from properties file
           aspPage = new URL(hName);
            HttpURLConnection connection2 = (HttpURLConnection)aspPage.openConnection();
//            System.out.println("After Connection :"+connection2);

            // setup connection
            connection2.setDoInput(true);
            connection2.setDoOutput(true);
            connection2.setRequestMethod("POST");
            connection2.setUseCaches(false);
            connection2.setRequestProperty("Content-type", "text/xml; charset=utf-8");

            String xml_request = new String(request);
            byte[] buffer = xml_request.getBytes("UTF-8");

            connection2.setRequestProperty("Content-Length", String.valueOf(buffer.length) );

            // POST the request to the connections OutputStream
            DataOutputStream toServer = new DataOutputStream(connection2.getOutputStream());
            try
            {
                toServer.write(buffer, 0, buffer.length);
            }
            finally
            {
                toServer.close();
                toServer.flush();
            }

            // read back the repsonse to check if the post request was successful
            isXMLResponse = connection2.getInputStream();

            int charRead;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (true)
            {
                charRead = isXMLResponse.read();
                if (charRead == -1)
                   break;

                baos.write(charRead);
            }
//            System.out.println("#################################");
                    response = baos.toString();
//            System.out.println("#################################"+response);
            
//                parseResponse(response);
            isXMLResponse.close();
        }
        catch (MalformedURLException mue)
        {
            logger.severe("MalformedURLException: " + mue.getMessage());
        }
        catch (IOException ioe)
        {
            logger.severe("IOException: " + ioe.getMessage());
        }
        catch (Exception exe)
        {
            logger.severe("Exeception: " + exe.getMessage());
        }
        finally
        {
        return response;
        }
    }
    
    /**
    * This method used to parse the response.
    * @param response String
    * @return AascFreightShopInfo response of freight shop.
    */
    
    public AascFreightShopInfo parseResponse(String response){
        logger.info("Entered parseResponse()");
        AascFreightShopInfo aascFreightShopInfo = new AascFreightShopInfo();
        try{
            response = response.replaceAll("<S:", "<");
            response = response.replaceAll("<ns2:", "<");
            response = response.replaceAll("<ns3:", "<");
            response = response.replaceAll("</ns3:", "</");
            response = response.replaceAll("</ns2:", "</");
            response = response.replaceAll("</S:", "</");
//            System.out.println("response after replace::::"+response);
        }catch(Exception e){
            logger.severe("Excetpion :"+e.getMessage());
        }
        
        try{
        // Get an instance of the parser
        parser = new DOMParser();

        // create InputSource from string containing xml response
        reader = new StringReader(response);

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
        AascFSResponseStatus responseStatus = new AascFSResponseStatus();
        String code="";
        String description = "";
            try {
                code = AascXmlParser.getValue(root, "code");
//                System.out.println("code:::::"+code);
                description = AascXmlParser.getValue(root, "description");
                logger.info("description:::::"+description);
                responseStatus.setCode(code);
                responseStatus.setDescription(description);
            } catch (Exception ex) {
                logger.severe("error getting severity: " + ex.getMessage());
            }
            aascFreightShopInfo.setResponseStatus(responseStatus);
            
            String carrierServiceCode = "";
            String carrierServiceName = "";
            String shippingRate = "";
            String shippingTransitTime = "";
            String sortOrder = "";
            try {
                node = 
                    root.selectSingleNode("/Envelope/Body/AAFreightShopRequestResponse/AAFreightShopResponse/carrierServiceLevels");
                
                NodeList carrierServiceLevelList = node.getChildNodes();
                LinkedList aascFSCarrierServiceLevelsArry = new LinkedList();
                for (int s = 0; s < carrierServiceLevelList.getLength(); s++) {
                    Node childNode = carrierServiceLevelList.item(s);
                    AascFSCarrierServiceLevels aascFSCarrierServiceLevels = new AascFSCarrierServiceLevels();
                    if (childNode.getNodeName().equalsIgnoreCase("carrierServiceLevel")) {
                        
                        carrierServiceCode = AascXmlParser.getValue((Element)carrierServiceLevelList.item(s),
                                                               "carrierServiceCode");
                        carrierServiceName = AascXmlParser.getValue((Element)carrierServiceLevelList.item(s),
                                                               "carrierServiceName");
                        shippingRate = AascXmlParser.getValue((Element)carrierServiceLevelList.item(s),
                                                               "shippingRate");
                        shippingTransitTime = AascXmlParser.getValue((Element)carrierServiceLevelList.item(s),
                                                               "shippingTransitTime");
                        sortOrder = AascXmlParser.getValue((Element)carrierServiceLevelList.item(s),
                                                               "sortOrder");
//                        System.out.println("shipping rate:::"+shippingRate+"shipping transit time:::::"+shippingTransitTime+"sortorder:::"+sortOrder);
                        aascFSCarrierServiceLevels.setCarrierServiceCode(carrierServiceCode);
                        aascFSCarrierServiceLevels.setCarrierServiceName(carrierServiceName);
                        aascFSCarrierServiceLevels.setShippingRate(shippingRate);    
                        aascFSCarrierServiceLevels.setShippingTransitTime(shippingTransitTime);
                        aascFSCarrierServiceLevels.setSortOrder(sortOrder);
                    
                    }
                    aascFSCarrierServiceLevelsArry.add(aascFSCarrierServiceLevels);
                }
                aascFreightShopInfo.setCarrierServiceLevels(aascFSCarrierServiceLevelsArry);
            } catch (Exception e) { 
                logger.info("Exception in Carrier Service Levels :" + e.getMessage());
            }
            
            String carrierName = "";
            String carrierStatus = "";
            try {
                node = 
                    root.selectSingleNode("/Envelope/Body/AAFreightShopRequestResponse/AAFreightShopResponse/carriersIncluded");
                NodeList carrierServiceLevelList = node.getChildNodes();
                LinkedList aascFSCarriersIncludedArray = new LinkedList();
                for (int s = 0; s < carrierServiceLevelList.getLength(); s++) {
                    AascFSCarriersIncluded aascFSCarriersIncluded = new AascFSCarriersIncluded();
                    Node childNode = carrierServiceLevelList.item(s);
                    if (childNode.getNodeName().equalsIgnoreCase("carrier")) {
                        carrierName = AascXmlParser.getValue((Element)carrierServiceLevelList.item(s),
                                                               "carrierName");
                        carrierStatus = AascXmlParser.getValue((Element)carrierServiceLevelList.item(s),
                                                               "carrierStatus");
//                        System.out.println("carrierName:::"+carrierName+"carrierStatus:::::"+carrierStatus);
                        aascFSCarriersIncluded.setCarrier(carrierName);    
                        aascFSCarriersIncluded.setCarrierStatus(carrierStatus);
                    }
                    aascFSCarriersIncludedArray.add(aascFSCarriersIncluded);
                }
                aascFreightShopInfo.setCarriersIncluded(aascFSCarriersIncludedArray);
            } catch (Exception e) { 
                logger.info("Exception in Carriers included: " + 
                            e.getMessage());
            }
            
            
        
        }catch(Exception e){
            logger.severe("Exception :"+e.getMessage());
        }
        
        logger.info("Exit parseResponse()");
        return aascFreightShopInfo;
    }
    
    /**
    * This method used to disable ssl verification for https.
    */
    
    private static void disableSslVerification() {
        try
        {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] 
            {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
    //                public void checkClientTrusted(X509Certificate[] certs, String authType) {
    //                }
    //                public void checkServerTrusted(X509Certificate[] certs, String authType) {
    //                }
              public javax.net.ssl.X509KeyManager getX509KeyManager()
              {
                return null;
              }
              public void checkClientTrusted(java.security.cert.X509Certificate[] certs, 
                                             String authType)
              {
              }
              public void checkServerTrusted(java.security.cert.X509Certificate[] certs, 
                                             String authType)
              {
              }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (Exception e1) 
            {
              e1.printStackTrace();
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException ne) {
            ne.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
    * This method used to write string to file.
    * @param str, file String
    */
    private void writeOutputFile(String str, String file) throws Exception {
        FileOutputStream fout = new FileOutputStream(file);
        try {
            fout.write(str.getBytes());
        } catch (Exception ex) {
            logger.severe("Exception :"+ex.getMessage());
        } finally {
            try {
                fout.close();
            } catch (Exception ex) {
                logger.severe("Exception :"+ex.getMessage());
            }

        }
    }
    private String encode(String src) {
        src = replaceStr(src, "&", "&amp;");
        src = replaceStr(src, "\"", "&quot;");
        src = replaceStr(src, "'", "&apos;");
        src = replaceStr(src, "<", "&lt;");
        src = replaceStr(src, ">", "&gt;");
        return src;
    }
    private String replaceStr(String src, String oldPattern, 
                              String newPattern) {

        String dst = ""; // the new bult up string based on src
        int i; // index of found token
        int last = 0; // last valid non token string data for concat  
        boolean done = false; // determines if we're done.

        if (src != null) {
            // while we'er not done, try finding and replacing
            while (!done) {
                // search for the pattern...
                i = src.indexOf(oldPattern, last);
                // if it's not found from our last point in the src string....
                if (i == -1) {
                    // we're done.
                    done = true;
                    // if our last point, happens to be before the end of the string
                    if (last < src.length()) {
                        // concat the rest of the string to our dst string
                        dst = dst.concat(src.substring(last, (src.length())));
                    }
                } else {
                    // we found the pattern
                    if (i != last) {
                        // if the pattern's not at the very first char of our searching point....
                        // we need to concat the text up to that point..
                        dst = dst.concat(src.substring(last, i));
                    }
                    // update our last var to our current found pattern, plus the lenght of the pattern
                    last = i + oldPattern.length();
                    // concat the new pattern to the dst string
                    dst = dst.concat(newPattern);
                }
            }
        } else {
            dst = src;
        }
        // finally, return the new string
        return dst;
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
}
