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
  * ReportsAction class extends ActionSupport class and implements ModelDriven, ServletRequestAware, ServletResponseAware.
  * This class gets the requestType from the reports jsp and forwards to respective jsps.
  * @version   1.1
  * @author    Eshwari M
  History
  *
  * 30/01/2015   Eshwari M   Formatted code, added documentation and ran code audit
  * 17/06/2015  Suman G             Added code to fix #2986
  * */
  
public class ReportsAction extends ActionSupport implements ModelDriven, 
                                               ServletRequestAware, 
                                               ServletResponseAware {
    
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

    private static Logger logger = AascLogger.getLogger("ReportsAction");

    /**
     This is the main action called from the Struts framework.
     @return returns the string object to forward the control back to the respective jsp page
     */
    public String execute() {
        logger.info("Inside ReportsAction ") ;
        String requestType = request.getParameter("requestType");
        logger.info("requestType::" + requestType);
        HttpSession session = request.getSession(true);

        if (session.isNew() || 
            !(session.getId().equals(session.getAttribute("SessionId")))) {
            logger.info("Inside error");
            return "error"; 
        }
        int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
        if(roleIdSession != 2 && roleIdSession != 5 && roleIdSession != 4){
         return "error";
        }
        if ("CarrierSLAReport".equalsIgnoreCase(requestType)) {
            logger.info("Inside carrier SLA");
            session.removeAttribute("requestId");
            return "CarrierSLAReport";
        }

        if ("CarrierShipActivity".equalsIgnoreCase(requestType)) {
            logger.info("Inside carrier activity");
            session.removeAttribute("requestId");
            return "CarrierShipActivity";
        }

        return "success";
    }

    public Object getModel() {
        return null;
    }


}
