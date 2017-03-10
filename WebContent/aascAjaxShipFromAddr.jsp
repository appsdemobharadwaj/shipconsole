<%
/*


  DESCRIPTION                                                              
    JSP to get Ship to Adress line 1  Values from data base
    Author Suman G                                
    Version - 1                                                            
    History   
    21/01/2015  Suman G     Removed unnecessary comments.
	03/06/2015  Y Pradeep   Changed locationName to locationId to get details based on locationId.
*/
%>
<%@page import="com.aasc.erp.model.*"%>
<%@ include file="aascHeader.jsp"%>
<%@ page import="java.net.URLDecoder"%>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxShipFromAddr"); %>
<%
   String shipFromAddress="";
   int locationId = 0;
   int  clientID=-1;  
   try{
            AascOracleShippingAddr aascOracleShippingAddr = new AascOracleShippingAddr();
            
            locationId = Integer.parseInt(request.getParameter("locationId"));   
            
            clientID=Integer.parseInt(request.getParameter("clientIdHid"));
            
            shipFromAddress=  aascOracleShippingAddr.getShipFromAddressline1(locationId,clientID);
            
   }
   catch(Exception e)
   {
            logger.severe("Got exception in getting shipToAddress"+e.getMessage());
           
   }
   logger.info("shipFromAddress ==  "+shipFromAddress);
   out.write(shipFromAddress);

%>
