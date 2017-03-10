/*==========================================================================+
|  DESCRIPTION                                                              |
|    javascript file for the aascFreightShopPopUp.jsp                       |
|    Author: Suman G                                                        |
|    Date :  18/02/2015                                                     |

    03/03/2015      Suman G     Added code for showing selected rates in text field.  |
    11/03/2015      Suman G     Added code for sending multiple packages in the request.     
    13/03/2015      Suman G     Modified code for Ajay Demo.
    18/03/2015      Suman G     Modified code for Ajay's suggestions.
    24/03/2015      Suman G     Reverted the changes made for Ajay's Demo.
    15/04/2015      Suman G     Added code to fix #2838 
    30/04/2015      Y Pradeep   Added code to call setCarrierPayMethodHidden() function in shipping page when a radio button is selected. Bug #2889.
    05/11/2015      Suman G     Added code for DHL and Stamps FS
+===========================================================================*/

function onLoad(){



    var freightShopOrGetRates = window.opener.document.getElementById('freightShopDecisionId').value;
    document.getElementById('decisionId').value = freightShopOrGetRates;
    
    var orderNumber = window.opener.document.getElementById('orderNumberID').value
    document.getElementById('orderNumberId').value = orderNumber;
//    alert('order nujmebr::::'+orderNumber);
    var fromAdd1 = window.opener.document.getElementById('shipFromAddressLine1').value;
    var fromAdd2 = window.opener.document.getElementById('shipFromAddressLine2').value;
    var fromCity = window.opener.document.getElementById('shipFromCity').value;
    var fromState = window.opener.document.getElementById('shipFromState').value;
    var fromZipCode = window.opener.document.getElementById('shipFromPostalCode').value;
    var fromCountry = window.opener.document.getElementById('shipFromCountryId').value;

    document.getElementById('fromAdressLine1Id').value = fromAdd1;
    document.getElementById('fromAdressLine2Id').value = fromAdd2;
    document.getElementById('fromCityId').value = fromCity;
    document.getElementById('fromStateId').value = fromState;
    document.getElementById('fromZipCodeId').value = fromZipCode;
    document.getElementById('fromCountryCodeId').value = fromCountry;
    
    var toAdd1 = window.opener.document.getElementById('shipToAddressId').value;
    var toAdd2 = window.opener.document.getElementById('shipToAddrLine2Id').value;
    var toCity = window.opener.document.getElementById('city').value;
    var toState = window.opener.document.getElementById('state').value;
    var toZipCode = window.opener.document.getElementById('zip').value;
    var toCountry = window.opener.document.getElementById('country').value;
    
    document.getElementById('toAdressLine1Id').value = toAdd1;
    document.getElementById('toAdressLine2Id').value = toAdd2;
    document.getElementById('toCityId').value = toCity;
    document.getElementById('toStateId').value = toState;
    document.getElementById('toZipCodeId').value = toZipCode;
    document.getElementById('toCountryCodeId').value = toCountry;
    
    document.getElementById('shipmentDateId').value = window.opener.document.getElementById('shipmentDate').value;
    var count = window.opener.document.getElementById('countPacketsID').value;
    document.getElementById('countPacksId').value = count;
    
    var declaredValue = 0;
    
    for(var s=1; s<=count ; s++){
          
          var x1=document.getElementById('hiddenTable').rows.length;
          var x=document.getElementById('hiddenTable').insertRow(x1);
          var y1=x.insertCell(0);
          y1.innerHTML = 
                                            '<input type="hidden" name="dimUOM'+s+'" id="dimUOMId'+s+'" />'+
                                            '<input type="hidden" name="dimValueFreight'+s+'" id="dimValueId'+s+'" />'+
                                            '<input type="hidden" name="weightUOM'+s+'" id="weightUOMId'+s+'" />'+
                                            '<input type="hidden" name="weightValue'+s+'" id="weightValueId'+s+'" />';
    
    }
    
    
    for(var i=1;i<=count;i++){
    
        var dimUom;
        dimUom  = window.opener.document.getElementById('dimensionValueHiddenID'+i).value; // It works only ajax call happend. Need to work on this.
        
        var lastIndex = dimUom.lastIndexOf('*');
        dimUom = dimUom.substring(0,lastIndex);
        lastIndex = dimUom.lastIndexOf('*');
        var dimValue = dimUom.substring(0,lastIndex);
        dimUom = dimUom.substring(lastIndex+1);
        
        if(dimUom == ''){
        
            dimUom = window.opener.document.getElementById('dimensionNameID'+i).options[window.opener.document.getElementById('dimensionNameID'+i).selectedIndex].value;
            lastIndex = dimUom.lastIndexOf('*');
            dimUom = dimUom.substring(0,lastIndex);
            lastIndex = dimUom.lastIndexOf('*');
            dimValue = dimUom.substring(0,lastIndex);
            dimUom = dimUom.substring(lastIndex+1);
//            alert('uom...in if:::'+dimUom);
        }
        dimValue = dimValue.replace('*','X');
        dimValue = dimValue.replace('*','X');
        document.getElementById('dimUOMId'+i).value = dimUom;
        document.getElementById('dimValueId'+i).value = dimValue;
        document.getElementById('weightUOMId'+i).value = window.opener.document.getElementById('uomID'+i).value;
        document.getElementById('weightValueId'+i).value = window.opener.document.getElementById('weightID'+i).value;
        
        declaredValue = declaredValue + parseInt(window.opener.document.getElementById('packageDeclaredValueID'+i).value);
        //alert('declared value:::::'+declaredValue);
    }
    if(declaredValue != 0){
        document.getElementById('isDutiableId').value = 'Y';
        document.getElementById('dhlDeclaredCurrencyId').value = 'USD';
        document.getElementById('dhlDeclaredValueId').value = declaredValue;
    }
    else
        document.getElementById('isDutiableId').value = 'N';

            
}
function validate(){

    var UPS    = document.getElementById('UPSCheckBoxId').checked;
    var FedEx  = document.getElementById('FedExCheckBoxId').checked;
//    var TNT    = document.getElementById('TNTCheckBoxId').checked;
    var DHL    = document.getElementById('DHLCheckBoxId').checked;
//    var Echo   = document.getElementById('EchoCheckBoxId').checked;
    var Stamps = document.getElementById('StampsCheckBoxId').checked;
//    var USPS   = document.getElementById('USPSCheckBoxId').checked;

    if((UPS == false) && (FedEx == false) && (DHL == false) && (Stamps == false)) // && (TNT == false) && (Echo == false)  && (USPS == false))
    {
        alert('Please select atleast one carrier');
        return false;
    }
    
    if(document.getElementById('submitButtonId').value == "0"){

        document.getElementById('submitButtonId').value="1";
    
   }
    else{
   
        alert("Request already submitted. Please Wait.");
        return false; 
   }
   
    document.getElementById('freightShopFormId').submit();
    document.getElementById("loading").style.display = "block";    
}

function updateShipMethod(index)
{
    
    var shipMethodListSize = window.opener.document.getElementById('shipMethodId').options.length;
    var i=1;
    
    window.opener.document.getElementById('ratesFromFreightShopId').value = document.getElementById('shipmentRateHiddenId'+index).value;
    while(i<shipMethodListSize){
        var shipMethod = window.opener.document.getElementById("shipMethodId").options[i].text;
//        alert(shipMethod);
        if(shipMethod == document.getElementById('shipMethodHiddenId'+index).value )
        {
            window.opener.document.getElementById("shipMethodId").options[i].selected = true;
//            alert('hi');
        }
        i++;
    }
    
    window.opener.setCarrierPayMethodHidden();
    
    window.opener.FillCarrier();
    
    window.opener.chgShipMethod();
             
    window.opener.getCcCsl('2');
    
        
    window.close();
}
