package com.aasc.erp.model;

import com.aasc.erp.util.AascLogger;

import com.aasc.erp.model.AascAccountNumbersDAO;
import com.aasc.erp.model.AascOracleAccountNumbersInfoDAO;

import com.aasc.erp.model.AascOracleShipMethodAjaxDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;


/*========================================================================+
@(#)AascOracleDAOFactory.java 23/07/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/


 /**
  * AascOracleDAOFactory is a class which creates the data base datasource and returns the datasource
  * @author      N Srisha
  * @version 1.0
  * @since
  * 
  * @History
  * 
  * 17/12/2014    Eshwari M   Renamed devliery to order for SC Lite.
  * 07/01/2015    Y Pradeep   Modified code to get connection from server datasource and removed createConnection() method.
  * 07/01/2015    Y Pradeep   Added comments in createDataSource() method for comment and uncommeting datasource.
  * 28/01/2015    Y Pradeep   Added code required for International Shipping.
  * 06/02/2015    Sunanda K   Added getAascVoidShipmentDAO() method.    
  * 23/06/2015    Y Pradeep   Added code related to AascPrinterSetupDAO call for Printer Setup in Options page.
  * 02/03/2016    Suman G     Added code for new object.
  **/

public class AascOracleDAOFactory extends AascDAOFactory  {

    private static Logger logger = 
        AascLogger.getLogger("AascOracleDAOFactory"); // this is a static method of the AaascLogger class which returns the object of the Logger class

    static AascConnectionPool aascConnectionPool = null;

    public AascPackageDimensionDAO getPackageDimensionDAO() {
        return new AascOraclePackageDimensionInfoDAO();
    }


    public AascGetLocDAO getLocationDAO() {
        return new AascOracleGetLocDAO();

    }
        
    public AascShipmentOrderInfoDAO getAascShipmentOrderInfoDAO() // Praveen 11Feb06
    {

        return new AascOracleShipmentOrderInfoDAO();

    }
        
    public AascAccountNumbersDAO getAccountNumbersDAO() {

        // AascOracleAccountNumbersDAO implements AascAccountNumbersInfoDAO

        return new AascOracleAccountNumbersInfoDAO();
    }
    
    public DataSource createDataSource() {

        BasicDataSource datasource =null; // Data base connection object which connects to data base
        ResourceBundle resources;
        DataSource resource = null;
        
    //    try {    

            //InitialContext initContext = new InitialContext();   
            
          // Context envContext = (Context) initContext.lookup("java:/comp/env");               // UnComment while porting a war file in server and comment while working in jdeveloper.
          // datasource = (DataSource) envContext.lookup("jdbc/ShipConsoleLyteCoreDS");         // UnComment while porting a war file in server and comment while working in jdeveloper.
//       datasource =(DataSource)initContext.lookup("jdbc/SCDEVR12_ERPCoreDS");                  // UnComment while working in jdeveloper and comment while porting war file in server.
           
           // initContext.close();    
		   
		   
		try {
			 resources = ResourceBundle.getBundle("config");
			
			datasource = new BasicDataSource();
			datasource.setDriverClassName(resources.getString("DriverClassName"));
			datasource.setUrl(resources.getString("Url"));
			datasource.setUsername(resources.getString("Username"));
			datasource.setPassword(resources.getString("Password"));
			datasource.setInitialSize(Integer.parseInt(resources.getString("InitialSize")));
			datasource.setMaxActive(Integer.parseInt(resources.getString("MaxActive")));
			datasource.setMinIdle(Integer.parseInt(resources.getString("MinIdle")));
			datasource.setMaxWait(Integer.parseInt(resources.getString("MaxWait")));
			datasource.setAccessToUnderlyingConnectionAllowed(Boolean.parseBoolean(resources.getString("AccessToUnderlyingConnectionAllowed")));
			resource = (DataSource)datasource;
			logger.info("datasource"+datasource.getDriverClassName()+" "+resource.equals(datasource));
		} catch (NumberFormatException e) {
			logger.severe("Exception::"+e.getMessage());
		}

       // }  
		catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());

        }
	  
        return resource;

    }

        /**

         * Method to close or free the database connection.

         * Removes the connection from the ConnectionPool.
         * @param key  

         * @reutrn String

         */
         public String getMessage(String key) {
                 ResourceBundle resources;
                 String message = "";
                 try {
                     resources = 
                             ResourceBundle.getBundle("ApplicationResources");
                     logger.info("After the resource bundle");
                     message = resources.getString(key);
                     System.out.println("after getting message");
                     logger.info("messages from prop files :: " + message);
                 } catch (MissingResourceException mre) {
                     logger.info("ApplicationResources.properties not found");
                 }
                 logger.info("inside the DBAccess ----------->"+message);
                 return message;
             }

        public boolean closeConnection(Connection connection) {

            try {

                connection.close();

                // aascConnectionPool.free(connection);

                return true;

            } catch (Exception exception) {

                logger.severe("Exception::"+exception.getMessage());
                return false;

            }

        }
        
    public AascReportsInfoDAO getAascReportsInfoDAO() {

        return new AascOracleReportsInfoDAO();

    }
    
    public AascProfileOptionsDAO getAascProfileOptionsDAO() {
        return new AascOracleProfileOptionsDAO();

    }
    
    public AascShipMethodDAO getAascShipMethodDAO() {
        return new AascOracleShipMethodDAO();

    }
    
    public AascTrackingOrderInfoDAO getAascTrackingOrderInfoDAO(){
        return new AascOracleTrackingOrderInfoDAO() ;
    }
    
    public AascSetupLocationDAO getAascSetupLocationDAO(){
        return new AascOracleSetupLocationDAO() ;
    }
    
  
    public AascPrinterSetupDAO getAascPrinterSetupDAO() {

        return new AascOraclePrinterSetupDAO();

    }
    
    public AascShipSaveDAO getAascShipSaveDAO()
    {
         return new AascOracleShipSaveDAO();
    }
    
    public AascIntlShipmentsDAO getAascIntlShipmentsDAO(){
         return new AascOracleIntlShipmentsDAO() ;
    }   
    public AascShipMethodAjaxDAO getAascShipMethodAjaxDAO() {
        return new AascOracleShipMethodAjaxDAO();

    }
    /* returns the AascOracleVoidShipmentDAO object*/


    public AascVoidShipmentDAO getAascVoidShipmentDAO() {

        return new AascOracleVoidShipmentDAO();

    }
    
    public AascSubscriptionDetailsDAO getAascSubscriptionDetailsDAO() {

        return new AascOracleSubscriptionDetailsDAO();

    }
    
}
