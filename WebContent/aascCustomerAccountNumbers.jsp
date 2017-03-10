<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%
/*========================================================================================================+
    15/01/2015  Y Pradeep   Merged Sunanda's code : getting title name from Application.Property file.
    20/01/2015  Sunanda K   Added code fo store the total index to get this value in js for validation fro bug fix #2519.
    21/01/2015  K Sunanda   Added code to display success messages in green and errored in red for bug #2506 
    04/02/2015  K Sunanda   Added Id for display messages
    20/02/2015  Eshwari M   Modified code to fix bug # 2622 by adding the missed Ids to the recepient and thirdparth fields
    05/03/2015  Sanjay & Khaja Added code for new UI changes.
    10/03/2015  K Sunanda   Renamed the javascript name to make it meaningful and changed this js filename in the jsp
    15/04/2015  Suman G     Added session to fix #2743
    23/04/2015  Y Pradeep   Removed footer and replaced shipexec logo to apps associates logo.
    11/06/2015  Suman G     Removed fevicon to fix #2992
    21/07/2015  Suman G     Modified message id for QA
    24/07/2015  Rakesh K    Modified Code to work application in offline.
    30/07/2015  Rakesh K    Modified Code to align buttons properly #3299.
    04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
    26/08/2015  N Srisha    Added Id's for Automation testing
    26/08/2015  Rakesh K       Added code to solve 3496.
    26/08/2015  N Srisha    Added Id's for Automation testing
+=======================================================================================================*/%>

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
  
  <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
     <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
       <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
       <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />-->
  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:property value="getText('SCCustomerAccountNumbers')" /></title>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css">
   <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
   
     <script language="JavaScript" SRC="aasc_Ship_To_Location.js" type="text/javascript">
   </script>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    
    <style type="text/css">
      html {
        height: 100%;
            }
    </style>
  </head>
  <body class="gradientbg">
  <div class="container-fluid" style="background-color:#ADADAD;">
  
  <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
            }
    %>
  
  <s:set name="key" value="%{#attr.key}"/>
  <jsp:useBean id="aascCustomerAccountNumbers" class="com.aasc.erp.bean.AascCustomerAccountNumbers"/>

     <s:form name="CustAccountNumberDetailsForm" method="POST" action="AascCustAccontNums" >

  <s:set name="locid" value="%{#attr.locationId}" />
    <s:set name="custAccNumList1" value="%{#session.custAccNumList}" />
  
       <table width="80%" border="0" cellpadding="0" cellspacing="0" align="center">
         <tr>
         <td>  
         </td>
         <!--21/01/2015 sunanda added-->
         <c:catch var="exception1">
         <s:set name="key" value="%{#attr.key}"/>
         <s:if test="%{#key != null}">
            <s:if test="%{#key==aasc700}">
             <td width="90%" class="displayMessage" id="displayMessage" align="center" valign="bottom">
              <s:property value="getText(#key)"/> 
             </td>
            </s:if>
            <s:else>
             <td width="90%" class="displayMessage1" id="displayMessage" align="center" valign="bottom">
              <s:property value="getText(#key)"/> 
             </td>
            </s:else>
              <s:set name="key" value="%{'null'}"/>
           </s:if>
         </c:catch>
           </tr>
     </table>
     
    <table width="80%" border="0" align="center"  class="tableDataCell">
        <tr align="center">
         <td  width="30%" colspan="23"  >
           <c:set var="locationId1" value="${param.locationId}" scope="page"/> 
           <s:set name="loc" value="%{#attr.locationId1}" />
           
         <s:hidden name="locationId" id="locationID" value="%{#loc}" />

           <!--style type="text/css"-->
         <!--  <s:if test='%{#actionType == "Create" || #actionType == "AddNewAccountNumber" || #actionType == "AllowAddNewAccountNumber"}'>
             
        
               <s:set name="negotiatedRatesStatus" value="%{'false'}"/>
               <s:set name="negotiatedRates" value="%{'false'}"/>
               <s:set name="negotiatedFlagStatus" value="%{'unchecked'}"/>
               <s:set name="accountNumberNegotiatedFlag" value="%{'false'}"/>
                <s:property value="getText('AccountNumberCreation')"/>
             </s:if>
             <s:else>
               <s:property value="getText('AccountNumberUpdation')"/>
             </s:else> -->
             
          <!--     <s:property value="getText('CustomerAccountNumbers')"/> -->
           </td>
       </tr>
     </table>
  
  <br/><br/><br/><br/>
  
  <div class="row" align="middle">
          <div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%">
                <div style="background-color:#7761a7;margin-top:5px;">
                    <label style="color:#ffffff;padding-top:5px;font-size: 20px;">Customer Account Numbers</label>
                </div>
                <br/>
                <table width="100%" align="center" border="0" class="table">
                  <tr>
                  <th style="background-color:#7761a7;color:white;font-weight:bold;font-size: 12px;"> 
                   <s:property value="getText('CarrierName')"/>
                  </th>
                  <th style="background-color:#7761a7;color:white;font-weight:bold;font-size: 12px;">
                   <s:property value="getText('Recipient')"/>
                  </th>
                  <th style="background-color:#7761a7;color:white;font-weight:bold;font-size: 12px;">
                   <s:property value="getText('ThirdParty')"/>
                  </th>
                  </tr>
                  
                  <s:set name="hashMap" value="#session.carrierCodes"  />
                  
                <s:set name="index" value="%{1}"/> 
                 
                   <s:iterator value="hashMap" id="hm" >
                   <s:set name="hashMapKey" value="%{#hm.key}" />
                   <s:set name="hashMapValue" value="%{#hm.value}"/>
                            
                
                          <s:if test="%{#hashMapKey != 999 && #hashMapKey != 0}" >    
                
                
                 
                  <s:set name="custAccNumList1" value="%{#session.custAccNumList}" /> 
                    
                  <s:if test="%{#custAccNumList1!=null}" >
                    <s:iterator id="aascCustomerAccountNumbers" value="#custAccNumList1">
                
                <s:if test="%{#hashMapKey == #aascCustomerAccountNumbers.carrierCode}" >
                
                  <tr>
                  
                  
                  <td class="dispalyFields" align="left" id="carrierCodeValue${index}">
                
                <s:property value="#hashMapValue" />
                  <s:hidden name="carrierCode%{#index}" id="carrierCodeID%{#index}" value="%{#hashMapKey}" />
                  
                
                  </td>
                  
                  <td class="dispalyFields" align="left">
                 
                   <s:textfield name="recipient%{#index}" id="recipientID%{#index}" value="%{#aascCustomerAccountNumbers.recipient}" />
                  </td>
                  
                  <td class="dispalyFields" align="left">
                       <s:textfield name="thirdParty%{#index}" id="thirdPartyID%{#index}" value="%{#aascCustomerAccountNumbers.thirdParty}" />
                
                  </td>
                  
                  </tr>
                   <s:set name="index" value="%{#index+1}"/>
                  </s:if>
                 
                 
                  </s:iterator>
                  
                  </s:if>
                  <s:else>
                  
                  <tr>
                
                  
                  <td class="dispalyFields" align="left" id="carrierCodeValue${index}">
                
                 
                 <s:property value="#hashMapValue" />
                
                
                <s:hidden name="carrierCode%{#index}" id="carrierCodeID%{#index}" value="%{#hashMapKey}" />
                  </td>
                  
                  <td class="dispalyFields" align="left">
                   
                   <s:textfield name="recipient%{#index}" id="recipientID%{#index}" value="" />
                  </td>
                  
                  <td class="dispalyFields" align="left">
                    
                       <s:textfield name="thirdParty%{#index}" id="thirdPartyID%{#index}" value="" />
                
                  </td>
                  
                  </tr>
                  <s:set name="index" value="%{#index+1}"/>
                  </s:else>
                  
                    
                    
                    </s:if>
                  </s:iterator>
                  <s:hidden value="%{#index}" name= "totalIndex" id="totalIndexID"/><!-- Sunanda added code fo store the total index to get this value in js for validation fro bug fix # -->
              </table>
              
        <table width="100%">
  
          <tr>
          
            <td> <s:hidden name="size" id="sizeID" value="%{#index}" />
            <s:hidden name="actionType" id="actionTypeID"  value="" />
            </td>
          </tr>
          
          <tr> 
          <td width="13%"> </td>
          
          <td align="right" width="35%">
            <!--<a  href="#" onclick="return onSaveAcctNumbers();"> <img src="buttons/aascSave1.png" name="Save" id="SaveButton" border="0" alt="Save" align="middle"></a> -->
            
            <button type="button" class="btn btn-success" name="save" id="SaveButton" onclick="return onSaveAcctNumbers();"  align="middle"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
           <td width="4%"> </td> 
          </td> <td align="left" width="35%">
            <!--<a href="javascript:window.close()" ><img src="buttons/aascClose1.png"  name="close" id="closeButton" border="0" alt="Close" align="middle"  ></a>-->
            
            <button type="button" class="btn btn-danger"  name="close" id="closeButton"  onclick="javascript:window.close();"  align="middle"> Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
            
          </td> 
          
          <td width="13%"> </td>
          </tr>
  </table>
          <br/>    
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
