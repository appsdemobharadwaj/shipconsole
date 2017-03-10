 
<!-- $Header: /u01/cvs/Products/AA_SAAS_MODULES/ShipConsole_Lyte/Ship/public_html/Attic/aascShipExecIntlAddessDetails.jsp,v 1.1.2.3 2016/03/02 07:24:53 pyammanuru Exp $ -->
<%/*================================================================================================+
|  DESCRIPTION                                                                                      |
|    JSP to display ShipExec Intl Address Details                                                        |
|    Author Joseph                                                                               |
|    Version   1                                                                                    |                                                                            
|    Creation 01-oct-2015                                                                           |
History:
  
+===========================================================================================================*/%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page errorPage="aascShipError.jsp" %>
<%@ page import="java.util.logging.*"%>
<%@ page import="java.util.*" %>
<%@ page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@ page import="com.aasc.erp.util.*"%>
<%@ page import="oracle.jdbc.driver.*"%>
<%@ page import="com.aasc.erp.carrier.*" %>

<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
       <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />-->
    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:property value="getText('ShipExecIntlAddressDetails')"/></title>
        <link type="text/css" rel="stylesheet" href="aasc_ss.css">
        <link type="text/css" rel="stylesheet" href="aasc_index.css">
        <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    
        <script language="javascript" SRC="aascShipExecIntlShipment_js.js">
        </script>
    </head>
    
    <body style="background-color:#ADADAD;" onload="loadUpsIntlAddr()">
     <div class="container-fluid" style="background-color:#ADADAD;">
    <form name="ShipExecIntlAddrForm" method="POST">
         <br/><br/>
          <br/><br/>
         <%!
            static Logger logger = AascLogger.getLogger("aascInternationalShipments.jsp");
            String addOrEditSoldFlag = "DISABLED";
        %>
        <%
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            pageContext.setAttribute("locationId", locationId);
        %>
        
        <s:set name="aascIntlDataBean" value="AascIntlDataBean" />
        <s:set name="impNamesHashMap" value="%{#aascIntlDataBean.impNames}"/>
        <s:set name="locationId" value="%{#attr.locationId}"/>
        <s:hidden name="locationId" id="locationId" value="%{#locationId}"/>
        <s:set name="addOrEditSoldFlag" value="%{'DISABLED'}" />
        <c:set var="shipFlag" value="${param.shipFlag}" />
        <s:set name="shipFlag" value="%{#attr.shipFlag}"/>
        <c:set var="addressType" value="${param.addressType}"/>
        <s:set name="addressType" value="%{#attr.addressType}"/>       

          <s:if test='%{!#shipFlag.equalsIgnoreCase("Y") && #addressType.equalsIgnoreCase("SoldToAddr")}'>
            <%
                 logger.info("################ shipFlagStr is not Y #############");
                addOrEditSoldFlag="";
            %>
            <s:set name="addOrEditSoldFlag" value="%{''}" />
         </s:if>
          <s:if test='%{#addressType.equalsIgnoreCase("SoldToAddr")}' >
                <s:set name="detailsType" value="%{'Sold To'}" />
            </s:if>
            <s:elseif test='%{#addressType.equalsIgnoreCase("FAgentInfo")}' >
                <s:set name="detailsType" value="%{'Forward Agent'}" />
            </s:elseif>
            <s:elseif test='%{#addressType.equalsIgnoreCase("NAFTAAddr")}' >
                <s:set name="detailsType" value="%{'NAFTA'}" />
            </s:elseif>
            <s:elseif test='%{#addressType.equalsIgnoreCase("ConsigneeInfo")}' >
                <s:set name="detailsType" value="%{'Consignee'} " />
            </s:elseif>
            <s:else>
                <s:set name="detailsType" value="%{'Intermediate Consignee'}" />
            </s:else>
                        
         <s:hidden value="%{#shipFlag}" name="shipFlag"/>
        <s:hidden name="addressType" value="%{#addressType}" />
        
        <div class="row" align="middle">
          <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%">
   <div style="background-color:#7761a7;margin-top:5px;margin-left:5px">
   <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;"><s:property value="%{#detailsType}"/> <s:property value="getText('Details')"/></label>
   </div>
            <br/>
            <c:if test="${fn:toLowerCase(addressType) == fn:toLowerCase('SoldToAddr')}" >
            <div class="row">
                <div class="col-sm-12">
                    <div class="col-sm-3 dispalyFieldsUPSAddrDetails" align="left">
                        <s:property value="getText('SelectSoldTo')"/> :
                    </div>
                    <div class="col-sm-3 tableDataCell" align="left">
                        <s:select list="#impNamesHashMap" name="selImporterName" id="upsIntlAddrselImporterNameID" listKey="key" listValue="value" headerKey="" headerValue="Select"
                            cssClass="inputs" onchange="getIntlImporterDetails();" />
                    </div>
                </div>
            </div>
            </c:if>
            <br/>
            <div class="row">
                <div class="col-sm-12">
                    <div class="col-sm-3 dispalyFieldsUPSAddrDetails" align="left">
                        <s:property value="getText('CompanyName')"/>
                    </div> 
                    <div class="col-sm-3" align="left">
                        <s:textfield cssClass="inputs" name="companyName" value="%{''}"/>
                    </div>
                    <div class="col-sm-3 dispalyFieldsUPSAddrDetails" align="left">
                        <s:property value="getText('AddressLine')"/>
                    </div>
                    <div class="col-sm-3" align="left">
                        <s:textfield cssClass="inputs" name="address" value="%{''}"/>
                    </div>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-sm-12">
                    <div class="col-sm-3 dispalyFieldsUPSAddrDetails" align="left">
                        <s:property value="getText('City')"/>
                    </div>
                    <div class="col-sm-3" align="left">
                        <s:textfield cssClass="inputs" name="city" value="%{''}"/>
                    </div>
                    <div class="col-sm-3 dispalyFieldsUPSAddrDetails" align="left">
                        <s:property value="getText('State')"/>
                    </div>
                    <div class="col-sm-3" align="left">
                        <s:textfield cssClass="inputs" name="state" value="%{''}"/>
                    </div>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-sm-12">
                    <div class="col-sm-3 dispalyFieldsUPSAddrDetails" align="left">
                        <s:property value="getText('PostalCode')"/>
                    </div>
                    <div class="col-sm-3" align="left">
                        <s:textfield cssClass="inputs" name="postalCode" value="%{''}"/>
                    </div>
                    <div class="col-sm-3 dispalyFieldsUPSAddrDetails" align="left">
                        <s:property value="getText('CountryCode')"/>
                    </div>
                    <div class="col-sm-3" align="left">
                        <s:textfield  cssClass="inputs" name="countryCode" value="%{''}"/>
                    </div>
                </div>
            </div>
            <br/>
            <c:if test="${fn:toLowerCase(addressType) == fn:toLowerCase('SoldToAddr') || fn:toLowerCase(addressType) == fn:toLowerCase('FAgentInfo') || fn:toLowerCase(addressType) == fn:toLowerCase('NAFTAAddr')}" >
            <div class="row">
                <div class="col-sm-12">
                    <div class="col-sm-3 dispalyFieldsUPSAddrDetails" align="left">
                        <s:property value="getText('TaxId')"/>
                    </div>
                    <div class="col-sm-3" align="left">
                        <s:textfield name="TaxId"  cssClass="inputs" value="%{''}"/>
                    </div>
                    </c:if>
                    <c:if test="${fn:toLowerCase(addressType) == fn:toLowerCase('SoldToAddr')}" >
                    <div class="col-sm-3 dispalyFieldsUPSAddrDetails" align="left">
                        <s:property value="getText('AttentionName')"/>
                    </div>
                    <div class="col-sm-3" align="left">
                        <s:textfield name="AttentionName"  cssClass="inputs"  value="%{''}" />
                    </div>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-sm-12">
                    <div class="col-sm-3 dispalyFieldsUPSAddrDetails" align="left">
                        <s:property value="getText('PhoneNumber')"/>
                    </div>
                    <div class="col-sm-3" align="left">
                        <s:textfield name="PhoneNum"  cssClass="inputs"  value="%{''}"/>
                    </div>
                </div>
            </div>
            
            <br/>
            <div class="row">
                <div class="col-sm-12 dispalyFieldsUPSAddrDetails">
                   
                        <s:checkbox name="addOrEditSoldTo" id="addOrEditSoldTo" value="update" fieldValue="update" disabled="%{#addOrEditSoldFlag}" />
                        <s:property value="getText('Save/updatethisImporterDetail')"/>
                    
                </div>
            </div>
             </c:if>
             <br/>
            
             
             <br/>
        <center>
            <button type="button" class="btn btn-success" name="save" id="upsIntlAddrSaveButtonID"  onclick="return saveUpsIntlAddrDetails();"  align="middle"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
            <button type="button" class="btn btn-danger"  name="close" id="upsIntlAddrCloseButtonID"  onclick="window.close();"  align="middle"> Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
           <!-- <s:a href="javascript:saveUpsIntlAddrDetails()" class="btn btn-primary"> <img src="buttons/aascSave1.png" name="save" id="upsIntlAddrSaveButtonID" border="0" onclick="return saveUpsIntlAddrDetails();"  ></s:a>
            <s:a href="javascript:window.close()"><img src="buttons/aascClose1.png"  name="close" id="upsIntlAddrCloseButtonID" border="0"  ></s:a> -->
        </center> 
        <br/>
         </div>
         
        </div>
        
        
       <!-- <table width="518" height="109" border="0" align="center" cellpadding="0" cellspacing="2" >
            <tr>
                <td class="tableheading" colspan="4">
                    <center> <s:property value="%{#detailsType}"/> <s:property value="getText('Details')"/>
                </center> </td>
            </tr>
              <c:if test="${fn:toLowerCase(addressType) == fn:toLowerCase('SoldToAddr')}" >
            <tr>
                 <td class="dispalyFields"><s:property value="getText('SelectSoldTo')"/> :
                </td>
                <td class="tableDataCell">
                     <s:select list="#impNamesHashMap" name="selImporterName" id="upsIntlAddrselImporterNameID" listKey="key" listValue="value" headerKey="" headerValue="Select"
                            cssClass="inputs" onchange="getIntlImporterDetails();" />
                </td>
            </tr></c:if>
            <tr>
                <td class="dispalyFields"><s:property value="getText('CompanyName')"/>
                </td>
                
                <td class="tableDataCell">
                    <s:textfield cssClass="inputs" name="companyName" value="%{''}"/>
                </td>
                
                <td class="dispalyFields"><s:property value="getText('AddressLine')"/></td>
                <td>
                   <s:textfield cssClass="inputs" name="address" value="%{''}"/>
                </td>
            </tr>
            
            <tr>
                <td class="dispalyFields"><s:property value="getText('City')"/></td>
                <td class="tableDataCell"><s:textfield cssClass="inputs" name="city" value="%{''}"/>
                </td>
                <td class="dispalyFields"><s:property value="getText('State')"/></td>
                <td class="tableDataCell">
                    <s:textfield cssClass="inputs" name="state" value="%{''}"/>
                </td>
            </tr>
            
            <tr>
                <td class="dispalyFields"><s:property value="getText('PostalCode')"/></td>
                <td class="tableDataCell">
                     <s:textfield cssClass="inputs" name="postalCode" value="%{''}"/>
                </td>
                
                <td class="dispalyFields"><s:property value="getText('CountryCode')"/>
                </td>
                
                <td class="tableDataCell">
                     <s:textfield  cssClass="inputs" name="countryCode" value="%{''}"/>
                </td>
            </tr>
            
            <c:if test="${fn:toLowerCase(addressType) == fn:toLowerCase('SoldToAddr') || fn:toLowerCase(addressType) == fn:toLowerCase('FAgentInfo') || fn:toLowerCase(addressType) == fn:toLowerCase('NAFTAAddr')}" >
            <tr>
                <td class="dispalyFields"><s:property value="getText('TaxId')"/></td>
                <td>    <s:textfield name="TaxId"  cssClass="inputs" value="%{''}"/>
                </td>
            </c:if>
            
            <c:if test="${fn:toLowerCase(addressType) == fn:toLowerCase('SoldToAddr')}" >
                <td class="dispalyFields"><s:property value="getText('AttentionName')"/></td>
                <td>   <s:textfield name="AttentionName"  cssClass="inputs"  value="%{''}" />
                </td>
            </tr>
            
            <tr>
                <td class="dispalyFields"><s:property value="getText('PhoneNumber')"/></td>
                <td>     <s:textfield name="PhoneNum"  cssClass="inputs"  value="%{''}"/>
                </td>
           </tr>
           
           <tr>
                <td class="dispalyFields" colspan="2">
                    <s:checkbox name="addOrEditSoldTo" id="addOrEditSoldTo" value="update" fieldValue="update" disabled="%{#addOrEditSoldFlag}" />
                    <s:property value="getText('Save/updatethisImporterDetail')"/>
                </td>
           </tr>
          </c:if>
      
        </table> -->
             
    </form>
    </div>
     <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
