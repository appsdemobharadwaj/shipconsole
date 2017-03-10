 /*
   * @(#)AascOracleOrderSearchDAO.java     21/01/2015
   * Copyright (c) Apps Associates Pvt. Ltd.
   * All rights reserved.
   */


 package com.aasc.erp.model;

 import com.aasc.erp.bean.AascOrderSearchBean;

 import com.aasc.erp.util.AascLogger;

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

 /**
   AascOracleOrderSearchDAO class implements AascOrderSearchDAO interface class.
   This class retrieves list of shipped order number 
   from the database and sets to the AascOrderSearchInfo bean class.
  */
  /*
     *@author Jagadish Jain
     *@version 1.1
     * History
     * 27/03/2015   Sunanda K       Modified code for bug fix #2701.
     * 08/05/2015   Y Pradeep       Added if condition to avoid null pointer exception for shipment date while retreiving. Bug #2913.
     */
 public class AascOracleOrderSearchDAO implements  AascOrderSearchDAO {
     public AascOracleOrderSearchDAO() {
     }
     private int status;
         private String errorStatus;
     SimpleJdbcCall simpleJdbcCall;
           
         private AascOracleDAOFactory connectionFactory = 
              new AascOracleDAOFactory(); // creating object
         
         AascOrderSearchBean aascOrderSearchBean;                  
         
         private static Logger logger = 
             AascLogger.getLogger("AascOracleOrderSearchDAO");
     /**
          Method searchOrderNumbers() passes shipment_fromDate, shipment_toDate or customer_name 
          as input parameter and gets list of orderNumbers from the database.
          @ param shipment_fromDate,shipment_toDate,customer_name String.
          @return AascOrderSearchBean object of AascOrderSearchBean class.
          */
          
         public AascOrderSearchBean searchOrderNumbers(int clientId, java.sql.Date shipmentFromDate, 
                                                                 java.sql.Date shipmentToDate, String customerName){
            
             aascOrderSearchBean = new AascOrderSearchBean();     
             logger.info("Entered searchOrderNumbers()");
             System.out.println("clientId"+clientId+"\t shipmentFromDate"+shipmentFromDate
                                    +"\t shipmentToDate"+shipmentToDate+"\t customerName"+customerName);
             try {
                 DataSource  datasource = connectionFactory.createDataSource();
                 simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIPMENT_ORDERS_PKG")
                                                                .withProcedureName("search_order_number")
                                                                .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                                .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR))
                                                                .declareParameters(new SqlOutParameter("aasc_shipment_details",OracleTypes.CURSOR));
                                                                 
                  //input parameters assignment
                 SqlParameterSource inputparams = 
                                      new MapSqlParameterSource().addValue("IP_CLIENT_ID",clientId)
                                                                 .addValue("IP_SHIPMENT_FROM_DATE",shipmentFromDate)
                                                                 .addValue("IP_SHIPMENT_TO_DATE",shipmentToDate)
                                                                 .addValue("IP_CUSTOMER_NAME",customerName);
                 
                 Map<String, Object> out = simpleJdbcCall.execute(inputparams);

                 logger.info("After Execute");
                 if (out.get("op_status") != null) {
                     status = 
                             Integer.parseInt(String.valueOf(out.get("op_status")));
                 }
                  errorStatus = String.valueOf(out.get("op_error_status"));
                 logger.info("Errostatus:"+status);
                 logger.info("ErrorMessage"+errorStatus);
                 
                 Iterator it = ((ArrayList)out.get("OP_ORDER_DETAILS")).iterator();
                 HashMap<String, ?> map1 = new HashMap<String, String> ();    
                     
                     if (status == 120) // if status == 120 success
                     {
                         LinkedList orderNumberlist = new LinkedList();
                         while (it.hasNext()) {
                             map1 = (HashMap<String, ?>)it.next();
                             aascOrderSearchBean = new AascOrderSearchBean(); 
                             aascOrderSearchBean.setOrderNumber((String)map1.get("ORDER_NUMBER"));
                             logger.info("ON"+(String)map1.get("ORDER_NUMBER"));
                             aascOrderSearchBean.setCustomerName((String)map1.get("CUSTOMER_NAME"));
                             //                        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                              logger.info(""+map1.get("SHIP_DATE"));
                             String shipDate=(String)map1.get("SHIP_DATE");
                             logger.info("shipDate: "+ shipDate);
                             if(shipDate != null && !"".equalsIgnoreCase(shipDate)){
                                 shipDate=shipDate.substring(0,10);
                                 //System.out.println("shipDate after substring: "+ shipDate);
                             } else {
                                 shipDate = "";
                             }
                             aascOrderSearchBean.setShipmentDate(shipDate);
                             aascOrderSearchBean.setShipMethod((String)map1.get("SHIP_METHOD"));
                             orderNumberlist.add(aascOrderSearchBean);
                             //                        System.out.println("orderNumber::"+orderNumberlist);
                         
                         }
                         
                         aascOrderSearchBean.setStatus(status);
                         aascOrderSearchBean.setOrderNumberList(orderNumberlist);
                       //  System.out.println("orderNumberlist::"+orderNumberlist);
                     } else // if status is not equal to 0 setting to null
                     {
                         aascOrderSearchBean.setStatus(status);
                         aascOrderSearchBean.setOrderNumberList(null);
                     }

             logger.info("Status in search_order_number procedure :" + status +"\t errorStatus :: "+errorStatus);
             }// end of try block
             catch (Exception e) {
             logger.severe("Exception::"+e.getMessage());
                 status = 122;
                 aascOrderSearchBean.setStatus(status);
                 //Sunanda added the above code for bug fix #2701
             } finally {
             } // end of finally block
             logger.info("Exit from searchOrderNumbers()");
             return aascOrderSearchBean;
             } // end of searchOrderNumbers()
     
     
     
 }
