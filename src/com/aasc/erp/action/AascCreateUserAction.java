package com.aasc.erp.action;

/*========================================================================+
@(#)AascCreateUserAction.java 05/08/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
* 
+===========================================================================*/


import com.aasc.erp.bean.AascUserBean;
import com.aasc.erp.delegate.AascCreateUserDelegate;
import com.aasc.erp.delegate.AascIndexRequestDelegate;


import com.aasc.erp.util.AascLogger;

import com.aasc.erp.util.PasswordGenerator;
import com.aasc.erp.util.SendMail;
import com.aasc.erp.util.TripleDES;

import java.util.logging.Logger;

import com.opensymphony.xwork2.ModelDriven;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


/**
 * AascCreateUserAction extends ActionSupport.
 * @author      N Srisha
 * @version 1.0
 * @since
 * 
 * History:
 * 
 * 
 * 17/12/2014  Eshwari M    Merged Sunanda code for SC Lite
 * 18/12/2014  Sunanda K    Removed SOPs and added logger statements, added java doc.
 * 07/01/2015  Y Pradeep    Merged Sunanda's Code : Removed SOP's and added comments, java doc.
 * 15/01/2015  Y Pradeep    Merged Sunanda's code on 1.0 release bugs.
 * 20/01/2015  K Sunanda    Removed unused variables and unnecessary loggers.
 * 20/01/2015  Y Pradeep    Removed unused code and printStackTrace.
 * 21/01/2015  Sunanda K    Modified code for bug fix #2558
 * 06/02/2015  Suman g      Passing actiontype as a parameter to sendMail() method for showing proper message in Email.
 * 10/03/2015  Sunanda K    added code to send Url along with username and password through mail 
 * 16/03/2015  Eshwari M    Added editUserProfile if block for EditProfile feature
 * 16/03/2015  Suman G      Added code for sending mail to Alternate Email Address.
 * 17/03/2015  Suman G      Modified if condition for fixing the issue which mail not sent to Alternate mail id while creating customer.
 * 24/03/2015  Suman G      Called editUserProfile() method which is in delegate class from editUserProfile if block to fix #2726.
 * 30/03/2015  Sunanda K    Modified code for bug fix #2748
 * 31/03/2015  Suman G      Added code to fix #2754
 * 13/04/2015  Suman G      Modified code to fix #2812.
 * 21/04/2015  Suman G      Added code to set user bean in session to fix #2754. 
 * 10/06/2015  Suman G      Added code to fix #2962 
 * 17/06/2015  Suman G             Added code to fix #2986
 * 04/12/2015  Suman G      Added code for resending the password functionality to user.
 **/
