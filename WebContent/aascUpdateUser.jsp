<%/*========================================================================+
@(#)aascLocationSetup.jsp 08/08/2014
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Update User Details
* @version 1
* @author       N Srisha
* @history
*  06/01/2015   Y Pradeep   Merged Sunanda's code : Removed the hard codings by getting from Properties file and changed save() function name to update().
*  21/01/2015   K Sunanda   Added code to display success messages in green and errored in red for bug #2506
*  04/02/2015   K Sunanda   Added Id for display messages
*  17/02/2015    Suman G     Modified code to fix #2506.
*  10/03/2015   K Sunanda   Added code for email addresses in readonly mode in edit User page
*  16/03/2015   Suman G     Removed readonly for AlternateEmail Id field.
*  23/03/2015   Sunanda k   Modified code for bug fix #2728
*  30/03/2015   Sunanda K   Modified code for bug fix #2748
*  31/03/2015   Sunanda K   Modified code for bug fix #2780
*  31/03/2015   Sunanda K   Modified code for bug fix #2781
*  14/04/2015   Y Pradeep   Modified code to display UI in proper order while navigating using tab button. Bug #2809.
*  15/04/2015   Suman G     Added session to fix #2743.
*  23/04/2015   Y Pradeep   Removed footer.
*  02/06/2015   Suman G     Changed error page to fix #2743.
*  11/06/2015   Suman G     Removed fevicon to fix #2992
*  07/07/2015   Dinakar     Aligined UI as per tab
*  21/07/2015    Suman G        Modified message id for QA
*  24/07/2015   Rakesh K    Modified Code to work application in offline.
04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
26/08/2015  Rakesh K       Added code to solve 3496.
*  04/12/2015   Suman G     Added code for resending the password functionality to user.
*/
%>
<link type="text/css" rel="stylesheet" href="aasc_ss.css">
<link type="text/css" rel="stylesheet" href="aasc_index.css"/>
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
  
     <!--<link href="kendo.common-material.min.css" rel="stylesheet" />
     <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
     <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
     <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script> -->
  
  
  <script  type="text/javascript" src="aasc_User_Details_js.js"> </script>
  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:property value="getText('UpdateUserDetails')"/></title>
    <style type="text/css">
      .href {color: #003399}
       html{height:100%;}
    </style>
     <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
     <link rel="stylesheet" type="text/css" href="css/style4.css" />
     
     
  </head>
  
  <body class="gradientbg">
  <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
    <s:form  name="UpdateUserForm" method="POST" action="AascCreateUserAction" >

      <c:set var="actionType1" value="${param.actiontype}" scope="page"/>
      <s:set name="actionType" value="%{#attr.actionType1}"/>
    
      <c:set var="currentRow1" value="${param.currentRow}" scope="page"/>
      <s:set name="currentRow" value="%{#attr.currentRow1}"/>
      
      <s:hidden name="actionTypeTemp" value="%{#actionType}"/>
      
      <s:set name="clientIDInt" value="%{#session.client_id}"/>
      <s:set name="clientIDStr" value="%{#clientIDInt + ''}"/>
      <s:hidden name="clientID" value="%{#clientIDInt}"/>
      <s:hidden name="actiontype" /> 
      
       <s:set name="locationDetailsMap" value="%{#session.locationDetailsMap}"/>
      
     <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
       <tr> 
           <td align="left"><%@ include file="aascIndexHeader.jsp"%></td>
       </tr>
       <tr>
       <!--21/01/2015 sunanda added-->
           <c:catch var="exception1">
            <s:set name="key" value="%{#attr.key}"/>
             <s:if test="%{#key != null}">
             <s:if test="%{#key=='aasc412' || #key=='aasc430'}">
              <td width="90%" class="displayMessage1" id="displayMessage" align="center" valign="bottom">
              <s:property value="getText(#key)"/> 
              </td>
             </s:if>
             <s:else>
              <td width="90%" class="displayMessage" id="displayMessage" align="center" valign="bottom">
                <s:property value="getText(#key)"/> 
              </td>
             </s:else>
                <s:set name="key" value="%{'null'}"/>
             </s:if>
           </c:catch>
           <s:if test="%{#exception1 != null}">
               <c:redirect url="/aascShipError.jsp">
                 <s:param name="errorObj" value="%{#exception1.message}" />
               </c:redirect>
           </s:if>
       </tr>
     </table>

     
     <s:if test='%{#actionType == "Edit"}'>     
        <s:set name="userDetailsList" value="%{#session.userDetailsList}"/>  
        <s:iterator id="userDetailsInfo" value="#userDetailsList" status="iteratorStatus">
           <s:if test="%{#currentRow == #iteratorStatus.count}">
               <s:set name="aascUserBean" value="%{#userDetailsInfo}"/>  
           </s:if>
        </s:iterator> 
     </s:if>
     <s:else>
        <s:set name="aascUserBean" value="%{#session.aascUserBean}"/>
        <s:set name="aascUserInfo" value="%{''}" scope="session"/>
    </s:else>
    
    <div class="row"><br/></div>
    <div class="row"><br/></div>
    <center>
    <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:90%">
                  <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <label style="color:#ffffff;font-size:20px;">Update User</label>
                    </div>       
                <div class="row"><br/></div>
          
            <s:hidden name="userID" value="%{#aascUserBean.userID}"/>     
            <s:hidden name= "password" value="%{#aascUserBean.password}" />
            
            
                    <div class="row" id="divSub" >
                        <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('FirstName')"/><font color="red">* </font></span></label>
                        <div class="col-sm-4">
                            <s:textfield name="firstName" id="firstName" cssClass="inputs" size="50" value="%{#aascUserBean.firstName}" maxlength="50" />
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('LastName')"/> </span> </label>
                        <div class="col-sm-4">
                            <s:textfield  name="lastName" id="lastName" cssClass="inputs" size="50" value="%{#aascUserBean.lastName}" maxlength="50" />
                        </div>
                        </div>
                    </div>
                    <div class="row"><br/></div>
                    <div class="row" id="divSub" >
                    <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('emailAddress')"/><font color="red">* </font></span></label>
                        <div class="col-sm-4">
                            <s:textfield cssClass="inputs" name="emailAddress" id="emailAddress"  size="50" value="%{#aascUserBean.emailAddress}" maxlength="50" />
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('alternateEmailAddress')"/> </span> </label>
                        <div class="col-sm-4">
                            <s:textfield cssClass="inputs" name="alternateEmailAddress" id="alternateEmailAddress"  size="50" value="%{#aascUserBean.alternateEmailAddress}" maxlength="50" />
                        </div>
                        </div>
                    </div>
                    <div class="row"><br/></div>
                    <div class="row" id="divSub" >
                    <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('role')"/></span></label>
                        <div class="col-sm-4">
                            <s:select cssClass="inputs" name="role" id="role" list="#{'3':'Admin', '4': 'Shipping User','5': 'Tracking'}"  value="%{#aascUserBean.role}"  />
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('Location')"/> </span> </label>
                        <div class="col-sm-4">
                            <s:select cssClass="inputs" list="locationDetailsMap"  listKey="key" listValue="value" cssStyle="height:25px" headerKey="Select" headerValue="Select"  name="locationId" size="1"  id="locationId" value="%{#aascUserBean.locationId}"/>
                        </div>
                        </div>
                    </div>
                    <div class="row"><br/></div>
                <div class="row" id="divSub" >
                    <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('Status')"/></span></label>
                        <div class="col-sm-4">
                            <s:select cssClass="inputs" list="#{'Y':'ACTIVE', 'N': 'INACTIVE'}" name="status" id="status"  value="%{#aascUserBean.status}"/> <!-- Srisha changed value of status for bug #3403-->
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('AddressBook')"/></span></label>
                        <div class="col-sm-4">
                            <s:select list="#{'UL':'User Level', 'CL': 'Customer Level'}" name="addressBookLevel" id="addressBookLevelId" cssClass="inputs" value="%{#aascUserBean.addressBookLevel}"/>
                        </div>
                    </div>
                </div>
            <div class="row"><br/></div>
                <div class="row" id="divSub">
                <div class=" col-sm-2" >
                </div>
                <div class=" col-sm-8" >
                    <s:hidden name="UpdateButtonId" id="UpdateButtonId" value="%{0}"/>
                         <button class="btn btn-success" name="Resend" id="ResendId" onclick="document.UpdateUserForm.actiontype.value='resendPassword';" value="resendPassword" alt="Edit" align="middle"> Resend Password <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                         <button class="btn btn-success" name="Save" id="SaveButton" onclick="document.UpdateUserForm.actiontype.value='editUserDetails';return update();" value="editUserDetails" alt="Edit" align="middle"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                         <button class="btn btn-warning" name="closeButton" id="closeButton" onclick="document.UpdateUserForm.actiontype.value='Cancel';" value="Cancel" alt="Close" align="middle"> Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                     
		</div>
                <div class=" col-sm-2" >
                </div>
             </div>
               <div class="row"><br/></div>                  
          
            </div>
            
            </center>
 
    </s:form>
     <div style="position:relative;top:300px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
