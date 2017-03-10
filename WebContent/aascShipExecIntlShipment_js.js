/*===========================================================================================================+
|  DESCRIPTION                                                                                              |
|    javascript file for the aascShipExecIntlShipment.jsp  validation                                            |
|    Author joseph                                                                                       |
|    Version   1                                                                                            |                                                                            
|    Creation 01-oct-2015                                                                                   |
History:
   
+===========================================================================================================*/

//load(start)

var shipFlagTemp="";
function load()
{
    opener.document.DynaShipmentShipSaveForm.InvVal.value = document.aascShipExecIntlShipmentsForm.InvVal.value;
    //alert(document.aascShipExecIntlShipmentsForm.ShipTosameSoldTo.value);
    var soldToCompanyName = document.aascShipExecIntlShipmentsForm.CompanyName.value;
    if(soldToCompanyName == null || soldToCompanyName == '') //Need to implement for Adhoc..as per discussion with sudhakar
    {
        document.aascShipExecIntlShipmentsForm.CompanyName.value = window.opener.document.DynaShipmentShipSaveForm.customerName.value;
        document.aascShipExecIntlShipmentsForm.AddressLine1.value = window.opener.document.DynaShipmentShipSaveForm.shipToAddress.value;  
        document.aascShipExecIntlShipmentsForm.City.value = window.opener.document.DynaShipmentShipSaveForm.city.value;
        document.aascShipExecIntlShipmentsForm.StateProvinceCode.value = window.opener.document.DynaShipmentShipSaveForm.state.value;
        document.aascShipExecIntlShipmentsForm.PostalCode.value = window.opener.document.DynaShipmentShipSaveForm.zip.value;
        document.aascShipExecIntlShipmentsForm.CountryCode.value = window.opener.document.DynaShipmentShipSaveForm.country.value;
        document.aascShipExecIntlShipmentsForm.SoldToAttention.value = window.opener.document.DynaShipmentShipSaveForm.contactName.value;
        document.aascShipExecIntlShipmentsForm.SoldToPhone.value = window.opener.document.DynaShipmentShipSaveForm.phoneNumber.value;
 
    } 
 
    var commLength = document.aascShipExecIntlShipmentsForm.commodityLine.length;
    var saveFlag = window.opener.document.DynaShipmentShipSaveForm.intlSaveFlag.value; 
  //alert('commLength::'+commLength);
    if(commLength > 2){
  
        document.aascShipExecIntlShipmentsForm.CommercialInvoice.disabled = true;
        if(saveFlag == "Y")
        {
            document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked = true;
        }
        document.aascShipExecIntlShipmentsForm.USCO.disabled = true;
        document.aascShipExecIntlShipmentsForm.NAFTACO.disabled = true;
        document.aascShipExecIntlShipmentsForm.SED.disabled = true;
    }else{
        document.aascShipExecIntlShipmentsForm.CommercialInvoice.disabled = false;
        document.aascShipExecIntlShipmentsForm.USCO.disabled = false;
        document.aascShipExecIntlShipmentsForm.NAFTACO.disabled = false;
        document.aascShipExecIntlShipmentsForm.SED.disabled = false;
    }
  
    // hideHeaderOptions();
  
    //document.aascShipExecIntlShipmentsForm.Quantity.disabled = true;
    //document.aascShipExecIntlShipmentsForm.UnitPrice.disabled = true;
    //document.aascShipExecIntlShipmentsForm.QuantityUnits.disabled = true;
    // document.aascShipExecIntlShipmentsForm.InvoiceNumber.disabled = true;
    document.aascShipExecIntlShipmentsForm.PuchaseOrderNumber.disabled = true;
    document.aascShipExecIntlShipmentsForm.TermsOfSale.disabled = true;
    document.aascShipExecIntlShipmentsForm.Purpose.disabled = true;
    document.aascShipExecIntlShipmentsForm.Discount.disabled = true;
    document.aascShipExecIntlShipmentsForm.FreightCharges.disabled = true;
    document.aascShipExecIntlShipmentsForm.InsuranceCharges.disabled = true;
    document.aascShipExecIntlShipmentsForm.OtherCharges.disabled = true;
    //  document.aascShipExecIntlShipmentsForm.InvoiceDate.disabled = true;
    document.aascShipExecIntlShipmentsForm.CurrencyCode.disabled = true;
    document.aascShipExecIntlShipmentsForm.comments.disabled = true;
    document.aascShipExecIntlShipmentsForm.DeclarationStmt.disabled = true;
    document.aascShipExecIntlShipmentsForm.PreferenceCriteria.disabled = true;
    document.aascShipExecIntlShipmentsForm.Producer.disabled = true;
    //  document.aascShipExecIntlShipmentsForm.NAFTA_ProduceInfo.disabled = true;
    document.aascShipExecIntlShipmentsForm.NAFTA_ProduceInfo.src ="buttons/aascDetailsOff1.png";
         
    document.aascShipExecIntlShipmentsForm.PCompanyName.disabled = true;
    document.aascShipExecIntlShipmentsForm.PAddressLine1.disabled = true;
    document.aascShipExecIntlShipmentsForm.PCity.disabled = true;
    document.aascShipExecIntlShipmentsForm.PStateProvinceCode.disabled = true;
    document.aascShipExecIntlShipmentsForm.PPostalCode.disabled = true;
    document.aascShipExecIntlShipmentsForm.PCountryCode.disabled = true;
    document.aascShipExecIntlShipmentsForm.RVCCalculationMethod.disabled = true;
    document.aascShipExecIntlShipmentsForm.NetCostPeriodBeginDate.disabled = true;
    document.aascShipExecIntlShipmentsForm.NetCostPeriodEndDate.disabled = true;
    document.aascShipExecIntlShipmentsForm.BlanketPeriodBeginDate.disabled = true;
    document.aascShipExecIntlShipmentsForm.BlanketPeriodEndDate.disabled = true;

    document.aascShipExecIntlShipmentsForm.NumberOfPieces.disabled = true;
    document.aascShipExecIntlShipmentsForm.Weight.disabled = true;
    document.aascShipExecIntlShipmentsForm.UOM.disabled = true;
    document.aascShipExecIntlShipmentsForm.UExportDate.disabled = true;
    document.aascShipExecIntlShipmentsForm.UExportingCarrier.disabled = true;


    document.aascShipExecIntlShipmentsForm.SEDTotalValue.disabled = true;
    document.aascShipExecIntlShipmentsForm.ScheduleBNumber.disabled = true;
    document.aascShipExecIntlShipmentsForm.ScheduleBQuantity.disabled = true;
    document.aascShipExecIntlShipmentsForm.ScheduleBUnitOfMeasure.disabled = true;
    document.aascShipExecIntlShipmentsForm.ExportType.disabled = true;
    document.aascShipExecIntlShipmentsForm.PointOfOrigin.disabled = true;
    document.aascShipExecIntlShipmentsForm.ModeOfTransport.disabled = true;
    document.aascShipExecIntlShipmentsForm.SExportDate.disabled = true;
    document.aascShipExecIntlShipmentsForm.SExportingCarrier.disabled = true;
    document.aascShipExecIntlShipmentsForm.InBondCode.disabled = true;
    document.aascShipExecIntlShipmentsForm.EntryNumber.disabled = true;
    document.aascShipExecIntlShipmentsForm.LoadingPier.disabled = true;
    document.aascShipExecIntlShipmentsForm.PortOfExport.disabled = true;
    document.aascShipExecIntlShipmentsForm.PortOfUnloading.disabled = true;
    document.aascShipExecIntlShipmentsForm.CarrierIdentificationCode.disabled = true;
    document.aascShipExecIntlShipmentsForm.Containerized.disabled = true;
    document.aascShipExecIntlShipmentsForm.HazardousMaterials.disabled = true;
    document.aascShipExecIntlShipmentsForm.RoutedExportTransaction.disabled = true;
    document.aascShipExecIntlShipmentsForm.PartiestoTransaction[0].disabled = true; //Added by Narasimha 16/11/2010
    document.aascShipExecIntlShipmentsForm.PartiestoTransaction[1].disabled = true;
    // document.aascShipExecIntlShipmentsForm.License.disabled = true;
    // document.aascShipExecIntlShipmentsForm.LicenseExceptionCode.disabled = true;
    //  document.aascShipExecIntlShipmentsForm.ECCN.disabled = true;
    document.aascShipExecIntlShipmentsForm.LicenseNumber.disabled = true;
    document.aascShipExecIntlShipmentsForm.LicenseDate.disabled = true;
         
    //document.aascShipExecIntlShipmentsForm.ConsigneeInfo.disabled = true;
    document.aascShipExecIntlShipmentsForm.ConsigneeInfo.src ="buttons/aascDetailsOff1.png";
    //document.aascShipExecIntlShipmentsForm.FAgentInfo.disabled = true;
    document.aascShipExecIntlShipmentsForm.FAgentInfo.src ="buttons/aascDetailsOff1.png";
    //document.aascShipExecIntlShipmentsForm.IConsigneeInfo.disabled = true;
    document.aascShipExecIntlShipmentsForm.IConsigneeInfo.src ="buttons/aascDetailsOff1.png";
         
    document.aascShipExecIntlShipmentsForm.CCompanyName.disabled = true;
    document.aascShipExecIntlShipmentsForm.CAddressLine1.disabled = true;
    document.aascShipExecIntlShipmentsForm.CCity.disabled = true;
    document.aascShipExecIntlShipmentsForm.CStateProvinceCode.disabled = true;
    document.aascShipExecIntlShipmentsForm.CPostalCode.disabled = true;
    document.aascShipExecIntlShipmentsForm.CCountryCode.disabled = true;
         
         
    var LicenseNumber=document.aascShipExecIntlShipmentsForm.LicenseNumber.value;
    var ExceptionCodes = document.aascShipExecIntlShipmentsForm.ExceptionCodes.value; 
    if((LicenseNumber =='' || LicenseNumber == null) && (ExceptionCodes =='' || ExceptionCodes == null))
    {
        document.aascShipExecIntlShipmentsForm.LicenseNumber.disabled = true;
        document.aascShipExecIntlShipmentsForm.LicenseDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.ExceptionCodes.disabled = true;
        document.aascShipExecIntlShipmentsForm.EccnNumber.disabled = true;
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

    if (document.aascShipExecIntlShipmentsForm.shipment.value == 'adhoc')
    {
        var shipFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;
        document.aascShipExecIntlShipmentsForm.shipment.value = window.opener.document.DynaShipmentShipSaveForm.shipmentType.value;
    }     

    if (document.aascShipExecIntlShipmentsForm.shipment.value == 'adhoc')
        document.aascShipExecIntlShipmentsForm.orderNo.value = window.opener.document.DynaShipmentShipSaveForm.orderNum.value;
    var shipFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value; 
    document.aascShipExecIntlShipmentsForm.shipment.value = window.opener.document.DynaShipmentShipSaveForm.shipmentType.value;

    //alert("shipFlag   "+shipFlag);     
    var descriptionVal = document.aascShipExecIntlShipmentsForm.description.value;
    //alert('descriptionVal::'+descriptionVal);
    if(descriptionVal.length < 1){
        getIntlCommodityLineDetails(); 
    }

    if(shipFlag == 'Y')
    {
    //alert("hi");
           
        document.aascShipExecIntlShipmentsForm.description.disabled = true;
        document.aascShipExecIntlShipmentsForm.HarmonizedCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.CountryOfManufacture.disabled = true;
        document.aascShipExecIntlShipmentsForm.Quantity.disabled = true;
        document.aascShipExecIntlShipmentsForm.UnitPrice.disabled = true;
        document.aascShipExecIntlShipmentsForm.QuantityUnits.disabled = true;
        document.aascShipExecIntlShipmentsForm.InvoiceNumber.disabled = true;
        document.aascShipExecIntlShipmentsForm.PuchaseOrderNumber.disabled = true;
        document.aascShipExecIntlShipmentsForm.TermsOfSale.disabled = true;
        document.aascShipExecIntlShipmentsForm.Purpose.disabled = true;
        document.aascShipExecIntlShipmentsForm.Discount.disabled = true;
        document.aascShipExecIntlShipmentsForm.FreightCharges.disabled = true;
        document.aascShipExecIntlShipmentsForm.InsuranceCharges.disabled = true;
        document.aascShipExecIntlShipmentsForm.OtherCharges.disabled = true;
        document.aascShipExecIntlShipmentsForm.InvoiceDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.CurrencyCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.comments.disabled = true;
        document.aascShipExecIntlShipmentsForm.DeclarationStmt.disabled = true;
        document.aascShipExecIntlShipmentsForm.PreferenceCriteria.disabled = true;
        document.aascShipExecIntlShipmentsForm.Producer.disabled = true;
           
      //document.aascShipExecIntlShipmentsForm.NAFTA_ProduceInfo.disabled = true;
     // document.aascShipExecIntlShipmentsForm.NAFTA_ProduceInfo.src ="buttons/aascDetailsOff1.png";
           
        document.aascShipExecIntlShipmentsForm.PCompanyName.disabled = true;
        document.aascShipExecIntlShipmentsForm.PAddressLine1.disabled = true;
        document.aascShipExecIntlShipmentsForm.PCity.disabled = true;
        document.aascShipExecIntlShipmentsForm.PStateProvinceCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.PPostalCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.PCountryCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.RVCCalculationMethod.disabled = true;
        document.aascShipExecIntlShipmentsForm.NetCostPeriodBeginDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.NetCostPeriodEndDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.BlanketPeriodBeginDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.BlanketPeriodEndDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.NumberOfPieces.disabled = true;
        document.aascShipExecIntlShipmentsForm.Weight.disabled = true;
        document.aascShipExecIntlShipmentsForm.UOM.disabled = true;
        document.aascShipExecIntlShipmentsForm.UExportDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.UExportingCarrier.disabled = true;
        document.aascShipExecIntlShipmentsForm.SEDTotalValue.disabled = true;
        document.aascShipExecIntlShipmentsForm.ScheduleBNumber.disabled = true;
        document.aascShipExecIntlShipmentsForm.ScheduleBQuantity.disabled = true;
        document.aascShipExecIntlShipmentsForm.ScheduleBUnitOfMeasure.disabled = true;
        document.aascShipExecIntlShipmentsForm.ExportType.disabled = true;
        document.aascShipExecIntlShipmentsForm.PointOfOrigin.disabled = true;
        document.aascShipExecIntlShipmentsForm.ModeOfTransport.disabled = true;
        document.aascShipExecIntlShipmentsForm.SExportDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.SExportingCarrier.disabled = true;
        document.aascShipExecIntlShipmentsForm.InBondCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.EntryNumber.disabled = true;
        document.aascShipExecIntlShipmentsForm.LoadingPier.disabled = true;
        document.aascShipExecIntlShipmentsForm.PortOfExport.disabled = true;
        document.aascShipExecIntlShipmentsForm.PortOfUnloading.disabled = true;
        document.aascShipExecIntlShipmentsForm.CarrierIdentificationCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.Containerized.disabled = true;
        document.aascShipExecIntlShipmentsForm.HazardousMaterials.disabled = true;
        document.aascShipExecIntlShipmentsForm.RoutedExportTransaction.disabled = true;
        document.aascShipExecIntlShipmentsForm.PartiestoTransaction[0].disabled = true; //Added by Narasimha 16/11/2010
        document.aascShipExecIntlShipmentsForm.PartiestoTransaction[1].disabled = true;
        // document.aascShipExecIntlShipmentsForm.License.disabled = true;
        // document.aascShipExecIntlShipmentsForm.LicenseExceptionCode.disabled = true;
        //  document.aascShipExecIntlShipmentsForm.ECCN.disabled = true;
        document.aascShipExecIntlShipmentsForm.LicenseNumber.disabled = true;
        document.aascShipExecIntlShipmentsForm.LicenseDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.ExceptionCodes.disabled = true;
        document.aascShipExecIntlShipmentsForm.EccnNumber.disabled = true;
        //document.aascShipExecIntlShipmentsForm.ConsigneeInfo.disabled = true;
        //   document.aascShipExecIntlShipmentsForm.ConsigneeInfo.src ="buttons/aascDetailsOff1.png";
        //document.aascShipExecIntlShipmentsForm.FAgentInfo.disabled = true;
        // document.aascShipExecIntlShipmentsForm.FAgentInfo.src ="buttons/aascDetailsOff1.png";
        //document.aascShipExecIntlShipmentsForm.IConsigneeInfo.disabled = true;
        // document.aascShipExecIntlShipmentsForm.IConsigneeInfo.src ="buttons/aascDetailsOff1.png";
         
        document.aascShipExecIntlShipmentsForm.CCompanyName.disabled = true;
        document.aascShipExecIntlShipmentsForm.CAddressLine1.disabled = true;
        document.aascShipExecIntlShipmentsForm.CCity.disabled = true;
        document.aascShipExecIntlShipmentsForm.CStateProvinceCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.CPostalCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.CCountryCode.disabled = true;
         
        document.aascShipExecIntlShipmentsForm.addComm.disabled = true;
        //document.aascShipExecIntlShipmentsForm.editComm.disabled = true;
        document.aascShipExecIntlShipmentsForm.delComm.disabled = true;
         
        document.aascShipExecIntlShipmentsForm.CompanyName.disabled = true;
        document.aascShipExecIntlShipmentsForm.AddressLine1.disabled = true;
        document.aascShipExecIntlShipmentsForm.AddressLine2.disabled = true;
        document.aascShipExecIntlShipmentsForm.City.disabled = true;
        document.aascShipExecIntlShipmentsForm.StateProvinceCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.PostalCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.CountryCode.disabled = true;
          
        document.aascShipExecIntlShipmentsForm.CommercialInvoice.disabled = true;
        document.aascShipExecIntlShipmentsForm.USCO.disabled = true;
        document.aascShipExecIntlShipmentsForm.NAFTACO.disabled = true;
        document.aascShipExecIntlShipmentsForm.SED.disabled = true;
        document.aascShipExecIntlShipmentsForm.ShipFromTaxID.disabled = true;
        document.aascShipExecIntlShipmentsForm.ShipFromPhone.disabled = true;
        document.aascShipExecIntlShipmentsForm.ShipFromAttention.disabled = true;
        document.aascShipExecIntlShipmentsForm.ShipToTaxID.disabled = true;
        //document.aascShipExecIntlShipmentsForm.ShipTosameSoldTo.disabled = true;
        document.aascShipExecIntlShipmentsForm.TaxIdNum.disabled = true;
        document.aascShipExecIntlShipmentsForm.SoldToAttention.disabled = true;
        document.aascShipExecIntlShipmentsForm.SoldToPhone.disabled = true;
        document.aascShipExecIntlShipmentsForm.InvCurCd.disabled = true;
        document.aascShipExecIntlShipmentsForm.InvVal.disabled = true;
    }
    disableECCNNumber();
   
    shipFlagTemp=shipFlag;
}


function hideHeaderOptions()
{
    document.aascShipExecIntlShipmentsForm.USCO.style.visibility = 'hidden';
}

//load(end

function saveDetails()
{    
        //alert('in save');
    var desc = trim(document.aascShipExecIntlShipmentsForm.description.value);
    var tariffcode = trim(document.aascShipExecIntlShipmentsForm.HarmonizedCode.value);
    var countryOfmanufacture = trim(document.aascShipExecIntlShipmentsForm.CountryOfManufacture.value);
       
    var UOM=trim(document.aascShipExecIntlShipmentsForm.UOM.value);

    var alertStr = "";
       
    /*   if( window.opener.ShipInsertForm.intTotalCustomsValue.value == "Y") 
       {
       alert('Details are Already Saved')
       return false;
       } */
     /*  if(desc ==''||desc == null)
       {
       alertStr = alertStr+"Please enter \'Product description\' in Product Details Section\n";
       document.aascShipExecIntlShipmentsForm.description.focus();
      // return false;
       }
       if(tariffcode ==''||tariffcode == null)
       {
       alertStr = alertStr+"Please enter \'Product Tariff Code\' in Product Details Section\n";
       document.aascShipExecIntlShipmentsForm.HarmonizedCode.focus();
       //return false;
       }
       if(countryOfmanufacture ==''||countryOfmanufacture == null)
       {
       alertStr = alertStr+"Please enter \'Country of Manfacture\' in Product Details Section\n";
       document.aascShipExecIntlShipmentsForm.CountryOfManufacture.focus();
       //return false;
       }
       
       if(alertStr != "" && alertStr !=null)
      {
      alert(alertStr);
      return false;
      }*/
      
    if(document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked &&!document.aascShipExecIntlShipmentsForm.CommercialInvoice.disabled)
    {
         //alert("hi");
        document.aascShipExecIntlShipmentsForm.CIFlag.value = 'Y';
         
       //  var form = document.aascShipExecIntlShipmentsForm.commodityLine;
        // var value = form.options.value;
  
        var commLength = document.aascShipExecIntlShipmentsForm.commodityLine.length;
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
        document.aascShipExecIntlShipmentsForm.CIFlag.value = 'N';
    }
    //NAFTA SAVING
    /*if(isInteger(trim(document.aascShipExecIntlShipmentsForm.InvVal.value))==false || trim(document.aascShipExecIntlShipmentsForm.InvVal.value)== ''){
            alert("Enter Integer Value For Invoice Value");
            document.aascShipExecIntlShipmentsForm.InvVal.focus();
            return false;

    }*/
    //      var shipmentType = document.aascShipExecIntlShipmentsForm.shipment.value;
    //            if(shipmentType == "adhoc")
    //            {
                    var shipToAddressCountry = window.opener.document.DynaShipmentShipSaveForm.country.value;
    //            }else{
    //            var shipToAddressCountry = window.opener.document.ShipInsertForm.shipToAddressCountry.value;
    //            
    //            }
                
    if(shipToAddressCountry=='PR' || shipToAddressCountry=='CA')
    {
        if((trim(document.aascShipExecIntlShipmentsForm.InvCurCd.value)=="")||(trim(document.aascShipExecIntlShipmentsForm.InvCurCd.value)==null))
        {
            alert("Please Enter value for Invoice Currency Code");
            document.aascShipExecIntlShipmentsForm.InvCurCd.focus();
            return false;
        }
        if(trim(document.aascShipExecIntlShipmentsForm.InvVal.value)==0)
        {
            alert("The Invoice value should be greater than Zero");
            document.aascShipExecIntlShipmentsForm.InvVal.focus();
            return false;
        }
    }

    /*if(document.aascShipExecIntlShipmentsForm.commodityLine.length < 3)
    {
       alert("Please Enter Details of Atleast one Product");
       document.aascShipExecIntlShipmentsForm.description.focus();
       return false;
    } */
       
    //       if(document.aascShipExecIntlShipmentsForm.ShipTosameSoldTo.value=='N')
    //       {
                if(saveSolAddress() ==false)
	        {
                    return false;
                }
    //       }
            if(document.aascShipExecIntlShipmentsForm.NAFTACO.checked)
            {
                // alert('in nafta');
                document.aascShipExecIntlShipmentsForm.NAFTAFlag.value = 'Y';
                //document.aascShipExecIntlShipmentsForm.NAFTACO.disabled = true;
                if(saveNAFTA() ==false)
                {
                    return false;
                }
            }
            else
            {
                document.aascShipExecIntlShipmentsForm.NAFTAFlag.value = 'N';
            }
            //CI SAVING 
            if(document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked)
            {
                //alert('CI');
                document.aascShipExecIntlShipmentsForm.CIFlag.value = 'Y';
                // document.aascShipExecIntlShipmentsForm.CommercialInvoice.disabled = true;
                if(saveCI() ==false)
                {
                    return false;
                }
            }
            else
            {
                document.aascShipExecIntlShipmentsForm.CIFlag.value = 'N';
            }
            //USCO SAVING
            if(document.aascShipExecIntlShipmentsForm.USCO.checked)
            {
                //alert('CO');
                document.aascShipExecIntlShipmentsForm.COFlag.value = 'Y';
                //  document.aascShipExecIntlShipmentsForm.USCO.disabled = true;
                if(saveUSCO() ==false)
                {
                    return false;
                }
            }
            else
            {
                document.aascShipExecIntlShipmentsForm.COFlag.value = 'N';
            }
            //SED SAVING
            if(document.aascShipExecIntlShipmentsForm.SED.checked)
            {
                //alert('SED');
                document.aascShipExecIntlShipmentsForm.SEDFlag.value = 'Y';
                //document.aascShipExecIntlShipmentsForm.SED.disabled = true;
                if(saveSED() ==false)
                {
                    return false;
                }
            }
            else
            {
                document.aascShipExecIntlShipmentsForm.SEDFlag.value = 'N';
            }
            if(document.aascShipExecIntlShipmentsForm.ShipFromTaxID.value == '' && document.aascShipExecIntlShipmentsForm.SEDFlag.value == 'Y')
            {
                alert(" Please Enter Ship From EIN/TaxID ");
                document.aascShipExecIntlShipmentsForm.ShipFromTaxID.focus();
                return false;
            } 

            /*  if(document.aascShipExecIntlShipmentsForm.ShipFromPhone.value == '' )
                {
                    alert(" Please Enter Ship From Phone Number ");
                    document.aascShipExecIntlShipmentsForm.ShipFromPhone.focus();
                    return false;
                }else{
                    var phonenumber=document.aascShipExecIntlShipmentsForm.ShipFromPhone.value;
                    if((phonenumber.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (phonenumber.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) 
                    {
					alert("Please Enter Valid 10 Digit Phone Number :");
					document.aascShipExecIntlShipmentsForm.ShipFromPhone.focus();
					return false
                    }
                }
            */
       
            /*  if(document.aascShipExecIntlShipmentsForm.ShipFromTaxID.value != '' )
                {
                    var phonenumber=document.aascShipExecIntlShipmentsForm.ShipFromPhone.value;
                    if((phonenumber.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (phonenumber.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) 
  				{
					alert("Please Enter Valid 10 Digit Phone Number :");
					document.aascShipExecIntlShipmentsForm.ShipFromPhone.focus();
					return false
                                }   
                }
            */
       
       
            /*  if(document.aascShipExecIntlShipmentsForm.ShipFromAttention.value == '' )
                {
                 alert(" Please Enter Ship From Attention Name ");
                 document.aascShipExecIntlShipmentsForm.ShipFromAttention.focus();
                 return false;
                } 
                if(document.aascShipExecIntlShipmentsForm.ShipToTaxID.value == '' )
                {
                 alert(" Please Enter Ship To EIN/TaxID ");
                 document.aascShipExecIntlShipmentsForm.ShipToTaxID.focus();
                 return false;
                }
                var ComInv = document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked;
                var USCO = document.aascShipExecIntlShipmentsForm.USCO.checked;
                var NAFTACO = document.aascShipExecIntlShipmentsForm.NAFTACO.checked;
                var SED= document.aascShipExecIntlShipmentsForm.SED.checked ;
       
            */
            if(document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked==true ||document.aascShipExecIntlShipmentsForm.NAFTACO.checked==true){  
               /*if(document.aascShipExecIntlShipmentsForm.TaxIdNum.value == '' )
               {
                 alert(" Please Open Sold To details PopUp and enter Tax Id ");
                // document.aascShipExecIntlShipmentsForm.TaxIdNum.focus();
                 return false;
               }  */
               if(document.aascShipExecIntlShipmentsForm.SoldToAttention.value == '' )
               {
                 alert(" Please Open Sold To details PopUp and enter Attention Name ");
                // document.aascShipExecIntlShipmentsForm.SoldToAttention.focus();
                 return false;
               }
     
                if(document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked==true)
                {  
                    if(document.aascShipExecIntlShipmentsForm.SoldToPhone.value == '' )
                    {
                        alert(" Please Open Sold To details PopUp and enter Phone Number ");
                        // document.aascShipExecIntlShipmentsForm.SoldToPhone.focus();
                        return false;
                    }
                    else
                    {
                        var phonenumber=document.aascShipExecIntlShipmentsForm.SoldToPhone.value;
                        if((phonenumber.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (phonenumber.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) 
                        {
        //					alert("Please Enter Valid 10 Digit Phone Number in Sold To Details PopUp");
        //					//document.aascShipExecIntlShipmentsForm.SoldToPhone.focus();
        //					return false;
                        }
                    }
                }
       
            }
    /*   if(document.aascShipExecIntlShipmentsForm.InvCurCd.value == '' )
       {
         alert(" Please Enter Valid Invoice Currency Code upto 3 characters ");
         document.aascShipExecIntlShipmentsForm.InvCurCd.focus();
         return false;
       }else{
        var InvCurCd = document.aascShipExecIntlShipmentsForm.InvCurCd.value;
        if(InvCurCd.length > 3){
         alert(" Please Enter Valid Invoice Currency Code ");
         document.aascShipExecIntlShipmentsForm.InvCurCd.focus();
         return false;
        }
       }
       if(document.aascShipExecIntlShipmentsForm.InvVal.value == '' )
       {
         alert(" Please Enter Invoice Monitary Value ");
         document.aascShipExecIntlShipmentsForm.InvVal.focus();
         return false;
       }
       */
       
       if((document.aascShipExecIntlShipmentsForm.CIFlag.value != 'Y' && window.opener.document.DynaShipmentShipSaveForm.country.value != 'CA' &&
            window.opener.document.DynaShipmentShipSaveForm.country.value != 'PR' && window.opener.document.DynaShipmentShipSaveForm.shipFromCountry.value != 'US') &&
                document.aascShipExecIntlShipmentsForm.COFlag.value != 'Y' && document.aascShipExecIntlShipmentsForm.NAFTAFlag.value != 'Y' && 
                    document.aascShipExecIntlShipmentsForm.SEDFlag.value != 'Y' )
        {
            alert("Please Select Commercial Invoice");
            return false;
        }
       
       document.aascShipExecIntlShipmentsForm.actionType.value='SAVE';
       document.aascShipExecIntlShipmentsForm.submit();
       
       window.opener.document.DynaShipmentShipSaveForm.intlSaveFlag.value = "Y";
       
       window.opener.document.DynaShipmentShipSaveForm.intTotalCustomsValue.value = "Y";
       
       window.opener.document.DynaShipmentShipSaveForm.intlCustomsFlag.value = "Y";
}

function enableCI()
{
    //alert('CI is selected'+document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked);
    if(document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked)
    {
        document.aascShipExecIntlShipmentsForm.CIFlag.value = 'Y';
           //alert('CI also selected');
         //  document.aascShipExecIntlShipmentsForm.Quantity.disabled = false;
         //  document.aascShipExecIntlShipmentsForm.UnitPrice.disabled = false;
         //  document.aascShipExecIntlShipmentsForm.QuantityUnits.disabled = false;
           
         //  document.aascShipExecIntlShipmentsForm.InvoiceNumber.disabled = false;
        document.aascShipExecIntlShipmentsForm.PuchaseOrderNumber.disabled = false;
        document.aascShipExecIntlShipmentsForm.TermsOfSale.disabled = false;
        document.aascShipExecIntlShipmentsForm.Purpose.disabled = false;
        document.aascShipExecIntlShipmentsForm.Discount.disabled = false;
        document.aascShipExecIntlShipmentsForm.FreightCharges.disabled = false;
        document.aascShipExecIntlShipmentsForm.InsuranceCharges.disabled = false;
        document.aascShipExecIntlShipmentsForm.OtherCharges.disabled = false;
         //  document.aascShipExecIntlShipmentsForm.InvoiceDate.disabled = false;
        document.aascShipExecIntlShipmentsForm.CurrencyCode.disabled = false;
        document.aascShipExecIntlShipmentsForm.comments.disabled = false;
        document.aascShipExecIntlShipmentsForm.DeclarationStmt.disabled = false;
           
        var invoiceDate = document.aascShipExecIntlShipmentsForm.InvoiceDate.value;
        if(invoiceDate == null || invoiceDate == ''){
            var shipFlag2 = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;
            if(shipFlag2!= 'Y')
                var shipmentDate = window.opener.document.DynaShipmentShipSaveForm.shipmentDate.value;
            else
                var shipmentDate = window.opener.document.DynaShipmentShipSaveForm.shipmentDateName.value;
            document.aascShipExecIntlShipmentsForm.InvoiceDate.value = shipmentDate.substring(0,10);
        }
    }
    else
    {
        document.aascShipExecIntlShipmentsForm.CIFlag.value = 'N';
          // alert('CI not selected');
         //  document.aascShipExecIntlShipmentsForm.Quantity.disabled = true;
         //  document.aascShipExecIntlShipmentsForm.UnitPrice.disabled = true;
         //  document.aascShipExecIntlShipmentsForm.QuantityUnits.disabled = true;
           
         //  document.aascShipExecIntlShipmentsForm.InvoiceNumber.disabled = true;
        document.aascShipExecIntlShipmentsForm.PuchaseOrderNumber.disabled = true;
        document.aascShipExecIntlShipmentsForm.TermsOfSale.disabled = true;
        document.aascShipExecIntlShipmentsForm.Purpose.disabled = true;
        document.aascShipExecIntlShipmentsForm.Discount.disabled = true;
        document.aascShipExecIntlShipmentsForm.FreightCharges.disabled = true;
        document.aascShipExecIntlShipmentsForm.InsuranceCharges.disabled = true;
        document.aascShipExecIntlShipmentsForm.OtherCharges.disabled = true;
         //  document.aascShipExecIntlShipmentsForm.InvoiceDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.CurrencyCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.comments.disabled = true;
        document.aascShipExecIntlShipmentsForm.DeclarationStmt.disabled = true;
                         
    }
           
         
}

function enableNAFTA()
{
//alert('NAFTA is selected');
        
  //       alert('NAFTA');
         
          
    if(document.aascShipExecIntlShipmentsForm.NAFTACO.checked)
    {
        document.aascShipExecIntlShipmentsForm.NAFTAFlag.value = 'Y';
    //     alert('NAFTA also selected');
        document.aascShipExecIntlShipmentsForm.PreferenceCriteria.disabled = false;
        document.aascShipExecIntlShipmentsForm.Producer.disabled = false;
         
     //    document.aascShipExecIntlShipmentsForm.NAFTA_ProduceInfo.disabled = false;
        document.aascShipExecIntlShipmentsForm.NAFTA_ProduceInfo.src ="buttons/aascDetails1.png";
         
        document.aascShipExecIntlShipmentsForm.PCompanyName.disabled = false;
        document.aascShipExecIntlShipmentsForm.PAddressLine1.disabled = false;
        document.aascShipExecIntlShipmentsForm.PCity.disabled = false;
        document.aascShipExecIntlShipmentsForm.PStateProvinceCode.disabled = false;
        document.aascShipExecIntlShipmentsForm.PPostalCode.disabled = false;
        document.aascShipExecIntlShipmentsForm.PCountryCode.disabled = false;
        document.aascShipExecIntlShipmentsForm.RVCCalculationMethod.disabled = false;
        document.aascShipExecIntlShipmentsForm.NetCostPeriodBeginDate.disabled = false;
        document.aascShipExecIntlShipmentsForm.NetCostPeriodEndDate.disabled = false;
        document.aascShipExecIntlShipmentsForm.BlanketPeriodBeginDate.disabled = false;
        document.aascShipExecIntlShipmentsForm.BlanketPeriodEndDate.disabled = false;
    }
    else
    {
         //alert('NAFTA not selected');
        document.aascShipExecIntlShipmentsForm.NAFTAFlag.value = 'N';
        document.aascShipExecIntlShipmentsForm.PreferenceCriteria.disabled = true;
        document.aascShipExecIntlShipmentsForm.Producer.disabled = true;
         
      //   document.aascShipExecIntlShipmentsForm.NAFTA_ProduceInfo.disabled = true;
        document.aascShipExecIntlShipmentsForm.NAFTA_ProduceInfo.src ="buttons/aascDetailsOff1.png";
        document.aascShipExecIntlShipmentsForm.PCompanyName.disabled = true;
        document.aascShipExecIntlShipmentsForm.PAddressLine1.disabled = true;
        document.aascShipExecIntlShipmentsForm.PCity.disabled = true;
        document.aascShipExecIntlShipmentsForm.PStateProvinceCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.PPostalCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.PCountryCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.RVCCalculationMethod.disabled = true;
        document.aascShipExecIntlShipmentsForm.NetCostPeriodBeginDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.NetCostPeriodEndDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.BlanketPeriodBeginDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.BlanketPeriodEndDate.disabled = true;
         
    }
                  
                  
}
         
function enableUSCO()
{
    // if(document.aascShipExecIntlShipmentsForm.SED.checked)
//alert('USCO is selected');
    if(document.aascShipExecIntlShipmentsForm.USCO.checked) 
    {
        document.aascShipExecIntlShipmentsForm.COFlag.value = 'Y';
      //  alert('USCO also selected');
        document.aascShipExecIntlShipmentsForm.NumberOfPieces.disabled = false;
        document.aascShipExecIntlShipmentsForm.UExportDate.disabled = false;
        document.aascShipExecIntlShipmentsForm.UExportingCarrier.disabled = false;
    }
    else
    {
        document.aascShipExecIntlShipmentsForm.COFlag.value = 'N';
         // alert('USCO not selected');
        document.aascShipExecIntlShipmentsForm.NumberOfPieces.disabled = true;
        document.aascShipExecIntlShipmentsForm.UExportDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.UExportingCarrier.disabled = true;
    }
         
    if((document.aascShipExecIntlShipmentsForm.SED.checked) || (document.aascShipExecIntlShipmentsForm.USCO.checked))
    {
        document.aascShipExecIntlShipmentsForm.Weight.disabled = false;
        document.aascShipExecIntlShipmentsForm.UOM.disabled = false;
    }
    else
    {
        document.aascShipExecIntlShipmentsForm.Weight.disabled = true;
        document.aascShipExecIntlShipmentsForm.UOM.disabled = true;
    }
}

function enableSED()
{
//alert('SED is selected');
    if(document.aascShipExecIntlShipmentsForm.SED.checked)
    {   
        document.aascShipExecIntlShipmentsForm.SEDFlag.value = 'Y';
        document.aascShipExecIntlShipmentsForm.Weight.disabled = false;
        document.aascShipExecIntlShipmentsForm.UOM.disabled = false;
        document.aascShipExecIntlShipmentsForm.SEDTotalValue.disabled = false;
        document.aascShipExecIntlShipmentsForm.ScheduleBNumber.disabled = false;
        document.aascShipExecIntlShipmentsForm.ScheduleBQuantity.disabled = false;
        document.aascShipExecIntlShipmentsForm.ScheduleBUnitOfMeasure.disabled = false;
        document.aascShipExecIntlShipmentsForm.ExportType.disabled = false;       
        document.aascShipExecIntlShipmentsForm.PointOfOrigin.disabled = false;
        document.aascShipExecIntlShipmentsForm.ModeOfTransport.disabled = false;
        document.aascShipExecIntlShipmentsForm.SExportDate.disabled = false;
        document.aascShipExecIntlShipmentsForm.SExportingCarrier.disabled = false;
        document.aascShipExecIntlShipmentsForm.InBondCode.disabled = false;
        document.aascShipExecIntlShipmentsForm.EntryNumber.disabled = false;
        document.aascShipExecIntlShipmentsForm.LoadingPier.disabled = false;
        document.aascShipExecIntlShipmentsForm.PortOfExport.disabled = false;
        document.aascShipExecIntlShipmentsForm.PortOfUnloading.disabled = false;
        document.aascShipExecIntlShipmentsForm.CarrierIdentificationCode.disabled = false;
        document.aascShipExecIntlShipmentsForm.Containerized.disabled = false;
        document.aascShipExecIntlShipmentsForm.HazardousMaterials.disabled = false;
        document.aascShipExecIntlShipmentsForm.RoutedExportTransaction.disabled = false;
        document.aascShipExecIntlShipmentsForm.PartiestoTransaction[0].disabled = false; //Added by Narasimha 16/11/2010
        document.aascShipExecIntlShipmentsForm.PartiestoTransaction[1].disabled = false;
       //  document.aascShipExecIntlShipmentsForm.License.disabled = false;
        // document.aascShipExecIntlShipmentsForm.LicenseExceptionCode.disabled = false;
       //  document.aascShipExecIntlShipmentsForm.ECCN.disabled = false;
        document.aascShipExecIntlShipmentsForm.LicenseNumber.disabled = false;
        document.aascShipExecIntlShipmentsForm.LicenseDate.disabled = false;
         
        // document.aascShipExecIntlShipmentsForm.ConsigneeInfo.disabled = false;
        document.aascShipExecIntlShipmentsForm.ConsigneeInfo.src ="buttons/aascDetails1.png";
         //document.aascShipExecIntlShipmentsForm.FAgentInfo.disabled = false;
        document.aascShipExecIntlShipmentsForm.FAgentInfo.src ="buttons/aascDetails1.png";
         //document.aascShipExecIntlShipmentsForm.IConsigneeInfo.disabled = false;
        document.aascShipExecIntlShipmentsForm.IConsigneeInfo.src ="buttons/aascDetails1.png";
         
        document.aascShipExecIntlShipmentsForm.CCompanyName.disabled = false;
        document.aascShipExecIntlShipmentsForm.CAddressLine1.disabled = false;
        document.aascShipExecIntlShipmentsForm.CCity.disabled = false;
        document.aascShipExecIntlShipmentsForm.CStateProvinceCode.disabled = false;
        document.aascShipExecIntlShipmentsForm.CPostalCode.disabled = false;
        document.aascShipExecIntlShipmentsForm.CCountryCode.disabled = false;
        var LicenseNumber=document.aascShipExecIntlShipmentsForm.LicenseNumber.value;
         var ExceptionCodes = document.aascShipExecIntlShipmentsForm.ExceptionCodes.value; 
        if((LicenseNumber =='' || LicenseNumber == null) && (ExceptionCodes =='' || ExceptionCodes == null))
        {
            document.aascShipExecIntlShipmentsForm.LicenseNumber.disabled = true;
            document.aascShipExecIntlShipmentsForm.LicenseDate.disabled = true;
            document.aascShipExecIntlShipmentsForm.ExceptionCodes.disabled = true;
            document.aascShipExecIntlShipmentsForm.EccnNumber.disabled = true;
        }
         
    }
    else
    {
        document.aascShipExecIntlShipmentsForm.SEDFlag.value = 'N';
  //       alert('SED not selected');
        document.aascShipExecIntlShipmentsForm.SEDTotalValue.disabled = true;
        document.aascShipExecIntlShipmentsForm.ScheduleBNumber.disabled = true;
        document.aascShipExecIntlShipmentsForm.ScheduleBQuantity.disabled = true;
        document.aascShipExecIntlShipmentsForm.ScheduleBUnitOfMeasure.disabled = true;
        document.aascShipExecIntlShipmentsForm.ExportType.disabled = true;
        document.aascShipExecIntlShipmentsForm.PointOfOrigin.disabled = true;
        document.aascShipExecIntlShipmentsForm.ModeOfTransport.disabled = true;
        document.aascShipExecIntlShipmentsForm.SExportDate.disabled = true;
        document.aascShipExecIntlShipmentsForm.SExportingCarrier.disabled = true;
        document.aascShipExecIntlShipmentsForm.InBondCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.EntryNumber.disabled = true;
        document.aascShipExecIntlShipmentsForm.LoadingPier.disabled = true;
        document.aascShipExecIntlShipmentsForm.PortOfExport.disabled = true;
        document.aascShipExecIntlShipmentsForm.PortOfUnloading.disabled = true;
        document.aascShipExecIntlShipmentsForm.CarrierIdentificationCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.Containerized.disabled = true;
        document.aascShipExecIntlShipmentsForm.HazardousMaterials.disabled = true;
        document.aascShipExecIntlShipmentsForm.RoutedExportTransaction.disabled = true;
        document.aascShipExecIntlShipmentsForm.PartiestoTransaction[0].disabled = true;  //Added by Narasimha 16/11/2010
        document.aascShipExecIntlShipmentsForm.PartiestoTransaction[1].disabled = true;
        //document.aascShipExecIntlShipmentsForm.License.disabled = true;
        // document.aascShipExecIntlShipmentsForm.LicenseExceptionCode.disabled = true;
        //  document.aascShipExecIntlShipmentsForm.ECCN.disabled = true;
        document.aascShipExecIntlShipmentsForm.LicenseNumber.disabled = true;
        document.aascShipExecIntlShipmentsForm.LicenseDate.disabled = true;
        
        // document.aascShipExecIntlShipmentsForm.ConsigneeInfo.disabled = true;
        document.aascShipExecIntlShipmentsForm.ConsigneeInfo.src ="buttons/aascDetailsOff1.png";
        //document.aascShipExecIntlShipmentsForm.FAgentInfo.disabled = true;
        document.aascShipExecIntlShipmentsForm.FAgentInfo.src ="buttons/aascDetailsOff1.png";
         //document.aascShipExecIntlShipmentsForm.IConsigneeInfo.disabled = true;
        document.aascShipExecIntlShipmentsForm.IConsigneeInfo.src ="buttons/aascDetailsOff1.png";
         
        document.aascShipExecIntlShipmentsForm.CCompanyName.disabled = true;
        document.aascShipExecIntlShipmentsForm.CAddressLine1.disabled = true;
        document.aascShipExecIntlShipmentsForm.CCity.disabled = true;
        document.aascShipExecIntlShipmentsForm.CStateProvinceCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.CPostalCode.disabled = true;
        document.aascShipExecIntlShipmentsForm.CCountryCode.disabled = true;
    }
    if((document.aascShipExecIntlShipmentsForm.SED.checked) || (document.aascShipExecIntlShipmentsForm.USCO.checked))
    {
        document.aascShipExecIntlShipmentsForm.Weight.disabled = false;
        document.aascShipExecIntlShipmentsForm.UOM.disabled = false;
    }
    else
    {
        document.aascShipExecIntlShipmentsForm.Weight.disabled = true;
        document.aascShipExecIntlShipmentsForm.UOM.disabled = true;
    }
}


function checkLicNumOrExcpCode(val)
{
    if(document.aascShipExecIntlShipmentsForm.SED.checked)
    {
        if(val == "LicenseNumber")
        {
            document.aascShipExecIntlShipmentsForm.LicenseNumber.disabled = false;
            document.aascShipExecIntlShipmentsForm.LicenseDate.disabled = false;
            document.getElementById("ExceptionCodes").disabled = true;
            document.aascShipExecIntlShipmentsForm.EccnNumber.disabled = true;
            document.aascShipExecIntlShipmentsForm.EccnNumber.value = "";
            //  document.getElementById("ExceptionCodes").text = "Select";
            document.aascShipExecIntlShipmentsForm.ExceptionCodes.options[document.aascShipExecIntlShipmentsForm.ExceptionCodes.selectedIndex].text = "Select";
            document.aascShipExecIntlShipmentsForm.ExceptionCodes.options[document.aascShipExecIntlShipmentsForm.ExceptionCodes.selectedIndex].value = ""
        }

        if(val == "ExceptionCode")
        {
            document.getElementById("ExceptionCodes").disabled = false;
            document.aascShipExecIntlShipmentsForm.EccnNumber.disabled = false;
            document.aascShipExecIntlShipmentsForm.LicenseNumber.disabled = true;
            document.aascShipExecIntlShipmentsForm.LicenseDate.disabled = true;   
            document.aascShipExecIntlShipmentsForm.LicenseNumber.value = "";
            document.aascShipExecIntlShipmentsForm.LicenseDate.value = "";
        }
    }
}

function saveUSCO()
{

    if(document.aascShipExecIntlShipmentsForm.commodityLine.length < 3)
    {
        alert("Please Enter Details of Atleast one Product");
        document.aascShipExecIntlShipmentsForm.description.focus();
        return false;
    } 
    var alertStr ="";
    /*var noOfPkgsPerCommodity = trim(document.aascShipExecIntlShipmentsForm.NumberOfPieces.value);
    var Weight=trim(document.aascShipExecIntlShipmentsForm.Weight.value);
    var UOM=trim(document.aascShipExecIntlShipmentsForm.UOM.value);*/
    var UExportDate=trim(document.aascShipExecIntlShipmentsForm.UExportDate.value);
    var UExportingCarrier=trim(document.aascShipExecIntlShipmentsForm.UExportingCarrier.value);
    /* if(noOfPkgsPerCommodity ==''||noOfPkgsPerCommodity == null)
    {
        alertStr =alertStr+"Please enter \'Number Of Packages Per Commodity\' in Product Details Section\n";
        document.aascShipExecIntlShipmentsForm.NumberOfPieces.focus();
        // return false;
    }
    if(Weight=="" || Weight==null)
    {
        alertStr =alertStr+ "Please Enter \'Weight\' in Product Details Section \n";
        document.aascShipExecIntlShipmentsForm.Weight.focus();
    }   

    if(UOM=="" || UOM==null)
    {
        alertStr =alertStr+ "Please Enter \'UOM\' in Product Details Section \n";
        document.aascShipExecIntlShipmentsForm.UOM.focus();
    }*/

    if(UExportDate=="" || UExportDate==null)
    {
        alertStr =alertStr+ "Please Enter \'ExportDate\' in US Certificate Of Origin Section \n";
        document.aascShipExecIntlShipmentsForm.UExportDate.focus();
    }
    if(UExportingCarrier=="" || UExportingCarrier==null)
    {
        alertStr =alertStr+ "Please Enter \'ExportingCarrier\' in US Certificate Of Origin Section \n";
        document.aascShipExecIntlShipmentsForm.UExportingCarrier.focus();
    }

    if(isDate(UExportDate) == false)
    {
       //alert(" Please Enter ExportLicense Expiration Date in YYYY-MM-DD format ");
       document.aascShipExecIntlShipmentsForm.UExportDate.focus();
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
    /*  var noOfPieces = trim(document.aascShipExecIntlShipmentsForm.Quantity.value);
    var unitPrice = trim(document.aascShipExecIntlShipmentsForm.UnitPrice.value);
    var quantityUnits = trim(document.aascShipExecIntlShipmentsForm.QuantityUnits.value);*/
    var reasonForExport =trim( document.aascShipExecIntlShipmentsForm.Purpose.value);
    var invoiceDate =trim( document.aascShipExecIntlShipmentsForm.InvoiceDate.value);
    var currencyCode =trim( document.aascShipExecIntlShipmentsForm.CurrencyCode.value);
    var discount = trim(document.aascShipExecIntlShipmentsForm.Discount.value);
    //alert("discount  ::"+discount);
    var alertStr = "";
    
    /*    if(noOfPieces ==''||noOfPieces == null)
    {
        alertStr=alertStr+"Please Enter \'Number Of Units\' in Product Details Section \n";
        document.aascShipExecIntlShipmentsForm.Quantity.focus();
        // return false;
    }
    if(unitPrice ==''||unitPrice == null)
    {
        alertStr=alertStr+"Please Enter \'Price per unit\' in Product Details Section\n";
        document.aascShipExecIntlShipmentsForm.UnitPrice.focus();
        //return false;
    }
    if(quantityUnits ==''||quantityUnits == null)
    {
        alertStr=alertStr+"Please enter \'Unit of Measurement\' in Product Details Section \n";
        document.aascShipExecIntlShipmentsForm.QuantityUnits.focus();
        //return false;
    }*/
    if(reasonForExport ==''||reasonForExport == null)
    {
        alertStr=alertStr+"Please enter \'Reason For Export\' in Commercial Invoice Section \n";
        document.aascShipExecIntlShipmentsForm.Purpose.focus();
        //return false;
    }
    if(invoiceDate ==''||invoiceDate == null)
    {
        alertStr=alertStr+"Please enter \'Invoice Date\' in Commercial Invoice Section \n";
        document.aascShipExecIntlShipmentsForm.InvoiceDate.focus();
        //return false;
    }
    if(currencyCode ==''||currencyCode == null)
    {
        alertStr=alertStr+"Please enter \'Currency Code\' in Commercial Invoice Section \n";
        document.aascShipExecIntlShipmentsForm.CurrencyCode.focus();
        // return false;
    }
    if(discount < 0)
    {
        alertStr=alertStr+"Please enter Positive \'Discount\' in Commercial Invoice Section \n";
        document.aascShipExecIntlShipmentsForm.Discount.focus();
    }
    if(isDate(invoiceDate) == false)
    {
        //alert(" Please Enter ExportLicense Expiration Date in YYYY-MM-DD format ");
        document.aascShipExecIntlShipmentsForm.InvoiceDate.focus();
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
    /*var preferenceCriteria = trim(document.aascShipExecIntlShipmentsForm.PreferenceCriteria.value);
    var producer = trim(document.aascShipExecIntlShipmentsForm.Producer.value);
    var rvcCalMethod = trim(document.aascShipExecIntlShipmentsForm.RVCCalculationMethod.value); */
    var BlanketPeriodBeginDate =trim( document.aascShipExecIntlShipmentsForm.BlanketPeriodBeginDate.value);
    var BlanketPeriodEndDate = trim( document.aascShipExecIntlShipmentsForm.BlanketPeriodEndDate.value);
    var alertStr = "";

    /* if(preferenceCriteria ==''||preferenceCriteria == null)
    {
       alertStr=alertStr+"Please select Any \'Preference Criteria\' in Product 3 Section \n";
       document.aascShipExecIntlShipmentsForm.PreferenceCriteria.focus();
       
    }
    if(producer ==''||producer == null)
    {
       alertStr=alertStr+"Please select \'Producer Info\' in Product Description Section \n";
       document.aascShipExecIntlShipmentsForm.Producer.focus();
       
    }
    if(rvcCalMethod ==''||rvcCalMethod == null)
    {
       alertStr=alertStr+"Please select any \'RVC Calculation Method\' in Product Description Section \n";
       document.aascShipExecIntlShipmentsForm.RVCCalculationMethod.focus();
       
    }*/
    if (BlanketPeriodBeginDate =="" || BlanketPeriodBeginDate ==null)
    {
        alertStr=alertStr+"Please Enter \'BlanketPeriodBeginDate\' in NAFTA Certificate Of Origin Section \n";
        document.aascShipExecIntlShipmentsForm.BlanketPeriodBeginDate.focus();
    }
    
    if (BlanketPeriodEndDate =="" || BlanketPeriodEndDate ==null)
    {
        alertStr=alertStr+"Please Enter \'BlanketPeriodEndDate\' in NAFTA Certificate Of Origin Section \n";
        document.aascShipExecIntlShipmentsForm.BlanketPeriodEndDate.focus();
    }

    if(isDate(BlanketPeriodEndDate) == false)
    {
           //alert(" Please Enter ExportLicense Expiration Date in YYYY-MM-DD format ");
           document.aascShipExecIntlShipmentsForm.BlanketPeriodEndDate.focus();
           return false;
        
    }

    if(isDate(BlanketPeriodBeginDate) == false)
    {
           //alert(" Please Enter ExportLicense Expiration Date in YYYY-MM-DD format ");
           document.aascShipExecIntlShipmentsForm.BlanketPeriodBeginDate.focus();
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
       /*  var pkgWeight = trim(document.aascShipExecIntlShipmentsForm.Weight.value);
         var uom = trim(document.aascShipExecIntlShipmentsForm.UOM.value);
         var sedTotalValue = trim(document.aascShipExecIntlShipmentsForm.SEDTotalValue.value);
         var scheduleBNumber =trim( document.aascShipExecIntlShipmentsForm.ScheduleBNumber.value);
         var scheduleBQty =trim( document.aascShipExecIntlShipmentsForm.ScheduleBQuantity.value);
         var scheduleBUOM = trim(document.aascShipExecIntlShipmentsForm.ScheduleBUnitOfMeasure.value);
         var expType = trim(document.aascShipExecIntlShipmentsForm.ExportType.value);*/
         var pointOfOrigin =trim( document.aascShipExecIntlShipmentsForm.PointOfOrigin.value);
         var modeOfTransport =trim( document.aascShipExecIntlShipmentsForm.ModeOfTransport.value);
         var sExportDate = trim(document.aascShipExecIntlShipmentsForm.SExportDate.value);
         var sExportingCarrier = trim(document.aascShipExecIntlShipmentsForm.SExportingCarrier.value);
         var inBondCode =trim( document.aascShipExecIntlShipmentsForm.InBondCode.value);
         var cCompanyName =trim( document.aascShipExecIntlShipmentsForm.CCompanyName.value);
         var cAddressLine1 = trim(document.aascShipExecIntlShipmentsForm.CAddressLine1.value);
         var cCity = trim(document.aascShipExecIntlShipmentsForm.CCity.value);
         var cStateProvinceCode =trim( document.aascShipExecIntlShipmentsForm.CStateProvinceCode.value);
         var cPostalCode = trim(document.aascShipExecIntlShipmentsForm.CPostalCode.value);
         var cCountryCode = trim(document.aascShipExecIntlShipmentsForm.CCountryCode.value);
         var PartiestoTransaction = "";
         var LicenseDate=document.aascShipExecIntlShipmentsForm.LicenseDate.value;
         var LicenseNumber=document.aascShipExecIntlShipmentsForm.LicenseNumber.value;
          var ExceptionCodes =document.aascShipExecIntlShipmentsForm.ExceptionCodes.value;
          var EccnNumber = document.aascShipExecIntlShipmentsForm.EccnNumber.value;
         var EntryNumber = document.aascShipExecIntlShipmentsForm.EntryNumber.value;
         
    for (var i=0; i < document.aascShipExecIntlShipmentsForm.PartiestoTransaction.length; i++)
    {
        if (document.aascShipExecIntlShipmentsForm.PartiestoTransaction[i].checked)
        {
            PartiestoTransaction = document.aascShipExecIntlShipmentsForm.PartiestoTransaction[i].value;
        }
    }

    var alertStr = "";
    /*   if(pkgWeight ==''||pkgWeight == null)
       {
       alertStr=alertStr+"Please enter \'Weight of the Package\' in Product Details Section. \n";
       document.aascShipExecIntlShipmentsForm.Weight.focus();
      // return false;
       }
       if(uom ==''||uom == null)
       {
       alertStr=alertStr+"Please enter \'Unit of Measurement\' for Package Weight in in Product Details Section.\n";
       document.aascShipExecIntlShipmentsForm.UOM.focus();
      // return false;
       }
       if(sedTotalValue ==''||sedTotalValue == null)
       {
       alertStr=alertStr+"Please enter \'SED Total Value\' in Product Details Section.\n";
       document.aascShipExecIntlShipmentsForm.SEDTotalValue.focus();
       //return false;
       }
       if(scheduleBNumber ==''||scheduleBNumber == null)
       {
       alertStr=alertStr+"Please enter \'ScheduleBNumber\' for SED in Product Details Section.\n";
       document.aascShipExecIntlShipmentsForm.ScheduleBNumber.focus();
       //return false;
       }
       if(scheduleBQty ==''||scheduleBQty == null)
       {
       alertStr=alertStr+"Please enter \'ScheduleBQuantity\' for SED in Product Details Section. \n";
       document.aascShipExecIntlShipmentsForm.ScheduleBQuantity.focus();
     //  return false;
       }
        if(scheduleBUOM ==''||scheduleBUOM == null)
       {
       alertStr=alertStr+"Please enter \'ScheduleB UnitOfMeasure\' for SED in Product Details Section \n";
       document.aascShipExecIntlShipmentsForm.ScheduleBUnitOfMeasure.focus();
      // return false;
       }
       if(expType ==''||expType == null)
       {
       alertStr=alertStr+"Please enter \'Export Type\' for SED in Product Details Section. \n";
       document.aascShipExecIntlShipmentsForm.ExportType.focus();
      // return false;
       } */
    if(pointOfOrigin ==''||pointOfOrigin == null)
    {
       alertStr=alertStr+"Please enter \'Point(State) Of Origin\' for SED in Shipper's Export Declaration Section. \n";
       document.aascShipExecIntlShipmentsForm.PointOfOrigin.focus();
      // return false;
    }
    if(modeOfTransport ==''||modeOfTransport == null)
    {
       alertStr=alertStr+"Please select \'Mode of Transport\' in Shipper's Export Declaration Section. \n";
       document.aascShipExecIntlShipmentsForm.ModeOfTransport.focus();
     //  return false;
    }
    if(sExportDate ==''||sExportDate == null)
    {
       alertStr=alertStr+"Please enter \'Export Date\' for SED in Shipper's Export Declaration Section. \n";
       document.aascShipExecIntlShipmentsForm.SExportDate.focus();
      // return false;
    }
    if(sExportingCarrier ==''||sExportingCarrier == null)
    {
       alertStr=alertStr+"Please enter \'SED Exporting Carrier\' in Shipper's Export Declaration Section.\n";
       document.aascShipExecIntlShipmentsForm.SExportingCarrier.focus();
     //  return false;
    }
    if(inBondCode ==''||inBondCode == null)
    {
       alertStr=alertStr+"Please select \'InBondCode\' for SED in Shipper's Export Declaration Section. \n";
       document.aascShipExecIntlShipmentsForm.InBondCode.focus();
       //return false;
    }
    if((inBondCode !="70") &&(EntryNumber ==""||EntryNumber == null))
    {
       alertStr=alertStr+"\'InBondCode\' Code is not 70 So please enter \'Entry Number\' for SED in Shipper's Export Declaration Section. \n";
       document.aascShipExecIntlShipmentsForm.EntryNumber.focus();
       //return false;
    }
      // alert('PartiestoTransaction::'+PartiestoTransaction);
    if(PartiestoTransaction !="N" && PartiestoTransaction !="R" )
    {
       alertStr=alertStr+"Please Select \'Parties to Transaction\' Radio Button for SED in Shipper's Export Declaration Section. \n";
      // document.aascShipExecIntlShipmentsForm.PartiestoTransaction.focus();
       //return false;
    }
       
    if((cCompanyName ==''||cCompanyName == null)||(cAddressLine1 ==''||cAddressLine1 == null)||(cCity ==''||cCity == null)||(cStateProvinceCode ==''||cStateProvinceCode == null)||(cPostalCode ==''||cPostalCode == null)||(cCountryCode ==''||cCountryCode == null))
    {
       alertStr=alertStr+"Please enter \'SED Ultimate Consignee Information\' in Shipper's Export Declaration Section. \n";
       //document.aascShipExecIntlShipmentsForm.CCompanyName.focus();
     //  return false;
    }
       
    if((LicenseNumber =='' || LicenseNumber == null) && (ExceptionCodes =='' || ExceptionCodes == null))
    {
       alertStr=alertStr+"Please enter \'License Information\' for SED in Shipper's Export Declaration Section. \n";
      // document.aascShipExecIntlShipmentsForm.LicenseNumber.focus();
       //return false;
    }
      // alert("LicenseNumber=="+LicenseNumber);
    if((LicenseNumber !='' || LicenseNumber !=""))
    {
        if(LicenseDate =='' || LicenseDate == null)
        {
                alert("Please enter \'License Date\' for SED in Shipper's Export Declaration Section. \n");
                document.aascShipExecIntlShipmentsForm.LicenseDate.focus();
                return false;
        }
    }
       
     //  alert("ExceptionCodes=="+ExceptionCodes);
    if((ExceptionCodes !='' || ExceptionCodes !=""))
    {
           var ExceptionCodesStr=document.aascShipExecIntlShipmentsForm.ExceptionCodes.options[document.aascShipExecIntlShipmentsForm.ExceptionCodes.selectedIndex].value;
    
            if(ExceptionCodesStr=="CIV" || ExceptionCodesStr=="CTP" || ExceptionCodesStr=="ENC" || ExceptionCodesStr=="KMI" || ExceptionCodesStr=="LVS")
            {
                 if(EccnNumber =='' || EccnNumber == null)
                {
                    alert("Please enter \'Eccn Number\' for SED in Shipper's Export Declaration Section. \n");
                    document.aascShipExecIntlShipmentsForm.EccnNumber.focus();
                    return false;
                }
            }    
    }
    /*     if(LicenseDate =='' || LicenseDate == null)
       {
       alertStr=alertStr+"Please enter \'License Date\' for SED in Shipper's Export Declaration Section. \n";
       document.aascShipExecIntlShipmentsForm.LicenseDate.focus();
       //return false;
       }
      if(isDate(LicenseDate) == false)
{
      
       document.aascShipExecIntlShipmentsForm.LicenseDate.focus();
       return false;
    
}
       */
     /*     if(isNaN(cPostalCode))
         {
          alertStr=alertStr+"Please Enter Numeric Value in the \'Postal Code\' Field in Shipper's Export Declaration Section.\n"; 
          document.aascShipExecIntlShipmentsForm.CPostalCode.focus();
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
  // alert('RVCCalculationMethod in validateNetCost=='+document.aascShipExecIntlShipmentsForm.RVCCalculationMethod.value);
    if(document.aascShipExecIntlShipmentsForm.RVCCalculationMethod.value == "NO")
    {
         document.aascShipExecIntlShipmentsForm.NetCostPeriodEndDate.disabled = true;
         document.aascShipExecIntlShipmentsForm.NetCostPeriodBeginDate.disabled = true;
    }
    else
    {
         document.aascShipExecIntlShipmentsForm.NetCostPeriodEndDate.disabled = false;
         document.aascShipExecIntlShipmentsForm.NetCostPeriodBeginDate.disabled = false;
    }
}

function saveProductDetails()
{
    var alertStr = "";
    var desc = trim(document.aascShipExecIntlShipmentsForm.description.value);
    var tariffcode = trim(document.aascShipExecIntlShipmentsForm.HarmonizedCode.value);
    var countryOfmanufacture = trim(document.aascShipExecIntlShipmentsForm.CountryOfManufacture.value);

    if(desc ==''||desc == null)
    {
       alertStr = alertStr+"Please enter \'Product Description\' in Product Details Section\n";
       document.aascShipExecIntlShipmentsForm.description.focus();
      // return false;
    }
       
       //SC_UPS
    for (var i = 0; i < desc.length; i++) {
  	
        if(desc.charCodeAt(i)==43||desc.charCodeAt(i)==42||desc.charCodeAt(i)==60||desc.charCodeAt(i)==62)
        {
            alert("Please enter valid Product Description");
            document.aascShipExecIntlShipmentsForm.description.focus();
            return false;
        }
    }
       
      /* if(tariffcode ==''||tariffcode == null)
       {
       alertStr = alertStr+"Please enter \'Product Tariff Code\' in Product Details Section\n";
       document.aascShipExecIntlShipmentsForm.HarmonizedCode.focus();
       //return false;
       } */
    if(countryOfmanufacture ==''||countryOfmanufacture == null)
    {
       alertStr = alertStr+"Please enter \'Country of Manfacture\' in Product Details Section\n";
       document.aascShipExecIntlShipmentsForm.CountryOfManufacture.focus();
       //return false;
    }
       
    if(alertStr != "" && alertStr !=null)
    {
      alert(alertStr);
      return false;
    }
      
    //alert(document.aascShipExecIntlShipmentsForm.COFlag.value);
    if(document.aascShipExecIntlShipmentsForm.CIFlag.value != 'Y' && document.aascShipExecIntlShipmentsForm.COFlag.value != 'Y' && 
            document.aascShipExecIntlShipmentsForm.NAFTAFlag.value != 'Y' && document.aascShipExecIntlShipmentsForm.SEDFlag.value != 'Y' )
    {
        alert("Please Select Commercial Invoice");
        return false;
    }
    if(document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked)
    {
        document.aascShipExecIntlShipmentsForm.CIFlag.value = 'Y';
        if(saveCICommodity() ==false)
	{
            return false;
        }
        
    }
    else
    {
        document.aascShipExecIntlShipmentsForm.CIFlag.value = 'N';
    }
      
    if(document.aascShipExecIntlShipmentsForm.USCO.checked)
    {
        document.aascShipExecIntlShipmentsForm.COFlag.value = 'Y';
        if(saveUSCOCommodity() ==false)
	{
            return false;
        }
    }
    else
    {
        document.aascShipExecIntlShipmentsForm.COFlag.value = 'N';
    }

    if(document.aascShipExecIntlShipmentsForm.NAFTACO.checked)
    {
        document.aascShipExecIntlShipmentsForm.NAFTAFlag.value = 'Y';
        var tariffcode = trim(document.aascShipExecIntlShipmentsForm.HarmonizedCode.value);
         /*
         sc_skp_7.0 the below code is commented in 6.7.1*/
        if(tariffcode ==''||tariffcode == null)
        {
            alert('Please enter Product Tariff Code in Product Details Section');
            document.aascShipExecIntlShipmentsForm.HarmonizedCode.focus();
            return false;
        }
        if(saveNaftaCommodity() ==false)
	{
            return false;
        }
    }
    else
    {
        document.aascShipExecIntlShipmentsForm.NAFTAFlag.value = 'N';
    }
          
    if(document.aascShipExecIntlShipmentsForm.SED.checked)
    {      
        document.aascShipExecIntlShipmentsForm.SEDFlag.value = 'Y';
        if(saveSEDCommodity() ==false)
        {
            return false;
        }
    }
    else
    {
        document.aascShipExecIntlShipmentsForm.SEDFlag.value = 'N';
    }
    document.aascShipExecIntlShipmentsForm.addCommodityFlag.value='Y';
    if(document.aascShipExecIntlShipmentsForm.addCommodityFlag.value =='Y')
    {
        document.aascShipExecIntlShipmentsForm.NAFTACO.disabled=true;
        document.aascShipExecIntlShipmentsForm.SED.disabled = true;
        document.aascShipExecIntlShipmentsForm.USCO.disabled = true;
        document.aascShipExecIntlShipmentsForm.CommercialInvoice.disabled = true;
    }
  
    //  var commLength = document.aascShipExecIntlShipmentsForm.commodityLine.length;
    //    if(document.aascShipExecIntlShipmentsForm.actionStr.value == 'ADD')
    //    {
    //    if(commLength == 52 ){
    //       alert(" Only 50 Commodity Items are allowed for save ");
    //       return false;
    //    }
    //    }

    document.aascShipExecIntlShipmentsForm.selectLength.value = document.aascShipExecIntlShipmentsForm.commodityLine.length + 1;         
    if(window.opener.document.DynaShipmentShipSaveForm.flagShip.value!='Y')
    {
        document.aascShipExecIntlShipmentsForm.actionType.value='ADD';
        document.aascShipExecIntlShipmentsForm.submit();
    }
    //window.opener.document.DynaShipmentShipSaveForm.intlSaveFlag.value = "Y";
    window.opener.document.DynaShipmentShipSaveForm.intlCustomsFlag.value = "Y";
}

function saveSEDCommodity()
{
    //alert("hi");
    var pkgWeight = trim(document.aascShipExecIntlShipmentsForm.Weight.value);
    var uom = trim(document.aascShipExecIntlShipmentsForm.UOM.value);
    var sedTotalValue = trim(document.aascShipExecIntlShipmentsForm.SEDTotalValue.value);
    var scheduleBNumber =trim( document.aascShipExecIntlShipmentsForm.ScheduleBNumber.value);
    var scheduleBQty =trim( document.aascShipExecIntlShipmentsForm.ScheduleBQuantity.value);
    var scheduleBUOM = trim(document.aascShipExecIntlShipmentsForm.ScheduleBUnitOfMeasure.value);
    var expType = trim(document.aascShipExecIntlShipmentsForm.ExportType.value);
    var alertStr ="";
    
    if(pkgWeight ==''||pkgWeight == null)
    {
       alertStr=alertStr+"Please enter \'Weight of the Package\' in Product Details Section. \n";
       document.aascShipExecIntlShipmentsForm.Weight.focus();
      // return false;
    }
    if(isNaN(pkgWeight))
    {
       alertStr=alertStr+"Please enter Numeric Value for \'Weight of the Package\' in Product Details Section. \n";
       document.aascShipExecIntlShipmentsForm.Weight.focus();
      // return false;
    }
    if(pkgWeight < 0)  //Added by Narasimha 16/11/2010
    {
       alertStr=alertStr+"Please enter Positive Numeric Value for \'Weight of the Package\' in Product Details Section. \n";
       document.aascShipExecIntlShipmentsForm.Weight.focus();
    }
    if(uom ==''||uom == null)
    {
       alertStr=alertStr+"Please enter \'Unit of Measurement\' for Package Weight in in Product Details Section.\n";
       document.aascShipExecIntlShipmentsForm.UOM.focus();
      // return false;
    }
    if(sedTotalValue ==''||sedTotalValue == null)
    {
       alertStr=alertStr+"Please enter \'SED Total Value\' in Product Details Section.\n";
       document.aascShipExecIntlShipmentsForm.SEDTotalValue.focus();
       //return false;
    }
    if(sedTotalValue <= 0)
    {
       alertStr=alertStr+"Please enter \'SED Total Value\' in Product Details Section.\n";
       document.aascShipExecIntlShipmentsForm.SEDTotalValue.focus();
       //return false;
    }
    if(isNaN(sedTotalValue))
    {
       alertStr=alertStr+"Please enter numeric value for \'SED Total Value\' in Product Details Section.\n";
       document.aascShipExecIntlShipmentsForm.SEDTotalValue.focus();
       //return false;
    }
    if(scheduleBNumber ==''||scheduleBNumber == null)
    {
       alertStr=alertStr+"Please enter \'ScheduleBNumber\' for SED in Product Details Section.\n";
       document.aascShipExecIntlShipmentsForm.ScheduleBNumber.focus();
       //return false;
    }
    if(scheduleBQty ==''||scheduleBQty == null)
    {
       alertStr=alertStr+"Please enter \'ScheduleBQuantity\' for SED in Product Details Section. \n";
       document.aascShipExecIntlShipmentsForm.ScheduleBQuantity.focus();
     //  return false;
    }
    if(isNaN(scheduleBQty))
    {
       alertStr=alertStr+"Please enter Numeric Value for \'ScheduleBQuantity\' for SED in Product Details Section. \n";
       document.aascShipExecIntlShipmentsForm.ScheduleBQuantity.focus();
     //  return false;
    }
    if(scheduleBUOM ==''||scheduleBUOM == null)
    {
       alertStr=alertStr+"Please enter \'ScheduleB UnitOfMeasure\' for SED in Product Details Section \n";
       document.aascShipExecIntlShipmentsForm.ScheduleBUnitOfMeasure.focus();
      // return false;
    }
    if(expType ==''||expType == null)
    {
       alertStr=alertStr+"Please enter \'Export Type\' for SED in Product Details Section. \n";
       document.aascShipExecIntlShipmentsForm.ExportType.focus();
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
    var preferenceCriteria = trim(document.aascShipExecIntlShipmentsForm.PreferenceCriteria.value);
    var producer = trim(document.aascShipExecIntlShipmentsForm.Producer.value);
    var rvcCalMethod = trim(document.aascShipExecIntlShipmentsForm.RVCCalculationMethod.value);
    var alertStr ="";

    if(preferenceCriteria ==''||preferenceCriteria == null)
    {
       alertStr=alertStr+"Please select Any \'Preference Criteria\' in Product Description Section \n";
       document.aascShipExecIntlShipmentsForm.PreferenceCriteria.focus();
       
    }
    if(producer ==''||producer == null)
    {
       alertStr=alertStr+"Please select \'Producer Info\' in Product Description Section \n";
       document.aascShipExecIntlShipmentsForm.Producer.focus();
       
    }
    if(rvcCalMethod ==''||rvcCalMethod == null)
    {
       alertStr=alertStr+"Please select any \'RVC Calculation Method\' in Product Description Section \n";
       document.aascShipExecIntlShipmentsForm.RVCCalculationMethod.focus();
       
    }
//alert(rvcCalMethod);
    if(rvcCalMethod == "NC")
    {
        if (document.aascShipExecIntlShipmentsForm.NetCostPeriodBeginDate.value =="" || document.aascShipExecIntlShipmentsForm.NetCostPeriodBeginDate.value ==null)
        {
            alertStr=alertStr+"Please Enter \'NetCostPeriodBeginDate\' \n";
            document.aascShipExecIntlShipmentsForm.NetCostPeriodBeginDate.focus();
        }

        if (document.aascShipExecIntlShipmentsForm.NetCostPeriodEndDate.value =="" || document.aascShipExecIntlShipmentsForm.NetCostPeriodEndDate.value ==null)
        {
            alertStr=alertStr+"Please Enter \'NetCostPeriodEndDate\' \n";
            document.aascShipExecIntlShipmentsForm.NetCostPeriodEndDate.focus();
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
    var desc = trim(document.aascShipExecIntlShipmentsForm.description.value);
    //var tariffcode = trim(document.aascShipExecIntlShipmentsForm.HarmonizedCode.value);
    var countryOfmanufacture = trim(document.aascShipExecIntlShipmentsForm.CountryOfManufacture.value);

    var noOfPieces = trim(document.aascShipExecIntlShipmentsForm.Quantity.value);
    var unitPrice = trim(document.aascShipExecIntlShipmentsForm.UnitPrice.value);
    var quantityUnits = trim(document.aascShipExecIntlShipmentsForm.QuantityUnits.value);

    var commLength = document.aascShipExecIntlShipmentsForm.commodityLine.length;

    var alertStr = "";

    if(commLength >= 2)
    {
        if(desc ==''||desc == null)
        {
            alertStr = alertStr+"Please enter \'Product description\' in Product Details Section\n";
            document.aascShipExecIntlShipmentsForm.description.focus();
              // return false;
        }
      /* if(tariffcode ==''||tariffcode == null)
       {
       alertStr = alertStr+"Please enter \'Product Tariff Code\' in Product Details Section\n";
       document.aascShipExecIntlShipmentsForm.HarmonizedCode.focus();
       //return false;
       }*/
        if(countryOfmanufacture ==''||countryOfmanufacture == null)
        {
             alertStr = alertStr+"Please enter \'Country of Manfacture\' in Product Details Section\n";
             document.aascShipExecIntlShipmentsForm.CountryOfManufacture.focus();
             //return false;
        }

        if(noOfPieces ==''||noOfPieces == null)
        {
            alertStr=alertStr+"Please Enter \'Number Of Units\' in Product Details Section \n";
            document.aascShipExecIntlShipmentsForm.Quantity.focus();
            // return false;
        }
        if(unitPrice ==''||unitPrice == null)
        {
            alertStr=alertStr+"Please Enter \'Price per unit\' in Product Details Section\n";
            document.aascShipExecIntlShipmentsForm.UnitPrice.focus();
            //return false;
        }
        if(isNaN(unitPrice))
        {
            alertStr=alertStr+"Please Enter Numeric Value for \'Price per unit\' in Product Details Section\n";
            document.aascShipExecIntlShipmentsForm.UnitPrice.focus();
            //return false;
        } 
        if(unitPrice < 0)  //Added by Narasimha 16/11/2010
        {
            alertStr=alertStr+"Please Enter Positive Value for \'Price per unit\' in Product Details Section\n";
            document.aascShipExecIntlShipmentsForm.UnitPrice.focus();
        }
        if(quantityUnits ==''||quantityUnits == null)
        {
            alertStr=alertStr+"Please enter \'Unit of Measurement\' in Product Details Section \n";
            document.aascShipExecIntlShipmentsForm.QuantityUnits.focus();
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
    var noOfPkgsPerCommodity = trim(document.aascShipExecIntlShipmentsForm.NumberOfPieces.value);
    var Weight=trim(document.aascShipExecIntlShipmentsForm.Weight.value);
    var UOM=trim(document.aascShipExecIntlShipmentsForm.UOM.value);

    if(noOfPkgsPerCommodity ==''||noOfPkgsPerCommodity == null)
    {
       alertStr =alertStr+"Please enter \'Number Of Packages Per Commodity\' in Product Details Section\n";
       document.aascShipExecIntlShipmentsForm.NumberOfPieces.focus();
      // return false;
    }
    if(Weight=="" || Weight==null)
    {
        alertStr =alertStr+ "Please Enter \'Weight\' in Product Details Section \n";
        document.aascShipExecIntlShipmentsForm.Weight.focus();
    }
    if(isNaN(Weight)) //Added by Narasimha 16/11/2010
    {
       alertStr=alertStr+"Please enter Numeric Value for \'Weight of the Package\' in Product Details Section. \n";
       document.aascShipExecIntlShipmentsForm.Weight.focus();
      // return false;
    }
    if(Weight < 0) //Added by Narasimha 16/11/2010
    {
        alertStr=alertStr+"Please enter Positive Numeric Value for \'Weight of the Package\' in Product Details Section. \n";
        document.aascShipExecIntlShipmentsForm.Weight.focus();
    }
    if(UOM=="" || UOM==null)
    {
        alertStr =alertStr+ "Please Enter \'UOM\' in Product Details Section \n";
        document.aascShipExecIntlShipmentsForm.UOM.focus();
    }
    if(alertStr != "" && alertStr !=null)
    {
        alert(alertStr);
        return false;
    }
}



function disableChkBox()
{
 if(document.aascShipExecIntlShipmentsForm.addCommodityFlag.value =='Y')
  {
  document.aascShipExecIntlShipmentsForm.NAFTACO.disabled=true;
  document.aascShipExecIntlShipmentsForm.SED.disabled = true;
  document.aascShipExecIntlShipmentsForm.USCO.disabled = true;
  document.aascShipExecIntlShipmentsForm.CommercialInvoice.disabled = true;
  }
}

function editOptions()
{
 // var form = document.aascShipExecIntlShipmentsForm.commodityLine;
 // var value = form.options.value;
 // alert('hai');
  var form = document.aascShipExecIntlShipmentsForm.commodityLine;

    for (var i=0; i<form.options.length; i++){
         if (form.options[i].selected==true){
          var value = form.options[document.aascShipExecIntlShipmentsForm.commodityLine.selectedIndex].value;    
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
    
    document.aascShipExecIntlShipmentsForm.opValue.value = value;
    enableCommodityDetailDiv();
    document.aascShipExecIntlShipmentsForm.actionType.value='EDIT';
    //alert('actionType::'+document.aascShipExecIntlShipmentsForm.actionType.value);
    document.aascShipExecIntlShipmentsForm.submit();
}


function delOptions()
{
//var form = document.aascShipExecIntlShipmentsForm.commodityLine;
//var value = form.options.value;

    var form = document.aascShipExecIntlShipmentsForm.commodityLine;
    
    for (var i=0; i<form.options.length; i++){
        if (form.options[i].selected==true){
          var value = form.options[document.aascShipExecIntlShipmentsForm.commodityLine.selectedIndex].value;    
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
        document.aascShipExecIntlShipmentsForm.actionType.value='DELETE';
        document.aascShipExecIntlShipmentsForm.submit();
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

    window.opener.document.aascShipExecIntlShipmentsForm.CompanyName.disabled = false;
    window.opener.document.aascShipExecIntlShipmentsForm.AddressLine1.disabled = false;
    window.opener.document.aascShipExecIntlShipmentsForm.AddressLine2.disabled = false;
    window.opener.document.aascShipExecIntlShipmentsForm.City.disabled = false;
    window.opener.document.aascShipExecIntlShipmentsForm.StateProvinceCode.disabled = false;
    window.opener.document.aascShipExecIntlShipmentsForm.PostalCode.disabled = false;
    window.opener.document.aascShipExecIntlShipmentsForm.CountryCode.disabled = false;
    
    document.ShipExecIntlAddrForm.TaxId.value= window.opener.document.aascShipExecIntlShipmentsForm.TaxIdNum.value;
    document.ShipExecIntlAddrForm.AttentionName.value= window.opener.document.aascShipExecIntlShipmentsForm.SoldToAttention.value;
    
    //alert("AttentionName   :"+document.ShipExecIntlAddrForm.AttentionName.value);
    
    document.ShipExecIntlAddrForm.PhoneNum.value= window.opener.document.aascShipExecIntlShipmentsForm.SoldToPhone.value;  
    // alert('soldToFlag'+window.opener.document.aascShipExecIntlShipmentsForm.soldToFlag.value); 
    
    //if(window.opener.document.aascShipExecIntlShipmentsForm.soldToFlag.value == 'true')
    //{
      document.ShipExecIntlAddrForm.companyName.value= window.opener.document.aascShipExecIntlShipmentsForm.CompanyName.value;
      document.ShipExecIntlAddrForm.address.value= window.opener.document.aascShipExecIntlShipmentsForm.AddressLine1.value;
      document.ShipExecIntlAddrForm.city.value= window.opener.document.aascShipExecIntlShipmentsForm.City.value;
      document.ShipExecIntlAddrForm.state.value= window.opener.document.aascShipExecIntlShipmentsForm.StateProvinceCode.value;
      document.ShipExecIntlAddrForm.postalCode.value= window.opener.document.aascShipExecIntlShipmentsForm.PostalCode.value;
      document.ShipExecIntlAddrForm.countryCode.value= window.opener.document.aascShipExecIntlShipmentsForm.CountryCode.value;
    //}
         
    /*document.aascShipExecIntlShipmentsForm.CompanyName.value = '';
    document.aascShipExecIntlShipmentsForm.AddressLine1.value = '';
     // document.aascShipExecIntlShipmentsForm.AddressLine2.value = window.opener.ShipInsertForm.shipToAddressLine2and3.value;
    document.aascShipExecIntlShipmentsForm.City.value = '';
    document.aascShipExecIntlShipmentsForm.StateProvinceCode.value = '';
    document.aascShipExecIntlShipmentsForm.PostalCode.value = '';
    document.aascShipExecIntlShipmentsForm.CountryCode.value=''; */
  
}    





function saveSolAddress()
{
    var CompanyName= document.aascShipExecIntlShipmentsForm.CompanyName.value;
    var AddressLine1 = document.aascShipExecIntlShipmentsForm.AddressLine1.value;
    var AddressLine2 = document.aascShipExecIntlShipmentsForm.AddressLine2.value;
    var City = document.aascShipExecIntlShipmentsForm.City.value;
    var StateProvinceCode = document.aascShipExecIntlShipmentsForm.StateProvinceCode.value;
    var PostalCode = document.aascShipExecIntlShipmentsForm.PostalCode.value;
    var CountryCode = document.aascShipExecIntlShipmentsForm.CountryCode.value;
    
    //if(document.aascShipExecIntlShipmentsForm.ShipTosameSoldTo.value=='N')
    //{
    if(document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked==true ||document.aascShipExecIntlShipmentsForm.NAFTACO.checked==true){ 
    if(CompanyName =='' || City== '' ||CountryCode== '' || AddressLine1=='')
    {
    alert("Please Enter All Sold to Address Values");
    //document.aascShipExecIntlShipmentsForm.CompanyName.focus();
     return false;
    }
    //}
     
    } 

}
     
function chkCommodity()
{

    var commLength = document.aascShipExecIntlShipmentsForm.commodityLine.length;
 /* if(commLength <3){
  alert('Please Enter Details of Atleast One Product');
  return false;
  } */
}

function chkGeneralDetails()
{
    if((document.aascShipExecIntlShipmentsForm.TaxIdNum.value != '' || document.aascShipExecIntlShipmentsForm.SoldToAttention.value != '' ||
       document.aascShipExecIntlShipmentsForm.SoldToPhone.value != '' || document.aascShipExecIntlShipmentsForm.InvCurCd.value != ''  )&&
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
        var commLength = document.aascShipExecIntlShipmentsForm.commodityLine.length;
        if(commLength <3){
            alert('Please Enter Details of Atleast One Product');
            return false;
        }
    }
    var newwindow;
    
    var shipFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;
    var locationId = document.getElementById("locationId").value;
    var carrierCode = document.getElementById("carrierCode").value;
    document.aascShipExecIntlShipmentsForm.addressType.value = addressType;
    newwindow=window.open("AascShipExecIntlAddressAction.action?actionType=shipExecIntlAddressAction&carrierCode="+carrierCode+"&shipFlag="+shipFlag+"&addressType="+addressType+"&locationId="+locationId,'name','height=250,width=600,scrollbars=yes, resizable=yes');
    if (window.focus) {newwindow.focus()}
    return false;
}        

function loadUpsIntlAddr()
{
    var addressType=window.opener.document.aascShipExecIntlShipmentsForm.addressType.value;

    if( shipFlagTemp != 'Y' && addressType == 'SoldToAddr')
    {
        chkSoldAddress();
    }
    else
    {
        document.ShipExecIntlAddrForm.addressType.value=addressType;
        //alert(addressType+"  onload");
        if(document.ShipExecIntlAddrForm.addressType.value=='NAFTAAddr'){
             document.ShipExecIntlAddrForm.companyName.value=window.opener.document.aascShipExecIntlShipmentsForm.PCompanyName.value;
             document.ShipExecIntlAddrForm.address.value=window.opener.document.aascShipExecIntlShipmentsForm.PAddressLine1.value;
             document.ShipExecIntlAddrForm.city.value=window.opener.document.aascShipExecIntlShipmentsForm.PCity.value;
             document.ShipExecIntlAddrForm.state.value=window.opener.document.aascShipExecIntlShipmentsForm.PStateProvinceCode.value;
             document.ShipExecIntlAddrForm.postalCode.value=window.opener.document.aascShipExecIntlShipmentsForm.PPostalCode.value;
             document.ShipExecIntlAddrForm.countryCode.value=window.opener.document.aascShipExecIntlShipmentsForm.PCountryCode.value;
             document.ShipExecIntlAddrForm.TaxId.value= window.opener.document.aascShipExecIntlShipmentsForm.NTaxIdNum.value;
        }
        else if(document.ShipExecIntlAddrForm.addressType.value=='SoldToAddr'){
     
             var ShipTosameSoldToFlag  =  window.opener.document.aascShipExecIntlShipmentsForm.ShipTosameSoldTo.value;
             //alert('ShipTosameSoldToFlag=='+ShipTosameSoldToFlag);
             
        //     if(ShipTosameSoldToFlag=='Y')
        //     {
        //         document.ShipExecIntlAddrForm.companyName.readOnly = true;
        //         document.ShipExecIntlAddrForm.address.readOnly = true;
        //         document.ShipExecIntlAddrForm.city.readOnly = true;
        //         document.ShipExecIntlAddrForm.state.readOnly = true;
        //         document.ShipExecIntlAddrForm.postalCode.readOnly = true;
        //         document.ShipExecIntlAddrForm.countryCode.readOnly = true; 
        //     }
        //     else
        //     {
        //         document.ShipExecIntlAddrForm.companyName.readOnly = false;
        //         document.ShipExecIntlAddrForm.address.readOnly = false;
        //         document.ShipExecIntlAddrForm.city.readOnly = false;
        //         document.ShipExecIntlAddrForm.state.readOnly = false;
        //         document.ShipExecIntlAddrForm.postalCode.readOnly = false;
        //         document.ShipExecIntlAddrForm.countryCode.readOnly = false; 
        //     }
             document.ShipExecIntlAddrForm.companyName.value=window.opener.document.aascShipExecIntlShipmentsForm.CompanyName.value;
             document.ShipExecIntlAddrForm.address.value=window.opener.document.aascShipExecIntlShipmentsForm.AddressLine1.value;
             document.ShipExecIntlAddrForm.city.value=window.opener.document.aascShipExecIntlShipmentsForm.City.value;
             document.ShipExecIntlAddrForm.state.value=window.opener.document.aascShipExecIntlShipmentsForm.StateProvinceCode.value;
             document.ShipExecIntlAddrForm.postalCode.value=window.opener.document.aascShipExecIntlShipmentsForm.PostalCode.value;
             document.ShipExecIntlAddrForm.countryCode.value= window.opener.document.aascShipExecIntlShipmentsForm.CountryCode.value;
             document.ShipExecIntlAddrForm.TaxId.value= window.opener.document.aascShipExecIntlShipmentsForm.TaxIdNum.value;
             document.ShipExecIntlAddrForm.AttentionName.value= window.opener.document.aascShipExecIntlShipmentsForm.SoldToAttention.value;
             document.ShipExecIntlAddrForm.PhoneNum.value= window.opener.document.aascShipExecIntlShipmentsForm.SoldToPhone.value;  
        }
        else if(document.ShipExecIntlAddrForm.addressType.value=='ConsigneeInfo'){
             document.ShipExecIntlAddrForm.companyName.value=window.opener.document.aascShipExecIntlShipmentsForm.CCompanyName.value;
             document.ShipExecIntlAddrForm.address.value=window.opener.document.aascShipExecIntlShipmentsForm.CAddressLine1.value;
             document.ShipExecIntlAddrForm.city.value=window.opener.document.aascShipExecIntlShipmentsForm.CCity.value;
             document.ShipExecIntlAddrForm.state.value=window.opener.document.aascShipExecIntlShipmentsForm.CStateProvinceCode.value;
             document.ShipExecIntlAddrForm.postalCode.value=window.opener.document.aascShipExecIntlShipmentsForm.CPostalCode.value;
            document.ShipExecIntlAddrForm.countryCode.value= window.opener.document.aascShipExecIntlShipmentsForm.CCountryCode.value;
        }
        else if(document.ShipExecIntlAddrForm.addressType.value=='FAgentInfo'){
             document.ShipExecIntlAddrForm.companyName.value=window.opener.document.aascShipExecIntlShipmentsForm.FCompanyName.value;
             document.ShipExecIntlAddrForm.address.value=window.opener.document.aascShipExecIntlShipmentsForm.FAddressLine1.value;
             document.ShipExecIntlAddrForm.city.value=window.opener.document.aascShipExecIntlShipmentsForm.FCity.value;
             document.ShipExecIntlAddrForm.state.value=window.opener.document.aascShipExecIntlShipmentsForm.FStateProvinceCode.value;
             document.ShipExecIntlAddrForm.postalCode.value=window.opener.document.aascShipExecIntlShipmentsForm.FPostalCode.value;
            document.ShipExecIntlAddrForm.countryCode.value= window.opener.document.aascShipExecIntlShipmentsForm.FCountryCode.value;
            document.ShipExecIntlAddrForm.TaxId.value= window.opener.document.aascShipExecIntlShipmentsForm.FTaxIdNum.value;
        }
        else if(document.ShipExecIntlAddrForm.addressType.value=='IConsigneeInfo'){
            document.ShipExecIntlAddrForm.companyName.value=window.opener.document.aascShipExecIntlShipmentsForm.ICCompanyName.value;
            document.ShipExecIntlAddrForm.address.value=window.opener.document.aascShipExecIntlShipmentsForm.ICAddressLine1.value;
            document.ShipExecIntlAddrForm.city.value=window.opener.document.aascShipExecIntlShipmentsForm.ICCity.value;
            document.ShipExecIntlAddrForm.state.value=window.opener.document.aascShipExecIntlShipmentsForm.ICStateProvinceCode.value;
            document.ShipExecIntlAddrForm.postalCode.value=window.opener.document.aascShipExecIntlShipmentsForm.ICPostalCode.value;
            document.ShipExecIntlAddrForm.countryCode.value= window.opener.document.aascShipExecIntlShipmentsForm.ICCountryCode.value;
        }
     
        if(document.ShipExecIntlAddrForm.shipFlag.value == "Y")
        {  
             document.ShipExecIntlAddrForm.companyName.readOnly = true;
             document.ShipExecIntlAddrForm.address.readOnly = true;
             document.ShipExecIntlAddrForm.city.readOnly = true;
             document.ShipExecIntlAddrForm.state.readOnly = true;
             document.ShipExecIntlAddrForm.postalCode.readOnly = true;
             document.ShipExecIntlAddrForm.countryCode.readOnly = true;
             document.ShipExecIntlAddrForm.save.disabled = true;
             document.ShipExecIntlAddrForm.close.focus();
        }  
    }
}
     
    
function saveUpsIntlAddrDetails()
{
      var pCode = trim(document.ShipExecIntlAddrForm.postalCode.value);
      var companyName = document.ShipExecIntlAddrForm.companyName.value;
      var addressSize = trim(document.ShipExecIntlAddrForm.address.value).length;
      var citySize = trim(document.ShipExecIntlAddrForm.city.value).length;
      var stateSize = trim(document.ShipExecIntlAddrForm.state.value).length;
      var postalCodeSize = trim(document.ShipExecIntlAddrForm.postalCode.value).length;
      var addressType = trim(document.ShipExecIntlAddrForm.addressType.value);
      var countryCodeSize =trim(document.ShipExecIntlAddrForm.countryCode.value).length;
      
      
      
     if(Validation()==false)
     {
      return false;
     }
     else
     {
     
      if(document.ShipExecIntlAddrForm.addressType.value=='NAFTAAddr'){
     window.opener.document.aascShipExecIntlShipmentsForm.PCompanyName.value=document.ShipExecIntlAddrForm.companyName.value;
     window.opener.document.aascShipExecIntlShipmentsForm.PAddressLine1.value=document.ShipExecIntlAddrForm.address.value;
     window.opener.document.aascShipExecIntlShipmentsForm.PCity.value=document.ShipExecIntlAddrForm.city.value;
     window.opener.document.aascShipExecIntlShipmentsForm.PStateProvinceCode.value=document.ShipExecIntlAddrForm.state.value;
     window.opener.document.aascShipExecIntlShipmentsForm.PPostalCode.value=document.ShipExecIntlAddrForm.postalCode.value;
     window.opener.document.aascShipExecIntlShipmentsForm.PCountryCode.value=document.ShipExecIntlAddrForm.countryCode.value;
     window.opener.document.aascShipExecIntlShipmentsForm.NTaxIdNum.value = document.ShipExecIntlAddrForm.TaxId.value;
     window.close();
     }
     else if(document.ShipExecIntlAddrForm.addressType.value=='SoldToAddr'){
     window.opener.document.aascShipExecIntlShipmentsForm.CompanyName.value=document.ShipExecIntlAddrForm.companyName.value;
     window.opener.document.aascShipExecIntlShipmentsForm.AddressLine1.value=document.ShipExecIntlAddrForm.address.value;
     window.opener.document.aascShipExecIntlShipmentsForm.City.value=document.ShipExecIntlAddrForm.city.value;
     window.opener.document.aascShipExecIntlShipmentsForm.StateProvinceCode.value=document.ShipExecIntlAddrForm.state.value;
     window.opener.document.aascShipExecIntlShipmentsForm.PostalCode.value=document.ShipExecIntlAddrForm.postalCode.value;
     window.opener.document.aascShipExecIntlShipmentsForm.CountryCode.value=document.ShipExecIntlAddrForm.countryCode.value;
     window.opener.document.aascShipExecIntlShipmentsForm.TaxIdNum.value = document.ShipExecIntlAddrForm.TaxId.value;
     window.opener.document.aascShipExecIntlShipmentsForm.SoldToAttention.value = document.ShipExecIntlAddrForm.AttentionName.value;
     window.opener.document.aascShipExecIntlShipmentsForm.SoldToPhone.value = document.ShipExecIntlAddrForm.PhoneNum.value;
     
     var addorEditSoldTo = document.ShipExecIntlAddrForm.addOrEditSoldTo.checked;
     if(addorEditSoldTo){
     window.opener.document.aascShipExecIntlShipmentsForm.addOrEditImporter.value = document.ShipExecIntlAddrForm.addOrEditSoldTo.value;
     }
    // alert("window.opener.aascShipExecIntlShipmentsForm.addOrEditImporter.value"+window.opener.document.aascShipExecIntlShipmentsForm.addOrEditImporter.value);
//     if(window.opener.document.aascShipExecIntlShipmentsForm.ShipTosameSoldTo.value=='N'){
//      window.opener.document.aascShipExecIntlShipmentsForm.soldToFlag.value='true';
// //alert('soldToFlag'+window.opener.document.aascShipExecIntlShipmentsForm.soldToFlag.value); 
// }
      window.close();
     }
     else if(document.ShipExecIntlAddrForm.addressType.value=='ConsigneeInfo'){
     window.opener.document.aascShipExecIntlShipmentsForm.CCompanyName.value=document.ShipExecIntlAddrForm.companyName.value;
     window.opener.document.aascShipExecIntlShipmentsForm.CAddressLine1.value=document.ShipExecIntlAddrForm.address.value;
     window.opener.document.aascShipExecIntlShipmentsForm.CCity.value=document.ShipExecIntlAddrForm.city.value;
     window.opener.document.aascShipExecIntlShipmentsForm.CStateProvinceCode.value=document.ShipExecIntlAddrForm.state.value;
     window.opener.document.aascShipExecIntlShipmentsForm.CPostalCode.value=document.ShipExecIntlAddrForm.postalCode.value;
     window.opener.document.aascShipExecIntlShipmentsForm.CCountryCode.value=document.ShipExecIntlAddrForm.countryCode.value;
     window.close();
     }
      else if(document.ShipExecIntlAddrForm.addressType.value=='FAgentInfo'){
     window.opener.document.aascShipExecIntlShipmentsForm.FCompanyName.value=document.ShipExecIntlAddrForm.companyName.value;
     window.opener.document.aascShipExecIntlShipmentsForm.FAddressLine1.value=document.ShipExecIntlAddrForm.address.value;
     window.opener.document.aascShipExecIntlShipmentsForm.FCity.value=document.ShipExecIntlAddrForm.city.value;
     window.opener.document.aascShipExecIntlShipmentsForm.FStateProvinceCode.value=document.ShipExecIntlAddrForm.state.value;
     window.opener.document.aascShipExecIntlShipmentsForm.FPostalCode.value=document.ShipExecIntlAddrForm.postalCode.value;
     window.opener.document.aascShipExecIntlShipmentsForm.FCountryCode.value=document.ShipExecIntlAddrForm.countryCode.value;
     window.opener.document.aascShipExecIntlShipmentsForm.FTaxIdNum.value = document.ShipExecIntlAddrForm.TaxId.value;
     window.close();
     }
      else if(document.ShipExecIntlAddrForm.addressType.value=='IConsigneeInfo'){
     window.opener.document.aascShipExecIntlShipmentsForm.ICCompanyName.value=document.ShipExecIntlAddrForm.companyName.value;
     window.opener.document.aascShipExecIntlShipmentsForm.ICAddressLine1.value=document.ShipExecIntlAddrForm.address.value;
     window.opener.document.aascShipExecIntlShipmentsForm.ICCity.value=document.ShipExecIntlAddrForm.city.value;
     window.opener.document.aascShipExecIntlShipmentsForm.ICStateProvinceCode.value=document.ShipExecIntlAddrForm.state.value;
     window.opener.document.aascShipExecIntlShipmentsForm.ICPostalCode.value=document.ShipExecIntlAddrForm.postalCode.value;
     window.opener.document.aascShipExecIntlShipmentsForm.ICCountryCode.value=document.ShipExecIntlAddrForm.countryCode.value;
     window.close();
     }
   }
 //  alert('window.opener.aascShipExecIntlShipmentsForm.CompanyName.value=='+window.opener.aascShipExecIntlShipmentsForm.CompanyName.value);
}

function viewPrinted()
{
//alert("Entered viewPrint()");
//document.aascShipExecIntlShipmentsForm.actionType.value='VIEWPRINT';
//document.aascShipExecIntlShipmentsForm.submit();
  var upsMode=document.getElementById("upsModeHid").value;
    popupWindow = window.open('aascIntlDocViewPrint.jsp?upsMode='+upsMode,'_blank', 'toolbar=yes, scrollbars=yes, resizable=yes');
    popupWindow.focus();
}       
        
function disableECCNNumber()
{

    var ExceptionCodesStr=document.aascShipExecIntlShipmentsForm.ExceptionCodes.options[document.aascShipExecIntlShipmentsForm.ExceptionCodes.selectedIndex].value;
    
    if(ExceptionCodesStr=="CIV" || ExceptionCodesStr=="CTP" || ExceptionCodesStr=="ENC" || ExceptionCodesStr=="KMI" || ExceptionCodesStr=="LVS")
    {
     document.aascShipExecIntlShipmentsForm.EccnNumber.disabled = false;
    }
    else
    {
     document.aascShipExecIntlShipmentsForm.EccnNumber.disabled = true;
    }

}

function Validation()
{
   var addressType = trim(document.ShipExecIntlAddrForm.addressType.value);

   if(addressType!="SoldToAddr")
   {
      
               if(trim(document.ShipExecIntlAddrForm.companyName.value)==""){
                         alert("Please Enter Ups Intl Company Name :");
                         document.ShipExecIntlAddrForm.companyName.focus();
                        return false;                       
                }
             if(trim(document.ShipExecIntlAddrForm.address.value)==""){
                         alert("Please Enter Ups Intl Address :");
                         document.ShipExecIntlAddrForm.address.focus();
                          return false;    
                         }
            if(trim(document.ShipExecIntlAddrForm.city.value)==""){
                         alert("Please Enter Ups Intl City :");
                         document.ShipExecIntlAddrForm.city.focus();
                          return false;    
                         }
             if(trim(document.ShipExecIntlAddrForm.state.value)==""){
                         alert("Please Enter Ups Intl State :");
                 document.ShipExecIntlAddrForm.state.focus();
                  return false;    
                         }
            if(document.ShipExecIntlAddrForm.postalCode.value==""){
                         alert("Please Enter Ups Intl Postal Code :");
                         document.ShipExecIntlAddrForm.postalCode.focus();
                          return false;    
                         }
            if(trim(document.ShipExecIntlAddrForm.countryCode.value)==""){
                         alert("Please Enter Ups Intl CountryCode Code :");
                         document.ShipExecIntlAddrForm.countryCode.focus();
                          return false;    
                         }    
    }
    
    if((addressType=="FAgentInfo") ||(addressType=="NAFTAAddr"))
   {
     if(trim(document.ShipExecIntlAddrForm.TaxId.value)==""){
                         alert("Please Enter Ups Intl Tax Id :");
                         document.ShipExecIntlAddrForm.TaxId.focus();
                        return false;                       
                }
   }
   return true;
}

function ShowFields() // added by khaja to show the selected form fields
{ 
    //  alert('entered ShowFields()');
    var ComInv = document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked;
    var USCO = document.aascShipExecIntlShipmentsForm.USCO.checked;
    var NAFTACO = document.aascShipExecIntlShipmentsForm.NAFTACO.checked;
    var SED= document.aascShipExecIntlShipmentsForm.SED.checked ;
    
    
    //alert("in ShowFields ::"+ComInv);
    //alert("in ShowFields ::"+USCO);
    //alert("in ShowFields ::"+NAFTACO);
    //alert("in ShowFields ::"+SED);
    
    
    //alert("box val :"+document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked);
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
        document.aascShipExecIntlShipmentsForm.NumberOfPieces.disabled="true";
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
    var selectform = document.aascShipExecIntlShipmentsForm.SelectForm.checked;
    if(selectform==false){
        document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked=false;
        document.aascShipExecIntlShipmentsForm.USCO.checked=false;
        document.aascShipExecIntlShipmentsForm.NAFTACO.checked=false;
        document.aascShipExecIntlShipmentsForm.SED.checked=false ;   
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
    var currentCommItem=document.aascShipExecIntlShipmentsForm.selCommItems.options[document.aascShipExecIntlShipmentsForm.selCommItems.selectedIndex].text;
        
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
              document.aascShipExecIntlShipmentsForm.CountryOfManufacture.value=countryOfManufacture;
              
              startIndex  = responseStringDummy.indexOf('*');
              numberOfPieces     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
             // document.aascShipExecIntlShipmentsForm.Quantity.value=numberOfPieces;
              
              startIndex  = responseStringDummy.indexOf('*');
              quantityUnits     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
             // document.aascShipExecIntlShipmentsForm.Quantity.value=quantityUnits;
              
              startIndex  = responseStringDummy.indexOf('*');
              quantity     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
              document.aascShipExecIntlShipmentsForm.Quantity.value=quantity;
             
              startIndex  = responseStringDummy.indexOf('*');
              UOM     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
              document.aascShipExecIntlShipmentsForm.QuantityUnits.value=UOM;
              
              if(document.aascShipExecIntlShipmentsForm.QuantityUnits.value == "")
              {
                document.aascShipExecIntlShipmentsForm.QuantityUnits.value = "";
              
              }
              
              startIndex  = responseStringDummy.indexOf('*');
              unitPrice     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
              document.aascShipExecIntlShipmentsForm.UnitPrice.value=unitPrice;
              
             // alert("description   :"+description);
                                 
              startIndex  = responseStringDummy.indexOf('*');
              description     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
              document.aascShipExecIntlShipmentsForm.description.value=description;
              
           //   document.aascShipExecIntlShipmentsForm.Weight.value=weight;
           
              startIndex  = responseStringDummy.indexOf('*');
              harmonizedCode     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
         //     document.aascShipExecIntlShipmentsForm.HarmonizedCode.value=harmonizedCode;
    //          
              startIndex  = responseStringDummy.indexOf('*');
              tariffCode     = responseStringDummy.substring(0,startIndex);
              responseStringDummy=responseStringDummy.substring(startIndex+1);
              document.aascShipExecIntlShipmentsForm.HarmonizedCode.value=tariffCode;
    //          
    //          exportLicenseExpirationDate = responseStringDummy;
    //          document.aascShipExecIntlShipmentsForm.ExportLicenseExpirationDate.value=exportLicenseExpirationDate;
    //          
              
           }
        }
        var locationId = document.getElementById("locationId").value;
        var url="aascAjaxIntlCommodityDetail.jsp?currentItem="+currentCommItem+"&locationId="+locationId;
        xmlHttp.open("GET",url,true);  // Calling 
        xmlHttp.setRequestHeader("Cache-Control","no-cache");
        xmlHttp.setRequestHeader("Pragma","no-cache");
        xmlHttp.setRequestHeader("If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT");
        xmlHttp.send(null);  
}


function getIntlImporterDetails(){
    var currentImporter = document.ShipExecIntlAddrForm.selImporterName.value;
    if(currentImporter == 'Select')
    {
//       document.ShipExecIntlAddrForm.AttentionName.value="";
//       document.ShipExecIntlAddrForm.companyName.value="";
//       document.ShipExecIntlAddrForm.PhoneNum.value="";
//       document.ShipExecIntlAddrForm.address.value="";
//       document.ShipExecIntlAddrForm.city.value="";
//       document.ShipExecIntlAddrForm.state.value="";
//       document.ShipExecIntlAddrForm.postalCode.value="";
//       document.ShipExecIntlAddrForm.countryCode.value="";
//       document.ShipExecIntlAddrForm.TaxId.value="";
    }
  
    else {
        getAjaxInlImporterDetail();
    }
}

function getAjaxInlImporterDetail(){
    var currentImporter = document.ShipExecIntlAddrForm.selImporterName.value;
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
          document.ShipExecIntlAddrForm.TaxId.value=ImporterTINOrDUNS;
          
          startIndex  = responseStringDummy.indexOf('*');
          ImporterTINOrDUNSType     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          
          startIndex  = responseStringDummy.indexOf('*');
          importerAddress1     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.ShipExecIntlAddrForm.address.value=importerAddress1;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerAddress2     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCity     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.ShipExecIntlAddrForm.city.value=importerCity;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCompName     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.ShipExecIntlAddrForm.companyName.value=importerCompName;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCountryCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.ShipExecIntlAddrForm.countryCode.value=importerCountryCode;
                             
          startIndex  = responseStringDummy.indexOf('*');
          importerPhoneNum     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.ShipExecIntlAddrForm.PhoneNum.value=importerPhoneNum;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerPostalCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.ShipExecIntlAddrForm.postalCode.value=importerPostalCode;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerState     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.ShipExecIntlAddrForm.state.value=importerState;
          
          importerName = responseStringDummy;
          document.ShipExecIntlAddrForm.AttentionName.value=importerName;
       }
    }
    var locationId = document.getElementById("locationId").value;
    var url="aascAjaxIntlImporterDetail.jsp?currentImporter="+currentImporter+"&locationId="+locationId;
    xmlHttp.open("GET",url,true);  // Calling 
    xmlHttp.send(null);  
}

function getIntlCommodityLineDetails(){
//alert('entered getIntlCommodityLineDetails()');
    var currentCommItem = document.aascShipExecIntlShipmentsForm.selCommItems.value;
    var shipFlag = document.aascShipExecIntlShipmentsForm.shipFlagStr.value;
    //alert('currentCommItem:'+currentCommItem);
   

    if(currentCommItem == 'Select')
    {
       disableCommodityDetailDiv();
       document.aascShipExecIntlShipmentsForm.CountryOfManufacture.value="US";
       document.aascShipExecIntlShipmentsForm.Quantity.value="";
       document.aascShipExecIntlShipmentsForm.QuantityUnits.value="";
       document.aascShipExecIntlShipmentsForm.UnitPrice.value="";
       document.aascShipExecIntlShipmentsForm.description.value="";
       document.aascShipExecIntlShipmentsForm.HarmonizedCode.value="";
       document.aascShipExecIntlShipmentsForm.addComm.disabled=true;
    }
    else if(currentCommItem == 'Create'){
      //alert('enterd create');
       enableCommodityDetailDiv();
      //alert('after calling enableCommodityDetailDiv()');
       document.aascShipExecIntlShipmentsForm.CountryOfManufacture.value="US";
       document.aascShipExecIntlShipmentsForm.Quantity.value="";
       document.aascShipExecIntlShipmentsForm.QuantityUnits.value="";
       document.aascShipExecIntlShipmentsForm.UnitPrice.value="";
       document.aascShipExecIntlShipmentsForm.description.value="";
       document.aascShipExecIntlShipmentsForm.HarmonizedCode.value="";
       
       if(shipFlag == 'Y'){
        document.aascShipExecIntlShipmentsForm.addComm.disabled=true;
       }else{
        document.aascShipExecIntlShipmentsForm.addComm.disabled=false;
       }
    }
    else {
        if(shipFlag == 'Y'){
        document.aascShipExecIntlShipmentsForm.addComm.disabled=true;
       }else{
        document.aascShipExecIntlShipmentsForm.addComm.disabled=false;
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
     var frCharge = document.aascShipExecIntlShipmentsForm.FreightCharges.value;
     var inCharge = document.aascShipExecIntlShipmentsForm.InsuranceCharges.value;
     var msCharge = document.aascShipExecIntlShipmentsForm.OtherCharges.value;
     var lineCustomValue = document.aascShipExecIntlShipmentsForm.commCustomValue.value;
     var InvVal = document.aascShipExecIntlShipmentsForm.InvVal.value;
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
           document.aascShipExecIntlShipmentsForm.FreightCharge.focus();
  	       return false;
        }
     }
     for (var i = 0; i < inCharge.length; i++) {
  	    if(!(inCharge.charCodeAt(i)>47 && inCharge.charCodeAt(i)<58) && inCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascShipExecIntlShipmentsForm.InsuranceCharge.focus();
  	       return false;
        }
     }
     for (var i = 0; i < msCharge.length; i++) {
  	    if(!(msCharge.charCodeAt(i)>47 && msCharge.charCodeAt(i)<58) && msCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascShipExecIntlShipmentsForm.TaxesOrMiscellaneousCharge.focus();
  	       return false;
        }
     }      
     var rnum = parseFloat(lineCustomValue)+parseFloat(msCharge)+parseFloat(inCharge)+parseFloat(frCharge);
     if (rnum > 8191 && rnum < 10485) {
		     rnum = rnum-5000;
            	     var newnumber = Math.round(rnum*Math.pow(10,2))/Math.pow(10,2);
		     newnumber = newnumber+5000;
	  } else {
		     var newnumber = Math.round(rnum*Math.pow(10,2))/Math.pow(10,2);
	  }
      document.aascShipExecIntlShipmentsForm.InvVal.value = newnumber;
   //     document.aascShipExecIntlShipmentsForm.TotalCustomsValue.value = Math.round(parseFloat(lineCustomValue)+parseFloat(msCharge)+parseFloat(inCharge)+parseFloat(frCharge));
     
}
// Added by Ravi Teja to display Invoice details for Canada and Puerto Rico country. Also this function call the totalCustomValue function which calculates Invoice total
function showInvoiceForCA()
{  
    if((window.opener.document.DynaShipmentShipSaveForm.country.value=='CA' || window.opener.document.DynaShipmentShipSaveForm.country.value=='PR') &&
            window.opener.document.DynaShipmentShipSaveForm.shipFromCountryId.value == 'US' && !document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked){
       
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
//                window.opener.document.DynaShipmentShipSaveForm.shipFromCountryId.value == 'US' && document.aascShipExecIntlShipmentsForm.InvVal.value == 0){
            totalCustomValue();
//         }
    }
}

function calculateInvoiceTotal()
{
//    if((window.opener.document.DynaShipmentShipSaveForm.country.value=='CA' || window.opener.document.DynaShipmentShipSaveForm.country.value=='PR') &&
//        window.opener.document.DynaShipmentShipSaveForm.shipFromCountryId.value == 'US' && !document.aascShipExecIntlShipmentsForm.CommercialInvoice.checked){
            totalCustomValue();
//    }
}

function assignOrderNumber(){
    window.opener.document.DynaShipmentShipSaveForm.orderNum.value=document.aascShipExecIntlShipmentsForm.orderNumber.value;
}
