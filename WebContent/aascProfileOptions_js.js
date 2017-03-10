// Create by Suman Gunda for validations of submit profile options
// @ Created on 08-08-2014
// 24/12/2014  Jagadish Jain   Fixed all disabling and enabling issue for different roles
// 13/02/2015  Suman G         Added reference 1 and 2 related validaitons to fix #2603.
// 20/02/2015  Y Pradeep       Modified code for Address Validationa dn Freight Shopping. Removed Average pallet weight and lable printing check box.
// 20/02/2015  Sunanda K       Added functions disableSave(),enableSave() to fix #2582
// 24/02/2015  Y Pradeep       Modified code for Address Validationa.
// 26/02/2015  Suman G         Added code for freight shop.
// 10/03/2015  Y Pradeep       Added validations for Adderss Validationa nad Freight Shoping features.
// 13/03/2015  K Sunanda       Added missing out code for bug fix #2582 after the UI changes
// 19/03/2015  Y Pradeep       Added code to disable Profile Options Fields in Role 4. Bug #2704.
// 30/03/2015  K Suannda       Added code for company code mandatory validation for bug fix #2742.
// 07/04/2015  Y Pradeep       Added trim() function to avoiding empty spaces in text filed while saving profile options. Bug #2788.
// 08/04/2015  Y Pradeep       Commented code to display Location LOV enabled for all roles except role 5. Bug #2807.
// 10/04/2015  Y Pradeep       Added stopEnterKeyPress function to avoid form submition when enter button is pressed any field is focused in jsp page. Bug #2785.
// 13/04/2015  Y Pradeep       Modified code to trim company name field and assigning back to it. Bug #2788.
// 14/04/2015  Y Pradeep       Added stopEnterKeyPress function code to disable form submition for text, radio and checkbox fields.
// 14/04/2015  Eshwari M       Modified alert to resolve bug # 2855.
// 17/04/2015  Y Pradeep       Removed code to focus save image on page load. Bug #2863.
// 11/05/2015  Y Pradeep       Corrected spell mistake regarding bug #2917.
// 03/05/2015  k Sunanda       Modified code for bug fix #2933
// 03/06/2015  Y Pradeep       Added checkBoxValue() function for checking the Weighing Scale checkbox.
// 09/06/2015  Y Pradeep       Added weighingScaleNameId and weighingScaleId field to bed disabled for role 2 and role 4. Bug #2971.
// 09/06/2015  Y Pradeep       Modified code to disable or enable when Weighing Scale checkbox is checked.
// 23/06/2015  Y Pradeep       Added onClickPrinterSetup() function to call Printer Setup page for setting multple printes based on lable format.
// 23/07/2015  Y Pradeep       Added validation when Printer Details button is clicked without selecting location in Profile Options page. Bug #3213.
// 11/08/2015  Y Pradeep       Added validation for Weighing Scale LOV selection while saving. Bug #3345.
// 24/08/2015  Y Pradeep       Added '=' to clientId parameter while passing it for opening Printer Configuration popup. Bug #3465.
// 30/11/2015  Y Pradeep       Removed javascript errors in Profile Options page. Bug #4050.
// 21/12/2015  Y Pradeep       Commented code to remove validations and fields related to username, password and account number for addressvalidation and freightshopping.
// 28/12/2015  Suman G         Commented code to remove customer name field.

