<%
/*===========================================================================================================================================================+
|  DESCRIPTION                                                                                                                                               |
|    JSP to get the Shipmethods based on carrier selected for a given client                                                                                                        |
|    Author Eshwari M                                                                                                                                        |
|    Version   1.1                                                                                                                                           |     
|    Creation  28/11/2014                                                                                                                                    | 
|                                                                                                                                                            | 
|  HISTORY                                                                                                                                                   |
|   30/01/2015   Eshwari M      Formatted the code and ran code audit                                                                                        |
+============================================================================================================================================================*/
%>

<%@page import="com.aasc.erp.model.*"%>
<%@page import="com.aasc.erp.util.*"%>
<%@include file ="aascHeader.jsp" %>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxShipMethodInfo"); %>
<%
/*
Integer clientId = (Integer)session.getAttribute("clientId"); 
String carrierIdStr=(String)request.getParameter("carrierType");
*/
logger.info("Inside aascAjaxShipMethodInfo.jsp");
try
{
    int roleId = (Integer)session.getAttribute("role_id"); 
    logger.info("roleId : "+roleId);
    int clientId=0;
    if(roleId == 2)
    {
        String clientIdStr = request.getParameter("clientId");
        try
        {
           clientId = Integer.parseInt(clientIdStr);
        }
        catch(Exception e)
        {
           //e.printStackTrace();
           clientId =0;
        }
    }
    else
    {
       clientId = (Integer)session.getAttribute("client_id"); 
    }
    String carrierIdStr=(String)request.getParameter("carrierType");
    int carrierId;         
    carrierId=Integer.parseInt(carrierIdStr);
    AascOracleGetShipMethodInfo aascOracleGetShipMethodInfo= new AascOracleGetShipMethodInfo();
    String shipMethods =aascOracleGetShipMethodInfo.getShipMethodInfo(carrierId, clientId);
    //logger.info("shipMethods options ::"+shipMethods);
    if(shipMethods.equals(""))
    {
       out.write("**"); 
    }
    else
    {
        out.write(shipMethods);
    }
    }catch(Exception e)
    {
       logger.info("Exception occurred : "+e.getMessage());
    }

%>
