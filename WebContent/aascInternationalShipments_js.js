/*==========================================================================================================+
|  DESCRIPTION                                                                                              |
|    javascript file for the aascInternationalShipments.jsp  validation                                     |
|    Author Y Pradeep                                                                                       |
|    Version   1                                                                                            |                                                                            
|    Creation 28-JAN-2015                                                                                   |
History:
    28/01/2015   Y Pradeep    Added this file for Fedex Indernation page.
    11/02/2015   Y Pradeep    Added code for proper validations after shipping.
    16/02/2015   Y Pradeep    Modified code to generate order number on click og Ship button in Shipping page.
    10/03/2015   Y Pradeep    Alligned code in proper order.
    15/03/2015   Y Pradeep    Added code to open Commercial Invoice in new window.
    10/11/2015   Suman G      Added code to fix #3946
    24/11/2015   Mahesh V     Added code to fix #4024
    02/03/2016   Suman G      Added Naveen Code
    11/03/2016   Suman G      Commented code to fix #4392
+===========================================================================================================*/

function saveDetails()
{             
     var accNo = document.aascINTLShipmentsForm.AccNumber.value;
     var conCode = document.aascINTLShipmentsForm.countryCode.value;
     
   //  if(document.aascINTLShipmentsForm.payerType.value == "THIRDPARTY")
   //  {
       if(accNo == '' )
       {
         alert(" Carrier Account Number Must be provided ");
         document.aascINTLShipmentsForm.AccNumber.focus();
         return false;
       }
       if(conCode == '' )
       {
         alert(" Carrier CountryCode Must be provided ");
         document.aascINTLShipmentsForm.countryCode.focus();
         return false;
       }
      
    // }
     
     var senderTIN = document.aascINTLShipmentsForm.SenderTINOrDUNS.value;
     var senderTINType = document.aascINTLShipmentsForm.SenderTINOrDUNSType.value;
     var exempNo = document.aascINTLShipmentsForm.AESOrFTSRExemptionNumber.value;
     
     if(senderTIN != '' && senderTINType == '')
     {
     alert(" Please select one Sender Tax Type ");
     aascINTLShipmentsForm.SenderTINOrDUNSType.focus();
     return false;
     }
     
   //  if(senderTIN == '' && senderTINType != '')
   //  {
    // alert(" Please enter SenderTINOrDUNS Number ");
    // aascINTLShipmentsForm.SenderTINOrDUNS.focus();
    // return false;
    // }
     var totalCustomValue = document.aascINTLShipmentsForm.TotalCustomsValue.value;
     if(totalCustomValue >= 2500 && exempNo == '')
     {
//     alert("Please enter ITN/FTR Exemption Number ");
//     aascINTLShipmentsForm.AESOrFTSRExemptionNumber.focus();
//     return false;
     }
     
     var totalCvalue = document.aascINTLShipmentsForm.TotalCustomsValue.value;
     var lineCustomValue = document.aascINTLShipmentsForm.commCustomValue.value;
     //alert("lineCustomValue"+lineCustomValue)
     if(lineCustomValue == '0.0')
     {
       alert(" Please enter atleast one Commodity ");
       //document.aascINTLShipmentsForm.TotalCustomsValue.focus();
       return false;
     }
     
     var payerType1 = document.aascINTLShipmentsForm.payerType.value
     if(payerType1 == '')
     {
       alert(" Please Select a Payer Type ");
       document.aascINTLShipmentsForm.payerType.focus();
       return false;
     }
     
     var termsOfSale = document.aascINTLShipmentsForm.TermsOfSale.value;
      var generateCI = document.aascINTLShipmentsForm.generateCI.value;
     if(termsOfSale == '' && generateCI == 'Y')
     {
       alert(" Please Select Valid Terms of Sale ");
       document.aascINTLShipmentsForm.TermsOfSale.focus();
       return false;
     }
     
     var frCharge = document.aascINTLShipmentsForm.FreightCharge.value;
     var inCharge = document.aascINTLShipmentsForm.InsuranceCharge.value;
     var msCharge = document.aascINTLShipmentsForm.TaxesOrMiscellaneousCharge.value;
     var lineCustomValue = document.aascINTLShipmentsForm.commCustomValue.value;
     var totalCustomValue = document.aascINTLShipmentsForm.TotalCustomsValue.value;
    /* 
     if( parseFloat(totalCustomValue) < (parseFloat(lineCustomValue)+parseFloat(msCharge)+parseFloat(inCharge)+parseFloat(frCharge))){
         alert("Please Enter Total Custom Value greater then or equal to \n (Total Commodity Value + Insurance Charge + Frieght Charge + Taxes or MiscellaneousCharge)");
         return false;
     }*/
     var decimalCount =0;
     for (var i = 0; i < frCharge.length; i++) {
  	    if(!(frCharge.charCodeAt(i)>47 && frCharge.charCodeAt(i)<58) && frCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascINTLShipmentsForm.FreightCharge.focus();
  	       return false;
        }
       if(frCharge.charCodeAt(i) == 46)
        {
         decimalCount = decimalCount + 1;
        }
    }
    if(decimalCount > 1)
    {
      alert("Please Enter valid Decimal Value only");
      document.aascINTLShipmentsForm.FreightCharge.focus();
  	  return false;
    }
    decimalCount = 0;
     for (var i = 0; i < inCharge.length; i++) {
  	    if(!(inCharge.charCodeAt(i)>47 && inCharge.charCodeAt(i)<58) && inCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascINTLShipmentsForm.InsuranceCharge.focus();
  	       return false;
        }
        if(inCharge.charCodeAt(i) == 46)
        {
         decimalCount = decimalCount + 1;
        }
     }
     if(decimalCount > 1)
     {
       alert("Please Enter valid Decimal Value only");
       document.aascINTLShipmentsForm.InsuranceCharge.focus();
  	   return false;
     }
     decimalCount = 0;
     for (var i = 0; i < msCharge.length; i++) {
        if(!(msCharge.charCodeAt(i)>47 && msCharge.charCodeAt(i)<58) && msCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascINTLShipmentsForm.TaxesOrMiscellaneousCharge.focus();
  	       return false;
        }
        if(msCharge.charCodeAt(i) == 46)
        {
         decimalCount = decimalCount + 1;
        }
     }
     if(decimalCount > 1)
     {
       alert("Please Enter valid Decimal Value only");
       document.aascINTLShipmentsForm.TaxesOrMiscellaneousCharge.focus();
  	   return false;
     }
     
//     document.aascINTLShipmentsForm.actionType.value='SAVE';//SC_7.0_LB
    // document.aascINTLShipmentsForm.action="/ShipConsole-ViewController-context-root/AascIntlShipAction.do";
     //alert('dfed'+document.aascINTLShipmentsForm.actionType.value);
//     document.aascINTLShipmentsForm.submit();//SC_7.0_LB
     //alert('after submit');
        window.opener.document.DynaShipmentShipSaveForm.intTotalCustomsValue.value=document.aascINTLShipmentsForm.TotalCustomsValue.value;
        window.opener.document.DynaShipmentShipSaveForm.intlWeightValue.value=document.aascINTLShipmentsForm.commWeightValue.value;
        window.opener.document.DynaShipmentShipSaveForm.intlSaveFlag.value = "Y";
        
     
     var service = document.aascINTLShipmentsForm.service.value;
     //alert('service : '+service);
     
     //alert('********');
     //alert('11111 : '+service.contains('FREIGHT'));
     //alert('22222 : '+service.contains('Freight'));
     
     if((service.indexOf('FREIGHT')!= -1) || (service.indexOf('Freight') != -1) || (service.indexOf('freight') != -1))
     {
        //alert('Inside if loop');
        //var packingListEnclosed = document.aascINTLShipmentsForm.packingListEnclosed.value;
        var shippersLoadAndCount = document.aascINTLShipmentsForm.shippersLoadAndCount.value;
        var bookingConfirmationNumber = document.aascINTLShipmentsForm.bookingConfirmationNumber.value;
        
        //alert('shippersLoadAndCount : '+shippersLoadAndCount);
        //alert('bookingConfirmationNumber : '+bookingConfirmationNumber);
        
        if(shippersLoadAndCount == null || shippersLoadAndCount == '')
        {
          alert('Please enter value for Shippers Load And Count');
          document.aascINTLShipmentsForm.shippersLoadAndCount.focus();
          return false;
        }
        if(bookingConfirmationNumber == null || bookingConfirmationNumber == '')
        {
          alert("Please enter value for Booking Confirmation Number");
          document.aascINTLShipmentsForm.bookingConfirmationNumber.focus();
          return false;
          
        }
        
        if(!isInteger(shippersLoadAndCount))
        {
          alert('Please enter numeric value for Shippers Load And Count');
          
          document.aascINTLShipmentsForm.shippersLoadAndCount.focus();
          return false;
        } 
        
        
        if(shippersLoadAndCount.length > 5 || shippersLoadAndCount  == 0)  
        {
          alert('Shippers Load And Count must be in the range of 1 - 99999');
          document.aascINTLShipmentsForm.shippersLoadAndCount.focus();
          return false;
        }  
        
        if(bookingConfirmationNumber.length <8 || bookingConfirmationNumber.length > 12)  
        {
          alert('Booking Confirmation Number value must be 8 - 12 characters in length');
          document.aascINTLShipmentsForm.bookingConfirmationNumber.focus();
          return false;
        }  
     }
     
     document.aascINTLShipmentsForm.actionType.value='SAVE';
     document.aascINTLShipmentsForm.submit();
     
     //alert("closing ....");
     //window.close();
     /*
     window.opener.DynaShipmentShipSaveForm.intPayerType.value=document.aascINTLShipmentsForm.payerType.value;
     window.opener.DynaShipmentShipSaveForm.intAccNumber.value=document.aascINTLShipmentsForm.AccNumber.value;
     window.opener.DynaShipmentShipSaveForm.intcountryCode.value=document.aascINTLShipmentsForm.countryCode.value;
     window.opener.DynaShipmentShipSaveForm.intTermsOfSale.value=document.aascINTLShipmentsForm.TermsOfSale.value;
     window.opener.DynaShipmentShipSaveForm.intTotalCustomsValue.value=document.aascINTLShipmentsForm.TotalCustomsValue.value;
     window.opener.DynaShipmentShipSaveForm.intFreightCharge.value= document.aascINTLShipmentsForm.FreightCharge.value;
     window.opener.DynaShipmentShipSaveForm.intInsuranceCharge.value= document.aascINTLShipmentsForm.InsuranceCharge.value;
     window.opener.DynaShipmentShipSaveForm.intTaxesOrMiscellaneousCharge.value= document.aascINTLShipmentsForm.TaxesOrMiscellaneousCharge.value;
     window.opener.DynaShipmentShipSaveForm.intPurpose.value= document.aascINTLShipmentsForm.Purpose.value;
     window.opener.DynaShipmentShipSaveForm.intSenderTINOrDUNS.value= document.aascINTLShipmentsForm.SenderTINOrDUNS.value;
     window.opener.DynaShipmentShipSaveForm.intSenderTINOrDUNSType.value= document.aascINTLShipmentsForm.SenderTINOrDUNSType.value;
     window.close();
     */
     
}

