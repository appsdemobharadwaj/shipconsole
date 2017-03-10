package com.aasc.erp.model;

/*========================================================================+
@(#)AascProfileOptionsDAO.java 08/08/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/

 import com.aasc.erp.bean.AascProfileOptionsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * AascProfileOptionsDAO is an interface has abstract method 
       which is implemented by the  AascOracleProfileOptionsDAO class.

 * @author      Suman Gunda
 * @version 1
 * @since
 * @History
 * 
 * 10/03/2015   Y Pradeep       Removed getLookUpDetails() method for removing reference1value and reference2value select fields.
 *
 */
public interface AascProfileOptionsDAO {
    /**

     * This Method implements by the  AascOracleProfileOptionsDAO class.

     * @param  sessionList LinkedList
     
     * @return aascProfileOptionsBean of aascProfileOptionsBean class  

     */
     public AascProfileOptionsBean getProfileOptionsBean(LinkedList sessionList);
     
     /**

      * This Method implements by the  AascOracleProfileOptionsDAO class.

      * @return ArrayList of aascProfileOptionsBean class  

      */
     public ArrayList getCarrierPayMethodValues();
     
     
     
    /**

     * This Method implements by the  AascOracleProfileOptionsDAO class.

     * @return int   

     */     
     public HashMap getClientDetailsLookUpValues();

     
    public int saveOrUpdateProfileOptions(AascProfileOptionsBean aascProfileOptionsBean);
    
    /**

     * This Method implements by the  AascOracleProfileOptionsDAO class.

     * @param carrierCode int, hazMatId String, locationId int, clientID int, userId int

     * @return String   

     */
    public String getHazMatLookUpValues(int carrierCode, String hazMatId, int locationId, int clientID, int userId);
}
