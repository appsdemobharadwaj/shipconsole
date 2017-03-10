<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%/*========================================================================+
@(#)aascUpdateShipFromLocation.jsp 13/08/2014
* Copyright (c) 2014 Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Updating ShipTo Locatin details
* @version 2 
* @author Jagadish
* @history
* 
* 17-Dec-2014   Eshwari M    Merged Sunanda code on issues pending
* 07-Jan-2015   Y Pradeep    Merged Sunandas code : Removed the hard codings by getting from Properties file, removed adhoc and added proper id's.
* 21-Jan-2015   K Sunanda    Added code to display success messages in green and errored in red for bug #2506
* 04/02/2015    K Sunanda    Added Id for display messages
* 14/03/2015   Suman G      Commented code to fix issue of postal code numaric value.  
* 23/03/2015   Sunanda      Added code for newly created fields email address and addressline 3
* 08/04/2015    Y Pradeep   Modified code to align fields in order and in a single table to do tab key navigation. Bug #2809.
* 15/04/2015    Suman G     Modified code to fix #2850.
* 15/04/2015    Suman G     Added session to fix #2743
* 23/04/2015    Y Pradeep   Removed footer.
* 20/05/2015    K Sunanda   Modified code for contact name to be mandatory and 
                            no special characters to be allowed for Ship To Location name
* 11/06/2015  Suman G       Removed fevicon to fix #2992
* 12/06/2015  Suman G       Added Padmavathi's code to fix #2985
* 19/06/2015  Sunanda K     Removed HardCodings and got the headings from applicationResourceProperties
* 19/06/2015  Sunanda K     Changed the tags to struts 2 tags
* 07/07/2015  Dinakar       Aligined UI as per tab
* 21/07/2015    Suman G        Modified message id for QA
* 24/07/2015  Rakesh K      Modified Code to work application in offline.
* 04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
* 26/08/2015  N Srisha    Added Id's for Automation testing
* 26/08/2015  Rakesh K       Added code to solve 3496.
* 28/08/2015  N Srisha        Added validation for email address bug #3497
* 28/10/2015  Suman G       Added validation for email address.
* 13/11/2015  Suman G       Added code to fix #2985.
* 09/12/2015  Y Pradeep     Modified code to display proper message. Bug #4088.
*/
%>
<link type="text/css" rel="stylesheet" href="aasc_ss.css">
<%! 
  public String nullStringToSpace(Object object) {
        String spcStr = "";
        if (object == null || "null".equalsIgnoreCase(object.toString())) {
            return spcStr;
        } else {
            return object.toString();
        }
   }