function loadDetails()
{    
    var CCode = document.aascINTLShipmentsForm.CountryOfManufacture.value;
    var QUnits = document.aascINTLShipmentsForm.QuantityUnits.value;
    var shipFlag = document.aascINTLShipmentsForm.shipFlag.value;//added this line
    var country = document.aascINTLShipmentsForm.countryCode.value;
   var maskAccountCheck = window.opener.document.DynaShipmentShipSaveForm.maskAccountCheck.value;
    var acNumber = window.opener.document.DynaShipmentShipSaveForm.carrierAccountNumber.value;
     document.getElementById("AccNumberHid").value = acNumber;
    if (maskAccountCheck == "Y"){
//    alert("in mask");
    document.getElementById("AccNumber").value = acNumber;
//        if(window.opener.document.DynaShipmentShipSaveForm.carrierPayMethod.value == 'PP'){ 
//            if(shipFlag != 'Y')
//            mask();
//        }
    }
    else{
     document.getElementById("AccNumber").value = acNumber;
    }
     if(country == '')
        document.aascINTLShipmentsForm.countryCode.value = 'US';
     //alert('load::'+window.opener.document.DynaShipmentShipSaveForm.carrierPayMethod.value);     
      document.aascINTLShipmentsForm.payerType.value = window.opener.document.DynaShipmentShipSaveForm.carrierPayMethod.value;
      
      
     if(window.opener.document.DynaShipmentShipSaveForm.carrierPayMethod.value == 'PP'){
        document.aascINTLShipmentsForm.payerType.value ='SENDER'; 
      
     } else if(window.opener.document.DynaShipmentShipSaveForm.carrierPayMethod.value == 'CG'){
        document.aascINTLShipmentsForm.payerType.value ='RECIPIENT';       
     }
     else if(window.opener.document.DynaShipmentShipSaveForm.carrierPayMethod.value == 'TP'){
        document.aascINTLShipmentsForm.payerType.value ='THIRDPARTY'; 
     }

     if(CCode == '')
         document.aascINTLShipmentsForm.CountryOfManufacture.value = 'US';
    
    var flagShip = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;
    //alert("shipFlag"+shipFlag);
    document.aascINTLShipmentsForm.shipment.value = window.opener.document.DynaShipmentShipSaveForm.shipmentType.value;
    
    document.aascINTLShipmentsForm.orderNo.value = window.opener.document.DynaShipmentShipSaveForm.orderNum.value;
    
    var shipmentStatusFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value ;  //shipmentStatusFlag.value; 
     //alert('shipmentStatusFlag : '+shipmentStatusFlag);
    var service = document.getElementById('serviceID').value;
    //alert('service : '+service);
    
    /*if(shipmentStatusFlag == 'Y')
    {
      if((service.indexOf('FREIGHT')!= -1) || (service.indexOf('Freight') != -1))
       {
           document.aascINTLShipmentsForm.packingListEnclosed.disabled = true;
           document.aascINTLShipmentsForm.shippersLoadAndCount.disabled = true;
           document.aascINTLShipmentsForm.bookingConfirmationNumber.disabled = true;
       }
    }
    else
    {
      if((service.indexOf('FREIGHT')!= -1) || (service.indexOf('Freight') != -1))
       {
           document.aascINTLShipmentsForm.packingListEnclosed.disabled = false;
           document.aascINTLShipmentsForm.shippersLoadAndCount.disabled = false;
           document.aascINTLShipmentsForm.bookingConfirmationNumber.disabled = false;
       }
    }*/
     if(flagShip == 'Y'){
       document.aascINTLShipmentsForm.addComm.disabled = true;
      // document.aascINTLShipmentsForm.editComm.disabled = true;
       document.aascINTLShipmentsForm.delComm.disabled = true;
       document.aascINTLShipmentsForm.save.disabled = true;
       document.aascINTLShipmentsForm.payerType.disabled = true;
       document.aascINTLShipmentsForm.AccNumber.disabled = true;
       document.aascINTLShipmentsForm.countryCode.disabled = true;
       document.aascINTLShipmentsForm.TermsOfSale.disabled = true;
     //  document.aascINTLShipmentsForm.TotalCustomsValue.disabled = true;
       document.aascINTLShipmentsForm.FreightCharge.disabled = true;
       document.aascINTLShipmentsForm.InsuranceCharge.disabled = true;
       document.aascINTLShipmentsForm.TaxesOrMiscellaneousCharge.disabled = true;
       document.aascINTLShipmentsForm.Purpose.disabled = true;
       document.aascINTLShipmentsForm.SenderTINOrDUNS.disabled = true;
       document.aascINTLShipmentsForm.SenderTINOrDUNSType.disabled = true;
       document.aascINTLShipmentsForm.AESOrFTSRExemptionNumber.disabled = true;
       
       if((service.indexOf('FREIGHT')!= -1) || (service.indexOf('Freight') != -1))
       {
           document.aascINTLShipmentsForm.packingListEnclosed.disabled = true;
           document.aascINTLShipmentsForm.shippersLoadAndCount.disabled = true;
           document.aascINTLShipmentsForm.bookingConfirmationNumber.disabled = true;
       }
       
       var descriptionVal = document.aascINTLShipmentsForm.description.value;
        if(descriptionVal.length < 1){
            getIntlCommodityLineDetails(); 
       }
       
       document.aascINTLShipmentsForm.selImporterName.disabled = true;
       document.aascINTLShipmentsForm.importerName.disabled = true;
       document.aascINTLShipmentsForm.importerCompName.disabled = true;
       document.aascINTLShipmentsForm.importerPhoneNum.disabled = true;
       document.aascINTLShipmentsForm.importerAddress1.disabled = true;
       document.aascINTLShipmentsForm.importerAddress2.disabled = true;
       document.aascINTLShipmentsForm.importerCity.disabled = true;
       document.aascINTLShipmentsForm.importerState.disabled = true;
       document.aascINTLShipmentsForm.importerPostalCode.disabled = true;
       document.aascINTLShipmentsForm.importerCountryCode.disabled = true;
       document.aascINTLShipmentsForm.ImporterTINOrDUNS.disabled = true;
       document.aascINTLShipmentsForm.ImporterTINOrDUNSType.disabled = true;
       document.aascINTLShipmentsForm.selBrokerName.disabled = true;
       document.aascINTLShipmentsForm.brokerName.disabled = true;
       document.aascINTLShipmentsForm.brokerCompName.disabled = true;
       document.aascINTLShipmentsForm.brokerPhoneNum.disabled = true;
       document.aascINTLShipmentsForm.brokerAddress1.disabled = true;
       document.aascINTLShipmentsForm.brokerAddress2.disabled = true;
       document.aascINTLShipmentsForm.brokerCity.disabled = true;
       document.aascINTLShipmentsForm.brokerState.disabled = true;
       document.aascINTLShipmentsForm.brokerPostalCode.disabled = true;
       document.aascINTLShipmentsForm.brokerCountryCode.disabled = true;
       
    }else{
       document.aascINTLShipmentsForm.addComm.disabled = false;
       document.aascINTLShipmentsForm.editComm.disabled = false;
       document.aascINTLShipmentsForm.delComm.disabled = false;
       document.aascINTLShipmentsForm.save.disabled = false;
       document.aascINTLShipmentsForm.payerType.disabled = false;
       document.aascINTLShipmentsForm.AccNumber.disabled = false;
       document.aascINTLShipmentsForm.countryCode.disabled = false;
       document.aascINTLShipmentsForm.TermsOfSale.disabled = false;
     //  document.aascINTLShipmentsForm.TotalCustomsValue.disabled = false;
       document.aascINTLShipmentsForm.FreightCharge.disabled = false;
       document.aascINTLShipmentsForm.InsuranceCharge.disabled = false;
       document.aascINTLShipmentsForm.TaxesOrMiscellaneousCharge.disabled = false;
       document.aascINTLShipmentsForm.Purpose.disabled = false;
       document.aascINTLShipmentsForm.SenderTINOrDUNS.disabled = false;
       document.aascINTLShipmentsForm.SenderTINOrDUNSType.disabled = false;
       document.aascINTLShipmentsForm.AESOrFTSRExemptionNumber.disabled = false;
       
       if((service.indexOf('FREIGHT')!= -1) || (service.indexOf('Freight') != -1))
       {
           document.aascINTLShipmentsForm.packingListEnclosed.disabled = false;
           document.aascINTLShipmentsForm.shippersLoadAndCount.disabled = false;
           document.aascINTLShipmentsForm.bookingConfirmationNumber.disabled = false;
       }
       //Added below line of code
        var descriptionVal = document.aascINTLShipmentsForm.description.value;
        if(descriptionVal.length < 1){
            getIntlCommodityLineDetails(); 
       }
       
       document.aascINTLShipmentsForm.selImporterName.disabled = false;
       document.aascINTLShipmentsForm.importerName.disabled = false;
       document.aascINTLShipmentsForm.importerCompName.disabled = false;
       document.aascINTLShipmentsForm.importerPhoneNum.disabled = false;
       document.aascINTLShipmentsForm.importerAddress1.disabled = false;
       document.aascINTLShipmentsForm.importerAddress2.disabled = false;
       document.aascINTLShipmentsForm.importerCity.disabled = false;
       document.aascINTLShipmentsForm.importerState.disabled = false;
       document.aascINTLShipmentsForm.importerPostalCode.disabled = false;
       document.aascINTLShipmentsForm.importerCountryCode.disabled = false;
       document.aascINTLShipmentsForm.ImporterTINOrDUNS.disabled = false;
       document.aascINTLShipmentsForm.ImporterTINOrDUNSType.disabled = false;
       document.aascINTLShipmentsForm.selBrokerName.disabled = false;
       document.aascINTLShipmentsForm.brokerName.disabled = false;
       document.aascINTLShipmentsForm.brokerCompName.disabled = false;
       document.aascINTLShipmentsForm.brokerPhoneNum.disabled = false;
       document.aascINTLShipmentsForm.brokerAddress1.disabled = false;
       document.aascINTLShipmentsForm.brokerAddress2.disabled = false;
       document.aascINTLShipmentsForm.brokerCity.disabled = false;
       document.aascINTLShipmentsForm.brokerState.disabled = false;
       document.aascINTLShipmentsForm.brokerPostalCode.disabled = false;
       document.aascINTLShipmentsForm.brokerCountryCode.disabled = false;
    }
    
    /*
     document.aascINTLShipmentsForm.payerType.value = window.opener.DynaShipmentShipSaveForm.intPayerType.value;
     document.aascINTLShipmentsForm.AccNumber.value = window.opener.DynaShipmentShipSaveForm.intAccNumber.value;
     document.aascINTLShipmentsForm.countryCode.value = window.opener.DynaShipmentShipSaveForm.intcountryCode.value;
     document.aascINTLShipmentsForm.TermsOfSale.value = window.opener.DynaShipmentShipSaveForm.intTermsOfSale.value;
     document.aascINTLShipmentsForm.TotalCustomsValue.value = window.opener.DynaShipmentShipSaveForm.intTotalCustomsValue.value;
     document.aascINTLShipmentsForm.FreightCharge.value = window.opener.DynaShipmentShipSaveForm.intFreightCharge.value;
     document.aascINTLShipmentsForm.InsuranceCharge.value = window.opener.DynaShipmentShipSaveForm.intInsuranceCharge.value;
     document.aascINTLShipmentsForm.TaxesOrMiscellaneousCharge.value = window.opener.DynaShipmentShipSaveForm.intTaxesOrMiscellaneousCharge.value;
     document.aascINTLShipmentsForm.Purpose.value = window.opener.DynaShipmentShipSaveForm.intPurpose.value;
     document.aascINTLShipmentsForm.SenderTINOrDUNS.value = window.opener.DynaShipmentShipSaveForm.intSenderTINOrDUNS.value;
     document.aascINTLShipmentsForm.SenderTINOrDUNSType.value = window.opener.DynaShipmentShipSaveForm.intSenderTINOrDUNSType.value
     */
    // alert("SIZE"+document.aascINTLShipmentsForm.commodityLine.length);
     
          
}


   function mask(){
  
var account1 = document.getElementById("AccNumber");
var maskedAcc1 = new Array(account1.value.length-3).join('X') + account1.value.substr(account1.value.length-4, 4);
document.getElementById("AccNumber").value = maskedAcc1;
}

