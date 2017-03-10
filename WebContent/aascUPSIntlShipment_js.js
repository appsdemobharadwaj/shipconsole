/*===========================================================================================================+
|  DESCRIPTION                                                                                              |
|    javascript file for the aascUPSIntlShipment.jsp  validation                                            |
|    Author Y Pradeep                                                                                       |
|    Version   1                                                                                            |                                                                            
|    Creation 28-JAN-2015                                                                                   |
History:
    28/01/2015   Y Pradeep    Added this file for Fedex Indernation page.
    02/02/2015   Y Pradeep    Modified name of the jsp popup to open to display International Documents.
    11/02/2015   Y Pradeep    Modified code to call aascUpsIntlAddresssDetails.jsp page from window.open.
    16/02/2015   Y Pradeep    Modified code to generate order number on click og Ship button in Shipping page.
    10/03/2015   Y Pradeep    Alligned code in proper order.
    15/03/2015   Y Pradeep    Added code to open Commercial Invoice in new window.
    07/07/2015   Rakesh K     Modified screen layouts to align in Tablet
    09/11/2015   Suman G      Added code to fix issue
    10/11/2015   Suman G      Added code to fix #3946
+===========================================================================================================*/

//load(start)

var shipFlagTemp="";
function load()
{
//     document.aascUPSIntlShipmentsForm.InvVal.value = document.aascUPSIntlShipmentsForm.commCustomValue.value;
opener.document.DynaShipmentShipSaveForm.InvVal.value = document.aascUPSIntlShipmentsForm.InvVal.value;

    var soldToCompanyName = document.aascUPSIntlShipmentsForm.CompanyName.value;
    if(soldToCompanyName == null || soldToCompanyName == '') //Need to implement for Adhoc..as per discussion with sudhakar
    {
        document.aascUPSIntlShipmentsForm.CompanyName.value = window.opener.document.DynaShipmentShipSaveForm.customerName.value;
        document.aascUPSIntlShipmentsForm.AddressLine1.value = window.opener.document.DynaShipmentShipSaveForm.shipToAddress.value;  
        document.aascUPSIntlShipmentsForm.City.value = window.opener.document.DynaShipmentShipSaveForm.city.value;
        document.aascUPSIntlShipmentsForm.StateProvinceCode.value = window.opener.document.DynaShipmentShipSaveForm.state.value;
        document.aascUPSIntlShipmentsForm.PostalCode.value = window.opener.document.DynaShipmentShipSaveForm.zip.value;
        document.aascUPSIntlShipmentsForm.CountryCode.value = window.opener.document.DynaShipmentShipSaveForm.country.value;
        document.aascUPSIntlShipmentsForm.SoldToAttention.value = window.opener.document.DynaShipmentShipSaveForm.contactName.value;
        document.aascUPSIntlShipmentsForm.SoldToPhone.value = window.opener.document.DynaShipmentShipSaveForm.phoneNumber.value;
 
    } 
 
    var commLength = document.aascUPSIntlShipmentsForm.commodityLine.length;
    var saveFlag = window.opener.document.DynaShipmentShipSaveForm.intlSaveFlag.value;
  //alert('commLength::'+commLength);
    if(commLength > 2){
  
        document.aascUPSIntlShipmentsForm.CommercialInvoice.disabled = true;
        if(saveFlag == "Y")
        {
            document.aascUPSIntlShipmentsForm.CommercialInvoice.checked = true;
        }
        document.aascUPSIntlShipmentsForm.USCO.disabled = true;
        document.aascUPSIntlShipmentsForm.NAFTACO.disabled = true;
        document.aascUPSIntlShipmentsForm.SED.disabled = true;
    }else{
        document.aascUPSIntlShipmentsForm.CommercialInvoice.disabled = false;
        document.aascUPSIntlShipmentsForm.USCO.disabled = false;
        document.aascUPSIntlShipmentsForm.NAFTACO.disabled = false;
        document.aascUPSIntlShipmentsForm.SED.disabled = false;
    }
  
    // hideHeaderOptions();
  
    //document.aascUPSIntlShipmentsForm.Quantity.disabled = true;
    //document.aascUPSIntlShipmentsForm.UnitPrice.disabled = true;
    //document.aascUPSIntlShipmentsForm.QuantityUnits.disabled = true;
    // document.aascUPSIntlShipmentsForm.InvoiceNumber.disabled = true;
    document.aascUPSIntlShipmentsForm.PuchaseOrderNumber.disabled = true;
    document.aascUPSIntlShipmentsForm.TermsOfSale.disabled = true;
    document.aascUPSIntlShipmentsForm.Purpose.disabled = true;
    document.aascUPSIntlShipmentsForm.Discount.disabled = true;
    document.aascUPSIntlShipmentsForm.FreightCharges.disabled = true;
    document.aascUPSIntlShipmentsForm.InsuranceCharges.disabled = true;
    document.aascUPSIntlShipmentsForm.OtherCharges.disabled = true;
    //  document.aascUPSIntlShipmentsForm.InvoiceDate.disabled = true;
    document.aascUPSIntlShipmentsForm.CurrencyCode.disabled = true;
    document.aascUPSIntlShipmentsForm.comments.disabled = true;
    document.aascUPSIntlShipmentsForm.DeclarationStmt.disabled = true;
    document.aascUPSIntlShipmentsForm.PreferenceCriteria.disabled = true;
    document.aascUPSIntlShipmentsForm.Producer.disabled = true;
    //  document.aascUPSIntlShipmentsForm.NAFTA_ProduceInfo.disabled = true;
    document.aascUPSIntlShipmentsForm.NAFTA_ProduceInfo.src ="buttons/aascDetailsOff1.png";
         
    document.aascUPSIntlShipmentsForm.PCompanyName.disabled = true;
    document.aascUPSIntlShipmentsForm.PAddressLine1.disabled = true;
    document.aascUPSIntlShipmentsForm.PCity.disabled = true;
    document.aascUPSIntlShipmentsForm.PStateProvinceCode.disabled = true;
    document.aascUPSIntlShipmentsForm.PPostalCode.disabled = true;
    document.aascUPSIntlShipmentsForm.PCountryCode.disabled = true;
    document.aascUPSIntlShipmentsForm.RVCCalculationMethod.disabled = true;
    document.aascUPSIntlShipmentsForm.NetCostPeriodBeginDate.disabled = true;
    document.aascUPSIntlShipmentsForm.NetCostPeriodEndDate.disabled = true;
    document.aascUPSIntlShipmentsForm.BlanketPeriodBeginDate.disabled = true;
    document.aascUPSIntlShipmentsForm.BlanketPeriodEndDate.disabled = true;

    document.aascUPSIntlShipmentsForm.NumberOfPieces.disabled = true;
    document.aascUPSIntlShipmentsForm.Weight.disabled = true;
    document.aascUPSIntlShipmentsForm.UOM.disabled = true;
    document.aascUPSIntlShipmentsForm.UExportDate.disabled = true;
    document.aascUPSIntlShipmentsForm.UExportingCarrier.disabled = true;


    document.aascUPSIntlShipmentsForm.SEDTotalValue.disabled = true;
    document.aascUPSIntlShipmentsForm.ScheduleBNumber.disabled = true;
    document.aascUPSIntlShipmentsForm.ScheduleBQuantity.disabled = true;
    document.aascUPSIntlShipmentsForm.ScheduleBUnitOfMeasure.disabled = true;
    document.aascUPSIntlShipmentsForm.ExportType.disabled = true;
    document.aascUPSIntlShipmentsForm.PointOfOrigin.disabled = true;
    document.aascUPSIntlShipmentsForm.ModeOfTransport.disabled = true;
    document.aascUPSIntlShipmentsForm.SExportDate.disabled = true;
    document.aascUPSIntlShipmentsForm.SExportingCarrier.disabled = true;
    document.aascUPSIntlShipmentsForm.InBondCode.disabled = true;
    document.aascUPSIntlShipmentsForm.EntryNumber.disabled = true;
    document.aascUPSIntlShipmentsForm.LoadingPier.disabled = true;
    document.aascUPSIntlShipmentsForm.PortOfExport.disabled = true;
    document.aascUPSIntlShipmentsForm.PortOfUnloading.disabled = true;
    document.aascUPSIntlShipmentsForm.CarrierIdentificationCode.disabled = true;
    document.aascUPSIntlShipmentsForm.Containerized.disabled = true;
    document.aascUPSIntlShipmentsForm.HazardousMaterials.disabled = true;
    document.aascUPSIntlShipmentsForm.RoutedExportTransaction.disabled = true;
    document.aascUPSIntlShipmentsForm.PartiestoTransaction[0].disabled = true; //Added by Narasimha 16/11/2010
    document.aascUPSIntlShipmentsForm.PartiestoTransaction[1].disabled = true;
    // document.aascUPSIntlShipmentsForm.License.disabled = true;
    // document.aascUPSIntlShipmentsForm.LicenseExceptionCode.disabled = true;
    //  document.aascUPSIntlShipmentsForm.ECCN.disabled = true;
    document.aascUPSIntlShipmentsForm.LicenseNumber.disabled = true;
    document.aascUPSIntlShipmentsForm.LicenseDate.disabled = true;
         
    //document.aascUPSIntlShipmentsForm.ConsigneeInfo.disabled = true;
    document.aascUPSIntlShipmentsForm.ConsigneeInfo.src ="buttons/aascDetailsOff1.png";
    //document.aascUPSIntlShipmentsForm.FAgentInfo.disabled = true;
    document.aascUPSIntlShipmentsForm.FAgentInfo.src ="buttons/aascDetailsOff1.png";
    //document.aascUPSIntlShipmentsForm.IConsigneeInfo.disabled = true;
    document.aascUPSIntlShipmentsForm.IConsigneeInfo.src ="buttons/aascDetailsOff1.png";
         
    document.aascUPSIntlShipmentsForm.CCompanyName.disabled = true;
    document.aascUPSIntlShipmentsForm.CAddressLine1.disabled = true;
    document.aascUPSIntlShipmentsForm.CCity.disabled = true;
    document.aascUPSIntlShipmentsForm.CStateProvinceCode.disabled = true;
    document.aascUPSIntlShipmentsForm.CPostalCode.disabled = true;
    document.aascUPSIntlShipmentsForm.CCountryCode.disabled = true;
         
         
    var LicenseNumber=document.aascUPSIntlShipmentsForm.LicenseNumber.value;
    var ExceptionCodes = document.aascUPSIntlShipmentsForm.ExceptionCodes.value; 
    if((LicenseNumber =='' || LicenseNumber == null) && (ExceptionCodes =='' || ExceptionCodes == null))
    {
        document.aascUPSIntlShipmentsForm.LicenseNumber.disabled = true;
        document.aascUPSIntlShipmentsForm.LicenseDate.disabled = true;
        document.aascUPSIntlShipmentsForm.ExceptionCodes.disabled = true;
        document.aascUPSIntlShipmentsForm.EccnNumber.disabled = true;
    }
    enableCI();
    enableNAFTA();
    enableUSCO();
    enableSED();
    document.getElementById('ComInvoiceID').style.display ="none";
    //document.getElementById('ComInvoiceChargesID').style.display ="none";
    document.getElementById('USCertificateID').style.display ="none";
    document.getElementById('NAFTACertificateID').style.display ="none";
    document.getElementById('ShippersExportID').style.display ="none";
    
    //document.getElementById('cominvHR').style.display ="none";
    //document.getElementById('cominvChargesHR').style.display ="none";
    document.getElementById('USCertifiHR').style.display ="none";
    document.getElementById('NAFTACertifiHR').style.display ="none";
    document.getElementById('ShipperExpoHR').style.display ="none";

    //document.getElementById('ComInRow').style.display ="none";
    document.getElementById('USRow').style.display ="none";
    document.getElementById('NAFTARow1').style.display ="none";
    document.getElementById('NAFTARow2').style.display ="none";
    document.getElementById('NAFTARow3').style.display ="none";
    document.getElementById('SEDRow1').style.display ="none";
    document.getElementById('SEDRow2').style.display ="none";

    //alert('before ShowFields()');
    ShowFields();
    //alert('after ShowFields()');
    //SelectForms();

    if (document.aascUPSIntlShipmentsForm.shipment.value == 'adhoc')
    {
        var shipFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;
        document.aascUPSIntlShipmentsForm.shipment.value = window.opener.document.DynaShipmentShipSaveForm.shipmentType.value;
    }     

    if (document.aascUPSIntlShipmentsForm.shipment.value == 'adhoc')
        document.aascUPSIntlShipmentsForm.orderNo.value = window.opener.document.DynaShipmentShipSaveForm.orderNum.value;
    var shipFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value; 
    document.aascUPSIntlShipmentsForm.shipment.value = window.opener.document.DynaShipmentShipSaveForm.shipmentType.value;

    //alert("shipFlag   "+shipFlag);     
    var descriptionVal = document.aascUPSIntlShipmentsForm.description.value;
    //alert('descriptionVal::'+descriptionVal);
    if(descriptionVal.length < 1){
        getIntlCommodityLineDetails(); 
    }

    if(shipFlag == 'Y')
    {
    //alert("hi");
           
        document.aascUPSIntlShipmentsForm.description.disabled = true;
        document.aascUPSIntlShipmentsForm.HarmonizedCode.disabled = true;
        document.aascUPSIntlShipmentsForm.CountryOfManufacture.disabled = true;
        document.aascUPSIntlShipmentsForm.Quantity.disabled = true;
        document.aascUPSIntlShipmentsForm.UnitPrice.disabled = true;
        document.aascUPSIntlShipmentsForm.QuantityUnits.disabled = true;
        document.aascUPSIntlShipmentsForm.InvoiceNumber.disabled = true;
        document.aascUPSIntlShipmentsForm.PuchaseOrderNumber.disabled = true;
        document.aascUPSIntlShipmentsForm.TermsOfSale.disabled = true;
        document.aascUPSIntlShipmentsForm.Purpose.disabled = true;
        document.aascUPSIntlShipmentsForm.Discount.disabled = true;
        document.aascUPSIntlShipmentsForm.FreightCharges.disabled = true;
        document.aascUPSIntlShipmentsForm.InsuranceCharges.disabled = true;
        document.aascUPSIntlShipmentsForm.OtherCharges.disabled = true;
        document.aascUPSIntlShipmentsForm.InvoiceDate.disabled = true;
        document.aascUPSIntlShipmentsForm.CurrencyCode.disabled = true;
        document.aascUPSIntlShipmentsForm.comments.disabled = true;
        document.aascUPSIntlShipmentsForm.DeclarationStmt.disabled = true;
        document.aascUPSIntlShipmentsForm.PreferenceCriteria.disabled = true;
        document.aascUPSIntlShipmentsForm.Producer.disabled = true;
           
      //document.aascUPSIntlShipmentsForm.NAFTA_ProduceInfo.disabled = true;
     // document.aascUPSIntlShipmentsForm.NAFTA_ProduceInfo.src ="buttons/aascDetailsOff1.png";
           
        document.aascUPSIntlShipmentsForm.PCompanyName.disabled = true;
        document.aascUPSIntlShipmentsForm.PAddressLine1.disabled = true;
        document.aascUPSIntlShipmentsForm.PCity.disabled = true;
        document.aascUPSIntlShipmentsForm.PStateProvinceCode.disabled = true;
        document.aascUPSIntlShipmentsForm.PPostalCode.disabled = true;
        document.aascUPSIntlShipmentsForm.PCountryCode.disabled = true;
        document.aascUPSIntlShipmentsForm.RVCCalculationMethod.disabled = true;
        document.aascUPSIntlShipmentsForm.NetCostPeriodBeginDate.disabled = true;
        document.aascUPSIntlShipmentsForm.NetCostPeriodEndDate.disabled = true;
        document.aascUPSIntlShipmentsForm.BlanketPeriodBeginDate.disabled = true;
        document.aascUPSIntlShipmentsForm.BlanketPeriodEndDate.disabled = true;
        document.aascUPSIntlShipmentsForm.NumberOfPieces.disabled = true;
        document.aascUPSIntlShipmentsForm.Weight.disabled = true;
        document.aascUPSIntlShipmentsForm.UOM.disabled = true;
        document.aascUPSIntlShipmentsForm.UExportDate.disabled = true;
        document.aascUPSIntlShipmentsForm.UExportingCarrier.disabled = true;
        document.aascUPSIntlShipmentsForm.SEDTotalValue.disabled = true;
        document.aascUPSIntlShipmentsForm.ScheduleBNumber.disabled = true;
        document.aascUPSIntlShipmentsForm.ScheduleBQuantity.disabled = true;
        document.aascUPSIntlShipmentsForm.ScheduleBUnitOfMeasure.disabled = true;
        document.aascUPSIntlShipmentsForm.ExportType.disabled = true;
        document.aascUPSIntlShipmentsForm.PointOfOrigin.disabled = true;
        document.aascUPSIntlShipmentsForm.ModeOfTransport.disabled = true;
        document.aascUPSIntlShipmentsForm.SExportDate.disabled = true;
        document.aascUPSIntlShipmentsForm.SExportingCarrier.disabled = true;
        document.aascUPSIntlShipmentsForm.InBondCode.disabled = true;
        document.aascUPSIntlShipmentsForm.EntryNumber.disabled = true;
        document.aascUPSIntlShipmentsForm.LoadingPier.disabled = true;
        document.aascUPSIntlShipmentsForm.PortOfExport.disabled = true;
        document.aascUPSIntlShipmentsForm.PortOfUnloading.disabled = true;
        document.aascUPSIntlShipmentsForm.CarrierIdentificationCode.disabled = true;
        document.aascUPSIntlShipmentsForm.Containerized.disabled = true;
        document.aascUPSIntlShipmentsForm.HazardousMaterials.disabled = true;
        document.aascUPSIntlShipmentsForm.RoutedExportTransaction.disabled = true;
        document.aascUPSIntlShipmentsForm.PartiestoTransaction[0].disabled = true; //Added by Narasimha 16/11/2010
        document.aascUPSIntlShipmentsForm.PartiestoTransaction[1].disabled = true;
        // document.aascUPSIntlShipmentsForm.License.disabled = true;
        // document.aascUPSIntlShipmentsForm.LicenseExceptionCode.disabled = true;
        //  document.aascUPSIntlShipmentsForm.ECCN.disabled = true;
        document.aascUPSIntlShipmentsForm.LicenseNumber.disabled = true;
        document.aascUPSIntlShipmentsForm.LicenseDate.disabled = true;
        document.aascUPSIntlShipmentsForm.ExceptionCodes.disabled = true;
        document.aascUPSIntlShipmentsForm.EccnNumber.disabled = true;
        //document.aascUPSIntlShipmentsForm.ConsigneeInfo.disabled = true;
        //   document.aascUPSIntlShipmentsForm.ConsigneeInfo.src ="buttons/aascDetailsOff1.png";
        //document.aascUPSIntlShipmentsForm.FAgentInfo.disabled = true;
        // document.aascUPSIntlShipmentsForm.FAgentInfo.src ="buttons/aascDetailsOff1.png";
        //document.aascUPSIntlShipmentsForm.IConsigneeInfo.disabled = true;
        // document.aascUPSIntlShipmentsForm.IConsigneeInfo.src ="buttons/aascDetailsOff1.png";
         
        document.aascUPSIntlShipmentsForm.CCompanyName.disabled = true;
        document.aascUPSIntlShipmentsForm.CAddressLine1.disabled = true;
        document.aascUPSIntlShipmentsForm.CCity.disabled = true;
        document.aascUPSIntlShipmentsForm.CStateProvinceCode.disabled = true;
        document.aascUPSIntlShipmentsForm.CPostalCode.disabled = true;
        document.aascUPSIntlShipmentsForm.CCountryCode.disabled = true;
         
        document.aascUPSIntlShipmentsForm.addComm.disabled = true;
        //document.aascUPSIntlShipmentsForm.editComm.disabled = true;
        document.aascUPSIntlShipmentsForm.delComm.disabled = true;
         
        document.aascUPSIntlShipmentsForm.CompanyName.disabled = true;
        document.aascUPSIntlShipmentsForm.AddressLine1.disabled = true;
        document.aascUPSIntlShipmentsForm.AddressLine2.disabled = true;
        document.aascUPSIntlShipmentsForm.City.disabled = true;
        document.aascUPSIntlShipmentsForm.StateProvinceCode.disabled = true;
        document.aascUPSIntlShipmentsForm.PostalCode.disabled = true;
        document.aascUPSIntlShipmentsForm.CountryCode.disabled = true;
          
        document.aascUPSIntlShipmentsForm.CommercialInvoice.disabled = true;
        document.aascUPSIntlShipmentsForm.USCO.disabled = true;
        document.aascUPSIntlShipmentsForm.NAFTACO.disabled = true;
        document.aascUPSIntlShipmentsForm.SED.disabled = true;
        document.aascUPSIntlShipmentsForm.ShipFromTaxID.disabled = true;
        document.aascUPSIntlShipmentsForm.ShipFromPhone.disabled = true;
        document.aascUPSIntlShipmentsForm.ShipFromAttention.disabled = true;
        document.aascUPSIntlShipmentsForm.ShipToTaxID.disabled = true;
        //document.aascUPSIntlShipmentsForm.ShipTosameSoldTo.disabled = true;
        document.aascUPSIntlShipmentsForm.TaxIdNum.disabled = true;
        document.aascUPSIntlShipmentsForm.SoldToAttention.disabled = true;
        document.aascUPSIntlShipmentsForm.SoldToPhone.disabled = true;
        document.aascUPSIntlShipmentsForm.InvCurCd.disabled = true;
        document.aascUPSIntlShipmentsForm.InvVal.disabled = true;
    }
    disableECCNNumber();
   
    shipFlagTemp=shipFlag;
}


