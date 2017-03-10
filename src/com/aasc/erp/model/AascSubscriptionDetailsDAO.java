package com.aasc.erp.model;

import com.aasc.erp.bean.AascRatesTable;
import com.aasc.erp.bean.AascSubscriptionDetailsBean;

import java.util.HashMap;
import java.util.LinkedList;


/**
 * AascSubscriptionDetailsDAO is an interface has abstract method 
      which is implemented by the  AascOracleSubscriptionDetailsDAO class.

 * @author 	Suman G
 * @version 1
 * @since   25/02/2016
 * 
 * 
 */

public interface AascSubscriptionDetailsDAO {
    
    /**

     * This Method implements by the  AascOracleUserControlDAO class.

     * @param clientId int 
 
     * @return AascSubscriptionDetailsBean 

     */
     
    public AascSubscriptionDetailsBean subscriptionDetails(int clientId);
    
    /**

     * This Method implements by the  AascOracleUserControlDAO class.

     * @param aascSubscriptionDetailsBean AascSubscriptionDetailsBean 
    
     * @return int 

     */
    public int updateTransactionDetails(AascSubscriptionDetailsBean aascSubscriptionDetailsBean);
    
    /**

     * This Method implements by the  AascOracleUserControlDAO class.
    
     * @return LinkedList 

     */
    public LinkedList getRateDetails();
    
}
