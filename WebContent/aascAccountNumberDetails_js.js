/*========================================================================================

Date        Resource       Change history
------------------------------------------------------------------------------------------
07/04/2015  Y Pradeep      Uncommented validation for accessLicenseNumber field. Bug #2798.
10/04/2015  Y Pradeep      Added stopEnterKeyPress function to avoid form submition when enter button is pressed any field is focused in jsp page.
14/04/2015  Y Pradeep      Added stopEnterKeyPress function code to disable form submition for text, radio and checkbox fields.
======================================================================================================================================*/

function checkData()
     {
       var accountNumberVal= document.AccountNumberDetailsForm.accountNumber.value;
       var carrierCodeVal= document.AccountNumberDetailsForm.carrierCodeHidden.value;
       //alert("carrierCodeVal : "+carrierCodeVal);
       if(accountNumberVal == "")
       {
         alert("Please Enter Account Number");
         document.AccountNumberDetailsForm.accountNumber.focus();
         return false;
       }
              
       if(carrierCodeVal == 110 || carrierCodeVal == 111)
       {
         var meterNumberVal = document.AccountNumberDetailsForm.meterNumber.value;            
         //alert("meterNumberVal : "+meterNumberVal);
         if(meterNumberVal == "")
         {          
           alert("Please Enter Meter Number");
           document.AccountNumberDetailsForm.meterNumber.focus();
           return false;
         }  
       }       
       else if(carrierCodeVal == 100)
       {
       
         var accessLicenseNumberVal = document.AccountNumberDetailsForm.accessLicenseNumber.value;
       
         //alert("accessLicenseNumberVal : "+accessLicenseNumberVal);         
         if(accessLicenseNumberVal == "")
         {
           alert("Please Enter Access License Number");
           document.AccountNumberDetailsForm.accessLicenseNumber.focus();
           return false;
         }
         
       }
//       if(carrierCodeVal == 110){
//           return validateFiles(); 
//       }
       return true;
     }
        
     function loadFunction()
     {
       var carrierCode = document.AccountNumberDetailsForm.carrierCode.value;  
       //alert('Carrier Code : '+carrierCode);  
       document.AccountNumberDetailsForm.carrierCodeHidden.value = document.AccountNumberDetailsForm.carrierCode.value;
       var validateKey = document.AccountNumberDetailsForm.validateKey.value;   //AccountNumberDetailsForm
       //  var locaID = window.opener.document.aascCarrierConfigurationForm['locationId'].value;
   //alert("locaID ::"+locaID);
  //document.getElementById('locationID').value= locaID;
       /*if(validateKey == 'aasc709')
       {
         alert("Account Number already exist. Please Enter Unique value for Account Number");
         return false;
       }     */    
       
       
       return true;                        
     }
     
     function setNegotiatedFlagValue()
     {
       //var negotiatedFlagStatus = document.AccountNumberDetailsForm['accountNumberNegotiatedFlag'].checked ;
       var negotiatedFlagStatus = document.AccountNumberDetailsForm.accountNumberNegotiatedFlag.checked ;
       //alert('negotiatedFlagStatus : '+negotiatedFlagStatus);
       if(negotiatedFlagStatus == true)
       {
         //alert("Inside if");
         document.AccountNumberDetailsForm.accountNumberNegotiatedFlag.value = "Y" ;
         document.AccountNumberDetailsForm.accountNumberActive.value = "Y" ;
         document.AccountNumberDetailsForm.negotiatedRates.value = "N" ;
       }
       else
       {
         //alert("Inside else");
         document.AccountNumberDetailsForm.accountNumberNegotiatedFlag.value = "N" ;
         document.AccountNumberDetailsForm.negotiatedRates.value = "N" ;
       }
     }
     function setAccountNumberRegistrationValue()
     {
       var accountNumberRegistrationFlag = document.AccountNumberDetailsForm.accountNumberRegistrationFlag.checked ;
       //alert('accountNumberRegistrationFlag : '+accountNumberRegistrationFlag);
       if(accountNumberRegistrationFlag == true)
       {
         //alert("Inside if");
         document.AccountNumberDetailsForm.accountNumberRegistrationFlag.value = "Yes" ;
         document.AccountNumberDetailsForm.registrationStatus.value = "Yes" ;
         /*document.AccountNumberDetailsForm.accountNumberActive.value = "Y" ;
         document.AccountNumberDetailsForm.negotiatedRates.value = "N" ;*/
       }
       else
       {
         //alert("Inside else");
         document.AccountNumberDetailsForm.accountNumberRegistrationFlag.value = "No" ;
         document.AccountNumberDetailsForm.registrationStatus.value = "No" ;
         //document.AccountNumberDetailsForm.negotiatedRates.value = "N" ;
       }
       return true;
     }
     
     function setNegotiatedRatesValue()
     {
       var negotiatedFlagStatus = document.AccountNumberDetailsForm.accountNumberNegotiatedFlag.checked ;
       if(negotiatedFlagStatus == false)
       {
         alert("Please Select Negotiated Flag");
         return false;
       }  
       var negotiatedRatesStatus = document.AccountNumberDetailsForm.negotiatedRates.checked ;
       
       //alert('negotiatedRatesStatus : '+negotiatedRatesStatus   );
       if(negotiatedRatesStatus == true)
       {
         //alert("Inside if");
         document.AccountNumberDetailsForm.negotiatedRates.value = "Y" ;
       }
       else
       {
         //alert("Inside else");
         document.AccountNumberDetailsForm.negotiatedRates.value = "N" ;
       }
       return true;
     }
     
     function onUpdate()
     {
//       alert("Inside onUpdate()");
       var negotiatedFlagStatus = document.AccountNumberDetailsForm.accountNumberNegotiatedFlag.checked ;
       //alert("negotiatedFlagStatus : "+negotiatedFlagStatus);
       if(negotiatedFlagStatus == true)
       {
         document.AccountNumberDetailsForm.accountNumberNegotiatedFlag.value = "Y";
         //alert("Inside if");
       }  
       else
       {
         document.AccountNumberDetailsForm.accountNumberNegotiatedFlag.value = "N" ;
         //alert("Inside else");
       }
       
       var negotiatedRatesStatus = document.AccountNumberDetailsForm.negotiatedRates.checked ;
       //alert("negotiatedRatesStatus : "+negotiatedRatesStatus);
       if(negotiatedRatesStatus == true)
       {
         document.AccountNumberDetailsForm.negotiatedRates.value = "Y";
         //alert("Inside if");
       }  
       else
       {
         document.AccountNumberDetailsForm.negotiatedRates.value = "N" ;
         //alert("Inside else");
       }
       //alert("accountNumberNegotiatedFlag : "+document.AccountNumberDetailsForm.accountNumberNegotiatedFlag.value);
       //alert("negotiatedRates : "+document.AccountNumberDetailsForm.negotiatedRates.value);
       
     }
     
     function validateFiles() {
        var sigImageControl = document.getElementById('signatureImagePath').value;
        var logoImageControl = document.getElementById('logoImagePath').value;
        
        
        //Regular Expression for fileupload control.
        var reg = /^(.jpeg|.jpg|.gif|.png)$/i;
        if (sigImageControl.length > 0)
        {
            var sigImageLastIndex=sigImageControl.lastIndexOf(".");
            var sigImageSubstring = sigImageControl.substring(sigImageLastIndex,sigImageControl.length);
            //Checks with the control value.
            if (!reg.test(sigImageSubstring))
            {   
                //If the condition not satisfied shows error message.
                alert("Only image files are allowed!");
                return false;
            }
        }
        
        if (logoImageControl.length > 0)
        {
            var logoImageLastIndex=logoImageControl.lastIndexOf(".");
            var logoImageSubstring = logoImageControl.substring(logoImageLastIndex,logoImageControl.length);
            //Checks with the control value.
            if (!reg.test(logoImageSubstring))
            {
                //If the condition not satisfied shows error message.
                alert("Only image files are allowed!");
                return false;
            }
        }
        
    } //End of function validate.
     
//This funcion "stopEnterKeyPress(evt)"added by Khaja for Browser Campatability
function stopEnterKeyPress(evt) {
  var evt = (evt) ? evt : ((event) ? event : null);
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
 
  if ((evt.keyCode == 13) && ((node.type=="text") || (node.type=="checkbox") || (node.type=="radio")))  {return false;}

}
document.onkeypress = stopEnterKeyPress;   ///This line belong to the  above function stopEnterKeyPress(evt) to stop Form submition on press of Enter key
