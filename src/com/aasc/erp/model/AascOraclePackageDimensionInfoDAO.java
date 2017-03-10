/*
  * @(#)AascOraclePackageDimensionInfoDAO.java
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 * ====================================================================
 *
 * This class is used to retrive the package dimensions Information
 @History
 	18/02/2009    Madhavi  Added   code to close connections
        10/03/2009    Pavan J    Added code for HazMat Material enhancements for FedEx Ground
        11/03/09      Madhavi    Modified logging code
        27/02/2013    Sravanthi  Added code for changing UOM LOV values in Package details
        27/11/2014    Sunanda.k  added Spring JDBC logic for the following code  
        19/12/2014    Y Pradeep  Modified nullStrToSpc method
        31/12/2014    Eshwari M  Modified type variable name to roleId to make it relevant
        20/01/2015    K Sunanda  Removed unused variables and unnecessary loggers and unused imports, unused varibles
 *
 */
package com.aasc.erp.model;

import com.aasc.erp.bean.AascPackageDimensionInfo;
import com.aasc.erp.util.AascLogger;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Logger;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


public class AascOraclePackageDimensionInfoDAO implements AascPackageDimensionDAO {

    AascOracleDAOFactory connectionFactory = 
        new AascOracleDAOFactory(); // creating the object for AascPackageInfo
    ResultSet OrgResultSet = null;
    ArrayList dimensionArrayList = null;
    //CallableStatement reportRespCallStmt=null;
    AascPackageDimensionInfo aascPackageDimensionInfo = null;
    LinkedList lineLinkedList = null;
    boolean checkConnection = true;
    int dimensionId = 0;
    String dimensionName = "";
    int dimensionLength = 0;
    int dimensionWidth = 0;
    int dimensionHeight = 0;
    String dimensionUnits = "";
    String dimensionActive = "";
    String dimensionDefault = "";
    int status = 0;

    private static Logger logger = 
        AascLogger.getLogger("AascOracleDeliveryInfoDAO");

    SimpleJdbcCall simpleJdbcCall;
    
    public AascOraclePackageDimensionInfoDAO ()
    {}

