<%@ page contentType="text/html;charset=UTF-8"%>
<%
/*

  DESCRIPTION                                                              
    JSP to get generate order number for shipping when intl popup is opened
    Author Jagadish Jain 17/02/2015                                
    Version - 1                                                            
    History  
    
    07/04/2015  Y Pradeep       Modified if condition to generate order number if ordernumber is empty. Bug #2808 and #2696.
	27/05/2015  Suman G         Added code to fix #2936
*/
%>
<%@page import="com.aasc.erp.model.*"%>
<%@page import="com.aasc.erp.delegate.*"%>
<%@page import="com.aasc.erp.bean.*"%>
<%@page import="com.aasc.erp.util.*"%>
<%@page import="java.util.logging.*"%>


<%! private static Logger logger=AascLogger.getLogger("aascAjaxOrderNumberGen"); %>
<%
                String orderNumber = "";
                AascShipmentOrderInfoDAO aascShipmentOrderInfoDAO = null;
                AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1); // object of AascDAOFactory
                AascShipmentOrderInfo aascShipmentOrderInfo = (AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo");
                AascShipmentHeaderInfo headerBean = aascShipmentOrderInfo.getShipmentHeaderInfo();
                orderNumber = headerBean.getOrderNumber();
               int clientId=Integer.parseInt(request.getParameter("clientId"));
			   String fsFlag = request.getParameter("fsFlag");
               session.setAttribute("saveHeaderFlag","N");
                if("".equalsIgnoreCase(orderNumber) || orderNumber == null){
                    aascShipmentOrderInfoDAO = aascDAOFactory.getAascShipmentOrderInfoDAO(); // this method returns the object of the AascShipmentOrderInfoDAO class
                    orderNumber = aascShipmentOrderInfoDAO.getOrderNumber(clientId);
                    orderNumber = "SC"+orderNumber;
                    headerBean.setOrderNumber(orderNumber);
                    aascShipmentOrderInfo.setShipmentHeaderInfo(headerBean);
                    session.setAttribute("AascShipmentOrderInfo", aascShipmentOrderInfo);
                    session.setAttribute("saveHeaderFlag","Y");
                   // request.setAttribute("orderNumber", orderNumber);
                }
				if("1".equalsIgnoreCase(fsFlag)){
                    AascFreightShopDelegate aascFreightShopDelegate = new AascFreightShopDelegate();
                    aascFreightShopDelegate.commonAction(session,orderNumber);
                }
                out.write(orderNumber);
%>
