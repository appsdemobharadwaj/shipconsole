 <%       
/*==========================================================================+
|  DESCRIPTION                                                              |
|    JSP aascForgotPassword for displaying the forgot password fields       | 
|    information                                                            |
|    Author Suman Gunda                                                     |
|    Version   1.1                                                          |                                                                            
|    Creation  04/02/2015                                                   |      
|    History :                                                              |
    14/04/2015      Suman G         Added hidden field to fix #2814 issue.
    23/04/2015      Y Pradeep       Replaced shipexec logo to apps associates logo.
	02/06/2015      Suman G         Changed error page to fix #2743.
        01/07/2015      Rakesh K    Changed complete screen to New GUI to fix #3124
        24/07/2015  Rakesh K    Modified Code to work application in offline.
        04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
        26/08/2015  Rakesh K       Added code to solve 3496.
+===========================================================================*/%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
    
   <!-- <style type="text/css"> 
     @font-face {   
     font-family: 'Glyphicons Halflings';   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot');   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.woff') format('woff'),  
     url('bootstrap/fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg'); } 
    </style> -->
  
    <!--<meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />-->
  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:property value="getText('ForgotPasswordTitle')"/></title>
     <link href="aasc_ss.css" rel="stylesheet" type="text/css"/>
     <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
     <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
    <script src="aascForgotPassword.js" type="text/javascript"></script>
  </head>
  <body class="gradientbg" style="height:100%" onload="onload()">
  <div class="container-fluid" style="background-color:#ADADAD;">
    <s:form name="forgotPasswordForm" method="POST" action="AascChangePassword">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
            <tr>
                <td>  
                    <br>
                    <br>
                </td>
                <td width="90%" class="displayMessage" id="displayMessage" align="right" valign="bottom">
                    <c:catch var="exception1">
                        <s:set name="status" value="%{#attr.status}"/>
                        <s:set name="errorStatus" value="%{#attr.errorStatus}" />
                        
                        <s:if test="%{#status==1}">           
                            <s:hidden name="status" id="statusID" value="%{#status}" />
                            <h5> <s:property  value="%{'Your Password is reset successfully. Please check your mail'}" /> </h5>
                        </s:if>
                        <s:elseif test="%{#status==10}">
                            <s:hidden name="status" id="statusID" value="10" />
                            <h5> <s:property  value="%{'No Users found with the Provided data'}" /> </h5>
                        </s:elseif>
                        <s:elseif test="%{#status==20}">
                            <s:hidden name="status" id="statusID" value="20" />
                            <h5> <s:property  value="%{'Invalid Registered Email ID provided'}" /> </h5>
                        </s:elseif>  
                        <s:elseif test="%{#status==0}">
                            <s:hidden name="status" id="statusID" value="0" />
                        </s:elseif>                 
                        <s:else>
                            <s:hidden name="status" id="statusID" value="100" />
                        </s:else>
                    </c:catch>
                </td>
                <s:if test="%{#exception1 != null}">
                    <c:redirect url="/aascShipError.jsp">
                        <s:param name="errorObj" value="%{#exception1.message}" />
                    </c:redirect>
                </s:if> 
            
            </tr>
        </table>
        <br/>
        <div class="row" align="center">
        <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:70%">
            <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;color:white;" align="center">
                <label style="color:#ffffff;font-size: 20px;">Forgot Password</label>
            </div>
            </br>
            <s:hidden name="actiontype" /> 
                   <%
                   String userName =(String)request.getAttribute("userName");
                   String emailId = (String)request.getAttribute("emailId");
                   pageContext.setAttribute("userName",userName);
                   pageContext.setAttribute("emailId",emailId);
                   %>
                   <s:set name="userName" value="%{#attr.userName}"/>
                   <s:set name="emailId" value="%{#attr.emailId}" />
            <div class="row">
                <div class="col-sm-12">
                <div class="col-sm-3"></div>
                    <div class="col-sm-3">
                        <s:property value="getText('UserName')"/>
                    </div>
                    <div class="col-sm-4" class="dispalyFields">
                         <s:textfield  name="userName" value="%{#userName}"/>
                    </div>
                    <div class="col-sm-2"></div>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-sm-12">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-8">
                         <s:hidden name="SaveButtonId" id="SaveButtonId" value="%{'0'}"/>
                    <s:if test="%{#status==1}">  
                        <!--<img name="Save" id="SaveButton" src="buttons/aascGetPassword2Off.png" alt="" align="middle" border="0" />-->
                        <span class="btn btn-primary" name="Save" id="SaveButton" > Get Password <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></span>
                        <s:set name="status" value="%{'null'}"/>
                    </s:if>
                    <s:else>
                        <!--<s:a  href="#" onclick="return validateFields();"><img name="Save" id="SaveButton" border="0" src="buttons/aascGetPassword2.png" alt="" align="middle" /></s:a>-->
                        <span class="btn btn-primary" name="Save" id="SaveButton"   onclick="return validateFields();" > Get Password <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></span>
                    </s:else>
                    <!--<s:a href="javascript:window.close();" cssStyle=""> <img src="buttons/aascClose2.png" alt="" align="middle" border="0"/> </s:a>-->
                    <span class="btn btn-danger" id="CloseButton"   onclick="javascript:window.close();" > Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></span>    
                    </div>
                    <div class="col-sm-2"></div>
                </div>
            </div>
            <br/>
        </div>
        </div>
    </s:form>
    </div>
     <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
