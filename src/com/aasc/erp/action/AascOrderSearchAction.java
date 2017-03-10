 /*

  * @(#)AascOrderSearchAction.java        21/01/2015

  ** Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.

  * All rights reserved.

  */

 package com.aasc.erp.action;

 import com.aasc.erp.bean.AascOrderSearchBean;
 import com.aasc.erp.delegate.AascOrderSearchDelegate;
 import com.aasc.erp.util.AascLogger;

 import com.opensymphony.xwork2.ActionSupport;

 import java.sql.Date;


 import java.util.LinkedList;
 import java.util.logging.Logger;

 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;

 import javax.servlet.http.HttpSession;

 import org.apache.struts2.interceptor.ServletRequestAware;
 import org.apache.struts2.interceptor.ServletResponseAware;

 /**
   AascOrderSearchAction class extends Action class
   This class takes values from Jsp and displays the information.
  */

  /*
    *@author Jagadish Jain 
    *@version 1.1
    * History
    * 27/03/2015  Sunanda K    Modified code for bug fix #2701
    * 17/06/2015  Suman G             Added code to fix #2986
    */



 public class AascOrderSearchAction  extends ActionSupport implements ServletRequestAware, 
                                                           ServletResponseAware { 
                                                           
     private static Logger logger = 
         AascLogger.getLogger("AascOrderSearchAction");
     
     private String fromDate;
     private String toDate;
     private String customerName;
     int queryTimeOutInt;
     private int returnStatus; // holds the value of the returnStatus of the methods
     String key = null;
     
     AascOrderSearchBean aascOrderSearchBean;
     AascOrderSearchDelegate aascOrderSearchDelegate=new AascOrderSearchDelegate();
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
                                                           
     public AascOrderSearchAction() {
     }
     
     /**
      *execute() method gets fromDate,toDate or customer_name from jsp and displays list of order details shipped within the date range.
      @return success
      @throws java.io.IOException If an input or output exception occurred
      @throws javax.servlet.ServletException when a class is not found
      */
      
      
      public String execute(){
        logger.info("Entered execute()");
        
          try {
              
              HttpSession session = 
                  request.getSession(true); // creating the object of the HttpSession class

              if (session.isNew() || 
                  !(session.getId().equals(session.getAttribute("SessionId")))) {
                  return "error"; // added this code for session validation.
                }
               int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
               if(roleIdSession != 2 && roleIdSession != 4){
                   return "error";
               }
                int clientId = 0;
                Integer clientIdInt = null;

                String queryTimeOut = (String)session.getAttribute("queryTimeOut");
                queryTimeOutInt = Integer.parseInt(queryTimeOut);
                   
                clientIdInt = (Integer)session.getAttribute("client_id");
                logger.info("clientIdInt::" + clientIdInt);
                clientId = clientIdInt.intValue() ;

            
                fromDate = request.getParameter("fromDate");
                toDate = request.getParameter("toDate");
                customerName = request.getParameter("customerName");
                
                logger.info("shipment_fromDate"+fromDate+"/t shipment_toDate"+toDate
                                +"/t customer_name"+customerName);
                   
                request.setAttribute("fromDate",fromDate);
                request.setAttribute("toDate",toDate);
                request.setAttribute("customerName",customerName);
                
 //              DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
               java.sql.Date shipmentFromDate=null;
               java.sql.Date shipmentToDate =null;
               if(!("".equals(fromDate) || fromDate == null)){
                shipmentFromDate = Date.valueOf(fromDate);
               }
                if(!("".equals(toDate) || toDate == null)){
                 shipmentToDate = Date.valueOf(toDate) ;
                }
                
                aascOrderSearchBean=
                         aascOrderSearchDelegate.searchOrder(clientId,shipmentFromDate,
                                                                           shipmentToDate,customerName);
                LinkedList searchOrderNumberList = aascOrderSearchBean.getOrderNumberList();

                 session.setAttribute("SearchOrderNumberList",(Object)searchOrderNumberList);
                 returnStatus = aascOrderSearchBean.getStatus();
               //  System.out.println("SearchOrderNumberList:: "+searchOrderNumberList.isEmpty());
                if (returnStatus == 120) {
                    logger.severe("inside If block return status is :" + 
                                  returnStatus);
                    key = "aasc200";
                    request.setAttribute("key", key);
                }

                else if(returnStatus == 121||returnStatus==122) {//Sunanda modified code for bug fix #2701
                    logger.severe("in Else if block return status is :" + 
                                  returnStatus);
                    key = "OrderSearchError";
                    request.setAttribute("key", key);          
                }
                else {
                    logger.severe("in else block return status is :" + 
                                  returnStatus);
                    key = "OrderSearchErrorValues";
                    request.setAttribute("key", key);                
                }
            } // end of try block 
            catch (Exception e) {
              logger.severe("Exception::"+e.getMessage());
            }
     return "success";
     } // end of execute() method
 }
