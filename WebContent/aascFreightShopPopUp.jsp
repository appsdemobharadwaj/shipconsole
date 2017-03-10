<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%       
/*==========================================================================+
|  DESCRIPTION                                                              |
|    JSP freightShopCarrierSelect for selecting the carriers for            | 
|    freight shop                                                           |
|    Author Suman G                                                         |
|    Version   1.1                                                          |                                                                            
|    Creation 18/02/2015                                                    |
|  @History:                                                                |
    
    21/02/2015      Suman G         Added code for taking labels from ApplicationResource.properties file instead of Hard code.
    05/03/2015      Suman G         Added code for Get Rates.
    11/03/2015      Suman G         Added code for sending multiple packages in freight shop request.    
    13/03/2015      Suman G         Added code for showing error message.    
    14/03/2015      Suman G         Added code for new UI.
    18/03/2015      Suman G         Modified code for Ajay's suggestions.
    24/03/2015      Suman G         Reverted code for Freight Shopping.
    26/03/2015      Suman G         Added code for display message in FreightShopping window.
    27/03/2015      Suman G         Added code to fix #2729 and also display message in proper colour.
    15/04/2015      Suman G         Added submitButtonId hidden filed to fix #2838
    23/04/2015      Suman G         Modified code to fix #2768
    23/04/2015      Suman G         Fixed the issue of display messages not in proper way.
    11/06/2015      Suman G         Removed fevicon to fix #2992
    24/06/2015      Rakesh          Applied the fix #3076
    26/06/2015      Rakesh          Applied the fix #3027
    21/07/2015    Suman G        Modified message id for QA
    22/07/2015    Rakesh K       Modified header
    24/07/2015    Rakesh K       Modified Code to work application in offline.
    04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
    26/08/2015  Rakesh K       Added code to solve 3496.
    05/11/2015      Suman G         Added code for DHL and Stamps in Freight Shopping.
+===========================================================================*/%>
<%@ page import="java.util.logging.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.aasc.erp.util.*"%>
<%!  private static Logger logger=AascLogger.getLogger("aascShipOrderSearch"); %>
<%@ page errorPage="aascShipError.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
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
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <link href="kendo.common-material.min.css" rel="stylesheet" />
        <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />-->
        
        
        <title><s:property value="getText('FreightShop')"/></title>
        <style type="text/css">
            html{ height:100%;  }
        </style>
        <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
        
        <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
        
        
        
        <script language="JavaScript" type="text/javascript" src="aascFreightShopPopUp_js.js">
        </script>
        <style type="text/css">
           html{height:100%} 
            /*CSS for ajax loader icon on page load */
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
    <body class="gradientbg" onload="onLoad()" style="background-color:#ADADAD;">
    


    <!-- ajax loader image  -->          
            <div id="loading" style="background-color:#888888;display:none">
            <img id="loading-image" src="images/ajax-loader1.gif" alt="Loading..." />
            </div>
     <!-- code ends here -->
     
    <div  class="container-fluid" style="background-color:#ADADAD; width:100%;">
    
        <jsp:useBean id="aascFreightShopInfo" class="com.aasc.erp.bean.AascFreightShopInfo"/>
        <jsp:useBean id="carrierServiceLevels" class="com.aasc.erp.bean.AascFSCarrierServiceLevels"/>
        <jsp:useBean id="carriersIncluded" class="com.aasc.erp.bean.AascFSCarriersIncluded"/>
        <jsp:useBean id="responseStatus" class="com.aasc.erp.bean.AascFSResponseStatus"/>
    
        <s:form name="freightShopForm" id="freightShopFormId" method="POST" action="FreightShopAction" >
        
        <s:hidden name="orderNumber" id="orderNumberId" />
        <s:hidden name="fromAdressLine1" id="fromAdressLine1Id" />
        <s:hidden name="fromAdressLine2" id="fromAdressLine2Id" />
        <s:hidden name="fromCity" id="fromCityId" />
        <s:hidden name="fromState" id="fromStateId" />
        <s:hidden name="fromZipCode" id="fromZipCodeId" />
        <s:hidden name="fromCountryCode" id="fromCountryCodeId" />
        
        <s:hidden name="toAdressLine1" id="toAdressLine1Id" />
        <s:hidden name="toAdressLine2" id="toAdressLine2Id" />
        <s:hidden name="toCity" id="toCityId" />
        <s:hidden name="toState" id="toStateId" />
        <s:hidden name="toZipCode" id="toZipCodeId" />
        <s:hidden name="toCountryCode" id="toCountryCodeId" />
        <s:hidden name="shipDate" id="shipmentDateId" />
        <c:set var="countPackages" value="${param.countPackages}" />
        <s:set name="countPackages" value="%{#attr.countPackages}" />

        <s:hidden name="countPackets" id="countPacksId" value="%{#attr.countPackages}" />
        <s:hidden name="decision" id="decisionId" />
        <s:hidden name="submitButton" id="submitButtonId" value="%{0}"/>
        
        <s:hidden name="dhlDeclaredCurrency" id="dhlDeclaredCurrencyId" />
        <s:hidden name="dhlDeclaredValue" id="dhlDeclaredValueId" />
        <s:hidden name="isDutiable" id="isDutiableId" />
        <s:set name="index" value="%{1}"/> 
        
        <table border="0" id="hiddenTable">
            <tr>
                <td>
                    <s:hidden name="dimUOM%{#index}" id="dimUOMId%{#index}" />
                    <s:hidden name="dimValueFreight%{#index}" id="dimValueId%{#index}" />
                    <s:hidden name="weightUOM%{#index}" id="weightUOMId%{#index}" />
                    <s:hidden name="weightValue%{#index}" id="weightValueId%{#index}" />
                </td>
            </tr>
        </table>    
        <%
        
            String upsCheckBox = (String)request.getAttribute("upsCheckBox");
            String fedExCheckBox = (String)request.getAttribute("fedExCheckBox");
            String dhlCheckBox = (String)request.getAttribute("dhlCheckBox");
            String stampsCheckBox = (String)request.getAttribute("stampsCheckBox");
            String sortRuleHidden = (String)request.getAttribute("sortRuleHidden");
            
            pageContext.setAttribute("upsCheckBox",upsCheckBox);
            pageContext.setAttribute("fedExCheckBox",fedExCheckBox);
            pageContext.setAttribute("dhlCheckBox",dhlCheckBox);
            pageContext.setAttribute("stampsCheckBox",stampsCheckBox);
            pageContext.setAttribute("sortRuleHidden",sortRuleHidden);
        
        %>
        
        <s:set name="upsCheckBox" value="%{#attr.upsCheckBox}" />
        <s:set name="fedExCheckBox" value="%{#attr.fedExCheckBox}" />
        <s:set name="dhlCheckBox" value="%{#attr.dhlCheckBox}" />
        <s:set name="stampsCheckBox" value="%{#attr.stampsCheckBox}" />
        
        <s:set name="sortRuleHidden" value="%{#attr.sortRuleHidden}" />
        
        

        <br><br>
          
          <div class="row">
          
           <div class="col-sm-2"></div>

            <div class="col-sm-8" style="background-color:#F0F0F0; border-width: 1px; border-radius:10px; border-color:#19b698; border-style:solid;"> <!--maindiv-->
            
        <!-- <div class="well" style="border-color:#19b698; border-radius:10px;" >
            <label align="center" style="background-color:#19b698;height:20px;color:#ffffff;padding-left:10px;font-size:16px; font-weight:bold; width:90%">Services</label>
             <br> <br>  
          -->
          <!-- Modified for fix #3076-->
             <div align="center" style="background-color:#19b698; margin-top:5px;margin-left:5px;height:30px;font-weight:bold;">
                 <label style="color:#ffffff;padding-left:10px;font-size:20px;margin-top:1px">Services</label>
             </div> 
          
           <div class="row"><br/><br/></div> 
           
            <div class="row">
            
                <div class="col-sm-3" id="labelsUPS"> 
                    <s:property value="getText(\'UPS\')"/>&nbsp;
                    <s:checkbox name="UPSCheckBox" id="UPSCheckBoxId" value="%{#upsCheckBox}" />
                </div>
               <div class="col-sm-3" id="labelsFedEx">
                    <s:property value="getText(\'FedEx\')"/>&nbsp;
                    <s:checkbox name="FedExCheckBox" id="FedExCheckBoxId" value="%{#fedExCheckBox}" />
               </div>
               <div class="col-sm-3" id="labelsDHL">
                    <s:property value="getText(\'DHL\')"/>&nbsp;
                    <s:checkbox name="DHLCheckBox" id="DHLCheckBoxId" value="%{#dhlCheckBox}" />
               </div>
               <div class="col-sm-3" id="labelsFedExStamps">
                    <s:property value="getText(\'Stamps\')"/>&nbsp;
                    <s:checkbox name="StampsCheckBox" id="StampsCheckBoxId" value="%{#stampsCheckBox}" />
               </div>
             <div class="col-sm-3">
            </div><br><br>
    
            </div>
            <br>
            <div class="row">
                <div align="center" id="labelsSortRule">
                    <s:property value="getText(\'SortRule\')" />
                    <s:select list="#{\'Rate\':\'Rate\', \'Time\': \'Time\'}" name="sortRule" id="sortRuleId" size="1" value="%{#sortRuleHidden}" />
                </div>
            </div><br><br>

            <div class="row">
                <div align="center" >
                    <a href="#" onclick="return validate()">
                         <button type="button"  id="freightShopId" class="btn btn-info">FreightShop 
                         <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                    </a>
                </div> 
                <div class="row"><br/><br/></div>
            </div>
            </div>
        <div class="col-sm-2"></div>
     </div> <!-- well div-->
    
        <s:set name="response" value="%{#attr.aascFreightShopResponse}" />
        <s:set name="carrierServiceLevel" value="%{#response.carrierServiceLevels}" />
        <s:set name="carriersIncluded" value="%{#response.carriersIncluded}" />
        <s:set name="responseStatus" value="%{#response.responseStatus}" />
        
        <s:set name="code" value="%{#responseStatus.code}" />
        <s:set name="description" value="%{#responseStatus.description}" />
        <%
                    String errorDescription = (String)request.getAttribute("errorFreightShop");
                    String errorUPS = (String)request.getAttribute("errorUPSFreightShop");
                    String errorFedEx = (String)request.getAttribute("errorFedExFreightShop");
                    String errorDHL = (String)request.getAttribute("errorDHLFreightShop");
                    String errorStamps = (String)request.getAttribute("errorStampsFreightShop");
                    logger.info("errorDescription"+errorDescription);
                    pageContext.setAttribute("errorDescription",errorDescription);
                    pageContext.setAttribute("errorUPS",errorUPS);
                    pageContext.setAttribute("errorFedEx",errorFedEx);
                    pageContext.setAttribute("errorDHL",errorDHL);
                    pageContext.setAttribute("errorStamps",errorStamps);
                    String error = (String)request.getAttribute("error");