function genSelect()
{
    //document.forms['aascINTLShipmentsForm'].commodityLine.options.length = 5;
    document.forms['aascINTLShipmentsForm'].commodityLine.options.size = 5;
    document.forms['aascINTLShipmentsForm'].commodityLine.options[0] =  new Option("Product       Unit        Weight       Value","1",true,false);
    document.forms['aascINTLShipmentsForm'].commodityLine.options[1] =  new Option("-----------------------------------------------------","2",true,false);
    // document.forms['SigTrackingPageMainForm'].inputNumberText.options.length = 0; 
    //   document.forms['SigTrackingPageMainForm'].inputNumberText.options[0] =  new Option("","",true,false);
}

function saveComm(){

 var noPieces = document.aascINTLShipmentsForm.NumberOfPieces.value;
 var des = document.aascINTLShipmentsForm.description.value;
 var Contry = document.aascINTLShipmentsForm.CountryOfManufacture.value;
 var weight = document.aascINTLShipmentsForm.Weight.value;
 var quantity = document.aascINTLShipmentsForm.Quantity.value;
 var quaUnits = document.aascINTLShipmentsForm.QuantityUnits.value;
 var customs = document.aascINTLShipmentsForm.CustomsValue.value;
 var unitValue = document.aascINTLShipmentsForm.UnitPrice.value;
 var expNo = document.aascINTLShipmentsForm.ExportLicenseNumber.value;
 var expDate = document.aascINTLShipmentsForm.ExportLicenseExpirationDate.value;
 
  if(navigator.appName !='Microsoft Internet Explorer')
  {
    expDate= expDate.trim(); //khaja added this line for bug fix 1128 
  }

  if(noPieces == ''){
    alert("  Please Enter Number of Packages for Item  ");
    document.aascINTLShipmentsForm.NumberOfPieces.focus();
    return false;
  }
  for (var i = 0; i < noPieces.length; i++) {
    if(!(noPieces.charCodeAt(i)>47 && noPieces.charCodeAt(i)<58))
    {
        alert("Please Enter valid Numbers only");
        document.aascINTLShipmentsForm.NumberOfPieces.focus();
  	return false;
    }
    
  }
 
  if(des == ''){
    alert("  Please Enter Product name/Description  ");
    document.aascINTLShipmentsForm.description.focus();
    return false;
  }
        for (var i = 0; i < des.length; i++) 
        {
  	
            if(des.charCodeAt(i)==43||des.charCodeAt(i)==42||des.charCodeAt(i)==60||des.charCodeAt(i)==62||des.charCodeAt(i)==34)
            {
            alert("Please enter valid Product Description");
            document.aascINTLShipmentsForm.Description.focus();
                return false;
            }
        }
  if(Contry == ''){
    alert("  Please Enter Country Of Manufacture of the Item  ");
    document.aascINTLShipmentsForm.CountryOfManufacture.focus();
    return false;
  }
  if(weight == ''){
    alert("  Please Enter Weight of Item  ");
    document.aascINTLShipmentsForm.Weight.focus();
    return false;
  }
  var decimalCount = 0;
  for (var i = 0; i < weight.length; i++) {
    if(!(weight.charCodeAt(i)>47 && weight.charCodeAt(i)<58) && weight.charCodeAt(i)!=46 )
    {
        alert("Please Enter valid Decimal Value only");
        document.aascINTLShipmentsForm.Weight.focus();
  	return false;
    }
    if(weight.charCodeAt(i) == 46)
    {
        decimalCount = decimalCount + 1;
    }
  }
  if(decimalCount > 1)
  {
    alert("Please Enter valid Decimal Value only");
    document.aascINTLShipmentsForm.Weight.focus();
  	return false;
  }
  if(quantity == ''){
    alert("  Please Enter Quantity of Item  ");
    document.aascINTLShipmentsForm.Quantity.focus();
    return false;
  }
  for (var i = 0; i < quantity.length; i++) {
    if(!(quantity.charCodeAt(i)>47 && quantity.charCodeAt(i)<58))
    {
        alert("Please Enter valid Numbers only");
        document.aascINTLShipmentsForm.Quantity.focus();
  	return false;
    }
  }
  if(quaUnits == ''){
    alert("  Please Enter Unit of Measure for Quantity of the Item ");
    document.aascINTLShipmentsForm.QuantityUnits.focus();
    return false;
  }
  if(unitValue == ''){
    alert("  Please Enter Unit Price of Item  ");
    document.aascINTLShipmentsForm.UnitPrice.focus();
    return false;
  }
  decimalCount = 0;
  for (var i = 0; i < unitValue.length; i++) {
    if(!(unitValue.charCodeAt(i)>47 && unitValue.charCodeAt(i)<58) && unitValue.charCodeAt(i)!=46 )
    {
        alert("Please Enter valid Decimal Value only");
        document.aascINTLShipmentsForm.UnitPrice.focus();
  	return false;
    }
    if(unitValue.charCodeAt(i) == 46)
    {
        decimalCount = decimalCount + 1;
    }
  }
  if(decimalCount > 1)
  {
    alert("Please Enter valid Decimal Value only");
    document.aascINTLShipmentsForm.UnitPrice.focus();
  	return false;
  }
  if(customs == ''){
    alert("  Please Enter Customs Value of Item  ");
    document.aascINTLShipmentsForm.CustomsValue.focus();
    return false;
  }
  decimalCount = 0;
  for (var i = 0; i < customs.length; i++) {
    if(!(customs.charCodeAt(i)>47 && customs.charCodeAt(i)<58) && customs.charCodeAt(i)!=46 )
    {
        alert("Please Enter valid Decimal Value only");
        document.aascINTLShipmentsForm.CustomsValue.focus();
  	return false;
    }
    if(customs.charCodeAt(i) == 46)
    {
     decimalCount = decimalCount + 1;
    }
  }
  if(decimalCount > 1)
  {
    alert("Please Enter valid Decimal Value only");
    document.aascINTLShipmentsForm.CustomsValue.focus();
  	return false;
  }
  if (expNo != ''){
      if(expNo.length != 7){
//        alert(" ExportLicense Number should contain one Alpha and six numeric value ");
//        document.aascINTLShipmentsForm.ExportLicenseNumber.focus();
//        return false;
      }
      if(expNo.charCodeAt(0)<65 || (expNo.charCodeAt(0)>90 && expNo.charCodeAt(0)<97 )|| expNo.charCodeAt(0)>122)
      {
//        alert(" ExportLicense Number should contain one Alpha and six numeric value ");
//        document.aascINTLShipmentsForm.ExportLicenseNumber.focus();
//        return false;
      
      }
      for (var i = 1; i < expNo.length; i++) {
              if(!(expNo.charCodeAt(i)>47 && expNo.charCodeAt(i)<58)){
       
//              alert(" ExportLicense Number should contain one Alpha and six numeric value ");
//              document.aascINTLShipmentsForm.ExportLicenseNumber.focus();
//              return false;
          }    
        }
  }
  
  if ((expNo != '' && expDate == '') || (expNo == '' && expDate != ''))
  {
//     alert(" Please Enter valid Export License Number and \n valid Export License Expiration Date ");
//     document.aascINTLShipmentsForm.ExportLicenseNumber.focus();
//     return false;
  }
  /*
    if(expDate.length != 10){
       alert(" Please Enter ExportLicense Expiration Date in YYYY-MM-DD format ");
       document.aascINTLShipmentsForm.ExportLicenseExpirationDate.focus();
       return false;
    }
    */
    //validate Date started

    var dtCh= "-";
    var minYear=1990;
    var maxYear=2100;
    /*function isInteger(s){
        var i;
        for (i = 0; i < s.length; i++){   
            // Check that current character is number.
            var c = s.charAt(i);
            if (((c < "0") || (c > "9"))) 
                return false;
        }
        // All characters are numbers.
        return true;
    }*/
    
    function stripCharsInBag(s, bag){
        var i;
        var returnString = "";
        // Search through string's characters one by one.
        // If character is not in bag, append to returnString.
        for (i = 0; i < s.length; i++){   
            var c = s.charAt(i);
            if (bag.indexOf(c) == -1)
                returnString += c;

        }
        return returnString;

    }

    function daysInFebruary (year){
        return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
    }

    function DaysArray(n) {
        for (var i = 1; i <= n; i++) {
            this[i] = 31
            if (i==4 || i==6 || i==9 || i==11) 
            {this[i] = 30}
    
            if (i==2) 
            {this[i] = 29}

	} 
        return this
}

    function isDate(dtStr){
        if(dtStr != ''){
            var daysInMonth = DaysArray(12)
            var pos1=dtStr.indexOf(dtCh)
            var pos2=dtStr.indexOf(dtCh,pos1+1)
            var strYear=dtStr.substring(0,pos1)
            var strMonth=dtStr.substring(pos1+1,pos2)
            var strDay=dtStr.substring(pos2+1)	
            strYr=strYear
            if (strDay.charAt(0)=="0" && strDay.length>1) 
                strDay=strDay.substring(1)

            if (strMonth.charAt(0)=="0" && strMonth.length>1) 
                strMonth=strMonth.substring(1)

            for (var i = 1; i <= 3; i++) {
                if (strYr.charAt(0)=="0" && strYr.length>1) 
                    strYr=strYr.substring(1)

            }
            month=parseInt(strMonth)
            day=parseInt(strDay)
            year=parseInt(strYr)
            if (pos1==-1 || pos2==-1){
                alert("The Date Format Should Be : YYYY-MM-DD")
                return false
            }
            if (strMonth.length<1 || month<1 || month>12){
                alert("Please Enter A Valid Month")
                return false
            }
            if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
                alert("Please Enter A Valid Day")
                return false
            }
            if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
                alert("Please Enter A Valid 4 Digit Year Between "+minYear+" And "+maxYear)
                return false
            }
            if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
                alert("Please Enter A Valid Date")
                return false
            }
            return true
        }
    }

    //end of date validation
    if(isDate(expDate) == false){
       //alert(" Please Enter ExportLicense Expiration Date in YYYY-MM-DD format ");
       document.aascINTLShipmentsForm.ExportLicenseExpirationDate.focus();
       return false;
    
    }
    //    var commLength = document.aascINTLShipmentsForm.commodityLine.length;
    //    if(document.aascINTLShipmentsForm.actionStr.value == 'ADD')
    //    {
    //    if(commLength == 22 ){
    //       alert(" Only 20 Commodity Items are allowed for save ");
    //       return false;
    //    }
    //    }
      
    
    // alert("ExportLicenseExpirationDate:"+document.aascINTLShipmentsForm.ExportLicenseExpirationDate.value);
    document.aascINTLShipmentsForm.selectLength.value = document.aascINTLShipmentsForm.commodityLine.length + 1;
    document.aascINTLShipmentsForm.actionType.value='ADD';
    document.aascINTLShipmentsForm.submit();
    
    //window.opener.DynaShipmentShipSaveForm.intlSaveFlag.value = "Y"

}

