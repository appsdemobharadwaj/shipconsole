/*========================================================================================

Date        Resource       Change history
------------------------------------------------------------------------------------------
11/08/2013 Suman	    Added validations for create adhoc ship from location, also added below following methods for validations.
                            clearFields(), checkData(), checkInternationalPhone(), isIntegerPhone();
17/12/2014 Eshwari M        Merged Sunanda code after alignment and testing
30/12/2014 Sunanda          Added onSaveAcctNumbers() function.
07/01/2015 Y Pradeep        Merged Sunanda's code : Added comments
15/01/2015 Y Pradeep        Merged Sunanda's code on 1.0 release bugs.
21/01/2015 K Sunanda        Modified the code for bug fixes #2512 and #2573
18/02/2015 K Sunanda        Modified the code for bug fix #2619
10/03/2015 K Sunanda        Added code for bug fix #2497
23/03/2015 K Sunanda        Added code for newly created fields email address and addressline 3.
13/04/2015 Y pradeep        Commmented if and else conditions to submit form when all fields in aascCustomerAccountNumbers.jsp page are empty while saving. Bug #2643.
20/05/2015 K Sunanda        Modified code for contact name to be mandatory and 
                            no special characters to be allowed for Ship To Location name
12/06/2015  Suman G         Added Padmavathi's code to fix #2985  
28/08/2015  N Srisha        Added validation for email address bug #3497
28/10/2015  Suman G         Added validation for email address to fix #3558
13/11/2015  Suman G         Added code to fix #2985
03/12/2015  Y Pradeep       Modified code to make Customer Name as editable and to validate and allow only particular special characters. Bug #4054.
09/12/2015  Y Pradeep       Modified code to not allow . when delete button is clicked. Bug #4087.
======================================================================================================================================*/



function onClickAccountNumbers(){
}

function showLoader(){

var file= document.uploadShipTo.myFile.value;

if(file=="" || file==null)
{
alert("Please select file to upload");

return false;
}
else
{
document.uploadShipTo.actiontype.value='uploadShipToLocation';
document.uploadShipTo.submit();
return true;
}
}

function openAccountNumbers(){
}

function onSaveAcctNumbers(){
    var totalIndex=document.getElementById("totalIndexID").value;
//    alert("totalIndex :: "+totalIndex);
    var condition=0;
    var i=1;
    while(i<totalIndex){
    var recipient=document.getElementById("recipientID"+i).value;
    var thirdParty=document.getElementById("thirdPartyID"+i).value;
    if(recipient==''&& thirdParty=='')
     {
         condition++;
        
     }
     i++;
     }
// Commmented if and else conditions to submit form when all fields are empty. By Y Pradeep
//     if(condition != (totalIndex-1))
//     {
         document.CustAccountNumberDetailsForm.actionType.value='save';
         document.CustAccountNumberDetailsForm.submit();
         return true;
//    }
//    else
//    {
//         alert("Please enter atleast one Account Number");
//         return false;
//     }
}

function clearFields()
{ 

    document.popperform.shipToCustLocation.value='';
    document.popperform.shipToCustomerName.value='Select';
    document.popperform.addressLine1.value='';
    document.popperform.addressLine2.value='';
    document.popperform.addressLine3.value='';
    document.popperform.emailAddress.value='';
    document.popperform.shipToContactName.value='';
    document.popperform.city.value='';
    document.popperform.state.value='';
    document.popperform.postalCode.value='';
    document.popperform.countryCode.value='Select';
    document.popperform.phoneNumber.value='';
}

function trim(str)
{
    // alert("str :"+str);
    try{
            return str.replace(/^\s*|\s*$/g,"");
    }catch(err)
    {
    } 
}
        
