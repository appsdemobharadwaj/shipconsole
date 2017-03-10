<%@page import="java.io.*" %>
<%@page import="java.text.*" %>
<%@page import="java.sql.*"%>
<%@page import="java.util.logging.*"%>
<%@page import="java.util.*" %>
<%@page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@page import="com.aasc.erp.util.*"%>
<%@page import="oracle.jdbc.driver.*"%>
<%@page import="com.aasc.erp.carrier.*" %>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxClientValidation"); %>
<%
/*

  DESCRIPTION                                                              
    The client validation for tolerance, expiry check and balance check is made through this jsp
    Author Jagadish  12/08/2015                                 
    Version - 1                                                            
    History             
    
    12/08/2015      Jagadish         Wrote the complete initial code.
    24/02/2016      Suman G          Changes done for new Transaction Management design.
*/
%>

<%

logger.info("Entered aascAjaxClientValidation");
     Integer clientIdInt=0;
     Integer pkgCount=0;
     String toMailId="";
//     String balance_alert_flag="";
//    String tolerance_email_flag="";
    String balance_email_flag="N";
    Integer op_status = 0;
     try
     {
      
      clientIdInt=(Integer)session.getAttribute("client_id");
     String clientId = ""+clientIdInt;
     pkgCount=Integer.parseInt((String)request.getParameter("pkg_count"));
     toMailId=(String)request.getParameter("toMailId");
      
     }
     catch(Exception e)
     {
       clientIdInt=(Integer)session.getAttribute("client_id");
       logger.info("clientIdInt"+clientIdInt);
       e.printStackTrace();
     }
     
     try{
     int queryTimeOut = Integer.parseInt((String)session.getAttribute("queryTimeOut"));
     
     logger.info("aascAjaxClientValidation here"+"clientIdInt"+clientIdInt+": pkgCount"+pkgCount);
     
     AascOracleClientValidationDAO aascOracleClientValidationDAO = new AascOracleClientValidationDAO();
     HashMap clientValidationHashMap=aascOracleClientValidationDAO.clientValidation(clientIdInt,pkgCount);
     CustomerValidationEmails customerValidationEmails=new CustomerValidationEmails();
    op_status=(Integer)clientValidationHashMap.get("status");
    if( op_status == 100 || op_status == 101)
    {
    
//                    balance_alert_flag= (String)clientValidationHashMap.get("balance_alert_flag");
//                    tolerance_email_flag=(String) clientValidationHashMap.get("tolerance_email_flag");
                    balance_email_flag=(String)clientValidationHashMap.get("balance_email_flag");
                    
//                    if(tolerance_email_flag.equalsIgnoreCase("y")){
//                    try{
//                    customerValidationEmails.send(toMailId,"tolerance_email_flag");
//                    }
//                    catch(Exception e){
//                    logger.info("Error sending tolerance email");
//                    }
//                    
//                    }
                    
                    if(balance_email_flag.equalsIgnoreCase("y")){
                    try{
                    customerValidationEmails.send(toMailId,"balance_email_flag");
                    }
                    catch(Exception e){
                    logger.info("Error sending subscription expiry email");
                    }
                    
                    }
    
        }
        
     }
     catch(Exception e){
     e.printStackTrace();
     
     }
      
     String option =balance_email_flag+"*"+op_status+"$";
     logger.info("options in aascAjaxClientValidation.jsp ::"+option+":: options");
     out.write(option);
  
  %>
  
  
