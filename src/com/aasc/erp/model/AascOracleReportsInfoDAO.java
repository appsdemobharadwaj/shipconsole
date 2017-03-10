package com.aasc.erp.model;

import com.aasc.erp.bean.AascTrackingDetailsInfo;
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

import org.apache.commons.dbcp.DelegatingConnection;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


/**
 * AascOracleReportsInfoDAO is a DAO class for Reports functionalities.
 * @version   1.1
 * @author    Eshwari M
 * History
 *
 * 19/12/2014   Y Pradeep   Modified nullStrToSpc method
 * 31/12/2014   Eshwari M   Moved getTrackingDetails, saveTrackingDetails methods from this class to AascOracleTrackingOrderInfoDAO
 * 07/01/2015   Y Pradeep   Added History section, java doc's and code alignment.
 * 30/01/2015   Eshwari M   Moved back getTrackingDetails, saveTrackingDetails methods from AascOracleTrackingOrderInfoDAO to this class 
 *                          as these methods are related to Reports
 * */

public class AascOracleReportsInfoDAO implements AascReportsInfoDAO {
    private int status; // returns success status from data base 

    private AascOracleDAOFactory aascOracleDAOFactory; // holds the object of AascOracleDAOFactory class

    private static Logger logger = 
        AascLogger.getLogger("AascOracleReportsInfoDAO");
    AascOracleDAOFactory connectionFactory = 
        new AascOracleDAOFactory(); 
        
    SimpleJdbcCall simpleJdbcCall;
    /** Default constructor. **/
    public AascOracleReportsInfoDAO() {
        aascOracleDAOFactory = 
                new AascOracleDAOFactory(); // creating a object of AascOracleDAOFactory class
    }
    