function trimOne(str)
{
    // alert("str :"+str);
    try{
            return str.replace("-","");
    }catch(err)
    {
    } 
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
    document.popperform.shipToCustLocation.value = trim(document.popperform.shipToCustLocation.value);
    document.popperform.addressLine1.value= trim(document.popperform.addressLine1.value);
    document.popperform.addressLine2.value = trim(document.popperform.addressLine2.value);
    document.popperform.addressLine3.value = trim(document.popperform.addressLine3.value);
     document.popperform.emailAddress.value = trim(document.popperform.emailAddress.value);
    document.popperform.city.value = trim(document.popperform.city.value);
    document.popperform.state.value = trim(document.popperform.state.value);
    document.popperform.postalCode.value = trim(document.popperform.postalCode.value);
    document.popperform.countryCode.value = trim(document.popperform.countryCode.value);
    document.popperform.phoneNumber.value = trim(document.popperform.phoneNumber.value);
    var shipToCustLocation = document.popperform.shipToCustLocation.value;
    var address1Val= document.popperform.addressLine1.value;
    var address2Val = document.popperform.addressLine2.value;
    var address3Val = document.popperform.addressLine3.value;
    var emailid=  document.popperform.emailAddress.value;
    var cityVal = document.popperform.city.value;
    //  alert("cityVal :"+cityVal);
    var stateVal = document.popperform.state.value;
    var postalCodeVal = document.popperform.postalCode.value;
    var countryVal = document.popperform.countryCode.value;
    var phoneNumberVal = document.popperform.phoneNumber.value;
    var shipToCustomerName = document.popperform.shipToCustomerName.value;
    var shipToContactName=document.popperform.shipToContactName.value;
   
    
    // below code added by Jagadish for not allowing special charcters for customer name and contact name
     if(chkSplCharsAll(shipToContactName)==false)
    {
        alert("No Special Characters Allowed for contact name.");
        document.popperform.shipToContactName.focus();
        return false;
    }
     if(chkSplCharsCustomerName(shipToCustomerName)==false)
    {
        alert("Only @#&-_.,() special characters are allowed for Customer name.");
        document.popperform.shipToCustomerName.focus();
        return false;
    }
    //end of Jagadish code
   // below code added by Sunanda for not allowing special charcters for Ship To Location name
   //Mahesh added below code for shipToCustLocation not allowing special characters
       if(chkSplCharsAll(shipToCustLocation)==false)
    {
        alert("No Special Characters Allowed for Ship to location name.");
        document.popperform.shipToCustLocation.focus();
        return false;
    } 
    //Sunanda's code end here 
    if(shipToCustomerName == ""|| shipToCustomerName=="Select")
    {
        alert("Please Enter or select Customer Name");
        document.popperform.shipToCustomerName.focus();
        return false;
    } 
    if(shipToCustLocation == "")
    {
        alert("Please Enter Location Name");
        document.popperform.shipToCustLocation.focus();
        return false;
    }
    //Mahesh commented below code for shipToCustLocation not allowing special characters
//    if(chkSplCharsLocation(shipToCustLocation)) 
//    {
//        alert("Location Name Should not contain Special characters other than ~@^`{}|[]\:?");
//        document.popperform.shipToCustLocation.focus();
//        return false;
//    }
    //Sunanda added below code for cantact name to be mandatory
    if(shipToContactName == "")
    {
        alert("Please Enter Contact Name");
        document.popperform.shipToContactName.focus();
        return false;
    }
    //Sunanda code ends here
    if(address1Val == "")
    {
        alert("Please Enter AddressLine1");
        document.popperform.addressLine1.focus();
        return false;
    }
    if(address1Val.indexOf('"') != -1) 
    {
        alert("Address1 Should not contain Double Quote");
        document.popperform.addressLine1.focus();
        return false;
    }
    if((address2Val.indexOf('"') != -1) && (address2Val != "")) 
    {
        alert("Address2 Should not contain Double Quote");
        document.popperform.addressLine2.focus();
        return false;
    }
     if((address3Val.indexOf('"') != -1) && (address3Val != "")) 
    {
        alert("Address3 Should not contain Double Quote");
        document.popperform.addressLine2.focus();
        return false;
    }
    if(cityVal == "")
    {
        alert("Please Enter City");
        document.popperform.city.focus();
        return false;
    }
    if(stateVal == "")
    {	
        alert("Please Enter State");
        document.popperform.state.focus();
        return false;
    }
    if(isChar(trim(stateVal))==false)
    {
        alert("Numeric Values Are Not Allowed for State.");
        document.popperform.state.focus();
        return false;
    } 
    if(chkSplCharsAll(stateVal)==false)
    {
        alert("No Special Characters Allowed for State.");
        document.popperform.state.focus();
        return false;
    }
    if(postalCodeVal == "")
    {	
        alert("Please Enter Postal Code");
        document.popperform.postalCode.focus();
        return false;
    }
    if(postalCodeVal < 0)
    {
        alert("Postal Code should be greater than zero");
        document.popperform.postalCode.focus();
        return false;
    }
    var temPval= trimOne(postalCodeVal);
    if(chkSplCharsAll(temPval)==false)
    {
        alert("No Special Characters Allowed for Postal code.");
        document.popperform.postalCode.focus();
        return false;
    }
    if(countryVal == "" || countryVal=="Select")
    {	
        alert("Please Select Country");
        document.popperform.countryCode.focus();
        return false;
    }
    if(isChar(trim(countryVal))==false)
    {
        alert("Numeric Values Are Not Allowed for Country.");
        document.popperform.countryCode.focus();
        return false;
    }   
    if(chkSplCharsAll(countryVal)==false)
    {
        alert("No Special Characters Allowed for Country.");
        document.popperform.countryCode.focus();
        return false;
   }
   if(phoneNumberVal == "")
   {	
        alert("Please Enter Phone Number");
        document.popperform.phoneNumber.focus();
        return false;
   }
   if(phoneNumberVal!="" && phoneNumberVal!=null && phoneNumberVal!=0)
   {
        if (checkInternationalPhone(phoneNumberVal)==false){
            
//            alert("Please Enter only numeric values for the Phone Number");
//            document.popperform.phoneNumber.focus();
//            return false;
        }
    }
    
   if(emailid.length > 50){
        alert("Email Address cannot be greater than 50 char");
        document.popperform.emailAddress.focus();
        return false;
    }
    else if(!validateEmail(emailid)){
        alert("Please enter valid User Name/Email Address");
        document.popperform.emailAddress.focus();
        return false;
    }
//    if(emailid.length != 0 && emailid.length != null ) {
//    if(!/^[a-zA-Z0-9_.]+@[a-zA-Z]+.com$/.test(emailid)){
//        alert("Please enter valid Email Address. in the form 'name@domain.com'");
//        document.popperform.emailAddress.focus();
//        return false;
//    }
//    }
   document.popperform.submit();
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
    document.popperform.state.value = (document.popperform.state.value).toUpperCase();
}
    
