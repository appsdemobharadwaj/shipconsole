
 <%/*=======================================================================================================+
|  DESCRIPTION                                                                                             |
|    JSP For Package Dimensions Details                                                                    |                                                       |
|    Version - 1                                                                                           |       
|    20/11/2014   Sunanda.K     Added code from ShipConsoleCloud version1.2                                |
|    17/12/2014   Eshwari M     Merged Sunanda code and removed harcodings by getting from properties file |
|    29/12/2014   Pradeep Y     Fixed all issues in tracking page for all roles and arranged code in proper|
|                               allignment.                                                                |
|    02/01/2015   Pradeep Y     Changed shipToCompanyName to customerName.
|    07/01/2015   Pradeep Y     Corrected footer name.
|    19/01/2015   Pradeep Y     Removed commented code.
     19/01/2105   Pradeep Y     Added code to get Ship To Location name. For bug #2537.
     05/03/2015    Sanjay & Khaja Added code for new UI changes.
     10/03/2015   Y Pradeep     Added missing name for param tag
     14/03/2015   Eshwari M     Aligned code and added setActionType() to make tracking functionality workable
     15/03/2015   Y Pradeep     Removed unused code and modified colors for address section.
     18/03/2015   Y Pradeep     Added code to opening WayBill details in new window.
     20/03/2015   Y Pradeep     Modified code to change look and functionality of Shipment Tracking Date.
     24/03/2015   Y Pradeep     Removed Tracking Number text from Footer. Bug #2700.
     01/04/2015   Y Pradeep     Added formtarget="_blank" for all s:submit buttons to open in new window. Bug #2766.
     10/04/2015   Y Pradeep     Added code to display today's date in Date field. Bug #2819.
     23/04/2015   Y Pradeep     Removed footer and added code to avoid conflicts in jquery for new calendar.
     27/05/2015   Y Pradeep     Modified code to display and allow Order Numbers with special characters(Encode and Decode).
     11/06/2015   Suman G       Removed fevicon to fix #2992
     24/06/2015   Naresh        Modified the code for fix #3075
     25/06/2015   Rakesh K      Increased the size of Tracking text.   
     25/06/2015   Suman G       Changed hidden field position to fix #3073
     13/07/2015   Dinakar G     Aligined UI as per tab
     17/07/2015   Y Pradeep     Setting 'Go' to the mainActionType variable instead of '' when clicked on Go button. Bug #3147.
     24/07/2015  Rakesh K    Modified Code to work application in offline.
     03/08/2015   Dinakar G     Modified the code for fix #3288
     04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
     19/08/2015   Dinakar G     Modified the code for fix #3425
     26/08/2015  Rakesh K       Added code to solve 3496.
     31/08/2015  Suman G        Added code for Trial User modifications
     20/11/2015  Suman G        Added code to restrict user to track DHL & Stamps carriers.
+==========================================================================================================*/%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
  
  <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
     <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
       <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />-->
  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:property value="getText('SCTrackingPage')" /></title>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <script src="aasc_tracking_js.js" type="text/javascript"> </script>
    <script type="text/javascript">
        var j = jQuery.noConflict();
        j(document).ready(function() {
            // create DatePicker from input HTML element
            j("#ShipmentTrackDateID").kendoDatePicker({
            parseFormats:["MM-dd-yyyy"],
            format: "MM-dd-yyyy"
            }).data("kendoDatePicker");
        });
    </script>
        <style type="text/css">
        .href {color: #003399}  
        html{height: 100%;} 
    .style1 {FONT-FAMILY: Tahoma;
    font-size: 12px;
    font-weight: bold;}
    strong {
    font-size: 12px;
    }
    #FormPod,#trackingNumber{
    margin-top:6px;
    }
    #sAlign{
    text-align:justify;
    }
    </style>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
 <!--   <link rel="shortcut icon" href="/kendo-ui/favicon.ico"/> -->
    
  </head>
  <body class="gradientbg" onload="getLocations();getOrderNumbers();getLocationValue();">
  <!--<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0"> -->
	<div>
      <%@ include file="aascIndexHeader.jsp"%>
      </div>
    
  
   <!-- </div>-->
  <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
  
            
        <% 
          String url = request.getContextPath();
          if(session.isNew())
          {
             response.sendRedirect(url+"/aascShipError.jsp");
    
          }
          java.sql.Date shipmentDate = null;
        %>
		<%
            String inputNumber = (String)request.getAttribute("inputNumberText");
            pageContext.setAttribute("inputNumber",inputNumber);
            
        %>
        
        <jsp:useBean id="date" class="java.util.Date"/>
        <fmt:formatDate var="sysdate" value="${date}" pattern="MM-dd-yyyy"/>
        <c:set var="trackDate" value="${requestScope.trackDate}"/>        
        <c:if test="${trackDate == '' || trackDate == null}">
          <c:set var="trackDate" value="${sysdate}"/>
        </c:if>
        <c:set var="inputNumberType" value="${param.InputTypeSelect}"
               scope="page"/>
        <s:set name="inputNumberType" value="%{#attr.inputNumberType}"/>
        <s:set name="inputNumber" value="%{#attr.inputNumber}"/>
        <c:set var="clientId" value="${param.clientIdSelect}" scope="page"/>
        <s:set name="clientId" value="%{#attr.clientId}"/>
        <s:if test="%{#clientId == 0}">
          <s:set name="clientId" value="%{''}"/>
        </s:if>
        <c:set var="locationId" value="${param.locationIdSelect}"
               scope="page"/>
        <s:set name="locationId" value="%{#attr.locationId}"/>
        <s:set name="userId" value="%{#attr.user_id}"/>
        <s:hidden name="userId" id="userId" value="%{#userId}" />
        <s:set name="inputNumberList2" value="%{''}"/>
        <s:set name="inputNumberList1" value="%{#request.inputNumberList}"/>
        <s:set name="reportResp" value="%{10}"/>
        <s:set name="role_id" value="%{0}"/>
        <s:set name="client_id" value="%{0}"/>
        <s:set name="ip_location_id" value="%{0}"/>
        <c:catch var="Execption1">
          <s:set name="aascTrackingOrderInfo"
                 value="%{#session.TrackingInfo}"/>
          <s:set name="locationId" value="%{#session.location_id}"/>
          <s:set name="role_id" value="%{#session.role_id}"/>
          <s:set name="client_id" value="%{#session.client_id}"/>
        </c:catch>
        <c:if test="${Execption1 != null}"></c:if>
        <s:set name="locationId1" value="%{#request.locationValue}"/>
        <c:catch var="Execption3">
          <s:set name="locationIdStr"
                 value="%{#locationId1.substring(0,1)}"/>
        </c:catch>
        <s:if test="%{#Execption3 != null}">
          <s:set name="locationId1" value="%{''}"/>
        </s:if>
        <s:if test='%{#locationId1 == "" || #locationId1 == null}'>
          <s:set name="locationId1" value="%{#request.locationValue}"/>
        </s:if>
        <s:else>
          <s:set name="locationId" value="%{#locationId1}"/>
        </s:else>
        <c:catch var="customerException">
          <s:set name="clientDetailsHashMap" value="%{#session.clientDetailsHashMap}"/>
        </c:catch>
        <s:if test="%{#customerException != null}" >
            <s:bean name="java.util.HashMap" id="clientDetailsHashMap">
                <s:param  name="clientDetailsHashMap" value="%{'select'}"/>
            </s:bean>
        </s:if>
        <s:if test="%{#customerExecption != null}">
          <%  response.sendRedirect(url+"/aascShipError.jsp"); %>
        </s:if>
        <c:catch var="locationDetailsMapException">
          <s:set name="locationDetailsMap" value="%{#session.locationDetailsMap}"/>
        </c:catch>
        <s:if test="%{#locationDetailsMapException != null}" >
            <s:bean name="java.util.HashMap" id="locationDetailsMap">
                <s:param  name="locationDetailsMap" value="%{'select'}"/>
            </s:bean>
        </s:if>
        <s:form name="SigTrackingPageMainForm" method="POST" action="TrackingInformationAction" onsubmit="return displayInfo()">
        <div class="row"><br/></div>
        <div class="row"><br/></div>
        <div class="row"><br/></div>
        <center>
             <div  class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:90%">
                   <!--<fieldset class="scheduler-border" style="border-color:#19b698; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;">-->
                    <div align="center" style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <label style="color:#ffffff;font-size: 20px;">Tracking</label>
                    </div>

                    <s:hidden id="roleId" name="roleId" value="%{#role_id}"/>
                    <div class="row"><br/></div> 
                        
                    
                     <div class="col-sm-12" id="divSub" >
                        <label for="inputEmail" class="control-label col-sm-12"> <span class="dispalyFields" > Select the Lists and Enter a Number then Click on 'Go' Button </span></label>
                        </div>  
                        <div class="row"><br/></div>
          
          
          
          
          <div class="row"><br/></div>
                    <div class="col-sm-12" id="divSub" >
                        <s:if test="%{#role_id == 2}">
                        
                        <label for="inputEmail" class="control-label col-sm-1"> <span class="dispalyFields" > <s:property value="getText('ClientDetails')"/><font color="red">* </font></span></label>
                        <s:set name="disableFlag" value="true"/>
                        <div class="col-sm-2">
                                <s:select list="#clientDetailsHashMap" name="clientIdSelect" id="clientIdSelect" 
                                   class="form-control" listKey="key" listValue="value"  size="1" onchange="getLocations();deleteNo();" headerValue="Select" headerKey="" value="#clientId"/>
                                <s:hidden name="clientIdHidden" value="%{#clientId}"/>
                                </div>
                            
                            </s:if>
                            <s:else>
                                <s:hidden name="clientIdHidden" id="clientIdHidden"
                                    value="%{#client_id}"/>
                            </s:else>
                             <br/><br/></br></br>
                            
                            <label for="inputEmail" class="control-label col-sm-1"> <span class="dispalyFields" > <s:property value="getText('Location')"/> </span> </label>
                            <s:hidden name="locationValue"/>
                        <div class="col-sm-2">
                            <s:set name="disableFlag" value="true"/>
                            <s:select list="#locationDetailsMap" name="locationIdSelect" id="locationIdSelect" cssClass="form-control"
                                listKey="key" listValue="value"  size="1" headerValue="Select" headerKey="" value="#locationId" onchange="getOrderNumbers();getLocationValue();deleteNo();"/>
                            <s:hidden name="locationIdHidden" id="locationIdHidden" value="%{#locationId}"/>
                                <s:hidden name="mainActionType" id="mainActionType" value="%{''}" />
                        </div>
                        <div class="col-sm-2">
                            <s:select list="#{'OrderNumber':'Order Number','TrackingNumber':'Tracking Number'}" id="InputTypeSelect" 
                             class="form-control"  name="InputTypeSelect" value="#inputNumberType" size="1" onchange="getOrderNumbers();"/>
                        </div>
                      
                        <label for="inputEmail" class="control-label col-sm-1"> <span class="dispalyFields" > <s:property value="getText('Date')"/> </span> </label>
                        <div class="col-sm-2">
                            <input style="width: 95%;" type="text" placeholder="MM-DD-YYYY" name="ShipmentTrackDate" value="${trackDate}" id="ShipmentTrackDateID" maxlength="10" onchange="getOrderNumbers()" />
                        </div>
                        
                        <div class="col-sm-2">
                            <c:if test="${inputNumber == null}">
                                <c:set var="inputNumber" value="${'Select'}"/>
                            </c:if>
                            <select name="inputNumberText" id="inputNumberText" size="1" onkeydown="fnKeyDownHandler(this, event);" onkeyup="fnKeyUpHandler_A(this, event); return false;" 
                               class="form-control" onkeypress="return fnKeyPressHandler_A(this, event);" onchange="fnChangeHandler_A(this, event);" onblur="disableGo();" >
                            <option value="<c:out value='${inputNumber}'/>" selected="selected"><c:out value="${inputNumber}"/></option>
                            </select>
							<s:hidden name="orderNameEncode" id="orderNameEncode" />
                        </div>
                        <div class="col-sm-2">
                        <s:hidden name="orderNameEncode" id="orderNameEncode" />
                        <input type="HIDDEN" name="orderName" id="orderName" value="<c:out value='${inputNumber}'/>"></input>
                         <button class="btn btn-primary" name="GoButton" id="goButton1" onclick="setActionType('Go');validateInfo();" value="Go"> Go <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                       
                     <!--    <s:hidden name="orderNameEncode" id="orderNameEncode" />
                 <input type="HIDDEN" name="orderName" id="orderName" value="<c:out value='${inputNumber}'/>"></input>
                 <input name="GoButton" id="goButton1" type="image" value="Go" src="buttons/aascGo1.JPG" alt="[submit]" align="middle" onclick="setActionType('');validateInfo();"></input>
               -->
                        
                        </div>
                        
                    </div>
                    <div class="row"><br/></div>
                    <div class="row"><br/></div>
                   <s:set name="inputNumberList2" value="%{''}"/>
                        <s:if test='%{#inputNumber != null && #inputNumberType == "OrderNumber" && #inputNumberList2 == ""}'>
                            <c:catch var="Exception5">
                            <s:set name="orderList" value="%{#aascTrackingOrderInfo.orderList}"/>
                            </c:catch>
                            <s:if test="%{Exception5 != null}">
                            <c:out value="Exception when getting list of order names"/>
                        </s:if>
                            <s:if test="%{#orderList != null && #orderList.length() > 1}">
                                <s:set name="inputNumberList1" value="%{#request.inputNumberList}"/>
                             <div align="right">
                                 <strong><s:property value="getText('SelectDelivery')"/></strong>
                             </div>
                            <div>
                                <s:set name="inputNumberList2" value="%{#deliveryIterator.orderNumber}"/>
                                <s:select list="orderList" name="inputNumberList" id="inputNumberList" listKey="" 
                                   class="form-control" listValue="inputNumberList2" headerKey="" headerValue="select" size="1" />
                                <button class="btn btn-primary" name="GoButton" id="goButton1" onclick="setActionType('');validateInfo();" value="Go" alt="[submit]" align="middle"> Go <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                             <br/>
                             </div>
                            </s:if>
                            </s:if>
                            <div class="row"><br/></div> 
                            <div class="row"><br/></div>
                            <div class="row"><br/></div>
                     <p>
                <c:catch var="Exception6">
                    <c:set var="inputNumber1" value="${fn:substring(inputNumber,0,1)}"/>
                </c:catch>
                <c:if test="${Exception6 != null}">
                    <c:set var="inputNumber" value=""/>
                </c:if>
                    </p>
                <c:if test="Exception7 != null">
                <c:out value="Exception in aascTrackingPageMain.jsp"/>
                </c:if>
            
                <c:catch var="Exception7">
                <s:if test='%{(!(#inputNumber == "") || !(#inputNumberList1 == "")) && #inputNumberType != null && #aascTrackingOrderInfo !=null}'>
                    <s:set name="aascHeaderInfo" value="%{#aascTrackingOrderInfo.headerInfo}"/>
                    <s:set name="packageList" value="%{#aascTrackingOrderInfo.packageInfo}"/>
                    <s:if test="%{(#aascHeaderInfo!=null) && (#packageList!=null) }">
                    <div align="center" style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <label style="color:#ffffff;padding-left:10px;font-size: 12px;margin-top:5px">Header Information</label>
                    </div>
                     <div class="row"><br/></div> 
                    
                    <div class="row" id="divSub" >
                        
                        <div class="col-sm-12">
                        <div class="col-sm-2">
                        </div>
                        <div class="col-sm-4" align="left">
                           
                        <label  class="control-label col-sm-6"  > <span class="dispalyFields" > <s:property value="getText('OrderNumber')"/></span></label>
                        
                            <label for="inputEmail" class="control-label col-sm-6" > <span class="dispalyDBFieldsNew" > <s:property value="%{#aascHeaderInfo.orderNumber}"/></span></label>
                        
                        </div>
                        <div class="col-sm-4" align="left">
                        <label for="inputEmail" class="control-label col-sm-6"  > <span class="dispalyFields" > <s:property value="getText('Waybill')"/></span></label>
                       
                        <div class="col-sm-6" align="left">
                            <s:form name="FormTrackingWayBill" id="FormTrackingWayBill" action="TrackingInformationAction" method="post" >
                                      
                                      <s:submit name="wayBillButton" id="wayBillButton" value="%{#aascHeaderInfo.wayBill}"  onclick="return setActionType('WAYBILL');" formtarget="_blank"/>
                                     <!-- <button class="btn btn-default" name="wayBillButton"  id="wayBillButton"  value="%{'#aascHeaderInfo.wayBill'}"  onclick="setActionType('WAYBILL');"  formtarget="_blank"/>-->  
                                     <s:hidden name="actionType" id="actionType" value="%{'WAYBILL'}"/>
                                      <s:hidden name="shipMethod" id="shipMethod" value="%{#aascHeaderInfo.shipMethodMeaning}"/>
                                      <s:hidden name="orderNumber" id="orderNumber" value="%{#aascHeaderInfo.orderNumber}"/>
                                      
                                    </s:form>
                             </div>
                             </div>
                            
                            <div class="col-sm-2">
                            </div>
                            
                    </div>
                    <br/>
                    
                    
                    <div class="col-sm-12">
                    <div class="col-sm-2">
                        </div>
                        <div class="col-sm-4" align="left">
                        <label for="inputEmail" class="control-label col-sm-6" > <span class="dispalyFields" > <s:property value="getText('CustomerName')"/></span></label>
                        
                            <label for="inputEmail" class="control-label col-sm-6" > <span class="dispalyDBFieldsNew" > <s:property value="%{#aascHeaderInfo.customerName}"/></span></label>
                        </div>
                        <div class="col-sm-4" align="left">
                        <label for="inputEmail" class="control-label col-sm-6" > <span class="dispalyFields" > <s:property value="getText('ShipmentCost')"/></span></label>
                        
                            <label for="inputEmail" class="control-label col-sm-6"> <span class="dispalyDBFieldsNew" > <s:property value="%{#aascHeaderInfo.shipmentCost}"/></span></label>
                            
                         </div>
                         <div class="col-sm-2">
                        </div>
                    </div>
                            <s:set name="shipmentDate" id="shipmentDate" value="%{#aascHeaderInfo.shipmentDate}"/>
                                    <s:if test="%{#shipmentDate == null}">
                                     <%            
                                     java.util.Date today =  new java.util.Date();
                                     shipmentDate = new java.sql.Date(today.getTime());
                                     pageContext.setAttribute("shipmentDate",shipmentDate);
                                     %>
                                    </s:if>
                        
                    
                    <div class="col-sm-12">
                    <div class="col-sm-2">
                        </div>
                        <div class="col-sm-4" align="left">
                        <label for="inputEmail" class="control-label col-sm-6" > <span class="dispalyFields" > <s:property value="getText('ShipToLocation')"/></span></label>
                        
                            <label for="inputEmail" class="control-label col-sm-6" > <span class="dispalyDBFieldsNew" > <s:property value="%{#aascHeaderInfo.shipToLocationName}"/></span></label>
                        </div>
                        <div class="col-sm-4" align="left">
                        <label for="inputEmail" class="control-label col-sm-6" > <span class="dispalyFields" > <s:property value="getText('ShipmentDate')"/></span></label>
                        
                            <label for="inputEmail" class="control-label col-sm-6"> <span class="dispalyDBFieldsNew" > <s:property value="%{#shipmentDate}"/></span></label>
                        
                        </div>
                        <div class="col-sm-2">
                        </div>
                    </div>
                    
                    <div class="col-sm-12">
                    <div class="col-sm-2">
                        </div>
                        <div class="col-sm-4" align="left">
                        <label for="inputEmail" class="control-label col-sm-6" > <span class="dispalyFields" > Ship To</span></label>
                        
                            <label for="inputEmail" class="control-label col-sm-6" > <span class="dispalyDBFieldsNew" > 
                                 <s:property value="%{#aascHeaderInfo.shipToAddrLine1}"/>
                                    ,
                                    <br/>
                                    <s:if test='%{#aascHeaderInfo.shipToAddrLine2 != ""}'>
                                    <s:property value="%{#aascHeaderInfo.shipToAddrLine2}"/>
                                    ,
                                    <br/>
                                    </s:if>
                                    <s:if test='%{#aascHeaderInfo.shipToAddrLine3 != ""}'>
                                    <s:property value="%{#aascHeaderInfo.shipToAddrLine3}"/>
                                    ,
                                    <br/>
                                    </s:if>
                                    <s:property value="%{#aascHeaderInfo.shipToAddressCity}"/>
                                    ,
                                    <s:property value="%{#aascHeaderInfo.shipToAddressState}"/>
                                    ,
                                    <s:property value="%{#aascHeaderInfo.shipToPostalCode}"/>
                                    ,
                                    <s:property value="%{#aascHeaderInfo.shipToCountry}"/>
                            
                            </span></label>
                           </div>
                           <div class="col-sm-4" align="left">
                        <label for="inputEmail" class="control-label col-sm-6" > <span class="dispalyFields" > <s:property value="getText('ShipmentCarrier')"/></span></label>
                        
                            <label for="inputEmail" class="control-label col-sm-6"> <span class="dispalyDBFieldsNew" > <s:property value="%{#aascHeaderInfo.carrierName}"/></span></label>
                            
                            <s:hidden name="carrierName" id="carrierNameId" value="%{#aascHeaderInfo.carrierName}" />
                       
                        </div>
                        <div class="col-sm-2">
                        </div>
                    </div>
                    
                    <div class="col-sm-12">
                    <div class="col-sm-2">
                        </div>
                        <div class="col-sm-4">
                            
                        </div>
                        <div class="col-sm-4" align="left">
                        
                        <label for="inputEmail" class="control-label col-sm-6" > <span class="dispalyFields" > <s:property value="getText('ShipmentMethod')"/></span></label>
                        
                            <label for="inputEmail" class="control-label col-sm-6"> <span class="dispalyDBFieldsNew" > <s:property value="%{#aascHeaderInfo.shipMethodMeaning}"/></span></label>
                        
                        </div>
                        <div class="col-sm-2">
                        </div>
                    </div>
                    <s:form name="FormPod" id="FormPod" action="TrackingInformationAction" method="post" >
                        <div class="col-sm-12" id="divSub" >
                          <div class="col-sm-7" id="divSub" >
                          </div>
                          <div class="col-sm-3" id="divSub" >
                            <s:set name="carrier" id="carrier" value="%{#aascHeaderInfo.carrierName}"/>
                                      <s:if test='%{#carrier == "UPS"}'>
                                      <s:submit name="podButton" id="podButton" value="Show POD" src="buttons/aascPOD1.png" align="middle" onclick="setActionType('POD');" formtarget="_blank"/>
                                           
                                          <s:hidden name="actionType" id="actionType" value="%{'POD'}"/>
                                          <s:hidden name="shipMethod" id="shipMethod" value="%{#aascHeaderInfo.shipMethodMeaning}"/>
                                          <s:hidden name="wayBillButton" id="wayBillButton" value="%{#aascHeaderInfo.wayBill}"/>
                                          <s:hidden name="orderNumber" id="orderNumber" value="%{#aascHeaderInfo.orderNumber}"/>
                                        </s:if>
                            </div>
                            <div class="col-sm-2" id="divSub" >
                        </div>
                        </br></br>
                        </div>
                        
                    </s:form>
                    </div>
                    
                    <div style="width:100%;overflow-x: auto;">
                        <table width="70%" border="1" align="center">
                            <tr>
                              <td width="96" class="smallHeadings" align="center"><s:property value="getText('PackageID')"/></td>
                              <td width="180" class="smallHeadings" align="center"><s:property value="getText('UOM')"/></td>
                              <td width="112" class="smallHeadings" align="center"><s:property value="getText('Weight')"/></td>
                              <s:if test='%{#aascHeaderInfo.carrierName == "UPS"}'>
                              <td width="272" class="smallHeadings" align="center"><s:property value="getText('UPS')"/>&nbsp;<s:property value="getText('TrackingNumber')"/></td>
                              </s:if>
                              <s:else>
                               <td width="272" class="smallHeadings" align="center"><s:property value="getText('TrackingNumber')"/></td>
                              </s:else>
                              <s:if test='%{!(#aascHeaderInfo.carrierName == "UPS" || #aascHeaderInfo.carrierName == "Stamps.com" || #aascHeaderInfo.carrierName == "DHL" )}'>
                                <td width="150" class="smallHeadings" align="center"><s:property value="getText('POD')"/> </td>
                              </s:if>
                            </tr>
                            <s:if test="%{#packageList != null}">
                             <s:set name="packageNumber" id="packageNumber" value="%{0}"/>
                             <s:iterator id="packageIterator" value="%{#packageList}">
                             <s:set name="packageNumber" id="packageNumber" value="%{#packageNumber+1}"/>
                             <s:set name="aascPackageInfo" id="aascPackageInfo" value="%{#packageIterator}"/>
                             <tr>
                               <td height="10" class="tableDataCell" align="center">
                                  <s:property value="%{#packageNumber}"/>
                               </td>
                               <td class="tableDataCell" align="center">
                                  <s:property value="%{#aascPackageInfo.uom}"/>
                               </td>
                               <td class="tableDataCell" align="center">
                                  <s:property value="%{#aascPackageInfo.weight}"/>
                               </td>
                               <td class="tableDataCell" align="center">
                                    <s:form name="FormTrackingNumber" id="FormTrackingNumber" action="TrackingInformationAction" method="post">
                                        <s:submit name="trackingNumber" id="trackingNumber" value="%{#aascPackageInfo.trackingNumber}"  onclick="return setActionType('TRACKING');" formtarget="_blank"/>
                                        <s:hidden name="actionType" id="actionType" value="%{'TRACKING'}"/>                 
                                        <s:hidden name="shipMethod" id="shipMethod" value="%{#aascHeaderInfo.shipMethodMeaning}"/>
                                        <s:hidden name="orderNumber" id="orderNumber" value="%{#aascHeaderInfo.orderNumber}"/>
                                    </s:form>
                                </td>
                                <s:if test='%{!(#aascHeaderInfo.carrierName == "UPS" || #aascHeaderInfo.carrierName == "Stamps.com" || #aascHeaderInfo.carrierName == "DHL")}'>
                                    <td class="tableDataCell" align="center">
                                              <!--Gururaj code end -->
                                        <s:form name="FormPod" id="FormPod" action="TrackingInformationAction" method="post" margin-top="5px" >
                                            <s:submit name="podButton" id="podButton2" value="Show POD" src="buttons/aascPOD1.png" align="middle" onclick="setActionType('POD');" formtarget="_blank"/>
                                            <s:hidden name="actionType" id="actionType" value="%{'POD'}"/>
                                            <s:hidden name="shipMethod" id="shipMethod" value="%{#aascHeaderInfo.shipMethodMeaning}"/>
                                            <s:hidden name="wayBillButton" id="wayBillButton" value="%{#aascPackageInfo.trackingNumber}"/>
                                            <s:hidden name="orderNumber" id="orderNumber" value="%{#aascHeaderInfo.orderNumber}"/>
                                        </s:form>
                                    </td>
                                </s:if>
                                 </tr>
                             </s:iterator>
                           </s:if>
                         </table>
                         </br>
                         </br>
                         </div>
                    
                    
                    </s:if>
                    
                        <s:else>
                             <s:if test='%{(#aascHeaderInfo == null) && (#packageList == null) && (#inputNumberList2 == "")}'>
                             <label for="inputEmail" class="control-label col-sm-2"> <span class="dispalyFields" > <s:property value="getText('TrackingDisplayMessage')"/></span></label>
                                
                            </s:if>
                        </s:else>
                    </s:if>
                    </c:catch> 
                  
                    <span id="txtMessage" class="displayMessage"></span>
          
          
       </div>
       
      </center>
      
        </s:form>
         <!--     <s:set name="inputNumberList2" value="%{''}"/>
            <s:if test='%{#inputNumber != null && #inputNumberType == "OrderNumber" && #inputNumberList2 == ""}'>
              <c:catch var="Exception5">
                <s:set name="orderList" value="%{#aascTrackingOrderInfo.orderList}"/>
              </c:catch>
            <s:if test="%{Exception5 != null}">
              <c:out value="Exception when getting list of order names"/>
            </s:if>
            <s:if test="%{#orderList != null && #orderList.length() > 1}">
               <s:set name="inputNumberList1" value="%{#request.inputNumberList}"/>
               <tr valign="middle">
                  <td height="44">
                     <div align="right">
                         <strong><s:property value="getText('SelectDelivery')"/></strong>
                     </div>
                  </td>
                  <td nowrap="noWrap">
                    <s:set name="inputNumberList2" value="%{#deliveryIterator.orderNumber}"/>
                    <s:select list="orderList" name="inputNumberList" id="inputNumberList" listKey="" 
                                    listValue="inputNumberList2" headerKey="" headerValue="select" size="1" cssClass="inputs"/>
                    <input name="GoButton" id="GoButton" type="image" value="Go" src="buttons/aascGo1.png" alt="[submit]" align="middle"></input>
                 </td>
                  <td nowrap="noWrap">&nbsp;</td>
               </tr>
            </s:if>
            </s:if>
          </table>
        </fieldset>
        <p>
         <c:catch var="Exception6">
           <c:set var="inputNumber1" value="${fn:substring(inputNumber,0,1)}"/>
         </c:catch>
         <c:if test="${Exception6 != null}">
           <c:set var="inputNumber" value=""/>
         </c:if>
        </p>
        <c:if test="Exception7 != null">
          <c:out value="Exception in aascTrackingPageMain.jsp"/>
        </c:if>
        <c:catch var="Exception7">
          <s:if test='%{(!(#inputNumber == "") || !(#inputNumberList1 == "")) && #inputNumberType != null && #aascTrackingOrderInfo !=null}'>
             <s:set name="aascHeaderInfo" value="%{#aascTrackingOrderInfo.headerInfo}"/>
             <s:set name="packageList" value="%{#aascTrackingOrderInfo.packageInfo}"/>
             <s:if test="%{(#aascHeaderInfo!=null) && (#packageList!=null) }">
             
               <div align="center" style="background-color:#7761a7;margin-top:10px;margin-left:5px;height:30px;font-weight:bold;font-size:12px;color:white;">
                       <label style="color:#ffffff;padding-left:10px;font-size: 14px;margin-top:5px">Header Information</label>
                    </div>
                    <div class="row"><br/></div>
                    <div class="col-md-12">
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-8" style="width:100%;overflow-x: auto;">
                    <table width="100%" border="0" cellpadding="0" font-size="16px" cellspacing="0" >            
                      <tr>
                         <td>
                            <table  align="center" class="table">
                               <tr>
                                  <td width="15%">
                                  </td>
                                  <td width="10%" align="left" valign="top"
                                      nowrap="noWrap" class="style1"><s:property value="getText('OrderNumber')"/></td>
                                  <td width="15%" align="left" valign="top"
                                      nowrap="noWrap" class="dispalyDBFieldsNew">
                                    <s:property value="%{#aascHeaderInfo.orderNumber}"/> 
                                  </td>
                                  <td width="10%" valign="top" nowrap="noWrap"
                                      class="style1">
                                    <strong><s:property value="getText('Waybill')"/></strong>
                                    
                                  </td>
                                  <td width="15%" valign="top" nowrap="noWrap"
                                      class="dispalyDBFieldsNew">
                                    <s:form name="FormTrackingWayBill" id="FormTrackingWayBill" action="TrackingInformationAction" method="post" >
                                      <s:submit name="wayBillButton" id="wayBillButton" value="%{#aascHeaderInfo.wayBill}" onclick="setActionType('WAYBILL');" formtarget="_blank"/>
                                      <s:hidden name="actionType" id="actionType" value="%{'WAYBILL'}"/>
                                      <s:hidden name="shipMethod" id="shipMethod" value="%{#aascHeaderInfo.shipMethodMeaning}"/>
                                      <s:hidden name="orderNumber" id="orderNumber" value="%{#aascHeaderInfo.orderNumber}"/>
                                    </s:form>
                               </td>
                               <td width="15%">
                               </td>
                               </tr>
                               
                               
                               
                               
                               
                               
                            </table>
                         </td>
                      </tr>
                      <tr>
                         <td height="20">
                         <table width="60%" border="1"  align="center">
                            <tr>
                              <td width="80" class="smallHeadings" align="center"><s:property value="getText('PackageID')"/></td>
                              <td width="140" class="smallHeadings" align="center"><s:property value="getText('UOM')"/></td>
                              <td width="100" class="smallHeadings" align="center"><s:property value="getText('Weight')"/></td>
                              <s:if test='%{#aascHeaderInfo.carrierName == "UPS"}'>
                              <td width="200" class="smallHeadings" align="center"><s:property value="getText('UPS')"/>&nbsp;<s:property value="getText('TrackingNumber')"/></td>
                              </s:if>
                              <s:else>
                               <td width="230" class="smallHeadings" align="center"><s:property value="getText('TrackingNumber')"/></td>
                              </s:else>
                              <s:if test='%{!(#aascHeaderInfo.carrierName == "UPS")}'>
                                <td width="150" class="smallHeadings" align="center"><s:property value="getText('POD')"/> </td>
                              </s:if>
                            </tr>
                            <s:if test="%{#packageList != null}">
                             <s:set name="packageNumber" id="packageNumber" value="%{0}"/>
                             <s:iterator id="packageIterator" value="%{#packageList}">
                             <s:set name="packageNumber" id="packageNumber" value="%{#packageNumber+1}"/>
                             <s:set name="aascPackageInfo" id="aascPackageInfo" value="%{#packageIterator}"/>
                             <tr>
                               <td height="10" class="tableDataCell" align="center">
                                  <s:property value="%{#packageNumber}"/>
                               </td>
                               <td class="tableDataCell" align="center">
                                  <s:property value="%{#aascPackageInfo.uom}"/>
                               </td>
                               <td class="tableDataCell" align="center">
                                  <s:property value="%{#aascPackageInfo.weight}"/>
                               </td>
                               <td class="tableDataCell" align="center">
                                    <s:form name="FormTrackingNumber" id="FormTrackingNumber" action="TrackingInformationAction" method="post" >
                                        <s:submit name="trackingNumber" id="trackingNumber" value="%{#aascPackageInfo.trackingNumber}"  onclick="setActionType('TRACKING');" formtarget="_blank"/>
                                        <s:hidden name="actionType" id="actionType" value="%{'TRACKING'}"/>                 
                                        <s:hidden name="shipMethod" id="shipMethod" value="%{#aascHeaderInfo.shipMethodMeaning}"/>
                                        <s:hidden name="orderNumber" id="orderNumber" value="%{#aascHeaderInfo.orderNumber}"/>
                                    </s:form>
                                </td>
                                <s:if test='%{!(#aascHeaderInfo.carrierName == "UPS")}'>
                                    <td class="tableDataCell" align="center">
                                              <!--Gururaj code end 
                                        <s:form name="FormPod" id="FormPod" action="TrackingInformationAction" method="post" >
                                            <s:submit name="podButton" id="podButton2" value="Show POD" src="buttons/aascPOD1.png" align="right" onclick="setActionType('POD');" formtarget="_blank"/>
                                            <s:hidden name="actionType" id="actionType" value="%{'POD'}"/>
                                            <s:hidden name="shipMethod" id="shipMethod" value="%{#aascHeaderInfo.shipMethodMeaning}"/>
                                            <s:hidden name="wayBillButton" id="wayBillButton" value="%{#aascPackageInfo.trackingNumber}"/>
                                            <s:hidden name="orderNumber" id="orderNumber" value="%{#aascHeaderInfo.orderNumber}"/>
                                        </s:form>
                                    </td>
                                </s:if>
                                 </tr>
                             </s:iterator>
                           </s:if>
                         </table>
                         </td>
                      </tr>
                     </table>
                     </div>
                     <div class="col-md-2">
                     </div>
                     </div>
                     <div class="row"><br/></div>
                    <br></br>
                    <table align="center" width="100%">
                        <tr>
                          <td align="left" width="15%">&nbsp;</td>
                          <td align="center" width="70%">
                              <s:if test='%{#carrier == "UPS"}'>
                                <font size="2" face="Arial, Helvetica, sans-serif">
                                  <s:property value="getText('UPSTrackingTerms1')" /> 
                                  <a href="aascUpsTermsAndConditions.jsp" target="_blank" style="color:black; font-weight:bold; text-decoration:underline; "><s:property value="getText('UPSTrackingTerms2')" />   </a>
                                </font>
                              </s:if>
                          </td>
                          <td align="right" width="15%">&nbsp;</td>
                        </tr>
                    </table>                    
                          
                
             <div class="row"><br/></div>
             </s:if>
             <s:else>
                <s:if test='%{(#aascHeaderInfo == null) && (#packageList == null) && (#inputNumberList2 == "")}'>
                  <span class="displayMessage"><s:property value="getText('TrackingDisplayMessage')" /></span>
                </s:if>
             </s:else>
          </s:if>
        </c:catch>
        <span id="txtMessage" class="displayMessage"></span>
        
        </div>
      </td>
        </div> -->
        
     </div> 

 <div  style="position:relative;top:200px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
                <br></br>
    </div>
    
    </body>

<%!
      /**
      * nullStringToSpace() method is used when the string is null it replaces with space.
      * @param object of type Object
      * @return object of type String.
      */  
      public String nullStringToSpace(Object object) {
        String spcStr = "";
        if (object == null) {
          return spcStr;
        }
        else {
          return object.toString();
        }
      }        
   %>

</html>
