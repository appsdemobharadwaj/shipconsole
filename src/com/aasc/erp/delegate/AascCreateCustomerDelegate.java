/*========================================================================+
@(#)AascCreateCustomerDelegate.java 25/07/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/
package com.aasc.erp.delegate;

import com.aasc.erp.bean.AascCustomerBean;
import com.aasc.erp.bean.AascSubscriptionDetailsBean;
import com.aasc.erp.model.AascDAOFactory;
import com.aasc.erp.model.AascOracleUserControlDAO;
import com.aasc.erp.model.AascSubscriptionDetailsDAO;
import com.aasc.erp.model.AascUserControlDAO;

import com.aasc.erp.util.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * AascCreateCustomerDelegate class for customer and user related logics.<br />
 * @author      Suman Gunda <br>
 * @version 1 <br>
 * @since
 * 
 *  17-Dec-2014  Eshwari M  Merged Sunanda code for SC Lite
 *  06-Feb-2015  Suman g    Passing actiontype as a parameter to sendMail() method for showing proper message.
 *  09-Mar-2015  Sunanda K  Added code to pass Url with Username and password in mail
 *  28-Oct-2015  Suman G    Added code to implement Transaction Count for Update Customer.
 *  24-FEB-2016  Suman G    Changed bean for new Transaction Management design.
 *  09-MAR-2016  Suman G    Added code to fix #4385
 * */
public class

