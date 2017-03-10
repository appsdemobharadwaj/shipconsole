<%
/*========================================================================+
@(#)aascProfileOption.jsp 05/08/2014
* Copyright (c) 2014-2015 Apps Associates Pvt. Ltd. 
* All rights reserved.   
    24/12/2014  Jagadish Jain   Fixed all issues relaed to UI, Roles, Validations. Addded code for missed fileds for retriving and saving. Arranged code in proper order.
    13/01/2014  Y Pradeep       Fixed bug #2486 by adding headerValue to defaultPayMethod select field.
    15/01/2015  Y Pradeep       Merged Sunanda's code : getting title name from Application.Property file.
    20/01/2015  Y Pradeep       Modified code for Address Validationa dn Freight Shopping.
    20/02/2015  K Sunanda       Fixed bug #2582 by adding disasble and enable buttons method
    24/02/2015  Y Pradeep       Modified code for Address Validationa.
    26/02/2015  Suman G         Added code related to freight shop.
    26/02/2015  Y Pradeep       Modified code to change field names of username, password and account number common for Address Validation and Freight Shopping.
    10/03/2015  Jagadish        Added code for new UI changes.
    13/03/2015  K Suannda       Added missing out code for bug fix #2582 after the UI changes.
    15/03/2015  Y Pradeep       Removed lable path field from UI.
    17/03/2015  Y Pradeep       Renamed aascProfileOptionsBean to aascProfileOptions and clearing aascProfileOptionsBean from session.
    14/04/2015  Y Pradeep       Modified code display message in proper style, bug #2840. Modified code to display UI in proper order while navigating using tab button, bug #2809.
    15/04/2015  Suman G         Added session to fix #2743
    23/04/2015  Y Pradeep       Removed footer.
    03-06-2015  K Sunanda       Modified code for bug fix #2933
    03/06/2015  Y Pradeep       Added Weighing Scale selection field and check box for configuring Weighing Scale base on Location.
    08/06/2015  K Sunanda       Modified code for bug fix #2973
    09/06/2015  Y Pradeep       Modified code for proper allignment.
    11/06/2015  K Sunanda       Modified the below code for bug fix #2975
    11/06/2015  Suman G         Removed fevicon to fix #2992
    17/06/2015  Y Pradeep       Alligned weighing scale type fields in order.
    23/06/2015  Y Pradeep       Added code related to printer setup and details button in this page for configuring Printers based on lable formats.
    23/06/2015  Rakesh K        Added code for settings header.
    01/07/2015  Suman G         Added changes made by Padmavathi to fix #3122.
    13/07/2015  Y Pradeep       Uncommented code related to Printer Setups popup.
    14/07/2015  Rakesh K        Aligned for Role 1
    24/07/2015  Rakesh K        Modified Code to work application in offline.
    28/07/2015  Rakesh K        Modified Code to increase space between Weighing scale line and Printer SetUp line.
    04/08/2015  Rakesh K        Modified Code bootstrap css file work across all browsers.
    26/08/2015  Dinakar G       Added id's for Automation testing.
    26/08/2015  Rakesh K       Added code to solve 3496.
    21/12/2015  Y Pradeep       Modified code to disable username, password and account number fields for freight shopping and address validation.
    28/12/2015  Suman G         Commented code to remove customer name field.
+===========================================================================*/
/***
* JSP For Profile Options
* @version 1.0
* @author Jagadish Jain
* @history
*
*/
%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%> 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page errorPage="aascShipError.jsp" %> 


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
  
    <!--<meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
     <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>-->
    
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
   
    
    <title><s:property value="getText('SCProfileOptions')" /></title>
    
    <script type="text/javascript" SRC="aascProfileOptions_js.js"></script>
    
    <style type="text/css">
       
  .dropd{
			background-color: #FFFFFF;
			background-color: #FFF;
			text-decoration: none;
			width:50%; 
			height: 34px;
			font-size: 14px;
		
			border: 1px solid #CCC;		
  }
 
     </style>
