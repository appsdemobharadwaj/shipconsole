 /*
   * @(#)AascUpsShipmentInfo.java        09/04/2007
   * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
   * All rights reserved.
   */
 package com.aasc.erp.carrier;

 import com.aasc.erp.bean.AascShipmentHeaderInfo;
 import com.aasc.erp.bean.AascShipmentOrderInfo;
 import com.aasc.erp.bean.AascShipmentPackageInfo;
 /*import com.aasc.erp.model.AascDeliveryInfo;
 import com.aasc.erp.model.AascHeaderInfo;
 import com.aasc.erp.model.AascPackageInfo;*/
 import com.aasc.erp.bean.AascProfileOptionsBean;
 import com.aasc.erp.util.AascLogger;

 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.StringReader;

 import java.util.Date;
 import java.util.HashMap;
 import java.util.LinkedList;
 import java.util.ListIterator;
 import java.util.StringTokenizer;
 import java.util.logging.Logger;

 import oracle.xml.parser.v2.DOMParser;
 import oracle.xml.parser.v2.XMLDocument;
 import oracle.xml.parser.v2.XMLElement;

 import org.w3c.dom.Element;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;

 import org.xml.sax.InputSource;


 /**
  * AascUpsShipmentInfo Class is used to parse the response and
  * set the parsed data to header bean and package bean.
  * @author      
  * @version - 1
  *@since 
  *          17/12/2014  Eshwari M      Renamed adhoc to shipment for SC Lite
  *          19/01/2015  Suman G        Removed unused comments in history and code.
  *          10/07/2015  Suman G        Changed condition to fix zpl label printing by Padmavathi.
  *          17/11/2015  Y Pradeep      Modifed code to stop printing EPL labels twice and resolved printing label in GIF format with doctab top. Bugs #3968, #3959, #3917 and #3918.
  */
 public class AascUpsShipmentInfo {
     private static Logger logger = 
         AascLogger.getLogger("AascCarrierShipmentInfo"); // Logger object for issuing logging requests
     private String displayMessage = ""; // String containing status of response
     private String errorDescription = ""; // String containing error message  
     private String errorSeverity = ""; // String containing error message  
     private String errorCode = ""; // String containing error message  
     private String shipmentDigest = "";
     private StringReader reader = null; // String reader object which takes String as input and converts it into character stream 
     private InputSource input = null; // This object is used to encapsulate character stream into single object
     private DOMParser parser = null; // This object is used to parse xml document 
     private XMLDocument xmlDocument = null; // This encapsulates xml document
     private XMLElement root = null; // This encapsulates xml element 
     private Node node = null; // It represents single node in dom tree
     private NodeList nodeList = null; // It represents ordered collection of tracking nodes    

     private LinkedList packageList = null; // Linked list containing package bean objects
     private ListIterator packageListIterator = null;
     private int packageSize = 0; // Indicates  number of packages in the delivery    
     private String labelPath =""; // holds the path where the label file needs to be placed
     private String responseStatus = ""; // holds the status of the response
     private String unescapedTotalShipmentCharges = ""; // indicates shipment cost with any special characters like comma
     private String totalShipmentCharges = ""; // indicates total shipment charges
     private String totalShipmentChargesCurrCode = ""; // holds the currency code of the shipment charges
     private String unescapedTotalFreightCharges = "";
     private String totalFreightCharges = "";
     private String wayBillNumber = ""; // contains parsed wayBillNumber of the delivery    
     private NodeList labelNodes = null; // It represents ordered collection of nodes
     private String label = ""; // labelstring that holds label from parsed response
     private HashMap parsedDataHashMap = null;
     private String errorLocation = "";
     private String labelFormatExtension = "";
     
     private byte[] parsedLabel; // holds decode label

     private FileOutputStream fout = 
         null; // file output stream to write label to specified file
     private NodeList surchargeList = null; // added by vandana on 17/05/2007
     double declaredVal;
     boolean highValFlag;
     private String intlDoc = "";
     private byte[] parsedIntlDoc;
     private NodeList intlDocNodes = null;
     private String customerContext = "";
     private String publishedHandlingCharges = "";
     private String handlingCharges = "";
     
     private float biledWeight;
     private String publisheCharges = "";
     private Node node1 = null;
     private Node node2 = null;
     
     public AascUpsShipmentInfo() {
     }

     /** This method can replace the null values with nullString.
      * @return String that is ""
      * @param obj object of type Object
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
      * @param  str String as input 
      * @return returns String with required format
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
      * @param str takes String containing from substring which is replaced by to substring 
      * @param from  a substring which has to be replaced
      * @param to  a substring which replaces other substring
      * @return returns String with replaced values
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


     AascShipmentPackageInfo aascShipmentPackageInfo = null;


     HashMap parseResponse(AascShipmentHeaderInfo aascShipmentHeaderInfo, 
                           AascShipmentOrderInfo aascShipmentOrderInfo, 
                           String labelFormat, String shipmentResponse, 
                           String transactionType, 
                           AascProfileOptionsBean aascProfileOptionsInfo, 
                           String docTabLocation,String cloudLabelPath) {
         logger.info("Entered parseResponse() for shipment");
         parsedDataHashMap = new HashMap();

                
         labelPath=cloudLabelPath;
         

         try {
             packageList = aascShipmentOrderInfo.getShipmentPackageInfo();
             packageSize = packageList.size();

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

             // Print or extract document fields(root element)
             root = (XMLElement)xmlDocument.getDocumentElement();

             // extract info and save to AASC schema.
             responseStatus = 
                     (AascXmlParser.getValue(root, "ResponseStatusDescription")).toLowerCase();
             parsedDataHashMap.put("ResponseStatusDescription", responseStatus);


             if (responseStatus != null && responseStatus != "" && 
                 responseStatus.equalsIgnoreCase("success")) {

                
                 if (transactionType.equalsIgnoreCase("ShipConfirm")) {
                     
                     String errorChk = "";
                     String warningChk = "";
                     warningChk = aascShipmentHeaderInfo.getWarningChk();
                     try {
                         warningChk.substring(0, 1);
                     } catch (Exception e) {
                         warningChk = "";
                     }
                     
                     errorChk = 
                             (AascXmlParser.getValue(root, "ErrorSeverity")).toLowerCase();


                     if (((!warningChk.equalsIgnoreCase("continue")) && 
                          (!errorChk.equalsIgnoreCase("warning"))) || 
                         (warningChk.equalsIgnoreCase("continue")) && 
                         (errorChk.equalsIgnoreCase("warning"))) {
                         logger.info("Normal Execution");
                         node = 
 root.selectSingleNode("/ShipmentConfirmResponse/ShipmentDigest/text()");
                         shipmentDigest = node.getNodeValue();
                         parsedDataHashMap.put("ShipmentDigest", 
                                               shipmentDigest);
                         parsedDataHashMap.put("ShipmentDigest", 
                                               shipmentDigest);
                         customerContext = 
                                 root.selectSingleNode("/ShipmentConfirmResponse/Response/TransactionReference/CustomerContext/text()").getNodeValue();
                         parsedDataHashMap.put("CustomerContext", 
                                               customerContext);
                         String updatedShipmentCostStr = "";

                         String updatedFreightCostStr = "";
                         
                         if (nullStrToSpc(aascProfileOptionsInfo.getAcctNumNegotiatedFlag()).equalsIgnoreCase("Y")) {
                             if (aascProfileOptionsInfo.getNonDiscountedCost().equalsIgnoreCase("Y")) {
                                 node1 = 
                                         root.selectSingleNode("/ShipmentConfirmResponse/NegotiatedRates/NetSummaryCharges/GrandTotal/MonetaryValue/text()");
                                 updatedShipmentCostStr = node1.getNodeValue();
                                 updatedShipmentCostStr = 
                                         escape(updatedShipmentCostStr);

                                 updatedFreightCostStr = node1.getNodeValue();
                                 updatedFreightCostStr = 
                                         escape(updatedShipmentCostStr);
                                 
                             } else {
                                 node1 = 
                                         root.selectSingleNode("/ShipmentConfirmResponse/NegotiatedRates/NetSummaryCharges/GrandTotal/MonetaryValue/text()");
                                 updatedShipmentCostStr = node1.getNodeValue();
                                 updatedShipmentCostStr = 
                                         escape(updatedShipmentCostStr);
                                 
                                 node2 = 
                                         root.selectSingleNode("/ShipmentConfirmResponse/ShipmentCharges/TotalCharges/MonetaryValue/text()");
                                 updatedFreightCostStr = node2.getNodeValue();
                                 updatedFreightCostStr = 
                                         escape(updatedFreightCostStr);
                                 
                             }
                         } else {
                             
                             node1 = 
                                     root.selectSingleNode("/ShipmentConfirmResponse/ShipmentCharges/TotalCharges/MonetaryValue/text()");
                             updatedShipmentCostStr = node1.getNodeValue();
                             updatedShipmentCostStr = 
                                     escape(updatedShipmentCostStr);

                             updatedFreightCostStr = node1.getNodeValue();
                             updatedFreightCostStr = 
                                     escape(updatedShipmentCostStr);
                             
                         }
                         if (updatedShipmentCostStr != null && 
                             !updatedShipmentCostStr.equalsIgnoreCase("")) {
                             double updatedShipmentCost = 
                                 Double.parseDouble(updatedShipmentCostStr);

                             aascShipmentHeaderInfo.setShipmentCost(updatedShipmentCost);
                             aascShipmentHeaderInfo.setFreightCost(updatedShipmentCost);
                             packageListIterator = packageList.listIterator();

                             
                             return parsedDataHashMap;
                         }

                         if (updatedFreightCostStr != null && 
                             !updatedFreightCostStr.equalsIgnoreCase("")) {
                             double updatedFreightCost = 
                                 Double.parseDouble(updatedFreightCostStr);

                             aascShipmentHeaderInfo.setFreightCost(updatedFreightCost);
                             packageListIterator = packageList.listIterator();

                            return parsedDataHashMap;
                         }
                     } else {
                         
                         parsedDataHashMap.put("ResponseStatusDescription", 
                                               "Error");
                         errorSeverity = 
                                 AascXmlParser.getValue(root, "ErrorSeverity");
                         errorCode = AascXmlParser.getValue(root, "ErrorCode");
                         errorDescription = 
                                 AascXmlParser.getValue(root, "ErrorDescription");
                         errorLocation = 
                                 AascXmlParser.getValue(root, "ErrorLocationElementName");
                         displayMessage = 
                                 "Error Severity: " + errorSeverity + " Error Code: " + 
                                 errorCode + " Error Description: " + 
                                 errorDescription + " Error Location: " + 
                                 errorLocation;
                          node = root.selectSingleNode("/ShipmentConfirmResponse/ShipmentDigest/text()");
                          shipmentDigest = node.getNodeValue();
                         parsedDataHashMap.put("ShipmentDigest", 
                                               shipmentDigest); 

                         aascShipmentHeaderInfo.setErrorChk(errorChk);
                         aascShipmentHeaderInfo.setError(errorDescription);
                         
                         parsedDataHashMap.put("ErrorDetails", displayMessage);
                         return parsedDataHashMap;
                     }
                 } //end of if(transactionType.equalsIgnoreCase("ShipConfirm")) for shipment
                 else if (transactionType.equalsIgnoreCase("ShipAccept")) {

                     logger.info("transactionType is ShipAccept");
                     if (nullStrToSpc(aascProfileOptionsInfo.getAcctNumNegotiatedFlag()).equalsIgnoreCase("Y")) {
                         if (aascProfileOptionsInfo.getNonDiscountedCost().equalsIgnoreCase("Y")) {
                             try {
                                 node1 = 
                                         root.selectSingleNode("/ShipmentAcceptResponse/ShipmentResults/NegotiatedRates/NetSummaryCharges/GrandTotal/MonetaryValue/text()");
                                 unescapedTotalShipmentCharges = 
                                         node1.getNodeValue();
                                 unescapedTotalFreightCharges = 
                                         node1.getNodeValue();
                                 
                             } catch (Exception e) {
                                 double unescapedTotalShipmentChargesTest = 
                                     aascShipmentHeaderInfo.getShipmentCost();
                                 
                                 unescapedTotalShipmentCharges = 
                                         String.valueOf(unescapedTotalShipmentChargesTest);
                                 double unescapedTotalFreightChargesTest = 
                                     aascShipmentHeaderInfo.getShipmentCost();
                                 unescapedTotalFreightCharges = 
                                         String.valueOf(unescapedTotalFreightChargesTest);

                             }

                         } else {
                             try {
                                 node1 = 
                                         root.selectSingleNode("/ShipmentAcceptResponse/ShipmentResults/NegotiatedRates/NetSummaryCharges/GrandTotal/MonetaryValue/text()");
                                 unescapedTotalShipmentCharges = 
                                         node1.getNodeValue();
                                 
                             } catch (Exception e) {

                                 double unescapedTotalShipmentChargesTest = 
                                     aascShipmentHeaderInfo.getShipmentCost();
                                 
                                 unescapedTotalShipmentCharges = 
                                         String.valueOf(unescapedTotalShipmentChargesTest);
                             }
                             node2 = 
                                     root.selectSingleNode("/ShipmentAcceptResponse/ShipmentResults/ShipmentCharges/TotalCharges/MonetaryValue/text()");
                             unescapedTotalFreightCharges = 
                                     node2.getNodeValue();
                             
                         }
                     } else {
                         node1 = 
                                 root.selectSingleNode("/ShipmentAcceptResponse/ShipmentResults/ShipmentCharges/TotalCharges/MonetaryValue/text()");
                         unescapedTotalShipmentCharges = node1.getNodeValue();
                         unescapedTotalFreightCharges = node1.getNodeValue();
                         
                     }
                     String warningChk1 = "";
                     warningChk1 = aascShipmentHeaderInfo.getWarningChk();
                     try {
                         warningChk1.substring(0, 1);
                     } catch (Exception e) {
                         warningChk1 = "";
                     }


                     if (warningChk1.equalsIgnoreCase("continue")) {
                         logger.info("warningChk1  is continue");
                         node1 = 
                                 root.selectSingleNode("/ShipmentAcceptResponse/ShipmentResults/ShipmentCharges/TotalCharges/MonetaryValue/text()");
                         unescapedTotalShipmentCharges = node1.getNodeValue();
                         
                         unescapedTotalFreightCharges = node1.getNodeValue();

                         String errorChkTest = "";
                         aascShipmentHeaderInfo.setErrorChk(errorChkTest);
                     }
                     totalShipmentCharges = 
                             escape(unescapedTotalShipmentCharges);

                     totalFreightCharges = escape(unescapedTotalFreightCharges);

                     aascShipmentHeaderInfo.setShipmentCost(Double.parseDouble(totalShipmentCharges));

                     // Add the below statement by ravip@24-Apr-2007 to set the Freight cost.
                     aascShipmentHeaderInfo.setFreightCost(Double.parseDouble(totalFreightCharges));
                     
                     node = 
 root.selectSingleNode("/ShipmentAcceptResponse/ShipmentResults/ShipmentCharges/TotalCharges/CurrencyCode/text()");
                     totalShipmentChargesCurrCode = node.getNodeValue();
                     node = 
 root.selectSingleNode("/ShipmentAcceptResponse/ShipmentResults/ShipmentCharges/TotalCharges/MonetaryValue/text()");

                     publishedHandlingCharges = node.getNodeValue();

                     node = 
 root.selectSingleNode("/ShipmentAcceptResponse/ShipmentResults/ShipmentCharges/ServiceOptionsCharges/MonetaryValue/text()");

                     handlingCharges = node.getNodeValue();

                     node = 
 root.selectSingleNode("/ShipmentAcceptResponse/ShipmentResults/ShipmentCharges/TransportationCharges/MonetaryValue/text()");

                     publisheCharges = node.getNodeValue();


                     // get waybill Number  which will be set to the first tracking number.


                     nodeList = 
                             root.selectNodes("/ShipmentAcceptResponse/ShipmentResults/PackageResults/TrackingNumber/text()");
                     wayBillNumber = nodeList.item(0).getNodeValue();


                     aascShipmentHeaderInfo.setWayBill(wayBillNumber);
                     


                     labelNodes = 
                             root.selectNodes("/ShipmentAcceptResponse/ShipmentResults/PackageResults/LabelImage");


                     surchargeList = 
                             root.selectNodes("/ShipmentAcceptResponse/ShipmentResults/PackageResults/ServiceOptionsCharges/MonetaryValue/text()");

                     try {
                         
                         biledWeight = 
                                 Float.parseFloat(root.selectSingleNode("/ShipmentAcceptResponse/ShipmentResults/BillingWeight/Weight/text()").getNodeValue());
                     } catch (Exception e) {
                         biledWeight = 0;
                         logger.info("biledWeight Exception " + e);
                     }
                     //Added
                     packageListIterator = packageList.listIterator();
    
                     
                     // get package tracking numbers and msn numbers and set them to package bean simultaneously
                     if (nodeList != null) {
                         for (int nodeCount = 0, packageCount = 1; 
                              nodeCount < nodeList.getLength() && 
                              packageCount <= packageSize; 
                              nodeCount++, packageCount++) {
                             aascShipmentPackageInfo = 
                                     (AascShipmentPackageInfo)packageListIterator.next();
                             aascShipmentPackageInfo.setTrackingNumber(nodeList.item(nodeCount).getNodeValue());
                             String trackingNum = 
                                 nullStrToSpc(nodeList.item(nodeCount).getNodeValue());

                             String pkgSurCharge = 
                                 surchargeList.item(nodeCount).getNodeValue();

                             aascShipmentPackageInfo.setSurCharges(Double.parseDouble(pkgSurCharge));

                             declaredVal = 
                                     aascShipmentPackageInfo.getPackageDeclaredValue();

                             if (declaredVal > 999) {
                                 highValFlag = true;
                             }
                             if (labelNodes != null) {
                                 label = 
                                         nullStrToSpc(AascXmlParser.getValue((Element)labelNodes.item(nodeCount), 
                                                                             "GraphicImage"));
                                 parsedLabel = AascBase64.decode(label);
                                 
   String Paymethod = "SHP";
   if(aascShipmentHeaderInfo.getCarrierPaymentMethod().equalsIgnoreCase("RECIPIENT")){
       Paymethod="REC";  
   }
                                 String phrase="";   
                                  
                                  if(labelFormat.equalsIgnoreCase("ZPL")) {
                                   phrase = 
                                      new String("\n^FO10,1270^ADN,18,10^FD" + 
                                                 aascShipmentHeaderInfo.getCarrierAccountNumber() + "^FS"+
                                                 "\n^FO200,1270^ADN,18,10^FD" + 
                                                 aascShipmentHeaderInfo.getShipmentDate() + "^FS"+
                                                 "\n^FO400,1270^ADN,18,10^FDACT WT " + 
                                                 aascShipmentHeaderInfo.getPackageWeight() + 
                                                 " LBS^FS\n^FO670,1270^ADN,18,10^FD#PK " + 
                                                 packageCount + "^FS"+
                                                 "\n^FO10,1295^ADN,18,10^FDSERVICE " + 
                                                 aascShipmentHeaderInfo.getShipMethodMeaning() + "^FS"+
                                                 "\n^FO530,1295^ADN,18,10^FDBL WT " + 
                                                 biledWeight + 
                                                 " LBS^FS\n^FO10,1320^ADN,18,10^FDTRACKING# " + 
                                                 trackingNum + "^FS"+
                                                 "\n^FO520,1320^ADN,18,10^FDALL CURRENCY USD^FS\n^FO10,1345^ADN,18,10^FDREFERENCE1 " + 
                                                 aascShipmentHeaderInfo.getReference1() + "^FS"+
                                                 "\n^FO10,1370^ADN,18,10^FDREFERENCE2 " + 
                                                 aascShipmentHeaderInfo.getReference2() + "^FS"+
                                                 "\n^FO10,1415^ADN,18,10^FDHANDLING CHARGE " + 
                                                 handlingCharges + "^FS"+
                                                 "\n^FO520,1415^ADN,18,10^FDFRT: "+Paymethod+"\n^FO10,1440^ADN,18,10^FDSHIPMENT PUB RATE CHARGES^FS\n^FO550,1440^ADN,18,10^FDSVC " + 
                                                 publisheCharges + 
                                                 " USD^FS\n^FO10,1465^ADN,18,10^FDDV " + 
                                                 declaredVal + "^FS"+
                                                 "\n^FO300,1465^ADN,18,10^FDCOD " + 
                                                 aascShipmentPackageInfo.getCodAmt() + 
                                                 "^FS\n^FO600,1465^ADN,18,10^FDRS  0.00^FS\n^FO10,1490^ADN,18,10^FDDC 0.00^FS\n^FO300,1490^ADN,18,10^FDDGD 0.00^FS\n^FO10,1515^ADN,18,10^FDAH 0.00^FS\n^FO300,1515^ADN,18,10^FDPR  0.00^FS\n^FO600,1515^ADN,18,10^FDROD 0.00^FS\n^FO10,1540^ADN,18,10^FDTOT PUB CHG " + 
                                                 publisheCharges + 
                                                 "^FS\n^FO450,1540^ADN,18,10^FDPUB+HANDLING " + 
                                                 publishedHandlingCharges + 
                                                 "^FS" + "\n^XZ^XZ"+ "\n");
                                         logger.info("phrase::" + phrase);    
                                                     
                                             
                                     } else if(labelFormat.equalsIgnoreCase("EPL")){
                                     
                                     phrase = 
                                     new String("\nA10,1325,0,3,1,1,N,\"" + 
                                                aascShipmentHeaderInfo.getCarrierAccountNumber() + 
                                                "\"\nA200,1325,0,3,1,1,N,\"" + 
                                                aascShipmentHeaderInfo.getShipmentDate() + 
                                                "\"\nA400,1325,0,3,1,1,N,\"ACT WT " + 
                                                aascShipmentPackageInfo.getWeight() + 
                                                " LBS\"\nA670,1325,0,3,1,1,N,\"#PK " + 
                                                packageCount + 
                                                "\"\nA10,1350,0,3,1,1,N,\"SERVICE " + 
                                                aascShipmentHeaderInfo.getShipMethodMeaning() + 
                                                "\"\nA400,1350,0,3,1,1,N,\"BILL WT " + 
                                                biledWeight + 
                                                " LBS\"\nA10,1375,0,3,1,1,N,\"TRACKING# " + 
                                                trackingNum + 
                                                "\"\nA520,1375,0,3,1,1,N,\"ALL CURRENCY USD\"\nA10,1400,0,3,1,1,N,\"REFERENCE1 " + 
                                                aascShipmentHeaderInfo.getReference1() + 
                                                "\"\nA10,1425,0,3,1,1,N,\"REFERENCE2 " + 
                                                aascShipmentHeaderInfo.getReference2() + 
                                                "\"\nA10,1470,0,3,1,1,N,\"HANDLING CHARGE " + 
                                                handlingCharges + 
                                                "\"\nA520,1470,0,3,1,1,N,\"FRT: SHP\"\nA10,1495,0,3,1,1,N,\"SHIPMENT PUB RATE CHARGES \"\nA550,1495,0,3,1,1,N,\"SVC " + 
                                                publisheCharges + 
                                                " USD\"\nA10,1520,0,3,1,1,N,\"DV " + 
                                                aascShipmentPackageInfo.getPackageDeclaredValue() + 
                                                "\"\nA300,1520,0,3,1,1,N,\"COD " + 
                                                aascShipmentPackageInfo.getCodAmt() + 
                                                "\"\nA600,1520,0,3,1,1,N,\"RS  0.00\"\nA10,1545,0,3,1,1,N,\"DC 0.00\"\nA300,1545,0,3,1,1,N,\"DGD 0.00\"\nA10,1570,0,3,1,1,N,\"AH 0.00\"\nA300,1570,0,3,1,1,N,\"PR  0.00\"\nA600,1570,0,3,1,1,N,\"ROD 0.00\"\nA10,1595,0,3,1,1,N,\"TOT PUB CHG " + 
                                                publisheCharges + 
                                                "\"\nA450,1595,0,3,1,1,N,\"PUB+HANDLING " + 
                                                publishedHandlingCharges + 
                                                "\"" + "\n");
                                                }
                                 byte[] bytes = phrase.getBytes();
                                 if (!(label.equals("")) && label != null) {
                                     try {
                                         File f = 
                                             new File(labelPath + trackingNum + 
                                                      labelFormatExtension);
                                         fout = new FileOutputStream(f);
                                         fout.write(parsedLabel);
                                         fout.close();
                                         if (labelFormatExtension.equalsIgnoreCase("") && 
                                             (docTabLocation.equalsIgnoreCase("BOTTOM") || 
                                              docTabLocation.equalsIgnoreCase("TOP"))) {
                                             logger.info("in labelFormatExtension.equalsIgnoreCase(\"\") && docTabLocation.equalsIgnoreCase(\"BOTTOM\")");
                                             byte[] parsedLabelTemp = 
                                                 new byte[(int)f.length()];
                                             byte[] parsedLabelTemp1 = 
                                                 new byte[(int)f.length()];
                                             byte[] parsedLabelTemp2 = null;
                                             //byte[] b = new byte[(int)f.length()];


                                             try {
                                                 FileInputStream fileInputStream = 
                                                     new FileInputStream(f);
                                                 fileInputStream.read(parsedLabelTemp);

                                                 int len = 0;
                                                 int labelIndex = 0;
                                                 try {
                                                     for (int p1Index = 
                                                          parsedLabelTemp.length; 
                                                          p1Index > 0; 
                                                          p1Index--) {
                                                         len++;
                                                         if ((parsedLabelTemp[p1Index - 
                                                              1] == 80) && 
                                                             (parsedLabelTemp[p1Index] == 
                                                              49) && ("EPL".equalsIgnoreCase(labelFormat))) {
                                                             
                                                             labelIndex = 
                                                                     p1Index - 
                                                                     1;
                                                             len++;
                                                             break;
                                                         }
                                                     }
                                                     

                                                     if ((parsedLabelTemp[labelIndex] == 
                                                          80) && 
                                                         (parsedLabelTemp[labelIndex + 
                                                          1] == 49) && ("EPL".equalsIgnoreCase(labelFormat))) {
                                                         for (int i = 0; 
                                                              i < labelIndex; 
                                                              i++) {
                                                             parsedLabelTemp1[i] = 
                                                                     parsedLabelTemp[i];
                                                         }
                                                     } else {
                                                         for (int i = 0; 
                                                              i < parsedLabelTemp.length; 
                                                              i++) {
                                                             parsedLabelTemp1[i] = 
                                                                     parsedLabelTemp[i];
                                                         }
                                                     }

                                                     parsedLabelTemp2 = 
                                                             new byte[len];
                                                     int l = 0;
                                                     for (int k = labelIndex; 
                                                          k <
                                                          parsedLabelTemp.length; 
                                                          k++) {
                                                         parsedLabelTemp2[l] = 
                                                                 parsedLabelTemp[k];

                                                         l++;
                                                     }
                                                 } catch (Exception e) {
                                                     logger.severe("Inside the label printing block " + 
                                                                   e);
                                                 }


                                                 FileOutputStream fout1 = 
                                                     new FileOutputStream(labelPath + 
                                                                          trackingNum + 
                                                                          labelFormatExtension);
                                                 if (docTabLocation.equalsIgnoreCase("BOTTOM")) {
                                                     if(labelFormat.equalsIgnoreCase("ZPL")){
                                                         String str=new String(parsedLabelTemp1);
                                                         String str1=str.substring(0,str.indexOf("^XZ^XZ"));
                                                         StringBuffer strBuffer=new StringBuffer();
                                                         strBuffer.append(str1);
                                                         strBuffer.append(new String(bytes));
                                                         str1=strBuffer.toString();
                                                         parsedLabelTemp1=str1.getBytes();
                                                         fout1.write(parsedLabelTemp1);
                                                     }
                                                     else if(labelFormat.equalsIgnoreCase("EPL")){
                                                         fout1.write(parsedLabelTemp1);
                                                         fout1.write(bytes);
                                                     }
                                                 } else if (docTabLocation.equalsIgnoreCase("TOP")&&!(labelFormat.equalsIgnoreCase("GIF"))) {
                                                     fout1.write(bytes);
                                                     fout1.write(parsedLabelTemp1);
                                                 }
                                                 
                                                 if(!"ZPL".equalsIgnoreCase(labelFormat)){  
                                                    fout1.write(parsedLabelTemp2);
                                                 }
                                                // fout1.write(parsedLabelTemp2);
                                                 fout1.close();

                                                
                                             } catch (FileNotFoundException e) {
                                                 logger.severe("File Not Found." + 
                                                               e.getMessage());
                                                 e.printStackTrace();
                                             }

                                         }

                                         //writeOutputFile(parsedLabel,labelPath+wayBillNumber+"_"+(nodeCount+1)+labelFormatExtension);
                                         logger.info("after writing label to file:" + 
                                                     labelPath + wayBillNumber + 
                                                     labelFormatExtension);
                                         
                                                     
                                     } catch (FileNotFoundException fileNotFoundException) {

                                         logger.severe("file path to which label needs to be written is not found" + 
                                                       "\n file name:" + 
                                                       labelPath + 
                                                       wayBillNumber + (0) + 
                                                       labelFormatExtension);
                                                  
                                                
                                     }
                                 }
                             } //end of if(labelNodes!=null)


                             try {
                                 intlDocNodes = 
                                         root.selectNodes("/ShipmentAcceptResponse/ShipmentResults/Form/Image");
                                 if (intlDocNodes != null) {
                                     intlDoc = 
                                             nullStrToSpc(AascXmlParser.getValue((Element)intlDocNodes.item(nodeCount), 
                                                                                 "GraphicImage"));
                                     parsedIntlDoc = AascBase64.decode(intlDoc);
                                     if (!(intlDoc.equals("")) && 
                                         intlDoc != null) {

                                         String orderNumberIntl = 
                                             aascShipmentHeaderInfo.getOrderNumber();
                                         Date shippedDateIntl = 
                                             aascShipmentHeaderInfo.getShipmentDate();

                                         String shippedDateIntlStr = 
                                             shippedDateIntl.toString().replaceAll("-", 
                                                                                   "_");
                                         String labelPathIntlDoc = "";
                                         labelPathIntlDoc = 
                                                 labelPath + "intlDocs/";

                                         logger.info("labelPathIntlDoc==" + 
                                                     labelPathIntlDoc);
                                         String upsIntlDoc = 
                                             orderNumberIntl + "_Shipment_" + 
                                             shippedDateIntlStr + "_UPS.pdf";

                                         try {

                                             fout = 
 new FileOutputStream(labelPathIntlDoc + upsIntlDoc);

                                             fout.write(parsedIntlDoc);

                                             fout.close();
                                   
                                         } catch (FileNotFoundException fileNotFoundException) {
                                             logger.severe("file path to which label needs to be written is not found" + fileNotFoundException.getMessage());
                                         }
                                     }
                                 } // end of if(intlDocNodes!=null)

                             } catch (Exception e) {
                                 logger.info("in intldocs catch" + e);
                             }

                         } //end of for loop
                          
                     }
                 } // end of for loop 
                
             } //end of if (responseStatus != null && responseStatus!=""&& responseStatus.equalsIgnoreCase("success")) 
             else { // ship failed                         
                 errorSeverity = AascXmlParser.getValue(root, "ErrorSeverity");
                 errorCode = AascXmlParser.getValue(root, "ErrorCode");
                 errorDescription = 
                         AascXmlParser.getValue(root, "ErrorDescription");
                 errorLocation = 
                         AascXmlParser.getValue(root, "ErrorLocationElementName");
                 displayMessage = 
                         "Error Severity: " + errorSeverity + " Error Code: " + 
                         errorCode + " Error Description: " + errorDescription + 
                         " Error Location: " + errorLocation;
                 // aascCarrierShipment.setError(errorDescription);
                 logger.severe("error in parsing xml document---" + 
                               displayMessage);
                 parsedDataHashMap.put("ErrorDetails", displayMessage);
             } // else
         } catch (Exception e) {
             logger.severe("Exception in parsing " + transactionType + 
                           " response :" + e.getMessage());
         }
         logger.info("Exit from parseResponse() for shipment");
         return parsedDataHashMap;
     }

     public static String replaceWord(String original, String find, 
                                      String replacement) {
         String result = "";
         String delimiters = "/";
         StringTokenizer st = new StringTokenizer(original, delimiters, true);

         while (st.hasMoreTokens()) {
             String w = st.nextToken();
             if (w.equals(find)) {

                 result = result + replacement;

             } else {
                 result = result + w;
             }
         }
         return result;
     }

     public static void replaceWordinFile(String filename) {
         try {
             File file = new File(filename);
             BufferedReader reader = new BufferedReader(new FileReader(file));
             String line = "", oldtext = "";
             //while((line = reader.readLine()) != null)

             while ((line = reader.readLine()) != null) {

                 if (line.length() != 0) {


                     oldtext += line + "\r\n";
                 }
             }
             reader.close();
             // replace a word in a file
             //String newtext = oldtext.replaceAll("drink", "Love");

             //To replace a line in a file
             String newtext = oldtext.replaceAll("P1", "");
             // newtext = newtext.replaceAll("\n<<","");

             FileWriter writer = new FileWriter(filename);
             writer.write(newtext);
             writer.write("P1");
             writer.close();
         } catch (IOException ioe) {
             ioe.printStackTrace();
         }
     }

 }// end of the class
