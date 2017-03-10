<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ page errorPage="aascShipError.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%
/*==========================================================================+
|  DESCRIPTION                                                                   |
|    javascript file for the aascUPSDirectPayMethodDetails.jsp  validation       |
|    Author Narasimha Earla                                                      |
|    Version   1                                                                 |                                                                            
|    Creation 23-Sept-2010                                                       |
|    24-Nov-2014   Eshwari M   Added this file from cloud 1.2 version            |
|    17-Dec-2014   Eshwari M   Removed adhoc for SC Lite   
|    15/01/2015    Y Pradeep   Merged Sunanda's code for getting title name from Application.Property file.
|    15/04/2015    Suman G     Added session to fix #2743
     28/04/2015    Y pradeep   Removed length and isInteger validations to Postal Code.
     07/07/2015    Rakesh K    Removed style attribute.
     09/07/2015    Suman G     Added Padmavathi's code to remove style attribute to fix issue #3169
     22/07/2015    Rakesh K     Modified UI.
     24/07/2015    Rakesh K    Modified Code to work application in offline.
     28/07/2015    Rakesh K    Added ID for Save button #3206.
     03/08/2015    Dinakar G    Modified Code to fix Bug #3288.
     04/08/2015    Rakesh K    Modified Code bootstrap css file work across all browsers.
     26/08/2015    Rakesh K       Added code to solve 3496.
     10/11/2015    Mahesh V     Added code for FedEx Recepient development
     27/11/2015    Mahesh V     Added code for UPS Recepient and Third Party development and FedEX third party development
+================================================================================*/
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><s:property value="getText('UPSPayMethodDetails')" /></title>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css">
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    
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
    
    <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1"></meta>-->
  
    
     <script type="text/javascript">
     
function loadPayMethodDetails()
{               
      //alert("Inside loadPayMethodDetails ");
      var payMethod=window.opener.document.getElementById('carrierPayMethodId').value;
      var carrierCode = document.aascUpsPayMethodForm.carrierCode.value;
      if(window.opener.document.getElementById('acctPostalCode').value==null){
         window.opener.document.getElementById('acctPostalCode').value ="";
        }
        
            var str=window.opener.document.getElementById('customerName').options[window.opener.document.getElementById('customerName').selectedIndex].value;
            if(payMethod=="CG" || payMethod=="FC"){
                document.aascUpsPayMethodForm.acctPostalCode.value = window.opener.document.getElementById('rcAcctPostalCodeId').value;
                    if(payMethod=="CG" && carrierCode != '100'){
                    document.aascUpsPayMethodForm.rcCompanyName.value = window.opener.document.getElementById('rcCompanyNameId').value;
                    if(window.opener.document.getElementById('rcCompanyNameId').value == '' && window.opener.document.getElementById('flagShip1').value != "Y"){
                    document.aascUpsPayMethodForm.rcCompanyName.value = str;
                    }
                }
            }
         

      if(window.opener.document.getElementById('shipMethodId').disabled==true)
      {
           document.aascUpsPayMethodForm.acctPostalCode.readOnly=true; 
           if(payMethod=="TP")
            {
             document.aascUpsPayMethodForm.countryCode.readOnly=true;  
             document.aascUpsPayMethodForm.countryCode.value=window.opener.document.getElementById('tpCountrySymbolID').value;
             if(window.opener.document.getElementById('tpCountrySymbolID').value == ''){
             document.aascUpsPayMethodForm.countryCode.value=window.opener.document.getElementById('countryCodeVal').value;
            }
        }
      }
      else
      {
            document.aascUpsPayMethodForm.acctPostalCode.readOnly=false;        
            if(payMethod=="TP")
            {         
                 document.aascUpsPayMethodForm.countryCode.readOnly=false;
                 document.aascUpsPayMethodForm.countryCode.value=window.opener.document.getElementById('tpCountrySymbolID').value;
                 if(window.opener.document.getElementById('tpCountrySymbolID').value == ''){
                 document.aascUpsPayMethodForm.countryCode.value=window.opener.document.getElementById('countryCodeVal').value;
            }
                 document.aascUpsPayMethodForm.acctPostalCode.value=window.opener.document.getElementById('acctPostalCode').value; 

            }
      }
      document.aascUpsPayMethodForm.companyName.value=trim(window.opener.document.getElementById('tpCompanyNameID').value);
      document.aascUpsPayMethodForm.address.value=trim(window.opener.document.getElementById('tpAddressID').value);
      document.aascUpsPayMethodForm.city.value=trim(window.opener.document.getElementById('tpCityID').value);
      document.aascUpsPayMethodForm.state.value=trim(window.opener.document.getElementById('tpStateID').value);
      if(window.opener.document.getElementById('flagShip1').value == "Y")
      {  

         document.aascUpsPayMethodForm.companyName.readOnly = true;
         document.aascUpsPayMethodForm.address.readOnly = true;
         document.aascUpsPayMethodForm.city.readOnly = true;
         document.aascUpsPayMethodForm.state.readOnly = true;
         if(payMethod=="CG" && carrierCode != '100')
         document.aascUpsPayMethodForm.rcCompanyName.readOnly = true;
         if(payMethod=="TP"){
         document.aascUpsPayMethodForm.acctPostalCode.value=window.opener.document.getElementById('acctPostalCode').value;

         }
      } else {
      if(document.aascUpsPayMethodForm.acctPostalCode.value == ''){
         document.aascUpsPayMethodForm.acctPostalCode.value=window.opener.document.getElementById('zip').value;
         }

      }          
     
}//end of the loadPackageOptions()

