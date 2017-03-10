<%
/*
$Header: /u01/cvs/Products/AA_SAAS_MODULES/ShipConsole_Lyte/Ship/public_html/aascAjaxGetCarrierCombValues.jsp,v 1.1.1.1 2014/11/28 11:45:58 vmalluru Exp $

  DESCRIPTION                                                              
    JSP to get Carrier Combination Values from data base
    Author rajesh jagadam 30/04/2007                                   
    Version - 1                                                            
    History     
    10/06/09   Madhavi    Modified logging code
    20/11/14   Eshwari    Added this file from cloud 1.2 version
*/
%> 
<%@page import="com.aasc.erp.model.*"%>
<%@ include file="aascHeader.jsp"%>
<%!  private static Logger logger=AascLogger.getLogger("aascAjaxGetCarrierCombValues"); %>
<%


int carriercode=-1;
String carrierservicelevel="";
String dropoftype="";
String packaging="";
String carrierPaymentTerms="";
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
  
try{
  carrierPaymentTerms=(String)request.getParameter("CarrierPayMethodText");
  }
  catch(Exception e)
  {
  packaging="";
  }
AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);
AascShipMethodAjaxDAO shipMethodDB=aascDAOFactory.getAascShipMethodAjaxDAO(); 
String retrieveCombValues=shipMethodDB.getCarrierCombValidValues(carriercode,carrierservicelevel,dropoftype,packaging,carrierPaymentTerms);  
//out.println(Retrieve_cpt);
logger.info("carriercode=="+carriercode+"carrierservicelevel=="+carrierservicelevel+"dropoftype=="+dropoftype+"packaging=="+packaging+"carrierPaymentTerms=="+carrierPaymentTerms);
if(retrieveCombValues.equals("-1"))
{
out.write("**");
}
else
{
out.write(retrieveCombValues);
}
%>
