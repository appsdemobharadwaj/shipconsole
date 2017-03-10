
/*==================================================================================================+
|  DESCRIPTION                                                                                      |
|    javascript file for the aascCarrierConfigurationSettings.jsp validation                        |
|    Author Y Pradeep                                                                               |
|    Version   1.1                                                                                  |
|    Creation 10/12/2014                                                                            |
+===========================================================================*/
/*========================================================================================
Date        Resource       Change history
------------------------------------------------------------------------------------------
10/12/2014  Y Pradeep         Added disableLocation(), getLocationList(), onCarrierClientChange() and setClientIdVal() methods required for Ship Console Lite carrier configuration.
18/12/2014  Y Pradeep         Setting client id in LoadLabels() method.
07/01/2014  Y Pradeep         Renamed car to carrierCode in disabledAdminField() function.
16/02/2015  Suman G           Commented code related to Support hazmat shipping label to fix #2610.
10/03/2015  Y Pradeep         Removed old history and mdified authod and creation details.
19/03/2015  Y Pradeep         Added if condition for selecting carrier mode while saving carrier details.
31/03/2015  Y Pradeep         Modified width of Account Number pop to 750 to display all fiels properly. Bug #2739.
08/04/2015  Y Pradeep         Commented code to display Location LOV enabled for all roles except role 5. Bug #2807.
14/04/2015  Y Pradeep         Added stopEnterKeyPress function code to disable form submition for text, radio and checkbox fields.
20/07/2015  Suman G           Added code for Email Notification.
04/08/2015  Suman G           Modified code for issue #3294
10/08/2015  Dinakar G           Modified code for issue #3361
01/11/2015  Mahesh V    Added code for Stamps.com new fields implemented for Stamps.com Integration
04/11/2015  Mahesh v        Added code for bugs for Stamps.com
17/11/2015  Shiva G         Added code for DHL Region code
30/11/2015  Y Pradeep       Added trim to all text fields in Carrier Configuration page. Bug #4040.
========================================================================================*/


    
function disabledAdminField(){

    if(document.aascCarrierConfigurationForm.roleIdHidden.value!=3 && document.aascCarrierConfigurationForm.roleIdHidden.value!=1)
    {
      document.aascCarrierConfigurationForm.protocol.disabled=true;
    //  document.aascCarrierConfigurationForm.hubId.disabled=true;
      document.aascCarrierConfigurationForm.port.disabled=true;
      document.aascCarrierConfigurationForm.serverIpAddress.disabled=true;
      document.aascCarrierConfigurationForm.userName.disabled=true;
      document.aascCarrierConfigurationForm.password.disabled=true;
      document.aascCarrierConfigurationForm.prefix.disabled=true;
      document.aascCarrierConfigurationForm.domain.disabled=true;
      document.aascCarrierConfigurationForm.integrationId.disabled=true;
      //  document.aascCarrierConfigurationForm.accessLicenseNumber.disabled=true;
      //  document.aascCarrierConfigurationForm.meterNumber.disabled=true;
      document.aascCarrierConfigurationForm.pdPort.disabled=true;
      
      document.aascCarrierConfigurationForm.Op900LabelFormat.disabled=true;
      
      //Mahesh
      document.aascCarrierConfigurationForm.paperSize.disabled=true;
      document.aascCarrierConfigurationForm.intlPrintLayout.disabled=true;
      document.aascCarrierConfigurationForm.nonDelivery.disabled=true;
      
//      document.aascCarrierConfigurationForm.supportHazmatShipping.disabled=true;

     // document.aascCarrierConfigurationForm.IntlDocSubType.disabled=true;   

      //SC_7.0_RS
    //  document.aascCarrierConfigurationForm.eodLabelFormats.disabled=true;
      document.aascCarrierConfigurationForm.labelStock.disabled=true;
      document.aascCarrierConfigurationForm.docTab.disabled=true;
     // document.aascCarrierConfigurationForm.modelSymbol.disabled=true;
     // document.aascCarrierConfigurationForm.intlLabelFormat.disabled=true;
      //SC_7.0_RS
//      document.aascCarrierConfigurationForm.eodPrinterSymbol.disabled=true;
//      document.aascCarrierConfigurationForm.eodStockSymbol.disabled=true;
//      document.aascCarrierConfigurationForm.eodDOCPort.disabled=true;
//      //SC_7.0_RS
//      document.aascCarrierConfigurationForm.stockSymbol.disabled=true;
//      document.aascCarrierConfigurationForm.eodPort.disabled=true;
//      document.aascCarrierConfigurationForm.accountnumberdff.disabled=true;
      //   document.aascCarrierConfigurationForm.nonDiscountedCost.disabled=true;
      //   document.aascCarrierConfigurationForm.acctNumNegotiatedFlag.disabled=true;
      document.aascCarrierConfigurationForm.emailNotificationFlag.disabled=true;
//      document.aascCarrierConfigurationForm.SenderEmail.disabled = true;
//      document.aascCarrierConfigurationForm.recepientEmailAddress1.disabled = true;
//      document.aascCarrierConfigurationForm.recepientEmailAddress2.disabled = true;
//      document.aascCarrierConfigurationForm.recepientEmailAddress3.disabled = true;
//      document.aascCarrierConfigurationForm.recepientEmailAddress4.disabled = true;
//      document.aascCarrierConfigurationForm.recepientEmailAddress5.disabled = true;
     
      document.aascCarrierConfigurationForm.IsReference1Required.disabled = true;
      document.aascCarrierConfigurationForm.IsReference2Required.disabled = true;
      //SC_EMail_SJ
//      document.aascCarrierConfigurationForm.CarrierSalesOrderNumber.disabled = true;
      document.aascCarrierConfigurationForm.CarrierCustomerName.disabled = true;
//      document.aascCarrierConfigurationForm.deliveryItemNumbers.disabled = true;
        

      document.aascCarrierConfigurationForm.ShipNotification.disabled = true;
      document.aascCarrierConfigurationForm.ExceptionNotification.disabled = true;
      document.aascCarrierConfigurationForm.DeliveryNotification.disabled = true;
   //   alert(".Format[0] ::"+document.aascCarrierConfigurationForm.Format[0].value);
      document.aascCarrierConfigurationForm.Format[0].disabled = true;
      document.aascCarrierConfigurationForm.Format[1].disabled = true;
      document.aascCarrierConfigurationForm.Format[2].disabled = true;
      document.aascCarrierConfigurationForm.Mode.disabled = true;
      document.aascCarrierConfigurationForm.enableFlag.disabled = true;
      document.aascCarrierConfigurationForm.carrierCodeValue.disabled = true;
    }
    else{
       if(document.aascCarrierConfigurationForm.roleIdHidden.value==3 || document.aascCarrierConfigurationForm.roleIdHidden.value==1)
       {
          document.aascCarrierConfigurationForm.protocol.disabled=false;
      //    document.aascCarrierConfigurationForm.hubId.disabled=false;
          document.aascCarrierConfigurationForm.port.disabled=false;
          document.aascCarrierConfigurationForm.serverIpAddress.disabled=false;
          document.aascCarrierConfigurationForm.userName.disabled=false;
          document.aascCarrierConfigurationForm.password.disabled=false;
          document.aascCarrierConfigurationForm.prefix.disabled=false;
          document.aascCarrierConfigurationForm.domain.disabled=false;
          document.aascCarrierConfigurationForm.integrationId.disabled=false;
	  //  document.aascCarrierConfigurationForm.accessLicenseNumber.disabled=false;
          //  document.aascCarrierConfigurationForm.meterNumber.disabled=false;
        
          document.aascCarrierConfigurationForm.Op900LabelFormat.disabled=false;
        
//          document.aascCarrierConfigurationForm.supportHazmatShipping.disabled=false;

      //    document.aascCarrierConfigurationForm.IntlDocSubType.disabled=false;  
          document.aascCarrierConfigurationForm.pdPort.disabled=false;
          //SC_7.0_RS
       //   document.aascCarrierConfigurationForm.eodLabelFormats.disabled=false;
      //    document.aascCarrierConfigurationForm.intlLabelFormat.disabled=false;
          document.aascCarrierConfigurationForm.labelStock.disabled=false;
          document.aascCarrierConfigurationForm.docTab.disabled=false;
          
          //Mahesh
          
                document.aascCarrierConfigurationForm.paperSize.disabled=false;
                document.aascCarrierConfigurationForm.intlPrintLayout.disabled=false;
                document.aascCarrierConfigurationForm.nonDelivery.disabled=false;
       //   document.aascCarrierConfigurationForm.modelSymbol.disabled=false;
          //SC_7.0_RS
//          document.aascCarrierConfigurationForm.eodPrinterSymbol.disabled=false;
//          document.aascCarrierConfigurationForm.eodStockSymbol.disabled=false;
//          document.aascCarrierConfigurationForm.eodDOCPort.disabled=false;

//          document.aascCarrierConfigurationForm.stockSymbol.disabled=false;
//          document.aascCarrierConfigurationForm.eodPort.disabled=false;
//          //    document.aascCarrierConfigurationForm.nonDiscountedCost.disabled=false;
          //     document.aascCarrierConfigurationForm.acctNumNegotiatedFlag.disabled=false;
          document.aascCarrierConfigurationForm.Mode.disabled = false;
          document.aascCarrierConfigurationForm.enableFlag.disabled = false;
          document.aascCarrierConfigurationForm.carrierCodeValue.disabled = false;

       }
    }
    var carrierCode=document.aascCarrierConfigurationForm.carrierCodeValue.options[document.aascCarrierConfigurationForm.carrierCodeValue.selectedIndex].value; 
//    if(car=='111')
//    {
//        document.aascCarrierConfigurationForm.hubId.style.display = "";
//        document.getElementById("smartPostLabel").style.display = "";
//    }
//    else{
//        document.aascCarrierConfigurationForm.hubId.style.display = "none";
//        document.getElementById("smartPostLabel").style.display = "none";
//    }
//   
    if(carrierCode=='111')
    {
        document.aascCarrierConfigurationForm.Op900LabelFormat.style.display = "";
        document.getElementById("hazmatOp900Label").style.display = "";
    }
    else{
         document.aascCarrierConfigurationForm.Op900LabelFormat.style.display = "none";
         document.getElementById("hazmatOp900Label").style.display = "none";
    }
 //Shiva added below code for DHL
 if(carrierCode=='114')
    {
        document.getElementById('IntegrationIdDiv').style.display = "none";
        document.getElementById("DHLRegionCodeDiv").style.display = "";
    }
    else{
        document.getElementById('IntegrationIdDiv').style.display = "";
        document.getElementById("DHLRegionCodeDiv").style.display = "none";
    }
  //Shiva code end  
 
//    if(car=='110')  //added by Jagadish
//   {
//   document.aascCarrierConfigurationForm.IntlDocSubType.style.display = "";
//   document.getElementById("intlDocSubTypeLabel").style.display = "";
//   }
//   else{
//   document.aascCarrierConfigurationForm.IntlDocSubType.style.display = "none";
//   document.getElementById("intlDocSubTypeLabel").style.display = "none";
//   }
    //alert('car : '+car);
//    if(car == '100' || car == '110' || car == '111' || car == '')
//    {
//         document.aascCarrierConfigurationForm.supportHazmatShipping.style.display = "none";
//         document.getElementById("supportHazmatShippingLable").style.display = "none";
//         document.aascCarrierConfigurationForm.supportHazmatShipping.options[document.aascCarrierConfigurationForm.supportHazmatShipping.selectedIndex].value = "" ; 
//    }
//    else
  //  {
//         document.aascCarrierConfigurationForm.supportHazmatShipping.style.display = "";
//         document.getElementById("supportHazmatShippingLable").style.display = "";
         //var carrierName = document.aascCarrierConfigurationForm.carrierName.options[document.aascCarrierConfigurationForm.carrierName.selectedIndex].text; 
         /* if(car == '999')// && carrierName.toUpperCase() == 'DHL')
         {
           document.aascCarrierConfigurationForm.supportHazmatShipping.disabled = false;  
         }
         else
         {
         document.aascCarrierConfigurationForm.supportHazmatShipping.disabled = true;
         }*/
//         alert("666");
//         document.aascCarrierConfigurationForm.supportHazmatShipping.disabled = false;  
  //  }
}