function hideHeaderOptions()
{
    document.aascUPSIntlShipmentsForm.USCO.style.visibility = 'hidden';
}

//load(end

function saveDetails()
{    
        //alert('in save');
    var desc = trim(document.aascUPSIntlShipmentsForm.description.value);
    var tariffcode = trim(document.aascUPSIntlShipmentsForm.HarmonizedCode.value);
    var countryOfmanufacture = trim(document.aascUPSIntlShipmentsForm.CountryOfManufacture.value);
       
    var UOM=trim(document.aascUPSIntlShipmentsForm.UOM.value);

    var alertStr = "";
       
    /*   if( window.opener.ShipInsertForm.intTotalCustomsValue.value == "Y") 
       {
       alert('Details are Already Saved')
       return false;
       } */
     /*  if(desc ==''||desc == null)
       {
       alertStr = alertStr+"Please enter \'Product description\' in Product Details Section\n";
       document.aascUPSIntlShipmentsForm.description.focus();
      // return false;
       }
       if(tariffcode ==''||tariffcode == null)
       {
       alertStr = alertStr+"Please enter \'Product Tariff Code\' in Product Details Section\n";
       document.aascUPSIntlShipmentsForm.HarmonizedCode.focus();
       //return false;
       }
       if(countryOfmanufacture ==''||countryOfmanufacture == null)
       {
       alertStr = alertStr+"Please enter \'Country of Manfacture\' in Product Details Section\n";
       document.aascUPSIntlShipmentsForm.CountryOfManufacture.focus();
       //return false;
       }
       
       if(alertStr != "" && alertStr !=null)
      {
      alert(alertStr);
      return false;
      }*/
      
    if(document.aascUPSIntlShipmentsForm.CommercialInvoice.checked &&!document.aascUPSIntlShipmentsForm.CommercialInvoice.disabled)
    {
         //alert("hi");
        document.aascUPSIntlShipmentsForm.CIFlag.value = 'Y';
         
       //  var form = document.aascUPSIntlShipmentsForm.commodityLine;
        // var value = form.options.value;
  
        var commLength = document.aascUPSIntlShipmentsForm.commodityLine.length;
        if(commLength < 3){
            alert("  Please add atleast one Commodity Item  ");
            return false;
        }

        if(saveCICommodity() ==false)
        {
            return false;
        }
        
    }
    else
    {
        document.aascUPSIntlShipmentsForm.CIFlag.value = 'N';
    }
    //NAFTA SAVING
    /*if(isInteger(trim(document.aascUPSIntlShipmentsForm.InvVal.value))==false || trim(document.aascUPSIntlShipmentsForm.InvVal.value)== ''){
            alert("Enter Integer Value For Invoice Value");
            document.aascUPSIntlShipmentsForm.InvVal.focus();
            return false;

    }*/
    //      var shipmentType = document.aascUPSIntlShipmentsForm.shipment.value;
    //            if(shipmentType == "adhoc")
    //            {
                    var shipToAddressCountry = window.opener.document.DynaShipmentShipSaveForm.country.value;
    //            }else{
    //            var shipToAddressCountry = window.opener.document.ShipInsertForm.shipToAddressCountry.value;
    //            
    //            }
                
    if(shipToAddressCountry=='PR' || shipToAddressCountry=='CA')
    {
        if((trim(document.aascUPSIntlShipmentsForm.InvCurCd.value)=="")||(trim(document.aascUPSIntlShipmentsForm.InvCurCd.value)==null))
        {
            alert("Please Enter value for Invoice Currency Code");
            document.aascUPSIntlShipmentsForm.InvCurCd.focus();
            return false;
        }
        if(trim(document.aascUPSIntlShipmentsForm.InvVal.value)==0)
        {
            alert("The Invoice value should be greater than Zero");
            document.aascUPSIntlShipmentsForm.InvVal.focus();
            return false;
        }
    }

//Added code to not to allow Invoice Value as decimal value
        var newNumber=document.aascUPSIntlShipmentsForm.InvVal.value;
        if (newNumber%1 != 0 || (newNumber - Math.floor(newNumber)) != 0)
        {
            alert("UPS International Shipment doesn't allow Invoice Value as decimal value");
            document.aascUPSIntlShipmentsForm.InvVal.focus();
            return false;
        } 
    /*if(document.aascUPSIntlShipmentsForm.commodityLine.length < 3)
    {
       alert("Please Enter Details of Atleast one Product");
       document.aascUPSIntlShipmentsForm.description.focus();
       return false;
    } */
       
    //       if(document.aascUPSIntlShipmentsForm.ShipTosameSoldTo.value=='N')
    //       {
                if(saveSolAddress() ==false)
	        {
                    return false;
                }
    //       }
            if(document.aascUPSIntlShipmentsForm.NAFTACO.checked)
            {
                // alert('in nafta');
                document.aascUPSIntlShipmentsForm.NAFTAFlag.value = 'Y';
                //document.aascUPSIntlShipmentsForm.NAFTACO.disabled = true;
                if(saveNAFTA() ==false)
                {
                    return false;
                }
            }
            else
            {
                document.aascUPSIntlShipmentsForm.NAFTAFlag.value = 'N';
            }
            //CI SAVING 
            if(document.aascUPSIntlShipmentsForm.CommercialInvoice.checked)
            {
                //alert('CI');
                document.aascUPSIntlShipmentsForm.CIFlag.value = 'Y';
                // document.aascUPSIntlShipmentsForm.CommercialInvoice.disabled = true;
                if(saveCI() ==false)
                {
                    return false;
                }
            }
            else
            {
                document.aascUPSIntlShipmentsForm.CIFlag.value = 'N';
            }
            //USCO SAVING
            if(document.aascUPSIntlShipmentsForm.USCO.checked)
            {
                //alert('CO');
                document.aascUPSIntlShipmentsForm.COFlag.value = 'Y';
                //  document.aascUPSIntlShipmentsForm.USCO.disabled = true;
                if(saveUSCO() ==false)
                {
                    return false;
                }
            }
            else
            {
                document.aascUPSIntlShipmentsForm.COFlag.value = 'N';
            }
            //SED SAVING
            if(document.aascUPSIntlShipmentsForm.SED.checked)
            {
                //alert('SED');
                document.aascUPSIntlShipmentsForm.SEDFlag.value = 'Y';
                //document.aascUPSIntlShipmentsForm.SED.disabled = true;
                if(saveSED() ==false)
                {
                    return false;
                }
            }
            else
            {
                document.aascUPSIntlShipmentsForm.SEDFlag.value = 'N';
            }
            if(document.aascUPSIntlShipmentsForm.ShipFromTaxID.value == '' && document.aascUPSIntlShipmentsForm.SEDFlag.value == 'Y')
            {
                alert(" Please Enter Ship From EIN/TaxID ");
                document.aascUPSIntlShipmentsForm.ShipFromTaxID.focus();
                return false;
            } 

            /*  if(document.aascUPSIntlShipmentsForm.ShipFromPhone.value == '' )
                {
                    alert(" Please Enter Ship From Phone Number ");
                    document.aascUPSIntlShipmentsForm.ShipFromPhone.focus();
                    return false;
                }else{
                    var phonenumber=document.aascUPSIntlShipmentsForm.ShipFromPhone.value;
                    if((phonenumber.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (phonenumber.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) 
                    {
					alert("Please Enter Valid 10 Digit Phone Number :");
					document.aascUPSIntlShipmentsForm.ShipFromPhone.focus();
					return false
                    }
                }
            */
       
            /*  if(document.aascUPSIntlShipmentsForm.ShipFromTaxID.value != '' )
                {
                    var phonenumber=document.aascUPSIntlShipmentsForm.ShipFromPhone.value;
                    if((phonenumber.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (phonenumber.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) 
  				{
					alert("Please Enter Valid 10 Digit Phone Number :");
					document.aascUPSIntlShipmentsForm.ShipFromPhone.focus();
					return false
                                }   
                }
            */
       
       
            /*  if(document.aascUPSIntlShipmentsForm.ShipFromAttention.value == '' )
                {
                 alert(" Please Enter Ship From Attention Name ");
                 document.aascUPSIntlShipmentsForm.ShipFromAttention.focus();
                 return false;
                } 
                if(document.aascUPSIntlShipmentsForm.ShipToTaxID.value == '' )
                {
                 alert(" Please Enter Ship To EIN/TaxID ");
                 document.aascUPSIntlShipmentsForm.ShipToTaxID.focus();
                 return false;
                }
                var ComInv = document.aascUPSIntlShipmentsForm.CommercialInvoice.checked;
                var USCO = document.aascUPSIntlShipmentsForm.USCO.checked;
                var NAFTACO = document.aascUPSIntlShipmentsForm.NAFTACO.checked;
                var SED= document.aascUPSIntlShipmentsForm.SED.checked ;
       
            */
            if(document.aascUPSIntlShipmentsForm.CommercialInvoice.checked==true ||document.aascUPSIntlShipmentsForm.NAFTACO.checked==true){  
               /*if(document.aascUPSIntlShipmentsForm.TaxIdNum.value == '' )
               {
                 alert(" Please Open Sold To details PopUp and enter Tax Id ");
                // document.aascUPSIntlShipmentsForm.TaxIdNum.focus();
                 return false;
               }  */
               if(document.aascUPSIntlShipmentsForm.SoldToAttention.value == '' )
               {
                 alert(" Please Open Sold To details PopUp and enter Attention Name ");
                // document.aascUPSIntlShipmentsForm.SoldToAttention.focus();
                 return false;
               }
     
                if(document.aascUPSIntlShipmentsForm.CommercialInvoice.checked==true)
                {  
                    if(document.aascUPSIntlShipmentsForm.SoldToPhone.value == '' )
                    {
                        alert(" Please Open Sold To details PopUp and enter Phone Number ");
                        // document.aascUPSIntlShipmentsForm.SoldToPhone.focus();
                        return false;
                    }
                    else
                    {
                        var phonenumber=document.aascUPSIntlShipmentsForm.SoldToPhone.value;
                        if((phonenumber.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (phonenumber.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) 
                        {
        //					alert("Please Enter Valid 10 Digit Phone Number in Sold To Details PopUp");
        //					//document.aascUPSIntlShipmentsForm.SoldToPhone.focus();
        //					return false;
                        }
                    }
                }
       
            }
    /*   if(document.aascUPSIntlShipmentsForm.InvCurCd.value == '' )
       {
         alert(" Please Enter Valid Invoice Currency Code upto 3 characters ");
         document.aascUPSIntlShipmentsForm.InvCurCd.focus();
         return false;
       }else{
        var InvCurCd = document.aascUPSIntlShipmentsForm.InvCurCd.value;
        if(InvCurCd.length > 3){
         alert(" Please Enter Valid Invoice Currency Code ");
         document.aascUPSIntlShipmentsForm.InvCurCd.focus();
         return false;
        }
       }
       if(document.aascUPSIntlShipmentsForm.InvVal.value == '' )
       {
         alert(" Please Enter Invoice Monitary Value ");
         document.aascUPSIntlShipmentsForm.InvVal.focus();
         return false;
       }
       */
       
       if((document.aascUPSIntlShipmentsForm.CIFlag.value != 'Y' && window.opener.document.DynaShipmentShipSaveForm.country.value != 'CA' &&
            window.opener.document.DynaShipmentShipSaveForm.country.value != 'PR' && window.opener.document.DynaShipmentShipSaveForm.shipFromCountry.value != 'US') &&
                document.aascUPSIntlShipmentsForm.COFlag.value != 'Y' && document.aascUPSIntlShipmentsForm.NAFTAFlag.value != 'Y' && 
                    document.aascUPSIntlShipmentsForm.SEDFlag.value != 'Y' )
        {
            alert("Please Select Commercial Invoice");
            return false;
        }
       
       document.aascUPSIntlShipmentsForm.actionType.value='SAVE';
       document.aascUPSIntlShipmentsForm.submit();
       
       window.opener.document.DynaShipmentShipSaveForm.intlSaveFlag.value = "Y";
       
       window.opener.document.DynaShipmentShipSaveForm.intTotalCustomsValue.value = "Y";
       
       window.opener.document.DynaShipmentShipSaveForm.intlCustomsFlag.value = "Y";
}

function enableCI()
{
    //alert('CI is selected'+document.aascUPSIntlShipmentsForm.CommercialInvoice.checked);
    if(document.aascUPSIntlShipmentsForm.CommercialInvoice.checked)
    {
        document.aascUPSIntlShipmentsForm.CIFlag.value = 'Y';
           //alert('CI also selected');
         //  document.aascUPSIntlShipmentsForm.Quantity.disabled = false;
         //  document.aascUPSIntlShipmentsForm.UnitPrice.disabled = false;
         //  document.aascUPSIntlShipmentsForm.QuantityUnits.disabled = false;
           
         //  document.aascUPSIntlShipmentsForm.InvoiceNumber.disabled = false;
        document.aascUPSIntlShipmentsForm.PuchaseOrderNumber.disabled = false;
        document.aascUPSIntlShipmentsForm.TermsOfSale.disabled = false;
        document.aascUPSIntlShipmentsForm.Purpose.disabled = false;
        document.aascUPSIntlShipmentsForm.Discount.disabled = false;
        document.aascUPSIntlShipmentsForm.FreightCharges.disabled = false;
        document.aascUPSIntlShipmentsForm.InsuranceCharges.disabled = false;
        document.aascUPSIntlShipmentsForm.OtherCharges.disabled = false;
         //  document.aascUPSIntlShipmentsForm.InvoiceDate.disabled = false;
        document.aascUPSIntlShipmentsForm.CurrencyCode.disabled = false;
        document.aascUPSIntlShipmentsForm.comments.disabled = false;
        document.aascUPSIntlShipmentsForm.DeclarationStmt.disabled = false;
           
        var invoiceDate = document.aascUPSIntlShipmentsForm.InvoiceDate.value;
        if(invoiceDate == null || invoiceDate == ''){
            var shipFlag2 = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;
            if(shipFlag2!= 'Y')
                var shipmentDate = window.opener.document.DynaShipmentShipSaveForm.shipmentDate.value;
            else
                var shipmentDate = window.opener.document.DynaShipmentShipSaveForm.shipmentDateName.value;
                document.aascUPSIntlShipmentsForm.InvoiceDate.value = shipmentDate.substring(0,10);
        }
    }
    else
    {
        document.aascUPSIntlShipmentsForm.CIFlag.value = 'N';
          // alert('CI not selected');
         //  document.aascUPSIntlShipmentsForm.Quantity.disabled = true;
         //  document.aascUPSIntlShipmentsForm.UnitPrice.disabled = true;
         //  document.aascUPSIntlShipmentsForm.QuantityUnits.disabled = true;
           
         //  document.aascUPSIntlShipmentsForm.InvoiceNumber.disabled = true;
        document.aascUPSIntlShipmentsForm.PuchaseOrderNumber.disabled = true;
        document.aascUPSIntlShipmentsForm.TermsOfSale.disabled = true;
        document.aascUPSIntlShipmentsForm.Purpose.disabled = true;
        document.aascUPSIntlShipmentsForm.Discount.disabled = true;
        document.aascUPSIntlShipmentsForm.FreightCharges.disabled = true;
        document.aascUPSIntlShipmentsForm.InsuranceCharges.disabled = true;
        document.aascUPSIntlShipmentsForm.OtherCharges.disabled = true;
         //  document.aascUPSIntlShipmentsForm.InvoiceDate.disabled = true;
        document.aascUPSIntlShipmentsForm.CurrencyCode.disabled = true;
        document.aascUPSIntlShipmentsForm.comments.disabled = true;
        document.aascUPSIntlShipmentsForm.DeclarationStmt.disabled = true;
                         
    }
           
         
}

function enableNAFTA()
{
//alert('NAFTA is selected');
        
  //       alert('NAFTA');
         
          
    if(document.aascUPSIntlShipmentsForm.NAFTACO.checked)
    {
        document.aascUPSIntlShipmentsForm.NAFTAFlag.value = 'Y';
    //     alert('NAFTA also selected');
        document.aascUPSIntlShipmentsForm.PreferenceCriteria.disabled = false;
        document.aascUPSIntlShipmentsForm.Producer.disabled = false;
         
     //    document.aascUPSIntlShipmentsForm.NAFTA_ProduceInfo.disabled = false;
        document.aascUPSIntlShipmentsForm.NAFTA_ProduceInfo.src ="buttons/aascDetails1.png";
         
        document.aascUPSIntlShipmentsForm.PCompanyName.disabled = false;
        document.aascUPSIntlShipmentsForm.PAddressLine1.disabled = false;
        document.aascUPSIntlShipmentsForm.PCity.disabled = false;
        document.aascUPSIntlShipmentsForm.PStateProvinceCode.disabled = false;
        document.aascUPSIntlShipmentsForm.PPostalCode.disabled = false;
        document.aascUPSIntlShipmentsForm.PCountryCode.disabled = false;
        document.aascUPSIntlShipmentsForm.RVCCalculationMethod.disabled = false;
        document.aascUPSIntlShipmentsForm.NetCostPeriodBeginDate.disabled = false;
        document.aascUPSIntlShipmentsForm.NetCostPeriodEndDate.disabled = false;
        document.aascUPSIntlShipmentsForm.BlanketPeriodBeginDate.disabled = false;
        document.aascUPSIntlShipmentsForm.BlanketPeriodEndDate.disabled = false;
    }
    else
    {
         //alert('NAFTA not selected');
        document.aascUPSIntlShipmentsForm.NAFTAFlag.value = 'N';
        document.aascUPSIntlShipmentsForm.PreferenceCriteria.disabled = true;
        document.aascUPSIntlShipmentsForm.Producer.disabled = true;
         
      //   document.aascUPSIntlShipmentsForm.NAFTA_ProduceInfo.disabled = true;
        document.aascUPSIntlShipmentsForm.NAFTA_ProduceInfo.src ="buttons/aascDetailsOff1.png";
        document.aascUPSIntlShipmentsForm.PCompanyName.disabled = true;
        document.aascUPSIntlShipmentsForm.PAddressLine1.disabled = true;
        document.aascUPSIntlShipmentsForm.PCity.disabled = true;
        document.aascUPSIntlShipmentsForm.PStateProvinceCode.disabled = true;
        document.aascUPSIntlShipmentsForm.PPostalCode.disabled = true;
        document.aascUPSIntlShipmentsForm.PCountryCode.disabled = true;
        document.aascUPSIntlShipmentsForm.RVCCalculationMethod.disabled = true;
        document.aascUPSIntlShipmentsForm.NetCostPeriodBeginDate.disabled = true;
        document.aascUPSIntlShipmentsForm.NetCostPeriodEndDate.disabled = true;
        document.aascUPSIntlShipmentsForm.BlanketPeriodBeginDate.disabled = true;
        document.aascUPSIntlShipmentsForm.BlanketPeriodEndDate.disabled = true;
         
    }
                  
                  
}
         
function enableUSCO()
{
    // if(document.aascUPSIntlShipmentsForm.SED.checked)
//alert('USCO is selected');
    if(document.aascUPSIntlShipmentsForm.USCO.checked) 
    {
        document.aascUPSIntlShipmentsForm.COFlag.value = 'Y';
      //  alert('USCO also selected');
        document.aascUPSIntlShipmentsForm.NumberOfPieces.disabled = false;
        document.aascUPSIntlShipmentsForm.UExportDate.disabled = false;
        document.aascUPSIntlShipmentsForm.UExportingCarrier.disabled = false;
    }
    else
    {
        document.aascUPSIntlShipmentsForm.COFlag.value = 'N';
         // alert('USCO not selected');
        document.aascUPSIntlShipmentsForm.NumberOfPieces.disabled = true;
        document.aascUPSIntlShipmentsForm.UExportDate.disabled = true;
        document.aascUPSIntlShipmentsForm.UExportingCarrier.disabled = true;
    }
         
    if((document.aascUPSIntlShipmentsForm.SED.checked) || (document.aascUPSIntlShipmentsForm.USCO.checked))
    {
        document.aascUPSIntlShipmentsForm.Weight.disabled = false;
        document.aascUPSIntlShipmentsForm.UOM.disabled = false;
    }
    else
    {
        document.aascUPSIntlShipmentsForm.Weight.disabled = true;
        document.aascUPSIntlShipmentsForm.UOM.disabled = true;
    }
}

function enableSED()
{
//alert('SED is selected');
    if(document.aascUPSIntlShipmentsForm.SED.checked)
    {   
        document.aascUPSIntlShipmentsForm.SEDFlag.value = 'Y';
        document.aascUPSIntlShipmentsForm.Weight.disabled = false;
        document.aascUPSIntlShipmentsForm.UOM.disabled = false;
        document.aascUPSIntlShipmentsForm.SEDTotalValue.disabled = false;
        document.aascUPSIntlShipmentsForm.ScheduleBNumber.disabled = false;
        document.aascUPSIntlShipmentsForm.ScheduleBQuantity.disabled = false;
        document.aascUPSIntlShipmentsForm.ScheduleBUnitOfMeasure.disabled = false;
        document.aascUPSIntlShipmentsForm.ExportType.disabled = false;       
        document.aascUPSIntlShipmentsForm.PointOfOrigin.disabled = false;
        document.aascUPSIntlShipmentsForm.ModeOfTransport.disabled = false;
        document.aascUPSIntlShipmentsForm.SExportDate.disabled = false;
        document.aascUPSIntlShipmentsForm.SExportingCarrier.disabled = false;
        document.aascUPSIntlShipmentsForm.InBondCode.disabled = false;
        document.aascUPSIntlShipmentsForm.EntryNumber.disabled = false;
        document.aascUPSIntlShipmentsForm.LoadingPier.disabled = false;
        document.aascUPSIntlShipmentsForm.PortOfExport.disabled = false;
        document.aascUPSIntlShipmentsForm.PortOfUnloading.disabled = false;
        document.aascUPSIntlShipmentsForm.CarrierIdentificationCode.disabled = false;
        document.aascUPSIntlShipmentsForm.Containerized.disabled = false;
        document.aascUPSIntlShipmentsForm.HazardousMaterials.disabled = false;
        document.aascUPSIntlShipmentsForm.RoutedExportTransaction.disabled = false;
        document.aascUPSIntlShipmentsForm.PartiestoTransaction[0].disabled = false; //Added by Narasimha 16/11/2010
        document.aascUPSIntlShipmentsForm.PartiestoTransaction[1].disabled = false;
       //  document.aascUPSIntlShipmentsForm.License.disabled = false;
        // document.aascUPSIntlShipmentsForm.LicenseExceptionCode.disabled = false;
       //  document.aascUPSIntlShipmentsForm.ECCN.disabled = false;
        document.aascUPSIntlShipmentsForm.LicenseNumber.disabled = false;
        document.aascUPSIntlShipmentsForm.LicenseDate.disabled = false;
         
        // document.aascUPSIntlShipmentsForm.ConsigneeInfo.disabled = false;
        document.aascUPSIntlShipmentsForm.ConsigneeInfo.src ="buttons/aascDetails1.png";
         //document.aascUPSIntlShipmentsForm.FAgentInfo.disabled = false;
        document.aascUPSIntlShipmentsForm.FAgentInfo.src ="buttons/aascDetails1.png";
         //document.aascUPSIntlShipmentsForm.IConsigneeInfo.disabled = false;
        document.aascUPSIntlShipmentsForm.IConsigneeInfo.src ="buttons/aascDetails1.png";
         
        document.aascUPSIntlShipmentsForm.CCompanyName.disabled = false;
        document.aascUPSIntlShipmentsForm.CAddressLine1.disabled = false;
        document.aascUPSIntlShipmentsForm.CCity.disabled = false;
        document.aascUPSIntlShipmentsForm.CStateProvinceCode.disabled = false;
        document.aascUPSIntlShipmentsForm.CPostalCode.disabled = false;
        document.aascUPSIntlShipmentsForm.CCountryCode.disabled = false;
        var LicenseNumber=document.aascUPSIntlShipmentsForm.LicenseNumber.value;
         var ExceptionCodes = document.aascUPSIntlShipmentsForm.ExceptionCodes.value; 
        if((LicenseNumber =='' || LicenseNumber == null) && (ExceptionCodes =='' || ExceptionCodes == null))
        {
            document.aascUPSIntlShipmentsForm.LicenseNumber.disabled = true;
            document.aascUPSIntlShipmentsForm.LicenseDate.disabled = true;
            document.aascUPSIntlShipmentsForm.ExceptionCodes.disabled = true;
            document.aascUPSIntlShipmentsForm.EccnNumber.disabled = true;
        }
         
    }
    else
    {
        document.aascUPSIntlShipmentsForm.SEDFlag.value = 'N';
  //       alert('SED not selected');
        document.aascUPSIntlShipmentsForm.SEDTotalValue.disabled = true;
        document.aascUPSIntlShipmentsForm.ScheduleBNumber.disabled = true;
        document.aascUPSIntlShipmentsForm.ScheduleBQuantity.disabled = true;
        document.aascUPSIntlShipmentsForm.ScheduleBUnitOfMeasure.disabled = true;
        document.aascUPSIntlShipmentsForm.ExportType.disabled = true;
        document.aascUPSIntlShipmentsForm.PointOfOrigin.disabled = true;
        document.aascUPSIntlShipmentsForm.ModeOfTransport.disabled = true;
        document.aascUPSIntlShipmentsForm.SExportDate.disabled = true;
        document.aascUPSIntlShipmentsForm.SExportingCarrier.disabled = true;
        document.aascUPSIntlShipmentsForm.InBondCode.disabled = true;
        document.aascUPSIntlShipmentsForm.EntryNumber.disabled = true;
        document.aascUPSIntlShipmentsForm.LoadingPier.disabled = true;
        document.aascUPSIntlShipmentsForm.PortOfExport.disabled = true;
        document.aascUPSIntlShipmentsForm.PortOfUnloading.disabled = true;
        document.aascUPSIntlShipmentsForm.CarrierIdentificationCode.disabled = true;
        document.aascUPSIntlShipmentsForm.Containerized.disabled = true;
        document.aascUPSIntlShipmentsForm.HazardousMaterials.disabled = true;
        document.aascUPSIntlShipmentsForm.RoutedExportTransaction.disabled = true;
        document.aascUPSIntlShipmentsForm.PartiestoTransaction[0].disabled = true;  //Added by Narasimha 16/11/2010
        document.aascUPSIntlShipmentsForm.PartiestoTransaction[1].disabled = true;
        //document.aascUPSIntlShipmentsForm.License.disabled = true;
        // document.aascUPSIntlShipmentsForm.LicenseExceptionCode.disabled = true;
        //  document.aascUPSIntlShipmentsForm.ECCN.disabled = true;
        document.aascUPSIntlShipmentsForm.LicenseNumber.disabled = true;
        document.aascUPSIntlShipmentsForm.LicenseDate.disabled = true;
        
        // document.aascUPSIntlShipmentsForm.ConsigneeInfo.disabled = true;
        document.aascUPSIntlShipmentsForm.ConsigneeInfo.src ="buttons/aascDetailsOff1.png";
        //document.aascUPSIntlShipmentsForm.FAgentInfo.disabled = true;
        document.aascUPSIntlShipmentsForm.FAgentInfo.src ="buttons/aascDetailsOff1.png";
         //document.aascUPSIntlShipmentsForm.IConsigneeInfo.disabled = true;
        document.aascUPSIntlShipmentsForm.IConsigneeInfo.src ="buttons/aascDetailsOff1.png";
         
        document.aascUPSIntlShipmentsForm.CCompanyName.disabled = true;
        document.aascUPSIntlShipmentsForm.CAddressLine1.disabled = true;
        document.aascUPSIntlShipmentsForm.CCity.disabled = true;
        document.aascUPSIntlShipmentsForm.CStateProvinceCode.disabled = true;
        document.aascUPSIntlShipmentsForm.CPostalCode.disabled = true;
        document.aascUPSIntlShipmentsForm.CCountryCode.disabled = true;
    }
    if((document.aascUPSIntlShipmentsForm.SED.checked) || (document.aascUPSIntlShipmentsForm.USCO.checked))
    {
        document.aascUPSIntlShipmentsForm.Weight.disabled = false;
        document.aascUPSIntlShipmentsForm.UOM.disabled = false;
    }
    else
    {
        document.aascUPSIntlShipmentsForm.Weight.disabled = true;
        document.aascUPSIntlShipmentsForm.UOM.disabled = true;
    }
}


function checkLicNumOrExcpCode(val)
{
    if(document.aascUPSIntlShipmentsForm.SED.checked)
    {
        if(val == "LicenseNumber")
        {
            document.aascUPSIntlShipmentsForm.LicenseNumber.disabled = false;
            document.aascUPSIntlShipmentsForm.LicenseDate.disabled = false;
            document.getElementById("ExceptionCodes").disabled = true;
            document.aascUPSIntlShipmentsForm.EccnNumber.disabled = true;
            document.aascUPSIntlShipmentsForm.EccnNumber.value = "";
            //  document.getElementById("ExceptionCodes").text = "Select";
            document.aascUPSIntlShipmentsForm.ExceptionCodes.options[document.aascUPSIntlShipmentsForm.ExceptionCodes.selectedIndex].text = "Select";
            document.aascUPSIntlShipmentsForm.ExceptionCodes.options[document.aascUPSIntlShipmentsForm.ExceptionCodes.selectedIndex].value = ""
        }

        if(val == "ExceptionCode")
        {
            document.getElementById("ExceptionCodes").disabled = false;
            document.aascUPSIntlShipmentsForm.EccnNumber.disabled = false;
            document.aascUPSIntlShipmentsForm.LicenseNumber.disabled = true;
            document.aascUPSIntlShipmentsForm.LicenseDate.disabled = true;   
            document.aascUPSIntlShipmentsForm.LicenseNumber.value = "";
            document.aascUPSIntlShipmentsForm.LicenseDate.value = "";
        }
    }
}

function saveUSCO()
{

    if(document.aascUPSIntlShipmentsForm.commodityLine.length < 3)
    {
        alert("Please Enter Details of Atleast one Product");
        document.aascUPSIntlShipmentsForm.description.focus();
        return false;
    } 
    var alertStr ="";
    /*var noOfPkgsPerCommodity = trim(document.aascUPSIntlShipmentsForm.NumberOfPieces.value);
    var Weight=trim(document.aascUPSIntlShipmentsForm.Weight.value);
    var UOM=trim(document.aascUPSIntlShipmentsForm.UOM.value);*/
    var UExportDate=trim(document.aascUPSIntlShipmentsForm.UExportDate.value);
    var UExportingCarrier=trim(document.aascUPSIntlShipmentsForm.UExportingCarrier.value);
    /* if(noOfPkgsPerCommodity ==''||noOfPkgsPerCommodity == null)
    {
        alertStr =alertStr+"Please enter \'Number Of Packages Per Commodity\' in Product Details Section\n";
        document.aascUPSIntlShipmentsForm.NumberOfPieces.focus();
        // return false;
    }
    if(Weight=="" || Weight==null)
    {
        alertStr =alertStr+ "Please Enter \'Weight\' in Product Details Section \n";
        document.aascUPSIntlShipmentsForm.Weight.focus();
    }   

    if(UOM=="" || UOM==null)
    {
        alertStr =alertStr+ "Please Enter \'UOM\' in Product Details Section \n";
        document.aascUPSIntlShipmentsForm.UOM.focus();
    }*/

    if(UExportDate=="" || UExportDate==null)
    {
        alertStr =alertStr+ "Please Enter \'ExportDate\' in US Certificate Of Origin Section \n";
        document.aascUPSIntlShipmentsForm.UExportDate.focus();
    }
    if(UExportingCarrier=="" || UExportingCarrier==null)
    {
        alertStr =alertStr+ "Please Enter \'ExportingCarrier\' in US Certificate Of Origin Section \n";
        document.aascUPSIntlShipmentsForm.UExportingCarrier.focus();
    }

    if(isDate(UExportDate) == false)
    {
       //alert(" Please Enter ExportLicense Expiration Date in YYYY-MM-DD format ");
       document.aascUPSIntlShipmentsForm.UExportDate.focus();
       return false;
    }

    if(alertStr != "" && alertStr !=null)
    {
      alert(alertStr);
      return false;
    }
}

function saveCI()
{
    /*  var noOfPieces = trim(document.aascUPSIntlShipmentsForm.Quantity.value);
    var unitPrice = trim(document.aascUPSIntlShipmentsForm.UnitPrice.value);
    var quantityUnits = trim(document.aascUPSIntlShipmentsForm.QuantityUnits.value);*/
    var reasonForExport =trim( document.aascUPSIntlShipmentsForm.Purpose.value);
    var invoiceDate =trim( document.aascUPSIntlShipmentsForm.InvoiceDate.value);
    var currencyCode =trim( document.aascUPSIntlShipmentsForm.CurrencyCode.value);
    var discount = trim(document.aascUPSIntlShipmentsForm.Discount.value);
    //alert("discount  ::"+discount);
    var alertStr = "";
    
    /*    if(noOfPieces ==''||noOfPieces == null)
    {
        alertStr=alertStr+"Please Enter \'Number Of Units\' in Product Details Section \n";
        document.aascUPSIntlShipmentsForm.Quantity.focus();
        // return false;
    }
    if(unitPrice ==''||unitPrice == null)
    {
        alertStr=alertStr+"Please Enter \'Price per unit\' in Product Details Section\n";
        document.aascUPSIntlShipmentsForm.UnitPrice.focus();
        //return false;
    }
    if(quantityUnits ==''||quantityUnits == null)
    {
        alertStr=alertStr+"Please enter \'Unit of Measurement\' in Product Details Section \n";
        document.aascUPSIntlShipmentsForm.QuantityUnits.focus();
        //return false;
    }*/
    if(reasonForExport ==''||reasonForExport == null)
    {
        alertStr=alertStr+"Please enter \'Reason For Export\' in Commercial Invoice Section \n";
        document.aascUPSIntlShipmentsForm.Purpose.focus();
        //return false;
    }
    if(invoiceDate ==''||invoiceDate == null)
    {
        alertStr=alertStr+"Please enter \'Invoice Date\' in Commercial Invoice Section \n";
        document.aascUPSIntlShipmentsForm.InvoiceDate.focus();
        //return false;
    }
    if(currencyCode ==''||currencyCode == null)
    {
        alertStr=alertStr+"Please enter \'Currency Code\' in Commercial Invoice Section \n";
        document.aascUPSIntlShipmentsForm.CurrencyCode.focus();
        // return false;
    }
    if(discount < 0)
    {
        alertStr=alertStr+"Please enter Positive \'Discount\' in Commercial Invoice Section \n";
        document.aascUPSIntlShipmentsForm.Discount.focus();
    }
    if(isDate(invoiceDate) == false)
    {
        //alert(" Please Enter ExportLicense Expiration Date in YYYY-MM-DD format ");
        document.aascUPSIntlShipmentsForm.InvoiceDate.focus();
        return false;
    } 
    if(alertStr != "" && alertStr !=null)
    {
        alert(alertStr);
        return false;
    }      
}

function saveNAFTA()
{
    /*var preferenceCriteria = trim(document.aascUPSIntlShipmentsForm.PreferenceCriteria.value);
    var producer = trim(document.aascUPSIntlShipmentsForm.Producer.value);
    var rvcCalMethod = trim(document.aascUPSIntlShipmentsForm.RVCCalculationMethod.value); */
    var BlanketPeriodBeginDate =trim( document.aascUPSIntlShipmentsForm.BlanketPeriodBeginDate.value);
    var BlanketPeriodEndDate = trim( document.aascUPSIntlShipmentsForm.BlanketPeriodEndDate.value);
    var alertStr = "";

    /* if(preferenceCriteria ==''||preferenceCriteria == null)
    {
       alertStr=alertStr+"Please select Any \'Preference Criteria\' in Product 3 Section \n";
       document.aascUPSIntlShipmentsForm.PreferenceCriteria.focus();
       
    }
    if(producer ==''||producer == null)
    {
       alertStr=alertStr+"Please select \'Producer Info\' in Product Description Section \n";
       document.aascUPSIntlShipmentsForm.Producer.focus();
       
    }
    if(rvcCalMethod ==''||rvcCalMethod == null)
    {
       alertStr=alertStr+"Please select any \'RVC Calculation Method\' in Product Description Section \n";
       document.aascUPSIntlShipmentsForm.RVCCalculationMethod.focus();
       
    }*/
    if (BlanketPeriodBeginDate =="" || BlanketPeriodBeginDate ==null)
    {
        alertStr=alertStr+"Please Enter \'BlanketPeriodBeginDate\' in NAFTA Certificate Of Origin Section \n";
        document.aascUPSIntlShipmentsForm.BlanketPeriodBeginDate.focus();
    }
    
    if (BlanketPeriodEndDate =="" || BlanketPeriodEndDate ==null)
    {
        alertStr=alertStr+"Please Enter \'BlanketPeriodEndDate\' in NAFTA Certificate Of Origin Section \n";
        document.aascUPSIntlShipmentsForm.BlanketPeriodEndDate.focus();
    }

    if(isDate(BlanketPeriodEndDate) == false)
    {
           //alert(" Please Enter ExportLicense Expiration Date in YYYY-MM-DD format ");
           document.aascUPSIntlShipmentsForm.BlanketPeriodEndDate.focus();
           return false;
        
    }

    if(isDate(BlanketPeriodBeginDate) == false)
    {
           //alert(" Please Enter ExportLicense Expiration Date in YYYY-MM-DD format ");
           document.aascUPSIntlShipmentsForm.BlanketPeriodBeginDate.focus();
           return false;
        
    }

      if(alertStr != "" && alertStr !=null)
      {
      alert(alertStr);
      return false;
      }

}

function saveSED()
{
       /*  var pkgWeight = trim(document.aascUPSIntlShipmentsForm.Weight.value);
         var uom = trim(document.aascUPSIntlShipmentsForm.UOM.value);
         var sedTotalValue = trim(document.aascUPSIntlShipmentsForm.SEDTotalValue.value);
         var scheduleBNumber =trim( document.aascUPSIntlShipmentsForm.ScheduleBNumber.value);
         var scheduleBQty =trim( document.aascUPSIntlShipmentsForm.ScheduleBQuantity.value);
         var scheduleBUOM = trim(document.aascUPSIntlShipmentsForm.ScheduleBUnitOfMeasure.value);
         var expType = trim(document.aascUPSIntlShipmentsForm.ExportType.value);*/
         var pointOfOrigin =trim( document.aascUPSIntlShipmentsForm.PointOfOrigin.value);
         var modeOfTransport =trim( document.aascUPSIntlShipmentsForm.ModeOfTransport.value);
         var sExportDate = trim(document.aascUPSIntlShipmentsForm.SExportDate.value);
         var sExportingCarrier = trim(document.aascUPSIntlShipmentsForm.SExportingCarrier.value);
         var inBondCode =trim( document.aascUPSIntlShipmentsForm.InBondCode.value);
         var cCompanyName =trim( document.aascUPSIntlShipmentsForm.CCompanyName.value);
         var cAddressLine1 = trim(document.aascUPSIntlShipmentsForm.CAddressLine1.value);
         var cCity = trim(document.aascUPSIntlShipmentsForm.CCity.value);
         var cStateProvinceCode =trim( document.aascUPSIntlShipmentsForm.CStateProvinceCode.value);
         var cPostalCode = trim(document.aascUPSIntlShipmentsForm.CPostalCode.value);
         var cCountryCode = trim(document.aascUPSIntlShipmentsForm.CCountryCode.value);
         var PartiestoTransaction = "";
         var LicenseDate=document.aascUPSIntlShipmentsForm.LicenseDate.value;
         var LicenseNumber=document.aascUPSIntlShipmentsForm.LicenseNumber.value;
          var ExceptionCodes =document.aascUPSIntlShipmentsForm.ExceptionCodes.value;
          var EccnNumber = document.aascUPSIntlShipmentsForm.EccnNumber.value;
         var EntryNumber = document.aascUPSIntlShipmentsForm.EntryNumber.value;
         
    for (var i=0; i < document.aascUPSIntlShipmentsForm.PartiestoTransaction.length; i++)
    {
        if (document.aascUPSIntlShipmentsForm.PartiestoTransaction[i].checked)
        {
            PartiestoTransaction = document.aascUPSIntlShipmentsForm.PartiestoTransaction[i].value;
        }
    }

    var alertStr = "";
    /*   if(pkgWeight ==''||pkgWeight == null)
       {
       alertStr=alertStr+"Please enter \'Weight of the Package\' in Product Details Section. \n";
       document.aascUPSIntlShipmentsForm.Weight.focus();
      // return false;
       }
       if(uom ==''||uom == null)
       {
       alertStr=alertStr+"Please enter \'Unit of Measurement\' for Package Weight in in Product Details Section.\n";
       document.aascUPSIntlShipmentsForm.UOM.focus();
      // return false;
       }
       if(sedTotalValue ==''||sedTotalValue == null)
       {
       alertStr=alertStr+"Please enter \'SED Total Value\' in Product Details Section.\n";
       document.aascUPSIntlShipmentsForm.SEDTotalValue.focus();
       //return false;
       }
       if(scheduleBNumber ==''||scheduleBNumber == null)
       {
       alertStr=alertStr+"Please enter \'ScheduleBNumber\' for SED in Product Details Section.\n";
       document.aascUPSIntlShipmentsForm.ScheduleBNumber.focus();
       //return false;
       }
       if(scheduleBQty ==''||scheduleBQty == null)
       {
       alertStr=alertStr+"Please enter \'ScheduleBQuantity\' for SED in Product Details Section. \n";
       document.aascUPSIntlShipmentsForm.ScheduleBQuantity.focus();
     //  return false;
       }
        if(scheduleBUOM ==''||scheduleBUOM == null)
       {
       alertStr=alertStr+"Please enter \'ScheduleB UnitOfMeasure\' for SED in Product Details Section \n";
       document.aascUPSIntlShipmentsForm.ScheduleBUnitOfMeasure.focus();
      // return false;
       }
       if(expType ==''||expType == null)
       {
       alertStr=alertStr+"Please enter \'Export Type\' for SED in Product Details Section. \n";
       document.aascUPSIntlShipmentsForm.ExportType.focus();
      // return false;
       } */
    if(pointOfOrigin ==''||pointOfOrigin == null)
    {
       alertStr=alertStr+"Please enter \'Point(State) Of Origin\' for SED in Shipper's Export Declaration Section. \n";
       document.aascUPSIntlShipmentsForm.PointOfOrigin.focus();
      // return false;
    }
    if(modeOfTransport ==''||modeOfTransport == null)
    {
       alertStr=alertStr+"Please select \'Mode of Transport\' in Shipper's Export Declaration Section. \n";
       document.aascUPSIntlShipmentsForm.ModeOfTransport.focus();
     //  return false;
    }
    if(sExportDate ==''||sExportDate == null)
    {
       alertStr=alertStr+"Please enter \'Export Date\' for SED in Shipper's Export Declaration Section. \n";
       document.aascUPSIntlShipmentsForm.SExportDate.focus();
      // return false;
    }
    if(sExportingCarrier ==''||sExportingCarrier == null)
    {
       alertStr=alertStr+"Please enter \'SED Exporting Carrier\' in Shipper's Export Declaration Section.\n";
       document.aascUPSIntlShipmentsForm.SExportingCarrier.focus();
     //  return false;
    }
    if(inBondCode ==''||inBondCode == null)
    {
       alertStr=alertStr+"Please select \'InBondCode\' for SED in Shipper's Export Declaration Section. \n";
       document.aascUPSIntlShipmentsForm.InBondCode.focus();
       //return false;
    }
    if((inBondCode !="70") &&(EntryNumber ==""||EntryNumber == null))
    {
       alertStr=alertStr+"\'InBondCode\' Code is not 70 So please enter \'Entry Number\' for SED in Shipper's Export Declaration Section. \n";
       document.aascUPSIntlShipmentsForm.EntryNumber.focus();
       //return false;
    }
      // alert('PartiestoTransaction::'+PartiestoTransaction);
    if(PartiestoTransaction !="N" && PartiestoTransaction !="R" )
    {
       alertStr=alertStr+"Please Select \'Parties to Transaction\' Radio Button for SED in Shipper's Export Declaration Section. \n";
      // document.aascUPSIntlShipmentsForm.PartiestoTransaction.focus();
       //return false;
    }
       
    if((cCompanyName ==''||cCompanyName == null)||(cAddressLine1 ==''||cAddressLine1 == null)||(cCity ==''||cCity == null)||(cStateProvinceCode ==''||cStateProvinceCode == null)||(cPostalCode ==''||cPostalCode == null)||(cCountryCode ==''||cCountryCode == null))
    {
       alertStr=alertStr+"Please enter \'SED Ultimate Consignee Information\' in Shipper's Export Declaration Section. \n";
       //document.aascUPSIntlShipmentsForm.CCompanyName.focus();
     //  return false;
    }
       
    if((LicenseNumber =='' || LicenseNumber == null) && (ExceptionCodes =='' || ExceptionCodes == null))
    {
       alertStr=alertStr+"Please enter \'License Information\' for SED in Shipper's Export Declaration Section. \n";
      // document.aascUPSIntlShipmentsForm.LicenseNumber.focus();
       //return false;
    }
      // alert("LicenseNumber=="+LicenseNumber);
    if((LicenseNumber !='' || LicenseNumber !=""))
    {
        if(LicenseDate =='' || LicenseDate == null)
        {
                alert("Please enter \'License Date\' for SED in Shipper's Export Declaration Section. \n");
                document.aascUPSIntlShipmentsForm.LicenseDate.focus();
                return false;
        }
    }
       
     //  alert("ExceptionCodes=="+ExceptionCodes);
    if((ExceptionCodes !='' || ExceptionCodes !=""))
    {
           var ExceptionCodesStr=document.aascUPSIntlShipmentsForm.ExceptionCodes.options[document.aascUPSIntlShipmentsForm.ExceptionCodes.selectedIndex].value;
    
            if(ExceptionCodesStr=="CIV" || ExceptionCodesStr=="CTP" || ExceptionCodesStr=="ENC" || ExceptionCodesStr=="KMI" || ExceptionCodesStr=="LVS")
            {
                 if(EccnNumber =='' || EccnNumber == null)
                {
                    alert("Please enter \'Eccn Number\' for SED in Shipper's Export Declaration Section. \n");
                    document.aascUPSIntlShipmentsForm.EccnNumber.focus();
                    return false;
                }
            }    
    }
    /*     if(LicenseDate =='' || LicenseDate == null)
       {
       alertStr=alertStr+"Please enter \'License Date\' for SED in Shipper's Export Declaration Section. \n";
       document.aascUPSIntlShipmentsForm.LicenseDate.focus();
       //return false;
       }
      if(isDate(LicenseDate) == false)
{
      
       document.aascUPSIntlShipmentsForm.LicenseDate.focus();
       return false;
    
}
       */
     /*     if(isNaN(cPostalCode))
         {
          alertStr=alertStr+"Please Enter Numeric Value in the \'Postal Code\' Field in Shipper's Export Declaration Section.\n"; 
          document.aascUPSIntlShipmentsForm.CPostalCode.focus();
         } */
    if(alertStr != "" && alertStr !=null)
    {
        alert(alertStr);
        return false;
    }
}

 //validate Date started

var dtCh= "-";
var minYear=1990;
var maxYear=2100;
function isInteger(s){
    var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) 
            return false;

    }
    // All characters are numbers.
    return true;
}

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
            {
                this[i] = 30
            }
        if (i==2) 
        {
            this[i] = 29
        }
    } 
    return this
}

