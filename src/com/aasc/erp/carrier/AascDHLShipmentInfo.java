
/**
 * AascDHLShipmentInfo Class is used to parse the shipment response from
 * DHL and set the parsed data(shipment cost and tracking numbers)
 * to header bean and package bean.
 * @author      G S Shekar
 * @version - 1
 * @History
 *
 * Date           Resource          Changes
 *--------------------------------------------------------------------------------------------------------- 
 * 17/11/2015     Shiva G           Modified code to use Glabal Schema for DHL 
 */


package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.util.AascLogger;

import java.io.FileOutputStream;
import java.io.StringReader;

//import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
//import java.util.Map;
import java.util.logging.Logger;

//import javax.xml.parsers.DocumentBuilder;

//import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLElement;

//import org.w3c.dom.Document;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import org.xml.sax.InputSource;


public class AascDHLShipmentInfo {

    public AascDHLShipmentInfo() {
    }
    private static Logger logger = 
        AascLogger.getLogger("AascDHLShipmentInfo"); // Logger object for issuing logging requests
    private String displayMessage = ""; // String containing status of response
//    private String errorDescription = ""; // String containing error message  
    private StringReader reader = 
        null; // String reader object which takes String as input and converts it into character stream 
    private InputSource input = 
        null; // This object is used to encapsulate character stream into single object
    private DOMParser parser = 
        null; // This object is used to parse xml document 
    private XMLDocument xmlDocument = null; // This encapsulates xml document
    private XMLElement root = null; // This encapsulates xml element 
    private Node node = null; // It represents single node in dom tree
//    private NodeList nodeList = 
//        null; // It represents ordered collection of tracking nodes   
    
    private LinkedList packageList = 
        null; // Linked list containing package bean objects
//    private int numOfPackages = 
//        0; // Indicates  number of packages in the delivery
//    private String errorCode = 
//        ""; // indicates error code in unsuccessful xml response      
    private String trackingNumber = 
        ""; // indicates parsed tracking number of package in the fedex shipment response
  
//    private String rDHLrackingNumber = "";
  
    private String totalShipmentCharges = 
        "0.00"; // indicates total shipment charges   
    private String totalFreightCharges = 
        "0.00"; // indicates total shipment charges  
////    private String totalShipmentListCharges = 
//        "0.00"; // indicates total shipment charges    
    private String wayBillNumber = 
        ""; // indicates parsed waybill number of delivery in the fedex shipment response
//    private String softErrorType = 
//        ""; // holds soft error(or warning) type returned in response    
//    private ListIterator packageListIterator = 
//        null; // list iterator to traverse through the packages in the delivery
    private String labelPath = "";
    private byte[] parsedLabel;
    private String shipmentCost = "";
    private String productName = "";
//    private String DHLErrorCode = "";
    private FileOutputStream fout = 
        null; // file output stream to write label to specified file
    
    private HashMap hashMap = new HashMap();