function saveDetails()
{
  var payMethod=window.opener.document.getElementById('carrierPayMethodId').value;
  var acctPostalCode;
  var ctryCode;
  var companyName;
  
  var carrierCode = document.aascUpsPayMethodForm.carrierCode.value;
  
  if(window.opener.document.getElementById('shipMethodId').disabled==false)
  { 
   
      acctPostalCode=document.aascUpsPayMethodForm.acctPostalCode.value; 
      if(payMethod=="CG" || payMethod=="FC"){
          
     window.opener.document.getElementById('rcAcctPostalCodeId').value=document.aascUpsPayMethodForm.acctPostalCode.value;
     if(payMethod=="CG" && carrierCode != '100'){
      companyName=document.aascUpsPayMethodForm.rcCompanyName.value;
      window.opener.document.getElementById('rcCompanyNameId').value=document.aascUpsPayMethodForm.rcCompanyName.value;
    }
  }
//      alert("acctPostalCode in save:"+acctPostalCode);
//      alert("companyName in save:"+companyName);

   
   /*if(acctPostalCode.length!=5)
   {
        alert("Account Postal Code Should Contain 5 Digits.");
        document.aascUpsPayMethodForm.acctPostalCode.focus();
        return false;
   }
   
   if(isInteger(acctPostalCode)==false)
   {
       alert("Please Enter Only Numbers for Postal Code.");
       document.aascUpsPayMethodForm.acctPostalCode.focus();
       return false;
   }*/

    
   if(payMethod=="TP")
   {
      ctryCode=document.aascUpsPayMethodForm.countryCode.value;
      window.opener.document.getElementById('acctPostalCode').value=document.aascUpsPayMethodForm.acctPostalCode.value;
//      alert("ctryCode tp save:"+ctryCode);
     
     if(ctryCode.length!=2)
     {
        alert("Country Code Should Contain 2 Letters.");
        document.aascUpsPayMethodForm.countryCode.focus();
        return false;
     }
     window.opener.document.getElementById('tpCountrySymbolID').value=document.aascUpsPayMethodForm.countryCode.value; 
   }
  }
  //SC_7.0_LB
  if(window.opener.document.getElementById('tpCompanyNameID').value == 'undefined'){
  window.opener.document.getElementById('tpCompanyNameID').value = '';
  }
  if(window.opener.document.getElementById('tpAddressID').value == 'undefined'){
  window.opener.document.getElementById('tpAddressID').value = '';
  }
  if(window.opener.document.getElementById('tpCityID').value == 'undefined'){
  window.opener.document.getElementById('tpCityID').value = '';
  }
  if(window.opener.document.getElementById('tpStateID').value == 'undefined'){
  window.opener.document.getElementById('tpStateID').value = '';
  }
  
    if(document.aascUpsPayMethodForm.companyName.value == 'undefined'){
    document.aascUpsPayMethodForm.companyName.value = '';
    }
  
    if(document.aascUpsPayMethodForm.address.value == 'undefined'){
    document.aascUpsPayMethodForm.address.value = '';
    }
  
    if(document.aascUpsPayMethodForm.city.value == 'undefined'){
    document.aascUpsPayMethodForm.city.value = '';
    }
  
    if(document.aascUpsPayMethodForm.state.value == 'undefined'){
    document.aascUpsPayMethodForm.state.value = '';
    }
  
  window.opener.document.getElementById('tpCompanyNameID').value=document.aascUpsPayMethodForm.companyName.value;
  window.opener.document.getElementById('tpAddressID').value=document.aascUpsPayMethodForm.address.value;
  window.opener.document.getElementById('tpCityID').value=document.aascUpsPayMethodForm.city.value;
  window.opener.document.getElementById('tpStateID').value=document.aascUpsPayMethodForm.state.value;
//  window.opener.document.getElementById('tpCountrySymbolID').value=document.aascUpsPayMethodForm.countryCode.value;


  window.close(); 
}

