<%@ page contentType="text/html;charset=UTF-8"%>
<%
/*

  DESCRIPTION                                                              
    JSP to delete packages from db based on Order Number, ClientId and Package Count when delete or clear button clicked in Package Details section.
    Author Y Pradeep 08/05/2015                                
    Version - 1                                                            
    History  
    
    08/05/2015  Y Pradeep      Added this file for deleting or clearing package details in database. Bug #2910.
    

*/
%>
<%@page import="com.aasc.erp.model.*"%>
<%@page import="com.aasc.erp.bean.*"%>
<%@page import="com.aasc.erp.util.*"%>
<%@page import="java.util.logging.*"%>


<%! private static Logger logger=AascLogger.getLogger("aascAjaxDeletePackages"); %>
<%
                String orderNumber = "";
                String submitButton = "";
                String opStatus = "";
                int packNumber = 0;
                AascShipmentOrderInfoDAO aascShipmentOrderInfoDAO = null;
                AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1); // object of AascDAOFactory
                int clientId=Integer.parseInt(request.getParameter("clientId"));
                orderNumber=request.getParameter("orderNumber");
                packNumber=Integer.parseInt(request.getParameter("packNumber"));
                submitButton = request.getParameter("submitButton");
                if("DELETE".equalsIgnoreCase(submitButton)){
                    if(orderNumber != null && !"".equalsIgnoreCase(orderNumber)){
                        aascShipmentOrderInfoDAO = aascDAOFactory.getAascShipmentOrderInfoDAO(); // this method returns the object of the AascShipmentOrderInfoDAO class
                        opStatus = aascShipmentOrderInfoDAO.deletePackage(clientId, orderNumber, packNumber);
                    }
                }
                else if("CLEAR".equalsIgnoreCase(submitButton)){
                    if(orderNumber != null && !"".equalsIgnoreCase(orderNumber)){
                        aascShipmentOrderInfoDAO = aascDAOFactory.getAascShipmentOrderInfoDAO(); // this method returns the object of the AascShipmentOrderInfoDAO class
                        for(int i=2; i<=packNumber; i++){
                            opStatus = aascShipmentOrderInfoDAO.deletePackage(clientId, orderNumber, i);
                        }
                    }
                }
                
                out.write(opStatus);
%>
