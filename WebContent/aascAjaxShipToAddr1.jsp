<%
/*

  DESCRIPTION                                                              
    JSP to get Ship to Adress line 1  Values from data base
    Author Gayaz Ahamed  26/07/2007                                 
    Version - 1                                                            
    History   
    02/01/2015  Y Pradeep   Modified code to get Ship To Location names in Shipment page.
*/
%>
<%@page import="com.aasc.erp.model.*"%>
<%@ include file="aascHeader.jsp"%>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxShipToAddr1"); %>
<%

 String shipToAddress1="@@@";
 String customerName="";
 int  clientID=-1;
     try{
            AascOracleShippingAddr aascOracleShippingAddr=new AascOracleShippingAddr();
            
            customerName=request.getParameter("customerName");
            clientID=Integer.parseInt(request.getParameter("clientIdHid"));
            shipToAddress1 = shipToAddress1 + aascOracleShippingAddr.getCustLocationName(customerName,clientID);
            logger.info("shipToAddress1 : "+shipToAddress1);
            }
            catch(Exception e)
            {
            logger.severe("Got exception in getting ajax values"+e.getMessage());
            e.printStackTrace();
            }
            out.write(shipToAddress1);
%>
