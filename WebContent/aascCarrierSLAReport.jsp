<%
/*====================================================================================================================+
|  DESCRIPTION                                                                                                        |
|    A JSP to display the report parameters that ahs to select fro the report output                                  |
|    Author Eshwari                                                                                                   |
|    Version   1.1                                                                                                    |                                                                            
|    Creation  28/11/2014                                                                                             |      
|    History :                                                                                                        |
|                                                                                                                     |
|    15/01/2015  Y Pradeep   Merged Sunanda's code : getting title name from Application.Property file.               |
|    29/01/2015  Eshwari M   Added GetTrackingDetails Button to get the tracking details in role 5                    |
|    30/01/2015  Eshwari M   Formatted the code and ran code audit                                                    |
|    05/03/2015  Sanjay & Khaja Added code for new UI changes.                                                        |
|    11/03/2015  Jagadish Implemented the new UI changes                                                              |   
|    |7/03/2015  Eshwari M   Added labelsPath hidden field to send labels path to getTrackingDetailsAjax()            |              
|    18/03/2015  Y pradeep   Removed Analytics link at bottom.                                                        |
     10/04/2015  Y Pradeep   Removed validateLoc function in onclick of submit button when selected HTML to avoid opening both location validate alert and HTML page.
     15/04/2015  Y Pradeep   Added code related to new Calander UI. Bug #2789.
     15/04/2015  Suman G          Added session to fix #2743
     23/04/2015  Y Pradeep   Removed footer and added code to avoid conflicts in jquery for new calendar.
     11/06/2015  Suman G     Removed fevicon to fix #2992
     22/06/2015  Y Pradeep   Added type attribute to avoid form submition.
     09/07/2015  Dinakar     Aligined UI as per tab
     15/07/2015  Rakesh K    Aligined UI as per Role5
     24/07/2015  Rakesh K    Modified Code to work application in offline.
     04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
     26/08/2015  Rakesh K       Added code to solve 3496.
+=====================================================================================================================*/
%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ page errorPage="aascShipError.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
  
  <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
   
    <script language="javascript" src="bootstrap/js/jquery11.min.js" type="text/javascript"></script>
     <!--<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>-->
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link href="https://kendo.cdn.telerik.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <script language="javascript" src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    <script src="kendo.all.min.js" type="text/javascript"></script>
    
    <!--<style type="text/css"> 
     @font-face {   
     font-family: 'Glyphicons Halflings';   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot');   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.woff') format('woff'),  
     url('bootstrap/fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg'); } 
    </style>-->
  
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
     <title><s:property value="getText('SCCarrierSLAReport')" /></title>
     
     
     <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
     <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
   <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
   <!-- <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>
    <script src="kendo.all.min.js" type="text/javascript"></script>
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
     <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
   <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
   <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>-->
   
    <script type="text/javascript" src="aasc_reports_js.js"> 
     </script>
     
    <script>
        var j = jQuery.noConflict();
        j(document).ready(function() {
            // create DatePicker from input HTML element
            j("#fromDateID").kendoDatePicker({
            parseFormats:["MM-dd-yyyy"],
            format: "MM-dd-yyyy"
            }).data("kendoDatePicker");
        });
        
        j(document).ready(function() {
            // create DatePicker from input HTML element
            j("#toDateID").kendoDatePicker({
            parseFormats:["MM-dd-yyyy"],
            format: "MM-dd-yyyy"
            }).data("kendoDatePicker");
        });
    </script>
     
      <style type="text/css">
      
      html {
        height: 100%;
      }
        
    </style>
  </head>
  <body class="gradientbg" 
        onload="disableCheck('carrierSLAReportForm');getLocations('carrierSLAReportForm');getValueCarrier('carrierSLAReportForm');shipmethajax('carrierSLAReportForm');">
        
        <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
        
        <div height="54" colspan="6">
          <%@ include file="aascIndexHeader.jsp"%>
        </div>
      
   
      <!-- Gururaj code end-->
     

     <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
         
        <s:set name="sysdate" value="%{''}" />
        <s:set name="fromDate" value="%{''}" /> 
        <s:set name="toDate" value="%{''}" />
        <s:set name="select" value="%{''}" />
        <s:set name="inputNumber" value="%{''}" />
        <s:set name="carrierIdStr" value="%{''}" />
        <s:set name="clientIdNum" value="%{''}"/>
        
        <s:set name="labelsPath" value="%{#session.cloudLabelPath}"/>
           
        <s:set name="roleId" value="%{#session.role_id}"/>
        <s:hidden name="roleId" id="roleId" value="%{#roleId}"/>
        
        <s:hidden name="labelsPath" id="labelsPath" value="%{#labelsPath}"/>

        <jsp:useBean id="date" class="java.util.Date"/>
        <fmt:formatDate var="sysdate" value="${date}" pattern="MM-dd-yyyy"/>
        <s:set name="sysdate" value="%{#attr.sysdate}" />
         
        <c:set var="inputNumber1" value="${param.shipMethodList}"/>
        <s:set name="inputNumber" value="%{#attr.inputNumber1}" />
        <s:set name="carrierIdStr" value="%{#request.carrierId}"/>
        <s:set name="ip_location_id" value="%{#session.location_id}" />
        <s:set name="client_id" value="%{#session.client_id}" />
        <c:catch var="Exception2">
          <s:set name="fromDate" value="%{#request.fromDate}"/>
                  <s:if test='%{#fromDate == null|| #fromDate ==  ""}'>
            <s:set name="fromDate" value="#sysdate"/>
          </s:if>
          <c:set var="toDate" value="${param.toDate}"/>
          <s:set name="toDate" value="%{#attr.toDate}"/>
            <s:if test='%{#toDate == null || #toDate == ""}'>
            <s:set name="toDate" value="#sysdate"/>
          </s:if>
        </c:catch>
          
          <c:catch var="Exception3">
            <s:set name="aascGetLocationInfo" value="%{#session.locationInfo}"/>
            <s:if test="%{#aascGetLocationInfo == null}">
              <jsp:useBean id="nullPointerException"
                           class="java.lang.NullPointerException"/>
              <s:set name="Exception31" value="%{#nullPointerException}"/>
            </s:if>
          </c:catch>
          <!-- code to get customer details and location name deatils from session-->
        <s:if test="%{#roleId == 2}">
          <c:catch var="customerException">
              <s:set name="clientDetailsHashMap"
                     value="%{#session.clientDetailsHashMap}"/>
          </c:catch>
          
            <c:set var="clientId1" value="${param.clientIdSelect}"
                   scope="page"/>
          <s:set name="clientId" value="%{#attr.clientId1}" />        
          <s:if test="%{#clientId == 0}">
              <s:set name="clientId" value="%{'0'}"/>
          </s:if>
          <s:else>
             <s:set name="clientId" value="%{#attr.clientId1}"/>
          </s:else>
          <s:hidden name="clientId"/>
          <c:set var="locationId" value="${param.locationIdSelect}"
                 scope="page"/>
          <s:set name="locationId" value="%{#attr.locationId}" /> 
        </s:if>
        <s:else>
          <c:catch var="locationException">
              <s:set name="locationDetailsMap"
                     value="%{#session.locationDetailsMap}"/>
          </c:catch> 
      
          <s:if test="%{#locationExecption != null}">
              <c:out value="Error in Retrieving session attribute locationDetailsMap"/>
              <c:redirect url="/aascShipError.jsp">
                  <c:param name="errorObj" value="SessionError" />
              </c:redirect>
          </s:if>
          <s:set name="locationId" value="%{#session.location_id}"/>    
     
        </s:else>
        <s:set name="locationDetails" value="%{#aascGetLocationInfo.orgDetails}" /> 
             
          <s:form name="carrierSLAReportForm" method="POST" action="ReportDriverAction" onsubmit="return displayInfo('carrierSLAReportForm')">
          <div class="row"><br/></div>
          <div class="row"><br/></div>
          <div class="row"><br/></div>
          <center>
          
          <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:80%">
                   <!-- <fieldset class="scheduler-border" style="border-color:#19b698; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;"> -->
                   <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <label style="color:#ffffff;font-size: 20px;">On Time Delivery Report</label>
                    </div>
                    
                        <div class="row"><br/></div> 
                    
                    
                    <div class="row" id="divSub" style="width:80%; margin-left:8%;">
                        <div class="col-sm-12">
                                <s:set name="locationDetailsMap" value="%{#session.locationDetailsMap}"/>
                            <s:if test="%{#roleId == 2}">
                                     <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('ClientDetails')"/></span></label>
                                <div class="col-sm-7">
                                   <s:set name="disableFlag" value="true"/>
                         <s:select list="#clientDetailsHashMap" name="clientIdSelect" id="clientIdSelect" listKey="key" listValue="value"  size="1"
                                onchange="getLocations('carrierSLAReportForm');shipmethajax(\'carrierSLAReportForm\');" headerValue="Select" headerKey=""
                                      value="#clientId"/>
                         <s:hidden name="clientIdHidden" id="clientIdHidden" value="%{#clientId}"/>
                            </div>
                            </s:if>
                                    <s:else>
                                         <s:hidden name="clientIdHidden" id="clientIdHidden"  value="%{#client_id}"/>
                                    </s:else>
                                   </div>
                               </div>
                               <div class="row"><br/></div>
                               
                      <div class="row" id="divSub" style="width:80%; margin-left:8%;">
                        <div class="col-sm-12">
                                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('Location')"/></span></label>
                                <div class="col-sm-5">
                                   <s:set name="disableFlag" value="true"/>
                                <s:if test="%{#roleId == 4}">
                                    <s:select list="#locationDetailsMap" name="locationIdSelect" id="locationIdSelect" listKey="key"
                                      class="form-control" listValue="value"  size="1" headerValue="Select" headerKey="" value="#locationId"/>
                                    <s:hidden name="locationIdHidden" id="locationIdHidden" value="%{#locationId}"/>
                                </s:if>
                                 <s:else>
                                    <s:select list="#locationDetailsMap" name="locationIdSelect" id="locationIdSelect" listKey="key"
                                     class="form-control" listValue="value"  size="1" headerValue="Select" headerKey="" value="#locationId"/>
                                    <s:hidden name="locationIdHidden" id="locationIdHidden" value="%{#locationId}"/>
                                </s:else>
                                     
                        <s:hidden name="submitCount" value="0"/>
                        <s:hidden name="carrierId" id="carrierId" value="%{''}"/>
                                   </div>
                                   <div class="col-sm-2">
                                        <s:if test="%{#roleId == 5}">
                          <button class="btn btn-success" name="submitButton" type="button" id="submitButton" onclick="javascript:getTrackingDetails();" value="submitButton" alt="[submitButton]" align="middle"> Get Tracking Details <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                         <!-- <s:a href="javascript:getTrackingDetails()">
                            <img src="buttons/GetTrackingDetails.png" name="submitButton" border=0 align="middle" alt="">
                          </s:a>  --> 
                        </s:if>    
                                   </div>
                               </div>
                               </div>
                               <div class="row"><br/></div>

                        <div class="row" id="divSub" style="width:80%; margin-left:8%;">
                        <div class="col-sm-12">
                                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('CarrierName')"/></span></label>
                                <div class="col-sm-5">
                                   <s:select list="#{'-1':'ALL','100':'UPS', '110':'FEDEX EXPRESS', '111':'FEDEX GROUND', '999':'OTHERS'}" name="carrierSelect" id="carrierSelect" 
                                     size="1"  onchange="getValueCarrier('carrierSLAReportForm');shipmethajax('carrierSLAReportForm');" listKey="key" listValue="value" 
                                     class="form-control"    headerValue="Select Carrier" value="#carrierIdStr"/>
                                   </div>
                               </div>
                               </div>
                               <div class="row"><br/></div>
                        <div class="row" id="divSub" style="width:80%; margin-left:8%;">
                        <div class="col-sm-12">
                                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('ShipMethod')"/></span></label>
                                <div class="col-sm-5">
                                   <s:if test="%{#inputNumber == null}">
                                        <s:set name="inputNumber" value="%{''}"/>
                                    </s:if>
                                    <s:hidden name="shipMethod" id="shipMethod" value="%{#inputNumber}"/>
                                    <s:select list="inputNumber" name="shipMethodList" id="shipMethodList" size="1"  
                                      class="form-control"  onchange="getValueCarrier(\'aascCarrierShipActivityForm\');" value="%{#inputNumber}" headerValue="Select Ship Method"/>
                                   </div>
                               </div>
                               </div>
                               <div class="row"><br/></div>
                               
                        <div class="row" id="divSub" style="width:80%; margin-left:8%;">
                        <div class="col-sm-12">
                                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('FromDate')"/></span></label>
                                <div class="col-sm-9" align="left">
                                   <s:textfield maxlength="10" name="fromDate" id="fromDateID" type="date" value="%{#fromDate}" size="15"/>
                                   </div>
                               </div>
                               </div>
                               <div class="row"><br/></div>
                               
                         <div class="row" id="divSub" style="width:80%; margin-left:8%;">
                        <div class="col-sm-12">
                                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('ToDate')"/></span></label>
                                <div class="col-sm-9" align="left">
                                   <s:textfield maxlength="10" name="toDate" id="toDateID" value="%{#toDate}" type="date" size="15"/>
                                   </div>
                               </div>
                               </div>
                               <div class="row"><br/></div>
                               
                       <div class="row" id="divSub" style="width:80%; margin-left:8%;">
                        <div class="col-sm-12">
                                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('ReportType')"/></span></label>
                                <div class="col-sm-9" align="left">
                                   <input type="radio" name="reportType" value="PDF"></input>
                                      <s:property value="getText('PDF')"/>
                                      <input type="radio" name="reportType" value="HTML"></input>
                                      <s:property value="getText('HTML')"/>
                                      <input type="radio" name="reportType" value="CSV" checked="checked"></input>
                                      <s:property value="getText('CSV')"/>
                                      <input type="radio" name="reportType" value="RTF"></input>
                                      <s:property value="getText('RTF')"/>
                                      <input type="radio" name="reportType" value="XLS"></input>
                                      <s:property value="getText('XLS')"/>
                                   </div>
                               </div>
                               </div>        
                    
                   <div>
                   <div class="row"><br/></div>
                     <center>
                             <s:hidden name="submitButton" value="%{\'\'}"/>
                             <s:hidden name="actionType" value='%{\"CarrierSLAReport"}'/>   
                             <button class="btn btn-success" name="submitButton" type="button" id="save" onclick="openPopupForHTML('carrierSLAReportForm');" value="Save" alt="[save]" align="middle"> Submit <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                            <!-- <button class="btn btn-info" name="ClearBtn" id="CloseButton" onclick="window.close();" value="CloseButton" alt="[close]" align="middle"> Clear <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>-->
           
                            <s:url action="ReportsAction" id="url">
                            <s:param name="requestType" value="%{\'CarrierSLAReport\'}"></s:param>
                          </s:url>
                        
                          <s:a href="%{url}" name="#CarrierSLAReport.action1">
                            <span class="btn btn-warning" >
                             Clear <span class="badge" ><span class="glyphicon glyphicon-remove"></span></span></span>

                          </s:a>

           
           
                         </center> 
                         </div>
                         </br>
             
                             
            </div>
          
          
          
          
          
          
          
         <!--  <table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
             <tr>
               <td width="10%" align="center">
                 <div id="indexLoad" style="display: none;">
                   <img src="images/ajax-loader.gif" alt="" height="25" width="25"/>
                 </div>
               </td>
             </tr>
           </table>
           
           <table width="60%"  border="0" cellspacing="0" cellpadding="0">
             <tr>
               <td align="center" valign="bottom">
                <fieldset>
             <legend><img src="images/OnTimeDeliveryReportImage.png"   alt="" align="middle"></legend>
             
                 <table cellspacing="1" cellpadding="1" width="98%" align="center">
                    <s:set name="locationDetailsMap" value="%{#session.locationDetailsMap}"/>
                   <s:if test="%{#roleId == 2}">
                   <tr class="tableDataCell">
                      <td class="dispalyFields"><s:property value="getText('ClientDetails')"/></td>
                      <td align="left" nowrap="nowrap"   class="dispalyFields">
                         <s:set name="disableFlag" value="true"/>
                         <s:select list="#clientDetailsHashMap" name="clientIdSelect" id="clientIdSelect" listKey="key" listValue="value" cssClass="inputs" size="1"
                                onchange="getLocations('carrierSLAReportForm');shipmethajax(\'carrierSLAReportForm\');" headerValue="Select" headerKey=""
                                      value="#clientId"/>
                         <s:hidden name="clientIdHidden" id="clientIdHidden" value="%{#clientId}"/>
                      </td>
                   </tr> 
	           </s:if>
                   <s:else>
                      <s:hidden name="clientIdHidden" id="clientIdHidden"  value="%{#client_id}"/>
                   </s:else>
                   <tr class="tableDataCell">
                      <td class="dispalyFields"><s:property value="getText('Location')"/></td>
                      <td align="left" nowrap="nowrap" class="dispalyFields">
                 
                        <s:set name="disableFlag" value="true"/>
                        <s:if test="%{#roleId == 4}">    
                     
                            <s:select list="#locationDetailsMap" name="locationIdSelect" id="locationIdSelect" listKey="key" listValue="value" cssClass="inputs" 
                                size="1" headerValue="Select" headerKey="" value="#locationId"/>
                            <s:hidden name="locationIdHidden" id="locationIdHidden" value="%{#locationId}"/>
                        </s:if> 
                        <s:else>
                            <s:select list="#locationDetailsMap" name="locationIdSelect" id="locationIdSelect" listKey="key" listValue="value" cssClass="inputs"
                                size="1" headerValue="Select" headerKey="" value="#locationId"/>
                           <s:hidden name="locationIdHidden" id="locationIdHidden" value="%{#locationId}"/>
                        </s:else>
                 
                        <s:if test="%{#roleId == 5}">
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                          <s:a href="javascript:getTrackingDetails()">
                            <img src="buttons/GetTrackingDetails.png" name="submitButton" border=0 align="middle" alt="">
                          </s:a>   
                        </s:if>         
                        <s:hidden name="submitCount" value="0"/>
                        <s:hidden name="carrierId" id="carrierId" value="%{''}"/>
                      </td>
                   </tr>
              
                   <tr>
                     <td class="dispalyFields"><s:property value="getText('CarrierName')"/></td>
                     <td> 
                       <s:select list="#{'-1':'ALL','100':'UPS', '110':'FEDEX EXPRESS', '111':'FEDEX GROUND', '999':'OTHERS'}" name="carrierSelect" id="carrierSelect" 
                            cssClass="inputs" size="1"  onchange="getValueCarrier('carrierSLAReportForm');shipmethajax('carrierSLAReportForm');" listKey="key" listValue="value" 
                                headerValue="Select Carrier" value="#carrierIdStr"/>
                     </td>
                   </tr>
                   <tr>
                     <td class="dispalyFields"><s:property value="getText('ShipMethod')"/></td>
                     <td>
                        <s:if test="%{#inputNumber == null}">
                           <s:set name="inputNumber"   value="%{''}"/>
                        </s:if>
                        <s:hidden name="shipMethod"  id="shipMethod" value="%{#inputNumber}"/>
                        <s:select list="inputNumber" name="shipMethodList" size="1" cssClass="inputs" onchange="getValueCarrier('carrierSLAReportForm');" value="#inputNumber"
                                 headerValue="Select Ship Method"/>
             
                     </td>
                   </tr>
                   <tr>
                     <td class="dispalyFields"><s:property value="getText('FromDate')"/></td>
                     <td>
                         <s:textfield maxlength="10" name="fromDate" id="fromDateID" value="%{#fromDate}" size="15"/>
                     </td>
                   </tr>
                   <tr>
                        <td class="dispalyFields"><s:property value="getText('ToDate')"/></td>
                        <td>
                           <s:textfield maxlength="10" name="toDate" id="toDateID" value="%{#toDate}" size="15"/>
                       </td>
                   </tr>
                   <tr>
                      <td class="dispalyFields"><s:property value="getText('ReportType')"/></td>
                      <td class="dispalyFields">
                          <input type="radio" name="reportType" value="PDF"></input>
                          <s:property value="getText('PDF')"/>
                          <input type="radio" name="reportType" value="HTML"></input>
                          <s:property value="getText('HTML')"/>
                          <input type="radio" name="reportType" value="CSV" checked="checked"></input>
                          <s:property value="getText('CSV')"/>
                          <input type="radio" name="reportType" value="RTF"></input>
                          <s:property value="getText('RTF')"/>
                          <input type="radio" name="reportType" value="XLS"></input>
                          <s:property value="getText('XLS')"/>
                      </td>
                   </tr>
                   <tr>
                     <td>&nbsp;</td>
                     <td align="left">
                       <s:hidden name="submitButton"  value="%{''}"/>
                       <s:hidden name="actionType"  value='%{"CarrierSLAReport"}'/>
                       
                       <s:a href="#" onclick="return openPopupForHTML('carrierSLAReportForm');">
                           <img src="buttons/aascSubmit1.jpg" name="submitButton" border="0" alt=""></img>
                       </s:a>
                       
                       &nbsp;&nbsp;
                       <s:url action="ReportsAction" id="url"> 
                            <s:param name="requestType" value="%{'CarrierSLAReport'}"></s:param>
                       </s:url> 
                       <s:a href="%{url}" name="#CarrierSLAReport.action1">
                            <img src="buttons/aascClear1.png" name="ClearBtn" border="0" alt=""></img>
                       </s:a>
                                            
                     </td>
                    
                   </tr>
                 </table>
           
           </fieldset>
               </td>
             </tr>
           </table>-->
             
          </center>
          </s:form>
    
   
    
    </div>
     <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
</html>