%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
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
  <script src="jquery-1.8.3.js" type="text/javascript"></script>
 <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />-->
 
  <script src="jquery-ui.js" type="text/javascript"></script>

  <script type="text/javascript"> 
  
  function trim(str)
  {
        // alert("str :"+str);
        try{
        return str.replace(/^\s*|\s*$/g,"");
        }catch(err){}
       
  }
  function trimOne(str)
  {
        // alert("str :"+str);
        try{
        return str.replace("-","");
        }catch(err){}
  }
  function isInteger(s)        
  {
        var i;
        for (i = 0; i < s.length; i++)
        {   
        var c = s.charAt(i);
        if (((c < "0") || (c > "9")))
          return false;
        } 
        return true;
   } 
  function checkData()
   {
        document.UpdateShipToLocationForm.shipToCustLocation.value = trim(document.UpdateShipToLocationForm.shipToCustLocation.value);
		document.UpdateShipToLocationForm.shipToCustomerName.value = trim(document.UpdateShipToLocationForm.shipToCustomerName.value);
        document.UpdateShipToLocationForm.addressLine1.value= trim(document.UpdateShipToLocationForm.addressLine1.value);
        document.UpdateShipToLocationForm.addressLine2.value = trim(document.UpdateShipToLocationForm.addressLine2.value);
        document.UpdateShipToLocationForm.city.value = trim(document.UpdateShipToLocationForm.city.value);
        document.UpdateShipToLocationForm.state.value = trim(document.UpdateShipToLocationForm.state.value);
        document.UpdateShipToLocationForm.postalCode.value = trim(document.UpdateShipToLocationForm.postalCode.value);
        document.UpdateShipToLocationForm.countryCode.value = trim(document.UpdateShipToLocationForm.countryCode.value);
        document.UpdateShipToLocationForm.phoneNumber.value = trim(document.UpdateShipToLocationForm.phoneNumber.value);
        document.UpdateShipToLocationForm.emailAddress.value = trim(document.UpdateShipToLocationForm.emailAddress.value);
        var shipToCustomerName = document.UpdateShipToLocationForm.shipToCustomerName.value;
        var address1Val= document.UpdateShipToLocationForm.addressLine1.value;
        var address2Val = document.UpdateShipToLocationForm.addressLine2.value;
        var cityVal = document.UpdateShipToLocationForm.city.value;
        var stateVal = document.UpdateShipToLocationForm.state.value;
        var postalCodeVal = document.UpdateShipToLocationForm.postalCode.value;
        var countryVal = document.UpdateShipToLocationForm.countryCode.value;
        var phoneNumberVal = document.UpdateShipToLocationForm.phoneNumber.value;
		var shipToContactName=document.UpdateShipToLocationForm.shipToContactName.value;
        var shipToCustLocation=document.UpdateShipToLocationForm.shipToCustLocation.value;
        var email = document.UpdateShipToLocationForm.emailAddress.value;
       
 // below code added by Jagadish for not allowing special charcters for customer name and contact name
     if(chkSplCharsAll(shipToContactName)==false)
    {
        alert("No Special Characters Allowed for contact name.");
        document.UpdateShipToLocationForm.shipToContactName.focus();
        return false;
    }
    if(chkSplCharsCustomerName(shipToCustomerName)==false)
    {
        alert("Only @#&-_.,() special characters are allowed for Customer name.");
        document.UpdateShipToLocationForm.shipToCustomerName.focus();
        return false;
    } 
    //end of Jagadish code
    
	// below code added by Sunanda for not allowing special charcters for Ship To Location name
        //Mahesh added below code for shipToCustLocation not allowing special characters
       if(chkSplCharsAll(shipToCustLocation)==false)
    {
        alert("No Special Characters Allowed for Ship to location name.");
        document.UpdateShipToLocationForm.shipToCustLocation.focus();
        return false;
    } 
    //Sunanda's code end here 
     //Sunanda added below code for cantact name to be mandatory
    if(shipToContactName == "")
    {
        alert("Please Enter Contact Name");
        document.UpdateShipToLocationForm.shipToContactName.focus();
        return false;
    }
    //Sunanda code ends here	
        if(shipToCustomerName == "")
        {
            alert("Please Enter Customer Name");
            document.UpdateShipToLocationForm.shipToCustomerName.focus();
            return false;
        } 
        if(shipToCustomerName.indexOf('"') != -1) 
        {
            alert("Customer Name Should not contain Double Quote");
            document.UpdateShipToLocationForm.shipToCustomerName.focus();
            return false;
        }
        if(shipToCustLocation == "")
    {
        alert("Please Enter Location Name");
        document.UpdateShipToLocationForm.shipToCustLocation.focus();
        return false;
    } 
    //Mahesh commented below code for shipToCustLocation not allowing special characters
//    if(chkSplCharsLocation(shipToCustLocation)) 
//    {
//        alert("Location Name Should not contain Special characters other than ~@^`{}|[]\:?");
//        document.UpdateShipToLocationForm.shipToCustLocation.focus();
//        return false;
//    }
        if(address1Val == "")
        {
            alert("Please Enter Address1");
            document.UpdateShipToLocationForm.addressLine1.focus();
            return false;
        }
          if(address1Val.indexOf('"') != -1) 
        {
            alert("Address1 Should not contain Double Quote");
            document.UpdateShipToLocationForm.addressLine1.focus();
            return false;
        }
        
        if((address2Val.indexOf('"') != -1) && (address2Val != "")) 
        {
            alert("Address2 Should not contain Double Quote");
            document.UpdateShipToLocationForm.addressLine2.focus();
            return false;
        }
        
        if(cityVal == "")
        {
            alert("Please Enter City");
            document.UpdateShipToLocationForm.city.focus();
            return false;
        }
          
        if(stateVal == "")
        {	
            alert("Please Enter State");
            document.UpdateShipToLocationForm.state.focus();
            return false;
        }
        if(isChar(trim(stateVal))==false)
        {
            alert("Numeric Values Are Not Allowed for State.");
            document.UpdateShipToLocationForm.state.focus();
            return false;
        }
        
        if(chkSplCharsAll(stateVal)==false)
        {
            alert("No Special Characters Allowed for State.");
            document.UpdateShipToLocationForm.state.focus();
            return false;
        }
        
        if(postalCodeVal == "")
        {	
            alert("Please Enter Postal Code");
            document.UpdateShipToLocationForm.postalCode.focus();
            return false;
        }
        if(postalCodeVal < 0)
        {
            alert("Postal Code should be greater than zero");
            document.UpdateShipToLocationForm.postalCode.focus();
            return false;
        }
        var temPval= trimOne(postalCodeVal);
        if(isNaN(temPval))
        {
//        alert("Enter valid number for Postal Code.");
        //document.UpdateShipToLocationForm.postalCode.focus();
//                   return false;
        }
        
            if(countryVal == "" || countryVal == "Select")
          {	
            alert("Please Enter Country");
            document.UpdateShipToLocationForm.countryCode.focus();
            return false;
          }
          if(isChar(trim(countryVal))==false)
         {
          alert("Numeric Values Are Not Allowed for Country.");
        document.UpdateShipToLocationForm.countryCode.focus();
        return false;
         }   
         
         if(chkSplCharsAll(countryVal)==false)
        {
        alert("No Special Characters Allowed for Country.");
        document.UpdateShipToLocationForm.countryCode.focus();
                    return false;
        }
        
        if(phoneNumberVal == "")
        {	
            alert("Please Enter Phone Number");
            document.UpdateShipToLocationForm.phoneNumber.focus();
            return false;
        }
          
        if(phoneNumberVal!="" && phoneNumberVal!=null && phoneNumberVal!=0)
        {
        
            if (checkInternationalPhone(phoneNumberVal)==false)
            {
//                alert("Please Enter only numeric values for the Phone Number");
//                document.UpdateShipToLocationForm.phoneNumber.focus();
//                return false;
            }
        }
//        if(email.length == 0 || email.length == null ){
//        alert("Please enter Email Address ");
//        document.UpdateShipToLocationForm.emailAddress.focus();
//        return false;
//        }
        if(email.length > 50){
            alert("Email Address cannot be greater than 50 char");
            document.UpdateShipToLocationForm.emailAddress.focus();
            return false;
        }
    else if(!validateEmail(email)){
        alert("Please enter valid Email Address");
        document.UpdateShipToLocationForm.emailAddress.focus();
        return false;
    }
//        if(email.length != 0 && email.length != null ) {
//            if(!/^[a-zA-Z0-9_.]+@[a-zA-Z]+.com$/.test(email)){
//                alert("Please enter valid Email Address. in the form 'name@domain.com'");
//                document.UpdateShipToLocationForm.emailAddress.focus();
//                return false;
//            }
//        }
        document.UpdateShipToLocationForm.actiontype.value='editLocationDetails';
        document.UpdateShipToLocationForm.submit();
         return true;
    }
    
    function chkSplCharsLocation(message)
    {
       var len= (trim(message)).length;
       var message = trim(message);
    //   alert("message :"+len);
       var test=0;
       for(var index = 0; index <len;index++)
       {
               var c = message.charAt(index);
    //	      alert("c :"+c);
               if(c == "!"||c == "#"||c == "$"||c == "%"||c == "&"||c == '*'||c == '('||c == ')'||c == "+" ||c == "_" ||c == "-"||c == "="||c == '"'||c == ";"||c == "'"||c == '<'||c == '>'||c == ','||c == '.'||c == '/' )  
               {
                     return true;
               }
       }  
       return false;
    }
   function trimPhone(s)
   {   
        var i;
        var returnString = "";
        // Search through string's characters one by one.
        // If character is not a whitespace, append to returnString.
        for (i = 0; i < s.length; i++)
        {   
        // Check that current character isn't whitespace.
        var c = s.charAt(i);
        if (c != " ") returnString += c;
        }
        return returnString;
   }    
  function checkInternationalPhone(strPhone)
  {
    // Declaring required variables
    var digits = "0123456789";
    // non-digit characters which are allowed in phone numbers
    var phoneNumberDelimiters = "()- ";
    // characters which are allowed in international phone numbers
    // (a leading + is OK)
    var validWorldPhoneChars = phoneNumberDelimiters + "+";
    // Minimum no of digits in an international phone no.
    var minDigitsInIPhoneNumber = 10;
    var bracket=3
    strPhone=trimPhone(strPhone)
    //alert('strPhone.indexOf("+") :'+strPhone.indexOf("+"));
    if(strPhone.indexOf("+")>1) 
    {
        //alert('3');
        return false
   }
   //alert('strPhone.indexOf("-") :'+strPhone.indexOf("-"));
    if(strPhone.indexOf("-")!=-1)
    {
        bracket=bracket+1;
        //alert('bracket::'+bracket);
    }
    if(strPhone.indexOf("(")!=-1 && strPhone.indexOf("(")>bracket)
    {
        return false;
    }
    var brchr=strPhone.indexOf("(")
    if(strPhone.indexOf("(")!=-1 && strPhone.charAt(brchr+4)!=")")
    {
        return false;
    }
    if(strPhone.indexOf("(")==-1 && strPhone.indexOf(")")!=-1)
    {
        return false;
    }
    s=stripCharsInBagPhone(strPhone,validWorldPhoneChars);
    return (isIntegerPhone(s));
  }

  function isIntegerPhone(s)
  {  
        var i;
        for (i = 0; i < s.length; i++)
        {   
            // Check that current character is number.
            var c = s.charAt(i);
            if (((c < "0") || (c > "9"))) return false;
        }
        // All characters are numbers.
        return true;
  }
  function stripCharsInBagPhone(s, bag)
  {  
        var i;
        var returnString = "";
        // Search through string's characters one by one.
        // If character is not in bag, append to returnString.
        for (i = 0; i < s.length; i++)
        {   
            // Check that current character isn't whitespace.
            var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
        }
        return returnString;
  }
