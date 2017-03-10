package com.aasc.erp.model;
/*
 * @(#)AascAccountNumbersDAO.java     28/11/2014
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 */

import com.aasc.erp.bean.AascAccountNumbersBean;

/**
 * AascAccountNumbersDAO class is DAO factory interface for Account Numbers.
 * @version   1
 * @author    Venkateswaramma Malluru
 * History
 * 
 * 07-Jan-2015   Y Pradeep           Commented retrieveAccountNumbers() method which is not in use.
 * 30-Jan-2015   Y Pradeep           Ran self audit and added java doc for all methods.
 */
public interface AascAccountNumbersDAO {

    /** Method to get Account Numbers.
     * @param carrierCode
     * @param clientId
     * @param locId
     * @param queryTimeOut
     */
    public AascAccountNumbersBean getAccountNumbersInfo(int carrierCode,int clientId,int locId, 
                                                        int queryTimeOut
                                                        );

    /** Method to save all Account Numbers.
     * @param userId
     * @param aascAccountNumbersInfo
     * @param queryTimeOut
     */
    public int saveAllAcountNumbersInfo(int userId, 
                                        AascAccountNumbersBean aascAccountNumbersInfo, 
                                        int queryTimeOut);

    /** Method to save the Account Numbers.
     * @param userId
     * @param locationId
     * @param aascAccountNumbersInfo
     * @param queryTimeOut
     */
    public AascAccountNumbersBean getSaveAccountNumbersInfo(int userId, 
                                                            int locationId,
                                                            AascAccountNumbersBean aascAccountNumbersInfo, 
                                                            int queryTimeOut);

}
