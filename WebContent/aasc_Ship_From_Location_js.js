/*========================================================================+
@(#)aasc_Ship_From_Location_js.js 28/07/2014
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
*  
*  History
*  
* 17-Dec-2014   Eshwari M   Merged Sunanda code after alignment and testing
* 06/12/2015    Y Pradeep   Merged Sunanda's code : Added comments and added countryVal in if condition.
* 10/03/2015    K Sunanda   Modified code for bug fix #2659
* 23/03/2015    K Sunanda   Added code for newly created fields Address3 and Email Address
* 14/04/2015    Eshwari M   Modified alerts to match the label names for resolve bug #2854
* 12/06/2015    Suman g     Added Padmavathi's code for issue #2985 
* 28/08/2015  N Srisha        Added validation for email address bug #3497
* 28/10/2015  Suman G         Added validation for email address to fix #3558
* 13/11/2015  Suman G         Added code to fix #2985
+===========================================================================*/

//function to clear the fields in page when clear button is clicked 
function clearFields()
{
    document.createLocation.locationName.value='';
    document.createLocation.contactName.value='';
    document.createLocation.addressLine1.value='';
    document.createLocation.addressLine2.value='';
    document.createLocation.addressLine3.value='';
    document.createLocation.emailAddress.value='';
    document.createLocation.city.value='';
    document.createLocation.state.value='';
    document.createLocation.postalCode.value='';
    document.createLocation.countryCode.value='Select';
    document.createLocation.phoneNumber.value='';
    document.createLocation.locationName.focus();
}


function trim(str)
{
    // alert("str :"+str);
    try{
            return str.replace(/^\s*|\s*$/g,"");
       }
    catch(err){}
}
        
