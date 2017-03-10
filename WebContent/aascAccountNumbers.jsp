 <%/*========================================================================+
@(#)aascAccountNumbers.jsp 28/11/2014
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
  10/03/2015  Jagadish         Added code for new UI changes.
  19/03/2015  Y Pradeep        Modified cssClass from smallHeadings to tableheading for Account Registered title in table.
  10/04/2015  Y Pradeep        Added stopEnterKeyPress function to avoid form submition when enter button is pressed any field is focused in jsp page.
  14/04/2015  Y Pradeep        Modified code to get Account Registered and Negotiated Rates values while editing a particular account details. Bug #2831.
  15/04/2015  Suman G          Added session to fix #2743.
  23/04/2015  Y Pradeep        Removed footer and replaced shipexec logo to apps associates logo.
  05/05/2015  Y Pradeep        Modified code to save data properly after editing and updating also modified code to display message proper alignment and changed background UI color.
  11/06/2015  Suman G          Removed fevicon to fix #2992
  07/07/2015  Suman G          Added Padmavathi's Code to fix issue #3165
  24/07/2015  Rakesh K         Modified Code to work application in offline.
  28/07/2015  Rakesh K         Modified Code UI alignment #3271.
  04/08/2015  Rakesh K         Modified Code bootstrap css file work across all browsers.
  14/08/2015  Dinakar G        Modified Code UI alignment #3406.
  26/08/2015  N Srisha         Added Id's for Automation testing
  26/08/2015  Rakesh K       Added code to solve 3496.
  04/11/2015  Suman G          Added code to fix #3285
+===========================================================================*/
/***
* JSP For Account Numbers
* @version 1
* @author Venkateswaramma Malluru
* history
*
* 30-Jan-2015  Y Pradeep           Ran self audit and code alignment.
* 14-Apr-2015  Y Pradeep           Added stopEnterKeyPress function code to disable form submition for text, radio and checkbox fields.
* 21-Aug-2015  N Srisha            Added jquery, image tag and CSS for ajax loader icon on page load for bug #3392
*/
%>
<link type="text/css" rel="stylesheet" href="aasc_ss.css">
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

        
        <script language="javascript" type="text/javascript"> 
            //This funcion "stopEnterKeyPress(evt)"added by Khaja for Browser Campatability
            function stopEnterKeyPress(evt) {
              var evt = (evt) ? evt : ((event) ? event : null);
              var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
             
              if ((evt.keyCode == 13) && ((node.type=="text") || (node.type=="checkbox") || (node.type=="radio")))  {return false;}
            
            }
            document.onkeypress = stopEnterKeyPress;   ///This line belong to the  above function stopEnterKeyPress(evt) to stop Form submition on press of Enter key
            
            function methodOnUpdate()
            {     
                var acctNumberCount = document.AccountNumbersForm.accountNumberCount.value;
                var count = 0;
                for(var index = 1;index <= acctNumberCount; index++)
                {
                    if(document.AccountNumbersForm['accountNumberActive'+index].checked==true)
                    {
                        document.AccountNumbersForm['accountNumberActive'+index].value="Y";
                    }
                    else 
                    {
                        document.AccountNumbersForm['accountNumberActive'+index].value="N";
                    }
                    
                    if(document.AccountNumbersForm['accountNumberDefault'+index].checked==true)
                    {
                        document.AccountNumbersForm['accountNumberDefault'+index].value="Y";
                    }
                    else
                    {
                        count++;
                        document.AccountNumbersForm['accountNumberDefault'+index].value="N";
                    }  
          
                    if(document.AccountNumbersForm['accountNumberNegotiatedFlag'+index].checked==true)
                    {
                        document.AccountNumbersForm['accountNumberNegotiatedFlag'+index].value="Y";
                    }
                    else 
                    {
                        document.AccountNumbersForm['accountNumberNegotiatedFlag'+index].value="N";
                    }
          
                    if(document.AccountNumbersForm['negotiatedRates'+index].checked==true)
                    {
                        document.AccountNumbersForm['negotiatedRates'+index].value="Y";
                    }
                    else 
                    {
                        document.AccountNumbersForm['negotiatedRates'+index].value="N";
                    }
                } 
        
                if(count == acctNumberCount)
                {
                    alert("Please Select atleast one Default button");
                    return false;
                }
                
                for(var index = 1;index <= acctNumberCount; index++)
                {
                    if((document.AccountNumbersForm['accountNumberDefault'+index].value=="Y") && (document.AccountNumbersForm['accountNumberActive'+index].value=="N"))
                    {   
                        var status1 = confirm('Do you want to enable Active flag for selected default AccountNumber ?');
                        if(status1)
                        {
                            document.AccountNumbersForm['accountNumberActive'+index].checked=true;
                            document.AccountNumbersForm['accountNumberActive'+index].value="Y";
                            return true;
                        }
                        else
                        {
                            return false;
                        }  
                    }
                }
                return true;
            }
      
            function methodOnEdit(rowCount)
            {         
                document.AccountNumbersForm.currentRow.value=rowCount;
                document.AccountNumbersForm.actiontype.value='Edit';
            } 
      
            function methodOnRegister(rowValue)
            {         
                var accountNo = document.AccountNumbersForm['accountNumber'+rowValue].value;
                document.AccountNumbersForm.UpsAccountNumberHidden.value=accountNo;
                document.AccountNumbersForm.submit();
            } 
      
            function methodForDefault(rowCount)
            {
                var accountNumberCount = document.AccountNumbersForm.accountNumberCount.value;
                for(var index = 1;index <= accountNumberCount; index++)
                {
                    if(rowCount != index)            
                    {
                        document.AccountNumbersForm['accountNumberDefault'+index].checked=false;
                        document.AccountNumbersForm['accountNumberDefault'+index].value="N";
                    }
                    else
                    {
                        document.AccountNumbersForm['accountNumberDefault'+index].checked=true;
                        document.AccountNumbersForm['accountNumberDefault'+index].value="Y";
                    }
                }
            }
      
            function methodForNegotiatedFlag(rowCount)
            {
                var accountNumberNegotiatedFlagStatus = document.AccountNumbersForm['accountNumberNegotiatedFlag'+rowCount].checked;
                
                if(accountNumberNegotiatedFlagStatus == true)
                {
                    document.AccountNumbersForm['negotiatedRates'+rowCount].disabled = false;
                    document.AccountNumbersForm['negotiatedRates'+rowCount].value = "N";
                    
                    document.AccountNumbersForm['accountNumberDefault'+rowCount].disabled = false;
                    document.AccountNumbersForm['accountNumberDefault'+rowCount].value = "N" ;
                }
                else
                {
//                    document.AccountNumbersForm['negotiatedRates'+rowCount].checked = false ;
                    document.AccountNumbersForm['negotiatedRates'+rowCount].value = "N" ;
                    document.AccountNumbersForm['negotiatedRates'+rowCount].disabled = true;
                }
            }
      
            function methodForAccountActive(rowCount)
            {
                var accountNumberActiveStatus = document.AccountNumbersForm['accountNumberActive'+rowCount].checked;
                if(accountNumberActiveStatus == true)
                {
                    if(document.AccountNumbersForm['accountNumberNegotiatedFlag'+rowCount].checked==true)
                    {
                        document.AccountNumbersForm['accountNumberNegotiatedFlag'+rowCount].disabled = false;
//                        document.AccountNumbersForm['accountNumberNegotiatedFlag'+rowCount].checked = true;
                        document.AccountNumbersForm['accountNumberNegotiatedFlag'+rowCount].value = "Y";
                    }else{
                        document.AccountNumbersForm['accountNumberNegotiatedFlag'+rowCount].disabled = false;
//                        document.AccountNumbersForm['accountNumberNegotiatedFlag'+rowCount].checked = false;
                        document.AccountNumbersForm['accountNumberNegotiatedFlag'+rowCount].value = "N";
                    }
          
                    if(document.AccountNumbersForm['negotiatedRates'+rowCount].checked ==true)
                    {
                        document.AccountNumbersForm['negotiatedRates'+rowCount].value = "Y";
//                        document.AccountNumbersForm['negotiatedRates'+rowCount].checked = true;
                        document.AccountNumbersForm['negotiatedRates'+rowCount].disabled = false;
                    }else{
                        document.AccountNumbersForm['negotiatedRates'+rowCount].value = "N";
//                        document.AccountNumbersForm['negotiatedRates'+rowCount].checked = false;
                        document.AccountNumbersForm['negotiatedRates'+rowCount].disabled = true;
                    }
                    
                    document.AccountNumbersForm['accountNumberDefault'+rowCount].disabled = false;
                    document.AccountNumbersForm['accountNumberDefault'+rowCount].checked = false;
                    document.AccountNumbersForm['accountNumberDefault'+rowCount].value = "N";
                }
                else{
                    document.AccountNumbersForm['accountNumberDefault'+rowCount].value = "N";
                    document.AccountNumbersForm['accountNumberDefault'+rowCount].checked = false;
                    document.AccountNumbersForm['accountNumberDefault'+rowCount].disabled = true;
          
                    document.AccountNumbersForm['accountNumberNegotiatedFlag'+rowCount].value = "N";
//                    document.AccountNumbersForm['accountNumberNegotiatedFlag'+rowCount].checked = false;
                    document.AccountNumbersForm['accountNumberNegotiatedFlag'+rowCount].disabled = true;
          
                    document.AccountNumbersForm['negotiatedRates'+rowCount].value = "N";
//                    document.AccountNumbersForm['negotiatedRates'+rowCount].checked = false;
                    document.AccountNumbersForm['negotiatedRates'+rowCount].disabled = true;
                }
            }
         
            function disableActiveDefault()
            {
                var accessValue = document.AccountNumbersForm.accessLevel.value;
                var acctNumberCount = document.AccountNumbersForm.accountNumberCount.value;
                if(accessValue == 4 || accessValue == 2)
                {
                    for(var index = 1;index <= acctNumberCount; index++)
                    {
                        document.AccountNumbersForm['accountNumberActive'+index].disabled = true;
                        document.AccountNumbersForm['accountNumberDefault'+index].disabled = true;
                        document.AccountNumbersForm['accountNumberNegotiatedFlag'+index].disabled = true;
                        document.AccountNumbersForm['negotiatedRates'+index].disabled = true;
                    }
                }
            }
         
            function onLoadActionType()
            { 
                var locationId = window.opener.document.aascCarrierConfigurationForm['locationId'].value;
                var clientId = window.opener.document.aascCarrierConfigurationForm['clientId'].value;
                var carrierCode = window.opener.document.aascCarrierConfigurationForm['carrierCodeValue'].value;
                var acctNumberCount = document.AccountNumbersForm.accountNumberCount.value;//Added by Narasimha 16/11/2010
                for(var index = 1;index <= acctNumberCount; index++)
                { 
                    var accountNumberNegotiatedFlagStatus = document.AccountNumbersForm['accountNumberNegotiatedFlag'+index].checked;
                    if(accountNumberNegotiatedFlagStatus == true && document.AccountNumbersForm['accountNumberActive'+index].checked==true)
                    {
                        document.AccountNumbersForm['negotiatedRates'+index].disabled = false;
//                        document.AccountNumbersForm['negotiatedRates'+index].value = "N";
                        document.AccountNumbersForm['accountNumberDefault'+index].disabled = false;
                    }
                    else if(accountNumberNegotiatedFlagStatus == true && document.AccountNumbersForm['accountNumberActive'+index].checked==false)
                    {
                        document.AccountNumbersForm['negotiatedRates'+index].disabled = true;
                    }
                    else
                    {
//                        document.AccountNumbersForm['negotiatedRates'+index].checked = false ;
                        document.AccountNumbersForm['negotiatedRates'+index].value = "N" ;
                        document.AccountNumbersForm['negotiatedRates'+index].disabled = true;
                    }
                }
            
                document.AccountNumbersForm.locationIdHidden.value = locationId ;
                document.AccountNumbersForm.clientIdHidden.value = clientId ;
                document.AccountNumbersForm.carrierCodeHidden.value = carrierCode;
                document.AccountNumbersForm.actiontype.value ='success';
            }
      
        </script>
        <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
        <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ShipConsole :: Account Numbers</title>
         <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
  
        <style type="text/css">
            .href {color: #003399}
              #divRow{
 margin-left:3%;
 margin-right:3%;
 }
 label{
 color:solid;
 }
 div{
 font-size:12px;
  font-weight: bold;
 }

 textfield {
    width: 150px;
   	border-radius:5px;
}
input[type=tel] {
    width: 150px;
   	border-radius:5px;
}
input[type=email] {
    width: 150px;
   	border-radius:5px;
}
select{
width: 150px;
   	border-radius:5px;
}
 /* Srisha added CSS for ajax loader icon on page load */
