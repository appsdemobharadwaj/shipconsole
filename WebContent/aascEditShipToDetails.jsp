<%
/*========================================================================+
@(#)aascEditShipToDetails.jsp 11/08/2014
* Copyright (c) 2014-2015 Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Editing ShipToDetails
* @version 1.0
* @author Suman Gunda
* @history
*
*  17-Dec-2014   Eshwari M       Merged sunanda code
*  13-Jan-2015   Y Pradeep       Fixed bug number #2485 by changing accessLevel to roleId in enableDisableOnAccessLevel() function.
*  15/01/2015    Y Pradeep       Merged Sunanda's code : getting title name from Application.Property file.
*  15/04/2015    Suman G         Added session to fix #2743
*  23/04/2015    Y Pradeep       Removed footer.
*  01/05/2015    Y Pradeep       Modified UI and table size to display save and cancle buttons in the page.
*  11/06/2015    Suman G         Removed fevicon to fix #2992
*  10/07/2015    Suman G         Added Padmavathi's code to align the label and checkbox in aascEditShipToDetails.jsp
*  24/07/2015    Rakesh K        Modified Code to work application in offline.
04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
26/08/2015  Rakesh K       Added code to solve 3496.
*  28/12/2015    Suman G         Commented code to remove customer name field.
*/
%>

<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>  
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
  
  <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
   
    <script language="javascript" src="bootstrap/js/jquery11.min.js" type="text/javascript"></script>
    <!--<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>-->
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
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
  
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
   <!-- <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
   <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">-->
    <title>
<s:property value="getText('SCEditShipToDetails')" /></title>
    <link href="aasc_ss.css" rel="stylesheet" type="text/css"/>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    <script type="text/javascript"  >