function chkSplCharsCustomerName(message)
{
    var len= (trim(message)).length;
    var message = trim(message);
    
    for(var index = 0; index <len;index++)
    {
        var c = message.charAt(index);
        if(c == "=" || c == "+" || c == '?' || c == '>' || c == '<' || c == "}" || c == "{" || c == "]" || c == "[" || c == "/" || c == ';' || c == ':' || c == '"' || c == '!' || c=='$' || c == '%' || c=='^' || c=='*')  
        {
            return false;
        }
    
    }  
    return true;
}
  function chkSplChars(message)
  {
        var len= (trim(message)).length;
        var message = trim(message);
        //alert("message :"+message);
        for(var index = 0; index <len;index++)
        {
            var c = message.charAt(index);
            //   alert("c :"+c);
            //   if(c == ','||c == '!'||c=='@'||c == '#'||c=='$'||c == '%'||c=='^'||c == '&'||c=='*'||c == '('||c==')')  
             if(c == "="||c == "-"||c == "+"||c == ","||c == '?'||c == '>'||c == '<'||c == "}"||c == "{"||c == "]"||c == "["||c == "/"||c == ';'||c == ':'||c == '"'||c == '_'||c == ','||c == '!'||c=='@'||c == '#'||c=='$'||c == '%'||c=='^'||c == '&'||c=='*'||c == '('||c==')')  
             {
                return false;
             }
        }   
  }

  function chkSplCharsAll(message)
  {
        var len= (trim(message)).length;
        var message = trim(message);
        //alert("message :"+message);
        for(var index = 0; index <len;index++)
        {
        var c = message.charAt(index);
        //   alert("c :"+c);
        //   if(c == '"'||c == ':'||c == ';'||c == '\'||c == '\'||c == '}'||c == '{'||c == ']'||c == '['||c == '?'||c == '/'||c == '>'||c == '<'||c == '='||c == '_'||c == '+'||c == '-'||c == ','||c == '!'||c=='@'||c == '#'||c=='$'||c == '%'||c=='^'||c == '&'||c=='*'||c == '('||c==')')  
           if(c == "="||c == "_"||c == "+"||c == ","||c == "-"||c == '?'||c == '>'||c == '<'||c == "}"||c == "{"||c == "]"||c == "["||c == "/"||c == ';'||c == ':'||c == '"'||c == '_'||c == ','||c == '!'||c=='@'||c == '#'||c=='$'||c == '%'||c=='^'||c == '&'||c=='*'||c == '('||c==')')  
         {
                        return false;
         }
        }  
      return true;
  }
  function isChar(s)
  {
        var i;
        for (i = 0; i < s.length; i++){   
                var c = s.charAt(i);
        //c == '"'||c == ':'||c == ';'||c == '\'||c == '\'||c == '}'||c == '{'||c == ']'||c == '['||c == '?'||c == '/'||c == '>'||c == '<'||c == '='||c == '_'||c == '+'||                          
                if (!((c < "0") || (c > "9"))) return false;
        }
        return true;
  }  
        
  function isNumber(s)
  {
        var i;
        for (i = 0; i < s.length; i++){   
                var c = s.charAt(i);
        //c == '"'||c == ':'||c == ';'||c == '\'||c == '\'||c == '}'||c == '{'||c == ']'||c == '['||c == '?'||c == '/'||c == '>'||c == '<'||c == '='||c == '_'||c == '+'||                          
                if (((c < "0") || (c > "9"))) return false;
        }
        return true;
  }  
  function stateChgCase()
  {
        document.UpdateShipToLocationForm.state.value = (document.UpdateShipToLocationForm.state.value).toUpperCase();
  }
  function countryCodeChgCase()
  {
        document.UpdateShipToLocationForm.countryCode.value = (document.UpdateShipToLocationForm.countryCode.value).toUpperCase();
  }
  function openAccountNumbers()
  {
        var locationId = document.UpdateShipToLocationForm.customerLocationId.value;
        if(locationId!=""){
        acctNumbersWindow =  window.open("aascCustomerAccountNumbers.jsp?locationId="+locationId,"Post",'width=600,height=500,top=100,left=100,scrollbars=yes, resizable=yes');
        acctNumbersWindow.focus();
        }
  }
  function redirectToLogin(){
        
        document.getElementById("UpdateShipToLocationFormId").action = "login.action?actiontype=Logout";
        document.getElementById("UpdateShipToLocationFormId").submit();
        
    }
    function validateEmail(x)
{
    var atpos=x.indexOf("@");
    var dotpos=x.lastIndexOf(".");
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length){
        //alert("Not a valid e-mail address");
        return false;
    }
    else
    {
    return true;
    }
    
}
function chkSplCharsLocation(message)
{
   var len= (trim(message)).length;
   var message = trim(message);
//   alert("message :"+len);
   var test=0;
   for(var index = 0; index <len;index++)
   {
	   var c = message.charAt(index);
//	      alert("c :"+c);
	   if(c == "!"||c == "#"||c == "$"||c == "%"||c == "&"||c == '*'||c == '('||c == ')'||c == "+"||c == "_" ||c == "-"||c == "="||c == '"'||c == ";"||c == "'"||c == '<'||c == '>'||c == ','||c == '.'||c == '/' )  
	   {
		 return true;
	   }
   }  
   return false;
}
</script>
  <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title> <s:property value="getText('ShippingUpdateShipTo')"/></title>
    <style type="text/css">
      .href {color: #003399}
    </style>
     <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
  
    
    <style type="text/css">
      html {
        height: 100%;
            }
            
             fieldset.scheduler-border {
        border: 2px groove #7761a7 !important;
        padding: 1.4em 1.4em 1.4em 1.4em !important;
       border-radius: 15px;}
       .button { 
       width: 150px;
       padding: 10px; 
       background-color: #FF8C00; 
       box-shadow: -8px 8px 10px 3px rgba(0,0,0,0.2); 
       font-weight:bold; text-decoration:none; }
       .blurred {
      -webkit-filter: blur(2px);
      -webkit-transition: all 2s;
}

    </style>
  </head>
  
    <body onload="">
    <div class="container-fluid" style="background-color:#ADADAD;">
    <s:form name="UpdateShipToLocationForm" id="UpdateShipToLocationFormId"  method="POST" action="AascUpdateShipToLocationAction">
    
    <s:bean id="countryNameHashMap" name="java.util.HashMap"/>
    
    <c:catch var="exception1">
        <s:set name="countryNameHashMap" value="%{#session.countryNameHashMap}" /> 
        </c:catch>
    <s:if test="%{#exception1 != null}">
        <s:param  name="countryNameHashMap" value="%{'select'}"/>
    </s:if>
    
      <c:set var="actionType1" value="${param.actiontype}" scope="page"/>
      <s:set name="actionType" value="%{#attr.actionType1}"/>
      <c:set var="currentRow1" value="${param.currentRow}" scope="page"/>
      <s:set name="currentRow" value="%{#attr.currentRow1}"/>
      <s:hidden name="actionType" value="%{#actionType}"/>
      <s:set name="clientIDInt" value="%{#session.client_id}"/>
      <%
        int clientId1 = ((Integer)session.getAttribute("client_id")).intValue();
        int userId1=(Integer)session.getAttribute("user_id");
        int locationId1=((Integer)session.getAttribute("location_id")).intValue();
        pageContext.setAttribute("client_id",clientId1);
        pageContext.setAttribute("user_id",userId1);
        pageContext.setAttribute("location_id",locationId1);
      %>
        <s:set name="client_Id" value="%{#attr.client_id}"/>
        <s:set name="user_Id" value="%{#attr.user_id}"/>
        <s:set name="location_Id" value="%{#attr.location_id}"/>
        <s:hidden name="client_Id" /> 
        <s:hidden name="user_Id" /> 
        <s:hidden name="location_Id" /> 
        <s:hidden name="actiontype" />
         <table width="100%" border="0" cellpadding="0" cellspacing="0" >  <!--Sunanda added for DB message -->
          <tr> <td  align="left"><s:include value="aascIndexHeader.jsp" /></td> </tr>
       <tr>
       <!--21/01/2015 sunanda added-->
            <c:catch var="exception1">
              <s:set name="key" value="%{#attr.key}"/>
              <s:if test="%{#key != null}">
            <s:if test="%{#key==aasc604}">
             <td class="displayMessage" id="displayMessage" align="center" valign="bottom">
              <s:property value="getText(#key)"/> 
             </td>
            </s:if>
            <s:else>
             <td class="displayMessage1" id="displayMessage" align="center" valign="bottom">
                <s:property value="getText(#key)"/>
             </td>
            </s:else>
            <s:set name="key" value="%{'null'}"/>
              </s:if>
            </c:catch>
          <s:if test="%{#exception1 != null}">
            <redirect url="/aascShipError.jsp">
              <s:param name="errorObj" value="%{#exception1.message}"/>
            </redirect>
          </s:if>
       </tr>
      </table>
        <s:if test="%{#exception1 != null}">
        <c:redirect url="/aascShipError.jsp">
          <s:param name="errorObj" value="%{#exception1.message}"/>
        </c:redirect>
        </s:if> 
      <s:if test='%{#actionType == "Edit"}'>     
      <s:set name="locationDetailsList" value="%{#session.shipToCustomersList}"/>  
        <s:iterator id="locationDetailsInfo" value="#locationDetailsList" status="iteratorStatus">
           <s:if test="%{#currentRow == #iteratorStatus.count}">
               <s:set name="locationDetailsInfoBean" value="%{#locationDetailsInfo}"/>
           </s:if>
        </s:iterator> 
  </s:if>
  <s:else>
            <s:set name="locationDetailsInfoBean" value="%{#session.aascShipToLocationsInfo}"/>
            <s:set name="aascShipToLocationsInfo" value="%{''}" scope="session"/>
  </s:else>
  <s:set name="locationId" value="%{#locationDetailsInfoBean.customerLocationId}" />
      <s:hidden name="locationId%{#currentRow}" cssClass="dispalyFields" id="%{'currentRow'}" value="%{#locationId}"/>
      <s:hidden name="customerLocationId" value="%{#locationId}"/>
      
      <br/>
         <div class="row">   <br/></div>
        <div class="row">    <br/></div>
        
      
        
            <center>
            
                <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #3D566D; border-style: solid;margin-left:1%;margin-right:1%;width:90%"> 
                        <div style="background-color:#3D566D;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                            <label style="color:#ffffff;padding-left:10px;font-size:20px">Update Ship To Location</label>
                        </div> 
                        
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('CustomerName')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="shipToCustomerName" readonly="true"  cssClass="inputs"  id="shipToCustomerNameId" size="20" value="%{#locationDetailsInfoBean.shipToCustomerName}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('ContactName')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="shipToContactName"  cssClass="inputs"  id="shipToContactNameId" size="20" value="%{#locationDetailsInfoBean.shipToContactName}"/>
                            </div>
                          </div>
                        </div>
                        
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('LocationName')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="shipToCustLocation"  cssClass="inputs"  id="shipToCustLocationId" size="20" value="%{#locationDetailsInfoBean.shipToCustLocation}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('AddressLine1')"/> </span> </label>
                            
                            <div class="col-sm-3">
                               <s:textfield name="addressLine1" cssClass="inputs" id="addressLine1Id" size="20" value="%{#locationDetailsInfoBean.addressLine1}"/>
                            </div>
                          </div>
                        </div>
                        
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('AddressLine2')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                 <s:textfield name="addressLine2" cssClass="inputs" id="addressLine2Id" size="20" value="%{#locationDetailsInfoBean.addressLine2}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('AddressLine3')"/> </span> </label>
                            
                            <div class="col-sm-3">
                               <s:textfield name="addressLine3" cssClass="inputs" id="addressLine3Id" size="20" value="%{#locationDetailsInfoBean.addressLine3}"/>
                            </div>
                          </div>
                        </div>
                        
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('City')"/> </span> </label>
                            
                            <div class="col-sm-3">
                             <s:textfield name="city"  cssClass="inputs" id="cityId" size="20" value="%{#locationDetailsInfoBean.city}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('State')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="state"  cssClass="inputs" id="stateId" size="20" onblur="stateChgCase()" value="%{#locationDetailsInfoBean.state}"/>
                            </div>
                          </div>
                        </div>
                        
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('PostalCode')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="postalCode"  cssClass="inputs" id="postalCodeId" size="20" value="%{#locationDetailsInfoBean.postalCode}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('Country')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:iterator value="countryNameHashMap">
                                                <s:set name="countryNameHashMapKey" value="%{#key}"/>
                                                <s:set name="countryNameHashMapValue" value="%{#value}"/>
                                    </s:iterator>
                                    <s:select list="countryNameHashMap" listKey="value" listValue="key" headerKey="Select" headerValue="Select" name="countryCode"
							 cssClass="inputs" onblur="countryCodeChgCase()" id="countryCodeId" value="%{#locationDetailsInfoBean.countryCode}"/>
                            </div>
                          </div>
                        </div>
                        
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('PhoneNumber')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="phoneNumber"  cssClass="inputs" id="phoneNumberId" size="20"  value="%{#locationDetailsInfoBean.phoneNumber}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('emailAddress1')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                  <s:textfield name="emailAddress" cssClass="inputs" id="emailAddressId" size="20" value="%{#locationDetailsInfoBean.emailAddress}"/>
                            </div>
                          </div>
                        </div>
                        
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('AddAccountNunmbers')"/> </span> </label>
                           
                            <div class="col-sm-3" align="left">
                                <span class="btn btn-primary" name="acctNumberDetails" id="acctNumberDetails"  onclick="openAccountNumbers(); return true; " style="margin-left: 12px;" alt="Account Numbers">Details </span>
                            </div>
                          </div>
                        </div>
                        <div class="row"><br/></div>
                        <div class="row" >
                          <div class="col-sm-3">
                          </div>
                            <div class="col-sm-6">
                                <input type="hidden" name="UpdateButtonId" value="0" id="UpdateButtonId">
                                <button class="btn btn-success" onclick="return checkData();" name="Save" id="SaveButton" value="editLocationDetails" alt="Save" align="middle">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                                <button class="btn btn-danger" name="closeButton" id="closeButton" onclick="document.UpdateShipToLocationForm.actiontype.value='Cancel';" value="Cancel" alt="Close" align="middle">Close <span class="badge"><span class="glyphicon glyphicon-off"></span></span></button>
                            </div>
                            <div class="col-sm-3">
                            </div>
                           </div>
                           
                           <div class="row"><br/></div>
                        
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
