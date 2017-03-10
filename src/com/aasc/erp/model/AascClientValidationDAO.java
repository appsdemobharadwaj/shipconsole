package com.aasc.erp.model;

import java.util.HashMap;

/**
 * AascClientValidationDAO is an interface has abstract method 
      which is implemented by the  AascOracleClientValidationDAO class.

 * @author 	Jagadish Jain
 * @version 1
 * @since
 * 
 * 
 * 12/08/2015   Jagadish Jain    Wrote the initial code required to make db call to perform client validation(Check for tolerance and expiry)
 */
public interface AascClientValidationDAO {

    /**

     * This Method implements by the  AascOracleUserControlDAO class.

     * @param int clientId and int pkgCount

     * @return HashMap 

     */
     
    public HashMap clientValidation(int clientId,int pkgCount);

}