function setCheckBoxValue(checkBoxName)
{
  var Chkname=checkBoxName.name;
  if(document.aascEditShipToDetailsForm[Chkname].checked)
  {
    document.aascEditShipToDetailsForm[Chkname].value="Y";
  }
  else
  {
    document.aascEditShipToDetailsForm[Chkname].value="N";
  }
}
function checkBoxValidate()
{
  if(//(!document.aascEditShipToDetailsForm.customerName.checked) &&
    (!document.aascEditShipToDetailsForm.addrLines.checked) &&
    (!document.aascEditShipToDetailsForm.city.checked) &&
    (!document.aascEditShipToDetailsForm.state.checked) &&
    (!document.aascEditShipToDetailsForm.postalCode.checked) &&
    (!document.aascEditShipToDetailsForm.countryCode.checked))
    {
        alert("Please Select atleast one checkbox");
        return false;
    }
    return true;
}
function saveEditShipToDetails()
{

//alert("saveEditShipToDetails");
        if(!checkBoxValidate())
        {
        return false;
        }
        else
        {   
        
//        alert("else :::");
        
//            setCheckBoxValue(document.aascEditShipToDetailsForm.customerName);
            setCheckBoxValue(document.aascEditShipToDetailsForm.addrLines);
            setCheckBoxValue(document.aascEditShipToDetailsForm.city);
            setCheckBoxValue(document.aascEditShipToDetailsForm.state);
            setCheckBoxValue(document.aascEditShipToDetailsForm.postalCode);
            setCheckBoxValue(document.aascEditShipToDetailsForm.countryCode);
            
            //alert("document.aascEditShipToDetailsForm.customerName.value  ::"+document.aascEditShipToDetailsForm.customerName.value);
//            window.opener.document.profileOptionsForm.customerNameFlag.value=document.aascEditShipToDetailsForm.customerName.value;  
            window.opener.document.profileOptionsForm.addrLinesFlag.value=document.aascEditShipToDetailsForm.addrLines.value;  
            window.opener.document.profileOptionsForm.cityFlag.value=document.aascEditShipToDetailsForm.city.value;  
            window.opener.document.profileOptionsForm.stateFlag.value=document.aascEditShipToDetailsForm.state.value;  
            window.opener.document.profileOptionsForm.postalCodeFlag.value=document.aascEditShipToDetailsForm.postalCode.value;  
            window.opener.document.profileOptionsForm.countryCodeFlag.value=document.aascEditShipToDetailsForm.countryCode.value;  
            window.opener.document.profileOptionsForm.editShipToSaveStatus.value="Y";
            //alert("window.opener.profileOptionsForm['editShipToSaveStatus'].value  ::"+window.opener.profileOptionsForm['editShipToSaveStatus'].value);     
            window.close();
        }
}
function onLoad()
{
   //Edit ShipTo Flag is already checked (or) Edit Details popup save button is clicked
   if(window.opener.document.profileOptionsForm.editShipToAddressHidden.value=="Y" || window.opener.document.profileOptionsForm.editShipToSaveStatus.value=="Y")
   {
       //alert("Inside if");
       if(//(window.opener.document.profileOptionsForm.customerNameFlag.value=="") &&
       (window.opener.document.profileOptionsForm.addrLinesFlag.value == "") &&
       (window.opener.document.profileOptionsForm.cityFlag.value == "") &&
       (window.opener.document.profileOptionsForm.stateFlag.value == "") &&
       (window.opener.document.profileOptionsForm.postalCodeFlag.value == "") &&
       (window.opener.document.profileOptionsForm.countryCodeFlag.value == ""))
       {
           //The above 'IF' condition is for if we select the new organization from the PO page where the default shiptoaddress is 'Y' then all fields in editshiptodetails page should be checked. 
//            document.aascEditShipToDetailsForm.customerName.value="Y";
            document.aascEditShipToDetailsForm.addrLines.value="Y";  
            document.aascEditShipToDetailsForm.city.value="Y";  
            document.aascEditShipToDetailsForm.state.value="Y";  
            document.aascEditShipToDetailsForm.postalCode.value="Y";  
            document.aascEditShipToDetailsForm.countryCode.value="Y";
            getCheckBoxValue();
       }
       else
       {
//           document.aascEditShipToDetailsForm.customerName.value=window.opener.document.profileOptionsForm.customerNameFlag.value;
           document.aascEditShipToDetailsForm.addrLines.value=window.opener.document.profileOptionsForm.addrLinesFlag.value;
           document.aascEditShipToDetailsForm.city.value=window.opener.document.profileOptionsForm.cityFlag.value;
           document.aascEditShipToDetailsForm.state.value=window.opener.document.profileOptionsForm.stateFlag.value;
           document.aascEditShipToDetailsForm.postalCode.value=window.opener.document.profileOptionsForm.postalCodeFlag.value;
           document.aascEditShipToDetailsForm.countryCode.value=window.opener.document.profileOptionsForm.countryCodeFlag.value;
           getCheckBoxValue();
       }
   }
   else // Edit ShipTo Flag is already unchecked, now we checked the Edit ShipTo Flag
   {
//       alert("Inside else");
//        document.aascEditShipToDetailsForm.customerName.value="Y";
        document.aascEditShipToDetailsForm.addrLines.value="Y";  
        document.aascEditShipToDetailsForm.city.value="Y";  
        document.aascEditShipToDetailsForm.state.value="Y";  
        document.aascEditShipToDetailsForm.postalCode.value="Y";  
        document.aascEditShipToDetailsForm.countryCode.value="Y";
        getCheckBoxValue();
   }
   enableDisableOnAccessLevel();
}

function enableDisableOnAccessLevel()
{    //For other than Admin
     if(window.opener.document.profileOptionsForm.roleId.value != 1 && window.opener.document.profileOptionsForm.roleId.value != 3)
     {
//       document.aascEditShipToDetailsForm.customerName.disabled=true;
       document.aascEditShipToDetailsForm.addrLines.disabled=true;
       document.aascEditShipToDetailsForm.city.disabled=true;
       document.aascEditShipToDetailsForm.state.disabled=true;
       document.aascEditShipToDetailsForm.postalCode.disabled=true;
       document.aascEditShipToDetailsForm.countryCode.disabled=true;
     }
}