//function disabled()
//{
//    if(document.aascCarrierConfigurationForm.carrierN.value=="")
//    {
//      document.aascCarrierConfigurationForm.protocol.disabled=true;
//      document.aascCarrierConfigurationForm.hubId.disabled=true;
//
//      document.aascCarrierConfigurationForm.port.disabled=true;
//      document.aascCarrierConfigurationForm.serverIpAddress.disabled=true;
//      document.aascCarrierConfigurationForm.userName.disabled=true;
//      document.aascCarrierConfigurationForm.password.disabled=true;
//      document.aascCarrierConfigurationForm.prefix.disabled=true;
//      document.aascCarrierConfigurationForm.domain.disabled=true;
//      //  document.aascCarrierConfigurationForm.accountNo.disabled=true;
//      document.aascCarrierConfigurationForm.pdPort.disabled=true;
//      
//      document.aascCarrierConfigurationForm.Op900LabelFormat.disabled=true;
//      document.aascCarrierConfigurationForm.supportHazmatShipping.disabled=true;
//      document.aascCarrierConfigurationForm.IntlDocSubType.disabled=true;
//      
//      //SC_7.0_RS
//      document.aascCarrierConfigurationForm.eodLabelFormats.disabled=true;
//      document.aascCarrierConfigurationForm.intlLabelFormat.disabled=true;
//      document.aascCarrierConfigurationForm.labelStock.disabled=true;
//      document.aascCarrierConfigurationForm.docTab.disabled=true;
//      document.aascCarrierConfigurationForm.modelSymbol.disabled=true;
//      //SC_7.0_RS
//      document.aascCarrierConfigurationForm.eodPrinterSymbol.disabled=true;
//      document.aascCarrierConfigurationForm.eodStockSymbol.disabled=true;
//      document.aascCarrierConfigurationForm.eodDOCPort.disabled=true;
//      document.aascCarrierConfigurationForm.stockSymbol.disabled=true;
//      document.aascCarrierConfigurationForm.eodPort.disabled=true;
//      document.aascCarrierConfigurationForm.Mode.disabled = true;
//      document.aascCarrierConfigurationForm.enableFlag.disabled = true;
//      document.aascCarrierConfigurationForm.carrierCodeValue.disabled = true;
//    }
//}

