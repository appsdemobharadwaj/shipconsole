<%
/*

  DESCRIPTION                                                              
    JSP to get Email Details from data base
    Author Suman G 10/07/2015 
    Version - 1                                                            
    History  
    
*/
%>
<%@page import="com.aasc.erp.model.*"%>
<%@page import="com.aasc.erp.util.*"%>
<%@page import="java.util.logging.*"%>


<%! private static Logger logger=AascLogger.getLogger("aascAjaxRetrieveEmailDetails"); %>
<%
    String retrieveCcCsl = "@@@" ;
    try{
    String carrierCodeTemp=(String)request.getParameter("carrierCode");
    String locationIDTemp = (String)request.getParameter("locationId");
    int carrierCode = Integer.parseInt(carrierCodeTemp);
    int locationID = Integer.parseInt(locationIDTemp);
    int clientId =(Integer)session.getAttribute("client_id");
    AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);
    AascShipMethodAjaxDAO shipMethodDB=aascDAOFactory.getAascShipMethodAjaxDAO(); 
    retrieveCcCsl = retrieveCcCsl + shipMethodDB.getEmailDetails(carrierCode,locationID,clientId);
    //out.println(Retrieve_cc_csl);
    logger.info("Email details in ajax ::"+retrieveCcCsl);
    if(retrieveCcCsl.equals("-1"))
    {
        out.write("**");
    }
    else{
        out.write(retrieveCcCsl);
    }
    
    }catch(Exception e)
    {
       e.printStackTrace() ;
    }
%>