function disableSave(){
//alert("inside disableSave");
document.getElementById('saveImage').disabled=true;
document.getElementById('reset').disabled=true;
document.getElementById('saveImage').src="buttons/aascSaveOff1.png";//Sunanda Added for bug fix #2582
document.getElementById('reset').src="buttons/aascClearOff1.png";

}
function enableSave(){
//alert("inside enableSave");
document.getElementById('saveImage').disabled=false;
document.getElementById('reset').disabled=false;
document.getElementById('saveImage').src="buttons/aascSave1.png";//Sunanda Added for bug fix #2582
document.getElementById('reset').src="buttons/aascClear1.png";

}
function load(){
    var roleId = document.profileOptionsForm.roleId.value;
   // alert(roleId);
    if(document.profileOptionsForm.weighingScale.checked)
    {
     document.profileOptionsForm.weighingScale.value="Y";
     document.profileOptionsForm.weighingScaleName.disabled = false;
    }
    else
    {
     document.profileOptionsForm.weighingScale.value="N";
     document.profileOptionsForm.weighingScaleName.disabled = true;
     document.profileOptionsForm.weighingScaleName.selectedIndex = 0;
    }
    
    if(document.profileOptionsForm.enableSaturdayFlagHidden.value == "Y")
        document.profileOptionsForm.enableSaturdayFlag.checked = true;
    if(document.profileOptionsForm.editShipToAddressHidden.value == "Y")
        document.profileOptionsForm.editShipToAddress.checked = true;
    if(document.profileOptionsForm.fsFlagHidden.value == 'Y' || document.profileOptionsForm.fsFlagHidden.value == 'y')    
    {
      if(document.profileOptionsForm.fsFlagHidden.value == "Y")
        document.profileOptionsForm.freightShopping.checked = true;
    }    
    if(document.profileOptionsForm.reference1Hidden.value == "Y")
    document.profileOptionsForm.reference1.checked = true;
    
    if(document.profileOptionsForm.reference2Hidden.value == "Y")
    document.profileOptionsForm.reference2.checked = true;
    
     if(document.profileOptionsForm.fsFlagHidden.value == "Y")
    document.profileOptionsForm.freightShopping.checked = true;
        
    if(roleId == 4 || roleId==2)
    {
        /*if(roleId==4){
            document.profileOptionsForm.locationId.disabled = true;
            document.getElementById('GoButton').disabled=true;
            document.getElementById('GoButton').src="buttons/aascGoOff.png";
        }*/
        document.profileOptionsForm.defaultPayMethod.disabled = true;
        document.profileOptionsForm.enableSaturdayFlag.disabled = true;
        document.profileOptionsForm.editShipToAddress.disabled = true;
          /*var mode=trim(document.getElementById("upsMode").value);
                  if( mode != 'ShipExec' ){
        document.profileOptionsForm.addresValidation.disabled = true;
        }
        else{
        document.profileOptionsForm.addresValidation.disabled = false;
        }*/
        document.profileOptionsForm.addresValidation.disabled = true;
        document.profileOptionsForm.freightShopping.disabled = true;
        document.profileOptionsForm.maskAccount.disabled = true;
        //document.profileOptionsForm.wsUserName.disabled = true;
        //document.profileOptionsForm.wsPassword.disabled = true;
        //document.profileOptionsForm.wsAccountNumber.disabled = true;
        document.profileOptionsForm.reference1.disabled = true;
        document.profileOptionsForm.reference2.disabled = true;
        document.profileOptionsForm.companyName.disabled = true;
        document.profileOptionsForm.shipperName.disabled = true;
        document.profileOptionsForm.weighingScaleName.disabled = true;
        document.profileOptionsForm.weighingScale.disabled = true;
                
    }
    //enableFields();  
}

