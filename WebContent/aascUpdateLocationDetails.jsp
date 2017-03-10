<%/*========================================================================+
@(#)aascUpdateLocationDetails.jsp 31/01/2009
* Copyright (c) Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Updating Loaction Details
* @version 1 
* @author   N Srisha
* @history
* 
* 17-Dec-2014   Eshwari M    Merged Sunanda code on issues pending
* 07-Jan-2015   Y Pradeep    Merged Sunandas code : Removed the hard codings by getting from Properties file 
* 20-Jan-2015   K Sunanda    Removed JavaScript and added to aasc_Update_Locations_js.js
* 21-Jan-2015   K Sunanda    Added code to display success messages in green and errored in red for bug #2506
* 04/02/2015    K Sunanda    Added Id for display messages
* 05/03/2015    Sanjay & Khaja Added code for new UI changes.
* 10/03/2015    Y Pradeep    Added missing name for param tag
* 23/03/2015    K Sunanda    Added code for new fields Address line 3 and email address
* 07/04/2015    Y Pradeep    Modified code to align fields in order and in a single table to do tab key navigation. Bug #2809.
* 15/04/2015    Eshwari M    Reordered Contact Name and Location name fields.
* 17/04/2015    Y Pradeep    Removed duplicate field names for locationId. Bug #2659.
* 15/04/2015    Suman G      Added session to fix #2743.
* 23/04/2015    Y Pradeep    Removed footer.
* 11/06/2015    Suman G      Removed fevicon to fix #2992
* 19/06/2015    Sunanda K    Removed HardCodings and got the headings from applicationResourceProperties
* 07/07/2015    Dinakar      Aligined UI as per tab
* 21/07/2015    Suman G        Modified message id for QA
* 24/07/2015    Rakesh K     Modified Code to work application in offline.
04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
26/08/2015  Rakesh K       Added code to solve 3496.
*/
%>

<link type="text/css" rel="stylesheet" href="aasc_ss.css">

