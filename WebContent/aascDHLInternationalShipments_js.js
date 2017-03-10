/*===========================================================================================================+

|  DESCRIPTION                                                                                               |

|    javascript file for the aascInternationalShipments.jsp  validation                                         |
|    Author G S Shekar
|    Version  1                                                                                              |                                                                            
|    Creation 27-OCT-2015    
|    History
|    Date           Resource            Changes
|   06/11/2015      Shiva G         Modified code to fix the issue 3926
    10/11/2015      Suman G         Added code to fix #3946
+===========================================================================================================*/


function saveDetails()
{    
        
     
     var lineCustomValue = document.aascDHLINTLShipmentsForm.commCustomValue.value;

     
//     window.opener.document.DynaShipmentShipSaveForm.intTotalCustomsValue.value=document.aascDHLINTLShipmentsForm.TotalCustomsValue.value;
     window.opener.document.DynaShipmentShipSaveForm.intlWeightValue.value=document.aascDHLINTLShipmentsForm.commWeightValue.value;
     window.opener.document.DynaShipmentShipSaveForm.intlSaveFlag.value = "Y";
          
     var payerType = document.aascDHLINTLShipmentsForm.payerType.value;
     var AccNumber = document.aascDHLINTLShipmentsForm.AccNumber.value;
     var TermsOfTrade = document.aascDHLINTLShipmentsForm.TermsOfTrade.value;
     
     if(payerType==""){
        alert("Please select Bill duties/taxes/fees to");
        document.aascDHLINTLShipmentsForm.payerType.focus();
        return false;
     }
     if(AccNumber==""){
        alert("Please enter Account Number");
        document.aascDHLINTLShipmentsForm.AccNumber.focus();
        return false;
     }
     if(TermsOfTrade==""){
        alert("Please Select Terms Of Trade");
        document.aascDHLINTLShipmentsForm.TermsOfTrade.focus();
        return false;
     }
     
     document.aascDHLINTLShipmentsForm.actionType.value='SAVE';
     document.aascDHLINTLShipmentsForm.submit();
     
     
}

function loadDetails()
{    
    var CCode = document.aascDHLINTLShipmentsForm.CountryOfManufacture.value;
    var QUnits = document.aascDHLINTLShipmentsForm.QuantityUnits.value;
     var shipFlag = document.aascDHLINTLShipmentsForm.shipFlag.value;//added this line
    
    var acNumber = window.opener.document.DynaShipmentShipSaveForm.carrierAccountNumber.value;
    window.document.getElementById("AccNumber").value = acNumber;
    
    if(CCode == '')
         document.aascDHLINTLShipmentsForm.CountryOfManufacture.value = 'US';
      if (document.aascDHLINTLShipmentsForm.shipment.value == 'adhoc')
      {
    var shipFlagAdc = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;
    //alert("shipFlag"+shipFlag);
    document.aascDHLINTLShipmentsForm.shipment.value = window.opener.document.DynaShipmentShipSaveForm.shipmentType.value;
    
         document.aascDHLINTLShipmentsForm.orderNo.value = window.opener.document.DynaShipmentShipSaveForm.orderNum.value;
     }
     var shipmentStatusFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value ;  //shipmentStatusFlag.value; 
     //alert('shipmentStatusFlag : '+shipmentStatusFlag);
        
    if(shipFlag == 'Y'){
       document.aascDHLINTLShipmentsForm.addComm.disabled = true;
      // document.aascDHLINTLShipmentsForm.editComm.disabled = true;
       document.aascDHLINTLShipmentsForm.delComm.disabled = true;
       document.aascDHLINTLShipmentsForm.save.disabled = true;
       document.aascDHLINTLShipmentsForm.payerType.disabled = true;
       document.aascDHLINTLShipmentsForm.AccNumber.disabled = true;
       document.aascDHLINTLShipmentsForm.TermsOfTrade.disabled = true; //Shiva added
       
       
       var descriptionVal = document.aascDHLINTLShipmentsForm.description.value;
        if(descriptionVal.length < 1){
        getIntlCommodityLineDetails(); 
       }
       
    }
    else{
       document.aascDHLINTLShipmentsForm.addComm.disabled = false;
       document.aascDHLINTLShipmentsForm.editComm.disabled = false;
       document.aascDHLINTLShipmentsForm.delComm.disabled = false;
       document.aascDHLINTLShipmentsForm.save.disabled = false;
       
       //Added below line of code
        var descriptionVal = document.aascDHLINTLShipmentsForm.description.value;
        if(descriptionVal.length < 1){
        getIntlCommodityLineDetails(); 
       }
    }
    
     
          
}

function assignOrderNumber(){
    window.opener.document.DynaShipmentShipSaveForm.orderNum.value=document.aascDHLINTLShipmentsForm.orderNumber.value;
}


function genSelect()
{
//document.forms['aascDHLINTLShipmentsForm'].commodityLine.options.length = 5;
document.forms['aascDHLINTLShipmentsForm'].commodityLine.options.size = 5;
document.forms['aascDHLINTLShipmentsForm'].commodityLine.options[0] =  new Option("Product       Unit        Weight       Value","1",true,false);
document.forms['aascDHLINTLShipmentsForm'].commodityLine.options[1] =  new Option("-----------------------------------------------------","2",true,false);


// document.forms['SigTrackingPageMainForm'].inputNumberText.options.length = 0; 
//   document.forms['SigTrackingPageMainForm'].inputNumberText.options[0] =  new Option("","",true,false);

}

