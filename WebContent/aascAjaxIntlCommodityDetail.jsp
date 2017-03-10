<%@include file ="aascHeader.jsp" %>
<%@ page import="java.net.URLDecoder"%>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxIntlCommodityDetail"); %>
<%
/*
  DESCRIPTION                                                              
    JSP to get International Commodity Item detail from data base
    Author Y Pradeep 28-JAN-2015
    Version - 1                                                            
    History  
*/
%>
<%
     
    String currentItem = request.getParameter("currentItem");
    currentItem=currentItem.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
    
    int locationId = Integer.parseInt(request.getParameter("locationId"));
    int clientId = (Integer)session.getAttribute("client_id");
    
    logger.info("currentItem:"+currentItem);

    try {
        currentItem=URLDecoder.decode(currentItem,"UTF-8");
    } catch(Exception e){
        logger.info("inside catch block in aascAjaxIntlCommodityDetail e = "+e.getMessage());
    }
    logger.info("currentItem after decoding:"+currentItem);
    
    String itemDetail = null;
    AascOracleIntlShipmentsDAO aascOracleIntlShipmentsDAO = new AascOracleIntlShipmentsDAO();
    itemDetail = aascOracleIntlShipmentsDAO.getIntlCommodityItemDetail(currentItem, clientId, locationId);
     
    logger.info("itemDetail"+itemDetail);
    if(itemDetail.equals("")) {
        out.write("**"); 
    } else {
        out.write(itemDetail);
    }
%>
