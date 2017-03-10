<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@ page import="com.aasc.erp.util.*"%>
<%@page import="java.util.logging.*"%>
<%@ page import="java.util.*"%>

<%@ taglib uri="/struts-tags" prefix="s"%>
 <script language="JavaScript" src="aasc_Shipment_JS.js" type="text/javascript"></script>
 <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>aascShipmentPackageDetailsHelp</title>
  </head>
  <body>
  
     <%
                AascShipmentHeaderInfo  aascShipmentHeaderInfo=(AascShipmentHeaderInfo)request.getAttribute("aascShipmentHeaderInfo");
                pageContext.setAttribute("aascShipmentHeaderInfoTmp",aascShipmentHeaderInfo);
        %>
  <s:set name="aascShipmentHeaderInfo" value="%{#attr.aascShipmentHeaderInfoTmp}"/>
  
  
   <s:hidden name="rtnShipFromCompany%{#packCount}" id="rtnShipFromCompanyID%{#packCount}" value="%{#rtnShipFromCompany}"/>
              <s:hidden name="rtnShipToCompany%{#packCount}" id="rtnShipToCompanyID%{#packCount}" value="%{#rtnShipToCompany}"/>
              <s:hidden name="rtnShipFromContact%{#packCount}" id="rtnShipFromContactID%{#packCount}" value="%{#rtnShipFromContact}"/>
              <s:hidden name="rtnShipToContact%{#packCount}" id="rtnShipToContactID%{#packCount}" value="%{#rtnShipToContact}"/>
              <s:hidden name="rtnShipFromLine1%{#packCount}" id="rtnShipFromLine1ID%{#packCount}" value="%{#rtnShipFromLine1}"/>
              <s:hidden name="rtnShipToLine1%{#packCount}" id="rtnShipToLine1ID%{#packCount}" value="%{#rtnShipToLine1}"/>
              <s:hidden name="rtnShipFromLine2%{#packCount}" id="rtnShipFromLine2ID%{#packCount}" value="%{#rtnShipFromLine2}"/>
              <s:hidden name="rtnShipToLine2%{#packCount}" id="rtnShipToLine2ID%{#packCount}" value="%{#rtnShipToLine2}"/>
              <s:hidden name="rtnShipFromCity%{#packCount}" id="rtnShipFromCityID%{#packCount}" value="%{#rtnShipFromCity}"/>
              <s:hidden name="rtnCountrySymbol%{#packCount}" id="rtnCountrySymbolID%{#packCount}" value="%{#rtnCountrySymbol}"/>
              <s:hidden name="rtnShipFromSate%{#packCount}" id="rtnShipFromSateID%{#packCount}" value="%{#rtnShipFromSate}"/>
              <s:hidden name="rtnShipFromZip%{#packCount}" id="rtnShipFromZipID%{#packCount}" value="%{#rtnShipFromZip}"/>
              
              <s:hidden name="rtnShipToCity%{#packCount}" id="rtnShipToCityID%{#packCount}" value="%{#rtnShipToCity%>"/>
              <s:hidden name="rtnShipToState%{#packCount}" id="rtnShipToStateID%{#packCount}" value="%{#rtnShipToState}"/>
              <s:hidden name="rtnShipToZip%{#packCount}" id="rtnShipToZipID%{#packCount}" value="%{#rtnShipToZip}"/>
              <s:hidden name="rtnShipFromPhone%{#packCount}" id="rtnShipFromPhoneID%{#packCount}" value="%{#rtnShipFromPhone}"/>
              <s:hidden name="rtnShipToPhone%{#packCount}" id="rtnShipToPhoneID%{#packCount}" value="%{#rtnShipToPhone}"/>
              <s:hidden name="rtnShipMethod%{#packCount}" id="rtnShipMethodID%{#packCount}" value="%{#rtnShipMethod}"/>
              <s:hidden name="rtnDropOfType%{#packCount}" id="rtnDropOfTypeID%{#packCount}" value="%{#rtnDropOfType}"/>
              <s:hidden name="rtnPackageList%{#packCount}" id="rtnPackageListID%{#packCount}" value="%{#rtnPackageList}"/>
              <s:hidden name="rtnPayMethod%{#packCount}" id="rtnPayMethodID%{#packCount}" value="%{#rtnPayMethod}"/>
              <s:hidden name="rtnACNumber%{#packCount}" id="rtnACNumberID%{#packCount}" value="%{#rtnACNumber}"/>
              <s:hidden name="rtnRMA%{#packCount}" id="rtnRMAID%{#packCount}" value="%{#rtnRMA}"/>
              <s:hidden name="rtnTrackingNumber%{#packCount}" id="rtnTrackingNumberID%{#packCount}" value="%{#rtnTrackingNumber}"/>
              <s:hidden name="rtnShipmentCost%{#packCount}" id="rtnShipmentCostID%{#packCount}" value="%{#rtnShipmentCost}"/>
             
  
  </body>
</html>