function saveComm()
{

    var noPieces = document.aascDHLINTLShipmentsForm.NumberOfPieces.value;
    var des = document.aascDHLINTLShipmentsForm.description.value;
    var Contry = document.aascDHLINTLShipmentsForm.CountryOfManufacture.value;
    var weight = document.aascDHLINTLShipmentsForm.Weight.value;
    var quantity = document.aascDHLINTLShipmentsForm.Quantity.value;
    var quaUnits = document.aascDHLINTLShipmentsForm.QuantityUnits.value;
    var customs = document.aascDHLINTLShipmentsForm.CustomsValue.value;
    var unitValue = document.aascDHLINTLShipmentsForm.UnitPrice.value;
    var expNo = document.aascDHLINTLShipmentsForm.ExportLicenseNumber.value;
    var expDate = document.aascDHLINTLShipmentsForm.ExportLicenseExpirationDate.value;
     
    if(navigator.appName !='Microsoft Internet Explorer')
    {
       expDate= expDate.trim(); //khaja added this line for bug fix 1128 
    }

    if(noPieces == ''){
       alert("  Please Enter Number of Pieces for Item  ");
       document.aascDHLINTLShipmentsForm.NumberOfPieces.focus();
       return false;
    }
    for (var i = 0; i < noPieces.length; i++) {
  	if(!(noPieces.charCodeAt(i)>47 && noPieces.charCodeAt(i)<58))
       {
          alert("Please Enter valid Numbers only");
          document.aascDHLINTLShipmentsForm.NumberOfPieces.focus();
  	  return false;
       }
    }
 
    if(des == '')
    {
       alert("  Please Enter Product name/Description  ");
       document.aascDHLINTLShipmentsForm.description.focus();
       return false;
    }
        // Gururaj modified code for allowing all special charecters except *+"<> for bug 1798
        for (var i = 0; i < des.length; i++) 
        {
  	
            if(des.charCodeAt(i)==43||des.charCodeAt(i)==42||des.charCodeAt(i)==60||des.charCodeAt(i)==62||des.charCodeAt(i)==34)
            {
            alert("Please enter valid Product Description");
            document.aascDHLINTLShipmentsForm.description.focus();
                return false;
            }
        }
        //end
     
     if(Contry == ''){
        alert("  Please Enter Country Of Manufacture of the Item  ");
        document.aascDHLINTLShipmentsForm.CountryOfManufacture.focus();
        return false;
     }
     if(weight == ''){
        alert("  Please Enter Weight of Item  ");
        document.aascDHLINTLShipmentsForm.Weight.focus();
        return false;
     }
     var decimalCount = 0;
     for (var i = 0; i < weight.length; i++) {
  	if(!(weight.charCodeAt(i)>47 && weight.charCodeAt(i)<58) && weight.charCodeAt(i)!=46 )
        {
            alert("Please Enter valid Decimal Value only");
            document.aascDHLINTLShipmentsForm.Weight.focus();
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
        document.aascDHLINTLShipmentsForm.Weight.focus();
  	return false;
     }
     var HarmonizedCode =document.aascDHLINTLShipmentsForm.HarmonizedCode.value;
     if(HarmonizedCode==""){
        alert("Please enter Commodity code");
        document.aascDHLINTLShipmentsForm.HarmonizedCode.focus();
        return false;
     }
     if(quantity == ''){
        alert("  Please Enter Quantity of Item  ");
        document.aascDHLINTLShipmentsForm.Quantity.focus();
        return false;
     }
     for (var i = 0; i < quantity.length; i++) {
        if(!(quantity.charCodeAt(i)>47 && quantity.charCodeAt(i)<58))
        {
            alert("Please Enter valid Numbers only");
            document.aascDHLINTLShipmentsForm.Quantity.focus();
            return false;
        }
     }

     if(quaUnits == ''){
        alert("  Please Enter Unit of Measure for Quantity of the Item ");
        document.aascDHLINTLShipmentsForm.QuantityUnits.focus();
        return false;
     }

     if(customs == ''){
        alert("  Please Enter Customs Value of Item  ");
        document.aascDHLINTLShipmentsForm.CustomsValue.focus();
        return false;
     }
     decimalCount = 0;
     for (var i = 0; i < customs.length; i++) {
        if(!(customs.charCodeAt(i)>47 && customs.charCodeAt(i)<58) && customs.charCodeAt(i)!=46 )
        {
            alert("Please Enter valid Decimal Value only");
            document.aascDHLINTLShipmentsForm.CustomsValue.focus();
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
        document.aascDHLINTLShipmentsForm.CustomsValue.focus();
  	return false;
     }
    //Shiva commented below code for for issue #3926
//     if (expNo != '')
//     {
//        if(expNo.length != 7){
//            alert(" ExportLicense Number should contain one Alpha and six numeric value ");
//            document.aascDHLINTLShipmentsForm.ExportLicenseNumber.focus();
//            return false;
//        }
//        if(expNo.charCodeAt(0)<65 || (expNo.charCodeAt(0)>90 && expNo.charCodeAt(0)<97 )|| expNo.charCodeAt(0)>122)
//        {
//            alert(" ExportLicense Number should contain one Alpha and six numeric value ");
//            document.aascDHLINTLShipmentsForm.ExportLicenseNumber.focus();
//            return false;
//        }
//        for (var i = 1; i < expNo.length; i++) 
//        {     
//            if(!(expNo.charCodeAt(i)>47 && expNo.charCodeAt(i)<58))
//            {
//               alert(" ExportLicense Number should contain one Alpha and six numeric value ");
//               document.aascDHLINTLShipmentsForm.ExportLicenseNumber.focus();
//               return false;
//            }    
//        }
//     }
  
//     if ((expNo != '' && expDate == '') || (expNo == '' && expDate != ''))
//     {
//         alert(" Please Enter valid Export License Number and \n valid Export License Expiration Date ");
//         document.aascDHLINTLShipmentsForm.ExportLicenseNumber.focus();
//         return false;
//     }

     var dtCh= "-";
     var minYear=1990;
     var maxYear=2100;

     function stripCharsInBag(s, bag){
            var i;
            var returnString = "";
           // Search through string's characters one by one.
           // If character is not in bag, append to returnString.
           for (i = 0; i < s.length; i++){   
                    var c = s.charAt(i);
                    if (bag.indexOf(c) == -1) returnString += c;
           }
           return returnString;
     }
 
     function daysInFebruary (year){
            return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
     }

     function DaysArray(n) {
        for (var i = 1; i <= n; i++) {
            this[i] = 31;
            if (i==4 || i==6 || i==9 || i==11) {this[i] = 30;  }
            if (i==2) {this[i] = 29;  }
        } 
        return this;
     }

     function isDate(dtStr){
        if(dtStr != '')
        {
		var daysInMonth = DaysArray(12);
                var pos1=dtStr.indexOf(dtCh);
                var pos2=dtStr.indexOf(dtCh,pos1+1);
                var strYear=dtStr.substring(0,pos1);
                var strMonth=dtStr.substring(pos1+1,pos2);
                var strDay=dtStr.substring(pos2+1)	;
                strYr=strYear;
                if (strDay.charAt(0)=="0" && strDay.length>1) 
                    strDay=strDay.substring(1) ;
                if (strMonth.charAt(0)=="0" && strMonth.length>1) 
                    strMonth=strMonth.substring(1)  ;
		for (var i = 1; i <= 3; i++) {
                     if (strYr.charAt(0)=="0" && strYr.length>1) 
                        strYr=strYr.substring(1);
		}

                month=parseInt(strMonth) ;
                day=parseInt(strDay);
                year=parseInt(strYr);

		if (pos1==-1 || pos2==-1){
                       alert("The Date Format Should Be : YYYY-MM-DD");
                       return false;
		}
		if (strMonth.length<1 || month<1 || month>12){
                        alert("Please Enter A Valid Month");
                        return false;
		}

		if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month])
                {
                        alert("Please Enter A Valid Day");
                        return false;
		}

		if (strYear.length != 4 || year==0 || year<minYear || year>maxYear)
                {
                        alert("Please Enter A Valid 4 Digit Year Between "+minYear+" And "+maxYear);
                        return false;
		}
		if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
                        alert("Please Enter A Valid Date");
                        return false;
                }
                return true;
         }
     }//end of date validation

     
