<%
/*
$Header: /u01/cvs/Products/AA_SAAS_MODULES/ShipConsole_Lyte/Ship/public_html/aascAjaxRetrieveCarrierPayTerms.jsp,v 1.1.1.1 2014/11/28 11:45:58 vmalluru Exp $

  DESCRIPTION                                                              
    JSP to get Carrier Payment Terms from data base
    Author rajesh jagadam 30/04/2007                                   
    Version - 1                                                            
    History   
    10/06/09    Madhavi    Modified logging code
    19/11/14    Eshwari    Added this class from Cloud 1.2 version
*/
%>
<%@page import="com.aasc.erp.model.*"%>
<%@include file ="aascHeader.jsp" %>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxRetrieveCarrierPayTerms"); %>
<%

    int carriercode=-1;
    String carrierservicelevel="";
    String dropoftype="";
    String packaging="";
    try{
      carriercode=Integer.parseInt(request.getParameter("ajaxCarrierCode"));
    }
    catch(Exception e)
    {
      carriercode=-1;
    }
    
    try{
      carrierservicelevel=(String)request.getParameter("ajaxcarrierservicelevel");
    }
    catch(Exception e)
    {
       carrierservicelevel="";
    }
    
    try{
      dropoftype=(String)request.getParameter("dropOfType");
    }
    catch(Exception e)
    {
       dropoftype="";
    }
    
    try{
      packaging=(String)request.getParameter("packageList");
    }
    catch(Exception e)
    {
      packaging="";
    }
    AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);
    AascShipMethodAjaxDAO shipMethodDB=aascDAOFactory.getAascShipMethodAjaxDAO(); 
    String retrieveCpt=shipMethodDB.retrieveCpt(carriercode,carrierservicelevel,dropoftype,packaging);  
    //out.println(Retrieve_cpt);
    logger.info("CarrierPayTerms options ::"+retrieveCpt);
    if(retrieveCpt.equals("-1"))
    {
      out.write("**");
    }
    else
    {
       out.write(retrieveCpt);
    }
%>