//to change country code to Uppercase whenever entered in Lowercase   
function countryCodeChgCase()
{
    document.popperform.countryCode.value = (document.popperform.countryCode.value).toUpperCase();    
}

////////////////////////////////////////////////////////////////////////////////////////////////////////
   //For editable select box
    ////////////////////////////////////////////////////////////////////////////////////////////////////////   
   

function getBrowser() 
{
    if( navigator.userAgent.indexOf("Chrome") != -1 ) {
    return "Chrome";
    } else if( navigator.userAgent.indexOf("Opera") != -1 ) {
    return "Opera";
    } else if( navigator.userAgent.indexOf("MSIE") != -1 ) {
    return "IE";
    } else if( navigator.userAgent.indexOf("Firefox") != -1 ) {
    return "Firefox";
    }else if( navigator.userAgent.indexOf("Safari") != -1 ) {
    return "Safari";
    } else {
    return "unknown";
    }
}


function fnKeyDownHandler(getdropdown, e)
{
    fnSanityCheck(getdropdown);
    
    // Press [ <- ] and [ -> ] arrow keys on the keyboard to change alignment/flow.
    // ...go to Start : Press  [ <- ] Arrow Key
    // ...go to End : Press [ -> ] Arrow Key
    // (this is useful when the edited-text content exceeds the ListBox-fixed-width)
    // This works best on Internet Explorer, and not on Netscape
    
    var vEventKeyCode = FindKeyCode(e);
    
    // Press left/right arrow keys
    if(vEventKeyCode == 37)
    {
    fnLeftToRight(getdropdown);
    }
    if(vEventKeyCode == 39)
    {
    fnRightToLeft(getdropdown);
    }
    
    // Delete key pressed
    if(vEventKeyCode == 46)
    {
    fnDelete(getdropdown);
    }
    
    // backspace key pressed
    if(vEventKeyCode == 8 || vEventKeyCode==127)
    {
    if(e.which) //Netscape
    {
         
        var browser=getBrowser();
      
        if(browser=="Chrome"||browser=="Safari"||browser=="IE"||browser=="Firefox")//Sunanda added for bug fix #2573 date 21/01/2015
        {
            
            fnKeyPressHandler_B(getdropdown, e);
            e.preventDefault();
             
        }
        else
        {
            //  e.which = '';
            return false;
        }
    }
    else //Internet Explorer
    {
      //To prevent backspace from activating the -Back- button of the browser
      e.keyCode = '';
      if(window.event.keyCode)
      {
      window.event.keyCode = '';
      }
    }
    return true;
    }
    
    // Tab key pressed, use code below to reorient to Left-To-Right flow, if needed
    //if(vEventKeyCode == 9)
    //{
    //  fnLeftToRight(getdropdown);
    //}
    //alert('onkeydown+getdropdown_length::'+getdropdown.value.length);
    if(getdropdown.value.length > 33)
    {
//      alert('Please Enter Upto 35 Characters Only ');
//      getdropdown.focus();
//      return false;
    }
    
}

