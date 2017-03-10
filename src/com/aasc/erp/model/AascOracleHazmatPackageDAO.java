package com.aasc.erp.model;


import com.aasc.erp.bean.AascAddressInfo;
import com.aasc.erp.bean.AascHazmatPackageInfo;
import com.aasc.erp.bean.AascIntlCommodityInfo;
import com.aasc.erp.bean.AascIntlInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.util.AascLogger;

import com.aasc.erp.util.Formatter;

import java.math.BigDecimal;

import java.sql.Types;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

/*History
 * 30/10/2013 Tina, Mahesh & Sanjay	Added code for Multiple Hazmat Enhancement for Hologic.
 * 12/12/2013 Y Pradeep          Added file for Multiple Hazmat Enhancement from 7.1.7.6 release.
 **/
public class AascOracleHazmatPackageDAO implements AascHazmatPackageDAO{
    public AascOracleHazmatPackageDAO() {
    }
    
    private int opStatus; 
    private String errorStauts; 
    private String errorStatus;
    private String hazardousMaterialType;
    
    private String hazardousMaterialId;
    
   private String  hazMatOverPackFlag;
    
   private String  hazardousMaterialClass;
    
     private double hazardousMaterialCharges;
    
    private double hazardousMaterialQuantity;
    
    private String hazardousMaterialUnit;
    
    private String hazMatIdentificationNo;
    
   private String  hazMatDOTLabelType;
    
   private String  hazMatEmergencyContactNo;
    
   private String  hazMatEmergencyContactName;
    
   private String  hazardousMaterialPkgGroup;
    
   private double  hazMatPackagingCnt;
    
   private String  hazMatPackagingUnits;
    
   private String hazMatTechnicalName;
    
   private String hazMatSignatureName;
    
   private String hazMatPackInstructions;
   private String hazmatMaterialId = "";
   private String HazardousMaterialType ="";
   
   private String orderNumber;
    int pkgSequenceId = 0;
   
    
    SimpleJdbcCall simpleJdbcCall;
    
    Connection connection = null;
    AascOracleDAOFactory aascOracleDAOFactory = new AascOracleDAOFactory();
    
    HashMap<String, ?> map1;
    
    ResultSet HazmatResultSet = null;
    ResultSet HazmatPackageResultSet = null;
    
    ResultSet hazmatmResultSet;
    
    
    CallableStatement callableStatement=null;
    
