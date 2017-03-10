<%@include file ="aascHeader.jsp" %>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxIntlImporterDetail"); %>
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
     
     String currentImporter = request.getParameter("currentImporter");
     logger.info("currentImporter"+currentImporter);
     int locationId = Integer.parseInt(request.getParameter("locationId"));
    int clientId = (Integer)session.getAttribute("client_id");
     logger.info("locationId"+locationId+" clientId = "+clientId);
     String importerDetail = null;
     AascOracleIntlShipmentsDAO aascOracleIntlShipmentsDAO = new AascOracleIntlShipmentsDAO();
     importerDetail = aascOracleIntlShipmentsDAO.getIntlImporterDetail(currentImporter, clientId, locationId);
     
     logger.info("importerDetail"+importerDetail);
     if(importerDetail.equals(""))
    {
    out.write("**"); 
    }
    else
    {
    out.write(importerDetail);
    }
%>
