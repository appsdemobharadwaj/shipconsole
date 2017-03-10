/**
   
   20/11/2014     Eshwari    Added this file from cloud 1.2 version
   06/12/2015     Y Pradeep  Merged Sunanda's code : Added comments.
   
*/

function trim(str){
  return str.replace(/^\s*|\s*$/g,"");
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

//This function is for validating the dimension details while loading values into Dimension Details page
function setValues()
{
    var packCount=document.PackageDimensionsForm.packCount.value;
    //alert("setValues"+packCount);

    var dimensionId=trim(document.PackageDimensionsForm.packageDimensionDimId.value);
    var dimensionName=trim(document.PackageDimensionsForm.packageDimensionName.value);
        
    var dimensionLength=trim(document.PackageDimensionsForm.packageDimensionLenght.value);
    var dimensionWidth=trim(document.PackageDimensionsForm.packageDimensionWidth.value);
    var dimensionHeight=trim(document.PackageDimensionsForm.packageDimensionHeight.value);
    
    var dimensionUnits=trim(document.PackageDimensionsForm.packageDimensionUnits.value);
    
    if(dimensionLength == ""){
        alert("Please enter Dimension Length");
        document.PackageDimensionsForm.packageDimensionLenght.focus();     
        return false;     
    }
    
    
    if(dimensionWidth == ""){
        alert("Please enter Dimension Width");
        document.PackageDimensionsForm.packageDimensionWidth.focus();
        return false;
    }
    
    if(dimensionHeight == ""){
        alert("Please enter Dimension Height");
        document.PackageDimensionsForm.packageDimensionHeight.focus();
        return false;
    }
    
    if(dimensionUnits == ""){
        alert("Please Select Dimension Units");
        document.PackageDimensionsForm.packageDimensionUnits.focus();
        return false;
    }
    //Suman Gunda added code for  #2285
    var carrierTest=window.opener.document.getElementById("ajaxCarrierCode").value;
    //    alert("dimensionUnits "+dimensionUnits+"  carrierTest "+carrierTest);
    if(carrierTest != 113 && dimensionUnits == 'M' )
    {
        alert("Selected carrier does not support units in meters");
        document.PackageDimensionsForm.packageDimensionUnits.focus();
        return false;
    }
    if(dimensionLength > 0 && dimensionWidth ==0)
    {
      alert("Please enter valid data for Width");
      document.PackageDimensionsForm.packageDimensionWidth.focus();
      return false;
    }
    if(dimensionLength > 0 && dimensionHeight ==0)
    {
      alert("Please enter valid data for Height");
      document.PackageDimensionsForm.packageDimensionHeight.focus();
      return false;
    }
              
    if(dimensionWidth > 0 && dimensionLength ==0)
    {
      alert("Please enter valid data for Length");
      document.PackageDimensionsForm.packageDimensionLenght.focus();
      return false;
    }
    if(dimensionWidth > 0 && dimensionHeight ==0)
    {
      alert("Please enter valid data for Height");
      document.PackageDimensionsForm.packageDimensionHeight.focus();
      return false;
    }
      
    if(dimensionHeight > 0 && dimensionLength ==0)
    {
      alert("Please enter valid data for Length");
      document.PackageDimensionsForm.packageDimensionLenght.focus();
      return false;
    }
    if(dimensionHeight > 0 && dimensionWidth ==0)
    {
      alert("Please enter valid data for Width");
      document.PackageDimensionsForm.packageDimensionWidth.focus();
      return false;
    }
    if(dimensionLength>999999999)
    {
       alert("Length should not be greater than 999999999");
       document.PackageDimensionsForm.packageDimensionLenght.focus();
       return false;
    }
    if(isInteger(document.PackageDimensionsForm.packageDimensionLenght.value)==false)
    {	
         alert("Enter Numeric Value for Length");
         document.PackageDimensionsForm.packageDimensionLenght.focus();
         return false;
    }

    if(dimensionWidth>999999999)
    {
       alert("Width should not be greater than 999999999");
       document.PackageDimensionsForm.packageDimensionWidth.focus();
       return false;
    }

    if(isInteger(document.PackageDimensionsForm.packageDimensionWidth.value)==false)
    {	
         alert("Enter Numeric Value for Width");
         document.PackageDimensionsForm.packageDimensionWidth.focus();
         return false;
    }
    if(dimensionHeight>999999999)
    {
       alert("Height should not be greater than 999999999");
       document.PackageDimensionsForm.packageDimensionHeight.focus();
       return false;
    }
     
    if(isInteger(document.PackageDimensionsForm.packageDimensionHeight.value)==false)
    {	
         alert("Enter Numeric Value for Height");
         document.PackageDimensionsForm.packageDimensionHeight.focus();
         return false;
    }
    
    var dimensionIdStr=dimensionLength+"*"+dimensionWidth+"*"+dimensionHeight+"*"+dimensionUnits+"*"+dimensionId;
 
    var nameLength=parseInt(window.opener.document.getElementById('dimensionNameID'+packCount).length);
    for(var i=0; i<nameLength; i++)
    {
      if(window.opener.document.getElementById('dimensionNameID'+packCount).options[i].text == 'Other')
      {
          window.opener.document.getElementById('dimensionNameID'+packCount).options[i].value=dimensionIdStr;
          window.opener.document.getElementById('dimensionNameID'+packCount).options[i].selected = true;
      }
    }
    window.close();
    return true;
}

//this function is to load the package dimension details in enable edit and disable edit format
function loadValues()
{
    //alert("loadValues");
    var dimensionName=document.PackageDimensionsForm.packageDimensionName.value;
    if(dimensionName != "Other"){
        document.PackageDimensionsForm.packageDimensionLenght.readOnly=true;
        document.PackageDimensionsForm.packageDimensionWidth.readOnly=true;
        document.PackageDimensionsForm.packageDimensionHeight.readOnly=true;
        document.PackageDimensionsForm.packageDimensionUnits.disabled=true;
    
    }else{
        var packCount=document.PackageDimensionsForm.packCount.value;
        
        var trackingNumber=window.opener.document.getElementById('trackingNumberID'+packCount).value;
        if((trackingNumber == null || trackingNumber == "") ){
        document.PackageDimensionsForm.packageDimensionLenght.readOnly=false;
        document.PackageDimensionsForm.packageDimensionWidth.readOnly=false;
        document.PackageDimensionsForm.packageDimensionHeight.readOnly=false;
        document.PackageDimensionsForm.packageDimensionUnits.disabled=false;
        
        document.getElementById("NewButton").disabled=false;
        }else{
        document.PackageDimensionsForm.packageDimensionLenght.readOnly=true;
        document.PackageDimensionsForm.packageDimensionWidth.readOnly=true;
        document.PackageDimensionsForm.packageDimensionHeight.readOnly=true;
        document.PackageDimensionsForm.packageDimensionUnits.disabled=true;
        
        document.getElementById("NewButton").disabled=true;
        }
    }
}
