package com.aasc.erp.model;

import com.aasc.erp.bean.AascCustomerBean;

import com.aasc.erp.bean.AascUserBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * AascUserControlDAO is an interface has abstract method 
      which is implemented by the  AascOracleUserControlDAO class.

 * @author 	N Srisha
 * @version 1
 * @since
 * 
 * 
 * 24/07/2014   Suman Gunda     Added getAllCustomerDetails() method for create customer
 * 17/12/2014   Eshwari M       Merged Sunanda code 
 * 04/02/2015   Suman G         Added forgotPassword() for forgot password functionality. 
 * 04/02/2015   Suman G         Added changePassword() for change password functionality.
 * 16/03/2015   Eshwari M       Added editUserProfile() for EditProfile feature
 * 16/03/2015   Suman G         Modified code of createOrUpdateUser() method for send mail to Alternate Email Address.
 * 03/06/2015   Y Pradeep       Added getWeightScaleNames() method for Weighing Scale details.
 * 04/12/2015   Suman G         Added code for resending the password functionality to user.
 * 24/02/2016   Suman G         Added code for new Transaction Management design.
 */
 
public interface AascUserControlDAO {


    /**

     * This Method implements by the  AascOracleUserControlDAO class.

     * @param userName String and password String 

     * @return HashMap 

     */
     
    public HashMap userAuthentication(String userName,String password);
    
    /**

     * This Method implements by the  AascOracleUserControlDAO class.

     * @return LinnkedList
     
     * It is used to get all existing customer details in Customer Details Page 

     */
    
    public LinkedList getAllCustomerDetails();
    
    /**

     * This Method implements by the  AascOracleUserControlDAO class.

     * @return LinnkedList

     * It is used for getting country code list
     
     */
     
    public LinkedList getCountryCodeDetails();
    
    /**

     * This Method implements by the  AascOracleUserControlDAO class.

     * @return HashMap

     * It is used for getting country names
     
     */
    public HashMap getCountryNameDetails();
    
    /**

     * This Method implements by the  AascOracleUserControlDAO class.

     * @return int

     * It is used for save customer details(either create or update)
     
     */
    public int saveCustomerDetails(AascCustomerBean aascCustomerInfo);
    
    
    /**

     * This Method implements by the  AascOracleUserControlDAO class.

     * @return String

     * It is used for save or update user details(either create or update)
     
     */
    
    public String createOrUpdateUser(AascUserBean aascUserBean);
    
    
    /**

     * This Method implements by the  AascOracleUserControlDAO class.

     * @return int

     * It is used for edit user profile
     
     */
    
    
    
    
    /**

     * This Method implements by the  AascOracleUserControlDAO class.

     * @param clientID int type
     
     * @return LinkedList

     * It is used for getting all existing user details
     
     */
     
    public LinkedList getAllUserDetails(int clientID);
    
    /**

     * This Method implements by the  AascOracleUserControlDAO class.
     * @param userName String type
     * @param oldPassword String type
     * @param newPassword String type
     * @return int
     * It is used for change the password and returns the status of change.
     
     */
     
    public int changePassword(String userName, String oldPassword, String newPassword);
    
    /**
     * This Method implements by the  AascOracleUserControlDAO class.
     * @param userName String type
     * @param password String type
     * @return int
     * It is used for create a new password if user forgot password.
     
     */
     
    public int forgotPassword(String userName, String password);
    
    /**
     * This Method implements by the  AascOracleUserControlDAO class.
     * @param 
     * @return int
     * It is used for updating the use profile details in database
     */
    public int editUserProfile(AascUserBean aascUserBean);
    
    /**
     * This Method implements by the  AascOracleUserControlDAO class. It is used for getting weighingscale names.
     * @return HashMap
     */
    public HashMap getWeightScaleNames();
    
    /**
     * This Method implements by the  AascOracleUserControlDAO class.
     * @param 
     * @return String
     * It is used for resending user 
     */
    public String resendPassword(AascUserBean aascUserBean);
    
    /**
    
     * This Method interacts with the database 
                and retrieves monthly carrier Details to disply graph.
    
     * @param client_id String and location_id String 
    
     * @return ArrayList 
    
     */
    public ArrayList getMonthlyCarrierDetails(int client_id, int location_id);
    
    /**
    
     * This Method interacts with the database 
                and retrieves Pricing Details
    
     * @param customerType String, transactionRange String and durationType String 
    
     * @return LinkedList 
    
     */
    public LinkedList getPricingDetails(String customerType, String transactionRange, String durationType);
    
    /**
    
     * This Method interacts with the database and retrieves Transaction Ranges
    
     * @param customerType String 
    
     * @return String 
    
     */
    public String getTransactionRange(String customerType);
    
    /**
    
     * This Method interacts with the database and retrieves Transaction Ranges
    
     * @param customerType String and transactionCount String
    
     * @return double
    
     */
    public double getTotalFeeOnTransactionCount(String customerType, String transactionCount);
}
