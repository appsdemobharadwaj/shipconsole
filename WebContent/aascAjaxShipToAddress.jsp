<%
/*
$Header: /u01/cvs/Products/AA_SAAS_MODULES/ShipConsole_Lyte/Ship/public_html/aascAjaxShipToAddress.jsp,v 1.4 2015/05/11 07:30:23 pyammanuru Exp $

  DESCRIPTION                                                              
    JSP to get Ship to Adress  Values from data base
    Author Eshwari  21/11/2014
    Version - 1                                                            
    History    
    21/11/2014   Eshwari    Added this file from cloud 1.2 version
    02/01/2015   Y Pradeep  Modified code to get Ship To Location details in Shipment page.
*/
%>
<%@page import="com.aasc.erp.model.*"%>
<%@ include file="aascHeader.jsp"%>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxShipToAddress"); %>

<%
  String shipToLocation="";
  String shipToAddress="@@@";
  String customerName="";
  int  clientId=-1;
  try{
        AascOracleShippingAddr aascOraclecShippingAddr=new AascOracleShippingAddr();
           
        customerName = request.getParameter("customer");
        shipToLocation = request.getParameter("shipToLocation");
        // customerName=customerName.trim();commented to get customername as entered in UI
        // ShipToAddr1=ShipToAddr1.trim(); commented to get Ship to address as entered in UI
        clientId=Integer.parseInt(request.getParameter("clientIdHid"));
        shipToAddress = shipToAddress + aascOraclecShippingAddr.getAllShipToDetails(shipToLocation,customerName,clientId);
        logger.info("shipToAddress == "+shipToAddress);
  }
  catch(Exception e)
  {
        logger.severe("Got exception in getting shipToAddress"+e.getMessage());
        e.printStackTrace();
  }
        out.write(shipToAddress);
%>
