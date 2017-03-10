/*

 * @(#)AascOrderSearchDAO.java        21/01/2015

 ** Copyright (c) Apps Associates Pvt. Ltd.

 * All rights reserved.

 */
package com.aasc.erp.model;

import com.aasc.erp.bean.AascOrderSearchBean;

/*

 * @author Jagadish Jain

 * @version 1.1

 */

/**

 AascOrderSearchDAO is an interface has an abstract method 

 which is implemented by the  AascOracleOrderSearchDAO class.

 AascOracleOrderSearchDAO is a class which interacts with the database and 

 corresponding bean file for retriving the Order related information.

 */
public interface AascOrderSearchDAO {

    /**

         * This Method implements by the  AascOracleOrderSearchDAO class.
         *  @param clientId, shipment_fromDate,shipment_toDate,customer_name String.
         * @return AascOrderSearchBean Object

         */

        public AascOrderSearchBean searchOrderNumbers(int clientId, java.sql.Date shipment_fromDate, 
                                                         java.sql.Date shipment_toDate, String customer_name);

}
