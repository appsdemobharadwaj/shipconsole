 <%       
/*==========================================================================+
|  DESCRIPTION                                                              |
|    JSP aascChangePassword for displaying the change password fields       | 
|    information                                                            |
|    Author Suman Gunda                                                     |
|    Version   1.1                                                          |                                                                            
|    Creation  04/02/2015                                                   |      
|    History :                                                              |
    23/03/2015      Suman G         Added actiontype to fix #2727.
    05/05/2015      Y Pradeep       Aded new UI background color.
    02/06/2015      Suman G         Changed error page to fix #2743.
    22/07/2015      Rakesh K        Modified UI Alignment.
    24/07/2015      Rakesh K        Modified Code to work application in offline.
    04/08/2015      Rakesh K        Modified Code bootstrap css file work across all browsers.
    26/08/2015  Rakesh K       Added code to solve 3496.
+===========================================================================*/%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    
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
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://markusslima.github.io/bootstrap-filestyle/js/bootstrap-filestyle.min.js"> </script>-->
   
    <title><s:property value="getText('ChangePasswordTitle')"/></title>
     <link href="aasc_ss.css" rel="stylesheet" type="text/css"/>
     <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
     <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    
    <style type="text/css">
        #divSub{
            margin-left:10px;; 
            margin-right:10px;
        }
    </style>
    
    
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
        
       
        
    <script type="text/javascript" src="aascChangePassword.js" ></script>
  </head>
  
  <body style="background-color:#ADADAD" onload="onload()">
  <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
  <form name="changePasswordForm" method="POST" action="AascChangePassword" class="form" style="margin-left:auto; margin-right:auto;border-radius:5px;">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
       <tr>
         <td>  </td>
         <td width="90%" class="displayMessage" id="displayMessage" align="right" valign="bottom">
            <c:catch var="exception1">
                <s:set name="status" value="%{#attr.status}"/>
                <s:set name="errorStatus" value="%{#attr.errorStatus}" />
    
                <s:set name="loginUsername" value="%{#attr.LoginuserName}" />
         
                <s:if test="%{#status==1}">           
                  <s:hidden name="status" id="statusID" value="%{#status}" />
                  <s:set name="status" value="%{'null'}"/>
                </s:if>
                <s:elseif test="%{#status==10}">
                    <s:hidden name="status" id="statusID" value="10" />
                    <h5 class="displayMessage"> <s:property value="%{'Username or Password is Incorrect'}" /> </h5>
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
     <br/><br/><br/>
        <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:95%">
        <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px">
                       <label style="color:#ffffff;padding-left:10px;font-size:20px">Change Password</label>
                    </div>
                    </br>
                        <div class="row"><br/></div>
                        
				

      <!-- <tr>
        <td colspan="4"><h3 class="pageHeading"><s:property value="getText('ChangePassword')"/></h3>
            
        </td>
       </tr>-->
        <s:hidden name="actiontype" /> 
            <div class="row" id="divSub" >
                <label for="inputEmail" class="control-label col-sm-4"> <span class="dispalyFields">&nbsp;<s:property value="getText('UserName')"/> :</span></label>
                <div class="col-sm-8">
                   <s:textfield name="userNameCPwd" id="userNameCPwdID" class="form-control" value="%{#loginUsername}" readonly="true" />
                </div>
            </div>
            <div class="row"><br/></div>
            <div class="row" id="divSub">
               <label for="inputPassword" class="control-label col-sm-4"> <span class="dispalyFields" >&nbsp;<s:property value="getText('OldPassword')"/> :</span></label>
                <div class="col-sm-8">
                    <s:password name="oldPasswordCPwd" id="oldPasswordCPwdID"  class="form-control" value=""/>
                </div>
            </div>
            <div class="row"><br/></div>
            <div class="row" id="divSub" >
                <label for="inputNumber" class="control-label col-sm-4"><span class="dispalyFields" >&nbsp;<s:property value="getText('NewPassword')"/> :</span></label>
                <div class="col-sm-8">
                    <s:password name="newPasswordCPwd" id="newPasswordCPwdID" class="form-control" value=""/>
                </div>
             </div> 
             <div class="row"><br/></div>
            <div class="row" id="divSub">
                <label for="inputNumber" class="control-label col-sm-4"><span class="dispalyFields" >&nbsp;<s:property value="getText('ReTypePassword')"/> :</span></label>
                <div class="col-sm-8">
                    <s:password name="reTypePasswordCPwd" id="reTypePasswordCPwdID" class="form-control" value=""/>
                </div>
            </div>  
            <div class="row"><br/></div>
            <div class="row" id="divSub">
                 <div class=" col-sm-1" >
                 </div>
                <div class=" col-sm-10" align="center">
                    <button class="btn btn-success" name="Save" id="SaveButton" onclick="document.changePasswordForm.actiontype.value='ChangePassword';return save();" value="AddNewUser" alt="[reset]" align="middle"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                    <button class="btn btn-warning" name="reset" id="reset" onclick="window.close()" value="Cancel" alt="[reset]" align="middle"> Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                 </div>
                <div class=" col-sm-1" >
                 </div>
            </div>
                    
                     
                
           <div class="row"><br/></div>  
    </div>
    </form>
  </div>
   <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
