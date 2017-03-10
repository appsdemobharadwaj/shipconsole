package com.aasc.erp.delegate;

import com.aasc.erp.bean.AascUserBean;
import com.aasc.erp.model.AascOracleUserControlDAO;
import com.aasc.erp.model.AascUserControlDAO;
import com.aasc.erp.util.AascLogger;

import com.aasc.erp.util.SendMail;

import java.util.LinkedList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/*========================================================================+
@(#)AascCreateUserDelegate.java 05/08/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/


/**
 * AascCreateUserDelegate class pass data to AascSetupLocationDAO class.
 * @author      N Srisha
 * @version 1.0
 * @since
 * 
 * History:<br>
 * 
 *  17-Dec-2014   Eshwari M   Merged Sunanda code for SC Lite
 *  15-Jan-2015   Y Pradeep   Merged Sunanda's code on 1.0 release bugs.
 *  20-Jan-2015   K Sunanda   Removed unused variables and unnecessary loggers
 *  20-Jan-2015   Y Pradeep   Removed commented code.
 *  16-Mar-2015    Eshwari M  Added editUserProfile() for EditProfile feature
 *  16-Mar-2015   Suman G     Modified return type from int to String of addOrUpdateUser() method for Alternate Email ID
 *  30-Mar-2015   Sunanda K   Modified code for bug fix #2748
 *  04-Dec-2015   Suman G     Added code for resending the password functionality to user.
 **/
public class

AascCreateUserDelegate {

    private static Logger logger = 
        AascLogger.getLogger("AascCreateUserDelegate");

    int clientId;
    int userID;

    AascUserControlDAO aascUserControlDAO = new AascOracleUserControlDAO();

    /**

     * commonAction Method gets clientId and userId from session
                    and sets to object.

     * @param  session, aascUserBean object of class AascUserBean  

     * @return  aascUserBean object

     */
    public AascUserBean commonAction(HttpServletRequest request, 
                                     AascUserBean aascUserBean, 
                                     HttpSession session) {

        try {
          
            String actionType = aascUserBean.getActionType();
            if ("editUserDetails".equalsIgnoreCase(actionType) || 
                "editUserProfile".equalsIgnoreCase(actionType)) {
                clientId = 
                        (Integer)(session.getAttribute("client_id")); 
                userID = Integer.parseInt((request.getParameter("userID")));

                LinkedList userList = new LinkedList();
                userList = aascUserControlDAO.getAllUserDetails(clientId);
                logger.info("User list in Common action class is "+userList);
                session.setAttribute("userDetailsList", (Object)userList);
                //Sunanda Modified code for bug fix #2748
            } else if ("AddNewUser".equalsIgnoreCase(actionType)) {
                clientId = (Integer)request.getAttribute("clientID");
                userID = 0;
                logger.info("clientId: " + clientId);
                logger.info("user_id: " + userID);
            }

            //aascUserBean.setClientID(clientId);
            //aascUserBean.setUserID(userID);

        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }
        return aascUserBean;
    }

    /**

     * This Method used to create or update user.

     * @param  aascUserBean AascUserBean  

     * @return  String

     */
    public String addOrUpdateUser(AascUserBean aascUserBean) {
        logger.info("Entered AddOrUpdateUser in AascCreateUserDelegate");
        String status = aascUserControlDAO.createOrUpdateUser(aascUserBean);

        return status;
    }
    /**

     * This Method used to get user details.

     * @return  LinkedList

     */
    public LinkedList getUserDetails() {
        LinkedList userList = new LinkedList();
        userList = aascUserControlDAO.getAllUserDetails(clientId);

        return userList;
    }

    public int editUserProfile(AascUserBean aascUserBean)
    {
       int status = aascUserControlDAO.editUserProfile(aascUserBean);
       return status ;
    }
    
    public void resendPassword(AascUserBean aascUserBean, HttpServletRequest request){
        String password = aascUserControlDAO.resendPassword(aascUserBean);
//        int status=0;
            clientId = 
         (Integer)(request.getSession(true).getAttribute("client_id"));
        String url = request.getScheme()+ "://"+request.getServerName()+":"+ request.getServerPort()+ request.getContextPath();
        
        if(!"".equalsIgnoreCase(password)){
            SendMail sendMail=new SendMail();
            String EmailId=aascUserBean.getEmailAddress();
            boolean sentResponse=sendMail.send(EmailId,"", password,request.getParameter("actiontype"),url);
            String alternateId=aascUserBean.getAlternateEmailAddress();
            logger.info("alternate mail id::::"+alternateId);
            if(alternateId!=null && !"".equalsIgnoreCase(alternateId)){
                sendMail.send(EmailId,alternateId, password,request.getParameter("actiontype"),url);
            }
            if(sentResponse == true){ 
                request.setAttribute("key", "aasc430");

                try {
                    LinkedList userList = new LinkedList();
                    userList = getUserDetails();
                    request.getSession(true).setAttribute("userDetailsList", (Object)userList);
                } catch (Exception e) {
                    logger.severe("Exception::"+e.getMessage());
                }
            }        
        }
        else {
            request.setAttribute("key", "aasc431");
        }
//        return status ;
        
    }
}