<!-- 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="http://markusslima.github.io/bootstrap-filestyle/js/bootstrap-filestyle.min.js"> </script>-->
<script type="text/javascript">
function redirectToLogin(){
        
    document.getElementById("profileOptionsFormID").action = "login.action?actiontype=Logout";
    document.getElementById("profileOptionsFormID").submit();
        
}
</script>
    
 <!-- <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
   <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">-->
    
    
   <style type="text/css">
      html {
        height: 100%;
            }
    </style>
  </head>
  <body class="gradientbg" style="background-color:#ADADAD" onload="load();" >
  <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
    <jsp:useBean id="locationsMap" class="java.util.HashMap"/>
    <jsp:useBean id="carrierPayMethodsList" class="java.util.ArrayList"/>
     <jsp:useBean id="clientDetailsHashMap" class="java.util.HashMap"/>
    <s:set name="roleId" value="%{#attr.role_id}"/>
   
    <s:set name="aascProfileOptions" value="%{#attr.aascProfileOptionsBean}" />
    <s:set name="aascProfileOptionsBean" value="%{''}" scope="session"/>
    <s:set name="clientIdMain" value="%{''}" />
<s:set name="clientIdName" value="%{''}" />
<s:set name="clientIdNum" value="%{''}" />
<s:set name="clientChkCondition" value="%{''}" />
<s:if test="%{#roleId == 4}">
 <s:set name="invorgSess" value="%{#session.inv_organization_id}" />
 <s:set name="invOrgIdNum" value="%{#invorgSess}"/>
 <s:set name="clientIdNum" value="%{#session.client_id}"/>
 <s:set name="chkCondition" value="%{'allow'}" />
 <s:set name="clientChkCondition" value="%{'allow'}"/> 
</s:if>

<s:if test="%{#roleId == 3 || #roleId == 4}">
  <s:set name="clientIdMain" value="%{#session.client_id}" />
  <s:set name="clientIdNum" value="%{#session.client_id}"/>
  <s:set name="chkCondition" value="%{'allow'}" />
  <s:set name="clientChkCondition" value="%{'allow'}"/>
</s:if>

<s:if test="%{#roleId == 1 || #roleId == 2}">
   
      <s:set name="clientIdMain" value="%{#attr.clientId}" />
   
   <c:catch var="clientException">
     <s:if test="%{#clientIdMain == null || #clientIdMain == ''}">
       <s:set name="clientIdMain" value="%{''}" />
     </s:if>
   </c:catch>
   <s:if test="%{#clientException != null}">
     <s:set name="clientIdMain" value="%{''}" />
     <s:property value="clientException"/>
   </s:if>
   <s:if test="%{#clientIdMain == ''}">
       <s:set name="clientChkCondition" value="%{'notallow'}" />
   </s:if>
   <s:else>
       <s:set name="clientChkCondition" value="%{'allow'}" />
       <s:set name="clientIdNum" value="%{#clientIdMain}" />
       <s:if test="%{#aascProfileOptionsInfo.clientId != 0 && #aascProfileOptionsInfo.clientId != #clientIdNum}">
          <s:set name="clientIdNum" value="%{#aascProfileOptionsInfo.clientId}" />
       </s:if>
   </s:else>
