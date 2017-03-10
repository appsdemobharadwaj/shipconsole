
<%@ page contentType="text/html;charset=UTF-8"%>

<!--/*========================================================================+
@(#)aascCreateCustomer.jsp 24/07/2014
* Copyright (c) 2014-2015 Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Creating Customers
* @version 1.0
* @author Suman Gunda
* @history
*
* 07/01/2015    Y Pradeep   Corrected footer name.
* 15/01/2015    Y Pradeep   Merged Sunanda's code : getting title name from Application.Property file.
* 21/01/2015    K Sunanda   Added code to display success messages in green and errored in red for bug #2506 
* 04/02/2015    K Sunanda   Added Id for display messages
* 17/02/2015    Suman G     Modified code to fix #2506.
* 05/03/2015    Sanjay & Khaja Added code for new UI changes.
* 10/03/2015    Y Pradeep   Added missing name for param tag.
* 10/03/2015    K Sunanda   Removed Profile Options related code.
* 23/04/2015    Y Pradeep   Removed footer.
* 26/05/2015    Suman G     Added Vikas code for new UI.
* 11/06/2015    Suman G     Removed fevicon to fix #2992
* 16/07/2015    Dinakar G    Modified alignment in screen for tablet
* 16/07/2015  Rakesh K    Modified alignment.
* 17/07/2015  Dinakar G    Modified alignment.
* 21/07/2015    Suman G        Modified message id for QA
* 22/07/2015    Y Pradeep   Removed maxlength attribute for Cloud Label Path textfield. Bug #3200.
* 24/07/2015   Rakesh K    Modified Code to work application in offline.
* 28/07/2015  Dinakar G        Modified code to fix #3278
* 30/07/2015  Y Pradeep     Modified textfiled name from ContactName to contactName to do proper validations and avoid script errors. Bug #3277.
* 04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
* 07/08/2015  Dinakar G        Modified code to fix #3340
* 07/08/2015  Dinakar G        Modified code to fix #3355
* 24/08/2015  Jagadish Jain    Added code for pricing details.
26/08/2015  Rakesh K       Added code to solve 3496.
* 28/10/2015    Suman G     Added code to calculate on change of original cost.
24/02/2016     Suman G      Changed Transaction Management design.
/
-->

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
  
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
      <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
     <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
      <link href="kendo.common-material.min.css" rel="stylesheet" />-->
     
        <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
        <link type="text/css" rel="stylesheet" href="aasc_ss.css">
        <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
        <link rel="stylesheet" type="text/css" href="css/style4.css" />
        <!--<link rel="stylesheet" href="demo.css">
<link type="text/css" rel="stylesheet" href="login.css"/>-->
   <!--<link type="text/css" rel="stylesheet" href="menu_header.css"/>

   <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>
  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>-->
   
    <script language="javascript" type="text/javascript">
        var button = "N";
        function disableSubmit()
        {
            if(button=="Y")
            {
                alert("Request already submitted. Please Wait.");
                return false;
            }
        }
        function changePassword(){
            tpwindow =  window.open("aascChangePassword.jsp","Post",'width=500,height=350,top=100,left=100,scrollbars=yes, resizable=yes');
            tpwindow.focus();
        }
        function loader()
        {
            var pb;
            pb = document.getElementById("indexLoad");
            pb.innerHTML = '<img src="images/ajax-loader.gif" width="80" height ="80"/>';
            pb.style.display = '';
        }
         var j = jQuery.noConflict();
        j(document).ready(function() {
            // create DatePicker from input HTML element
//                $("#shipmentDate").kendoDatePicker();
            j("#licenseStartDateID").kendoDatePicker({
                parseFormats:["yyyy-MM-dd"],
                format: "yyyy-MM-dd"
            }).data("kendoDatePicker");
            j("#licenseEndDateID").kendoDatePicker({
                parseFormats:["yyyy-MM-dd"],
                format: "yyyy-MM-dd"
            }).data("kendoDatePicker");

        });
    </script>
  
  <style type="text/css">
body,td,th {
	font-family: Tahoma;
	font-size: 14px;
	font-weight: bold;
}

    html{height:100%}
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  
     
    
     
  
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
  
    <script language="JavaScript" src="aasc_Index_Header_js.js" type="text/javascript"></script>
    <title><s:property value="getText('SCCreateCustomer')" /></title>
    <s:bean id="countryNameHashMap" name="java.util.HashMap"/>
    
    <c:catch var="exception1">
        <s:set name="countryNameHashMap" value="%{#session.countryNameHashMap}" />
    </c:catch>
    <s:if test="%{#exception1 != null}">
        <s:param  name="countryNameHashMap" value="%{'select'}"/>
    </s:if>
    
    <s:bean id="customerTypeHashMap" name="java.util.HashMap" />
    
    <c:catch var="exception1">
        <s:set name="customerTypeHashMap" value="%{#session.customerType}" />
    </c:catch>
    <s:if test="%{#exception1 != null}">
        <s:param  name="customerTypeHashMap" value="%{'select'}"/>
    </s:if>
    
    <s:bean id="transactionRangeHashMap" name="java.util.HashMap" />
    
    <c:catch var="exception1">
        <s:set name="transactionRangeHashMap" value="%{#session.transactionRange}" />
    </c:catch>
    <s:if test="%{#exception1 != null}">
        <s:param  name="transactionRangeHashMap" value="%{'select'}"/>
    </s:if>
    
    <s:bean id="durationTypeHashMap" name="java.util.HashMap" />
    
    <c:catch var="exception1">
        <s:set name="durationTypeHashMap" value="%{#session.durationType}" />
    </c:catch>
    <s:if test="%{#exception1 != null}">
        <s:param  name="durationTypeHashMap" value="%{'select'}"/>
    </s:if>
    
     <link rel="stylesheet" href="demo.css">
	<link type="text/css" rel="stylesheet" href="login.css"/>
    <link type="text/css" rel="stylesheet" href="menu_header.css"/>
    <script  type="text/javascript"  src="aascCreateCustomer_js.js"> </script>
    
  </head>
  
  <body class="gradientbg" style="height:100%" onload="load();">
  <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
        <s:form  name="CreateCustomerForm" method="POST" action="AascCreateCustomerAction" >
 <%@ include file="aascIndexHeader.jsp"%>
    
  <s:set name="actionType" value="%{#attr.actionType}" scope="page"/>
    
  <s:set name="currentRow" value="%{#attr.currentRow}" scope="page" />
 
    <s:hidden name="actiontype" /> 
      <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" >
        <tr>
        <!--21/01/2015 sunanda added-->
         <c:catch var="exception1">
          <s:set name="key" value="%{#attr.key}"/>
           <s:if test="%{#key != null}">
            <s:if test="%{#key=='aasc406'||#key=='aasc408'}">
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
	
     <div class="row"><br/></div>
     <div class="row">
       <div class="col-sm-12" align="center">
             
                <h5 style="color:white;font-size:20px;font-weight:bold;"><s:property value="getText('CreateCustomer')"/></h5>
          
            </div>
       </div> 
     <div class="row"><br/></div>
     <s:set name="aascCustomerInfo" value="%{#session.aascCustomerInfo}"/>
     <s:set name="aascCustomerInfo" value="%{''}" scope="session"/>
     <center>
	<div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:90%">
                  
      <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;" align="left">
                       <label style="color:#ffffff;font-size:15px;margin-left: 5px;margin-top: 4px;"><s:property value="getText('CompanyDetails')"/></label>
                    </div>  
      
      
                        <div class="row"><br/></div>
      
         <s:set name="countryCodelist" value="%{#session.countryCodelist}" />
         <s:set name="originalCost" value="%{#session.originalCost}" />
         <s:set name="licenseStartDateStr" value="%{#attr.licenseStartDateStr}"/>
         <s:set name="licenseEndDateStr" value="%{#attr.licenseEndDateStr}"/>
		 
		 <div class="row" id="divSub" >
                      <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('CompanyName')"/> <font color="red">* </font></span></label>
                        <div class="col-sm-4">
                            <s:textfield name="companyName" id="companyNameID" cssClass="inputs" value="%{#aascCustomerInfo.companyName}" size="50" maxlength="50"/>
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('ContactName')"/><font color="red">* </font> </span> </label>
                        <div class="col-sm-4">
                            <s:textfield name="contactName" id="contactNameID" cssClass="inputs" size="50" value="%{#aascCustomerInfo.contactName}" maxlength="50" />
                        </div>
                      </div>  
                    </div>
					<div class="row"><br/></div>
		<div class="row" id="divSub" >
                      <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('AddressLine1')"/> <font color="red">* </font></span></label>
                        <div class="col-sm-4">
                            <s:textfield name="addressLine1" id="addressLine1ID" cssClass="inputs" value="%{#aascCustomerInfo.addressLine1}" size="50" maxlength="50"/>
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('AddressLine2')"/> </span> </label>
                        <div class="col-sm-4">
                            <s:textfield name="addressLine2" id="addressLine2" cssClass="inputs" size="50" value="%{#aascCustomerInfo.addressLine2}" maxlength="50" />
                        </div>
                      </div>  
                    </div>
					<div class="row"><br/></div>
		<div class="row" id="divSub" >
                      <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('City')"/> <font color="red">* </font></span></label>
                        <div class="col-sm-4">
                            <s:textfield name="city" id="cityID" cssClass="inputs" value="%{#aascCustomerInfo.city}" size="50" maxlength="50"/>
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('State')"/> <font color="red">* </font></span> </label>
                        <div class="col-sm-4">
                            <s:textfield name="state" id="stateID" cssClass="inputs" size="50" value="%{#aascCustomerInfo.state}" maxlength="50" />
                        </div>
                      </div>  
                    </div>
					<div class="row"><br/></div>
		<div class="row" id="divSub" >
                      <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('PostalCode')"/> <font color="red">* </font></span></label>
                        <div class="col-sm-4">
                            <s:textfield name="postalCode" id="postalCodeID" cssClass="inputs" value="%{#aascCustomerInfo.postalCode}" size="50" maxlength="50"/>
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('Country')"/> </span> </label>
                        <div class="col-sm-4">
                            <s:iterator value="countryNameHashMap">
								<s:set name="countryNameHashMapKey" value="%{#key}"/>
								<s:set name="countryNameHashMapValue" value="%{#value}"/>
							</s:iterator>
							<s:select list="countryNameHashMap" listKey="value" listValue="key" headerKey="Select" headerValue="Select" name="countryCode"
							 cssClass="inputs" cssStyle="height:25px" id="countryCodeID" value="%{#aascCustomerInfo.countryCode}"/>
                        </div>
                      </div>  
                    </div>
					<div class="row"><br/></div>
		<div class="row" id="divSub" >
                      <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('PhoneNumber')"/> <font color="red">* </font></span></label>
                        <div class="col-sm-4">
                            <s:textfield name="phoneNumber" id="phoneNumberID" cssClass="inputs" value="%{#aascCustomerInfo.phoneNumber}" size="50" maxlength="50"/>
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('userEmail')"/> <font color="red">* </font></span> </label>
                        <div class="col-sm-4">
                            <s:textfield name="emailAddress" id="emailAddressID" cssClass="inputs" size="50" value="%{#aascCustomerInfo.emailAddress}" maxlength="50" />
                        </div>
                      </div>  
                    </div>
					<div class="row"><br/></div>
					
					
                     <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;" align="left">
                       <label style="color:#ffffff;font-size:15px;margin-left: 5px;margin-top: 4px;"><s:property value="getText('PricingDetails')"/></label>
                    </div>  
                    
					<div class="row"><br/></div>

                <div class="row">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" ><s:property value="getText('CustomerType')"/></span></label>
                    <div class="col-sm-4">
                        <s:select list="customerTypeHashMap" listKey="value" listValue="key" name="customerType" 
                        cssStyle="height:25px" id="customerTypeId" cssClass="inputs" onchange="getEstimatedTransactionRange();"/>
                    </div>
                  </div>
                </div>
                
                <br/>
                
                <div class="row">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" ><s:property value="getText('InvoiceType')"/>:</span></label>
                    
                    <div class="col-sm-1">
                    <input type="radio" id="invoiceTypeId" name="invoiceType" value="Duration" onclick="hideFields(this.value);"/> 
                    </div>
                    <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="getText('DurationBasedPrice')"/></span></label>
                    
                    
                    <div class="col-sm-2"></div>
                    <div class="col-sm-1">
                    <input type="radio" id="invoiceTypeId" name="invoiceType" value="Transaction" onclick="hideFields(this.value);" disabled="true"/>
                    </div>
                    <label for="" class="control-label col-sm-3" style="text-align: left;" > <span class="dispalyFields"><s:property value="getText('TransactionBasedPrice')"/></span></label>
                    
                 
                 </div>
                </div>
                
                <br/>
                
                <div class="row" id="durationBased">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" ><s:property value="getText('MonthlyEstimatedTransactionRange')"/></span></label>
                    <div class="col-sm-4">
                        <s:select list="transactionRangeHashMap" name="monthlyEstimatedTransactionRange" id="monthlyEstimatedTransactionRangeId" cssStyle="height:25px" 
                            cssClass="inputs" value="%{#aascCustomerInfo.monthlyEstimatedTransactionRange}" onchange="getTotalPrice();"/>
                    </div>
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" ><s:property value="getText('PricingDuration')"/></span></label>
                    <div class="col-sm-4">
                        <s:select list="durationTypeHashMap" listKey="value" listValue="key" name="pricingDuration" 
                        cssStyle="height:25px" id="pricingDurationId" cssClass="inputs" onchange="getTotalPrice();"/>
                    </div>
                 </div>
                </div>
                
               <br/>
                
                <div class="row">		
                    <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" ><s:property value="getText('CustomerStartDate')"/></span></label>
                        <div class="col-sm-4">
                            <s:textfield name="licenseStartDateText"  id="licenseStartDateID" placeholder="YYYY-MM-DD" cssClass="inputs" size="50" type="text" value="%{#aascCustomerInfo.customerStartDate}" maxlength="25" />
                        
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" ><s:property value="getText('SubscriptionEndDate')"/></span> </label>
                        <div class="col-sm-4">
                            <s:textfield name="subscriptionEndDate" type="text" id="subscriptionEndDateId" cssClass="inputs" size="16" maxlength="25" readonly="true"/>
                        </div>
                    </div>
                </div> 
                
                <br/>
                      
                <div class="row" id="durationBased2">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" ><s:property value="getText('TotalFee')"/></span></label>
                    <div class="col-sm-4">
                        <s:textfield name="totalFee" id="totalFeeId" cssClass="inputs" size="50" maxlength="50" />
                    </div>
                    
                 </div>
                </div>  
                
                
                
               
                
                <br/>
                
                <div class="row" id="durationBased3">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" ><s:property value="getText('CumulativePackageCount')"/></span></label>
                    <div class="col-sm-4">
                        <s:textfield name="cumulativePackageCount" id="cumulativePackageCountId" cssClass="inputs" value="%{#aascCustomerInfo.cumulativePackageCount}" size="50" maxlength="50" />
                    </div>
                     <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" ><s:property value="getText('CurrentPkgBalance')"/></span></label>
                    <div class="col-sm-4">
                        <s:textfield name="currentPackageBalance" id="currentPackageBalanceId" cssClass="inputs" value="%{#aascCustomerInfo.currentPackageBalance}" size="50" maxlength="50"/>
                    </div>
                 </div>
                </div>
                
                
                
                <br/>
         <!--       
                <div class="row">
                 <div class="col-sm-12">
                    <div id="type1">
                        </br>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" >Per Package List Rate ($)</span></label>
                        
                        <div class="col-sm-4">
                        <s:textfield name="discount" id="discountID" cssClass="inputs" value="%{#aascCustomerInfo.discount}" size="50" maxlength="50" onchange="calcDiscountCost();"/>
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" >Total Cost</span></label>
                    
                    <div class="col-sm-4">
                    <s:textfield name="originalTotal" id="originalTotalID" cssClass="inputs" value="%{#aascCustomerInfo.originalTotal}" size="50" maxlength="50"/>
                    </div>
                    </div>
                    <div id="type2">
                        </br>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" >Flat Rate</span></label>
                        
                        <div class="col-sm-4">
                        <s:textfield name="discountTotal" id="discountTotalID" cssClass="inputs" value="%{#aascCustomerInfo.discountTotal}" size="50" maxlength="50"/>
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" >Total Cost</span></label>
                    
                    <div class="col-sm-4">
                    <s:textfield name="originalTotal" id="originalTotalID" cssClass="inputs" value="%{#aascCustomerInfo.originalTotal}" size="50" maxlength="50"/>
                    </div>
                   </div>
                   
                 </div>
                </div>
                
                <div class="row"><br/></div>
		
                
                <div  id="type3">
                <br/>
                
                <div class="row">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" >Per package discount(%)</span></label>
                    
                    <div class="col-sm-4">
                    <s:textfield name="discount" id="discountID" cssClass="inputs" value="%{#aascCustomerInfo.discount}" size="50" maxlength="50" onchange="calcDiscountCost();"/>
                    </div>
                    
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" >Discount total</span></label>
                    
                    <div class="col-sm-4">
                    <s:textfield name="discountTotal" id="discountTotalID" cssClass="inputs" value="%{#aascCustomerInfo.discountTotal}" size="50" maxlength="50"/>
                    </div>
                 
                 </div>
                </div>
                 </div>  
                   <br/>
                   
                <div class="row">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" >Monthly package count</span></label>
                    
                    <div class="col-sm-4">
                    <s:textfield name="discount" id="discountID" cssClass="inputs" value="%{#aascCustomerInfo.discount}" size="50" maxlength="50" onchange="calcDiscountCost();"/>
                    </div>
                    
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" >Cumulative Package</span></label>
                    
                    <div class="col-sm-4">
                    <s:textfield name="discountTotal" id="discountTotalID" cssClass="inputs" value="%{#aascCustomerInfo.discountTotal}" size="50" maxlength="50"/>
                    </div>
                 
                 </div>
                </div>
                
                <br/>
                
                <div class="row">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" >Current Balance</span></label>
                    
                    <div class="col-sm-4">
                    <s:textfield name="originalCost" id="originalCostID" cssClass="inputs" value="%{#session.originalCost}" size="50" maxlength="50"  onblur="calcOriginalCost();"/>
                    </div>
                    
                    
                 
                 </div>
                </div>
                   
                   -->
					<div class="row"><br/></div>
					
				
                     <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;" align="left">
                       <label style="color:#ffffff;font-size:15px;margin-left: 5px;margin-top: 4px;"><s:property value="getText('UserDetails')"/></label>
                    </div>  
                    
					<div class="row"><br/></div>
				<div class="row">	
					<div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('FirstName')"/><font color="red">* </font></span></label>
                        <div class="col-sm-4">
                            <s:textfield name="firstName" id="firstNameID" cssClass="inputs" value="%{#aascCustomerInfo.firstName}" size="50" maxlength="50"/>
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('LastName')"/> </span> </label>
                        <div class="col-sm-4">
                            <s:textfield name="lastName" id="lastNameID" cssClass="inputs" value="%{#aascCustomerInfo.LastName}" size="50" maxlength="50"/>
                        </div>
                      </div>  
                    </div>
					<div class="row"><br/></div>
                         <div class="row">               
			<div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('Status')"/></span></label>
                        <div class="col-sm-4">
                            <s:select list="#{\'Y\':\'ACTIVE\', \'N\': \'INACTIVE\'}" name="status" cssStyle="height:25px" id="statusID" cssClass="inputs" value="#status"/>
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('CloudLabelPath1')"/> <font color="red">* </font></span> </label>
                        <div class="col-sm-4">
                            <s:textfield name="CloudLabelPath" id="CloudLabelPathID" cssClass="inputs" value="%{#aascCustomerInfo.cloudLabelPath}" size="50"/>
                        </div>
                      </div>
                      </div>
                    
		<div class="row"><br/></div>
			  <div class="row" id="divSub">
                <div class=" col-sm-2" >
                </div>
                <div class=" col-sm-8" >
                    <s:hidden name="SaveButtonId" id="SaveButtonId" value="%{0}"/>
                          <button class="btn btn-success" name="Save" id="SaveButton" onclick="document.CreateCustomerForm.actiontype.value='AddNewCustomer';return save();" align="middle"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                          <button class="btn btn-warning" type="button" name="clearButton"  id="clearButton" onclick="clearFields();"  > Clear <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                          <button class="btn btn-danger" name="closeButton" id="closeButton" onclick="document.CreateCustomerForm.actiontype.value='Cancel';" value="Cancel" alt="Close" align="middle"> Close <span class="badge"><span class="glyphicon glyphicon-off"></span></span></button>  
                    
		</div>
                    <div class=" col-sm-2" >
                </div>
                
                
             </div>
             <div class="row"><br/></div>
					
        </div>
        </center>
    
         <br/><br/><br/>
   
    
    
    </s:form>
    </div>
     <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
	</body>
</html>