    private static Logger logger = 
        AascLogger.getLogger("AascHazardousPackageDAO");
    Formatter formatter = new Formatter();    
    
    
    /**
     * This method can replace the null values with nullString
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
    
    public int saveHazmatPackageDetails(AascHazmatPackageInfo aascHazmatPackageInfo, int pkgSequenceId, String orderNumber) {
                                        
       
        int commNo = 1;
//        System.out.println("pkgSequenceId::"+pkgSequenceId);
//        System.out.println("orderNumber::"+orderNumber);
//            System.out.println("Entered saveHazmatPackageDetails method");
                try {
                    hazardousMaterialId = aascHazmatPackageInfo.getHazMatId();
//                    System.out.println("hazardousMaterialId::"+hazardousMaterialId);
//                    hazMatOverPackFlag = 
//                        aascHazmatPackageInfo.getHazMatOverPackFlag();
//                        System.out.println("hazMatOverPackFlag::"+hazMatOverPackFlag);
                hazardousMaterialClass = 
                        aascHazmatPackageInfo.getHazMatClass();
//                        System.out.println("hazardousMaterialClass::"+hazardousMaterialClass);
                hazardousMaterialCharges = 
                        aascHazmatPackageInfo.getHazMatCharges();
//                        System.out.println("hazardousMaterialCharges"+hazardousMaterialCharges);
                hazardousMaterialQuantity = 
                        aascHazmatPackageInfo.getHazMatQty();
//                        System.out.println("hazardousMaterialQuantity"+hazardousMaterialQuantity);
                    hazardousMaterialUnit = aascHazmatPackageInfo.getHazMatUnit();
//                    System.out.println("hazardousMaterialUnit"+hazardousMaterialUnit);
                hazMatIdentificationNo = aascHazmatPackageInfo.getHazMatIdNo();
//                System.out.println("hazMatIdentificationNo"+hazMatIdentificationNo);
                    hazMatDOTLabelType = aascHazmatPackageInfo.getHazMatDOTLabel();
//                    System.out.println("hazMatDOTLabelType"+hazMatDOTLabelType);
                hazMatEmergencyContactNo = 
                        aascHazmatPackageInfo.getHazMatEmerContactNo();
//                        System.out.println("hazMatEmergencyContactNo"+hazMatEmergencyContactNo);
                hazMatEmergencyContactName = 
                        aascHazmatPackageInfo.getHazMatEmerContactName();
//                        System.out.println("hazMatEmergencyContactName"+hazMatEmergencyContactName);
                hazardousMaterialPkgGroup = 
                        aascHazmatPackageInfo.getHazMatPkgGroup();
//                        System.out.println("hazardousMaterialPkgGroup"+hazardousMaterialPkgGroup);
                hazMatPackagingCnt = 
                        aascHazmatPackageInfo.getHazmatPkgingCnt();
//                        System.out.println("hazMatPackagingCnt "+hazMatPackagingCnt);
                hazMatPackagingUnits = 
                        aascHazmatPackageInfo.getHazmatPkgingUnits();
//                        System.out.println("hazMatPackagingUnits::"+hazMatPackagingUnits);
                    hazMatTechnicalName = 
                            aascHazmatPackageInfo.getHazmatTechnicalName();
//                            System.out.println("hazMatTechnicalName::"+hazMatTechnicalName);
                    hazMatSignatureName = 
                            aascHazmatPackageInfo.getHazmatSignatureName();
//                        System.out.println("hazMatSignatureName::"+hazMatSignatureName);
                hazMatPackInstructions = 
                        aascHazmatPackageInfo.getHazmatPackInstructions();
//                        System.out.println("hazMatPackInstructions::"+hazMatPackInstructions);
                    HazardousMaterialType  =    aascHazmatPackageInfo.getHazMatType(); 
//                    System.out.println("HazardousMaterialType::"+HazardousMaterialType);
                    hazmatMaterialId = aascHazmatPackageInfo.getHazMatId();
//                    System.out.println("hazmatMaterialId::"+hazmatMaterialId);
                     
                     DataSource datasource = aascOracleDAOFactory.createDataSource(); // this method returns the datasources object
                     
                     simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_HAZMAT_SHIPMENT_PKG")
                                                                    .withProcedureName("SAVE_HAZMAT_SHIPMENT_COMMODITY")
                                                                    .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                                    .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));  
                                                                    
                                                                    
                    SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_COMMODITY_NO", commNo)
                                                                                .addValue("IP_ORDER_NUMBER", orderNumber)
                                                                                .addValue("IP_PACKAGE_SEQUENCE_ID", pkgSequenceId)
                                                                                .addValue("IP_HAZMAT_MATERIAL_ID", hazardousMaterialId) 
                                                                                .addValue("IP_IDENTIFICATION_NUMBER", hazMatIdentificationNo)
                                                                                .addValue("IP_VOID_FLAG", "N")
                                                                                .addValue("IP_EMERGENCY_CONTACT_NUMBER", hazMatEmergencyContactNo)
                                                                                .addValue("IP_EMERGENCY_CONTACT_NAME", hazMatEmergencyContactName)
                                                                                .addValue("IP_PACKGING_GROUP", hazardousMaterialPkgGroup)
                                                                                .addValue("IP_WEIGHT", hazardousMaterialQuantity)
                                                                                .addValue("IP_UNITS", hazardousMaterialUnit)
                                                                                .addValue("IP_DOT_LABELS", hazMatDOTLabelType)
                                                                                .addValue("IP_SHIPPER_CLASS", hazardousMaterialClass)
                                                                                .addValue("IP_PACKAGING_COUNT", hazMatPackagingCnt)
                                                                                .addValue("IP_PACKAGING_UNITS", hazMatPackagingUnits)
                                                                                .addValue("IP_TECHNICAL_NAME", hazMatTechnicalName)
                                                                                .addValue("IP_PACKING_INSTRUCTION", hazMatPackInstructions)
                                                                                .addValue("IP_SIGNATURE_NAME", hazMatSignatureName)
                                                                                .addValue("IP_HAZMAT_MATERIAL_TYPE", HazardousMaterialType)
                                                                               ;
                    
                    Map<String, Object> out = simpleJdbcCall.execute(inputparams);

                    opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
                    
                    errorStatus = String.valueOf(out.get("OP_ERROR_STATUS"));
                    
                    logger.info("AASC_ERP_HAZMAT_SHIPMENT_PKG.SAVE_HAZMAT_SHIPMENT_COMMODITY opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
                    } catch(Exception e){
                    logger.severe("Exception e : "+e.getMessage());
                                e.printStackTrace();
                    }
                    logger.info("Exit from saveHazmatPackageDetails method");
                
        return opStatus;
    }
       

    public int deleteHazmatPackageDetails(int pkgSequenceId, String orderNumber, int commodityNo) {
                                          
                                          
       
            logger.info("Entered deleteHazmatPackageDetails() method");
                try {
//                   System.out.println("pkgSequenceId::"+pkgSequenceId);
//                   System.out.println("orderNumber::"+orderNumber);
//                   System.out.println("commodityNo::"+commodityNo);
                    DataSource datasource = aascOracleDAOFactory.createDataSource(); // this method returns the datasources object
                    
                    simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_HAZMAT_SHIPMENT_PKG")
                                                                   .withProcedureName("DELETE_HAZMAT_COMMODITY")
                                                                   .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                                   .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));  
                                                                   
                    SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_ORDER_NUMBER", orderNumber)
                                                                                .addValue("IP_PACKAGE_SEQUENCE_ID", pkgSequenceId)
                                                                                .addValue("IP_COMMODITY_NO", commodityNo);
                    
                    Map<String, Object> out = simpleJdbcCall.execute(inputparams);

                    opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
                    
                    errorStatus = String.valueOf(out.get("OP_ERROR_STATUS"));
                    
                    connection.close();
                } // closing the try bolck
                catch (SQLException e) {
                    formatter.writeLogger(logger, e);
                    try {
                        connection.rollback();
                        // connection.commit();
                    } catch (SQLException se) {
                        formatter.writeLogger(logger, e);
                    }
                } // closing the catch block 
               
                logger.info("Exit from  the deleteHazmatPackageDetails() method");
            return opStatus;
        }


    public LinkedList getHazmatShipmentDetails(int pkgSequenceId, String orderNumber){
        logger.info("Enter in to the getHazmatShipmentDetails() method");
//        System.out.println("pkgSequenceId::"+pkgSequenceId);
//        System.out.println("orderNumber::"+orderNumber);
       LinkedList hazmatDetailsList = new LinkedList();
        
        
            try {
                DataSource datasource = aascOracleDAOFactory.createDataSource(); // this method returns the datasources object
                
                simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_HAZMAT_SHIPMENT_PKG")
                                                               .withProcedureName("GET_HAZMAT_SHIPMENT_DETAILS")
                                                               .declareParameters(new SqlOutParameter("aasc_hazmat_shipment_details", OracleTypes.CURSOR))
                                                               .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                               .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));  
                                                               
                SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_ORDER_NUMBER", orderNumber)
                                                                            .addValue("IP_PACKAGE_SEQUENCE_ID", pkgSequenceId);
                                                                            
                Map<String, Object> out = simpleJdbcCall.execute(inputparams);
//                System.out.println("out::::"+out);
                opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
                
                errorStatus = String.valueOf(out.get("OP_ERROR_STATUS"));
//                System.out.println("opStatus::"+opStatus);
                
                if(opStatus == opStatus){
                    Iterator hazmatShipmentDetails = ((ArrayList)out.get("OP_HAZMAT_SHIPMENT_DETAILS")).iterator();
                    
                    while(hazmatShipmentDetails.hasNext()){
                    
                        map1 = (HashMap<String, ?>)hazmatShipmentDetails.next();
                    AascHazmatPackageInfo aascHazmatPackageInfo = new AascHazmatPackageInfo();                 
//                          System.out.println("String.valueOf(map1.get(ORDER_NUMBER))::"+nullStrToSpc(String.valueOf(map1.get("ORDER_NUMBER"))));
                    aascHazmatPackageInfo.setOrderNumber(nullStrToSpc(String.valueOf(map1.get("ORDER_NUMBER"))));
//                    System.out.println("String.valueOf(map1.get(SHIPPER_CLASS))::"+String.valueOf(map1.get("SHIPPER_CLASS")));
                    aascHazmatPackageInfo.setHazMatClass(nullStrToSpc(String.valueOf(map1.get("SHIPPER_CLASS"))));
//                    System.out.println("map1.get(\"COMMODITY_NO\")::"+map1.get("COMMODITY_NO"));
                    aascHazmatPackageInfo.setHazmatCommNo(((BigDecimal)map1.get("COMMODITY_NO")).intValue());
//                    System.out.println("map1.get(\"DOT_LABELS\")::"+map1.get("DOT_LABELS"));
                    aascHazmatPackageInfo.setHazMatDOTLabel(nullStrToSpc(String.valueOf(map1.get("DOT_LABELS"))));
                    aascHazmatPackageInfo.setHazMatEmerContactName(nullStrToSpc(String.valueOf(map1.get("EMERGENCY_CONTACT_NAME"))));
                    aascHazmatPackageInfo.setHazMatEmerContactNo(nullStrToSpc(String.valueOf(map1.get("EMERGENCY_CONTACT_NUMBER"))));
                    aascHazmatPackageInfo.setHazMatId(nullStrToSpc(String.valueOf(map1.get("HAZMAT_MATERIAL_ID"))));
                    aascHazmatPackageInfo.setHazMatIdNo(nullStrToSpc(String.valueOf(map1.get("IDENTIFICATION_NUMBER"))));
                    aascHazmatPackageInfo.setHazmatPackInstructions(nullStrToSpc(String.valueOf(map1.get("PACKING_INSTRUCTION"))));
                    aascHazmatPackageInfo.setHazMatPkgGroup(nullStrToSpc(String.valueOf(map1.get("PACKGING_GROUP"))));
                    aascHazmatPackageInfo.setHazmatPkgingCnt(((BigDecimal)map1.get("PACKAGING_COUNT")).intValue());
                    aascHazmatPackageInfo.setHazmatPkgingUnits(nullStrToSpc(String.valueOf(map1.get("PACKAGING_UNITS"))));
                    aascHazmatPackageInfo.setHazMatQty(((BigDecimal)map1.get("WEIGHT")).intValue());
                    aascHazmatPackageInfo.setHazmatSignatureName(nullStrToSpc(String.valueOf(map1.get("SIGNATURE_NAME"))));
                    aascHazmatPackageInfo.setHazmatTechnicalName(nullStrToSpc(String.valueOf(map1.get("TECHNICAL_NAME"))));
                    aascHazmatPackageInfo.setHazMatType(nullStrToSpc(String.valueOf(map1.get("HAZMAT_MATERIAL_TYPE"))));
                    aascHazmatPackageInfo.setHazMatUnit(nullStrToSpc(String.valueOf(map1.get("UNITS"))));
                    aascHazmatPackageInfo.setShipmentPackageId(((BigDecimal)map1.get("PACKAGE_SEQUENCE_ID")).intValue());
                    
                    hazmatDetailsList.add(aascHazmatPackageInfo);
                    AascShipmentPackageInfo aascShipmentPackageInfo = new AascShipmentPackageInfo();
                    aascShipmentPackageInfo.setHazmatPackageInfoList(hazmatDetailsList);
                    
                }
//                 System.out.println("hazmatDetailsList size : "+hazmatDetailsList.size());
              }
            } catch (Exception e) {
                e.printStackTrace();
            }            
     
        logger.info("Exit from getHazmatShipmentDetails() method");
        return hazmatDetailsList;
    }
    
}