#loading {
   width: 100%;
   height: 100%;
   top: 0px;
   left: 0px;
   position: fixed;
   display: block;
   opacity: 0.7;
   background-color: #fff;
   z-index: 99;
   text-align: center;
}

#loading-image {
  position: absolute;
  top: 20%;
  left: 45%;
  z-index: 100;
}
/* css ends here for ajax loader */
        </style>
    </head>
    <body class="gradientbg" onload="onLoadActionType();disableActiveDefault();">
     <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
            }
    %>
        <% try {%>
  <!-- Srisha added ajax loader image  -->          
        <div id="loading" style="background-color:#888888">
          <img id="loading-image" src="images/ajax-loader1.gif" alt="Loading..." />
        </div> <!-- code ends here -->
         <div class="container-fluid" style="background-color:#ADADAD;">
     <s:form name="AccountNumbersForm" method="POST" action="AascAccountNumbersAction">
          
                        <input type="hidden" name="locationIdHidden" id="locationIdHidden" />
                        <input type="hidden" name="clientIdHidden" id="clientIdHidden" />
                        <input type="hidden" name="carrierCodeHidden" id="carrierCodeHidden" />
                        <s:hidden name="UpsAccountNumberHidden" id="UpsAccountNumberHidden" value="%{''}" />
                        <s:set name="locationId" value="%{0}"/>
                        <s:set name="queryTimeOut" value="%{0}"/>
                        <s:set name="accessLevel" value="%{4}"/>
                        <s:set name="ORACLE" value="%{1}"/>
                        <s:set name="carrierCode" value="%{0}"/>
                        <c:set var="carrierCode1" value="${param.carrierCodeHidden}" scope="page" />
                        <s:set name="session" value="#request.session"/>
                        <jsp:useBean id="aascAccountNumbersInfo" class="com.aasc.erp.bean.AascAccountNumbersBean"/>
                        <jsp:useBean id="accountNumbersList" class="java.util.LinkedList"/>
                        
                        <c:catch var="exception">
                            <s:set name="aascAccountNumbersInfo" value="%{#session.aascAccountNumbersInfo}"/>
                            <s:set name="accessLevel" value="%{#session.role_id}"/>
                            <s:set name="queryTimeOut" value="%{#session.queryTimeOut}"/>
                            <s:set name="carrierCode" value="%{#attr.carrierCode1}"/>
                        </c:catch>
                        <s:if test="%{#exception != null}">

                            <!--  <c:redirect url="/aascShipError.jsp">
                                    <s:param name="errorObj" value="%{#exception.message}" />
                            </c:redirect> -->
                        </s:if>
                        
                        <s:if test="%{#aascAccountNumbersInfo != null}">
                            <s:set name="accountNumbersList" value="%{#aascAccountNumbersInfo.accountNumbersList}"/>
                            <s:set name="locationId" value="%{#aascAccountNumbersInfo.locationId}"/>
                        </s:if>
                        <s:hidden name="accessLevel" value="%{#attr.role_id}"/>
                        <s:hidden name="locationId" id="locationID" value="%{#attr.locId}"/>
                      <br/><br/> 
                <table width="" border="0" cellpadding="0" cellspacing="1" align="center">
                            <tr valign="bottom">
                            <!--    <td  class="displayMessage" width="30%"><img src="images/aasc_Apps_Logo.png" alt="" width="202" height="48" onClick=""> </td>-->
                                <td width="5%" class="displayMessage">&nbsp;</td>
                                <c:catch var="exception1">
                                        <s:set name="key" value="%{#attr.key}"/>
                                        <s:if test="%{#key != null}">
                                            <s:if test="%{#key==aasc700||#key==aasc702||#key==aasc704||#key==aasc706||#key==aasc710}">
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
                                            <s:param name="errorObj" value="%{#exception.message}" />
                                        </c:redirect>
                                    </s:if>
                                <td width="5%" class="displayMessage">
                                    <div align="right">
                                        <s:url  id="/aascShipConsoleIndex.jsp"></s:url>
                                        <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1">
                                            <tr>
                  <!--td width="15%"><div ><a href="aascShipConsoleIndex.jsp"><img src="images/home.png" width="20" height="20" border="0"></a></div></td-->  
                  <!--<td width="10%"><div ><a href="aascAccountNumbersHelp.jsp" target="_blank"><img src="images/help.png" width="20" height="20" border="0"></a></div></td>-->
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                            </tr>  
                        </table>  
           <br/><br/>
                        <s:hidden name="actiontype" value="%{''}"/>
                        <table width="90%" border="0" align="center">
                            <tr width="40%">
                   <!--             <td  width="30%" colspan="23" class="pageHeading"  border="0">Account Numbers</td -->
                                <s:if test="%{#accessLevel == 3 || #accessLevel == 1}">
                                    <td align="right">    
                                        <button name="createButton" id="createButtonId" class="btn btn-primary" onclick="document.AccountNumbersForm.actiontype.value='Create';" value="Create"  >Create <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                             &nbsp;&nbsp;&nbsp;&nbsp;
                                       <!-- <input name="NewButton" type="image" id="NewButton" onclick="document.AccountNumbersForm.actiontype.value='Create';" value="Create" src="buttons/aasccreate1.png" alt="Create" align="middle">-->
                                        <button name="SaveButton" id="SaveButton" class="btn btn-info" onclick="document.AccountNumbersForm.actiontype.value='Update'; return methodOnUpdate();" value="Update" >Update <span class="badge"><span class="glyphicon glyphicon-pencil"></span></span></button>
                                       <!-- <input name="SaveButton" type="image" id="SaveButton" onclick="document.AccountNumbersForm.actiontype.value='Update'; return methodOnUpdate();" value="Update" src="buttons/update1.png" alt="Update" align="middle">-->
                <!--input type="BUTTON" name="go" value="Go" onclick="methodOne();"/-->
                                    </td>
                                </s:if>
                            </tr>
                        </table>
                
               </br> 
              
                 <!-- <div class="container-fluid" style="width:100% ;margin-left:auto; margin-right:auto;margin-top:50px;border:1px #ECE0F8;border-radius:5px;padding-left:0px;padding-right:0px;background-color:#F0F0F0">

                          <div  style="color: white;width:100%; height:40px;font-size:20px; background-color:#19b698; font:#f7f7ff; ">
<label style="margin-left:30px;margin-top:5px;">-->


 <div class="form-group">
  <div class="col-md-12" style="border-color:#7761a7; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;"> 
 <!-- <fieldset class="scheduler-border" style="border-color:#19b698; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;"> -->
       <div style="background-color:#7761a7;margin-top:5px;overflow:auto;" align="center">
        <label style="color:#ffffff;font-size:20px; ">Account Numbers </label>
 </div>
  </br>
  <div style="width:100%;overflow-x: auto;"> 
            <table  id="table" class="table" style="font-size:small;" >
              
                            <tr style=" background-color:#19b698;"  >
                                <s:if test="%{#accessLevel == 3 || #accessLevel == 1}">
                                    <th style="color:white;font-weight:bold"><s:property value="getText('Edit')"/></th><!--Added to fix issue 3165-->
                                </s:if>
          <!--td align="left" nowrap class="smallHeadings" >Account Number Identifier</td-->
          <!--td align="left" nowrap class="smallHeadings" >Carrier Code</td-->
                                <th style="color:white;font-weight:bold" nowrap> <s:property value="getText('AccountNumber')"/> </th><!--Added to fix issue 3165-->
                                <s:if test="%{#carrierCode == 110 || #carrierCode == 111 || #carrierCode == 112}">
                                    <th style="color:white;font-weight:bold" nowrap><s:property value="getText('MeterNumber')"/></th><!--Added to fix issue 3165-->
                                </s:if>
                                <s:else>
                                    <s:if test="%{#carrierCode == 100}">
                                        <th style="color:white;font-weight:bold" ><s:property value="getText('AccessLicenseNumber')"/></th><!--Added to fix issue 3165-->
                                    </s:if>
                                </s:else>
                                <s:if test="%{#carrierCode == 110 || #carrierCode == 111 || #carrierCode == 112}">
                                   <th style="color:white;font-weight:bold" ><s:property value="getText('AccountUserName')"/></th><!--Added to fix issue 3165-->
                                    <th style="color:white;font-weight:bold" ><s:property value="getText('AccountPassword')"/></th><!--Added to fix issue 3165-->
                                </s:if>
                                <th style="color:white;font-weight:bold" ><s:property value="getText('Active')"/></th><!--Added to fix issue 3165-->
                               <th style="color:white;font-weight:bold" ><s:property value="getText('NegotiatedFlag')"/></th><!--Added to fix issue 3165-->
                               <th style="color:white;font-weight:bold" ><s:property value="getText('NegotiatedRates')"/></th><!--Added to fix issue 3165-->
                                <s:if test="%{#carrierCode == 100}">
                                    <th style="color:white;font-weight:bold" ><s:property value="getText('AccountRegistered')"/> </th><!--Added to fix issue 3165-->
                                </s:if>           
                                <th style="color:white;font-weight:bold" ><s:property value="getText('Default')"/></th><!--Added to fix issue 3165-->
                            </tr>
    
                            <s:hidden name="currentRow"  />
                            <s:set name="accountNumberCount" value="%{0}"/>
                            <s:set name="accountNumberId" value="%{0}"/>

                            <s:iterator id="aascAccountNumbersInfo" value="#accountNumbersList">
                                <s:set name="accountNumberCount" value="%{#accountNumberCount + 1}"/>
                                <s:set name="accountNumberId" value="%{#aascAccountNumbersInfo.accountNumberId}"/>
                                <s:set name="accountNumber" value="%{#aascAccountNumbersInfo.accountNumber}"/>
                                <s:set name="meterNumber" value="%{#aascAccountNumbersInfo.meterNumber}"/>
                                <s:set name="accessLicenseNumber" value="%{#aascAccountNumbersInfo.accessLicenseNumber}"/>
                                <s:set name="accountNumberActive" value="%{#aascAccountNumbersInfo.accountNumberActive}"/>
                                <s:set name="accountNumberDefault" value="%{#aascAccountNumbersInfo.accountNumberDefault}"/>
                                <s:set name="accountNumberNegotiatedFlag" value="%{#aascAccountNumbersInfo.accountNumberNegotiatedFlag}"/>
                                <s:set name="negotiatedRates" value="%{#aascAccountNumbersInfo.negotiatedRates}"/>
                                <s:set name="accountUserName" value="%{#aascAccountNumbersInfo.accountUserName}"/>
                                <s:set name="accountPassword" value="%{#aascAccountNumbersInfo.accountPassword}"/>
                                <%
                                    com.aasc.erp.bean.AascAccountNumbersBean info=(com.aasc.erp.bean.AascAccountNumbersBean)pageContext.getAttribute("aascAccountNumbersInfo1");
             //String accountNumberRegistrationFlag1 = nullStringToSpace(info.getRegistrationStatus());
             //pageContext.setAttribute("accountNumberRegistrationFlag1",accountNumberRegistrationFlag1);
                                %>
                                
                                <s:set name="accountNumberRegistrationFlag1" value="%{#aascAccountNumbersInfo.accountNumberRegistrationFlag}" />
                                <s:set name="accountNumberRegistrationFlag" value="%{#accountNumberRegistrationFlag1}"/>
                                <s:set name="activeCheckstatus" value="%{''}"/>
                                <s:set name="activeCheckDisabled" value="%{''}"/>
                                <s:set name="defaultCheckstatus" value="%{''}"/>
                                <s:set name="negotiatedFlagCheckstatus" value="%{''}"/>
                                <s:set name="negotiatedRatesCheckstatus" value="%{''}"/>
                                <s:set name="registrationFlagCheckstatus" value="%{''}"/>
                                
             
                                <s:if test="%{#carrierCode == 100}">
                                       <s:if test='%{#accountNumberRegistrationFlag.equalsIgnoreCase("Yes")}'>
                                        <s:if test='%{#accountNumberActive == "Y"}'>
                                            <s:set name="activeCheckDisabled" value="%{''}"/>
                                            <s:set name="activeCheckstatus" value="%{'true'}"/>
                                            <s:set name="accountNumberActive" value="%{'Y'}"/>
                                        </s:if>
                                        <s:else>
                                            <s:set name="activeCheckstatus" value="%{'false'}"/>
                                            <s:set name="accountNumberActive" value="%{'N'}"/>
                                        </s:else>
                                    </s:if>
                                    <s:else>
                                        <s:set name="activeCheckDisabled" value="%{'true'}"/>
                                        <s:set name="activeCheckstatus" value="%{'false'}"/>
                                        <s:set name="accountNumberActive" value="%{'N'}"/>
                                    </s:else>
                                </s:if>
                                <s:else>
                                    <s:if test='%{#accountNumberActive == "Y"}'>
                                        <s:set name="activeCheckDisabled" value="%{''}"/>
                                        <s:set name="activeCheckstatus" value="%{'true'}"/>
                                        <s:set name="accountNumberActive" value="%{'Y'}"/>
                                    </s:if>
                                    <s:else>
                                        <s:set name="activeCheckstatus" value="%{'false'}"/>
                                        <s:set name="accountNumberActive" value="%{'N'}"/>
                                    </s:else>
                                </s:else> 
                                <s:if test='%{#accountNumberDefault == "Y"}'>
                                    <s:set name="defaultCheckstatus" value="%{'checked'}"/>
                                    <s:set name="accountNumberDefault" value="%{'Y'}"/>
                                </s:if>
                                <s:else>
                                    <s:set name="defaultCheckstatus" value="%{'unchecked'}"/>
                                    <s:set name="accountNumberDefault" value="%{'N'}"/>
                                </s:else>
                                <s:if test='%{#accountNumberNegotiatedFlag == "Y"}'>
                                    <s:set name="negotiatedFlagCheckstatus" value="%{'true'}"/>
                                    <s:set name="accountNumberNegotiatedFlag" value="%{'Y'}"/>
                                </s:if>
                                <s:else>
                                    <s:set name="negotiatedFlagCheckstatus" value="%{'false'}"/>
                                    <s:set name="accountNumberNegotiatedFlag" value="%{'N'}"/>
                                </s:else>
                                <s:if test='%{#negotiatedRates == "Y"}'>
                                    <s:set name="negotiatedRatesCheckstatus" value="%{'true'}"/>
                                    <s:set name="negotiatedRates" value="%{'Y'}"/>
                                </s:if>
                                <s:else>
                                    <s:set name="negotiatedRatesCheckstatus" value="%{'false'}"/>
                                    <s:set name="negotiatedRates" value="%{'N'}"/>
                                </s:else>
                            <c:catch var="exception2">
                                <s:if test='%{#accountNumberRegistrationFlag == "" || #accountNumberRegistrationFlag.equalsIgnoreCase("No")}'>
                                    <s:set name="accountNumberRegistrationFlag" value="%{'No'}"/>
                                </s:if>
                                <s:else>
                                    <s:set name="accountNumberRegistrationFlag" value="%{'Yes'}"/>
                                </s:else>
                            </c:catch> 
                            <s:if  test="%{#exception2 != null}">
                                <s:property value="%{#exception2.message}"/> 
                            </s:if>
                            <tr>
                                <s:if test="%{#accessLevel == 3 || #accessLevel == 1}">
                                    <td>    
                                        <button name="accountNumberImg<c:out value='${accountNumberCount}'/>" type=""  id="accountNumberImgId<c:out value='${accountNumberCount}'/>" onclick="methodOnEdit('<c:out value='${accountNumberCount}'/>');" class="btn btn-primary" value = "<c:out value='${accountNumberCount}'/>" ><span class="glyphicon glyphicon-pencil"></span></button>
                                    </td>
                                </s:if>
                                <s:hidden name ="accountNumberId%{#accountNumberCount}"   cssClass="dispalyFields"  id="%{#accountNumberCount}" value ="%{#accountNumberId}" />
                                <s:hidden name ="carrierCode%{#accountNumberCount}" cssClass="dispalyFields"  id="%{#accountNumberCount}" value ="%{#carrierCode}"  /> 
                                <s:hidden name ="accountNumber%{#accountNumberCount}" cssClass="dispalyFields"  id="%{#accountNumberCount}" value ="%{#accountNumber}" />
                                <s:hidden name ="meterNumber%{#accountNumberCount}" cssClass="dispalyFields"  id="%{#accountNumberCount}" value ="%{#meterNumber}"  />
                                <s:hidden name ="accessLicenseNumber%{#accountNumberCount}" cssClass="dispalyFields"  id="%{#accountNumberCount}" value ="%{#accessLicenseNumber}" />
                                <s:hidden name ="accountUserName%{#accountNumberCount}" cssClass="dispalyFields"  id="%{#accountNumberCount}" value ="%{#accountUserName}" /> 
                                <s:hidden name ="accountPassword%{#accountNumberCount}" cssClass="dispalyFields"  id="%{#accountNumberCount}" value ="%{#accountPassword}" />          
          
                                <s:hidden name = "AccountNumberIdHidden"  value="%{#accountNumberId}" />
                                <td id="accountNoId<c:out value='${accountNumberCount}'/>" >
                                    <s:property value="%{#accountNumber}"/>
                                </td>
                                <s:if test="%{#carrierCode == 110 || #carrierCode == 111 || #carrierCode==112}">
                                    <td id="metermeNoId<c:out value='${accountNumberCount}'/>"><s:property value="%{#meterNumber}"/></td>
                                </s:if>
                                <s:else>
                                    <s:if test="%{#carrierCode == 100}">
                                        <td id="accessLicenceNoId<c:out value='${accountNumberCount}'/>"><s:property value="%{#accessLicenseNumber}"/></td>
                                    </s:if> 
                                </s:else> 
                                <s:if test="%{#carrierCode == 110 || #carrierCode == 111 || #carrierCode==112}">
                                    <td><s:property value="%{#accountUserName}"/></td>
                                    <td><s:property value="%{#accountPassword}"/></td>
                                </s:if>
                           
                                <td>
                                    <s:checkbox name ="accountNumberActive%{#accountNumberCount}" disabled="%{#activeCheckDisabled}"  
                                                    onclick="methodForAccountActive(%{#accountNumberCount});" cssClass="inputFields"  fieldValue="%{#accountNumberActive}" 
                                                                id="accountNumberActiveId%{#accountNumberCount}" value="%{#activeCheckstatus}" />
                                </td>
                                <td>
                                    <s:if test='%{#activeCheckstatus == "true"}'>
                                        <s:if test="%{#accessLevel == 3 || #accessLevel == 1}">
                                            <s:checkbox name ="accountNumberNegotiatedFlag%{#accountNumberCount}"  onclick="methodForNegotiatedFlag(%{#accountNumberCount});" 
                                                    cssClass="inputFields" fieldValue="%{#accountNumberNegotiatedFlag}" id="accountNumberNegotiatedFlagId%{#accountNumberCount}"  
                                                            value= "%{#negotiatedFlagCheckstatus}" />
                                        </s:if>
                                        <s:else>
                                            <s:checkbox name ="accountNumberNegotiatedFlag%{#accountNumberCount}" disabled="true" 
                                                    onclick="methodForNegotiatedFlag(%{#accountNumberCount});" cssClass="inputFields" fieldValue="%{#accountNumberNegotiatedFlag}" 
                                                            id="accountNumberNegotiatedFlag%{#accountNumberCount}" value = "%{#negotiatedFlagCheckstatus}" />
                                        </s:else>
                                    </s:if>
                                    <s:else>
                                        <s:checkbox name ="accountNumberNegotiatedFlag%{#accountNumberCount}" disabled="true" cssClass="inputFields" 
                                                fieldValue="%{#accountNumberNegotiatedFlag}" id="accountNumberNegotiatedFlag%{#accountNumberCount}" value = "%{#negotiatedFlagCheckstatus}" />
                                    </s:else>
                                </td>
                                <td>
                                    
                                        <s:checkbox name ="negotiatedRates%{#accountNumberCount}" cssClass="inputFields" fieldValue="%{#negotiatedRates}" 
                                                id="negotiatedRates%{#accountNumberCount}" value="%{#negotiatedRatesCheckstatus}"/>
                                    
                                </td>
                                <s:if test="%{#carrierCode == 100}">
                                    <td>
                                        <s:if test='%{#accountNumberRegistrationFlag.equalsIgnoreCase("No")}'>
                                            <s:if test="%{#accessLevel == 3 || #accessLevel == 1}">
                                                <s:textfield name ="accountNumberRegistrationFlag%{#accountNumberCount}"  size="5" onclick="" maxlength="5"   cssClass="inputFields" 
                                                        disabled="%{#registrationFlagCheckstatus}"  id="accountNumberRegistrationFlag%{#accountNumberCount}" value = "%{#accountNumberRegistrationFlag}"/>
                              <!--                  <button  name="registrationImg%{#accountNumberCount}"  id="registrationImg%{#accountNumberCount}" 
                                                        onclick="document.AccountNumbersForm.actiontype.value='Register'; methodOnRegister('%{#accountNumberCount}');" 
                                                                class="btn btn-primary" value = "%{#accountNumberCount}"  >Register </button>   -->
                                            </s:if>
                                            <s:else>
                                                <s:textfield name ="accountNumberRegistrationFlag%{#accountNumberCount}" size="5" onclick="" maxlength="5" cssClass="inputFields"   
                                                        id="accountNumberRegistrationFlag%{#accountNumberCount}" value = "%{#accountNumberRegistrationFlag}"/>
                                            </s:else>
                                        </s:if>
                                        <s:else>
                                            <s:textfield name ="accountNumberRegistrationFlag%{#accountNumberCount}" size="5"  maxlength="5" onclick=""  cssClass="inputFields" 
                                                    disabled="%{#registrationFlagCheckstatus}"  id="accountNumberRegistrationFlag%{#accountNumberCount}" value = "%{#accountNumberRegistrationFlag}" />
                                        </s:else>
                                    </td>
                                </s:if>
                                <td>
                                    <s:if test='%{#activeCheckstatus == "true"}'>
                                        <s:if test='%{#defaultCheckstatus == "checked"}'>
                                            <input name ="accountNumberDefault<c:out value='${accountNumberCount}'/>" type = "radio" 
                                                    onclick="methodForDefault(<c:out value='${accountNumberCount}'/>);" class="inputFields<c:out value='${defaultCheckstatus}'/>"  
                                                            id="accountNumberDefault<c:out value='${accountNumberCount}'/>" value ="<c:out value='${accountNumberDefault}'/>" 
                                                                    checked="<c:out value='${defaultCheckstatus}'/>"/>
                                        </s:if>
                                        <s:else>  
                                            <input name ="accountNumberDefault<c:out value='${accountNumberCount}'/>" type = "radio" 
                                                    onclick="methodForDefault(<c:out value='${accountNumberCount}'/>);" class="inputFields<c:out value='${defaultCheckstatus}'/>"  
                                                            id="accountNumberDefault<c:out value='${accountNumberCount}'/>" value ="<c:out value='${accountNumberDefault}'/>"/>
                                        </s:else> 
                                    </s:if>
                                    <s:else>
                                        <input name ="accountNumberDefault<c:out value='${accountNumberCount}'/>" type = "radio" disabled="disabled" 
                                                onclick="methodForDefault(<c:out value='${accountNumberCount}'/>);" class="inputFields<c:out value='${defaultCheckstatus}'/>"  
                                                        id="accountNumberDefault<c:out value='${accountNumberCount}'/>" value ="<c:out value='${accountNumberDefault}'/>"/>
                                    </s:else>
                                </td>
                            </tr>
                        </s:iterator>
                    </table>  
                    
                    <s:hidden  name="accountNumberCount" value="%{#accountNumberCount}"/>
                    </div>
                    </br>
               <!--    </fieldset> -->
               </div>
                 </div>   
    </s:form>
    </div> 
    <% } catch(Exception e){
        e.printStackTrace();
    }%>
  <!-- Srisha added jquery for ajax loader icon on page load -->  
     <script language="javascript" type="text/javascript">
         $(window).load(function() {
         //alert("Window Loaded..");
         $('#loading').hide();
         });
    </script> 
    <!-- Code ends here-->
 <div style="position:relative;top:400px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
</html>