    // following variables are used to write decoded label into file
    private String label = ""; // holds parsed label from response
//     private AascProfileOptionsInfo aascProfileOptionsInfo = 
//         null; // profile options information object
    
    
    
    
     HashMap parseWebServiceResponse(String shipmentResponse,
                                     AascShipmentHeaderInfo aascShipmentHeaderInfo,
                                     LinkedList shipPackageInfo, 
                                     String intlFlag, // may require in future
                                     String ajaxcarrierservicelevel,// may require in future
                                     String quoteParsing,
                                     String carrierPayMethod,
                                     String cloudLabelPath) 
     {
         logger.info("Entered DHL parseResponse()");
         
         //logger.info("shipmentResponse "+shipmentResponse);
          shipmentResponse = shipmentResponse.replaceAll("<res:", "<");
          shipmentResponse = shipmentResponse.replaceAll("</res:", "</");
          
            String quoteParseFlag = "";
            String conditionCode = "";
            String conditionData = "";
            String localProductName = "";
            
            
          
         labelPath = cloudLabelPath;
        
                  //labelPath = "D:\\label_dhl\\";
         try {
         
        
//          System.out.println("-------------dhl shipment response parsing parsing----------------------");
          
            //quoteParsing = quoteParsing ;
          String[] parts = quoteParsing.split("###");
          logger.info("quoteParsing "+quoteParsing);
          logger.info("quoteParsing length "+parts.length);
          quoteParseFlag = parts[0];
          localProductName = parts[1];
          shipmentCost = parts[2];
          conditionCode = parts[3];
          conditionData = parts[4];
          
          logger.info("quoteParseFlag : "+quoteParseFlag);
          
          if("success".equalsIgnoreCase(quoteParseFlag)) 
          {
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
            
            
            if(shipmentResponse.contains("ShipmentValidateErrorResponse")) //ShipmentValidateErrorResponse
            {
                node = root.selectSingleNode("/ShipmentValidateErrorResponse/Response/Status/Condition/ConditionCode/text()");
                logger.info("carrier ConditionCode : "+ nullStrToSpc(node.getNodeValue()));
                conditionCode = nullStrToSpc(node.getNodeValue());
                
                node = root.selectSingleNode("/ShipmentValidateErrorResponse/Response/Status/Condition/ConditionData/text()");
                logger.info("node : "+node);
                logger.info("carrier ConditionData : "+ nullStrToSpc(node.getNodeValue()));
                conditionData = nullStrToSpc(node.getNodeValue());
            
                displayMessage = conditionCode+" "+ conditionData;
                logger.severe("errorDescription:" + displayMessage);
                hashMap.put("status", displayMessage);
            }
            else //ErrorResponse
            if(shipmentResponse.contains("ErrorResponse ")) 
            {
                node = root.selectSingleNode("/ErrorResponse/Response/Status/Condition/ConditionCode/text()");
                logger.info("carrier ConditionCode : "+ nullStrToSpc(node.getNodeValue()));
                conditionCode = nullStrToSpc(node.getNodeValue());
                
                node = root.selectSingleNode("/ErrorResponse/Response/Status/Condition/ConditionData/text()");
                logger.info("carrier ConditionData : "+ nullStrToSpc(node.getNodeValue()));
                conditionData = nullStrToSpc(node.getNodeValue());
                
                displayMessage = conditionCode+" "+ conditionData;
                logger.severe("errorDescription:" + displayMessage);
                hashMap.put("status", displayMessage);
            }
            else//success 
            {
                node = root.selectSingleNode("/ShipmentResponse/AirwayBillNumber/text()");
//                System.out.println(" air way bil number "+nullStrToSpc(node.getNodeValue()));
                wayBillNumber = nullStrToSpc(node.getNodeValue());
                
                logger.info("getting label");
                node = root.selectSingleNode("/ShipmentResponse/LabelImage/OutputImage/text()");
                label = nullStrToSpc(node.getNodeValue());
                logger.info("label --- > "+label);
                parsedLabel = AascBase64.decode(label);
                
                try{
                logger.info("label path "+labelPath+wayBillNumber);
                fout = 
                new FileOutputStream(labelPath + wayBillNumber);
                fout.write(parsedLabel);
                fout.close();
                }
                catch(Exception ee) {
                    ee.printStackTrace();
                    logger.info("exception in writing the file");
                }
                
                
                ListIterator packageListIterator = shipPackageInfo.listIterator();
                int packgeNum=1;
                while (packageListIterator.hasNext()) 
                {
                  AascShipmentPackageInfo  aascPackageInfo = 
                                (AascShipmentPackageInfo)packageListIterator.next();
//                    String pkgLineNo = Integer.toString(aascPackageInfo.getPackageNumber());
                    String pkgLineNo =""+packgeNum;
                    String resPkgLineNo = "";
                    logger.info("pkgLineNo : "+pkgLineNo);
                    NodeList pieceList = xmlDocument.getElementsByTagName("Piece");
                    logger.info("------------------------> getLength() "+pieceList.getLength());
                    
                    if (pieceList != null && pieceList.getLength() > 0) 
                    {
                        for (int i = 0; i < pieceList.getLength(); i++) 
                        {
                            Node node = pieceList.item(i);
                    
                            if (node.getNodeType() == Node.ELEMENT_NODE) 
                            {
                    
                                Element e = (Element) node;
                                NodeList pieceNumberList = e.getElementsByTagName("PieceNumber");
                                NodeList licensePlateList = e.getElementsByTagName("LicensePlate");
                               
                                if(pieceNumberList.item(0) != null) 
                                {
                                    resPkgLineNo = nullStrToSpc(pieceNumberList.item(0).getChildNodes().item(0).getNodeValue());
                                    logger.info("resPkgLineNo "+resPkgLineNo);
                                    if(pkgLineNo.equalsIgnoreCase(resPkgLineNo)) 
                                    {
                                    
                                        trackingNumber =  nullStrToSpc(licensePlateList.item(0).getChildNodes().item(0).getNodeValue());//getting tracking number
                                        aascPackageInfo.setTrackingNumber(trackingNumber);
                                        logger.info("trackingNumber "+trackingNumber);
                                      
                                    }
                                }
                                
                                
                            }
                        }
                    }
                    
                    
                    aascPackageInfo.setMasterTrackingNumber(wayBillNumber);
                    aascShipmentHeaderInfo.setWayBill(wayBillNumber);
                    
                    //parsedLabel = AascBase64.decode(label);
                packgeNum++;    
                }//package level 
            
                
                totalShipmentCharges = shipmentCost;
                totalFreightCharges = shipmentCost;
                
                if("R".equalsIgnoreCase(carrierPayMethod)){ // Receiver pays, can not charge shipper, so making default to 0.0
                    totalShipmentCharges = "0.0";
                    totalFreightCharges = "0.0";
                    
                }
                aascShipmentHeaderInfo.setShipmentCost(Double.parseDouble(totalShipmentCharges));
                aascShipmentHeaderInfo.setFreightCost(Double.parseDouble(totalFreightCharges));  
              
                hashMap.put("masterTrkNum", wayBillNumber);
                hashMap.put("shipmentCost", shipmentCost);
                
                displayMessage = "success";
                hashMap.put("status", displayMessage);
                
                
            }
            
          }//error at quote parsing
          else 
          {
           //   
              logger.info("Error message from quote parsing");
//              System.out.println("-------------------> "+conditionCode+conditionData);
              displayMessage = conditionCode+" "+ conditionData;
              
              logger.severe("errorDescription:" + displayMessage);
              hashMap.put("status", displayMessage);
           
          }
          
         
        }//try
        catch(Exception exp) {
            displayMessage = "Error in parsing or setting the values to bean!";
            logger.severe("Exception::"+exp.getMessage());
        }
            logger.info("Exit from parseResponse()");
            return hashMap;
        }
     
