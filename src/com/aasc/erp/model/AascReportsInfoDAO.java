package com.aasc.erp.model;

import java.util.LinkedList;


 /**
  * AascReportsInfoDAO is a DAO interface and is implemented by AascOracleReportsInfoDAO class for Reports functionalities.
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

public interface AascReportsInfoDAO {

    public abstract LinkedList getTrackingDetails(int locationId, int clientId);           
    
    public abstract int saveTrackingDetails(LinkedList trackingDetailsList); 

  
} // end of AascReportsInfoDAO class