//                    System.out.println("error:::::"+error);
                    pageContext.setAttribute("error",error);
         %>
         <s:set name="error" value="%{#attr.errorDescription}" />
        <s:set name="errorUPS" value="%{#attr.errorUPS}" />
        <s:set name="errorFedEx" value="%{#attr.errorFedEx}" />
        <s:set name="errorDHL" value="%{#attr.errorDHL}" />
        <s:set name="errorStamps" value="%{#attr.errorStamps}" />
        <div align="center" id="messageId">
        
            <s:if test="%{#description == 'success' || #description == 'Success'}">
                <div class="displayMessage1" id="displayMessage" >
                    <s:property value="getText('FreightShopSuccess')" />
                </div>
            </s:if>
            <s:else>
                <div class="displayMessage" id="displayMessage" >
                   <s:if test="%{#error == 'aasc55'}" >
                        <s:property value="getText(#error)" />
                   </s:if>
                   <s:else>
                        <s:property value="%{#attr.error}" />
                   </s:else>
                </div>
            </s:else>
            
            <s:if test="%{#errorUPS == null || #errorUPS == ''}">
            </s:if>
            <s:else>
                <div class="displayMessage" id="displayMessage" >
                    <s:if test="%{#errorUPS== 'aasc54' }">
                        UPS: <s:property value="getText(#errorUPS)" />
                    </s:if>
                    <s:elseif test="%{#errorUPS == 'success' || #errorUPS == 'Success'}">
                        
                    </s:elseif>
                    <s:else>
                        UPS: <s:property value="%{#errorUPS}" />
                    </s:else>
                    
                 </div>
            </s:else>
            
            <s:if test="%{#errorFedEx == null || #errorFedEx == ''}">
            </s:if>
            <s:else>
            
                <div class="displayMessage" id="displayMessage" >
                    <s:if test="%{#errorFedEx== 'aasc54' }">
                        FedEx: <s:property value="getText(#errorFedEx)" />
                    </s:if>
                    <s:elseif test="%{#errorFedEx == 'FDXE:Request was successfully processed.FDXG:Request was successfully processed.'}" >
                    </s:elseif>
                    <s:else>
                        FedEx: <s:property value="%{#errorFedEx}" />
                    </s:else>
                </div>
            </s:else>
            
            <s:if test="%{#errorDHL == null || #errorDHL == ''}">
            </s:if>
            <s:else>
            
                <div class="displayMessage" id="displayMessage" >
                    <s:if test="%{#errorDHL== 'aasc54' }">
                        DHL: <s:property value="getText(#errorDHL)" />
                    </s:if>
                    <s:elseif test="%{#errorDHL == 'success' || #errorDHL == 'Success'}">
                        
                    </s:elseif>
                    <s:else>
                        DHL: <s:property value="%{#errorDHL}" />
                    </s:else>
                </div>
            </s:else>   
            
            <s:if test="%{#errorStamps == null || #errorStamps == ''}">
            </s:if>
            <s:else>
            
                <div class="displayMessage" id="displayMessage" >
                    <s:if test="%{#errorStamps== 'aasc54' }">
                        Stamps: <s:property value="getText(#errorStamps)" />
                    </s:if>
                    <s:elseif test="%{#errorStamps == 'success' || #errorStamps == 'Success'}">
                        
                    </s:elseif>
                    <s:else>
                        Stamps: <s:property value="%{#errorStamps}" />
                    </s:else>
                </div>
            </s:else>
            
        </div>
        
        
         <div class="row">
         
       
     <s:if test="%{#description == 'success' || #description == 'Success'}">
     <div class="col-sm-3"></div>
    
               <div class="cols-sm-6">
               
            <fieldset class="form-scheduler-border" style="border-color:#19b698;overflow:auto;background-color:#F0F0F0;width:50%">
               <br/>
               <table class="table" style="overflow-x:auto;font-size:small;" align="center">
            <thead style="background-color:#19b698;color:#FFFFFF" >
                <tr>
                    <th>
                        <s:property value="getText('FSShipMethods')"/>
                    </th>
                    <th>
                        <s:property value="getText('FSRate')" />
                    </th>
                    <th>
                        <s:property value="getText('FSTransitTime')" />
                    </th>
                </tr>
                </thead>
                <s:set name="index" value="%{1}" />
                <s:iterator id="carrierServiceLevels" value="%{#carrierServiceLevel}">
                    <tr>
                        <td>
                            <s:set name="shipMethodMeaning" value="%{#carrierServiceLevels.carrierServiceName}" />
                            <s:hidden name="shipMethodHidden%{#index}" id="shipMethodHiddenId%{#index}" value="%{#shipMethodMeaning}" />
                            <s:radio name="selectedShipMethod%{#index}" id ="selectedShipMethodId" list="%{#carrierServiceLevels.carrierServiceName}" onclick="updateShipMethod(%{#index})" />
                            
                        </td>
                        <td>
                            <s:set name="shipmentRate" value="%{#carrierServiceLevels.shippingRate}" />
                            <s:property value="%{#shipmentRate}" />
                            <s:hidden name="shipmentRateHidden%{#index}" id="shipmentRateHiddenId%{#index}" value="%{#shipmentRate}" />
                        </td>
                        <td>
                            <s:set name="shippingTransitTime" value="%{#carrierServiceLevels.shippingTransitTime}" />
                            <s:if test="%{#shippingTransitTime=='9999.0'}" >
                                <s:property value="%{#''}" />
                            </s:if>
                            <s:else>
                                <s:property value="%{#shippingTransitTime}" />
                            </s:else>
                        </td>
                    </tr>
                    
                    <s:set name="index" value="%{#index+1}"/>
                
                </s:iterator>
            </table>
            </fieldset>
      
            </div>
            <div class="col-sm-3"></div>
        </s:if> 
         
     </div>
         
    
        </s:form>
    </div>
     <div style="position:relative;top:400px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
    
</html> 
