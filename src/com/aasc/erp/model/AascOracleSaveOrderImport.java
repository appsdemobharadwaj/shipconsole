package com.aasc.erp.model;

import com.aasc.erp.bean.AascERPImportOrdHREC;
import com.aasc.erp.bean.AascERPImportOrdHTBL;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.util.AascLogger;

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
//import org.apache.commons.dbcp.DelegatingConnection;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


/**
 * AascOracleSaveOrderImport class is DAO factory class class for IMport orders.
 * @version   1
 * @author    Jagadish Jain
 * History
 *
 * 24/12/2014   Jagadish Jain   Wrote complete code required to upload the imported order from csv to SC Lite databse table.
 * 11/05/2015   Suman G         Modified code to fix #2753
 */

public class AascOracleSaveOrderImport implements AascSaveOrderImportDAO{
    public AascOracleSaveOrderImport() {
    }
    
    SimpleJdbcCall simpleJdbcCall;

    private int returnStatus; // holds the opStatus value  of the procedure
    private String errorStatus; // holds the errorStatus value  of the procedure
       

    private int opStatus;

   
    private AascOracleDAOFactory connectionFactory = 
        new AascOracleDAOFactory(); // Creating a object of AascOracleDAOFactory class.
    private Connection connection = 
        null; // holds the object of the oracle connection
    private CallableStatement     headerCallableStatement=null;
    private static Logger logger = 
        AascLogger.getLogger("AascOracleSaveOrderImport"); // this is a static method of the AaascLogger class which returns the object of the Logger class

    
    //Added
    boolean checkConnection = true;

    AascShipMethodInfo shipMethodInfo;

  
    