        String parseQuoteSeriveResponse(String quoteResponse,
                                        String intlFlag,// may requrie in future
                                        String shipMethodActualName,
                                        String cloudLabelPath
                                     ) {
          logger.info("Entered DHL quote parseResponse()");
         
          String qParsing = "";
          String conditionData = "";
          String conditionCode = "";
          String localProductName = "";
          String shippingCharge = "0.000";
          logger.info("quoteResponse "+quoteResponse);
          quoteResponse = quoteResponse.replaceAll("<res:", "<");
          quoteResponse = quoteResponse.replaceAll("</res:", "</");
          
          
         labelPath = cloudLabelPath;
         try {
         
         // Get an instance of the parser
         parser = new DOMParser();

         // create InputSource from string containing xml response
         reader = new StringReader(quoteResponse);

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
             // Print or extract document fields
            
             
//          System.out.println("-------------dhl quote parsing----------------------");
          
         if(quoteResponse.contains("ErrorResponse"))
         {
             logger.info("inside quote error parsing");
             
             node = root.selectSingleNode("/ErrorResponse/Response/Status/Condition/ConditionCode/text()");
             logger.info("carrier ConditionCode : "+ nullStrToSpc(node.getNodeValue()));
             conditionCode = nullStrToSpc(node.getNodeValue());
             if("".equalsIgnoreCase(conditionCode))
                 conditionCode = "@";
             
             node = root.selectSingleNode("/ErrorResponse/Response/Status/Condition/ConditionData/text()");
             logger.info("carrier ConditionData : "+ nullStrToSpc(node.getNodeValue()));
             conditionData = nullStrToSpc(node.getNodeValue());
             if("".equalsIgnoreCase(conditionData))
                 conditionData = "@";
             
             qParsing = "error"+"###"+"@###"+"@###"+conditionCode+"###"+conditionData;
         }
         else 
         if(quoteResponse.contains("ConditionCode")) 
         {
             logger.info("inside quote ConditionCode parsing");
             
             node = root.selectSingleNode("/DCTResponse/GetQuoteResponse/Note/Condition/ConditionCode/text()");
             logger.info("carrier ConditionCode : "+ nullStrToSpc(node.getNodeValue()));
             conditionCode = nullStrToSpc(node.getNodeValue());
             if("".equalsIgnoreCase(conditionCode))
                 conditionCode = "@";
             
             node = root.selectSingleNode("/DCTResponse/GetQuoteResponse/Note/Condition/ConditionData/text()");
             logger.info("carrier ConditionData : "+ nullStrToSpc(node.getNodeValue()));
             conditionData = nullStrToSpc(node.getNodeValue());
             if("".equalsIgnoreCase(conditionData))
                 conditionData = "@";
             
             qParsing = "error"+"###"+"@###"+"@###"+conditionCode+"###"+conditionData; 
         }
         else 
         {
             logger.info("success rate parsing"); 
             //-------------------------------------
             DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
             DocumentBuilder db = dbf.newDocumentBuilder();
                                                
               //parse using builder to get DOM representation of the XML file
              Document dom = db.parse(new InputSource(new StringReader(quoteResponse)));
              Element docEle = dom.getDocumentElement();
              
             //Shiva modified below code for getting the correct rates for services 
              
             NodeList qtdShpNodeList=null;
             String productNameXml ="";
             String charge = "";
             String localProductCode ="";
             String globalProductCode = "";
             Boolean matchFlag = false;
             int qtdShpLength=0;
             String globalProductCodeStr="";
             String localProductCodeStr="";
            
             qtdShpNodeList = root.selectNodes("/DCTResponse/GetQuoteResponse/BkgDetails/QtdShp");
             if(qtdShpNodeList!=null){
                 qtdShpLength=qtdShpNodeList.getLength();
                 logger.info("Number of carriers returned :"+qtdShpLength);
                 for(int i=0;i<qtdShpLength;i++){
                     try{
                         productNameXml = nullStrToSpc(AascXmlParser.getValue((Element)qtdShpNodeList.item(i), "ProductShortName"));
                         globalProductCodeStr = nullStrToSpc(AascXmlParser.getValue((Element)qtdShpNodeList.item(i), "GlobalProductCode"));
                         localProductCodeStr = nullStrToSpc(AascXmlParser.getValue((Element)qtdShpNodeList.item(i), "LocalProductCode"));
                         
                         charge = AascXmlParser.getValue((Element)qtdShpNodeList.item(i), "ShippingCharge");
                         charge ="".equals(charge)?"0.000":charge;
                         
                         logger.info("productNameXml "+productNameXml+"  shipMethodActualName  "+shipMethodActualName+" charge "+charge);
                         logger.info("localProductCodeStr "+localProductCodeStr);      
                         if(productNameXml.equalsIgnoreCase(shipMethodActualName)){
                            matchFlag = true;
                            shippingCharge =  charge;
                            localProductCode = localProductCodeStr;
                            globalProductCode = globalProductCodeStr;
                            if(!"0.000".equals(shippingCharge)){
                                break;
                            }     
                         }
                         
                     }catch(Exception e){
                         logger.severe("Exception::"+e.getMessage());
                     }
                 }
             }
            //Shiva modifications end
             //-------------------------------------
             
             /*node = root.selectSingleNode("/DCTResponse/GetQuoteResponse/BkgDetails/QtdShp/LocalProductName/text()");
             logger.info("carrier LocalProductName : "+ nullStrToSpc(node.getNodeValue()));
             localProductName = nullStrToSpc(node.getNodeValue());
             if("".equalsIgnoreCase(localProductName))
                 localProductName = "@";
             
             node = root.selectSingleNode("/DCTResponse/GetQuoteResponse/BkgDetails/QtdShp/ShippingCharge/text()");
//             logger.info("carrier ShippingCharge : "+ nullStrToSpc(node.getNodeValue()));
            if(node != null)
             shippingCharge = nullStrToSpc(node.getNodeValue());
             if("".equalsIgnoreCase(shippingCharge))
                 shippingCharge = "@";*/
              System.out.println("shippingCharge ---"+shippingCharge+"----");
                 
              if("".equalsIgnoreCase(productName) || productName == null)
                  localProductName = "@";
              if("".equalsIgnoreCase(shippingCharge) || shippingCharge == null)
                  shippingCharge = "@";
              if("".equalsIgnoreCase(globalProductCode) || globalProductCode == null)
                 globalProductCode = "@";
             
                 
//             System.out.println("shippingCharge ---"+shippingCharge+":::::");   
             
             if(!matchFlag){
                 qParsing = "error"+"###"+"@###"+"@###"+" "+"###"+"Selected Service is not available from source to destination."; 
             }
             else{//selected server level is not configure for rates to receive.
             if("0.000".equalsIgnoreCase(shippingCharge)){    
                 qParsing = "error"+"###"+"@###"+"@###"+" "+"###"+"No Rates returned"; 
             }
             else{
                 qParsing = "success"+"###"+localProductCode+globalProductCode+"###"+shippingCharge+"###@"+"###@"; 
             }
             }
             
         }
          
         }
         catch(Exception e){
             e.printStackTrace();
         }
         finally{
             return qParsing;
         }
     }

 /** This method can replace the null values with nullString.
  * @return String that is ""
  * @param obj-object of type Object
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
  * @param  String as input 
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
  * @param takes String containing from substring which is replaced by to substring
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
} 
