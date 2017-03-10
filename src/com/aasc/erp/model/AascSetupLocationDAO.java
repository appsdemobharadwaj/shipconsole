package com.aasc.erp.model;
/*========================================================================+
@(#)AascSetupLocation.java 24/07/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/


import com.aasc.erp.bean.AascSetupLocationBean;

import java.util.HashMap;

/**
 * AascSetupLocationDAO is an interface has abstract method 
      which is implemented by the  AascOracleSetupLocationDAO class.

 * @author 	N Srisha
 * @version 1
 * @since
 * History:

 * 07/01/2015   Eshwari M   Merged Sunanda's code : Modified getAllLocationDetails to return hashmap instead of linkedlist.
 * 20/01/2015   Sunanda K   Removed unused imports
 */
 

public interface AascSetupLocationDAO {

    /**
     * This Method implements by the  AascOracleSetupLocationDAO class.
     * @param aascSetupLocationBean of AascSetupLocationBean class 
     * @return integer 
     */
    public int createOrUpdateLocation(AascSetupLocationBean aascSetupLocationBean);
    /**
     * This Method implements by the  AascOracleSetupLocationDAO class.
     * @param clientId int 
     * @return LinkedList 
     */
    public HashMap getAllLocationDetails(int clientId );
                                                     
}
