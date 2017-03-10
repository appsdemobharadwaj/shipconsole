<%/*========================================================================+
@(#)aascUserDetails.jsp 23/07/2014
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For User Details
* @version 1
* @author       N Srisha
* @history
*  
*  17-Dec-2014   Eshwari M   Merged Sunanda code after alignment and testing
*  07-Jan-2015   Y Pradeep   Merged Sunanda code alignment
*  15/01/2015    Y Pradeep   Merged Sunanda's code for getting title name from Application.Property file.
*  21/01/2015    K Sunanda   Added code to display success messages in green and errored in red for bug #2506 
*  04/02/2015    K Sunanda   Added Id for display messages.
*  15/02/2015    Y Pradeep   Added last name to users details table. Bug #2829.
*  15/04/2015    Suman G     Added session to fix #2743
*  23/04/2015    Y Pradeep   Removed footer.
*  11/06/2015    Suman G     Removed fevicon to fix #2992
*  23/06/2015    Rakesh K     Modifed code to fix #3061
*  07/07/2015    Dinakar      Aligined UI as per tab
*  21/07/2015    Suman G        Modified message id for QA
*  24/07/2015  Rakesh K    Modified Code to work application in offline.
04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
*  26/08/2015   Dinakar G  Added id's for Automation testing.  
26/08/2015  Rakesh K       Added code to solve 3496.
*  24/11/2015   Y Pradeep   Displaying locationName variable instead of locationId. Bug #4025.
*/
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
  
   <script  type="text/javascript" src="aasc_User_Details_js.js"> </script>
      
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    
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
    </style>
    <!--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>-->
    <title><s:property value="getText('UserDetails1')"/></title>
    <!--<style type="text/css">
      .href {color: #003399}
    </style>-->
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    <!-- <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>-->
  
  </head>
  
  <body >
  <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
    <s:include value="aascIndexHeader.jsp" />
    <div class="container-fluid" style="background-color:#ADADAD;">
    <s:form  name="UserDetailsForm" method="POST" action="AascCreateUserAction" >
    <!--  <table   border="0" cellpadding="0" cellspacing="0" align="center">
      <tr valign="top">
        <td height="55">-->
        <s:set name="userDetailsList" value="%{#session.userDetailsList}"/>  
        <s:set name="clientIDInt" value="%{#session.client_id}"/>
        <s:set name="clientID" value="%{#clientIDInt + ''}"/>
        <s:hidden name="actiontype" /> 
        <s:hidden name="currentRow" /> 
        <div class="row">  
                
                        <!--21/01/2015 sunanda added-->
                  <c:catch var="exception1">
                   <s:set name="key" value="%{#attr.key}"/>
                    <s:if test="%{#key != null}">
                  <s:if test="%{#key==aasc410||#key==aasc412}">
                   <div class="col-sm-8 displayMessage" width="90%" id="displayMessage" align="center" valign="bottom">
                    <s:property value="getText(#key)"/> 
                   </div>
                  </s:if>
                  <s:else>
                   <div class="col-sm-4 displayMessage1" width="90%" id="displayMessage" align="center" valign="bottom">
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
       </br>
       <div class="row">
       <div class="col-sm-12">
             <div class="col-sm-6" align="right">
                <h5 style="color:white;font-size:20px;font-weight:bold;"><s:property value="getText('UserDetails')"/></h5>
          </div>
          <div class="col-sm-6" align="right">   
            <button class="btn btn-success" name="CreateButton"  id="CreateButton" onclick="document.UserDetailsForm.actiontype.value='Create';return validateSubmit();" value="Create"  alt="Create" align="middle" >Create <span class="badge"><span class="glyphicon glyphicon-pencil"></span></span></button>
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
           <th align="left" nowrap class="shipToTableHeader" ><s:property value="getText('FirstName')"/></th>
           <th align="left" nowrap class="shipToTableHeader" ><s:property value="getText('LastName')"/></th>
           <th align="left" nowrap class="shipToTableHeader" ><s:property value="getText('role')"/></th>
           <th align="left" nowrap class="shipToTableHeader" ><s:property value="getText('Location')"/></th>
           <th align="left" nowrap class="shipToTableHeader" ><s:property value="getText('emailAddress')"/></th>
           <th align="left" nowrap class="shipToTableHeader" ><s:property value="getText('alternateEmailAddress')"/></th>
           <th align="left" nowrap class="shipToTableHeader" ><s:property value="getText('AddressBook')"/></th>
           <th align="left" nowrap class="shipToTableHeader" ><s:property value="getText('Status')"/></th>

        </tr>
        </thead>
            <s:set name="userCount" value="%{0}"/>
       <s:iterator id="userDetailsInfo" value="#userDetailsList">
            <s:set name="userCount" value="%{#userCount+1}"/>
        <tr id="rowId<c:out value='${userCount}'/>">
           <td width="5">   
                  <button class="btn btn-primary"  name="customerDetailsImg<c:out value='${userCount}'/>"   id="customerDetailsImgId<c:out value='${userCount}'/>" onclick="document.UserDetailsForm.actiontype.value='Edit'; methodOnEdit('<c:out value='${userCount}'/>');return validateSubmit();" class="inputFields" value = "<c:out value='${userCount}'/>" alt="Edit" > <span><span class="glyphicon glyphicon-pencil"></span></span></button>
           </td>
             <s:set name="role" value="%{#userDetailsInfo.role}"/>
             
             <s:if test="%{#role == 3}">
               <s:set name="role_name" value="%{'Admin'}"/>
             </s:if>
             <s:elseif test="%{#role == 4}">
                 <s:set name="role_name" value="%{'Shipping User'}"/>
             </s:elseif>
             <s:else>
                  <s:set name="role_name" value="%{'Tracking'}"/>
             </s:else>
             
             <s:set name="status" value="%{#userDetailsInfo.status}"/>
              
             <s:if test='%{#status == "Y"}'>
                  <s:set name="statusFlag" value="%{'ACTIVE'}"/>
             </s:if>
             <s:else>
                  <s:set name="statusFlag" value="%{'INACTIVE'}"/>
             </s:else>
           <td nowrap class="dispalyFieldsWithOutBold" id="firstName<c:out value='${userCount}'/>" ><s:property value="%{#userDetailsInfo.firstName}"/></td>
           <td nowrap class="dispalyFieldsWithOutBold" id="lastName<c:out value='${userCount}'/>" ><s:property value="%{#userDetailsInfo.lastName}"/></td>
           <td nowrap class="dispalyFieldsWithOutBold" id="roleName<c:out value='${userCount}'/>" ><s:property value="%{#role_name}"/></td>
           <td nowrap class="dispalyFieldsWithOutBold" id="locationName<c:out value='${userCount}'/>" ><s:property value="%{#userDetailsInfo.locationName}"/></td>
           <td nowrap class="dispalyFieldsWithOutBold" id="emailAddress<c:out value='${userCount}'/>" ><s:property value="%{#userDetailsInfo.emailAddress}"/></td>
           <td nowrap class="dispalyFieldsWithOutBold" id="alternateEmail<c:out value='${userCount}'/>" > &nbsp; <s:property value="%{#userDetailsInfo.alternateEmailAddress}"/></td>
       <s:if test='%{#userDetailsInfo.addressBookLevel == "UL"}'>
            <td nowrap class="dispalyFieldsWithOutBold" id="alternateEmail<c:out value='${userCount}'/>" > &nbsp; User Level</td>
       </s:if>
       <s:elseif test='%{#userDetailsInfo.addressBookLevel == "CL"}' >
            <td nowrap class="dispalyFieldsWithOutBold" id="alternateEmail<c:out value='${userCount}'/>" > &nbsp; Customer Level</td>
       </s:elseif>
       <s:else>
            <td nowrap class="dispalyFieldsWithOutBold" id="alternateEmail<c:out value='${userCount}'/>" > &nbsp;<s:property value="%{#userDetailsInfo.addressBookLevel}"/></td>
       </s:else>
           <td nowrap class="dispalyFieldsWithOutBold" id="statusFlag<c:out value='${userCount}'/>" ><s:property value="%{#statusFlag}"/></td> 
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