function saveValidation(){

    var defaultPayMethod=document.getElementById('defaultPayMethodID').value;
    //var addresValidation = document.profileOptionsForm.addresValidation.checked;
    //var freightShopping = document.profileOptionsForm.freightShopping.checked;
    //var wsUserName = document.profileOptionsForm.wsUserName.value;
    //var wsPassword = document.profileOptionsForm.wsPassword.value;
    //var wsAccountNumber = document.profileOptionsForm.wsAccountNumber.value;
    var locationIndex = document.getElementById('locationID').selectedIndex;
    var locationValue = document.getElementById('locationID')[locationIndex].text;
    var companyName= trim(document.getElementById('companyNameID').value);
    document.getElementById('companyNameID').value = companyName;
    var shipperName = trim(document.getElementById('shipperNameID').value);
    document.getElementById('shipperNameID').value = shipperName;
    if(locationValue == "--Select One--")
    {
        alert("Select Location and click on Go");
        document.profileOptionsForm.locationId.focus();
        return false;  
    }  
    if(defaultPayMethod==""){
    alert("Select Pay Method");
    document.getElementById('defaultPayMethodID').focus();
    return false;
    }
    
    //Sunanda added for bug fix #2742
    if(companyName == null || companyName == "")
    {
        alert("Please enter Company Name");
        document.getElementById('companyNameID').focus();
        return false;
    }
//    vikas commented code for issue #3826
//     if(shipperName == null || shipperName == "")
//    {
//        alert("Please enter shipperName");
//        document.getElementById('shipperNameID').focus();
//        return false;
//    }
// vikas comments ended    
    if(document.profileOptionsForm.weighingScale.checked){
        if(document.profileOptionsForm.weighingScaleName.options[document.profileOptionsForm.weighingScaleName.selectedIndex].text == "--Select--"){
            alert('Please select a Weight Scale Model');
            document.profileOptionsForm.weighingScaleName.focus();
            return false;
        }
    }
    
    /*if(addresValidation == true || freightShopping == true) {
        if(wsUserName == "" || wsUserName == null){
            alert("Please enter UserName for Address Validation or Freight Shop");
            document.profileOptionsForm.wsUserName.focus();
            return false;
        }
        
        if(wsPassword == "" || wsPassword == null){
            
            alert("Please enter Password for Address Validation or Freight Shop");
            document.profileOptionsForm.wsPassword.focus();
            return false;
        }
        
        if(wsAccountNumber == "" || wsAccountNumber == null){
            
            alert("Please enter Account Number for Address Validation or Freight Shop");
            document.profileOptionsForm.wsAccountNumber.focus();
            return false;
        }       
        
    }*/
    //Sunanda added below code from SAAS for bug fix #2933
        if(!document.profileOptionsForm.editShipToAddressID.checked) //If the checked checkbox is unchecked and pressed the save button in PO page.
        {
            //alert("Inside if");
//            document.profileOptionsForm.customerNameFlag.value="N";
            document.profileOptionsForm.addrLinesFlag.value="N";
            document.profileOptionsForm.cityFlag.value="N";
            document.profileOptionsForm.stateFlag.value="N";
            document.profileOptionsForm.postalCodeFlag.value="N";
            document.profileOptionsForm.countryCodeFlag.value="N";
        }
        else if((document.profileOptionsForm.editShipToSaveStatus.value=="N" && document.profileOptionsForm.editShipToDBFlag.value!="Y")||
        (//document.profileOptionsForm.customerNameFlag.value == "" &&
        document.profileOptionsForm.addrLinesFlag.value == "" &&
        document.profileOptionsForm.cityFlag.value == "" &&
        document.profileOptionsForm.stateFlag.value == "" &&
        document.profileOptionsForm.postalCodeFlag.value == "" &&
        document.profileOptionsForm.countryCodeFlag.value == "" ))
        {
         //(1)Already unchecked checkbox(from Db i.e editShipToDBFlag=N) is checked and without opening the details page and pressed the save button in PO page. (Or)
         //(2)if we select the new organization from the PO page where the Editshiptoaddress is 'Y' and we directly pressed the save in PO page then all fields(hidden fields in PO) in editshiptodetails page should be checked.

            var ConfirmStatus = confirm ("Checked all checkboxes in EditShipToDetails PopUp Page and Saving it !!!")
            if (ConfirmStatus==false)
            {
              //If cancel button of confirm box is clicked
              return false;
            }

//            document.profileOptionsForm.customerNameFlag.value="Y";
            document.profileOptionsForm.addrLinesFlag.value="Y";
            document.profileOptionsForm.cityFlag.value="Y";
            document.profileOptionsForm.stateFlag.value="Y";
            document.profileOptionsForm.postalCodeFlag.value="Y";
            document.profileOptionsForm.countryCodeFlag.value="Y";
            
            
    }
//alert("outside save validation");
document.profileOptionsForm.actionType.value="Save/Update";
return true;



}

function trim(str)
{
  return str.replace(/^\s+|\s+$/g,"");
}

function validation(){
    
    var locationIndex = document.getElementById('locationID').selectedIndex;
    var locationValue = document.getElementById('locationID')[locationIndex].text;
    

    if(locationValue == "--Select One--")
    {
        alert("Select Location and click on Go");
        document.profileOptionsForm.locationId.focus();
        return false;  
    }  
    
    
//alert(" ref 1 ::"+document.profileOptionsForm.reference1.checked);
//alert(" ref 2::"+document.profileOptionsForm.reference2.checked);


    }
    
    
