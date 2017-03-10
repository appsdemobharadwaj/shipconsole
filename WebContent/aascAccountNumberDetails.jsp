
<%/*========================================================================+
@(#)aascAccountNumberDetails.jsp 28/11/2014
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
+===========================================================================*/
/***
* JSP For Account Number Details to create new Account Number
* @version 1
* @author Venkateswaramma Malluru
* history
*
* 15/01/2015   Y Pradeep      Merged Sunanda's code : getting title name from Application.Property file.
* 21/01/2015   K Sunanda      Added code to display success messages in green and errored in red for bug #2506 
* 30/01/2015   Y Pradeep      Ran self audit and code alignment.
* 14/04/2015   Y Pradeep      Modified code to get Account Registered and Negotiated Rates values while editing a particular account details. Bug #2831.
* 16/04/2015   Suman G        Added session to fix #2743
* 23/04/2015   Y Pradeep      Removed footer and replaced shipexec logo to apps associates logo.
* 05/05/2015   Y Pradeep      Modified code to save data properly after creating, editing and updating.
* 02/06/2015   Suman G        Changed error page to fix #2743.   
* 25/06/2015   Suman G        Added Padmavathi's code for issue #3070 to remove junk characters
* 24/07/2015   Rakesh K       Modified Code to work application in offline.
  04/08/2015   Rakesh K       Modified Code bootstrap css file work across all browsers.
  26/08/2015  Rakesh K       Added code to solve 3496.
*/
%>

