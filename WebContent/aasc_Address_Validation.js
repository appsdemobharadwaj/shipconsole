/*==========================================================================+
|  DESCRIPTION                                                              |
|    javascript file for the aascAddressValidation.jsp                      |
|    Author     :   Y Pradeep                                               |
|    Created on :   25/02/2015                                              |
+===========================================================================*/
/*========================================================================================
Date        Resource       Change history
------------------------------------------------------------------------------------------
26/02/2015  Y Pradeep       Added this file for Address Validation.
13/03/2015  Y Pradeep       Added code to check Residential checkbox in Shipping page if Ship To Address is Residential.
16/03/2015  Y Pradeep       Modified code to display proper message.
17/03/2014  Y Pradeep       Modified code to set Residential Flag check in Shipping page if address is residential and valid address. Bug #2862.
02/03/2016  Suman G         Changed alert to fix an issue.   
========================================================================================*/

function displayAlert(){
    
    var descriptionStatus = document.getElementById("descriptionStatusHiddenId").value;
    var addressClassification = document.getElementById("addressClassificationId").value;
    var addressType = document.getElementById("addressTypeId").value;
    var residentialFlagId = window.opener.document.getElementById("residentialFlagId");
    if(addressClassification == "Residential" || addressClassification == "RESIDENTIAL"){
        var residentialFlagId = window.opener.document.getElementById("residentialFlagId");
        residentialFlagId.value = "Y";
        residentialFlagId.checked = true;
    }
//    alert('descriptionStatus::::'+descriptionStatus);
    if((descriptionStatus != "SUCCESS") && descriptionStatus != '' && addressClassification == "" && addressType == ""){
        alert(descriptionStatus);
//        var residentialFlagId = window.opener.document.getElementById("residentialFlagId");
//        residentialFlagId.value = "N";
//        residentialFlagId.checked = false;
        window.close();
    } else if((descriptionStatus == "SUCCESS") && (addressType == "INSUFFICIENT_DATA" || addressType == "POSTAL_CODE_NOT_FOUND") && addressClassification == "UNDETERMINED"){
        alert("Address provided is insufficient.");
        window.close();
    } 
    else if ( descriptionStatus == ''){
        alert('Please Contact ShipConsole Support Team...');
        window.close();
        }
}

function getDetails(){
    var selectedIndex = 0;
    var addressLength = document.getElementsByName('selectedResponseAddress');
    for(i = 0; i<addressLength.length; i++){
        if(addressLength[i].checked){
           selectedIndex = i+1;
           break;
        }
    }
//    alert("selectedIndex == "+selectedIndex); 
    
    window.opener.document.DynaShipmentShipSaveForm.shipToAddress.value = document.aascAddressValidationForm['responseAddress'+selectedIndex].value;
    window.opener.document.DynaShipmentShipSaveForm.shipToAddrLine2.value = "";
    window.opener.document.DynaShipmentShipSaveForm.city.value = document.aascAddressValidationForm['responseCity'+selectedIndex].value;
    window.opener.document.DynaShipmentShipSaveForm.state.value = document.aascAddressValidationForm['responseState'+selectedIndex].value;
    window.opener.document.DynaShipmentShipSaveForm.postalCode.value = document.aascAddressValidationForm['responsePostalCode'+selectedIndex].value;
    window.opener.document.DynaShipmentShipSaveForm.countrySymbol.value = document.aascAddressValidationForm['responseCountryCode'+selectedIndex].value;
    
    var addressClassification = document.getElementById("addressClassificationId").value;
    if(addressClassification == "Residential" || addressClassification == "RESIDENTIAL"){
        var residentialFlagId = window.opener.document.getElementById("residentialFlagId");
        residentialFlagId.value = "Y";
        residentialFlagId.checked = true;
    }
    else{
        var residentialFlagId = window.opener.document.getElementById("residentialFlagId");
        residentialFlagId.value = "N";
        residentialFlagId.checked = false;
    }
    window.close();
}