function trimOne(str)
{
    // alert("str :"+str);
    try{
            return str.replace("-","");
       }
    catch(err){}
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
         
//function for validating SetUpLocation Details Entered in the page before saving        
function checkData()
{       
        document.createLocation.locationName.value = trim(document.createLocation.locationName.value);
        document.createLocation.contactName.value = trim(document.createLocation.contactName.value);
        document.createLocation.addressLine1.value = trim(document.createLocation.addressLine1.value);
        document.createLocation.addressLine2.value = trim(document.createLocation.addressLine2.value);
        document.createLocation.addressLine3.value = trim(document.createLocation.addressLine3.value);
        document.createLocation.emailAddress.value = trim(document.createLocation.emailAddress.value);
        document.createLocation.city.value = trim(document.createLocation.city.value);
        document.createLocation.state.value = trim(document.createLocation.state.value);
        document.createLocation.postalCode.value = trim(document.createLocation.postalCode.value);
        document.createLocation.countryCode.value = trim(document.createLocation.countryCode.value);
        document.createLocation.phoneNumber.value = trim(document.createLocation.phoneNumber.value);
        var locationName = document.createLocation.locationName.value;
        var contactName = document.createLocation.contactName.value;
        var address1Val= document.createLocation.addressLine1.value;
        var address2Val = document.createLocation.addressLine2.value;
        var address3Val = document.createLocation.addressLine3.value;
        var emailid=  document.createLocation.emailAddress.value;
        var cityVal = document.createLocation.city.value;
        //  alert("cityVal :"+cityVal);
        var stateVal = document.createLocation.state.value;
        var postalCodeVal = document.createLocation.postalCode.value;
        var countryVal = document.createLocation.countryCode.value;
        var phoneNumberVal = document.createLocation.phoneNumber.value;

        
        
        // below code added by Jagadish for not allowing special charcters for customer name and contact name
//     if(chkSplCharsAll(locationName)==false)
//    {
//        alert("No Special Characters Allowed for location name.");
//        document.createLocation.locationName.focus();
//        return false;
//    }
     if(chkSplCharsAll(contactName)==false)
    {
        alert("No Special Characters Allowed for contact name.");
        document.createLocation.contactName.focus();
        return false;
    } 
    //end of Jagadish code
        
        if(locationName == "")
        {
        		alert("Please Enter Location Name");
            document.createLocation.locationName.focus();
            return false;
        } 
        if(chkSplCharsLocation(locationName))
	      {
            alert("Location Name Should not contain Special characters other than ~@^`{}-|[]\:?");
            document.createLocation.locationName.focus();
            return false;
        }
        if(contactName == "")
        {
	          alert("Please Enter Contact Name");
            document.createLocation.contactName.focus();
            return false;
        } 
        if(contactName.indexOf('"') != -1) 
	      {
            alert("Contact Name Should not contain Double Quote");
            document.createLocation.contactName.focus();
            return false;
        }
        if(address1Val == "")
        {
	    alert("Please Enter AddressLine1");
            document.createLocation.addressLine1.focus();
            return false;
        }
        if(address1Val.indexOf('"') != -1) 
        {
		  alert("Address Line1 Should not contain Double Quote");
	          document.createLocation.addressLine1.focus();
	          return false;
        }
        if((address2Val.indexOf('"') != -1) && (address2Val != "")) 
        {
		  alert("Address Line2 Should not contain Double Quote");
	          document.createLocation.addressLine2.focus();
	          return false;
        }
         if((address3Val.indexOf('"') != -1) && (address3Val != "")) 
        {
		  alert("Address Line3 Should not contain Double Quote");
	          document.createLocation.addressLine2.focus();
	          return false;
        }
        if(cityVal == "")
        {
            alert("Please Enter City");
            document.createLocation.city.focus();
            return false;
        }
        if(stateVal == "")
        {	
            alert("Please Enter State");
            document.createLocation.state.focus();
            return false;
        }
        if(isChar(trim(stateVal))==false)
		    {
            alert("Numeric Values Are Not Allowed for State.");
            document.createLocation.state.focus();
            return false;
		    } 
        if(chkSplCharsAll(stateVal)==false)
		    {
       		alert("No Special Characters Allowed for State.");
		document.createLocation.state.focus();
		return false;
	}
        if(postalCodeVal == "")
	{	
            alert("Please Enter Postal Code");
            document.createLocation.postalCode.focus();
            return false;   
        }
        if(postalCodeVal < 0)
        {
	    alert("Postal Code should be greater than zero");
            document.createLocation.postalCode.focus();
            return false;
        }
        var temPval= trimOne(postalCodeVal);
        if(chkSplCharsAll(temPval)==false)
        {
           alert("No Special Characters Allowed for Postal code.");//bug fix #2659
           document.createLocation.postalCode.focus();
           return false;
        }
        if(countryVal == "" || countryVal=="Select")
        {	
            alert("Please Select Country");
            document.createLocation.countryCode.focus();
            return false;
        }
        if(isChar(trim(countryVal))==false)
        {
            alert("Numeric Values Are Not Allowed for Country.");
            document.createLocation.countryCode.focus();
            return false;
        }   
        if(chkSplCharsAll(countryVal)==false)
        {
       	      alert("No Special Characters Allowed for Country.");
              document.createLocation.countryCode.focus();
              return false;
        }
        if(phoneNumberVal == "")
        {	
            alert("Please Enter Contact Number");
            document.createLocation.phoneNumber.focus();
            return false;
        }
        if(phoneNumberVal!="" && phoneNumberVal!=null && phoneNumberVal!=0)
        {
            if (checkInternationalPhone(phoneNumberVal)==false)
            {
//                    alert("Please Enter only numeric values for the Phone Number");
//                    document.createLocation.phoneNumber.focus();
//                    return false;
            }
        }
         
       if(emailid.length > 50){
            alert("Email Address cannot be greater than 50 char");
            document.createLocation.emailAddress.focus();
            return false;
        }
         else if(!validateEmail(emailid)){
        alert("Please enter valid User Name/Email Address");
        document.createLocation.emailAddress.focus();
        return false;
    }
//        if(emailid.length != 0 && emailid.length != null ) {
//        if(!/^[a-zA-Z0-9_.]+@[a-zA-Z]+.com$/.test(emailid)){
//            alert("Please enter valid Email Address. in the form 'name@domain.com'");
//            document.createLocation.emailAddress.focus();
//            return false;
//        }
//        }
        document.createLocation.submit();
        return true;
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
       for (i = 0; i < s.length; i++)
       {   
          var c = s.charAt(i);
          //c == '"'||c == ':'||c == ';'||c == '\'||c == '\'||c == '}'||c == '{'||c == ']'||c == '['||c == '?'||c == '/'||c == '>'||c == '<'||c == '='||c == '_'||c == '+'||                          
          if (!((c < "0") || (c > "9"))) return false;
       }
       return true;
}  
               
function isNumber(s)
{
       var i;
       for (i = 0; i < s.length; i++)
       {   
           var c = s.charAt(i);
           //c == '"'||c == ':'||c == ';'||c == '\'||c == '\'||c == '}'||c == '{'||c == ']'||c == '['||c == '?'||c == '/'||c == '>'||c == '<'||c == '='||c == '_'||c == '+'||                          
           if (((c < "0") || (c > "9"))) return false;
       }
       return true;
}  
    
//to change state code to Uppercase whenever entered in Lowercase   
function stateChgCase()
{
       document.createLocation.state.value = (document.createLocation.state.value).toUpperCase();
}
    
//to change country code to Uppercase whenever entered in Lowercase   
function countryCodeChgCase()
{
       document.createLocation.countryCode.value = (document.createLocation.countryCode.value).toUpperCase();    
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
	   if(c == "!"||c == "#"||c == "$"||c == "%"||c == "&"||c == '*'||c == '('||c == ')'||c == "+" ||c == "_" ||c == "="||c == '"'||c == ";"||c == "'"||c == '<'||c == '>'||c == ','||c == '.'||c == '/' )  
	   {
		 return true;
	   }
   }  
   return false;
}