public class AascCreateUserAction implements ServletRequestAware, ServletResponseAware, 
                                ModelDriven {

    static Logger logger = 
        AascLogger.getLogger(" AascCreateUserAction "); // logger object
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    String actiontype = "";
    AascUserBean aascUserBean = new AascUserBean();
    AascIndexRequestDelegate aascIndexRequestDelegate = new AascIndexRequestDelegate();

    public HttpServletRequest getServletRequest() {
        return request;
    }

    public void setServletRequest(HttpServletRequest req) {
        this.request = req;
    }

    public HttpServletResponse getServletResponse() {
        return response;
    }

    public void setServletResponse(HttpServletResponse resp) {
        this.response = resp;
    }

    /**

     * This Method used to perform the action for user creation.

     * @return  String

     */
    public String execute() {

        actiontype = request.getParameter("actiontype");
        logger.info("actiontype.............." + actiontype);
        aascUserBean.setActionType(actiontype);
        AascCreateUserDelegate aascCreateUserDelegate = 
            new AascCreateUserDelegate();

        HttpSession session = request.getSession();
        if(session.isNew() || 
                !(session.getId().equals(session.getAttribute("SessionId")))){
            logger.info("in Session "+session.isNew());
            return "sessionError";
        }
        aascUserBean = 
                aascCreateUserDelegate.commonAction(request, aascUserBean, 
                                                    session);
        int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
        
      
      
        if (actiontype.equalsIgnoreCase("Create")) {
            logger.info("actiontype is CREATE");
            
            if(roleIdSession != 3){
                return "sessionError";
            }
            
            LinkedList userList1 = new LinkedList();

            session.setAttribute("aascUserBean", (Object)userList1);

                aascIndexRequestDelegate.getLocationDetails(session);
          aascIndexRequestDelegate.getUserDetails(session);
            return "Create";
        } else if (actiontype.equalsIgnoreCase("Cancel")) {
            logger.info("actiontype is CANCEL");

            if(roleIdSession != 3){
                return "sessionError";
            }
            
                aascIndexRequestDelegate.getLocationDetails(session);
            String message = aascIndexRequestDelegate.getUserDetails(session);
            if ("success".equalsIgnoreCase(message)) {
                return "Cancel";
            }
            return "Cancel";
        } else if (actiontype.equalsIgnoreCase("Edit")) {
            logger.info("actiontype is Edit");
            
            if(roleIdSession != 3){
                return "sessionError";
            }
            
            return "Edit";
        }

        else if (actiontype.equalsIgnoreCase("editUserProfile")) {
            logger.info("actiontype is EditUserProfile");
            
            int status = aascCreateUserDelegate.editUserProfile(aascUserBean);
            if(status==1){
                session.setAttribute("aascUserBean", aascUserBean);
                session.setAttribute("firstName",aascUserBean.getFirstName());
                session.setAttribute("lastName",aascUserBean.getLastName());
                request.setAttribute("key","aasc412");
            }
            else{
                request.setAttribute("key","aasc413");
            }
            return "EditProfile";
        }

        else if (actiontype.equalsIgnoreCase("AddNewUser")) {
            logger.info("actiontype is AddNewUser");
            
            if(roleIdSession != 3){
                return "sessionError";
            }
            
            PasswordGenerator passwordGenerator=new PasswordGenerator();
            String passw=passwordGenerator.getPassword();
            String encryptedPassword="";
            try {
                logger.info("newPassword      ::::" + passw);
                TripleDES tripleDES = new TripleDES();
                encryptedPassword = tripleDES.encrypt(passw);
                logger.info("encryptedNewPassword  ::::" + encryptedPassword);
            
            } catch (Exception e) {
                logger.severe("Exception::"+e.getMessage());
            }
            int userIdUser = (Integer)session.getAttribute("user_id");
            aascUserBean.setCreatedByUser(userIdUser);
            aascUserBean.setPassword(encryptedPassword);
            String statusString = aascCreateUserDelegate.addOrUpdateUser(aascUserBean);
            int status = Integer.parseInt(statusString);
            String url = request.getScheme()+ "://"+request.getServerName()+":"+ request.getServerPort()+ request.getContextPath();
            //logger.info("@@@@@@The url is $$$$$$@@@@"+url);
            if (status == 1) {
                SendMail sendMail=new SendMail();
                String EmailId=aascUserBean.getEmailAddress();
                boolean sentResponse=sendMail.send(EmailId,"", passw,request.getParameter("actiontype"),url);
                String alternateId=aascUserBean.getAlternateEmailAddress();
                logger.info("alternate mail id:::"+alternateId+"::");
                if(!"".equalsIgnoreCase(alternateId)){
                    
                   sendMail.send(EmailId,alternateId, passw,request.getParameter("actiontype"),url);
                }
                if(sentResponse == true){ 
                    request.setAttribute("key", "aasc410");

                    try {
                        LinkedList userList = new LinkedList();
                        userList = aascCreateUserDelegate.getUserDetails();
                        session.setAttribute("userDetailsList", (Object)userList);
                    } catch (Exception e) {
                        logger.severe("Exception::"+e.getMessage());
                    }
                }        
            }
            else if (status == 2)
                    request.setAttribute("key", "aasc412");
            else if (status == 10) {
                request.setAttribute("key", "aasc41010");
            } else {
                request.setAttribute("key", "aasc411");
            }
            session.setAttribute("aascUserBean", aascUserBean);//Sunanda's fix for bug #2558
            return "Create";
        } else if (actiontype.equalsIgnoreCase("editUserDetails")) {
            logger.info("actiontype is Edit editUserDetails");
            
            if(roleIdSession != 3){
                return "sessionError";
            }
                
            String passw = "";
            int status = 0;
            logger.info("addOrUpdateUser userban values user Id"+aascUserBean.getUserID());
            String statusString = aascCreateUserDelegate.addOrUpdateUser(aascUserBean);
            try{
                int index = statusString.indexOf("$$$");
                if(index != -1){
                    passw = statusString.substring(index+3);
                    status = Integer.parseInt(statusString.substring(0,1));
                }
                else{
                    status = Integer.parseInt(statusString);
                }
            }catch(Exception e){
                logger.severe("Exception ::::"+e.getMessage());
            }
            if (status == 2 || status == 4) {

                if(status == 4){
            
                    String url = request.getScheme()+ "://"+request.getServerName()+":"+ request.getServerPort()+ request.getContextPath();
                    logger.info("password:::::"+passw);
                    logger.info("url:::::"+url);
                    SendMail sendMail=new SendMail();
                    String EmailId=aascUserBean.getEmailAddress();
                    boolean sentResponse=sendMail.send(EmailId,"", passw,request.getParameter("actiontype"),url);
                    String alternateId=aascUserBean.getAlternateEmailAddress();
                    logger.info("alternate mail id::::"+alternateId);
                    if(alternateId!=null){
                        sendMail.send(EmailId,alternateId, passw,request.getParameter("actiontype"),url);
                    }
                    if(sentResponse == true){ 
                request.setAttribute("key", "aasc412");
                try {
                    LinkedList userList = new LinkedList();
                    userList = aascCreateUserDelegate.getUserDetails();
                    session.setAttribute("userDetailsList", (Object)userList);
                } catch (Exception e) {
                    logger.severe("Exception::"+e.getMessage());

                  }
                }
              }
                    if(status == 2){
              request.setAttribute("key","aasc412");
               try{
                   LinkedList userList=new LinkedList();
                   userList = aascCreateUserDelegate.getUserDetails();
                   session.setAttribute("userDetailsList", (Object)userList);
                        } catch (Exception e) {
                 logger.severe("Exception::"+e.getMessage());
                        
                        }
               }
            } else if(status == 10) {
             request.setAttribute("key","aasc41010");
            } else {
            request.setAttribute("key","aasc413");
            }
            session.setAttribute("aascUserBean",aascUserBean);
            return "Edit";
        }else if (actiontype.equalsIgnoreCase("resendPassword")) {
            logger.info("actiontype is resendPassword");
            
            if(roleIdSession != 3){
                return "sessionError";
            }
            
            aascCreateUserDelegate.resendPassword(aascUserBean, request);
            
            session.setAttribute("aascUserBean",aascUserBean);
            
            return "Edit";
        }
        

        return "success";
    }

    /**
     * getModel() this is a struts2 implementation method for initializing the form bean variables.
     * @return    Object  Returns the bean object of the form.
     */
    public Object getModel() {
        return aascUserBean;
    }
}