function fnLeftToRight(getdropdown)
{
    getdropdown.style.direction = "ltr";
}

function fnRightToLeft(getdropdown)
{
    getdropdown.style.direction = "rtl";
}

function fnDelete(getdropdown)
{
    if(getdropdown.options.length != 0)
    // if dropdown is not empty
    {
    if (getdropdown.options.selectedIndex == vEditableOptionIndex_A)
    // if option the Editable field
    {
      getdropdown.options[getdropdown.options.selectedIndex].text = '';
      getdropdown.options[getdropdown.options.selectedIndex].value = ''; //Use this line only if want to change the internal value too; else this line is not required.
    }
    }
}


  /*
  Since Internet Explorer and Netscape have different
  ways of returning the key code, displaying keys
  browser-independently is a bit harder.
  However, you can create a script that displays keys
  for either browser.
  The following function will display each key
  in the status line:

  The "FindKey.." function receives the "event" object
  from the event handler and stores it in the variable "e".
  It checks whether the "e.which" property exists (for Netscape),
  and stores it in the "keycode" variable if present.
  Otherwise, it assumes the browser is Internet Explorer
  and assigns to keycode the "e.keyCode" property.
  */

function FindKeyCode(e)
{
    if(e.which)
    {
    keycode=e.which;  //Netscape
    }
    else
    {
    keycode=e.keyCode; //Internet Explorer
    }

    return keycode;
}

function FindKeyChar(e)
{
    keycode = FindKeyCode(e);
    if((keycode==8)||(keycode==127))
    {
    character="backspace"
    }
    else if((keycode==46))
    {
    character="delete"
    }
    else
    {
    character=String.fromCharCode(keycode);
    }
    return character;
}

function fnSanityCheck(getdropdown)
{
    if(vEditableOptionIndex_A>(getdropdown.options.length-1))
    {
    alert("PROGRAMMING ERROR: The value of variable vEditableOptionIndex_... cannot be greater than (length of dropdown - 1)");
    return false;
    }
}
 
  /*----------------------------------------------
  Dropdown specific global variables are:
  -----------------------------------------------
  1) vEditableOptionIndex_A   --> this needs to be set by Programmer!! See explanation.
  2) vEditableOptionText_A    --> this needs to be set by Programmer!! See explanation.
  3) vPreviousSelectIndex_A
  4) vSelectIndex_A
  5) vSelectChange_A

  --------------------------- Subrata Chakrabarty */

  /*----------------------------------------------
  The dropdown specific functions
  (which manipulate dropdown specific global variables)
  used by all dropdowns are:
  -----------------------------------------------
  1) function fnChangeHandler_A(getdropdown)
  2) function fnKeyPressHandler_A(getdropdown, e)
  3) function fnKeyUpHandler_A(getdropdown, e)

  --------------------------- Subrata Chakrabarty */

  /*------------------------------------------------
  IMPORTANT: Global Variable required to be SET by programmer
  -------------------------- Subrata Chakrabarty  */

  var vEditableOptionIndex_A = 0;

  // Give Index of Editable option in the dropdown.
  // For eg.
  // if first option is editable then vEditableOptionIndex_A = 0;
  // if second option is editable then vEditableOptionIndex_A = 1;
  // if third option is editable then vEditableOptionIndex_A = 2;
  // if last option is editable then vEditableOptionIndex_A = (length of dropdown - 1).
  // Note: the value of vEditableOptionIndex_A cannot be greater than (length of dropdown - 1)

  var vEditableOptionText_A = "--?--";

  // Give the default text of the Editable option in the dropdown.
  // For eg.
  // if the editable option is <option ...>--?--</option>,
  // then set vEditableOptionText_A = "--?--";

  /*------------------------------------------------
  Global Variables required for
  fnChangeHandler_A(), fnKeyPressHandler_A() and fnKeyUpHandler_A()
  for Editable Dropdowns
  -------------------------- Subrata Chakrabarty  */

  var vPreviousSelectIndex_A = 0;
  // Contains the Previously Selected Index, set to 0 by default

  var vSelectIndex_A = 0;
  // Contains the Currently Selected Index, set to 0 by default

  var vSelectChange_A = 'MANUAL_CLICK';
  // Indicates whether Change in dropdown selected option
  // was due to a Manual Click
  // or due to System properties of dropdown.

  // vSelectChange_A = 'MANUAL_CLICK' indicates that
  // the jump to a non-editable option in the dropdown was due
  // to a Manual click (i.e.,changed on purpose by user).

  // vSelectChange_A = 'AUTO_SYSTEM' indicates that
  // the jump to a non-editable option was due to System properties of dropdown
  // (i.e.,user did not change the option in the dropdown;
  // instead an automatic jump happened due to inbuilt
  // dropdown properties of browser on typing of a character )

  /*------------------------------------------------
  Functions required for  Editable Dropdowns
  -------------------------- Subrata Chakrabarty  */

