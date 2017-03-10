/*========================================================================+
@(#)AascOrderSearchDelegate.java 22/01/2015
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/

package com.aasc.erp.delegate;

import com.aasc.erp.bean.AascOrderSearchBean;
import com.aasc.erp.model.AascOracleOrderSearchDAO;

import com.aasc.erp.model.AascOrderSearchDAO;

import com.aasc.erp.util.AascLogger;

import java.util.logging.Logger;


/**
 * AascCreateCustomerDelegate class for customer and user related logics.<br />
 * @author      Jagadish
 * @version 1.1
 * @since
 * 
 *  22-Jan-2015 Jagadish Jain Added complete code. 
 * */
public class AascOrderSearchDelegate {
    public AascOrderSearchDelegate() {
    }
    static Logger logger = 
        AascLogger.getLogger(" AascOrderSearchDelegate "); // logger object
    
    
         /**
              Method searchOrder() passes shipment_fromDate, shipment_toDate or customer_name 
              as input parameter and gets list of orderNumbers from the database.
              @param shipmentFromDate,shipmentFromDate,customerName String.
              @return aascOrderSearchBean object of aascOrderSearchBean class.
              */
    public AascOrderSearchBean searchOrder(int clientId, java.sql.Date shipmentFromDate, 
                                                            java.sql.Date shipmentToDate, String customerName){
                                                            
                    AascOrderSearchBean aascOrderSearchBean = new  AascOrderSearchBean();
                    try{
                    AascOrderSearchDAO aascOrderSearchDAO = new AascOracleOrderSearchDAO() ;
                    aascOrderSearchBean=
                                        aascOrderSearchDAO.searchOrderNumbers(clientId,shipmentFromDate,
                                                                                       shipmentToDate,customerName);
                    }
                    catch(Exception e){
                        logger.severe(e.getMessage());
                    }
                    
                     
                    return aascOrderSearchBean ;
                                                            
                                                            }
}
