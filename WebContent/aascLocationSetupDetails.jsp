<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%/*========================================================================+
@(#)aascLocationSetup.jsp 23/07/2014
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Customer and locations Details
* @version 1
* @author       N Srisha
* @history
*  
* 17-Dec-2014     Eshwari M   Merged Sunandas code and removed the hard codings by getting from Properties file 
*  19-Dec-2014    Sunanda k   Removed Text Hard codings and retrieved from Application properties file
* 07-Jan-2015     Y Pradeep   Merged Sunandas code : Removed the hard codings by getting from Properties file 
* 21-Jan-2015     K Sunanda   Added code to display success messages in green and errored in red for bug #2506
* 04/02/2015      K Sunanda   Added Id for display messages
* 10/03/2015      K Sunanda   Modified code for UI alignments
* 23/03/2015      K Sunanda   Added code for newly created fields email address and addressline 3
* 15/04/2015      Suman G     Modified code to fix #2850.
* 15/04/2015      Eshwari M   Modified code to fix bug # 2582
* 15/04/2015      Suman G     Added session to fix #2743
* 20/04/2015      Suman G     Removed nowrap to show details properly.
* 23/04/2015      Y Pradeep   Removed footer.
* 11/06/2015      Suman G     Removed fevicon to fix #2992
* 19/06/2015      Sunanda k   Added code to wrap the text in the fields of the table
* 23/06/2015      Rakesh K   Modifed code to fix #3061
* 07/07/2015      Dinakar    Aligined UI as per tab
* 24/07/2015      Rakesh K    Modified Code to work application in offline.
04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
12/08/2015  Rakesh K    Modified Code to align in Role 2 #2793.
*  26/08/2015   Dinakar G  Added id's for Automation testing.
26/08/2015  Rakesh K       Added code to solve 3496.
*/
%>

<link type="text/css" rel="stylesheet" href="aasc_ss.css">
<link type="text/css" rel="stylesheet" href="aasc_index.css">
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%> 
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
  
 <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
     <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
       <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
  <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>
   <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />-->
  
  <link type="text/css" rel="stylesheet" href="jquery-ui.css" />
   
  <script language="JavaScript" src="jquery-ui.js" type="text/javascript"></script>  
    <script language="javascript" type="text/javascript"> 
    
    //Disable refresh or f5 button
function showKeyCode(e) 
 {
    // debugger;
    var keycode;
    if (window.event)
        keycode = window.event.keyCode;
    else if (e)
        keycode = e.which;
        // Mozilla firefox
    if ($.browser.mozilla) {
        if (keycode == 116 || (e.ctrlKey && keycode == 82)) {
        if (e.preventDefault) {
            e.preventDefault();
            e.stopPropagation();
            }
        }
    }
    // IE
    else if ($.browser.msie) {
        if (keycode == 116 || (window.event.ctrlKey && keycode == 82)) {
            window.event.returnValue = false;
            window.event.keyCode = 0;
            window.status = "Refresh is disabled";
        }
    }
    else {
        switch (e.keyCode) {
            case 116: // 'F5'
                event.returnValue = false;
                event.keyCode = 0;
                window.status = "Refresh is disabled";
                break;
        }
    }
 }
function methodOnEdit(rowCount)
 {         
      document.LocationDetailsForm.currentRow.value=rowCount;
 } 
      