function fnChangeHandler_A(getdropdown)
{
    fnSanityCheck(getdropdown);
    
    vPreviousSelectIndex_A = vSelectIndex_A;
    // Contains the Previously Selected Index
    
    vSelectIndex_A = getdropdown.options.selectedIndex;
    // Contains the Currently Selected Index
    
    if ((vPreviousSelectIndex_A == (vEditableOptionIndex_A)) && (vSelectIndex_A != (vEditableOptionIndex_A))&&(vSelectChange_A != 'MANUAL_CLICK'))
    // To Set value of Index variables - Subrata Chakrabarty
    {
        getdropdown[(vEditableOptionIndex_A)].selected=true;
        vPreviousSelectIndex_A = vSelectIndex_A;
        vSelectIndex_A = getdropdown.options.selectedIndex;
        vSelectChange_A = 'MANUAL_CLICK';
        // Indicates that the Change in dropdown selected
        // option was due to a Manual Click
    }
    
}

function fnKeyPressHandler_A(getdropdown, e)
{
    fnSanityCheck(getdropdown);
    
    keycode = FindKeyCode(e);
    keychar = FindKeyChar(e);
    
    // Check for allowable Characters
    // The various characters allowable for entry into Editable option..
    // may be customized by minor modifications in the code (if condition below)
    // (you need to know the keycode/ASCII value of the  character to be allowed/disallowed.
    // - Subrata Chakrabarty
    
    if ((keycode>47 && keycode<59)||(keycode>62 && keycode<127) ||(keycode==32))
    {
      var vAllowableCharacter = "yes";
    }
    else
    {
      var vAllowableCharacter = "no";
    }
    
    if(getdropdown.options.length != 0)
    // if dropdown is not empty
    if (getdropdown.options.selectedIndex == (vEditableOptionIndex_A))
    // if selected option the Editable option of the dropdown
    {
    
      var vEditString = getdropdown[vEditableOptionIndex_A].text;
    
    // make Editable option Null if it is being edited for the first time
    if((vAllowableCharacter == "yes")||(keychar=="backspace"))
    {
      if (vEditString == vEditableOptionText_A)
       vEditString = "";
    }
    if (keychar=="backspace")
      // To handle backspace - Subrata Chakrabarty
    {
      vEditString = vEditString.substring(0,vEditString.length-1);
      // Decrease length of string by one from right
      vSelectChange_A = 'MANUAL_CLICK';
      // Indicates that the Change in dropdown selected
      // option was due to a Manual Click
    }
    if (vAllowableCharacter == "yes")
    // To handle addition of a character - Subrata Chakrabarty
    {
    vEditString+=String.fromCharCode(keycode);
    // Concatenate Enter character to Editable string
    
    // The following portion handles the "automatic Jump" bug
    // The "automatic Jump" bug (Description):
    //   If a alphabet is entered (while editing)
    //   ...which is contained as a first character in one of the read-only options
    //   ..the focus automatically "jumps" to the read-only option
    //   (-- this is a common property of normal dropdowns
    //    ..but..is undesirable while editing).
    //Sunanda added code for accepting space as a character in customer name 21/01/2015 for bug fix 2512
     if (vAllowableCharacter == "yes")
        // To handle addition of a character - Subrata Chakrabarty
        {
          
           if(keycode==32)
             {
             space=space+1;
            // alert("space in space :"+space);
             }
             

        if(space>0 && keycode!=32){
        var k = vEditString;
        for(p=1;p<=space;p++)
        {
           k = k+" ";    
         //  alert("in for loop:"+p+" & K ="+k.length)
           }
         //  alert("k in for loop:"+k+"k len"+k.length);
                  vEditString=k+String.fromCharCode(keycode);
               //   alert("vEditString in if loop"+vEditString+"^ len :"+vEditString.length);
               //   alert("space 2 :"+space);
                space=0;
        }else{
        
          vEditString=vEditString+String.fromCharCode(keycode);
                         //   alert("vEditString in else loop"+vEditString);
    
          }
    var i=0;
    var vEnteredChar = String.fromCharCode(keycode);
    var vUpperCaseEnteredChar = vEnteredChar;
    var vLowerCaseEnteredChar = vEnteredChar;
    
    
    if(((keycode)>=97)&&((keycode)<=122))
    // if vEnteredChar lowercase
    vUpperCaseEnteredChar = String.fromCharCode(keycode - 32);
    // This is UpperCase
    
    
    if(((keycode)>=65)&&((keycode)<=90))
    // if vEnteredChar is UpperCase
    vLowerCaseEnteredChar = String.fromCharCode(keycode + 32);
    // This is lowercase
    
    if(e.which) //For Netscape
    {
    // Compare the typed character (into the editable option)
    // with the first character of all the other
    // options (non-editable).
    
    // To note if the jump to the non-editable option was due
    // to a Manual click (i.e.,changed on purpose by user)
    // or due to System properties of dropdown
    // (i.e.,user did not change the option in the dropdown;
    // instead an automatic jump happened due to inbuilt
    // dropdown properties of browser on typing of a character )
    
     for (i=0;i<=(getdropdown.options.length-1);i++)
     {
        if(i!=vEditableOptionIndex_A)
        {
            var vReadOnlyString = getdropdown[i].text;
            var vFirstChar = vReadOnlyString.substring(0,1);
         if((vFirstChar == vUpperCaseEnteredChar)||(vFirstChar == vLowerCaseEnteredChar))
         {
            vSelectChange_A = 'AUTO_SYSTEM';
            // Indicates that the Change in dropdown selected
            // option was due to System properties of dropdown
         break;
         }
         else
         {
            vSelectChange_A = 'MANUAL_CLICK';
            // Indicates that the Change in dropdown selected
            // option was due to a Manual Click
         }
        }
       }
      }
    }
    
    // Set the new edited string into the Editable option
    getdropdown.options[vEditableOptionIndex_A].text = vEditString;
    getdropdown.options[vEditableOptionIndex_A].value = vEditString; //Use this line only if want to change the internal value too; else this line is not required.
    
    return false;
  }
    return true;
}
}

