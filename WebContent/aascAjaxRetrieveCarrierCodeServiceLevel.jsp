<%
/*
$Header: /u01/cvs/Products/AA_SAAS_MODULES/ShipConsole_Lyte/Ship/public_html/aascAjaxRetrieveCarrierCodeServiceLevel.jsp,v 1.2 2015/04/28 09:58:38 vmalluru Exp $

  DESCRIPTION                                                              
    JSP to get Carrier Code , Carrier Service Level (Connect Ship Tag) from data base
    Author Eshwari 18/11/2014                               
    Version - 1                                                            
    History  
    18/11/14    Eshwari    Added this file from Cloud 1.2 version
    24/04/15    Eshwari    Added try catch to handle exception
*/
%>
<%@page import="com.aasc.erp.model.*"%>
<%@page import="com.aasc.erp.bean.*"%>
<%@page import="com.aasc.erp.util.*"%>
<%@page import="java.util.logging.*"%>


<%! private static Logger logger=AascLogger.getLogger("aascAjaxRetrieveCarrierCodeServiceLevel"); %>
<%
    String retrieveCcCsl = "@@@" ;
    try{
    String shipmethod=(String)request.getParameter("shipMethod");
    String locationIDTemp = (String)request.getParameter("locationIDTemp");
    int carrierId=Integer.parseInt(request.getParameter("carrierId"));
    int locationID = Integer.parseInt(locationIDTemp);
    int clientId =(Integer)session.getAttribute("client_id");
    AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);
    AascShipMethodAjaxDAO shipMethodDB=aascDAOFactory.getAascShipMethodAjaxDAO(); 
    retrieveCcCsl = shipMethodDB.retrieveCcCsl(shipmethod,locationID,carrierId,clientId);
    //out.println(Retrieve_cc_csl);
    logger.info("CarrierCode CarrierServiceLevel options ::"+retrieveCcCsl);
    if(retrieveCcCsl.equals("-1"))
    {
        out.write("**");
    }
    else
    {
         /*AascDeliveryInfo deliveryInfo=  ((AascDeliveryInfo)session.getAttribute("DeliveryInfo"));
         if(deliveryInfo != null){
         AascShipmentHeaderInfo headerInfo = deliveryInfo.getHeaderInfo();*/
         
         AascShipmentOrderInfo orderInfo = (AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo") ;
         if(orderInfo != null){
             AascShipmentHeaderInfo headerInfo = orderInfo.getShipmentHeaderInfo();
             try{
                 String carrierServiceLevel=retrieveCcCsl.substring(retrieveCcCsl.indexOf("@")+1,retrieveCcCsl.lastIndexOf("@"));
                 logger.info("carrierServiceLevel : "+carrierServiceLevel);
                 headerInfo.setCarrierServiceLevel(carrierServiceLevel);  
             }catch(Exception e){
              logger.info("Exception in aascAjaxRetrieveCarrierCodeServiceLevel.jsp file::"+e.getMessage());
             }
         }
         out.write(retrieveCcCsl);
    }
    }catch(Exception e)
    {
       e.printStackTrace() ;
    }
%>

