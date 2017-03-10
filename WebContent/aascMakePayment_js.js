/*========================================================================+
@(#)aascMakePayment_js.js 25/02/2016
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   

* @version 1
* @author       Suman G
* @history

+===========================================================================*/
function load(){

        
    document.getElementById('editButtonId').style.display = "";
    document.getElementById('submitButtonsId').style.display = "none";
    
    
//    alert('hi');
    disableFields();

}

function hideFields(type){
    //alert(type);
    if(type == 'Duration'){
        document.getElementById('durationBased').style.display = "";
        document.getElementById('durationBased2').style.display = "";
        document.getElementById('durationBased3').style.display = "";
        document.getElementById('transactionBased').style.display = "none";
        document.getElementById('transactionBased2').style.display = "none";
        
     
        getTotalPrice();
    
//        document.getElementById('transactionBased3').style.display = "none";
    }
    else if(type == 'Transaction'){
        document.getElementById('durationBased').style.display = "none";
        document.getElementById('durationBased2').style.display = "none";
        document.getElementById('durationBased3').style.display = "none";
        document.getElementById('transactionBased').style.display = "";
        document.getElementById('transactionBased2').style.display = "";
//        document.getElementById('transactionBased3').style.display = "";
        getPriceBasedOnTransactionCount();
    }
}

function disableFields()
{
    var type = document.getElementById('invoiceTypeHiddenId').value;
//alert(type);
    document.PaymentDetailsForm.invoiceType.value = type;
    if(type == 'Duration')
        document.getElementById('invoiceTypeDurationId').checked = true;
    else 
        document.getElementById('invoiceTypeTransactionId').checked = true;
    
    
    document.getElementById('totalFeeDurationId').value = document.getElementById('totalFeeId').value;
    document.getElementById('totalFeeTransactionId').value = document.getElementById('totalFeeId').value;
    
    
    document.getElementById('customerTypeId').disabled = true;
    document.getElementById('invoiceTypeDurationId').disabled = true;
    document.getElementById('invoiceTypeTransactionId').disabled = true;
    document.getElementById('monthlyEstimatedTransactionRangeId').disabled = true;
    document.getElementById('pricingDurationId').disabled = true;
    document.getElementById('licenseStartDateID').readonly = true;
    document.getElementById('transactionCountId').disabled = true;
    document.getElementById('totalFeeTransactionId').disabled = true;
    document.getElementById('totalFeeDurationId').disabled = true;
    document.getElementById('cumulativePackageCountTransactionId').disabled = true;
    document.getElementById('currentPackageBalanceTransactionId').disabled = true;
    document.getElementById('cumulativePackageCountDurationId').disabled = true;
    document.getElementById('currentPackageBalanceDurationId').disabled = true; 
    
    hideFields(type);
}

function enableFields()
{
    document.getElementById('customerTypeId').disabled = false;
    document.getElementById('invoiceTypeDurationId').disabled = false;
    if(document.getElementById('subscriptionExpiryFlagId').value == 'N'){
        document.getElementById('invoiceTypeTransactionId').disabled = false;
    }else{
        document.getElementById('invoiceTypeDurationId').checked = true;
        hideFields('Duration');
    }
    document.getElementById('monthlyEstimatedTransactionRangeId').disabled = false;
    document.getElementById('pricingDurationId').disabled = false;
    document.getElementById('licenseStartDateID').readonly = false;
    document.getElementById('transactionCountId').disabled = false;
    document.getElementById('totalFeeTransactionId').disabled = false;
    document.getElementById('totalFeeDurationId').disabled = false;
    document.getElementById('cumulativePackageCountTransactionId').disabled = false;
    document.getElementById('currentPackageBalanceTransactionId').disabled = false;
    document.getElementById('cumulativePackageCountDurationId').disabled = false;
    document.getElementById('currentPackageBalanceDurationId').disabled = false;
    
    var type = document.getElementById('invoiceTypeHiddenId').value;
    if(type == 'Duration')
    {
        getTotalPrice();
    }
    else{
        getPriceBasedOnTransactionCount();
    }
    
    document.getElementById('editButtonId').style.display = "none";
    document.getElementById('submitButtonsId').style.display = "";
}

function getEstimatedTransactionRange()
{

    var customerType = document.getElementById('customerTypeId').value; 
    
    var xmlHttp;
      try
        {    // Firefox, Opera 8.0+, Safari    
        xmlHttp=new XMLHttpRequest();    
        }
      catch (e)
        {    // Internet Explorer    
          try
            {      
              xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
            }
          catch (e)
            {      
              try
                {        
                  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");        
                }
              catch (e)
                {        
                  alert("Your browser does not support AJAX!");        
                  return false;        
                }      
            }    
        }
        
        
        xmlHttp.onreadystatechange=function()
        {
          if(xmlHttp.readyState==4)
          {
            var responseStringDummy=trim(xmlHttp.responseText);
            var index = responseStringDummy.indexOf('@@@');
            responseStringDummy = responseStringDummy.substr(index+3);
            document.getElementById('monthlyEstimatedTransactionRangeId').options.length = 0;
            
            for(i=0;responseStringDummy.length > 0; i++){
                index = responseStringDummy.indexOf('***');
                var opt = responseStringDummy.substr(0,index);
                document.getElementById('monthlyEstimatedTransactionRangeId').options[i] =  new Option(opt,opt,true,true);
                responseStringDummy = responseStringDummy.substr(index+3,responseStringDummy.length);
            }
            document.getElementById('monthlyEstimatedTransactionRangeId').options[0].selected = true;
            getTotalPrice();
          }
        }
        var url="aascAjaxEstimatedTransactionRange.jsp?customerType="+customerType;
        xmlHttp.open("POST",url,false);
        xmlHttp.send(null); 
    
}