function onClickEditShipToDetails()
{

 var roleId1 = document.profileOptionsForm.roleId.value;
 
 if(document.profileOptionsForm.editShipToAddress.checked==false){
 alert("Please check 'Edit Ship To Address ' to open 'Details' popup");
 }
 
 if(document.profileOptionsForm.editShipToAddress.checked==true)
  {
  shipWindow =  window.open("aascEditShipToDetails.jsp?accessRole="+roleId1,"Post",'width=500,height=300,top=400,left=500,scrollbars=yes, resizable=yes');//Made resizable from no to yes in order to enable maximize option in IE
  if (window.focus)
  {
  shipWindow.focus();
  }
  }
}


// onPOClientChange() added by Jagadish for role 1 and role 2
function onPOClientChange()
{ 
 //document.profileOptionsForm.clientId.selectedIndex = 0;
 document.profileOptionsForm.locationId.selectedIndex = 0;
}
// getPOLocations() added by Jagadish for role 1 and role 2
function getPOLocations()
{
  //alert("Inside getPOOperatingUnits"); 
  document.profileOptionsForm.actionType.value='clientDetail';
  document.profileOptionsForm.submit();
}

/*function enableFields(){
    var addresValidation = document.profileOptionsForm.addresValidation.checked;
    var freightShopping = document.profileOptionsForm.freightShopping.checked;
    if(addresValidation == true || freightShopping == true) {
        document.getElementById('addressValidationTableId').style.display ="block";
    }
    else {
        document.getElementById('addressValidationTableId').style.display ="none";
    }

}*/

function changeAV(){
    var addresValidation = document.profileOptionsForm.addresValidation.checked;
    if(addresValidation){
        document.profileOptionsForm.addresValidation.value='Y';
    }else{
        document.profileOptionsForm.addresValidation.value='N';
    }    
}
// vikas added code to mask the carrier account number in the shipment page
function changeMA(){
    var maskAccountNum = document.profileOptionsForm.maskAccount.checked;
    if(maskAccountNum){
        document.profileOptionsForm.maskAccount.value='Y';
    }else{
        document.profileOptionsForm.maskAccount.value='N';
    }    
}
// vikas code ended

function changeFS(){
    var freightShop = document.profileOptionsForm.freightShopping.checked;
    if(freightShop){
        document.profileOptionsForm.freightShopping.value='Y';
    }else{
        document.profileOptionsForm.freightShopping.value='N';
    }
}

//This funcion "stopEnterKeyPress(evt)"added by Khaja for Browser Campatability
function stopEnterKeyPress(evt) {
  var evt = (evt) ? evt : ((event) ? event : null);
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
 
  if ((evt.keyCode == 13) && ((node.type=="text") || (node.type=="checkbox") || (node.type=="radio")))  {return false;}

}
document.onkeypress = stopEnterKeyPress;   ///This line belong to the  above function stopEnterKeyPress(evt) to stop Form submition on press of Enter key


function checkBoxValue(checkbox)
{
  var Chkname=checkbox.name;
  if(document.profileOptionsForm[Chkname].checked)
  {
    document.profileOptionsForm[Chkname].value="Y";
    document.profileOptionsForm.weighingScaleName.disabled = false;
  }
  else
  {
    document.profileOptionsForm[Chkname].value="N";
    document.profileOptionsForm.weighingScaleName.disabled = true;
    document.profileOptionsForm.weighingScaleName.selectedIndex = 0;
  }
}

function onClickPrinterSetup(){
        
    var locationText = document.profileOptionsForm.locationId.options[document.profileOptionsForm.locationId.selectedIndex].text;
    if(locationText == "--Select One--")
    {
        alert("Select Location and click on Go");
        document.profileOptionsForm.locationId.focus();
        return false;  
    }
    
    var locationId=document.profileOptionsForm.locationId.options[document.profileOptionsForm.locationId.selectedIndex].value;
        
    var roleId = document.profileOptionsForm.roleId.value;
    
    var clientId = "";
    if(roleId == 1 || roleId == 2){
        clientId = document.getElementById("clientId").options[document.getElementById("clientId").selectedIndex].value;
    }
    
    shipWindow =  window.open("PrinterSetupAction.action?submit=printerDetails&locationId="+locationId+"&roleId="+roleId+"&clientId="+clientId,"Post",'width=930,height=550,top=80,left=100,scrollbars=yes, resizable=yes');
    if (window.focus)
    {
        shipWindow.focus();
    }

}