AascCreateCustomerDelegate {
    public AascCreateCustomerDelegate() {
    }
    static Logger logger = 
        AascLogger.getLogger(" AascCreateCustomerDelegate "); // logger object
    AascUserControlDAO aascUserControlDAO = new AascOracleUserControlDAO();
    AascCustomerBean aascCustomerInfo = new AascCustomerBean();
    
    AascDAOFactory factory = AascDAOFactory.getAascDAOFactory(1);

    /**

     * This Method implements by the  AascCreateCustomerDelegate class for getting country hashmap.

     * @param session HttpSession

     * @return String 

     */
     
    public String setCountryDetails(HttpSession session) {
        try {


            logger.info("Entered setCountryDetails");
            LinkedList countryCodelist = 
                aascUserControlDAO.getCountryCodeDetails();
            session.setAttribute("countryCodelist", (Object)countryCodelist);

            Map countryNameHashMap = 
                new TreeMap((HashMap<?,?>)aascUserControlDAO.getCountryNameDetails());
            
            LinkedList customerType = aascUserControlDAO.getPricingDetails("CUSTOMER_TYPE","","");
         //   System.out.println("customertype;:::"+customerType);
            Iterator it = customerType.iterator();
            HashMap customerTypeHM = new HashMap();
            while(it.hasNext()){
                String code = (String)it.next();
                customerTypeHM.put(code,code);
            }
            LinkedList transactionRange = aascUserControlDAO.getPricingDetails((String)customerType.get(0),"TRANSACTION_RANGE","");
      //      System.out.println("Transaction range::::"+transactionRange);
            it = transactionRange.iterator();
            HashMap transactionRangeHM = new HashMap();
            while(it.hasNext()){
                String code = (String)it.next();
                transactionRangeHM.put(code,code);
            }
            LinkedList durationType = aascUserControlDAO.getPricingDetails("","","DURATION_TYPE");
            it = durationType.iterator();
            HashMap durationTypeHM = new HashMap();
            while(it.hasNext()){
                String code = (String)it.next();
                durationTypeHM.put(code,code);
            }
            logger.info("customer type::::::"+customerType);
            logger.info("durationType::::::"+durationType);
            
            session.setAttribute("customerType",(Object)customerTypeHM);
            session.setAttribute("durationType",(Object)durationTypeHM);
            session.setAttribute("transactionRange",(Object)transactionRangeHM);
            session.setAttribute("countryNameHashMap", 
                                 (Object)countryNameHashMap);
            return "success";
        } catch (Exception e) {
            return "failure";
        }

    }
    
    
    /**

     * This Method implements by the  AascCreateCustomerDelegate class for Creating New Customer.

     * @param request HttpServletRequest, session HttpSession and aascCustomerInfo AascCustomerBean

     * @return String 

     */

    public String createNewCustomer(HttpServletRequest request, 
                                    HttpSession session, 
                                    AascCustomerBean aascCustomerInfo) {
        logger.info("Entered createNewCustomer");
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.getNext();
        SendMail sendMail = new SendMail();
        String url = request.getScheme()+ "://"+request.getServerName()+":"+ request.getServerPort()+ request.getContextPath();
        //logger.info("@@@@@@The url is $$$$$$@@@@"+url);
        boolean statusMail = false;

        aascCustomerInfo.setPassword(password);

        int status = 0;


        status = aascUserControlDAO.saveCustomerDetails(aascCustomerInfo);


        if (status == 1) {
            try{
            statusMail = 
                    sendMail.send(aascCustomerInfo.getEmailAddress(), "", password,request.getParameter("actiontype"), url);
            if (statusMail == true) {
                request.setAttribute("key", "aasc406");
                try {
                    LinkedList customerList = new LinkedList();
                    customerList = aascUserControlDAO.getAllCustomerDetails();
                    session.setAttribute("customerDetailsList", 
                                         (Object)customerList);
                } catch (Exception e) {
                    logger.severe(e.getMessage());
                }
                session.setAttribute("aascCustomerInfo", aascCustomerInfo);
                return "View";
            } else {
                request.setAttribute("key", "aasc40730");
            }
            }catch(Exception e){
                logger.severe(e.getMessage());
            }
        } else if (status == 0) {
            request.setAttribute("key", "aasc407");
        } else if (status == 10) {
            request.setAttribute("key", "aasc40710");
        } else if (status == 20) {
            request.setAttribute("key", "aasc40720");
        } else {
            request.setAttribute("key", "aasc406");
        }
        session.setAttribute("aascCustomerInfo", aascCustomerInfo);
        logger.info("Exit createNewCustomer");
        return "Create";
    }


    /**

     * This Method implements by the  AascCreateCustomerDelegate class for Update existing Customer.

     * @param request HttpServletRequest, session HttpSession and aascCustomerInfo AascCustomerBean

     * @return String 

     */
    
    public String updateCustomer(HttpServletRequest request, 
                                 HttpSession session, 
                                 AascCustomerBean aascCustomerInfo) {
        logger.info("Entered updateNewCustomer");         
        int status = aascUserControlDAO.saveCustomerDetails(aascCustomerInfo);
        LinkedList customerList = new LinkedList();

        if (status == 1) {
            request.setAttribute("key", "aasc408");
            try {
                
                customerList = aascUserControlDAO.getAllCustomerDetails();
                session.setAttribute("customerDetailsList", 
                                     (Object)customerList);
                LinkedList countryCodelist = 
                    aascUserControlDAO.getCountryCodeDetails();
                session.setAttribute("countryCodelist", 
                                     (Object)countryCodelist);
            } catch (Exception e) {
                logger.severe(e.getMessage());

            }
            //return "success";
            Iterator it2 = customerList.iterator();
            AascCustomerBean obj = null;
            while(it2.hasNext()){
                obj = (AascCustomerBean)it2.next();
                if(obj.getClientID() == aascCustomerInfo.getClientID()){
                    aascCustomerInfo.setMakePayment("N");
                    aascCustomerInfo.setCurrentPackageBalance(obj.getCurrentPackageBalance());
                    aascCustomerInfo.setCumulativePackageCount(obj.getCumulativePackageCount());
                    aascCustomerInfo.setSubscriptionEndDate(obj.getSubscriptionEndDate());
                    aascCustomerInfo.setSubscriptionExpiryFlag(obj.getSubscriptionExpiryFlag());
                    aascCustomerInfo.setCustomerType(obj.getCustomerType());
                    aascCustomerInfo.setInvoiceType(obj.getInvoiceType());
                    aascCustomerInfo.setMonthlyEstimatedTransactionRange(obj.getMonthlyEstimatedTransactionRange());
                    aascCustomerInfo.setPricingDuration(obj.getPricingDuration());
                }
            }
            session.setAttribute("aascCustomerInfo", aascCustomerInfo);
            //session.setAttribute("editSave","editSave");
            return "Edit";

        } else if (status == 10) {
            request.setAttribute("key", "aasc40710");
        } else if (status == 20) {
            request.setAttribute("key", "aasc40720");
        } else {
            request.setAttribute("key", "aasc409");
        }
        session.setAttribute("aascCustomerInfo", aascCustomerInfo);
        logger.info("Exit updateNewCustomer");         
        return "Edit";

    }
    public void updateTransactionDetails(HttpServletRequest request, HttpSession session){
        AascSubscriptionDetailsBean obj = new AascSubscriptionDetailsBean();
        obj.setClientID((Integer)session.getAttribute("client_id"));
        obj.setCustomerType(request.getParameter("customerType"));
        obj.setInvoiceType(request.getParameter("invoiceType"));
        obj.setMonthlyEstimatedTransactionRange(request.getParameter("monthlyEstimatedTransactionRange"));
        obj.setPricingDuration(request.getParameter("pricingDuration"));
        obj.setTransactionCount(Integer.parseInt(request.getParameter("transactionCount")));
        
        AascSubscriptionDetailsDAO aascSubscriptionDetailsDAO = factory.getAascSubscriptionDetailsDAO();
        
        int status = aascSubscriptionDetailsDAO.updateTransactionDetails(obj);
        
        if(status == 100){
            AascIndexRequestDelegate aascIndexRequestDelegate = new AascIndexRequestDelegate();
            aascIndexRequestDelegate.getPricingDetails(request,session);
            request.setAttribute("key", "aasc453");
        }
        
    }
}   // End of AascCreateCustomerDelegate class

