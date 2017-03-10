
<%
/***
* JSP For  ShipTo Location Details
* @version 2
* @author Jagadish Jain
* @history
*  
* 17-Dec-2014   Eshwari M      Merged Sunanda code and removed harcodings by getting from properties file
* 06-Jan-2015   Y Pradeep      Merged Sunanda's code : Done self audit and removed accesslevel
* 21-Jan-2015   K Sunanda      Added code to display success messages in green and errored in red for bug #2506 
* 04/02/2015    K Sunanda      Added Id for display messages
* 10/03/2015    K Sunanda      Renamed the javascript name to make it meaningful and changed this js filename in the jsp
* 23/03/2015    K Sunanda      Added code for newly created fields email address and addressline 3
* 14/04/2015    Suman G        Modified labels to fix #2850
* 15/04/2015    Suman G        Added session to fix #2743
* 20/04/2015    Suman G        Removed nowrap to fix #2790
* 23/04/2015    Y Pradeep      Removed footer.
* 10/06/2015    K Sunanda      Modified the below code for bug fix #2963
* 11/06/2015    Suman G        Removed fevicon to fix #2992
* 23/06/2015    Rakesh K       Modifed code to fix #3061
* 07/07/2015    Dinakar        Aligined UI as per tab
* 21/07/2015    Suman G        Modified message id for QA
* 24/07/2015    Rakesh K       Modified Code to work application in offline.
* 30/07/2015    Dinakar G      Modifed code to fix #3232
* 04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
* 26/08/2015   Dinakar G  Added id's for Automation testing.
* 26/08/2015  N Srisha    Added Id's for Automation testing
26/08/2015  Rakesh K       Added code to solve 3496.
* 
*/
%>

<%! 
  public String nullStringToSpace(Object object) {
        String spcStr = "";
        if (object == null) {
            return spcStr;
        } else {
            return object.toString();
        }
    }
%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
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
    
    <!--<meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />-->
       
    <link type="text/css" rel="stylesheet" href="aasc_ss.css">
    <script language="JavaScript" SRC="aasc_Ship_To_Location.js" type="text/javascript"></script> 
    
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/> 
     
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    <link href="kendo.common-material.min.css" rel="stylesheet" />
       
<!--
    <link type="text/css" rel="stylesheet" href="jquery-ui.css" />
   <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>
  <script language="JavaScript" src="jquery-ui.js" type="text/javascript"></script>  
    -->