function getTotalPrice()
{

    var customerType = document.getElementById('customerTypeId').value; 
    var transactionRange = document.getElementById('monthlyEstimatedTransactionRangeId').value; 
    var durationType = document.getElementById('pricingDurationId').value; 
    
    var xmlHttp;
      try
        {    // Firefox, Opera 8.0+, Safari    
        xmlHttp=new XMLHttpRequest();    
        }
      catch (e)
        {    // Internet Explorer    
          try
            {      
              xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
            }
          catch (e)
            {      
              try
                {        
                  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");        
                }
              catch (e)
                {        
                  alert("Your browser does not support AJAX!");        
                  return false;        
                }      
            }    
        }
        
        
        xmlHttp.onreadystatechange=function()
        {
          if(xmlHttp.readyState==4)
          {
            var responseStringDummy=trim(xmlHttp.responseText);
            var index = responseStringDummy.indexOf('@@@');
            responseStringDummy = responseStringDummy.substr(index+3);
            document.getElementById('totalFeeId').value = responseStringDummy;
            document.getElementById('totalFeeDurationId').value = responseStringDummy;
//            alert(document.CreateCustomerForm.totalFee.value);
//            document.getElementById('estimatedTransactionRangeId').options.length = 0;
            
//            for(i=0;responseStringDummy.length > 0; i++){
//                index = responseStringDummy.indexOf('***');
//                var opt = responseStringDummy.substr(0,index);
//                document.getElementById('estimatedTransactionRangeId').options[i] =  new Option(opt,opt,true,true);
//                responseStringDummy = responseStringDummy.substr(index+3,responseStringDummy.length);
//            }
//            document.getElementById('estimatedTransactionRangeId').options[i].selected = true;
          }
        }
        var url="aascAjaxTotalPrice.jsp?customerType="+customerType+"&transactionRange="+transactionRange+"&durationType="+durationType;
        xmlHttp.open("POST",url,false);
        xmlHttp.send(null); 
    
}

function getTotalFeeOnTransactionCount(){

    var transactionCount = document.getElementById('transactionCountId').value;
//    alert('transactionCount:::'+transactionCount);
    var valid=/^[0-9 ]$/;
    if(transactionCount.length == 0 || transactionCount < 0 || isNaN(transactionCount)){
        document.getElementById('transactionCountId').value = 0;
        document.getElementById('totalFeeTransactionId').value = 0;
    }else{
        if(isFloat(transactionCount)){
            alert("Transaction Count should be a positive number, not a decimal");   
            document.UpdateCustomerForm.transactionCount.focus();
            return false;
        }
        else{
            getPriceBasedOnTransactionCount();
        }
    }
}

function getPriceBasedOnTransactionCount()
{
    var customerType = document.getElementById('customerTypeId').value; 
    var transactionCount = document.getElementById('transactionCountId').value;
    
    var xmlHttp;
      try
        {    // Firefox, Opera 8.0+, Safari    
        xmlHttp=new XMLHttpRequest();    
        }
      catch (e)
        {    // Internet Explorer    
          try
            {      
              xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
            }
          catch (e)
            {      
              try
                {        
                  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");        
                }
              catch (e)
                {        
                  alert("Your browser does not support AJAX!");        
                  return false;        
                }      
            }    
        }
        
        
        xmlHttp.onreadystatechange=function()
        {
          if(xmlHttp.readyState==4)
          {
            var responseStringDummy=trim(xmlHttp.responseText);
            var index = responseStringDummy.indexOf('@@@');
            responseStringDummy = responseStringDummy.substr(index+3);
            document.getElementById('totalFeeId').value = responseStringDummy;
            document.getElementById('totalFeeTransactionId').value = responseStringDummy;
//            alert(document.CreateCustomerForm.totalFee.value);
//            document.getElementById('estimatedTransactionRangeId').options.length = 0;
            
//            for(i=0;responseStringDummy.length > 0; i++){
//                index = responseStringDummy.indexOf('***');
//                var opt = responseStringDummy.substr(0,index);
//                document.getElementById('estimatedTransactionRangeId').options[i] =  new Option(opt,opt,true,true);
//                responseStringDummy = responseStringDummy.substr(index+3,responseStringDummy.length);
//            }
//            document.getElementById('estimatedTransactionRangeId').options[i].selected = true;
          }
        }
        
        var url="aascAjaxTotalPrice.jsp?transactionCount="+transactionCount+"&customerType="+customerType;
        xmlHttp.open("POST",url,false);
        xmlHttp.send(null); 
    
}

function trim(str)
{
  return str.replace(/^\s*|\s*$/g,"");
}

function getRates()
{
    aascSubscriptionDetails =  window.open("aascSubscriptionDetails.jsp","Post",'width=770,height=500,top=100,left=100,scrollbars=yes, resizable=yes');
    aascSubscriptionDetails.focus();
}

function save()
{
    var tranCount = document.getElementById('transactionCountId').value;
    
    if(document.getElementById('invoiceTypeTransactionId').checked == true){
    
        if(tranCount.length == 0 ){
            alert("Transaction Count cannot be empty");   
            document.PaymentDetailsForm.transactionCount.focus();
            return false;
        }
        else if(isNaN(tranCount)){
            alert("Transaction Count can only be numbers");   
            document.PaymentDetailsForm.transactionCount.focus();
            return false;
        }
        else if(tranCount <= 0){
            alert("Transaction Count should be greater than 0");   
            document.PaymentDetailsForm.transactionCount.focus();
            return false;
        }
        else if(isFloat(tranCount)){
            alert("Transaction Count should be a positive number, not a decimal");   
            document.PaymentDetailsForm.transactionCount.focus();
            return false;
        }
    }
    
}

function isFloat(n){
    return n % 1 !== 0;
}