function genOptions()
{
    var anOption = document.createElement("OPTION") 
    var Product = document.aascINTLShipmentsForm.NumberOfPieces.value
    var Unit = document.aascINTLShipmentsForm.Quantity.value
    var Weight = document.aascINTLShipmentsForm.Weight.value
    var Value = document.aascINTLShipmentsForm.UnitPrice.value
    var optionValue = document.aascINTLShipmentsForm.commodityLine.length
    
    
    //alert("Product"+Product);
    //alert("optionValue"+optionValue);
    
    if(Product == null || Product ==''||Product == 'null' || Product ==' '){
        document.aascINTLShipmentsForm.NumberOfPieces.focus();
    }else{
        //document.aascINTLShipmentsForm.commodityLine.options.add(anOption) 
        //anOption.innerText =  " "+Product+"        "+Unit+"       "+Weight+"       "+Value;
        //anOption.Value = "3"//optionValue + 1 
        document.forms['aascINTLShipmentsForm'].commodityLine.options[optionValue] =  new Option(" "+Product+"        "+Unit+"       "+Weight+"       "+Value,optionValue + 1,true,false);
        //alert("anOption.Value"+anOption.Value);
        
        document.aascINTLShipmentsForm.NumberOfPieces.value = "";
        document.aascINTLShipmentsForm.Quantity.value = "";
        document.aascINTLShipmentsForm.Weight.value = "";
        document.aascINTLShipmentsForm.UnitPrice.value = "";
        document.aascINTLShipmentsForm.description.value = "";
        document.aascINTLShipmentsForm.CountryOfManufacture.value = "";
        document.aascINTLShipmentsForm.HarmonizedCode.value = "";
        document.aascINTLShipmentsForm.QuantityUnits.value = "";
        document.aascINTLShipmentsForm.CustomsValue.value = "";
        document.aascINTLShipmentsForm.ExportLicenseNumber.value = "";
        document.aascINTLShipmentsForm.ExportLicenseExpirationDate.value = "";
    
    }
}