//     if(isDate(expDate) == false){
//       //alert(" Please Enter ExportLicense Expiration Date in YYYY-MM-DD format ");
//       document.aascDHLINTLShipmentsForm.ExportLicenseExpirationDate.focus();
//       return false;
//     }
     var commLength = document.aascDHLINTLShipmentsForm.commodityLine.length;
     if(document.aascDHLINTLShipmentsForm.actionStr.value == 'ADD')
     {
        //if(commLength == 22 ){
        if(commLength == 101 ){
           //alert(" Only 20 Commodity Items are allowed for save ");
           alert(" Only 99 Commodity Items are allowed for save ");
           return false;
        }
     }

    
     document.aascDHLINTLShipmentsForm.selectLength.value = document.aascDHLINTLShipmentsForm.commodityLine.length + 1;
     document.aascDHLINTLShipmentsForm.actionType.value='ADD';
     document.aascDHLINTLShipmentsForm.submit();

}
function enableIBN()
{
   var inbondType = document.getElementById("InBondCode").options[document.getElementById("InBondCode").selectedIndex].value;
   //alert("inbondType : "+inbondType);
   if(inbondType == "36" || inbondType == "37")   // || inbondType == "67" || inbondType == "68")
   {
     document.getElementById("EntryNumber").disabled=false;
     //document.getElementById("ForeignTradeZone").disabled=false;
   } 
   else
   {
    document.getElementById("EntryNumber").disabled=true;
    //document.getElementById("ForeignTradeZone").disabled=true;
   }
      var exportCodeVal = document.getElementById('exportCodeValue').value;
             // alert('exportCodeVal:'+exportCodeVal);
             
   if(inbondType == "67" || inbondType == "68" || exportCodeVal == 'ZD' ) // || inbondType == "67" || inbondType == "68")
   {
     //document.getElementById("EntryNumber").disabled=false;
     document.getElementById("ForeignTradeZone").disabled=false;
   } 
   else
   {
    //document.getElementById("EntryNumber").disabled=true;
    document.getElementById("ForeignTradeZone").disabled=true;
   }
   
  
   return;  
}
function genOptions()
{

var anOption = document.createElement("OPTION") 
var Product = document.aascDHLINTLShipmentsForm.NumberOfPieces.value
var Unit = document.aascDHLINTLShipmentsForm.Quantity.value
var Weight = document.aascDHLINTLShipmentsForm.Weight.value
var Value = document.aascDHLINTLShipmentsForm.UnitPrice.value
var optionValue = document.aascDHLINTLShipmentsForm.commodityLine.length


//alert("Product"+Product);
//alert("optionValue"+optionValue);

if(Product == null || Product ==''||Product == 'null' || Product ==' '){
document.aascDHLINTLShipmentsForm.NumberOfPieces.focus();
}else{
//document.aascDHLINTLShipmentsForm.commodityLine.options.add(anOption) 
//anOption.innerText =  " "+Product+"        "+Unit+"       "+Weight+"       "+Value;
//anOption.Value = "3"//optionValue + 1 
document.forms['aascDHLINTLShipmentsForm'].commodityLine.options[optionValue] =  new Option(" "+Product+"        "+Unit+"       "+Weight+"       "+Value,optionValue + 1,true,false);
//alert("anOption.Value"+anOption.Value);

document.aascDHLINTLShipmentsForm.NumberOfPieces.value = "";
document.aascDHLINTLShipmentsForm.Quantity.value = "";
document.aascDHLINTLShipmentsForm.Weight.value = "";
document.aascDHLINTLShipmentsForm.UnitPrice.value = "";
document.aascDHLINTLShipmentsForm.description.value = "";
document.aascDHLINTLShipmentsForm.CountryOfManufacture.value = "";
document.aascDHLINTLShipmentsForm.HarmonizedCode.value = "";
document.aascDHLINTLShipmentsForm.QuantityUnits.value = "";
document.aascDHLINTLShipmentsForm.CustomsValue.value = "";
document.aascDHLINTLShipmentsForm.ExportLicenseNumber.value = "";
document.aascDHLINTLShipmentsForm.ExportLicenseExpirationDate.value = "";

}
}