     /**
       Method getTrackingDetails() gets list of Tracking numbers.
       @param locationId  Id of the location seleced in the Reports page
       @param clientId    Id of the client for whom the report has to be generated
       @return trackingList object of type LinkedList containing the tracking information of the shipped orders .
      */
     public LinkedList getTrackingDetails(int locationId, int clientId) {
        logger.info("Entered getTrackingDetails()");
        LinkedList trackingList=null;
        String unitvalue=null;
        try{
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_location_id",locationId)
                                                                        .addValue("ip_client_id",clientId);
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_REPORTS_PKG")
                                                           .withProcedureName("get_tracking_details")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("AASC_GET_TRACKING_DETAILS", OracleTypes.CURSOR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
         
            status = Integer.parseInt(String.valueOf(out.get("op_status")));
            //String error_status = String.valueOf(out.get("op_error_status"));
                 
            //logger.info("out : "+out);
            Iterator it = ((ArrayList)out.get("OP_TRACKING_DETAILS")).iterator();
               
            trackingList=new LinkedList();
            HashMap<String, ?> map1 = null ;
            AascTrackingDetailsInfo aascTrackingDetailsInfo = null ;
            while (it.hasNext()) {
                map1 = (HashMap<String, ?>)it.next();

                //logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::" + map1.toString());
                aascTrackingDetailsInfo = new AascTrackingDetailsInfo();

                unitvalue =  (String)map1.get("TRACKING_NUMBER") ;                                         
                
                aascTrackingDetailsInfo.setTrackingNumber((String)map1.get("TRACKING_NUMBER"));   
                aascTrackingDetailsInfo.setShipmentId(((BigDecimal)map1.get("SHIPMENT_ID")).intValue()); 
                aascTrackingDetailsInfo.setPackageId(((BigDecimal)map1.get("PKG_NUMBER")).intValue());
                aascTrackingDetailsInfo.setCarrierCode(((BigDecimal)map1.get("CARRIER_CODE")).intValue());
                aascTrackingDetailsInfo.setCarrierMode((String)map1.get("CMODE"));
                aascTrackingDetailsInfo.setServerIpAddress((String)map1.get("SERVER_IP_ADDRESS"));
                aascTrackingDetailsInfo.setPrefix((String)map1.get("PREFIX"));
                aascTrackingDetailsInfo.setPort(((BigDecimal)map1.get("PORT")).intValue());
                aascTrackingDetailsInfo.setUserName((String)map1.get("CARRIER_USER_NAME"));
                aascTrackingDetailsInfo.setPassword((String)map1.get("CARRIER_PASSWORD"));
                aascTrackingDetailsInfo.setAccountNumber((String)map1.get("ACCOUNT_NUMBER"));
                aascTrackingDetailsInfo.setMeterNumber((String)map1.get("METER_NUMBER"));
                aascTrackingDetailsInfo.setAccessLicenseNumber((String)map1.get("ACCESS_LICENSE_NUMBER"));
                
                trackingList.add(aascTrackingDetailsInfo);
            }
        } catch (Exception e) {
            logger.severe("Error " + e.getMessage());
        }
                       
        return trackingList;

     } // end of getTrackingDetails()


      /**
        Method saveTrackingDetails() gets list of Tracking numbers.
        @param trackingDetailsList  LinkedList containing the tracking information that has to be saved to database 
        @return integer, which is a status of the saving to database.
       */
        public int saveTrackingDetails(LinkedList trackingDetailsList){
          logger.info("entered saveTrackingDetails");
          int opStatus=0;
          Connection connection = null ;
          CallableStatement callableStatement = null ;
          try
          {
             // For jdev
             //connection = aascOracleDAOFactory.createDataSource().getConnection();
            
             //For Tomcat
             OracleConnection oracleConnection = (OracleConnection)((DelegatingConnection)connection).getInnermostDelegate();
            
             int size=trackingDetailsList.size();
             STRUCT[] structArray = new STRUCT[size];
             Iterator iterator=trackingDetailsList.listIterator();
             int count=0;
             try{
               AascTrackingDetailsInfo trackingDetailsInfo = null ;
               while(iterator.hasNext()) {
                 Object[] aascTrackingDetailsRec=new Object[5];
                 trackingDetailsInfo = (AascTrackingDetailsInfo)iterator.next();

                 aascTrackingDetailsRec[0]=trackingDetailsInfo.getTrackingNumber();
                 aascTrackingDetailsRec[1]=trackingDetailsInfo.getShipmentId();
                 aascTrackingDetailsRec[2]=trackingDetailsInfo.getPackageId();
                 aascTrackingDetailsRec[3]=trackingDetailsInfo.getDeliveryStatus();
                 aascTrackingDetailsRec[4]=trackingDetailsInfo.getDeliveredDate();
                 // For jdev
                 //StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("AASC_ERP_TRACKING_DETAILS_REC",connection);
                 //STRUCT itemObject = new STRUCT(itemDescriptor,connection,aascTrackingDetailsRec);
                 //For Tomcat
                 StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("AASC_ERP_TRACKING_DETAILS_REC",oracleConnection);
                 STRUCT itemObject = new STRUCT(itemDescriptor,oracleConnection,aascTrackingDetailsRec);
                  
                 structArray[count] = itemObject;
                 count++;
               }
             }
             catch(Exception e){
                 e.printStackTrace();
             }
             
             if (connection != null) {
               try {
                  
                  callableStatement = connection.prepareCall("{call AASC_ERP_REPORTS_PKG.save_tracking_details(?,?,?)}");
                       
                  //For Jdeveloper
                  //ArrayDescriptor trackingDetailsDes = ArrayDescriptor.createDescriptor("AASC_ERP_TRACKING_DETAILS_TBL", connection);
                  //ARRAY trackingDetailsArray = new ARRAY(trackingDetailsDes,connection,structArray);
                     
                  //For Tomcat
                  ArrayDescriptor trackingDetailsDes = ArrayDescriptor.createDescriptor("AASC_ERP_TRACKING_DETAILS_TBL", oracleConnection);
                  ARRAY trackingDetailsArray = new ARRAY(trackingDetailsDes,oracleConnection,structArray); 
                  
                  callableStatement.setObject(1,(Object)trackingDetailsArray);
                  callableStatement.registerOutParameter(2, Types.VARCHAR);
                  callableStatement.registerOutParameter(3, Types.INTEGER);
                  
                  callableStatement.execute();
                  
                  String errorStatus = callableStatement.getString(2);
                  opStatus = callableStatement.getInt(3);
                  logger.info("calling AASC_ERP_REPORTS_PKG.save_tracking_details, opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
                
               }catch (Exception e) { 
                 logger.severe(" Exception in saving tracking details::"+e.getMessage());   
                 
               }
               finally {
                if (callableStatement != null) {
                  try {
                      callableStatement.close(); // closing the ref cursor
                      callableStatement = null;
                  } catch (Exception e) {
                      logger.severe("Error in closing callableStatement"+e.getMessage());   
                  }
                }
                
                if (connection != null) {
                  try {
                      connection.close();
                      connection = null;
                  } catch (Exception e) {
                      logger.severe("Exception::"+e.getMessage());
                  }
                }
               } 
             }
          }catch(Exception e)
          {
             logger.info("Exception : "+e.getMessage());
          }
         return opStatus;   
        }             

    /**
     * This method can replace the null values with empty String
     * @return String that is ""
     * @param obj of type Object
     */
     String nullStrToSpc(Object obj) {
         String spcStr = "";
         if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
             return spcStr;
         } else {
             return obj.toString();
         }
     }

} // end of AascOracleReportsInfoDAO class