function editOptions()
{
    var form = document.aascINTLShipmentsForm.commodityLine;
    
    for (var i=0; i<form.options.length; i++){
     if (form.options[i].selected==true){
       var value = form.options[document.aascINTLShipmentsForm.commodityLine.selectedIndex].value; // khaja add this line for bug fix 1129    
       break;
     }else{
      var value ="";
    }
     
    }
    
    if(value == 111 || value == 222){
    alert("  Please select a Commodity Item to Edit  ");
    return false;
    }
    if(value == '')
    {
    alert("  Please select a Commodity Item to Edit  ");
    return false;
    }
    //alert ("value :"+value);
    document.aascINTLShipmentsForm.opValue.value = value;
    enableCommodityDetailDiv();
    document.aascINTLShipmentsForm.actionType.value='EDIT';
    document.aascINTLShipmentsForm.submit();

}

function delOptions()
{

    var form = document.aascINTLShipmentsForm.commodityLine;
    
    for (var i=0; i<form.options.length; i++){
     if (form.options[i].selected==true){
       var value = form.options[document.aascINTLShipmentsForm.commodityLine.selectedIndex].value; // khaja add this line for bug fix 1129    
       break;
     }else{
      var value ="";
    }
     
    }
    
    //var value = form.options.value;
    
    if(value == 111 || value == 222)
    {
    alert("  Please select a Commodity Item to Delete  ");
    return false;
    }
    if(value == '')
    {
    alert("  Please select a Commodity Item to Delete  ");
    return false;
    }
    /*
    alert("delete");
    document.aascINTLShipmentsForm.commodityLine.options[document.aascINTLShipmentsForm.commodityLine.selectedIndex].text='';
    var length = document.aascINTLShipmentsForm.commodityLine.length;
    */
    //for(i=length;i>3;i--){
    //document.forms['aascINTLShipmentsForm'].commodityLine.options[i-1] = document.forms['aascINTLShipmentsForm'].commodityLine.options[i];
    
    //}
    //document.forms['aascINTLShipmentsForm'].commodityLine.options[i].text ='';
    //document.forms['aascINTLShipmentsForm'].commodityLine.options[i].value ='';
    //document.forms['aascINTLShipmentsForm'].commodityLine.options[i] =
    //document.aascINTLShipmentsForm.commodityLine.length = (document.aascINTLShipmentsForm.commodityLine.length)-1
    
    document.aascINTLShipmentsForm.actionType.value='DELETE';
    document.aascINTLShipmentsForm.submit();
}

