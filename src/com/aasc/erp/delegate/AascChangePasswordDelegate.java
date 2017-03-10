package com.aasc.erp.delegate;

/*========================================================================+
 @(#)AascChangePasswordDelegate.java 04/02/2015
 * Copyright (c)Apps Associates Pvt. Ltd.
 * All rights reserved.
 +===========================================================================*/

import com.aasc.erp.model.AascOracleUserControlDAO;
import com.aasc.erp.model.AascUserControlDAO;
import com.aasc.erp.util.AascLogger;
import com.aasc.erp.util.PasswordGenerator;
import com.aasc.erp.util.SendMail;
import com.aasc.erp.util.TripleDES;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * AascChangePasswordDelegate  <br>
 * This class having the functionality Change Password and Forgot Password.
 * @author      Suman Gunda <br>
 * @version 1 <br>
 * @since 04/02/2015
 * 
 * 06/02/2015  Suman G      Passing actiontype as a parameter to sendMail() method for showing proper message in Email.
 * 09/03/2015  Sunanda k    modified code to send url along with password and username through mail
 * */
public class

AascChangePasswordDelegate {
    public AascChangePasswordDelegate() {
    }

    static Logger logger = 
        AascLogger.getLogger(" AascChangePasswordDelegate "); // logger object

    AascUserControlDAO aascUserControlDAO = new AascOracleUserControlDAO();

    /**

     * This Method has the business logic for change password.
     * @param request HttpServletRequest
     * @return int
     * It is used for change the password and returns the status of change.
     
     */

    public int changePassword(HttpServletRequest request) {
        logger.info("Entered changepassword()");
        String username = request.getParameter("userNameCPwd");
        String newPassword = request.getParameter("newPasswordCPwd");
        String oldPassword = request.getParameter("oldPasswordCPwd");

        int status = 
            aascUserControlDAO.changePassword(username, oldPassword, newPassword);

        logger.info("Exit changepassword()");
        return status;
    }

    /**

     * This Method has the business logic for forgot password.
     * @param request HttpServletRequest
     * @return int
     * It is used for update the password with new password and returns the status.
     
     */
     
    public int forgotPassword(HttpServletRequest request) {
        logger.info("Entered forgotPassword()");
        String userName = request.getParameter("userName");
        String newPassword = "";
        String encryptedNewPassword = "";
        SendMail sendMail = new SendMail();
        boolean statusMail = false;
        try {
            PasswordGenerator passwordGen = new PasswordGenerator();
            newPassword = passwordGen.getNext();
            logger.info("newPassword      ::::" + newPassword);
            TripleDES tripleDES = new TripleDES();
            encryptedNewPassword = tripleDES.encrypt(newPassword);
            logger.info("encryptedNewPassword  ::::" + encryptedNewPassword);
        } catch (Exception e) {
            logger.severe("Exception :" + e.getMessage());
        }
        int status = 
            aascUserControlDAO.forgotPassword(userName, encryptedNewPassword);
        String url = request.getScheme()+ "://"+request.getServerName()+":"+ request.getServerPort()+ request.getContextPath();
        //logger.info("@@@@@@The url is $$$$$$@@@@"+url);
        if (status == 1) {
            statusMail = sendMail.send(userName, "", newPassword, request.getParameter("actiontype"),url);
            if (statusMail == false) {
                status = 20;
            }
        }

        request.setAttribute("userName", userName);

        logger.info("Exit forgotPassword()");
        return status;
    }

}
