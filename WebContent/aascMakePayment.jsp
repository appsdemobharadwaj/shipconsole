<%@ page contentType="text/html;charset=windows-1252"%>
<%/*========================================================================+
@(#)aascMakePayment.jsp 12/02/2016
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Making Payments.
* @version 1
* @author       Suman G
* @history
*  
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
<%@ page contentType="text/html;charset=windows-1252" isELIgnored="false"%>
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
            
      <script  type="text/javascript" src="aascMakePayment_js.js"> </script>
      

    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title><s:property value="getText('PaymentDetails')"/></title>
    <style type="text/css">
      .href {color: #003399}
       html{height:100%;}
    </style>
     <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
     <link rel="stylesheet" type="text/css" href="css/style4.css" />
     
     <script  type="text/javascript"  src="aascMakePayment_js.js"></script>
     
  </head>
  
  <body class="gradientbg" onload="load();">
  <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
  <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
    <s:form  name="PaymentDetailsForm" method="POST" action="AascUpdateSubscriptionDetailsAction" margin-left="auto" margin-right="auto" border-radius="5px">
      <s:set name="clientIDInt" value="%{#session.client_id}"/>
      <s:set name="clientIDStr" value="%{#clientIDInt + ''}"/>
      <s:hidden name="clientID" value="%{#clientIDInt}"/>
      <s:hidden name="actiontype" /> 
      <s:hidden name="key" value="%{#attr.key}"/>
      
         <div class="row">
            <s:include value="aascIndexHeader.jsp" />
           <!--21/01/2015 sunanda added-->
               <c:catch var="exception1">
                 <s:set name="key" value="%{#attr.key}"/>
                 <s:if test="%{#key != null}">
            <s:if test="%{#key=='aasc453'}">
            <div class="col-sm-8 displayMessage1" id="displayMessage" align="center" >
                    <s:property value="getText(#key)"/>
                   </div>
               </s:if>
             <s:else>
             <div class="col-sm-4 displayMessage1" id="displayMessage" align="center">
                     <s:property value="getText(#key)"/> 
                   </div>
              </s:else>
                  <s:set name="key" value="%{'null'}"/>
                 </s:if>
               </c:catch>
            <s:if test="%{#exception1 != null}">
               <c:redirect url="/aascShipError.jsp">
                 <s:param name="errorObj" value="%{#exception1.message}" />
               </c:redirect>
            </s:if>
          </div>
     <s:set name="aascSubscriptionDetailsBean" value="%{#session.aascSubscriptionDetailsBean}"/>
     
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
        
    <div class="row"><br/></div>
    <div class="row"><br/></div>

        <center>
 
        <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:90%">
                  <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <label style="color:#ffffff;font-size:20px">Payment Details</label>
                    </div>  
                    
            <div class="row"><br/></div>
        <s:hidden name="subscriptionExpiryFlag" id="subscriptionExpiryFlagId" value="%{#aascSubscriptionDetailsBean.subscriptionExpiryFlag}" />
        <div class="row">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Customer Type</span></label>
                    <div class="col-sm-4">
                        <s:select list="customerTypeHashMap" listKey="value" listValue="key" name="customerType" 
                        cssStyle="height:25px" id="customerTypeId" cssClass="inputs" value="%{#aascSubscriptionDetailsBean.customerType}" onchange="getEstimatedTransactionRange();"/>
                    </div>
                    
                    <div class="col-sm-2"></div>
                    <div class="col-sm-4">
                        
                        <span class="btn btn-primary" name="details" id="detailsId" onclick="getRates();">
                             Pricing List <span class="badge" ><span class="glyphicon glyphicon-cloud"></span></span></span>
                    </div>
                  </div>
                </div>
                
                <br/>
                
                <s:hidden name="invoiceTypeHidden" id="invoiceTypeHiddenId" value="%{#aascSubscriptionDetailsBean.invoiceType}" />
                <div class="row">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Invoice Type:</span></label>
                    
                    <div class="col-sm-1">
                    <input type="radio" id="invoiceTypeDurationId" name="invoiceType" value="Duration" onclick="hideFields(this.value);"/> 
                    </div>
                    <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Duration based Price</span></label>
                    
                    
                    <div class="col-sm-2"></div>
                    <div class="col-sm-1">
                    <input type="radio" id="invoiceTypeTransactionId" name="invoiceType" value="Transaction" onclick="hideFields(this.value);" />
                    </div>
                    <label for="" class="control-label col-sm-3" style="text-align: left;" > <span class="dispalyFields">Transaction based Price</span></label>
                    
                 
                 </div>
                </div>
                
                <br/>
                
                <div class="row" id="durationBased">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Monthly Estimated Transaction Range</span></label>
                    <div class="col-sm-4">
                        <s:select list="transactionRangeHashMap" name="monthlyEstimatedTransactionRange" id="monthlyEstimatedTransactionRangeId" cssStyle="height:25px" 
                            cssClass="inputs" value="%{#aascSubscriptionDetailsBean.monthlyEstimatedTransactionRange}" onchange="getTotalPrice();"/>
                    </div>
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Pricing Duration</span></label>
                    <div class="col-sm-4">
                        <s:select list="durationTypeHashMap" listKey="value" listValue="key" name="pricingDuration" 
                        cssStyle="height:25px" id="pricingDurationId" cssClass="inputs" value="%{#aascSubscriptionDetailsBean.pricingDuration}"  onchange="getTotalPrice();"/>
                    </div>
                 </div>
                </div>
                <s:hidden name="totalFee" id="totalFeeId"  value="%{#aascSubscriptionDetailsBean.totalFee}" />
                <div class="row" id="transactionBased">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Transactions Count</span></label>
                    <div class="col-sm-4">
                        <s:textfield name="transactionCount" id="transactionCountId" cssClass="inputs" value="%{#aascSubscriptionDetailsBean.transactionCount}" size="50" maxlength="50" onchange="getTotalFeeOnTransactionCount();" />
                    </div>
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Total Fee</span></label>
                    <div class="col-sm-4">
                        <s:textfield name="totalFeeTransaction" id="totalFeeTransactionId" cssClass="inputs" size="50" maxlength="50" readonly="true"/>
                    </div>
                 </div>
                </div>
                <br/>
                
                <div class="row">		
                    <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Customer Start Date</span></label>
                        <div class="col-sm-4">
                            <s:textfield name="licenseStartDateText"  id="licenseStartDateID" placeholder="YYYY-MM-DD" cssClass="inputs" size="50" type="text" value="%{#aascSubscriptionDetailsBean.customerStartDate}" maxlength="25" readonly="true" />
                        
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Subscription End Date</span> </label>
                        <div class="col-sm-4">
                            <s:textfield name="subscriptionEndDate" type="text" id="subscriptionEndDateId" cssClass="inputs" size="16" maxlength="25" readonly="true" value="%{#aascSubscriptionDetailsBean.subscriptionEndDate}"/>
                        </div>
                    </div>
                </div> 
                
                <br/>
                      
                <div class="row" id="durationBased2">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Total Fee</span></label>
                    <div class="col-sm-4">
                        <s:textfield name="totalFeeDuration" id="totalFeeDurationId" cssClass="inputs" size="50" maxlength="50" readonly="true"/>
                    </div>
                    
                 </div>
                </div>  
                
                
                <s:hidden name="currentPackageBalance" id="currentPackageBalanceId" value="%{#aascSubscriptionDetailsBean.currentPackageBalance}" />
                <div class="row" id="transactionBased2">
                 <div class="col-sm-12">
                    
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Cumulative Package Count</span></label>
                    <div class="col-sm-4">
                        <s:textfield name="cumulativePackageCountTransaction" id="cumulativePackageCountTransactionId" cssClass="inputs" value="%{#aascSubscriptionDetailsBean.cumulativePackageCount}" size="50" maxlength="50" readonly="true" />
                    </div>
                     <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Current Package Balance</span></label>
                    <div class="col-sm-4">
                        <s:textfield name="currentPackageBalanceTransaction" id="currentPackageBalanceTransactionId" cssClass="inputs" value="%{#aascSubscriptionDetailsBean.currentPackageBalance}" size="50" maxlength="50" readonly="true"/>
                    </div>
                    
                 </div>
                </div>
                
                <br/>
                
                <div class="row" id="durationBased3">
                 <div class="col-sm-12">
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Cumulative Package Count</span></label>
                    <div class="col-sm-4">
                        <s:textfield name="cumulativePackageCountDuration" id="cumulativePackageCountDurationId" cssClass="inputs" value="%{#aascSubscriptionDetailsBean.cumulativePackageCount}" size="50" maxlength="50" readonly="true" />
                    </div>
                    <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > Current Package Balance</span></label>
                    <div class="col-sm-4">
                        <s:textfield name="currentPackageBalanceDuration" id="currentPackageBalanceDurationId" cssClass="inputs" value="%{#aascSubscriptionDetailsBean.currentPackageBalance}" size="50" maxlength="50" readonly="true"/>
                    </div>
                 </div>
                </div>
                
                <br/>         
           
                        <div class="row"><br/></div>
                      
                            <div class="row"><br/></div>
                <div class="row" id="divSub">
                <div class=" col-sm-2" >
                </div>
                <s:hidden name="SaveButtonId" id="SaveButtonId" value="%{0}"/>
                <div class=" col-sm-8" id="editButtonId">
                    
                    
                         <span class="btn btn-primary" name="clearButton" id="clearButton" > <!-- Add this field for enabling button onclick="enableFields();" -->
                             Update Pricing Details <span class="badge" ><span class="glyphicon glyphicon-cloud"></span></span></span>
                      
		</div>
                <div class=" col-sm-8" id="submitButtonsId">
                                        
                         <button class="btn btn-success" name="Save" id="SaveButton" onclick="document.PaymentDetailsForm.actiontype.value='UpdateTransaction';return save();" alt="Save" align="middle"> Make Payment <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                         
                         <span class="btn btn-warning" name="clearButton" id="clearButton" onclick="load();">
                            Cancel <span class="badge" style="background-color:marun"><span class="glyphicon glyphicon-remove"></span></span></span>
                         
                      
		</div>
                    
                
             </div>
             </br>                     
            
                   
       </div>
       
       </center>
     
     
    </s:form>
    </div>
    
                     

 <div style="position:relative;top:300px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
