<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%
/*========================================================================================================+
| DESCRIPTION                                                                                             |
|   JSP aascAddressValidation for validating addres related information                                   |
|   Author Y Pradeep                                                                                      |
|   Version   1.0                                                                                         |
|   Creation 24/02/2015                                                                                   |
    
    24/02/2015      Y Pradeep       Added this file for Address Validation.
    26/02/2015      Y Pradeep       Added code to display parsed responce details accordingly.
    27/02/2015      Y Pradeep       Ran self audit and getting titles and lables from Property file.
    05/03/2015    Sanjay & Khaja Added code for new UI changes.
    10/03/2015      Y Pradeep       Added missing name for param tag.
    14/03/2015      Suman G         Added for text field.
    17/03/2015      Y Pradeep       Modified code hide tabel.
    17/04/2015      Suman G         Added session to fix #2743.
    23/04/2015      Y Pradeep       Replaced shipexec logo to apps associates logo.
    11/06/2015      Suman G         Removed fevicon to fix #2992
    24/06/2015      Rakesh K        Modified UI alignments issue #3032
    26/06/2015      Rakesh K        Modified button to fix issue #3024
    24/07/2015      Rakesh K        Modified Code to work application in offline.
    29/07/2015      Rakesh K        Modified Code to fix #3032.
    04/08/2015      Rakesh K        Modified Code bootstrap css file work across all browsers.
    26/08/2015  Rakesh K       Added code to solve 3496.
    09/12/2015      Y Pradeep       Added condition to dsiplay updated data. Bug #4048.
+=======================================================================================================*/%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%> 
<%@ page errorPage="aascShipError.jsp" %>
<%@ page import="java.util.logging.*"%>
<%@ page import="java.util.*" %>
<%@ page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@ page import="com.aasc.erp.util.*"%>
<%@ page import="oracle.jdbc.driver.*"%>
<%@ page import="com.aasc.erp.carrier.*" %>
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
       <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />-->
    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><s:property value="getText('AddressValidation')"/></title>
        <style type="text/css">
            html{ height:100%;  }
        </style>
        <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
        
        <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
      
        <script language="JavaScript" type="text/javascript" src="aasc_Address_Validation.js">
        </script>
    </head>
    <body class="gradientbg" style="height:100%" onload="displayAlert();">
    
    <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
            }
    %>
    
        <table width="100%"  align="center" >
            <tr>
                <td width="400" align="left"> </td>
                <td><font color="red" size= "2"></font></td>
                <td width="100">&nbsp;&nbsp;</td>
            </tr>
        </table>
        <jsp:useBean id="addressList" class="java.util.LinkedList"/>
        <c:catch var="exception1">
            <s:set name="addressList" value="addressList" />
        </c:catch>
        <s:if test="%{#exception1 != null}">
            <s:bean name="java.util.LinkedList" id="addressList" >
                <s:param name="addressList" value="%{''}" />
            </s:bean>
        </s:if>
        
        <jsp:useBean id="addressValidationBean" class="com.aasc.erp.bean.AascAddressValidationBean" />        
        <s:hidden name="addressClassification" id="addressClassificationId" value="%{#attr.addressClassification}" />
        <s:hidden name="addressType" id="addressTypeId" value="%{#attr.addressType}" />
        <s:set name="addressType" value="%{#attr.addressType}" />
        <s:set name="descriptionStatus" value="%{#attr.descriptionStatus}" />
        <s:hidden name="descriptionStatusHidden" id="descriptionStatusHiddenId" value="%{#attr.descriptionStatus}" />
        
        
        <div class="form-group">
        
        <fieldset class="scheduler-border" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%">
            <div style="background-color:#3D566D;margin-top:5px;margin-left:5px;height:30px" align="center">
                       <label style="color:#ffffff;font-size:20px; height:100%;padding-top:1px; " >Address Validation</label>
                    </div>
                    </br>
                        <div class="row"><br/></div>
        
        <s:form name="aascAddressValidationForm" method="POST" action="AddressValidationAction" onsubmit="">
        
            <div style="font-size:small;height:20px" align="center">
                    <s:if test='%{#addressType != null && #descriptionStatus == "SUCCESS"}'>
                       <label style="color:red;"><s:property value="getText(#addressType)"/></label>
                       </s:if>
                    </div>
                    </br>
                        <div class="row"><br/></div>
        
                    <s:if test='%{(#addressType == "AMBIGUIOUS ADDRESS" || #addressType == "MODIFIED_TO_ACHIEVE_MATCH") && #descriptionStatus == "SUCCESS"}'>
                <table class="table" style="overflow-x:auto;">
                <thead>
                <tr>
                    <th align="center" nowrap style="background-color:#19b698; color:#ffffff;font-size: 14px;font-weight: bold;width:15%;" ><s:property value="getText('SelectShipAddress')"/></th>
                    <th align="center" nowrap style="background-color:#19b698; color:#ffffff;font-size: 14px;font-weight: bold;width:10%;" ><s:property value="getText('City')"/></th>
                    <th align="center" nowrap style="background-color:#19b698; color:#ffffff;font-size: 14px;font-weight: bold;width:10%;" ><s:property value="getText('State')"/></th>
                    <th align="center" nowrap style="background-color:#19b698; color:#ffffff;font-size: 14px;font-weight: bold;width:10%;" ><s:property value="getText('PostalCode')"/></th>
                    <th align="center" nowrap style="background-color:#19b698; color:#ffffff;font-size: 14px;font-weight: bold;width:10%;" ><s:property value="getText('CountryCode')"/></th>
                </tr>
                </thead>
                    
                    <s:set name="index" value="%{1}" />
                    <s:iterator id="addressValidationBean" value="%{#addressList}">
                        <tr>
                            <td align="left" class="dispalyFieldsUPSAddrDetails">
                                <s:radio name="selectedResponseAddress" id ="selectedResponseAddressId%{#index}" list="%{#addressValidationBean.responseAddress}" onclick="getDetails();" />
                                <s:hidden name="responseAddress%{#index}" value="%{#addressValidationBean.responseAddress}" />
                            </td>
                            <td align="left" class="dispalyFieldsUPSAddrDetails">
                                <s:set name="responseCity" value="%{#addressValidationBean.responseCity}" />
                                <s:hidden name="responseCity%{#index}" value="%{#addressValidationBean.responseCity}" />
                                <s:property value="%{#responseCity}" />
                            </td>
                            <td align="left" class="dispalyFieldsUPSAddrDetails">
                                <s:set name="responseState" value="%{#addressValidationBean.responseState}" />
                                <s:hidden name="responseState%{#index}" value="%{#addressValidationBean.responseState}" />
                                <s:property value="%{#responseState}" />
                            </td>
                            <td align="left" class="dispalyFieldsUPSAddrDetails">
                                <s:set name="responsePostalCode" value="%{#addressValidationBean.responsePostalCode}" />
                                <s:hidden name="responsePostalCode%{#index}" value="%{#addressValidationBean.responsePostalCode}" />
                                <s:property value="%{#responsePostalCode}" />
                            </td>
                            <td align="left" class="dispalyFieldsUPSAddrDetails">
                                <s:set name="responseCountryCode" value="%{#addressValidationBean.responseCountryCode}" />
                                <s:hidden name="responseCountryCode%{#index}" value="%{#addressValidationBean.responseCountryCode}" />
                                <s:property value="%{#responseCountryCode}" />
                            </td>
                        </tr>
                        <s:set name="index" value="%{#index+1}" />
                    </s:iterator>
                    
                </table>
                <br/>
                <center>
                <button type="button" class="btn btn-danger" name="close" id="closeId" onclick="javascript:window.close()" value="Cancel" alt="" align="middle"> Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                   </center>
                   <div class="row"><br/></div>
            </s:if>
        </s:form>
            <s:set name="addressType" value="%{'null'}"/>
        </fieldset>
        </div>
         <div style="position:relative;top:400px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
</html>
