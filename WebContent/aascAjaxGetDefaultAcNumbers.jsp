<%
/*

  DESCRIPTION                                                              
    JSP to get default TP and FC from data base
    Author Suman G 11/02/2015                                
    Version - 1                                                            
    History  

*/
%>
<%@page import="com.aasc.erp.model.*"%>
<%@page import="com.aasc.erp.bean.*"%>
<%@page import="com.aasc.erp.util.*"%>
<%@page import="java.util.logging.*"%>


<%! private static Logger logger=AascLogger.getLogger("aascAjaxRetrieveCarrierCodeServiceLevel"); %>
<%
    String retrieveCcCsl = "@@@" ;
    
    String custLocationId = (String)request.getParameter("custLocationId");
    int clientId=Integer.parseInt(request.getParameter("clientId"));
    String locationSelected = (String)request.getParameter("locationSelected");
    int carrierCode = Integer.parseInt(request.getParameter("carrierCode"));

    
    AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);
    AascShipMethodAjaxDAO shipMethodDB=aascDAOFactory.getAascShipMethodAjaxDAO(); 
    retrieveCcCsl = shipMethodDB.retrieveDefaultAcNo(clientId,custLocationId,locationSelected,carrierCode);
    
    logger.info("CarrierCode CarrierServiceLevel options ::"+retrieveCcCsl);
    if(retrieveCcCsl.equals("-1"))
    {
        out.write("**");
    }
    else
    {
         out.write(retrieveCcCsl);
    }
%>