function editOptions()
{
    
    var form = document.aascDHLINTLShipmentsForm.commodityLine;

    for (var i=0; i<form.options.length; i++){
       if (form.options[i].selected==true){
          var value = form.options[document.aascDHLINTLShipmentsForm.commodityLine.selectedIndex].value; // khaja add this line for bug fix 1129    
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
    document.aascDHLINTLShipmentsForm.opValue.value = value;
    enableCommodityDetailDiv();
    document.aascDHLINTLShipmentsForm.actionType.value='EDIT';
    document.aascDHLINTLShipmentsForm.submit();
}

function delOptions()
{

var form = document.aascDHLINTLShipmentsForm.commodityLine;

for (var i=0; i<form.options.length; i++){
 if (form.options[i].selected==true){
  var value = form.options[document.aascDHLINTLShipmentsForm.commodityLine.selectedIndex].value; // khaja add this line for bug fix 1129    
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

document.aascDHLINTLShipmentsForm.actionType.value='DELETE';
document.aascDHLINTLShipmentsForm.submit();
}

function onChange()
{
document.aascDHLINTLShipmentsForm.AccNumber.value = "";
document.aascDHLINTLShipmentsForm.countryCode.value = "";
}


function getAdhocFlag() // added by sambit on 10/06/2008
{
    if(window.opener.document.DynaShipmentShipSaveForm.shipmentType.value == 'adhoc')
     {
       return true;
     }
    else if (window.opener.document.DynaShipmentShipSaveForm.shipmentType.value == 'normal')
     {
       return false;
     }
    return true;
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
    var currentCommItem = document.aascDHLINTLShipmentsForm.selCommItems.value;
    var shipFlag = document.aascDHLINTLShipmentsForm.shipFlag.value;
    var licenseType = 'licenseType_'+1;
    var exportCode = 'exportCode_'+1;
    var commodityCode = 'commodityCode_'+1;
    if(currentCommItem == 'Select')
    {
       disableCommodityDetailDiv();
       document.aascDHLINTLShipmentsForm.CountryOfManufacture.value="US";
       document.aascDHLINTLShipmentsForm.CustomsValue.value="";
       document.aascDHLINTLShipmentsForm.NumberOfPieces.value="1";
       document.aascDHLINTLShipmentsForm.Quantity.value="";
       document.aascDHLINTLShipmentsForm.QuantityUnits.value="";
       document.aascDHLINTLShipmentsForm.UnitPrice.value="";
       document.aascDHLINTLShipmentsForm.description.value="";
       document.aascDHLINTLShipmentsForm.Weight.value="";
       document.aascDHLINTLShipmentsForm.HarmonizedCode.value="";
       document.aascDHLINTLShipmentsForm.ExportLicenseNumber.value="";
       document.aascDHLINTLShipmentsForm.ExportLicenseExpirationDate.value="";
       document.aascDHLINTLShipmentsForm.addComm.disabled=true;
//       document.getElementById(licenseType).value='NotSelected';
//       document.getElementById(exportCode).value='NotSelected';
//       document.getElementById(commodityCode).value='';
    }
    else if(currentCommItem == 'Create'){
       enableCommodityDetailDiv();
       
       document.aascDHLINTLShipmentsForm.CountryOfManufacture.value="US";
       document.aascDHLINTLShipmentsForm.CustomsValue.value="";
       document.aascDHLINTLShipmentsForm.NumberOfPieces.value="1";
       document.aascDHLINTLShipmentsForm.Quantity.value="";
       document.aascDHLINTLShipmentsForm.QuantityUnits.value="";
       document.aascDHLINTLShipmentsForm.UnitPrice.value="";
       document.aascDHLINTLShipmentsForm.description.value="";
       document.aascDHLINTLShipmentsForm.Weight.value="";
       document.aascDHLINTLShipmentsForm.HarmonizedCode.value="";
       document.aascDHLINTLShipmentsForm.ExportLicenseNumber.value="";
       document.aascDHLINTLShipmentsForm.ExportLicenseExpirationDate.value="";
//       document.getElementById(licenseType).value='NotSelected';
//       document.getElementById(exportCode).value='NotSelected';
//       document.getElementById(commodityCode).value='';
       
       if(shipFlag == 'Y'){
        document.aascDHLINTLShipmentsForm.addComm.disabled=true;
       }else{
        document.aascDHLINTLShipmentsForm.addComm.disabled=false;
       }
    }
    else {
        if(shipFlag == 'Y'){
        document.aascDHLINTLShipmentsForm.addComm.disabled=true;
       }else{
        document.aascDHLINTLShipmentsForm.addComm.disabled=false;
       }        enableCommodityDetailDiv();
        
        getAjaxInlCommodityDetails();
    }
}

function getAjaxInlCommodityDetails(){
   
    var currentCommItem=document.aascDHLINTLShipmentsForm.selCommItems.options[document.aascDHLINTLShipmentsForm.selCommItems.selectedIndex].text;
    
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
          var lineNumber = 1;
          var responseStringDummy=xmlHttp.responseText;
          
          startIndex  = responseStringDummy.indexOf('*');
          responseStringDummy=responseStringDummy.substring(startIndex+1);
           
          startIndex  = responseStringDummy.indexOf('*');
          countryOfManufacture     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.CountryOfManufacture.value=countryOfManufacture;
          
          startIndex  = responseStringDummy.indexOf('*');
          customsValue     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.CustomsValue.value=customsValue;
          
          startIndex  = responseStringDummy.indexOf('*');
          numberOfPieces     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.NumberOfPieces.value=numberOfPieces;
          
          startIndex  = responseStringDummy.indexOf('*');
          quantity     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.Quantity.value=quantity;
          
          startIndex  = responseStringDummy.indexOf('*');
          quantityUnits     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.QuantityUnits.value=quantityUnits;
          
          if(document.aascDHLINTLShipmentsForm.QuantityUnits.value == "")
          {
            document.aascDHLINTLShipmentsForm.QuantityUnits.value = "";
          
          }
          
          startIndex  = responseStringDummy.indexOf('*');
          unitprice     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.UnitPrice.value=unitprice;
          
          startIndex  = responseStringDummy.indexOf('*');
          description     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.description.value=description;
                             
          startIndex  = responseStringDummy.indexOf('*');
          weight     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.Weight.value=weight;
          
          startIndex  = responseStringDummy.indexOf('*');
          harmonizedCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.HarmonizedCode.value=harmonizedCode;
          
          startIndex  = responseStringDummy.indexOf('*');
          expLicenseNo     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.ExportLicenseNumber.value=expLicenseNo;
          
          startIndex  = responseStringDummy.indexOf('*');
          exportLicenseExpirationDate = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.ExportLicenseExpirationDate.value=exportLicenseExpirationDate;
          
          calcUnitPrice();
       }
    } 
    //Shiva added below code to encode special charecters for (# special charecter) 
    //in mozilla firef0x browser for bug fix #2293
    var currentItemtemp=encodeURIComponent(currentCommItem);
    var locationId = document.getElementById("locationId").value;
    var url="aascAjaxIntlCommodityDetail.jsp?currentItem="+currentItemtemp+"&locationId="+locationId;
    //Shiva code end
    xmlHttp.open("POST",url,true);  // Calling 
//    xmlHttp.setRequestHeader("Cache-Control","no-cache");
//    xmlHttp.setRequestHeader("Pragma","no-cache");
//    xmlHttp.setRequestHeader("If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT");
    xmlHttp.send(null);  
}

function viewPrinted()
{
//alert("Entered viewPrint()");
document.aascDHLINTLShipmentsForm.actionType.value='VIEWPRINT';
document.aascDHLINTLShipmentsForm.submit();
} 

function getIntlImporterDetails(){
    var currentImporter = document.aascDHLINTLShipmentsForm.selImporterName.value;
    if(currentImporter == 'Select')
    {
       document.aascDHLINTLShipmentsForm.importerName.value="";
       document.aascDHLINTLShipmentsForm.importerCompName.value="";
       document.aascDHLINTLShipmentsForm.importerPhoneNum.value="";
       document.aascDHLINTLShipmentsForm.importerAddress1.value="";
       document.aascDHLINTLShipmentsForm.importerAddress2.value="";
       document.aascDHLINTLShipmentsForm.importerCity.value="";
       document.aascDHLINTLShipmentsForm.importerState.value="";
       document.aascDHLINTLShipmentsForm.importerPostalCode.value="";
       document.aascDHLINTLShipmentsForm.importerCountryCode.value="";
       document.aascDHLINTLShipmentsForm.ImporterTINOrDUNS.value="";
       document.aascDHLINTLShipmentsForm.ImporterTINOrDUNSType.value="";
    }
  
    else {
        getAjaxInlImporterDetail();
    }
}

function getAjaxInlImporterDetail(){
    var currentImporter = document.aascDHLINTLShipmentsForm.selImporterName.value;
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
          document.aascDHLINTLShipmentsForm.ImporterTINOrDUNS.value=ImporterTINOrDUNS;
          
          startIndex  = responseStringDummy.indexOf('*');
          ImporterTINOrDUNSType     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.ImporterTINOrDUNSType.value=ImporterTINOrDUNSType;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerAddress1     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.importerAddress1.value=importerAddress1;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerAddress2     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.importerAddress2.value=importerAddress2;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCity     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.importerCity.value=importerCity;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCompName     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.importerCompName.value=importerCompName;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCountryCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.importerCountryCode.value=importerCountryCode;
                             
          startIndex  = responseStringDummy.indexOf('*');
          importerPhoneNum     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.importerPhoneNum.value=importerPhoneNum;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerPostalCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.importerPostalCode.value=importerPostalCode;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerState     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.importerState.value=importerState;
          
          importerName = responseStringDummy;
          document.aascDHLINTLShipmentsForm.importerName.value=importerName;
       }
    }
    var url="aascAjaxIntlImporterDetail.jsp?currentImporter="+currentImporter;
    xmlHttp.open("POST",url,true);  // Calling 
    xmlHttp.send(null);  
}