<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
  
   <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
     <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
       <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
  <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script> -->
  
    <script type="text/javascript" language="JavaScript" SRC="aasc_Update_Locations_js.js">
    </script>
    
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:property value="getText('UpdateLocationsDetails')"/></title>
    <style type="text/css">.href {color: #003399}
    </style>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    
    <style type="text/css">
      html {
        height: 100%;
            }
            
             fieldset.scheduler-border {
        border: 2px groove #7761a7 !important;
        padding: 1.4em 1.4em 1.4em 1.4em !important;
       border-radius: 15px;}
       .button { 
       width: 150px;
       padding: 10px; 
       background-color: #FF8C00; 
       box-shadow: -8px 8px 10px 3px rgba(0,0,0,0.2); 
       font-weight:bold; text-decoration:none; }
       .blurred {
      -webkit-filter: blur(2px);
      -webkit-transition: all 2s;
}
    </style>
  </head>
  
  <body>
  
   <div class="container-fluid" style="background-color:#ADADAD;">
  <s:form name="updateLocationDetails" method="POST"
                                 action="LocationSetupAction">
                                 
       <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
    
      <c:set var="actiontype" value="${param.actiontype}" />     
      <s:set name="actionType" value="%{#attr.actiontype}"/>
      <c:set var="currentRow1" value="${param.currentRow}" scope="page"/>
      <s:set name="currentRow" value="%{#attr.currentRow1}"/>
      <s:hidden name="actionType" value="%{#actionType}"/>
      <s:bean id="countryNameHashMap" name="java.util.HashMap"/>
      <c:catch var="exception1">
        <s:set name="countryNameHashMap" value="%{#session.countryNameHashMap}" />
      </c:catch>
      <s:if test="%{#exception1 != null}">
        <s:param  name="countryNameHashMap" value="%{'select'}"/>
      </s:if>
      <s:set name="client_Id" value="%{#session.client_id}"/>
      <s:set name="user_Id" value="%{#session.user_id}"/>
      <s:hidden name="client_Id" /> 
      <s:hidden name="user_Id" /> 
      <s:hidden name="actiontype" />
      <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
      <tr> <td  width="90%" align="left"><s:include value="aascIndexHeader.jsp" /></td> </tr>
       <tr>
       <!--21/01/2015 sunanda added-->
            <c:catch var="exception1">
              <s:set name="key" value="%{#attr.key}"/>
              <s:if test="%{#key != null}">
            <s:if test="%{#key==aasc604}">
             <td width="90%" class="displayMessage" id="displayMessage" align="center" valign="bottom">
              <s:property value="getText(#key)"/> 
             </td>
            </s:if>
            <s:else>
             <td width="90%" class="displayMessage1" id="displayMessage" align="center" valign="bottom">
                <s:property value="getText(#key)"/>
             </td>
            </s:else>
            <s:set name="key" value="%{'null'}"/>
              </s:if>
            </c:catch>
          <s:if test="%{#exception1 != null}">
            <redirect url="/aascShipError.jsp">
              <s:param name="errorObj" value="%{#exception1.message}"/>
            </redirect>
          </s:if>
        </tr>
      </table>
      
      <s:if test='%{#actionType == "Edit"}'>     
      <s:set name="locationDetailsList" value="%{#session.locationsList}"/>  
        <s:iterator id="aascSetupLocationList" value="#locationDetailsList" status="iteratorStatus">
           <s:if test="%{#currentRow == #iteratorStatus.count}">
               <s:set name="aascLocationDetailsBean" value="%{#aascSetupLocationList}"/>
           </s:if>
        </s:iterator> 
     </s:if>
     <s:else>
            <s:set name="aascLocationDetailsBean" value="%{#session.aascSetupLocationBean}"/>
            <s:set name="aascLocationsInfo" value="%{''}" scope="session"/>
     </s:else>
     <s:set name="locationId" value="%{#aascLocationDetailsBean.locationId}" />
     <s:hidden name="locationId" value="%{#locationId}"/>
     
      <br/>
            <div class="row"><br/></div>
            <div class="row"><br/></div>
            <center>
                
                    <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:90%"> 
                        <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                            <label style="color:#ffffff;font-size: 20px;">Update Ship From Location</label>
                        </div>  
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('LocationName')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="locationName"  cssClass="inputs"  id="shipFromOrg" size="20" value="%{#aascLocationDetailsBean.locationName}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('ContactName')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="contactName"  cssClass="inputs"  id="shipFromContactName" size="20" value="%{#aascLocationDetailsBean.contactName}"/>
                            </div>
                          </div>
                        </div>
                        
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('AddressLine1')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="addressLine1" cssClass="inputs" id="shipFromAddressLine1" size="20" value="%{#aascLocationDetailsBean.addressLine1}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('AddressLine2')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="addressLine2" cssClass="inputs" id="shipFromAddressLine2" size="20" value="%{#aascLocationDetailsBean.addressLine2}"/>
                            </div>
                          </div>
                        </div>
                        
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('AddressLine3')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="addressLine3" cssClass="inputs" id="shipFromAddressLine3" size="20" value="%{#aascLocationDetailsBean.addressLine3}"/><!--Sunanda added-->
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('City')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="city"  cssClass="inputs" id="shipFromCity" size="20" value="%{#aascLocationDetailsBean.city}"/>
                            </div>
                          </div>
                        </div>
                        
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('State')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="state"  cssClass="inputs" id="shipFromState" size="20" onblur="stateChgCase()" value="%{#aascLocationDetailsBean.state}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('PostalCode')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="postalCode"  cssClass="inputs" id="shipFromPostalCode" size="20" value="%{#aascLocationDetailsBean.postalCode}"/>
                            </div>
                          </div>
                        </div>
                        
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('Country')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:iterator value="countryNameHashMap">
                                 <s:set name="countryNameHashMapKey" value="key"/>
                                  <s:set name="countryNameHashMapValue" value="value"/>
                                  </s:iterator>
                                <s:select list="countryNameHashMap" cssClass="inputs" cssStyle="height:25px" listKey="value" listValue="key" headerKey="Select" headerValue="Select"  name="countryCode" size="1"   id="shipFromCountry" value="#aascLocationDetailsBean.countryCode" />
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('Status')"/> </span> </label>
                            <div class="col-sm-3">
                             <s:select list="#{'Y':'ACTIVE', 'N': 'INACTIVE'}" cssClass="inputs" name="locationStatus"    id="shipFromLocationStatus" value="%{#aascLocationDetailsBean.locationStatus}"/>

                            </div>
                          </div>
                        </div>
                        
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('ContactNumber')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="phoneNumber"  cssClass="inputs" id="shipFromPhoneNumber1" size="20"  value="%{#aascLocationDetailsBean.phoneNumber}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('emailAddress1')"/> </span> </label>
                           
                            <div class="col-sm-3">
                                <s:textfield name="emailAddress" cssClass="inputs" id="emailAddressId" size="20" value="%{#aascLocationDetailsBean.emailAddress}"/>
                            </div>
                          </div>
                        </div>
                        <div class="row"><br/></div>
                        <div class="row" >
                          <div class="col-sm-12">
                          <div class="col-sm-3">
                          </div>
                            <div class="col-sm-6 ">
                                <s:hidden name="UpdateButtonId" id="UpdateButtonId" value="%{0}"/>
                                <button class="btn btn-success" name="Save" id="SaveButton" onclick="document.updateLocationDetails.actiontype.value='editLocationDetails'; return checkData();" value="editLocationDetails" alt="Save" align="middle">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                                <button class="btn btn-danger" name="closeButton" id="closeButton" onclick="document.updateLocationDetails.actiontype.value='Cancel';" value="Cancel" alt="Close" align="middle">Close <span class="badge"><span class="glyphicon glyphicon-off"></span></span></button>
                            </div>
                            <div class="col-sm-3">
                            </div>
                            </div>
                           </div>
                        <div class="row"><br/></div>
                       </div>
            </center>    
  </s:form>
  </div>
  <div style="position:relative;top:300px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
  </html>