function onChange()
{
    document.aascINTLShipmentsForm.AccNumber.value = "";
    document.aascINTLShipmentsForm.countryCode.value = "";
}

function disableFields(){

    if(document.aascINTLShipmentsForm.payerType.value == "THIRDPARTY")
     {
     document.aascINTLShipmentsForm.AccNumber.disabled = false;
     document.aascINTLShipmentsForm.countryCode.disabled = false;
     }
    else{
      ////document.aascINTLShipmentsForm.AccNumber.disabled = true;             /////////////////////////////
      ////document.aascINTLShipmentsForm.countryCode.disabled = true;
    }

}

function totalCustomValue()
{
   
     var frCharge = document.aascINTLShipmentsForm.FreightCharge.value;
     var inCharge = document.aascINTLShipmentsForm.InsuranceCharge.value;
     var msCharge = document.aascINTLShipmentsForm.TaxesOrMiscellaneousCharge.value;
     var lineCustomValue = document.aascINTLShipmentsForm.commCustomValue.value;
     var totalCustomValue = document.aascINTLShipmentsForm.TotalCustomsValue.value;
     //alert('totalCustomValue::'+totalCustomValue);
     
     if(msCharge == '')
          msCharge = 0.0;
     if(inCharge == '')
          inCharge = 0.0;
     if(frCharge == '')
          frCharge = 0.0;
    for (var i = 0; i < frCharge.length; i++) {
  	    if(!(frCharge.charCodeAt(i)>47 && frCharge.charCodeAt(i)<58) && frCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascINTLShipmentsForm.FreightCharge.focus();
  	       return false;
        }
     }
     for (var i = 0; i < inCharge.length; i++) {
  	    if(!(inCharge.charCodeAt(i)>47 && inCharge.charCodeAt(i)<58) && inCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascINTLShipmentsForm.InsuranceCharge.focus();
  	       return false;
        }
     }
     for (var i = 0; i < msCharge.length; i++) {
  	    if(!(msCharge.charCodeAt(i)>47 && msCharge.charCodeAt(i)<58) && msCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascINTLShipmentsForm.TaxesOrMiscellaneousCharge.focus();
  	       return false;
        }
     }      
     //alert('lineCustomValue::'+lineCustomValue);
     //alert('msCharge::'+msCharge);
     //alert('inCharge::'+inCharge);
     //alert('frCharge::'+frCharge);
     var rnum = parseFloat(lineCustomValue)+parseFloat(msCharge)+parseFloat(inCharge)+parseFloat(frCharge);
     //alert('rnum::'+rnum);
     if (rnum > 8191 && rnum < 10485) {
		     rnum = rnum-5000;
		     var newnumber = Math.round(rnum*Math.pow(10,2))/Math.pow(10,2);
		     newnumber = newnumber+5000;
	  } else {
		     var newnumber = Math.round(rnum*Math.pow(10,2))/Math.pow(10,2);
	  }
//alert('newnumber::'+newnumber);
      document.aascINTLShipmentsForm.TotalCustomsValue.value = newnumber;
     //document.aascINTLShipmentsForm.TotalCustomsValue.value = Math.round(parseFloat(lineCustomValue)+parseFloat(msCharge)+parseFloat(inCharge)+parseFloat(frCharge));
     
     
}