function validateSubmit()
 {
    if(document.LocationDetailsForm.CreateButtonId.value == "0")
     {
       document.LocationDetailsForm.CreateButtonId.value="1";
       return true;
     }
    else
     {
       alert("Request already submitted. Please Wait.");
       return false; 
     }
 }
 </script>
  <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:property value="getText('LocationSetupDetails')"/></title>
    <style type="text/css">
      .href {color: #003399;}
      html {height : 100%;} 
      fieldset.scheduler-border {
        border: 2px groove #3d566d !important;
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
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
   
  </head>
  <body   onkeydown="return showKeyCode(event)">
  
  <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
    <s:include value="aascIndexHeader.jsp" />
<div class="container-fluid" style="background-color:#ADADAD;">
      <s:form  name="LocationDetailsForm" method="POST" action="LocationSetupAction" >

        <s:hidden name="actiontype" /> 
        <s:hidden name="currentRow" /> 
        <s:set name="locationDetailsList" value="%{#session.locationsList}"/>  
        <s:set name="client_Id" value="%{#session.client_id}"/>
        <s:set name="user_Id" value="%{#session.user_id}"/>
        <s:set name="role_id" value="%{0}" />
        <s:set name="role_idSse" value="%{#session.role_id}" />
        <s:set name="role_id" value="%{#role_idSse}" />
        <s:hidden name="client_Id" /> 
        <s:hidden name="user_Id" /> 
        
          
          
          <div class="row"> 
            <c:catch var="exception1">
               <s:set name="key" value="%{#attr.key}"/>
               <s:if test="%{#key != null}">
                  <s:if test="%{#key==aasc604}">
            <div class="col-sm-6" id="displayMessage">
                <s:property value="getText(#key)"/> 
            </div>
            </s:if>
                  <s:else>
            <div class="col-sm-6" id="displayMessage">
                 <s:property value="getText(#key)"/>
            </div>
            <s:set name="key" value="%{'null'}"/>
             </s:else>
               </s:if>
             </c:catch> 
                <s:if test="%{#role_id == 2}">
                 <s:set name="clientId" value="%{#attr.clientId}" />
                </s:if>
                <c:catch var="customerException">
                 <s:set name="clientDetailsHashMap" value="%{#session.clientDetailsHashMap}"/>
                </c:catch>
              
                <s:if test="%{#exception1 != null}">
                  <c:redirect url="/aascShipError.jsp">
                     <s:param name="errorObj" value="%{#exception1.message}" />
                   </c:redirect>
             </s:if>
          </div>
        <s:hidden name="CreateButtonId" id="CreateButtonId" value="%{0}"/>
        <br/>
        <div class="row">
            <div class="col-sm-12">
                <div class="col-sm-6" style="color:white" align="right">
                    <h4 style="font-size:20px;font-weight:bold"><s:property value="getText('ShipFromLocations')"/></h4>
                </div>
                <s:if test='%{#role_id == "3" }'>
                <div class="col-sm-6" align="right">
                    <button class="btn btn-success" name="CreateButton" id="CreateButton" onclick="document.LocationDetailsForm.actiontype.value='Create';return validateSubmit();" value="Create" alt="Create" align="middle">Create <span class="badge"><span class="glyphicon glyphicon-pencil"></span></span></button>
                </div>
                </s:if>
                <s:if test='%{#role_id == "2" }'>
                    <div class="col-sm-6">
                        <div class="col-sm-6" style="margin-top:10px">
                          <div style="color:white;font-size: 15px;" class="col-sm-6">
                              <s:property value="getText('CustomerName')"/>
                          </div>  
                        <s:set name="disableFlag" value="true"/>
                        <div class="col-sm-6" align="left">
                            <s:select list="#clientDetailsHashMap" cssStyle="width:95%" name="clientIdSelect" id="clientIdSelect" listKey="key" listValue="value"  cssClass="inputFields" size="1" 
                             onchange="return getlocations(); "
                             headerValue="Select" headerKey="" value="#clientId"/> 
                        </div>
             <s:hidden name="clientIdHidden" id="clientIdHiddenID" value="%{#clientId}"/>
             </div>
             <div class="col-sm-6">
                <button class="btn btn-success" name="GoButton" id="GoButton" onclick="document.LocationDetailsForm.actiontype.value='Go';" value="Go" alt="Go" align="center">Go <span class="badge"><span class="glyphicon glyphicon-pencil"></span></span></button>
             </div>
                    </div>
                    </s:if>
          <s:else>
          <s:hidden name="clientIdHidden" id="clientIdHiddenID" value="%{#client_id}"/>
          </s:else>
            </div>
        </div>
        
        
      <br>
      
      <div class="row">
        <div class="col-sm-12">
            <div class="form-group">
                <fieldset class="scheduler-border" style="border-color:#3d566d; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;"> 
                    <table class="table table-hover" style="overflow-x:auto;font-size:small;">
                        <thead>
                            <tr>
                                <s:if test='%{#role_id == "3" }'>
                                    <th align="right" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;"> <s:property value="getText('Edit')"/></th>
                                 </s:if>
                                   <th align="left" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;" > <s:property value="getText('LocationName')"/></th>
                                   <th align="left" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;" > <s:property value="getText('ContactName')"/></th> 
                                   <th align="left" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;" > <s:property value="getText('AddressLine1')"/></th>
                                   <th align="left" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;" > <s:property value="getText('AddressLine2')"/></th>
                                   <th align="left" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;" > <s:property value="getText('AddressLine3')"/></th>
                                   <th align="left" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;" > <s:property value="getText('emailAddress1')"/></th>
                                   <th align="left" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;" > <s:property value="getText('ContactNumber')"/></th>
                                   <th align="left" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;" > <s:property value="getText('City')"/></th>
                                   <th align="left" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;" > <s:property value="getText('State')"/></th>
                                   <th align="left" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;" > <s:property value="getText('PostalCode')"/></th>
                                   <th align="left" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;" > <s:property value="getText('Country')"/></th>
                                   <th align="left" nowrap style="background-color:#3d566d;color:#ffffff;font-size: 13px;font-weight: bold;" > <s:property value="getText('Status')"/></th>
                            </tr>
                            <s:set name="locationCount" value="%{0}"/>
                            <s:iterator id="locationDetailsIterator" value="#locationDetailsList">
               <s:set name="locationId" value="%{0}"/>
               <s:set name="locationId" value="%{#locationDetailsIterator.locationId}"/>
               <s:hidden name="locationId"/>
               <s:set name="locationCount" value="%{#locationCount+1}"/>
               <tr id="rowId<s:property value='%{#locationCount}'/>" >
                <s:if test='%{#role_id == "3" }'>
                 <td width="5">   
                   <!--<input name="locationDetailsImg<s:property value='%{#locationCount}'/>" type="image"  id="NewButton" onclick="document.LocationDetailsForm.actiontype.value='Edit'; methodOnEdit('<s:property value='%{#locationCount}'/>');return validateSubmit();" class="inputFields" value = "%{#locationCount}" src="images/edit.png" alt="Edit" >-->
                    <button class="btn btn-primary"  name="locationDetailsImg<s:property value='%{#locationCount}'/>"   id="locationDetailsImgId<s:property value='%{#locationCount}'/>" onclick="document.LocationDetailsForm.actiontype.value='Edit'; methodOnEdit('<s:property value='%{#locationCount}'/>');return validateSubmit();" class="inputFields" value = "%{#locationCount}" alt="Edit" > <span><span class="glyphicon glyphicon-pencil"></span></span></button>
                 </td>
                 </s:if>
                 <td style="width:400px; text-wrap;word-wrap: break-word;white-space: pre-wrap;word-break: break-all" id="locationName${locationCount}"> <s:property value="%{#locationDetailsIterator.locationName}"/></td>
                 <td style="width:400px; text-wrap;word-wrap: break-word;white-space: pre-wrap;word-break: break-all" id="contactName${locationCount}"> <s:property value="%{#locationDetailsIterator.contactName}"/></td>
                 <td style="width:400px; text-wrap;word-wrap: break-word;white-space: pre-wrap;word-break: break-all" id="addressLine1${locationCount}"> <s:property value="%{#locationDetailsIterator.addressLine1}"/></td>
                 <td style="width:400px; text-wrap;word-wrap: break-word;white-space: pre-wrap;word-break: break-all" id="addressLine2${locationCount}"> <s:property value="%{#locationDetailsIterator.addressLine2}"/></td>
                 <td style="width:400px; text-wrap;word-wrap: break-word;white-space: pre-wrap;word-break: break-all" id="addressLine3${locationCount}"> <s:property value="%{#locationDetailsIterator.addressLine3}"/></td>
                 <td style="width:400px; text-wrap;word-wrap: break-word;white-space: pre-wrap;word-break: break-all" id="userName${locationCount}"> <s:property value="%{#locationDetailsIterator.emailAddress}"/></td>
                 <td style="width:400px; text-wrap;word-wrap: break-word;white-space: pre-wrap;word-break: break-all" id="contactNumber${locationCount}"> <s:property value="%{#locationDetailsIterator.phoneNumber}"/></td>
                 <td style="width:400px; text-wrap;word-wrap: break-word;white-space: pre-wrap;word-break: break-all" id="city${locationCount}"> <s:property value="%{#locationDetailsIterator.city}"/></td>
                 <td style="width:400px; text-wrap;word-wrap: break-word;white-space: pre-wrap;word-break: break-all" id="state${locationCount}"> <s:property value="%{#locationDetailsIterator.state}"/></td>
                 <td style="width:400px; text-wrap;word-wrap: break-word;white-space: pre-wrap;word-break: break-all" id="postalCode${locationCount}"> <s:property value="%{#locationDetailsIterator.postalCode}"/></td>
                 <td style="width:400px; text-wrap;word-wrap: break-word;white-space: pre-wrap;word-break: break-all" id="country${locationCount}"> <s:property value="%{#locationDetailsIterator.countryCode}"/></td>
                 <td nowrap id="status${locationCount}"> 
                     <s:if test='%{#locationDetailsIterator.locationStatus == "Y" || #locationDetailsIterator.locationStatus == "y" }'>
                          <s:set name="locationStatus" value='%{"ACTIVE"}' />
                     </s:if>
                     <s:else>
                        <s:if test='%{#locationDetailsIterator.locationStatus == null || #locationDetailsIterator.locationStatus == "N" ||
                                      #locationDetailsIterator.locationStatus == "n" }'>
                          <s:set name="locationStatus" value='%{"INACTIVE"}' /> 
                        </s:if>  
                     </s:else>
                     <s:property value="%{#locationStatus}"/>
                 </td>
               </tr>
            </s:iterator>
                        </thead>
                    </table>
                </fieldset>
            </div>
        </div>
      </div>
    </s:form>
    </div>
     <div style="position:relative;top:300px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>

</html>
