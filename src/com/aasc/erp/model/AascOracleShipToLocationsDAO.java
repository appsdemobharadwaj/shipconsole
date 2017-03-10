 /**
   * <p>AascOracleShipToLocationDAO class retrieves Delivery Information.
   * 
   * @author      Jagadish Jain 
   * @version 1.0 
   * @since 

   * 11/08/2006   Jagadish Jain Created this file and all method required for ShipToLocation setup <br>
   * 17/12/2014   Merged Sunanda code for SC Lite
   *19/12/2014   Has done exception handling,replaced SOPs with logger statements
   * 26/12/2014  Suman G         Merged Sunanda code
   * 20/1/2015   Sunanda K       Added comment for class variables
   * 23/03/2015  Jagadish Jain   Added code for Importing ShipToLocation to db. 
   * 23/03/2015  Sunanda K       Added code fopr newly created fields address line 3 and email address.
   * 11/05/2015  Y Pradeep       Modified code to save email address while uploading from csv file. Bug #2845.
   * 13/05/2015   Y Pradeep      Removed locationId from saveImportedShipToLocations(), getAllCustomerLocationDetails(), getShipToCustomersList() method call to get Ship To Locations independent to Ship From Location
   *                             and commented locationId where it is not necessary.
   * 10/06/2015  Suman G         Added userId in getAllCustomerLocationDetails() to fix #2962 
   * 23/12/2015  Suman G         Added code for Error report for Upload Ship To Locations.
   
   **/
 package com.aasc.erp.model;


 import com.aasc.erp.bean.AascERPImportShipToLocationTBL;
 import com.aasc.erp.bean.AascShipToLocationsInfo;
 import com.aasc.erp.util.AascLogger;
 import java.math.BigDecimal;
 import java.sql.CallableStatement;
 import java.sql.Connection;


 import java.sql.Types;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.LinkedList;
 import java.util.Map;
 import java.util.logging.Logger;

 import javax.sql.DataSource;
 import oracle.jdbc.OracleConnection;
 import oracle.jdbc.OracleTypes;
 import oracle.sql.ARRAY;
 import oracle.sql.ArrayDescriptor;
 import oracle.sql.STRUCT;
 import oracle.sql.StructDescriptor;

 import org.apache.tomcat.dbcp.dbcp.DelegatingConnection;
 import org.springframework.jdbc.core.SqlOutParameter;
 import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
 import org.springframework.jdbc.core.namedparam.SqlParameterSource;
 import org.springframework.jdbc.core.simple.SimpleJdbcCall;
 import org.springframework.jdbc.object.StoredProcedure;

 public class AascOracleShipToLocationsDAO extends StoredProcedure implements AascShipToLocationsDAO {
     public AascOracleShipToLocationsDAO() {
     }
     SimpleJdbcCall simpleJdbcCall;
     private int userId;
     //Following variables used for ShipToLocation Details (Sunanda Added 20/1/2015)
     private int customerLocationId;
     private String shipToCustomerName = "";
     private String phoneNumber = "";
     private String addressLine1 = "";
     private String addressLine2 = "";
     private String addressLine3 = "";
     private String city = "";
     private String state = "";
     private String postalCode;
     private String countryCode = "";
     private String shipToContactName = "";
     private String enableFlag = "";
     private String shipToCustLocationName = "";
     private String emailAddress;
     private int returnStatus; // holds the opStatus value  of the procedure

     CallableStatement callableStatement;
     AascOracleDAOFactory connectionFactory = 
         new AascOracleDAOFactory(); // creating the object for AascPackageInfo
     Connection connection = null; // establishing the connection
     private static Logger logger = 
         AascLogger.getLogger("AascOracleShipToLocationsDAO");
     CallableStatement saveLocationCallableStatement, getLocationCallableStatement, editLocationCallableStatement, getCustomersCallableStatement;
     LinkedList locationList;
     int opStatus;
     String errorStatus;
     private CallableStatement     headerCallableStatement=null;



        /**
        * This method can replace the null values with nullString
        * @return String that is ""
        * @param obj of type Object
        */
     public String nullStrToSpc(Object obj) {
         String spcStr = "";
         if (obj == null) {
             return spcStr;
         } // closing the if block
         else {
             return obj.toString();
         } // closing the else block
     }
     
          /**
           * createCustomerLocation Method interacts with the database 
                      to save and update CustomerLocation Details.
           * @param aascShipToLocationsInfo of AascShipToLocationsInfo class 
           * @return integer  output error status from database whether CustomerLocationsDetails saved or not 

           */

           public HashMap saveImportedShipToLocations(int clientId, int userId,AascERPImportShipToLocationTBL aascERPImportShipToLocationTBL) {
               logger.info("Entered saveImportOrders");
              HashMap resultHM=new HashMap();
           returnStatus=0;
           //          connection =
           //                  connectionFactory.createConnection();
            int uploadId=0;
           DataSource datasource =
                     connectionFactory.createDataSource(); // this method returns the datasources object
           try{
                connection=datasource.getConnection();
                logger.info("Connection:"+connection);
           }
           catch(Exception e){
           logger.info(e.getMessage());
           }
              OracleConnection oracleConnection = (OracleConnection)((DelegatingConnection)connection).getInnermostDelegate();
               
               try {
               
                   int length = aascERPImportShipToLocationTBL.getAascShipToLocationsInfo().length;
                           AascShipToLocationsInfo[] aasc_import_headers = aascERPImportShipToLocationTBL.getAascShipToLocationsInfo();
                   STRUCT[] structArray = new STRUCT[length];
                   for(int i = 0; i<length;i++){
                        Object[] import_headers_array1 = new Object[12];
                        import_headers_array1[0]=aasc_import_headers[i].getShipToCustomerName();
                        import_headers_array1[1]=aasc_import_headers[i].getShipToCustLocation();
                        import_headers_array1[2]=aasc_import_headers[i].getShipToContactName();
                        import_headers_array1[3]=aasc_import_headers[i].getAddressLine1();
                        import_headers_array1[4]=aasc_import_headers[i].getAddressLine2();
                        import_headers_array1[5]=aasc_import_headers[i].getAddressLine3();
                        import_headers_array1[6]=aasc_import_headers[i].getCity();
                        import_headers_array1[7]=aasc_import_headers[i].getState();
                        import_headers_array1[8]=aasc_import_headers[i].getPostalCode();
                        import_headers_array1[9]=aasc_import_headers[i].getCountryCode();
                        import_headers_array1[10]=aasc_import_headers[i].getPhoneNumber();
                        import_headers_array1[11]=aasc_import_headers[i].getEmailAddress();
                  
                   //uncomment below two lines for tomcat
                      StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("AASC_ERP_SHIP_TO_LOCATION_REC",oracleConnection);
                      STRUCT itemObject1 = new STRUCT(itemDescriptor,oracleConnection,import_headers_array1);
                    
                   //uncomment below two lines for jdev
//                           StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("AASC_ERP_SHIP_TO_LOCATION_REC",connection);
//                           STRUCT itemObject1 = new STRUCT(itemDescriptor,connection,import_headers_array1);
                      
                                      structArray[i] = itemObject1;
                  }             
                 
                  //uncomment below two lines for tomcat
                  ArrayDescriptor descriptor =ArrayDescriptor.createDescriptor( "AASC_ERP_SHIP_TO_LOCATION_TBL", oracleConnection );
                   ARRAY array_to_pass =new ARRAY( descriptor, oracleConnection, structArray );
                   
                  //uncomment below two lines for jdev
//                       ArrayDescriptor descriptor =ArrayDescriptor.createDescriptor( "AASC_ERP_SHIP_TO_LOCATION_TBL", connection );
//                       ARRAY array_to_pass =new ARRAY( descriptor, connection, structArray );
                  //uncomment below two lines for tomcat
                         
                  if (connection != null) {
                     
                      headerCallableStatement = 
                              connection.prepareCall("{call " +  
                                                     "AASC_ERP_UPLOAD_LOCATION_PKG.UPLOAD_SHIP_TO_LOCATION(?,?,?,?,?,?)}");
                      headerCallableStatement.setInt(1, userId);
                      headerCallableStatement.setInt(2, clientId);
//                      headerCallableStatement.setInt(3, locationId);
                      headerCallableStatement.setArray(3, array_to_pass);
                      headerCallableStatement.registerOutParameter(4, 
                                                                   Types.INTEGER); // registering the out parameter for opStatus
                      headerCallableStatement.registerOutParameter(5, 
                                                                 Types.INTEGER);
                      headerCallableStatement.registerOutParameter(6, 
                                                                   Types.VARCHAR); // registering the out parameter for error status
                      headerCallableStatement.execute(); // calling the execute() method to execute the procedure
                      
                      uploadId = headerCallableStatement.getInt(4);
                      logger.info("upload id ::::"+uploadId);
                      returnStatus = 
                              headerCallableStatement.getInt(5); // out Parameter returns the opStatus value 
                      errorStatus = 
                              headerCallableStatement.getString(6); // out parameter returns the errorStatus 
                      //aascShipmentHeaderInfo.setOrderNumber(orderNumber); // setting the orderNumber to the aascShipmentHeaderInfo.setOrderNumber(orderNumber) method
                  } //end of if block
                  logger.info("AASC_ERP_CUSTOMER_LOCATION_PKG.UPLOAD_SHIP_TO_LOCATIONS opStatus:" + 
                              returnStatus + "\t errorStatus:" + errorStatus);
                              
                  LinkedList errorList=getUploadErrorStatus(uploadId); 
                  resultHM.put("uploadId",uploadId);
                  resultHM.put("errorList",(Object)errorList);
                  
              } // End of try block.
              catch (Exception e) {
                  logger.severe("Exception::"+e.getMessage());
                  try {
                      connection.rollback();
                     // connection.commit();
                  } //end of inner try block
                  catch (Exception se) {
                      logger.severe("Exception while doing rollback" + 
                                    se.getMessage());
                  } //end of inner catch block
              } //end of catch block

              finally {
                  try {
                      if (headerCallableStatement != null) {
                          try {
                              headerCallableStatement.close();
                              headerCallableStatement = null;
                          } catch (Exception e) {
                              logger.severe("Exception In closing callableStatement of class AascShipSaveDAO" + 
                                            e);
                          }
                      }

                  } //end of finally's try block
                  catch (Exception e) {
                      logger.severe("Got Exception when closing opened resources :" + 
                                    e.getMessage());

                  } //end of finally's catch block
              } //end of finally block 
              logger.info("Exit from saveHeaderInfo");
              return resultHM;
          } // End of saveHeaderInfo() method.

     public int createCustomerLocation(AascShipToLocationsInfo aascShipToLocationsInfo) {
         logger.info("Entered createCustomerLocation()");

         int opStatus = 0;
         try {
            
             String str1 = aascShipToLocationsInfo.getShipToCustomerName();
             logger.info("the length of customer name is " + str1.length());
             DataSource datasource = 
                 connectionFactory.createDataSource(); // this method returns the datasources object
             SqlParameterSource inputparams = 
                 new MapSqlParameterSource()
                 .addValue("ip_client_id",aascShipToLocationsInfo.getClientId())
                 .addValue("ip_user_id",aascShipToLocationsInfo.getUserId())
                 //.addValue("ip_location_id",aascShipToLocationsInfo.getLocationId())
                 .addValue("ip_customer_name",aascShipToLocationsInfo.getShipToCustomerName())
                 .addValue("IP_CUSTOMER_LOC_NAME",aascShipToLocationsInfo.getShipToCustLocation())
                 .addValue("ip_address1",aascShipToLocationsInfo.getAddressLine1())
                 .addValue("ip_address2",aascShipToLocationsInfo.getAddressLine2())
                 .addValue("ip_address3",aascShipToLocationsInfo.getAddressLine3())
                 .addValue("ip_email_id", nullStrToSpc(aascShipToLocationsInfo.getEmailAddress()))
                 .addValue("ip_city",aascShipToLocationsInfo.getCity())
                 .addValue("ip_state",aascShipToLocationsInfo.getState())
                 .addValue("ip_postal_code",aascShipToLocationsInfo.getPostalCode())
                 .addValue("ip_country",aascShipToLocationsInfo.getCountryCode())
                 .addValue("ip_phone_number",aascShipToLocationsInfo.getPhoneNumber())
                 .addValue("ip_contact_name",aascShipToLocationsInfo.getShipToContactName());
                 simpleJdbcCall =new SimpleJdbcCall(datasource)
                 .withCatalogName("AASC_ERP_CUSTOMER_LOCATION_PKG")
                 .withProcedureName("save_ship_to_location")
                 .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                 .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR));
                 //.declareParameters(new SqlOutParameter("aasc_loc_details", OracleTypes.CURSOR));
             
                 Map<String, Object> out = simpleJdbcCall.execute(inputparams);
                 logger.info("After Execute");
                 logger.info("out ::: " + out.toString());
                 opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
                 String error_status = String.valueOf(out.get("op_error_status"));
                 logger.info("opStatus" + opStatus + " && error_status ::" + 
                             error_status);

         } catch (Exception e) {
             logger.severe("Error in closing connection" + e);
             e.printStackTrace();
         }

         logger.info("Exit from createCustomerLocation()");
         return opStatus;
     }
     /**
      * editCusotmerLocation Method interacts with the database 
                 to edit CustomerLocation Details.
      * @param aascShipToLocationsInfo of AascShipToLocationsInfo class 
      * @return integer  output error status from database whether edited CustomerLocationsDetails saved or not 

      */
     public int editCusotmerLocation(AascShipToLocationsInfo aascShipToLocationsInfo) {
         logger.info("Entered editCusotmerLocation()");
         int opStatus = 0;
         try {
             DataSource datasource = 
                 connectionFactory.createDataSource(); // this method returns the datasources object
             SqlParameterSource inputparams = 
                 new MapSqlParameterSource().addValue("ip_client_id",aascShipToLocationsInfo.getClientId())
                 .addValue("ip_user_id",aascShipToLocationsInfo.getUserId())
                 //.addValue("ip_location_id",aascShipToLocationsInfo.getLocationId())
                 .addValue("ip_customer_location_id",aascShipToLocationsInfo.getCustomerLocationId())
                 .addValue("ip_cust_location_name",aascShipToLocationsInfo.getShipToCustLocation())
                 .addValue("ip_address1",aascShipToLocationsInfo.getAddressLine1())
                 .addValue("ip_address2",aascShipToLocationsInfo.getAddressLine2())
                 .addValue("ip_address3",aascShipToLocationsInfo.getAddressLine3())
                 .addValue("ip_email_id", nullStrToSpc(aascShipToLocationsInfo.getEmailAddress()))
                 .addValue("ip_city",aascShipToLocationsInfo.getCity())
                 .addValue("ip_state",aascShipToLocationsInfo.getState())
                 .addValue("ip_postal_code",aascShipToLocationsInfo.getPostalCode())
                 .addValue("ip_country",aascShipToLocationsInfo.getCountryCode())
                 .addValue("ip_phone_number",aascShipToLocationsInfo.getPhoneNumber())
                 .addValue("ip_contact_name",aascShipToLocationsInfo.getShipToContactName());

                 simpleJdbcCall =new SimpleJdbcCall(datasource)
                 .withCatalogName("AASC_ERP_CUSTOMER_LOCATION_PKG")
                 .withProcedureName("update_ship_to_location")
                 .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                 .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR));
                 //   .declareParameters(new SqlOutParameter("aasc_loc_details", OracleTypes.CURSOR));
                 Map<String, Object> out = simpleJdbcCall.execute(inputparams);
                 logger.info("After Execute");
 //                logger.info("out ::: " + out.toString());
                 opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
                 String error_status = String.valueOf(out.get("op_error_status"));
                 logger.info("opStatus" + opStatus + " && error_status ::" + 
                             error_status);
         } catch (Exception e) {
             logger.severe("Error in closing connection" + e);
             e.printStackTrace();
         }
         logger.info("Exit from editCusotmerLocation()");
         return opStatus;
     }
     /**
      * getAllCustomerLocationDetails Method interacts with the database 
                 and retrieves all CustomerLocation Details.
      * @param clientId      int            ClientID of the role 
      * @param userId    int            LocationId of that customer
      * @return LinkedList   locationList   Returns locationDetails for clientID and locationId specified
      */
     public LinkedList getAllCustomerLocationDetails(int clientId, int userId) {
         logger.info("Entered getAllCustomerLocationDetails()");
         locationList = new LinkedList();
         int opStatus = 0;
         try {
             DataSource datasource =connectionFactory.createDataSource(); // this method returns the datasources object
             SqlParameterSource inputparams =new MapSqlParameterSource()
             .addValue("ip_client_id",clientId)
             .addValue("IP_USER_ID",userId);
             //.addValue("ip_location_id",locationId);
             simpleJdbcCall =new SimpleJdbcCall(datasource)
             .withCatalogName("AASC_ERP_CUSTOMER_LOCATION_PKG")
             .withProcedureName("get_ship_to_details")
             .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
             .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR))
             .declareParameters(new SqlOutParameter("aasc_ship_to_loc_details",OracleTypes.CURSOR));
             Map<String, Object> out = simpleJdbcCall.execute(inputparams);
             logger.info("After Execute");
             opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
             String error_status = String.valueOf(out.get("op_error_status"));
             Iterator it =((ArrayList)out.get("OP_LOCATION_DETAILS")).iterator();
             logger.info("opStatus" + opStatus + " && error_status ::" + 
                         error_status);
             while (it.hasNext()) {
                 HashMap<String, ?> map1 = (HashMap<String, ?>)it.next();
                 AascShipToLocationsInfo ascShipToLocationsInfo = 
                     new AascShipToLocationsInfo();

                 customerLocationId =((BigDecimal)map1.get("CUSTOMER_LOCATION_ID")).intValue();
                 shipToCustomerName = nullStrToSpc((String)map1.get("CUSTOMER_NAME"));
                 addressLine1 = nullStrToSpc((String)map1.get("ADDRESS1"));
                 addressLine2 = nullStrToSpc((String)map1.get("ADDRESS2"));
                 addressLine3 = nullStrToSpc((String)map1.get("ADDRESS3"));
                 emailAddress = nullStrToSpc((String)map1.get("EMAIL_ID"));
                 city = nullStrToSpc((String)map1.get("CITY"));
                 state = nullStrToSpc((String)map1.get("STATE_PROVIENCE_CODE"));
                 postalCode = nullStrToSpc((String)map1.get("POSTAL_CODE"));
                 countryCode = nullStrToSpc((String)map1.get("COUNTRY_CODE"));
                 phoneNumber = nullStrToSpc((String)map1.get("PHONE_NUMBER"));
                 shipToContactName = 
                         nullStrToSpc((String)map1.get("SHIP_TO_CONTACT_NAME")); //Gururaj added  code for contact name field in  Ship from address     
                 shipToCustLocationName = 
                         nullStrToSpc((String)map1.get("CUSTOMER_LOCATION_NAME"));
                 // ascShipToLocationsInfo.setShipToCustLocation((String)map1.get("CUSTOMER_LOCATION_NAME"));
                 enableFlag = nullStrToSpc((String)map1.get("ENABLE_FLAG"));
                 ascShipToLocationsInfo.setClientId(clientId);
                 ascShipToLocationsInfo.setUserId(userId);
//                 ascShipToLocationsInfo.setLocationId(locationId);
                 ascShipToLocationsInfo.setCustomerLocationId(customerLocationId);
                 ascShipToLocationsInfo.setShipToCustomerName(shipToCustomerName);
                 ascShipToLocationsInfo.setAddressLine1(addressLine1);
                 ascShipToLocationsInfo.setAddressLine2(addressLine2);
                 ascShipToLocationsInfo.setAddressLine3(addressLine3);
                 ascShipToLocationsInfo.setEmailAddress(emailAddress);
                 ascShipToLocationsInfo.setCity(city);
                 ascShipToLocationsInfo.setState(state);
                 ascShipToLocationsInfo.setPostalCode(postalCode);
                 ascShipToLocationsInfo.setCountryCode(countryCode);
                 ascShipToLocationsInfo.setPhoneNumber(phoneNumber);
                 ascShipToLocationsInfo.setShipToContactName(shipToContactName); //Gururaj added  code for contact name field in  Ship from address
                 ascShipToLocationsInfo.setEnableFlag(enableFlag);
                 ascShipToLocationsInfo.setShipToCustLocation(shipToCustLocationName);
                 locationList.add(ascShipToLocationsInfo);
             }

         } catch (Exception e) {
             logger.severe("Error in closing connection" + e);
             e.printStackTrace();
         }
         logger.info("Exit from getAllCustomerLocationDetails");
         logger.info("before returning locationList size" + 
                     locationList.size());
         return locationList;
     }
     /**
      * getAllLocationDetails Method interacts with the database 
                 and retrieves all Location Details.
      * @param clientId      int            ClientID of the role 
      * @param locationId    int            LocationId of that customer
      * @return LinkedList   customersList  Returns ShipToCustomer details for clientID and locationId specified
      */
     public LinkedList getShipToCustomersList(int clientId) {
         LinkedList customersList = new LinkedList();
         logger.info("Entered getShipToCustomersList()");
         int opStatus = 0;
         try {
             DataSource datasource = 
                 connectionFactory.createDataSource(); // this method returns the datasources object
             String unitValue = null;
             SqlParameterSource inputparams =new MapSqlParameterSource().addValue("ip_client_id",clientId);
             //.addValue("ip_location_id",locationId);
             simpleJdbcCall =new SimpleJdbcCall(datasource)
             .withCatalogName("AASC_ERP_CUSTOMER_LOCATION_PKG")
             .withProcedureName("get_ship_to_customers_list")
             .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
             .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR))
             .declareParameters(new SqlOutParameter("aasc_ship_to_customers_list",OracleTypes.CURSOR));
             Map<String, Object> out = simpleJdbcCall.execute(inputparams);
             logger.info("After Execute");
             opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
             String error_status = String.valueOf(out.get("op_error_status"));
             Iterator it = ((ArrayList)out.get("OP_CUSTOMERS_LIST")).iterator();
             while (it.hasNext()) {
                 HashMap<String, ?> map1 = (HashMap<String, ?>)it.next();
                 unitValue = (String)map1.get("customer_name");
                 customersList.add(unitValue);
             }
             logger.info("opStatus" + opStatus + " && error_status ::" + 
                         error_status);

         } catch (Exception e) {
             logger.severe("Error in closing connection" + e);
             e.printStackTrace();
         }
         logger.info("Exit from getShipToCustomersList()");
         return customersList;
     }
     
     /**
     * getUploadErrorStatus() method of AascOracleSaveOrderImport class has three parameters clientId, locationId and importId
     * and returns a LinkedList object with errored out data.
     * This method takes import id as one of the input and returns the failed records for that import id. 
     * @param uploadId int
     * @return LinkedList
     */
     public LinkedList getUploadErrorStatus( int uploadId
                                    ) {
         LinkedList errorList=new LinkedList();
         try {
             logger.info("Entered getImportErrorStatus()");
             
             opStatus=0;  
     //              connection =
     //                      connectionFactory.createConnection(); // this method returns the oracleConnection object
            DataSource datasource = 
                          connectionFactory.createDataSource(); // this method returns the datasources object

            SqlParameterSource inputparams = 
                               new MapSqlParameterSource().addValue("IP_UPLOAD_ID",uploadId);
             simpleJdbcCall = 
                     new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_UPLOAD_LOCATION_PKG")
                     .withProcedureName("UPLOAD_ORDH_REPORT").declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER)).
                     declareParameters(new SqlOutParameter("OP_ERROR_MESSAGE",Types.VARCHAR)).
                     declareParameters(new SqlOutParameter("OP_TOTAL_REC_COUNT",Types.VARCHAR)).
                     declareParameters(new SqlOutParameter("OP_VALID_REC_COUNT",Types.VARCHAR)).
                     declareParameters(new SqlOutParameter("OP_ERROR_REC_COUNT",Types.VARCHAR)).
                     declareParameters(new SqlOutParameter("get_upload_shipto_details",OracleTypes.CURSOR));
                     
             Map<String, Object> out = simpleJdbcCall.execute(inputparams);

             logger.info("After Execute");
             logger.info("out ::: " + out.toString());
             
             if (out.get("OP_STATUS") != null) {
                 opStatus = 
                         Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
             }
             
             String errorStatus = String.valueOf(out.get("OP_ERROR_MESSAGE"));
             logger.info("Errostatus:"+opStatus);
             logger.info("ErrorMessage"+errorStatus);
             
             Iterator it = ((ArrayList)out.get("OP_ORDER_HEADER_DET")).iterator();
                
             while (it.hasNext()) {
                 HashMap<String, ?> map1 = (HashMap<String, ?>)it.next();
                 logger.info("Error"+(String)map1.get("CUSTOMER_NAME"));
                 errorList.add(map1);
                 
     //                  errorList.add(((String)map1.get("ERROR_CODE")));
     //                  errorList.add(((String)map1.get("ERROR_CODE")));
                 
             }
          
                      
     
         } catch (Exception e) {
             logger.severe("exception in getting import orders error status" + 
                           e.getMessage());

         } finally {
             try {
                 if (headerCallableStatement != null) {
                     try {
                         headerCallableStatement.close();
                         headerCallableStatement = null;
                         logger.info("closing  headerCallableStatement in getShipSaveDAO() method");
                     } catch (Exception e) {
                         logger.severe("error while closing  headerCallableStatement in getShipSaveDAO() method" + 
                                       e);
                     }
                 }

                 if (connection != null) {
               //      connection.commit();
                     connectionFactory.closeConnection(connection);
                     if (connection != null)
                         connection.close();
                 } //end of if block
             } //end of finally's try block
             catch (Exception e) {
                 logger.severe("Got exception in closing connection" + 
                               e.getMessage());

             } //end of finally's catch block
         } //end of finally block 

         logger.info("Exit from getShipSaveDAO()");
         return errorList;
     } 
 }