function isInteger(s){

    var i;

    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) 
            return false;

    }
    return true;
}
    
  
function checkForChars(event)
{
   //alert("keycode:"+event.keyCode);
   
   if(navigator.appName =='Microsoft Internet Explorer') 
   {     
     if(!((event.keyCode>=65 && event.keyCode<=90)||(event.keyCode>=97 && event.keyCode<=122)))
     {
        alert('Please Enter Only Characters For Country Code.');
        //return false;
        event.keyCode=0;
     }
     else
        event.keyCode=event.keyCode;
   }
   
   else{    //alert("keycode: else :"+ event.which);
   
     if(!((event.which>=65 && event.which<=90)||(event.which>=97 && event.which<=122 )))
     {
        if(!(event.which==8 || event.which==0)){
           alert('Please Enter Only Characters For Country Code.'); 
        }   
     }
   }
}

function trim(str){
//   return str.replace(/^\s*|\s*$/g,"");
}

 </script>     
  </head> 
  
  <s:bean id="countryNameHashMap" name="java.util.HashMap"/>
    
    <c:catch var="exception1">
        <s:set name="countryNameHashMap" value="%{#session.countryNameHashMap}" />
 <!--      <s:set name="countryNameHashMap1" value="%{#attr.countryNameHashMap}" />
       aaa<s:property value="%{#countryNameHashMap}"/>bbb -->
        </c:catch>
    <s:if test="%{#exception1 != null}">
        <s:param  name="countryNameHashMap" value="%{'select'}"/>
    </s:if>
  
  <c:set var="payMethod" value="${param.payMethod}" /> 
  <s:set name="payMethod" value="%{#attr.payMethod}" />
  
  <body onload="return loadPayMethodDetails()" style="background-color:#ADADAD; width:100%;height:100%">
   <div  class="container-fluid" style="background-color:#ADADAD; width:100%;">
   <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
               

            }
                String carrierCode = request.getParameter("carrierCode");
                pageContext.setAttribute("carrierCode",carrierCode);
        %>
  <s:form name="aascUpsPayMethodForm" method="POST"> 
  <s:hidden name="payMethodHidden" value="%{#payMethod}"/>
    <s:hidden name="carrierCode" value="%{#attr.carrierCode}"/>

  
  <br> 
  <br><br>
 
 
 
  
   <div class="container">
   <div class="row" align="center">
  
  
  
  <div class="col-sm-3">
  </div>
  
  
  <div class="col-sm-6"><!--main div-->
  
    <div class="well" style="border-color:#3d566d; border-radius:10px" >
   
   <div align="center" style="font-weight:bold;color:white;background-color:#3d566d;height:30px" >
   <label style="font-size:20px"><s:property value="getText('UPSPayMethodDetails')"/></label>
  </div>
  <br/>
  <div>
          <s:hidden cssClass="inputFields" name="companyName" value="%{''}"/>
         
          <s:hidden cssClass="inputFields" name="address" value="%{''}"/>
          
         
        <s:hidden cssClass="inputFields" name="city" value="%{''}"/>
        
       
         <s:hidden cssClass="inputFields" name="state" value="%{''}"/>
       </div>   
      
      
      <div class="row" align="left"><!--mini div-->
     
     
     <div>
     <div class="col-sm-6">
        <b> <s:property value="getText('AccountPostalCode')"/></b>
      
      </div>
      
      <div class="col-sm-6">
        <s:textfield class="inputFieldspayMethod" name="acctPostalCode" id="acctPostalCode" size="6" maxlength="5" value="%{''}"/>
      </div>
      
      </div>
      
      <br><br>
    <%