//date validation
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
function trim(stringToTrim) {
    return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function ltrim(stringToTrim) {
    return stringToTrim.replace(/^\s+/,"");
}
function rtrim(stringToTrim) {
    return stringToTrim.replace(/\s+$/,"");
}

function validateNetCost()
{
  // alert('RVCCalculationMethod in validateNetCost=='+document.aascUPSIntlShipmentsForm.RVCCalculationMethod.value);
    if(document.aascUPSIntlShipmentsForm.RVCCalculationMethod.value == "NO")
    {
         document.aascUPSIntlShipmentsForm.NetCostPeriodEndDate.disabled = true;
         document.aascUPSIntlShipmentsForm.NetCostPeriodBeginDate.disabled = true;
    }
    else
    {
         document.aascUPSIntlShipmentsForm.NetCostPeriodEndDate.disabled = false;
         document.aascUPSIntlShipmentsForm.NetCostPeriodBeginDate.disabled = false;
    }
}

function saveProductDetails()
{
    var alertStr = "";
    var desc = trim(document.aascUPSIntlShipmentsForm.description.value);
    var tariffcode = trim(document.aascUPSIntlShipmentsForm.HarmonizedCode.value);
    var countryOfmanufacture = trim(document.aascUPSIntlShipmentsForm.CountryOfManufacture.value);

    if(desc ==''||desc == null)
    {
       alertStr = alertStr+"Please enter \'Product Description\' in Commodity Line Item Section.\n";
       document.aascUPSIntlShipmentsForm.description.focus();
      // return false;
    }
       
       //SC_UPS
    for (var i = 0; i < desc.length; i++) {
  	
        if(desc.charCodeAt(i)==43||desc.charCodeAt(i)==42||desc.charCodeAt(i)==60||desc.charCodeAt(i)==62||desc.charCodeAt(i)==34)
        {
            alert("Please enter valid Product Description");
            document.aascUPSIntlShipmentsForm.description.focus();
            return false;
        }
    }
       
      /* if(tariffcode ==''||tariffcode == null)
       {
       alertStr = alertStr+"Please enter \'Product Tariff Code\' in Product Details Section\n";
       document.aascUPSIntlShipmentsForm.HarmonizedCode.focus();
       //return false;
       } */
    if(countryOfmanufacture ==''||countryOfmanufacture == null)
    {
       alertStr = alertStr+"Please enter \'Country of Manfacture\' in Commodity Line Item Section\n";
       document.aascUPSIntlShipmentsForm.CountryOfManufacture.focus();
       //return false;
    }
       
    if(alertStr != "" && alertStr !=null)
    {
      alert(alertStr);
      return false;
    }
      
    //alert(document.aascUPSIntlShipmentsForm.COFlag.value);
    if(document.aascUPSIntlShipmentsForm.CIFlag.value != 'Y' && document.aascUPSIntlShipmentsForm.COFlag.value != 'Y' && 
            document.aascUPSIntlShipmentsForm.NAFTAFlag.value != 'Y' && document.aascUPSIntlShipmentsForm.SEDFlag.value != 'Y' )
    {
        alert("Please Select Commercial Invoice");
        return false;
    }
    if(document.aascUPSIntlShipmentsForm.CommercialInvoice.checked)
    {
        document.aascUPSIntlShipmentsForm.CIFlag.value = 'Y';
        if(saveCICommodity() ==false)
	{
            return false;
        }
        
    }
    else
    {
        document.aascUPSIntlShipmentsForm.CIFlag.value = 'N';
    }
      
    if(document.aascUPSIntlShipmentsForm.USCO.checked)
    {
        document.aascUPSIntlShipmentsForm.COFlag.value = 'Y';
        if(saveUSCOCommodity() ==false)
	{
            return false;
        }
    }
    else
    {
        document.aascUPSIntlShipmentsForm.COFlag.value = 'N';
    }

    if(document.aascUPSIntlShipmentsForm.NAFTACO.checked)
    {
        document.aascUPSIntlShipmentsForm.NAFTAFlag.value = 'Y';
        var tariffcode = trim(document.aascUPSIntlShipmentsForm.HarmonizedCode.value);
         /*
         sc_skp_7.0 the below code is commented in 6.7.1*/
        if(tariffcode ==''||tariffcode == null)
        {
            alert('Please enter Product Tariff Code in Commodity Line Item Section');
            document.aascUPSIntlShipmentsForm.HarmonizedCode.focus();
            return false;
        }
        if(saveNaftaCommodity() ==false)
	{
            return false;
        }
    }
    else
    {
        document.aascUPSIntlShipmentsForm.NAFTAFlag.value = 'N';
    }
          
    if(document.aascUPSIntlShipmentsForm.SED.checked)
    {      
        document.aascUPSIntlShipmentsForm.SEDFlag.value = 'Y';
        if(saveSEDCommodity() ==false)
        {
            return false;
        }
    }
    else
    {
        document.aascUPSIntlShipmentsForm.SEDFlag.value = 'N';
    }
    document.aascUPSIntlShipmentsForm.addCommodityFlag.value='Y';
    if(document.aascUPSIntlShipmentsForm.addCommodityFlag.value =='Y')
    {
        document.aascUPSIntlShipmentsForm.NAFTACO.disabled=true;
        document.aascUPSIntlShipmentsForm.SED.disabled = true;
        document.aascUPSIntlShipmentsForm.USCO.disabled = true;
        document.aascUPSIntlShipmentsForm.CommercialInvoice.disabled = true;
    }
  
    //  var commLength = document.aascUPSIntlShipmentsForm.commodityLine.length;
    //    if(document.aascUPSIntlShipmentsForm.actionStr.value == 'ADD')
    //    {
    //    if(commLength == 52 ){
    //       alert(" Only 50 Commodity Items are allowed for save ");
    //       return false;
    //    }
    //    }

    document.aascUPSIntlShipmentsForm.selectLength.value = document.aascUPSIntlShipmentsForm.commodityLine.length + 1;         
    if(window.opener.document.DynaShipmentShipSaveForm.flagShip.value!='Y')
    {
        document.aascUPSIntlShipmentsForm.actionType.value='ADD';
        document.aascUPSIntlShipmentsForm.submit();
    }
    //window.opener.document.DynaShipmentShipSaveForm.intlSaveFlag.value = "Y";
    window.opener.document.DynaShipmentShipSaveForm.intlCustomsFlag.value = "Y";
}

function saveSEDCommodity()
{
    //alert("hi");
    var pkgWeight = trim(document.aascUPSIntlShipmentsForm.Weight.value);
    var uom = trim(document.aascUPSIntlShipmentsForm.UOM.value);
    var sedTotalValue = trim(document.aascUPSIntlShipmentsForm.SEDTotalValue.value);
    var scheduleBNumber =trim( document.aascUPSIntlShipmentsForm.ScheduleBNumber.value);
    var scheduleBQty =trim( document.aascUPSIntlShipmentsForm.ScheduleBQuantity.value);
    var scheduleBUOM = trim(document.aascUPSIntlShipmentsForm.ScheduleBUnitOfMeasure.value);
    var expType = trim(document.aascUPSIntlShipmentsForm.ExportType.value);
    var alertStr ="";
    
    if(pkgWeight ==''||pkgWeight == null)
    {
       alertStr=alertStr+"Please enter \'Weight of the Package\' in Commodity Line Item Section. \n";
       document.aascUPSIntlShipmentsForm.Weight.focus();
      // return false;
    }
    if(isNaN(pkgWeight))
    {
       alertStr=alertStr+"Please enter Numeric Value for \'Weight of the Package\' in Commodity Line Item Section. \n";
       document.aascUPSIntlShipmentsForm.Weight.focus();
      // return false;
    }
    if(pkgWeight < 0)  //Added by Narasimha 16/11/2010
    {
       alertStr=alertStr+"Please enter Positive Numeric Value for \'Weight of the Package\' in Commodity Line Item Section. \n";
       document.aascUPSIntlShipmentsForm.Weight.focus();
    }
    if(uom ==''||uom == null)
    {
       alertStr=alertStr+"Please enter \'Unit of Measurement\' for Package Weight in in Commodity Line Item Section.\n";
       document.aascUPSIntlShipmentsForm.UOM.focus();
      // return false;
    }
    if(sedTotalValue ==''||sedTotalValue == null)
    {
       alertStr=alertStr+"Please enter \'SED Total Value\' in Commodity Line Item Section.\n";
       document.aascUPSIntlShipmentsForm.SEDTotalValue.focus();
       //return false;
    }
    if(sedTotalValue <= 0)
    {
       alertStr=alertStr+"Please enter \'SED Total Value\' in Commodity Line Item Section.\n";
       document.aascUPSIntlShipmentsForm.SEDTotalValue.focus();
       //return false;
    }
    if(isNaN(sedTotalValue))
    {
       alertStr=alertStr+"Please enter numeric value for \'SED Total Value\' in Commodity Line Item Section.\n";
       document.aascUPSIntlShipmentsForm.SEDTotalValue.focus();
       //return false;
    }
    if(scheduleBNumber ==''||scheduleBNumber == null)
    {
       alertStr=alertStr+"Please enter \'ScheduleBNumber\' for SED in Commodity Line Item Section.\n";
       document.aascUPSIntlShipmentsForm.ScheduleBNumber.focus();
       //return false;
    }
    if(scheduleBQty ==''||scheduleBQty == null)
    {
       alertStr=alertStr+"Please enter \'ScheduleBQuantity\' for SED in Commodity Line Item Section. \n";
       document.aascUPSIntlShipmentsForm.ScheduleBQuantity.focus();
     //  return false;
    }
    if(isNaN(scheduleBQty))
    {
       alertStr=alertStr+"Please enter Numeric Value for \'ScheduleBQuantity\' for SED in Commodity Line Item Section. \n";
       document.aascUPSIntlShipmentsForm.ScheduleBQuantity.focus();
     //  return false;
    }
    if(scheduleBUOM ==''||scheduleBUOM == null)
    {
       alertStr=alertStr+"Please enter \'ScheduleB UnitOfMeasure\' for SED in Commodity Line Item Section \n";
       document.aascUPSIntlShipmentsForm.ScheduleBUnitOfMeasure.focus();
      // return false;
    }
    if(expType ==''||expType == null)
    {
       alertStr=alertStr+"Please enter \'Export Type\' for SED in Commodity Line Item Section. \n";
       document.aascUPSIntlShipmentsForm.ExportType.focus();
      // return false;
    }
    if(alertStr != "" && alertStr !=null)
    {
        alert(alertStr);
        return false;
    }
}
       
function saveNaftaCommodity()
{
    var preferenceCriteria = trim(document.aascUPSIntlShipmentsForm.PreferenceCriteria.value);
    var producer = trim(document.aascUPSIntlShipmentsForm.Producer.value);
    var rvcCalMethod = trim(document.aascUPSIntlShipmentsForm.RVCCalculationMethod.value);
    var alertStr ="";

    if(preferenceCriteria ==''||preferenceCriteria == null)
    {
       alertStr=alertStr+"Please select Any \'Preference Criteria\' in Product Description Section \n";
       document.aascUPSIntlShipmentsForm.PreferenceCriteria.focus();
       
    }
    if(producer ==''||producer == null)
    {
       alertStr=alertStr+"Please select \'Producer Info\' in Product Description Section \n";
       document.aascUPSIntlShipmentsForm.Producer.focus();
       
    }
    if(rvcCalMethod ==''||rvcCalMethod == null)
    {
       alertStr=alertStr+"Please select any \'RVC Calculation Method\' in Product Description Section \n";
       document.aascUPSIntlShipmentsForm.RVCCalculationMethod.focus();
       
    }
//alert(rvcCalMethod);
    if(rvcCalMethod == "NC")
    {
        if (document.aascUPSIntlShipmentsForm.NetCostPeriodBeginDate.value =="" || document.aascUPSIntlShipmentsForm.NetCostPeriodBeginDate.value ==null)
        {
            alertStr=alertStr+"Please Enter \'NetCostPeriodBeginDate\' \n";
            document.aascUPSIntlShipmentsForm.NetCostPeriodBeginDate.focus();
        }

        if (document.aascUPSIntlShipmentsForm.NetCostPeriodEndDate.value =="" || document.aascUPSIntlShipmentsForm.NetCostPeriodEndDate.value ==null)
        {
            alertStr=alertStr+"Please Enter \'NetCostPeriodEndDate\' \n";
            document.aascUPSIntlShipmentsForm.NetCostPeriodEndDate.focus();
        }
    }
        
    if(alertStr != "" && alertStr !=null)
    {
      alert(alertStr);
      return false;
    }
}

function saveCICommodity()
{
    var desc = trim(document.aascUPSIntlShipmentsForm.description.value);
    //var tariffcode = trim(document.aascUPSIntlShipmentsForm.HarmonizedCode.value);
    var countryOfmanufacture = trim(document.aascUPSIntlShipmentsForm.CountryOfManufacture.value);

    var noOfPieces = trim(document.aascUPSIntlShipmentsForm.Quantity.value);
    var unitPrice = trim(document.aascUPSIntlShipmentsForm.UnitPrice.value);
    var quantityUnits = trim(document.aascUPSIntlShipmentsForm.QuantityUnits.value);

    var commLength = document.aascUPSIntlShipmentsForm.commodityLine.length;

    var alertStr = "";

    if(commLength >= 2)
    {
        if(desc ==''||desc == null)
        {
            alertStr = alertStr+"Please enter \'Product description\' in Commodity Line Item Section\n";
            document.aascUPSIntlShipmentsForm.description.focus();
              // return false;
        }
      /* if(tariffcode ==''||tariffcode == null)
       {
       alertStr = alertStr+"Please enter \'Product Tariff Code\' in Product Details Section\n";
       document.aascUPSIntlShipmentsForm.HarmonizedCode.focus();
       //return false;
       }*/
        if(countryOfmanufacture ==''||countryOfmanufacture == null)
        {
             alertStr = alertStr+"Please enter \'Country of Manfacture\' in Commodity Line Item Section\n";
             document.aascUPSIntlShipmentsForm.CountryOfManufacture.focus();
             //return false;
        }

        if(noOfPieces ==''||noOfPieces == null)
        {
            alertStr=alertStr+"Please Enter \'Number Of Units\' in Commodity Line Item Section \n";
            document.aascUPSIntlShipmentsForm.Quantity.focus();
            // return false;
        }
        if(unitPrice ==''||unitPrice == null)
        {
            alertStr=alertStr+"Please Enter \'Price per unit\' in Commodity Line Item Section\n";
            document.aascUPSIntlShipmentsForm.UnitPrice.focus();
            //return false;
        }
        if(isNaN(unitPrice))
        {
            alertStr=alertStr+"Please Enter Numeric Value for \'Price per unit\' in Commodity Line Item Section\n";
            document.aascUPSIntlShipmentsForm.UnitPrice.focus();
            //return false;
        } 
        if(unitPrice < 0)  //Added by Narasimha 16/11/2010
        {
            alertStr=alertStr+"Please Enter Positive Value for \'Price per unit\' in Commodity Line Item Section\n";
            document.aascUPSIntlShipmentsForm.UnitPrice.focus();
        }
        if(quantityUnits ==''||quantityUnits == null)
        {
            alertStr=alertStr+"Please enter \'Unit of Measurement\' in Commodity Line Item Section \n";
            document.aascUPSIntlShipmentsForm.QuantityUnits.focus();
            //return false;
        }
        if(alertStr != "" && alertStr !=null)
        {
            alert(alertStr);
            return false;
        }
    }
}

function saveUSCOCommodity()
{
    var alertStr ="";
    var noOfPkgsPerCommodity = trim(document.aascUPSIntlShipmentsForm.NumberOfPieces.value);
    var Weight=trim(document.aascUPSIntlShipmentsForm.Weight.value);
    var UOM=trim(document.aascUPSIntlShipmentsForm.UOM.value);

    if(noOfPkgsPerCommodity ==''||noOfPkgsPerCommodity == null)
    {
       alertStr =alertStr+"Please enter \'Number Of Packages Per Commodity\' in Commodity Line Item Section\n";
       document.aascUPSIntlShipmentsForm.NumberOfPieces.focus();
      // return false;
    }
    if(Weight=="" || Weight==null)
    {
        alertStr =alertStr+ "Please Enter \'Weight\' in Commodity Line Item Section \n";
        document.aascUPSIntlShipmentsForm.Weight.focus();
    }
    if(isNaN(Weight)) //Added by Narasimha 16/11/2010
    {
       alertStr=alertStr+"Please enter Numeric Value for \'Weight of the Package\' in Commodity Line Item Section. \n";
       document.aascUPSIntlShipmentsForm.Weight.focus();
      // return false;
    }
    if(Weight < 0) //Added by Narasimha 16/11/2010
    {
        alertStr=alertStr+"Please enter Positive Numeric Value for \'Weight of the Package\' in Commodity Line Item Section. \n";
        document.aascUPSIntlShipmentsForm.Weight.focus();
    }
    if(UOM=="" || UOM==null)
    {
        alertStr =alertStr+ "Please Enter \'UOM\' in Commodity Line Item Section \n";
        document.aascUPSIntlShipmentsForm.UOM.focus();
    }
    if(alertStr != "" && alertStr !=null)
    {
        alert(alertStr);
        return false;
    }
}



function disableChkBox()
{
 if(document.aascUPSIntlShipmentsForm.addCommodityFlag.value =='Y')
  {
  document.aascUPSIntlShipmentsForm.NAFTACO.disabled=true;
  document.aascUPSIntlShipmentsForm.SED.disabled = true;
  document.aascUPSIntlShipmentsForm.USCO.disabled = true;
  document.aascUPSIntlShipmentsForm.CommercialInvoice.disabled = true;
  }
}

function editOptions()
{
 // var form = document.aascUPSIntlShipmentsForm.commodityLine;
 // var value = form.options.value;
 // alert('hai');
  var form = document.aascUPSIntlShipmentsForm.commodityLine;

    for (var i=0; i<form.options.length; i++){
         if (form.options[i].selected==true){
          var value = form.options[document.aascUPSIntlShipmentsForm.commodityLine.selectedIndex].value;    
          break;
         }else{
         var value ="";
        }
    }
    var shipFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;

      /*if(shipFlag!= 'Y')
    {
      if(chkGeneralDetails() ==false)
             {
             return false;
             }
    } */
    if(value == 111 || value == 222){
        alert("  Please select a Commodity Item to Edit  ");
        return false;
    }
    if(value == '')
    {
        alert("  Please select a Commodity Item to Edit  ");
        return false;
    }
    
    document.aascUPSIntlShipmentsForm.opValue.value = value;
    enableCommodityDetailDiv();
    document.aascUPSIntlShipmentsForm.actionType.value='EDIT';
    //alert('actionType::'+document.aascUPSIntlShipmentsForm.actionType.value);
    document.aascUPSIntlShipmentsForm.submit();
}


function delOptions()
{
//var form = document.aascUPSIntlShipmentsForm.commodityLine;
//var value = form.options.value;

    var form = document.aascUPSIntlShipmentsForm.commodityLine;
    
    for (var i=0; i<form.options.length; i++){
        if (form.options[i].selected==true){
          var value = form.options[document.aascUPSIntlShipmentsForm.commodityLine.selectedIndex].value;    
          break;
        }else{
         var value ="";
        }
    }

    var shipFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;

    /*if(shipFlag!= 'Y')
    {
    if(chkGeneralDetails() ==false)
             {
             return false;
             }
    } */
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
    if(shipFlag!= 'Y')
    {
        document.aascUPSIntlShipmentsForm.actionType.value='DELETE';
        document.aascUPSIntlShipmentsForm.submit();
    }
}
       


//Global variables

var winCal;
var dtToday;
var Cal;
var MonthName;
var WeekDayName1;
var WeekDayName2;
var exDateTime;//Existing Date and Time
var selDate;//selected date. version 1.7
var calSpanID = "calBorder"; // span ID
var domStyle=null; // span DOM object with style
var cnLeft="0";//left coordinate of calendar span
var cnTop="0";//top coordinate of calendar span
var xpos=0; // mouse x position
var ypos=0; // mouse y position
var calHeight=0; // calendar height
var CalWidth=208;// calendar width
var CellWidth=30;// width of day cell.
var TimeMode=24;// TimeMode value. 12 or 24
var StartYear =1990; //First Year in drop down year selection
var EndYear = 5; //End Year offset. i.e. Current Year + 5


//Configurable parameters

//var WindowTitle="DateTime Picker";//Date Time Picker title.

var SpanBorderColor = "#FFFFFF";//span border color
var SpanBgColor = "#FFFFFF";//span background color
var WeekChar=2;//number of character for week day. if 2 then Mo,Tu,We. if 3 then Mon,Tue,Wed.
var DateSeparator="-";//Date Separator, you can change it to "-" if you want.
var ShowLongMonth=true;//Show long month name in Calendar header. example: "January".
var ShowMonthYear=true;//Show Month and Year in Calendar header.
var MonthYearColor="#cc0033";//Font Color of Month and Year in Calendar header.
var WeekHeadColor="#18861B";//var WeekHeadColor="#18861B";//Background Color in Week header.
var SundayColor="#C0F64F";//var SundayColor="#C0F64F";//Background color of Sunday.
var SaturdayColor="#C0F64F";//Background color of Saturday.
var WeekDayColor="white";//Background color of weekdays.
var FontColor="#FFFF33";//color of font in Calendar day cell.
var TodayColor="#FFFF33";//var TodayColor="#FFFF33";//Background color of today.
var SelDateColor = "#8DD53C";//var SelDateColor="#8DD53C";//Backgrond color of selected date in textbox.
var YrSelColor="#cc0033";//color of font of Year selector.
var MthSelColor="#cc0033";//color of font of Month selector if "MonthSelector" is "arrow".
var HoverColor="#E0FF38"; //color when mouse move over.
var ThemeBg="";//Background image of Calendar window.
var CalBgColor="";//Backgroud color of Calendar window.
var PrecedeZero=true;//Preceding zero [true|false]
var MondayFirstDay=false;//true:Use Monday as first day; false:Sunday as first day. [true|false]  //added in version 1.7
var UseImageFiles = true;//Use image files with "arrows" and "close" button

//use the Month and Weekday in your preferred language.

var MonthName=["January", "February", "March", "April", "May", "June","July","August", "September", "October", "November", "December"];
var WeekDayName1=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
var WeekDayName2=["Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"];

document.onmousedown = pickIt;
document.onmousemove = dragIt;
document.onmouseup = dropIt;

function NewCssCal(pCtrl,pFormat,pScroller,pShowTime,pTimeMode,pHideSeconds) {
	// get current date and time
        
	dtToday = new Date();
        
//        alert("dtToday        "+dtToday);
        
	Cal=new Calendar(dtToday);

	if ((pShowTime!=null) && (pShowTime)) {
		Cal.ShowTime=true;
		if ((pTimeMode!=null) &&((pTimeMode=='12')||(pTimeMode=='24')))	{
			TimeMode=pTimeMode;
		}

		else TimeMode='24';

        if (pHideSeconds!=null)
        {
            if (pHideSeconds)
            {Cal.ShowSeconds=false;}
            else
            {Cal.ShowSeconds=true;}

        }

        else
        {
            Cal.ShowSeconds=false;
        }

	}

	if (pCtrl!=null)

		Cal.Ctrl=pCtrl;


	if (pFormat!=null)
		Cal.Format=pFormat.toUpperCase();
	else
	    Cal.Format="MMDDYYYY";

	if (pScroller!=null) {
		if (pScroller.toUpperCase()=="ARROW") {
			Cal.Scroller="ARROW";
		}
		else {
			Cal.Scroller="DROPDOWN";
		}
        }

	exDateTime=document.getElementById(pCtrl).value;

	if (exDateTime!="")	{ //Parse existing Date String
		var Sp1;//Index of Date Separator 1
		var Sp2;//Index of Date Separator 2
		var tSp1;//Index of Time Separator 1
		var tSp1;//Index of Time Separator 2
		var strMonth;
		var strDate;
		var strYear;
		var intMonth;
		var YearPattern;
		var strHour;
		var strMinute;
		var strSecond;
		var winHeight;
		//parse month

		Sp1=exDateTime.indexOf(DateSeparator,0)
		Sp2=exDateTime.indexOf(DateSeparator,(parseInt(Sp1)+1));
		var offset=parseInt(Cal.Format.toUpperCase().lastIndexOf("M"))-parseInt(Cal.Format.toUpperCase().indexOf("M"))-1;

		if ((Cal.Format.toUpperCase()=="DDMMYYYY") || (Cal.Format.toUpperCase()=="DDMMMYYYY")) {
			if (DateSeparator=="") {
				strMonth=exDateTime.substring(2,4+offset);
				strDate=exDateTime.substring(0,2);
				strYear=exDateTime.substring(4+offset,8+offset);
			}
			else {
				if(exDateTime.indexOf("D*") != -1) {   //DTG
					strMonth = exDateTime.substring(8, 11);
					strDate  = exDateTime.substring(0, 2);
					strYear  = "20" + exDateTime.substring(11, 13);  //Hack, nur für Jahreszahlen ab 2000

				} else {
					strMonth=exDateTime.substring(Sp1+1,Sp2);
					strDate=exDateTime.substring(0,Sp1);
					strYear=exDateTime.substring(Sp2+1,Sp2+5);
				}
			}

		}

		else if ((Cal.Format.toUpperCase()=="MMDDYYYY") || (Cal.Format.toUpperCase()=="MMMDDYYYY")) {

			if (DateSeparator=="") {
				strMonth=exDateTime.substring(0,2+offset);
				strDate=exDateTime.substring(2+offset,4+offset);
				strYear=exDateTime.substring(4+offset,8+offset);
			}

			else {

				strMonth=exDateTime.substring(0,Sp1);
				strDate=exDateTime.substring(Sp1+1,Sp2);
				strYear=exDateTime.substring(Sp2+1,Sp2+5);
			}

		}

		else if ((Cal.Format.toUpperCase()=="YYYYMMDD") || (Cal.Format.toUpperCase()=="YYYYMMMDD")) {

			if (DateSeparator=="") {
				strMonth=exDateTime.substring(4,6+offset);
				strDate=exDateTime.substring(6+offset,8+offset);
				strYear=exDateTime.substring(0,4);
			}

			else {
				strMonth=exDateTime.substring(Sp1+1,Sp2);
				strDate=exDateTime.substring(Sp2+1,Sp2+3);
				strYear=exDateTime.substring(0,Sp1);
			}

		}

		else if ((Cal.Format.toUpperCase()=="YYMMDD") || (Cal.Format.toUpperCase()=="YYMMMDD")) {

			if (DateSeparator=="") {
				strMonth=exDateTime.substring(2,4+offset);
				strDate=exDateTime.substring(4+offset,6+offset);
				strYear=exDateTime.substring(0,2);
			}

			else {
				strMonth=exDateTime.substring(Sp1+1,Sp2);
				strDate=exDateTime.substring(Sp2+1,Sp2+3);
				strYear=exDateTime.substring(0,Sp1);
			}

		}

		if (isNaN(strMonth))
			intMonth=Cal.GetMonthIndex(strMonth);
		else
			intMonth=parseInt(strMonth,10)-1;

		if ((parseInt(intMonth,10)>=0) && (parseInt(intMonth,10)<12))
			Cal.Month=intMonth;

		//end parse month

		//parse Date

		if ((parseInt(strDate,10)<=Cal.GetMonDays()) && (parseInt(strDate,10)>=1))
			Cal.Date=strDate;
		//end parse Date

		//parse year

		YearPattern=/^\d{4}$/;
		if (YearPattern.test(strYear))
			Cal.Year=parseInt(strYear,10);

		//end parse year

		//parse time

		if (Cal.ShowTime==true)	{

			//parse AM or PM

			if (TimeMode==12) {
				strAMPM=exDateTime.substring(exDateTime.length-2,exDateTime.length)
				Cal.AMorPM=strAMPM;

			}

			tSp1=exDateTime.indexOf(":",0)
			tSp2=exDateTime.indexOf(":",(parseInt(tSp1)+1));
			if (tSp1>0)	{

				strHour=exDateTime.substring(tSp1,(tSp1)-2);
				Cal.SetHour(strHour);

				strMinute=exDateTime.substring(tSp1+1,tSp1+3);
				Cal.SetMinute(strMinute);

				strSecond=exDateTime.substring(tSp2+1,tSp2+3);
				Cal.SetSecond(strSecond);

			} else if(exDateTime.indexOf("D*") != -1) {   //DTG
				strHour = exDateTime.substring(2, 4);
				Cal.SetHour(strHour);
				strMinute = exDateTime.substring(4, 6);
				Cal.SetMinute(strMinute);

			}
		}

	}

	selDate=new Date(Cal.Year,Cal.Month,Cal.Date);//version 1.7
        RenderCssCal(true);
	
}

function RenderCssCal(bNewCal) {

	if (typeof bNewCal == "undefined" || bNewCal != true) {bNewCal = false;}
	var vCalHeader;
	var vCalData;
	var vCalTime="";

	var i;
	var j;

	var SelectStr;
	var vDayCount=0;
	var vFirstDay;

	calHeight = 0; // reset the window height on refresh

	// Set the default cursor for the calendar

	winCalData="<span style='cursor:auto;'>\n";

	if (ThemeBg==""){CalBgColor="bgcolor='"+WeekDayColor+"'"}
	vCalHeader="<table "+CalBgColor+" background='"+ThemeBg+"' border=1 cellpadding=1 cellspacing=1 width='200px' valign='top'>\n";

	//Table for Month & Year Selector

	vCalHeader+="<tr>\n<td colspan='7'>\n<table border='0' width='200px' cellpadding='0' cellspacing='0'>\n<tr>\n";
	//******************Month and Year selector in dropdown list************************

	if (Cal.Scroller=="DROPDOWN") {
		vCalHeader+="<td align='center'><select name=\"MonthSelector\" onChange=\"javascript:Cal.SwitchMth(this.selectedIndex);RenderCssCal();\">\n";
		for (i=0;i<12;i++) {
			if (i==Cal.Month)
				SelectStr="Selected";
			else
				SelectStr="";
			    vCalHeader+="<option "+SelectStr+" value="+i+">"+MonthName[i]+"</option>\n";

		}

		vCalHeader+="</select></td>\n";
		//Year selector

		vCalHeader+="<td align='center'><select name=\"YearSelector\" size=\"1\" onChange=\"javascript:Cal.SwitchYear(this.value);RenderCssCal();\">\n";
		for (i = StartYear; i <= (dtToday.getFullYear() + EndYear);i++)	{
			if (i==Cal.Year)
				SelectStr="Selected";
			else
				SelectStr="";
			vCalHeader+="<option "+SelectStr+" value="+i+">"+i+"</option>\n";

		}

		vCalHeader+="</select></td>\n";
		calHeight += 30;
	}

	//******************End Month and Year selector in dropdown list*********************

	//******************Month and Year selector in arrow*********************************

    else if (Cal.Scroller=="ARROW")
    {

    if (UseImageFiles)
    {
  		vCalHeader+="<td><img onmousedown='javascript:Cal.DecYear();RenderCssCal();' src='images/cal_fastreverse.gif' width='13px' height='9' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td>\n";//Year scroller (decrease 1 year)
  		vCalHeader+="<td><img onmousedown='javascript:Cal.DecMonth();RenderCssCal();' src='images/cal_reverse.gif' width='13px' height='9' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td>\n";//Month scroller (decrease 1 month)
  		vCalHeader+="<td width='70%' class='calR'><font color='"+YrSelColor+"'>"+Cal.GetMonthName(ShowLongMonth)+" "+Cal.Year+"</font></td>\n"//Month and Year
  		vCalHeader+="<td><img onmousedown='javascript:Cal.IncMonth();RenderCssCal();' src='images/cal_forward.gif' width='13px' height='9' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td>\n";//Month scroller (increase 1 month)
  		vCalHeader+="<td><img onmousedown='javascript:Cal.IncYear();RenderCssCal();' src='images/cal_fastforward.gif' width='13px' height='9' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td>\n";//Year scroller (increase 1 year)

  	    calHeight += 22;
	  }
	  else
	  {
	  	vCalHeader+="<td><span id='dec_year' title='reverse year' onmousedown='javascript:Cal.DecYear();RenderCssCal();' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white; color:"+YrSelColor+"'>-</span></td>";//Year scroller (decrease 1 year)
	  	vCalHeader+="<td><span id='dec_month' title='reverse month' onmousedown='javascript:Cal.DecMonth();RenderCssCal();' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'>&lt;</span></td>\n";//Month scroller (decrease 1 month)
  		vCalHeader+="<td width='70%' class='calR'><font color='"+YrSelColor+"'>"+Cal.GetMonthName(ShowLongMonth)+" "+Cal.Year+"</font></td>\n"//Month and Year
  		vCalHeader+="<td><span id='inc_month' title='forward month' onmousedown='javascript:Cal.IncMonth();RenderCssCal();' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'>&gt;</span></td>\n";//Month scroller (increase 1 month)
  		vCalHeader+="<td><span id='inc_year' title='forward year' onmousedown='javascript:Cal.IncYear();RenderCssCal();'  onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white; color:"+YrSelColor+"'>+</span></td>\n";//Year scroller (increase 1 year)
  	    calHeight += 22;
	  }
	}

	vCalHeader+="</tr>\n</table>\n</td>\n</tr>\n"

  //******************End Month and Year selector in arrow******************************

	//Calendar header shows Month and Year
	if ((ShowMonthYear)&&(Cal.Scroller=="DROPDOWN")) {
        
		vCalHeader+="<tr><td colspan='7' class='calR'>\n<font color='"+MonthYearColor+"'>"+Cal.GetMonthName(ShowLongMonth)+" "+Cal.Year+"</font>\n</td></tr>\n";
	    calHeight += 19;
	}

	//Week day header

	vCalHeader+="<tr><td colspan=\"7\"><table cellspacing=1><tr>\n";
	var WeekDayName=new Array();//Added version 1.7
	if (MondayFirstDay==true)
		WeekDayName=WeekDayName2;
	else
		WeekDayName=WeekDayName1;
	for (i=0;i<7;i++) {
		vCalHeader+="<td bgcolor="+WeekHeadColor+" width='"+CellWidth+"px' class='calTD'><font color='white'>"+WeekDayName[i].substr(0,WeekChar)+"</font></td>\n";
	}

	calHeight += 19;
	vCalHeader+="</tr>\n";
	//Calendar detail
	CalDate=new Date(Cal.Year,Cal.Month);
	CalDate.setDate(1);

	vFirstDay=CalDate.getDay();

	//Added version 1.7

	if (MondayFirstDay==true) {
		vFirstDay-=1;
		if (vFirstDay==-1)
			vFirstDay=6;
	}

	//Added version 1.7

	vCalData="<tr>";
	calHeight += 19;
        
        
	for (i=0;i<vFirstDay;i++) {
		vCalData=vCalData+GenCell();
		vDayCount=vDayCount+1;
	}

	//Added version 1.7

	for (j=1;j<=Cal.GetMonDays();j++) {
		var strCell;
		if((vDayCount%7==0)&&(j > 1)) {
			vCalData=vCalData+"\n<tr>";
		}

		vDayCount=vDayCount+1;
		if ((j==dtToday.getDate())&&(Cal.Month==dtToday.getMonth())&&(Cal.Year==dtToday.getFullYear()))
			strCell=GenCell(j,true,TodayColor);//Highlight today's date
		else {
			if ((j==selDate.getDate())&&(Cal.Month==selDate.getMonth())&&(Cal.Year==selDate.getFullYear())) { //modified version 1.7
				strCell=GenCell(j,true,SelDateColor);
			}
			else {
				if (MondayFirstDay==true) {
					if (vDayCount%7==0)
						strCell=GenCell(j,false,SundayColor);
					else if ((vDayCount+1)%7==0)
						strCell=GenCell(j,false,SaturdayColor);
					else
						strCell=GenCell(j,null,WeekDayColor);
				}
				else {
					if (vDayCount%7==0)
						strCell=GenCell(j,false,SaturdayColor);
					else if ((vDayCount+6)%7==0)
						strCell=GenCell(j,false,SundayColor);
					else
						strCell=GenCell(j,null,WeekDayColor);
				}
			}
		}

		vCalData=vCalData+strCell;

		if((vDayCount%7==0)&&(j<Cal.GetMonDays())) {
			vCalData=vCalData+"\n</tr>";
			calHeight += 19;
		}
	}

	// finish the table proper

	if(!(vDayCount%7) == 0) {
		while(!(vDayCount % 7) == 0) {
			vCalData=vCalData+GenCell();
			vDayCount=vDayCount+1;
		}
	}

	vCalData=vCalData+"\n</table></td></tr>";


	//Time picker
	if (Cal.ShowTime)
	{
		var showHour;
		var ShowArrows=false;
		var HourCellWidth="35px"; //cell width with seconds.
		showHour=Cal.getShowHour();

		if (Cal.ShowSeconds==false && TimeMode==24 )
        {
		   ShowArrows=true;
		   HourCellWidth="10px";
		}

		vCalTime="\n<tr>\n<td colspan='7' align='center'><center>\n<table border='0' width='199px' cellpadding='0' cellspacing='2'>\n<tr>\n<td height='5px' width='"+HourCellWidth+"px'>&nbsp;</td>\n";

		if (ShowArrows && UseImageFiles)
		{
            vCalTime+="<td align='center'><table cellspacing='0' cellpadding='0' style='line-height:0pt'><tr><td><img onmousedown='startSpin(\"Hour\", \"plus\");' onmouseup='stopSpin();' src='images/cal_plus.gif' width='13px' height='9px' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td></tr><tr><td><img onmousedown='startSpin(\"Hour\", \"minus\");' onmouseup='stopSpin();' src='images/cal_minus.gif' width='13px' height='9px' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td></tr></table></td>\n";
		}

		vCalTime+="<td align='center' width='22px'><input type='text' name='hour' maxlength=2 size=1 style='WIDTH: 22px;color:"+MonthYearColor+";' value="+showHour+" onChange=\"javascript:Cal.SetHour(this.value)\">";
		vCalTime+="</td><td align='center' style='font-size:100%;'>:</td><td align='center' width='22px'>";
		vCalTime+="<input type='text' name='minute' maxlength=2 size=1 style='WIDTH: 22px;color:"+MonthYearColor+";' value="+Cal.Minutes+" onChange=\"javascript:Cal.SetMinute(this.value)\">";

		if (Cal.ShowSeconds) {
			vCalTime+="</td><td align='center' style='font-size:100%;'>:</td><td align='center' width='22px'>";
			vCalTime+="<input type='text' name='second' maxlength=2 size=1 style='WIDTH: 22px;color:"+MonthYearColor+";' value="+Cal.Seconds+" onChange=\"javascript:Cal.SetSecond(this.value)\">"; //parseInt(this.value,10)
		}

		if (TimeMode==12) {
			var SelectAm =(Cal.AMorPM=="AM")? "Selected":"";
			var SelectPm =(Cal.AMorPM=="PM")? "Selected":"";

            vCalTime+="</td><td>";
			vCalTime+="<select name=\"ampm\" onChange=\"javascript:Cal.SetAmPm(this.options[this.selectedIndex].value);\">\n";
			vCalTime+="<option "+SelectAm+" value=\"AM\">AM</option>";
			vCalTime+="<option "+SelectPm+" value=\"PM\">PM<option>";
			vCalTime+="</select>";
		}

		if (ShowArrows && UseImageFiles) {
		   vCalTime+="</td>\n<td align='center'><table cellspacing='0' cellpadding='0' style='line-height:0pt'><tr><td><img onmousedown='startSpin(\"Minute\", \"plus\");' onmouseup='stopSpin();' src='images/cal_plus.gif' width='13px' height='9px' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td></tr><tr><td><img onmousedown='startSpin(\"Minute\", \"minus\");' onmouseup='stopSpin();' src='images/cal_minus.gif' width='13px' height='9px' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td></tr></table>";
		}

		vCalTime+="</td>\n<td align='right' valign='bottom' width='"+HourCellWidth+"px'>";
	}

	else
		{vCalTime+="\n<tr>\n<td colspan='7' align='right'>";}

    if (UseImageFiles)
    {
       vCalTime+="<img onmousedown='javascript:closewin(\"" + Cal.Ctrl + "\"); stopSpin();' src='images/cal_close.gif' width='16px' height='14px' onmouseover='changeBorder(this,0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td>";
    }
    else
    {
       vCalTime+="<span id='close_cal' title='close'onmousedown='javascript:closewin(\"" + Cal.Ctrl + "\");' onmouseover='changeBorder(this, 0)'onmouseout='changeBorder(this, 1)' style='border:1px solid white; font-family: Arial;font-size: 10pt;'>x</span></td>";
    }

    vCalTime+="</tr>\n</table></center>\n</td>\n</tr>";
    calHeight += 31;
	vCalTime+="\n</table>\n</span>";

	//end time picker
    var funcCalback="function callback(id, datum) {\n";
    funcCalback+=" var CalId = document.getElementById(id); if (datum== 'undefined') { var d = new Date(); datum = d.getDate() + '/' +(d.getMonth()+1) + '/' + d.getFullYear(); } window.calDatum=datum;CalId.value=datum;\n";
    funcCalback+=" if (Cal.ShowTime) {\n";
    funcCalback+=" CalId.value+=' '+Cal.getShowHour()+':'+Cal.Minutes;\n";
    funcCalback+=" if (Cal.ShowSeconds)\n  CalId.value+=':'+Cal.Seconds;\n";
    funcCalback+=" if (TimeMode==12)\n  CalId.value+=''+Cal.getShowAMorPM();\n";
    funcCalback+="}\n winCal.style.visibility='hidden';\n}\n";


	// determines if there is enough space to open the cal above the position where it is called
	if (ypos > calHeight) {
	   ypos = ypos - calHeight;
	}

	if (winCal == undefined) {
	   var headID = document.getElementsByTagName("head")[0];

     // add javascript function to the span cal
       var e = document.createElement("script");
       e.type = "text/javascript";
       e.language = "javascript";
       e.text = funcCalback;
       headID.appendChild(e);
	   // add stylesheet to the span cal

	   var cssStr = ".calTD {font-family: verdana; font-size: 12px; text-align: center; border:0 }\n";
	   //cssStr+= ".calR {font-family: verdana; font-size: 12px; text-align: center; font-weight: bold; color: red;}"
		cssStr+= ".calR {font-family: verdana; font-size: 12px; text-align: center; font-weight: bold;}"

	   var style = document.createElement("style");
       style.type = "text/css";
       style.rel = "stylesheet";
       if(style.styleSheet) { // IE
          style.styleSheet.cssText = cssStr;
        }

	   else { // w3c
          var cssText = document.createTextNode(cssStr);
          style.appendChild(cssText);
		}

       headID.appendChild(style);
	   // create the outer frame that allows the cal. to be moved
	   var span = document.createElement("span");
       span.id = calSpanID;

       with (span.style) {position = "absolute"; left = (xpos+8)+'px'; top = (ypos-8)+'px'; width = CalWidth+'px'; border = "solid 2pt " + SpanBorderColor; padding = "0pt"; cursor = "move"; backgroundColor = SpanBgColor; zIndex = 100;}
       document.body.appendChild(span)
       winCal=document.getElementById(calSpanID);
    }

    else {
	  winCal.style.visibility = "visible";
	  winCal.style.Height = calHeight;

	  // set the position for a new calendar only
	  if(bNewCal==true){
	     winCal.style.left = (xpos+8)+'px';
	     winCal.style.top = (ypos-8)+'px';
	   }
	}

	winCal.innerHTML=winCalData + vCalHeader + vCalData + vCalTime;
	return true;
}

function GenCell(pValue,pHighLight,pColor) { //Generate table cell with value
	var PValue;
	var PCellStr;
	var vColor;

	var vHLstr1;//HighLight string
	var vHlstr2;
	var vTimeStr;

	if (pValue==null)
		PValue="";
	else
		PValue=pValue;
	if (pColor!=null)
		vColor="bgcolor=\""+pColor+"\"";
	else
		vColor=CalBgColor;
	    if ((pHighLight!=null)&&(pHighLight)) {
		   vHLstr1="<font class='calR'>";vHLstr2="</font>";
		 }
	    else {
		   vHLstr1="";vHLstr2="";
		 }

	if (Cal.ShowTime) {
		vTimeStr=' '+Cal.Hours+':'+Cal.Minutes;
		if (Cal.ShowSeconds)
			vTimeStr+=':'+Cal.Seconds;
		if (TimeMode==12)
			vTimeStr+=' '+Cal.AMorPM;
	}

	else
		vTimeStr="";

	if (PValue!="") {
		//PCellStr="\n<td "+vColor+" class='calTD' style='cursor: pointer;' onmouseover='changeBorder(this, 0);' onmouseout='changeBorder(this, 1);' onClick=\"javascript:callback('"+Cal.Ctrl+"','"+Cal.FormatDate(PValue)+"');\">"+vHLstr1+PValue+vHLstr2+"</td>";

		if(pColor == SaturdayColor || pColor == SundayColor || pColor == SelDateColor || pColor == TodayColor) {
			PCellStr="\n<td "+vColor+" class='calTD' style='cursor: pointer;' onmouseover='changeBorder(this, 0);' onmouseout=\"changeBorder(this, 1, '"+pColor+"');\" onClick=\"javascript:callback('"+Cal.Ctrl+"','"+Cal.FormatDate(PValue)+"');\">"+vHLstr1+PValue+vHLstr2+"</td>";

		}
		else {
			PCellStr="\n<td "+vColor+" class='calTD' style='cursor: pointer;' onmouseover='changeBorder(this, 0);' onmouseout='changeBorder(this, 1);' onClick=\"javascript:callback('"+Cal.Ctrl+"','"+Cal.FormatDate(PValue)+"');\">"+vHLstr1+PValue+vHLstr2+"</td>";
		}
	}
	else

		PCellStr="\n<td "+vColor+" class='calTD'>&nbsp;</td>";

	return PCellStr;

}

function Calendar(pDate,pCtrl) {

	//Properties
	this.Date=pDate.getDate();//selected date
	this.Month=pDate.getMonth();//selected month number
	this.Year=pDate.getFullYear();//selected year in 4 digits
	this.Hours=pDate.getHours();

	if (pDate.getMinutes()<10)
		this.Minutes="0"+pDate.getMinutes();
	else
		this.Minutes=pDate.getMinutes();

	if (pDate.getSeconds()<10)
		this.Seconds="0"+pDate.getSeconds();
	else
		this.Seconds=pDate.getSeconds();


	this.MyWindow=winCal;
	this.Ctrl=pCtrl;
	this.Format="ddMMyyyy";
	this.Separator=DateSeparator;
	this.ShowTime=false;
	this.Scroller="DROPDOWN";
	if (pDate.getHours()<12)
		this.AMorPM="AM";
	else
		this.AMorPM="PM";

	this.ShowSeconds=true;
}



function GetMonthIndex(shortMonthName) {
	for (i=0;i<12;i++) {
		if (MonthName[i].substring(0,3).toUpperCase()==shortMonthName.toUpperCase())
		   {return i;}
	}
}

Calendar.prototype.GetMonthIndex=GetMonthIndex;

function IncYear() {
	Cal.Year++;}
	
Calendar.prototype.IncYear=IncYear;

function DecYear() {
	Cal.Year--;}

Calendar.prototype.DecYear=DecYear;

function IncMonth() {
	Cal.Month++;
	if (Cal.Month>=12) {
		Cal.Month=0;
		Cal.IncYear();
	}
}

Calendar.prototype.IncMonth=IncMonth;

function DecMonth() {
	Cal.Month--;
	if (Cal.Month<0) {
		Cal.Month=11;
		Cal.DecYear();
	}
}

Calendar.prototype.DecMonth=DecMonth;


function SwitchMth(intMth) {
	Cal.Month=intMth;}
Calendar.prototype.SwitchMth=SwitchMth;

function SwitchYear(intYear) {
	Cal.Year=intYear;}
Calendar.prototype.SwitchYear=SwitchYear;

function SetHour(intHour) {
	var MaxHour;
	var MinHour;
	if (TimeMode==24) {
		MaxHour=23;MinHour=0}
	else if (TimeMode==12) {
		MaxHour=12;MinHour=1}
	else
		alert("TimeMode can only be 12 or 24");

	var HourExp=new RegExp("^\\d\\d");
	var SingleDigit=new RegExp("\\d");

	if ((HourExp.test(intHour) || SingleDigit.test(intHour)) && (parseInt(intHour,10)>MaxHour)) {
	    alert('please enter hours less than 24');
            document.getElementById('hour').value='00';
            document.getElementById('hour').style.backgroundColor="#A79DFE";
            intHour = MinHour;
	}

	else if ((HourExp.test(intHour) || SingleDigit.test(intHour)) && (parseInt(intHour,10)<MinHour)) {
  	    alert('enter valid hours');
            document.getElementById('hour').value='00';
            document.getElementById('hour').style.backgroundColor="#A79DFE";
            intHour = '00';
	}

         if(intHour==null || intHour=='')
        {

                intHour = '00';
        }
          if(isNaN(intHour))
        {
        alert('enter valid hours');
        document.getElementById('hour').value='00';
        document.getElementById('hour').style.backgroundColor="#A79DFE";
        intHour = '00';
        }
        if(isInteger(intHour)==false)
        {
        alert('enter valid hours');
        document.getElementById('hour').value='00';
        document.getElementById('hour').style.backgroundColor="#A79DFE";
        intHour = '00';
        }
	if (SingleDigit.test(intHour)) {
		intHour="0"+intHour+"";
	}

	if (HourExp.test(intHour) && (parseInt(intHour,10)<=MaxHour) && (parseInt(intHour,10)>=MinHour)) {
		if ((TimeMode==12) && (Cal.AMorPM=="PM")) {
			if (parseInt(intHour,10)==12)
				Cal.Hours=12;
			else
				Cal.Hours=parseInt(intHour,10)+12;
		}

		else if ((TimeMode==12) && (Cal.AMorPM=="AM")) {
			if (intHour==12)
				intHour-=12;

			Cal.Hours=parseInt(intHour,10);
		}

		else if (TimeMode==24)
			Cal.Hours=parseInt(intHour,10);
	}
        //alert('intHour=='+intHour);
}

Calendar.prototype.SetHour=SetHour;

function SetMinute(intMin) {
	var MaxMin=59;
	var MinMin=0;

	var SingleDigit=new RegExp("\\d");
	var SingleDigit2=new RegExp("^\\d{1}$");
	var MinExp=new RegExp("^\\d{2}$");

	if ((MinExp.test(intMin) || SingleDigit.test(intMin)) && (parseInt(intMin,10)>MaxMin)) {
		alert('please enter minutes less than 60');
                document.getElementById('minute').value='00';
                document.getElementById('minute').style.backgroundColor="#A79DFE";
                intMin = MinMin;
	}

	else if ((MinExp.test(intMin) || SingleDigit.test(intMin)) && (parseInt(intMin,10)<MinMin))	{
		alert('enter valid minutes');
                document.getElementById('minute').value='00';
                document.getElementById('minute').style.backgroundColor="#A79DFE";
                intMin = '00';
	}
        if(intMin==null || intMin=='')
        {

                intMin = '00';
        }
          if(isNaN(intMin))
        {
        alert('enter valid minutes');
        document.getElementById('minute').value='00';
        document.getElementById('minute').style.backgroundColor="#A79DFE";
        intMin = '00';
        }
        if(isInteger(intMin)==false)
        {
        alert('enter valid minutes');
        document.getElementById('minute').value='00';
        document.getElementById('minute').style.backgroundColor="#A79DFE";
        intMin = '00';
        }

	var strMin = intMin + "";
	if (SingleDigit2.test(intMin)) {
		strMin="0"+strMin+"";
	}

	if ((MinExp.test(intMin) || SingleDigit.test(intMin))
	 && (parseInt(intMin,10)<=59) && (parseInt(intMin,10)>=0)) {

	 	Cal.Minutes=strMin;
	}


    //   alert('strMin=='+strMin);
}

Calendar.prototype.SetMinute=SetMinute;

function SetSecond(intSec) {
	var MaxSec=59;
	var MinSec=0;
//alert('intSec=='+intSec);
	var SingleDigit=new RegExp("\\d");
	var SingleDigit2=new RegExp("^\\d{1}$");
	var SecExp=new RegExp("^\\d{2}$");

	if ((SecExp.test(intSec) || SingleDigit.test(intSec)) && (parseInt(intSec,10)>MaxSec)) {
		alert('please enter seconds less than 60');
                document.getElementById('second').value='00';
                document.getElementById('second').style.backgroundColor="#A79DFE";
                intSec = MinSec;
	}

       else if ((SecExp.test(intSec) || SingleDigit.test(intSec)) && (parseInt(intSec,10)<MinSec))	{
		alert('enter valid seconds');
                document.getElementById('second').value='00';
                document.getElementById('second').style.backgroundColor="#A79DFE";
                intSec = '00';
	}
        if(intSec==null || intSec=='')
        {

               // alert('Enter');
                intSec = '00';
        }

        if(isNaN(intSec))
        {
        alert('enter valid seconds');
        document.getElementById('second').value='00';
        document.getElementById('second').style.backgroundColor="#A79DFE";
        intSec = '00';
        }
       //  alert('intSec=='+intSec);

        if(isInteger(intSec)==false)
        {
        alert('enter valid seconds');
        document.getElementById('second').value='00';
        document.getElementById('second').style.backgroundColor="#A79DFE";
        intSec = '00';
        }
	var strSec = intSec + "";
	if (SingleDigit2.test(intSec)) {
		strSec="0"+strSec+"";
	}

	if ((SecExp.test(intSec) || SingleDigit.test(intSec))
	 && (parseInt(intSec,10)<=59) && (parseInt(intSec,10)>=0)) {

	 	Cal.Seconds=strSec;
	}

   //   alert('strSec=='+strSec);

}

Calendar.prototype.SetSecond=SetSecond;

function SetAmPm(pvalue) {
	this.AMorPM=pvalue;
	if (pvalue=="PM") {

		this.Hours=(parseInt(this.Hours,10))+12;
		if (this.Hours==24)
  		    this.Hours=12;
	}

	else if (pvalue=="AM")
		this.Hours-=12;
}

Calendar.prototype.SetAmPm=SetAmPm;

function getShowHour() {
	var finalHour;

    if (TimeMode==12) {
    	if (parseInt(this.Hours,10)==0) {
			this.AMorPM="AM";
			finalHour=parseInt(this.Hours,10)+12;
		}

		else if (parseInt(this.Hours,10)==12) {
			this.AMorPM="PM";
			finalHour=12;
		}

		else if (this.Hours>12)	{
			this.AMorPM="PM";
			if ((this.Hours-12)<10)

				finalHour="0"+((parseInt(this.Hours,10))-12);
			else
				finalHour=parseInt(this.Hours,10)-12;
		}
		else {
			this.AMorPM="AM";
			if (this.Hours<10)

				finalHour="0"+parseInt(this.Hours,10);
			else
				finalHour=this.Hours;
		}
	}

	else if (TimeMode==24) {
		if (this.Hours<10)
			finalHour="0"+parseInt(this.Hours,10);
		else
    		finalHour=this.Hours;
	}

	return finalHour;
}

Calendar.prototype.getShowHour=getShowHour;

function getShowAMorPM() {
	return this.AMorPM;
}

Calendar.prototype.getShowAMorPM=getShowAMorPM;

function GetMonthName(IsLong) {
	var Month=MonthName[this.Month];
	if (IsLong)
		return Month;
	else
		return Month.substr(0,3);
}

Calendar.prototype.GetMonthName=GetMonthName;

function GetMonDays() { //Get number of days in a month

	var DaysInMonth=[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	if (this.IsLeapYear()) {
		DaysInMonth[1]=29;
	}

	return DaysInMonth[this.Month];
}

Calendar.prototype.GetMonDays=GetMonDays;

function IsLeapYear() {
	if ((this.Year%4)==0) {
		if ((this.Year%100==0) && (this.Year%400)!=0) {
			return false;
		}
		else {
			return true;
		}
	}
	else {
		return false;
	}

}

Calendar.prototype.IsLeapYear=IsLeapYear;

function FormatDate(pDate)
{
	var MonthDigit=this.Month+1;
	if (PrecedeZero==true) {
		if (pDate<10)
			pDate="0"+pDate;
		if (MonthDigit<10)
			MonthDigit="0"+MonthDigit;
	}

	if (this.Format.toUpperCase()=="DDMMYYYY")
		return (pDate+DateSeparator+MonthDigit+DateSeparator+this.Year);

	else if (this.Format.toUpperCase()=="DDMMMYYYY")
		return (pDate+DateSeparator+this.GetMonthName(false)+DateSeparator+this.Year);
	else if (this.Format.toUpperCase()=="MMDDYYYY")
		return (MonthDigit+DateSeparator+pDate+DateSeparator+this.Year);
	else if (this.Format.toUpperCase()=="MMMDDYYYY")
		return (this.GetMonthName(false)+DateSeparator+pDate+DateSeparator+this.Year);
	else if (this.Format.toUpperCase()=="YYYYMMDD")
		return (this.Year+DateSeparator+MonthDigit+DateSeparator+pDate);
	else if (this.Format.toUpperCase()=="YYMMDD")
		return (String(this.Year).substring(2,4)+DateSeparator+MonthDigit+DateSeparator+pDate);
	else if (this.Format.toUpperCase()=="YYMMMDD")
		return (String(this.Year).substring(2,4)+DateSeparator+this.GetMonthName(false)+DateSeparator+pDate);
	else if (this.Format.toUpperCase()=="YYYYMMMDD")
		return (this.Year+DateSeparator+this.GetMonthName(false)+DateSeparator+pDate);
	else
		return (pDate+DateSeparator+(this.Month+1)+DateSeparator+this.Year);
}

Calendar.prototype.FormatDate=FormatDate;

function closewin(id) {
   var CalId = document.getElementById(id);
   CalId.focus();
   winCal.style.visibility='hidden';
 }

function changeBorder(element, col, oldBgColor) {
  if (col == 0) {
	element.style.background = HoverColor;
    element.style.borderColor = "black";
    element.style.cursor = "pointer";
  }

  else {
	if(oldBgColor) {
		element.style.background = oldBgColor;
	} else {
		element.style.background = "white";
	}
    element.style.borderColor = "white";
    element.style.cursor = "auto";
  }
}


function pickIt(evt) {
   // accesses the element that generates the event and retrieves its ID
   if (window.addEventListener) { // w3c
	  var objectID = evt.target.id;
      if (objectID.indexOf(calSpanID) != -1){
         var dom = document.getElementById(objectID);
         cnLeft=evt.pageX;
         cnTop=evt.pageY;

         if (dom.offsetLeft){
           cnLeft = (cnLeft - dom.offsetLeft); cnTop = (cnTop - dom.offsetTop);
          }
       }

	  // get mouse position on click
	  xpos = (evt.pageX);
	  ypos = (evt.pageY);
	}

   else { // IE
	  var objectID = event.srcElement.id;
      cnLeft=event.offsetX;
      cnTop=(event.offsetY);

	  // get mouse position on click
	  var de = document.documentElement;
      var b = document.body;

      xpos = event.clientX + (de.scrollLeft || b.scrollLeft) - (de.clientLeft || 0);
      ypos = event.clientY + (de.scrollTop || b.scrollTop) - (de.clientTop || 0);
    }
   // verify if this is a valid element to pick
   if (objectID.indexOf(calSpanID) != -1){
      domStyle = document.getElementById(objectID).style;
    }

   if (domStyle) {
      domStyle.zIndex = 100;
      return false;
    }

   else {
      domStyle = null;
      return;
    }
}



function dragIt(evt) {
   if (domStyle) {
      if (window.event) { //for IE
         domStyle.left = (event.clientX-cnLeft + document.body.scrollLeft)+'px';
         domStyle.top = (event.clientY-cnTop + document.body.scrollTop)+'px';
       } else {  //Firefox
         domStyle.left = (evt.clientX-cnLeft + document.body.scrollLeft)+'px';
         domStyle.top = (evt.clientY-cnTop + document.body.scrollTop)+'px';
       }
    }
}



function dropIt() {
	stopSpin();

   if (domStyle) {
      domStyle = null;
    }
 }
 
 //stops the time spinner
function stopSpin() {
	clearInterval(document.thisLoop);
}

       
 function chkSoldAddress()
{

    window.opener.document.aascUPSIntlShipmentsForm.CompanyName.disabled = false;
    window.opener.document.aascUPSIntlShipmentsForm.AddressLine1.disabled = false;
    window.opener.document.aascUPSIntlShipmentsForm.AddressLine2.disabled = false;
    window.opener.document.aascUPSIntlShipmentsForm.City.disabled = false;
    window.opener.document.aascUPSIntlShipmentsForm.StateProvinceCode.disabled = false;
    window.opener.document.aascUPSIntlShipmentsForm.PostalCode.disabled = false;
    window.opener.document.aascUPSIntlShipmentsForm.CountryCode.disabled = false;
    
    document.UPSIntlAddrForm.TaxId.value= window.opener.document.aascUPSIntlShipmentsForm.TaxIdNum.value;
    document.UPSIntlAddrForm.AttentionName.value= window.opener.document.aascUPSIntlShipmentsForm.SoldToAttention.value;
    
    //alert("AttentionName   :"+document.UPSIntlAddrForm.AttentionName.value);
    
    document.UPSIntlAddrForm.PhoneNum.value= window.opener.document.aascUPSIntlShipmentsForm.SoldToPhone.value;  
    // alert('soldToFlag'+window.opener.document.aascUPSIntlShipmentsForm.soldToFlag.value); 
    
    //if(window.opener.document.aascUPSIntlShipmentsForm.soldToFlag.value == 'true')
    //{
      document.UPSIntlAddrForm.companyName.value= window.opener.document.aascUPSIntlShipmentsForm.CompanyName.value;
      document.UPSIntlAddrForm.address.value= window.opener.document.aascUPSIntlShipmentsForm.AddressLine1.value;
      document.UPSIntlAddrForm.city.value= window.opener.document.aascUPSIntlShipmentsForm.City.value;
      document.UPSIntlAddrForm.state.value= window.opener.document.aascUPSIntlShipmentsForm.StateProvinceCode.value;
      document.UPSIntlAddrForm.postalCode.value= window.opener.document.aascUPSIntlShipmentsForm.PostalCode.value;
      document.UPSIntlAddrForm.countryCode.value= window.opener.document.aascUPSIntlShipmentsForm.CountryCode.value;
    //}
         
    /*document.aascUPSIntlShipmentsForm.CompanyName.value = '';
    document.aascUPSIntlShipmentsForm.AddressLine1.value = '';
     // document.aascUPSIntlShipmentsForm.AddressLine2.value = window.opener.ShipInsertForm.shipToAddressLine2and3.value;
    document.aascUPSIntlShipmentsForm.City.value = '';
    document.aascUPSIntlShipmentsForm.StateProvinceCode.value = '';
    document.aascUPSIntlShipmentsForm.PostalCode.value = '';
    document.aascUPSIntlShipmentsForm.CountryCode.value=''; */
  
}    





function saveSolAddress()
{
    var CompanyName= document.aascUPSIntlShipmentsForm.CompanyName.value;
    var AddressLine1 = document.aascUPSIntlShipmentsForm.AddressLine1.value;
    var AddressLine2 = document.aascUPSIntlShipmentsForm.AddressLine2.value;
    var City = document.aascUPSIntlShipmentsForm.City.value;
    var StateProvinceCode = document.aascUPSIntlShipmentsForm.StateProvinceCode.value;
    var PostalCode = document.aascUPSIntlShipmentsForm.PostalCode.value;
    var CountryCode = document.aascUPSIntlShipmentsForm.CountryCode.value;
    
    //if(document.aascUPSIntlShipmentsForm.ShipTosameSoldTo.value=='N')
    //{
    if(document.aascUPSIntlShipmentsForm.CommercialInvoice.checked==true ||document.aascUPSIntlShipmentsForm.NAFTACO.checked==true){ 
    if(CompanyName =='' || City== '' ||CountryCode== '' || AddressLine1=='')
    {
    alert("Please Enter All Sold to Address Values");
    //document.aascUPSIntlShipmentsForm.CompanyName.focus();
     return false;
    }
    //}
     
    } 

}
     
function chkCommodity()
{

    var commLength = document.aascUPSIntlShipmentsForm.commodityLine.length;
 /* if(commLength <3){
  alert('Please Enter Details of Atleast One Product');
  return false;
  } */
}

function chkGeneralDetails()
{
    if((document.aascUPSIntlShipmentsForm.TaxIdNum.value != '' || document.aascUPSIntlShipmentsForm.SoldToAttention.value != '' ||
       document.aascUPSIntlShipmentsForm.SoldToPhone.value != '' || document.aascUPSIntlShipmentsForm.InvCurCd.value != ''  )&&
       window.opener.document.DynaShipmentShipSaveForm.intTotalCustomsValue.value != "Y")
       {
       alert('Please First Save the Entered General Details');
      
       return false;
       } 
}
function openUpsIntlAddrDetailsPopup(url)
{
    //chkCommodity();
    var addressType=url;
    if(addressType!="NAFTAAddr")
    {
        var commLength = document.aascUPSIntlShipmentsForm.commodityLine.length;
        if(commLength <3){
            alert('Please Enter Details of Atleast One Product');
            return false;
        }
    }
    var newwindow;
    
    var shipFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;
    var locationId = document.getElementById("locationId").value;
    var carrierCode = document.getElementById("carrierCode").value;
    document.aascUPSIntlShipmentsForm.addressType.value = addressType;
    newwindow=window.open("AascUpsIntlAddressAction.action?actionType=upsIntlAddressAction&carrierCode="+carrierCode+"&shipFlag="+shipFlag+"&addressType="+addressType+"&locationId="+locationId,'name','height=250,width=600,scrollbars=yes, resizable=yes');
    if (window.focus) {newwindow.focus()}
    return false;
}        

function loadUpsIntlAddr()
{
    var addressType=window.opener.document.aascUPSIntlShipmentsForm.addressType.value;

    if( shipFlagTemp != 'Y' && addressType == 'SoldToAddr')
    {
        chkSoldAddress();
    }
    else
    {
        document.UPSIntlAddrForm.addressType.value=addressType;
        //alert(addressType+"  onload");
        if(document.UPSIntlAddrForm.addressType.value=='NAFTAAddr'){
             document.UPSIntlAddrForm.companyName.value=window.opener.document.aascUPSIntlShipmentsForm.PCompanyName.value;
             document.UPSIntlAddrForm.address.value=window.opener.document.aascUPSIntlShipmentsForm.PAddressLine1.value;
             document.UPSIntlAddrForm.city.value=window.opener.document.aascUPSIntlShipmentsForm.PCity.value;
             document.UPSIntlAddrForm.state.value=window.opener.document.aascUPSIntlShipmentsForm.PStateProvinceCode.value;
             document.UPSIntlAddrForm.postalCode.value=window.opener.document.aascUPSIntlShipmentsForm.PPostalCode.value;
             document.UPSIntlAddrForm.countryCode.value=window.opener.document.aascUPSIntlShipmentsForm.PCountryCode.value;
             document.UPSIntlAddrForm.TaxId.value= window.opener.document.aascUPSIntlShipmentsForm.NTaxIdNum.value;
        }
        else if(document.UPSIntlAddrForm.addressType.value=='SoldToAddr'){
     
             var ShipTosameSoldToFlag  =  window.opener.document.aascUPSIntlShipmentsForm.ShipTosameSoldTo.value;
             //alert('ShipTosameSoldToFlag=='+ShipTosameSoldToFlag);
             
        //     if(ShipTosameSoldToFlag=='Y')
        //     {
        //         document.UPSIntlAddrForm.companyName.readOnly = true;
        //         document.UPSIntlAddrForm.address.readOnly = true;
        //         document.UPSIntlAddrForm.city.readOnly = true;
        //         document.UPSIntlAddrForm.state.readOnly = true;
        //         document.UPSIntlAddrForm.postalCode.readOnly = true;
        //         document.UPSIntlAddrForm.countryCode.readOnly = true; 
        //     }
        //     else
        //     {
        //         document.UPSIntlAddrForm.companyName.readOnly = false;
        //         document.UPSIntlAddrForm.address.readOnly = false;
        //         document.UPSIntlAddrForm.city.readOnly = false;
        //         document.UPSIntlAddrForm.state.readOnly = false;
        //         document.UPSIntlAddrForm.postalCode.readOnly = false;
        //         document.UPSIntlAddrForm.countryCode.readOnly = false; 
        //     }
             document.UPSIntlAddrForm.companyName.value=window.opener.document.aascUPSIntlShipmentsForm.CompanyName.value;
             document.UPSIntlAddrForm.address.value=window.opener.document.aascUPSIntlShipmentsForm.AddressLine1.value;
             document.UPSIntlAddrForm.city.value=window.opener.document.aascUPSIntlShipmentsForm.City.value;
             document.UPSIntlAddrForm.state.value=window.opener.document.aascUPSIntlShipmentsForm.StateProvinceCode.value;
             document.UPSIntlAddrForm.postalCode.value=window.opener.document.aascUPSIntlShipmentsForm.PostalCode.value;
             document.UPSIntlAddrForm.countryCode.value= window.opener.document.aascUPSIntlShipmentsForm.CountryCode.value;
             document.UPSIntlAddrForm.TaxId.value= window.opener.document.aascUPSIntlShipmentsForm.TaxIdNum.value;
             document.UPSIntlAddrForm.AttentionName.value= window.opener.document.aascUPSIntlShipmentsForm.SoldToAttention.value;
             document.UPSIntlAddrForm.PhoneNum.value= window.opener.document.aascUPSIntlShipmentsForm.SoldToPhone.value;  
        }
        else if(document.UPSIntlAddrForm.addressType.value=='ConsigneeInfo'){
             document.UPSIntlAddrForm.companyName.value=window.opener.document.aascUPSIntlShipmentsForm.CCompanyName.value;
             document.UPSIntlAddrForm.address.value=window.opener.document.aascUPSIntlShipmentsForm.CAddressLine1.value;
             document.UPSIntlAddrForm.city.value=window.opener.document.aascUPSIntlShipmentsForm.CCity.value;
             document.UPSIntlAddrForm.state.value=window.opener.document.aascUPSIntlShipmentsForm.CStateProvinceCode.value;
             document.UPSIntlAddrForm.postalCode.value=window.opener.document.aascUPSIntlShipmentsForm.CPostalCode.value;
            document.UPSIntlAddrForm.countryCode.value= window.opener.document.aascUPSIntlShipmentsForm.CCountryCode.value;
        }
        else if(document.UPSIntlAddrForm.addressType.value=='FAgentInfo'){
             document.UPSIntlAddrForm.companyName.value=window.opener.document.aascUPSIntlShipmentsForm.FCompanyName.value;
             document.UPSIntlAddrForm.address.value=window.opener.document.aascUPSIntlShipmentsForm.FAddressLine1.value;
             document.UPSIntlAddrForm.city.value=window.opener.document.aascUPSIntlShipmentsForm.FCity.value;
             document.UPSIntlAddrForm.state.value=window.opener.document.aascUPSIntlShipmentsForm.FStateProvinceCode.value;
             document.UPSIntlAddrForm.postalCode.value=window.opener.document.aascUPSIntlShipmentsForm.FPostalCode.value;
            document.UPSIntlAddrForm.countryCode.value= window.opener.document.aascUPSIntlShipmentsForm.FCountryCode.value;
            document.UPSIntlAddrForm.TaxId.value= window.opener.document.aascUPSIntlShipmentsForm.FTaxIdNum.value;
        }
        else if(document.UPSIntlAddrForm.addressType.value=='IConsigneeInfo'){
            document.UPSIntlAddrForm.companyName.value=window.opener.document.aascUPSIntlShipmentsForm.ICCompanyName.value;
            document.UPSIntlAddrForm.address.value=window.opener.document.aascUPSIntlShipmentsForm.ICAddressLine1.value;
            document.UPSIntlAddrForm.city.value=window.opener.document.aascUPSIntlShipmentsForm.ICCity.value;
            document.UPSIntlAddrForm.state.value=window.opener.document.aascUPSIntlShipmentsForm.ICStateProvinceCode.value;
            document.UPSIntlAddrForm.postalCode.value=window.opener.document.aascUPSIntlShipmentsForm.ICPostalCode.value;
            document.UPSIntlAddrForm.countryCode.value= window.opener.document.aascUPSIntlShipmentsForm.ICCountryCode.value;
        }
     
        if(document.UPSIntlAddrForm.shipFlag.value == "Y")
        {  
             document.UPSIntlAddrForm.companyName.readOnly = true;
             document.UPSIntlAddrForm.address.readOnly = true;
             document.UPSIntlAddrForm.city.readOnly = true;
             document.UPSIntlAddrForm.state.readOnly = true;
             document.UPSIntlAddrForm.postalCode.readOnly = true;
             document.UPSIntlAddrForm.countryCode.readOnly = true;
             document.UPSIntlAddrForm.save.disabled = true;
             document.UPSIntlAddrForm.close.focus();
        }  
    }
}
     
    
function saveUpsIntlAddrDetails()
{
      var pCode = trim(document.UPSIntlAddrForm.postalCode.value);
      var companyName = document.UPSIntlAddrForm.companyName.value;
      var addressSize = trim(document.UPSIntlAddrForm.address.value).length;
      var citySize = trim(document.UPSIntlAddrForm.city.value).length;
      var stateSize = trim(document.UPSIntlAddrForm.state.value).length;
      var postalCodeSize = trim(document.UPSIntlAddrForm.postalCode.value).length;
      var addressType = trim(document.UPSIntlAddrForm.addressType.value);
      var countryCodeSize =trim(document.UPSIntlAddrForm.countryCode.value).length;
      
      
      
     if(Validation()==false)
     {
      return false;
     }
     else
     {
     
      if(document.UPSIntlAddrForm.addressType.value=='NAFTAAddr'){
     window.opener.document.aascUPSIntlShipmentsForm.PCompanyName.value=document.UPSIntlAddrForm.companyName.value;
     window.opener.document.aascUPSIntlShipmentsForm.PAddressLine1.value=document.UPSIntlAddrForm.address.value;
     window.opener.document.aascUPSIntlShipmentsForm.PCity.value=document.UPSIntlAddrForm.city.value;
     window.opener.document.aascUPSIntlShipmentsForm.PStateProvinceCode.value=document.UPSIntlAddrForm.state.value;
     window.opener.document.aascUPSIntlShipmentsForm.PPostalCode.value=document.UPSIntlAddrForm.postalCode.value;
     window.opener.document.aascUPSIntlShipmentsForm.PCountryCode.value=document.UPSIntlAddrForm.countryCode.value;
     window.opener.document.aascUPSIntlShipmentsForm.NTaxIdNum.value = document.UPSIntlAddrForm.TaxId.value;
     window.close();
     }
     else if(document.UPSIntlAddrForm.addressType.value=='SoldToAddr'){
     window.opener.document.aascUPSIntlShipmentsForm.CompanyName.value=document.UPSIntlAddrForm.companyName.value;
     window.opener.document.aascUPSIntlShipmentsForm.AddressLine1.value=document.UPSIntlAddrForm.address.value;
     window.opener.document.aascUPSIntlShipmentsForm.City.value=document.UPSIntlAddrForm.city.value;
     window.opener.document.aascUPSIntlShipmentsForm.StateProvinceCode.value=document.UPSIntlAddrForm.state.value;
     window.opener.document.aascUPSIntlShipmentsForm.PostalCode.value=document.UPSIntlAddrForm.postalCode.value;
     window.opener.document.aascUPSIntlShipmentsForm.CountryCode.value=document.UPSIntlAddrForm.countryCode.value;
     window.opener.document.aascUPSIntlShipmentsForm.TaxIdNum.value = document.UPSIntlAddrForm.TaxId.value;
     window.opener.document.aascUPSIntlShipmentsForm.SoldToAttention.value = document.UPSIntlAddrForm.AttentionName.value;
     window.opener.document.aascUPSIntlShipmentsForm.SoldToPhone.value = document.UPSIntlAddrForm.PhoneNum.value;
     
     var addorEditSoldTo = document.UPSIntlAddrForm.addOrEditSoldTo.checked;
     if(addorEditSoldTo){
     window.opener.document.aascUPSIntlShipmentsForm.addOrEditImporter.value = document.UPSIntlAddrForm.addOrEditSoldTo.value;
     }
    // alert("window.opener.aascUPSIntlShipmentsForm.addOrEditImporter.value"+window.opener.document.aascUPSIntlShipmentsForm.addOrEditImporter.value);
//     if(window.opener.document.aascUPSIntlShipmentsForm.ShipTosameSoldTo.value=='N'){
//      window.opener.document.aascUPSIntlShipmentsForm.soldToFlag.value='true';
// //alert('soldToFlag'+window.opener.document.aascUPSIntlShipmentsForm.soldToFlag.value); 
// }
      window.close();
     }
     else if(document.UPSIntlAddrForm.addressType.value=='ConsigneeInfo'){
     window.opener.document.aascUPSIntlShipmentsForm.CCompanyName.value=document.UPSIntlAddrForm.companyName.value;
     window.opener.document.aascUPSIntlShipmentsForm.CAddressLine1.value=document.UPSIntlAddrForm.address.value;
     window.opener.document.aascUPSIntlShipmentsForm.CCity.value=document.UPSIntlAddrForm.city.value;
     window.opener.document.aascUPSIntlShipmentsForm.CStateProvinceCode.value=document.UPSIntlAddrForm.state.value;
     window.opener.document.aascUPSIntlShipmentsForm.CPostalCode.value=document.UPSIntlAddrForm.postalCode.value;
     window.opener.document.aascUPSIntlShipmentsForm.CCountryCode.value=document.UPSIntlAddrForm.countryCode.value;
     window.close();
     }
      else if(document.UPSIntlAddrForm.addressType.value=='FAgentInfo'){
     window.opener.document.aascUPSIntlShipmentsForm.FCompanyName.value=document.UPSIntlAddrForm.companyName.value;
     window.opener.document.aascUPSIntlShipmentsForm.FAddressLine1.value=document.UPSIntlAddrForm.address.value;
     window.opener.document.aascUPSIntlShipmentsForm.FCity.value=document.UPSIntlAddrForm.city.value;
     window.opener.document.aascUPSIntlShipmentsForm.FStateProvinceCode.value=document.UPSIntlAddrForm.state.value;
     window.opener.document.aascUPSIntlShipmentsForm.FPostalCode.value=document.UPSIntlAddrForm.postalCode.value;
     window.opener.document.aascUPSIntlShipmentsForm.FCountryCode.value=document.UPSIntlAddrForm.countryCode.value;
     window.opener.document.aascUPSIntlShipmentsForm.FTaxIdNum.value = document.UPSIntlAddrForm.TaxId.value;
     window.close();
     }
      else if(document.UPSIntlAddrForm.addressType.value=='IConsigneeInfo'){
     window.opener.document.aascUPSIntlShipmentsForm.ICCompanyName.value=document.UPSIntlAddrForm.companyName.value;
     window.opener.document.aascUPSIntlShipmentsForm.ICAddressLine1.value=document.UPSIntlAddrForm.address.value;
     window.opener.document.aascUPSIntlShipmentsForm.ICCity.value=document.UPSIntlAddrForm.city.value;
     window.opener.document.aascUPSIntlShipmentsForm.ICStateProvinceCode.value=document.UPSIntlAddrForm.state.value;
     window.opener.document.aascUPSIntlShipmentsForm.ICPostalCode.value=document.UPSIntlAddrForm.postalCode.value;
     window.opener.document.aascUPSIntlShipmentsForm.ICCountryCode.value=document.UPSIntlAddrForm.countryCode.value;
     window.close();
     }
   }
 //  alert('window.opener.aascUPSIntlShipmentsForm.CompanyName.value=='+window.opener.aascUPSIntlShipmentsForm.CompanyName.value);
}

function viewPrinted()
{
//alert("Entered viewPrint()");
//document.aascUPSIntlShipmentsForm.actionType.value='VIEWPRINT';
//document.aascUPSIntlShipmentsForm.submit();
    popupWindow = window.open('aascIntlDocViewPrint.jsp','_blank', 'toolbar=yes, scrollbars=yes, resizable=yes');
    popupWindow.focus();
}       
        
function disableECCNNumber()
{

    var ExceptionCodesStr=document.aascUPSIntlShipmentsForm.ExceptionCodes.options[document.aascUPSIntlShipmentsForm.ExceptionCodes.selectedIndex].value;
    
    if(ExceptionCodesStr=="CIV" || ExceptionCodesStr=="CTP" || ExceptionCodesStr=="ENC" || ExceptionCodesStr=="KMI" || ExceptionCodesStr=="LVS")
    {
     document.aascUPSIntlShipmentsForm.EccnNumber.disabled = false;
    }
    else
    {
     document.aascUPSIntlShipmentsForm.EccnNumber.disabled = true;
    }

}

function Validation()
{
   var addressType = trim(document.UPSIntlAddrForm.addressType.value);

   if(addressType!="SoldToAddr")
   {
      
               if(trim(document.UPSIntlAddrForm.companyName.value)==""){
                         alert("Please Enter Ups Intl Company Name :");
                         document.UPSIntlAddrForm.companyName.focus();
                        return false;                       
                }
             if(trim(document.UPSIntlAddrForm.address.value)==""){
                         alert("Please Enter Ups Intl Address :");
                         document.UPSIntlAddrForm.address.focus();
                          return false;    
                         }
            if(trim(document.UPSIntlAddrForm.city.value)==""){
                         alert("Please Enter Ups Intl City :");
                         document.UPSIntlAddrForm.city.focus();
                          return false;    
                         }
             if(trim(document.UPSIntlAddrForm.state.value)==""){
                         alert("Please Enter Ups Intl State :");
                 document.UPSIntlAddrForm.state.focus();
                  return false;    
                         }
            if(document.UPSIntlAddrForm.postalCode.value==""){
                         alert("Please Enter Ups Intl Postal Code :");
                         document.UPSIntlAddrForm.postalCode.focus();
                          return false;    
                         }
            if(trim(document.UPSIntlAddrForm.countryCode.value)==""){
                         alert("Please Enter Ups Intl CountryCode Code :");
                         document.UPSIntlAddrForm.countryCode.focus();
                          return false;    
                         }    
    }
    
    if((addressType=="FAgentInfo") ||(addressType=="NAFTAAddr"))
   {
     if(trim(document.UPSIntlAddrForm.TaxId.value)==""){
                         alert("Please Enter Ups Intl Tax Id :");
                         document.UPSIntlAddrForm.TaxId.focus();
                        return false;                       
                }
   }
   return true;
}

function ShowFields() // added by khaja to show the selected form fields
{ 
    //  alert('entered ShowFields()');
    var ComInv = document.aascUPSIntlShipmentsForm.CommercialInvoice.checked;
    var USCO = document.aascUPSIntlShipmentsForm.USCO.checked;
    var NAFTACO = document.aascUPSIntlShipmentsForm.NAFTACO.checked;
    var SED= document.aascUPSIntlShipmentsForm.SED.checked ;
    
    
    //alert("in ShowFields ::"+ComInv);
    //alert("in ShowFields ::"+USCO);
    //alert("in ShowFields ::"+NAFTACO);
    //alert("in ShowFields ::"+SED);
    
    
    //alert("box val :"+document.aascUPSIntlShipmentsForm.CommercialInvoice.checked);
    //alert("ComInvoiceID style"+document.getElementById('ComInvoiceID').style.display);
    if(ComInv==true){
        document.getElementById('ComInvoiceID').style.display ="block";
        //document.getElementById('ComInvoiceChargesID').style.display ="block";
        //document.getElementById('cominvHR').style.display ="block";
        //document.getElementById('cominvChargesHR').style.display ="block";
        //document.getElementById('ComInRow').style.display ="block";
        
        
        document.getElementById('USCertificateID').style.display ="none";
        document.getElementById('USRow').style.display ="none";
        
        document.getElementById('NAFTACertificateID').style.display ="none";
        document.getElementById('NAFTARow1').style.display ="none";
        document.getElementById('NAFTARow2').style.display ="none";
        document.getElementById('NAFTARow3').style.display ="none";
        
        document.getElementById('ShippersExportID').style.display ="none";
        document.getElementById('SEDRow1').style.display ="none";
        document.getElementById('SEDRow2').style.display ="none";
        showInvoiceForCA();
    }
    else{  
        document.getElementById('ComInvoiceID').style.display ="none";
        //document.getElementById('ComInvoiceChargesID').style.display ="none";
        //document.getElementById('cominvHR').style.display ="none";
        //document.getElementById('cominvChargesHR').style.display ="none";
        //document.getElementById('ComInRow').style.display ="none";
           showInvoiceForCA();
     }
     
     if(USCO==true){   
         document.getElementById('USCertificateID').style.display ="block";
         document.getElementById('USCertifiHR').style.display ="block";
         document.getElementById('USRow').style.display ="block";
        
        
        document.getElementById('NAFTACertificateID').style.display ="none";
        document.getElementById('NAFTARow1').style.display ="none";
        document.getElementById('NAFTARow2').style.display ="none";
        document.getElementById('NAFTARow3').style.display ="none";
        
        document.getElementById('ShippersExportID').style.display ="none";
        document.getElementById('SEDRow1').style.display ="none";
        document.getElementById('SEDRow2').style.display ="none";
    
    }
    else{  document.getElementById('USCertificateID').style.display ="none";
        document.getElementById('USCertifiHR').style.display ="none";
        document.getElementById('USRow').style.display ="none";
        
    }

    if(NAFTACO==true)
    {
        document.getElementById('NAFTACertificateID').style.display ="block";
        document.getElementById('NAFTACertifiHR').style.display ="block";
        document.getElementById('NAFTARow1').style.display ="block";
        document.getElementById('NAFTARow2').style.display ="block";
        document.getElementById('NAFTARow3').style.display ="block";
        
        document.getElementById('ShippersExportID').style.display ="none";
        document.getElementById('SEDRow1').style.display ="none";
        document.getElementById('SEDRow2').style.display ="none";
    }
    else{   document.getElementById('NAFTACertificateID').style.display ="none";
        document.getElementById('NAFTACertifiHR').style.display ="none";
        document.getElementById('NAFTARow1').style.display ="none";
        document.getElementById('NAFTARow2').style.display ="none";
        document.getElementById('NAFTARow3').style.display ="none";
    }

    if(SED==true){
        document.getElementById('ShippersExportID').style.display ="block";
        document.getElementById('ShipperExpoHR').style.display ="block";
        document.getElementById('SEDRow1').style.display ="block";
        document.getElementById('SEDRow2').style.display ="block";
        document.getElementById('USRow').style.display ="block";
        document.aascUPSIntlShipmentsForm.NumberOfPieces.disabled="true";
    }
    else{
        document.getElementById('ShippersExportID').style.display ="none";
        document.getElementById('ShipperExpoHR').style.display ="none";
        document.getElementById('SEDRow1').style.display ="none";
        document.getElementById('SEDRow2').style.display ="none";
    }
}


function SelectForms() // added  by khaja to hide all forms if check box "Do You Want To Select FORMS :" not selected  
{
    var selectform = document.aascUPSIntlShipmentsForm.SelectForm.checked;
    if(selectform==false){
        document.aascUPSIntlShipmentsForm.CommercialInvoice.checked=false;
        document.aascUPSIntlShipmentsForm.USCO.checked=false;
        document.aascUPSIntlShipmentsForm.NAFTACO.checked=false;
        document.aascUPSIntlShipmentsForm.SED.checked=false ;   
        document.getElementById('TopSaveButtonID').style.display="none";
        document.getElementById('BottomSaveButtonID').style.display="none";
        document.getElementById('SelectFromID').style.display="none";
        document.getElementById('ProductDetailsID').style.display="none";
        //document.getElementById('ProductDetailsHR').style.display="none";
        document.getElementById('GeneralID1').style.display="none";
        document.getElementById('GeneralID2').style.display="none";
        document.getElementById('GeneralID3').style.display="none";
            
        if(window.opener.document.DynaShipmentShipSaveForm.shipToAddressCountry.value=='CA'){
            document.getElementById('InvoiceDetailsID1').style.display="block";
            document.getElementById('InvoiceDetailsID2').style.display="block";
            //document.getElementById('GeneralDetailsHR').style.display="block";
            document.getElementById('BottomSaveButtonID').style.display="block";
            document.getElementById('TopSaveButtonID').style.display="block";
        }else{
     
            document.getElementById('InvoiceDetailsID1').style.display="none";
            document.getElementById('InvoiceDetailsID2').style.display="none";
            //document.getElementById('GeneralDetailsHR').style.display="none";
        }
    }
    else{ //  alert('selectform :else');
        document.getElementById('SelectFromID').style.display="block";
        //alert('selectform :else 1');
        document.getElementById('ProductDetailsID').style.display="block";
        //document.getElementById('ProductDetailsHR').style.display="block";
        document.getElementById('GeneralID1').style.display="block";
        document.getElementById('GeneralID2').style.display="block";
        document.getElementById('GeneralID3').style.display="block";
        document.getElementById('InvoiceDetailsID1').style.display="block";
        document.getElementById('InvoiceDetailsID2').style.display="block";
        //document.getElementById('GeneralDetailsHR').style.display="block";
        document.getElementById('BottomSaveButtonID').style.display="block";
        document.getElementById('TopSaveButtonID').style.display="block";
    }
    ShowFields();
 
}

 
function getAjaxInlCommodityDetails(){
    var currentCommItem=document.aascUPSIntlShipmentsForm.selCommItems.options[document.aascUPSIntlShipmentsForm.selCommItems.selectedIndex].text;
        
    //    alert("currentCommItem  : "+currentCommItem);
        
        
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
              document.aascUPSIntlShipmentsForm.CountryOfManufacture.value=countryOfManufacture;
              
              startIndex  = responseStringDummy.indexOf('*');
              numberOfPieces     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
             // document.aascUPSIntlShipmentsForm.Quantity.value=numberOfPieces;
              
              startIndex  = responseStringDummy.indexOf('*');
              quantityUnits     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
             // document.aascUPSIntlShipmentsForm.Quantity.value=quantityUnits;
              
              startIndex  = responseStringDummy.indexOf('*');
              quantity     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
              document.aascUPSIntlShipmentsForm.Quantity.value=quantity;
             
              startIndex  = responseStringDummy.indexOf('*');
              UOM     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
              document.aascUPSIntlShipmentsForm.QuantityUnits.value=UOM;
              
              if(document.aascUPSIntlShipmentsForm.QuantityUnits.value == "")
              {
                document.aascUPSIntlShipmentsForm.QuantityUnits.value = "";
              
              }
              
              startIndex  = responseStringDummy.indexOf('*');
              unitPrice     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
              document.aascUPSIntlShipmentsForm.UnitPrice.value=unitPrice;
              
             // alert("description   :"+description);
                                 
              startIndex  = responseStringDummy.indexOf('*');
              description     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
              document.aascUPSIntlShipmentsForm.description.value=description;
              
           //   document.aascUPSIntlShipmentsForm.Weight.value=weight;
           
              startIndex  = responseStringDummy.indexOf('*');
              harmonizedCode     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
         //     document.aascUPSIntlShipmentsForm.HarmonizedCode.value=harmonizedCode;
    //          
              startIndex  = responseStringDummy.indexOf('*');
              tariffCode     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
              document.aascUPSIntlShipmentsForm.HarmonizedCode.value=tariffCode;
    //          
    //          exportLicenseExpirationDate = responseStringDummy;
    //          document.aascUPSIntlShipmentsForm.ExportLicenseExpirationDate.value=exportLicenseExpirationDate;
    //          
              
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


function getIntlImporterDetails(){
    var currentImporter = document.UPSIntlAddrForm.selImporterName.value;
    if(currentImporter == 'Select')
    {
//       document.UPSIntlAddrForm.AttentionName.value="";
//       document.UPSIntlAddrForm.companyName.value="";
//       document.UPSIntlAddrForm.PhoneNum.value="";
//       document.UPSIntlAddrForm.address.value="";
//       document.UPSIntlAddrForm.city.value="";
//       document.UPSIntlAddrForm.state.value="";
//       document.UPSIntlAddrForm.postalCode.value="";
//       document.UPSIntlAddrForm.countryCode.value="";
//       document.UPSIntlAddrForm.TaxId.value="";
    }
  
    else {
        getAjaxInlImporterDetail();
    }
}

function getAjaxInlImporterDetail(){
    var currentImporter = document.UPSIntlAddrForm.selImporterName.value;
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
          document.UPSIntlAddrForm.TaxId.value=ImporterTINOrDUNS;
          
          startIndex  = responseStringDummy.indexOf('*');
          ImporterTINOrDUNSType     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          
          startIndex  = responseStringDummy.indexOf('*');
          importerAddress1     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.address.value=importerAddress1;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerAddress2     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCity     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.city.value=importerCity;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCompName     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.companyName.value=importerCompName;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCountryCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.countryCode.value=importerCountryCode;
                             
          startIndex  = responseStringDummy.indexOf('*');
          importerPhoneNum     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.PhoneNum.value=importerPhoneNum;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerPostalCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.postalCode.value=importerPostalCode;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerState     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.state.value=importerState;
          
          importerName = responseStringDummy;
          document.UPSIntlAddrForm.AttentionName.value=importerName;
       }
    }
    var locationId = document.getElementById("locationId").value;
    var url="aascAjaxIntlImporterDetail.jsp?currentImporter="+currentImporter+"&locationId="+locationId;
    xmlHttp.open("GET",url,true);  // Calling 
    xmlHttp.send(null);  
}

function getIntlCommodityLineDetails(){
//alert('entered getIntlCommodityLineDetails()');
    var currentCommItem = document.aascUPSIntlShipmentsForm.selCommItems.value;
    var shipFlag = document.aascUPSIntlShipmentsForm.shipFlagStr.value;
    //alert('currentCommItem:'+currentCommItem);
   

    if(currentCommItem == 'Select')
    {
       disableCommodityDetailDiv();
       document.aascUPSIntlShipmentsForm.CountryOfManufacture.value="US";
       document.aascUPSIntlShipmentsForm.Quantity.value="";
       document.aascUPSIntlShipmentsForm.QuantityUnits.value="";
       document.aascUPSIntlShipmentsForm.UnitPrice.value="";
       document.aascUPSIntlShipmentsForm.description.value="";
       document.aascUPSIntlShipmentsForm.HarmonizedCode.value="";
       document.aascUPSIntlShipmentsForm.addComm.disabled=true;
    }
    else if(currentCommItem == 'Create'){
      //alert('enterd create');
       enableCommodityDetailDiv();
      //alert('after calling enableCommodityDetailDiv()');
       document.aascUPSIntlShipmentsForm.CountryOfManufacture.value="US";
       document.aascUPSIntlShipmentsForm.Quantity.value="";
       document.aascUPSIntlShipmentsForm.QuantityUnits.value="";
       document.aascUPSIntlShipmentsForm.UnitPrice.value="";
       document.aascUPSIntlShipmentsForm.description.value="";
       document.aascUPSIntlShipmentsForm.HarmonizedCode.value="";
       
       if(shipFlag == 'Y'){
        document.aascUPSIntlShipmentsForm.addComm.disabled=true;
       }else{
        document.aascUPSIntlShipmentsForm.addComm.disabled=false;
       }
    }
    else {
        if(shipFlag == 'Y'){
        document.aascUPSIntlShipmentsForm.addComm.disabled=true;
       }else{
        document.aascUPSIntlShipmentsForm.addComm.disabled=false;
       }        enableCommodityDetailDiv();
        
        getAjaxInlCommodityDetails();
    }
}

function enableCommodityDetailDiv(){
    //alert('entered enableCommodityDetailDiv');
     document.getElementById('commodityDetailDiv').style.display ="";
     document.getElementById('updateCommodityDetailDiv').style.display ="";
     document.getElementById('addCommodityDetailDiv').style.display ="";
 
}

function disableCommodityDetailDiv(){
     document.getElementById('commodityDetailDiv').style.display ="none";
     document.getElementById('updateCommodityDetailDiv').style.display ="none";
     document.getElementById('addCommodityDetailDiv').style.display ="none";
}

function totalCustomValue()
{
     var frCharge = document.aascUPSIntlShipmentsForm.FreightCharges.value;
     var inCharge = document.aascUPSIntlShipmentsForm.InsuranceCharges.value;
     var msCharge = document.aascUPSIntlShipmentsForm.OtherCharges.value;
     var lineCustomValue = document.aascUPSIntlShipmentsForm.commCustomValue.value;
     var InvVal = document.aascUPSIntlShipmentsForm.InvVal.value;
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
           document.aascUPSIntlShipmentsForm.FreightCharge.focus();
  	       return false;
        }
     }
     for (var i = 0; i < inCharge.length; i++) {
  	    if(!(inCharge.charCodeAt(i)>47 && inCharge.charCodeAt(i)<58) && inCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascUPSIntlShipmentsForm.InsuranceCharge.focus();
  	       return false;
        }
     }
     for (var i = 0; i < msCharge.length; i++) {
  	    if(!(msCharge.charCodeAt(i)>47 && msCharge.charCodeAt(i)<58) && msCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascUPSIntlShipmentsForm.TaxesOrMiscellaneousCharge.focus();
  	       return false;
        }
     }      
     var rnum = parseFloat(lineCustomValue)+parseFloat(msCharge)+parseFloat(inCharge)+parseFloat(frCharge);
