 /*
   * @(#)AascFreightShopDelegate.java     18/02/2015
   * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
   * All rights reserved.
   *
   */
 package com.aasc.erp.delegate;


 import com.aasc.erp.bean.AascFSCarrierServiceLevels;
 import com.aasc.erp.bean.AascFSCarriersIncluded;
 import com.aasc.erp.bean.AascFreightShopInfo;
 import com.aasc.erp.bean.AascProfileOptionsBean;
 import com.aasc.erp.bean.AascShipMethodInfo;
 import com.aasc.erp.bean.AascShipmentHeaderInfo;
 import com.aasc.erp.bean.AascShipmentOrderInfo;
 import com.aasc.erp.carrier.AascFreightShop;
 import com.aasc.erp.model.AascDAOFactory;
 import com.aasc.erp.model.AascOracleShipSaveDAO;
 import com.aasc.erp.model.AascShipMethodDAO;
 import com.aasc.erp.model.AascShipSaveDAO;

 import com.aasc.erp.util.AascLogger;


 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.LinkedList;

 import java.util.Map;
 import java.util.ResourceBundle;
 import java.util.Set;
 import java.util.logging.Logger;

 import javax.servlet.http.HttpServletRequest;

 import javax.servlet.http.HttpSession;

 /**
  * AascFreightShopDelegate class is delegate class for FreightShopping.
  * @version   1
  * @author    Suman G
  * History
  * 
  * 26/02/2015   Y Pradeep       Modified code to change field names of username, password and account number common for Address Validation and Freight Shopping.
  * 26/02/2015   Suman G         Removed unused code in the file.
  * 09/03/2015   Suman G         Added code for Get Rates.
  * 10/03/2015   Y Pradeep       Addred code to save order header details once after generating order number.
  * 11/03/2015   Suman G         Added code for taking multiple package details from jsp.
  * 13/03/2015   Suman G         Added code for showing proper message.
  * 24/03/2015   Suman G         Added code for remove error message while opening popup second time.
  * 26/03/2015   Suman G         Added code for display proper message in FreightShopping window(#2697).
  * 27/03/2015   Suman G         Added code to fix #2729.
  * 09/04/2015   Suman G         Added HashMap instead of LinkedList for Get Rates #2730. And also added international shipmethods for FedEx to fix #2769.
  * 10/04/2015   Suman G         Added code to fix issue in FS.
  * 10/04/2015   Suman G         Added code to handle if web service not exist.
  * 22/04/2015   Suman G         Modified code to fix #2768
  * 23/04/2015   Suman G         Fixed the issue to display proper message
  * 27/05/2015   Suman G         Added code to fix #2936
  * 29/06/2015   Suman G         Added condition for fedex carrier code FDXE along with FedEx.
  * 05/11/2015   Suman G         Added code for DHL and Stamps Freight Shopping.
  * 12/11/2015   Shiva G         Modified the code to fix the issue 3954
  * 23/11/2015   Suman G         Added code to fix #3970
  * 16/12/2015   Suman G         Added code to implement get rates feature for DHL.
  * 21/12/2015   Y Pradeep       Modified code to get webservice username, password and account number for freight shopping from Application.properties file.
  */
 public class

 AascFreightShopDelegate {

     public AascFreightShopDelegate() {
     }

     static Logger logger = 
         AascLogger.getLogger(" AascFreightShopDelegate "); // logger object

     /**
      * This method has all the business logic for freight shop.
      * This is also used to call the classes which have Data Base calls and Web service calls.
      * @param request HttpServletRequest
      * @param session HttpSession
      * @return String Success to navigate to freightShop Window.
      */
     public

     String getFreightShopMethods(HttpServletRequest request, 
                                  HttpSession session) {

         logger.info("Entered getFreightShopMethods()");
         String decision = request.getParameter("decision");
         if ("GetRates".equalsIgnoreCase(decision)) {
             commonAction(session,"");
         }
         ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
         String cloudLabelPath = (String)session.getAttribute("cloudLabelPath");
         AascProfileOptionsBean aascProfileOptionsInfo = 
             new AascProfileOptionsBean();
         String fregithShopEnable = "N";
         String appsUserName = "";
         String appsPassword = "";
         String appsAccountNumber = "";
         AascFreightShopInfo aascFreightShopResponse = 
             new AascFreightShopInfo();
         int countUPS = 0, countFedEx = 0, countDHL = 0, countStamps = 0;
         String sortRule = "";
         String upsResponse = "";//((AascFSCarriersIncluded)aascFreightShopResponse.getCarriersIncluded().get(0)).getCarrierStatus();
         String fedExResponse = "";//((AascFSCarriersIncluded)aascFreightShopResponse.getCarriersIncluded().get(1)).getCarrierStatus();
         String upsCheckBox = request.getParameter("UPSCheckBox");
         String fedExCheckBox = request.getParameter("FedExCheckBox");
         String dhlCheckBox = request.getParameter("DHLCheckBox");
         String stampsCheckBox = request.getParameter("StampsCheckBox");
         try {
             aascProfileOptionsInfo = 
                     (AascProfileOptionsBean)session.getAttribute("ProfileOptionsInfo"); //getting the object of the AascProfileOptionsInfo class object through the session
             fregithShopEnable = aascProfileOptionsInfo.getFreightShopping();

             if ("Y".equalsIgnoreCase(fregithShopEnable)) {
                 appsUserName = resourceBundle.getString("WsUserName");//aascProfileOptionsInfo.getWsUserName();
                 appsPassword = resourceBundle.getString("WsPassword");//aascProfileOptionsInfo.getWsPassword();
                 appsAccountNumber = resourceBundle.getString("WsAccountNumber");//aascProfileOptionsInfo.getWsAccountNumber();
             }

             String orderNumber = request.getParameter("orderNumber");
             logger.info("order:" + orderNumber);
             LinkedList shipFrom = new LinkedList();
             shipFrom.add(request.getParameter("fromAdressLine1"));
             shipFrom.add(request.getParameter("fromAdressLine2"));
             shipFrom.add(request.getParameter("fromCity"));
             shipFrom.add(request.getParameter("fromState"));
             shipFrom.add(request.getParameter("fromZipCode"));
             shipFrom.add(request.getParameter("fromCountryCode"));

             LinkedList shipTo = new LinkedList();
             shipTo.add(request.getParameter("toAdressLine1"));
             shipTo.add(request.getParameter("toAdressLine2"));
             shipTo.add(request.getParameter("toCity"));
             shipTo.add(request.getParameter("toState"));
             shipTo.add(request.getParameter("toZipCode"));
             shipTo.add(request.getParameter("toCountryCode"));

             LinkedList pkgDescription = new LinkedList();
             int count = 1; //request.getParameter("count"));
             try {
                 count = Integer.parseInt(request.getParameter("countPackets"));
             } catch (Exception e) {
                 logger.severe("Exception :" + e.getMessage());
             }
             pkgDescription.add(count);
             for (int i = 1; i <= count; i++) {
                 pkgDescription.add(request.getParameter("dimUOM" + i));
                 pkgDescription.add(request.getParameter("dimValueFreight" + 
                                                         i));
                 pkgDescription.add(request.getParameter("weightUOM" + i));
                 pkgDescription.add(request.getParameter("weightValue" + i));
             }
             java.sql.Timestamp timeStamp = java.sql.Timestamp.valueOf(request.getParameter("shipDate"));
             sortRule = request.getParameter("sortRule");

             LinkedList selectedCarriers = new LinkedList();
             selectedCarriers.add(request.getParameter("FedExCheckBox"));
             selectedCarriers.add(request.getParameter("UPSCheckBox"));
             selectedCarriers.add(request.getParameter("TNTCheckBox"));
             selectedCarriers.add(request.getParameter("DHLCheckBox"));
             selectedCarriers.add(request.getParameter("EchoCheckBox"));
             selectedCarriers.add(request.getParameter("StampsCheckBox"));
             selectedCarriers.add(request.getParameter("USPSCheckBox"));

             int locationID = 
                 ((Integer)session.getAttribute("location_id_selected")).intValue();
             logger.info("locationId:::::" + locationID);
             Integer clientIdInt = (Integer)session.getAttribute("client_id");
             String intlFlag = "N";

             if (!request.getParameter("fromCountryCode").equalsIgnoreCase(request.getParameter("toCountryCode"))) {
                 intlFlag = "Y";
                 logger.info("intl flag:::" + intlFlag);
             }

             AascDAOFactory aascDAOFactory = 
                 AascDAOFactory.getAascDAOFactory(1);
             AascShipMethodDAO aascshipMethodDAO = 
                 aascDAOFactory.getAascShipMethodDAO();


             LinkedList fedexDetailsList = new LinkedList();
             LinkedList upsDetailsList = new LinkedList();
             LinkedList tntDetailsList = new LinkedList();
             LinkedList dhlDetailsList = new LinkedList();
             LinkedList echoDetailsList = new LinkedList();
             LinkedList stampsDetailsList = new LinkedList();
             LinkedList uspsDetailsList = new LinkedList();
             LinkedList configuredCarriers = new LinkedList();
             HashMap hm = new HashMap();
             LinkedList configuredCarriersList = new LinkedList();
             configuredCarriers = 
                     aascshipMethodDAO.getFreightShopDetails(clientIdInt, 
                                                             locationID, 
                                                             intlFlag);

             Iterator it3 = configuredCarriers.iterator();


             try {

                 while (it3.hasNext()) {
                     String carrierName = (String)it3.next();
                     if ("UPS".equalsIgnoreCase(carrierName))
                         countUPS++;
                     else if ("FEDEX".equalsIgnoreCase(carrierName) || "FDXE".equalsIgnoreCase(carrierName) ||
                              "FDXG".equalsIgnoreCase(carrierName))
                         countFedEx++;
                     else if ("DHL".equalsIgnoreCase(carrierName))
                            countDHL++;
                     else if("Stamps.com".equalsIgnoreCase(carrierName))
                        countStamps++;

                     it3.next();
                     it3.next();

                 }
             } catch (Exception e) {
                 logger.severe("Exception got at checking for carrier codes::::" + 
                               e.getMessage());
             }

             if ("true".equalsIgnoreCase(request.getParameter("UPSCheckBox"))) {
                 if (countUPS == 0) {
                     request.setAttribute("errorUPSFreightShop", "aasc54");
                 }
             }
             if ("true".equalsIgnoreCase(request.getParameter("FedExCheckBox"))) {
                 if (countFedEx == 0) {
                     request.setAttribute("errorFedExFreightShop", "aasc54");
                 }
             }
             if ("true".equalsIgnoreCase(request.getParameter("DHLCheckBox"))) {
//                 System.out.println("inside dhl count:::::"+countDHL);
                 if (countDHL == 0) {
                    request.setAttribute("errorDHLFreightShop", "aasc54");
                 }
             }
             if ("true".equalsIgnoreCase(request.getParameter("StampsCheckBox"))) {
                 if (countStamps == 0) {
                     request.setAttribute("errorStampsFreightShop", "aasc54");
                 }
             }
             if ((countUPS + countFedEx + countDHL + countStamps) == 0) {
                 return "success";
             }
             /*==========================================//
                      Configured Carriers
              //==========================================*/
             Iterator it = configuredCarriers.iterator();
             try {
                 while (it.hasNext()) {
                     String carrierCode =  (String)it.next(); //  Leaving the carrier code.
//                     System.out.println("carrierCode:::::"+carrierCode);
                     String serviceType = (String)it.next();

                     if("FDXE".equalsIgnoreCase(carrierCode) || "FDXG".equalsIgnoreCase(carrierCode)){
                         if (serviceType.equals("PRIORITYOVERNIGHT")) {
                         
                             configuredCarriersList.add("PRIORITY_OVERNIGHT");
                             serviceType = "PRIORITY_OVERNIGHT";
                             
                         } else if (serviceType.equals("STANDARDOVERNIGHT")) {
                         
                             configuredCarriersList.add("STANDARD_OVERNIGHT");
                             serviceType = "STANDARD_OVERNIGHT";
                             
                         } else if (serviceType.equals("FEDEX1DAYFREIGHT")) {
                             
                             configuredCarriersList.add("FEDEX_1_DAY_FREIGHT");
                             serviceType = "FEDEX_1_DAY_FREIGHT";
                             
                         } else if (serviceType.equals("FEDEX2DAY")) {
                             
                             configuredCarriersList.add("FEDEX_2_DAY");
                             serviceType = "FEDEX_2_DAY";
                             
                         } else if (serviceType.equals("FEDEX2DAYFREIGHT")) {
                             
                             configuredCarriersList.add("FEDEX_2_DAY_FREIGHT");
                             serviceType = "FEDEX_2_DAY_FREIGHT";
                             
                         } else if (serviceType.equals("FEDEX3DAYFREIGHT")) {
                             
                             configuredCarriersList.add("FEDEX_3_DAY_FREIGHT");
                             serviceType = "FEDEX_3_DAY_FREIGHT";
                             
                         } else if (serviceType.equals("FEDEXEXPRESSSAVER")) {
                             
                             configuredCarriersList.add("FEDEX_EXPRESS_SAVER");
                             serviceType = "FEDEX_EXPRESS_SAVER";
                             
                         } else if (serviceType.equals("FEDEXGROUND")) {
                             
                             configuredCarriersList.add("FEDEX_GROUND");
                             serviceType = "FEDEX_GROUND";
                             
                         } else if (serviceType.equals("FIRSTOVERNIGHT")) {
                             
                             configuredCarriersList.add("FIRST_OVERNIGHT");
                             serviceType = "FIRST_OVERNIGHT";
                             
                         } else if (serviceType.equals("GROUNDHOMEDELIVERY")) {
                             
                             configuredCarriersList.add("GROUND_HOME_DELIVERY");
                             serviceType = "GROUND_HOME_DELIVERY";
                             
                         } else if (serviceType.equals("SMARTPOST")) {
                             
                             configuredCarriersList.add("SMART_POST");
                             serviceType = "SMART_POST";
                             
                         } else if (serviceType.equals("FEDEX_2_DAY_AM")) {
                             
                             configuredCarriersList.add("FEDEX_2_DAY_AM");
                             serviceType = "FEDEX_2_DAY_AM";
                             
                         } else if (serviceType.equals("INTERNATIONALPRIORITY") || 
                                    serviceType.equals("INTERNATIONAL PRIORITY")) {
                             
                             configuredCarriersList.add("INTERNATIONAL_PRIORITY");
                             serviceType = "INTERNATIONAL_PRIORITY";
                             
                         } else if (serviceType.equals("INTERNATIONALPRIORITYFREIGHT") || 
                                    serviceType.equals("INTERNATIONALPRIORITY FREIGHT")) {
                             
                             configuredCarriersList.add("INTERNATIONAL_PRIORITY_FREIGHT");
                             serviceType = "INTERNATIONAL_PRIORITY_FREIGHT";
                             
                         } else if (serviceType.equals("INTERNATIONALGROUND")) {
                             
                             configuredCarriersList.add("FEDEX_GROUND");
                             serviceType = "FEDEX_GROUND";
                             
                         } else if (serviceType.equals("INTERNATIONALFIRST") || 
                                    serviceType.equals("INTERNATIONAL FIRST")) {
                             
                             configuredCarriersList.add("INTERNATIONAL_FIRST");
                             serviceType = "INTERNATIONAL_FIRST";
                             
                         } else if (serviceType.equals("INTERNATIONALECONOMYFREIGHT") || 
                                    serviceType.equals("INTERNATIONALECONOMY FREIGHT")) {
                             
                             configuredCarriersList.add("INTERNATIONAL_ECONOMY_FREIGHT");
                             serviceType = "INTERNATIONAL_ECONOMY_FREIGHT";
                             
                         } else if (serviceType.equals("INTERNATIONALECONOMY") || 
                                    serviceType.equals("INTERNATIONAL ECONOMY")) {
                             
                             configuredCarriersList.add("INTERNATIONAL_ECONOMY");
                             serviceType = "INTERNATIONAL_ECONOMY";
                             
                         } else if (serviceType.equals("EUROPEFIRSTINTERNATIONALPRIORITY") || 
                                    serviceType.equals("EUROPEFIRSTINTERNATIONAL PRIORITY")) {
                             
                             configuredCarriersList.add("EUROPE_FIRST_INTERNATIONAL_PRIORITY");
                             serviceType = "EUROPE_FIRST_INTERNATIONAL_PRIORITY";
                         }    
                     } 
                     if("UPS".equalsIgnoreCase(carrierCode)){
                         if (serviceType.length() == 1) {
                             serviceType = "0" + serviceType;
                         }
                     }
                     if("Stamps.com".equalsIgnoreCase(carrierCode)){
                         serviceType = serviceType.replace('-','_');
                     }

                     configuredCarriersList.add(serviceType);
                     String shipmethodMeaning = (String)it.next();
                     hm.put(serviceType, shipmethodMeaning);

                 }
             } catch (Exception e) {
                 logger.severe("Got Exception while comparing service codes::" + 
                               e.getMessage());
             }
             String serviceCode = "";
             String shipMethod = "";

             String shipMethodMeaning = "";
             try {
                 if ("GetRates".equalsIgnoreCase(decision)) {
                     shipMethod = request.getParameter("getRateShipMethod");
                     int carrierCode = Integer.parseInt(shipMethod.substring(0,shipMethod.indexOf("||")));
                     shipMethod = shipMethod.substring(shipMethod.indexOf('*')+1,shipMethod.indexOf("@@"));
                     logger.info("inside get rates");

                     shipMethod = 
                             shipMethod.substring(shipMethod.indexOf('*') + 1);
      //               System.out.println("shipmethod::::"+shipMethod);
                     Set serviceLevelSet = hm.entrySet();
                     Iterator serviceLevelItr = serviceLevelSet.iterator();
                     while (serviceLevelItr.hasNext()) {
                         Map.Entry me = (Map.Entry)serviceLevelItr.next();
                         String code = me.getKey().toString();
                         shipMethodMeaning = me.getValue().toString();
                         logger.info("code::::in hm::::" + code);
                         logger.info("shipmethod meanign::in hm:::" + 
                                     shipMethodMeaning + "::");
                         
                         if (carrierCode == 100) {
                             code = "0" + code;
                         }
                         if (shipMethodMeaning.equalsIgnoreCase(shipMethod)) {
                             serviceCode = code;
                             break;
                         }

                     }
                     logger.info("servicecode::::" + serviceCode);
                 }
             } catch (Exception e) {
                 logger.severe("Got exception while compare values for get rates::" + 
                               e.getMessage());
             }
             /*==========================================//
                      All Carriers Details
              //==========================================*/
             AascShipMethodInfo aascShipMethodInfo;
             aascShipMethodInfo = 
                     (AascShipMethodInfo)session.getAttribute("ShipMethodInfo");

             if ("true".equalsIgnoreCase(request.getParameter("UPSCheckBox"))) {
                 String upsUserName = 
                     aascShipMethodInfo.getCarrierCodeUserName(100);
                 String upsPassword = 
                     aascShipMethodInfo.getCarrierCodePassword(100);
                 String accessLicenseNumber = 
                     aascShipMethodInfo.getCodeAccessLicenseNumber(100);
                 String carrierAccountNumber = 
                     aascShipMethodInfo.getCarrierCodeAccountNumber(100);
                 upsDetailsList.add(upsUserName);
                 upsDetailsList.add(upsPassword);
                 upsDetailsList.add(carrierAccountNumber);
                 upsDetailsList.add(accessLicenseNumber);
             }


             if ("true".equalsIgnoreCase(request.getParameter("FedExCheckBox"))) {
                 String fedExKey = 
                     aascShipMethodInfo.getCarrierCodeUserName(110);
                 String fedExPassword = 
                     aascShipMethodInfo.getCarrierCodePassword(110);
                 String meterNumber = 
                     aascShipMethodInfo.getCodeMeterNumber(110);
                 String carrierAccountNumber = 
                     aascShipMethodInfo.getCarrierCodeAccountNumber(110);
                if(fedExKey == null || "".equalsIgnoreCase(fedExKey)){
                    fedExKey = 
                        aascShipMethodInfo.getCarrierCodeUserName(111);
                    fedExPassword = 
                        aascShipMethodInfo.getCarrierCodePassword(111);
                    meterNumber = 
                        aascShipMethodInfo.getCodeMeterNumber(111);
                    carrierAccountNumber = 
                        aascShipMethodInfo.getCarrierCodeAccountNumber(111);
                }
                 fedexDetailsList.add(fedExKey);
                 fedexDetailsList.add(fedExPassword);
                 fedexDetailsList.add(carrierAccountNumber);
                 fedexDetailsList.add(meterNumber);
             }
             if ("true".equalsIgnoreCase(request.getParameter("StampsCheckBox"))) {
  //           System.out.println("inside stamps checkbox");
                 String stampsKey = 
                     aascShipMethodInfo.getCarrierCodeUserName(115);
                 String stampsPassword = 
                     aascShipMethodInfo.getCarrierCodePassword(115);
                 String stampsIntegrationId = 
                     aascShipMethodInfo.getCodeIntegrationId(115);
                 stampsDetailsList.add(stampsKey);
                 stampsDetailsList.add(stampsPassword);
                 stampsDetailsList.add(stampsIntegrationId);
             }
             if ("true".equalsIgnoreCase(request.getParameter("DHLCheckBox"))) {
//             System.out.println("inside dhl checkbox");
                 String dhlKey = 
                     aascShipMethodInfo.getCarrierCodeUserName(114);
                 String dhlPassword = 
                     aascShipMethodInfo.getCarrierCodePassword(114);
                 String dhlAccountNumber = 
                     aascShipMethodInfo.getCarrierCodeAccountNumber(114);//(114);
     //           System.out.println("dhlKey::::::"+dhlKey);
     //            System.out.println("dhlPassword::::::"+dhlPassword);
     //            System.out.println("dhlAccountNumber::::::"+dhlAccountNumber);
                 String isDutiable = request.getParameter("isDutiable");
                 String dhlDeclaredCurrency = request.getParameter("dhlDeclaredCurrency");
                 String dhlDeclaredValue = request.getParameter("dhlDeclaredValue");
//                 System.out.println("isDutiable:::::"+dhlDeclaredValue);
                 dhlDetailsList.add(dhlKey);
                 dhlDetailsList.add(dhlPassword);
                 dhlDetailsList.add(dhlAccountNumber);
                 dhlDetailsList.add(isDutiable);
                 dhlDetailsList.add(dhlDeclaredCurrency);
                 dhlDetailsList.add(dhlDeclaredValue);
                 
             }

             String URL = resourceBundle.getString("freightShoppingURL");
             String portNumber = 
                 resourceBundle.getString("freightShoppingPort");

             AascFreightShop aascFreightShop = new AascFreightShop();

             aascFreightShopResponse = 
                     aascFreightShop.webServiceContact(appsUserName, 
                                                       appsPassword, 
                                                       appsAccountNumber, 
                                                       shipFrom, shipTo, 
                                                       pkgDescription, 
                                                       timeStamp, sortRule, 
                                                       "false", 
                                                       selectedCarriers, 
                                                       fedexDetailsList, 
                                                       upsDetailsList, 
                                                       tntDetailsList, 
                                                       dhlDetailsList, 
                                                       echoDetailsList, 
                                                       stampsDetailsList, 
                                                       uspsDetailsList, 
                                                       orderNumber, 
                                                       configuredCarriersList, 
                                                       portNumber, URL, 
                                                       cloudLabelPath, decision, 
                                                       serviceCode);

             try {
                 LinkedList shipMethodCodes = 
                     aascFreightShopResponse.getCarrierServiceLevels();
                 Iterator codesIt = shipMethodCodes.iterator();
                
                String respCode = aascFreightShopResponse.getResponseStatus().getCode();
                if(respCode.indexOf("AUTH")!=-1){
                    request.setAttribute("key", "aasc24");
                    request.setAttribute("error", aascFreightShopResponse.getResponseStatus().getDescription());
                    return "success";
                }

                 while (codesIt.hasNext()) {
                     AascFSCarrierServiceLevels carrierServiceLevels = 
                         new AascFSCarrierServiceLevels();
                     carrierServiceLevels = 
                             (AascFSCarrierServiceLevels)codesIt.next();
                     String code = carrierServiceLevels.getCarrierServiceCode();
//                     System.out.println("code:::"+code);
                     if(code.indexOf("US") == 0){
                        code = code.replaceAll("_","-");
                     }
                     else if (!code.equalsIgnoreCase("FEDEX_2_DAY_AM")) {
                         code = code.replaceAll("_", "");
                     }
                     logger.info("shipmethod code in response:::::::" + code);
                     Iterator it2 = configuredCarriers.iterator();
                     while (it2.hasNext()) {
                         String carrierCode =  (String)it2.next(); //  Leaving the carrier code.
//                            it2.next();
//                         System.out.println("carrier code::::::"+carrierCode);
                         String shipMethodsStr = (String)it2.next();
                         String shipMethodName = (String)it2.next();
//                         logger.info("shipMethodsStr :::::" + shipMethodsStr);
//                         logger.info("shipMethodName :::::" + shipMethodName);
                         if (shipMethodsStr.length() == 1 && "UPS".equalsIgnoreCase(carrierCode))
                             shipMethodsStr = "0" + shipMethodsStr;
                         if (shipMethodsStr.equalsIgnoreCase(code)) {
                             carrierServiceLevels.setCarrierServiceName(shipMethodName);

                         }
                     }
                 }
             } catch (Exception e) {
                 logger.severe("Exception :" + e.getMessage());
             }


             request.setAttribute("aascFreightShopResponse", 
                                  aascFreightShopResponse);
             String WSResponse = 
                 aascFreightShopResponse.getResponseStatus().getDescription();
             if ("success".equalsIgnoreCase(WSResponse)) {
 //            System.out.println("in success if");
                 request.setAttribute("key", 
                                      "aasc53"); //Only for Get Rates in Freight Shopping we are taking directly from bean
                 if ("GetRates".equalsIgnoreCase(decision)) {
                     request.setAttribute("ratesFromGetRates", 
                                          ((AascFSCarrierServiceLevels)aascFreightShopResponse.getCarrierServiceLevels().get(0)).getShippingRate());
                     logger.info("rates from web service in Get Rates:::" + 
                                 ((AascFSCarrierServiceLevels)aascFreightShopResponse.getCarrierServiceLevels().get(0)).getShippingRate());
                 }
                 
                 
             } else { // But else condition for both Get Rates and Freight Shopping.
                 request.setAttribute("key", "aasc21");
                 String respFromCarrier = ((AascFSCarriersIncluded)aascFreightShopResponse.getCarriersIncluded().get(0)).getCarrierStatus();
                 logger.info("Failure from Web Service:::" + WSResponse);
                 request.setAttribute("error", respFromCarrier);
                 if ("GetRates".equalsIgnoreCase(decision)) {
                     if("success".equalsIgnoreCase(respFromCarrier)){
                         request.setAttribute("key", "aasc23");
                         request.setAttribute("error", "");
                     }
                 }
//System.out.println("carrier erro::::"+((AascFSCarriersIncluded)aascFreightShopResponse.getCarriersIncluded().get(0)).getCarrierStatus());

                 

             }
             
             LinkedList carriersIncluded = aascFreightShopResponse.getCarriersIncluded();
             for(int i=0;i<carriersIncluded.size();i++){
                 if("UPS".equalsIgnoreCase(((AascFSCarriersIncluded)carriersIncluded.get(i)).getCarrier())){
                     if (countUPS != 0)
                        request.setAttribute("errorUPSFreightShop", 
                                          ((AascFSCarriersIncluded)aascFreightShopResponse.getCarriersIncluded().get(i)).getCarrierStatus());
                 }
                 if("FedEx".equalsIgnoreCase(((AascFSCarriersIncluded)carriersIncluded.get(i)).getCarrier())){
                     if (countFedEx != 0)
                     request.setAttribute("errorFedExFreightShop", 
                                          ((AascFSCarriersIncluded)aascFreightShopResponse.getCarriersIncluded().get(i)).getCarrierStatus());
                 }
                 if("DHL".equalsIgnoreCase(((AascFSCarriersIncluded)carriersIncluded.get(i)).getCarrier())){
                     if (countDHL != 0)
                     request.setAttribute("errorDHLFreightShop", 
                                          ((AascFSCarriersIncluded)aascFreightShopResponse.getCarriersIncluded().get(i)).getCarrierStatus());
                 }
                 if("Stamps".equalsIgnoreCase(((AascFSCarriersIncluded)carriersIncluded.get(i)).getCarrier())){
                     if (countStamps != 0)
                     request.setAttribute("errorStampsFreightShop", 
                                          ((AascFSCarriersIncluded)aascFreightShopResponse.getCarriersIncluded().get(i)).getCarrierStatus());
                 }   
             }

         } catch (Exception e) {
             request.setAttribute("key", "aasc22");
             try{
             String respCode = aascFreightShopResponse.getResponseStatus().getCode();
             if(respCode.indexOf("AUTH")!=-1){
                 request.setAttribute("key", "aasc24");
                 String WSResponse = 
                     aascFreightShopResponse.getResponseStatus().getDescription();
                 request.setAttribute("error", WSResponse);
                 return "success";
             }}catch(Exception e2){
                 logger.severe("Exception e:"+e.getMessage());
             }
             try{
             String WSResponse = 
                 aascFreightShopResponse.getResponseStatus().getDescription();
             logger.info("Error from Web Service in catch:::" + WSResponse);

             if ("Failure".equalsIgnoreCase(WSResponse)) {

                 request.setAttribute("errorFreightShop", "aasc55");
                 
                 LinkedList carriersIncluded = aascFreightShopResponse.getCarriersIncluded();
                 for(int i=0;i<carriersIncluded.size();i++){
                     if("UPS".equalsIgnoreCase(((AascFSCarriersIncluded)carriersIncluded.get(i)).getCarrier())){
                         if (countUPS != 0)
                            request.setAttribute("errorUPSFreightShop", 
                                              ((AascFSCarriersIncluded)aascFreightShopResponse.getCarriersIncluded().get(i)).getCarrierStatus());
                     }
                     if("FedEx".equalsIgnoreCase(((AascFSCarriersIncluded)carriersIncluded.get(i)).getCarrier())){
                         if (countFedEx != 0)
                         request.setAttribute("errorFedExFreightShop", 
                                              ((AascFSCarriersIncluded)aascFreightShopResponse.getCarriersIncluded().get(i)).getCarrierStatus());
                     }
                     if("DHL".equalsIgnoreCase(((AascFSCarriersIncluded)carriersIncluded.get(i)).getCarrier())){
                         if (countDHL != 0)
                         request.setAttribute("errorDHLFreightShop", 
                                              ((AascFSCarriersIncluded)aascFreightShopResponse.getCarriersIncluded().get(i)).getCarrierStatus());
                     }
                     if("Stamps".equalsIgnoreCase(((AascFSCarriersIncluded)carriersIncluded.get(i)).getCarrier())){
                         if (countStamps != 0)
                         request.setAttribute("errorStampsFreightShop", 
                                              ((AascFSCarriersIncluded)aascFreightShopResponse.getCarriersIncluded().get(i)).getCarrierStatus());
                     }   
                 }
                 
             }
             logger.severe("Exception at End:" + e.getMessage());
             }catch(Exception e2){
                 logger.severe("Excetpion :"+e2.getMessage());
             }
         }
         request.setAttribute("upsCheckBox", upsCheckBox);
         request.setAttribute("fedExCheckBox", fedExCheckBox);
         request.setAttribute("dhlCheckBox", dhlCheckBox);
         request.setAttribute("stampsCheckBox", stampsCheckBox);
         request.setAttribute("sortRuleHidden", sortRule);
         logger.info("Exit getFreightShopMethods()");
         return "success";
     }

     /** This method is used to get common variables data from request and session for feature use.
      * @param session, orderNumber1
      * @return String
      */
     public String commonAction(HttpSession session,String orderNumber1) {
         logger.info("Entered execute method");

         String saveHeaderFlag = (String)session.getAttribute("saveHeaderFlag");


         try {
             if (session.isNew() || 
                 !(session.getId().equals(session.getAttribute("SessionId")))) {
                 return "error";
             }

             int userId = 
                 (Integer)session.getAttribute("user_id"); // string userid
             logger.info("userIdstr = " + userId);

             session.removeAttribute("Error");

             int clientId = (Integer)session.getAttribute("client_id");
             logger.info("clientId = " + clientId);

             //            int locationId = Integer.parseInt(request.getParameter("locationId"));
             AascShipSaveDAO aascShipSaveDAO = new AascOracleShipSaveDAO();
             AascShipmentOrderInfo aascShipmentOrderInfo = null;
             aascShipmentOrderInfo = 
                     (AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo");
             AascShipmentHeaderInfo headerBean = 
                 aascShipmentOrderInfo.getShipmentHeaderInfo();
             String orderNumber = "";
             
             if("".equalsIgnoreCase(orderNumber1))
                orderNumber = headerBean.getOrderNumber();
             else   
                 orderNumber = orderNumber1;
                 
             headerBean.setFsFlag("Y");
             aascShipmentOrderInfo.setShipmentHeaderInfo(headerBean);
             if (saveHeaderFlag.equalsIgnoreCase("Y")) {
                 logger.info("save header data");
                 aascShipSaveDAO.getShipSaveDAO(userId, aascShipmentOrderInfo, 
                                                clientId, 122);
                 session.setAttribute("saveHeaderFlag", "N");
             }
             session.setAttribute("submitFlag","Y");
             logger.info("orderNumber in intl delegate = " + orderNumber);
         } catch (Exception e) {
             logger.info("Got Exception in commonAction method " + 
                         e.getMessage());
             //            e.printStackTrace();
             return "error";
         }
         return "success";
     }
 }
