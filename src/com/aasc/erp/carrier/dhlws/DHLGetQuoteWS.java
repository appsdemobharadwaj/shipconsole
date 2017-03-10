package com.aasc.erp.carrier.dhlws;

  /**
   * DHLGetQuoteWS.java  is a java class for DHL carrier where in you call DHL webservice to get Quote for shipping.
   * @version   1
   * @author   G S Shekar
   * @History
   */



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

//import java.util.Date;

import java.util.HashMap;

import java.io.OutputStreamWriter;

//import java.net.URLEncoder;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;

public class

DHLGetQuoteWS {

    private static Logger logger = AascLogger.getLogger("DHLGetQuoteWS");
//    private String response="";
    private String timeStampStr="";
    private String deliveryId = "";
    public DHLGetQuoteWS() {
    }
    
    private LinkedList packageInfo = 
        null; // Delivery Package Information Object      
    
    
public String getQuoteInfo(HashMap hm, String ipAddress, int portNo, LinkedList shipPackageInfo) {
        logger.info("enter getQuoteinfo in aascdhl ship ws");
        String response = "";
//        String accessCode = "";
//        String dhlRequest = "";
//        String dhlResponce = "";
        StringBuffer request = buildQuoteRequest(hm, shipPackageInfo);
        //dhlpassward
        String outputFile = nullStringToSpace(hm.get("outputFile"));
        timeStampStr = nullStringToSpace(hm.get("timeStampStr"));
        this.deliveryId = nullStringToSpace(hm.get("deliveryId"));
        String requestFilePath = outputFile;
        try {
        
        //write basic request here 
         try {
             writeOutputFile(request.toString(), requestFilePath +deliveryId+"_"+timeStampStr+ "_quote_request.xml");
         } catch (Exception fileNotFoundException) {
             logger.severe("Exception::"+fileNotFoundException.getMessage() );
         }
        
            response = 
                    sendSOAPRequest(request.toString(), false,
                                    ipAddress, 
                                    requestFilePath);
            
            
            try {
                writeOutputFile(response, requestFilePath +deliveryId+"_"+timeStampStr+ "_quote_response.xml");
            } catch (Exception fileNotFoundException) {
                logger.severe("Exception::"+fileNotFoundException.getMessage());
            }
            
              
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } finally {
            return response;
        }
    }
    
    
    
    private StringBuffer buildQuoteRequest(HashMap hm, LinkedList shipPackageInfo) {
    StringBuffer quoteRequest = new StringBuffer();
        logger.info("****************************************************************************************");
        logger.info("HashMap: " + hm);
        logger.info("****************************************************************************************");
  

        String siteId = nullStringToSpace(hm.get("siteId")).trim();
        String dhlPassword = nullStringToSpace(hm.get("dhlPassword")).trim();
        String shipDate = nullStringToSpace(hm.get("shipDate")).trim();
        
        String senderAccountNumber = 
            nullStringToSpace(hm.get("senderAccountNumber")).trim();
        String isDutiable     = "N";
//        String receiverAccountNumber = 
//            nullStringToSpace(hm.get("receiverAccountNumber")).trim();
 
        String shipToAddressCity = 
            nullStringToSpace(hm.get("shipToAddressCity")).trim();
//        String shipToAddressState = 
//            nullStringToSpace(hm.get("shipToAddressState")).trim();
        String shipToAddressPostalCode = 
            nullStringToSpace(hm.get("shipToAddressPostalCode")).trim();
//        String shipToAddressLine1 = 
//            nullStringToSpace(hm.get("shipToAddressLine1")).trim();
//        String shipToAddressLine2 = 
//            nullStringToSpace(hm.get("shipToAddressLine2")).trim();
            
        String shipFromAddressPostalCode = 
            nullStringToSpace(hm.get("shipFromAddressPostalCode")).trim();     
       String shipFromAddressCity = 
            nullStringToSpace(hm.get("shipFromAddressCity")).trim();
//        String shipFromAddressState = 
//            nullStringToSpace(hm.get("shipFromAddressState")).trim();
        String shipFromCountry = 
            nullStringToSpace(hm.get("shipFromCountry")).trim();     
//       String SenderEmail = 
//            nullStringToSpace(hm.get("SenderEmail")).trim();     
//        String shipFromPersonName = 
//             nullStringToSpace(hm.get("shipFromPersonName")).trim();
//        String shipFromPhoneNumber1 = nullStringToSpace(hm.get("shipFromPhoneNumber1")).trim();
//        String shipFromCode = nullStringToSpace(hm.get("shipFromCode")).trim();
//        
//        String shipToCompanyName = 
//            nullStringToSpace(hm.get("shipToCompanyName")).trim();
        String shipToCountry = 
            nullStringToSpace(hm.get("shipToCountry")).trim();
//        String shipToContactPersonName = 
//            nullStringToSpace(hm.get("shipToContactPersonName")).trim();
//        String shipToContactPhoneNumber = 
//            nullStringToSpace(hm.get("shipToContactPhoneNumber")).trim();
//        String shipToCode = 
//            nullStringToSpace(hm.get("shipToCode")).trim();
//            
//        String shipFromCompanyName = 
//            nullStringToSpace(hm.get("shipFromCompanyName")).trim();
//
//        String shipFromAddressLine1 = 
//            nullStringToSpace(hm.get("shipFromAddressLine1")).trim();
//        String recipientEmailAddress1 = 
//            nullStringToSpace(hm.get("recipientEmailAddress1")).trim();
//        
//        
        String currencyCode = 
            nullStringToSpace(hm.get("currencyCode")).trim();

        String globalProductName = 
            nullStringToSpace(hm.get("globalProductName")).trim();
        String localProductName = 
            nullStringToSpace(hm.get("localProductName")).trim();

//        String option = 
//            nullStringToSpace(hm.get("option")).trim();
//        String labelFormat = 
//            nullStringToSpace(hm.get("labelFormat")).trim();
        String uom = "";
        String dimensionUnit = "";
        Double totalDeclareValue = 0.00;
        timeStampStr = nullStringToSpace(hm.get("timeStampStr")).trim();
        String messageReference = nullStringToSpace(hm.get("messageReference")).trim();
        
       ListIterator packageInfoIterator = shipPackageInfo.listIterator();
        ListIterator packageInfoIterator1 = shipPackageInfo.listIterator();
        
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
              
            
        double totalWeight = (Double)hm.get("totalWeight");

        
        try {

            quoteRequest.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                        quoteRequest.append("<req:DCTRequest xmlns:req=\"http://www.dhl.com\">\n" ); 
                        quoteRequest.append("<GetQuote>\n" ); 
                        quoteRequest.append("<Request>\n" ); 
                        quoteRequest.append("<ServiceHeader>\n" ); 
                        //quoteRequest.append("<MessageTime>2011-03-16T14:57:20-07:00</MessageTime>\n" ); 
                        quoteRequest.append("<MessageReference>"+messageReference+"</MessageReference>\n" ); 
                        quoteRequest.append("<SiteID>"+siteId+"</SiteID>\n" ); 
                        quoteRequest.append("<Password>"+dhlPassword+"</Password>\n" ); 
                        quoteRequest.append("</ServiceHeader>\n" ); 
                        quoteRequest.append("</Request>\n" );
                        
                        quoteRequest.append("<From>\n" ); 
                        quoteRequest.append("<CountryCode>"+ shipFromCountry +"</CountryCode>\n" ); 
                        quoteRequest.append("<Postalcode>"+ shipFromAddressPostalCode +"</Postalcode>\n" ); 
                        quoteRequest.append("<City>"+ shipFromAddressCity +"</City>\n" ); 
                        quoteRequest.append("</From>\n" ); 
                        
                        quoteRequest.append("<BkgDetails>\n" ); 
                        quoteRequest.append("<PaymentCountryCode>"+ shipFromCountry +"</PaymentCountryCode>\n" ); 
                        
                        quoteRequest.append("<Date>"+ shipDate +"</Date>\n" ); 
                        quoteRequest.append("<ReadyTime>PT9H</ReadyTime>\n" ); //? pick up time /  hard code?
                        
                        quoteRequest.append("<DimensionUnit>"+dimensionUnit+"</DimensionUnit>\n" ); 
                        quoteRequest.append("<WeightUnit>"+uom+"</WeightUnit>\n" ); 
                        quoteRequest.append("<Pieces>\n" );
                        
                        int packNum=1;
                        while (packageInfoIterator1.hasNext())
                        {
                           AascShipmentPackageInfo aascPackageBean = 
                               (AascShipmentPackageInfo)packageInfoIterator1.next();
                               
 
                        quoteRequest.append("<Piece>\n" ); 
                        quoteRequest.append("<PieceID>"+packNum+"</PieceID>\n" ); 
                        quoteRequest.append("<Height>"+(int)aascPackageBean.getPackageHeight()+"</Height>\n" ); 
                        quoteRequest.append("<Depth>"+(int)aascPackageBean.getPackageLength()+"</Depth>\n" ); 
                        quoteRequest.append("<Width>"+(int)aascPackageBean.getPackageWidth()+"</Width>\n" ); 
                        quoteRequest.append("<Weight>"+aascPackageBean.getWeight()+"</Weight>\n" ); 
                        quoteRequest.append("</Piece>\n" ); 
                            packNum++;
                        }
                        
                        quoteRequest.append("</Pieces>\n" ); 
                        
                        quoteRequest.append("<PaymentAccountNumber>"+senderAccountNumber+"</PaymentAccountNumber>\n" ); 
                        quoteRequest.append("<IsDutiable>"+isDutiable+"</IsDutiable>\n" ); //dutiable goods.
                        quoteRequest.append("<NetworkTypeCode>AL</NetworkTypeCode>"); // added this tag for get all the services present from source to destn.
                        
                        quoteRequest.append("</BkgDetails>\n" ); 
                        
                        quoteRequest.append("<To>\n" ); 
                        quoteRequest.append("<CountryCode>"+shipToCountry+"</CountryCode>\n" ); 
                        quoteRequest.append("<Postalcode>"+shipToAddressPostalCode+"</Postalcode>\n" ); 
                        quoteRequest.append("<City>"+ shipToAddressCity +"</City>\n" ); 
                        quoteRequest.append("</To>\n" ); 
                        
                        if("Y".equalsIgnoreCase(isDutiable)){
                        
                            quoteRequest.append("<Dutiable>\n" ); 
                            quoteRequest.append("<DeclaredCurrency>"+currencyCode+"</DeclaredCurrency>\n" ); 
                            quoteRequest.append("<DeclaredValue>"+totalDeclareValue+"</DeclaredValue>\n" ); 
                            quoteRequest.append("</Dutiable>\n" ); 
                            
                        }
                        
                        quoteRequest.append("</GetQuote>\n" );
                        quoteRequest.append("</req:DCTRequest>"); 
                  
            //System.out.println("quoteRequest -> "+quoteRequest);

        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } finally {
            return quoteRequest;
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
    
    //Shiva added below method as formatter is deleted
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