function viewDoc()
{

    popupWindow = window.open('aascIntlDocViewPrint.jsp','_blank');
    popupWindow.focus();
}

function changeCI(){
    var checkedCI = document.aascDHLINTLShipmentsForm.generateCI.checked;
    if(checkedCI){
    document.aascDHLINTLShipmentsForm.generateCI.value='Y';
    }else{
        document.aascDHLINTLShipmentsForm.generateCI.value='N';
    }
}

function calcUnitPrice(){
   var lineCustomValue = document.aascDHLINTLShipmentsForm.CustomsValue.value;
//   alert("lineCustomValue"+lineCustomValue);
   var quantity = document.aascDHLINTLShipmentsForm.Quantity.value;
//   alert("quantity"+quantity);
   var unitPrice = lineCustomValue/quantity;
//   alert("unitPrice"+unitPrice);
   document.aascDHLINTLShipmentsForm.UnitPrice.value = unitPrice;
}

function getIntlBrokerDetails(){
    var currentBroker = document.aascDHLINTLShipmentsForm.selBrokerName.value;
    if(currentBroker == 'Select')
    {
       document.aascDHLINTLShipmentsForm.brokerName.value="";
       document.aascDHLINTLShipmentsForm.brokerCompName.value="";
       document.aascDHLINTLShipmentsForm.brokerPhoneNum.value="";
       document.aascDHLINTLShipmentsForm.brokerAddress1.value="";
       document.aascDHLINTLShipmentsForm.brokerAddress2.value="";
       document.aascDHLINTLShipmentsForm.brokerCity.value="";
       document.aascDHLINTLShipmentsForm.brokerState.value="";
       document.aascDHLINTLShipmentsForm.brokerPostalCode.value="";
       document.aascDHLINTLShipmentsForm.brokerCountryCode.value="";      
    }
  
    else {
        getAjaxInlBrokerDetail();
    }
}