function isInteger(s)
{
        var i;

        for (i = 0; i < s.length; i++){   

                // Check that current character is number.

                var c = s.charAt(i);
                
                if (((c < "0") || (c > "9"))) return false;

        }

        // All characters are numbers.

        return true;
}
//Added below code 

function getIntlCommodityLineDetails(){
    var currentCommItem = document.aascINTLShipmentsForm.selCommItems.value;
    var shipFlag = document.aascINTLShipmentsForm.shipFlag.value;
    if(currentCommItem == 'Select')
    {
       disableCommodityDetailDiv();
       document.aascINTLShipmentsForm.CountryOfManufacture.value="US";
       document.aascINTLShipmentsForm.CustomsValue.value="";
       document.aascINTLShipmentsForm.NumberOfPieces.value="1";
       document.aascINTLShipmentsForm.Quantity.value="";
       document.aascINTLShipmentsForm.QuantityUnits.value="";
       document.aascINTLShipmentsForm.UnitPrice.value="";
       document.aascINTLShipmentsForm.description.value="";
       document.aascINTLShipmentsForm.Weight.value="";
       document.aascINTLShipmentsForm.HarmonizedCode.value="";
       document.aascINTLShipmentsForm.ExportLicenseNumber.value="";
       document.aascINTLShipmentsForm.ExportLicenseExpirationDate.value="";
       document.aascINTLShipmentsForm.addComm.disabled=true;
    }
    else if(currentCommItem == 'Create'){
       enableCommodityDetailDiv();
       document.aascINTLShipmentsForm.CountryOfManufacture.value="US";
       document.aascINTLShipmentsForm.CustomsValue.value="";
       document.aascINTLShipmentsForm.NumberOfPieces.value="1";
       document.aascINTLShipmentsForm.Quantity.value="";
       document.aascINTLShipmentsForm.QuantityUnits.value="";
       document.aascINTLShipmentsForm.UnitPrice.value="";
       document.aascINTLShipmentsForm.description.value="";
       document.aascINTLShipmentsForm.Weight.value="";
       document.aascINTLShipmentsForm.HarmonizedCode.value="";
       document.aascINTLShipmentsForm.ExportLicenseNumber.value="";
       document.aascINTLShipmentsForm.ExportLicenseExpirationDate.value="";
       
       if(shipFlag == 'Y'){
        document.aascINTLShipmentsForm.addComm.disabled=true;
       }else{
        document.aascINTLShipmentsForm.addComm.disabled=false;
       }
    }
    else {
        if(shipFlag == 'Y'){
        document.aascINTLShipmentsForm.addComm.disabled=true;
       }else{
        document.aascINTLShipmentsForm.addComm.disabled=false;
       }        enableCommodityDetailDiv();
        getAjaxInlCommodityDetails();
    }
}

function getAjaxInlCommodityDetails(){
    var currentCommItem = document.aascINTLShipmentsForm.selCommItems.value;
    var xmlHttp;
  try
    {    // Firefox, Opera 8.0+, Safari  
    xmlHttp=new XMLHttpRequest(); 
    }
  catch (e)
    {    // Internet Explorer 
    try
      {      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");     
      }
    catch (e)
      {    
      
      try
        {     
        xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");     
        }
      catch (e)
        {        
        alert("Your browser does not support AJAX!");  
        return false;      
        }    
        }  
        
        }
    xmlHttp.onreadystatechange=function()
      {
      if(xmlHttp.readyState==4)
        {
          var responseStringDummy=xmlHttp.responseText;
          startIndex  = responseStringDummy.indexOf('*');
          responseStringDummy=responseStringDummy.substring(startIndex+1);
           
          startIndex  = responseStringDummy.indexOf('*');
          countryOfManufacture     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.CountryOfManufacture.value=countryOfManufacture;
          
          startIndex  = responseStringDummy.indexOf('*');
          customsValue     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.CustomsValue.value=customsValue;
          
          startIndex  = responseStringDummy.indexOf('*');
          numberOfPieces     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.NumberOfPieces.value=numberOfPieces;
          
          startIndex  = responseStringDummy.indexOf('*');
          quantity     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.Quantity.value=quantity;
          
          startIndex  = responseStringDummy.indexOf('*');
          quantityUnits     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.QuantityUnits.value=quantityUnits;
          
          if(document.aascINTLShipmentsForm.QuantityUnits.value == "")
          {
            document.aascINTLShipmentsForm.QuantityUnits.value = "";
          
          }
          
          startIndex  = responseStringDummy.indexOf('*');
          unitprice     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.UnitPrice.value=unitprice;
          
          startIndex  = responseStringDummy.indexOf('*');
          description     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.description.value=description;
                             
          startIndex  = responseStringDummy.indexOf('*');
          weight     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.Weight.value=weight;
          
          startIndex  = responseStringDummy.indexOf('*');
          harmonizedCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.HarmonizedCode.value=harmonizedCode;
          
          startIndex  = responseStringDummy.indexOf('*');
          expLicenseNo     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.ExportLicenseNumber.value=expLicenseNo;
          
          exportLicenseExpirationDate = responseStringDummy;
          document.aascINTLShipmentsForm.ExportLicenseExpirationDate.value=exportLicenseExpirationDate;
          
          calcUnitPrice();
       }
    }
    var locationId = document.getElementById("locationId").value;
     var currentItemtemp=encodeURIComponent(currentCommItem);
    var url="aascAjaxIntlCommodityDetail.jsp?currentItem="+currentItemtemp+"&locationId="+locationId;
    xmlHttp.open("GET",url,true);  // Calling 
    xmlHttp.setRequestHeader("Cache-Control","no-cache");
    xmlHttp.setRequestHeader("Pragma","no-cache");
    xmlHttp.setRequestHeader("If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT");
    xmlHttp.send(null);  
}

function viewPrinted()
{
    //alert("Entered viewPrint()");
    document.aascINTLShipmentsForm.actionType.value='VIEWPRINT';
    document.aascINTLShipmentsForm.submit();
} 

function getIntlImporterDetails(){
    var currentImporter = document.aascINTLShipmentsForm.selImporterName.value;
    if(currentImporter == 'Select')
    {
       document.aascINTLShipmentsForm.importerName.value="";
       document.aascINTLShipmentsForm.importerCompName.value="";
       document.aascINTLShipmentsForm.importerPhoneNum.value="";
       document.aascINTLShipmentsForm.importerAddress1.value="";
       document.aascINTLShipmentsForm.importerAddress2.value="";
       document.aascINTLShipmentsForm.importerCity.value="";
       document.aascINTLShipmentsForm.importerState.value="";
       document.aascINTLShipmentsForm.importerPostalCode.value="";
       document.aascINTLShipmentsForm.importerCountryCode.value="";
       document.aascINTLShipmentsForm.ImporterTINOrDUNS.value="";
       document.aascINTLShipmentsForm.ImporterTINOrDUNSType.value="";
    }
  
    else {
        getAjaxInlImporterDetail();
    }
}

