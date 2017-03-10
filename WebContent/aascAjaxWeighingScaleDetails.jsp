<%
/*


  DESCRIPTION                                                              
    JSP to get Weighing Scale details Values from data base
    Author Y Pradeep
    Version - 1                                                            
    History   
	03/03/2016  Y Pradeep   Added this file to load Weighing Scale details.
*/
%>
<%@page import="com.aasc.erp.model.*"%>
<%@ include file="aascHeader.jsp"%>
<%@ page import="java.net.URLDecoder"%>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxWeighingScaleDetails"); %>
<%
   String weighingScaleDetails="";
   int locationId = 0;
   int  clientID=-1;  
   try{
            AascOracleShippingAddr aascOracleShippingAddr = new AascOracleShippingAddr();
            
            locationId = Integer.parseInt(request.getParameter("locationId"));   
            
            clientID=Integer.parseInt(request.getParameter("clientIdHid"));
            
            weighingScaleDetails=  aascOracleShippingAddr.getWeighingScaleDetails(locationId,clientID);
            
   }
   catch(Exception e)
   {
            logger.severe("Got exception in getting weighing scale details "+e.getMessage());
           
   }
   logger.info("weighingScaleDetails ==  "+weighingScaleDetails);
   out.write(weighingScaleDetails);

%>
