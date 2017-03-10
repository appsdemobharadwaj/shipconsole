<%/*========================================================================+
@(#)aascUpdateUser.jsp 15/03/2015
* Copyright (c) 2014-2015 Apps Associates Pvt. Ltd. 
* All rights reserved.   

|  DESCRIPTION                                                                                             |
|    JSP For Package Dimensions Details                                                                    |                                                       |
|    Version - 1                                                                                           |       
|    16/03/2015   Eshwari M     Added this file for edit user profile                                      |
|    16/03/2015   Khaja         Added UI to Edit Profile page                                              | 
|    16/03/2015   Eshwari M     Fixed the issue of no holding data in the fields after saving and          |
|                               color changes to the display messages                                      |
     21/04/2015   Suman G       Added code to show details on load and to fix #2754.
     05/05/2015   Y Pradeep     Aded new UI background color.
     22/07/2015   Rakesh K     Modified UI alignment.
     24/07/2015   Rakesh K     Modified Code to work application in offline.
     04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
     26/08/2015  Rakesh K       Added code to solve 3496.
+==========================================================================================================*/%>

<%@ page import="com.aasc.erp.bean.*"%>
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
  
   <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
   <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">-->
   
     <script type="text/javascript">
     function load()
     {
        var firstName = document.EditUserProfile.firstName.value;
        
        var lastName = document.EditUserProfile.lastName.value;
        
        if(firstName == '' || firstName == null)
        document.EditUserProfile.firstName.value = document.EditUserProfile.firstNameHidden.value ;
        if(lastName == '' || lastName == null)
            document.EditUserProfile.lastName.value = document.EditUserProfile.lastNameHidden.value ;
        var emailAddress = document.EditUserProfile.emailAddress.value;
        
        if(emailAddress == '' || emailAddress == null)
            document.EditUserProfile.emailAddress.value = document.EditUserProfile.emailAddressHidden.value ;
        
     }
         function save()
                {     
        var firstName = document.EditUserProfile.firstName.value;
        var emailAddress = document.EditUserProfile.emailAddress.value;
       
       if(firstName.length == 0 || firstName.length == null ){
            alert("Please enter First Name");
            document.EditUserProfile.firstName.focus();
            return false;
        }
       
        if(emailAddress.length == 0 || emailAddress.length == null ){
            alert("Please enter Email Address ");
            document.EditUserProfile.emailAddress.focus();
            return false;
        }
        else if(emailAddress.length > 50){
            alert("Email Address cannot be greater than 50 char");
            document.EditUserProfile.emailAddress.focus();
            return false;
        }
        else if(!validateEmail(emailAddress)){
            alert("Please enter valid Email Address.");
            document.EditUserProfile.emailAddress.focus();
            return false;
        }
           
         if(document.EditUserProfile.UpdateButtonId.value == "0")
        {
        // alert('entered IF');
            document.EditUserProfile.UpdateButtonId.value="1";
            return true;
        }
        else
        {
        // alert('entered ELSE');
            alert("Request already submitted. Please Wait.");
            return false; 
        }
       }
               
       function validateEmail(x)
       {
        var atpos=x.indexOf("@");
        var dotpos=x.lastIndexOf(".");
        if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length){
            //alert("Not a valid e-mail address");
            return false;
        }
        else
        {
            return true;
        }
       }
        
    </script>
    <%
      if(session.isNew()|| !(session.getId().equals(session.getAttribute("SessionId"))))
      {
        response.sendRedirect(request.getContextPath()+"/aascShipError.jsp");   
      }
    %>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
     <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
   
   
   
    <title><s:property value="getText('EditUserProfile')" /></title>  
    <style type="text/css">
      .href {color: #003399}
      #divSub{
    margin-left:10px;; 
    margin-right:10px;
    }
    </style>
     
  </head>
  <body style="background-color:#ADADAD" onload="load();">
    <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
    <form name="EditUserProfile" method="POST" action="AascUpdateUserDetailsAction" class="form" style="margin-left:auto; margin-right:auto;border-radius:5px;">
    <%
    Integer clientIdInt=(Integer)session.getAttribute("client_id");
    int userId=(Integer)session.getAttribute("user_id");
    String firstName = (String)session.getAttribute("firstName");
    String lastName = (String)session.getAttribute("lastName");
    String emailAddress = (String)session.getAttribute("emailAddress");
   
    pageContext.setAttribute("emailAddress",emailAddress);
    pageContext.setAttribute("clientId",clientIdInt);
    pageContext.setAttribute("userId",userId);
    pageContext.setAttribute("firstName",firstName);
    pageContext.setAttribute("lastName",lastName);
    %>
    <s:hidden name="userID" value="%{#attr.userId}"/>
    <s:hidden name="clientID" value="%{#attr.clientId}"/>
    <s:hidden name="firstNameHidden" value="%{#attr.firstName}"/>
    <s:hidden name="lastNameHidden" value="%{#attr.lastName}"/>
    <s:hidden name="emailAddressHidden" value="%{#attr.emailAddress}"/>

    <c:set var="actionType1" value="${param.actiontype}" scope="page"/>
    <s:set name="actionType" value="%{#attr.actionType1}"/>
    <s:hidden name="actionTypeTemp" value="%{#actionType}"/>
    <s:hidden name="actiontype" /> 
    <table width="80%" border="0" cellpadding="0" cellspacing="0" align="center">
       <tr>
         <td>  <!-- <img src="images/aasc_logo.png" alt="" width="202" height="48" onClick=""/> --> </td>
         <td width="90%" align="right" valign="bottom">
           <c:catch var="exception1">
              <s:set name="key" value="%{#attr.key}"/>
              <s:if test="%{#key != null}">
                 <s:if test="%{#key == 'aasc412'}">
                    <div class="displayMessage1" id="displayMessage">
                        <s:property value="getText(#key)"/> 
                    </div>
                  </s:if>  
                  <s:else>
                    <div class="displayMessage" id="displayMessage">
                        <s:property value="getText(#key)"/> 
                    </div>
                  </s:else>
                  <s:set name="key" value="%{'null'}"/>
              </s:if>
           </c:catch>
         </td>
         <s:if test="%{#exception1 != null}">
            <c:redirect url="/aascShipError.jsp">
              <s:param name="errorObj" value="%{#exception1.message}" />
            </c:redirect>
         </s:if>
         <td width="60%" class="displayMessage">
           <div align="right"><a href="<s:url value="/aascShipIndexPage.jsp"/>"/>
           
             <table width="80%"  border="0" align="center" cellpadding="0" cellspacing="0" >
               <tr>
                 <!--td width="59%"><div ><a href="aascShipIndexPage.jsp"><img src="images/home.png" width="20" height="20" border="0"></a></div></td-->
                <!-- <td width="41%"><div ><a href="aascShipFromLocationDetailsHelp.jsp" target="_blank"><img src="images/help.png" width="20" height="20" border="0"></a></div></td> -->
               </tr>
             </table>
           </div>
         </td>
       </tr>
     </table>
    
    
     
     <s:set name="aascUserInfoBean" value="%{#session.aascUserBean}"/>
     <s:set name="aascUserBean" value="%{''}" scope="session"/>


     
     <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:95%;margin-top:5%">
        <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px">
                       <label style="color:#ffffff;font-size:20px;padding-left:5px">Edit Profile</label>
                    </div>
                    </br>
                        <div class="row"><br/></div>
                         <div class="row" id="divSub" style="width:80%; margin-left:12%;">
                    <div class="col-sm-12">
                        <div class="col-sm-1">
                        </div>
                        <label for="inputEmail" class="control-label col-sm-5"> <span class="dispalyFields"><s:property value="getText('FirstName')"/><font color="red">* </font></span></label>
                        <div class="col-sm-5">
                          <s:textfield name="firstName" id="firstName"  cssClass="form-control" size="50" value="%{#aascUserInfoBean.firstName}" />
                        </div>
                        <div class="col-sm-1">
                        </div>
                        
                    </div>    
                </div>
                <div class="row"><br/></div>
                         <div class="row" id="divSub" style="width:80%; margin-left:12%;">
                    <div class="col-sm-12">
                    <div class="col-sm-1">
                        </div>
                        <label for="inputEmail" class="control-label col-sm-5"> <span class="dispalyFields"><s:property value="getText('LastName')"/><font color="red">* </font></span></label>
                        <div class="col-sm-5">
                          <s:textfield name="lastName" id="lastName"  class="form-control" size="50" value="%{#aascUserInfoBean.lastName}" />
                        </div>
                        <div class="col-sm-1">
                        </div>
                    </div>    
                </div>
                <div class="row"><br/></div>
                         <div class="row" id="divSub" style="width:80%; margin-left:12%;">
                    <div class="col-sm-12">
                    <div class="col-sm-1">
                        </div>
                        <label for="inputEmail" class="control-label col-sm-5"> <span class="dispalyFields"><s:property value="getText('emailAddress')"/><font color="red">* </font></span></label>
                        <div class="col-sm-5">
                          <s:textfield name="emailAddress" id="emailAddress"  class="form-control" size="50" value="%{#aascUserInfoBean.emailAddress}" maxlength="50" readonly="true" />
                        </div>
                        <div class="col-sm-1">
                        </div>
                    </div>    
                </div>
                
                
                
                        
      
            
    <!-- <table border="1" align="center" width="50%" >
       <tr class="tableDataCell">
         <td  noWrap><span class="dispalyFields" > <s:property value="getText('FirstName')"/><font color="red">* </font> </span></td>
         <td> <s:textfield name="firstName" id="firstName" cssClass="inputs" cssStyle="width:350px;" size="50" value="%{#aascUserInfoBean.firstName}" maxlength="50"  /> </td>
       </tr>
       <tr>
         <td  noWrap><span class="dispalyFields" > <s:property value="getText('LastName')"/></span></td>
         <td> <s:textfield name="lastName" id="lastName"  cssClass="inputs"  cssStyle="width:350px;" size="50" value="%{#aascUserInfoBean.lastName}" maxlength="50" /> </td>
       </tr>
       <tr class="tableDataCell">
         <td  noWrap><span class="dispalyFields" > <s:property value="getText('emailAddress')"/><font color="red">* </font> </span></td>
         <td> <s:textfield name="emailAddress" id="emailAddress" cssClass="inputs"  cssStyle="width:350px;" size="50" value="%{#aascUserInfoBean.emailAddress}" maxlength="50" readonly="true" /> </td>
       </tr>
     </table>-->
        <div class="row"><br/></div>
            <div class="row" id="divSub">
                 <div class=" col-sm-1" >
                 </div>
                <div class=" col-sm-10" align="center">
                    <button class="btn btn-success" name="Save" id="SaveButton" onclick="document.EditUserProfile.actiontype.value='editUserProfile';return save();" value="editUserDetails" alt="[reset]" align="middle"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                    <button class="btn btn-warning" name="reset" id="reset" onclick="window.close()" value="Cancel" alt="[reset]" align="middle"> Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                 </div>
                <div class=" col-sm-1" >
                 </div>
            </div>
              <div class="row"><br/></div> 
              </div>
     </form>
    </div>
 <div style="position:relative;top:300px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
