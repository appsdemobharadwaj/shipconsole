<%
/*===========================================================================================================================================================+
|  DESCRIPTION                                                                                                                                               |
|    JSP ta call the Ajax call for getting and saving the tracking detials from the carrier                                                                                                              |
|    Author Eshwari M                                                                                                                                        |
|    Version   1.1                                                                                                                                           |     
|    Creation  28/11/2014                                                                                                                                    | 
|                                                                                                                                                            | 
|  HISTORY                                                                                                                                                   |
|   30/01/2015   Eshwari M      Formatted the code and ran code audit                                                                                        |
|   17/03/2015   Eshwari M      Modified code to seng label path in the getTrackingDetails() for POD                                                         |
+============================================================================================================================================================*/
%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.carrier.*"%>
<%@ page import="com.aasc.erp.util.AascLogger"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.ParseException"%>
<%! private static Logger logger=AascLogger.getLogger("aascGetTrackingDetails"); %>
<%
   String clientId=request.getParameter("clientId");
   String locationId=request.getParameter("locationId");
   String labelsPath = request.getParameter("labelsPath");
        
   int clientIdInt=0;
   int locationIdInt=0;
   if(!"".equals(clientId) && clientId != null){ 
      clientIdInt=Integer.parseInt(clientId);
   }
   if(!"".equals(locationId) && locationId != null){
    locationIdInt=Integer.parseInt(locationId);
   } 
   logger.info("locationId : "+locationId+"  clientId : "+clientId+"   labelsPath : "+labelsPath);
       
   AascGetTrackingDetails aascGetTrackingDetails = new AascGetTrackingDetails();
   int status = aascGetTrackingDetails.getTrackingDetails(locationIdInt,clientIdInt, labelsPath);
   logger.info("status in aascGetTrackingDetails.jsp file::"+status);
   if(status == 120){
     out.println("success");
   }
   else
   {
     out.println("failure");
   }
%>
