package com.aasc.erp.model;

import com.aasc.erp.bean.AascCarrierConfigurationBean;
import com.aasc.erp.delegate.AascCarrierConfigurationDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * AascCarrierConfigurationDAO class is an interface has abstract method 
    which is implemented by the  AascOracleCarrierConfigurationDAO class.
 * @version   2
 * @author    Venkateswaramma Malluru
 * @History
 *
 * 07/01/2015   Y Pradeep   Added History section, java doc's and code alignment.
 * 
 * */

public interface AascCarrierConfigurationDAO {

    /**Method to get carrier names
     * @return HashMap
     */
    public HashMap getCarrierNames();
    
    /**Method to get carrier code values
     * @return HashMap
     */
    public HashMap getCarrierCodeValues();
    
    /**Method to get Look Up Values
     * @return HashMap
     */
    public HashMap getCarrierLookUpValues(int carrierCode, String lookUpCode, String carrierMode);
    
    /**Method to get carrier configuration details
     * @return HashMap
     */
    public AascCarrierConfigurationBean getCarrierConfigurationDetails(AascCarrierConfigurationBean aascCarrierConfigurationBean);
    
    /**Method to save carrier configuration details
     * @return HashMap
     */
    public int saveCarrierConfigurationInfo(AascCarrierConfigurationBean  aascCarrierConfigurationBean, int userId);
    
    /**Method to get Look Up Details
     * @return HashMap
     */
    public HashMap getLookUpDetails(String lookupCode);

    /** This method loads Carrier Look Up Values for lookUpCode and carrierCode
     * @param carrierCode int
     * @param lookUpCode String
     * @return hmlist HashMap
     */
    public HashMap getCarrierShipMethodValues(int carrierCode, String lookUpCode, String carrierMode);

}