function getAjaxInlBrokerDetail(){
    var currentBroker = document.aascDHLINTLShipmentsForm.selBrokerName.value;
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
          document.aascDHLINTLShipmentsForm.brokerAddress1.value=brokerAddress1;
          
          startIndex  = responseStringDummy.indexOf('*');
          brokerAddress2     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.brokerAddress2.value=brokerAddress2;
          
          startIndex  = responseStringDummy.indexOf('*');
          brokerCity     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.brokerCity.value=brokerCity;
          
          startIndex  = responseStringDummy.indexOf('*');
          brokerCompName     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.brokerCompName.value=brokerCompName;
          
          startIndex  = responseStringDummy.indexOf('*');
          brokerCountryCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.brokerCountryCode.value=brokerCountryCode;
                             
          startIndex  = responseStringDummy.indexOf('*');
          brokerPhoneNum     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.brokerPhoneNum.value=brokerPhoneNum;
          
          startIndex  = responseStringDummy.indexOf('*');
          brokerPostalCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.brokerPostalCode.value=brokerPostalCode;
          
          startIndex  = responseStringDummy.indexOf('*');
          brokerState     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.brokerState.value=brokerState;
          
          brokerName = responseStringDummy;
          document.aascDHLINTLShipmentsForm.brokerName.value=brokerName;
       }
    }
    var url="aascAjaxIntlBrokerDetail.jsp?currentBroker="+currentBroker;
    xmlHttp.open("POST",url,true);  // Calling 
    xmlHttp.send(null);  
}

