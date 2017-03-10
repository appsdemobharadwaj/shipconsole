 /*
   DESCRIPTION
     Class to get Ship to Adress line 1  Values from data base
     Author 
     Version - 1
     History
         18/11/2014    Eshwari    Added this class from Cloud 1.2 version
         29/12/2014    Eshwari M  Modified getShipFromAddr() to get the company name from profile options based on the location changed
         02/01/2015    Y Pradeep  Modified method names from getShipToAddr to getCustLocationName, getAddressline1 to getAllShipToDetails, getToAddress to getAllShipToDetails and added getShipToCustomers method.
                                  Converted all above methods to Spring JDBC. Also modified related packages.
         07/01/2015    Y Pradeep  Merged Sunanda's code : Converted method getShipFromAddressline1 and getShipFromAddr into Spring JDBC.
         21/01/2015    Suman G    Added two variables in getShipFromAddressline1() method for issue #2491.
         21/01/2015    Suman G    Added one op parameter to procedure in getShipFromAddressline1() method for issue #2493.
         02/02/2015    Suman G    Added op parameter to procedure in getShipFromAddressline1() method for issue #2490.
         09/03/2015    Sunanda k  Modified code for Profile Options Details of the Ship From Location selected
         24/04/2015    Eshwari M  Modified code to get the status of profile options contigured or not for ship From Location
         11/05/2015    Y Pradeep  Added code in getAllShipToDetails method to load address3 and email_id in Ajax call from database. Bug #2845.
         03/06/2015    Y Pradeep  Added code to get selected Weighing Scale details from Profile OPtions page.
         30/07/2015    Y Pradeep  Added code to get configured Printer details from Printer Configuration page. Bug #3289.
         29/10/2015    N Srisha   Added code to default the ship from email id from profile options. Bug #3494.
         09/03/2016    Y Pradeep  Added getWeighingScaleDetails method to get weighing scale details from db after ship button is clicked. Bug #4377.
         11/03/2016    Y Pradeep  Removed Printer IP Address related code as it is not required.
 */


 package com.aasc.erp.model;

 import com.aasc.erp.bean.AascShipToLocationsInfo;
 import com.aasc.erp.bean.AascShipmentHeaderInfo;
 import com.aasc.erp.util.AascLogger;

 import java.math.BigDecimal;


 import java.sql.Types;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.LinkedList;
 import java.util.List;
 import java.util.Map;
 import java.util.logging.Logger;

 import javax.sql.DataSource;

 import oracle.jdbc.OracleTypes;

 import org.springframework.jdbc.core.SqlOutParameter;
 import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
 import org.springframework.jdbc.core.namedparam.SqlParameterSource;
 import org.springframework.jdbc.core.simple.SimpleJdbcCall;


 public class AascOracleShippingAddr {
     private AascOracleDAOFactory aascOracleDAOFactory = 
         new AascOracleDAOFactory(); // class used to create connection
     private AascShipmentHeaderInfo aascHeaderInfo = null;
     private String shipToLocation = "";
     private String shipToLocationAddr = "";
     private static Logger logger = AascLogger.getLogger("AascOracleShippingAddr"); // logger object used for issuing logging requests
     SimpleJdbcCall simpleJdbcCall;


     /**
      * This method get ship to custmer names form the erp_customers.
      * @param clientId       int     client Id of the user logged in
      * @return customerList List contains shipTo Customers list
      */
     public List getShipToCustomers(int clientId, int userId, String addressBookLevel) {
         logger.info("Entered getShipToCustomers()");
         List customerList = new LinkedList();
         try {
             DataSource dataSource = aascOracleDAOFactory.createDataSource();

             simpleJdbcCall = new SimpleJdbcCall(dataSource).withCatalogName("AASC_ERP_ORDER_ADDR_PKG")
                                                            .withProcedureName("erp_customers")
                                                            .declareParameters(new SqlOutParameter("custnametyp", OracleTypes.CURSOR));
             logger.info("userId == "+userId+" addressBookLevel == "+addressBookLevel);
             SqlParameterSource inputparam = new MapSqlParameterSource().addValue("ip_client_id", clientId)
                                                                        .addValue("ip_user_id", userId)
                                                                        .addValue("ip_address_book_level", addressBookLevel);

             Map<String, Object> out = simpleJdbcCall.execute(inputparam);
             
             Iterator customerDetailsIt = ((ArrayList)out.get("OP_CUST_NAME")).iterator();
             AascShipToLocationsInfo aascShipToLocationsInfo = null;
             HashMap<String, ?> map1 = null;

             while(customerDetailsIt.hasNext()){
                 map1 = (HashMap<String, ?>)customerDetailsIt.next();
 //                logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());
                 aascShipToLocationsInfo = new AascShipToLocationsInfo();
                 aascShipToLocationsInfo.setCustomerId(((BigDecimal)map1.get("CUSTOMER_ID")).intValue());
                 aascShipToLocationsInfo.setShipToCustomerName(String.valueOf(map1.get("CUSTOMER_NAME")));
                 customerList.add(aascShipToLocationsInfo);
                 
                 map1.clear();
             }
             
         } catch (Exception e) {
             logger.severe("Exception::"+e.getMessage());
         } 
         
         logger.info("Exit from getShipToCustomers()");
         return customerList;
     }

     /**  This method gets the Ship From  Address for the given clientId and locationId.
      * @param aascHeaderInfo object  AascShipmentHeaderInfo
      * @param locationId     int     location id 
      * @param clientID       int     client Id of the user logged in
      * @param userId         int     userId of the user who has logged in  
      * @return object of AascShipmentHeaderInfo
      */
     public AascShipmentHeaderInfo getShipFromAddr(AascShipmentHeaderInfo aascHeaderInfo, 
                                                   int locationId, int clientID, 
                                                   int userId) {
         logger.info("Entered getShipFromAddr()");
         this.aascHeaderInfo = new AascShipmentHeaderInfo();
         try {
             DataSource dataSource = aascOracleDAOFactory.createDataSource();
             SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_location_id",locationId).addValue("ip_client_id",clientID).addValue("ip_user_id",userId);
             simpleJdbcCall = new SimpleJdbcCall(dataSource).withCatalogName("AASC_ERP_ORDER_ADDR_PKG")
                                                            .withProcedureName("ship_from_addr").declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                                            .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR))
                                                            .declareParameters(new SqlOutParameter("custaddresstyp",OracleTypes.CURSOR));
             Map<String, Object> out = simpleJdbcCall.execute(inputparams);

             Iterator customerAddressIt = 
                 ((ArrayList)out.get("OP_CUST_ADDR")).iterator();
 //            AascShipToLocationsInfo aascShipToLocationsInfo = null;
             HashMap<String, ?> map1 = null;

             while (customerAddressIt.hasNext()) {
                 map1 = (HashMap<String, ?>)customerAddressIt.next();
                 //                logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());


                 aascHeaderInfo.setShipFromLocation(nullStringToSpcace(map1.get("location_name")));
                 aascHeaderInfo.setShipFromAddressLine1(nullStringToSpcace(map1.get("addressline1")));
                 aascHeaderInfo.setShipFromAddressLine2(nullStringToSpcace(map1.get("addressline2")));
                 aascHeaderInfo.setShipFromAddressLine3(nullStringToSpcace(map1.get("addressline3")));
                 aascHeaderInfo.setShipFromCity(nullStringToSpcace(map1.get("city")));
                 aascHeaderInfo.setShipFromState(nullStringToSpcace(map1.get("state")));

                 aascHeaderInfo.setShipFromCountry(nullStringToSpcace(map1.get("country")));
                 aascHeaderInfo.setShipFromPostalCode(nullStringToSpcace(map1.get("postalcode")));
                 aascHeaderInfo.setShipFromPhoneNumber1(nullStringToSpcace(map1.get("contact_phonenumber")));
                 aascHeaderInfo.setShipFromPhoneNumber2(nullStringToSpcace(map1.get("telephone_number_2")));
             }
             logger.info("Ship from address Values are set to aascHeaderInfo bean");
         } catch (Exception e) {

             logger.severe("Exception::"+e.getMessage());
         }

         logger.info("Exit from getShipFromAddr()");
         return aascHeaderInfo;
     }

      /**
       * This method will interact with database and get customer location name.
       * @param  customerName    String    customer name selected from the drop down list
       * @param  clientId        int       client id of the user logged in
       * @return shipToLocation  String    Contains Location names of the selected customer
       */
      public String getCustLocationName(String customerName,int clientId) {
          logger.info("Entered getCustLocationName()");
          try {
              DataSource dataSource = aascOracleDAOFactory.createDataSource();
              
              simpleJdbcCall = new SimpleJdbcCall(dataSource).withCatalogName("AASC_ERP_ORDER_ADDR_PKG")
                                                             .withProcedureName("erp_cust_address")
                                                             .declareParameters(new SqlOutParameter("aasc_cust_locations", OracleTypes.CURSOR));
              
              SqlParameterSource inputparam = new MapSqlParameterSource().addValue("ip_client_id", clientId).addValue("ip_cust_name", customerName);

              Map<String, Object> out = simpleJdbcCall.execute(inputparam);
              
              Iterator customerDetailsIt = ((ArrayList)out.get("OP_CUST_LOCATIONS")).iterator();
              HashMap<String, ?> map1 = null;

              while(customerDetailsIt.hasNext()){
                  map1 = (HashMap<String, ?>)customerDetailsIt.next();
 //                 logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());
                  shipToLocation = shipToLocation + String.valueOf(map1.get("CUSTOMER_LOCATION_NAME")) + "***" + String.valueOf(map1.get("CUSTOMER_LOCATION_ID"))+"@";
                  
                  map1.clear();
              }

          }

          catch (Exception e) {
              logger.severe("Exception::"+e.getMessage());
          }
 //         logger.info("shipToLocation ==" + shipToLocation);
          logger.info("Exit from getCustLocationName()");
          return shipToLocation;

      }


      /**  This method gets the address of the ship from address and retunrs as string.
       * @param customerName  String   customer name selected from the drop down list
       * @param clientID      int      client id of the user logged in
       * @return String Contains Ship from Addressline1 of the selected customer
       */
      
     public String getShipFromAddressline1(int locationId,int clientID) {
         logger.info("Entered getShipFromAddressline1()");
         logger.info("locationId in DAO ::"+locationId+" && clientID ::"+clientID);
         StringBuffer shipFromAddress1 = new StringBuffer("@@@");
         String shipFromAddress2="";
         String printerDetails = "";
         try {
             DataSource dataSource = aascOracleDAOFactory.createDataSource();
             SqlParameterSource inputparams = new MapSqlParameterSource()
                                             .addValue("ip_location_id", locationId)
                                             .addValue("ip_client_id",clientID);

             simpleJdbcCall =new SimpleJdbcCall(dataSource).withCatalogName("AASC_ERP_ORDER_ADDR_PKG")
                                                           .withProcedureName("cust_ship_from_addr")
                                                           .declareParameters(new SqlOutParameter("OP_WEIGHING_SCALE",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("OP_VENDOR_ID",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("OP_PRODUCT_ID1",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("OP_PRODUCT_ID2",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("OP_TOPOLOGY",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("AASC_PRINTER_SETUP_DETAILS", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("custaddrtyp",OracleTypes.CURSOR));
             Map<String, Object> out = simpleJdbcCall.execute(inputparams);
             
             int opStatus = ((BigDecimal)out.get("OP_STATUS")).intValue() ;
             logger.info("OP_STATUS : "+opStatus);
             logger.info("OP_ERROR_STATUS : "+(String)out.get("OP_ERROR_STATUS"));
             Iterator ShipFromAddressline = 
                 ((ArrayList)out.get("OP_CUST_FROM_ADDR")).iterator();
 //            AascShipToLocationsInfo aascShipToLocationsInfo = null;
             HashMap<String, ?> map1 = null;
             logger.info("List size : "+((ArrayList)out.get("OP_CUST_FROM_ADDR")).size());
             if(opStatus == 1)
             {
               while (ShipFromAddressline.hasNext()) {
                 map1 = (HashMap<String, ?>)ShipFromAddressline.next();
                 
                 shipFromAddress1.append((nullStringToSpcace(map1.get("ADDRESS1")))+ "***")
                                 .append((nullStringToSpcace(map1.get("ADDRESS2"))) + "***")
                                 .append((nullStringToSpcace(map1.get("CITY"))) + "***")
                                 .append((nullStringToSpcace(map1.get("STATE_PROVIENCE_CODE"))) + "***")
                                 .append((nullStringToSpcace(map1.get("POSTAL_CODE"))) + "***")
                                 .append((nullStringToSpcace(map1.get("COUNTRY_CODE"))) + "***")
                                 .append((nullStringToSpcace(map1.get("PHONE_NUMBER")))+ "***")
                                 .append((nullStringToSpcace(map1.get("CONTACT_NAME")))+ "***")
                                 .append((nullStringToSpcace(map1.get("EMAIL_ID")))+ "***") // N Srisha added code to default the ship from email id from profile options, Bug #3494
                                 .append((nullStringToSpcace(map1.get("COMPANY_NAME")))+ "***")
                                 .append((nullStringToSpcace(map1.get("REFERENCE1")))+ "***")
                                 .append((nullStringToSpcace(map1.get("REFERENCE2")))+ "***")
                                 .append((nullStringToSpcace(map1.get("DEFAULT_CARRIER_PAY_METHOD")))+ "***")
                                 .append((nullStringToSpcace(map1.get("ENABLE_SAT_FLAG")))+ "***")
                                 .append((nullStringToSpcace(map1.get("EDIT_SHIP_TO_ADDRESS")))+ "***")
                                 .append((nullStringToSpcace(map1.get("EDIT_CUST_NAME")))+ "***")
                                 .append((nullStringToSpcace(map1.get("EDIT_ADDRESS_LINES")))+ "***")
                                 .append((nullStringToSpcace(map1.get("EDIT_CITY")))+ "***")
                                 .append((nullStringToSpcace(map1.get("EDIT_STATE")))+ "***")
                                 .append((nullStringToSpcace(map1.get("EDIT_POSTAL_CODE")))+ "***")
                                 .append((nullStringToSpcace(map1.get("EDIT_COUNTRY_CODE")))+ "***")
                                 .append(nullStringToSpcace(String.valueOf(out.get("OP_WEIGHING_SCALE")))+ "***")
                                 .append(nullStringToSpcace(String.valueOf(out.get("OP_VENDOR_ID")))+ "***")
                                 .append(nullStringToSpcace(String.valueOf(out.get("OP_PRODUCT_ID1")))+ "***")
                                 .append(nullStringToSpcace(String.valueOf(out.get("OP_PRODUCT_ID2")))+ "***")
                                 .append(nullStringToSpcace(String.valueOf(out.get("OP_TOPOLOGY")))+ "***")
                                 .append((nullStringToSpcace(map1.get("MASK_ACCOUNT_NUMBER")))+ "***")
                  .append((nullStringToSpcace(map1.get("WEIGHING_SCALE")))+ "***")
                  .append((nullStringToSpcace(map1.get("SHIPPER_NAME")))+ "***");

                /* shipFromAddress1 = 
                         shipFromAddress1 + out.get("op_company_name") + "***";
                 String reference1 = (String)out.get("op_reference1");
                 String reference2 = (String)out.get("op_reference2"); */
               }

               ArrayList printerList = ((ArrayList)out.get("OP_PRINTER_SETUP_DETAILS"));
               printerDetails = getPrinterDetails(printerList);
                 
             }
             else if(opStatus == 2)
             {
                 shipFromAddress1.append("Profile Options not Configured") ;
             }
             
             shipFromAddress2 = shipFromAddress1.toString() + printerDetails;
         }

         catch (Exception e) {
             logger.severe("Exception::"+e.getMessage());
         }
         logger.info("shipFromAddress2==" + shipFromAddress2);
         logger.info("Exit from getShipFromAddressline1()");
         return shipFromAddress2;
  
     }
     
     /** getPrinterDetails method is used to iterate and get all printer details and append as string.
      * @param printerList of ArrayList
      * @return printerDetailsof String
      */
     public String getPrinterDetails(ArrayList printerList){
         String printerDetails = "";
         StringBuffer printerListStr = new StringBuffer("");
         try{
               
             Iterator it = printerList.iterator();
             HashMap<String, ?> map1 = null;
             if (!printerList.isEmpty()) {
                 while (it.hasNext()) {
                     String printerName = "";
                     map1 = (HashMap<String, ?>)it.next();
                     if("Y".equalsIgnoreCase((String)map1.get("ENABLE_FLAG"))){
                         printerName = (String)map1.get("PRINTER_NAME");
                     }
                     printerListStr.append(nullStringToSpcace((String)map1.get("LABEL_FORMAT"))+ "***")
                                   .append(nullStringToSpcace(printerName)+ "***");
                 }
             } else {
                 printerListStr.append("Please configure printers for this location");
             }
             printerDetails = printerListStr.toString();
             logger.info("Printer details == "+printerDetails);
         } catch(Exception e){
             logger.severe("Exception e : "+e.getMessage());
         }
         logger.info("Exit from getPrinterSetupInfo()");
                
         return printerDetails;
            
     }
     
     /**
      * getAllShipToDetails Method interacts with the database 
                 and retrieves all ship to location details.
      * @param customerName String, shipToLocaation String and clientId int 
      * @return shipToLocationAddr String
      */
     public String getAllShipToDetails(String shipToLocation, String customerName,int clientId)    {
          logger.info("Entered getAllShipToDetails()");           
          logger.info("clientId :::::::"+clientId);
          logger.info("customerName :::::::"+customerName);
          logger.info("shipToLocation :::::::"+shipToLocation);
          
          try{
              
              DataSource  datasource = aascOracleDAOFactory.createDataSource(); // this method returns the datasources object
          
              SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_CLIENT_ID",clientId)
                                                                          .addValue("IP_CUSTOMER_NAME",customerName)
                                                                          .addValue("IP_SHIPTO_LOCATION",shipToLocation);
          
              simpleJdbcCall = new SimpleJdbcCall(datasource)
                             .withCatalogName("AASC_ERP_ORDER_ADDR_PKG")
                             .withProcedureName("SHIP_TO_ADDR")
                             .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                             .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR))
                             .declareParameters(new SqlOutParameter("AASC_SHIPTO_DETAILS", OracleTypes.CURSOR));
              
              Map<String,Object> out = simpleJdbcCall.execute(inputparams);
              
     //         opStatus=  Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
     //         errorStatus =  String.valueOf(out.get("OP_ERROR_STATUS"));
              Iterator it= ((ArrayList)out.get("OP_SHIPTO_LOCATIONS")).iterator();
              HashMap<String,?>  map1 = null ;
              
              while(it.hasNext()){
                  map1 =(HashMap<String,?>)it.next();
           
                  //logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());
                   shipToLocationAddr = shipToLocationAddr +
 //                                                          ((BigDecimal)map1.get("CUSTOMER_LOCATION_ID")).intValue() + 
 //                                                          "***" +
 //                                                          nullStringToSpcace((String)map1.get("CUSTOMER_LOCATION_NAME")) +
 //                                                          "***" +
                                                           nullStringToSpcace((String)map1.get("ADDRESS1")) +
                                                           "***" +
                                                           nullStringToSpcace((String)map1.get("ADDRESS2")) +
                                                           "***" +
                                                           nullStringToSpcace((String)map1.get("ADDRESS3")) +
                                                           "***" +
                                                           nullStringToSpcace((String)map1.get("CITY")) +
                                                           "***" +
                                                           nullStringToSpcace((String)map1.get("STATE_PROVIENCE_CODE")) +
                                                           "***" +
                                                           nullStringToSpcace((String)map1.get("COUNTRY_CODE")) +
                                                           "***" +
                                                           nullStringToSpcace((String)map1.get("POSTAL_CODE")) +
                                                           "***" +
                                                           nullStringToSpcace((String)map1.get("SHIP_TO_CONTACT_NAME")) + 
                                                           "***" +
                                                           nullStringToSpcace((String)map1.get("PHONE_NUMBER")) +
                                                           "***" +
                                                           nullStringToSpcace((String)map1.get("EMAIL_ID")) +
                                                           "***";
                  
                  map1.clear();
             }
          
         }catch (Exception e) {
             logger.severe("Got Exception in Getting  ship to address of a customer" + 
                           e.getMessage());

         }
          logger.info("shipToAddress== " + shipToLocationAddr);
         logger.info("Exit from getAllShipToDetails()");
         return shipToLocationAddr;

      } // closing the getAllShipToDetails method


       /**  This method gets the weighingscale details and retunrs as string.
        * @param customerName  String   customer name selected from the drop down list
        * @param clientID      int      client id of the user logged in
        * @return String Contains weighing scale details of the selected location
        */
       
       public String getWeighingScaleDetails(int locationId,int clientID) {
          logger.info("Entered getWeighingScaleDetails()");
          logger.info("locationId in DAO ::"+locationId+" && clientID ::"+clientID);
          StringBuffer weighingScaleDetails = new StringBuffer("@@@");
          String weighingScale = "";
          try {
              DataSource dataSource = aascOracleDAOFactory.createDataSource();
              SqlParameterSource inputparams = new MapSqlParameterSource()
                                              .addValue("ip_location_id", locationId)
                                              .addValue("ip_client_id",clientID);

              simpleJdbcCall =new SimpleJdbcCall(dataSource).withCatalogName("AASC_ERP_ORDER_ADDR_PKG")
                                                            .withProcedureName("GET_WEIGHING_SCALE_DETAILS")
                                                            .declareParameters(new SqlOutParameter("OP_WEIGHING_SCALE",Types.VARCHAR))
                                                            .declareParameters(new SqlOutParameter("OP_VENDOR_ID",Types.VARCHAR))
                                                            .declareParameters(new SqlOutParameter("OP_PRODUCT_ID1",Types.VARCHAR))
                                                            .declareParameters(new SqlOutParameter("OP_PRODUCT_ID2",Types.VARCHAR))
                                                            .declareParameters(new SqlOutParameter("OP_TOPOLOGY",Types.VARCHAR))
                                                            .declareParameters(new SqlOutParameter("OP_WEIGHING_SCALE_OPTION",Types.VARCHAR))
                                                            .declareParameters(new SqlOutParameter("OP_MASK_ACCOUNT_CHECK",Types.VARCHAR))
                                                            .declareParameters(new SqlOutParameter("OP_SHIPPER_NAME",Types.VARCHAR));
              Map<String, Object> out = simpleJdbcCall.execute(inputparams);
              
              int opStatus = ((BigDecimal)out.get("OP_STATUS")).intValue() ;
              logger.info("OP_STATUS : "+opStatus);
              logger.info("OP_ERROR_STATUS : "+(String)out.get("OP_ERROR_STATUS"));

                  weighingScaleDetails.append(nullStringToSpcace(String.valueOf(out.get("OP_WEIGHING_SCALE")))+ "***")
                                      .append(nullStringToSpcace(String.valueOf(out.get("OP_VENDOR_ID")))+ "***")
                                      .append(nullStringToSpcace(String.valueOf(out.get("OP_PRODUCT_ID1")))+ "***")
                                      .append(nullStringToSpcace(String.valueOf(out.get("OP_PRODUCT_ID2")))+ "***")
                                      .append(nullStringToSpcace(String.valueOf(out.get("OP_TOPOLOGY")))+ "***")
                                      .append(nullStringToSpcace(String.valueOf(out.get("OP_WEIGHING_SCALE_OPTION")))+ "***")
                                     .append(nullStringToSpcace(String.valueOf(out.get("OP_MASK_ACCOUNT_CHECK")))+ "***")
                                     .append(nullStringToSpcace(String.valueOf(out.get("OP_SHIPPER_NAME")))+ "***");
              weighingScale = weighingScaleDetails.toString();
          }

          catch (Exception e) {
              logger.severe("Exception::"+e.getMessage());
          }
          logger.info("weighingScale==" + weighingScaleDetails);
          logger.info("Exit from getWeighingScaleDetails()");
          return weighingScale;
       
       }

     /**
      * This method can replace the null values with nullString.
      * @param  object  Object
      * @return String that is ""
      */
     public String nullStringToSpcace(Object object) {
         String spcStr = "";
         if (object == null || "null".equalsIgnoreCase(object.toString())) {
             return spcStr;
         } //end of if block
         else {
             return object.toString();
         } //end of else block
     } //end of nullStringToSpcace method


 }
