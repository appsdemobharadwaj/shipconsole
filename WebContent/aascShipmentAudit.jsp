<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%       
/*====================================================================================+
|  DESCRIPTION                                                                        |
|    JSP aascShipConsoleShipment for displaying the Shipment  related                 | 
|    information for role 2                                                           |
|    Version   1.1                                                                    |                                                                            
|    History                                                                          |
|                                                                                     |
|  27/11/2014  Eshwari M     Added this file from SC Cloud 1.2 version for SC Lite    |   
|  27/12/2014  Eshwari M     Moved javascript code to js file                         |   
| 05/03/2015    Sanjay & Khaja Added code for new UI changes.
   10/03/2015  Y Pradeep     Added missing name for param tag.
   23/04/2015   Y Pradeep    Removef footer.
   12/08/2015   Rakesh K    Applied New Bootstrap UI.
   08/09/2015   Rakesh K    Applied https for Bootstrap.
   08/09/2015   Rakesh K    Removed Class in body for IE compatability.
+=====================================================================================*/%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%> 
<%@ taglib uri="/struts-tags" prefix="s"%>

 <%@ include file="aascIndexHeader.jsp"%>
<html>
   <head> 
   
    <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
   
    <script language="javascript" src="bootstrap/js/jquery11.min.js" type="text/javascript"></script>
    <!--<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>-->
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link href="https://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <script language="javascript" src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
   
   
   
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>ShipConsole :: Location Select</title>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <link href="aasc_ss.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
       html{height:100%}
    </style> 
    <style type="text/css"> 
              #ClassWidth { 
                             width: 14em; 
                          } 
    </style> 
    <style type="text/css">
        .href {color: #003399; font-weight: bold;}
        .href1 {color: #003399}
    </style>
    
    <script src="aascShipmentAudit_js.js" type="text/javascript"></script>
    
  </head>
  <body onload="onClientChange();">
  <br/><br/> <br/><br/>
  <div  class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:10%;margin-right:10%;width:80%">
    <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
        <label style="color:#ffffff;font-size:20px;padding-left:10px"><s:property value="getText('SelectLocation')"/></label>
    </div>
  <s:form name="aascShipmentAuditForm" method="post" action="AascShipmentAuditAction">
   <div class="row">
    <div class="col-sm-12">
        <s:bean name="java.util.HashMap" id="clientDetailsHashMap"></s:bean>
        
        <s:set name="readOnlyFlag" value="READONLY"/>
        
        <s:set name="disableFlag" value="DISABLED"/>
        <s:set name="roleIdTemp" value="%{0}"/>
        <s:set name="roleIdTemp" value="%{#session.role_id}"/>
        <s:hidden name="shipmentRequest" value='%{#session.ShipmentRequest}' />
   
        <s:set name="clientIdMain" value="%{''}" />
        <s:set name="clientIdNum" value="%{''}" />
        <s:set name="clientChkCondition" value="%{''}" />

        <s:if test="%{#roleIdTemp == 2}">
           <s:set name="clientIdMain" value="%{#parameters.clientId}" />
           <s:if test="%{#clientIdMain == null || #clientIdMain == ''}">
              <s:set name="clientIdMain" value="%{#attr.clientId}" />
           </s:if>
           <c:catch var="clientException">
             <s:if test="%{#clientIdMain == null || #clientIdMain == ''}">
               <s:set name="clientIdMain" value=""/>
             </s:if>
           </c:catch>
           <s:if test="%{#clientException != null}">
             <s:set name="clientIdMain" value="%{''}" />
             <s:property value="clientException"/>
           </s:if>
          
           <s:if test="%{#clientIdMain == ''}">
               <s:set name="clientChkCondition" value="%{'notallow'}" />
           </s:if>
           <s:else>
               <s:set name="clientChkCondition" value="%{'allow'}" />
               <s:set name="clientIdNum" value="%{#clientIdMain}" />
               <s:if test="%{#aascProfileOptionsInfo.clientId != 0 && #aascProfileOptionsInfo.clientId != #clientIdNum}">
                  <s:set name="clientIdNum" value="%{#aascProfileOptionsInfo.clientId}" />
               </s:if>
           </s:else>
         </s:if>
           
        <c:catch var="exception2">
          <s:set name="aascProfileOptionsInfo" value="%{#session.ProfileOptionsInfo}" />
          <s:if test="%{#aascProfileOptionsInfo == null }">
            <s:set name="exception2" value="%{'nullPointerException'}" /> 
          </s:if>
        </c:catch>    
        <s:if test="%{#exception2 != null}">
          
        </s:if>    
        
        <c:catch var="exception3">
          <s:set name="aascShipMethodInfo" value="%{#session.ShipMethodInfo}"/> 
          <s:if test="%{#aascShipMethodInfo == null}">
            <s:set name="exception3" value="%{'nullPointerException'}"/>
          </s:if>
        </c:catch>
        <s:if test="%{#exception3 != null}">
          
        </s:if>    
                      
        <c:catch var="customerException">
            <s:set name="clientDetailsHashMap" value="%{#session.clientDetailsHashMap}"/>
        </c:catch>
        <s:if test="%{#customerException != null}">
            <s:bean name="java.util.HashMap" id="clientDetailsHashMap">
               <s:param  name="clientDetailsHashMap" value="%{'select'}"/>
            </s:bean>
        </s:if>
        
        <s:set name="roleId" value="%{#session.role_id}"/>  
        <s:hidden name="roleId" id="roleId" value="%{#roleId}"/>
    </div>
    <br/>
    <div class="row">
        <div class="col-sm-12">
        <label for="inputEmail" class="control-label col-sm-2"> <span class="dispalyFields" ><s:property value="getText('ClientDetails')"/> </span></label>
            <div class="col-sm-2">
        
        <!--    <div class="col-sm-2"><h6><s:property value="getText('ClientDetails')"/></h6></div>
            <div class="col-sm-2" style="padding-top: 10px;"> -->
                <s:if test='%{#clientChkCondition == "notallow"}'>
                <!--    <s:select name="clientId" cssStyle="width:100%" id="clientId" cssClass="inputFields" size="1" onchange="onClientChange();"
                      list="#clientDetailsHashMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey=""/> -->
                      
                       <s:select list="#clientDetailsHashMap" name="clientId" id="clientId" 
                        class="form-control" listKey="key" listValue="value"  size="1" onchange="onClientChange();" headerValue="--Select One--" headerKey=""/>
                  </s:if>
                  <s:else>
                <!--    <s:select name="clientId" id="clientId" cssClass="inputFields" size="1" onchange="onClientChange();"
                      list="#clientDetailsHashMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey=""  value="%{#clientIdNum}" />  -->
                      
                      <s:select list="#clientDetailsHashMap" name="clientId" id="clientId" 
                        class="form-control" listKey="key" listValue="value"  size="1" onchange="onClientChange();" headerValue="--Select One--" headerKey="" value="%{#clientIdNum}"/>
                  </s:else>
            </div>
            <div class="col-sm-1">
           </div>
           
           <label for="inputEmail" class="control-label col-sm-1"> <span class="dispalyFields"><s:property value="getText('Location')"/></span></label>
            <div class="col-sm-2">
            <s:select name="locationId" id="locationId" size="1" class="form-control" 
                                            list="#{'1':'--Select--'}" listKey="key" listValue="value"  headerKey=""/>
           
           
        <!--    <div class="col-sm-2"><h6><s:property value="getText('Location')"/></h6></div>
            <div class="col-sm-2" style="padding-top: 10px;">
                <s:select cssStyle="width:100%" list="#{'1':'--Select--'}" name="locationId" id="locationId" cssClass="inputFields" size="1" /> -->
                  
            </div>
             <div class="col-sm-2">
                <!--<input name="GoButton" type="image" id="GoButton" onclick="document.aascShipmentAuditForm.submit123.value='Go'; return validate();" value="Go" src="buttons/aascGo1.png" alt="[submit]" align="middle">-->
                <button id="GoButton"  name="GoButton" class="btn btn-primary"  onclick="document.aascShipmentAuditForm.submit123.value='Go'; return validate();">Go <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>   
             </div>
        </div>
        <br/><br/><br/><br/>
    </div>
   </div>
   <!--<table style="height:100" width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="56">
        
      </td>
    </tr>
    <tr>
      <td align="left" valign="top" class="pageHeading"><div align="left"></div></td>
    </tr>
        
    <tr>
      <td height="400">
        <table style="height:100%" width="100%" border="0" cellpadding="0" cellspacing="0">
           <tr> <td height="10" colspan="4">&nbsp;&nbsp;</td></tr>
           <tr valign="top">  
              <td width="6%" align="left" style="border: 0px; border-collapse: collapse;"><STRONG>&nbsp;<s:property value="getText('ClientDetails')"/> :</STRONG> </td>                
              
              <td width="10%" align="left" style="border: 0px; border-collapse: collapse;"> 
                  <s:if test='%{#clientChkCondition == "notallow"}'>
                    <s:select name="clientId" id="clientId" cssClass="inputFields" size="1" onchange="onClientChange();"
                      list="#clientDetailsHashMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey=""/> 
                  </s:if>
                  <s:else>
                    <s:select name="clientId" id="clientId" cssClass="inputFields" size="1" onchange="onClientChange();"
                      list="#clientDetailsHashMap" listKey="key" listValue="value" headerValue="--Select One--" headerKey=""  value="%{#clientIdNum}" /> 
                  </s:else>
              </td>
              <td width="10%" align="right" style="border: 0px; border-collapse: collapse;"><STRONG><s:property value="getText('Location')"/> :</STRONG></td>
              <td width="13%" align="left" style="border: 0px; border-collapse: collapse;">
                  <s:select  list="#{'1':'--Select--'}" name="locationId" id="locationId" cssClass="inputFields" size="1" />
                   &nbsp; &nbsp;  &nbsp; &nbsp;
                  <input name="GoButton" type="image" id="GoButton" onclick="document.aascShipmentAuditForm.submit123.value='Go'; return validate();" value="Go" src="buttons/aascGo1.png" alt="[submit]" align="middle">
              </td>
              <td  width="20%">
              </td>
           </tr>     
        </table>
      </td>
    </tr>
   </table>-->
  </s:form>
  </div>
   <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