<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>  
<%@ taglib uri="/struts-tags" prefix="s"%>

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
 
 <!--<meta name="viewport" content="width=device-width, initial-scale=1"></meta>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
     <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
       <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
           <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />-->
 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><s:property value="getText('SCAccountNumberDetails')"/></title>
        <script  type="text/javascript"  src="aascAccountNumberDetails_js.js"> </script>
        <link type="text/css" rel="stylesheet" href="aasc_ss.css"></link>
        <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
        <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
        
    
         
        <style type="text/css">
            html{height:100%}
           
            #ClassWidth { 
                width: 20em; 
            } 
            #ClassWidth2 { 
                width: 16em; 
            } 
            #ClassWidth3 { 
                width: 6em; 
            } 
         
        </style>
    </head>
    <body style="background-color:#BDBDBD" onload="return loadFunction();">
    <div class="container-fluid" style="background-color:#ADADAD;">
 <!--   <div class="container-fluid" style=" ;margin-left:auto; margin-right:auto;margin-top:50px;padding-left:0px;padding-right:0px;">-->
       <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
            }
    %>
    
    <% try{
    %>
        <s:set name="key" value="%{#attr.key}"/>
        <s:if test="%{#key == 'aasc709'}">
            <s:set name="validateKey" value="%{#key}"/>
        </s:if>
        <jsp:useBean id="aascAccountNumbersInfo" class="com.aasc.erp.bean.AascAccountNumbersBean"/>
        <jsp:useBean id="aascAccountNumbersInfo1" class="com.aasc.erp.bean.AascAccountNumbersBean"/>
        <c:set var="carrierCode1" value="${param.carrierCodeHidden}" scope="page"/> 
        <s:set name="carrierCode" value="%{#attr.carrierCode1}" />
        <s:set name="accountNumberId" value="%{0}"/>
        <s:set name="accountNumber" value="%{''}"/> 
        <s:set name="meterNumber" value="%{''}"/> 
        <s:set name="accountUserName" value="%{''}"/> 
        <s:set name="accountPassword" value="%{''}"/> 
        <s:set name="accessLicenseNumber" value="%{''}"/> 
        <s:set name="accountNumberDefault" value="%{''}"/> 
        <s:set name="accountNumberActive" value="%{''}"/> 
        <s:set name="accountNumberNegotiatedFlag" value="%{''}"/> 
        <s:set name="negotiatedRates" value="%{''}"/> 
        <s:set name="negotiatedFlagStatus" value="%{''}"/> 
        <s:set name="negotiatedRatesStatus" value="%{''}"/> 
        <s:set name="accountNumberRegistrationFlag" value="%{''}"/> 
        <s:set name="accountNumberRegistrationFlagStatus" value="%{''}"/> 
        <c:set var="rowNo1" value="${param.currentRow}" scope="page"/>
        <s:set name="rowNo" value="%{#attr.rowNo1}" />
        <c:set var="actionType1" value="${param.actiontype}" scope="page"/>
        <s:set name="actionType" value="#attr.actionType1"/>
        
        <s:if test="%{#actionType == 'Edit' || #actionType == 'ErrorCondition'}">
        <%
            String rowNo1= (String)pageContext.getAttribute("rowNo");
            String accountNumberIdStr = request.getParameter("accountNumberId"+rowNo1); 
            int accountNumberId1 = Integer.parseInt(accountNumberIdStr);
            pageContext.setAttribute("accountNumberId1",accountNumberId1);
            String accountNumber1   = request.getParameter("accountNumber"+rowNo1) ;  
            pageContext.setAttribute("accountNumber1",accountNumber1);
            String meterNumber1   = request.getParameter("meterNumber"+rowNo1);  
            pageContext.setAttribute("meterNumber1",meterNumber1);
            String accessLicenseNumber1  = request.getParameter("accessLicenseNumber"+rowNo1); 
            pageContext.setAttribute("accessLicenseNumber1",accessLicenseNumber1);
            String accountNumberDefault1 = request.getParameter("accountNumberDefault"+rowNo1); 
            pageContext.setAttribute("accountNumberDefault1",accountNumberDefault1);       
            String accountUserName1 = request.getParameter("accountUserName"+rowNo1);  
            pageContext.setAttribute("accountUserName1",accountUserName1);
            String accountPassword1 = request.getParameter("accountPassword"+rowNo1);  
            pageContext.setAttribute("accountPassword1",accountPassword1);
        %>
            <s:set name="accountNumberId" value="%{#attr.accountNumberId1}"/>
            <s:set name="accountNumber" value="%{#attr.accountNumber1}"/>
            <s:set name="meterNumber" value="%{#attr.meterNumber1}"/>
            <s:set name="accountUserName" value="%{#attr.accountUserName1}"/>
            <s:set name="accountPassword" value="%{#attr.accountPassword1}"/>
            <s:set name="accessLicenseNumber" value="%{#attr.accessLicenseNumber1}"/>
            <s:set name="accountNumberDefault" value="%{#attr.accountNumberDefault1}"/>
            <s:if test="%{#accountNumberDefault == null}">
                <s:set name="accountNumberDefault" value="%{'N'}"/>
            </s:if> 
            <%
                String accountNumberActive1=request.getParameter("accountNumberActive"+rowNo1);
                pageContext.setAttribute("accountNumberActive1",accountNumberActive1);
            %>
            <s:set name="accountNumberActive" value="%{#attr.accountNumberActive1}"/>
            <s:if test="%{#accountNumberActive == null}">
                <s:set name="accountNumberActive" value="%{'N'}"/>
            </s:if> 
            <%
                String accountNumberNegotiatedFlag1 = request.getParameter("accountNumberNegotiatedFlag"+rowNo1); 
                pageContext.setAttribute("accountNumberNegotiatedFlag1",accountNumberNegotiatedFlag1);
            %>
            
            <s:set name="accountNumberNegotiatedFlag" value="%{#attr.accountNumberNegotiatedFlag1}"/>
            <s:if test="%{#accountNumberNegotiatedFlag == null}">
                <s:set name="accountNumberNegotiatedFlag" value="%{'N'}"/>
            </s:if>
            <%
                String negotiatedRates1 = request.getParameter("negotiatedRates"+rowNo1); 
                String accountNumberRegistrationFlag1=request.getParameter("accountNumberRegistrationFlag"+rowNo1);
                
                pageContext.setAttribute("negotiatedRates1",negotiatedRates1);
                pageContext.setAttribute("accountNumberRegistrationFlag1",accountNumberRegistrationFlag1);
            %>
             
            <s:set name="negotiatedRates" value="%{#attr.negotiatedRates1}"/>
            
            <s:set name="accountNumberRegistrationFlag" value="%{#attr.accountNumberRegistrationFlag1}"/>
            <s:if test="%{#accountNumberRegistrationFlag == null}">
                <s:set name="accountNumberRegistrationFlag" value="%{'No'}"/>
            </s:if>
            <s:if test="%{#negotiatedRates == null}">
                <s:set name="negotiatedRates" value="%{'N'}"/>
            </s:if>
            <s:if test='%{#accountNumberRegistrationFlag == "Yes"}'>
                <s:set name="accountNumberRegistrationFlagStatus" value="%{'true'}"/>
                <s:set name="accountNumberRegistrationFlag" value="%{'Yes'}"/>
            </s:if>
            <s:else>
                <s:set name="accountNumberRegistrationFlagStatus" value="%{'false'}"/>
                <s:set name="accountNumberRegistrationFlag" value="%{'No'}"/>
            </s:else>
            <s:if test='%{#accountNumberNegotiatedFlag == "Y"}'>
                <s:set name="negotiatedFlagStatus" value="%{'true'}"/>
            </s:if>
            <s:else>
                <s:set name="negotiatedFlagStatus" value="%{'false'}"/>
            </s:else>
            <s:if test='%{#negotiatedRates == "Y"}'>
                <s:set name="negotiatedRatesStatus" value="%{'true'}"/>
            </s:if>
            <s:else>
                <s:set name="negotiatedRatesStatus" value="%{'false'}"/>
            </s:else>
        </s:if>
        <s:else>
            <s:if test='%{#actionType == "EditSave"}'>
                <s:set name="aascAccountNumbersInfo" value="%{#session.aascAccountNumberEditInfo}" />
                <s:set name="accountNumbersList" value="%{#aascAccountNumbersInfo.accountNumbersList}"/> 
                <s:iterator id="aascAccountNumbersInfo1" value="%{#accountNumbersList}">
                    <s:set name="accountNumberId" value="%{#aascAccountNumbersInfo1.accountNumberId}"/>
                    <s:set name="accountNumber" value="%{#aascAccountNumbersInfo1.accountNumber}"/>
                    <s:set name="meterNumber" value="%{#aascAccountNumbersInfo1.meterNumber}"/>
                    <s:set name="accountUserName" value="%{#aascAccountNumbersInfo1.accountUserName}"/>
                    <s:set name="accountPassword" value="%{#aascAccountNumbersInfo1.accountPassword}"/>
                    <s:set name="accessLicenseNumber" value="%{#aascAccountNumbersInfo1.accessLicenseNumber}"/>
                    <s:set name="accountNumberDefault" value="%{#aascAccountNumbersInfo1.accountNumberDefault}"/>
                    <s:set name="accountNumberActive" value="%{#aascAccountNumbersInfo1.accountNumberActive}"/>
                    <s:set name="accountNumberNegotiatedFlag" value="%{#aascAccountNumbersInfo1.accountNumberNegotiatedFlag}"/>
                    <s:set name="negotiatedRates" value="%{#aascAccountNumbersInfo1.negotiatedRates}"/>
                    <s:set name="accountNumberRegistrationFlag" value="%{#aascAccountNumbersInfo1.accountNumberRegistrationFlag}"/>
                    <s:if test='%{#accountNumberRegistrationFlag == "Yes"}'>
                        <s:set name="accountNumberRegistrationFlagStatus" value="%{'true'}"/>
                        <s:set name="accountNumberRegistrationFlag" value="%{'Yes'}"/>
                    </s:if>
                    <s:else>
                        <s:set name="accountNumberRegistrationFlagStatus" value="%{'false'}"/>
                        <s:set name="accountNumberRegistrationFlag" value="%{'No'}"/>
                    </s:else>
                    <s:if test='%{#accountNumberNegotiatedFlag == "Y"}'>
                        <s:set name="negotiatedFlagStatus" value="%{'true'}"/>
                    </s:if>
                    <s:else>
                        <s:set name="negotiatedFlagStatus" value="%{'false'}"/>
                    </s:else>
                    <s:if test='%{#negotiatedRates == "Y"}'>
                        <s:set name="negotiatedRatesStatus" value="%{'true'}"/>
                    </s:if>
                    <s:else>
                        <s:set name="negotiatedRatesStatus" value="%{'false'}"/>
                    </s:else>
                </s:iterator>
            </s:if> 
        </s:else> 
        <s:form name="AccountNumberDetailsForm" method="POST" action="AascAccountNumberDetailsAction" >
          
            <s:set name="locationIdHiddenStr" value="%{#parameters.locationIdHidden}" />
            <s:set name="clientIdHiddenStr" value="%{#parameters.clientIdHidden}" />
            <s:hidden name="locationIdHidden" id="locationIdHidden" value="%{#locationIdHiddenStr}"/>
            <s:hidden name="clientIdHidden" id="clientIdHidden" value="%{#clientIdHiddenStr}"/>
            <s:hidden name="carrierCodeHidden" id="carrierCodeHidden" value="%{''}" />
            <s:hidden name="registrationStatus" id="registrationStatus" value="%{''}" />
            <table width="" border="0" cellpadding="0" cellspacing="0" align="center">
                <tr>
                    <td>
                    <!--<img src="images/aasc_Apps_Logo.png" alt="" width="202" height="48" onClick=""/>-->
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
                        <br/>
                        <s:set name="uploadErrorMsg" value="%{#session.uploadErrorMsg}" />
                        <s:if test="%{#uploadErrorMsg != null}">
                            <s:property value=" %{#uploadErrorMsg}" />
                        </s:if>
                        <c:remove var="uploadErrorMsg" scope="session" />
                        <s:set name="uploadErrorMsg" value="%{''}" />
                    </c:catch> 
                    <s:if test="%{#exception1 != null}">
                        <c:redirect url="/aascShipError.jsp">
                            <s:param name="errorObj" value="%{#exception1.message}" />
                        </c:redirect>
                    </s:if>
                    <s:set name="error" value="%{#session.Error}" /> 
                    <c:catch var="exception2">
                        <s:set name="errorStr" value="%{#error"/>
                    </c:catch> 
                    <s:if test="%{#exception2 != null}">
                        <s:set name="error" value="%{''}"/>  
                    </s:if>
                    <s:if test='%{#error == null || #error == ""}'>
                        <s:set name="error" value="%{''}"/>  
                    </s:if> 
                    <s:else>
                        <c:remove var="Error" scope="session"/> 
                    </s:else> 
                    <s:property value="%{#error}"/>
                    <td width="60%" class="displayMessage">
                        <div align="right"><a href="<s:url value="/aascShipConsoleIndex.jsp"/>"/>
                            <table width=""  border="0" align="center" cellpadding="0" cellspacing="0" >
                                <tr>
                 <!--td width="59%"><div ><a href="aascShipConsoleIndex.jsp"><img src="images/home.png" width="20" height="20" border="0"></a></div></td-->
                <!-- <td width="41%"><div ><a href="aascShipFromLocationDetailsHelp.jsp" target="_blank"><img src="images/help.png" width="20" height="20" border="0"></a></div></td> -->
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>
         <!-- <div class="container-fluid" style="width:95% ;margin-left:auto; margin-right:auto;margin-top:50px;border:1px #ECE0F8;border-radius:5px;padding-left:0px;padding-right:0px;background-color:#F0F0F0">

<div  style="color: white;width:100%; height:40px;font-size:20px; background-color:#19b698; font:#f7f7ff; ">
<label style="margin-left:30px;margin-top:5px;">  -->
</br>

<div class="row">
      <div class="col-sm-10 col-sm-push-1 scheduler-border" style="border-color:#7761a7; overflow:auto;margin-left:1%;margin-right:1%;margin-top:1%;background-color:#F0F0F0;"> 
   
         <div style="background-color:#7761a7; margin-top:5px;" align="center">
         <label style="color:#ffffff;font-size:20px">
                    <!--style type="text/css"-->
                        <s:if test='%{#actionType == "Create" || #actionType == "AddNewAccountNumber" || #actionType == "AllowAddNewAccountNumber"}'>
                            <s:set name="negotiatedRatesStatus" value="%{'false'}"/>
                            <s:set name="negotiatedRates" value="%{'N'}"/>
                            <s:set name="negotiatedFlagStatus" value="%{'false'}"/>
                            <s:set name="accountNumberNegotiatedFlag" value="%{'N'}"/>
                            <s:property value="getText('AccountNumberCreation')"/>
                        </s:if>
                        <s:else>
                            <s:property value="getText('AccountNumberUpdation')"/>
                        </s:else> 
          </label>
         </div>
                    
            </br>
            <s:if test='%{#actionType == "Edit" || #actionType == "Update" || #actionType == "AllowUpdate" || #actionType == "EditSave"}'>
                <s:hidden  name="accountNumberId" value="%{#accountNumberId}" />
            </s:if>
            <s:else>
                <s:hidden  name="accountNumberId" value="%{0}"/>
            </s:else>
            
                <s:hidden name="carrierCode"  value="%{#carrierCode}"/> 
            <div class="row" id="divRow" >
             <div class="col-sm-12">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-4 displayFields"><s:property value="getText('AccountNumber')"/> </div>
                    <div class="col-sm-4"> <s:textfield name="accountNumber" id="accountNumber" value="%{#accountNumber}" maxlength="50" cssClass="inputs"/> 
                        <s:hidden name="locationId" id="locationID" value="%{#attr.locId}"/>
                    </div>
                    <div class="col-sm-2"></div>
             </div>
            </div>
             </br>
            <s:if test="%{#carrierCode == '110' || #carrierCode == '111' || #carrierCode== '112' }">
              <div class="row" id="divRow" >
               <div class="col-sm-12">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-4 displayFields" > <s:property value="getText('MeterNumber')"/> </div>
                    <div class="col-sm-4"> <s:textfield name="meterNumber" id="meterNumber" value="%{#meterNumber}" cssClass="inputs" /> </div>
                    <div class="col-sm-2"></div>
               </div>     
              </div>
               </br>
            </s:if>
            <s:else>
                <s:if test="%{#carrierCode == 100}">
                   <div class="row" id="divRow">
                     <div class="col-sm-12">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-4 displayFields"><s:property value="getText('AccessLicenseNumber')"/> </div>
                        <div class="col-sm-4" > <s:textfield name="accessLicenseNumber" id="accessLicenseNumber" cssClass="inputs"  value="%{#accessLicenseNumber}" /> </div>
                        <div class="col-sm-2"></div>
                      </div>  
                    </div>
                     </br>
                    <div class="row" id="divRow" >
                      <div class="col-sm-12">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-4 displayFields"> <s:property value="getText('IsAccountRegistered')"/> </div>
                        <div class="col-sm-4"  >
                         <s:checkbox name ="accountNumberRegistrationFlag" id ="accountNumberRegistrationFlag"  onclick="return setAccountNumberRegistrationValue();" cssClass="inputFields" 
                                    value="%{#accountNumberRegistrationFlagStatus}" fieldValue="%{#accountNumberRegistrationFlag}"/>
                         
                         <!--   <s:checkbox name ="accountNumberRegistrationFlag"  onclick="return setAccountNumberRegistrationValue();" cssClass="inputFields" 
                                    value="%{#accountNumberRegistrationFlagStatus}" fieldValue="%{#accountNumberRegistrationFlag}"/>
-->                       </div>
                          <div class="col-sm-2"></div>
                        </div>
                     </div>
                      </br>
                </s:if>
            </s:else> 
            <s:if test="%{#carrierCode == 110 || #carrierCode == 111 || #carrierCode==112}">
               <div class="row" id="divRow" >
                  <div class="col-sm-12"> 
                    <div class="col-sm-2"></div>
                    <div class="col-sm-4 displayFields"> <s:property value="getText('AccountUserName')"/></div>
                    <div class="col-sm-4"  >
                        <s:textfield name ="accountUserName" id ="accountUserName" cssClass="inputs"  value="%{#accountUserName}"/> </div>
                    <div class="col-sm-2"></div>
                  </div>
                </div>
                 </br>
                <div class="row" id="divRow" >
                   <div class="col-sm-12">  
                     <div class="col-sm-2"></div>
                     <div class="col-sm-4 displayFields"> <s:property value="getText('AccountPassword')"/></div>
                     <div class="col-sm-4"  >
                        <s:textfield name ="accountPassword" id ="accountPassword"   cssClass="inputs" maxlength="30" value="%{#accountPassword}"/>
                     </div>
                     <div class="col-sm-2"></div>
                    </div>
                </div>
                 </br>
            </s:if> 
            
            <div class="row" id="divRow" >
               <div class="col-sm-12">
                 <div class="col-sm-2"></div>
                 <div class="col-sm-4 displayFields"><s:property value="getText('AccountNumberNegotiatedFlag')"/>
                 </div>
                 <div class="col-sm-4"  >
                     <s:checkbox name ="accountNumberNegotiatedFlag" id ="accountNumberNegotiatedFlag"  onclick="return setNegotiatedFlagValue();" cssClass="inputFields" value="%{#negotiatedFlagStatus}" 
                            fieldValue="%{#accountNumberNegotiatedFlag}"/>
                    <!--<s:checkbox name ="accountNumberNegotiatedFlag"  onclick="return setNegotiatedFlagValue();" cssClass="inputFields" value="%{#negotiatedFlagStatus}" 
                            fieldValue="%{#accountNumberNegotiatedFlag}"/> -->
                </div>
                <div class="col-sm-2"></div>
              </div> 
            </div>
            </br>
            <div class="row" id="divRow" >
              <div class="col-sm-12">
                <div class="col-sm-2"></div>
                <div class="col-sm-4 displayFields"> <s:property value="getText('NegotiatedRates')"/> </div>
                <div class="col-sm-4"  >
                    <s:checkbox name ="negotiatedRates" id ="negotiatedRates" onclick="return setNegotiatedRatesValue();" cssClass="inputFields" value="%{#negotiatedRatesStatus}" fieldValue="%{#negotiatedRates}" />
                    <s:hidden name="accountNumberDefault" id="accountNumberDefault" value="%{#accountNumberDefault}" />
                    <s:hidden name="accountNumberActive" id="accountNumberActive" value="%{#accountNumberActive}" />
               <!-- <s:checkbox name ="negotiatedRates" onclick="return setNegotiatedRatesValue();" cssClass="inputFields" value="%{#negotiatedRatesStatus}" fieldValue="%{#negotiatedRates}" />
                <s:hidden name="accountNumberDefault" id="accountNumberDefault" value="%{#accountNumberDefault}" />
                <s:hidden name="accountNumberActive" id="accountNumberActive" value="%{#accountNumberActive}" />-->
                </div>
                <div class="col-sm-2"></div>
             </div>
           </div>
            
    <!--    <s:if test="%{#carrierCode == 110}">
        <tr>
         <td width="22%" align="right" noWrap><span class="dispalyFields"> <s:property value="getText('SignatureImagePath')"/></span></td>
         <td> 
             <s:textfield name="signatureImagePath" id="signatureImagePath" cssClass="inputFields" />
         </td>
       </tr>
        <tr>
         <td width="22%" align="right" noWrap><span class="dispalyFields"> <s:property value="getText('LogoImagePath')"/> </span></td>
         <td> 
             <s:textfield name="logoImagePath" id="logoImagePath" cssClass="inputFields" />
         </td>
       </tr>
       </s:if>  -->
   
    <center>
        <br/><br/>
        <s:hidden name="actiontype" value="%{#actionType}" /> 
        <s:hidden name="rowNo" value="%{#rowNo}" /> 
        <s:hidden name="validateKey" value="%{#validateKey}" />
        <s:if test="%{#actionType == 'Edit' || #actionType == 'Update' || #actionType == 'EditSave' || #actionType == 'AllowUpdate'}">
            <!--<input name="Update" type="image" id="UpdateButton" onclick="document.AccountNumberDetailsForm.actiontype.value='EditSave'; return checkData(); onUpdate();" 
                    value="Save" src="buttons/update1.png" alt="Update" align="middle">-->
                     <button   name="Update" class="btn btn-primary btn-md" id="UpdateButton" onclick="document.AccountNumberDetailsForm.actiontype.value='EditSave'; return checkData(); onUpdate();" 
                    value="Save" >Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                              
        </s:if>
       
        <s:else>
         <button   name="Save" class="btn btn-primary btn-md" id="SaveButton" onclick="document.AccountNumberDetailsForm.actiontype.value='AddNewAccountNumber'; return checkData(); onUpdate();" 
                    value="Save" >Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                              
           <!-- <input name="Save" type="image" id="SaveButton" onclick="document.AccountNumberDetailsForm.actiontype.value='AddNewAccountNumber'; return checkData(); onUpdate();" 
                    value="Save" src="buttons/aascSave1.png" alt="Save" align="middle">-->
        </s:else>
      <!--  <input name="clearButton" type="image" id="clearButton" onclick="document.AccountNumberDetailsForm.actiontype.value='Cancel';" value="Clear" 
                src="buttons/aascCancel1.png" alt="Clear" align="middle">-->
                 <button   name="clearButton" class="btn btn-Danger btn-md" id="clearButton"  onclick="document.AccountNumberDetailsForm.actiontype.value='Cancel';" value="Clear" 
                     >Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
       
       <!--table align="center" border="0" width="80%">       
         <tr>
           <td height="194" colspan="2" valign="middle">
             <hr>
           </td>
         </tr>
         <tr>
           <td height="14" valign="middle" width="46%">
             <p>
               <strong>Copyright &copy; 2006 Apps Associates. All rights reserved </strong>
               <font size="2" color="#003399">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
             </p>
           </td>
           <td height="14" valign="right" width="54%">
             <strong>Send e-mail to :
               <span class="href">
                 <html:link page="/IndexRequestAction.do?requestType=Mail">
                 <!-/span><span class="href"->
                   <bean:message key="Mail"/>
                 </html:link>
               </span>
             </strong>
           </td>
         </tr> 
       </table-->
     
        </center>
     </br>

        </div>
      </div>  
    </s:form> 
    </div>
    <% }catch(Exception e){
    e.printStackTrace();
    }%>
<div style="position:relative;top:400px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>  
    </body>
</html> 
