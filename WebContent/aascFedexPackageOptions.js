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

    var  packCount=document.aascPackageOptionsForm.packageCount.value;
    var totalShippedQuantity =0.0;
    var shippedQuantity=0.0; 
  
    var shipStatusFlag = window.opener.document.getElementById("shipmentStatusFlagID").value;
    document.aascPackageOptionsForm.rtnShipFromCompany.value=window.opener.document.getElementById('rtnShipFromCompanyID'+packCount).value;  
    document.aascPackageOptionsForm.rtnShipToCompany.value=window.opener.document.getElementById('rtnShipToCompanyID'+packCount).value;  
    document.aascPackageOptionsForm.rtnShipFromContact.value=window.opener.document.getElementById('rtnShipFromContactID'+packCount).value;  
    document.aascPackageOptionsForm.rtnShipToContact.value=window.opener.document.getElementById('rtnShipToContactID'+packCount).value;  
    document.aascPackageOptionsForm.rtnShipFromLine1.value=window.opener.document.getElementById('rtnShipFromLine1ID'+packCount).value;  
    document.aascPackageOptionsForm.rtnShipToLine1.value=window.opener.document.getElementById('rtnShipToLine1ID'+packCount).value;  
    document.aascPackageOptionsForm.rtnShipFromLine2.value= window.opener.document.getElementById('rtnShipFromLine2ID'+packCount).value;  
    document.aascPackageOptionsForm.rtnShipToLine2.value=window.opener.document.getElementById('rtnShipToLine2ID'+packCount).value;  
    document.aascPackageOptionsForm.rtnShipFromCity.value=window.opener.document.getElementById('rtnShipFromCityID'+packCount).value;  
    document.aascPackageOptionsForm.rtnShipFromSate.value=window.opener.document.getElementById('rtnShipFromSateID'+packCount).value;  
    document.aascPackageOptionsForm.rtnShipFromZip.value=window.opener.document.getElementById('rtnShipFromZipID'+packCount).value;
    document.aascPackageOptionsForm.rtnShipToCity.value=window.opener.document.getElementById('rtnShipToCityID'+packCount).value;  
    document.aascPackageOptionsForm.rtnShipToState.value=window.opener.document.getElementById('rtnShipToStateID'+packCount).value;  
    document.aascPackageOptionsForm.rtnShipToZip.value=window.opener.document.getElementById('rtnShipToZipID'+packCount).value;  

    if((window.opener.document.getElementById('rtnShipFromPhoneID'+packCount).value)!="")
    {
       document.aascPackageOptionsForm.rtnShipFromPhone.value= window.opener.document.getElementById('rtnShipFromPhoneID'+packCount).value;
    }
    else{

        var rtnShipFromPhone=document.aascPackageOptionsForm.rtnShipFromPhone.value;
        if(rtnShipFromPhone == null || rtnShipFromPhone =="null" || rtnShipFromPhone =="")
        {
            var shipToPhoneMain=window.opener.document.getElementById('phoneNumberId').value;
            
            if(((shipToPhoneMain != null || shipToPhoneMain !="null" || shipToPhoneMain !="")) && window.opener.document.getElementById('trackingNumberID'+packCount).value=="")
            {
            document.aascPackageOptionsForm.rtnShipFromPhone.value=shipToPhoneMain;
            }
        }
    } 
    var rtnDeclaredValue=document.aascPackageOptionsForm.rtnDeclaredValue.value;
        
    if(rtnDeclaredValue == null || rtnDeclaredValue =="null" || rtnDeclaredValue =="" || rtnDeclaredValue==0 || rtnDeclaredValue==0.0)
    {
         var packageDeclaredValueMain=window.opener.document.getElementById('packageDeclaredValueID'+packCount).value; 
           
         if((packageDeclaredValueMain != null || packageDeclaredValueMain !="null" || packageDeclaredValueMain !="") && window.opener.document.getElementById('trackingNumberID'+packCount).value=="")
         {
            document.aascPackageOptionsForm.rtnDeclaredValue.value=packageDeclaredValueMain;
         }
    }
       
    document.aascPackageOptionsForm.rtnShipToPhone.value=window.opener.document.getElementById('rtnShipToPhoneID'+packCount).value;  
        
       
    if(window.opener.document.getElementById('rtnRMAID'+packCount).value=="null")
    {
        document.aascPackageOptionsForm.rtnRMA.value ="";
    }
    else
    {
        document.aascPackageOptionsForm.rtnRMA.value=window.opener.document.getElementById('rtnRMAID'+packCount).value; 
    }
    document.aascPackageOptionsForm.rtnTrackingNumber.value=window.opener.document.getElementById('rtnTrackingNumberID'+packCount).value;
    document.aascPackageOptionsForm.rtnShipmentCost.value=window.opener.document.getElementById('rtnShipmentCostID'+packCount).value;
 
    document.aascPackageOptionsForm.packageSaveCheck.value=window.opener.document.getElementById('packageSaveCheckID'+packCount).value;
    document.aascPackageOptionsForm.ajaxAfterShipDropOffType.value=window.opener.document.getElementById('rtnDropOfTypeID'+packCount).value;  
    document.aascPackageOptionsForm.ajaxAfterShipPackaging.value=window.opener.document.getElementById('rtnPackageListID'+packCount).value;  
    document.aascPackageOptionsForm.ajaxAfterShipCarrAccNumber.value=window.opener.document.getElementById('rtnACNumberID'+packCount).value;  
    var chCod=window.opener.document.getElementById('chCODID'+packCount).value;
    var codAmt=window.opener.document.getElementById('codAmtID'+packCount).value;
    if(chCod=="Y")
    {
       document.aascPackageOptionsForm.chCOD.checked =true;
       document.aascPackageOptionsForm.chCOD.value ="Y";
       document.aascPackageOptionsForm.codAmt.readOnly=false;
    }
    else
    {
    document.aascPackageOptionsForm.chCOD.checked =false;
       document.aascPackageOptionsForm.chCOD.value ="N";
       document.aascPackageOptionsForm.codAmt.readOnly=true;
    }
    document.aascPackageOptionsForm.codAmt.value=codAmt;
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
         document.aascPackageOptionsForm.holdAtLocation.checked =true;
         document.aascPackageOptionsForm.holdAtLocation.value ="Y";
         document.aascPackageOptionsForm.halPhone.value=phone;
         document.aascPackageOptionsForm.halAddrLine1.value=line1;
         document.aascPackageOptionsForm.halAddrLine2.value=line2;
         document.aascPackageOptionsForm.halCity.value=city;
         document.aascPackageOptionsForm.halState.value=state;
         document.aascPackageOptionsForm.halZip.value=postalCode;
     }
    else{
    document.aascPackageOptionsForm.holdAtLocation.checked =false;
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
             
             document.aascPackageOptionsForm.holdAtLocation.disabled = true;
             document.aascPackageOptionsForm.halPhone.disabled = true;
             document.aascPackageOptionsForm.halAddrLine1.disabled = true;
             document.aascPackageOptionsForm.halAddrLine2.disabled = true;
             document.aascPackageOptionsForm.halCity.disabled = true;
             document.aascPackageOptionsForm.halState.disabled = true;
             document.aascPackageOptionsForm.halZip.disabled = true;
         
         }
     }
     else
     {
        holdatLocationfn();
     }
     
     //=================================================
     
    var dryIce=window.opener.document.getElementById('chDryIceID'+packCount).value;
    var dryIceWeight=window.opener.document.getElementById('dryIceWeightID'+packCount).value;
    var dryIceUnits=window.opener.document.getElementById('dryIceUnitsID'+packCount).value;
    
    if(window.opener.document.getElementById('flagShip1').value !='Y'){
        if(dryIce=="Y")
        {
           document.aascPackageOptionsForm.chDryIce.checked =true;
           document.aascPackageOptionsForm.chDryIce.value ="Y";
           document.aascPackageOptionsForm.dryIceWeight.value=dryIceWeight;
           document.aascPackageOptionsForm.dryIceUnits.value=dryIceUnits;
        }
        else
        {
           document.aascPackageOptionsForm.chDryIce.checked =false;
           document.aascPackageOptionsForm.chDryIce.value ="N";
           document.aascPackageOptionsForm.dryIceWeight.disabled=true;
           document.aascPackageOptionsForm.dryIceUnits.disabled = true;
        }
    }else{
        if(dryIce=="Y")
        {
           document.aascPackageOptionsForm.chDryIce.checked =true;
           document.aascPackageOptionsForm.chDryIce.value ="Y";
           document.aascPackageOptionsForm.chDryIce.disabled = true;
           document.aascPackageOptionsForm.dryIceWeight.value=dryIceWeight;
           document.aascPackageOptionsForm.dryIceUnits.value=dryIceUnits;
           document.aascPackageOptionsForm.dryIceWeight.disabled=true;
           document.aascPackageOptionsForm.dryIceUnits.disabled = true;
        }
        else
        {
           document.aascPackageOptionsForm.chDryIce.checked =false;
           document.aascPackageOptionsForm.chDryIce.value ="N";
           document.aascPackageOptionsForm.chDryIce.disabled = true;
           document.aascPackageOptionsForm.dryIceWeight.disabled=true;
           document.aascPackageOptionsForm.dryIceUnits.disabled = true;
        }
    }
    if(packageTrackingNo != '')
    {
    
        document.aascPackageOptionsForm.dryIceWeight.value=dryIceWeight;
        document.aascPackageOptionsForm.dryIceUnits.value=dryIceUnits;
    
    }
     
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

     if(hazMatFlag == 'Y')
     {
          var hazMatQty = window.opener.document.getElementById('HazMatQtyID'+packCount).value;
          var hazMatUnit = window.opener.document.getElementById('HazMatUnitID'+packCount).value;
          document.aascPackageOptionsForm.HazardousMaterial.checked = true;
          document.aascPackageOptionsForm.HazardousMaterialCharges.value = hazMatCharges;
          document.aascPackageOptionsForm.HazardousMaterialType.value = hazMatType;
          document.aascPackageOptionsForm.HazardousMaterialQuantity.value = hazMatQty;
          document.aascPackageOptionsForm.HazardousMaterialUnit.value = hazMatUnit;

          document.aascPackageOptionsForm.HazardousMaterialClass.value = hazMatClass;
          document.aascPackageOptionsForm.HazardousMaterialId.value = hazardousMaterialId;
          document.aascPackageOptionsForm.HazardousMaterialPkgGroup.value = hazMatPkgGroup;
          document.aascPackageOptionsForm.HazMatDOTLabelType.value = hazMatDOTLabel;
          document.aascPackageOptionsForm.HazMatIdentificationNo.value = hazMatIdNo;
    
          document.aascPackageOptionsForm.HazMatEmergencyContactNo.value = hazMatEmerContactNo;
          document.aascPackageOptionsForm.HazMatEmergencyContactName.value = hazMatEmerContactName;

          document.aascPackageOptionsForm.HazMatPackagingCnt.value = hazmatPkgCnt;
          document.aascPackageOptionsForm.HazMatPackagingUnits.value = hazmatPkgUnits;
          document.aascPackageOptionsForm.HazMatTechnicalName.value = hazmatTechnicalName;
          document.aascPackageOptionsForm.HazMatSignatureName.value = hazmatSignatureName;
     }
     else{
      document.aascPackageOptionsForm.HazardousMaterial.checked = false;
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
           document.forms['aascPackageOptionsForm'].HazardousMaterialClass.options[0] = new Option(optionValue,optionValue); 
           document.forms['aascPackageOptionsForm'].HazardousMaterialPkgGroup.options[0] = new Option(optionValuePkgGroup,optionValuePkgGroup);  

           document.aascPackageOptionsForm.HazardousMaterial.disabled = true;
           document.aascPackageOptionsForm.HazardousMaterialType.disabled = true;
           document.aascPackageOptionsForm.HazardousMaterialClass.disabled = true;
           
           document.aascPackageOptionsForm.HazardousMaterialQuantity.disabled = true;
           document.aascPackageOptionsForm.HazardousMaterialUnit.disabled = true;
         
           document.aascPackageOptionsForm.HazardousMaterialId.disabled = true;
           document.aascPackageOptionsForm.HazMatIdentificationNo.disabled = true;
           document.aascPackageOptionsForm.HazMatDOTLabelType.disabled = true;
           document.aascPackageOptionsForm.HazMatEmergencyContactNo.disabled = true;
           document.aascPackageOptionsForm.HazMatEmergencyContactName.disabled = true;
           document.aascPackageOptionsForm.HazardousMaterialPkgGroup.disabled = true;

           document.aascPackageOptionsForm.HazMatPackagingCnt.disabled = true;
           document.aascPackageOptionsForm.HazMatPackagingUnits.disabled = true;
             
           document.aascPackageOptionsForm.HazMatTechnicalName.disabled = true;
           document.aascPackageOptionsForm.HazMatSignatureName.disabled = true;
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
       if(carrierCode=="111")
       {
         getHazMatPkgGroup();
       }
       hazardousMaterialFn();
     }
     var packageTrackingNumber=window.opener.document.getElementById('trackingNumberID'+packCount).value;
     if(packageTrackingNumber!='')
     {
      document.aascPackageOptionsForm.chCOD.disabled =true;
      document.aascPackageOptionsForm.codAmt.disabled=true;
     }
     if(window.opener.document.getElementById('shipMethodId').disabled==true)
     {
        var packageTrackingNumber=window.opener.document.getElementById('trackingNumberID'+packCount).value;
        document.aascPackageOptionsForm.signatureOptionCheck.value=window.opener.document.getElementById('signatureOptionID'+packCount).value;

        document.aascPackageOptionsForm.returnShipment.value=window.opener.document.getElementById('returnShipmentID'+packCount).value;
 
        document.aascPackageOptionsForm.PackageSurcharge.value=window.opener.document.getElementById('PackageSurchargeID'+packCount).value;
      
        document.aascPackageOptionsForm.PackageShipmentCost.value=window.opener.document.getElementById('PackageShipmentCostID'+packCount).value;

        if(document.aascPackageOptionsForm.returnShipment.value=="NONRETURN" || document.aascPackageOptionsForm.returnShipment.value=="" || document.aascPackageOptionsForm.returnShipment.value==null)
        {
           document.aascPackageOptionsForm.returnShipment.checked=false;
        }

        if(document.aascPackageOptionsForm.returnShipment.value=="PRINTRETURNLABEL")
        {
           document.aascPackageOptionsForm.returnShipment.checked=true;
        }
        if(document.aascPackageOptionsForm.signatureOptionCheck.value=="NONE" || document.aascPackageOptionsForm.signatureOptionCheck.value=="" || document.aascPackageOptionsForm.signatureOptionCheck.value==null)
        {
             document.aascPackageOptionsForm.signatureOptionCheck.checked=false
             document.aascPackageOptionsForm.signatureOption[0].disabled = true;
             document.aascPackageOptionsForm.signatureOption[1].disabled = true;
             document.aascPackageOptionsForm.signatureOption[0].checked = false;
             document.aascPackageOptionsForm.signatureOption[1].checked = false;
             document.aascPackageOptionsForm.signatureOption[2].disabled = true;
             document.aascPackageOptionsForm.signatureOption[3].disabled = true;
             document.aascPackageOptionsForm.signatureOption[2].checked = false;
             document.aascPackageOptionsForm.signatureOption[3].checked = false;
        }
        if(document.aascPackageOptionsForm.signatureOptionCheck.value!="NONE" && document.aascPackageOptionsForm.signatureOptionCheck.value!="" && document.aascPackageOptionsForm.signatureOptionCheck.value!=null)
        {
             document.aascPackageOptionsForm.signatureOptionCheck.checked=true;
             document.aascPackageOptionsForm.signatureOption[0].disabled = false;
             document.aascPackageOptionsForm.signatureOption[1].disabled = false;
             document.aascPackageOptionsForm.signatureOption[2].disabled = false;
             document.aascPackageOptionsForm.signatureOption[3].disabled = false;
        }
        if(document.aascPackageOptionsForm.signatureOptionCheck.value=="DIRECT")
        {
          document.aascPackageOptionsForm.signatureOption[0].checked=true;
          document.aascPackageOptionsForm.signatureOption[1].checked=false;
          document.aascPackageOptionsForm.signatureOption[2].checked=false;
          document.aascPackageOptionsForm.signatureOption[3].checked=false;
        }

        if(document.aascPackageOptionsForm.signatureOptionCheck.value=="ADULT")
        {
          document.aascPackageOptionsForm.signatureOption[1].checked=true;
          document.aascPackageOptionsForm.signatureOption[0].checked=false;
          document.aascPackageOptionsForm.signatureOption[2].checked=false;      
          document.aascPackageOptionsForm.signatureOption[3].checked=false;
        }
        if(document.aascPackageOptionsForm.signatureOptionCheck.value=="DELIVERWITHOUTSIGNATURE")
        {
          document.aascPackageOptionsForm.signatureOption[0].checked=false;
          document.aascPackageOptionsForm.signatureOption[1].checked=false;
          document.aascPackageOptionsForm.signatureOption[2].checked=true;
          document.aascPackageOptionsForm.signatureOption[3].checked=false;
        }
      
        if(document.aascPackageOptionsForm.signatureOptionCheck.value=="INDIRECT")
        {
          document.aascPackageOptionsForm.signatureOption[0].checked=false;
          document.aascPackageOptionsForm.signatureOption[1].checked=false;
          document.aascPackageOptionsForm.signatureOption[2].checked=false;
          document.aascPackageOptionsForm.signatureOption[3].checked=true;
        }  
        if((packageTrackingNumber==null || packageTrackingNumber=="") && ( shipStatusFlag!="B" && shipStatusFlag !="P"))
        {
            if(totalShippedQuantity==0)
            {
                document.aascPackageOptionsForm.signatureOptionCheck.disabled=true;
                document.aascPackageOptionsForm.signatureOption[0].disabled = true;
                document.aascPackageOptionsForm.signatureOption[1].disabled = true;
                document.aascPackageOptionsForm.signatureOption[2].disabled = true;
                document.aascPackageOptionsForm.signatureOption[3].disabled = true;
                document.aascPackageOptionsForm.returnShipment.disabled=true; 
                document.aascPackageOptionsForm.chCOD.disabled =true;
                document.aascPackageOptionsForm.codAmt.disabled=true;
            }
            else
            {
              document.aascPackageOptionsForm.signatureOptionCheck.disabled=false;

              document.aascPackageOptionsForm.signatureOption[0].disabled = false;
              document.aascPackageOptionsForm.signatureOption[1].disabled = false;
              document.aascPackageOptionsForm.signatureOption[2].disabled = false;
              document.aascPackageOptionsForm.signatureOption[3].disabled = false;

              document.aascPackageOptionsForm.returnShipment.disabled=false; 
              document.aascPackageOptionsForm.chCOD.disabled =false;
              document.aascPackageOptionsForm.codAmt.disabled=false;
           }
        }
        else
        {
          document.aascPackageOptionsForm.signatureOptionCheck.disabled=true;
          document.aascPackageOptionsForm.signatureOption[0].disabled = true;
          document.aascPackageOptionsForm.signatureOption[1].disabled = true;
          document.aascPackageOptionsForm.signatureOption[2].disabled = true;
          document.aascPackageOptionsForm.signatureOption[3].disabled = true;
        
          document.aascPackageOptionsForm.returnShipment.disabled=true; 
          document.aascPackageOptionsForm.chCOD.disabled =true;
          document.aascPackageOptionsForm.codAmt.disabled=true;
          document.aascPackageOptionsForm.HazardousMaterial.disabled=true;  // Added code to fix #2747
          document.aascPackageOptionsForm.holdAtLocation.disabled=true;     // Added code to fix #2747
           
//          document.aascPackageOptionsForm.save.src="buttons/aascSaveOff1.png"; //document.serialNumberForm.savebutton.src="buttons/aascSaveOff1.png";
        }

        rmaCheck();
    }
    else 
    {
         document.aascPackageOptionsForm.signatureOptionCheck.value=window.opener.document.getElementById('signatureOptionID'+packCount).value;
         document.aascPackageOptionsForm.returnShipment.value=window.opener.document.getElementById('returnShipmentID'+packCount).value;
         document.aascPackageOptionsForm.PackageSurcharge.value=window.opener.document.getElementById('PackageSurchargeID'+packCount).value;
         if(document.aascPackageOptionsForm.returnShipment.value=="NONRETURN" || document.aascPackageOptionsForm.returnShipment.value=="" || document.aascPackageOptionsForm.returnShipment.value==null)
         {
             document.aascPackageOptionsForm.returnShipment.checked=false;
         }

         if(document.aascPackageOptionsForm.returnShipment.value=="PRINTRETURNLABEL")
         {
               document.aascPackageOptionsForm.returnShipment.checked=true;
         }
         if(document.aascPackageOptionsForm.signatureOptionCheck.value=="NONE" || document.aascPackageOptionsForm.signatureOptionCheck.value=="" || document.aascPackageOptionsForm.signatureOptionCheck.value==null)
         {
             document.aascPackageOptionsForm.signatureOptionCheck.checked=false;
             document.aascPackageOptionsForm.signatureOption[0].disabled = true;
             document.aascPackageOptionsForm.signatureOption[1].disabled = true;
             document.aascPackageOptionsForm.signatureOption[0].checked = false;
             document.aascPackageOptionsForm.signatureOption[1].checked = false;
             document.aascPackageOptionsForm.signatureOption[2].disabled = true;
             document.aascPackageOptionsForm.signatureOption[3].disabled = true;
             document.aascPackageOptionsForm.signatureOption[2].checked = false;
             document.aascPackageOptionsForm.signatureOption[3].checked = false;
         }

         if(document.aascPackageOptionsForm.signatureOptionCheck.value!="NONE" && document.aascPackageOptionsForm.signatureOptionCheck.value!="" && document.aascPackageOptionsForm.signatureOptionCheck.value!=null)
         {
             document.aascPackageOptionsForm.signatureOptionCheck.checked=true;
             document.aascPackageOptionsForm.signatureOption[0].disabled = false;
             document.aascPackageOptionsForm.signatureOption[1].disabled = false;
             document.aascPackageOptionsForm.signatureOption[2].disabled = false;
             document.aascPackageOptionsForm.signatureOption[3].disabled = false;
             if(document.aascPackageOptionsForm.signatureOptionCheck.value=="DIRECT")
             {
                  document.aascPackageOptionsForm.signatureOption[0].checked=true;
                  document.aascPackageOptionsForm.signatureOption[1].checked=false;
                  document.aascPackageOptionsForm.signatureOption[2].checked=false;
                  document.aascPackageOptionsForm.signatureOption[3].checked=false;
             }
             if(document.aascPackageOptionsForm.signatureOptionCheck.value=="ADULT")
             {        
                document.aascPackageOptionsForm.signatureOption[1].checked=true;
                document.aascPackageOptionsForm.signatureOption[0].checked=false;
                document.aascPackageOptionsForm.signatureOption[2].checked=false;
                document.aascPackageOptionsForm.signatureOption[3].checked=false;
             }
             if(document.aascPackageOptionsForm.signatureOptionCheck.value=="DELIVERWITHOUTSIGNATURE")
             {
                  document.aascPackageOptionsForm.signatureOption[2].checked=true;
                  document.aascPackageOptionsForm.signatureOption[0].checked=false;
                  document.aascPackageOptionsForm.signatureOption[1].checked=false;
                  document.aascPackageOptionsForm.signatureOption[3].checked=false;
             }      
             if(document.aascPackageOptionsForm.signatureOptionCheck.value=="INDIRECT")
             {
                  document.aascPackageOptionsForm.signatureOption[3].checked=true;
                  document.aascPackageOptionsForm.signatureOption[0].checked=false;
                  document.aascPackageOptionsForm.signatureOption[2].checked=false;
                  document.aascPackageOptionsForm.signatureOption[1].checked=false;
             }
          }
          rmaCheck();
    }
    document.aascPackageOptionsForm.rtnShipFromCompany.value=window.opener.document.getElementById('customerName').options[window.opener.document.getElementById('customerName').selectedIndex].value;
    document.aascPackageOptionsForm.rtnShipFromContact.value=window.opener.document.getElementById('contactNameId').value;
    if(window.opener.document.getElementById('shipToAddressId').value!="")
    {
    document.aascPackageOptionsForm.rtnShipFromLine1.value=window.opener.document.getElementById('shipToAddressId').value;
    }
    document.aascPackageOptionsForm.rtnShipFromLine2.value=window.opener.document.getElementById('shipToAddrLine2Id').value;
    
    document.aascPackageOptionsForm.rtnShipFromCity.value=window.opener.document.getElementById('city').value;
    document.aascPackageOptionsForm.rtnShipFromSate.value=window.opener.document.getElementById('state').value;
    document.aascPackageOptionsForm.rtnShipFromZip.value=window.opener.document.getElementById('zip').value;
    var rtnShipToCompanyFromPage=window.opener.document.getElementById('shipFromLoc').value;
    var returnShipToCompanyName=rtnShipToCompanyFromPage.substring(0,rtnShipToCompanyFromPage.indexOf('*'));

    document.aascPackageOptionsForm.rtnShipFromPhone.value = window.opener.document.getElementById('phoneNumberId').value;      //  Added by suman G for #2535
    document.aascPackageOptionsForm.rtnShipToCompany.value=returnShipToCompanyName;
    document.aascPackageOptionsForm.rtnShipToContact.value=window.opener.document.getElementById('shipFromContactName').value;

    document.aascPackageOptionsForm.rtnShipToLine1.value=window.opener.document.getElementById('shipFromAddressLine1').value;
    document.aascPackageOptionsForm.rtnShipToLine2.value=window.opener.document.getElementById('shipFromAddressLine2').value;
    document.aascPackageOptionsForm.rtnShipToCity.value=window.opener.document.getElementById('shipFromCity').value;
    document.aascPackageOptionsForm.rtnShipToState.value=window.opener.document.getElementById('shipFromState').value;
    document.aascPackageOptionsForm.rtnShipToZip.value=window.opener.document.getElementById('shipFromPostalCode').value;
    document.aascPackageOptionsForm.rtnShipToPhone.value=window.opener.document.getElementById('shipFromPhoneNumber1').value;
    
    var rtnShipMethodvalLen=window.opener.document.getElementById('rtnShipMethodID'+packCount).value.length;
    var rtnTrackingNumber = window.opener.document.getElementById('rtnTrackingNumberID'+packCount).value;
    if(rtnTrackingNumber == "" || rtnTrackingNumber == null || rtnTrackingNumber == "null")
    {
    document.aascPackageOptionsForm.rtnTrackingNumber.value="";
    }
    if(packageTrackingNumber!="" )
    {
      getCcCsl('3');// after ship 
    }
    else
    {
      var testShipMethodvalue=document.aascPackageOptionsForm.rtnShipMethod.value;  
      var testForFedexUps=testShipMethodvalue.substring(0,testShipMethodvalue.indexOf("|"));
      if(document.aascPackageOptionsForm.packageSaveCheck.value=="Y")
      {    
        getCcCsl('3');// after ship
      }
      else
      {
          getCcCsl('1'); // on load
      }
    }
    var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;
    if(document.aascPackageOptionsForm.HazardousMaterial.checked == true)
    {
        if(str.substring(0,3)=="111")
        {        
         document.aascPackageOptionsForm.HazardousMaterialUnit.disabled = false;
         document.aascPackageOptionsForm.HazardousMaterialQuantity.disabled = false;
        }
        else
        {
             document.aascPackageOptionsForm.HazardousMaterialUnit.disabled = true;
             document.aascPackageOptionsForm.HazardousMaterialQuantity.disabled = true;
        }
        if(packageTrackingNo != '')
        {
             document.aascPackageOptionsForm.HazardousMaterialUnit.disabled = true;
             document.aascPackageOptionsForm.HazardousMaterialQuantity.disabled = true;
        }
    }
    if(window.opener.document.getElementById('flagShip1').value=='Y')
    {
      //alert('here');
      rtnShipMethod=window.opener.document.getElementById('rtnShipMethodID'+packCount).value;
      rtnShipMethod=rtnShipMethod.substring(0,rtnShipMethod.length-6);
      document.aascPackageOptionsForm.rtnShipMethod.options[document.aascPackageOptionsForm.rtnShipMethod.selectedIndex].text=rtnShipMethod;
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

function packageDeclaredValuefn()
{
    var declareval;
    if(isNaN(document.aascPackageOptionsForm.packageDeclaredValue.value))
    {
        alert('Enter Only Number in Declared value Field');
        return false;
    }
    if(document.aascPackageOptionsForm.packageDeclaredValue.value >=500)
    {
        document.aascPackageOptionsForm.signatureOptionCheck.checked = true;
        document.aascPackageOptionsForm.signatureOption[0].disabled = false;
        document.aascPackageOptionsForm.signatureOption[0].checked = true;
        document.aascPackageOptionsForm.signatureOption[1].disabled = false;
        document.aascPackageOptionsForm.signatureOption[1].checked = false;
        document.aascPackageOptionsForm.signatureOption[0].value="DIRECT";
        document.aascPackageOptionsForm.signatureOption[1].value="NONE";    
    }
    else
    {
        document.aascPackageOptionsForm.signatureOptionCheck.checked = false;
        document.aascPackageOptionsForm.signatureOption[0].disabled = true;
        document.aascPackageOptionsForm.signatureOption[1].disabled = true;
        document.aascPackageOptionsForm.signatureOption[0].checked = false;
        document.aascPackageOptionsForm.signatureOption[1].checked = false;
        document.aascPackageOptionsForm.signatureOption[0].value="NONE";
        document.aascPackageOptionsForm.signatureOption[1].value="NONE";
    }
 } 
 
function signatureOptionCheckfn()
{ 
  if(document.aascPackageOptionsForm.signatureOptionCheck.checked == true) 
  {
      document.aascPackageOptionsForm.signatureOption[0].disabled = false;
      document.aascPackageOptionsForm.signatureOption[1].disabled = false;
      document.aascPackageOptionsForm.signatureOption[2].disabled = false;
      document.aascPackageOptionsForm.signatureOption[3].disabled = false;
  }
  else
  {  
      document.aascPackageOptionsForm.signatureOption[0].disabled = true; 
      document.aascPackageOptionsForm.signatureOption[1].disabled = true;
      document.aascPackageOptionsForm.signatureOption[0].checked = false;
      document.aascPackageOptionsForm.signatureOption[1].checked = false;
      document.aascPackageOptionsForm.signatureOption[0].value = "NONE";
      document.aascPackageOptionsForm.signatureOption[1].value = "NONE";
      document.aascPackageOptionsForm.signatureOption[2].disabled = true;
      document.aascPackageOptionsForm.signatureOption[3].disabled = true;
      document.aascPackageOptionsForm.signatureOption[2].checked = false;
      document.aascPackageOptionsForm.signatureOption[3].checked = false;
      document.aascPackageOptionsForm.signatureOption[2].value = "NONE";
      document.aascPackageOptionsForm.signatureOption[3].value = "NONE";
  }
}

function returnShipmentfn()
{

    if(document.aascPackageOptionsForm.returnShipment.checked ==true)
    {
        document.aascPackageOptionsForm.returnShipment.value="PRINTRETURNLABEL";
    }
    else
    {
        document.aascPackageOptionsForm.returnShipment.value="NONRETURN";
        document.aascPackageOptionsForm.rtnDeclaredValue.value=0.0;
        document.aascPackageOptionsForm.rtnRMA.value="";
    }
}

function signatureOption1()
{
  if(document.aascPackageOptionsForm.signatureOptionCheck.checked == true)
  {
    if(document.aascPackageOptionsForm.signatureOption[0].checked ==true)
    {
        document.aascPackageOptionsForm.signatureOption[0].value="DIRECT";
        document.aascPackageOptionsForm.signatureOption[1].value="NONE";
        document.aascPackageOptionsForm.signatureOption[2].value="NONE";
        document.aascPackageOptionsForm.signatureOption[3].value="NONE";
    }
    else
    {
        document.aascPackageOptionsForm.signatureOption[0].value="NONE";
    }
  }
  else
      document.aascPackageOptionsForm.signatureOption[0].value="NONE";
}

function signatureOption2()
{
  if(document.aascPackageOptionsForm.signatureOptionCheck.checked == true)
  {
    if(document.aascPackageOptionsForm.signatureOption[1].checked ==true)
    {
        document.aascPackageOptionsForm.signatureOption[1].value="ADULT";
        document.aascPackageOptionsForm.signatureOption[0].value="NONE";
        document.aascPackageOptionsForm.signatureOption[2].value="NONE";
        document.aascPackageOptionsForm.signatureOption[3].value="NONE";
    }
    else
    {
        document.aascPackageOptionsForm.signatureOption[1].value="NONE";
    }
  }
  else
    document.aascPackageOptionsForm.signatureOption[1].value="NONE";
}


function signatureOption3()
{
  var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;
  if(str.substring(0,3)=="110")
  {
    if(document.aascPackageOptionsForm.signatureOptionCheck.checked == true)
    {
      if(document.aascPackageOptionsForm.signatureOption[2].checked ==true)
      {
      document.aascPackageOptionsForm.signatureOption[0].value="NONE";
      document.aascPackageOptionsForm.signatureOption[1].value="NONE";
      document.aascPackageOptionsForm.signatureOption[2].value="DELIVERWITHOUTSIGNATURE";
      document.aascPackageOptionsForm.signatureOption[3].value="NONE";
      }
      else
      {
      document.aascPackageOptionsForm.signatureOption[2].value="NONE";
      }
    }
    else
    document.aascPackageOptionsForm.signatureOption[2].value="NONE";
  }
  else
  {
    alert("Fedex Ground Doesnot Support this Option");
   document.aascPackageOptionsForm.signatureOption[2].checked =false; 
  }
}

function signatureOption4()
{
  if(document.aascPackageOptionsForm.signatureOptionCheck.checked == true)
  {
    if(document.aascPackageOptionsForm.signatureOption[3].checked ==true)
    {
    document.aascPackageOptionsForm.signatureOption[0].value="NONE";
    document.aascPackageOptionsForm.signatureOption[1].value="NONE";
    document.aascPackageOptionsForm.signatureOption[2].value="NONE";
    document.aascPackageOptionsForm.signatureOption[3].value="INDIRECT";
    }
    else
    {
    document.aascPackageOptionsForm.signatureOption[3].value="NONE";
    }
  }
  else
  document.aascPackageOptionsForm.signatureOption[3].value="NONE";
}


function saveDetails()
{
 var flagShipStr = window.opener.document.getElementById('flagShip1').value;
 if(flagShipStr=='Y'){//Sunanda added the following code for bug fix #2747
 
 return false;
 
 }
 
 //Sunanda added the below code for bug fix #2894
    
    if(window.opener.document.getElementById('flagShip1').value == 'Y'){
//    alert('at 805');
         var button = '<a href="#" onclick="return saveDetails();" ><img src="buttons/aascSave1.png" name="save" id="saveId1" alt ="" border="0" /></a>'+ '&nbsp;' + '<a href="#" onclick="loadPackageOptions()" ><img src="buttons/aascCancel1.png" name="cancel" id="cancelId1" alt="" /></a>'+ '&nbsp;' + '<a href="#" onclick="javascript:window.close()" ><img src="buttons/aascClose1.png" name="close" id="closeId1" border="0" alt="" /></a>';
        document.getElementById("buttonDiv").innerHTML = button;
        document.getElementById("buttonDiv2").innerHTML = button;
        
    }
    
 
 
var signatureOptionCheck;

var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value
//30/07/07
var strRtn=document.aascPackageOptionsForm.rtnShipMethod.value;
var rtnPaymethod=document.aascPackageOptionsForm.rtnPayMethod.value;
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
 packageCount=document.aascPackageOptionsForm.packageCount.value;
 //returnshipval=document.aascPackageOptionsForm.returnShipment.value; //30/07/07
 
 
 
 
 
 
 if(document.aascPackageOptionsForm.returnShipment.value =="PRINTRETURNLABEL"   && document.aascPackageOptionsForm.chCOD.value=="Y" )
     {
     alert("COD and Return shipment both can not be allowed at a time");
     return false;
     }


if(document.aascPackageOptionsForm.returnShipment.checked == false)

  {

     document.aascPackageOptionsForm.returnShipment.value="NONRETURN";

  }
  

 if(document.aascPackageOptionsForm.returnShipment.checked == true  && toDayDate != myDate && strRtn.substring(0,3)=="110")
 {
 alert('Return Shipment Feature Is Not Available With Future Day');
  return false;
 }
 
 //Added by gayaz
 
  if(strRtn.substring(0,3)=="111" && document.aascPackageOptionsForm.returnShipment.value =="PRINTRETURNLABEL"   && document.aascPackageOptionsForm.rtnDeclaredValue.value>100 )
     {
     alert("Package Declared value Should not be greater than 100 for This package");
     return false;
     }

 
 
 
 
 
 
    var shipMethod1=window.opener.document.getElementById('ajaxcarrierservicelevel').value;
     var shipMethodRtn=document.aascPackageOptionsForm.ajaxcarrierservicelevel.value;
       if (shipMethodRtn=="FEDEXEXPRESSSAVER" && document.aascPackageOptionsForm.returnShipment.checked == true)
     {
      alert('ReturnShipment is not allowed for the Ship Method FEDEXEXPRESSSAVER');
      return false;
     }  //30/07/07
     
     if((str.substring(0,3)=="110" || str.substring(0,3)=="111") &&  (payMethod1=="CG" || payMethod1=="TP") && document.aascPackageOptionsForm.chCOD.checked == true)
    {
    alert('COD is not allowed for FedEx THIRD PARTY BILLING and CONSIGNEE');
    return false;
    }
           
    if((strRtn.substring(0,3)=="110" || strRtn.substring(0,3)=="111") &&  rtnPaymethod=="CG" && document.aascPackageOptionsForm.returnShipment.checked == true)
    {
    alert('Return Shipment Feature Is Not Available With CONSIGNEE Paymethod ');
    return false;
    }
        
if(document.aascPackageOptionsForm.signatureOptionCheck.checked == false)
  {
     document.aascPackageOptionsForm.signatureOptionCheck.value="NONE";

         signatureOptionCheck=document.aascPackageOptionsForm.signatureOptionCheck.value;
      //   alert("signatureOptionCheck :"+abd);
         
  }
  else

  {
            if(document.aascPackageOptionsForm.signatureOption[0].checked == false &&

                   document.aascPackageOptionsForm.signatureOption[1].checked == false && document.aascPackageOptionsForm.signatureOption[2].checked == false && document.aascPackageOptionsForm.signatureOption[3].checked == false)

           {
                  alert('Please Check Signature Option Value');

                    return false;

           }

           else

             {
                 if(document.aascPackageOptionsForm.signatureOption[0].checked == true)

   {
                      signatureOptionCheck=document.aascPackageOptionsForm.signatureOption[0].value;
                     }
                 if(document.aascPackageOptionsForm.signatureOption[1].checked == true)
                    {
                      signatureOptionCheck=document.aascPackageOptionsForm.signatureOption[1].value;
                    }
                if(document.aascPackageOptionsForm.signatureOption[2].checked == true)
                {
                  signatureOptionCheck=document.aascPackageOptionsForm.signatureOption[2].value;
                }
                
                if(document.aascPackageOptionsForm.signatureOption[3].checked == true)
                {
                  signatureOptionCheck=document.aascPackageOptionsForm.signatureOption[3].value;
                }

            }
 }
       var codCheck=document.aascPackageOptionsForm.chCOD.checked;
      var codAmount=document.aascPackageOptionsForm.codAmt.value;
 
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
      document.aascPackageOptionsForm.chCOD.checked=false;
      document.aascPackageOptionsForm.chCOD.value="N";
      document.aascPackageOptionsForm.codAmt.value=0;
      return false;
      }
      }
     // if(document.aascPackageOptionsForm.returnShipment.value=="PRINTRETURNLABEL")
      if(document.aascPackageOptionsForm.returnShipment.value=="PRINTRETURNLABEL" && document.aascPackageOptionsForm.returnShipment.disabled==false)
      {
      if (checkShipToAddress()==false)

   {
   		return false;

		}
    
    
    
    
    var rtnACNumber=document.aascPackageOptionsForm.rtnACNumber.value;
    if(rtnACNumber=="" || rtnACNumber=="null" || rtnACNumber==null)
    {
    alert('Please Enter A/C Number');
    document.aascPackageOptionsForm.rtnACNumber.focus();
    return false;
    }
    //31/07/07(start)
    var rtnDeclaredValue= document.aascPackageOptionsForm.rtnDeclaredValue.value;
     if(isNaN(rtnDeclaredValue) || rtnDeclaredValue<0)
     {
     alert("Please enter the Return DeclaredValue Correctly");
     document.aascPackageOptionsForm.rtnDeclaredValue.focus();
     return false;
 
     }
       if(rtnDeclaredValue=="" || rtnDeclaredValue=='' )
       {
           document.aascPackageOptionsForm.rtnDeclaredValue.value=0;
       }
        var rtnDeclaredValueChk=document.aascPackageOptionsForm.rtnDeclaredValue.value;
        var position=parseInt(rtnDeclaredValueChk.indexOf("."));
 
        var decLen=parseInt(rtnDeclaredValueChk.length);
      
     
        var subdeclare=rtnDeclaredValueChk.substring(position+1,decLen);
     
      
        var subdeclarelen=parseInt(subdeclare.length);
      if(parseFloat(document.aascPackageOptionsForm.rtnDeclaredValue.value) > 9999999.99)
         {
       
           alert('Return Declared Value Should be Less Than or Equal to 9999999.99');
           document.aascPackageOptionsForm.rtnDeclaredValue.focus();
           return false;
        }
    if(subdeclarelen >2 && position >0)
       {
         alert('Please Give  Precision of two digits for The Return Declare value' );
         document.aascPackageOptionsForm.rtnDeclaredValue.focus();
         return false;
       }
    //31/07/07(end)
      }
      else
      {
      document.aascPackageOptionsForm.rtnDeclaredValue.value=0.0;
      document.aascPackageOptionsForm.rtnRMA.value="";
      }
          if(codCheck ==true)
        {
            window.opener.document.getElementById('chCODID'+packageCount).value="Y";
        }
        else{ 
          window.opener.document.getElementById('chCODID'+packageCount).value="N";
            }
            
    // Added by Sambit on 06/12/2007
    
   if(document.aascPackageOptionsForm.holdAtLocation.checked == true)
    {
      var halPhoneStr = document.aascPackageOptionsForm.halPhone.value;
    // Commented by Suman Gunda for bug #2079
//     if(((halPhoneStr.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (halPhoneStr.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) || halPhoneStr.length < 10) 
//				{
//
//					alert("Please Enter Valid 10 Digit Hold At Location Phone Number :");
//
//					document.aascPackageOptionsForm.halPhone.focus();
//
//					return false;
//
//				}
     if (document.aascPackageOptionsForm.halPhone.value == '')
      {
         alert('Please Enter Hold At Location Phone Number');
         document.aascPackageOptionsForm.halPhone.focus();
         return false;
      }
    if (document.aascPackageOptionsForm.halAddrLine1.value == '')
      {
         alert('Please Enter Hold At Location Line1 value');
         document.aascPackageOptionsForm.halAddrLine1.focus();
         return false;
      }
    if (document.aascPackageOptionsForm.halCity.value == '')
      {
         alert('Please Enter Hold At Location City');
         document.aascPackageOptionsForm.halCity.focus();
         return false;
      }
    if (document.aascPackageOptionsForm.halState.value == '')
      {
         alert('Please Enter Hold At Location State');
         document.aascPackageOptionsForm.halState.focus();
         return false;
      }
    if (document.aascPackageOptionsForm.halZip.value == '')
      {
         alert('Please Enter Hold At Location Postal Code');
         document.aascPackageOptionsForm.halZip.focus();
         return false;
      }
      
    }
    
    // End of 06/12/2007
    
         // Added by Sambit on 15/07/2008
    if(document.aascPackageOptionsForm.HazardousMaterial.checked == true)
    {
       if (document.aascPackageOptionsForm.HazardousMaterialType.value == '')
      {
         alert('Please Select Hazardous Material Type ');
         document.aascPackageOptionsForm.HazardousMaterialType.focus();
         return false;
      }
      
      //validations for the Haz material Quantity and Unit  Added on 22 jan 09
     if(str.substring(0,3)=="111")
     {
          if (document.aascPackageOptionsForm.HazardousMaterialQuantity.value == '')
          {
             alert('Please Enter Weight');
             document.aascPackageOptionsForm.HazardousMaterialQuantity.focus();
             return false;
          }
          
          if (document.aascPackageOptionsForm.HazardousMaterialUnit.value == '')
          {
             alert('Please Select Unit ');
             document.aascPackageOptionsForm.HazardousMaterialUnit.focus();
             return false;
          }
          
          //Added on Jun-03-2011      
         var hazmatPkgCnt =  document.aascPackageOptionsForm.HazMatPackagingCnt.value.replace(/^\s+|\s+$/g,"");
         var hazmatPkgUnits = document.aascPackageOptionsForm.HazMatPackagingUnits.value.replace(/^\s+|\s+$/g,"");
         var fdxCarrierMode = document.aascPackageOptionsForm.fdexCarrierMode.value;
         var hazmatTechnicalName = document.aascPackageOptionsForm.HazMatTechnicalName.value.replace(/^\s+|\s+$/g,"");
         var hazmatSignatureName = document.aascPackageOptionsForm.HazMatSignatureName.value.replace(/^\s+|\s+$/g,"");
         
         
       if(document.aascPackageOptionsForm.HazardousMaterial.checked == true && (fdxCarrierMode=="FedexWebServices" || fdxCarrierMode=="WEBSERVICES"))
        { 
         if(hazmatPkgCnt == null || hazmatPkgCnt =='')
         {
           alert("Please enter Hazmat Packaging Count");
           document.aascPackageOptionsForm.HazMatPackagingCnt.value = document.aascPackageOptionsForm.HazMatPackagingCnt.value.replace(/^\s+|\s+$/g,"");
           document.aascPackageOptionsForm.HazMatPackagingCnt.focus();
           return false;
         }
         
        var r =/^[0-9.]*$/;
        if(!r.test(document.aascPackageOptionsForm.HazMatPackagingCnt.value))
        {
        document.aascPackageOptionsForm.HazMatPackagingCnt.value = document.aascPackageOptionsForm.HazMatPackagingCnt.value.replace(/^\s+|\s+$/g,"");
        alert("Enter only numbers in Hazmat Packaging Count");
        document.aascPackageOptionsForm.HazMatPackagingCnt.focus();
        return false;
        }
         
         if(hazmatPkgUnits == null || hazmatPkgUnits =='')
         {
           alert("Please enter Hazmat Packaging Units");
           document.aascPackageOptionsForm.HazMatPackagingUnits.value = document.aascPackageOptionsForm.HazMatPackagingUnits.value.replace(/^\s+|\s+$/g,"");
           document.aascPackageOptionsForm.HazMatPackagingUnits.focus();
           return false;
         }
         
             //Added on Jul-05-2011
              if(hazmatTechnicalName == null || hazmatTechnicalName =='')
             {
               alert("Please enter Technical Name");
               document.aascPackageOptionsForm.HazMatTechnicalName.value = document.aascPackageOptionsForm.HazMatTechnicalName.value.replace(/^\s+|\s+$/g,"");
               document.aascPackageOptionsForm.HazMatTechnicalName.focus();
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
     var pkgDryIceUnits=aascPackageOptionsForm.dryIceUnits.options[document.aascPackageOptionsForm.dryIceUnits.selectedIndex].value;
     var pkgDryIceWeight=parseFloat(document.aascPackageOptionsForm.dryIceWeight.value);
     var chk=0;

     if(pkgUom==pkgDryIceUnits)
     {
     
     if(pkgDryIceWeight>=pkgWeight)
     {
     alert("Please Enter DryIce Weight Less than Package Weight");
     document.aascPackageOptionsForm.dryIceWeight.focus();
     chk=1;
     return false;
     }
     else if(pkgDryIceWeight>440 && pkgDryIceUnits=="LBS")
     {
     alert("Please Enter DryIce Weight Less than 440 LBS");
      document.aascPackageOptionsForm.dryIceWeight.focus();
      chk=1;
     return false;
     }
      else if(pkgDryIceWeight>200 && pkgDryIceUnits=="KGS")
     {
     alert("Please Enter DryIce Weight Less than 200 KGS");
      document.aascPackageOptionsForm.dryIceWeight.focus();
      chk=1;
     return false;
     }
     
     }
     else if(pkgDryIceWeight>440 && pkgDryIceUnits=="LBS")
     {
     alert("Please Enter DryIce Weight Less than 440 LBS");
      document.aascPackageOptionsForm.dryIceWeight.focus();
      chk=1;
     return false;
     }
      else if(pkgDryIceWeight>200 && pkgDryIceUnits=="KGS")
     {
     alert("Please Enter DryIce Weight Less than 200 KGS");
      document.aascPackageOptionsForm.dryIceWeight.focus();
      chk=1;
     return false;
     }
     
     
     if(chk==0)
     {
     if(document.aascPackageOptionsForm.chDryIce.checked)
     {
     window.opener.document.getElementById('chDryIceID'+packageCount).value ="Y" ;
     }
     else
     {
     window.opener.document.getElementById('chDryIceID'+packageCount).value ="N" ;
     }
     //alert('pkgDryIceWeight::'+pkgDryIceWeight);
     if(document.aascPackageOptionsForm.chDryIce.checked && (pkgDryIceWeight==""))
     {
     alert("Please Enter DryIce Weight");
      document.aascPackageOptionsForm.dryIceWeight.focus();
     return false;
     }
     
     if(isNaN(pkgDryIceWeight) && document.aascPackageOptionsForm.chDryIce.checked)
         {
       alert("The DryIce Weight should be a numeric value");
       document.aascPackageOptionsForm.dryIceWeight.focus();
       return false;
          }
          /*alert(document.aascPackageOptionsForm.chDryIce.checked);
          alert('pkgDryIceUnits::'+pkgDryIceUnits);
          alert('pkgDryIceWeight::'+pkgDryIceWeight);*/
          
     window.opener.document.getElementById('dryIceUnitsID'+packageCount).value =pkgDryIceUnits;// document.aascPackageOptionsForm.dryIceUnits.value;
     window.opener.document.getElementById('dryIceWeightID'+packageCount).value =pkgDryIceWeight; 
     }
     
    
    //===========================================================================
    
     window.opener.document.getElementById('codAmtID'+packageCount).value=document.aascPackageOptionsForm.codAmt.value;
     window.opener.document.getElementById('returnShipmentID'+packageCount).value=document.aascPackageOptionsForm.returnShipment.value;
     window.opener.document.getElementById('PackageSurchargeID'+packageCount).value=document.aascPackageOptionsForm.PackageSurcharge.value;  
     window.opener.document.getElementById('PackageShipmentCostID'+packageCount).value=document.aascPackageOptionsForm.PackageShipmentCost.value;  
     //alert("signature option::"+signatureOptionCheck);
     window.opener.document.getElementById('signatureOptionID'+packageCount).value=signatureOptionCheck;  
     
     //19/07/07(start)
     
     window.opener.document.getElementById('rtnShipFromCompanyID'+packageCount).value=document.aascPackageOptionsForm.rtnShipFromCompany.value;  
     window.opener.document.getElementById('rtnShipToCompanyID'+packageCount).value=document.aascPackageOptionsForm.rtnShipToCompany.value;  
     window.opener.document.getElementById('rtnShipFromContactID'+packageCount).value=document.aascPackageOptionsForm.rtnShipFromContact.value;  
     window.opener.document.getElementById('rtnShipToContactID'+packageCount).value=document.aascPackageOptionsForm.rtnShipToContact.value;  
     window.opener.document.getElementById('rtnShipFromLine1ID'+packageCount).value=document.aascPackageOptionsForm.rtnShipFromLine1.value;  
     window.opener.document.getElementById('rtnShipToLine1ID'+packageCount).value=document.aascPackageOptionsForm.rtnShipToLine1.value;  
     window.opener.document.getElementById('rtnShipFromLine2ID'+packageCount).value=document.aascPackageOptionsForm.rtnShipFromLine2.value;  
     window.opener.document.getElementById('rtnShipToLine2ID'+packageCount).value=document.aascPackageOptionsForm.rtnShipToLine2.value;  
     window.opener.document.getElementById('rtnShipFromCityID'+packageCount).value=document.aascPackageOptionsForm.rtnShipFromCity.value;  
     window.opener.document.getElementById('rtnShipFromSateID'+packageCount).value=document.aascPackageOptionsForm.rtnShipFromSate.value;  
     window.opener.document.getElementById('rtnShipFromZipID'+packageCount).value=document.aascPackageOptionsForm.rtnShipFromZip.value;  
     window.opener.document.getElementById('rtnShipToCityID'+packageCount).value=document.aascPackageOptionsForm.rtnShipToCity.value;  
     window.opener.document.getElementById('rtnShipToStateID'+packageCount).value=document.aascPackageOptionsForm.rtnShipToState.value;  
     window.opener.document.getElementById('rtnShipToZipID'+packageCount).value=document.aascPackageOptionsForm.rtnShipToZip.value;  
     window.opener.document.getElementById('rtnShipFromPhoneID'+packageCount).value=document.aascPackageOptionsForm.rtnShipFromPhone.value;  
     window.opener.document.getElementById('rtnShipToPhoneID'+packageCount).value=document.aascPackageOptionsForm.rtnShipToPhone.value;  
     window.opener.document.getElementById('rtnShipMethodID'+packageCount).value=document.aascPackageOptionsForm.ajaxShipMethod.value;  
     window.opener.document.getElementById('rtnDropOfTypeID'+packageCount).value=document.aascPackageOptionsForm.rtnDropOfType.value; 
     window.opener.document.getElementById('rtnPackageListID'+packageCount).value=document.aascPackageOptionsForm.rtnPackageList.value;  
     window.opener.document.getElementById('rtnPayMethodID'+packageCount).value=document.aascPackageOptionsForm.rtnPayMethod.options[document.aascPackageOptionsForm.rtnPayMethod.selectedIndex].text;
     window.opener.document.getElementById('rtnPayMethodCodeID'+packageCount).value=document.aascPackageOptionsForm.rtnPayMethod.options.value;
        
     window.opener.document.getElementById('rtnACNumberID'+packageCount).value=document.aascPackageOptionsForm.rtnACNumber.value;  
     window.opener.document.getElementById('rtnRMAID'+packageCount).value=document.aascPackageOptionsForm.rtnRMA.value;  
     
     window.opener.document.getElementById('packageSaveCheckID'+packageCount).value="Y";  
     window.opener.document.getElementById('rtnShipMethodNameID'+packageCount).value=document.aascPackageOptionsForm.rtnShipMethod.value;
     //30/07/07
     window.opener.document.getElementById('rtnDeclaredValueID'+packageCount).value=document.aascPackageOptionsForm.rtnDeclaredValue.value;
     //30/07/07
     
    //19/07/07(end)
  //   alert("rtnPayMethod :"+document.aascPackageOptionsForm.rtnPayMethod.options[document.aascPackageOptionsForm.rtnPayMethod.selectedIndex].text);
     
     // Added by Sambit on 15/11/07
     
     window.opener.document.getElementById('halPhoneID'+packageCount).value = document.aascPackageOptionsForm.halPhone.value;
     window.opener.document.getElementById('halAddrLine1ID'+packageCount).value = document.aascPackageOptionsForm.halAddrLine1.value;
     window.opener.document.getElementById('halAddrLine2ID'+packageCount).value = document.aascPackageOptionsForm.halAddrLine2.value;
     window.opener.document.getElementById('halCityID'+packageCount).value = document.aascPackageOptionsForm.halCity.value;
     window.opener.document.getElementById('halStateID'+packageCount).value = document.aascPackageOptionsForm.halState.value;
     window.opener.document.getElementById('halZipID'+packageCount).value = document.aascPackageOptionsForm.halZip.value;
     window.opener.document.getElementById('holdAtLocationID'+packageCount).value = document.aascPackageOptionsForm.holdAtLocation.value;
     
          // Added By Sambit on 11/07/08
     if(document.aascPackageOptionsForm.HazardousMaterial.checked)
    {
			document.aascPackageOptionsForm.HazardousMaterial.value="Y";
    }else{
      document.aascPackageOptionsForm.HazardousMaterial.value="N";
    }
     
     window.opener.document.getElementById('HazMatFlagID'+packageCount).value = document.aascPackageOptionsForm.HazardousMaterial.value;
     window.opener.document.getElementById('HazMatTypeID'+packageCount).value = document.aascPackageOptionsForm.HazardousMaterialType.value;
     window.opener.document.getElementById('HazMatClassID'+packageCount).value = document.aascPackageOptionsForm.HazardousMaterialClass.value;
     //alert(window.opener.DynaAdhocShipSaveForm['HazMatFlag'+packageCount].value);
   //window.opener.DynaAdhocShipSaveForm['HazMatCharges'+packageCount].value = document.aascPackageOptionsForm.HazardousMaterialCharges.value;
       
   //window.opener.DynaAdhocShipSaveForm['HazMatFlag'+packageCount].value=packageCount;
   //end of 11/07/08

     //pavan
     //alert(document.aascPackageOptionsForm.HazardousMaterialQuantity.value);
     window.opener.document.getElementById('HazMatQtyID'+packageCount).value = document.aascPackageOptionsForm.HazardousMaterialQuantity.value;
     window.opener.document.getElementById('HazMatUnitID'+packageCount).value = document.aascPackageOptionsForm.HazardousMaterialUnit.value;
     //alert("save::::"+document.aascPackageOptionsForm.HazMatIdentificationNo.value);
     //alert("save pkg count::::"+ packageCount);
    
      window.opener.document.getElementById('HazMatIdNoID'+packageCount).value = document.aascPackageOptionsForm.HazMatIdentificationNo.value;
       //alert("save 1122::::"+window.opener.DynaAdhocShipSaveForm['HazMatIdNo'+packageCount].value);
    window.opener.document.getElementById('HazMatPkgGroupID'+packageCount).value = document.aascPackageOptionsForm.HazardousMaterialPkgGroup.value;
    window.opener.document.getElementById('HazMatDOTLabelID'+packageCount).value = document.aascPackageOptionsForm.HazMatDOTLabelType.value;
    window.opener.document.getElementById('HazMatEmerContactNoID'+packageCount).value = document.aascPackageOptionsForm.HazMatEmergencyContactNo.value;
    window.opener.document.getElementById('HazMatEmerContactNameID'+packageCount).value = document.aascPackageOptionsForm.HazMatEmergencyContactName.value;
    window.opener.document.getElementById('HazMatIdID'+packageCount).value = document.aascPackageOptionsForm.HazardousMaterialId.value;
     
     
     // Added on Jun-03-2011
      
    window.opener.document.getElementById('HazMatPackagingCntID'+packageCount).value = document.aascPackageOptionsForm.HazMatPackagingCnt.value;
    window.opener.document.getElementById('HazMatPackagingUnitsID'+packageCount).value = document.aascPackageOptionsForm.HazMatPackagingUnits.value;
    
    // End on Jun-03-2011
    //Added on Jul-05-2011
        window.opener.document.getElementById('HazMatTechnicalNameID'+packageCount).value = document.aascPackageOptionsForm.HazMatTechnicalName.value;
    //End on Jul-05-2011
        window.opener.document.getElementById('HazMatSignatureNameID'+packageCount).value = document.aascPackageOptionsForm.HazMatSignatureName.value;
        //Added code to check Signature Options and Direct option when Hazardous Material is checked. Bug #3965.
        if(document.aascPackageOptionsForm.HazardousMaterial.checked== true)
        {
            if((document.aascPackageOptionsForm.signatureOptionCheck.value=="NONE") || ((document.aascPackageOptionsForm.signatureOption[0].checked!=true) && (document.aascPackageOptionsForm.signatureOption[1].checked!=true)))
            {
                //5_7_6 - Hologic Fix - Stop alert for selecting direct signature option, if hazmat option selected
                //        alert("'Direct' Signature Option will be selected for hazmat shipments");
                document.aascPackageOptionsForm.signatureOption[0].checked = true;
                document.aascPackageOptionsForm.signatureOptionCheck.checked = true;
                document.aascPackageOptionsForm.signatureOption[0].value="DIRECT";
                document.aascPackageOptionsForm.signatureOption[0].disabled = false;
                document.aascPackageOptionsForm.signatureOption[1].disabled = false;
                document.aascPackageOptionsForm.signatureOption[2].disabled = false;
                document.aascPackageOptionsForm.signatureOption[3].disabled = false; 
                //return false;
                window.opener.document.getElementById('signatureOptionID'+packageCount).value = document.aascPackageOptionsForm.signatureOption[0].value;   
            }
        }
        
     window.close();
 }





//Added byu gayaz 
function codValidate()
{
 var packCount=document.aascPackageOptionsForm.packageCount.value;

var codAmount=document.aascPackageOptionsForm.codAmt.value;
 if(isNaN(codAmount))
         {
       alert("The COD Amount should be a numeric value");
       document.aascPackageOptionsForm.chCOD.focus();
          }

}




	function codCheck(){

		var idval= parseInt(document.aascPackageOptionsForm.packageCount.value);

//		document.aascPackageOptionsForm.chCOD.value="N"; 

		document.aascPackageOptionsForm.codAmt.readOnly=true;

		document.aascPackageOptionsForm.codAmt.value="";

		if(document.aascPackageOptionsForm.chCOD.checked){

			document.aascPackageOptionsForm.chCOD.value="Y";

			document.aascPackageOptionsForm.codAmt.readOnly=false;
      document.aascPackageOptionsForm.codAmt.disabled=false;
		   }	
       else
       {
       document.aascPackageOptionsForm.chCOD.value="N";
       }

    }

 //18/07/07(start)
 function rmaCheck()
 {
   if(document.aascPackageOptionsForm.returnShipment.value=="PRINTRETURNLABEL" && document.aascPackageOptionsForm.returnShipment.disabled==false)
 {
 document.aascPackageOptionsForm.rtnShipFromCompany.disabled=false;
 document.aascPackageOptionsForm.rtnShipToCompany.disabled=false;
 document.aascPackageOptionsForm.rtnShipFromContact.disabled=false;
 document.aascPackageOptionsForm.rtnShipToContact.disabled=false;
 document.aascPackageOptionsForm.rtnShipFromLine1.disabled=false;
 document.aascPackageOptionsForm.rtnShipToLine1.disabled=false;
 document.aascPackageOptionsForm.rtnShipFromLine2.disabled=false;
 document.aascPackageOptionsForm.rtnShipToLine2.disabled=false;
 document.aascPackageOptionsForm.rtnShipFromCity.disabled=false;
 document.aascPackageOptionsForm.rtnShipFromSate.disabled=false;
 document.aascPackageOptionsForm.rtnShipFromZip.disabled=false;
 document.aascPackageOptionsForm.rtnShipToCity.disabled=false;
 document.aascPackageOptionsForm.rtnShipToState.disabled=false;
 document.aascPackageOptionsForm.rtnShipToZip.disabled=false;
 document.aascPackageOptionsForm.rtnShipFromPhone.disabled=false;
 document.aascPackageOptionsForm.rtnShipToPhone.disabled=false;
 document.aascPackageOptionsForm.rtnShipMethod.disabled=false;
 document.aascPackageOptionsForm.rtnDropOfType.disabled=false;
 document.aascPackageOptionsForm.rtnPackageList.disabled=false;
 document.aascPackageOptionsForm.rtnPayMethod.disabled=false;
 document.aascPackageOptionsForm.rtnACNumber.disabled=false;
 document.aascPackageOptionsForm.rtnRMA.disabled=false;
 document.aascPackageOptionsForm.rtnTrackingNumber.disabled=false;
 document.aascPackageOptionsForm.rtnDeclaredValue.disabled=false;
   }
 else
 {
	 document.aascPackageOptionsForm.rtnShipFromCompany.disabled=true;
 document.aascPackageOptionsForm.rtnShipToCompany.disabled=true;
 document.aascPackageOptionsForm.rtnShipFromContact.disabled=true;
 document.aascPackageOptionsForm.rtnShipToContact.disabled=true;
 document.aascPackageOptionsForm.rtnShipFromLine1.disabled=true;
 document.aascPackageOptionsForm.rtnShipToLine1.disabled=true;
 document.aascPackageOptionsForm.rtnShipFromLine2.disabled=true;
 document.aascPackageOptionsForm.rtnShipToLine2.disabled=true;
 document.aascPackageOptionsForm.rtnShipFromCity.disabled=true;
 document.aascPackageOptionsForm.rtnShipFromSate.disabled=true;
 document.aascPackageOptionsForm.rtnShipFromZip.disabled=true;
 document.aascPackageOptionsForm.rtnShipToCity.disabled=true;
 document.aascPackageOptionsForm.rtnShipToState.disabled=true;
 document.aascPackageOptionsForm.rtnShipToZip.disabled=true;
 document.aascPackageOptionsForm.rtnShipFromPhone.disabled=true;
 document.aascPackageOptionsForm.rtnShipToPhone.disabled=true;
 document.aascPackageOptionsForm.rtnShipMethod.disabled=true;
 document.aascPackageOptionsForm.rtnDropOfType.disabled=true;
 document.aascPackageOptionsForm.rtnPackageList.disabled=true;
 document.aascPackageOptionsForm.rtnPayMethod.disabled=true;
 document.aascPackageOptionsForm.rtnACNumber.disabled=true;
 document.aascPackageOptionsForm.rtnRMA.disabled=true;
 document.aascPackageOptionsForm.rtnTrackingNumber.disabled=true;
 document.aascPackageOptionsForm.rtnDeclaredValue.disabled=true;
  }
 }
  //18/07/07(end)
 
 
/************************************ 19-JULY-2007  *************************************/





/*************************************************************************************/
function getCcCsl(testForErrorCase)
  {  
//  alert("testForErrorCase  "+testForErrorCase);
  
  var packCount1=document.aascPackageOptionsForm.packageCount.value;
   var shipStatusFlag1=window.opener.document.getElementById('shipmentStatusFlagID').value;
     //var ShipConfirmedFlag1=window.opener.DynaAdhocShipSaveForm.ShipConfirmed.value;
  if(  shipStatusFlag1!="B" && shipStatusFlag1 !="P")
  {
  var localShipmethod=window.opener.document.getElementById('rtnShipMethodNameID'+packCount1).value;
  var localShipmethod1=window.opener.document.getElementById('rtnShipMethodID'+packCount1).value;
  }
  else
  {
  
 var localShipmethod= document.aascPackageOptionsForm.ajaxcarrierservicelevel.value;
 var localShipmethod1= document.aascPackageOptionsForm.ajaxcarrierservicelevel.value
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
    //  alert("On Load :"+document.aascPackageOptionsForm.ajaxAfterShipCarrPayMthdValue.value);
  /*  localDropOfType           =   document.aascPackageOptionsForm.rtnDropOfType.value;
    localPackageList          =   document.aascPackageOptionsForm.rtnPackageList.value;
    localCarrierPayMethodText =   document.aascPackageOptionsForm.rtnPayMethod.value;
    localCarrierAccNumber     =   document.aascPackageOptionsForm.rtnACNumber.value;   */
    
    
      localDropOfType           =   document.aascPackageOptionsForm.ajaxAfterShipDropOffType.value;
      localPackageList          =   document.aascPackageOptionsForm.ajaxAfterShipPackaging.value;
      localCarrierPayMethodText =   document.aascPackageOptionsForm.ajaxAfterShipCarrPayMthdValue.value;
      localCarrierAccNumber     =   document.aascPackageOptionsForm.ajaxAfterShipCarrAccNumber.value;  
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
      localDropOfType           =   document.aascPackageOptionsForm.ajaxAfterShipDropOffType.value;
      localPackageList          =   document.aascPackageOptionsForm.ajaxAfterShipPackaging.value;
      localCarrierPayMethodText =   document.aascPackageOptionsForm.ajaxAfterShipCarrPayMthdValue.value;
      localCarrierAccNumber     =   document.aascPackageOptionsForm.ajaxAfterShipCarrAccNumber.value;
      
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

  var ajaxOriginRegionCode=document.aascPackageOptionsForm.ajaxOriginRegionCode.value;

  var ajaxshipMethodvalue=document.aascPackageOptionsForm.rtnShipMethod.value;
  
  var testForFedexUps=ajaxshipMethodvalue.substring(0,ajaxshipMethodvalue.indexOf("|"));
//  alert("testForFedexUps  "+testForFedexUps);
  //
    document.aascPackageOptionsForm.rtnDropOfType.options.length=0 // added for clearing all the option fields in select tag
    document.aascPackageOptionsForm.rtnPackageList.options.length=0 // added for clearing all the option fields in select tag
    document.aascPackageOptionsForm.rtnPayMethod.options.length=0 // added for clearing all the option fields in select tag
    document.aascPackageOptionsForm.rtnACNumber.value='';         //setting default
    document.aascPackageOptionsForm.ajaxCCodeCServiceLevel.value='';                      // setting default
    document.aascPackageOptionsForm.ajaxCarrierCode.value='';             // setting default
    document.aascPackageOptionsForm.ajaxcarrierservicelevel.value='';     // setting default
    document.aascPackageOptionsForm.ajaxDropOffType.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxPackaging.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxCarrierPaymentTerms.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxUpsServiceLevelCode.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxDimensionReq.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxWeight.value='';                         // setting default
    
    
	/*
	document.aascPackageOptionsForm.ajaxMaxLength.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinLength.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxWidth.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinWidth.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxHeight.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinHeight.value='';                         // setting default
    
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
    for(i=0;i<document.aascPackageOptionsForm.rtnShipMethod.length;i++)
    {
    var testShipmethod=document.aascPackageOptionsForm.rtnShipMethod.options[document.aascPackageOptionsForm.rtnShipMethod.selectedIndex=i].value;
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
   
    document.aascPackageOptionsForm.rtnShipMethod.selectedIndex=finalIndex;

//    alert("rtnShipmethod::::::::::::::"+document.aascPackageOptionsForm.rtnShipMethod.value);
    ajaxshipMethodParse = document.aascPackageOptionsForm.rtnShipMethod.value;
    ajaxshipMethodParse = ajaxshipMethodParse.substring(ajaxshipMethodParse.indexOf("*")+1,ajaxshipMethodParse.length);
  }
  else
  {
    ajaxshipMethodParse=ajaxshipMethodvalue.substring(ajaxshipMethodvalue.indexOf("*")+1,ajaxshipMethodvalue.length);
//  alert('ajaxshipMethodParse::'+ajaxshipMethodParse);
  }
  
  document.aascPackageOptionsForm.ajaxShipMethod.value=ajaxshipMethodParse;
  
  var locationIDTemp=document.aascPackageOptionsForm.locationID.value;
  //var orgIDTemp=document.aascPackageOptionsForm.orgID.value;
  
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
            document.aascPackageOptionsForm.ajaxCCodeCServiceLevel.value=xmlHttp.responseText;
 
            var responseString  = document.aascPackageOptionsForm.ajaxCCodeCServiceLevel.value;
            
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
                      document.aascPackageOptionsForm.ajaxCarrierCode.value=parse1;
//                      parse2=responseStringDummy.substring(subindex1+1,index2);
                    //  alert("Parse2"+parse2);
                      document.aascPackageOptionsForm.ajaxcarrierservicelevel.value=parse2;
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
              
              if(document.aascPackageOptionsForm.ajaxUpsMode.value=='UPS Direct'&&document.aascPackageOptionsForm.ajaxCarrierCode.value=='100')
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
    document.aascPackageOptionsForm.rtnDropOfType.options.length=0 // added for clearing all the fields
    document.aascPackageOptionsForm.rtnPackageList.options.length=0 // added for clearing all the fields
    document.aascPackageOptionsForm.rtnPayMethod.options.length=0 // added for clearing all the fields
    document.aascPackageOptionsForm.rtnACNumber.value='';         //setting default
    
    document.aascPackageOptionsForm.ajaxDimensionReq.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxWeight.value='';                         // setting default
    
    document.aascPackageOptionsForm.ajaxMaxLength.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinLength.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxWidth.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinWidth.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxHeight.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinHeight.value='';                         // setting default
    
    xmlHttp.onreadystatechange=function()
      {
      if(xmlHttp.readyState==4)
        {
            document.aascPackageOptionsForm.ajaxDropOffType.value=xmlHttp.responseText;
            //document.DynaAdhocShipSaveForm.dropOfType.options[0]=new Option("-Select-", "-Select-", true, false); //removed default select 
            var responseString  ="";
            
            responseString  = document.aascPackageOptionsForm.ajaxDropOffType.value;
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
                        document.aascPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, true);
                       // alert("localDropOfType "+localDropOfType);
                      }
                      else
                      {
                        document.aascPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, false);
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
                document.aascPackageOptionsForm.ajaxDropOffType.value='';
                document.aascPackageOptionsForm.rtnDropOfType.options.length=1;
                document.aascPackageOptionsForm.rtnDropOfType.options.value='';
                document.aascPackageOptionsForm.rtnDropOfType.disabled=true;
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
    var url="aascAjaxRetrieveDropOffType.jsp?ajaxCarrierCode="+document.aascPackageOptionsForm.ajaxCarrierCode.value+"&ajaxcarrierservicelevel="+document.aascPackageOptionsForm.ajaxcarrierservicelevel.value;
    xmlHttp.open("POST",url,false);
    xmlHttp.send(null);  
    }  //end of function
    
 

function getPackageList(localPackageList,localCarrierPayMethodText,localCarrierAccNumber)
  { 
  //alert(localPackageList);
 // alert(localCarrierPayMethodText);
//  alert("in fun3");
//  alert(document.aascPackageOptionsForm.ajaxShipMethod.value);
//  alert(document.aascPackageOptionsForm.ajaxCarrierCode.value);
//  alert(document.aascPackageOptionsForm.ajaxcarrierservicelevel.value);
//  alert(document.aascPackageOptionsForm.ajaxDropOffType.value);
//  alert(document.aascPackageOptionsForm.rtnDropOfType.value);
  
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
    
    
    document.aascPackageOptionsForm.rtnPackageList.options.length=0 // added for clearing all the fields
    document.aascPackageOptionsForm.rtnPayMethod.options.length=0 // added for clearing all the fields
    document.aascPackageOptionsForm.rtnACNumber.value='';         //setting default
    
    document.aascPackageOptionsForm.ajaxDimensionReq.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxWeight.value='';                         // setting default
    
    document.aascPackageOptionsForm.ajaxMaxLength.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinLength.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxWidth.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinWidth.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxHeight.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinHeight.value='';                         // setting default
    
    xmlHttp.onreadystatechange=function()
      {
      if(xmlHttp.readyState==4)
        {
            document.aascPackageOptionsForm.ajaxPackaging.value=xmlHttp.responseText;
 //document.aascPackageOptionsForm.rtnPackageList.options[0]=new Option("-Select-", "-Select-", true, false);//removed default select
            var responseString  = document.aascPackageOptionsForm.ajaxPackaging.value;
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
                        document.aascPackageOptionsForm.rtnPackageList.options[counter]=new Option(parsetest, parsetest, true, true);
                       // alert("localPackageList "+localPackageList+"   "+parsetest);
                      }
                      else
                      {
                        document.aascPackageOptionsForm.rtnPackageList.options[counter]=new Option(parsetest, parsetest, true, false);
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
                document.aascPackageOptionsForm.ajaxPackaging.value='';
                document.aascPackageOptionsForm.rtnPackageList.options.length=1;
                document.aascPackageOptionsForm.rtnPackageList.options.value='';
                document.aascPackageOptionsForm.rtnPackageList.disabled=true;
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
    var url="aascAjaxRetrievePackaging.jsp?ajaxCarrierCode="+document.aascPackageOptionsForm.ajaxCarrierCode.value+"&ajaxcarrierservicelevel="+document.aascPackageOptionsForm.ajaxcarrierservicelevel.value+"&dropOfType="+document.aascPackageOptionsForm.rtnDropOfType.value;
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
    
    
    document.aascPackageOptionsForm.rtnPayMethod.options.length=0 // added for clearing all the fields
    document.aascPackageOptionsForm.rtnACNumber.value='';         //setting default
    
    document.aascPackageOptionsForm.ajaxDimensionReq.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxWeight.value='';                         // setting default
    
    document.aascPackageOptionsForm.ajaxMaxLength.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinLength.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxWidth.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinWidth.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxHeight.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinHeight.value='';                         // setting default
    
    xmlHttp.onreadystatechange=function()
      {
      if(xmlHttp.readyState==4)
        {
            document.aascPackageOptionsForm.ajaxCarrierPaymentTerms.value=xmlHttp.responseText;
 //document.aascPackageOptionsForm.rtnPayMethod.options[0]=new Option("-Select-", "-Select-", true, false);//removed default select
            var responseString  = document.aascPackageOptionsForm.ajaxCarrierPaymentTerms.value;
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
                        document.aascPackageOptionsForm.rtnPayMethod.options[counter]=new Option(parsetest,parsetestvalue, true, true);
                        
                        var fillCarrierAccNumber=localCarrierAccNumber;
                            if(fillCarrierAccNumber!='')
                            {
                            //alert("not null as"+fillCarrierAccNumber);
                            document.aascPackageOptionsForm.rtnACNumber.value=fillCarrierAccNumber;
                            }
                            
                      }
                      else
                      { 
                          if((parsetestvalue=='PP') && localCarrierPayMethodText=='')
                          { 
                        //   alert("setting it to default");
                            document.aascPackageOptionsForm.rtnPayMethod.options[counter]=new Option(parsetest,parsetestvalue, true, true);
                          }
                          else
                          {
                       //  alert("other");
                          document.aascPackageOptionsForm.rtnPayMethod.options[counter]=new Option(parsetest,parsetestvalue, true, false);                      
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
    var url="aascAjaxRetrieveCarrierPayTerms.jsp?ajaxCarrierCode="+document.aascPackageOptionsForm.ajaxCarrierCode.value+"&ajaxcarrierservicelevel="+document.aascPackageOptionsForm.ajaxcarrierservicelevel.value+"&dropOfType="+document.aascPackageOptionsForm.rtnDropOfType.value+"&packageList="+document.aascPackageOptionsForm.rtnPackageList.value;
    xmlHttp.open("POST",url,false);
    xmlHttp.send(null);  
    }        //end of the method
    
    function getAccountNumber()
{
 // alert("got here");
  //
    document.aascPackageOptionsForm.ajaxDimensionReq.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxWeight.value='';                         // setting default
    
    document.aascPackageOptionsForm.ajaxMaxLength.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinLength.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxWidth.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinWidth.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMaxHeight.value='';                         // setting default
    document.aascPackageOptionsForm.ajaxMinHeight.value='';                         // setting default
  //
  
  var ajaxshipMethodvalue=document.aascPackageOptionsForm.rtnShipMethod.value;
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
  
      var CarrierPayMethodTextstr=document.aascPackageOptionsForm.rtnPayMethod.value;
      //alert(CarrierPayMethodTextstr);
      if(CarrierPayMethodTextstr=='PP')
      {
        var str1=document.aascPackageOptionsForm.rtnShipMethod.options[document.aascPackageOptionsForm.rtnShipMethod.selectedIndex].id;
      //  alert(str1);
        if(str1!='')
        {
          document.aascPackageOptionsForm.rtnACNumber.value=str1;
          document.aascPackageOptionsForm.rtnACNumber.readOnly=true;
        }
      }
     getCarrierCombValues();// getting caombination values
  }
}

function checkShipToAddress()
{ 
      var rtnShipToCompany=document.aascPackageOptionsForm.rtnShipToCompany.value;
       var rtnShipToLine1=document.aascPackageOptionsForm.rtnShipToLine1.value;
      var rtnShipToLine2=document.aascPackageOptionsForm.rtnShipToLine2.value;
      var rtnShipFromCompany=document.aascPackageOptionsForm.rtnShipFromCompany.value;
      var rtnShipFromLine1=document.aascPackageOptionsForm.rtnShipFromLine1.value;
      var rtnShipFromLine2=document.aascPackageOptionsForm.rtnShipFromLine2.value;
       //var txtShipToAddressLine3=trim(document.aascPackageOptionsForm.txtShipToAddressLine3.value);
      var rtnShipFromCity=document.aascPackageOptionsForm.rtnShipFromCity.value;
       var rtnShipToCity=document.aascPackageOptionsForm.rtnShipToCity.value;
       
       var rtnShipFromSate=document.aascPackageOptionsForm.rtnShipFromSate.value;
       var rtnShipToState=document.aascPackageOptionsForm.rtnShipToState.value;
       var rtnShipFromZip=document.aascPackageOptionsForm.rtnShipFromZip.value;
       var rtnShipToZip=document.aascPackageOptionsForm.rtnShipToZip.value;
           
      
      var alertStr="";
      var str=document.aascPackageOptionsForm.rtnShipMethod.options[document.aascPackageOptionsForm.rtnShipMethod.selectedIndex].value;
        var carrierCode=str.substring(0,3);
      //var upsMode=trim(document.aascPackageOptionsForm.upsMode.value);
           
      if((rtnShipFromCompany==null || rtnShipFromCompany==""))
      {
       alertStr="Ship From Customer Name should not be null. \n";
      //document.aascPackageOptionsForm.rtnShipFromCompany.focus();
      }
       if((rtnShipToCompany==null || rtnShipToCompany==""))
      {
      alertStr=alertStr+"Ship To Customer Name should not be null. \n";
      //document.aascPackageOptionsForm.shipToCompanyName.focus();
      }
      
      if( rtnShipToCompany.length >35)
      {
      alertStr=alertStr+"Length of Ship To Company Name is "+rtnShipToCompany.length +" Char. Please limit to 35 Char. \n";
     // document.aascPackageOptionsForm.rtnShipToCompany.focus();
      }

	  if( rtnShipFromCompany.length >35)
      {
//       alertStr=alertStr+"Length of Ship From Company Name is "+rtnShipFromCompany.length +" Char. Please limit to 35 Char. \n";
     // document.aascPackageOptionsForm.rtnShipFromCompany.focus();
      }

      if(rtnShipToLine1==null || rtnShipToLine1=="")
      {
         alertStr=alertStr+"Ship To AddressLine1 should not be null. \n";
      //document.aascPackageOptionsForm.rtnShipFromLine1.focus();
      }
        if(rtnShipFromLine1==null || rtnShipFromLine1=="")
      {
       alertStr=alertStr+"Ship From  AddressLine1 should not be null. \n";
     // document.aascPackageOptionsForm.rtnShipToLine1.focus();
      }

      if( (rtnShipToLine1.length >35 && carrierCode=="100") || (rtnShipToLine1.length >35 && carrierCode=="111") || (rtnShipToLine1.length >35 && carrierCode=="110"))
      {
      alertStr=alertStr+"Length of Ship To AddressLine1 is "+rtnShipToLine1.length+ " Char. Please limit to 35 Char. \n";
      //document.aascPackageOptionsForm.rtnShipToLine1.focus();
      }


     if( (rtnShipFromLine1.length >35 && carrierCode=="100") || (rtnShipFromLine1.length >35 && carrierCode=="111") || (rtnShipFromLine1.length >35 && carrierCode=="110"))
      {
//      alertStr=alertStr+"Length of Ship From AddressLine1 is "+rtnShipFromLine1.length+ " Char. Please limit From 35 Char. \n";
      //document.aascPackageOptionsForm.rtnShipFromLine1.focus();
      }
   
  


     if( (rtnShipToLine2.length >35 && carrierCode=="100") || (rtnShipToLine2.length >35 && carrierCode=="111") || (rtnShipToLine2.length >35 && carrierCode=="110"))
      {
      alertStr=alertStr+"Length of Ship To AddressLine2 is "+rtnShipToLine2.length+ " Char. Please limit to 35 Char. \n";
      //document.aascPackageOptionsForm.rtnShipToLine2.focus();
      }


     if( (rtnShipFromLine2.length >35 && carrierCode=="100") || (rtnShipFromLine2.length >35 && carrierCode=="111") || (rtnShipFromLine2.length >35 && carrierCode=="110"))
      {
      alertStr=alertStr+"Length of Ship From AddressLine2 is "+rtnShipFromLine2.length+ " Char. Please limit From 35 Char. \n";
     // document.aascPackageOptionsForm.rtnShipFromLine2.focus();
      }





      if(rtnShipToCity==null || rtnShipToCity=="")
      {
      alertStr=alertStr+"Ship To City should not be null. \n";
     // document.aascPackageOptionsForm.rtnShipToCity.focus();
      }
       if( rtnShipToCity.length >20 && carrierCode=="111")
      {
      alertStr=alertStr+" Length of Ship To City is "+rtnShipToCity.length+ " Char. Please limit to 20 Char. \n";
     // document.aascPackageOptionsForm.rtnShipToCity.focus();
      }
      if( rtnShipToCity.length >35 && carrierCode=="110")
      {
      alertStr=alertStr+"Length of Ship To City is "+rtnShipToCity.length+ " Char. Please limit to 35 Char. \n";
      //document.aascPackageOptionsForm.rtnShipToCity.focus();
      }


	  if(rtnShipFromCity==null || rtnShipFromCity=="")
      {
      alertStr=alertStr+"Ship From City should not be null. \n";
      //document.aascPackageOptionsForm.rtnShipFromCity.focus();
      }
       if( rtnShipFromCity.length >20 && carrierCode=="111")
      {
      alertStr=alertStr+" Length of Ship From City is "+rtnShipFromCity.length+ " Char. Please limit to 20 Char. \n";
      //document.aascPackageOptionsForm.rtnShipFromCity.focus();
      }
      if( rtnShipFromCity.length >35 && carrierCode=="110")
      {
      alertStr=alertStr+"Length of Ship From City is "+rtnShipFromCity.length+ " Char. Please limit to 35 Char. \n";
      //document.aascPackageOptionsForm.rtnShipToCity.focus();
      }
      
      
      if(rtnShipFromSate==null || rtnShipFromSate=="")
      {
      alertStr=alertStr+"Ship From State should not be null. \n";
      //document.aascPackageOptionsForm.rtnShipFromSate.focus();
      }


if(rtnShipToState==null || rtnShipToState=="")
      {
      alertStr=alertStr+"Ship To State should not be null. \n";
      //document.aascPackageOptionsForm.rtnShipToState.focus();
      }



if(rtnShipFromZip ==null || rtnShipFromZip =="")
      {
      alertStr=alertStr+"Ship From Zip Code should not be null. \n";
      //document.aascPackageOptionsForm.rtnShipFromZip.focus();
      }




if(rtnShipToZip ==null || rtnShipToZip =="")
      {
      alertStr=alertStr+"Ship To Zip Code should not be null. \n";
      //document.aascPackageOptionsForm.rtnShipToZip.focus();
      }
           
      if(alertStr != "" && alertStr !=null)
      {
      alert(alertStr);
      return false;
      }
    //end of if(carrierCode=="100" && upsMode=="UPS Direct")
    
    
  if(document.aascPackageOptionsForm.rtnShipFromPhone.value=="")
  {

				alert("Please Enter The Ship From Phone Number :");

				document.aascPackageOptionsForm.rtnShipFromPhone.readOnly=false;

				document.aascPackageOptionsForm.rtnShipFromPhone.focus();

				return false;

			}



	else{

	var rtnShipFromPhone=document.aascPackageOptionsForm.rtnShipFromPhone.value;
//Commented by Suman Gunda for removing validation for return shipment phone number
		//		if((rtnShipFromPhone.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (rtnShipFromPhone.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) 
//     if(((rtnShipFromPhone.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (rtnShipFromPhone.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) || rtnShipFromPhone.length < 10) 
//				
//        {
//
//					alert("Please Enter Valid 10 Digit Ship From Phone Number :");
//
//					document.aascPackageOptionsForm.rtnShipFromPhone.focus();
//
//					return false;
//
//				}

			}





if(document.aascPackageOptionsForm.rtnShipToPhone.value==""){

				alert("Please Enter The Ship To Phone Number :");

				document.aascPackageOptionsForm.rtnShipToPhone.readOnly=false;

				document.aascPackageOptionsForm.rtnShipToPhone.focus();

				return false;

			}



	else{

	var rtnShipToPhone=document.aascPackageOptionsForm.rtnShipToPhone.value;
// Commented by Suman Gunda for removing validation for return shipment phone number
			//	if((rtnShipToPhone.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (rtnShipToPhone.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) 
//      if(((rtnShipToPhone.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (rtnShipToPhone.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) || rtnShipToPhone.length <10 ) 
//				{
//
//					alert("Please Enter Valid 10 Digit Ship To Phone Number :");
//
//					document.aascPackageOptionsForm.rtnShipToPhone.focus();
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
  
  var sIndex=document.aascPackageOptionsForm.rtnPayMethod.selectedIndex;
  var carrierLocalText="";
  if(sIndex=="-1")
  {
  carrierLocalText="";
  }
  else
  {
  carrierLocalText=document.aascPackageOptionsForm.rtnPayMethod.options[sIndex].text;
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
            document.aascPackageOptionsForm.ajaxDimensionReq.value='';        
            }
            else
            {
            
            document.aascPackageOptionsForm.ajaxDimensionReq.value=dimReqd;         
            }
            //alert(dimReqd);
            //alert(responseStringDummy);
            
            
            startIndex  = responseStringDummy.indexOf('*');
            maxWeight     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(maxWeight=='null')
            {
            document.aascPackageOptionsForm.ajaxMaxWeight.value='';        
            }
            else
            {
            
            document.aascPackageOptionsForm.ajaxMaxWeight.value=maxWeight; 
            }
            //alert(maxWeight);
            //alert(responseStringDummy);
            
            
            startIndex  = responseStringDummy.indexOf('*');
            maxLength     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(maxLength=='null')
            {
            document.aascPackageOptionsForm.ajaxMaxLength.value='';       
            }
            else
            {
             
            document.aascPackageOptionsForm.ajaxMaxLength.value=maxLength;
            }
            //alert(maxLength);
            //alert(responseStringDummy);
            
            
            startIndex  = responseStringDummy.indexOf('*');
            minLength     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(minLength=='null')
            {
            document.aascPackageOptionsForm.ajaxMinLength.value='';       
            }
            else
            {
             
            document.aascPackageOptionsForm.ajaxMinLength.value=minLength;
            }
            //alert(minLength);
            //alert(responseStringDummy);
            
            
            startIndex  = responseStringDummy.indexOf('*');
            maxWidth     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(maxWidth=='null')
            {
            document.aascPackageOptionsForm.ajaxMaxWidth.value='';
            }
            else
            {
            
            document.aascPackageOptionsForm.ajaxMaxWidth.value=maxWidth;
            }
            //alert(maxWidth);
            //alert(responseStringDummy);
            
            
            startIndex  = responseStringDummy.indexOf('*');
            minWidth     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(minWidth=='null')
            {
            document.aascPackageOptionsForm.ajaxMinWidth.value='';
            }
            else
            {
            
            document.aascPackageOptionsForm.ajaxMinWidth.value=minWidth;
            }
            //alert(minWidth);
            //alert(responseStringDummy);
            
            
            startIndex  = responseStringDummy.indexOf('*');
            maxHeight     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            if(maxHeight=='null')
            {
            document.aascPackageOptionsForm.ajaxMaxHeight.value='';
            }
            else
            {
            
            document.aascPackageOptionsForm.ajaxMaxHeight.value=maxHeight;
            }
        
            
            startIndex  = responseStringDummy.indexOf('*');
            minHeight     = responseStringDummy.substring(0,startIndex);
            
            if(minHeight=='null')
            {
            document.aascPackageOptionsForm.ajaxMinHeight.value='';
            }
            else
            {
            
            document.aascPackageOptionsForm.ajaxMinHeight.value=minHeight;
            }
    
        }
      }
    var url="aascAjaxGetCarrierCombValues.jsp?ajaxCarrierCode="+document.aascPackageOptionsForm.ajaxCarrierCode.value+"&ajaxcarrierservicelevel="+document.aascPackageOptionsForm.ajaxcarrierservicelevel.value+"&dropOfType="+document.aascPackageOptionsForm.rtnDropOfType.value+"&packageList="+document.aascPackageOptionsForm.rtnPackageList.value+"&CarrierPayMethodText="+carrierLocalText;
    xmlHttp.open("POST",url,false);
    xmlHttp.send(null);  
    }

  function setValue()	{
  var carrierTerm =document.aascPackageOptionsForm.rtnPayMethod.value;
  var str=document.aascPackageOptionsForm.rtnShipMethod.options[document.aascPackageOptionsForm.rtnShipMethod.selectedIndex].value;
  var carrierCode=str.substring(0,3);
  //var upsMode=trim(document.aascPackageOptionsForm.upsMode.value);
  var payMethodTemp=document.aascPackageOptionsForm.rtnPayMethod.value;
    
   	
  if(document.aascPackageOptionsForm.rtnPayMethod.value=='PP')
			{

				document.aascPackageOptionsForm.rtnACNumber.value=document.aascPackageOptionsForm.CarrierAcHidden.value;

				if(document.aascPackageOptionsForm.rtnACNumber.value == "")
					{
				    document.aascPackageOptionsForm.rtnACNumber.readOnly = false;
				    }

				   else
				  {
					document.aascPackageOptionsForm.rtnACNumber.readOnly = true;
				  }

		  }
  else{
			document.aascPackageOptionsForm.rtnACNumber.readOnly=false;
			document.aascPackageOptionsForm.rtnACNumber.value="";
	 }

    if(document.aascPackageOptionsForm.rtnPayMethod.value=="FC")
    {
       
       // document.DynaAdhocShipSaveForm.CarrierACNumberText.value= document.DynaAdhocShipSaveForm.fCACNumber.value;
        
               
          }
    }


function holdatLocationfn(){

//alert("Pkg count::"+document.aascPackageOptionsForm.packageCount.value);
var  packCount=document.aascPackageOptionsForm.packageCount.value;

//&& window.opener.DynaAdhocShipSaveForm.flagVoid.value == "Y" && window.opener.DynaAdhocShipSaveForm.flagShip.value != "Y"
if(document.aascPackageOptionsForm.holdAtLocation.checked == true)
{
   document.aascPackageOptionsForm.holdAtLocation.value = 'Y';

   document.aascPackageOptionsForm.halPhone.disabled = false;

   document.aascPackageOptionsForm.halAddrLine1.disabled = false;
   
   document.aascPackageOptionsForm.halAddrLine2.disabled = false;

   document.aascPackageOptionsForm.halCity.disabled = false;

   document.aascPackageOptionsForm.halState.disabled = false;
   
   document.aascPackageOptionsForm.halZip.disabled = false;
   
   
   //if((window.opener.document.DynaAdhocShipSaveForm.packageSaveCheck1.value=="Y" || window.opener.document.getElementById('flagShip1').value =="Y") &&( window.opener.document.getElementById('halZipID'+packCount).value != null && window.opener.document.getElementById('halZipID'+packCount).value != '') )
   if((window.opener.document.getElementById('flagShip1').value =="Y") &&( window.opener.document.getElementById('halZipID'+packCount).value != null && window.opener.document.getElementById('halZipID'+packCount).value != '') )
   {
    document.aascPackageOptionsForm.halPhone.value = window.opener.document.getElementById('halPhoneID'+packCount).value;
    document.aascPackageOptionsForm.halAddrLine1.value=window.opener.document.getElementById('halAddrLine1ID'+packCount).value;
    document.aascPackageOptionsForm.halAddrLine2.value=window.opener.document.getElementById('halAddrLine2ID'+packCount).value;
    document.aascPackageOptionsForm.halCity.value=window.opener.document.getElementById('halCityID'+packCount).value;
    document.aascPackageOptionsForm.halState.value=window.opener.document.getElementById('halStateID'+packCount).value;
    document.aascPackageOptionsForm.halZip.value =window.opener.document.getElementById('halZipID'+packCount).value;
   }
   else
   {
    document.aascPackageOptionsForm.halPhone.value = window.opener.document.getElementById('phoneNumberId').value;
    document.aascPackageOptionsForm.halAddrLine1.value=trim(window.opener.document.getElementById('shipToAddressId').value);
    document.aascPackageOptionsForm.halAddrLine2.value=window.opener.document.getElementById('shipToAddrLine2Id').value;
     
    document.aascPackageOptionsForm.halCity.value=window.opener.document.getElementById('city').value;
    document.aascPackageOptionsForm.halState.value=window.opener.document.getElementById('state').value;
    document.aascPackageOptionsForm.halZip.value =window.opener.document.getElementById('zip').value;
    
   }
   // document.aascPackageOptionsForm.holdAtLocation.disabled = false;
}
else
{
   document.aascPackageOptionsForm.holdAtLocation.value = 'N';
   document.aascPackageOptionsForm.halPhone.disabled = true;
   document.aascPackageOptionsForm.halAddrLine1.disabled = true;
   document.aascPackageOptionsForm.halAddrLine2.disabled = true;
   document.aascPackageOptionsForm.halCity.disabled = true;
   document.aascPackageOptionsForm.halState.disabled = true;
   document.aascPackageOptionsForm.halZip.disabled = true;   
   document.aascPackageOptionsForm.halPhone.value = '';
   document.aascPackageOptionsForm.halAddrLine1.value = '';
   document.aascPackageOptionsForm.halAddrLine2.value = '';
   document.aascPackageOptionsForm.halCity.value = '';
   document.aascPackageOptionsForm.halState.value = '';
   document.aascPackageOptionsForm.halZip.value = '';
}


}

function halValidate()
{
  var packCount=document.aascPackageOptionsForm.packageCount.value;
  var halPhone=document.aascPackageOptionsForm.halPhone.value;
  var halZip=document.aascPackageOptionsForm.halZip.value;
// Suman Gunda commented for halPhone number validaiton #2079  
//  if(isNaN(halPhone))
//  {
//   alert("The Phone No. should be a numeric value"); 
//   document.aascPackageOptionsForm.halPhone.focus();
//   return false;
//  }
  /* commented the code for fixing issue number 624 on 14/10/08 
 if(isNaN(halZip))
  {
   alert("The Postal Code should be a numeric value");
   document.aascPackageOptionsForm.halZip.focus();
   return false;
  } */
  }
  
  function hazardousMaterialFn()
{
 var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;
 
  if(document.aascPackageOptionsForm.HazardousMaterial.checked == true)
  {
    document.aascPackageOptionsForm.HazardousMaterial.value = 'Y';
    document.aascPackageOptionsForm.HazardousMaterialType.disabled = false;
    document.aascPackageOptionsForm.HazardousMaterialClass.disabled = false;
    
    if(str.substring(0,3)=="111")
    {
    //pavan
     document.aascPackageOptionsForm.HazardousMaterialUnit.disabled = false;
     document.aascPackageOptionsForm.HazardousMaterialQuantity.disabled = false;
     
     document.aascPackageOptionsForm.HazardousMaterialId.disabled = false;
    document.aascPackageOptionsForm.HazMatIdentificationNo.disabled = false;
    document.aascPackageOptionsForm.HazMatDOTLabelType.disabled = false;
    document.aascPackageOptionsForm.HazMatEmergencyContactNo.disabled = false;
    document.aascPackageOptionsForm.HazMatEmergencyContactName.disabled = false;
    document.aascPackageOptionsForm.HazardousMaterialPkgGroup.disabled = false;
     //alert(document.aascPackageOptionsForm.HazardousMaterialQuantity.disabled);
     //Added on Jun-03-2011
       document.aascPackageOptionsForm.HazMatPackagingCnt.disabled = false;
       document.aascPackageOptionsForm.HazMatPackagingUnits.disabled = false;
    //End on Jun-03-2011
    
    //Added on Jul-05-2011
         document.aascPackageOptionsForm.HazMatTechnicalName.disabled = false;
     //End on Jul-05-2011
        document.aascPackageOptionsForm.HazMatSignatureName.disabled = false;
    }
    else
    {
     document.aascPackageOptionsForm.HazardousMaterialId.disabled = true;
    document.aascPackageOptionsForm.HazMatIdentificationNo.disabled = true;
    document.aascPackageOptionsForm.HazMatDOTLabelType.disabled = true;
    document.aascPackageOptionsForm.HazMatEmergencyContactNo.disabled = true;
    document.aascPackageOptionsForm.HazMatEmergencyContactName.disabled = true;
    document.aascPackageOptionsForm.HazardousMaterialPkgGroup.disabled = true;

    //Added on Jun-03-2011
       document.aascPackageOptionsForm.HazMatPackagingCnt.disabled = true;
       document.aascPackageOptionsForm.HazMatPackagingUnits.disabled = true;
    //End on Jun-03-2011
    
     //Added on Jul-05-2011
         document.aascPackageOptionsForm.HazMatTechnicalName.disabled = true;
     //End on Jul-05-2011
         document.aascPackageOptionsForm.HazMatSignatureName.disabled = true;
    }
    var  packCount=document.aascPackageOptionsForm.packageCount.value;

    
  // document.aascPackageOptionsForm.HazardousMaterialWeight.value = window.opener.DynaAdhocShipSaveForm['weight'+packCount].value;
  // document.aascPackageOptionsForm.HazardousMaterialUnit.value = window.opener.DynaAdhocShipSaveForm['uom'+packCount].value;
     
    
  }
  else{
    document.aascPackageOptionsForm.HazardousMaterial.value = 'N';
    document.aascPackageOptionsForm.HazardousMaterialType.disabled = true;
    document.aascPackageOptionsForm.HazardousMaterialClass.disabled = true;
    
    //pavan
    document.aascPackageOptionsForm.HazardousMaterialUnit.disabled = true;
    
    document.aascPackageOptionsForm.HazardousMaterialQuantity.value ='';
    document.aascPackageOptionsForm.HazardousMaterialQuantity.disabled = true;
    
   // document.aascPackageOptionsForm.HazardousMaterialType.value = '';
   // document.aascPackageOptionsForm.HazardousMaterialClass.value = ''
   
    document.aascPackageOptionsForm.HazardousMaterialId.disabled = true;
    document.aascPackageOptionsForm.HazMatIdentificationNo.disabled = true;
    document.aascPackageOptionsForm.HazMatDOTLabelType.disabled = true;
    document.aascPackageOptionsForm.HazMatEmergencyContactNo.disabled = true;
    document.aascPackageOptionsForm.HazMatEmergencyContactName.disabled = true;
    document.aascPackageOptionsForm.HazardousMaterialPkgGroup.disabled = true;
    
    //Added on Jun-03-2011
       document.aascPackageOptionsForm.HazMatPackagingCnt.disabled = true;
       document.aascPackageOptionsForm.HazMatPackagingUnits.disabled = true;
    //End on Jun-03-2011
    
    //Added on Jul-05-2011
         document.aascPackageOptionsForm.HazMatTechnicalName.disabled = true;
     //End on Jul-05-2011
        document.aascPackageOptionsForm.HazMatSignatureName.disabled = true;
  }

}


// Added by Sambit on 25/07/2008  

function getHazMatClass()
   {
   //alert("Inside 2778");
   
   var str=window.opener.document.getElementById('shipMethodId').options[window.opener.document.getElementById('shipMethodId').selectedIndex].value;

      //var carrierCode = document.aascPackageOptionsForm.ajaxCarrierCode.value;//110;
      var carrierCode = str.substring(0,3)
   var lookUpMeaning = document.aascPackageOptionsForm.HazardousMaterialType.value;
  
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
           
           //document.aascPackageOptionsForm.HazardousMaterialClass.options.length=1;
           document.aascPackageOptionsForm.HazardousMaterialClass.options[0] = new Option("--Select--","--Select--",true,true);
           //alert("test Index:"+testindex);
            while(testindex!=-1)
            {
            optionValue=responseStringDummy.substr(0,testindex);
            //alert("2830"+optionValue);
            if(globalvar !="" && globalvar != null){
            //alert("Inside if 2831");
            
            if(globalvar!='' && globalvar==optionValue)
                      {
                       // document.aascPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, true);
                       document.forms['aascPackageOptionsForm'].HazardousMaterialClass.options[i] = new Option(optionValue,optionValue, true, true);
                      }
                      else
                      {
                       // document.aascPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, false);
                       document.forms['aascPackageOptionsForm'].HazardousMaterialClass.options[i] = new Option(optionValue,optionValue, true, false); 
                      }
            
            
            }else{
            
                document.forms['aascPackageOptionsForm'].HazardousMaterialClass.options[i] = new Option(optionValue,optionValue);
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

      //var carrierCode = document.aascPackageOptionsForm.ajaxCarrierCode.value;//110;
      var carrierCode = str.substring(0,3)
      
     // alert("In values from par:"+document.aascPackageOptionsForm.HazardousMaterialId.value);
      //alert("In values globalDefaultHazMatId:"+globalDefaultHazMatId);
      hazMatId = document.aascPackageOptionsForm.HazardousMaterialId.value;
        //alert("After haz ID:"+hazMatId);
     if((globalDefaultHazMatId!="")&&(hazMatId==""))
      {
      //alert("Inside IF");
        hazMatId = globalDefaultHazMatId;
      }
      else
      {
     // alert("Inside else");
        hazMatId = document.aascPackageOptionsForm.HazardousMaterialId.value;
      }
    
      var locationId = window.opener.document.getElementById('locationId').value;
      
      //alert("Inv Org:"+InvOrgId);
      
      //var lookUpMeaning = document.aascPackageOptionsForm.HazardousMaterialType.value;
  
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
            
            //alert("1111 globalDefaultHazMatId:"+globalDefaultHazMatId);
            //alert("1111 hazMatId:"+hazMatId);
           //On load both values are same            
           if(globalDefaultHazMatId==hazMatId)
           {
             document.aascPackageOptionsForm.HazardousMaterialId.value=globalDefaultHazMatId;
           }  
                     
            startIndex  = responseStringDummy.indexOf('*');
            responseStringDummy=responseStringDummy.substring(startIndex+1);
           
            startIndex  = responseStringDummy.indexOf('*');
            classes     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            var i =0;
            
            //alert("2"+classes);
            // document.forms['aascPackageOptionsForm'].HazardousMaterialClass.options[i] = Option(classes,classes, true, true);
             
             document.aascPackageOptionsForm.HazardousMaterialClass.value=classes;
            
            startIndex  = responseStringDummy.indexOf('*');
            quantity     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
             
            document.aascPackageOptionsForm.HazardousMaterialQuantity.value=quantity;
           
            startIndex  = responseStringDummy.indexOf('*');
            unit     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
             
            document.aascPackageOptionsForm.HazardousMaterialUnit.value=unit;
             //alert("4"+unit);
             //document.forms['aascPackageOptionsForm'].HazardousMaterialUnit.options[i] = Option(unit,unit, true, true);
           
            startIndex  = responseStringDummy.indexOf('*');
            IdNo     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
             
            document.aascPackageOptionsForm.HazMatIdentificationNo.value=IdNo;
            
           // alert("5"+IdNo);
            
            startIndex  = responseStringDummy.indexOf('*');
            labelType     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            //alert("6"+labelType);
            
            document.aascPackageOptionsForm.HazMatDOTLabelType.value=labelType;
                
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
            
            document.aascPackageOptionsForm.HazMatEmergencyContactNo.value=contactNo;
            
                            
            startIndex  = responseStringDummy.indexOf('*');
            contactName     = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            //alert("10"+contactName);
           
            if(contactName=="null")
            {
             document.aascPackageOptionsForm.HazMatEmergencyContactName.value="";
            }
            else
            {
            document.aascPackageOptionsForm.HazMatEmergencyContactName.value=contactName;
            }
                
            startIndex  = responseStringDummy.indexOf('*');
            pkgGroup  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            
             //alert("11"+pkgGroup);
             if(pkgGroup=="null")
            {
             document.aascPackageOptionsForm.HazardousMaterialPkgGroup.value="--Select--";
            }
            else
            {
            document.aascPackageOptionsForm.HazardousMaterialPkgGroup.value=pkgGroup;
            }
                      
            //12th
            startIndex  = responseStringDummy.indexOf('*');
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            startIndex  = responseStringDummy.indexOf('*');
            var pkgingCnt  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            document.aascPackageOptionsForm.HazMatPackagingCnt.value=pkgingCnt;
            
            //13th
            startIndex  = responseStringDummy.indexOf('*');
            var pkgingUnits  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            document.aascPackageOptionsForm.HazMatPackagingUnits.value=pkgingUnits;
            
            
            //Added on Jul-05-2011
            //14th
            startIndex  = responseStringDummy.indexOf('*');
            var technicalName  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            document.aascPackageOptionsForm.HazMatTechnicalName.value=technicalName;
            //End on Jul-05-2011
            startIndex  = responseStringDummy.indexOf('*');
            var signatureName  = responseStringDummy.substring(0,startIndex);
            responseStringDummy=responseStringDummy.substring(startIndex+1);
            
            document.aascPackageOptionsForm.HazMatSignatureName.value=signatureName;

        }
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

      //var carrierCode = document.aascPackageOptionsForm.ajaxCarrierCode.value;//110;
      var carrierCode = str.substring(0,3)
   var lookUpMeaning = document.aascPackageOptionsForm.HazardousMaterialType.value;
  
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
           
           document.aascPackageOptionsForm.HazardousMaterialPkgGroup.options.length=1;
           document.aascPackageOptionsForm.HazardousMaterialPkgGroup.options[0] = new Option("--Select--","--Select--",true,true);
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
                       // document.aascPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, true);
                       document.forms['aascPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option(optionValuePkgGrp,optionValuePkgGrp, true, true);
                      }
                      else
                      {
                     // alert("Else");
                       // document.aascPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, false);
                       document.forms['aascPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option(optionValuePkgGrp,optionValuePkgGrp, true, false); 
                      }
            
            
            }else{
//            alert("Main else");
            localCheckValue ="globalHazMatPkgGrpLoad";
                 document.forms['aascPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option(optionValuePkgGrp,optionValuePkgGrp);
                 //*****
                 if(globalHazMatPkgGrpLoad!='' && globalHazMatPkgGrpLoad==optionValuePkgGrp)
                      {
                      //alert(" Main else If");
                       // document.aascPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, true);
                       document.forms['aascPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option(optionValuePkgGrp,optionValuePkgGrp, true, true);
                      }                     
                      else
                      {
                      //alert("Here");              
                       // document.aascPackageOptionsForm.rtnDropOfType.options[counter]=new Option(parsetest, parsetest, true, false);
                       document.forms['aascPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option(optionValuePkgGrp,optionValuePkgGrp, true, false); 
                      }
                 //******
            }
            responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
            testindex  = responseStringDummy.indexOf('***');
            i++;
	          }
              /*    if(globalvar2=='' && localCheckValue == "globalvar2" )
                       {
                       document.forms['aascPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option('','', true, true); 
                       }else if(globalHazMatPkgGrpLoad=='' && localCheckValue== "globalHazMatPkgGrpLoad")
                       {
                       document.forms['aascPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option('','', true, true); 
                       }  */
                       if(globalvar2=='' || globalHazMatPkgGrpLoad == '' )
                       {
                       document.forms['aascPackageOptionsForm'].HazardousMaterialPkgGroup.options[i] = new Option('','', true, true); 
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
      
      globalDefaultHazMatId = document.aascPackageOptionsForm.hazMatIdHidden.value;
      //alert(globalDefaultHazMatId);
    
      if(carrierCode=="111")
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

function chDryIceCheck(){

            

            document.aascPackageOptionsForm.chDryIce.value="N"; 

            document.aascPackageOptionsForm.dryIceWeight.readOnly=true;

            document.aascPackageOptionsForm.dryIceWeight.value="";

            document.aascPackageOptionsForm.dryIceUnits.disabled=true;

            //document.aascPackageOptionsForm.dryIceUnits.value="";

          
           
            if(document.aascPackageOptionsForm.chDryIce.checked){

                    document.aascPackageOptionsForm.chDryIce.value="Y";

                    document.aascPackageOptionsForm.dryIceUnits.readOnly=false;
                    document.aascPackageOptionsForm.dryIceUnits.disabled=false;

                    document.aascPackageOptionsForm.dryIceWeight.readOnly=false;
                    document.aascPackageOptionsForm.dryIceWeight.disabled=false;
               }	
   else
   {
   document.aascPackageOptionsForm.chDryIce.value="N";
   
   }

}
    