<style type="text/css">
        fieldset.scheduler-border {
            border: 2px groove #19b698 !important;
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
     fieldset.scheduler-border1 {
            border: 3px groove ##BDBDBD !important;
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

    <title><s:property value="getText('ShipToLocationDetails')"/></title>
    
  <!-- load jquery library
   <script src="sDashboard-master/example/libs/jquery/jquery-1.8.2.js" type="text/javascript"> </script>
   load jquery ui library -->
   <!--	<script src="sDashboard-master/example/libs/jquery/jquery-ui.js" type="text/javascript"> </script>   -->
  
   <script language="javascript" type="text/javascript"> 
   //Disable refresh or f5 button
function showKeyCode(e)
  {
    // debugger;
    var keycode;
    if (window.event)keycode = window.event.keyCode;
    else if (e)
       keycode = e.which;
    // Mozilla firefox
    if ($.browser.mozilla) 
    {
       if (keycode == 116 || (e.ctrlKey && keycode == 82)) 
       {
           if (e.preventDefault) 
           {
              e.preventDefault();
              e.stopPropagation();
           }
       }
    }
    // IE
    else if ($.browser.msie)
    {
        if (keycode == 116 || (window.event.ctrlKey && keycode == 82)) 
        {
            window.event.returnValue = false;
            window.event.keyCode = 0;
            window.status = "Refresh is disabled";
        }
    }
    else
    {
        switch (e.keyCode)
        {
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
    document.ShipToLocationDetailsForm.currentRow.value=rowCount;
    document.ShipToLocationDetailsForm.locationIDnum.value=    document.ShipToLocationDetailsForm["locid"+rowCount].value;
  } 
 function validateSubmit()
  {
    if(document.ShipToLocationDetailsForm.CreateButtonId.value == "0")
    {
        // alert('entered IF');
        document.ShipToLocationDetailsForm.CreateButtonId.value="1";
        return true;
    }
    else
    {
        // alert('entered ELSE');
        alert("Request already submitted. Please Wait.");
        return false; 
    }
  }
 </script>
   
  </head>
  <body onkeydown="return showKeyCode(event)"   >
  <div class="container-fluid" style="background-color:#ADADAD;">
  <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
    <s:include value="aascIndexHeader.jsp" />
    <s:form  name="ShipToLocationDetailsForm" method="POST" action="ShipToLocationDetailsAction" >
      <s:hidden name="actiontype" /> 
      <s:hidden name="currentRow" /> 
      
      <s:set name="locationDetailsList" value="%{#session.shipToCustomersList}"/>  
      <%
       int clientId1 = ((Integer)session.getAttribute("client_id")).intValue();
       int userId1=(Integer)session.getAttribute("user_id");
       int locationId=((Integer)session.getAttribute("location_id")).intValue();
       pageContext.setAttribute("client_id",clientId1);
       pageContext.setAttribute("user_id",userId1);
       pageContext.setAttribute("location_id",locationId);
      %>
      <s:set name="client_Id" value="%{#attr.client_id}"/>
      <s:set name="user_Id" value="%{#attr.user_id}"/>
      <s:set name="location_Id" value="%{#attr.location_id}"/>
      <s:hidden name="client_Id" /> 
      <s:hidden name="user_Id" /> 
      <s:hidden name="location_Id" />
      <br >
      <br />
      <div class="row">
			<c:catch var="exception1">
        <s:set name="key" value="%{#attr.key}"/>
          <s:if test="%{#key != null}">
            <s:if test="%{#key==aasc606}">
             <div class="col-sm-8 displayMessage" width="90%" id="displayMessage" align="center" valign="bottom">
              <s:property value="getText(#key)"/> 
             </div>
            </s:if>
            <s:else>
             <div div class="col-sm-4 displayMessage1" width="90%" id="displayMessage" align="center" valign="bottom">
          <s:property value="getText(#key)"/> 
             </div>
            </s:else>
          <s:set name="key" value="%{'null'}"/>
        </s:if>
      </c:catch> 
      <s:if test="%{#exception1 != null}">
        <c:redirect url="/aascShipError.jsp">
          <s:param name="errorObj" value="%{#exception1.message}" />
        </c:redirect>
      </s:if>
    </div>  
       <s:hidden name="CreateButtonId" id="CreateButtonId" value="%{0}"/>
      
      <div class="row">
      <div class="col-sm-12">
		<div class="col-sm-6" align="right" style="color:#ffffff">
			<h4 style="font-size:20px;font-weight:bold"><s:property value="getText('ShipToLocation')"/></h4>
		</div>
		<div class="col-sm-6" align="right">     
           <!-- <button name="NewButton" id="NewButton" onclick="document.ShipToLocationDetailsForm.actiontype.value='Create';return validateSubmit();" type="button" class="btn btn-primary btn-sm">Create</button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button name="uploadButton" id="uploadButton" onclick="document.ShipToLocationDetailsForm.actiontype.value='Upload';" type="button" class="btn btn-primary btn-sm">Upload</button> 
             <input name="NewButton" type="image" id="NewButton" onclick="document.ShipToLocationDetailsForm.actiontype.value='Create';return validateSubmit();" value="Create" src="buttons/aasccreate1.png" alt="Create" align="middle">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input name="uploadButton" type="image" id="uploadButton" onclick="document.ShipToLocationDetailsForm.actiontype.value='Upload';" value="Upload" src="buttons/aascUpload.png" alt="Upload ShipToLocations" align="middle">
           
            -->
            <button class="btn btn-success" name="createButton" id="createButton" onclick="document.ShipToLocationDetailsForm.actiontype.value='Create';return validateSubmit();" value="Create" alt="Create" align="middle">Create <span class="badge"><span class="glyphicon glyphicon-pencil"></span></span></button>
          <button class="btn btn-info" name="uploadButton" id="uploadButton" onclick="document.ShipToLocationDetailsForm.actiontype.value='Upload';" value="Create" alt="Upload ShipToLocations" align="middle">Upload <span class="badge"><span class="glyphicon glyphicon-cloud"></span></span></button>
           <s:hidden name="locationIDnum" id="locationIDnumID" value="" />
		</div>
                </div>
	  </div>
      <br/>
      <div class="form-group">
     <fieldset class="scheduler-border" style="border-color:#19b698; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;"> 
<table  class="table" style="overflow-x:auto;font-size:small;">
	<thead>
        <tr>
	<th align="right" nowrap class="shipToTableHeader" ><s:property value="getText('Edit')"/></th>
	<th align="left" nowrap class="shipToTableHeader" ><s:property value="getText('CustomerName')"/></th>
	<th align="left" nowrap class="shipToTableHeader" ><s:property value="getText('LocationName')"/></th>
	<th align="left" nowrap class="shipToTableHeader"><s:property value="getText('ContactName')"/></th>
	<th align="left" nowrap class="shipToTableHeader"><s:property value="getText('AddressLine1')"/></th>
	<th align="left" nowrap class="shipToTableHeader"><s:property value="getText('AddressLine2')"/></th>
	<th align="left" nowrap class="shipToTableHeader"><s:property value="getText('AddressLine3')"/></th>
	<th align="left" nowrap class="shipToTableHeader"><s:property value="getText('emailAddress1')"/></th>
	<th align="left" nowrap class="shipToTableHeader"><s:property value="getText('PhoneNumber')"/></th>
	<th align="left" nowrap class="shipToTableHeader"><s:property value="getText('City')"/></th>
	<th align="left" nowrap class="shipToTableHeader"><s:property value="getText('State')"/></th>	
	<th align="left" nowrap class="shipToTableHeader"><s:property value="getText('PostalCode')"/></th>
	<th align="left" nowrap class="shipToTableHeader"><s:property value="getText('Country')"/></th>
	</tr>
        </thead>
	<s:set name="locationCount" value="%{0}"/>
      <s:iterator id="locationDetailsIterator" value="#locationDetailsList">
      <s:set name="customerLocationId" value="%{0}"/>
      <s:set name="customerLocationId" value="%{#locationDetailsIterator.customerLocationId}"/>
      <s:hidden name="customerLocationId"/>
      <s:set name="locationCount" value="%{#locationCount+1}"/>
	<tr id="rowId<c:out value='${locationCount}'/>">
        <td width="5">   
            <!--<input name="locationDetailsImg<c:out value='${locationCount}'/>" type="image"  id="NewButton" onclick="document.ShipToLocationDetailsForm.actiontype.value='Edit'; methodOnEdit('<c:out value='${locationCount}'/>');return validateSubmit();" class="inputFields" value = "<c:out value='${locationCount}'/>" src="images/edit.png" alt="Edit" >-->
            <button class="btn btn-primary"  name="locationDetailsImg<c:out value='${locationCount}'/>"   id="locationDetailsImgId<c:out value='${locationCount}'/>" onclick="document.ShipToLocationDetailsForm.actiontype.value='Edit'; methodOnEdit('<c:out value='${locationCount}'/>');return validateSubmit();" class="inputFields" value = "<c:out value='${locationCount}'/>" alt="Edit" > <span><span class="glyphicon glyphicon-pencil"></span></span></button>
            <s:hidden name="locid%{#locationCount}" value="%{#locationDetailsIterator.customerLocationId}" />
        </td>
        <td class="dispalyFieldsWithOutBold" id="shipToCustomerName${locationCount}"><s:property value="%{#locationDetailsIterator.shipToCustomerName}"/></td>
        <td class="dispalyFieldsWithOutBold" id="shipToCustLocation${locationCount}"><s:property value="%{#locationDetailsIterator.shipToCustLocation}"/></td>
        <td class="dispalyFieldsWithOutBold" id="shipToContactName${locationCount}"><s:property value="%{#locationDetailsIterator.shipToContactName}"/></td>
        <td class="dispalyFieldsWithOutBold" id="addressLine1${locationCount}"><s:property value="%{#locationDetailsIterator.addressLine1}"/></td>
        <td class="dispalyFieldsWithOutBold" id="addressLine2${locationCount}"><s:property value="%{#locationDetailsIterator.addressLine2}"/></td>
        <td class="dispalyFieldsWithOutBold" id="addressLine3${locationCount}"><s:property value="%{#locationDetailsIterator.addressLine3}"/></td>
        <td class="dispalyFieldsWithOutBold" id="emailAddress${locationCount}"><s:property value="%{#locationDetailsIterator.emailAddress}"/></td>
        <td class="dispalyFieldsWithOutBold" id="phoneNumber${locationCount}"><s:property value="%{#locationDetailsIterator.phoneNumber}"/></td>
        <td class="dispalyFieldsWithOutBold" id="city${locationCount}"><s:property value="%{#locationDetailsIterator.city}"/></td>
        <td class="dispalyFieldsWithOutBold" id="state${locationCount}"><s:property value="%{#locationDetailsIterator.state}"/></td>
        <td class="dispalyFieldsWithOutBold" id="postalCode${locationCount}"><s:property value="%{#locationDetailsIterator.postalCode}"/></td>
        <td class="dispalyFieldsWithOutBold" id="countryCode${locationCount}"><s:property value="%{#locationDetailsIterator.countryCode}"/></td>
       </tr>
      </s:iterator>
   </table>			
</fieldset>
</div>
<br/>
   </s:form>
   </div>
    <div style="position:relative;top:300px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
