<%
/*
$Header: /u01/cvs/Products/AA_SAAS_MODULES/ShipConsole_Lyte/Ship/public_html/aascAjaxTrakingInfo.jsp,v 1.4.4.1 2015/08/31 15:09:18 sgunda Exp $

  DESCRIPTION                                                              
    JSP to get tracking information given trakingType,date and locationId
                                                              
    History                                                                
     17/12/14    Eshwari M   Modified this file for SC Lite Tracking to resolve the date issue coming with Spring JDBC code.
     19/01/15    Y Pradeep   Modified code to remove unused variables and commented as suggested in Code Review document.
     20/01/15    Y Pradeep   Modified auther and version, also removed commented code.
     31/08/15    Suman G     Added user id for Trial User
*/
%>
<%@ page import="com.aasc.erp.model.*"%>
<%@ page import="java.sql.Date"%>
<%@ include file="aascHeader.jsp"%>
<%
 
String ShipTrackDate=request.getParameter("shipTrack");

String trakingType=request.getParameter("trakingType");
int locationId =0;
try
{
 locationId =Integer.parseInt(request.getParameter("locationId"));
}
catch(Exception e)
{
locationId =0;
}
int clientId = 0;
try
{
String clientIdStr = (String)request.getParameter("clientId") ;
clientId = Integer.parseInt(clientIdStr) ;
}catch(Exception e)
{
  clientId = 0 ;
}
int userId=0;
try{
    userId = Integer.parseInt(request.getParameter("userId"));
}catch(Exception e){
    userId = 0;
}
String date=null ;
try
{
         //String mon[]={"","JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
         String convertDate=ShipTrackDate;
         int len=convertDate.length();
         int index=convertDate.indexOf('-');
         int index1=convertDate.lastIndexOf('-');
         String syear=convertDate.substring(index1+1,len).trim();
         String smon=convertDate.substring(0,index).trim();
         String sdate=convertDate.substring(index+1,index1).trim();
         date=sdate+'-'+Integer.parseInt(smon)+'-'+syear;
         
 
                     
}catch(Exception e) 
{
e.getMessage();
}
AascOracleGetTrackInfo aascOracleGetTrackInfo= new AascOracleGetTrackInfo();

String trackingIds =aascOracleGetTrackInfo.getTrackingInfo(trakingType,date,locationId, clientId, userId);
 

out.write(trackingIds);
%>