//    if(payMethod.equalsIgnoreCase("TP"))  //Shiva commented #2090
//    {
    %>
    
 
    <s:if test='%{#payMethod == "TP"}'>  <!--Shiva added #2090-->
    <div class="col-sm-6">
        <b><s:property value="getText('ThirdPartyCountryCode')"/></b>
        </div>
       
      <div class="col-sm-6">
       <s:iterator value="countryNameHashMap">
                                                <s:set name="countryNameHashMapKey" value="%{#key}"/>
                                                <s:set name="countryNameHashMapValue" value="%{#value}"/>
                                    </s:iterator>
                                    <s:select list="countryNameHashMap" listKey="value" listValue="key" headerKey="Select" headerValue="Select" name="countryCode"
							 class="inputFieldspayMethod" onblur="shipFromCountryChgCase()" id="countryCode"/>
                                                         
                                                         
  <!-- <s:textfield class="inputFieldspayMethod"  name="countryCode" id="countryCode" size="3" maxlength="2" value="%{''}" /><!--Removed Style attribute to fix issue 3169-->
      </div>
    </s:if>
    
    
      <s:if test='%{#payMethod == "CG"}'> 
    <div class="col-sm-6">
        <b><s:property value="getText('CompanyName')"/></b>
        </div>
       
      <div class="col-sm-6">
        <s:textfield class="inputFieldspayMethod"  name="rcCompanyName" id="rcCompanyName" size="3" />
      </div>
      
    </s:if>
    
    </div><!--mini div-->
    
    
    <%
//    }
    %>

   
   <br> 
     
     <div>
     <div class="row">
     
     <div class="col-sm-2"> </div>
    
     <div class="col-sm-8">
    
       <button type="button" class="btn btn-success" name="save" id="upsSaveId" onclick="return saveDetails();">Save  <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>    <!--Added "return" by Shiva-->
      
      <a href="javascript:loadPayMethodDetails();">
       <button type="button" class="btn btn-warning"  id="upsCanelId" name="cancel" onclick="window.close();">Cancel <span class="badge"> <span class="glyphicon glyphicon-remove"></span></span></button>
      </a>
      
      
      </div>
      <div class="col-sm-2"> </div>
      </div>
  </div>
  
  </div><!--well div-->
   </div><!--main div-->
   
   
   
   
   
   <div class="col-sm-3">
   </div>
    
    
  
    </div>
   </div> 
    
    </s:form>
    </div>
     <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
    </html>