function disableLocation()
{
    var accessValue=document.aascCarrierConfigurationForm.roleIdHidden.value;
    if(accessValue == 4)
    {
        //document.aascCarrierConfigurationForm.locationId.disabled = true;
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
function portValid()
{
  if(isInteger(document.aascCarrierConfigurationForm.port.value)==false)
  {
    alert("Enter Numeric Value For Port");
    document.aascCarrierConfigurationForm.port.focus();
    return false;
  }
  return true;
}





function isValidEmail(mailFrom, required) {

    if (required==undefined) {   // if not specified, assume it's required
        required=true;
    }
    if (mailFrom==null) {
        if (required) {
            return false;
        }
        return true;
    }
    if (mailFrom.length==0) {
        if (required) {
            return false;
        }
        return true;
    }
    if (! allValidChars(mailFrom)) {  // check to make sure all characters are valid
        return false;
    }
    if (mailFrom.indexOf("@") < 1) { //  must contain @, and it must not be the first character
        return false;
    } else if (mailFrom.lastIndexOf(".") <= mailFrom.indexOf("@")) {  // last dot must be after the @
        return false;
    } else if (mailFrom.indexOf("@") == mailFrom.length) {  // @ must not be the last character
        return false;
    } else if (mailFrom.indexOf("..") >=0) { // two periods in a row is not valid
	return false;
    } else if (mailFrom.indexOf(".") == mailFrom.length) {  // . must not be the last character
	return false;
    }
    return true;
}

function allValidChars(mailFrom) {

  var parsed = true;

  var validchars = "abcdefghijklmnopqrstuvwxyz0123456789@.-_";

  for (var i=0; i < mailFrom.length; i++) {

    var letter = mailFrom.charAt(i).toLowerCase();

    if (validchars.indexOf(letter) != -1)

      continue;

    parsed = false;

    break;

  }

  return parsed;

}



function onCarrierCarrierChange()
{
      document.aascCarrierConfigurationForm.protocol.value='';
      document.aascCarrierConfigurationForm.port.value='0';
      document.aascCarrierConfigurationForm.serverIpAddress.value='';
      document.aascCarrierConfigurationForm.userName.value='';
      document.aascCarrierConfigurationForm.password.value='';
      document.aascCarrierConfigurationForm.prefix.value='';
      document.aascCarrierConfigurationForm.domain.value='';
      document.aascCarrierConfigurationForm.accountNo.value='';
      document.aascCarrierConfigurationForm.pdPort.value='';
      document.aascCarrierConfigurationForm.paperSize.value='';
      document.aascCarrierConfigurationForm.intlPrintLayout.value='';
      document.aascCarrierConfigurationForm.nonDelivery.value='';
      document.aascCarrierConfigurationForm.integrationId.value='';
      
      document.aascCarrierConfigurationForm.Op900LabelFormat.value='';
      
      var carrierName = document.aascCarrierConfigurationForm.carrierName.options[document.aascCarrierConfigurationForm.carrierName.selectedIndex].text; 
      //alert("Carrier Name : "+carrierName);
      if(carrierName == 'UPS' || carrierName == 'FDXE' || carrierName == 'FDXG' || carrierName.toUpperCase() == 'FEDERAL EXPRESS' || carrierName.toUpperCase() == '--SELECT ONE--')
      {
//          document.aascCarrierConfigurationForm.supportHazmatShipping.style.display = "none";
//          document.getElementById("supportHazmatShippingLable").style.display = "none";
//          document.aascCarrierConfigurationForm.supportHazmatShipping.options[document.aascCarrierConfigurationForm.supportHazmatShipping.selectedIndex].value = "" ; 
      }
      else
      {
//          document.aascCarrierConfigurationForm.supportHazmatShipping.style.display = "";
        //  document.getElementById("supportHazmatShippingLable").style.display = "";
//          document.aascCarrierConfigurationForm.supportHazmatShipping.options[document.aascCarrierConfigurationForm.supportHazmatShipping.selectedIndex].value = "Yes" ; 
      }
      //Shiva added below code for DHL
      if(carrierName == 'DHL')
      {
        document.getElementById('IntegrationIdDiv').style.display = "none";
        document.getElementById("DHLRegionCodeDiv").style.display = "";
        document.aascCarrierConfigurationForm.dhlRegionCode.value="";
      }
      else
      {
        document.getElementById('IntegrationIdDiv').style.display = "";
        document.getElementById("DHLRegionCodeDiv").style.display = "none";
      }
      //Shiva code end
      //document.aascCarrierConfigurationForm.supportHazmatShipping.value = 'Yes';
      //SC_7.0_RS
      //added by Jagadish
//         if(carrierName == 'FDXE')
//   {
//   document.aascCarrierConfigurationForm.IntlDocSubType.style.display = "";
//   document.getElementById("intlDocSubTypeLabel").style.display = "";
//   }
//   else{
//   document.aascCarrierConfigurationForm.IntlDocSubType.style.display = "none";
//   document.getElementById("intlDocSubTypeLabel").style.display = "none";
//   }
//      document.aascCarrierConfigurationForm.eodLabelFormats.value='';

      // document.aascCarrierConfigurationForm.labelStock.value='';
      //SC_7.0_RS
  //    document.aascCarrierConfigurationForm.modelSymbol.value='';
  //    document.aascCarrierConfigurationForm.intlLabelFormat.value='';
      //SC_7.0_RS
//      document.aascCarrierConfigurationForm.eodPrinterSymbol.value='';
//      document.aascCarrierConfigurationForm.eodStockSymbol.value='';
//      document.aascCarrierConfigurationForm.eodDOCPort.value='';
      //document.aascCarrierConfigurationForm.docTab.value='';
 //     document.aascCarrierConfigurationForm.modelSymbol.value='';
    //  document.aascCarrierConfigurationForm.stockSymbol.value='';
//      document.aascCarrierConfigurationForm.eodPort.value='';
      document.aascCarrierConfigurationForm.meterNumber.value='';
      document.aascCarrierConfigurationForm.accessLicenseNumber.value='';
//      document.aascCarrierConfigurationForm.SenderEmail.value='';
//      document.aascCarrierConfigurationForm.recepientEmailAddress1.value='';
//      document.aascCarrierConfigurationForm.recepientEmailAddress2.value='';
//      document.aascCarrierConfigurationForm.recepientEmailAddress3.value='';
//      document.aascCarrierConfigurationForm.recepientEmailAddress4.value='';
//      document.aascCarrierConfigurationForm.recepientEmailAddress5.value='';
    
      //   document.aascCarrierConfigurationForm.nonDiscountedCost.checked=false;
      //   document.aascCarrierConfigurationForm.acctNumNegotiatedFlag.checked=false;
      document.aascCarrierConfigurationForm.emailNotificationFlag.checked=false;
      document.aascCarrierConfigurationForm.IsReference1Required.checked=false;
      document.aascCarrierConfigurationForm.IsReference2Required.checked=false;
      //SC_EMail_SJ
//      document.aascCarrierConfigurationForm.CarrierSalesOrderNumber.checked = false;
      document.aascCarrierConfigurationForm.CarrierCustomerName.checked = false;
//      document.aascCarrierConfigurationForm.deliveryItemNumbers.checked = false;
      
      document.aascCarrierConfigurationForm.ShipNotification.checked=false;
      document.aascCarrierConfigurationForm.ExceptionNotification.checked=false;
      document.aascCarrierConfigurationForm.DeliveryNotification.checked=false;
      document.aascCarrierConfigurationForm.Format[0].checked = false;
      document.aascCarrierConfigurationForm.Format[1].checked = false;
      document.aascCarrierConfigurationForm.Format[2].checked = false;
      document.aascCarrierConfigurationForm.Mode.selectedIndex = 0;
      document.aascCarrierConfigurationForm.enableFlag.checked = false;
      document.aascCarrierConfigurationForm.carrierCodeValue.selectedIndex = 0;
      document.getElementById("buttonDisplay").innerHTML = '';
      var button = '';
      //'<img name="reset" src="buttons/aascClearOff1.png" border="0" /><img name="saveImage" src="buttons/aascSaveOff1.png" border="0" />';
      document.getElementById("buttonDisplay").innerHTML = button;
      /*
      document.getElementById("buttonDisplay").innerHTML = '';
      var button = '<img name="reset" src="buttons/aascClearOff1.png" border="0" /><img name="saveImage" src="buttons/aascSaveOff1.png" border="0" />';
      document.getElementById("buttonDisplay").innerHTML = button;
      */
}

function onCarrierClientChange()
{
 onCarrierCarrierChange();
 document.aascCarrierConfigurationForm.locationId.selectedIndex = 0;
 document.aascCarrierConfigurationForm.carrierName.selectedIndex = 0;
}


function selectCarrier()
{

//     var strOrg=document.aascCarrierConfigurationForm.invOrgId.options[document.aascCarrierConfigurationForm.invOrgId.selectedIndex].text;
     var strCar=document.aascCarrierConfigurationForm.carrierName.options[document.aascCarrierConfigurationForm.carrierName.selectedIndex].text;
     var strOperating=document.aascCarrierConfigurationForm.locationId.options[document.aascCarrierConfigurationForm.locationId.selectedIndex].text;
   // alert("strLocation ::"+strOperating);
   //   alert("strCar ::"+strCar);  
     //alert(document.aascCarrierConfigurationForm.shipMethodChk.value);
  
    if(strOperating=="--Select One--")
     {
        alert("Please Select Location ");
        document.aascCarrierConfigurationForm.locationId.focus();
        return false;
     }
     
              if(strCar=="--Select One--")
         {
           alert("Please Select Carrier and click on Go");
           document.aascCarrierConfigurationForm.carrierName.focus();
           return false;
         }
  
     if(document.aascCarrierConfigurationForm.submit11.value=='ShipMethod')
     {

         if(document.aascCarrierConfigurationForm.shipMethodChk.value!="Y")
         {
            alert('Please First Retrieve Carrier Details');
            return false;
         }
         else
         {
         //alert('here');
         //  alert(document.aascCarrierConfigurationForm.carrierCodeValue.selectedIndex);
         //alert(document.aascCarrierConfigurationForm.carrierName.options[document.aascCarrierConfigurationForm.carrierName.selectedIndex].text);
    
//document.aascCarrierConfigurationForm.carrierNameVal.value = document.aascCarrierConfigurationForm.carrierCodeValue.options[document.aascCarrierConfigurationForm.carrierCodeValue.selectedIndex].text;
         //alert(document.aascCarrierConfigurationForm.carrierNameVal.value);
         }
         if(document.aascCarrierConfigurationForm.enableFlag.checked==false)
         {
           alert('Please check the Enable Flag');
           return false;
         }
     }
   
//     if(document.aascCarrierConfigurationForm.submit11.value!='carrierLoad')
//     {
//         if(strOrg=="--Select--")
//         {
//           alert("Please Select Organization");
//           document.aascCarrierConfigurationForm.invOrgId.focus();
//           return false;
//         }
//         if(strCar=="--select One--")
//         {
//           alert("Please Select Carrier and click on Go");
//           document.aascCarrierConfigurationForm.carrierName.focus();
//           return false;
//         }
//     }
//     else if(document.aascCarrierConfigurationForm.submit11.value == 'carrierLoad')
//     {
//     //Shiva added code for multiple clicks on upload button for bug 1536
//     if(document.aascCarrierConfigurationForm.CarrierId.value=="1")
//     {
//        alert("Request already submitted .. Please wait..");
//        return false;
//     }
//         var confirmStatus = confirm("This will load Carriers and Ship Methods into Ship Console. Do you want to continue?") ;
//         if (confirmStatus == true)
//         {
//            document.aascCarrierConfigurationForm.CarrierId.value="1";
//            
//             document.aascCarrierConfigurationForm.submit();
//         }
//         else
//         {
//             return false ;
//         }
//     }
     if(document.aascCarrierConfigurationForm.submit11.value=='SaveUpdate')
     {
         document.aascCarrierConfigurationForm.port.value = trim(document.aascCarrierConfigurationForm.port.value);
         document.aascCarrierConfigurationForm.serverIpAddress.value = trim(document.aascCarrierConfigurationForm.serverIpAddress.value);
         document.aascCarrierConfigurationForm.userName.value = trim(document.aascCarrierConfigurationForm.userName.value);
         document.aascCarrierConfigurationForm.password.value = trim(document.aascCarrierConfigurationForm.password.value);
         document.aascCarrierConfigurationForm.prefix.value = trim(document.aascCarrierConfigurationForm.prefix.value);
         document.aascCarrierConfigurationForm.domain.value = trim(document.aascCarrierConfigurationForm.domain.value);
         document.aascCarrierConfigurationForm.integrationId.value = trim(document.aascCarrierConfigurationForm.integrationId.value);
         
         var carrierCode = document.aascCarrierConfigurationForm.carrierCode.value;
         var carrierMode = document.aascCarrierConfigurationForm.Mode.options[document.aascCarrierConfigurationForm.Mode.selectedIndex].text;
         // Added below if condition for selecting carrier mode.
         if(carrierMode=="Select")
         {
            alert("Please Select a Mode ");
            document.aascCarrierConfigurationForm.Mode.focus();
            return false;
         }
         
         if(carrierCode==110 || carrierCode==111 || carrierCode==100 )
         {  
                       
            if(document.aascCarrierConfigurationForm.protocol.value=="" || document.aascCarrierConfigurationForm.protocol.value==null)
            {
                alert("Please Select Protocol");
                document.aascCarrierConfigurationForm.protocol.focus();
                return false;
            }

            var carCode=document.aascCarrierConfigurationForm.carrierCodeValue.options[document.aascCarrierConfigurationForm.carrierCodeValue.selectedIndex].value;
//            if(carCode=='111')
//            {
//                if(document.aascCarrierConfigurationForm.hubId.value=="" || document.aascCarrierConfigurationForm.hubId.value==null)
//                {
//                  alert("Please Select SmartPost HudId");
//                  document.aascCarrierConfigurationForm.hubId.focus();
//                  return false;
//                }
//            }
            if(document.aascCarrierConfigurationForm.serverIpAddress.value=="" || document.aascCarrierConfigurationForm.serverIpAddress.value==null)
            {
              alert("Please enter Server IP Address");
              document.aascCarrierConfigurationForm.serverIpAddress.focus();
              return false;
            }
        
            // Bug Fix 1185 - Include validation to check for null 
             if(carCode == '111')
           {
            if(document.aascCarrierConfigurationForm.Op900LabelFormat.value=="" || document.aascCarrierConfigurationForm.Op900LabelFormat.value==null || document.aascCarrierConfigurationForm.Op900LabelFormat.value == "--Select--")
            {
                alert("Please select Op900 Label Format");
                document.aascCarrierConfigurationForm.Op900LabelFormat.focus();
                return false;
            }
            }
            /*    if(document.aascCarrierConfigurationForm.accountNo.value=="" || document.aascCarrierConfigurationForm.accountNo.value==null)
            {
                alert("Please enter account number");
                document.aascCarrierConfigurationForm.accountNo.focus();
                return false;
            } */

            if(document.aascCarrierConfigurationForm.pdPort.value=="" || document.aascCarrierConfigurationForm.pdPort.value==null)
            {
                alert("Please Select Label Format");
                document.aascCarrierConfigurationForm.pdPort.focus();
                return false;
            }
            if(document.aascCarrierConfigurationForm.docTab.value=="" || document.aascCarrierConfigurationForm.docTab.value==null)
            {
                alert("Please Select DocTab Location");
                document.aascCarrierConfigurationForm.docTab.focus();
                return false;
            }
            if((document.aascCarrierConfigurationForm.emailNotificationFlag.checked) && (document.aascCarrierConfigurationForm.submit11.value=="SaveUpdate"))
            {
            //For Email Notification
            if(document.aascCarrierConfigurationForm.ShipNotification.checked)
            {
                document.aascCarrierConfigurationForm.ShipNotification.value="Y";
            }
            else
            {
                document.aascCarrierConfigurationForm.ShipNotification.value="N";
            }
//            if((document.aascCarrierConfigurationForm.SenderEmail.value=="")||(document.aascCarrierConfigurationForm.SenderEmail.value==null))
//            {
//                alert("Please enter Sender Email Address");
//                document.aascCarrierConfigurationForm.SenderEmail.focus();
//                return false;
//            }
//            if((document.aascCarrierConfigurationForm.recepientEmailAddress1.value=="")||(document.aascCarrierConfigurationForm.recepientEmailAddress1.value==null))
//            {
//                if((document.aascCarrierConfigurationForm.recepientEmailAddress2.value=="")||(document.aascCarrierConfigurationForm.recepientEmailAddress2.value==null))
//                {
//                    if((document.aascCarrierConfigurationForm.recepientEmailAddress3.value=="")||(document.aascCarrierConfigurationForm.recepientEmailAddress3.value==null))
//                    {
//                        if((document.aascCarrierConfigurationForm.recepientEmailAddress4.value=="")||(document.aascCarrierConfigurationForm.recepientEmailAddress4.value==null))
//                        {
//                            if((document.aascCarrierConfigurationForm.recepientEmailAddress5.value=="")||(document.aascCarrierConfigurationForm.recepientEmailAddress5.value==null))
//                            {
//                                alert("Please enter atleast one Recepient Email Address");
//                                document.aascCarrierConfigurationForm.recepientEmailAddress1.focus();
//                                return false;
//                            }
//                        }
//                    }
//                }
//            }
            if((document.aascCarrierConfigurationForm.ShipNotification.checked==false) && (document.aascCarrierConfigurationForm.ExceptionNotification.checked==false) && (document.aascCarrierConfigurationForm.DeliveryNotification.checked==false))
            {
                alert("Please check atleast one Notification type");
                document.aascCarrierConfigurationForm.ShipNotification.focus();
                return false;
            }

//            if (! isValidEmail(document.aascCarrierConfigurationForm.SenderEmail.value))
//            {
//                alert("Please Enter A Valid Email Address");
//                document.aascCarrierConfigurationForm.SenderEmail.focus();
//                return false;        
//            }
//            if((!document.aascCarrierConfigurationForm.recepientEmailAddress1.value=="")||(!document.aascCarrierConfigurationForm.recepientEmailAddress1.value==null))
//            {    
//                if (! isValidEmail(document.aascCarrierConfigurationForm.recepientEmailAddress1.value))
//                {
//                    alert("Please Enter A Valid Email Address");
//                    document.aascCarrierConfigurationForm.recepientEmailAddress1.focus();
//                    return false;        
//                }
//            }
//            if((!document.aascCarrierConfigurationForm.recepientEmailAddress2.value=="")||(!document.aascCarrierConfigurationForm.recepientEmailAddress2.value==null))
//            {
//                if (! isValidEmail(document.aascCarrierConfigurationForm.recepientEmailAddress2.value))
//                {
//                    alert("Please Enter A Valid Email Address");
//                    document.aascCarrierConfigurationForm.recepientEmailAddress2.focus();
//                    return false;        
//                }
//            }
//            if((!document.aascCarrierConfigurationForm.recepientEmailAddress3.value=="")||(!document.aascCarrierConfigurationForm.recepientEmailAddress3.value==null))
//            {
//                if (! isValidEmail(document.aascCarrierConfigurationForm.recepientEmailAddress3.value))
//                {
//                    alert("Please Enter A Valid Email Address");
//                    document.aascCarrierConfigurationForm.recepientEmailAddress3.focus();
//                    return false;        
//                }
//            }
//            if((!document.aascCarrierConfigurationForm.recepientEmailAddress4.value=="")||(!document.aascCarrierConfigurationForm.recepientEmailAddress4.value==null))
//            {
//                if (! isValidEmail(document.aascCarrierConfigurationForm.recepientEmailAddress4.value))
//                {
//                    alert("Please Enter A Valid Email Address");
//                    document.aascCarrierConfigurationForm.recepientEmailAddress4.focus();
//                    return false;        
//                }
//            }
//            if((!document.aascCarrierConfigurationForm.recepientEmailAddress5.value=="")||(!document.aascCarrierConfigurationForm.recepientEmailAddress5.value==null))
//            {
//                if (! isValidEmail(document.aascCarrierConfigurationForm.recepientEmailAddress5.value))
//                {
//                    alert("Please Enter A Valid Email Address");
//                    document.aascCarrierConfigurationForm.recepientEmailAddress5.focus();
//                    return false;        
//                }
//            }
         }
     }
     if(carrierCode==110 || carrierCode==111 )
     {
        if(document.aascCarrierConfigurationForm.port.value=="" || document.aascCarrierConfigurationForm.port.value==null)
        {
            alert("Please enter Port");
           document.aascCarrierConfigurationForm.port.focus();
           return false;
        }
        /*    if(document.aascCarrierConfigurationForm.meterNumber.value=="" || document.aascCarrierConfigurationForm.meterNumber.value==null)
        {
            alert("Please enter Meter Number");
           document.aascCarrierConfigurationForm.meterNumber.focus();
           return false;
        }  */

        if(document.aascCarrierConfigurationForm.labelStock.value=="" || document.aascCarrierConfigurationForm.labelStock.value==null)
        {
            alert("Please Select Label Stock Orientation");
           document.aascCarrierConfigurationForm.labelStock.focus();
           return false;
        }
     }
     if(carrierCode==100 || carrierCode==115)
     {
//     alert("carrierMode::"+carrierMode);
     if(carrierMode != "ShipExec"){
        if(document.aascCarrierConfigurationForm.userName.value=="" || document.aascCarrierConfigurationForm.userName.value==null)
        {
            alert("Please enter User Name");
           document.aascCarrierConfigurationForm.userName.focus();
           return false;
        }

        if(document.aascCarrierConfigurationForm.password.value=="" || document.aascCarrierConfigurationForm.password.value==null)
        {
            alert("Please enter Password");
           document.aascCarrierConfigurationForm.password.focus();
           return false;
        }
}
        if(document.aascCarrierConfigurationForm.prefix.value=="" || document.aascCarrierConfigurationForm.prefix.value==null)
        {
           alert("Please enter Prefix");
           document.aascCarrierConfigurationForm.prefix.focus();
           return false;
        }
     }
     if((carrierCode==110 || carrierCode==111) && carrierMode == "FedexWebServices")
     {

        if(document.aascCarrierConfigurationForm.userName.value=="" || document.aascCarrierConfigurationForm.userName.value==null)
        {
            alert("Please enter User Name");
           document.aascCarrierConfigurationForm.userName.focus();
           return false;
        }

        if(document.aascCarrierConfigurationForm.password.value=="" || document.aascCarrierConfigurationForm.password.value==null)
        {
            alert("Please enter Password");
           document.aascCarrierConfigurationForm.password.focus();
           return false;
        }
     }
     if(carrierCode==114){
     
        if(document.aascCarrierConfigurationForm.protocol.value=="" || document.aascCarrierConfigurationForm.protocol.value==null)
        {
            alert("Please Select Protocol");
            document.aascCarrierConfigurationForm.protocol.focus();
            return false;
        }
        if(document.aascCarrierConfigurationForm.serverIpAddress.value=="" || document.aascCarrierConfigurationForm.serverIpAddress.value==null)
        {
          alert("Please enter Server IP Address");
          document.aascCarrierConfigurationForm.serverIpAddress.focus();
          return false;
        }    
        if(document.aascCarrierConfigurationForm.userName.value=="" || document.aascCarrierConfigurationForm.userName.value==null)
        {
            alert("Please enter User Name");
           document.aascCarrierConfigurationForm.userName.focus();
           return false;
        }

        if(document.aascCarrierConfigurationForm.password.value=="" || document.aascCarrierConfigurationForm.password.value==null)
        {
            alert("Please enter Password");
           document.aascCarrierConfigurationForm.password.focus();
           return false;
        }
		//Shiva added below code for DHL 
        if(document.aascCarrierConfigurationForm.dhlRegionCode.value=="" || document.aascCarrierConfigurationForm.dhlRegionCode.value==null)
        {
            alert("Please Select Region Code");
            document.aascCarrierConfigurationForm.dhlRegionCode.focus();
            return false;
        }
        if(document.aascCarrierConfigurationForm.pdPort.value=="EPL2" && document.aascCarrierConfigurationForm.labelStock.value=="6X4_thermal")
        {
            alert("LabelStock Type \'6X4_thermal\' is not supported for Label Format \'EPL2\'. Please change the LabelStock Type");
            document.aascCarrierConfigurationForm.labelStock.focus();
            return false;
        }
        //Shiva code end
     }
       //Mahesh Start
     if( carrierCode==115)
     {
            if(document.aascCarrierConfigurationForm.serverIpAddress.value=="" || document.aascCarrierConfigurationForm.serverIpAddress.value==null)
                {
                  alert("Please enter Server IP Address");
                  document.aascCarrierConfigurationForm.serverIpAddress.focus();
                  return false;
                }
                
                if(document.aascCarrierConfigurationForm.integrationId.value=="" || document.aascCarrierConfigurationForm.integrationId.value==null)
                {
                  alert("Please enter Integration Id");
                  document.aascCarrierConfigurationForm.integrationId.focus();
                  return false;
                }
     }
     //Mahesh End
  }  
}

function setClientIdVal(){
  document.aascCarrierConfigurationForm.clientIdVal.value=document.aascCarrierConfigurationForm.clientId.value;
}

function getLocationList()
{
  setClientIdVal();
  document.aascCarrierConfigurationForm.submit11.value='LocationDetails';
  document.aascCarrierConfigurationForm.submit();
}

//For Email Notification,to enable required fields when emailNotificationFlag is checked
function enableEmailNotification()
{

    if(!document.aascCarrierConfigurationForm.emailNotificationFlag.checked)
    {
//        document.aascCarrierConfigurationForm.SenderEmail.disabled = true;
//        document.aascCarrierConfigurationForm.recepientEmailAddress1.disabled = true;
//        document.aascCarrierConfigurationForm.recepientEmailAddress2.disabled = true;
//        document.aascCarrierConfigurationForm.recepientEmailAddress3.disabled = true;
//        document.aascCarrierConfigurationForm.recepientEmailAddress4.disabled = true;
//        document.aascCarrierConfigurationForm.recepientEmailAddress5.disabled = true;
        
        document.aascCarrierConfigurationForm.IsReference1Required.disabled = true;
        document.aascCarrierConfigurationForm.IsReference2Required.disabled = true;
        
        //SC_EMail_SJ
//        document.aascCarrierConfigurationForm.CarrierSalesOrderNumber.disabled = true;
        document.aascCarrierConfigurationForm.CarrierCustomerName.disabled = true;
//        document.aascCarrierConfigurationForm.deliveryItemNumbers.disabled = true;
        document.aascCarrierConfigurationForm.ShipNotification.disabled = true;
        document.aascCarrierConfigurationForm.ExceptionNotification.disabled = true;
        document.aascCarrierConfigurationForm.DeliveryNotification.disabled = true;
        document.aascCarrierConfigurationForm.Format[0].disabled = true;
        document.aascCarrierConfigurationForm.Format[1].disabled = true;
        document.aascCarrierConfigurationForm.Format[2].disabled = true;
//        document.aascCarrierConfigurationForm.SenderEmail.value ='';
//        document.aascCarrierConfigurationForm.recepientEmailAddress1.value ='';
//        document.aascCarrierConfigurationForm.recepientEmailAddress2.value ='';
//        document.aascCarrierConfigurationForm.recepientEmailAddress3.value ='';
//        document.aascCarrierConfigurationForm.recepientEmailAddress4.value ='';
//        document.aascCarrierConfigurationForm.recepientEmailAddress5.value ='';
        
        document.aascCarrierConfigurationForm.IsReference1Required.checked=false;
        document.aascCarrierConfigurationForm.IsReference2Required.checked=false;
        
        //SC_EMail_SJ
//        document.aascCarrierConfigurationForm.CarrierSalesOrderNumber.checked = false;
        document.aascCarrierConfigurationForm.CarrierCustomerName.checked = false;
//        document.aascCarrierConfigurationForm.deliveryItemNumbers.checked = false;
        document.aascCarrierConfigurationForm.ShipNotification.checked=false;
        document.aascCarrierConfigurationForm.ExceptionNotification.checked=false;
        document.aascCarrierConfigurationForm.DeliveryNotification.checked=false;
        document.aascCarrierConfigurationForm.Format[0].checked = true;
    }
    else
    {
//        document.aascCarrierConfigurationForm.SenderEmail.disabled = false;
//        document.aascCarrierConfigurationForm.recepientEmailAddress1.disabled = false;
//        document.aascCarrierConfigurationForm.recepientEmailAddress2.disabled = false;
//        document.aascCarrierConfigurationForm.recepientEmailAddress3.disabled = false;
//        document.aascCarrierConfigurationForm.recepientEmailAddress4.disabled = false;
//        document.aascCarrierConfigurationForm.recepientEmailAddress5.disabled = false;
       
       var carrierMode = document.aascCarrierConfigurationForm.Mode.options[document.aascCarrierConfigurationForm.Mode.selectedIndex].text;
        if(carrierMode == "ShipExec")
        {
            document.aascCarrierConfigurationForm.IsReference1Required.disabled = true;
            document.aascCarrierConfigurationForm.IsReference2Required.disabled = true;
            document.aascCarrierConfigurationForm.ShipNotification.disabled = false;
            document.aascCarrierConfigurationForm.ExceptionNotification.disabled = true;
            document.aascCarrierConfigurationForm.DeliveryNotification.disabled = true;
            document.aascCarrierConfigurationForm.CarrierCustomerName.disabled = true;
        
            document.aascCarrierConfigurationForm.Format[0].disabled = false;
            document.aascCarrierConfigurationForm.Format[1].disabled = false;
            document.aascCarrierConfigurationForm.Format[2].disabled = false; 
        }
        else{ 
        document.aascCarrierConfigurationForm.IsReference1Required.disabled = false;
        document.aascCarrierConfigurationForm.IsReference2Required.disabled = false;
        
        //SC_EMail_SJ
//        document.aascCarrierConfigurationForm.CarrierSalesOrderNumber.disabled = false;
        document.aascCarrierConfigurationForm.CarrierCustomerName.disabled = false;
//        document.aascCarrierConfigurationForm.deliveryItemNumbers.disabled = false;

        document.aascCarrierConfigurationForm.ShipNotification.disabled = false;
        document.aascCarrierConfigurationForm.ExceptionNotification.disabled = false;
        document.aascCarrierConfigurationForm.DeliveryNotification.disabled = false;
        document.aascCarrierConfigurationForm.Format[0].disabled = false;
        document.aascCarrierConfigurationForm.Format[1].disabled = false;
        document.aascCarrierConfigurationForm.Format[2].disabled = false;
    }
    }
}
//For Email Notification,to set values for checkboxes
function emailNotificationChkVal(checkbox)
{

 var Chkname=checkbox.name;

  if((Chkname=="emailNotificationFlag")&&(document.aascCarrierConfigurationForm.emailNotificationFlag.checked))
  {
   //   alert("Please enter dff attribute columns in recepient emails,that are mapped in Sales order");
//      document.aascCarrierConfigurationForm.SenderEmail.disabled = false;
//      document.aascCarrierConfigurationForm.SenderEmail.focus();
  }
  if(document.aascCarrierConfigurationForm[Chkname].checked)
  {
    document.aascCarrierConfigurationForm[Chkname].value="Y";
  }
  else
  {
    document.aascCarrierConfigurationForm[Chkname].value="N";
  }
}

//function onlyOneCheck(checkbox)
//{
//  var Chkname=checkbox.name;
//  if((Chkname=="IsReference2Required")&&(document.aascCarrierConfigurationForm.IsReference1Required.checked))
//  {
//        alert("Please check only One Reference number");
//        var status1=confirm('Do you want to uncheck Reference 1 and check Reference 2 ?');
//        if(status1)
//        {
//        document.aascCarrierConfigurationForm.IsReference1Required.checked=false;
//        document.aascCarrierConfigurationForm.IsReference2Required.checked=true;
//       //document.PackageDimensionForm['dimensionActive'+index].value="Y";
//        return true;
//        }
//        else
//        {
//            document.aascCarrierConfigurationForm.IsReference1Required.checked=true;
//            document.aascCarrierConfigurationForm.IsReference2Required.checked=false;
//             return false;
//        }
//  }
//  if((Chkname=="IsReference1Required")&&(document.aascCarrierConfigurationForm.IsReference2Required.checked))
//  {
//        alert("Please check only One Reference number");
//        var status1=confirm('Do you want to uncheck Reference 2 and check Reference 1 ?');
//        if(status1)
//        {
//        document.aascCarrierConfigurationForm.IsReference2Required.checked=false;
//        document.aascCarrierConfigurationForm.IsReference1Required.checked=true;
//        return true;
//        }
//        else
//        {
//            document.aascCarrierConfigurationForm.IsReference2Required.checked=true;
//            document.aascCarrierConfigurationForm.IsReference1Required.checked=false;
//             return false;
//        }
//  }
//
//}

function disableEmailflag()
{

    var carrierCode = document.aascCarrierConfigurationForm.carrierCode.value;
    if(carrierCode!=100 && carrierCode!=110 && carrierCode!=111)
    {

        document.aascCarrierConfigurationForm.emailNotificationFlag.checked=false;
        document.aascCarrierConfigurationForm.emailNotificationFlag.disabled=true;
//        document.aascCarrierConfigurationForm.SenderEmail.disabled = true;
//        document.aascCarrierConfigurationForm.recepientEmailAddress1.disabled = true;
//        document.aascCarrierConfigurationForm.recepientEmailAddress2.disabled = true;
//        document.aascCarrierConfigurationForm.recepientEmailAddress3.disabled = true;
//        document.aascCarrierConfigurationForm.recepientEmailAddress4.disabled = true;
//        document.aascCarrierConfigurationForm.recepientEmailAddress5.disabled = true;
        
        document.aascCarrierConfigurationForm.IsReference1Required.disabled = true;
        document.aascCarrierConfigurationForm.IsReference2Required.disabled = true;
        
        //SC_EMail_SJ
//        document.aascCarrierConfigurationForm.CarrierSalesOrderNumber.disabled = true;
        document.aascCarrierConfigurationForm.CarrierCustomerName.disabled = true;
//        document.aascCarrierConfigurationForm.deliveryItemNumbers.disabled = true;
        document.aascCarrierConfigurationForm.ShipNotification.disabled = true;
        document.aascCarrierConfigurationForm.ExceptionNotification.disabled = true;
        document.aascCarrierConfigurationForm.DeliveryNotification.disabled = true;
        document.aascCarrierConfigurationForm.Format[0].disabled = true;
        document.aascCarrierConfigurationForm.Format[1].disabled = true;
        document.aascCarrierConfigurationForm.Format[2].disabled = true;
//        document.aascCarrierConfigurationForm.SenderEmail.value ='';
//        document.aascCarrierConfigurationForm.recepientEmailAddress1.value ='';
//        document.aascCarrierConfigurationForm.recepientEmailAddress2.value ='';
//        document.aascCarrierConfigurationForm.recepientEmailAddress3.value ='';
//        document.aascCarrierConfigurationForm.recepientEmailAddress4.value ='';
//        document.aascCarrierConfigurationForm.recepientEmailAddress5.value ='';
        
        document.aascCarrierConfigurationForm.IsReference1Required.checked=false;
        document.aascCarrierConfigurationForm.IsReference2Required.checked=false;
        
        //SC_EMail_SJ
//        document.aascCarrierConfigurationForm.CarrierSalesOrderNumber.checked = false;
        document.aascCarrierConfigurationForm.CarrierCustomerName.checked = false;
//        document.aascCarrierConfigurationForm.deliveryItemNumbers.checked = false;
        
        
        document.aascCarrierConfigurationForm.ShipNotification.checked=false;
        document.aascCarrierConfigurationForm.ExceptionNotification.checked=false;
        document.aascCarrierConfigurationForm.DeliveryNotification.checked=false;
        document.aascCarrierConfigurationForm.Format[0].checked = false;
        document.aascCarrierConfigurationForm.Format[1].checked = false;
        document.aascCarrierConfigurationForm.Format[2].checked = false;
    }
}

function enableFlagChk()
{
  //alert(document.aascCarrierConfigurationForm.enableFlag.checked);
  /* String enableFlagval = document.aascCarrierConfigurationForm.enableFlag.value;
  if(enableFlagval == "" || enableFlagval == " " || enableFlagval == null)
  {
  document.aascCarrierConfigurationForm.enableFlag.checked = false;
  }else{
  document.aascCarrierConfigurationForm.enableFlag.checked = ;
  } */

  if(document.aascCarrierConfigurationForm.enableFlag.checked)
  {
    document.aascCarrierConfigurationForm.enableFlag.value="Y";
  }
  else
  {
    document.aascCarrierConfigurationForm.enableFlag.value="N";
  }

}


function trim(str)
{
  return str.replace(/^\s*|\s*$/g,"");
}



function LoadLabels()
{

  document.aascCarrierConfigurationForm.clientIdVal.value=document.aascCarrierConfigurationForm.clientId.value;
  document.aascCarrierConfigurationForm.carrierNameStr.value = document.aascCarrierConfigurationForm.carrierName.options[document.aascCarrierConfigurationForm.carrierName.selectedIndex].text;
  document.aascCarrierConfigurationForm.submit11.value='Load';

  document.aascCarrierConfigurationForm.submit();

}

//function openShipFromLocations()
//{
//  shipWindow =  window.open("aascShipFromLocations.jsp" ,"Post",'width=600,height=500,top=100,left=100,scrollbars=yes, resizable=yes');
//  shipWindow.focus();
//}
//
//function onClickShipFromLocations()
//{
//  var invOrgDropdownIndex = document.getElementById('invOrgId').selectedIndex;
//  var invOrgDropdownValue = document.getElementById('invOrgId')[invOrgDropdownIndex].text;
//  var opUnitDropdownIndex = document.getElementById('orgId').selectedIndex;
//  var opUnitDropdownValue = document.getElementById('orgId')[opUnitDropdownIndex].text;
//  //var ApplicationURL = document.aascOptionProfileForm.ApplicationURL.value;
//
//  if(opUnitDropdownValue=="--Select--")
//  {
//   alert("Please Select Operating Unit");
//   document.aascOptionProfileForm.orgId.focus();
//   return false;
//  }
//
//
//  if(invOrgDropdownValue=="--Select--")
//  {
//   alert("Please Select Organization and click on Go");
//   document.aascOptionProfileForm.invOrgId.focus();
//   return false;
//  }
//
//  /*if(ApplicationURL =="" || ApplicationURL ==" " || ApplicationURL ==null)
//  {
//   alert("Please click on Go button");
//  // document.aascOptionProfileForm.orgId.focus();
//   return false;
//  }*/
//
//  /*var submitButton = document.aascOptionProfileForm.submit123.value;
//  alert("submitButton : "+submitButton);
//  if((submitButton == null || submitButton != "Go")
//  {
//    alert("Please click on 'GO' button");
//    //document.aascOptionProfileForm.GoButton.focus();
//    return false;
//  }*/
//  return true;
//}


function openAccountNumbers()
{
  var carrierCode = document.aascCarrierConfigurationForm['carrierCodeValue'].value;
 
 var locId  = document.aascCarrierConfigurationForm['locationId'].value;
 //alert('locId : '+locId); 
  acctNumbersWindow =  window.open("aascAccountNumbers.jsp?carrierCodeHidden="+carrierCode,"Post",'width=770,height=500,top=100,left=100,scrollbars=yes, resizable=yes');
  acctNumbersWindow.focus();
}

function onClickAccountNumbers()
{
  //var strOrg=document.aascCarrierConfigurationForm.invOrgId.options[document.aascCarrierConfigurationForm.invOrgId.selectedIndex].text;
  var strCar=document.aascCarrierConfigurationForm.carrierName.options[document.aascCarrierConfigurationForm.carrierName.selectedIndex].text;
  var strOperating=document.aascCarrierConfigurationForm.locationId.options[document.aascCarrierConfigurationForm.locationId.selectedIndex].text;

  if(strOperating=="--select one--")
  {
    alert("Please Select Operating Unit");
    document.aascCarrierConfigurationForm.orgId.focus();
    return false;
  }

//  if(strOrg=="--select one--")
//  {
//    alert("Please Select Organization");
//    document.aascCarrierConfigurationForm.invOrgId.focus();
//    return false;
//  }
  if(strCar=="--select one--")
  {
    alert("Please Select Carrier and click on Go");
    document.aascCarrierConfigurationForm.carrierName.focus();
    return false;
  }
  var carrierCodeValue = document.aascCarrierConfigurationForm.carrierCodeValue.value;
  //alert("carrierCodeValue : "+carrierCodeValue);
  if(carrierCodeValue == null || carrierCodeValue == "")
  {
    alert("Please Click on GO button");
    //document.aascCarrierConfigurationForm.goImage.focus();
    return false;
  }

  /*if(carrierCodeValue != 100 && carrierCodeValue != 110 && carrierCodeValue != 111)
  {
    document.aascCarrierConfigurationForm.
  }*/

  //alert(document.aascCarrierConfigurationForm.submit11.value);
  /*var submitButton = document.aascCarrierConfigurationForm.submit11.value;
  alert("submitButton : "+submitButton);
  if(submitButton != "Go" || submitButton == null)
  {
    alert("Please click on 'GO' button");
    //document.aascOptionProfileForm.GoButton.focus();
    return false;
  }*/

  return true;
}


//
////Gururaj modified below code for containerizationUsed profile option
//function containerChk(checkbox)
//{
//   // alert("document.aascOptionProfileForm.containerizationUsed.checked :: "+document.aascOptionProfileForm.containerizationUsed.checked);
//    var Chkname=checkbox.name;
////    alert("Chkname :: "+Chkname);
////    alert("document.aascOptionProfileForm[Chkname].checked :: "+document.aascOptionProfileForm[Chkname].checked);
// //   alert(":: "+document.getElementById('containerizationUsed').checked);
//  if(document.getElementById('containerizationUsed').checked)
//  {
//    //Temporary solution for contanization
//    //alert("This is not Activated, It is under construction");
//    //document.aascOptionProfileForm.containerizationUsed.value="N";
//    //document.aascOptionProfileForm.containerizationUsed.checked =false;
//    document.aascOptionProfileForm.containerizationUsed.value="Y";
//    document.aascOptionProfileForm.containerizationUsed.checked =true;
//   // alert("document.aascOptionProfileForm.containerizationUsed.value :: "+document.aascOptionProfileForm.containerizationUsed.value);
// //   alert("document.aascOptionProfileForm.containerizationUsed.checked :: "+document.aascOptionProfileForm.containerizationUsed.checked);
//  }
//  else
//  {
// // alert("else");
//    document.aascOptionProfileForm.containerizationUsed.value="N";
//  }
////  alert("document.aascOptionProfileForm.containerizationUsed.value :: "+document.aascOptionProfileForm.containerizationUsed.value);
//}

////Gururaj code end
//function onClickEditShipToDetails()
//{
//  if(document.aascOptionProfileForm.editShipToAddress.checked)
//  {
//  shipWindow =  window.open("aascEditShipToDetails.jsp?","Post",'width=400,height=150,top=400,left=500,scrollbars=yes, resizable=no');
//  if (window.focus)
//  {
//  shipWindow.focus();
//  }
//  }
//}

//SC_UPS Shipping - Setting the generate commercial invoice profile option based on international shipping data profile option

//function onClickPrintDetails()
//{
//  
//  shipWindow =  window.open("aascPrintOptions.jsp?","Post",'width=400,height=150,top=400,left=500,scrollbars=yes, resizable=no');
//  if (window.focus)
//  {
//  shipWindow.focus();
//  }
//  
//}

//
//function checkBoxEditAllShipTo()
//{
//
//  if(document.aascOptionProfileForm.editShipToAddress.checked==true)
//  {
//   document.aascOptionProfileForm.editAllShipToImg.src ="buttons/aascDetails1.png";
//
//  }
//  else
//  {
//   document.aascOptionProfileForm.editAllShipToImg.src ="buttons/aascDetailsOff1.png";
//  }
//}

//
//function validateEditShipToAddr()
//{
//         //Added By Narasimha Earla 24-Aug-2010
//        if(!document.aascOptionProfileForm.editShipToAddress.checked) //If the checked checkbox is unchecked and pressed the save button in PO page.
//        {
//            //alert("Inside if");
//            document.aascOptionProfileForm.customerNameFlag.value="N";
//            document.aascOptionProfileForm.addrLinesFlag.value="N";
//            document.aascOptionProfileForm.cityFlag.value="N";
//            document.aascOptionProfileForm.stateFlag.value="N";
//            document.aascOptionProfileForm.postalCodeFlag.value="N";
//            document.aascOptionProfileForm.countryCodeFlag.value="N";
//        }
//        else if((document.aascOptionProfileForm.editShipToSaveStatus.value=="N" && document.aascOptionProfileForm.editShipToDBFlag.value!="Y")||
//        (document.aascOptionProfileForm.customerNameFlag.value == "" &&
//        document.aascOptionProfileForm.addrLinesFlag.value == "" &&
//        document.aascOptionProfileForm.cityFlag.value == "" &&
//        document.aascOptionProfileForm.stateFlag.value == "" &&
//        document.aascOptionProfileForm.postalCodeFlag.value == "" &&
//        document.aascOptionProfileForm.countryCodeFlag.value == "" ))
//        {
//         //(1)Already unchecked checkbox(from Db i.e editShipToDBFlag=N) is checked and without opening the details page and pressed the save button in PO page. (Or)
//         //(2)if we select the new organization from the PO page where the Editshiptoaddress is 'Y' and we directly pressed the save in PO page then all fields(hidden fields in PO) in editshiptodetails page should be checked.
//
//            var ConfirmStatus = confirm ("Checked all checkboxes in EditShipToDetails PopUp Page and Saving it !!!")
//            if (ConfirmStatus==false)
//            {
//              //If cancel button of confirm box is clicked
//              return false;
//            }
//
//            document.aascOptionProfileForm.customerNameFlag.value="Y";
//            document.aascOptionProfileForm.addrLinesFlag.value="Y";
//            document.aascOptionProfileForm.cityFlag.value="Y";
//            document.aascOptionProfileForm.stateFlag.value="Y";
//            document.aascOptionProfileForm.postalCodeFlag.value="Y";
//            document.aascOptionProfileForm.countryCodeFlag.value="Y";
//        }
//        return true;
//}


/*function BolGenerationmethodInput()
{
var bolGen=document.aascOptionProfileForm.bolGenerationmethod.value;
if(document.aascOptionProfileForm.accessLevel.value==3)
 {
if(bolGen=="NONE")
{
document.aascOptionProfileForm.bolGenerationmethodInput.disabled=true;
document.aascOptionProfileForm.bolGenerationmethodInput.value="";
}
else
{
document.aascOptionProfileForm.bolGenerationmethodInput.disabled=false;
}
}
}*/


//// Added by ravi teja for Elkay enhancement
//function validateAveragepalletWeight()
//{
//  var averagePalletWeight=document.getElementById('averagePalletWeight').value;
//  if((isNaN(averagePalletWeight)))
//  {
//      alert('Please enter numeric value for the Average Pallet Weight');
//      document.getElementById('averagePalletWeight').focus();
//  }
//}

//added for Online help
function openNewWindow (URL, windowName, windowOptions)
      {
          var window = getWindow (URL, windowName, windowOptions);
      }

      function getWindow(URL, windowName, windowOptions)
      {
        //alert("URL"+URL);
        //alert("windowName"+windowName);
        //alert("windowOptions"+windowOptions);
          var newWindow = open (URL, windowName, windowOptions)
          newWindow.focus();
          return window;
      }

 ////////////////////////////////////////////////////////////////////////////////////////////////////////
   //For editable select box
 ////////////////////////////////////////////////////////////////////////////////////////////////////////   

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
      //e.which = ''; //this property has only a getter.
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
      alert('Please Enter Upto 35 Characters Only ');
      getdropdown.focus();
      return false;
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
          vEditString+=String.fromCharCode(keycode);
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
  

//This funcion "stopEnterKeyPress(evt)"added by Khaja for Browser Campatability
function stopEnterKeyPress(evt) {
  var evt = (evt) ? evt : ((event) ? event : null);
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
 
  if ((evt.keyCode == 13) && ((node.type=="text") || (node.type=="checkbox") || (node.type=="radio")))  {return false;}

}
document.onkeypress = stopEnterKeyPress;   ///This line belong to the  above function stopEnterKeyPress(evt) to stop Form submition on press of Enter key