function getAesInterConsDetails(){
    var currentInterCons = document.aascDHLINTLShipmentsForm.selICName.value;
    if(currentInterCons == 'Select')
    {
       document.aascDHLINTLShipmentsForm.aesICIntermediateConsignee.value="";
       document.aascDHLINTLShipmentsForm.aesICName.value="";
       document.aascDHLINTLShipmentsForm.aesICLastName.value="";
       document.aascDHLINTLShipmentsForm.aesICCompanyName.value="";
       document.aascDHLINTLShipmentsForm.aesICAddressLine1.value="";
       document.aascDHLINTLShipmentsForm.aesICAddressLine2.value="";
       document.aascDHLINTLShipmentsForm.aesICCity.value="";
       document.aascDHLINTLShipmentsForm.aesICState.value="";
       document.aascDHLINTLShipmentsForm.aesICCountry.value="";
       document.aascDHLINTLShipmentsForm.aesICPhnNum.value="";
       document.aascDHLINTLShipmentsForm.aesICPostalCode.value="";      
    }
  
   
}

function getAjaxAesInerConsDetail(){
    var currentInterCons = document.aascDHLINTLShipmentsForm.selICName.value;
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
          aesICIntermediateConsignee     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.aesICIntermediateConsignee.value=aesICIntermediateConsignee;
          
          startIndex  = responseStringDummy.indexOf('*');
          aesICLastName     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.aesICLastName.value=aesICLastName;
          
           startIndex  = responseStringDummy.indexOf('*');
          aesICCompanyName     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.aesICCompanyName.value=aesICCompanyName;
          
          startIndex  = responseStringDummy.indexOf('*');
          aesICAddressLine1     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.aesICAddressLine1.value=aesICAddressLine1;
          
          startIndex  = responseStringDummy.indexOf('*');
          aesICAddressLine2     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.aesICAddressLine2.value=aesICAddressLine2;
          
          startIndex  = responseStringDummy.indexOf('*');
          aesICCity     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.aesICCity.value=aesICCity;
          
          startIndex  = responseStringDummy.indexOf('*');
          aesICState     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.aesICState.value=aesICState;
                             
          startIndex  = responseStringDummy.indexOf('*');
          aesICCountry     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.aesICCountry.value=aesICCountry;
          
          startIndex  = responseStringDummy.indexOf('*');
          aesICPhnNum     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.aesICPhnNum.value=aesICPhnNum;
          
          startIndex  = responseStringDummy.indexOf('*');
          aesICPostalCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascDHLINTLShipmentsForm.aesICPostalCode.value=aesICPostalCode;
          
          
          aesICName = responseStringDummy;
          document.aascDHLINTLShipmentsForm.aesICName.value=aesICName;
          
       }
    }
    var url="aascAjaxAesInterConsDetail.jsp?currentInterCons="+currentInterCons;
    xmlHttp.open("POST",url,true);  // Calling 
    xmlHttp.send(null);  
}


function enableCommodityDetailDiv(){
 document.getElementById('commodityDetailDiv').style.display ="";

}

function disableCommodityDetailDiv(){
  document.getElementById('commodityDetailDiv').style.display ="none";

}


function isSpclChar(variable){
    //alert(variable);
    var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\:<>?";
    
    for (var i = 0; i < variable.length; i++) {
        if (iChars.indexOf(variable.charAt(i)) != -1) {
            return true;
        }
    }
}

function enableAESTable(lineNumber)
{
    if(document.getElementById('AESLineItems').checked)
    {
      document.getElementById('AESLineItems').value = 'Y';
    }
    else
    {
      document.getElementById('AESLineItems').value = 'N';
    }
    var comodityAdded = document.getElementById("aesReqdTableFlag").value;
    var eccn = 'eccn_'+lineNumber;//ECCN 
    if(document.getElementById('AESLineItems').checked)
    {
            document.getElementById('commodityDetailDiv2').style.display ="";
            //document.getElementById(eccn).disabled=false;//for eccn field
            document.getElementById('AESDirect').style.display ="";
            document.getElementById('AESButtonPng').style.display ="";
    }
    else
    {
        document.getElementById('commodityDetailDiv2').style.display ="none";
        var AesDb = document.getElementById('AesDb').value;
        if((comodityAdded == "" || comodityAdded == "NO") && (AesDb == 'Y' || AesDb == 'y'))
        {
          document.getElementById('AESDirect').style.display ="none";
          document.getElementById('AESButtonPng').style.display ="none";
        }
        //document.getElementById(eccn).disabled=true;//for eccn field
    }
}


function enableFields(lineNumber)
{
    var eccn = 'eccn_'+lineNumber;

    
    //alert("document.getElementById(aesAt).value" +document.getElementById("aesAt").value);
    //var form = document.getElementById("selCommItems");
    //var value = form.options[form.selectedIndex].value;
    var value = document.getElementById("selCommItems").options[document.getElementById("selCommItems").selectedIndex].value;
    //alert(" value" +value);
    if(value == "Select" || value == "Create")
    {
      //do nothing, not enabling the aes line items
    }
    
}

function submitSED()
{
   document.getElementById('SEDForm').submit();
}
function showTip(oSel,e) {
//alert("tooltip");
var evt=e?e:window.event;
	var theTip = document.getElementById("tooltip");
	theTip.style.top = evt.clientY + 20;
	theTip.style.left = evt.clientX;
        
       if(navigator.appName !='Microsoft Internet Explorer')
        {
        theTip.textContent = oSel.options[oSel.selectedIndex].text;
        //alert("if"+oSel.options[oSel.selectedIndex].text);
        }
        else{
        theTip.innerText = oSel.options[oSel.selectedIndex].text; 
        //alert("else"+oSel.options[oSel.selectedIndex].text);
        }

	theTip.style.visibility = "visible";
	}

