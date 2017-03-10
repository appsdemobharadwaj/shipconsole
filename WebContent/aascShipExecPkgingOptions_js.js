/*===========================================================================================================+

|  DESCRIPTION                                                                                               |

|    javascript file for the aascFedexPackageOptions.jsp  validation                                         |

|    Author                                                                                 |

|    Version                                                                                               |                                                                            

|    Creation                                                                                     |

|    History :


20-Nov-2014         Eshwari M       Added this file from cloud 1.2 version
14-Jan-2015         Suman G         Changed from "document.getElementById('shipToAddressId').options[window.opener.document.getElementById('shipToAddressId').selectedIndex].value" to "document.getElementById('shipToAddressId').value" to fix #2530 and #2531
14-Jan-2015         Suman G         Modified substring condition from "rtnShipMethod.length-4" to "rtnShipMethod.length-6" for fix #2534.
27-Jan-2015         Suman G         Added a statement for pulling ship to phone number from shipment page and save it to ship from phone number in return shipment for #2535.
05/03/2015    Sanjay & Khaja Added code for new UI changes.
10/03/2015          Suman G         Modified shipmentDate id. 
30/03/2015          Sunanda K       Modified code for bug fix #2747
31/03/2015          Suman G         Added code to fix #2747
28/04/2015          Suman g         Added code to fix #2787
05/05/2015          Suman G         Added code to fix #2894
25/05/2015          Sunanda K       Modified code for bug fix #2894
03/06/2015          Suman G         Added code to fix #2955
09/08/2015          Suman G         Added vikas code to fix #2955
09/06/2015          Suman G         Added Code to fix #2955
22/06/2015          Suman G         Commented line at 466 to fix #3041
22/06/2015          Suman G         Uncommented line at 1403 to fix #3028
06/11/2015          Shiva G         Modified code disable tip for issue 3203
09/11/2015          Suman G         Added code to fix #3939.
20/11/2015          Y Pradeep       Modified code to get Hazardous Material details to load from Ajax call properly in FedEx Package options js.
                                    Added code to select Signature checkbox and Direct option by default when Hazardous Material checkbox is enabled while saving FedEx package options js.
                                    Bugs #3965 and #3984.
28/12/2015          Suman G         Added code to fix #4153
+===========================================================================================================*/

var globalvar="";
var globalvar2="";
var globalDefaultHazMatId="";
var globalHazMatPkgGrpLoad="";


  
function gettip(txt)
{
//    document.getElementById('tip').innerHTML=txt; //Shiva commented to fix the issue 3203
}
  
function resetTip()
{
//    document.getElementById('tip').innerHTML=""; //Shiva commented to fix the issue 3203
}


