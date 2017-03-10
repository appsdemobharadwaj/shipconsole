<%@include file ="aascHeader.jsp" %>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxIntlBrokerDetail"); %>
<%
/*
  DESCRIPTION                                                              
    JSP to get International Header detail from data base
    Author Y Pradeep 28-JAN-2015
    Version - 1                                                            
    History  
*/
%>
<%
              
     String currentBroker = request.getParameter("currentBroker");
     logger.info("currentBroker"+currentBroker);
     int locationId = Integer.parseInt(request.getParameter("locationId"));
     int clientId = (Integer)session.getAttribute("client_id");
     
     String brokerDetail = null;
     AascOracleIntlShipmentsDAO aascOracleIntlShipmentsDAO = new AascOracleIntlShipmentsDAO();
     brokerDetail = aascOracleIntlShipmentsDAO.getIntlBrokerDetail(currentBroker,clientId, locationId);
     
     logger.info("brokerDetail"+brokerDetail);
     if(brokerDetail.equals(""))
    {
    out.write("**"); 
    }
    else
    {
    out.write(brokerDetail);
    }
%>
