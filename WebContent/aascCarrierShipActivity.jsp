<%
/*=========================================================================================================================+
|  DESCRIPTION                                                                                                             |
|    A JSP to display the report parameters that ahs to select fro the report output                                       |
|    Author Eshwari                                                                                                        |
|    Version   1.1                                                                                                         |                                                                            
|    Creation  28/11/2014                                                                                                  |      
|    History :                                                                                                             |
|                                                                                                                          |
|    15/01/2015  Y Pradeep   Merged Sunanda's code : getting title name from Application.Property file.                    |
|    29/01/2015  Eshwari M   Added GetTrackingDetails Button to get the tracking details in role 5                         |
|    30/01/2015  Eshwari M   Formatted the code and ran code audit                                                         |
|                                                                                                                          |
|    05/03/2015    Sanjay & Khaja Added code for new UI changes.                                                           |  
|    11/03/2015    Jagadish Implemented the new UI changes                                                                 |
|    17/03/2015  Eshwari M   Modified to replces TransportationSpendReportImage image to ManifestSummaryReportImage image  |
|    18/03/2015  Y pradeep   Removed Analytics link at bottom.                                                             |
     10/04/2015  Y Pradeep   Removed validateLoc function in onclick of submit button when selected HTML to avoid opening both location validate alert and HTML page.
     15/04/2015  Y Pradeep   Added code related to new Calander UI. Bug #2789.
     16/04/2015  Suman G     Added session to fix #2743.
     23/04/2015  Y Pradeep   Removed footer and added code to avoid conflicts in jquery for new calendar.
     11/06/2015  Suman G     Removed fevicon to fix #2992.
     22/06/2015  Y pradeep   Added type attribute to avoid form submition
     09/07/2015  Dinakar     Aligined UI as per tab
     15/07/2015  Rakesh K    Aligined UI as per Role5
     24/07/2015  Rakesh K    Modified Code to work application in offline.
     04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
     26/08/2015  Rakesh K       Added code to solve 3496.
+==========================================================================================================================*/
%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ page errorPage="aascShipError.jsp"%>
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
  
  <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
   <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
   <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>
  <link href="kendo.common-material.min.css" rel="stylesheet" />
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <script src="kendo.all.min.js" type="text/javascript"></script>-->
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title><s:property value="getText('SCCarrierShipmentActivityDetailReport')" /></title>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    
  
    <script type="text/javascript" src="aasc_reports_js.js"> 
    </script>
    
     
     <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    
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
  </head>
  <body class="gradientbg"
        onload="disableCheck('aascCarrierShipActivityForm');getLocations('aascCarrierShipActivityForm');getValueCarrier('aascCarrierShipActivityForm');shipmethajax('aascCarrierShipActivityForm');">
    
    <%@ include file="aascIndexHeader.jsp"%>
    <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
    
    
  
     
    <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
          <s:set name="sysdate" value="%{''}"/>
          <s:set name="fromDate" value="%{''}"/>
          <s:set name="toDate" value="%{''}"/>
          <s:set name="select" value="%{''}"/>
          <s:set name="inputNumber" value="%{''}"/>
          <s:set name="carrierIdStr" value="%{''}"/>
          <s:set name="clientIdNum" value="%{''}"/>
          <s:set name="roleId" value="%{#session.role_id}"/>
          <s:hidden name="roleId" id="roleId"  value="%{#roleId}"/>
          <jsp:useBean id="date" class="java.util.Date"/>
          <fmt:formatDate var="sysdate1" value="${date}" pattern="MM-dd-yyyy"/>
          <s:set name="sysdate" value="%{#attr.sysdate1}"/>
          <c:set var="inputNumber1" value="${param.shipMethodList}"/>
          <s:set name="inputNumber" value="%{#attr.inputNumber1}"/>
          <s:set name="carrierIdStr" value="%{#request.carrierId}"/>
          <s:set name="ip_location_id" value="%{#session.location_id}"/>
          <s:set name="client_id" value="%{#session.client_id}"/>
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
              <jsp:useBean id="nullPointerException" class="java.lang.NullPointerException"/>
              <s:set name="Exception31" value="%{#nullPointerException}"/>
            </s:if>
          </c:catch>
          <!-- code to get customer details and location name deatils from session-->
          <s:if test="%{#roleId == 2}">
            <c:catch var="customerException">
              <s:set name="clientDetailsHashMap" value="%{#session.clientDetailsHashMap}"/>
            </c:catch>
            <c:set var="clientId1" value="${param.clientIdSelect}" scope="page"/>
            <s:set name="clientId" value="%{#attr.clientId1}"/>
            <s:if test="%{#clientId == 0}">
              <s:set name="clientId" value="%{'0'}"/>
            </s:if>
            <s:else>
              <s:set name="clientId" value="%{#attr.clientId1}"/>
            </s:else>
            <s:hidden name="clientId"/>
            <c:set var="locationId" value="${param.locationIdSelect}" scope="page"/>
            <s:set name="locationId" value="%{#attr.locationId}"/>
          </s:if>
          <s:else>
            <c:catch var="locationException">
              <s:set name="locationDetailsMap" value="%{#session.locationDetailsMap}"/>
            </c:catch>
            <s:if test="%{#locationExecption != null}">
              <c:out value="Error in Retrieving session attribute locationDetailsMap"/>
              <c:redirect url="/aascShipError.jsp">
                <c:param name="errorObj" value="SessionError"/>
              </c:redirect>
            </s:if>
            <s:set name="locationId" value="%{#session.location_id}"/>            
          </s:else>
          <s:set name="locationDetails" value="%{#aascGetLocationInfo.orgDetails}"/>
          <s:form name="aascCarrierShipActivityForm" method="POST" action="ReportDriverAction" onsubmit="return displayInfo('aascCarrierShipActivityForm')">
            <div class="row"><br/></div>
            <div class="row"><br/></div>
            <div class="row"><br/></div>
            <center>
           
            <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:80%">
                   <!--<fieldset class="scheduler-border" style="border-color:#19b698; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;">-->
                    <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <label style="color:#ffffff;font-size: 20px;">Manifest Summary Report</label>
                    </div>
                       <div class="row"><br/></div> 
                    
                    
                    <div class="row" id="divSub" style="width:80%; margin-left:12%;">
                        <div class="col-sm-12">
                                <s:set name="locationDetailsMap" value="%{#session.locationDetailsMap}"/>
                                <s:if test="%{#roleId == 2}">
                                     <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('ClientDetails')"/></span></label>
                                <div class="col-sm-5">
                                   <s:set name="disableFlag" value="true"/>
                            <s:select list="#clientDetailsHashMap" name="clientIdSelect" id="clientIdSelect" listKey="key" listValue="value"
                                  class="form-control"   size="1" onchange="getLocations(\'aascCarrierShipActivityForm\');shipmethajax(\'aascCarrierShipActivityForm\');"
                                            headerValue="Select" headerKey="" value="#clientId"/>
                            <s:hidden name="clientIdHidden" id="clientIdHidden" value="%{#clientId}"/>
                            </div>
                            </s:if>
                                    <s:else>
                                         <s:hidden name="clientIdHidden" id="clientIdHidden" value="%{#client_id}"/>
                                    </s:else>
                                   </div>
                               </div>
                               <div class="row"><br/></div>
                               
                      <div class="row" id="divSub" style="width:80%; margin-left:12%;">
                        <div class="col-sm-12">
                                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('Location')"/></span></label>
                                <div class="col-sm-5">
                                   <s:set name="disableFlag" value="true"/>
                                <s:if test="%{#roleId == 4}">
                                    <s:select list="#locationDetailsMap" name="locationIdSelect" id="locationIdSelect" listKey="key"
                                      class="form-control" listValue="value" size="1" headerValue="Select" headerKey="" value="#locationId"/>
                                    <s:hidden name="locationIdHidden" id="locationIdHidden" value="%{#locationId}"/>
                                </s:if>
                                 <s:else>
                                    <s:select list="#locationDetailsMap" name="locationIdSelect" id="locationIdSelect" listKey="key"
                                     class="form-control" listValue="value"  size="1" headerValue="Select" headerKey="" value="#locationId"/>
                                    <s:hidden name="locationIdHidden" id="locationIdHidden" value="%{#locationId}"/>
                                </s:else>
                                <s:hidden name="carrierId" id="carrierId" value="%{''}"/>
                                   </div>
                               </div>
                               </div>
                               <div class="row"><br/></div>

                        <div class="row" id="divSub" style="width:80%; margin-left:12%;">
                        <div class="col-sm-12">
                                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('CarrierName')"/></span></label>
                                <div class="col-sm-5">
                                   <s:select list="#{'-1':'ALL','100':'UPS', '110':'FEDEX EXPRESS', '111':'FEDEX GROUND', '999':'OTHERS'}" name="carrierSelect" id="carrierSelect"
                                    class="form-control"  size="1" onchange="getValueCarrier('aascCarrierShipActivityForm');shipmethajax('aascCarrierShipActivityForm');"
                                        listKey="key" listValue="value" headerValue="Select Carrier" value="#carrierIdStr"/>
                                   </div>
                               </div>
                               </div> 
                               <div class="row"><br/></div>
                        <div class="row" id="divSub" style="width:80%; margin-left:12%;">
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
                        <div class="row" id="divSub" style="width:80%; margin-left:12%;">
                        <div class="col-sm-12">
                                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('FromDate')"/></span></label>
                                <div class="col-sm-5" align="left">
                                   <s:textfield maxlength="10"  name="fromDate" id="fromDateID" value="%{#fromDate}" size="15"/>
                                   </div>
                               </div>
                               </div> 
                               <div class="row"><br/></div>
                         <div class="row" id="divSub" style="width:80%; margin-left:12%;">
                        <div class="col-sm-12">
                                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('ToDate')"/></span></label>
                                <div class="col-sm-5" align="left">
                                   <s:textfield maxlength="10" name="toDate" id="toDateID" value="%{#toDate}" size="15"/>
                                   </div>
                               </div>
                               </div>
                            <div class="row"><br/></div>   
                       <div class="row" id="divSub" style="width:80%; margin-left:12%;">
                        <div class="col-sm-12">
                                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('ReportType')"/></span></label>
                                <div class="col-sm-7" align="left">
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
                             <s:hidden name="actionType" value='%{\"CARRIERSHIPACTIVITY\"}'/>   
                             <button class="btn btn-success" name="submitButton" type="button"  onclick="openPopupForHTML('aascCarrierShipActivityForm');" alt="" align="middle"> Submit <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                            <!-- <button class="btn btn-danger" name="ClearBtn"   value="CloseButton" alt="" align="middle"> Clear <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>-->
           
           
                            <s:url action="ReportsAction" id="url">
                            <s:param name="requestType" value="%{\'CarrierShipActivity\'}"></s:param>
                          </s:url>
                        
                          <s:a href="%{url}" name="#CarrierShipActivity.action1">
                            <span class="btn btn-warning" >
                             Clear <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></span>

                          </s:a>

                            
                         </center> 
                         </div>
                         </br>
             
                 <!--   <table cellspacing="1" cellpadding="1" width="98%" align="center">
                
                      <s:set name="locationDetailsMap" value="%{#session.locationDetailsMap}"/>
                      <s:if test="%{#roleId == 2}">
                        <tr class="tableDataCell">
                          <td class="dispalyFields"><s:property value="getText('ClientDetails')"/></td>
                          <td align="left" nowrap="nowrap" class="dispalyFields">
                            <s:set name="disableFlag" value="true"/>
                            <s:select list="#clientDetailsHashMap" name="clientIdSelect" id="clientIdSelect" listKey="key" listValue="value"
                                      cssClass="inputs" size="1" onchange="getLocations(\'aascCarrierShipActivityForm\');shipmethajax(\'aascCarrierShipActivityForm\');"
                                            headerValue="Select" headerKey="" value="#clientId"/>
                            <s:hidden name="clientIdHidden" id="clientIdHidden" value="%{#clientId}"/>
                          </td>
                        </tr>
                      </s:if>
                      <s:else>
                          <s:hidden name="clientIdHidden" id="clientIdHidden" value="%{#client_id}"/>
                      </s:else>
                      <tr class="tableDataCell">
                        <td class="dispalyFields"><s:property value="getText('Location')"/></td>
                        <td align="left" nowrap="nowrap" class="dispalyFields">
                          <s:set name="disableFlag" value="true"/>
                          <s:if test="%{#roleId == 4}">
                            <s:select list="#locationDetailsMap" name="locationIdSelect" id="locationIdSelect" listKey="key"
                                      listValue="value" cssClass="inputs" size="1" headerValue="Select" headerKey="" value="#locationId"/>
                            <s:hidden name="locationIdHidden" id="locationIdHidden" value="%{#locationId}"/>
                          </s:if>
                          <s:else>
                            <s:select list="#locationDetailsMap" name="locationIdSelect" id="locationIdSelect" listKey="key"
                                      listValue="value" cssClass="inputs" size="1" headerValue="Select" headerKey="" value="#locationId"/>
                            <s:hidden name="locationIdHidden" id="locationIdHidden" value="%{#locationId}"/>
                          </s:else>
                          <s:hidden name="carrierId" id="carrierId" value="%{''}"/>
                        </td>
                      </tr>
       
                      <tr>
                        <td class="dispalyFields"><s:property value="getText('CarrierName')"/></td>
                        <td>
                          <s:select list="#{'-1':'ALL','100':'UPS', '110':'FEDEX EXPRESS', '111':'FEDEX GROUND', '999':'OTHERS'}" name="carrierSelect" id="carrierSelect"
                                    cssClass="inputs" size="1" onchange="getValueCarrier('aascCarrierShipActivityForm');shipmethajax('aascCarrierShipActivityForm');"
                                        listKey="key" listValue="value" headerValue="Select Carrier" value="#carrierIdStr"/>
                        </td>
                      </tr>
                      <tr>
                        <td class="dispalyFields"><s:property value="getText('ShipMethod')"/></td>
                        <td>
                          <s:if test="%{#inputNumber == null}">
                            <s:set name="inputNumber" value="%{''}"/>
                          </s:if>
                          <s:hidden name="shipMethod" id="shipMethod" value="%{#inputNumber}"/>
                          <s:select list="inputNumber" name="shipMethodList" id="shipMethodList" size="1" cssClass="inputs" 
                                onchange="getValueCarrier(\'aascCarrierShipActivityForm\');" value="%{#inputNumber}" headerValue="Select Ship Method"/>
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
                          <s:hidden name="submitButton" value="%{\'\'}"/>
                          <s:hidden name="actionType" value='%{\"CARRIERSHIPACTIVITY\"}'/>
                          <s:a href="#"  onclick="openPopupForHTML('aascCarrierShipActivityForm');">
                            <img src="buttons/aascSubmit1.jpg" alt="" name="submitButton" border="0"></img>
                          </s:a>
                          &nbsp;&nbsp;
                          <s:url action="ReportsAction" id="url">
                            <s:param name="requestType" value="%{\'CarrierShipActivity\'}"></s:param>
                          </s:url>
                          <s:a href="%{url}" name="#CarrierShipActivity.action1">
                            <img src="buttons/aascClear1.png" name="ClearBtn" alt="" border="0"></img>
                          </s:a>
                        </td>
                      </tr>
                    </table>-->
              
              <!--  </fieldset>-->
                  
            </div>
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