    /**
     * This method can replace the null values with nullString
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

    public AascPackageDimensionInfo getPackageDimensionInfo(int locationId, 
                                                            int roleId, 
                                                            int clientId) {
        logger.info("Entered getPackageDimensionInfo()");
        LinkedList PackageList = null;
        AascPackageDimensionInfo aascPackageDimensionInfo = null;
        aascPackageDimensionInfo = new AascPackageDimensionInfo();
        //String harzardousMatIdValue = null;
        // setQueryTimeout(100);
        try {
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_location_id",locationId).addValue("ip_action_type",roleId).addValue("ip_client_id",clientId);
            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_dimension_pkg").withProcedureName("get_dimension_details")
                                                  .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                  .declareParameters(new SqlOutParameter("aasc_dimension_details", OracleTypes.CURSOR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);


            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_status")));
            String error_status = String.valueOf(out.get("op_error_status"));
            logger.info("op_error_status in the getdimensioninfo" + 
                        error_status);
            dimensionArrayList = ((ArrayList)out.get("OP_DIMENSION_DETAILS"));
            //               Iterator it = ((ArrayList)out.get("OP_DIMENSION_DETAILS")).iterator();
            //               logger.info("list::::::::::::::::"+out.get("OP_DIMENSION_DETAILS"));
            //                               HashMap<String, ?> map1 = new HashMap<String, ?> ();
            //                               PackageList = new LinkedList();
            //                               while (it.hasNext()) {
            //                                    map1 = (HashMap<String, ?>)it.next();
            //
            //                                   logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::" +
            //                                                      map1.toString());
            //                                   DimValue =  ((BigDecimal)map1.get("dimension_id") ).intValue();
            //
            //                                       PackageList.add(DimValue);
            //                                   }


            try {
                PackageList = new LinkedList();
                aascPackageDimensionInfo = new AascPackageDimensionInfo();
                PackageList = 
                        getDimensionBean(); // calling getDimensionBean() to get Dimension information

                aascPackageDimensionInfo.setPackageDimensionList(PackageList);
                
                AascPackageDimensionInfo dimInfo = getUnitsInfo();
                aascPackageDimensionInfo.setUnitDetails(dimInfo.getUnitDetails());
                aascPackageDimensionInfo.setErrorStatus(opStatus);

            } catch (Exception e) {
                logger.severe("Got Exception in getDimensionBean();" + 
                              e.getMessage());
                e.printStackTrace();
            }


        } catch (Exception e) {
            logger.severe("Error in closing connection" + e);
            e.printStackTrace();
        }

        return aascPackageDimensionInfo;


    } // end of getPackageDimensionInfo()

    private LinkedList getDimensionBean() {
        logger.info("Entered getDimensionBean()");
        lineLinkedList = new LinkedList();
        try {
            Iterator it = dimensionArrayList.iterator();
            
            HashMap<String, ?> map1 = new HashMap<String, String>();

            while (it.hasNext()) {
                map1 = (HashMap<String, ?>)it.next();


                aascPackageDimensionInfo = new AascPackageDimensionInfo();

                int dimensionId = 
                    ((BigDecimal)map1.get("dimension_id")).intValue();
                aascPackageDimensionInfo.setDimensionId(dimensionId);

                String dimensionName = 
                    nullStrToSpc(map1.get("dimension_name"));
                aascPackageDimensionInfo.setDimensionName(dimensionName);

                int dimensionLength = 
                    ((BigDecimal)map1.get("dimension_length")).intValue();
                aascPackageDimensionInfo.setDimensionLength(dimensionLength);

                int dimensionWidth = 
                    ((BigDecimal)map1.get("dimension_width")).intValue();
                aascPackageDimensionInfo.setDimensionWidth(dimensionWidth);

                int dimensionHeight = 
                    ((BigDecimal)map1.get("dimension_height")).intValue();
                aascPackageDimensionInfo.setDimensionHeight(dimensionHeight);
             

                dimensionUnits = nullStrToSpc(map1.get("dimension_units"));
                aascPackageDimensionInfo.setDimensionUnits(dimensionUnits);

                dimensionActive = nullStrToSpc(map1.get("dim_active"));
                aascPackageDimensionInfo.setDimensionActive(dimensionActive);

                dimensionDefault = nullStrToSpc(map1.get("dim_default"));

                aascPackageDimensionInfo.setDimensionDefault(dimensionDefault);

                lineLinkedList.add(aascPackageDimensionInfo);

            } //end of while

        } catch (Exception e) {
            String errorMsg = e.getMessage().substring(3, 9);
            if (errorMsg.equalsIgnoreCase("-01013")) {
                status = -1013;
            } else {
                logger.severe("Got exception when errorMsg is not -01013" + 
                              e.getMessage());

            }
        }
        logger.info("Exited getDimensionBean()");
        return lineLinkedList;
    } // end for getDimensionBean


    public int saveAllPackageDimensionInfo(int userId, 
                                           AascPackageDimensionInfo aascPackageDimensionInfo1, 
                                           int queryTimeOut, int clientId) {   // Jagadish added clinetId parameter
        logger.info("Entered saveAllPackageDimensionInfo");

        int returnStatus = 0;
        returnStatus = aascPackageDimensionInfo1.getErrorStatus();
        LinkedList dimensionList1 = 
            aascPackageDimensionInfo1.getPackageDimensionList();
        ListIterator dimensionIterator1 = dimensionList1.listIterator();
        AascPackageDimensionInfo aascPackageDimensionInfo2 = null ;
        while (dimensionIterator1.hasNext()) {
            aascPackageDimensionInfo2 = 
                (AascPackageDimensionInfo)dimensionIterator1.next();
            dimensionId = aascPackageDimensionInfo2.getDimensionId();
            dimensionActive = aascPackageDimensionInfo2.getDimensionActive();
            dimensionDefault = aascPackageDimensionInfo2.getDimensionDefault();
            
            returnStatus = 
                    saveEachPackageDimensionInfo(userId, aascPackageDimensionInfo2, clientId); // Jagadish added clientId param

            logger.info("returnStatus from saveEachPackageDimensionInfo():" + 
                        returnStatus);

            if (returnStatus != 500) {

                return returnStatus;
            }

        }
        logger.info("Exited saveAllPackageDimensionInfo");
        return returnStatus;

    }

    public int saveEachPackageDimensionInfo(int userId, 
                                            AascPackageDimensionInfo aascPackageDimensionInfo2,
                                            int clientId) {   //Jagadish added clientId parameter
        int returnStatus = 0;

        logger.info("Process is in saveEachPackageDimensionInfo method");
        try {
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_location_id",  aascPackageDimensionInfo2.getLocationId())
                                           .addValue("ip_dimension_id", aascPackageDimensionInfo2.getDimensionId())
                                           .addValue("ip_assign", aascPackageDimensionInfo2.getDimensionActive())
                                           .addValue("ip_default", aascPackageDimensionInfo2.getDimensionDefault())
                                           .addValue("ip_user_id", userId).addValue("ip_client_id", clientId);


            simpleJdbcCall =
                    new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_dimension_pkg").withProcedureName("dimension_loc_assign").declareParameters(new SqlOutParameter("op_status", 
                                                                                                                                                                             Types.INTEGER)).declareParameters(new SqlOutParameter("op_error_status", 
                                                                                                                                                                                                                                   Types.VARCHAR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);


           
                Integer.parseInt(String.valueOf(out.get("op_status")));
            returnStatus = 
                    Integer.parseInt(String.valueOf(out.get("op_status")));
            logger.info("returnstatus in save eachpackage dimension" + 
                        returnStatus);
            String.valueOf(out.get("op_error_status"));
            // aascPackageDimensionInfo.setErrorStatus(opStatus);

        } catch (Exception e) {
            logger.severe("Got Exception in saveEachPackageDimensionInfo()" + 
                          e.getMessage());
            e.printStackTrace();
        }
        logger.info("Exited saveEachPackageDimensionInfo method");
        return returnStatus;
    }


    //Method to save the package dimension Information

    public AascPackageDimensionInfo getSavePackageDimensionInfo(int userId, 
                                                                int locationInt, 
                                                                AascPackageDimensionInfo aascPackageDimensionInfoDetail, 
                                                                int queryTimeOut,int clientId) {  
        logger.info("Process is in getSavePackageDimensionInfo method");

        try {
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_location_id", locationInt)
                                           .addValue("ip_dimension_name", aascPackageDimensionInfoDetail.getDimensionName())
                                           .addValue("ip_dimension_length", aascPackageDimensionInfoDetail.getDimensionLength())
                                           .addValue("ip_dimension_width", aascPackageDimensionInfoDetail.getDimensionWidth())
                                           .addValue("ip_dimension_height", aascPackageDimensionInfoDetail.getDimensionHeight())
                                           .addValue("ip_dimension_units", aascPackageDimensionInfoDetail.getDimensionUnits())
                                           .addValue("ip_action_type", aascPackageDimensionInfoDetail.getActionType())
                                           .addValue("ip_user_id", userId).addValue("ip_client_id",  clientId);


            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_dimension_pkg").withProcedureName("create_dimension")
                                                  .declareParameters(new SqlOutParameter("op_status",  Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_status",  Types.VARCHAR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);

            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_status")));
            String errorStatus = String.valueOf(out.get("op_error_status"));
            aascPackageDimensionInfoDetail.setErrorStatus(opStatus);
            aascPackageDimensionInfoDetail.setErrorMessage(errorStatus);

        } catch (Exception e) {

            logger.severe("Error in closing connection" + e);
        }
        return aascPackageDimensionInfoDetail;
    } // end of getSavePackageDimensionInfo()


    public AascPackageDimensionInfo getUpdatePackageDimensionInfo(int userId, 
                                                                  int locationInt, 
                                                                  AascPackageDimensionInfo aascPackageDimensionInfoEdit, 
                                                                  int queryTimeOut, 
                                                                  int clientId) {
        logger.info("Process is in getUpdatePackageDimensionInfo method");
        try {
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            logger.info("aascPackageDimensionInfoEdit.getActionType() : "+aascPackageDimensionInfoEdit.getActionType());    
            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_location_id", 
                                                     locationInt).addValue("ip_dimension_name", aascPackageDimensionInfoEdit.getDimensionName())
                                                                 .addValue("ip_dimension_length", aascPackageDimensionInfoEdit.getDimensionLength())
                                                                 .addValue("ip_dimension_width",  aascPackageDimensionInfoEdit.getDimensionWidth())
                                                                 .addValue("ip_dimension_height", aascPackageDimensionInfoEdit.getDimensionHeight())
                                                                 .addValue("ip_dimension_units", aascPackageDimensionInfoEdit.getDimensionUnits())
                                                                 .addValue("ip_action_type",  aascPackageDimensionInfoEdit.getActionType())
                                                                 .addValue("ip_user_id", userId)
                                                                 .addValue("ip_client_id", clientId);


            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_dimension_pkg").withProcedureName("create_dimension")
                                                  .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);

            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_status")));
            String errorStatus = String.valueOf(out.get("op_error_status"));
            aascPackageDimensionInfoEdit.setErrorStatus(opStatus);
            aascPackageDimensionInfoEdit.setErrorMessage(errorStatus);
         
        } catch (Exception e) {
            logger.severe("Error in closing connection" + e.getMessage());
            aascPackageDimensionInfoEdit.setErrorStatus(505);
        }
        return aascPackageDimensionInfoEdit;
    } // end of getUpdatePackageDimensionInfo()

    public AascPackageDimensionInfo getUnitsInfo() {
        logger.info("Inside the GetUnitsInfo method");
        LinkedList unitLinkedList = null;
        AascPackageDimensionInfo aascPackageDimensionInfo = null;
        aascPackageDimensionInfo = new AascPackageDimensionInfo();
        String unitValue = null;
        try {
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_carrier_code", 
                                                     0).addValue("ip_lookup_type", 
                                                                 "DIM_UNITS")
                                                        .addValue("IP_CARRIER_MODE",""); // vikas added carrierMOde as null because mode is not necessary here


            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIP_CONSOLE_PKG").withProcedureName("get_lookup_value_details")
                                                  .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                  .declareParameters(new SqlOutParameter("aasc_get_lookup_details", OracleTypes.CURSOR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_status")));
            aascPackageDimensionInfo.setErrorStatus(opStatus);

            Iterator it = ((ArrayList)out.get("OP_LOOKUP_DETAILS")).iterator();

            unitLinkedList = new LinkedList();
            while (it.hasNext()) {
                HashMap<String, ?> map1 = (HashMap<String, ?>)it.next();
   
                unitValue = (String)map1.get("lookup_value");
                unitLinkedList.add(unitValue);
            }
            aascPackageDimensionInfo.setErrorStatus(status);
            aascPackageDimensionInfo.setUnitDetails(unitLinkedList);
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }
        return aascPackageDimensionInfo;
    }


    public AascPackageDimensionInfo getPackageUOMInfo() {
     logger.info("Inside the getPackageUOMInfo method");
     LinkedList packageUOMList = null;
     AascPackageDimensionInfo aascPackageDimensionInfo = null;
     
     aascPackageDimensionInfo = new AascPackageDimensionInfo();
     String uomValue = null;
     try {
         DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_carrier_code", 
                                                     0).addValue("ip_lookup_type", 
                                                                 "PKGUOM");


            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIP_CONSOLE_PKG").withProcedureName("get_lookup_value_details")
                                                  .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
          
            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_status")));
            String errorStatus = String.valueOf(out.get("op_error_status"));
            aascPackageDimensionInfo.setErrorStatus(opStatus);
            aascPackageDimensionInfo.setErrorMessage(errorStatus);

            Iterator it = ((ArrayList)out.get("OP_LOOKUP_DETAILS")).iterator();
            packageUOMList = new LinkedList();
            while (it.hasNext()) {
                HashMap<String, ?> map1 = (HashMap<String, ?>)it.next();
                uomValue = (String)map1.get("lookup_value");
                packageUOMList.add(uomValue);
            }
            aascPackageDimensionInfo.setErrorStatus(status);
            aascPackageDimensionInfo.setPackageUOMDetails(packageUOMList);
        } catch (Exception e) {
            logger.severe("Error in closing connection" + e);
        }

        return aascPackageDimensionInfo;

    } // end of getPackageUOMInfo()


    public AascPackageDimensionInfo getHazardousUnitsInfo() {
        logger.info("Inside the getHazardousUnitsInfo method");
        LinkedList harzardousUnitLinkedList = null;
        AascPackageDimensionInfo aascPackageDimensionInfo = null;
        aascPackageDimensionInfo = new AascPackageDimensionInfo();
        String harzardousUnitValue = null;
        
        try {
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = 
                // setting as hazmat options are present only for FedEx
                new MapSqlParameterSource().addValue("ip_carrier_code", 
                                                     111).addValue("ip_lookup_type", 
                                                                   "HAZ_UNITS")
                                                    .addValue("IP_CARRIER_MODE","");   // vikas added carrierMOde as null because mode is not necessary here
            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIP_CONSOLE_PKG").withProcedureName("get_lookup_value_details")
                                                  .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                  .declareParameters(new SqlOutParameter("aasc_get_lookup_details", OracleTypes.CURSOR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);



            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_status")));
            //String error_status = String.valueOf(out.get("op_error_status"));
            aascPackageDimensionInfo.setErrorStatus(opStatus);

            Iterator it = ((ArrayList)out.get("OP_LOOKUP_DETAILS")).iterator();
            harzardousUnitLinkedList = new LinkedList();

            while (it.hasNext()) {
                HashMap<String, ?> map1 = (HashMap<String, ?>)it.next();

             
                harzardousUnitValue = (String)map1.get("LOOKUP_VALUE");

                harzardousUnitLinkedList.add(harzardousUnitValue);
            }
            aascPackageDimensionInfo.setErrorStatus(status);
            aascPackageDimensionInfo.setHazardousUnitDetails(harzardousUnitLinkedList);
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }


        return aascPackageDimensionInfo;


    } // end of getHazardousUnitsInfo()


    public AascPackageDimensionInfo getHazardousMaterialIdInfo(int clientId){
        logger.info("Inside the getHazardousMaterialIdInfo method");
        LinkedList harzardousMatIdLinkedList = null;
        AascPackageDimensionInfo aascPackageDimensionInfo = null;
        //AascPackageDimensionInfo aascPackageDimensionInfoBean = null;
        // AascHeaderInfo aascHeaderInfo = null;
        aascPackageDimensionInfo = new AascPackageDimensionInfo();
        String harzardousMatIdValue = null;
        
        try {
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_location_id", 1)
                                            .addValue("ip_client_id", clientId)
                                            ;


            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIP_CONSOLE_PKG").withProcedureName("get_hazmat_material_id")
                                                  .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                  .declareParameters(new SqlOutParameter("aasc_hazmat_material_ids", OracleTypes.CURSOR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);


            
            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_status")));
            aascPackageDimensionInfo.setErrorStatus(opStatus);

            Iterator it = 
                ((ArrayList)out.get("OP_HAZMAT_MATERIAL_IDS")).iterator();
            harzardousMatIdLinkedList = new LinkedList();
            HashMap<String, ?> map1 = null ;
            while (it.hasNext()) {
                map1 = (HashMap<String, ?>)it.next();

               
                harzardousMatIdValue = (String)map1.get("hazmat_material_id");

                harzardousMatIdLinkedList.add(harzardousMatIdValue);
            }

            aascPackageDimensionInfo.setHazardousMatIdDetails(harzardousMatIdLinkedList);
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }

        return aascPackageDimensionInfo;


    } // end of getHazardousUnitsInfo()

     public AascPackageDimensionInfo getHazardousMaterialIdSEInfo(int carrierCode, int clientId){
         logger.info("Inside the getHazardousMaterialIdInfo method");
         LinkedList harzardousMatIdLinkedList = null;
         AascPackageDimensionInfo aascPackageDimensionInfo = null;
         //AascPackageDimensionInfo aascPackageDimensionInfoBean = null;
         // AascHeaderInfo aascHeaderInfo = null;
         aascPackageDimensionInfo = new AascPackageDimensionInfo();
         String harzardousMatIdValue = null;
         System.out.println("carrierCode:in dao:"+carrierCode);
         System.out.println("clientId::"+clientId);
         try {
             DataSource datasource = 
                 connectionFactory.createDataSource(); // this method returns the datasources object
             SqlParameterSource inputparams = 
                 new MapSqlParameterSource().addValue("ip_location_id", 1)
                                             .addValue("ip_client_id", clientId)
                                             .addValue("ip_carrier_code", carrierCode);


             simpleJdbcCall = 
                     new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIP_CONSOLE_PKG").withProcedureName("GET_HAZMAT_MATERIAL_ID_SE")
                                                   .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                   .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                   .declareParameters(new SqlOutParameter("aasc_hazmat_material_ids", OracleTypes.CURSOR));

             Map<String, Object> out = simpleJdbcCall.execute(inputparams);


             
             int opStatus = 
                 Integer.parseInt(String.valueOf(out.get("op_status")));
             aascPackageDimensionInfo.setErrorStatus(opStatus);

             Iterator it = 
                 ((ArrayList)out.get("OP_HAZMAT_MATERIAL_IDS")).iterator();
             harzardousMatIdLinkedList = new LinkedList();
             HashMap<String, ?> map1 = null ;
             while (it.hasNext()) {
                 map1 = (HashMap<String, ?>)it.next();

                
                 harzardousMatIdValue = (String)map1.get("hazmat_material_id");
     System.out.println("in dao call harzardousMatIdValue::"+harzardousMatIdValue);
                 harzardousMatIdLinkedList.add(harzardousMatIdValue);
             }

             aascPackageDimensionInfo.setHazardousMatIdDetails(harzardousMatIdLinkedList);
             System.out.println("harzardousMatIdLinkedList:in dao ::"+harzardousMatIdLinkedList);
         } catch (Exception e) {
             logger.severe("Exception::"+e.getMessage());
         }

         return aascPackageDimensionInfo;


     } // end of getHazardousUnitsInfo()


}//End of class
