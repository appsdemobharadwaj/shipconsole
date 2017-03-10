<%
/*===========================================================================================================================================================+
|  DESCRIPTION                                                                                                                                               |
|    JSP to display the Output of HTML Reports                                                                                                               |
|    Author Eshwari M                                                                                                                                        |
|    Version   1.1                                                                                                                                           |     
|    Creation  28/11/2014                                                                                                                                    | 
|                                                                                                                                                            | 
|  HISTORY                                                                                                                                                   |
|   15/01/2015   Y Pradeep      Merged Sunanda's code : getting title name from Application.Property file.                                                   |
|   30/01/2015   Eshwari M      Formatted the code and ran code audit                                                                                        |
+============================================================================================================================================================*/
%>
<%@ page errorPage="aascShipError.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:property value="getText('SCReportPopup')" /></title>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <script language="JavaScript" src="aasc_reports_js.js" type="text/javascript"> 
    </script>
  </head>
  
  <body class="gradient" onload="submitHTMLReport();"><s:form name="aascReportsPopupForm"
                                                              method="POST"
                                                              action="ReportDriverAction1">
      <s:set name="roleId" value="%{}"/>
      <s:set name="clientId" value="%{}"/>
      <s:set name="locaionId" value="%{}"/>
      <s:set name="actionType" value="%{}"/>
      <s:set name="reportType" value="%{}"/>
      <s:set name="toDate" value="%{}"/>
      <s:set name="fromDate" value="%{}"/>
      <s:set name="shipMethod" value="%{}"/>
      <s:set name="carrierId" value="%{}"/>
      <c:set var="roleId2" value="${param.roleId}"/>
      <s:set name="roleId" value="%{#attr.roleId2}"/>
      <s:if test="%{#roleId == 2 }">
        <c:set var="clientId1" value="${param.clientId}"/>
        <s:set name="clientId" value="%{#attr.clientId1}"/>
        <s:hidden name="clientId" id="clientId" value="%{#clientId}"/>
      </s:if>
       
      <c:catch var="Exception1">
        <c:set var="locaionId1" value="${param.locaionId}"/>
        <s:set name="locaionId" value="%{#attr.locaionId1}"/>
        <s:if test="%{#locaionId == null }">
          <s:set name="locaionId" value="%{}"/>
        </s:if>
        <c:set var="actionType1" value="${param.actionType}"/>
        <s:set name="actionType" value="%{#attr.actionType1}"/>
        <s:if test="%{#actionType == null }">
          <s:set name="actionType" value="%{}"/>
        </s:if>
        <c:set var="reportType1" value="${param.reportType}"/>
        <s:set name="reportType" value="%{#attr.reportType1}"/>
        <s:if test="%{#reportType == null }">
          <s:set name="reportType" value="%{}"/>
        </s:if>
        <c:set var="toDate1" value="${param.toDate}"/>
        <s:set name="toDate" value="%{#attr.toDate1}"/>
        <s:if test="%{#toDate == null}">
          <s:set name="toDate" value="%{}"/>
        </s:if>
        <c:set var="fromDate1" value="${param.fromDate}"/>
        <s:set name="fromDate" value="%{#attr.fromDate1}"/>
        <s:if test="%{#fromDate == null}">
          <s:set name="fromDate" value="%{}"/>
        </s:if>
        <c:set var="shipMethod1" value="${param.shipMethod}"/>
        <s:set name="shipMethod" value="%{#attr.shipMethod1}"/>
        <s:if test="%{#shipMethod == null }">
          <s:set name="shipMethod" value="%{}"/>
        </s:if>
        <c:set var="carrierId1" value="${param.carrierId}"/>
        <s:set name="carrierId" value="%{#attr.carrierId1}"/>
        <s:if test="%{#carrierId == null }">
          <s:set name="carrierId" value="%{}"/>
        </s:if>
        <s:bean name="java.util.HashMap" id="paramMap">
          <s:set name="requestType" value="HTMLOutput"/>
          <s:set name="locaionId" value="%{#locaionId}"/>
          <s:set name="actionType" value="%{#actionType}"/>
          <s:set name="reportType" value="%{#reportType}"/>
          <s:set name="toDate" value="%{#toDate}"/>
          <s:set name="fromDate" value="%{#fromDate}"/>
          <s:set name="shipMethod" value="%{#shipMethod}"/>
          <s:set name="carrierId" value="%{#carrierId}"/>
        </s:bean>
        <s:set name="HTMLReportParams" value="%{#paramMap}"/>
      </c:catch>
      <c:if test="%{#Exception1 != null}"></c:if>
      <s:hidden name="hiddenlocaionId" id="hiddenlocaionId"
                value="%{#locaionId}"/>
      <s:hidden name="carrierSelect" id="carrierSelect" value="%{#carrierId}"/>
      <s:hidden name="shipMethod" id="shipMethod" value="%{#shipMethod}"/>
      <s:hidden name="fromDate" id="fromDate" value="%{#fromDate}"/>
      <s:hidden name="toDate" id="toDate" value="%{#toDate}"/>
      <s:hidden name="reportType" id="reportType" value="%{#reportType}"/>
      <s:hidden name="actionType" id="actionType" value="%{#actionType}"/>
    </s:form>
     <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
</html>
