package com.aasc.erp.action;

import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

 
 /**
  * ReportsInfoAction class extends ActionSupport class and implements ModelDriven, ServletRequestAware, ServletResponseAware.
  * This class gets the requestType from the reports jsp and forwards to respective jsps.
  * @version   1.1
  * @author    Eshwari M
  History
  *
  * 30/01/2015   Eshwari M   Formatted code, added documentation and ran code audit
  * */

public class ReportsInfoAction extends ActionSupport implements ModelDriven, 
                                                   ServletRequestAware, 
                                                   ServletResponseAware {
    
    private String actionType;
    private static Logger logger = AascLogger.getLogger("ReportsInfoAction");
    
    protected HttpServletRequest request;
    protected HttpServletResponse response;

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
     This is the main action called from the Struts framework.
     @return returns the string object to forward the control back to the respective jsp page
     */ 
    public String execute() {

        try {
            logger.info("InSide  ReportsInfoAction");
            HttpSession session = 
                request.getSession(); // creating the object of the HttpSession class

            if (session.isNew() || 
                !(session.getId().equals(session.getAttribute("SessionId")))) {
                return "error"; 
            }


            String userIdstr = 
                (String)session.getAttribute("user_id"); // getting UserId from session

            logger.info("userIdstr = " + userIdstr);
            int userId;
            if (userIdstr != null) {
                userId = Integer.parseInt(userIdstr);
            } else {
                return "error";
            }
            
            //added for roleId
            int roleId = 
                ((Integer)session.getAttribute("role_id")).intValue(); // string orgId                     
            logger.info("roleId is :" + roleId);

            actionType = 
                    request.getParameter("actionType"); // getting actionType from jsp
            logger.info("actionType = " + actionType);


        } // end of try block 
        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }

        return "success";
    }

    public Object getModel() {
        return null;
    }


}