    /**
    * saveImportOrders() method of AascOracleSaveOrderImport class has four parameters clientId, locationId,userId  and AascERPImportOrdHTBL
    * object and returns a Hashmap object with import id and error list.
    * This method is used to send the import order file data to the staging table where validations are perfomed. 
    * The errored out data will remain in staging table while success will be moved to shipments header table. 
    * @param clientId int, int locationId, int userId,AascERPImportOrdHTBL AascERPImportOrdHTBL
    * @return Hashmap
    */
     public HashMap saveImportOrders(int clientId, int locationId, int userId,AascERPImportOrdHTBL AascERPImportOrdHTBL) {
         logger.info("Entered saveImportOrders");
         
//          connection = 
//                  connectionFactory.createConnection();

 DataSource datasource = 
               connectionFactory.createDataSource(); // this method returns the datasources object
try{
               connection=datasource.getConnection();
}
catch(Exception e){
    logger.info(e.getMessage());
}
         OracleConnection oracleConnection = (OracleConnection)((DelegatingConnection)connection).getInnermostDelegate();   // UnComment this line for tomcat
//                  logger.info("Connection:"+connection);
         HashMap resultHM=new HashMap();
         int importId=0;
         
         try {
         
             int length = AascERPImportOrdHTBL.getAascERPImportOrdHREC().length;
                     AascERPImportOrdHREC[] aasc_import_headers = AascERPImportOrdHTBL.getAascERPImportOrdHREC();
             STRUCT[] structArray = new STRUCT[length];
             for(int i = 0; i<length;i++){
                             Object[] import_headers_array1 = new Object[20];
                 import_headers_array1[0]=aasc_import_headers[i].getOrderNumber();
                 import_headers_array1[1]=aasc_import_headers[i].getCustomerName();
                 import_headers_array1[2]=aasc_import_headers[i].getContactName();
                 import_headers_array1[3]=aasc_import_headers[i].getAddress1();
                 import_headers_array1[4]=aasc_import_headers[i].getAddress2();
                 import_headers_array1[5]=aasc_import_headers[i].getAddress3();
                 import_headers_array1[6]=aasc_import_headers[i].getCity();
                 import_headers_array1[7]=aasc_import_headers[i].getState();
                 import_headers_array1[8]=aasc_import_headers[i].getPostalCode();
                 import_headers_array1[9]=aasc_import_headers[i].getCountryCode();
                 import_headers_array1[10]=aasc_import_headers[i].getPhoneNumber();
                 import_headers_array1[11]=aasc_import_headers[i].getShipFromLocation();
                 import_headers_array1[12]=aasc_import_headers[i].getCompanyName();
                 import_headers_array1[13]=aasc_import_headers[i].getShipMethodMeaning();
                 import_headers_array1[14]=aasc_import_headers[i].getCarrierPayCode();
                 import_headers_array1[15]=aasc_import_headers[i].getCarrierAccountNumber();
                 import_headers_array1[16]=aasc_import_headers[i].getRefernce1();
                 import_headers_array1[17]=aasc_import_headers[i].getRefernce2();
                 
                 import_headers_array1[18]=aasc_import_headers[i].getShipToLocation();
                 import_headers_array1[19]=aasc_import_headers[i].getEmail();
                 //uncomment below two lines for tomcat
                 StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("AASC_ERP_IMPORT_ORDH_REC",oracleConnection);
                 STRUCT itemObject1 = new STRUCT(itemDescriptor,oracleConnection,import_headers_array1);
                 
                 //uncomment below two lines for jdev
//                 StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("AASC_ERP_IMPORT_ORDH_REC",connection);
//                 STRUCT itemObject1 = new STRUCT(itemDescriptor,connection,import_headers_array1);
                 
                                 structArray[i] = itemObject1;
             }             
            
             //uncomment below two lines for tomcat
             ArrayDescriptor descriptor =ArrayDescriptor.createDescriptor( "AASC_ERP_IMPORT_ORDH_TBL", oracleConnection );
              ARRAY array_to_pass =new ARRAY( descriptor, oracleConnection, structArray );
              
             //uncomment below two lines for jdev
//             ArrayDescriptor descriptor =ArrayDescriptor.createDescriptor( "AASC_ERP_IMPORT_ORDH_TBL", connection );
//             ARRAY array_to_pass =new ARRAY( descriptor, connection, structArray );
                    
             if (connection != null) {
                
                 headerCallableStatement = 
                         connection.prepareCall("{call " +  
                                                "AASC_ERP_IMPORT_ORDERS_PKG.LOAD_IMPORT_ORDH_DATA(?,?,?,?,?,?,?)}");
                 headerCallableStatement.setInt(1, userId);
                 headerCallableStatement.setInt(2, clientId);
                 headerCallableStatement.setInt(3, locationId);
                 headerCallableStatement.setArray(4, array_to_pass);
                 headerCallableStatement.registerOutParameter(5, 
                                                              Types.INTEGER); // registering the out parameter for opStatus
                 headerCallableStatement.registerOutParameter(6, 
                                                              Types.INTEGER); // registering the out parameter for opStatus
                 headerCallableStatement.registerOutParameter(7, 
                                                              Types.VARCHAR); // registering the out parameter for error status
                 headerCallableStatement.execute(); // calling the execute() method to execute the procedure
                 // orderNumber = headerCallableStatement.getInt(48);                         // out parameter returns order number
                  importId = 
                          headerCallableStatement.getInt(5);
                 returnStatus = 
                         headerCallableStatement.getInt(6); // out Parameter returns the opStatus value 
                 errorStatus = 
                         headerCallableStatement.getString(7); // out parameter returns the errorStatus 
                 //aascShipmentHeaderInfo.setOrderNumber(orderNumber); // setting the orderNumber to the aascShipmentHeaderInfo.setOrderNumber(orderNumber) method
             } //end of if block
             logger.info("Orders imported to database. Import id:"+importId);
             logger.info("AASC_ERP_IMPORT_ORDERS_PKG.LOAD_IMPORT_ORDH_DATA opStatus:" + 
                         returnStatus + "\t errorStatus:" + errorStatus);
             
//             if(returnStatus==100){
                    
                  LinkedList errorList=getImportErrorStatus(clientId,locationId,importId); 
                 resultHM.put("importId",importId);
                 resultHM.put("errorList",(Object)errorList);    
                 
//             }
//             else{
//                // int returnCode=getImportErrorStatus(clientId,locationId,importId);  
//                 resultHM.put("importId",0);
//                 resultHM.put("returnCode",0);
//             }
//             
             
             // connection.commit();//this method saves the data to the database
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



      
      /**
      * getImportErrorStatus() method of AascOracleSaveOrderImport class has three parameters clientId, locationId and importId
      * and returns a LinkedList object with errored out data.
      * This method takes import id as one of the input and returns the failed records for that import id. 
      * @param clientId int, int locationId,int importId
      * @return LinkedList
      */
      public LinkedList getImportErrorStatus(int clientId, int locationId,int importId
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
                                new MapSqlParameterSource().addValue("IP_IMPORT_ID",importId);
              simpleJdbcCall = 
                      new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_IMPORT_ORDERS_PKG")
                      .withProcedureName("IMPORT_ORDH_REPORT").declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER)).
                      declareParameters(new SqlOutParameter("OP_ERROR_MESSAGE",Types.VARCHAR)).
                      declareParameters(new SqlOutParameter("OP_TOTAL_REC_COUNT",Types.VARCHAR)).
                      declareParameters(new SqlOutParameter("OP_VALID_REC_COUNT",Types.VARCHAR)).
                      declareParameters(new SqlOutParameter("OP_ERROR_REC_COUNT",Types.VARCHAR)).
                      declareParameters(new SqlOutParameter("get_order_header_details",OracleTypes.CURSOR));
                      
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
                  logger.info("Error"+(String)map1.get("ORDER_NUMBER"));
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
