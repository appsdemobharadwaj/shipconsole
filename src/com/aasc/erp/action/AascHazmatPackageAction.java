package com.aasc.erp.action;

import com.aasc.erp.bean.AascHazmatPackageInfo;
import com.aasc.erp.bean.AascIntlDataBean;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.delegate.AascHazmatDelegate;
import com.aasc.erp.model.AascDAOFactory;
import com.aasc.erp.model.AascHazmatPackageDAO;
import com.aasc.erp.util.AascLogger;
import com.aasc.erp.util.Formatter;

import com.opensymphony.xwork2.Action;

import com.opensymphony.xwork2.ActionSupport;

import java.io.IOException;

 import java.util.LinkedList;
 import java.util.logging.Logger;

 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;

import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


/**
 * AascHazmatPackageAcion extends Action. <br>
 * @author      <br>
 * @version 2 <br>
 * @since
 * */
 public class AascHazmatPackageAction extends ActionSupport  implements  ServletRequestAware, ServletResponseAware  {

    static Logger logger = AascLogger.getLogger(" AascHazmatPackageAction "); // logger object

 protected HttpServletRequest request;
 protected HttpServletResponse response;
     
     AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1); // object of AascDAOFactory
      int opStatus = 0;

      int pkgSequenceId;
    
    
    AascHazmatPackageInfo aascHazmatPackageInfo = null;

     public String execute(){

         HttpSession session = request.getSession(); // getting the Session object
          logger.info("Entered execute method");
             if(session.isNew() || 
                     !(session.getId().equals(session.getAttribute("SessionId")))){
                 logger.info("in Session "+session.isNew());
                 return "errorSession";
             }
        String action = request.getParameter("actionType").trim();
         logger.info("action::"+action);
        
         String pkgSeqStr = request.getParameter("packCount");
         System.out.println("pkgSeqStr::"+pkgSeqStr);
         int pkgSequenceId = Integer.parseInt(pkgSeqStr);
         System.out.println("pkgSequenceId:::"+pkgSequenceId);
         String orderNumber = request.getParameter("orderNumber1");
         System.out.println("orderNumber::"+orderNumber);


         AascHazmatDelegate aascHazmatDelegate=new AascHazmatDelegate(); 
         
             if ("SAVE".equalsIgnoreCase(action)) {
                 logger.info("actionType is SAVE");
                 String status=aascHazmatDelegate.saveAction(request,session);
                 request.setAttribute("orderNum",orderNumber);
                     request.setAttribute("packCount",pkgSequenceId);

                 if("error".equalsIgnoreCase(status)){
                     return "error";
                 }else if("success".equalsIgnoreCase(status)){
                     return "success";
                 }
             }
             if ("DELETE".equalsIgnoreCase(action)) {
                 logger.info("actionType is DELETE");
                 String status1=aascHazmatDelegate.deleteAction(request,session);
                 request.setAttribute("orderNum",orderNumber);
                     request.setAttribute("packCount",pkgSequenceId);
//                                 request.setAttribute("commodityValue",commodityValue);

                 if("error".equalsIgnoreCase(status1)){
                     return "error";
                 }else if("success".equalsIgnoreCase(status1)){
                     return "success";
                 }
             }
             if ("VIEWPRINT".equalsIgnoreCase(action)) {
                 logger.info("Entered into action VIEWPRINT");
                 return "viewPrint";
             }
             if ("ADD".equalsIgnoreCase(action)) {
                 logger.info("actionType is ADD");
                 String status2=aascHazmatDelegate.addAction(request,session);
                 request.setAttribute("orderNum",orderNumber);
                     request.setAttribute("packCount",pkgSequenceId);
//                     request.setAttribute("orderNum",orderNumber);
                 if("error".equalsIgnoreCase(status2)){
                     return "error";

                 }else if("success".equalsIgnoreCase(status2)){
                             return "success";
                         }
//                     setAascShipmentPackageInfo(aascShipmentPackageInfo);
                 }
 return "success";
         }
    public HttpServletRequest getServletRequest() {
    return request;
    }
    public void setServletRequest(HttpServletRequest req){
    this.request = req;
    }
    
    public HttpServletResponse getServletResponse() {
    return response;
    }
    
    public void setServletResponse(HttpServletResponse resp) {
    this.response = resp;
    }
  
 }