function hideTip() {
	document.getElementById("tooltip").style.visibility = "hidden";
}

function checkSaveDetails()
{
   
    var lineCustomValue = document.aascDHLINTLShipmentsForm.commCustomValue.value;
     if(lineCustomValue == '0.0')
     {
       alert(" Please enter atleast one Commodity ");
       return false;
     }

        saveDetails();
}

function defaultValue()
{
    var lineNumber = 1;
    var licenseType = 'licenseType_'+lineNumber;
    var DdtcQty = 'DdtcQty_'+lineNumber;
    var DdtcItar = 'DdtcItar_'+lineNumber;
    var DdtcReg  = 'DdtcReg_'+lineNumber;
    var DdtcSignif = 'DdtcSignif_'+lineNumber;
    var DdtcEligible = 'DdtcEligible_'+lineNumber;
    var DdtcUsml = 'DdtcUsml_'+lineNumber;
    var DdtcUnit = 'DdtcUnit_'+lineNumber;
    var value = document.getElementById(licenseType).options[document.getElementById(licenseType).selectedIndex].value; 
    //alert('ddtc value : '+ value);
        if(!(value == 'SAG' || value == 'SCA' || value == 'S00' || value == 'S05' || value == 'S61' || value == 'S73' || value == 'S85' || value == 'S94'))
        {
           document.getElementById(DdtcReg).value = '';//DDTC Registration Number
           document.getElementById(DdtcSignif).value = 'N';//DDTC Significant Military Equipment Indicator
           document.getElementById(DdtcUsml).value = 'NotSelected';//DDTC USML Category Code
           document.getElementById(DdtcUnit).value = 'NotSelected';//DDTC Unit of Measure Code
           document.getElementById(DdtcQty).value = '';;//DDTC Quantity
        }
        if(!(value == 'SCA' || value == 'S00' ))
        {
            document.getElementById(DdtcEligible).value = 'N';//DDTC Eligible Party Cert. Indicator
        }
        if(!(value == 'SAG' || value == 'SCA' || value == 'S00'))
        {
            document.getElementById(DdtcItar).value = 'NotSelected';//DDTC ITAR Exemption
        }       
}


function defaultVehicleValue(lineNumber)
{
    
    var usedVehicle = 'usedVehicle_'+lineNumber;
    var vehicleIDType = 'vehicleIDType_'+lineNumber; 
    var productID = 'productID_'+lineNumber;
    var vehicleTitleNo = 'vehicleTitleNo_'+lineNumber;
    var vehicleTitleState = 'vehicleTitleState_'+lineNumber;
    
    var vehicleFlag = document.getElementById(usedVehicle).options[document.getElementById(usedVehicle).selectedIndex].value;
    if(vehicleFlag != 'Y')
    {
         document.getElementById(vehicleIDType).value='V';
         document.getElementById(productID).value='';
         document.getElementById(vehicleTitleNo).value='';
         document.getElementById(vehicleTitleState).value='NotSelected';
    }      
}


function onChangeAlert()
{
  
}

// Validates that the input string is a valid date formatted as "mm/dd/yy"    
function isValidDate(dateString)
{
    // First check for the pattern
    if(!/^\d{2}\/\d{2}\/\d{2}$/.test(dateString))
      return false;

    // Parse the date parts to integers
    var parts = dateString.split("/");
    var day = parseInt(parts[1], 10);
    var month = parseInt(parts[0], 10);
    var year = parseInt(parts[2], 10);
    
    // Check the ranges of month and year
    if(year < 13 || year > 99 || month == 0 || month > 12)
      return false;
    
    var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
    
    // Adjust for leap years
    if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
      monthLength[1] = 29;

    // Check the range of the day
    if(!(day > 0 && day <= monthLength[month - 1]))
    {
    alert("Date is in inappropriate format");
    document.getElementById('SExportDate').focus();
    return false;
    }
    
    //yy-mm-dd
    var currentDate = new Date();
     var day2 = currentDate.getDate();
    var month2 = currentDate.getMonth() + 1;
    var year2 = currentDate.getFullYear();

    var one_day=1000*60*60*24;  // calculation of number of milliseconds per day
    var date2=new Date(year+2000,month-1,day);
    var date1=new Date(year2,month2-1,day2);
    
    _Diff=Math.ceil((date2.getTime()-date1.getTime())/(one_day));
        if(_Diff > 120)
        {
       alert("Departure date can't be more than 120 days in future"); 
       document.getElementById('SExportDate').focus();
        return false;
        }
        return true;
}
 //Mahesh Intermediate Consignee
 
 
function getEnabled(){

if(document.getElementById('aesICIntermediateConsignee').checked == true){
document.getElementById('aesICIntermediateConsignee').value = 'Y';
document.getElementById('aesIntermediateConsigneeTableID').style.display = '';

}

else{
document.getElementById('aesICIntermediateConsignee').value = 'N';
document.getElementById('aesIntermediateConsigneeTableID').style.display = 'none';



}
}