function fnKeyUpHandler_A(getdropdown, e)
{
    fnSanityCheck(getdropdown);
    
    if(e.which) // Netscape
    {
    if(vSelectChange_A == 'AUTO_SYSTEM')
    {
        // if editable dropdown option jumped while editing
        // (due to typing of a character which is the first character of some other option)
        // then go back to the editable option.
        getdropdown[(vEditableOptionIndex_A)].selected=true;
    }
    
    var vEventKeyCode = FindKeyCode(e);
    // if [ <- ] or [ -> ] arrow keys are pressed, select the editable option
    if((vEventKeyCode == 37)||(vEventKeyCode == 39))
     {
        getdropdown[vEditableOptionIndex_A].selected=true;
     }
    }
    
}

/*-------------------------------------------------------------------------------------------- Subrata Chakrabarty */

  /*----------------------------------------------
  Dropdown specific global variables are:
  -----------------------------------------------
  1) vEditableOptionIndex_B   --> this needs to be set by Programmer!! See explanation.
  2) vEditableOptionText_B    --> this needs to be set by Programmer!! See explanation.
  3) vPreviousSelectIndex_B
  4) vSelectIndex_B
  5) vSelectChange_B

  --------------------------- Subrata Chakrabarty */

  /*----------------------------------------------
  The dropdown specific functions
  (which manipulate dropdown specific global variables)
  used by all dropdowns are:
  -----------------------------------------------
  1) function fnChangeHandler_B(getdropdown)
  2) function fnKeyPressHandler_B(getdropdown, e)
  3) function fnKeyUpHandler_B(getdropdown, e)

  --------------------------- Subrata Chakrabarty */

  /*------------------------------------------------
  IMPORTANT: Global Variable required to be SET by programmer
  -------------------------- Subrata Chakrabarty  */

  var vEditableOptionIndex_B = 0;

  // Give Index of Editable option in the dropdown.
  // For eg.
  // if first option is editable then vEditableOptionIndex_B = 0;
  // if second option is editable then vEditableOptionIndex_B = 1;
  // if third option is editable then vEditableOptionIndex_B = 2;
  // if last option is editable then vEditableOptionIndex_B = (length of dropdown - 1).
  // Note: the value of vEditableOptionIndex_B cannot be greater than (length of dropdown - 1)

  var vEditableOptionText_B = "--?--";

  // Give the default text of the Editable option in the dropdown.
  // For eg.
  // if the editable option is <option ...>--?--</option>,
  // then set vEditableOptionText_B = "--?--";

  /*------------------------------------------------
  Global Variables required for
  fnChangeHandler_B(), fnKeyPressHandler_B() and fnKeyUpHandler_B()
  for Editable Dropdowns
  -------------------------- Subrata Chakrabarty  */

  var vPreviousSelectIndex_B = 0;
  // Contains the Previously Selected Index, set to 0 by default
 var space=0;
  var vSelectIndex_B = 0;
  // Contains the Currently Selected Index, set to 0 by default

  var vSelectChange_B = 'MANUAL_CLICK';
  // Indicates whether Change in dropdown selected option
  // was due to a Manual Click
  // or due to System properties of dropdown.

  // vSelectChange_B = 'MANUAL_CLICK' indicates that
  // the jump to a non-editable option in the dropdown was due
  // to a Manual click (i.e.,changed on purpose by user).

  // vSelectChange_B = 'AUTO_SYSTEM' indicates that
  // the jump to a non-editable option was due to System properties of dropdown
  // (i.e.,user did not change the option in the dropdown;
  // instead an automatic jump happened due to inbuilt
  // dropdown properties of browser on typing of a character )

  /*------------------------------------------------
  Functions required for  Editable Dropdowns
  -------------------------- Subrata Chakrabarty  */

  function fnChangeHandler_B(getdropdown)
  {
    fnSanityCheck(getdropdown);

    vPreviousSelectIndex_B = vSelectIndex_B;
    // Contains the Previously Selected Index

    vSelectIndex_B = getdropdown.options.selectedIndex;
    // Contains the Currently Selected Index

    if ((vPreviousSelectIndex_B == (vEditableOptionIndex_B)) && (vSelectIndex_B != (vEditableOptionIndex_B))&&(vSelectChange_B != 'MANUAL_CLICK'))
    // To Set value of Index variables - Subrata Chakrabarty
    {
      getdropdown[(vEditableOptionIndex_B)].selected=true;
      vPreviousSelectIndex_B = vSelectIndex_B;
      vSelectIndex_B = getdropdown.options.selectedIndex;
      vSelectChange_B = 'MANUAL_CLICK';
      // Indicates that the Change in dropdown selected
      // option was due to a Manual Click
    }
    
    
   // getShipToAddress();
  }

  function fnKeyPressHandler_B(getdropdown, e)
  {
    //fnSanityCheck(getdropdown);
//    keycode = FindKeyCode(e);
//    keychar = FindKeyChar(e);
    keycode = e.which;//FindKeyCode(e);
    keychar = String.fromCharCode(keycode);//FindKeyChar(e);
    // Check for allowable Characters
    // The various characters allowable for entry into Editable option..
    // may be customized by minor modifications in the code (if condition below)
    // (you need to know the keycode/ASCII value of the  character to be allowed/disallowed.
    // - Subrata Chakrabarty
    if ((keycode>32 && keycode<59)||(keycode>57 && keycode<127) ||(keycode==32) || (keycode>185 && keycode<192) || (keycode>219 && keycode<222))
    {
      var vAllowableCharacter = "yes";
    }
    else
    {
      var vAllowableCharacter = "no";
    }


    if(getdropdown.options.length != 0)
    // if dropdown is not empty
      if (getdropdown.options.selectedIndex == (vEditableOptionIndex_B))
      // if selected option the Editable option of the dropdown
      {

        var vEditString = getdropdown[vEditableOptionIndex_B].text;

        // make Editable option Null if it is being edited for the first time
        if((vAllowableCharacter == "yes")||(keychar=="backspace"))
        {
          if (vEditString == vEditableOptionText_B)
            vEditString = "";
        }
        if (keychar=="backspace")
        // To handle backspace - Subrata Chakrabarty
        {
          vEditString = vEditString.substring(0,vEditString.length-1);
          // Decrease length of string by one from right

          vSelectChange_B = 'MANUAL_CLICK';
          // Indicates that the Change in dropdown selected
          // option was due to a Manual Click

        }

        if (vAllowableCharacter == "yes")
        // To handle addition of a character - Subrata Chakrabarty
        {
           if(keycode==32)
             {
             space=space+1;
            // alert("space in space :"+space);
             }
             

        if(space>0 && keycode!=32){
        var k = vEditString;
        for(p=1;p<=space;p++)
        {
           k = k+" ";    
         //  alert("in for loop:"+p+" & K ="+k.length)
           }
         //  alert("k in for loop:"+k+"k len"+k.length);
                  vEditString=k+keychar;//String.fromCharCode(keycode);
               //   alert("vEditString in if loop"+vEditString+"^ len :"+vEditString.length);
               //   alert("space 2 :"+space);
                space=0;
        }else{
        
          vEditString=vEditString+keychar;//String.fromCharCode(keycode);
                         //   alert("vEditString in else loop"+vEditString);

          }
          
          //vEditString+=String.fromCharCode(keycode);
          // Concatenate Enter character to Editable string

          // The following portion handles the "automatic Jump" bug
          // The "automatic Jump" bug (Description):
          //   If a alphabet is entered (while editing)
          //   ...which is contained as a first character in one of the read-only options
          //   ..the focus automatically "jumps" to the read-only option
          //   (-- this is a common property of normal dropdowns
          //    ..but..is undesirable while editing).

          var i=0;
          var vEnteredChar = String.fromCharCode(keycode);
          var vUpperCaseEnteredChar = vEnteredChar;
          var vLowerCaseEnteredChar = vEnteredChar;


          if(((keycode)>=97)&&((keycode)<=122))
          // if vEnteredChar lowercase
            vUpperCaseEnteredChar = String.fromCharCode(keycode - 32);
            // This is UpperCase


          if(((keycode)>=65)&&((keycode)<=90))
          // if vEnteredChar is UpperCase
            vLowerCaseEnteredChar = String.fromCharCode(keycode + 32);
            // This is lowercase
          if(e.which) //For Netscape
          {
            // Compare the typed character (into the editable option)
            // with the first character of all the other
            // options (non-editable).

            // To note if the jump to the non-editable option was due
            // to a Manual click (i.e.,changed on purpose by user)
            // or due to System properties of dropdown
            // (i.e.,user did not change the option in the dropdown;

            // instead an automatic jump happened due to inbuilt
            // dropdown properties of browser on typing of a character )
            

            for (i=0;i<=(getdropdown.options.length-1);i++)
            {
              if(i!=vEditableOptionIndex_B)
              {
                var vReadOnlyString = getdropdown[i].text;
                var vFirstChar = vReadOnlyString.substring(0,1);
                if((vFirstChar == vUpperCaseEnteredChar)||(vFirstChar == vLowerCaseEnteredChar))
                {
                  vSelectChange_B = 'AUTO_SYSTEM';
                  // Indicates that the Change in dropdown selected
                  // option was due to System properties of dropdown
                  break;
                }
                else
                {
                  vSelectChange_B = 'MANUAL_CLICK';
                  // Indicates that the Change in dropdown selected
                  // option was due to a Manual Click
                }
              }
            }
          }
        }
        // Set the new edited string into the Editable option
        getdropdown.options[vEditableOptionIndex_B].text = vEditString;
        getdropdown.options[vEditableOptionIndex_B].value = vEditString; //Use this line only if want to change the internal value too; else this line is not required.

        return false;
      }
   
    return true;
  }

  function fnKeyUpHandler_B(getdropdown, e)
  {
    fnSanityCheck(getdropdown);

    if(e.which) // Netscape
    {
      if(vSelectChange_B == 'AUTO_SYSTEM')
      {
        // if editable dropdown option jumped while editing
        // (due to typing of a character which is the first character of some other option)
        // then go back to the editable option.
        getdropdown[(vEditableOptionIndex_B)].selected=true;
      }

      var vEventKeyCode = FindKeyCode(e);
      // if [ <- ] or [ -> ] arrow keys are pressed, select the editable option
      if((vEventKeyCode == 37)||(vEventKeyCode == 39))
      {
        getdropdown[vEditableOptionIndex_B].selected=true;
      }
    }
    
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
	   if(c == "!"||c == "#"||c == "$"||c == "%"||c == "&"||c == '*'||c == '('||c == ')'||c == "+" ||c == "_" ||c == "-"||c == "="||c == '"'||c == ";"||c == "'"||c == '<'||c == '>'||c == ','||c == '.'||c == '/' )  
	   {
		 return true;
	   }
   }  
   return false;
}