function loadPackageOptions()
{

document.aascShipExecPackageOptionsForm.elements['rtnShipToCompany'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['rtnShipToContact'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['rtnShipToLine1'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['rtnShipToLine2'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['rtnShipToCity'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['rtnShipToState'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['rtnShipToZip'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['rtnShipToPhone'].style.display='none';
//document.aascShipExecPackageOptionsForm.elements['rtnACNumber'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['rtnDropOfType'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['rtnPackageList'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['rtnRMA'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['holdAtLocation'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['halPhone'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['halAddrLine1'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['halAddrLine2'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['halCity'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['halState'].style.display='none';
document.aascShipExecPackageOptionsForm.elements['halZip'].style.display='none';
//document.aascShipExecPackageOptionsForm.elements['rtnRMA'].style.display='none';
//document.aascShipExecPackageOptionsForm.elements['signatureOptionCheck'].style.display='none';
//document.aascShipExecPackageOptionsForm.elements['signatureOption1'].style.display='none';
//document.aascShipExecPackageOptionsForm.elements['signatureOption2'].style.display='none';
//document.aascShipExecPackageOptionsForm.elements['signatureOption3'].style.display='none';
//document.aascShipExecPackageOptionsForm.elements['signatureOption4'].style.display='none';
//document.aascShipExecPackageOptionsForm.elements['rtnDeclaredValue'].style.display='none';


    var  packCount=document.aascShipExecPackageOptionsForm.packCount.value;
//    alert("packCount:89:"+document.aascShipExecPackageOptionsForm.packCount.value);
    var totalShippedQuantity =0.0;
    var shippedQuantity=0.0; 
  
    var shipStatusFlag = window.opener.document.getElementById("shipmentStatusFlagID").value;
     if(window.opener.document.getElementById('LargePackageID'+packCount).value == "Y")
    {
    document.getElementById('LargePackage').checked=true;
     }
    else 
    {
    document.getElementById('LargePackage').checked=false;
    }
    
       if(window.opener.document.getElementById('AdditionalHandlingID'+packCount).value == "Y")
    {
    document.aascShipExecPackageOptionsForm.AdditionalHandling.checked=true;
     }
    else 
    {
    document.aascShipExecPackageOptionsForm.AdditionalHandling.checked=false;
    }
     
     document.aascShipExecPackageOptionsForm.returnDescription.value=window.opener.document.getElementById('returnDescriptionID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.rtnShipFromCompany.value=window.opener.document.getElementById('rtnShipFromCompanyID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.rtnShipToCompany.value=window.opener.document.getElementById('rtnShipToCompanyID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.rtnShipFromContact.value=window.opener.document.getElementById('rtnShipFromContactID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.rtnShipToContact.value=window.opener.document.getElementById('rtnShipToContactID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.rtnShipFromLine1.value=window.opener.document.getElementById('rtnShipFromLine1ID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.rtnShipToLine1.value=window.opener.document.getElementById('rtnShipToLine1ID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.rtnShipFromLine2.value= window.opener.document.getElementById('rtnShipFromLine2ID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.rtnShipToLine2.value=window.opener.document.getElementById('rtnShipToLine2ID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.rtnCountrySymbol.value=window.opener.document.getElementById('rtnCountrySymbolID'+packCount).value;  
        document.aascShipExecPackageOptionsForm.rtnShipFromCity.value=window.opener.document.getElementById('rtnShipFromCityID'+packCount).value;
    document.aascShipExecPackageOptionsForm.rtnShipFromSate.value=window.opener.document.getElementById('rtnShipFromSateID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.rtnShipFromZip.value=window.opener.document.getElementById('rtnShipFromZipID'+packCount).value;
    document.aascShipExecPackageOptionsForm.rtnShipToCity.value=window.opener.document.getElementById('rtnShipToCityID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.rtnShipToState.value=window.opener.document.getElementById('rtnShipToStateID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.rtnShipToZip.value=window.opener.document.getElementById('rtnShipToZipID'+packCount).value;  

    if((window.opener.document.getElementById('rtnShipFromPhoneID'+packCount).value)!="")
    {
       document.aascShipExecPackageOptionsForm.rtnShipFromPhone.value= window.opener.document.getElementById('rtnShipFromPhoneID'+packCount).value;
    }
    else{

        var rtnShipFromPhone=document.aascShipExecPackageOptionsForm.rtnShipFromPhone.value;
        if(rtnShipFromPhone == null || rtnShipFromPhone =="null" || rtnShipFromPhone =="")
        {
            var shipToPhoneMain=window.opener.document.getElementById('phoneNumberId').value;
            
            if(((shipToPhoneMain != null || shipToPhoneMain !="null" || shipToPhoneMain !="")) && window.opener.document.getElementById('trackingNumberID'+packCount).value=="")
            {
            document.aascShipExecPackageOptionsForm.rtnShipFromPhone.value=shipToPhoneMain;
            }
        }
    } 
//    var rtnDeclaredValue=document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value;
        
//    if(rtnDeclaredValue == null || rtnDeclaredValue =="null" || rtnDeclaredValue =="" || rtnDeclaredValue==0 || rtnDeclaredValue==0.0)
//    {
//         var packageDeclaredValueMain=window.opener.document.getElementById('packageDeclaredValueID'+packCount).value; 
//           
//         if((packageDeclaredValueMain != null || packageDeclaredValueMain !="null" || packageDeclaredValueMain !="") && window.opener.document.getElementById('trackingNumberID'+packCount).value=="")
//         {
//            document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value=packageDeclaredValueMain;
//         }
//    }
       
    document.aascShipExecPackageOptionsForm.rtnShipToPhone.value=window.opener.document.getElementById('rtnShipToPhoneID'+packCount).value;  
        
       
    if(window.opener.document.getElementById('rtnRMAID'+packCount).value=="null")
    {
        document.aascShipExecPackageOptionsForm.rtnRMA.value ="";
    }
    else
    {
        document.aascShipExecPackageOptionsForm.rtnRMA.value=window.opener.document.getElementById('rtnRMAID'+packCount).value; 
    }
    document.aascShipExecPackageOptionsForm.rtnTrackingNumber.value=window.opener.document.getElementById('rtnTrackingNumberID'+packCount).value;
    document.aascShipExecPackageOptionsForm.rtnShipmentCost.value=window.opener.document.getElementById('rtnShipmentCostID'+packCount).value;
 
    document.aascShipExecPackageOptionsForm.packageSaveCheck.value=window.opener.document.getElementById('packageSaveCheckID'+packCount).value;
    document.aascShipExecPackageOptionsForm.ajaxAfterShipDropOffType.value=window.opener.document.getElementById('rtnDropOfTypeID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.ajaxAfterShipPackaging.value=window.opener.document.getElementById('rtnPackageListID'+packCount).value;  
    document.aascShipExecPackageOptionsForm.ajaxAfterShipCarrAccNumber.value=window.opener.document.getElementById('rtnACNumberID'+packCount).value;  
    var packCount;
      packCount=document.aascShipExecPackageOptionsForm.packCount.value;
// alert("packCount:204:"+document.aascShipExecPackageOptionsForm.packCount.value);
      var pkging;
      var codType;
      var codFundsCode;   
      var codCurrCode;
      var delConfirm;      
      var length;
      var AdditionalHandling;
      var LargePackage;
        
      if(window.opener.document.getElementById('shipMethodId').disabled==true)
      {
       document.aascShipExecPackageOptionsForm.upsPackaging.disabled=true;
       document.aascShipExecPackageOptionsForm.upsCodCode.disabled=true;
       document.aascShipExecPackageOptionsForm.upsCodFundsCode.disabled=true;
       document.aascShipExecPackageOptionsForm.upsCodCurrCode.disabled=true;  
       document.aascShipExecPackageOptionsForm.upsDelConfirm.disabled=true;      
       document.aascShipExecPackageOptionsForm.upsCodCheckBox.disabled=true;
       document.aascShipExecPackageOptionsForm.upsCodAmt.readOnly=true;       
       document.aascShipExecPackageOptionsForm.PackageSurcharge.value=window.opener.document.getElementById('upsSurChargeID'+packCount).value; 
       document.aascShipExecPackageOptionsForm.AdditionalHandling.disabled=true;
       document.aascShipExecPackageOptionsForm.LargePackage.disabled=true;
      }
      else
      {              
         if(window.opener.document.getElementById('chCODID'+packCount).value!="Y")
         {
             document.aascShipExecPackageOptionsForm.codTempFlag.value="N";
             document.aascShipExecPackageOptionsForm.upsCodCode.disabled = true;
             document.aascShipExecPackageOptionsForm.upsCodFundsCode.disabled = true;
             document.aascShipExecPackageOptionsForm.upsCodCurrCode.disabled =true;
             document.aascShipExecPackageOptionsForm.upsCodAmt.readOnly=true;
             document.aascShipExecPackageOptionsForm.upsCodCheckBox.checked=false;              
         }
         else if(window.opener.document.getElementById('chCODID'+packCount).value=="Y")
         {
             document.aascShipExecPackageOptionsForm.codTempFlag.value="Y";
             document.aascShipExecPackageOptionsForm.upsCodCode.disabled = false;
             document.aascShipExecPackageOptionsForm.upsCodFundsCode.disabled = false;
             document.aascShipExecPackageOptionsForm.upsCodCurrCode.disabled =false;
             document.aascShipExecPackageOptionsForm.upsCodAmt.readOnly=false;
             document.aascShipExecPackageOptionsForm.upsCodCheckBox.checked=true;        
         }
      }
     
       
      if(window.opener.document.getElementById('chCODID'+packCount).value=="Y")
      {
         codType=window.opener.document.getElementById('codTypeID'+packCount).value;
         codFundsCode=window.opener.document.getElementById('codFundsCodeID'+packCount).value;
         codCurrCode=window.opener.document.getElementById('codCurrCodeID'+packCount).value;                  
         
         length=document.aascShipExecPackageOptionsForm.upsCodCode.length;
         for(var i=0; i<length; i++)
         {
            if(document.aascShipExecPackageOptionsForm.upsCodCode.options[i].value==codType)
            {                     
             document.aascShipExecPackageOptionsForm.upsCodCode.options[i].selected=true;                     
            }
         }
         
         length=document.aascShipExecPackageOptionsForm.upsCodFundsCode.length;
         for(var i=0; i<length; i++)
         {
            if(document.aascShipExecPackageOptionsForm.upsCodFundsCode.options[i].value==codFundsCode)
            {                  
               document.aascShipExecPackageOptionsForm.upsCodFundsCode.options[i].selected=true;                  
            }
         }
                 
         length=document.aascShipExecPackageOptionsForm.upsCodCurrCode.length;
         for(var i=0; i<length; i++)
         {
            if(document.aascShipExecPackageOptionsForm.upsCodCurrCode.options[i].value==codCurrCode)
            {                  
              document.aascShipExecPackageOptionsForm.upsCodCurrCode.options[i].selected=true;                   
            }
         }
      }
       
      pkging=window.opener.document.getElementById('pkgingID'+packCount).value;       
      delConfirm=window.opener.document.getElementById('delConfirmID'+packCount).value;    
       LargePackage= window.opener.document.getElementById('LargePackageID'+packCount).value;    
       AdditionalHandling = window.opener.document.getElementById('AdditionalHandlingID'+packCount).value;    
                    
      length=document.aascShipExecPackageOptionsForm.upsPackaging.length;
      for(var i=0; i<length; i++)
      {
        if(document.aascShipExecPackageOptionsForm.upsPackaging.options[i].value==pkging)
        {
           document.aascShipExecPackageOptionsForm.upsPackaging.options[i].selected=true;
        }
      }
        
      length=document.aascShipExecPackageOptionsForm.upsDelConfirm.length;
      for(var i=0; i<length; i++)
      {
        if(document.aascShipExecPackageOptionsForm.upsDelConfirm.options[i].value==delConfirm)
        {
           document.aascShipExecPackageOptionsForm.upsDelConfirm.options[i].selected=true;
        }
      }
      
      document.aascShipExecPackageOptionsForm.PackageSurcharge.readOnly=true;  
      document.aascShipExecPackageOptionsForm.upsCodAmt.value=window.opener.document.getElementById('codAmtID'+packCount).value;
    var phone = window.opener.document.getElementById('halPhoneID'+packCount).value;
    var line1=window.opener.document.getElementById('halAddrLine1ID'+packCount).value;
    if(line1=="null") line1="";
         
    var line2=window.opener.document.getElementById('halAddrLine2ID'+packCount).value;
         
    var city=window.opener.document.getElementById('halCityID'+packCount).value;
         
    var state=window.opener.document.getElementById('halStateID'+packCount).value;
    var postalCode=window.opener.document.getElementById('halZipID'+packCount).value;
        
    var state=window.opener.document.getElementById('halStateID'+packCount).value;
    var postalCode=window.opener.document.getElementById('halZipID'+packCount).value;
    var hal=window.opener.document.getElementById('holdAtLocationID'+packCount).value;
          
    if(hal=="Y"){
         document.aascShipExecPackageOptionsForm.holdAtLocation.checked =true;
         document.aascShipExecPackageOptionsForm.holdAtLocation.value ="Y";
         document.aascShipExecPackageOptionsForm.halPhone.value=phone;
         document.aascShipExecPackageOptionsForm.halAddrLine1.value=line1;
         document.aascShipExecPackageOptionsForm.halAddrLine2.value=line2;
         document.aascShipExecPackageOptionsForm.halCity.value=city;
         document.aascShipExecPackageOptionsForm.halState.value=state;
         document.aascShipExecPackageOptionsForm.halZip.value=postalCode;
     }
    else{
    document.aascShipExecPackageOptionsForm.holdAtLocation.checked =false;
    }
     var packageHalZip = window.opener.document.getElementById('halZipID'+packCount).value;
     var packageTrackingNo = window.opener.document.getElementById('trackingNumberID'+packCount).value;
     var flagShipStr = window.opener.document.getElementById('flagShip1').value;
     if((packageHalZip != '' && flagShipStr == "Y")|| window.opener.document.getElementById('holdAtLocationID'+packCount).value == 'N')
     { 
   
         if(flagShipStr == 'N') 
         {         
            holdatLocationfn();
         }
         if(packageTrackingNo != '')
         {
             
             document.aascShipExecPackageOptionsForm.holdAtLocation.disabled = true;
             document.aascShipExecPackageOptionsForm.halPhone.disabled = true;
             document.aascShipExecPackageOptionsForm.halAddrLine1.disabled = true;
             document.aascShipExecPackageOptionsForm.halAddrLine2.disabled = true;
             document.aascShipExecPackageOptionsForm.halCity.disabled = true;
             document.aascShipExecPackageOptionsForm.halState.disabled = true;
             document.aascShipExecPackageOptionsForm.halZip.disabled = true;
         
         }
     }
     else
     {
        holdatLocationfn();
     }
     
     //=================================================
     
//    var dryIce=window.opener.document.getElementById('chDryIceID'+packCount).value;
//    var dryIceWeight=window.opener.document.getElementById('dryIceWeightID'+packCount).value;
//    var dryIceUnits=window.opener.document.getElementById('dryIceUnitsID'+packCount).value;
    
//    if(window.opener.document.getElementById('flagShip1').value !='Y'){
//        if(dryIce=="Y")
//        {
//           document.aascShipExecPackageOptionsForm.chDryIce.checked =true;
//           document.aascShipExecPackageOptionsForm.chDryIce.value ="Y";
//           document.aascShipExecPackageOptionsForm.dryIceWeight.value=dryIceWeight;
//           document.aascShipExecPackageOptionsForm.dryIceUnits.value=dryIceUnits;
//        }
//        else
//        {
//           document.aascShipExecPackageOptionsForm.chDryIce.checked =false;
//           document.aascShipExecPackageOptionsForm.chDryIce.value ="N";
//           document.aascShipExecPackageOptionsForm.dryIceWeight.disabled=true;
//           document.aascShipExecPackageOptionsForm.dryIceUnits.disabled = true;
//        }
//    }else{
//        if(dryIce=="Y")
//        {
//           document.aascShipExecPackageOptionsForm.chDryIce.checked =true;
//           document.aascShipExecPackageOptionsForm.chDryIce.value ="Y";
//           document.aascShipExecPackageOptionsForm.chDryIce.disabled = true;
//           document.aascShipExecPackageOptionsForm.dryIceWeight.value=dryIceWeight;
//           document.aascShipExecPackageOptionsForm.dryIceUnits.value=dryIceUnits;
//           document.aascShipExecPackageOptionsForm.dryIceWeight.disabled=true;
//           document.aascShipExecPackageOptionsForm.dryIceUnits.disabled = true;
//        }
//        else
//        {
//           document.aascShipExecPackageOptionsForm.chDryIce.checked =false;
//           document.aascShipExecPackageOptionsForm.chDryIce.value ="N";
//           document.aascShipExecPackageOptionsForm.chDryIce.disabled = true;
//           document.aascShipExecPackageOptionsForm.dryIceWeight.disabled=true;
//           document.aascShipExecPackageOptionsForm.dryIceUnits.disabled = true;
//        }
//    }
//    if(packageTrackingNo != '')
//    {
//    
//        document.aascShipExecPackageOptionsForm.dryIceWeight.value=dryIceWeight;
//        document.aascShipExecPackageOptionsForm.dryIceUnits.value=dryIceUnits;
//    
//    }
     
     //=================================================
     
     var hazMatFlag = window.opener.document.getElementById('HazMatFlagID'+packCount).value;
     var hazMatCharges = window.opener.document.getElementById('HazMatChargesID'+packCount).value;
     var hazMatType = window.opener.document.getElementById('HazMatTypeID'+packCount).value;
     var hazMatClass = window.opener.document.getElementById('HazMatClassID'+packCount).value;
      
     var hazMatIdNo = window.opener.document.getElementById('HazMatIdNoID'+packCount).value;
     var hazMatPkgGroup = window.opener.document.getElementById('HazMatPkgGroupID'+packCount).value;
     var hazMatDOTLabel = window.opener.document.getElementById('HazMatDOTLabelID'+packCount).value;
     if(hazMatDOTLabel=="null")
       hazMatDOTLabel="";
     
     var hazMatEmerContactNo = window.opener.document.getElementById('HazMatEmerContactNoID'+packCount).value;
     if(hazMatEmerContactNo=="null")
        hazMatEmerContactNo="";
     var hazMatEmerContactName = window.opener.document.getElementById('HazMatEmerContactNameID'+packCount).value;
     if(hazMatEmerContactName=="null")
        hazMatEmerContactName="";
     
     var hazardousMaterialId = window.opener.document.getElementById('HazMatIdID'+packCount).value;
     globalHazMatPkgGrpLoad =hazMatPkgGroup;

     var hazmatPkgCnt = window.opener.document.getElementById('HazMatPackagingCntID'+packCount).value;
     var hazmatPkgUnits = window.opener.document.getElementById('HazMatPackagingUnitsID'+packCount).value;
     var hazmatTechnicalName = window.opener.document.getElementById('HazMatTechnicalNameID'+packCount).value;
     var hazmatSignatureName = window.opener.document.getElementById('HazMatSignatureNameID'+packCount).value;
 var HazMatPackInstructions = window.opener.document.getElementById('HazMatPackInstructionsID'+packCount).value;
     if(hazMatFlag == 'Y')
     {
          var hazMatQty = window.opener.document.getElementById('HazMatQtyID'+packCount).value;
          var hazMatUnit = window.opener.document.getElementById('HazMatUnitID'+packCount).value;
          document.aascShipExecPackageOptionsForm.HazMatFlag.checked = true;
          document.aascShipExecPackageOptionsForm.HazardousMaterialCharges.value = hazMatCharges;
          document.aascShipExecPackageOptionsForm.HazardousMaterialType.value = hazMatType;
          document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.value = hazMatQty;
          document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.value = hazMatUnit;
          document.aascShipExecPackageOptionsForm.HazMatPackInstructions.value = HazMatPackInstructions;
            document.getElementById('HazardousMaterialClass').options[0] = new Option(hazMatClass,hazMatClass,true,true);
          document.aascShipExecPackageOptionsForm.HazardousMaterialId.value = hazardousMaterialId;
         document.aascShipExecPackageOptionsForm.HazardousMaterialPkgGroup.value = hazMatPkgGroup;
          document.aascShipExecPackageOptionsForm.HazMatDOTLabelType.value = hazMatDOTLabel;
          document.aascShipExecPackageOptionsForm.HazMatIdentificationNo.value = hazMatIdNo;
    
          document.aascShipExecPackageOptionsForm.HazMatEmergencyContactNo.value = hazMatEmerContactNo;
          document.aascShipExecPackageOptionsForm.HazMatEmergencyContactName.value = hazMatEmerContactName;

          document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value = hazmatPkgCnt;
          document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.value = hazmatPkgUnits;
          document.aascShipExecPackageOptionsForm.HazMatTechnicalName.value = hazmatTechnicalName;
          document.aascShipExecPackageOptionsForm.HazMatSignatureName.value = hazmatSignatureName;
     }
     else{
      document.aascShipExecPackageOptionsForm.HazMatFlag.checked = false;
     }
     if((hazMatType != '' && flagShipStr == "Y")|| hazMatFlag == 'N')
     {
        if(flagShipStr == 'N')
        {
           hazardousMaterialFn();
        }
       
        if(packageTrackingNo != '')
        {
           var optionValue=hazMatClass;      
           var  optionValuePkgGroup=hazMatPkgGroup;
           document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialClass.options1 = new Option(optionValue,optionValue); 
           document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialPkgGroup.options1 = new Option(optionValuePkgGroup,optionValuePkgGroup);  

           document.aascShipExecPackageOptionsForm.HazMatFlag.disabled = true;
           document.aascShipExecPackageOptionsForm.HazardousMaterialType.disabled = true;
           document.aascShipExecPackageOptionsForm.HazardousMaterialClass.disabled = true;
           
           document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.disabled = true;
           document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.disabled = true;
         
           document.aascShipExecPackageOptionsForm.HazardousMaterialId.disabled = true;
           document.aascShipExecPackageOptionsForm.HazMatIdentificationNo.disabled = true;
           document.aascShipExecPackageOptionsForm.HazMatDOTLabelType.disabled = true;
           document.aascShipExecPackageOptionsForm.HazMatEmergencyContactNo.disabled = true;
           document.aascShipExecPackageOptionsForm.HazMatEmergencyContactName.disabled = true;
           document.aascShipExecPackageOptionsForm.HazMatPackInstructions.disabled = true;
           document.aascShipExecPackageOptionsForm.HazardousMaterialPkgGroup.disabled = true;

           document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.disabled = true;
           document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.disabled = true;
             
           document.aascShipExecPackageOptionsForm.HazMatTechnicalName.disabled = true;
           document.aascShipExecPackageOptionsForm.HazMatSignatureName.disabled = true;
        }else{
            globalvar=hazMatClass;
            globalvar2 = hazMatPkgGroup;
    
            getHazMatClass();
            getHazMatPkgGroup();
           
            hazardousMaterialFn();
        }
     }
     else{
       globalvar=hazMatClass;
       globalvar2 = hazMatPkgGroup;
       getHazMatClass();
       
       var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;
       var carrierCode = str.substring(0,3)
       if(carrierCode=="100")
       {
         getHazMatPkgGroup();
       }
       hazardousMaterialFn();
     }
     var packageTrackingNumber=window.opener.document.getElementById('trackingNumberID'+packCount).value;
     if(packageTrackingNumber!='')
     {
      document.aascShipExecPackageOptionsForm.upsCodCheckBox.disabled =true;
      document.aascShipExecPackageOptionsForm.upsCodAmt.disabled=true;
     }
     if(window.opener.document.getElementById('shipMethodId').disabled==true)
     {
        var packageTrackingNumber=window.opener.document.getElementById('trackingNumberID'+packCount).value;
//        document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=window.opener.document.getElementById('signatureOptionID'+packCount).value;

        document.aascShipExecPackageOptionsForm.returnShipment.value=window.opener.document.getElementById('returnShipmentID'+packCount).value;
 
        document.aascShipExecPackageOptionsForm.PackageSurcharge.value=window.opener.document.getElementById('PackageSurchargeID'+packCount).value;
      
        document.aascShipExecPackageOptionsForm.PackageShipmentCost.value=window.opener.document.getElementById('PackageShipmentCostID'+packCount).value;

        if(document.aascShipExecPackageOptionsForm.returnShipment.value=="NONRETURN" || document.aascShipExecPackageOptionsForm.returnShipment.value=="" || document.aascShipExecPackageOptionsForm.returnShipment.value==null)
        {
           document.aascShipExecPackageOptionsForm.returnShipment.checked=false;
        }

        if(document.aascShipExecPackageOptionsForm.returnShipment.value=="PRINTRETURNLABEL")
        {
           document.aascShipExecPackageOptionsForm.returnShipment.checked=true;
        }
//        if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="NONE" || document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="" || document.aascShipExecPackageOptionsForm.signatureOptionCheck.value==null)
//        {
//             document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked=false
//             document.aascShipExecPackageOptionsForm.signatureOption1.disabled = true;
//             document.aascShipExecPackageOptionsForm.signatureOption2.disabled = true;
//             document.aascShipExecPackageOptionsForm.signatureOption1.checked = false;
//             document.aascShipExecPackageOptionsForm.signatureOption2.checked = false;
//             document.aascShipExecPackageOptionsForm.signatureOption3.disabled = true;
//             document.aascShipExecPackageOptionsForm.signatureOption4.disabled = true;
//             document.aascShipExecPackageOptionsForm.signatureOption3.checked = false;
//             document.aascShipExecPackageOptionsForm.signatureOption4.checked = false;
//        }
//        if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.value!="NONE" && document.aascShipExecPackageOptionsForm.signatureOptionCheck.value!="" && document.aascShipExecPackageOptionsForm.signatureOptionCheck.value!=null)
//        {
//             document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked=true;
//             document.aascShipExecPackageOptionsForm.signatureOption1.disabled = false;
//             document.aascShipExecPackageOptionsForm.signatureOption2.disabled = false;
//             document.aascShipExecPackageOptionsForm.signatureOption3.disabled = false;
//             document.aascShipExecPackageOptionsForm.signatureOption4.disabled = false;
//        }
//        if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="DIRECT")
//        {
//          document.aascShipExecPackageOptionsForm.signatureOption1.checked=true;
//          document.aascShipExecPackageOptionsForm.signatureOption2.checked=false;
//          document.aascShipExecPackageOptionsForm.signatureOption3.checked=false;
//          document.aascShipExecPackageOptionsForm.signatureOption4.checked=false;
//        }

//        if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="ADULT")
//        {
//          document.aascShipExecPackageOptionsForm.signatureOption2.checked=true;
//          document.aascShipExecPackageOptionsForm.signatureOption1.checked=false;
//          document.aascShipExecPackageOptionsForm.signatureOption3.checked=false;      
//          document.aascShipExecPackageOptionsForm.signatureOption4.checked=false;
//        }
//        if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="DELIVERWITHOUTSIGNATURE")
//        {
//          document.aascShipExecPackageOptionsForm.signatureOption1.checked=false;
//          document.aascShipExecPackageOptionsForm.signatureOption2.checked=false;
//          document.aascShipExecPackageOptionsForm.signatureOption3.checked=true;
//          document.aascShipExecPackageOptionsForm.signatureOption4.checked=false;
//        }
      
//        if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="INDIRECT")
//        {
//          document.aascShipExecPackageOptionsForm.signatureOption1.checked=false;
//          document.aascShipExecPackageOptionsForm.signatureOption2.checked=false;
//          document.aascShipExecPackageOptionsForm.signatureOption3.checked=false;
//          document.aascShipExecPackageOptionsForm.signatureOption4.checked=true;
//        }  
        if((packageTrackingNumber==null || packageTrackingNumber=="") && ( shipStatusFlag!="B" && shipStatusFlag !="P"))
        {
            if(totalShippedQuantity==0)
            {
//                document.aascShipExecPackageOptionsForm.signatureOptionCheck.disabled=true;
//                document.aascShipExecPackageOptionsForm.signatureOption1.disabled = true;
//                document.aascShipExecPackageOptionsForm.signatureOption2.disabled = true;
//                document.aascShipExecPackageOptionsForm.signatureOption3.disabled = true;
//                document.aascShipExecPackageOptionsForm.signatureOption4.disabled = true;
                document.aascShipExecPackageOptionsForm.returnShipment.disabled=true; 
                document.aascShipExecPackageOptionsForm.upsCodCheckBox.disabled =true;
                document.aascShipExecPackageOptionsForm.upsCodAmt.disabled=true;
            }
            else
            {
//              document.aascShipExecPackageOptionsForm.signatureOptionCheck.disabled=false;
//              document.aascShipExecPackageOptionsForm.signatureOption1.disabled = false;
//              document.aascShipExecPackageOptionsForm.signatureOption2.disabled = false;
//              document.aascShipExecPackageOptionsForm.signatureOption3.disabled = false;
//              document.aascShipExecPackageOptionsForm.signatureOption4.disabled = false;

              document.aascShipExecPackageOptionsForm.returnShipment.disabled=false; 
              document.aascShipExecPackageOptionsForm.upsCodCheckBox.disabled =false;
              document.aascShipExecPackageOptionsForm.upsCodAmt.disabled=false;
           }
        }
        else
        {
//          document.aascShipExecPackageOptionsForm.signatureOptionCheck.disabled=true;
//          document.aascShipExecPackageOptionsForm.signatureOption1.disabled = true;
//          document.aascShipExecPackageOptionsForm.signatureOption2.disabled = true;
//          document.aascShipExecPackageOptionsForm.signatureOption3.disabled = true;
//          document.aascShipExecPackageOptionsForm.signatureOption4.disabled = true;
        
          document.aascShipExecPackageOptionsForm.returnShipment.disabled=true; 
          document.aascShipExecPackageOptionsForm.upsCodCheckBox.disabled =true;
          document.aascShipExecPackageOptionsForm.upsCodAmt.disabled=true;
          document.aascShipExecPackageOptionsForm.HazMatFlag.disabled=true;  // Added code to fix #2747
          document.aascShipExecPackageOptionsForm.holdAtLocation.disabled=true;     // Added code to fix #2747
           
//          document.aascShipExecPackageOptionsForm.save.src="buttons/aascSaveOff1.png"; //document.serialNumberForm.savebutton.src="buttons/aascSaveOff1.png";
        }

        rmaCheck();
    }
    else 
    {
//         document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=window.opener.document.getElementById('signatureOptionID'+packCount).value;
         document.aascShipExecPackageOptionsForm.returnShipment.value=window.opener.document.getElementById('returnShipmentID'+packCount).value;
         document.aascShipExecPackageOptionsForm.PackageSurcharge.value=window.opener.document.getElementById('PackageSurchargeID'+packCount).value;
         if(document.aascShipExecPackageOptionsForm.returnShipment.value=="NONRETURN" || document.aascShipExecPackageOptionsForm.returnShipment.value=="" || document.aascShipExecPackageOptionsForm.returnShipment.value==null)
         {
             document.aascShipExecPackageOptionsForm.returnShipment.checked=false;
         }

         if(document.aascShipExecPackageOptionsForm.returnShipment.value=="PRINTRETURNLABEL")
         {
               document.aascShipExecPackageOptionsForm.returnShipment.checked=true;
         }
//         if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="NONE" || document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="" || document.aascShipExecPackageOptionsForm.signatureOptionCheck.value==null)
//         {
//             document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked=false;
//             document.aascShipExecPackageOptionsForm.signatureOption1.disabled = true;
//             document.aascShipExecPackageOptionsForm.signatureOption2.disabled = true;
//             document.aascShipExecPackageOptionsForm.signatureOption1.checked = false;
//             document.aascShipExecPackageOptionsForm.signatureOption2.checked = false;
//             document.aascShipExecPackageOptionsForm.signatureOption3.disabled = true;
//             document.aascShipExecPackageOptionsForm.signatureOption4.disabled = true;
//             document.aascShipExecPackageOptionsForm.signatureOption3.checked = false;
//             document.aascShipExecPackageOptionsForm.signatureOption4.checked = false;
//         }

//         if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.value!="NONE" && document.aascShipExecPackageOptionsForm.signatureOptionCheck.value!="" && document.aascShipExecPackageOptionsForm.signatureOptionCheck.value!=null)
//         {
//             document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked=true;
//             document.aascShipExecPackageOptionsForm.signatureOption1.disabled = false;
//             document.aascShipExecPackageOptionsForm.signatureOption2.disabled = false;
//             document.aascShipExecPackageOptionsForm.signatureOption3.disabled = false;
//             document.aascShipExecPackageOptionsForm.signatureOption4.disabled = false;
//             if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="DIRECT")
//             {
//                  document.aascShipExecPackageOptionsForm.signatureOption1.checked=true;
//                  document.aascShipExecPackageOptionsForm.signatureOption2.checked=false;
//                  document.aascShipExecPackageOptionsForm.signatureOption3.checked=false;
//                  document.aascShipExecPackageOptionsForm.signatureOption4.checked=false;
//             }
//             if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="ADULT")
//             {        
//                document.aascShipExecPackageOptionsForm.signatureOption2.checked=true;
//                document.aascShipExecPackageOptionsForm.signatureOption1.checked=false;
//                document.aascShipExecPackageOptionsForm.signatureOption3.checked=false;
//                document.aascShipExecPackageOptionsForm.signatureOption4.checked=false;
//             }
//             if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="DELIVERWITHOUTSIGNATURE")
//             {
//                  document.aascShipExecPackageOptionsForm.signatureOption3.checked=true;
//                  document.aascShipExecPackageOptionsForm.signatureOption1.checked=false;
//                  document.aascShipExecPackageOptionsForm.signatureOption2.checked=false;
//                  document.aascShipExecPackageOptionsForm.signatureOption4.checked=false;
//             }      
//             if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="INDIRECT")
//             {
//                  document.aascShipExecPackageOptionsForm.signatureOption4.checked=true;
//                  document.aascShipExecPackageOptionsForm.signatureOption1.checked=false;
//                  document.aascShipExecPackageOptionsForm.signatureOption3.checked=false;
//                  document.aascShipExecPackageOptionsForm.signatureOption2.checked=false;
//             }
//          }
          rmaCheck();
    }
    document.aascShipExecPackageOptionsForm.rtnShipFromCompany.value=window.opener.document.getElementById('customerName').options[window.opener.document.getElementById('customerName').selectedIndex].value;
    document.aascShipExecPackageOptionsForm.rtnShipFromContact.value=window.opener.document.getElementById('contactNameId').value;
    if(window.opener.document.getElementById('shipToAddressId').value!="")
    {
    document.aascShipExecPackageOptionsForm.rtnShipFromLine1.value=window.opener.document.getElementById('shipToAddressId').value;
    }
    document.aascShipExecPackageOptionsForm.rtnShipFromLine2.value=window.opener.document.getElementById('shipToAddrLine2Id').value;
    document.aascShipExecPackageOptionsForm.rtnCountrySymbol.value=window.opener.document.getElementById('country').value;
    document.aascShipExecPackageOptionsForm.rtnShipFromCity.value=window.opener.document.getElementById('city').value;
    document.aascShipExecPackageOptionsForm.rtnShipFromSate.value=window.opener.document.getElementById('state').value;
    document.aascShipExecPackageOptionsForm.rtnShipFromZip.value=window.opener.document.getElementById('zip').value;
    var rtnShipToCompanyFromPage=window.opener.document.getElementById('companyNameId').value;
//    alert("rtnShipToCompanyFromPage::"+rtnShipToCompanyFromPage);
//    var returnShipToCompanyName=rtnShipToCompanyFromPage.substring(0,rtnShipToCompanyFromPage.indexOf('*'));

    document.aascShipExecPackageOptionsForm.rtnShipFromPhone.value = window.opener.document.getElementById('phoneNumberId').value;      //  Added by suman G for #2535
    document.aascShipExecPackageOptionsForm.rtnShipToCompany.value=rtnShipToCompanyFromPage;
    document.aascShipExecPackageOptionsForm.rtnShipToContact.value=window.opener.document.getElementById('shipFromContactName').value;

    document.aascShipExecPackageOptionsForm.rtnShipToLine1.value=window.opener.document.getElementById('shipFromAddressLine1').value;
    document.aascShipExecPackageOptionsForm.rtnShipToLine2.value=window.opener.document.getElementById('shipFromAddressLine2').value;
    document.aascShipExecPackageOptionsForm.rtnShipToCity.value=window.opener.document.getElementById('shipFromCity').value;
    document.aascShipExecPackageOptionsForm.rtnShipToState.value=window.opener.document.getElementById('shipFromState').value;
    document.aascShipExecPackageOptionsForm.rtnShipToZip.value=window.opener.document.getElementById('shipFromPostalCode').value;
    document.aascShipExecPackageOptionsForm.rtnShipToPhone.value=window.opener.document.getElementById('shipFromPhoneNumber1').value;
    
    var rtnShipMethodvalLen=window.opener.document.getElementById('rtnShipMethodID'+packCount).value.length;
    var rtnTrackingNumber = window.opener.document.getElementById('rtnTrackingNumberID'+packCount).value;
    if(rtnTrackingNumber == "" || rtnTrackingNumber == null || rtnTrackingNumber == "null")
    {
    document.aascShipExecPackageOptionsForm.rtnTrackingNumber.value="";
    }
    if(packageTrackingNumber!="" )
    {
      getCcCsl('3');// after ship 
    }
    else
    {
      var testShipMethodvalue=document.aascShipExecPackageOptionsForm.rtnShipMethod.value;  
      var testForFedexUps=testShipMethodvalue.substring(0,testShipMethodvalue.indexOf("|"));
      if(document.aascShipExecPackageOptionsForm.packageSaveCheck.value=="Y")
      {    
        getCcCsl('3');// after ship
      }
      else
      {
          getCcCsl('1'); // on load
      }
    }
    var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;
    if(document.aascShipExecPackageOptionsForm.HazMatFlag.checked == true)
    {
        if(str.substring(0,3)=="100")
        {        
         document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.disabled = false;
         document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.disabled = false;
        }
        else
        {
             document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.disabled = true;
             document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.disabled = true;
        }
        if(packageTrackingNo != '')
        {
             document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.disabled = true;
             document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.disabled = true;
        }
    }
    if(window.opener.document.getElementById('flagShip1').value=='Y')
    {
      //alert('here');
      rtnShipMethod=window.opener.document.getElementById('rtnShipMethodID'+packCount).value;
      rtnShipMethod=rtnShipMethod.substring(0,rtnShipMethod.length);
//      alert("646::"+rtnShipMethod);
      document.aascShipExecPackageOptionsForm.rtnShipMethod.options[document.aascShipExecPackageOptionsForm.rtnShipMethod.selectedIndex].text=rtnShipMethod;
    }
    
    if(window.opener.document.getElementById('flagShip1').value != 'Y'){
//        var button = '<a href="#" onclick="return saveDetails();" ><img src="buttons/aascSave1.png" name="save" id="saveId1" alt ="" border="0" /></a>'+ '&nbsp;' +
//                    '<a href="#" onclick="javascript:loadPackageOptions()"   ><img src="buttons/aascCancel1.png" name="cancel" id="cancelId1"  alt="" /></a>'+ '&nbsp;' +
//                    '<a href="#" onclick="javascript:window.close()" ><img src="buttons/aascClose1.png" name="close" id="closeId1" border="0" alt="" /></a>';
        var button1 = '<button class="btn btn-success" name="Save1" id="saveId1" onclick="return saveDetails();" > Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>'+ '&nbsp;' + '<button class="btn btn-warning" name="cancel1" id="cancelId1" onclick="javascript:loadPackageOptions()" > Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>'+ '&nbsp;' + '<button class="btn btn-danger" name="close1" id="closeId1" onclick="javascript:window.close()"> Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>';

        var button2 = '<button class="btn btn-success" name="Save2" id="saveId2" onclick="return saveDetails();" > Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>'+ '&nbsp;' + '<button class="btn btn-warning" name="cancel2" id="cancelId2" onclick="javascript:loadPackageOptions()" > Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>'+ '&nbsp;' + '<button class="btn btn-danger" name="close2" id="closeId2" onclick="javascript:window.close()"> Close <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>';
        document.getElementById("buttonDiv").innerHTML = button1;
        document.getElementById("buttonDiv2").innerHTML = button2;
        
    }
    
}

function checkBoxFunc()
{
    if(document.aascShipExecPackageOptionsForm.upsCodCheckBox.checked==true)
    {
     document.aascShipExecPackageOptionsForm.codTempFlag.value="Y";
     document.aascShipExecPackageOptionsForm.upsCodCode.disabled = false;
     document.aascShipExecPackageOptionsForm.upsCodFundsCode.disabled = false;
     document.aascShipExecPackageOptionsForm.upsCodCurrCode.disabled =false;     
     document.aascShipExecPackageOptionsForm.upsCodAmt.readOnly=false;
     document.aascShipExecPackageOptionsForm.upsCodAmt.focus();
     var length=parseInt(document.aascShipExecPackageOptionsForm.upsDelConfirm.length);
     for(i=0; i<length; i++)
     {
        if(document.aascShipExecPackageOptionsForm.upsDelConfirm.options[i].value=="3")
        {
           document.aascShipExecPackageOptionsForm.upsDelConfirm.options[i].selected=true;
        }
     }
    }
    else
    {
     document.aascShipExecPackageOptionsForm.codTempFlag.value="N";
     document.aascShipExecPackageOptionsForm.upsCodCode.disabled = true;
     document.aascShipExecPackageOptionsForm.upsCodFundsCode.disabled = true;
     document.aascShipExecPackageOptionsForm.upsCodCurrCode.disabled =true;     
     document.aascShipExecPackageOptionsForm.upsCodAmt.readOnly=true;
     document.aascShipExecPackageOptionsForm.upsCodAmt.value="";
     
     var length=parseInt(document.aascShipExecPackageOptionsForm.upsDelConfirm.length);
   
     for(i=0; i<length; i++)
     {
       if(document.aascShipExecPackageOptionsForm.upsDelConfirm.options[i].value=="NA")
       {
         var adultIndex=i;
         document.aascShipExecPackageOptionsForm.upsDelConfirm.options[i].selected=true;
       }
     }
    }
    
 
    
}//end of the function


function checkupsCodAmt()
{
var upsCodAmt=document.aascShipExecPackageOptionsForm.upsCodAmt.value;
var codFlag=document.aascShipExecPackageOptionsForm.codTempFlag.value;

  if(isNaN(upsCodAmt))
  {
       alert('Please Enter Only Digits In COD Amount Field.');
       document.aascShipExecPackageOptionsForm.upsCodAmt.focus();
       return false;
  }
  
  if(codFlag == "Y" && upsCodAmt<1)
  {
      alert("COD Amount Should be Greater than or Equal to 1");
      document.aascShipExecPackageOptionsForm.upsCodAmt.focus();
      return false;
      
  }
  
  if(codFlag=="Y")
  {
     
     var length=parseInt(document.aascShipExecPackageOptionsForm.upsDelConfirm.length);
     for(i=0; i<length; i++)
     {
      if(document.aascShipExecPackageOptionsForm.upsDelConfirm.options[i].value=="3")
      {
       var adultIndex=i;
       document.aascShipExecPackageOptionsForm.upsDelConfirm.options[i].selected=true;
      }
     }
     
     if(upsCodAmt=="" || upsCodAmt==null)
     {
      alert('Please Enter A Value In COD Amount Field.');
      document.aascShipExecPackageOptionsForm.upsCodAmt.focus();
      return false;
     }
  }
}


function chkDeliveryConfirmation()
{
 var codFlag=document.aascShipExecPackageOptionsForm.codTempFlag.value;
 var delConfirm=document.aascShipExecPackageOptionsForm.upsDelConfirm.value; 
 delConfirm=parseInt(delConfirm);
 
   if(delConfirm!="3" && codFlag=="Y")
    {
     alert("Please Select --Adult Signature Required-- For Delivery Confirmation.");
     document.aascShipExecPackageOptionsForm.upsDelConfirm.focus();
     return false;
    }
}

function savePackageOptionsDetails()
{
var length;
var packageCount;
var checkBoxVal;
var imgSrcVal;

  if(window.opener.document.getElementById('shipMethodId').disabled!=true)
  {   
    packageCount=document.aascShipExecPackageOptionsForm.packCount.value;
//     alert("packCount:916:"+document.aascShipExecPackageOptionsForm.packCount.value);
    checkBoxVal=document.aascShipExecPackageOptionsForm.codTempFlag.value;
    var AdditionalHandling = document.aascShipExecPackageOptionsForm.AdditionalHandling.value;
    var LargePackage = document.aascShipExecPackageOptionsForm.LargePackage.value;
    var upsPackaging=document.aascShipExecPackageOptionsForm.upsPackaging.options[document.aascShipExecPackageOptionsForm.upsPackaging.selectedIndex].value;    
    var upsDelConfirm=document.aascShipExecPackageOptionsForm.upsDelConfirm.options[document.aascShipExecPackageOptionsForm.upsDelConfirm.selectedIndex].value;
    length=parseInt(document.aascShipExecPackageOptionsForm.upsPackaging.length);
    for(var i=0; i<length; i++)
    {
       if(document.aascShipExecPackageOptionsForm.upsPackaging.options[i].value==upsPackaging)
       {
         document.aascShipExecPackageOptionsForm.upsPackaging.options[i].selected=true;
         window.opener.document.getElementById('pkgingID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsPackaging.options[document.aascShipExecPackageOptionsForm.upsPackaging.selectedIndex].value;
       }
    }
       
       window.opener.document.getElementById('LargePackageID'+packageCount).value=document.aascShipExecPackageOptionsForm.LargePackage.value;
       window.opener.document.getElementById('AdditionalHandlingID'+packageCount).value=document.aascShipExecPackageOptionsForm.AdditionalHandling.value;
       
    if(chkDeliveryConfirmation()==false)
    {
      document.aascShipExecPackageOptionsForm.upsDelConfirm.focus();
      return false;
    }
      
    if(checkBoxVal=="Y")
    {
      var upsCodCode=document.aascShipExecPackageOptionsForm.upsCodCode.options[document.aascShipExecPackageOptionsForm.upsCodCode.selectedIndex].value;
      var upsCodFundsCode=document.aascShipExecPackageOptionsForm.upsCodFundsCode.options[document.aascShipExecPackageOptionsForm.upsCodFundsCode.selectedIndex].value;
      var upsCodCurrCode=document.aascShipExecPackageOptionsForm.upsCodCurrCode.options[document.aascShipExecPackageOptionsForm.upsCodCurrCode.selectedIndex].value;
      
      var upsCodAmt= document.aascShipExecPackageOptionsForm.upsCodAmt.value;

      if(upsCodAmt>=1)
      {
          window.opener.document.getElementById('chCODID'+packageCount).value="Y";
      }
      else
      {
          window.opener.document.getElementById('chCODID'+packageCount).value="N";
      }

      window.opener.document.getElementById('codAmtID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodAmt.value;
                  
      length=parseInt(document.aascShipExecPackageOptionsForm.upsCodCode.length);
      for(var i=0; i<length; i++)
      {
        if(document.aascShipExecPackageOptionsForm.upsCodCode.options[i].value==upsCodCode)
        {
           document.aascShipExecPackageOptionsForm.upsCodCode.options[i].selected=true;
           window.opener.document.getElementById('codTypeID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodCode.options[document.aascShipExecPackageOptionsForm.upsCodCode.selectedIndex].value;
        }
      }
       
      //////////////////////////upsCodFundsCode///////////////////////////////////////
      length=parseInt(document.aascShipExecPackageOptionsForm.upsCodFundsCode.length);
      for(var i=0; i<length; i++)
      {
        if(document.aascShipExecPackageOptionsForm.upsCodFundsCode.options[i].value==upsCodFundsCode)
        {
           document.aascShipExecPackageOptionsForm.upsCodFundsCode.options[i].selected=true;
           window.opener.document.getElementById('codFundsCodeID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodFundsCode.options[document.aascShipExecPackageOptionsForm.upsCodFundsCode.selectedIndex].value;
        }
      }
       
      //////////////////////////upsCodCurrCode///////////////////////////////////////
      length=parseInt(document.aascShipExecPackageOptionsForm.upsCodCurrCode.length);
      for(var i=0; i<length; i++)
      {
        if(document.aascShipExecPackageOptionsForm.upsCodCurrCode.options[i].value==upsCodCurrCode)
        {
           document.aascShipExecPackageOptionsForm.upsCodCurrCode.options[i].selected=true;
           window.opener.document.getElementById('codCurrCodeID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodCurrCode.options[document.aascShipExecPackageOptionsForm.upsCodCurrCode.selectedIndex].value;
        }
      }
       
     }//end of if(checkBoxVal=="Y")
     else
     {
          window.opener.document.getElementById('chCODID'+packageCount).value="N";
          window.opener.document.getElementById('codAmtID'+packageCount).value="";
          window.opener.document.getElementById('codTypeID'+packageCount).value="";
          window.opener.document.getElementById('codFundsCodeID'+packageCount).value="";
          window.opener.document.getElementById('codCurrCodeID'+packageCount).value="";
     }//end of else of if(checkBoxVal=="Y")
     
     //////////////////////////upsDelConfirm///////////////////////////////////////
     length=parseInt(document.aascShipExecPackageOptionsForm.upsDelConfirm.length);
     for(var i=0; i<length; i++)
     {
        if(document.aascShipExecPackageOptionsForm.upsDelConfirm.options[i].value==upsDelConfirm)
        {
         document.aascShipExecPackageOptionsForm.upsDelConfirm.options[i].selected=true;
         window.opener.document.getElementById('delConfirmID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsDelConfirm.options[document.aascShipExecPackageOptionsForm.upsDelConfirm.selectedIndex].value;
        }
     }
     
     if(checkupsCodAmt()==false)
     {
        return false;
     }
     
     
  largePackageCheckBoxFunc();
  addlHandlingCheckBoxFunc();
          if(document.aascShipExecPackageOptionsForm.LargePackage.value !="Y")
        {
         window.opener.document.getElementById('LargePackageID'+packageCount).value="N";              
        }
        else 
        {
         window.opener.document.getElementById('LargePackageID'+packageCount).value="Y";       
        }
      
        if(document.aascShipExecPackageOptionsForm.AdditionalHandling.value !="Y")
        {
         window.opener.document.getElementById('AdditionalHandlingID'+packageCount).value="N";            
        }
        else 
        {
         window.opener.document.getElementById('AdditionalHandlingID'+packageCount).value="Y";       
        }
    }//end of if ship method is not disabled( before ship/save)
  
   window.close();
}//end of the method.

function packageDeclaredValuefn()
{
    var declareval;
    if(isNaN(document.aascShipExecPackageOptionsForm.packageDeclaredValue.value))
    {
        alert('Enter Only Number in Declared value Field');
        return false;
    }
//    if(document.aascShipExecPackageOptionsForm.packageDeclaredValue.value >=500)
//    {
//        document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked = true;
//        document.aascShipExecPackageOptionsForm.signatureOption1.disabled = false;
//        document.aascShipExecPackageOptionsForm.signatureOption1.checked = true;
//        document.aascShipExecPackageOptionsForm.signatureOption2.disabled = false;
//        document.aascShipExecPackageOptionsForm.signatureOption2.checked = false;
//        document.aascShipExecPackageOptionsForm.signatureOption1.value="DIRECT";
//        document.aascShipExecPackageOptionsForm.signatureOption2.value="NONE";    
//    }
//    else
//    {
//        document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked = false;
//        document.aascShipExecPackageOptionsForm.signatureOption1.disabled = true;
//        document.aascShipExecPackageOptionsForm.signatureOption2.disabled = true;
//        document.aascShipExecPackageOptionsForm.signatureOption1.checked = false;
//        document.aascShipExecPackageOptionsForm.signatureOption2.checked = false;
//        document.aascShipExecPackageOptionsForm.signatureOption1.value="NONE";
//        document.aascShipExecPackageOptionsForm.signatureOption2.value="NONE";
//    }
 } 
 
//function signatureOptionCheckfn()
//{ 
//  if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked == true) 
//  {
//      document.aascShipExecPackageOptionsForm.signatureOption1.disabled = false;
//      document.aascShipExecPackageOptionsForm.signatureOption2.disabled = false;
//      document.aascShipExecPackageOptionsForm.signatureOption3.disabled = false;
//      document.aascShipExecPackageOptionsForm.signatureOption4.disabled = false;
//  }
//  else
//  {  
//      document.aascShipExecPackageOptionsForm.signatureOption1.disabled = true; 
//      document.aascShipExecPackageOptionsForm.signatureOption2.disabled = true;
//      document.aascShipExecPackageOptionsForm.signatureOption1.checked = false;
//      document.aascShipExecPackageOptionsForm.signatureOption2.checked = false;
//      document.aascShipExecPackageOptionsForm.signatureOption1.value = "NONE";
//      document.aascShipExecPackageOptionsForm.signatureOption2.value = "NONE";
//      document.aascShipExecPackageOptionsForm.signatureOption3.disabled = true;
//      document.aascShipExecPackageOptionsForm.signatureOption4.disabled = true;
//      document.aascShipExecPackageOptionsForm.signatureOption3.checked = false;
//      document.aascShipExecPackageOptionsForm.signatureOption4.checked = false;
//      document.aascShipExecPackageOptionsForm.signatureOption3.value = "NONE";
//      document.aascShipExecPackageOptionsForm.signatureOption4.value = "NONE";
//  }
//}

function returnShipmentfn()
{
    
    
     if(document.aascShipExecPackageOptionsForm.returnShipment.checked ==true)
    {
        document.aascShipExecPackageOptionsForm.returnShipment.value="PRINTRETURNLABEL";
    }
    else
    {
        document.aascShipExecPackageOptionsForm.returnShipment.value="NONRETURN";
//        document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value=0.0;
        document.aascShipExecPackageOptionsForm.rtnRMA.value="";
    }
}

//function signatureOption1()
//{
//  if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked == true)
//  {
//    if(document.aascShipExecPackageOptionsForm.signatureOption1.checked ==true)
//    {
//        document.aascShipExecPackageOptionsForm.signatureOption1.value="DIRECT";
//        document.aascShipExecPackageOptionsForm.signatureOption2.value="NONE";
//        document.aascShipExecPackageOptionsForm.signatureOption3.value="NONE";
//        document.aascShipExecPackageOptionsForm.signatureOption4.value="NONE";
//    }
//    else
//    {
//        document.aascShipExecPackageOptionsForm.signatureOption1.value="NONE";
//    }
//  }
//  else
//      document.aascShipExecPackageOptionsForm.signatureOption1.value="NONE";
//}

//function signatureOption2()
//{
//  if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked == true)
//  {
//    if(document.aascShipExecPackageOptionsForm.signatureOption2.checked ==true)
//    {
//        document.aascShipExecPackageOptionsForm.signatureOption2.value="ADULT";
//        document.aascShipExecPackageOptionsForm.signatureOption1.value="NONE";
//        document.aascShipExecPackageOptionsForm.signatureOption3.value="NONE";
//        document.aascShipExecPackageOptionsForm.signatureOption4.value="NONE";
//    }
//    else
//    {
//        document.aascShipExecPackageOptionsForm.signatureOption2.value="NONE";
//    }
//  }
//  else
//    document.aascShipExecPackageOptionsForm.signatureOption2.value="NONE";
//}


//function signatureOption3()
//{
//  var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;
//  if(str.substring(0,3)=="110")
//  {
//    if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked == true)
//    {
//      if(document.aascShipExecPackageOptionsForm.signatureOption3.checked ==true)
//      {
//      document.aascShipExecPackageOptionsForm.signatureOption1.value="NONE";
//      document.aascShipExecPackageOptionsForm.signatureOption2.value="NONE";
//      document.aascShipExecPackageOptionsForm.signatureOption3.value="DELIVERWITHOUTSIGNATURE";
//      document.aascShipExecPackageOptionsForm.signatureOption4.value="NONE";
//      }
//      else
//      {
//      document.aascShipExecPackageOptionsForm.signatureOption3.value="NONE";
//      }
//    }
//    else
//    document.aascShipExecPackageOptionsForm.signatureOption3.value="NONE";
//  }
//  else
//  {
//    alert("Fedex Ground Doesnot Support this Option");
//   document.aascShipExecPackageOptionsForm.signatureOption3.checked =false; 
//  }
//}

//function signatureOption4()
//{
//  if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked == true)
//  {
//    if(document.aascShipExecPackageOptionsForm.signatureOption4.checked ==true)
//    {
//    document.aascShipExecPackageOptionsForm.signatureOption1.value="NONE";
//    document.aascShipExecPackageOptionsForm.signatureOption2.value="NONE";
//    document.aascShipExecPackageOptionsForm.signatureOption3.value="NONE";
//    document.aascShipExecPackageOptionsForm.signatureOption4.value="INDIRECT";
//    }
//    else
//    {
//    document.aascShipExecPackageOptionsForm.signatureOption4.value="NONE";
//    }
//  }
//  else
//  document.aascShipExecPackageOptionsForm.signatureOption4.value="NONE";
//}


function largePackageCheckBoxFunc()
{
    if(document.aascShipExecPackageOptionsForm.LargePackage.checked==true)
    {
     document.aascShipExecPackageOptionsForm.LargePackage.value="Y";
    }
    else
    {
     document.aascShipExecPackageOptionsForm.LargePackage.value="N";
   
    }
}

function addlHandlingCheckBoxFunc()
{
    if(document.aascShipExecPackageOptionsForm.AdditionalHandling.checked==true)
    {
     document.aascShipExecPackageOptionsForm.AdditionalHandling.value="Y";
    }
    else
    {
     document.aascShipExecPackageOptionsForm.AdditionalHandling.value="N";
   
    }
}

function saveDetails()
{

 if(document.aascShipExecPackageOptionsForm.returnShipment.value =="PRINTRETURNLABEL"   && document.aascShipExecPackageOptionsForm.codTempFlag.value=="Y" )
     {
     alert("COD and Return shipment both can not be allowed at a time");
     return false;
     }
 
 var flagShipStr = window.opener.document.getElementById('flagShip1').value;
 if(flagShipStr=='Y'){//Sunanda added the following code for bug fix #2747
 
 return false;
 
 }
 
  if(document.aascShipExecPackageOptionsForm.returnShipment.value =="PRINTRETURNLABEL" && document.aascShipExecPackageOptionsForm.returnDescription.value=="" )
     {
     alert(" Please enter Return Description ");
     document.aascShipExecPackageOptionsForm.returnDescription.focus();
     return false;
     }   
     

  if(window.opener.document.getElementById('shipMethodId').disabled!=true)
    {
     var packageCount;
     var point=".";
     var pos1;
     var decimalNum;
     var floatingNum;
     var length;
     var checkBoxVal;
     packageCount=document.aascShipExecPackageOptionsForm.packCount.value;
//      alert("packCount:1203:"+document.aascShipExecPackageOptionsForm.packCount.value);
     checkBoxVal=document.aascShipExecPackageOptionsForm.codTempFlag.value;
     if(checkBoxVal=="Y")
     {   
       var upsCodAmt= document.aascShipExecPackageOptionsForm.upsCodAmt.value;

       if(upsCodAmt >=1)
       {
         window.opener.document.getElementById('chCODID'+packageCount).value="Y";
       }
       else
       {
         window.opener.document.getElementById('chCODID'+packageCount).value="N";
       }

       window.opener.document.getElementById('codAmtID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodAmt.value;
       window.opener.document.getElementById('codTypeID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodCode.value;
       window.opener.document.getElementById('codFundsCodeID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodFundsCode.value;
       window.opener.document.getElementById('codCurrCodeID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodCurrCode.value;           
     }
     else
     {
       window.opener.document.getElementById('chCODID'+packageCount).value="N";
       window.opener.document.getElementById('codAmtID'+packageCount).value="";
       window.opener.document.getElementById('codTypeID'+packageCount).value="";
       window.opener.document.getElementById('codFundsCodeID'+packageCount).value="";
       window.opener.document.getElementById('codCurrCodeID'+packageCount).value="";
     }
         window.opener.document.getElementById('LargePackageID'+packageCount).value=document.aascShipExecPackageOptionsForm.LargePackage.value;
     window.opener.document.getElementById('AdditionalHandlingID'+packageCount).value=document.aascShipExecPackageOptionsForm.AdditionalHandling.value;
     window.opener.document.getElementById('pkgingID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsPackaging.value;
     window.opener.document.getElementById('delConfirmID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsDelConfirm.value;
    }
 //Sunanda added the below code for bug fix #2894
    
    if(window.opener.document.getElementById('flagShip1').value == 'Y'){
//    alert('at 805');
         var button = '<a href="#" onclick="return saveDetails();" ><img src="buttons/aascSave1.png" name="save" id="saveId1" alt ="" border="0" /></a>'+ '&nbsp;' + '<a href="#" onclick="loadPackageOptions()" ><img src="buttons/aascCancel1.png" name="cancel" id="cancelId1" alt="" /></a>'+ '&nbsp;' + '<a href="#" onclick="javascript:window.close()" ><img src="buttons/aascClose1.png" name="close" id="closeId1" border="0" alt="" /></a>';
        document.getElementById("buttonDiv").innerHTML = button;
        document.getElementById("buttonDiv2").innerHTML = button;
        
    }
    
 
 
//var signatureOptionCheck;

var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value
//30/07/07
var strRtn=document.aascShipExecPackageOptionsForm.rtnShipMethod.value;
var rtnPaymethod=document.aascShipExecPackageOptionsForm.rtnPayMethod.value;
//30/07/07
//var returnshipval;  //30/07/07
var packageCount;

var toDayDate=window.opener.document.getElementById('shipmentDate').value;
toDayDate = toDayDate.substring(0,10); // ship date without timestamp to fix #4153
//alert("aasa::"+window.opener.DynaAdhocShipSaveForm.carrierPayMethod.value);
//alert("wwww::"+window.opener.DynaAdhocShipSaveForm.carrierPayMethod.options[window.opener.DynaAdhocShipSaveForm.carrierPayMethod.selectedIndex].value);
//alert("dddd::::"+window.opener.DynaAdhocShipSaveForm.carrierPayMethod.options[window.opener.DynaAdhocShipSaveForm.carrierPayMethod.selectedIndex].value);
var payMethod1 = window.opener.document.getElementById('carrierPayMethodId').value;
//window.opener.DynaAdhocShipSaveForm.CarrierPayMethod.options[window.opener.DynaAdhocShipSaveForm.CarrierPayMethod.selectedIndex].value;
var currentTime = new Date();
var month = currentTime.getMonth() + 1;
var day = currentTime.getDate();
var year = currentTime.getFullYear();

if(day<10)
{
day='0'+day;
}
if(month<10)
{
month='0'+month;
}
var myDate=year+"-"+month+"-"+day;
 packageCount=document.aascShipExecPackageOptionsForm.packCount.value;
//  alert("packCount:1281:"+document.aascShipExecPackageOptionsForm.packCount.value);
 //returnshipval=document.aascShipExecPackageOptionsForm.returnShipment.value; //30/07/07
 
 
 
 
 
 


if(document.aascShipExecPackageOptionsForm.returnShipment.checked == false)

  {

     document.aascShipExecPackageOptionsForm.returnShipment.value="NONRETURN";

  }
  

 if(document.aascShipExecPackageOptionsForm.returnShipment.checked == true  && toDayDate != myDate && strRtn.substring(0,3)=="100")
 {
 alert('Return Shipment Feature Is Not Available With Future Day');
  return false;
 }
 
 //Added by gayaz
 
//  if(strRtn.substring(0,3)=="100" && document.aascShipExecPackageOptionsForm.returnShipment.value =="PRINTRETURNLABEL"   && document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value>100 )
//     {
//     alert("Package Declared value Should not be greater than 100 for This package");
//     return false;
//     }

 
 
 
 
 
 
    var shipMethod1=window.opener.document.getElementById('ajaxcarrierservicelevel').value;
     var shipMethodRtn=document.aascShipExecPackageOptionsForm.ajaxcarrierservicelevel.value;
       if (shipMethodRtn=="FEDEXEXPRESSSAVER" && document.aascShipExecPackageOptionsForm.returnShipment.checked == true)
     {
      alert('ReturnShipment is not allowed for the Ship Method FEDEXEXPRESSSAVER');
      return false;
     }  //30/07/07
     
     if((str.substring(0,3)=="100") &&  (payMethod1=="CG" || payMethod1=="TP") && document.aascShipExecPackageOptionsForm.upsCodCheckBox.checked == true)
    {
    alert('COD is not allowed for THIRD PARTY BILLING and CONSIGNEE');
    return false;
    }
           
    if((strRtn.substring(0,3)=="100") &&  rtnPaymethod=="CG" && document.aascShipExecPackageOptionsForm.returnShipment.checked == true)
    {
    alert('Return Shipment Feature Is Not Available With CONSIGNEE Paymethod ');
    return false;
    }
        
//if(document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked == false)
//  {
//     document.aascShipExecPackageOptionsForm.signatureOptionCheck.value="NONE";
//
//         signatureOptionCheck=document.aascShipExecPackageOptionsForm.signatureOptionCheck.value;
//      //   alert("signatureOptionCheck :"+abd);
//         
//  }
//  else

//  {
//            if(document.aascShipExecPackageOptionsForm.signatureOption1.checked == false &&
//
//                   document.aascShipExecPackageOptionsForm.signatureOption2.checked == false && document.aascShipExecPackageOptionsForm.signatureOption3.checked == false && document.aascShipExecPackageOptionsForm.signatureOption4.checked == false)
//
//           {
////                  alert('Please Check Signature Option Value');
//
////                    return false;
//
//           }
//
//           else
//
//             {
//                 if(document.aascShipExecPackageOptionsForm.signatureOption1.checked == true)
//
//   {
//                      signatureOptionCheck=document.aascShipExecPackageOptionsForm.signatureOption1.value;
//                     }
//                 if(document.aascShipExecPackageOptionsForm.signatureOption2.checked == true)
//                    {
//                      signatureOptionCheck=document.aascShipExecPackageOptionsForm.signatureOption2.value;
//                    }
//                if(document.aascShipExecPackageOptionsForm.signatureOption3.checked == true)
//                {
//                  signatureOptionCheck=document.aascShipExecPackageOptionsForm.signatureOption3.value;
//                }
//                
//                if(document.aascShipExecPackageOptionsForm.signatureOption4.checked == true)
//                {
//                  signatureOptionCheck=document.aascShipExecPackageOptionsForm.signatureOption4.value;
//                }
//
//            }
// }
       var codCheck=document.aascShipExecPackageOptionsForm.upsCodCheckBox.checked;
      var codAmount=document.aascShipExecPackageOptionsForm.upsCodAmt.value;
 
      if(codCheck==true &&(isNaN(codAmount) ||codAmount==''))
      {
      alert("Please enter COD amount as a numeric value ");
      return false;
      
      }
      if(codCheck==true && codAmount<1)
      {
      alert("COD Amount Should be Greater than or Equal to 1");
      return false;
      
      }
     
      if(packageCount!=1){
//      alert('fist package cod::::'+window.opener.document.getElementById('chCODID1').value);
      if(codCheck ==true && window.opener.document.getElementById('chCODID1').value !="Y")
      {
      alert("You can not select COD Unless you select the cod of the first package");
      document.aascShipExecPackageOptionsForm.upsCodCheckBox.checked=false;
      document.aascShipExecPackageOptionsForm.upsCodCheckBox.value="N";
      document.aascShipExecPackageOptionsForm.upsCodAmt.value=0;
      return false;
      }
      }
     // if(document.aascShipExecPackageOptionsForm.returnShipment.value=="PRINTRETURNLABEL")
      if(document.aascShipExecPackageOptionsForm.returnShipment.value=="PRINTRETURNLABEL" && document.aascShipExecPackageOptionsForm.returnShipment.disabled==false)
      {
      if (checkShipToAddress()==false)

   {
   		return false;

		}
    
    
    
    
//    var rtnACNumber=document.aascShipExecPackageOptionsForm.rtnACNumber.value;
//    if(rtnACNumber=="" || rtnACNumber=="null" || rtnACNumber==null)
//    {
//    alert('Please Enter A/C Number');
//    document.aascShipExecPackageOptionsForm.rtnACNumber.focus();
//    return false;
//    }
    //31/07/07(start)
//    var rtnDeclaredValue= document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value;
//     if(isNaN(rtnDeclaredValue) || rtnDeclaredValue<0)
//     {
//     alert("Please enter the Return DeclaredValue Correctly");
//     document.aascShipExecPackageOptionsForm.rtnDeclaredValue.focus();
//     return false;
// 
//     }
//       if(rtnDeclaredValue=="" || rtnDeclaredValue=='' )
//       {
//           document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value=0;
//       }
//        var rtnDeclaredValueChk=document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value;
//        var position=parseInt(rtnDeclaredValueChk.indexOf("."));
 
//        var decLen=parseInt(rtnDeclaredValueChk.length);
      
     
//        var subdeclare=rtnDeclaredValueChk.substring(position+1,decLen);
     
      
//        var subdeclarelen=parseInt(subdeclare.length);
//      if(parseFloat(document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value) > 9999999.99)
//         {
//       
//           alert('Return Declared Value Should be Less Than or Equal to 9999999.99');
//           document.aascShipExecPackageOptionsForm.rtnDeclaredValue.focus();
//           return false;
//        }
//    if(subdeclarelen >2 && position >0)
//       {
//         alert('Please Give  Precision of two digits for The Return Declare value' );
//         document.aascShipExecPackageOptionsForm.rtnDeclaredValue.focus();
//         return false;
//       }
    //31/07/07(end)
      }
      else
      {
//      document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value=0.0;
      document.aascShipExecPackageOptionsForm.rtnRMA.value="";
      }
          if(codCheck ==true)
        {
            window.opener.document.getElementById('chCODID'+packageCount).value="Y";
        }
        else{ 
          window.opener.document.getElementById('chCODID'+packageCount).value="N";
            }
            
    // Added by Sambit on 06/12/2007
    
   if(document.aascShipExecPackageOptionsForm.holdAtLocation.checked == true)
    {
      var halPhoneStr = document.aascShipExecPackageOptionsForm.halPhone.value;
    // Commented by Suman Gunda for bug #2079
//     if(((halPhoneStr.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (halPhoneStr.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) || halPhoneStr.length < 10) 
//				{
//
//					alert("Please Enter Valid 10 Digit Hold At Location Phone Number :");
//
//					document.aascShipExecPackageOptionsForm.halPhone.focus();
//
//					return false;
//
//				}
     if (document.aascShipExecPackageOptionsForm.halPhone.value == '')
      {
         alert('Please Enter Hold At Location Phone Number');
         document.aascShipExecPackageOptionsForm.halPhone.focus();
         return false;
      }
    if (document.aascShipExecPackageOptionsForm.halAddrLine1.value == '')
      {
         alert('Please Enter Hold At Location Line1 value');
         document.aascShipExecPackageOptionsForm.halAddrLine1.focus();
         return false;
      }
    if (document.aascShipExecPackageOptionsForm.halCity.value == '')
      {
         alert('Please Enter Hold At Location City');
         document.aascShipExecPackageOptionsForm.halCity.focus();
         return false;
      }
    if (document.aascShipExecPackageOptionsForm.halState.value == '')
      {
         alert('Please Enter Hold At Location State');
         document.aascShipExecPackageOptionsForm.halState.focus();
         return false;
      }
    if (document.aascShipExecPackageOptionsForm.halZip.value == '')
      {
         alert('Please Enter Hold At Location Postal Code');
         document.aascShipExecPackageOptionsForm.halZip.focus();
         return false;
      }
      
    }
    
    // End of 06/12/2007
    
         // Added by Sambit on 15/07/2008
    if(document.aascShipExecPackageOptionsForm.HazMatFlag.checked == true)
    {
       if (document.aascShipExecPackageOptionsForm.HazardousMaterialType.value == '')
      {
         alert('Please Select Hazardous Material Type ');
         document.aascShipExecPackageOptionsForm.HazardousMaterialType.focus();
         return false;
      }
      
      //validations for the Haz material Quantity and Unit  Added on 22 jan 09
     if(str.substring(0,3)=="100")
     {
          if (document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.value == '')
          {
             alert('Please Enter Weight');
             document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.focus();
             return false;
          }
          
          if (document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.value == '')
          {
             alert('Please Select Unit ');
             document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.focus();
             return false;
          }
          
          //Added on Jun-03-2011      
         var hazmatPkgCnt =  document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value.replace(/^\s+|\s+$/g,"");
         var hazmatPkgUnits = document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.value.replace(/^\s+|\s+$/g,"");
         var upsCarrierMode = document.aascShipExecPackageOptionsForm.upsCarrierMode.value;
         var hazmatTechnicalName = document.aascShipExecPackageOptionsForm.HazMatTechnicalName.value.replace(/^\s+|\s+$/g,"");
         var hazmatSignatureName = document.aascShipExecPackageOptionsForm.HazMatSignatureName.value.replace(/^\s+|\s+$/g,"");
         
         
       if(document.aascShipExecPackageOptionsForm.HazMatFlag.checked == true && upsCarrierMode=="ShipExec")
        { 
         if(hazmatPkgCnt == null || hazmatPkgCnt =='')
         {
           alert("Please enter Hazmat Packaging Count");
           document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value = document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value.replace(/^\s+|\s+$/g,"");
           document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.focus();
           return false;
         }
         
        var r =/^[0-9.]*$/;
        if(!r.test(document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value))
        {
        document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value = document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value.replace(/^\s+|\s+$/g,"");
        alert("Enter only numbers in Hazmat Packaging Count");
        document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.focus();
        return false;
        }
         
         if(hazmatPkgUnits == null || hazmatPkgUnits =='')
         {
           alert("Please enter Hazmat Packaging Units");
           document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.value = document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.value.replace(/^\s+|\s+$/g,"");
           document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.focus();
           return false;
         }
         
             //Added on Jul-05-2011
              if(hazmatTechnicalName == null || hazmatTechnicalName =='')
             {
               alert("Please enter Technical Name");
               document.aascShipExecPackageOptionsForm.HazMatTechnicalName.value = document.aascShipExecPackageOptionsForm.HazMatTechnicalName.value.replace(/^\s+|\s+$/g,"");
               document.aascShipExecPackageOptionsForm.HazMatTechnicalName.focus();
               return false;
             }
             //End on Jul-05-2011
             
            
      }  // End on Jun-03-2011
      
          
     } //end 22 jan 09
      
    }
    //End of 15/07/2008
    
    //===========================================================================
    
    var pkgWeight=parseFloat(window.opener.document.getElementById('weightID'+packageCount).value);
     var pkgUom=window.opener.document.getElementById('uomID'+packageCount).value+"S";
//     var pkgDryIceUnits=aascShipExecPackageOptionsForm.dryIceUnits.options[document.aascShipExecPackageOptionsForm.dryIceUnits.selectedIndex].value;
//     var pkgDryIceWeight=parseFloat(document.aascShipExecPackageOptionsForm.dryIceWeight.value);
     var chk=0;

//     if(pkgUom==pkgDryIceUnits)
//     {
     
//     if(pkgDryIceWeight>=pkgWeight)
//     {
//     alert("Please Enter DryIce Weight Less than Package Weight");
//     document.aascShipExecPackageOptionsForm.dryIceWeight.focus();
//     chk=1;
//     return false;
//     }
//     else if(pkgDryIceWeight>440 && pkgDryIceUnits=="LBS")
//     {
//     alert("Please Enter DryIce Weight Less than 440 LBS");
//      document.aascShipExecPackageOptionsForm.dryIceWeight.focus();
//      chk=1;
//     return false;
//     }
//      else if(pkgDryIceWeight>200 && pkgDryIceUnits=="KGS")
//     {
//     alert("Please Enter DryIce Weight Less than 200 KGS");
//      document.aascShipExecPackageOptionsForm.dryIceWeight.focus();
//      chk=1;
//     return false;
//     }
     
//     }
//     else if(pkgDryIceWeight>440 && pkgDryIceUnits=="LBS")
//     {
//     alert("Please Enter DryIce Weight Less than 440 LBS");
//      document.aascShipExecPackageOptionsForm.dryIceWeight.focus();
//      chk=1;
//     return false;
//     }
//      else if(pkgDryIceWeight>200 && pkgDryIceUnits=="KGS")
//     {
//     alert("Please Enter DryIce Weight Less than 200 KGS");
//      document.aascShipExecPackageOptionsForm.dryIceWeight.focus();
//      chk=1;
//     return false;
//     }
     
     
//     if(chk==0)
//     {
//     if(document.aascShipExecPackageOptionsForm.chDryIce.checked)
//     {
//     window.opener.document.getElementById('chDryIceID'+packageCount).value ="Y" ;
//     }
//     else
//     {
//     window.opener.document.getElementById('chDryIceID'+packageCount).value ="N" ;
//     }
     //alert('pkgDryIceWeight::'+pkgDryIceWeight);
//     if(document.aascShipExecPackageOptionsForm.chDryIce.checked && (pkgDryIceWeight==""))
//     {
//     alert("Please Enter DryIce Weight");
//      document.aascShipExecPackageOptionsForm.dryIceWeight.focus();
//     return false;
//     }
     
//     if(isNaN(pkgDryIceWeight) && document.aascShipExecPackageOptionsForm.chDryIce.checked)
//         {
//       alert("The DryIce Weight should be a numeric value");
//       document.aascShipExecPackageOptionsForm.dryIceWeight.focus();
//       return false;
//          }
          /*alert(document.aascShipExecPackageOptionsForm.chDryIce.checked);
          alert('pkgDryIceUnits::'+pkgDryIceUnits);
          alert('pkgDryIceWeight::'+pkgDryIceWeight);*/
          
//     window.opener.document.getElementById('dryIceUnitsID'+packageCount).value =pkgDryIceUnits;// document.aascShipExecPackageOptionsForm.dryIceUnits.value;
//     window.opener.document.getElementById('dryIceWeightID'+packageCount).value =pkgDryIceWeight; 
//     }
     
    
    //===========================================================================
    
    
    
    
     window.opener.document.getElementById('codAmtID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodAmt.value;
     window.opener.document.getElementById('returnShipmentID'+packageCount).value=document.aascShipExecPackageOptionsForm.returnShipment.value;
     window.opener.document.getElementById('PackageSurchargeID'+packageCount).value=document.aascShipExecPackageOptionsForm.PackageSurcharge.value;  
     window.opener.document.getElementById('PackageShipmentCostID'+packageCount).value=document.aascShipExecPackageOptionsForm.PackageShipmentCost.value;  
     //alert("signature option::"+signatureOptionCheck);
//     window.opener.document.getElementById('signatureOptionID'+packageCount).value=signatureOptionCheck;  
     
     //19/07/07(start)
    
     window.opener.document.getElementById('LargePackageID'+packageCount).value=document.aascShipExecPackageOptionsForm.LargePackage.value;  
     window.opener.document.getElementById('AdditionalHandlingID'+packageCount).value=document.aascShipExecPackageOptionsForm.AdditionalHandling.value;  
     window.opener.document.getElementById('returnDescriptionID'+packageCount).value=document.aascShipExecPackageOptionsForm.returnDescription.value;  
     window.opener.document.getElementById('rtnShipFromCompanyID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromCompany.value;  
     window.opener.document.getElementById('rtnShipToCompanyID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToCompany.value;  
     window.opener.document.getElementById('rtnShipFromContactID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromContact.value;  
     window.opener.document.getElementById('rtnShipToContactID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToContact.value;  
     window.opener.document.getElementById('rtnShipFromLine1ID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromLine1.value;  
     window.opener.document.getElementById('rtnShipToLine1ID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToLine1.value;  
     window.opener.document.getElementById('rtnShipFromLine2ID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromLine2.value;  
     window.opener.document.getElementById('rtnShipToLine2ID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToLine2.value;  
     window.opener.document.getElementById('rtnShipFromCityID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromCity.value;  
     window.opener.document.getElementById('rtnCountrySymbolID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnCountrySymbol.value;  
     window.opener.document.getElementById('rtnShipFromSateID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromSate.value;  
     window.opener.document.getElementById('rtnShipFromZipID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromZip.value;  
     window.opener.document.getElementById('rtnShipToCityID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToCity.value;  
     window.opener.document.getElementById('rtnShipToStateID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToState.value;  
     window.opener.document.getElementById('rtnShipToZipID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToZip.value;  
     window.opener.document.getElementById('rtnShipFromPhoneID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromPhone.value;  
     window.opener.document.getElementById('rtnShipToPhoneID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToPhone.value;  
//     alert('shiomethod::::'+document.aascShipExecPackageOptionsForm.ajaxShipMethod.value);
       var rtnShipmethodAjax = document.aascShipExecPackageOptionsForm.ajaxShipMethod.value;
     try{
     document.aascShipExecPackageOptionsForm.ajaxShipMethod.value = rtnShipmethodAjax.substring(0,rtnShipmethodAjax.lastIndexOf('@')-1);
//     alert(document.aascShipExecPackageOptionsForm.ajaxShipMethod.value);
     }catch(e ){
//     alert('hi');
     }
     window.opener.document.getElementById('rtnShipMethodID'+packageCount).value=document.aascShipExecPackageOptionsForm.ajaxShipMethod.value;  
     window.opener.document.getElementById('rtnDropOfTypeID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnDropOfType.value; 
     window.opener.document.getElementById('rtnPackageListID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnPackageList.value;  
     window.opener.document.getElementById('rtnPayMethodID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnPayMethod.options[document.aascShipExecPackageOptionsForm.rtnPayMethod.selectedIndex].text;
     window.opener.document.getElementById('rtnPayMethodCodeID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnPayMethod.options.value;
        
     window.opener.document.getElementById('rtnACNumberID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnACNumber.value;  
     window.opener.document.getElementById('rtnRMAID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnRMA.value;  
     
     window.opener.document.getElementById('packageSaveCheckID'+packageCount).value="Y";  
     window.opener.document.getElementById('rtnShipMethodNameID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipMethod.value;
     //30/07/07
//     window.opener.document.getElementById('rtnDeclaredValueID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value;
     //30/07/07
     
    //19/07/07(end)
  //   alert("rtnPayMethod :"+document.aascShipExecPackageOptionsForm.rtnPayMethod.options[document.aascShipExecPackageOptionsForm.rtnPayMethod.selectedIndex].text);
     
     // Added by Sambit on 15/11/07
     
     window.opener.document.getElementById('halPhoneID'+packageCount).value = document.aascShipExecPackageOptionsForm.halPhone.value;
     window.opener.document.getElementById('halAddrLine1ID'+packageCount).value = document.aascShipExecPackageOptionsForm.halAddrLine1.value;
     window.opener.document.getElementById('halAddrLine2ID'+packageCount).value = document.aascShipExecPackageOptionsForm.halAddrLine2.value;
     window.opener.document.getElementById('halCityID'+packageCount).value = document.aascShipExecPackageOptionsForm.halCity.value;
     window.opener.document.getElementById('halStateID'+packageCount).value = document.aascShipExecPackageOptionsForm.halState.value;
     window.opener.document.getElementById('halZipID'+packageCount).value = document.aascShipExecPackageOptionsForm.halZip.value;
     window.opener.document.getElementById('holdAtLocationID'+packageCount).value = document.aascShipExecPackageOptionsForm.holdAtLocation.value;
     
          // Added By Sambit on 11/07/08
     if(document.aascShipExecPackageOptionsForm.HazMatFlag.checked)
    {
			document.aascShipExecPackageOptionsForm.HazMatFlag.value="Y";
    }else{
      document.aascShipExecPackageOptionsForm.HazMatFlag.value="N";
    }
     
     window.opener.document.getElementById('HazMatFlagID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatFlag.value;
     window.opener.document.getElementById('HazMatTypeID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialType.value;
     window.opener.document.getElementById('HazMatClassID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialClass.value;
     //alert(window.opener.DynaAdhocShipSaveForm['HazMatFlag'+packageCount].value);
//   window.opener.DynaAdhocShipSaveForm['HazMatCharges'+packageCount].value = document.aascShipExecPackageOptionsForm.HazardousMaterialCharges.value;
       
   //window.opener.DynaAdhocShipSaveForm['HazMatFlag'+packageCount].value=packageCount;
   //end of 11/07/08

     //pavan
     //alert(document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.value);
     window.opener.document.getElementById('HazMatQtyID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.value;
     window.opener.document.getElementById('HazMatUnitID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.value;
     //alert("save::::"+document.aascShipExecPackageOptionsForm.HazMatIdentificationNo.value);
     //alert("save pkg count::::"+ packageCount);
    
      window.opener.document.getElementById('HazMatIdNoID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatIdentificationNo.value;
       //alert("save 1122::::"+window.opener.DynaAdhocShipSaveForm['HazMatIdNo'+packageCount].value);
    window.opener.document.getElementById('HazMatPkgGroupID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialPkgGroup.value;
    window.opener.document.getElementById('HazMatDOTLabelID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatDOTLabelType.value;
    window.opener.document.getElementById('HazMatEmerContactNoID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatEmergencyContactNo.value;
    window.opener.document.getElementById('HazMatEmerContactNameID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatEmergencyContactName.value;
    window.opener.document.getElementById('HazMatIdID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialId.value;
     
     
     // Added on Jun-03-2011
      
    window.opener.document.getElementById('HazMatPackagingCntID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value;
    window.opener.document.getElementById('HazMatPackagingUnitsID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.value;
     window.opener.document.getElementById('HazMatPackInstructionsID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatPackInstructions.value;
    
    // End on Jun-03-2011
    //Added on Jul-05-2011
        window.opener.document.getElementById('HazMatTechnicalNameID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatTechnicalName.value;
    //End on Jul-05-2011
        window.opener.document.getElementById('HazMatSignatureNameID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatSignatureName.value;
        //Added code to check Signature Options and Direct option when Hazardous Material is checked. Bug #3965.
        if(document.aascShipExecPackageOptionsForm.HazMatFlag.checked== true)
        {
//            if((document.aascShipExecPackageOptionsForm.signatureOptionCheck.value=="NONE") || ((document.aascShipExecPackageOptionsForm.signatureOption1.checked!=true) && (document.aascShipExecPackageOptionsForm.signatureOption2.checked!=true)))
//            {
                //5_7_6 - Hologic Fix - Stop alert for selecting direct signature option, if hazmat option selected
                //        alert("'Direct' Signature Option will be selected for hazmat shipments");
//                document.aascShipExecPackageOptionsForm.signatureOption1.checked = true;
//                document.aascShipExecPackageOptionsForm.signatureOptionCheck.checked = true;
//                document.aascShipExecPackageOptionsForm.signatureOption1.value="DIRECT";
//                document.aascShipExecPackageOptionsForm.signatureOption1.disabled = false;
//                document.aascShipExecPackageOptionsForm.signatureOption2.disabled = false;
//                document.aascShipExecPackageOptionsForm.signatureOption3.disabled = false;
//                document.aascShipExecPackageOptionsForm.signatureOption4.disabled = false; 
                //return false;
//                window.opener.document.getElementById('signatureOptionID'+packageCount).value = document.aascShipExecPackageOptionsForm.signatureOption1.value;   
//            }
        }
        document.aascShipExecPackageOptionsForm.actionType.value='SAVED';
     window.close();
 }





//Added byu gayaz 
function codValidate()
{
 var packCount=document.aascShipExecPackageOptionsForm.packCount.value;
// alert("packCount:1840:"+document.aascShipExecPackageOptionsForm.packCount.value);
var codAmount=document.aascShipExecPackageOptionsForm.upsCodAmt.value;
 if(isNaN(codAmount))
         {
       alert("The COD Amount should be a numeric value");
       document.aascShipExecPackageOptionsForm.upsCodCheckBox.focus();
          }

}




	function codCheck(){

		var idval= parseInt(document.aascShipExecPackageOptionsForm.packageCount.value);

//		document.aascShipExecPackageOptionsForm.upsCodCheckBox.value="N"; 

		document.aascShipExecPackageOptionsForm.upsCodAmt.readOnly=true;

		document.aascShipExecPackageOptionsForm.upsCodAmt.value="";

		if(document.aascShipExecPackageOptionsForm.upsCodCheckBox.checked){

			document.aascShipExecPackageOptionsForm.upsCodCheckBox.value="Y";

			document.aascShipExecPackageOptionsForm.upsCodAmt.readOnly=false;
      document.aascShipExecPackageOptionsForm.upsCodAmt.disabled=false;
		   }	
       else
       {
       document.aascShipExecPackageOptionsForm.upsCodCheckBox.value="N";
       }

    }

 //18/07/07(start)
 function rmaCheck()
 {
   if(document.aascShipExecPackageOptionsForm.returnShipment.value=="PRINTRETURNLABEL" && document.aascShipExecPackageOptionsForm.returnShipment.disabled==false)
 {
 document.aascShipExecPackageOptionsForm.rtnShipFromCompany.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipToCompany.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipFromContact.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipToContact.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipFromLine1.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipToLine1.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipFromLine2.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipToLine2.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipFromCity.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipFromSate.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipFromZip.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipToCity.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipToState.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipToZip.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipFromPhone.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipToPhone.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnShipMethod.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnDropOfType.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnPackageList.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnPayMethod.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnACNumber.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnRMA.disabled=false;
 document.aascShipExecPackageOptionsForm.rtnTrackingNumber.disabled=false;
// document.aascShipExecPackageOptionsForm.rtnDeclaredValue.disabled=false;
 document.aascShipExecPackageOptionsForm.returnDescription.disabled=false;
   }
 else
 {
	 document.aascShipExecPackageOptionsForm.rtnShipFromCompany.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipToCompany.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipFromContact.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipToContact.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipFromLine1.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipToLine1.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipFromLine2.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipToLine2.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipFromCity.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipFromSate.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipFromZip.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipToCity.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipToState.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipToZip.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipFromPhone.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipToPhone.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnShipMethod.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnDropOfType.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnPackageList.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnPayMethod.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnACNumber.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnRMA.disabled=true;
 document.aascShipExecPackageOptionsForm.rtnTrackingNumber.disabled=true;
// document.aascShipExecPackageOptionsForm.rtnDeclaredValue.disabled=true;
 document.aascShipExecPackageOptionsForm.returnDescription.disabled=true;
  }
 }
  //18/07/07(end)
 
 
/************************************ 19-JULY-2007  *************************************/





/*************************************************************************************/
function getCcCsl(testForErrorCase)
  {  
//  alert("testForErrorCase  "+testForErrorCase);
//   alert("packCount:1950:"+document.aascShipExecPackageOptionsForm.packCount.value);
  var packCount1=document.aascShipExecPackageOptionsForm.packCount.value;
   var shipStatusFlag1=window.opener.document.getElementById('shipmentStatusFlagID').value;
     //var ShipConfirmedFlag1=window.opener.DynaAdhocShipSaveForm.ShipConfirmed.value;
  if(  shipStatusFlag1!="B" && shipStatusFlag1 !="P")
  {
  var localShipmethod=window.opener.document.getElementById('rtnShipMethodNameID'+packCount1).value;
  var localShipmethod1=window.opener.document.getElementById('rtnShipMethodID'+packCount1).value;
  }
  else
  {
  
 var localShipmethod= document.aascShipExecPackageOptionsForm.ajaxcarrierservicelevel.value;
 var localShipmethod1= document.aascShipExecPackageOptionsForm.ajaxcarrierservicelevel.value
  }
  var localDropOfType="";
  var localPackageList="";
  var localCarrierPayMethodText="";
  var localCarrierAccNumber="";
  if(testForErrorCase=="1")
  {
    
      
      if(localShipmethod=="" || localShipmethod =='null' || localShipmethod == null )
      {
      if(localShipmethod1=="" || localShipmethod1 =='null' || localShipmethod1 == null )
      {
      localShipmethod =window.opener.document.getElementById('shipMethodId').value;
      
      }
      else
      {
      localShipmethod=localShipmethod1;
      
      }
      }
    //  alert("On Load :"+document.aascShipExecPackageOptionsForm.ajaxAfterShipCarrPayMthdValue.value);
  /*  localDropOfType           =   document.aascShipExecPackageOptionsForm.rtnDropOfType.value;
    localPackageList          =   document.aascShipExecPackageOptionsForm.rtnPackageList.value;
    localCarrierPayMethodText =   document.aascShipExecPackageOptionsForm.rtnPayMethod.value;
    localCarrierAccNumber     =   document.aascShipExecPackageOptionsForm.rtnACNumber.value;   */
    
    
      localDropOfType           =   document.aascShipExecPackageOptionsForm.ajaxAfterShipDropOffType.value;
      localPackageList          =   document.aascShipExecPackageOptionsForm.ajaxAfterShipPackaging.value;
      localCarrierPayMethodText =   document.aascShipExecPackageOptionsForm.ajaxAfterShipCarrPayMthdValue.value;
      localCarrierAccNumber     =   document.aascShipExecPackageOptionsForm.ajaxAfterShipCarrAccNumber.value;  
      if((localDropOfType=='' || localDropOfType=="null" || localDropOfType==null) && (localPackageList=='' || localPackageList=="null" || localPackageList==null))
      {//alert("here::::");
      localShipmethod =window.opener.document.getElementById('shipMethodId').value;
//      alert("localshipmethod::::::1341:::::"+window.opener.document.getElementById('shipMethodId').value);
      localDropOfType=window.opener.document.getElementById('dropOftypeId').value;
      localPackageList=window.opener.document.getElementById('packagesId').value;
      localCarrierPayMethodText=window.opener.document.getElementById('carrierPayMethodId').value;
      localCarrierAccNumber=window.opener.document.getElementById('carrierAccountNumberId').value;
      
//      alert("localDropOfType : "+localDropOfType);
//      alert("localPackageList : "+localPackageList);
//      alert("localCarrierPayMethodText : "+localCarrierPayMethodText);
//      alert("localCarrierAccNumber : "+localCarrierAccNumber);
      }
      
  //  alert("on load 1"+localDropOfType);
  }
  else
  {
    if(testForErrorCase=="3") // rajesh 28-may-2007
    {
//      alert("3333333333");
      localDropOfType           =   document.aascShipExecPackageOptionsForm.ajaxAfterShipDropOffType.value;
      localPackageList          =   document.aascShipExecPackageOptionsForm.ajaxAfterShipPackaging.value;
      localCarrierPayMethodText =   document.aascShipExecPackageOptionsForm.ajaxAfterShipCarrPayMthdValue.value;
      localCarrierAccNumber     =   document.aascShipExecPackageOptionsForm.ajaxAfterShipCarrAccNumber.value;
      
    //  alert("duplicate"+localDropOfType+"  "+localPackageList+"  "+localCarrierPayMethodText+"  "+localCarrierAccNumber);   
    }
    else
    {
//      alert("on change 2 ");
      localShipmethod          ='';
      localDropOfType           =   '';
      localPackageList          =   '';
      localCarrierPayMethodText =   '';
      localCarrierAccNumber     =   '';
    }
  }  
  /*
  var localDropOfType           =   window.opener.document.getElementById('dropOftypeId').value;
  var localPackageList          =   window.opener.document.getElementById('packagesId').value;
  var localCarrierPayMethodText =   window.opener.document.getElementById('carrierPayMethodId').value;
  */

  var ajaxOriginRegionCode=document.aascShipExecPackageOptionsForm.ajaxOriginRegionCode.value;

  var ajaxshipMethodvalue=document.aascShipExecPackageOptionsForm.rtnShipMethod.value;
  
  var testForFedexUps=ajaxshipMethodvalue.substring(0,ajaxshipMethodvalue.indexOf("|"));
//  alert("testForFedexUps  "+testForFedexUps);
  //
    document.aascShipExecPackageOptionsForm.rtnDropOfType.options.length=0 // added for clearing all the option fields in select tag
    document.aascShipExecPackageOptionsForm.rtnPackageList.options.length=0 // added for clearing all the option fields in select tag
    document.aascShipExecPackageOptionsForm.rtnPayMethod.options.length=0 // added for clearing all the option fields in select tag
    document.aascShipExecPackageOptionsForm.rtnACNumber.value='';         //setting default
    document.aascShipExecPackageOptionsForm.ajaxCCodeCServiceLevel.value='';                      // setting default
    document.aascShipExecPackageOptionsForm.ajaxCarrierCode.value='';             // setting default
    document.aascShipExecPackageOptionsForm.ajaxcarrierservicelevel.value='';     // setting default
    document.aascShipExecPackageOptionsForm.ajaxDropOffType.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxPackaging.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxCarrierPaymentTerms.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxUpsServiceLevelCode.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxDimensionReq.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxWeight.value='';                         // setting default
    
    
	/*
	document.aascShipExecPackageOptionsForm.ajaxMaxLength.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinLength.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxWidth.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinWidth.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxHeight.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinHeight.value='';                         // setting default
    
     */ 
    
  if(testForFedexUps!='100'&&testForFedexUps!='110'&&testForFedexUps!='111')
  {
  //alert("this is different");
//  fillCarrierPayMethod();
  }
  else{
  //return false;
  //
  var ajaxshipMethodParse='';
  
   if(localShipmethod!='')
  {
    ajaxshipMethodParse=localShipmethod.substring(localShipmethod.indexOf("*")+1,localShipmethod.length);
    localShipmethod=localShipmethod.substring(localShipmethod.indexOf("*")+1,localShipmethod.length);
    var finalIndex=0;
    for(i=0;i<document.aascShipExecPackageOptionsForm.rtnShipMethod.length;i++)
    {
    var testShipmethod=document.aascShipExecPackageOptionsForm.rtnShipMethod.options[document.aascShipExecPackageOptionsForm.rtnShipMethod.selectedIndex=i].value;
    testShipmethod=testShipmethod.substring(testShipmethod.indexOf("*")+1,testShipmethod.length);
      if(localShipmethod==testShipmethod)
    {
    finalIndex=i;
    }
    else
    {
         testShipmethod=testShipmethod.substring(0,testShipmethod.length-4);
         if(localShipmethod==testShipmethod)
         {
   
            finalIndex=i;
         }
    }
    }
   
    document.aascShipExecPackageOptionsForm.rtnShipMethod.selectedIndex=finalIndex;

//    alert("rtnShipmethod::::::::::::::"+document.aascShipExecPackageOptionsForm.rtnShipMethod.value);
    ajaxshipMethodParse = document.aascShipExecPackageOptionsForm.rtnShipMethod.value;
    ajaxshipMethodParse = ajaxshipMethodParse.substring(ajaxshipMethodParse.indexOf("*")+1,ajaxshipMethodParse.length);
  }
  else
  {
    ajaxshipMethodParse=ajaxshipMethodvalue.substring(ajaxshipMethodvalue.indexOf("*")+1,ajaxshipMethodvalue.length);
//  alert('ajaxshipMethodParse::'+ajaxshipMethodParse);
  }
  
  document.aascShipExecPackageOptionsForm.ajaxShipMethod.value=ajaxshipMethodParse;
  
  var locationIDTemp=document.aascShipExecPackageOptionsForm.locationID.value;
  //var orgIDTemp=document.aascShipExecPackageOptionsForm.orgID.value;
  
//  alert('ajaxshipMethodParse'+ajaxshipMethodParse);
  var xmlHttp;
  try
    {    // Firefox, Opera 8.0+, Safari    
    xmlHttp=new XMLHttpRequest();    
    }
  catch (e)
    {    // Internet Explorer    
      try
        {      
          xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
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
            document.aascShipExecPackageOptionsForm.ajaxCCodeCServiceLevel.value=xmlHttp.responseText;
 
            var responseString  = document.aascShipExecPackageOptionsForm.ajaxCCodeCServiceLevel.value;
            
            var counter=0;
            var responseStringDummy=responseString;
            var responseStringlen = responseStringDummy.length;        
//            alert(responseStringDummy);
            //alert(responseStringlen);
            
            //for(;responseStringDummy.length>1;)
            //  {
                var testindex  = responseStringDummy.indexOf('*');
              //  alert(testindex);
                if(testindex!=-1)
                {
                  var parsetest;
                  var parse1;
                  var parse2;
                  var index2;
                  var subindex1;
                  responseStringDummy=responseStringDummy.substring(testindex+1);
                  //alert(responseStringDummy);
                  if(responseStringDummy!='')
                    {
                      subindex1=responseStringDummy.indexOf('@');
                      index2=responseStringDummy.indexOf('*');
                      
                        //5_7 Merging Adhoc Bug Fixes - Parsing CarrierServiceLevel
                      var responseStringDummy2 = responseStringDummy.substring(subindex1+1);
                      index2=responseStringDummy2.indexOf('@');
                      parse2=responseStringDummy2.substring(0,index2);
//                      aler
                      
                      parse1=responseStringDummy.substring(0,subindex1);
                      //alert(parse1);
                      document.aascShipExecPackageOptionsForm.ajaxCarrierCode.value=parse1;
//                      parse2=responseStringDummy.substring(subindex1+1,index2);
                    //  alert("Parse2"+parse2);
                      document.aascShipExecPackageOptionsForm.ajaxcarrierservicelevel.value=parse2;
                      parsetest=responseStringDummy.substring(0,index2);
                      counter=counter+1;
                      
//                      responseStringDummy=responseStringDummy.substring(index2);
//                      alert(responseStringDummy);
                    }
                }
                else
                {
                    responseStringDummy='';          
                }
            //  }// end of for
              
              if(document.aascShipExecPackageOptionsForm.ajaxUpsMode.value=='UPS Direct'&&document.aascShipExecPackageOptionsForm.ajaxCarrierCode.value=='100')
              {
              getUpsServiceLevelCode();
              }
              
              getDropOffType(localDropOfType,localPackageList,localCarrierPayMethodText,localCarrierAccNumber);               
                          
          }
      }
      
    
//      alert('ajaxshipMethodParse--'+ajaxshipMethodParse);
       var indexTemp=ajaxshipMethodParse.indexOf('@@');
//       alert('indexTemp'+indexTemp);
        var shipMethod='';
       if(indexTemp < 0 )
        shipMethod= ajaxshipMethodParse;
    else
        shipMethod= ajaxshipMethodParse.substring(0,indexTemp);
//        alert('shipMethod----'+shipMethod);
      var carrierId=ajaxshipMethodParse.substring(indexTemp+2);
//      alert('carrierId :: ::'+carrierId);
      
    var url="aascAjaxRetrieveCarrierCodeServiceLevel.jsp?shipMethod="+shipMethod+"&locationIDTemp="+locationIDTemp+"&carrierId="+carrierId;
    xmlHttp.open("POST",url,false);
    xmlHttp.send(null);  
    }
}    
    
    


	
function getDropOffType(localDropOfType,localPackageList,localCarrierPayMethodText,localCarrierAccNumber)
  { 
  //alert("in fun2");
  /*alert("in DROP "+localDropOfType);
  alert("in DROP "+localPackageList);
  alert("in DROP "+localCarrierPayMethodText);
  */
  
  var xmlHttp;
  try
    {    // Firefox, Opera 8.0+, Safari    
    xmlHttp=new XMLHttpRequest();    
    }
  catch (e)
    {    // Internet Explorer    
      try
        {      
          xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
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
    document.aascShipExecPackageOptionsForm.rtnDropOfType.options.length=0 // added for clearing all the fields
    document.aascShipExecPackageOptionsForm.rtnPackageList.options.length=0 // added for clearing all the fields
    document.aascShipExecPackageOptionsForm.rtnPayMethod.options.length=0 // added for clearing all the fields
    document.aascShipExecPackageOptionsForm.rtnACNumber.value='';         //setting default
    
    document.aascShipExecPackageOptionsForm.ajaxDimensionReq.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxWeight.value='';                         // setting default
    
    document.aascShipExecPackageOptionsForm.ajaxMaxLength.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinLength.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxWidth.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinWidth.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxHeight.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinHeight.value='';                         // setting default
    
    xmlHttp.onreadystatechange=function()
      {
      if(xmlHttp.readyState==4)
        {
            document.aascShipExecPackageOptionsForm.ajaxDropOffType.value=xmlHttp.responseText;
            //document.DynaAdhocShipSaveForm.dropOfType.options1=new Option("-Select-", "-Select-", true, false); //removed default select 
            var responseString  ="";
            
            responseString  = document.aascShipExecPackageOptionsForm.ajaxDropOffType.value;
//            alert("res"+responseString);
            var responseStringlen = responseString.length;        
            
            var counter=0;
            
            
            var responseStringDummy=responseString;
            for(;responseStringDummy.length>1;)
              {
                var testindex  = responseStringDummy.indexOf('*');
      //          alert(testindex);
                if(testindex!=-1)
                {
                  var parsetest;
                  var index2;
                  responseStringDummy=responseStringDummy.substring(testindex+1);
                  
                  if(responseStringDummy!='')
                    {
                      index2=responseStringDummy.indexOf('*');
        //              alert(index2);
                      parsetest=responseStringDummy.substring(0,index2);
//                      alert("in if parsetest= "+parsetest);
                      if(parsetest=='null'||parsetest=='')
                      {
            //          alert("got null");
                      }
                      else
                      {
                      
                      
                      if(localDropOfType!='' && parsetest==localDropOfType)
                      {
                        document.aascShipExecPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, true);
                       // alert("localDropOfType "+localDropOfType);
                      }
                      else
                      {
                        document.aascShipExecPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, false);
                        //alert("got null");
                      }
                      
                      counter=counter+1;
                      }
                      
                    }
                }
                else
                {
                    responseStringDummy='';          
                }
              } // end of for
   //           alert("counter = "+counter );
              
              if(counter==0)
              {
               // alert("control going to package with counter value 0 ");
                document.aascShipExecPackageOptionsForm.ajaxDropOffType.value='';
                document.aascShipExecPackageOptionsForm.rtnDropOfType.options.length=1;
                document.aascShipExecPackageOptionsForm.rtnDropOfType.options.value='';
                document.aascShipExecPackageOptionsForm.rtnDropOfType.disabled=true;
                getPackageList(localPackageList,localCarrierPayMethodText,localCarrierAccNumber);     
              
              }
              else
              {
                if(localDropOfType!='')
                {
                  //alert("control going to package with counter not 0(for err localdropofftype not null) ");
                  //alert("in Drop "+localPackageList);
                  //alert("in Drop "+localCarrierPayMethodText);
                  getPackageList(localPackageList,localCarrierPayMethodText,localCarrierAccNumber);     
                }
                else
                {
                  //alert("in D null"+localPackageList);
                  //alert("in D null"+localCarrierPayMethodText);
                  //alert("this is normal case as default value should be first value");
                  getPackageList(localPackageList,localCarrierPayMethodText,localCarrierAccNumber);   
                }
              }
              
          }
      }
    var url="aascAjaxRetrieveDropOffType.jsp?ajaxCarrierCode="+document.aascShipExecPackageOptionsForm.ajaxCarrierCode.value+"&ajaxcarrierservicelevel="+document.aascShipExecPackageOptionsForm.ajaxcarrierservicelevel.value;
    xmlHttp.open("POST",url,false);
    xmlHttp.send(null);  
    }  //end of function
    
 

function getPackageList(localPackageList,localCarrierPayMethodText,localCarrierAccNumber)
  { 
  //alert(localPackageList);
 // alert(localCarrierPayMethodText);
//  alert("in fun3");
//  alert(document.aascShipExecPackageOptionsForm.ajaxShipMethod.value);
//  alert(document.aascShipExecPackageOptionsForm.ajaxCarrierCode.value);
//  alert(document.aascShipExecPackageOptionsForm.ajaxcarrierservicelevel.value);
//  alert(document.aascShipExecPackageOptionsForm.ajaxDropOffType.value);
//  alert(document.aascShipExecPackageOptionsForm.rtnDropOfType.value);
  
  var xmlHttp;
  try
    {    // Firefox, Opera 8.0+, Safari    
    xmlHttp=new XMLHttpRequest();    
    }
  catch (e)
    {    // Internet Explorer    
      try
        {      
          xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
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
    
    
    document.aascShipExecPackageOptionsForm.rtnPackageList.options.length=0 // added for clearing all the fields
    document.aascShipExecPackageOptionsForm.rtnPayMethod.options.length=0 // added for clearing all the fields
    document.aascShipExecPackageOptionsForm.rtnACNumber.value='';         //setting default
    
    document.aascShipExecPackageOptionsForm.ajaxDimensionReq.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxWeight.value='';                         // setting default
    
    document.aascShipExecPackageOptionsForm.ajaxMaxLength.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinLength.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxWidth.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinWidth.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxHeight.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinHeight.value='';                         // setting default
    
    xmlHttp.onreadystatechange=function()
      {
      if(xmlHttp.readyState==4)
        {
            document.aascShipExecPackageOptionsForm.ajaxPackaging.value=xmlHttp.responseText;
 //document.aascShipExecPackageOptionsForm.rtnPackageList.options1=new Option("-Select-", "-Select-", true, false);//removed default select
            var responseString  = document.aascShipExecPackageOptionsForm.ajaxPackaging.value;
  //          alert("responseString = "+responseString);
            var responseStringlen = responseString.length;        
            var counter=0;
            
               
            var responseStringDummy=responseString;
            for(;responseStringDummy.length>1;)
              {
                var testindex  = responseStringDummy.indexOf('*');
                if(testindex!=-1)
                {
                  var parsetest;
                  var index2;
                  responseStringDummy=responseStringDummy.substring(testindex+1);
                  if(responseStringDummy!='')
                    {
                      index2=responseStringDummy.indexOf('*');
                      parsetest=responseStringDummy.substring(0,index2);
    //                  alert("parsetest  "+parsetest);
                      if(parsetest=='null'||parsetest=='')
                      {
      //                alert("no value for 3");
                      }
                      else
                      {
                      
                      
                      if(localPackageList!='' && parsetest==localPackageList)
                      {
                        document.aascShipExecPackageOptionsForm.rtnPackageList.options[counter]=new Option(parsetest, parsetest, true, true);
                       // alert("localPackageList "+localPackageList+"   "+parsetest);
                      }
                      else
                      {
                        document.aascShipExecPackageOptionsForm.rtnPackageList.options[counter]=new Option(parsetest, parsetest, true, false);
                       // alert("got null"+"   "+parsetest);
                      }
                      
                      counter=counter+1;
                      }
                    }
                }
                else
                {
                    responseStringDummy='';          
                }
              }// end of for
        //      alert("counterat fun3 = "+counter);
              
              if(counter==0)
              {
                //alert("control going to carrierpay term with counter 0");
                document.aascShipExecPackageOptionsForm.ajaxPackaging.value='';
                document.aascShipExecPackageOptionsForm.rtnPackageList.options.length=1;
                document.aascShipExecPackageOptionsForm.rtnPackageList.options.value='';
                document.aascShipExecPackageOptionsForm.rtnPackageList.disabled=true;
                getCarrierPayMethod(localCarrierPayMethodText,localCarrierAccNumber);              
              }
              else
              {
                if(localPackageList!='')
                {
                  //alert("control going to carrier with counter not 0(for err  localpackagelist)");
                 // alert("in Pack "+localCarrierPayMethodText);
                  getCarrierPayMethod(localCarrierPayMethodText,localCarrierAccNumber);     
                }
                else
                {
                  //alert("in pack"+localPackageList);
                  //alert("this is the normal case as default value should be first selected");
                  getCarrierPayMethod(localCarrierPayMethodText,localCarrierAccNumber);
                }
                
              }
              
          }
      }
    var url="aascAjaxRetrievePackaging.jsp?ajaxCarrierCode="+document.aascShipExecPackageOptionsForm.ajaxCarrierCode.value+"&ajaxcarrierservicelevel="+document.aascShipExecPackageOptionsForm.ajaxcarrierservicelevel.value+"&dropOfType="+document.aascShipExecPackageOptionsForm.rtnDropOfType.value;
    xmlHttp.open("POST",url,false);
    xmlHttp.send(null);  
    }    //end of Method
    
    
    function getCarrierPayMethod(localCarrierPayMethodText,localCarrierAccNumber)
  { 

// alert("localCarrierPayMethodText :"+localCarrierPayMethodText);
  var xmlHttp;
  try
    {    // Firefox, Opera 8.0+, Safari    
    xmlHttp=new XMLHttpRequest();    
    }
  catch (e)
    {    // Internet Explorer    
      try
        {      
          xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
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
    
    
    document.aascShipExecPackageOptionsForm.rtnPayMethod.options.length=0 // added for clearing all the fields
    document.aascShipExecPackageOptionsForm.rtnACNumber.value='';         //setting default
    
    document.aascShipExecPackageOptionsForm.ajaxDimensionReq.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxWeight.value='';                         // setting default
    
    document.aascShipExecPackageOptionsForm.ajaxMaxLength.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinLength.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxWidth.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinWidth.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxHeight.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinHeight.value='';                         // setting default
    
    xmlHttp.onreadystatechange=function()
      {
      if(xmlHttp.readyState==4)
        {
            document.aascShipExecPackageOptionsForm.ajaxCarrierPaymentTerms.value=xmlHttp.responseText;
 //document.aascShipExecPackageOptionsForm.rtnPayMethod.options1=new Option("-Select-", "-Select-", true, false);//removed default select
            var responseString  = document.aascShipExecPackageOptionsForm.ajaxCarrierPaymentTerms.value;
            var responseStringlen = responseString.length;        
            var counter=0;
            //alert("responseString  "+responseString);
            var responseStringDummy=responseString;
            for(;responseStringDummy.length>1;)
              {
                var testindex  = responseStringDummy.indexOf('*');
                //alert(testindex);
                if(testindex!=-1)
                {
                  var parsetest;
                  var parsetestvalue;
                  var index2;
                  responseStringDummy=responseStringDummy.substring(testindex+1);
                  //alert(responseStringDummy);
                  if(responseStringDummy!='')
                    {
                      index2=responseStringDummy.indexOf('@');
                      
                      parsetest=responseStringDummy.substring(0,index2);
                      parsetestvalue=responseStringDummy.substring(index2+1,responseStringDummy.indexOf('*'));
                  //    alert("parsetestvalue:"+parsetestvalue);
                      
                      if(parsetestvalue=='PP' || parsetestvalue=='TP' )
                      {
                      
                      if(localCarrierPayMethodText!='' && parsetestvalue==localCarrierPayMethodText)
                      {
                      //  alert("setting it to as previous  "+localCarrierPayMethodText);
                        document.aascShipExecPackageOptionsForm.rtnPayMethod.options[counter]=new Option(parsetest,parsetestvalue, true, true);
                        
                        var fillCarrierAccNumber=localCarrierAccNumber;
                            if(fillCarrierAccNumber!='')
                            {
                            //alert("not null as"+fillCarrierAccNumber);
                            document.aascShipExecPackageOptionsForm.rtnACNumber.value=fillCarrierAccNumber;
                            }
                            
                      }
                      else
                      { 
                          if((parsetestvalue=='PP') && localCarrierPayMethodText=='')
                          { 
                        //   alert("setting it to default");
                            document.aascShipExecPackageOptionsForm.rtnPayMethod.options[counter]=new Option(parsetest,parsetestvalue, true, true);
                          }
                          else
                          {
                       //  alert("other");
                          document.aascShipExecPackageOptionsForm.rtnPayMethod.options[counter]=new Option(parsetest,parsetestvalue, true, false);                      
                          }
                      }
                      counter=counter+1;
                      }
                      // counter=counter+1; moved from here to inside if above 
                    }
                }
                else
                {
                    responseStringDummy='';          
                }
              }//end of for
              getAccountNumber();
          }
      }
    var url="aascAjaxRetrieveCarrierPayTerms.jsp?ajaxCarrierCode="+document.aascShipExecPackageOptionsForm.ajaxCarrierCode.value+"&ajaxcarrierservicelevel="+document.aascShipExecPackageOptionsForm.ajaxcarrierservicelevel.value+"&dropOfType="+document.aascShipExecPackageOptionsForm.rtnDropOfType.value+"&packageList="+document.aascShipExecPackageOptionsForm.rtnPackageList.value;
    xmlHttp.open("POST",url,false);
    xmlHttp.send(null);  
    }        //end of the method
    
    function getAccountNumber()
{
 // alert("got here");
  //
    document.aascShipExecPackageOptionsForm.ajaxDimensionReq.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxWeight.value='';                         // setting default
    
    document.aascShipExecPackageOptionsForm.ajaxMaxLength.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinLength.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxWidth.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinWidth.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMaxHeight.value='';                         // setting default
    document.aascShipExecPackageOptionsForm.ajaxMinHeight.value='';                         // setting default
  //
  
  var ajaxshipMethodvalue=document.aascShipExecPackageOptionsForm.rtnShipMethod.value;
  var testForFedexUps=ajaxshipMethodvalue.substring(0,ajaxshipMethodvalue.indexOf("|"));
  //alert("testForFedexUps  "+testForFedexUps);
  //
  if(testForFedexUps!='100'&&testForFedexUps!='110'&&testForFedexUps!='111')
  {
   // alert("this is different");
    //ex();
  }
  else
  {
  
      var CarrierPayMethodTextstr=document.aascShipExecPackageOptionsForm.rtnPayMethod.value;
      //alert(CarrierPayMethodTextstr);
      if(CarrierPayMethodTextstr=='PP')
      {
        var str1=document.aascShipExecPackageOptionsForm.rtnShipMethod.options[document.aascShipExecPackageOptionsForm.rtnShipMethod.selectedIndex].id;
      //  alert(str1);
        if(str1!='')
        {
          document.aascShipExecPackageOptionsForm.rtnACNumber.value=str1;
          document.aascShipExecPackageOptionsForm.rtnACNumber.readOnly=true;
        }
      }
     getCarrierCombValues();// getting caombination values
  }
}

function checkShipToAddress()
{ 
      var rtnShipToCompany=document.aascShipExecPackageOptionsForm.rtnShipToCompany.value;
       var rtnShipToLine1=document.aascShipExecPackageOptionsForm.rtnShipToLine1.value;
      var rtnShipToLine2=document.aascShipExecPackageOptionsForm.rtnShipToLine2.value;
      var rtnShipFromCompany=document.aascShipExecPackageOptionsForm.rtnShipFromCompany.value;
      var rtnShipFromLine1=document.aascShipExecPackageOptionsForm.rtnShipFromLine1.value;
      var rtnShipFromLine2=document.aascShipExecPackageOptionsForm.rtnShipFromLine2.value;
       //var txtShipToAddressLine3=trim(document.aascShipExecPackageOptionsForm.txtShipToAddressLine3.value);
      var rtnShipFromCity=document.aascShipExecPackageOptionsForm.rtnShipFromCity.value;
       var rtnShipToCity=document.aascShipExecPackageOptionsForm.rtnShipToCity.value;
       
       var rtnShipFromSate=document.aascShipExecPackageOptionsForm.rtnShipFromSate.value;
       var rtnShipToState=document.aascShipExecPackageOptionsForm.rtnShipToState.value;
       var rtnShipFromZip=document.aascShipExecPackageOptionsForm.rtnShipFromZip.value;
       var rtnShipToZip=document.aascShipExecPackageOptionsForm.rtnShipToZip.value;
           
      
      var alertStr="";
      var str=document.aascShipExecPackageOptionsForm.rtnShipMethod.options[document.aascShipExecPackageOptionsForm.rtnShipMethod.selectedIndex].value;
        var carrierCode=str.substring(0,3);
      //var upsMode=trim(document.aascShipExecPackageOptionsForm.upsMode.value);
           
      if((rtnShipFromCompany==null || rtnShipFromCompany==""))
      {
       alertStr="Ship From Customer Name should not be null. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipFromCompany.focus();
      }
       if((rtnShipToCompany==null || rtnShipToCompany==""))
      {
      alertStr=alertStr+"Ship To Customer Name should not be null. \n";
      //document.aascShipExecPackageOptionsForm.shipToCompanyName.focus();
      }
      
      if( rtnShipToCompany.length >35)
      {
      alertStr=alertStr+"Length of Ship To Company Name is "+rtnShipToCompany.length +" Char. Please limit to 35 Char. \n";
     // document.aascShipExecPackageOptionsForm.rtnShipToCompany.focus();
      }

	  if( rtnShipFromCompany.length >35)
      {
//       alertStr=alertStr+"Length of Ship From Company Name is "+rtnShipFromCompany.length +" Char. Please limit to 35 Char. \n";
     // document.aascShipExecPackageOptionsForm.rtnShipFromCompany.focus();
      }

      if(rtnShipToLine1==null || rtnShipToLine1=="")
      {
         alertStr=alertStr+"Ship To AddressLine1 should not be null. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipFromLine1.focus();
      }
        if(rtnShipFromLine1==null || rtnShipFromLine1=="")
      {
       alertStr=alertStr+"Ship From  AddressLine1 should not be null. \n";
     // document.aascShipExecPackageOptionsForm.rtnShipToLine1.focus();
      }

      if( (rtnShipToLine1.length >35 && carrierCode=="100") || (rtnShipToLine1.length >35 && carrierCode=="111") || (rtnShipToLine1.length >35 && carrierCode=="110"))
      {
      alertStr=alertStr+"Length of Ship To AddressLine1 is "+rtnShipToLine1.length+ " Char. Please limit to 35 Char. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipToLine1.focus();
      }


     if( (rtnShipFromLine1.length >35 && carrierCode=="100") || (rtnShipFromLine1.length >35 && carrierCode=="111") || (rtnShipFromLine1.length >35 && carrierCode=="110"))
      {
//      alertStr=alertStr+"Length of Ship From AddressLine1 is "+rtnShipFromLine1.length+ " Char. Please limit From 35 Char. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipFromLine1.focus();
      }
   
  


     if( (rtnShipToLine2.length >35 && carrierCode=="100") || (rtnShipToLine2.length >35 && carrierCode=="111") || (rtnShipToLine2.length >35 && carrierCode=="110"))
      {
      alertStr=alertStr+"Length of Ship To AddressLine2 is "+rtnShipToLine2.length+ " Char. Please limit to 35 Char. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipToLine2.focus();
      }


     if( (rtnShipFromLine2.length >35 && carrierCode=="100") || (rtnShipFromLine2.length >35 && carrierCode=="111") || (rtnShipFromLine2.length >35 && carrierCode=="110"))
      {
      alertStr=alertStr+"Length of Ship From AddressLine2 is "+rtnShipFromLine2.length+ " Char. Please limit From 35 Char. \n";
     // document.aascShipExecPackageOptionsForm.rtnShipFromLine2.focus();
      }





      if(rtnShipToCity==null || rtnShipToCity=="")
      {
      alertStr=alertStr+"Ship To City should not be null. \n";
     // document.aascShipExecPackageOptionsForm.rtnShipToCity.focus();
      }
       if( rtnShipToCity.length >20 && carrierCode=="100")
      {
      alertStr=alertStr+" Length of Ship To City is "+rtnShipToCity.length+ " Char. Please limit to 20 Char. \n";
     // document.aascShipExecPackageOptionsForm.rtnShipToCity.focus();
      }
      if( rtnShipToCity.length >35 && carrierCode=="100")
      {
      alertStr=alertStr+"Length of Ship To City is "+rtnShipToCity.length+ " Char. Please limit to 35 Char. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipToCity.focus();
      }


	  if(rtnShipFromCity==null || rtnShipFromCity=="")
      {
      alertStr=alertStr+"Ship From City should not be null. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipFromCity.focus();
      }
       if( rtnShipFromCity.length >20 && carrierCode=="100")
      {
      alertStr=alertStr+" Length of Ship From City is "+rtnShipFromCity.length+ " Char. Please limit to 20 Char. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipFromCity.focus();
      }
      if( rtnShipFromCity.length >35 && carrierCode=="100")
      {
      alertStr=alertStr+"Length of Ship From City is "+rtnShipFromCity.length+ " Char. Please limit to 35 Char. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipToCity.focus();
      }
      
      
      if(rtnShipFromSate==null || rtnShipFromSate=="")
      {
      alertStr=alertStr+"Ship From State should not be null. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipFromSate.focus();
      }


if(rtnShipToState==null || rtnShipToState=="")
      {
      alertStr=alertStr+"Ship To State should not be null. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipToState.focus();
      }



if(rtnShipFromZip ==null || rtnShipFromZip =="")
      {
      alertStr=alertStr+"Ship From Zip Code should not be null. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipFromZip.focus();
      }




if(rtnShipToZip ==null || rtnShipToZip =="")
      {
      alertStr=alertStr+"Ship To Zip Code should not be null. \n";
      //document.aascShipExecPackageOptionsForm.rtnShipToZip.focus();
      }
           
      if(alertStr != "" && alertStr !=null)
      {
      alert(alertStr);
      return false;
      }
    //end of if(carrierCode=="100" && upsMode=="UPS Direct")
    
    
  if(document.aascShipExecPackageOptionsForm.rtnShipFromPhone.value=="")
  {

				alert("Please Enter The Ship From Phone Number :");

				document.aascShipExecPackageOptionsForm.rtnShipFromPhone.readOnly=false;

				document.aascShipExecPackageOptionsForm.rtnShipFromPhone.focus();

				return false;

			}



	else{

	var rtnShipFromPhone=document.aascShipExecPackageOptionsForm.rtnShipFromPhone.value;
//Commented by Suman Gunda for removing validation for return shipment phone number
		//		if((rtnShipFromPhone.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (rtnShipFromPhone.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) 
//     if(((rtnShipFromPhone.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (rtnShipFromPhone.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) || rtnShipFromPhone.length < 10) 
//				
//        {
//
//					alert("Please Enter Valid 10 Digit Ship From Phone Number :");
//
//					document.aascShipExecPackageOptionsForm.rtnShipFromPhone.focus();
//
//					return false;
//
//				}

			}





if(document.aascShipExecPackageOptionsForm.rtnShipToPhone.value==""){

				alert("Please Enter The Ship To Phone Number :");

				document.aascShipExecPackageOptionsForm.rtnShipToPhone.readOnly=false;

				document.aascShipExecPackageOptionsForm.rtnShipToPhone.focus();

				return false;

			}



	else{

	var rtnShipToPhone=document.aascShipExecPackageOptionsForm.rtnShipToPhone.value;
// Commented by Suman Gunda for removing validation for return shipment phone number
			//	if((rtnShipToPhone.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (rtnShipToPhone.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) 
//      if(((rtnShipToPhone.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (rtnShipToPhone.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) || rtnShipToPhone.length <10 ) 
//				{
//
//					alert("Please Enter Valid 10 Digit Ship To Phone Number :");
//
//					document.aascShipExecPackageOptionsForm.rtnShipToPhone.focus();
//
//					return false;
//
//				}

			}  
   } 
 
 function getCarrierCombValues()
  { 
  
  //  alert("in fun4");
  /*alert(document.DynaAdhocShipSaveForm.ajaxShipMethod.value);
  alert(document.DynaAdhocShipSaveForm.ajaxCarrierCode.value);
  alert(document.DynaAdhocShipSaveForm.ajaxcarrierservicelevel.value);
  alert(document.DynaAdhocShipSaveForm.ajaxDropOffType.value);
  alert(document.DynaAdhocShipSaveForm.dropOfType.value);
  alert(document.DynaAdhocShipSaveForm.packageList.value);*/
  //var carrierLocalText=document.DynaAdhocShipSaveForm.CarrierPayMethodText.options[document.DynaAdhocShipSaveForm.CarrierPayMethodText.selectedIndex].text;
  
  var sIndex=document.aascShipExecPackageOptionsForm.rtnPayMethod.selectedIndex;
  var carrierLocalText="";
  if(sIndex=="-1")
  {
  carrierLocalText="";
  }
  else
  {
  carrierLocalText=document.aascShipExecPackageOptionsForm.rtnPayMethod.options[sIndex].text;
  }
  
  
  
  var xmlHttp;
  try
    {    // Firefox, Opera 8.0+, Safari    
    xmlHttp=new XMLHttpRequest();    
    }
  catch (e)
    {    // Internet Explorer    
      try
        {      
          xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
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
            var carrierCombValues=xmlHttp.responseText;
 
            var responseString  = carrierCombValues;
            var responseStringlen = responseString.length;        
            var counter=0;
            //alert("responseString  "+responseString);
            var responseStringDummy=responseString;
            
            //
            var startIndex  ="";
            var dimReqd     ="";
            var maxWeight   ="";
            var maxLength   ="";
            var minLength   ="";
            var maxWidth    ="";
            var minWidth    ="";
            var maxHeight   ="";
            var minHeight   ="";
            
            startIndex  = responseStringDummy.indexOf('*');
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            //alert(responseStringDummy);
            
            startIndex  = responseStringDummy.indexOf('*');
            dimReqd     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(dimReqd=='null')
            {
            document.aascShipExecPackageOptionsForm.ajaxDimensionReq.value='';        
            }
            else
            {
            
            document.aascShipExecPackageOptionsForm.ajaxDimensionReq.value=dimReqd;         
            }
            //alert(dimReqd);
            //alert(responseStringDummy);
            
            
            startIndex  = responseStringDummy.indexOf('*');
            maxWeight     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(maxWeight=='null')
            {
            document.aascShipExecPackageOptionsForm.ajaxMaxWeight.value='';        
            }
            else
            {
            
            document.aascShipExecPackageOptionsForm.ajaxMaxWeight.value=maxWeight; 
            }
            //alert(maxWeight);
            //alert(responseStringDummy);
            
            
            startIndex  = responseStringDummy.indexOf('*');
            maxLength     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(maxLength=='null')
            {
            document.aascShipExecPackageOptionsForm.ajaxMaxLength.value='';       
            }
            else
            {
             
            document.aascShipExecPackageOptionsForm.ajaxMaxLength.value=maxLength;
            }
            //alert(maxLength);
            //alert(responseStringDummy);
            
            
            startIndex  = responseStringDummy.indexOf('*');
            minLength     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(minLength=='null')
            {
            document.aascShipExecPackageOptionsForm.ajaxMinLength.value='';       
            }
            else
            {
             
            document.aascShipExecPackageOptionsForm.ajaxMinLength.value=minLength;
            }
            //alert(minLength);
            //alert(responseStringDummy);
            
            
            startIndex  = responseStringDummy.indexOf('*');
            maxWidth     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(maxWidth=='null')
            {
            document.aascShipExecPackageOptionsForm.ajaxMaxWidth.value='';
            }
            else
            {
            
            document.aascShipExecPackageOptionsForm.ajaxMaxWidth.value=maxWidth;
            }
            //alert(maxWidth);
            //alert(responseStringDummy);
            
            
            startIndex  = responseStringDummy.indexOf('*');
            minWidth     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(minWidth=='null')
            {
            document.aascShipExecPackageOptionsForm.ajaxMinWidth.value='';
            }
            else
            {
            
            document.aascShipExecPackageOptionsForm.ajaxMinWidth.value=minWidth;
            }
            //alert(minWidth);
            //alert(responseStringDummy);
            
            
            startIndex  = responseStringDummy.indexOf('*');
            maxHeight     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(maxHeight=='null')
            {
            document.aascShipExecPackageOptionsForm.ajaxMaxHeight.value='';
            }
            else
            {
            
            document.aascShipExecPackageOptionsForm.ajaxMaxHeight.value=maxHeight;
            }
        
            
            startIndex  = responseStringDummy.indexOf('*');
            minHeight     = responseStringDummy.substring(0,startIndex);
            
            if(minHeight=='null')
            {
            document.aascShipExecPackageOptionsForm.ajaxMinHeight.value='';
            }
            else
            {
            
            document.aascShipExecPackageOptionsForm.ajaxMinHeight.value=minHeight;
            }
    
        }
      }
    var url="aascAjaxGetCarrierCombValues.jsp?ajaxCarrierCode="+document.aascShipExecPackageOptionsForm.ajaxCarrierCode.value+"&ajaxcarrierservicelevel="+document.aascShipExecPackageOptionsForm.ajaxcarrierservicelevel.value+"&dropOfType="+document.aascShipExecPackageOptionsForm.rtnDropOfType.value+"&packageList="+document.aascShipExecPackageOptionsForm.rtnPackageList.value+"&CarrierPayMethodText="+carrierLocalText;
    xmlHttp.open("POST",url,false);
    xmlHttp.send(null);  
    }

  function setValue()	{
  var carrierTerm =document.aascShipExecPackageOptionsForm.rtnPayMethod.value;
  var str=document.aascShipExecPackageOptionsForm.rtnShipMethod.options[document.aascShipExecPackageOptionsForm.rtnShipMethod.selectedIndex].value;
  var carrierCode=str.substring(0,3);
  //var upsMode=trim(document.aascShipExecPackageOptionsForm.upsMode.value);
  var payMethodTemp=document.aascShipExecPackageOptionsForm.rtnPayMethod.value;
    
   	
  if(document.aascShipExecPackageOptionsForm.rtnPayMethod.value=='PP')
			{

				document.aascShipExecPackageOptionsForm.rtnACNumber.value=document.aascShipExecPackageOptionsForm.CarrierAcHidden.value;

				if(document.aascShipExecPackageOptionsForm.rtnACNumber.value == "")
					{
				    document.aascShipExecPackageOptionsForm.rtnACNumber.readOnly = false;
				    }

				   else
				  {
					document.aascShipExecPackageOptionsForm.rtnACNumber.readOnly = true;
				  }

		  }
  else{
			document.aascShipExecPackageOptionsForm.rtnACNumber.readOnly=false;
			document.aascShipExecPackageOptionsForm.rtnACNumber.value="";
	 }

    if(document.aascShipExecPackageOptionsForm.rtnPayMethod.value=="FC")
    {
       
       // document.DynaAdhocShipSaveForm.CarrierACNumberText.value= document.DynaAdhocShipSaveForm.fCACNumber.value;
        
               
          }
    }


function holdatLocationfn(){

//alert("Pkg count:3181:"+document.aascShipExecPackageOptionsForm.packCount.value);
var  packCount=document.aascShipExecPackageOptionsForm.packCount.value;

//&& window.opener.DynaAdhocShipSaveForm.flagVoid.value == "Y" && window.opener.DynaAdhocShipSaveForm.flagShip.value != "Y"
if(document.aascShipExecPackageOptionsForm.holdAtLocation.checked == true)
{
   document.aascShipExecPackageOptionsForm.holdAtLocation.value = 'Y';

   document.aascShipExecPackageOptionsForm.halPhone.disabled = false;

   document.aascShipExecPackageOptionsForm.halAddrLine1.disabled = false;
   
   document.aascShipExecPackageOptionsForm.halAddrLine2.disabled = false;

   document.aascShipExecPackageOptionsForm.halCity.disabled = false;

   document.aascShipExecPackageOptionsForm.halState.disabled = false;
   
   document.aascShipExecPackageOptionsForm.halZip.disabled = false;
   
   
   //if((window.opener.document.DynaAdhocShipSaveForm.packageSaveCheck1.value=="Y" || window.opener.document.getElementById('flagShip1').value =="Y") &&( window.opener.document.getElementById('halZipID'+packCount).value != null && window.opener.document.getElementById('halZipID'+packCount).value != '') )
   if((window.opener.document.getElementById('flagShip1').value =="Y") &&( window.opener.document.getElementById('halZipID'+packCount).value != null && window.opener.document.getElementById('halZipID'+packCount).value != '') )
   {
    document.aascShipExecPackageOptionsForm.halPhone.value = window.opener.document.getElementById('halPhoneID'+packCount).value;
    document.aascShipExecPackageOptionsForm.halAddrLine1.value=window.opener.document.getElementById('halAddrLine1ID'+packCount).value;
    document.aascShipExecPackageOptionsForm.halAddrLine2.value=window.opener.document.getElementById('halAddrLine2ID'+packCount).value;
    document.aascShipExecPackageOptionsForm.halCity.value=window.opener.document.getElementById('halCityID'+packCount).value;
    document.aascShipExecPackageOptionsForm.halState.value=window.opener.document.getElementById('halStateID'+packCount).value;
    document.aascShipExecPackageOptionsForm.halZip.value =window.opener.document.getElementById('halZipID'+packCount).value;
   }
   else
   {
    document.aascShipExecPackageOptionsForm.halPhone.value = window.opener.document.getElementById('phoneNumberId').value;
    document.aascShipExecPackageOptionsForm.halAddrLine1.value=trim(window.opener.document.getElementById('shipToAddressId').value);
    document.aascShipExecPackageOptionsForm.halAddrLine2.value=window.opener.document.getElementById('shipToAddrLine2Id').value;
     
    document.aascShipExecPackageOptionsForm.halCity.value=window.opener.document.getElementById('city').value;
    document.aascShipExecPackageOptionsForm.halState.value=window.opener.document.getElementById('state').value;
    document.aascShipExecPackageOptionsForm.halZip.value =window.opener.document.getElementById('zip').value;
    
   }
   // document.aascShipExecPackageOptionsForm.holdAtLocation.disabled = false;
}
else
{
   document.aascShipExecPackageOptionsForm.holdAtLocation.value = 'N';
   document.aascShipExecPackageOptionsForm.halPhone.disabled = true;
   document.aascShipExecPackageOptionsForm.halAddrLine1.disabled = true;
   document.aascShipExecPackageOptionsForm.halAddrLine2.disabled = true;
   document.aascShipExecPackageOptionsForm.halCity.disabled = true;
   document.aascShipExecPackageOptionsForm.halState.disabled = true;
   document.aascShipExecPackageOptionsForm.halZip.disabled = true;   
   document.aascShipExecPackageOptionsForm.halPhone.value = '';
   document.aascShipExecPackageOptionsForm.halAddrLine1.value = '';
   document.aascShipExecPackageOptionsForm.halAddrLine2.value = '';
   document.aascShipExecPackageOptionsForm.halCity.value = '';
   document.aascShipExecPackageOptionsForm.halState.value = '';
   document.aascShipExecPackageOptionsForm.halZip.value = '';
}


}

function halValidate()
{
  var packCount=document.aascShipExecPackageOptionsForm.packageCount.value;
  var halPhone=document.aascShipExecPackageOptionsForm.halPhone.value;
  var halZip=document.aascShipExecPackageOptionsForm.halZip.value;
// Suman Gunda commented for halPhone number validaiton #2079  
//  if(isNaN(halPhone))
//  {
//   alert("The Phone No. should be a numeric value"); 
//   document.aascShipExecPackageOptionsForm.halPhone.focus();
//   return false;
//  }
  /* commented the code for fixing issue number 624 on 14/10/08 
 if(isNaN(halZip))
  {
   alert("The Postal Code should be a numeric value");
   document.aascShipExecPackageOptionsForm.halZip.focus();
   return false;
  } */
  }
  
  function hazardousMaterialFn()
{
 var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;
 
  if(document.aascShipExecPackageOptionsForm.HazMatFlag.checked == true)
  {
    document.aascShipExecPackageOptionsForm.HazMatFlag.value = 'Y';
    document.aascShipExecPackageOptionsForm.HazardousMaterialType.disabled = false;
    document.aascShipExecPackageOptionsForm.HazardousMaterialClass.disabled = false;
    
    if(str.substring(0,3)=="100")
    {
    //pavan
     document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.disabled = false;
     document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.disabled = false;
     
     document.aascShipExecPackageOptionsForm.HazardousMaterialId.disabled = false;
    document.aascShipExecPackageOptionsForm.HazMatIdentificationNo.disabled = false;
    document.aascShipExecPackageOptionsForm.HazMatDOTLabelType.disabled = false;
    document.aascShipExecPackageOptionsForm.HazMatEmergencyContactNo.disabled = false;
    document.aascShipExecPackageOptionsForm.HazMatEmergencyContactName.disabled = false;
    document.aascShipExecPackageOptionsForm.HazardousMaterialPkgGroup.disabled = false;
     //alert(document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.disabled);
     //Added on Jun-03-2011
       document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.disabled = false;
       document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.disabled = false;
    //End on Jun-03-2011
    document.aascShipExecPackageOptionsForm.HazMatPackInstructions.disabled = false;
    //Added on Jul-05-2011
         document.aascShipExecPackageOptionsForm.HazMatTechnicalName.disabled = false;
     //End on Jul-05-2011
        document.aascShipExecPackageOptionsForm.HazMatSignatureName.disabled = false;
    }
    else
    {
     document.aascShipExecPackageOptionsForm.HazardousMaterialId.disabled = true;
    document.aascShipExecPackageOptionsForm.HazMatIdentificationNo.disabled = true;
    document.aascShipExecPackageOptionsForm.HazMatDOTLabelType.disabled = true;
    document.aascShipExecPackageOptionsForm.HazMatEmergencyContactNo.disabled = true;
    document.aascShipExecPackageOptionsForm.HazMatEmergencyContactName.disabled = true;
    document.aascShipExecPackageOptionsForm.HazardousMaterialPkgGroup.disabled = true;
document.aascShipExecPackageOptionsForm.HazMatPackInstructions.disabled = true;
    //Added on Jun-03-2011
       document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.disabled = true;
       document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.disabled = true;
    //End on Jun-03-2011
    
     //Added on Jul-05-2011
         document.aascShipExecPackageOptionsForm.HazMatTechnicalName.disabled = true;
     //End on Jul-05-2011
         document.aascShipExecPackageOptionsForm.HazMatSignatureName.disabled = true;
    }
    var  packCount=document.aascShipExecPackageOptionsForm.packCount.value;

    
  // document.aascShipExecPackageOptionsForm.HazardousMaterialWeight.value = window.opener.DynaAdhocShipSaveForm['weight'+packCount].value;
  // document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.value = window.opener.DynaAdhocShipSaveForm['uom'+packCount].value;
     
    
  }
  else{
    document.aascShipExecPackageOptionsForm.HazMatFlag.value = 'N';
    document.aascShipExecPackageOptionsForm.HazardousMaterialType.disabled = true;
    document.aascShipExecPackageOptionsForm.HazardousMaterialClass.disabled = true;
    
    //pavan
    document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.disabled = true;
    
    document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.value ='';
    document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.disabled = true;
    
   // document.aascShipExecPackageOptionsForm.HazardousMaterialType.value = '';
   // document.aascShipExecPackageOptionsForm.HazardousMaterialClass.value = ''
   
    document.aascShipExecPackageOptionsForm.HazardousMaterialId.disabled = true;
    document.aascShipExecPackageOptionsForm.HazMatIdentificationNo.disabled = true;
    document.aascShipExecPackageOptionsForm.HazMatDOTLabelType.disabled = true;
    document.aascShipExecPackageOptionsForm.HazMatEmergencyContactNo.disabled = true;
    document.aascShipExecPackageOptionsForm.HazMatEmergencyContactName.disabled = true;
    document.aascShipExecPackageOptionsForm.HazardousMaterialPkgGroup.disabled = true;
    
    //Added on Jun-03-2011
       document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.disabled = true;
       document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.disabled = true;
    //End on Jun-03-2011
    
    //Added on Jul-05-2011
         document.aascShipExecPackageOptionsForm.HazMatTechnicalName.disabled = true;
     //End on Jul-05-2011
        document.aascShipExecPackageOptionsForm.HazMatSignatureName.disabled = true;
  }

}


// Added by Sambit on 25/07/2008  

function getHazMatClass()
   {
   //alert("Inside 2778");
   
   var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;

      //var carrierCode = document.aascShipExecPackageOptionsForm.ajaxCarrierCode.value;//110;
      var carrierCode = str.substring(0,3)
   var lookUpMeaning = document.aascShipExecPackageOptionsForm.HazardousMaterialType.value;
  
  //alert("CarrierCode:"+carrierCode);
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
        {        alert("Your browser does not support AJAX!");  
        return false;      
        }    
        }  
        
        }
    xmlHttp.onreadystatechange=function()
      {
      if(xmlHttp.readyState==4)
        {
//            alert('xmlHttp.readyState :'+xmlHttp.readyState);
//            alert('xmlHttp.responseText ::'+trim(xmlHttp.responseText));
            var responseStringDummy=trim(xmlHttp.responseText);
            
            //alert("REsp Strng"+responseStringDummy);
            var index = responseStringDummy.indexOf("@@@");
            responseStringDummy = responseStringDummy.substr(index+3);
//            alert("responseStringDummy : "+responseStringDummy);
            var testindex  = responseStringDummy.indexOf('***');
            var optionValue;
            var i=1;
          //  alert("in if(xmlHttp.readyState==4)");
          //  alert("testindex:"+testindex);
           // alert("globalvar :"+globalvar);
           
           //document.aascShipExecPackageOptionsForm.HazardousMaterialClass.options.length=1;
           document.aascShipExecPackageOptionsForm.HazardousMaterialClass.options1 = new Option("--Select--","--Select--",true,true);
           //alert("test Index:"+testindex);
            while(testindex!=-1)
            {
            optionValue=responseStringDummy.substr(0,testindex);
            //alert("2830"+optionValue);
            if(globalvar !="" && globalvar != null){
            //alert("Inside if 2831");
            
            if(globalvar!='' && globalvar==optionValue)
                      {
                       // document.aascShipExecPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, true);
                       document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialClass.options[i] = new Option(optionValue,optionValue, true, true);
                      }
                      else
                      {
                       // document.aascShipExecPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, false);
                       document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialClass.options[i] = new Option(optionValue,optionValue, true, false); 
                      }
            
            
            }else{
            
                document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialClass.options[i] = new Option(optionValue,optionValue);
            }
            responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
            testindex  = responseStringDummy.indexOf('***');
            i++;
	          }
            globalvar="";
        }
      }
   
    var url="aascAjaxHazMatClass.jsp?carrierCode="+carrierCode+"&lookUpMeaning="+lookUpMeaning;
    xmlHttp.open("POST",url,false);  // Calling 
    xmlHttp.send(null); 
      
      //alert("2857"+globalvar);
    
    }
    
function trim(str)
{
  return str.replace(/^\s*|\s*$/g,"");
}
    // End of 25/07/2008
    
function getHazMatValues()
{
//   alert("Inside getHazMatValues method ");
   getHazMatClass();
   
    var startIndex  ="";
            
            var quantity   ="";
            var unit   ="";
            var IdNo   ="";
            
   
   var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;

      //var carrierCode = document.aascShipExecPackageOptionsForm.ajaxCarrierCode.value;//110;
      var carrierCode = str.substring(0,3)
      
     // alert("In values from par:"+document.aascShipExecPackageOptionsForm.HazardousMaterialId.value);
      //alert("In values globalDefaultHazMatId:"+globalDefaultHazMatId);
      hazMatId = document.aascShipExecPackageOptionsForm.HazardousMaterialId.value;
        //alert("After haz ID:"+hazMatId);
     if((globalDefaultHazMatId!="")&&(hazMatId==""))
      {
      //alert("Inside IF");
        hazMatId = globalDefaultHazMatId;
      }
      else
      {
     // alert("Inside else");
        hazMatId = document.aascShipExecPackageOptionsForm.HazardousMaterialId.value;
      }
    
      var locationId = window.opener.document.getElementById('locationId').value;
      
      //alert("Inv Org:"+InvOrgId);
      
      //var lookUpMeaning = document.aascShipExecPackageOptionsForm.HazardousMaterialType.value;
  
  //alert("CarrierCode:"+carrierCode);
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
        {        alert("Your browser does not support AJAX!");  
        return false;      
        }    
        }  
        
        }
    xmlHttp.onreadystatechange=function()
      {
      if(xmlHttp.readyState==4)
        {
         
            var responseStringDummy=trim(xmlHttp.responseText);
            
//            alert("1111 responseStringDummy:"+responseStringDummy);
            //alert("1111 hazMatId:"+hazMatId);
           //On load both values are same            
           if(globalDefaultHazMatId==hazMatId)
           {
             document.aascShipExecPackageOptionsForm.HazardousMaterialId.value=globalDefaultHazMatId;
           }  
                        
                        startIndex  = responseStringDummy.indexOf('*');
            responseStringDummy=responseStringDummy.substring(startIndex+1);
           
            startIndex  = responseStringDummy.indexOf('*');
            classes     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            var i =0;
            
            //alert("responseStringDummy"+responseStringDummy);
             document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialClass.options[i] = new Option(classes,classes, true, true);//uncommented code for SC_7.1.7.6 patch 
             
             document.aascShipExecPackageOptionsForm.HazardousMaterialClass.value=classes;
            
            startIndex  = responseStringDummy.indexOf('*');
            quantity     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
         //   alert("qty=="+quantity);
            
             
            document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.value=quantity;
           
            startIndex  = responseStringDummy.indexOf('*');
            unit     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
             
            document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.value=unit;
             //alert("4"+unit);
             //document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialUnit.options[i] = Option(unit,unit, true, true);
           
            startIndex  = responseStringDummy.indexOf('*');
            IdNo     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
             
            document.aascShipExecPackageOptionsForm.HazMatIdentificationNo.value=IdNo;
            
           // alert("5"+IdNo);
            
            startIndex  = responseStringDummy.indexOf('*');
            labelType     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            //alert("6"+labelType);
            
            document.aascShipExecPackageOptionsForm.HazMatDOTLabelType.value=labelType;
                
            startIndex  = responseStringDummy.indexOf('*');
            req  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            //alert("7"+req);
                           
            startIndex  = responseStringDummy.indexOf('*');
            carriercode  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            //alert("8"+carriercode);
            
            startIndex  = responseStringDummy.indexOf('*');
            contactNo     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            //alert("9"+contactNo);
            
            document.aascShipExecPackageOptionsForm.HazMatEmergencyContactNo.value=contactNo;
            
                            
            startIndex  = responseStringDummy.indexOf('*');
            contactName     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            //alert("10"+contactName);
           
            if(contactName=="null")
            {
             document.aascShipExecPackageOptionsForm.HazMatEmergencyContactName.value="";
            }
            else
            {
            document.aascShipExecPackageOptionsForm.HazMatEmergencyContactName.value=contactName;
            }
                
            startIndex  = responseStringDummy.indexOf('*');
            pkgGroup  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            
             //alert("11"+pkgGroup);
             if(pkgGroup=="null")
            {
             document.aascShipExecPackageOptionsForm.HazardousMaterialPkgGroup.value="--Select--";
            }
            else
            {
            document.aascShipExecPackageOptionsForm.HazardousMaterialPkgGroup.value=pkgGroup;
            }
            
            //12th
//            startIndex  = responseStringDummy.indexOf('*');
//            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            startIndex  = responseStringDummy.indexOf('*');
            var pkgingCnt  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value=pkgingCnt;
            
            //13th
            startIndex  = responseStringDummy.indexOf('*');
            var pkgingUnits  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.value=pkgingUnits;
            
            
            //Added on Jul-05-2011
            //14th
            startIndex  = responseStringDummy.indexOf('*');
            var technicalName  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            document.aascShipExecPackageOptionsForm.HazMatTechnicalName.value=technicalName;
            //End on Jul-05-2011
            
             startIndex  = responseStringDummy.indexOf('*');
            var signatureName  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            document.aascShipExecPackageOptionsForm.HazMatSignatureName.value=signatureName;
                      
            startIndex  = responseStringDummy.indexOf('*');
            var packingInstruction  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            
            if(packingInstruction=="null")
            {
             document.aascShipExecPackageOptionsForm.HazMatPackInstructions.value="";
            }
            else
            {
            document.aascShipExecPackageOptionsForm.HazMatPackInstructions.value=packingInstruction;
            }
        }

                startIndex  = responseStringDummy.indexOf('*');
        var hazmatMaterialType  = responseStringDummy.substring(0,startIndex);
        responseStringDummy=responseStringDummy.substring(startIndex+1);
        
       document.aascShipExecPackageOptionsForm.HazardousMaterialType.value=hazmatMaterialType;

      }
   
    var url="aascAjaxHazMatValues.jsp?carrierCode="+carrierCode+"&hazMatId="+hazMatId+"&locationId="+locationId;
    xmlHttp.open("POST",url,true);  // Shiva changed from GET to POST  
    xmlHttp.send(null); 
      
      //alert("2857"+globalvar);
      
      
    
    }

function getHazMatPkgGroup()
   {
   //alert("Inside 2778");
   
   //alert("Inside getHazMatPkgGroup method ");
   
   var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;

      //var carrierCode = document.aascShipExecPackageOptionsForm.ajaxCarrierCode.value;//110;
      var carrierCode = str.substring(0,3)
   var lookUpMeaning = document.aascShipExecPackageOptionsForm.HazardousMaterialType.value;
  
  //alert("CarrierCode:"+carrierCode);
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
        {        alert("Your browser does not support AJAX!");  
        return false;      
        }    
        }  
        
        }
    xmlHttp.onreadystatechange=function()
      {
      if(xmlHttp.readyState==4)
        {
           // alert('xmlHttp.readyState :'+xmlHttp.readyState);
           // alert('xmlHttp.responseText ::'+trim(xmlHttp.responseText));
           var responseStringDummy=trim(xmlHttp.responseText);
//           alert("REsp Strng"+responseStringDummy);
           var index = responseStringDummy.indexOf("@@@");
            responseStringDummy = responseStringDummy.substr(index+3);
            var testindex  = responseStringDummy.indexOf('***');
            var optionValuePkgGrp;
            var i=0;
          //  alert("in if(xmlHttp.readyState==4)");
          //  alert("testindex:"+testindex);
       //     alert("globalvar2 :"+globalvar2);
           
           document.aascShipExecPackageOptionsForm.HazardousMaterialPkgGroup.options.length=1;
           document.aascShipExecPackageOptionsForm.HazardousMaterialPkgGroup.options1 = new Option("--Select--","--Select--",true,true);
//           alert("test Index:"+testindex);
            while(testindex!=-1)
            {
            //alert("globalHazMatPkgGrpLoad iside ffff:"+globalHazMatPkgGrpLoad);
            optionValuePkgGrp=responseStringDummy.substr(0,testindex);
            var localCheckValue="";
//            alert("3133:"+optionValuePkgGrp);
            if(globalvar2 !="" && globalvar2 != null){
//            alert("Inside if 3134");
            localCheckValue = "globalvar2";
            if(globalvar2!='' && globalvar2==optionValuePkgGrp)
                      {
                       // document.aascShipExecPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, true);
                       document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option(optionValuePkgGrp,optionValuePkgGrp, true, true);
                      }
                      else
                      {
                     // alert("Else");
                       // document.aascShipExecPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, false);
                       document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option(optionValuePkgGrp,optionValuePkgGrp, true, false); 
                      }
            
            
            }else{
//            alert("Main else");
            localCheckValue ="globalHazMatPkgGrpLoad";
                 document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option(optionValuePkgGrp,optionValuePkgGrp);
                 //*****
                 if(globalHazMatPkgGrpLoad!='' && globalHazMatPkgGrpLoad==optionValuePkgGrp)
                      {
                      //alert(" Main else If");
                       // document.aascShipExecPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, true);
                       document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option(optionValuePkgGrp,optionValuePkgGrp, true, true);
                      }                     
                      else
                      {
                      //alert("Here");              
                       // document.aascShipExecPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, false);
                       document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option(optionValuePkgGrp,optionValuePkgGrp, true, false); 
                      }
                 //******
            }
            responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
            testindex  = responseStringDummy.indexOf('***');
            i++;
	          }
              /*    if(globalvar2=='' && localCheckValue == "globalvar2" )
                       {
                       document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option('','', true, true); 
                       }else if(globalHazMatPkgGrpLoad=='' && localCheckValue== "globalHazMatPkgGrpLoad")
                       {
                       document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option('','', true, true); 
                       }  */
                       if(globalvar2=='' || globalHazMatPkgGrpLoad == '' )
                       {
                       document.forms['aascShipExecPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option('','', true, true); 
                       }
            globalvar2="";
        }
      }
   
    var url="aascAjaxHazMatPackingGroup.jsp?carrierCode="+carrierCode+"&lookUpMeaning="+lookUpMeaning;
    xmlHttp.open("POST",url,false);  // Calling 
    xmlHttp.send(null); 
      
      //alert("3164"+globalvar2);
    
    }

function defaultProfileHazMatValues()
{
      var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;
      var carrierCode = str.substring(0,3);
      
      globalDefaultHazMatId = document.aascShipExecPackageOptionsForm.hazMatIdHidden.value;
      //alert(globalDefaultHazMatId);
    
      if(carrierCode=="100")
      {
         if(globalDefaultHazMatId!="")
         {
           getHazMatPkgGroup();
           getHazMatValues();
         }
      }

}
  
  //added for online help
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

//function chDryIceCheck(){
//
//            
//
//            document.aascShipExecPackageOptionsForm.chDryIce.value="N"; 
//
//            document.aascShipExecPackageOptionsForm.dryIceWeight.readOnly=true;
//
//            document.aascShipExecPackageOptionsForm.dryIceWeight.value="";
//
//            document.aascShipExecPackageOptionsForm.dryIceUnits.disabled=true;
//
//            //document.aascShipExecPackageOptionsForm.dryIceUnits.value="";
//
//          
//           
//            if(document.aascShipExecPackageOptionsForm.chDryIce.checked){
//
//                    document.aascShipExecPackageOptionsForm.chDryIce.value="Y";
//
//                    document.aascShipExecPackageOptionsForm.dryIceUnits.readOnly=false;
//                    document.aascShipExecPackageOptionsForm.dryIceUnits.disabled=false;
//
//                    document.aascShipExecPackageOptionsForm.dryIceWeight.readOnly=false;
//                    document.aascShipExecPackageOptionsForm.dryIceWeight.disabled=false;
//               }	
//   else
//   {
//   document.aascShipExecPackageOptionsForm.chDryIce.value="N";
//   
//   }
//
//}
    
//function saveHazmatDetails()
//{
//
//document.aascShipExecPackageOptionsForm.actiontype.value='ADDHAZMAT';
//var checkhmType = document.getElementById("checkhmType").value;
//if(checkhmType == "ACCESSIBLE" || checkhmType == "INACCESSIBLE")
//{
//var haxMatType = document.getElementById("HazardousMaterialType").value;
//    if(checkhmType != haxMatType)
//    {
//        alert("Hazardous Material Type should be same for all the commodity.");
//        return false;
//    }
//
//}
//
//   var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;
//
//   if(document.aascShipExecPackageOptionsForm.HazMatFlag.checked == true)
//    {
//       if (document.aascShipExecPackageOptionsForm.HazardousMaterialType.value == '')
//      {
//         alert('Please Select Hazardous Material Type ');
//         document.aascShipExecPackageOptionsForm.HazardousMaterialType.focus();
//         return false;
//      }
//      
//      //validations for the Haz material Quantity and Unit  Added on 22 jan 09
//      if (trim(document.aascShipExecPackageOptionsForm.HazMatSignatureName.value) == '' && str.substring(0,3)=="110")
//      {
//         alert('Please Select Signature Name ');
//         document.aascShipExecPackageOptionsForm.HazMatSignatureName.focus();
//         return false;
//      }
//      
//     if(str.substring(0,3)=="100" )  ////110 condition added by Jagadish
//     {
//     
//         if (document.aascShipExecPackageOptionsForm.HazardousMaterialClass.value == '')
//          {
//             alert('Please Select HazMat Id ');
//             document.aascShipExecPackageOptionsForm.HazardousMaterialClass.focus();
//             return false;
//          }
//          if (document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.value == '')
//          {
//             alert('Please enter Weight ');
//             document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.focus();
//             return false;
//          }
//
//         var hazmatPkgCnt =  document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value.replace(/^\s+|\s+$/g,"");
//         var hazmatPkgUnits = document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.value.replace(/^\s+|\s+$/g,"");
////         var fdxCarrierMode = document.aascShipExecPackageOptionsForm.fdexCarrierMode.value;
//         var hazmatTechnicalName = document.aascShipExecPackageOptionsForm.HazMatTechnicalName.value.replace(/^\s+|\s+$/g,"");
////         var hazmatPackInstructions=document.aascShipExecPackageOptionsForm.HazMatPackInstructions.value.replace(/^\s+|\s+$/g,"");
//         var commodityLine = document.getElementById("commodityLine").options.length;
////         var HazMatOverPackFlag = document.getElementById("HazMatOverPackFlag").checked;
//
////        alert("commodityLine "+commodityLine);
//        
////        alert("HazMatOverPackFlag  "+HazMatOverPackFlag);
//         
////        if(document.aascShipExecPackageOptionsForm.HazMatFlag.checked == true && (fdxCarrierMode=="FedexWebServices" || fdxCarrierMode=="WEBSERVICES"))
////        { 
////         if(hazmatPkgCnt == null || hazmatPkgCnt =='')
////         {
////           alert("Please enter Hazmat Packaging Count");
////           document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value = document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value.replace(/^\s+|\s+$/g,"");
////           document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.focus();
////           return false;
////         }
////         
////        var r =/^[0-9.]*$/;
////        if(!r.test(document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value))
////        {
////        document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value = document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value.replace(/^\s+|\s+$/g,"");
////        alert("Enter only numbers in Hazmat Packaging Count");
////        document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.focus();
////        return false;
////        }
////         
////         if(hazmatPkgUnits == null || hazmatPkgUnits =='')
////         {
////           alert("Please enter Hazmat Packaging Units");
////           document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.value = document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.value.replace(/^\s+|\s+$/g,"");
////           document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.focus();
////           return false;
////         }
////         alert('before hazmatTechnicalName ');
////             
////      } 
//      
//     } 
// var strTemp=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;
//       var carrierCodeTemp = strTemp.substring(0,3)
//             
//         if(document.getElementById("commodityLine").options.length > 2 
////         && document.getElementById("HazMatOverPackFlag").checked == false 
////         && carrierCodeTemp == 110
//         ){
//         
////         alert("Please select HazMatOverPackFlag for more than one package" );
////         return false;
//         
//         }
//    }
//          
//holdData();
//document.aascShipExecPackageOptionsForm.actionType.value='ADD';
//document.aascShipExecPackageOptionsForm.submit();
//
//}

//function deleteHazmatDetail(){
//var commValNo= 0;
////alert("selected index"+document.aascShipExecPackageOptionsForm.commodityLine.selectedIndex);
//
//document.aascShipExecPackageOptionsForm.actiontype.value='DELETEHAZMAT';
//if(document.aascShipExecPackageOptionsForm.commodityLine.selectedIndex != -1){
//commValNo = document.aascShipExecPackageOptionsForm.commodityLine.options[document.aascShipExecPackageOptionsForm.commodityLine.selectedIndex].value;
//}
//document.getElementById("commodityNo").value = commValNo;
//
//if(commValNo == 111 || commValNo == 222)
//{
//    alert("  Please select a Commodity to Delete  ");
//    return false;
//}
//    if(commValNo == "")
//    {
//        alert("  Please select a Commodity to Delete  ");
//        return false;
//    }
//    document.aascShipExecPackageOptionsForm.actionType.value='DELETE';
//holdData();
//document.aascShipExecPackageOptionsForm.submit();
//
//}

//function holdData(){
////alert("hello "+document.aascShipExecPackageOptionsForm.packageCount.value);
////var  packageCount=document.aascShipExecPackageOptionsForm.packageCount.value;
////alert(packageCount);
////    window.opener.document.getElementById('codAmtID'+packageCount).value=document.aascShipExecPackageOptionsForm.codAmt.value;
////    alert("2");
////     window.opener.document.getElementById('returnShipmentID'+packageCount).value=document.aascShipExecPackageOptionsForm.returnShipment.value;
////     window.opener.document.getElementById('PackageSurchargeID'+packageCount).value=document.aascShipExecPackageOptionsForm.PackageSurcharge.value;  
////     window.opener.document.getElementById('PackageShipmentCostID'+packageCount).value=document.aascShipExecPackageOptionsForm.PackageShipmentCost.value;  
//     //alert("signature option::"+abc);
//     //window.opener.document.getElementById('signatureOptionID'+packageCount).value=abc;  
//     
//     //19/07/07(start)
//     
////     window.opener.document.getElementById('rtnShipFromCompanyID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromCompany.value;  
////     window.opener.document.getElementById('rtnShipToCompanyID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToCompany.value;  
////     window.opener.document.getElementById('rtnShipFromContactID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromContact.value;  
////     window.opener.document.getElementById('rtnShipToContactID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToContact.value;  
////     window.opener.document.getElementById('rtnShipFromLine1ID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromLine1.value;  
////     window.opener.document.getElementById('rtnShipToLine1ID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToLine1.value;  
////     window.opener.document.getElementById('rtnShipFromLine2ID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromLine2.value;  
////     window.opener.document.getElementById('rtnShipToLine2ID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToLine2.value;  
////     window.opener.document.getElementById('rtnShipFromCityID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromCity.value;  
////     window.opener.document.getElementById('rtnShipFromSateID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromSate.value;  
////     window.opener.document.getElementById('rtnShipFromZipID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromZip.value;  
////     window.opener.document.getElementById('rtnShipToCityID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToCity.value;  
////     window.opener.document.getElementById('rtnShipToStateID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToState.value;  
////     window.opener.document.getElementById('rtnShipToZipID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToZip.value;  
////     window.opener.document.getElementById('rtnShipFromPhoneID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromPhone.value;  
////     window.opener.document.getElementById('rtnShipToPhoneID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToPhone.value;  
////     window.opener.document.getElementById('rtnShipMethodID'+packageCount).value=document.aascShipExecPackageOptionsForm.ajaxShipMethod.value;  
////     window.opener.document.getElementById('rtnDropOfTypeID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnDropOfType.value;  
////     window.opener.document.getElementById('rtnPackageListID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnPackageList.value;  
//     //alert("4");
//     //window.opener.document.getElementById('rtnPayMethodID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnPayMethod.options[document.aascShipExecPackageOptionsForm.rtnPayMethod.selectedIndex].text;
//     //alert("3");
////     window.opener.document.getElementById('rtnPayMethodCodeID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnPayMethod.options.value;
////        
////     window.opener.document.getElementById('rtnACNumberID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnACNumber.value;  
////     window.opener.document.getElementById('rtnRMAID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnRMA.value;  
////     
////     window.opener.document.getElementById('packageSaveCheckID'+packageCount).value="Y";  
////     window.opener.document.getElementById('rtnShipMethodNameID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipMethod.value;
////     30/07/07
////     window.opener.document.getElementById('rtnDeclaredValueID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value;
////     30/07/07
////     
//    //19/07/07(end)
////     alert("rtnPayMethod :"+document.aascShipExecPackageOptionsForm.rtnPayMethod.options[document.aascShipExecPackageOptionsForm.rtnPayMethod.selectedIndex].text);
//     
//     // Added by Sambit on 15/11/07
//     
////     window.opener.document.getElementById('halPhoneID'+packageCount).value = document.aascShipExecPackageOptionsForm.halPhone.value;
////     window.opener.document.getElementById('halAddrLine1ID'+packageCount).value = document.aascShipExecPackageOptionsForm.halAddrLine1.value;
////     window.opener.document.getElementById('halAddrLine2ID'+packageCount).value = document.aascShipExecPackageOptionsForm.halAddrLine2.value;
////     window.opener.document.getElementById('halCityID'+packageCount).value = document.aascShipExecPackageOptionsForm.halCity.value;
////     window.opener.document.getElementById('halStateID'+packageCount).value = document.aascShipExecPackageOptionsForm.halState.value;
////     window.opener.document.getElementById('halZipID'+packageCount).value = document.aascShipExecPackageOptionsForm.halZip.value;
////     window.opener.document.getElementById('holdAtLocationID'+packageCount).value = document.aascShipExecPackageOptionsForm.holdAtLocation.value;
////     alert("5");
//          // Added By Sambit on 11/07/08
//var  packageCount=document.aascShipExecPackageOptionsForm.packCount.value;
////alert("packageCount::"+packageCount);
//     if(document.aascShipExecPackageOptionsForm.HazMatFlag.checked)
//    {
//			document.aascShipExecPackageOptionsForm.HazMatFlag.value="Y";
//    }else{
//      document.aascShipExecPackageOptionsForm.HazMatFlag.value="N";
//    }
////    alert("document.aascShipExecPackageOptionsForm.HazMatFlag.value::"+document.aascShipExecPackageOptionsForm.HazMatFlag.value);
////     alert("6");
//     window.opener.document.getElementById('HazMatFlagID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatFlag.value;
//     window.opener.document.getElementById('HazMatCountID'+packageCount).value = document.aascShipExecPackageOptionsForm.hazmatListCount.value;
//     window.opener.document.getElementById('HazMatTypeID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialType.value;
//     window.opener.document.getElementById('HazMatClassID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialClass.value;
////     alert(window.opener.DynaAdhocShipSaveForm['HazMat+Flag'+packageCount].value);
//   //window.opener.DynaAdhocShipSaveForm['HazMatCharges'+packageCount].value = document.aascShipExecPackageOptionsForm.HazardousMaterialCharges.value;
////        alert("7");
//   //window.opener.DynaAdhocShipSaveForm['HazMatFlag'+packageCount].value=packageCount;
//   //end of 11/07/08
//
//     //pavan
//     //alert(document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.value);
//     window.opener.document.getElementById('HazMatQtyID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.value;
//     window.opener.document.getElementById('HazMatUnitID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.value;
//
//    
//      window.opener.document.getElementById('HazMatIdNoID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatIdentificationNo.value;
//       //alert("save 1122::::"+window.opener.DynaAdhocShipSaveForm['HazMatIdNo'+packageCount].value);
//    window.opener.document.getElementById('HazMatPkgGroupID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialPkgGroup.value;
//    window.opener.document.getElementById('HazMatDOTLabelID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatDOTLabelType.value;
//    window.opener.document.getElementById('HazMatEmerContactNoID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatEmergencyContactNo.value;
//    window.opener.document.getElementById('HazMatEmerContactNameID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatEmergencyContactName.value;
//    window.opener.document.getElementById('HazMatIdID'+packageCount).value = "Select";
//
//     
//     // Added on Jun-03-2011
//      
//    window.opener.document.getElementById('HazMatPackagingCntID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value;
//    window.opener.document.getElementById('HazMatPackagingUnitsID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.value;
//     window.opener.document.getElementById('HazMatPackInstructionsID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatPackInstructions.value;
//        window.opener.document.getElementById('HazMatTechnicalNameID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatTechnicalName.value;
// 
//        window.opener.document.getElementById('HazMatSignatureNameID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatSignatureName.value;
//       // alert("1 "+document.aascShipExecPackageOptionsForm.HazMatOverPackFlag.value);
////        alert("7");
////        if(document.aascShipExecPackageOptionsForm.HazMatOverPackFlag.checked)
////    {
////       document.aascShipExecPackageOptionsForm.HazMatOverPackFlag.value="Y";
////    }else{
////       document.aascShipExecPackageOptionsForm.HazMatOverPackFlag.value="N";
////    }
////        window.opener.document.getElementById('HazMatOverPackFlagID'+packageCount).value=document.aascShipExecPackageOptionsForm.HazMatOverPackFlag.value;
//        
////         alert("8");
////    alert("bye"); 
//if(window.opener.document.getElementById('shipMethodId').disabled!=true || !(window.opener.document.getElementById('trackingNumberID'+packageCount).readOnly))
//        {
//         //var declaredVal=document.aascShipExecPackageOptionsForm.declaredVal.value;
//         var point=".";
//         var pos1;
//         var decimalNum;
//         var floatingNum;
//         var length;
//         var checkBoxVal;
//         var packageCount;//sc_7.0_skp
//         //var delConfirm=document.aascShipExecPackageOptionsForm.upsDelConfirm.value;
//         //delConfirm=parseInt(delConfirm);
//         //sc_7.0_skp
//         packageCount=document.aascShipExecPackageOptionsForm.packageCount.value;
//         //alert('packageCount:'+packageCount);
//         checkBoxVal=document.aascShipExecPackageOptionsForm.upsCodCheckBox.value;
//         //alert("checkBoxVal:"+checkBoxVal);
//         if(checkBoxVal=="Y")
//         {
//         // window.opener.ShipInsertForm['chCOD'+packageCount].value="Y";
//         var upsCodAmt= document.aascShipExecPackageOptionsForm.upsCodAmt.value;
//
//if(upsCodAmt >=1)
//         {
//          window.opener.document.getElementById('chCODID'+packageCount).value="Y";
//          }
//          else
//          {
//          window.opener.document.getElementById('chCODID'+packageCount).value="N";
//          }
//          window.opener.document.getElementById('codAmtID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodAmt.value;
//          window.opener.document.getElementById('codTypeID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodCode.value;
//          window.opener.document.getElementById('codFundsCodeID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodFundsCode.value;
//          window.opener.document.getElementById('codCurrCodeID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodCurrCode.value;           
//          //if(chkDeliveryConfirmation()==false)
//          //return false;
//         }
//         else
//         {
//          window.opener.document.getElementById('chCODID'+packageCount).value="N";
//          window.opener.document.getElementById('codAmtID'+packageCount).value="";
//          window.opener.document.getElementById('codTypeID'+packageCount).value="";
//          window.opener.document.getElementById('codFundsCodeID'+packageCount).value="";
//          window.opener.document.getElementById('codCurrCodeID'+packageCount).value="";
//         }
//         //alert("base page cod:"+window.opener.ShipInsertForm['codHidden'+packageCount].value);
//         //alert("base page cod amt:"+window.opener.ShipInsertForm['codAmtHidden'+packageCount].value);
//         
//         
////         window.opener.document.getElementById('pkgingID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsPackaging.value;
////         window.opener.document.getElementById('delConfirmID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsDelConfirm.value;
//         
//         /*if(window.opener.ShipInsertForm['chCOD'+packageCount].value=="Y")
//         {
//         window.opener.ShipInsertForm['codType'+packageCount].value=document.aascShipExecPackageOptionsForm.upsCodCode.value;
//         window.opener.ShipInsertForm['codFundsCode'+packageCount].value=document.aascShipExecPackageOptionsForm.upsCodFundsCode.value;
//         window.opener.ShipInsertForm['codCurrCode'+packageCount].value=document.aascShipExecPackageOptionsForm.upsCodCurrCode.value;
//         }
//         else //if(window.opener.ShipInsertForm['chCOD'+packageCount].value=="N")
//         {
//         window.opener.ShipInsertForm['codType'+packageCount].value="";
//         window.opener.ShipInsertForm['codFundsCode'+packageCount].value="";
//         window.opener.ShipInsertForm['codCurrCode'+packageCount].value="";
//         }*/
//                                             
//         //window.opener.ShipInsertForm['declaredVal'+packageCount].value=document.aascShipExecPackageOptionsForm.declaredVal.value;
//         //window.opener.ShipInsertForm['declaredCurrCode'+packageCount].value=document.aascShipExecPackageOptionsForm.declaredCurrCode.value;
//         //return true;
//         }
//         savePackageOptionsDetails();
//}

function savePackageOptionsDetails()
{

var length;
var packageCount;
var checkBoxVal;
var imgSrcVal;
//addition for return shipment
//alert("returnShipment :"+document.aascShipExecPackageOptionsForm.returnShipment.checked+"&& value :"+document.aascShipExecPackageOptionsForm.returnShipment.value);

//if(document.aascShipExecPackageOptionsForm.returnShipment.checked == false)
//
//  {
//
//     document.aascShipExecPackageOptionsForm.returnShipment.value="NONRETURN";
//
//  }

//if(document.aascShipExecPackageOptionsForm.returnShipment.value=="PRINTRETURNLABEL" && document.aascShipExecPackageOptionsForm.returnShipment.disabled==false)
//      {
//      if (checkShipToAddress()==false)
//
//   {
//   		return false;
//
//		}  
//       }         
//       
       
//       if(document.aascShipExecPackageOptionsForm.returnShipment.value =="PRINTRETURNLABEL"   && document.aascShipExecPackageOptionsForm.codTempFlag.value=="Y" )
//     {
//     alert("COD and Return shipment both can not be allowed at a time");
//     return false;
//     }


/* var rtnDeclaredValue= document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value;
     if(isNaN(rtnDeclaredValue) || rtnDeclaredValue<0)
     {
     alert("Please enter the Return DeclaredValue Correctly");
     document.aascShipExecPackageOptionsForm.rtnDeclaredValue.focus();
     return false;
 
     }
       if(rtnDeclaredValue=="" || rtnDeclaredValue=='' )
       {
           document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value=0;
       } */
//end of return shipment

/*if(document.aascShipExecPackageOptionsForm.saveButton!=null)
{
 imgSrcVal=document.aascShipExecPackageOptionsForm.saveButton.src;
}
if(imgSrcVal!=null && imgSrcVal!="")
{
 if(imgSrcVal=="buttons/aascSaveOff1.png")
 {
  //do nothing
 }
 else if(imgSrcVal=="buttons/aascSave1.png")
 { */
 
//Gaylord Enhancement - Assigning Package Count before, in order to continue the flow normally, for newly added packages
 packageCount=document.aascShipExecPackageOptionsForm.packCount.value;
//  if(window.opener.document.ShipInsertForm.shipMethod.disabled!=true || !(window.opener.document.getElementById('trackingNumberID'+packageCount).readOnly))
//  {   
//    packageCount=document.aascShipExecPackageOptionsForm.packageCount.value;
//    checkBoxVal=document.aascShipExecPackageOptionsForm.codTempFlag.value;
    /*var upsCodCode=document.aascShipExecPackageOptionsForm.upsCodCode.value;
    var upsCodFundsCode=document.aascShipExecPackageOptionsForm.upsCodFundsCode.value;
    var upsCodCurrCode=document.aascShipExecPackageOptionsForm.upsCodCurrCode.value;    
   
    var upsPackaging=document.aascShipExecPackageOptionsForm.upsPackaging.value;
    var upsDelConfirm=document.aascShipExecPackageOptionsForm.upsDelConfirm.value;
    var declaredCurrCode=document.aascShipExecPackageOptionsForm.declaredCurrCode.value;*/
    
//    var upsPackaging=document.aascShipExecPackageOptionsForm.upsPackaging.options[document.aascShipExecPackageOptionsForm.upsPackaging.selectedIndex].value;    
//    var upsDelConfirm=document.aascShipExecPackageOptionsForm.upsDelConfirm.options[document.aascShipExecPackageOptionsForm.upsDelConfirm.selectedIndex].value;
    //var declaredCurrCode=document.aascShipExecPackageOptionsForm.declaredCurrCode.options[document.aascShipExecPackageOptionsForm.declaredCurrCode.selectedIndex].value;
    //////////////////////////upsPackaging///////////////////////////////////////
//      length=parseInt(document.aascShipExecPackageOptionsForm.upsPackaging.length);
//       for(var i=0; i<length; i++)
//       {
//        if(document.aascShipExecPackageOptionsForm.upsPackaging.options[i].value==upsPackaging)
//        {
         //var adultIndex=i;
//         document.aascShipExecPackageOptionsForm.upsPackaging.options[i].selected=true;
//         window.opener.document.getElementById('pkgingID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsPackaging.options[document.aascShipExecPackageOptionsForm.upsPackaging.selectedIndex].value;
//        }
//       }
       
//alert(document.aascShipExecPackageOptionsForm.upsPackaging.options[document.aascShipExecPackageOptionsForm.upsPackaging.selectedIndex].text);
// if(document.aascShipExecPackageOptionsForm.upsPackaging.options[document.aascShipExecPackageOptionsForm.upsPackaging.selectedIndex].text != 'Customer Supplied Package')
//       {
//       alert('in 1');
//       var nameLength=parseInt(window.opener.document.getElementById('dimensionNameID'+packageCount).length);
//       for(var i=0; i<nameLength; i++)
//       {
//             if(window.opener.document.getElementById('dimensionNameID'+packageCount).options[i].text == 'Standard Dimension')
//        {
//          window.opener.document.getElementById('dimensionNameID'+packageCount).options[i].selected=true;
//          window.opener.document.getElementById('dimButtonID'+packageCount).disabled=true;
//        }
//       }
//         window.opener.document.getElementById('dimensionNameID'+packageCount).disabled = true;      
//       }
//       else
//       {
       //alert('in 2');
//       var nameLength=parseInt(window.opener.document.getElementById('dimensionNameID'+packageCount).length);
       //alert("nameLength :"+nameLength);
       //Added by pavan to hold value even in error case 31/03/10
//        var dimensionNameHidden=window.opener.document.getElementById('dimensionNameID'+packageCount).options[window.opener.document.getElementById('dimensionNameID'+packageCount).selectedIndex].text;           
      //alert("dimensionNameHidden :"+dimensionNameHidden);
//      var dimensionValueHidden=window.opener.document.getElementById('dimensionNameID'+packageCount).options[window.opener.document.getElementById('dimensionNameID'+packageCount).selectedIndex].value;           
         
//      var defaultDimeName=window.opener.document.getElementById('defaultDimeNameID'+packageCount).value;
// 
//        var selectedDimValue="";
        //alert("dimensionNameHidden :"+dimensionNameHidden);
        //alert("defaultDimeName :"+defaultDimeName);
        //alert("window.opener.ShipInsertForm['dimensionName'+packageCount].options[packageCount].text::"+window.opener.ShipInsertForm['dimensionName'+packageCount].options[packageCount].text);
        
//        if (((dimensionNameHidden==defaultDimeName)&& (dimensionNameHidden!='Standard Dimension')) )
//        {
        
//          selectedDimValue=defaultDimeName;
//        }
//        else
//        {
         
//         if ((dimensionNameHidden=='Standard Dimension'))
//         {
//         selectedDimValue=defaultDimeName;
//         }
//         else
//         {
//           selectedDimValue =dimensionNameHidden;
//         }
//        }
        
        
//       for(var i=0; i<nameLength; i++)
//       {
       
       
//        if(window.opener.document.getElementById('dimensionNameID'+packageCount).options[i].text == selectedDimValue)
//        {
//          window.opener.document.getElementById('dimensionNameID'+packageCount).options[i].selected=true;
//          window.opener.document.getElementById('dimButtonID'+packageCount).disabled=false;
//        }
//       }
//         window.opener.document.getElementById('dimensionNameID'+packageCount).disabled = false; 
       
//       }
       
//13/10/2008(end)        
//      if(chkDeliveryConfirmation()==false)
//    {
//     document.aascShipExecPackageOptionsForm.upsDelConfirm.focus();
//     return false;
//    }
      
//     if(checkBoxVal=="Y")
//     {
//      var upsCodCode=document.aascShipExecPackageOptionsForm.upsCodCode.options[document.aascShipExecPackageOptionsForm.upsCodCode.selectedIndex].value;
//      var upsCodFundsCode=document.aascShipExecPackageOptionsForm.upsCodFundsCode.options[document.aascShipExecPackageOptionsForm.upsCodFundsCode.selectedIndex].value;
//      var upsCodCurrCode=document.aascShipExecPackageOptionsForm.upsCodCurrCode.options[document.aascShipExecPackageOptionsForm.upsCodCurrCode.selectedIndex].value;
//      var upsCodAmt= document.aascShipExecPackageOptionsForm.upsCodAmt.value;

//if(upsCodAmt>=1)
//         {
//          window.opener.document.getElementById('chCODID'+packageCount).value="Y";
//          }
//          else
//          {
//          window.opener.document.getElementById('chCODID'+packageCount).value="N";
//          }
     // window.opener.ShipInsertForm['chCOD'+packageCount].value="Y";
//      window.opener.document.getElementById('codAmtID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodAmt.value;
                  
      //////////////////////////upsCodCode///////////////////////////////////////
//      length=parseInt(document.aascShipExecPackageOptionsForm.upsCodCode.length);
//       for(var i=0; i<length; i++)
//       {
//        if(document.aascShipExecPackageOptionsForm.upsCodCode.options[i].value==upsCodCode)
//        {
         //var adultIndex=i;
//         document.aascShipExecPackageOptionsForm.upsCodCode.options[i].selected=true;
//         window.opener.document.getElementById('codTypeID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodCode.options[document.aascShipExecPackageOptionsForm.upsCodCode.selectedIndex].value;
//        }
//       }
       
       //////////////////////////upsCodFundsCode///////////////////////////////////////
//       length=parseInt(document.aascShipExecPackageOptionsForm.upsCodFundsCode.length);
//       for(var i=0; i<length; i++)
//       {
//        if(document.aascShipExecPackageOptionsForm.upsCodFundsCode.options[i].value==upsCodFundsCode)
//        {
         //var adultIndex=i;
//         document.aascShipExecPackageOptionsForm.upsCodFundsCode.options[i].selected=true;
//         window.opener.document.getElementById('codFundsCodeID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodFundsCode.options[document.aascShipExecPackageOptionsForm.upsCodFundsCode.selectedIndex].value;
//        }
//       }
       
       //////////////////////////upsCodCurrCode///////////////////////////////////////
//       length=parseInt(document.aascShipExecPackageOptionsForm.upsCodCurrCode.length);
//       for(var i=0; i<length; i++)
//       {
//        if(document.aascShipExecPackageOptionsForm.upsCodCurrCode.options[i].value==upsCodCurrCode)
//        {
//         var adultIndex=i;
//         document.aascShipExecPackageOptionsForm.upsCodCurrCode.options[i].selected=true;
//         window.opener.document.getElementById('codCurrCodeID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsCodCurrCode.options[document.aascShipExecPackageOptionsForm.upsCodCurrCode.selectedIndex].value;
//        }
//       }
       
//     }//end of if(checkBoxVal=="Y")
//     else
//     {
//          window.opener.document.getElementById('chCODID'+packageCount).value="N";
//          window.opener.document.getElementById('codAmtID'+packageCount).value="";
//          window.opener.document.getElementById('codTypeID'+packageCount).value="";
//          window.opener.document.getElementById('codFundsCodeID'+packageCount).value="";
//          window.opener.document.getElementById('codCurrCodeID'+packageCount).value="";
//     }//end of else of if(checkBoxVal=="Y")
     
     
   
      //////////////////////////upsDelConfirm///////////////////////////////////////
       length=parseInt(document.aascShipExecPackageOptionsForm.upsDelConfirm.length);
       for(var i=0; i<length; i++)
       {
        if(document.aascShipExecPackageOptionsForm.upsDelConfirm.options[i].value==upsDelConfirm)
        {
//         var adultIndex=i;
         document.aascShipExecPackageOptionsForm.upsDelConfirm.options[i].selected=true;
         window.opener.document.getElementById('delConfirmID'+packageCount).value=document.aascShipExecPackageOptionsForm.upsDelConfirm.options[document.aascShipExecPackageOptionsForm.upsDelConfirm.selectedIndex].value;
        }
       }
       
       //window.opener.ShipInsertForm['declaredVal'+packageCount].value=document.aascShipExecPackageOptionsForm.declaredVal.value;
       
       /*length=parseInt(document.aascShipExecPackageOptionsForm.declaredCurrCode.length);
       for(var i=0; i<length; i++)
       {
        if(document.aascShipExecPackageOptionsForm.declaredCurrCode.options[i].value==declaredCurrCode)
        {
         //var adultIndex=i;
         document.aascShipExecPackageOptionsForm.declaredCurrCode.options[i].selected=true;
         window.opener.ShipInsertForm['declaredCurrCode'+packageCount].value=document.aascShipExecPackageOptionsForm.declaredCurrCode.options[document.aascShipExecPackageOptionsForm.declaredCurrCode.selectedIndex].value;
        }
       }*/
       
   /* if(chkDeliveryConfirmation()==false)
    {
     document.aascShipExecPackageOptionsForm.upsDelConfirm.focus();
     return false;
    } */
//     if(checkCodAmt()==false)
//     {
//     return false;
//     }
     
     //alert("upsLargePackageCheckBox::"+document.aascShipExecPackageOptionsForm.upsLargePackageCheckBox.value);
     //alert("upsAddlHandlingCheckBox::"+document.aascShipExecPackageOptionsForm.upsAddlHandlingCheckBox.value);
     
    //window.opener.ShipInsertForm['largePackageFlag'+packageCount].value=document.aascShipExecPackageOptionsForm.upsLargePackageCheckBox.value;
    //window.opener.ShipInsertForm['addlHandlingFlag'+packageCount].value=document.aascShipExecPackageOptionsForm.upsAddlHandlingCheckBox.value;
 // alert(document.aascShipExecPackageOptionsForm.upsLargePackageCheckBox.value);
//  largePackageCheckBoxFunc();
//  addlHandlingCheckBoxFunc();
//  declaredValChargesToShipperCheckBox();
//          if(document.aascShipExecPackageOptionsForm.upsLargePackageCheckBox.value !="Y")
//        {
         //document.aascShipExecPackageOptionsForm.codTempFlag.value="N";
//         window.opener.document.getElementById('largePackageFlagID'+packageCount).value="N";              
//        }
//        else 
//        {
         //document.aascShipExecPackageOptionsForm.codTempFlag.value="Y";
//         window.opener.document.getElementById('largePackageFlagID'+packageCount).value="Y";       
//        }
      
//        if(document.aascShipExecPackageOptionsForm.upsAddlHandlingCheckBox.value !="Y")
//        {
         //document.aascShipExecPackageOptionsForm.codTempFlag.value="N";
//         window.opener.document.getElementById('addlHandlingFlagID'+packageCount).value="N";            
//        }
//        else 
//        {
         //document.aascShipExecPackageOptionsForm.codTempFlag.value="Y";
//         window.opener.document.getElementById('addlHandlingFlagID'+packageCount).value="Y";       
//        }
//GUru        
//
//        if(document.aascShipExecPackageOptionsForm.declaredValToShipper.value !="Y")
//        {
//         //document.aascShipExecPackageOptionsForm.codTempFlag.value="N";
//         window.opener.document.getElementById('declaredValToShipperFlagID'+packageCount).value="N";            
//        }
//        else 
//        {
//         //document.aascShipExecPackageOptionsForm.codTempFlag.value="Y";
//         window.opener.document.getElementById('declaredValToShipperFlagID'+packageCount).value="Y";       
//        }

     
//    }//end of if ship method is not disabled( before ship/save)
  /*}//else if(imgSrcVal=="buttons/aascSave1.png")
}//if(imgSrcVal!=null && imgSrcVal!="")*/
//GUru

//window.opener.document.getElementById('returnShipmentID'+packageCount).value=document.aascShipExecPackageOptionsForm.returnShipment.value;
//alert("test1 :"+document.aascShipExecPackageOptionsForm.returnShipment.value);
//alert("test2 :"+window.opener.document.getElementById('returnShipmentID'+packageCount).value);
//window.opener.document.getElementById('rtnShipToCompanyID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToCompany.value;
//window.opener.document.getElementById('rtnShipToContactID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToContact.value;
//window.opener.document.getElementById('rtnShipToLine1ID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToLine1.value;
//window.opener.document.getElementById('rtnShipToLine2ID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToLine2.value;

//window.opener.document.getElementById('rtnShipToCityID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToCity.value;
//window.opener.document.getElementById('rtnShipToStateID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToState.value;
//window.opener.document.getElementById('rtnShipToZipID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToZip.value;
//window.opener.document.getElementById('rtnShipToPhoneID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipToPhone.value;
//window.opener.document.getElementById('rtnShipFromCompanyID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromCompany.value;
//alert("rtnShipFromCompany :"+document.aascShipExecPackageOptionsForm.rtnShipFromCompany.value);
//window.opener.document.getElementById('rtnShipFromContactID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromContact.value;
//window.opener.document.getElementById('rtnShipFromLine1ID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromLine1.value;
//window.opener.document.getElementById('rtnShipFromLine2ID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromLine2.value;

//window.opener.document.getElementById('rtnShipFromCityID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromCity.value;
//window.opener.document.getElementById('rtnShipFromSateID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromSate.value;

//window.opener.document.getElementById('rtnShipFromZipID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromZip.value;
//window.opener.document.getElementById('rtnShipFromPhoneID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipFromPhone.value;

// window.opener.document.getElementById('packageSaveCheckID'+packageCount).value="Y"; 
//window.opener.document.getElementById('returnDescriptionriptionID'+packageCount).value=document.aascShipExecPackageOptionsForm.returnDescription.value;
//window.opener.ShipInsertForm['rtnShipMethod'+packageCount].value=document.aascShipExecPackageOptionsForm.rtnShipMethod.value;

// window.opener.document.getElementById('rtnShipMethodID'+packageCount).value=document.aascShipExecPackageOptionsForm.ajaxShipMethod.value;  
// window.opener.document.getElementById('rtnPayMethodID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnPayMethod.options[document.aascShipExecPackageOptionsForm.rtnPayMethod.selectedIndex].text;
// window.opener.document.getElementById('rtnPayMethodCodeID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnPayMethod.options.value;
// window.opener.document.getElementById('rtnACNumberID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnACNumber.value;  
// window.opener.document.getElementById('rtnShipMethodNameID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnShipMethod.value;
// window.opener.document.getElementById('rtnDeclaredValueID'+packageCount).value=document.aascShipExecPackageOptionsForm.rtnDeclaredValue.value;
//alert("ajaxUpsServiceLevelCode :"+document.aascShipExecPackageOptionsForm.ajaxUpsServiceLevelCode.value);
//var val = document.aascShipExecPackageOptionsForm.ajaxUpsServiceLevelCode.value;

// window.opener.document.getElementById('rtnajaxUpsServiceLevelCodeID'+packageCount).value = parseInt(document.aascShipExecPackageOptionsForm.ajaxUpsServiceLevelCode.value);
//alert("ajaxUpsServiceLevelCode :"+document.aascShipExecPackageOptionsForm.ajaxUpsServiceLevelCode.value);
/*
window.opener.document.getElementById('rtnTPCompanynameID'+packageCount).value=document.aascShipExecPackageOptionsForm.companyName.value;
window.opener.document.getElementById('rtnTPAddressID'+packageCount).value=document.aascShipExecPackageOptionsForm.address.value;
window.opener.document.getElementById('rtnTPStateID'+packageCount).value=document.aascShipExecPackageOptionsForm.state.value;
window.opener.document.getElementById('rtnTPCityID'+packageCount).value=document.aascShipExecPackageOptionsForm.city.value;
window.opener.document.getElementById('rtnTPPostalCodeID'+packageCount).value=document.aascShipExecPackageOptionsForm.postalCode.value;
window.opener.document.getElementById('rtnTPCountrySymbolID'+packageCount).value=document.aascShipExecPackageOptionsForm.countrySymbol.value;
*/ 

if(document.aascShipExecPackageOptionsForm.HazMatFlag.checked)
          {
            document.aascShipExecPackageOptionsForm.HazMatFlag.value="Y";
          }
          else
          {
            document.aascShipExecPackageOptionsForm.HazMatFlag.value="N";
          }
//          if(document.aascShipExecPackageOptionsForm.HazMatOverPackFlag.checked)
//          {
//            document.aascShipExecPackageOptionsForm.HazMatOverPackFlag.value="Y";
//          }
//          else
//          {
//            document.aascShipExecPackageOptionsForm.HazMatOverPackFlag.value="N";
//          }

          window.opener.document.getElementById('HazMatFlagID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatFlag.value;
        window.opener.document.getElementById('HazMatCountID'+packageCount).value = document.aascShipExecPackageOptionsForm.hazmatListCount.value;
//        window.opener.document.getElementById('HazMatOverPackFlagID'+packageCount).value=document.aascShipExecPackageOptionsForm.HazMatOverPackFlag.value;
        window.opener.document.getElementById('HazMatTypeID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialType.value;
//        alert("window.opener.document.getElementById('HazMatTypeID'+packageCount).value :: "+window.opener.document.getElementById('HazMatTypeID'+packageCount).value);
        window.opener.document.getElementById('HazMatClassID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialClass.value;
        
         window.opener.document.getElementById('HazMatQtyID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialQuantity.value;
     window.opener.document.getElementById('HazMatUnitID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialUnit.value;
     
      window.opener.document.getElementById('HazMatIdNoID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatIdentificationNo.value;
    window.opener.document.getElementById('HazMatPkgGroupID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialPkgGroup.value;
    window.opener.document.getElementById('HazMatDOTLabelID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatDOTLabelType.value;
    window.opener.document.getElementById('HazMatEmerContactNoID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatEmergencyContactNo.value;
    window.opener.document.getElementById('HazMatEmerContactNameID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatEmergencyContactName.value;
    window.opener.document.getElementById('HazMatIdID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazardousMaterialId.value;
      window.opener.document.getElementById('HazMatPackagingCntID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatPackagingCnt.value;
    window.opener.document.getElementById('HazMatPackagingUnitsID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatPackagingUnits.value;
      window.opener.document.getElementById('HazMatTechnicalNameID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatTechnicalName.value;
         window.opener.document.getElementById('HazMatSignatureNameID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatSignatureName.value;
        window.opener.document.getElementById('HazMatPackInstructionsID'+packageCount).value = document.aascShipExecPackageOptionsForm.HazMatPackInstructions.value;
//            window.close();
         
}//end of the method.
