<%
/*
$Header: /u01/cvs/Products/AA_SAAS_MODULES/ShipConsole_Lyte/Ship/public_html/aascAjaxGetUpsServiceLevelCode.jsp,v 1.2 2014/12/09 11:50:23 vmalluru Exp $

  DESCRIPTION                                                              
    JSP to get Ups Service Level Code from data base
    Author rajesh jagadam 30/04/2007                                   
    Version - 1                                                            
    History 
    
    21-Nov-2014     Eshwari   Added this file from cloud 1.2 version 
*/
%>
<%@page import="com.aasc.erp.model.*"%>
<%@ include file="aascHeader.jsp"%>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxGetUpsServiceLevelCode"); %>
<%

    int carriercode=-1;
    String carrierservicelevel="";
    String ajaxOriginRegionCode="";
    
    try{
      carriercode=Integer.parseInt(request.getParameter("ajaxCarrierCode"));
    }
    catch(Exception e)
    {
      carriercode=-1;
    }
    logger.info("carriercode : "+carriercode);
    
    try{
      carrierservicelevel=(String)request.getParameter("ajaxcarrierservicelevel");
    }
    catch(Exception e)
    {
      carrierservicelevel="";
    }
    logger.info("carrierservicelevel : "+carrierservicelevel);
    try{
      ajaxOriginRegionCode=(String)request.getParameter("ajaxOriginRegionCode");
    }
    catch(Exception e)
    {
      ajaxOriginRegionCode="";
    }
    logger.info("ajaxOriginRegionCode : "+ajaxOriginRegionCode);
    
    AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);
    AascShipMethodAjaxDAO shipMethodDB=aascDAOFactory.getAascShipMethodAjaxDAO(); 
    String upsServiceLevelCode = "@@@";
    upsServiceLevelCode = upsServiceLevelCode + shipMethodDB.retrieveUpsServiceLevelCode(carriercode,carrierservicelevel,ajaxOriginRegionCode);
    //out.println(Retrieve_cpt);
    logger.info("upsServiceLevelCode=="+upsServiceLevelCode);
    if(upsServiceLevelCode.equals("-1"))
    {
    out.write("");
    }
    else
    {
    out.write(upsServiceLevelCode);
    }
%>