//     var newnumber = 0;
     if (rnum > 8191 && rnum < 10485) {
		     rnum = rnum-5000;
		     newnumber = Math.round(rnum*Math.pow(10,2))/Math.pow(10,2);
		     newnumber = newnumber+5000;
	  } else {
		     newnumber = Math.round(rnum*Math.pow(10,2))/Math.pow(10,2);
	  }
      document.aascUPSIntlShipmentsForm.InvVal.value = newnumber;
     //document.aascUPSIntlShipmentsForm.TotalCustomsValue.value = Math.round(parseFloat(lineCustomValue)+parseFloat(msCharge)+parseFloat(inCharge)+parseFloat(frCharge));
     
     
}
// Added by Ravi Teja to display Invoice details for Canada and Puerto Rico country. Also this function call the totalCustomValue function which calculates Invoice total
function showInvoiceForCA()
{  
    if((window.opener.document.DynaShipmentShipSaveForm.country.value=='CA' || window.opener.document.DynaShipmentShipSaveForm.country.value=='PR') &&
            window.opener.document.DynaShipmentShipSaveForm.shipFromCountryId.value == 'US' && !document.aascUPSIntlShipmentsForm.CommercialInvoice.checked){
       
       //document.getElementById('cominvHR').style.display ="block";
      document.getElementById('ComInvoiceID').style.display ="block";
       document.getElementById('CIDetailsRow1').style.display ="none";
        document.getElementById('CIDetailsRow2').style.display ="none";
         document.getElementById('CIDetailsRow3').style.display ="none";
         document.getElementById('CIDetailsRow4').style.display ="none";
         document.getElementById('CIDetailsRow5').style.display ="none";
         document.getElementById('CMInvoice').style.display ="none";
         
    }
    else{ 
        document.getElementById('CIDetailsRow1').style.display ="";
        document.getElementById('CIDetailsRow2').style.display ="";
         document.getElementById('CIDetailsRow3').style.display ="";
         document.getElementById('CIDetailsRow4').style.display ="";
         document.getElementById('CIDetailsRow5').style.display ="";
         document.getElementById('CMInvoice').style.display ="";
//        if((window.opener.document.DynaShipmentShipSaveForm.country.value=='CA' || window.opener.document.DynaShipmentShipSaveForm.country.value=='PR') &&
//                window.opener.document.DynaShipmentShipSaveForm.shipFromCountryId.value == 'US' && document.aascUPSIntlShipmentsForm.InvVal.value == 0){
            totalCustomValue();
//         }
    }
}

function calculateInvoiceTotal()
{
//    if((window.opener.document.DynaShipmentShipSaveForm.country.value=='CA' || window.opener.document.DynaShipmentShipSaveForm.country.value=='PR') &&
//        window.opener.document.DynaShipmentShipSaveForm.shipFromCountryId.value == 'US' && !document.aascUPSIntlShipmentsForm.CommercialInvoice.checked){
            totalCustomValue();
//    }
}

function assignOrderNumber(){
    window.opener.document.DynaShipmentShipSaveForm.orderNum.value=document.aascUPSIntlShipmentsForm.orderNumber.value;
}