function getCheckBoxValue()
{
//    if(document.aascEditShipToDetailsForm.customerName.value=='Y')
//    {
//     document.aascEditShipToDetailsForm.customerName.checked=true;
//    }
//    else
//    {
//     document.aascEditShipToDetailsForm.customerName.checked=false;
//    }
    
    if(document.aascEditShipToDetailsForm.addrLines.value=='Y')
    {
     document.aascEditShipToDetailsForm.addrLines.checked=true;
    }
    else
    {
     document.aascEditShipToDetailsForm.addrLines.checked=false;
    }
    
    if(document.aascEditShipToDetailsForm.city.value=='Y')
    {
     document.aascEditShipToDetailsForm.city.checked=true;
    }
    else
    {
     document.aascEditShipToDetailsForm.city.checked=false;
    }
    
    if(document.aascEditShipToDetailsForm.state.value=='Y')
    {
     document.aascEditShipToDetailsForm.state.checked=true;
    }
    else
    {
     document.aascEditShipToDetailsForm.state.checked=false;
    }
    
    if(document.aascEditShipToDetailsForm.postalCode.value=='Y')
    {
     document.aascEditShipToDetailsForm.postalCode.checked=true;
    }
    else
    {
     document.aascEditShipToDetailsForm.postalCode.checked=false;
    }
    
    if(document.aascEditShipToDetailsForm.countryCode.value=='Y')
    {
     document.aascEditShipToDetailsForm.countryCode.checked=true;
    }
    else
    {
     document.aascEditShipToDetailsForm.countryCode.checked=false;
    }
}


    </script>  
  </head>
  <body class="gradientbg" onload="onLoad();">
  
  <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
            }
    %>
   <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
   <s:form name="aascEditShipToDetailsForm">   <br><br>
   
   <div class="row">
                    <div class="col-sm-12">
                    <div class="col-sm-1"> </div>
                    <div class="col-sm-10"> 
   
   <fieldset class="scheduler-border" style="border-color:#19b698;width:80%; margin-top:1%;background-color:#F0F0F0;text:center;">
     <!-- <div class="row" id="divSub" style="width:100%; ">
            <div class="col-sm-12" align="center">
                <label for="inputEmail" class="control-label "> <span class="dispalyFields"><s:property value="getText('CustomerName')"/></span></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkbox name="customerName" onclick="setCheckBoxValue(this);"  id="customerName" value=""/>
              </div>
              </div>    -->
         <div class="row" id="divSub" style="width:100%; ">   
           <div class="col-sm-12" align="center">
                <label for="inputEmail" class="control-label "> <span class="dispalyFields"><s:property value="getText('AddressLines')"/></span></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:checkbox name="addrLines" onclick="setCheckBoxValue(this);"  value=""/>
               
               </div>    
            </div>
      <div class="row" id="divSub" style="width:100%; ">
            <div class="col-sm-12" align="center">
                <label for="inputEmail" class="control-label "> <span class="dispalyFields"><s:property value="getText('City')"/></span></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <s:checkbox name="city" onclick="setCheckBoxValue(this);"  value=""/>
               </div>                      
                </div>
                <div class="row" id="divSub" style="width:100%; ">
                <div class="col-sm-12" align="center">
                <label for="inputEmail" class="control-label"> <span class="dispalyFields"><s:property value="getText('State')"/></span></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                
                       <s:checkbox name="state" onclick="setCheckBoxValue(this);"  value=""/>
                </div>
               </div>    
           
       <div class="row" id="divSub" style="width:100%; ">
            <div class="col-sm-12" align="center">
                <label for="inputEmail" class="control-label "> <span class="dispalyFields"><s:property value="getText('PostalCode')"/></span></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                
                        <s:checkbox name="postalCode" onclick="setCheckBoxValue(this);"  value=""/>
                </div>
                </div>
                 <div class="row" id="divSub" style="width:100%; ">
                  <div class="col-sm-12" align="center">
                <label for="inputEmail" class="control-label "> <span class="dispalyFields"><s:property value="getText('CountryCode')"/></span></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               
                       <s:checkbox name="countryCode" value="" onclick="setCheckBoxValue(this);"  />
                </div>
               </div>    
            
     <s:set name="accessLevel" value="%{0}"/>
     <%
      String rol = request.getParameter("accessRole");
      request.setAttribute("rol",rol);
     %>
     <s:set name="accessLevel" value="%{#attr.rol}"/>
     <s:if test="%{#accessLevel == 1 || #accessLevel == 3}">
        <hr> <center>
            <button class="btn btn-success" name="save" id="save" onclick="return saveEditShipToDetails();" value="Save" alt="[save]" align="middle"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
            <button class="btn btn-danger" name="CloseButton" id="CloseButton" onclick="window.close();" value="CloseButton" alt="[close]" align="middle"> Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
           
       </center> <hr>
     </s:if>
     </fieldset>
         <div class="col-sm-1"> </div>
                    </div>             
                </div>
                </div>
   </s:form>
   
   </div>
    <div style="position:relative;top:300px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
 </body>
</html>