</s:if>
     
    <c:catch var="customerException">
    <s:set name="clientDetailsHashMap" value="%{#session.clientDetailsHashMap}"/>
    </c:catch>
  <s:if test="%{#customerException != null}">
        <s:bean name="java.util.HashMap" id="clientDetailsHashMap">
            <s:param name="select" value="%{'select'}"/>
        </s:bean>
    </s:if>
            
    <c:catch var="exception2">
        <s:set name="locationsMap" value="%{#attr.locationDetailsMap}" />
    </c:catch>
    <s:if test="%{#exception2 != null}" >
        <s:bean name="java.util.HashMap" id="locationsMap">
            <s:param  name="select" value="%{'select'}"/>
        </s:bean>
    </s:if>
    
    <c:catch var="exception3">
        <s:set name="carrierPayMethodsList" value="%{#session.carrierPayMethods}"/>
    </c:catch>
    <s:if test="%{#exception3 != null}">
        <s:bean name="java.util.ArrayList" id="carrierPayMethodsList" >
            <s:param name="select" value="%{'select'}" />
        </s:bean>
    </s:if>
    
    <c:catch var="exception4">
        <s:set name="weightScaleNamesHashMap" value="%{#session.weightScaleNamesHashMap}"/>
    </c:catch>
    <s:if test="%{#exception4 != null}">
        <s:bean name="java.util.HashMap" id="weightScaleNamesHashMap" >
            <s:param name="select" value="%{'select'}" />
        </s:bean>
    </s:if>
    
    <s:include value="aascIndexHeader.jsp" />
    <s:hidden name="actionType" />
   <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
    
    <s:form class="form-horizontal" name="profileOptionsForm" id="profileOptionsFormID" action="ProfileOptionsAction" method="POST" role="form" onsubmit="return validation();" text-align="center">
    <div class="row">
     <s:hidden name="roleId" value="%{#roleId}" />
     
       </div>
        
            <br/>
           
            <s:set name="key" value="%{#request.key}"/>
             <s:if test="%{#key != null}">		        
                 <s:if test="{#key == 'aasc180'}">  <div align="left" class="displayMessage1" id="displayMessage"> <s:property value="getText(#request.key)"/>     </div>  </s:if>
              <s:else>  
              <div align="left" id="displayMessage"> 
              
              <s:property value="getText(#request.key)"/> 
              
              </div> 
              
              </s:else>                    
                <s:set name="key" value="%{''}" />
             </s:if>
            
        <center>  
                    
                    
           <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:90%">
          
          <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <label style="color:#ffffff;font-size: 20px;"><s:property value="getText('ProfileOptions')"/></label>
                    </div>
                    </br>
        <div class="row">
        <div class="col-sm-12">
             <s:if test="%{#roleId == 1 || #roleId == 2}">
            <div class="col-sm-4" style="margin-top: 8px;" align="right">
                <STRONG style="font-size:12px"><s:property value="getText('ClientDetails')"/></STRONG> 
            </div>
            <div class="col-sm-4">
                      <s:if test='%{#clientChkCondition == "notallow"}'>
                        <s:select name="clientId" id="clientId" class="form-control" size="1" onchange="onPOClientChange();getPOLocations();"
                    list="#clientDetailsHashMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey=""/> 
                      </s:if>
                      <s:else>
                        <s:select name="clientId" id="clientId" class="form-control" size="1" onchange="onPOClientChange();getPOLocations();"
                          list="#clientDetailsHashMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey=""  value="#clientIdMain" /> 
                      </s:else>
                    
            </div>
            <div class="col-sm-4"></div>
            </s:if>
            </div>
        </div>
        <br/>
        <div class="row">
           <div class="col-sm-12"> 
            <div class="col-sm-4" style="margin-top: 6px;" align="right">
                <label class="control-label" style="font-size:12px;margin-top:0px" for="name"><s:property value="getText('Location')"/></label>   
             </div>  
                <s:if test="%{#roleId == 1 || #roleId == 2}">
                <div class="col-sm-4">
                    <s:select name="locationId" id="locationID" class="form-control" size="1" 
                          list="#locationsMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey=""/> 
                          </div>
                </s:if>
                <s:else>
                <div class="col-sm-4">
                    <s:select name="locationId" id="locationID" class="form-control" size="1" onchange="disableSave();" 
                          list="#locationsMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey=""  value="%{#aascProfileOptions.locationId}" /> 
                </div>
                </s:else>
                <s:hidden name="actionType" />
                <s:if test="%{#roleId == 1 || #roleId == 2 || #roleId == 3 || #roleId == 4}">
                    <div class="col-sm-4" align="left">
                      <button class="btn btn-primary" name="GoButton"  id="GoButton"  onclick="document.profileOptionsForm.actionType.value='Go'; enableSave();" value="Go"  alt="[submit]" align="middle"> Go <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                    </div>
                
                </s:if>
                </div>
                </div>
                <br/>
           <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <label style="color:#ffffff;padding-left:10px;font-size: 12px;margin-top:5px">Settings</label>
                    </div>
                    </br>         
                    
          <div class="row" id="divSub" style="width:100%; ">
            <div class="col-sm-12">
            <br/>
                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('CarrierPayMethod')"/></span></label>
                <div class="col-sm-3">
                      <s:select name="defaultPayMethod" id="defaultPayMethodID" class="form-control" size="1" 
                                    list="#carrierPayMethodsList" value="%{#aascProfileOptions.defaultPayMethod}" headerKey="" headerValue="--Select--"   /> 
                </div>
                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('CompanyName')"/></span></label>
                <div class="col-sm-3">
                      <s:textfield name="companyName" id="companyNameID" class="form-control" value="%{#aascProfileOptions.companyName}" />
                </div>
               </div>    
            </div>
            <div class="row"><br/></div>
            <div class="row" id="divSub" style="width:100%;">
            <div class="col-sm-12">
                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('EnableSaturdayDelivery')"/></span></label>
                <div class="col-sm-3" align="left">
                      <s:hidden name="enableSaturdayFlagHidden" value="%{#aascProfileOptions.enableSaturdayFlag}" />
                                <s:checkbox name="enableSaturdayFlag"  id="enableSaturdayFlagID" value="%{#aascProfileOptions.enableSaturdayFlag}" />
                </div>
                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('EditShipToAddress')"/><s:property value="%{' '}" /></span></label>
                <div class="col-sm-3" align="left">
                      <s:hidden name="editShipToAddressHidden" value="%{#aascProfileOptions.editShipToAddress}" />
                                <s:checkbox  name="editShipToAddress" id="editShipToAddressID" value="%{#aascProfileOptions.editShipToAddress}" />
                               <a href="#" class="btn btn-primary" style="margin-left:10px;" onclick="javascript:onClickEditShipToDetails();" id="editAllShipToID" name="editAllShipTo"> Details </a>

                         <!--       <button class="btn btn-primary" name="editAllShipTo" id="editAllShipToID" onclick="onClickEditShipToDetails();" value="Details" alt="" align="middle"> Details <span class="badge"><span class="glyphicon glyphicon-open"></span></span></button> 
                                
                                <s:hidden name="customerNameFlag" id="customerNameFlagID" value="%{#aascProfileOptions.customerNameFlag}" /> -->
                                <s:hidden name="addrLinesFlag" id="addrLinesFlagID" value="%{#aascProfileOptions.addrLinesFlag}" />
                                <s:hidden name="cityFlag"  id="cityFlagID" value="%{#aascProfileOptions.cityFlag}" />
                                <s:hidden name="stateFlag" id="stateFlagID" value="%{#aascProfileOptions.stateFlag}" />
                                <s:hidden name="postalCodeFlag" id="postalCodeFlagID"  value="%{#aascProfileOptions.postalCodeFlag}" />
                                <s:hidden name="countryCodeFlag" id="countryCodeFlagID" value="%{#aascProfileOptions.countryCodeFlag}" />
                                <s:hidden name="editShipToDBFlag" value="%{#aascProfileOptions.editShipToAddress}"/> <!--sunanda modified for bug fix #2973-->
                                <s:hidden name="editShipToSaveStatus"  id="editShipToSaveStatusID" value="%{'N'}" /> <!--sunanda modified for bug fix #2933-->
                </div>
               </div>    
            </div>
            <div class="row"><br/></div>
            <div class="row" id="divSub" style="width:100%;">
            <div class="col-sm-12">
                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('Reference1Mandatory')"/>?</span></label>
                <div class="col-sm-3" align="left">
                      <s:checkbox name="reference1"  id="reference1ID"   value="%{#aascProfileOptions.reference1}" />                         
                                <s:hidden name="reference1Hidden" value="%{#aascProfileOptions.reference1}" />
                </div>
                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('Reference2Mandatory')"/>?</span></label>
                <div class="col-sm-3" align="left">
                      <s:checkbox name="reference2"  id="reference2ID"    value="%{#aascProfileOptions.reference2}" />    
                                <s:hidden name="reference2Hidden" value="%{#aascProfileOptions.reference2}" />
                </div>
               </div>    
            </div>

<div class="row"><br/></div>

        <div class="row" id="divSub" style="width:100%;">
            <div class="col-sm-12">
              <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('WeighingScale')"/></span></label>
                <div class="col-sm-3" align="left">
                   <s:set name="flag" value="%{''}"/>
                    <s:if test='%{#aascProfileOptions.weighingScale == "Y"}'>
                       <s:set name="flag" value="%{'true'}"/>
                    </s:if>
                    <s:else>
                       <s:set name="flag" value="%{'false'}"/>
                   </s:else>
                   <s:checkbox name="weighingScale" id="weighingScaleId" onclick="checkBoxValue(this)" value="#flag" fieldValue="%{#aascProfileOptions.weighingScale}"/>     
                </div>
               <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('WeightScaleName')"/></span></label>
                <div class="col-sm-3">
                    <s:select name="weighingScaleName" id="weighingScaleNameId" cssClass="form-control" size="1" 
                        list="#weightScaleNamesHashMap" value="%{#aascProfileOptions.weighingScaleName}" listKey="key" listValue="value" 
                            headerKey="" headerValue="--Select--"/> 
                </div>
            </div>
        </div>
        <div class="row"><br/></div>
        <!--Commented for now.-->
        <div class="row" id="divSub" style="width:100%; ">
                <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> 
                        <span class="dispalyFields"> <s:property value="getText('PrinterSetup')"/> </span>
                    </label>
                    <div class="col-sm-3" align="left">
                        <a href="#" class="btn btn-primary" style="margin-left:10px;" onclick="javascript:onClickPrinterSetup();" id="printerSetupID" name="printerSetup">  <s:property value="getText('PrinterDetails')"/> <span class="badge"><span class="glyphicon glyphicon-open"></span></span></a>
                    </div>
                    <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> 
                        <span class="dispalyFields"><s:property value="getText('ShipperName')"/> </span>
                    </label>
                    <div class="col-sm-3" align="left">
                        <s:textfield name="shipperName" id="shipperNameID" class="form-control" value="%{#aascProfileOptions.shipperName}" />
                    </div>
                </div>
        </div>
            <div class="row"><br/></div>
            <div class="row" id="divSub" style="width:100%;">
            <div class="col-sm-12">
                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields">
                        <s:property value="getText('AddressValidation')"/>
                               
                                <s:if test='%{#aascProfileOptions.addresValidation == "Y"}' >
                                    <s:set name="flagChk" value="%{'true'}" />
                                </s:if>
                                <s:else>
                                    <s:set name="flagChk" value="%{'false'}" />
                                </s:else>
                        
                                <s:if test='%{#aascProfileOptions.freightShopping == "Y"}' >
                                    <s:set name="fsFlagChk" value="%{'true'}" />
                                </s:if>
                                <s:else>
                                    <s:set name="fsFlagChk" value="%{'false'}" />
                                </s:else>
                </span></label>
                <div class="col-sm-3" align="left">
                      <s:checkbox name="addresValidation"  id="addresValidationId"  fieldValue="%{#aascProfileOptions.addresValidation}"  
                                        value="#flagChk" onchange="changeAV()" />
                </div>
                <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('FreightShopping')"/></span></label>
                <div class="col-sm-3" align="left">
                      <s:checkbox name="freightShopping"  id="freightShoppingID"  value="#fsFlagChk" fieldValue="%{#aascProfileOptions.freightShopping}" 
                                        onchange="changeFS()" />
                                <s:hidden name="fsFlagHidden" value="%{#aascProfileOptions.freightShopping}" />
                </div>
               </div>    
            </div>
            <!-- vikas added code for masking the carrier account number if the pofile options is checked -->
             <div class="row"><br/></div>
            <div class="row" id="divSub" style="width:100%;">
            <div class="col-sm-12">
             <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('Mask Account Number')"/>
              <s:if test='%{#aascProfileOptions.maskAccount == "Y"}' >
                <s:set name="maFlagChk" value="%{'true'}" />
                </s:if>
                 <s:else>
                   <s:set name="maFlagChk" value="%{'false'}" />
                     </s:else>
             </span></label>
                <div class="col-sm-3" align="left">
                      <s:checkbox name="maskAccount"  id="maskAccountID"  value="#maFlagChk" fieldValue="%{#aascProfileOptions.maskAccount}" onchange="changeMA()" />
                                <s:hidden name="maFlagHidden" id="maFlagHiddenID" value="" />
                </div>
             </div>     
           </div>
           <!-- vikas code ended -->
            <!--<div class="row"><br/></div>
            <div id="addressValidationTableId">
            <div class="row" id="divSub" style="width:60%;" align="center";>
            <div class="col-sm-12">
                <label for="inputEmail" class="control-label col-sm-6" style="text-align: left;padding-left: 60px;"> <span class="dispalyFields"><s:property value="getText('UserName')"/></span></label>
                <div class="col-sm-6">
                      <s:textfield name="wsUserName" id="wsUserName" class="form-control" value="%{#aascProfileOptions.wsUserName}" />
                </div>
               </div>    
            </div>
            <div class="row"><br/></div>
            <div class="row" id="divSub" style="width:60%; align="center";">
            <div class="col-sm-12">
                <label for="inputEmail" class="control-label col-sm-6" style="text-align: left;padding-left: 60px;"> <span class="dispalyFields"><s:property value="getText('Password')"/></span></label>
                <div class="col-sm-6">
                      <s:textfield name="wsPassword" id="wsPassword"  class="form-control" value="%{#aascProfileOptions.wsPassword}" />
                </div>
                </div>    
            </div>
            <div class="row"><br/></div>
            <div class="row" id="divSub" style="width:60%; align="center";">
            <div class="col-sm-12">
                <label for="inputEmail" class="control-label col-sm-6" style="text-align: left;padding-left: 60px;"> <span class="dispalyFields"><s:property value="getText('AccountNumber')"/></span></label>
                <div class="col-sm-6">
                      <s:textfield name="wsAccountNumber" id="wsAccountNumber"  class="form-control" value="%{#aascProfileOptions.wsAccountNumber}" />
                </div>
                </div>    
            </div>
            </div>-->
            <div class="row"><br/></div>
            <div class="row" id="divSub">
                <div class=" col-sm-4" >
                </div>
                <div class=" col-sm-4" >
                    
                    <s:if test="%{#roleId==1 || #roleId==3 }" >
                    <div class="col-sm-6">
                        <button class="btn btn-warning" name="reset" id="reset" onclick="document.profileOptionsForm.actionType.value='Go';" value="Cancel" alt="[reset]" align="middle"> Clear <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                      </div>
                    <div class="col-sm-6">
                        <button class="btn btn-success" name="saveImage" id="saveImage" onclick="return saveValidation();" value="AddNewUser" alt="[submit]" align="middle"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                    </div>
                    </s:if>
                    <s:elseif test="%{#roleId==4}">
                        <div class="col-sm-6">
                            <button disabled="true" class="btn btn-warning" name="reset" id="reset"  value="Cancel" alt="[reset]" align="middle"> Clear <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                        </div>
                        <div class="col-sm-6">
                            <button disabled="true" class="btn btn-success" name="saveImage" id="saveImage"  value="AddNewUser" alt="[submit]" align="middle"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                        </div>
                     </s:elseif>
		</div>
                <div class=" col-sm-4" >
                </div>
             </div>
            </br>
           
    
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