function getAjaxInlImporterDetail(){
    var currentImporter = document.aascINTLShipmentsForm.selImporterName.value;
    var xmlHttp;
  try
    {    // Firefox, Opera 8.0+, Safari  
    xmlHttp=new XMLHttpRequest(); 
    }
  catch (e)
    {    // Internet Explorer 
    try
      {      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");     
      }
    catch (e)
      {    
      
      try
        {     
        xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");     
        }
      catch (e)
        {        
        alert("Your browser does not support AJAX!");  
        return false;      
        }    
        }  
        
        }
    xmlHttp.onreadystatechange=function()
      {
      if(xmlHttp.readyState==4)
        {
          var responseStringDummy=xmlHttp.responseText;
          startIndex  = responseStringDummy.indexOf('*');
          responseStringDummy=responseStringDummy.substring(startIndex+1);
           
          startIndex  = responseStringDummy.indexOf('*');
          ImporterTINOrDUNS = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.ImporterTINOrDUNS.value=ImporterTINOrDUNS;
          
          startIndex  = responseStringDummy.indexOf('*');
          ImporterTINOrDUNSType     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.ImporterTINOrDUNSType.value=ImporterTINOrDUNSType;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerAddress1     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.importerAddress1.value=importerAddress1;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerAddress2     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.importerAddress2.value=importerAddress2;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCity     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.importerCity.value=importerCity;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCompName     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.importerCompName.value=importerCompName;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCountryCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.importerCountryCode.value=importerCountryCode;
                             
          startIndex  = responseStringDummy.indexOf('*');
          importerPhoneNum     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.importerPhoneNum.value=importerPhoneNum;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerPostalCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.importerPostalCode.value=importerPostalCode;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerState     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.importerState.value=importerState;
          
          importerName = responseStringDummy;
          document.aascINTLShipmentsForm.importerName.value=importerName;
       }
    }
    var locationId = document.getElementById("locationId").value;
    var url="aascAjaxIntlImporterDetail.jsp?currentImporter="+currentImporter+"&locationId="+locationId;
    xmlHttp.open("POST",url,true);  // Calling 
    xmlHttp.send(null);  
}

function viewDoc()
{

    popupWindow = window.open('aascIntlDocViewPrint.jsp','_blank', 'toolbar=yes, scrollbars=yes, resizable=yes');
    popupWindow.focus();
}

function changeCI(){
    var checkedCI = document.aascINTLShipmentsForm.generateCI.checked;
    if(checkedCI){
        document.aascINTLShipmentsForm.generateCI.value='Y';
    }else{
        document.aascINTLShipmentsForm.generateCI.value='N';
    }
}

function calcUnitPrice(){
   var lineCustomValue = document.aascINTLShipmentsForm.CustomsValue.value;
//   alert("lineCustomValue"+lineCustomValue);
   var quantity = document.aascINTLShipmentsForm.Quantity.value;
//   alert("quantity"+quantity);
   var unitPrice = lineCustomValue/quantity;
//   alert("unitPrice"+unitPrice);
   document.aascINTLShipmentsForm.UnitPrice.value = unitPrice;
}

function getIntlBrokerDetails(){
    var currentBroker = document.aascINTLShipmentsForm.selBrokerName.value;
    if(currentBroker == 'Select')
    {
       document.aascINTLShipmentsForm.brokerName.value="";
       document.aascINTLShipmentsForm.brokerCompName.value="";
       document.aascINTLShipmentsForm.brokerPhoneNum.value="";
       document.aascINTLShipmentsForm.brokerAddress1.value="";
       document.aascINTLShipmentsForm.brokerAddress2.value="";
       document.aascINTLShipmentsForm.brokerCity.value="";
       document.aascINTLShipmentsForm.brokerState.value="";
       document.aascINTLShipmentsForm.brokerPostalCode.value="";
       document.aascINTLShipmentsForm.brokerCountryCode.value="";      
    }
  
    else {
        getAjaxInlBrokerDetail();
    }
}

function getAjaxInlBrokerDetail(){
    var currentBroker = document.aascINTLShipmentsForm.selBrokerName.value;
    var xmlHttp;
  try
    {    // Firefox, Opera 8.0+, Safari  
    xmlHttp=new XMLHttpRequest(); 
    }
  catch (e)
    {    // Internet Explorer 
    try
      {      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");     
      }
    catch (e)
      {    
      
      try
        {     
        xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");     
        }
      catch (e)
        {        
        alert("Your browser does not support AJAX!");  
        return false;      
        }    
        }  
        
        }
    xmlHttp.onreadystatechange=function()
      {
      if(xmlHttp.readyState==4)
        {
          var responseStringDummy=xmlHttp.responseText;
          startIndex  = responseStringDummy.indexOf('*');
          responseStringDummy=responseStringDummy.substring(startIndex+1);
            
          startIndex  = responseStringDummy.indexOf('*');
          brokerAddress1     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.brokerAddress1.value=brokerAddress1;
          
          startIndex  = responseStringDummy.indexOf('*');
          brokerAddress2     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.brokerAddress2.value=brokerAddress2;
          
          startIndex  = responseStringDummy.indexOf('*');
          brokerCity     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.brokerCity.value=brokerCity;
          
          startIndex  = responseStringDummy.indexOf('*');
          brokerCompName     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.brokerCompName.value=brokerCompName;
          
          startIndex  = responseStringDummy.indexOf('*');
          brokerCountryCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.brokerCountryCode.value=brokerCountryCode;
                             
          startIndex  = responseStringDummy.indexOf('*');
          brokerPhoneNum     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.brokerPhoneNum.value=brokerPhoneNum;
          
          startIndex  = responseStringDummy.indexOf('*');
          brokerPostalCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.brokerPostalCode.value=brokerPostalCode;
          
          startIndex  = responseStringDummy.indexOf('*');
          brokerState     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascINTLShipmentsForm.brokerState.value=brokerState;
          
          brokerName = responseStringDummy;
          document.aascINTLShipmentsForm.brokerName.value=brokerName;
       }
    }
    var locationId = document.getElementById("locationId").value;
    var url="aascAjaxIntlBrokerDetail.jsp?currentBroker="+currentBroker+"&locationId="+locationId;
    xmlHttp.open("POST",url,true);  // Calling 
    xmlHttp.send(null);  
}

function enableCommodityDetailDiv(){
    document.getElementById('commodityDetailDiv').style.display ="";
}

function disableCommodityDetailDiv(){
    document.getElementById('commodityDetailDiv').style.display ="none";

}

function assignOrderNumber(){
    window.opener.document.DynaShipmentShipSaveForm.orderNum.value=document.aascINTLShipmentsForm.orderNumber.value;
}
