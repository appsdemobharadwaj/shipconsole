/*==========================================================================+
|  DESCRIPTION                                                              |
|    javascript file for the aascUPSDirectPkgingOptions.jsp  validation     |
|    Author Vandana Gangisetty                                              |
|    Version   1                                                            |                                                                            
|    Creation 10-May-2007                                                   |
|    History                                                                |
|          -17/05/07 Vandana Added code to retreive package surcharge       |
|          -21/05/07 Vandana Moved Surcharge Read Only                      |
|          -14/06/07 Vandana Added code to save cod and cod currency code   |
|                            and also added validations.                    |
|          -18/06/07 Vandana Moved cod amt and signature option validations |
|                            from onBlur event to save button onClick event.|
|          -19/06/07 Vandana Added code to show adult signature required    |
|		                     option by default when cod is checked              | 
           -19/09/07 Dedeepya Added code to give alert when cod amount is 
                              less than one.  
           -20/11/14 Eshwari  Copied this file from cloud 1.2 version       
           -05/05/15 Suman G  Added if condition for handling the save, cancel and close buttons before ship and after ship.
+===========================================================================*/
function loadPackageOptions()
{
      
      
      if(window.opener.document.getElementById('flagShip1').value == 'Y'){
        document.getElementById('b4Ship').style.display = "none";
        document.getElementById('afterShip').style.display = "";
      }
      else{
        document.getElementById('afterShip').style.display = "none";
        document.getElementById('b4Ship').style.display = "";
      }
      var packCount;
      packCount=document.aascUpsPackageOptionsForm.packageCount.value;
      var pkging;
      var codType;
      var codFundsCode;   
      var codCurrCode;
      var delConfirm;      
      var length;
        
      if(window.opener.document.getElementById('shipMethodId').disabled==true)
      {
       document.aascUpsPackageOptionsForm.upsPackaging.disabled=true;
       document.aascUpsPackageOptionsForm.upsCodCode.disabled=true;
       document.aascUpsPackageOptionsForm.upsCodFundsCode.disabled=true;
       document.aascUpsPackageOptionsForm.upsCodCurrCode.disabled=true;  
       document.aascUpsPackageOptionsForm.upsDelConfirm.disabled=true;      
       document.aascUpsPackageOptionsForm.upsCodCheckBox.disabled=true;
       document.aascUpsPackageOptionsForm.upsCodAmt.readOnly=true;       
       document.aascUpsPackageOptionsForm.pkgSurCharge.value=window.opener.document.getElementById('upsSurChargeID'+packCount).value; 
      }
      else
      {              
         if(window.opener.document.getElementById('chCODID'+packCount).value!="Y")
         {
             document.aascUpsPackageOptionsForm.codTempFlag.value="N";
             document.aascUpsPackageOptionsForm.upsCodCode.disabled = true;
             document.aascUpsPackageOptionsForm.upsCodFundsCode.disabled = true;
             document.aascUpsPackageOptionsForm.upsCodCurrCode.disabled =true;
             document.aascUpsPackageOptionsForm.upsCodAmt.readOnly=true;
             document.aascUpsPackageOptionsForm.upsCodCheckBox.checked=false;              
         }
         else if(window.opener.document.getElementById('chCODID'+packCount).value=="Y")
         {
             document.aascUpsPackageOptionsForm.codTempFlag.value="Y";
             document.aascUpsPackageOptionsForm.upsCodCode.disabled = false;
             document.aascUpsPackageOptionsForm.upsCodFundsCode.disabled = false;
             document.aascUpsPackageOptionsForm.upsCodCurrCode.disabled =false;
             document.aascUpsPackageOptionsForm.upsCodAmt.readOnly=false;
             document.aascUpsPackageOptionsForm.upsCodCheckBox.checked=true;        
         }
      }
     
      if(window.opener.document.getElementById('chCODID'+packCount).value=="Y")
      {
         codType=window.opener.document.getElementById('codTypeID'+packCount).value;
         codFundsCode=window.opener.document.getElementById('codFundsCodeID'+packCount).value;
         codCurrCode=window.opener.document.getElementById('codCurrCodeID'+packCount).value;                  
         
         length=document.aascUpsPackageOptionsForm.upsCodCode.length;
         for(var i=0; i<length; i++)
         {
            if(document.aascUpsPackageOptionsForm.upsCodCode.options[i].value==codType)
            {                     
             document.aascUpsPackageOptionsForm.upsCodCode.options[i].selected=true;                     
            }
         }
         
         length=document.aascUpsPackageOptionsForm.upsCodFundsCode.length;
         for(var i=0; i<length; i++)
         {
            if(document.aascUpsPackageOptionsForm.upsCodFundsCode.options[i].value==codFundsCode)
            {                  
               document.aascUpsPackageOptionsForm.upsCodFundsCode.options[i].selected=true;                  
            }
         }
                 
         length=document.aascUpsPackageOptionsForm.upsCodCurrCode.length;
         for(var i=0; i<length; i++)
         {
            if(document.aascUpsPackageOptionsForm.upsCodCurrCode.options[i].value==codCurrCode)
            {                  
              document.aascUpsPackageOptionsForm.upsCodCurrCode.options[i].selected=true;                   
            }
         }
      }
       
      pkging=window.opener.document.getElementById('pkgingID'+packCount).value;       
      delConfirm=window.opener.document.getElementById('delConfirmID'+packCount).value;    
                    
      length=document.aascUpsPackageOptionsForm.upsPackaging.length;
      for(var i=0; i<length; i++)
      {
        if(document.aascUpsPackageOptionsForm.upsPackaging.options[i].value==pkging)
        {
           document.aascUpsPackageOptionsForm.upsPackaging.options[i].selected=true;
        }
      }
        
      length=document.aascUpsPackageOptionsForm.upsDelConfirm.length;
      for(var i=0; i<length; i++)
      {
        if(document.aascUpsPackageOptionsForm.upsDelConfirm.options[i].value==delConfirm)
        {
           document.aascUpsPackageOptionsForm.upsDelConfirm.options[i].selected=true;
        }
      }
      
      document.aascUpsPackageOptionsForm.pkgSurCharge.readOnly=true;  
      document.aascUpsPackageOptionsForm.upsCodAmt.value=window.opener.document.getElementById('codAmtID'+packCount).value;
      
}//end of the loadPackageOptions()

function checkBoxFunc()
{
    if(document.aascUpsPackageOptionsForm.upsCodCheckBox.checked==true)
    {
     document.aascUpsPackageOptionsForm.codTempFlag.value="Y";
     document.aascUpsPackageOptionsForm.upsCodCode.disabled = false;
     document.aascUpsPackageOptionsForm.upsCodFundsCode.disabled = false;
     document.aascUpsPackageOptionsForm.upsCodCurrCode.disabled =false;     
     document.aascUpsPackageOptionsForm.upsCodAmt.readOnly=false;
     document.aascUpsPackageOptionsForm.upsCodAmt.focus();
     var length=parseInt(document.aascUpsPackageOptionsForm.upsDelConfirm.length);
     for(i=0; i<length; i++)
     {
        if(document.aascUpsPackageOptionsForm.upsDelConfirm.options[i].value=="3")
        {
           document.aascUpsPackageOptionsForm.upsDelConfirm.options[i].selected=true;
        }
     }
    }
    else
    {
     document.aascUpsPackageOptionsForm.codTempFlag.value="N";
     document.aascUpsPackageOptionsForm.upsCodCode.disabled = true;
     document.aascUpsPackageOptionsForm.upsCodFundsCode.disabled = true;
     document.aascUpsPackageOptionsForm.upsCodCurrCode.disabled =true;     
     document.aascUpsPackageOptionsForm.upsCodAmt.readOnly=true;
     document.aascUpsPackageOptionsForm.upsCodAmt.value="";
     
     var length=parseInt(document.aascUpsPackageOptionsForm.upsDelConfirm.length);
   
     for(i=0; i<length; i++)
     {
       if(document.aascUpsPackageOptionsForm.upsDelConfirm.options[i].value=="NA")
       {
         var adultIndex=i;
         document.aascUpsPackageOptionsForm.upsDelConfirm.options[i].selected=true;
       }
     }
    }
}//end of the function

function checkCodAmt()
{
var codAmt=document.aascUpsPackageOptionsForm.upsCodAmt.value;
var codFlag=document.aascUpsPackageOptionsForm.codTempFlag.value;

  if(isNaN(codAmt))
  {
       alert('Please Enter Only Digits In COD Amount Field.');
       document.aascUpsPackageOptionsForm.upsCodAmt.focus();
       return false;
  }
  
  if(codFlag == "Y" && codAmt<1)
  {
      alert("COD Amount Should be Greater than or Equal to 1");
      document.aascUpsPackageOptionsForm.upsCodAmt.focus();
      return false;
      
  }
  
  if(codFlag=="Y")
  {
     
     var length=parseInt(document.aascUpsPackageOptionsForm.upsDelConfirm.length);
     for(i=0; i<length; i++)
     {
      if(document.aascUpsPackageOptionsForm.upsDelConfirm.options[i].value=="3")
      {
       var adultIndex=i;
       document.aascUpsPackageOptionsForm.upsDelConfirm.options[i].selected=true;
      }
     }
     
     if(codAmt=="" || codAmt==null)
     {
      alert('Please Enter A Value In COD Amount Field.');
      document.aascUpsPackageOptionsForm.upsCodAmt.focus();
      return false;
     }
  }
}

function chkDeliveryConfirmation()
{
 var codFlag=document.aascUpsPackageOptionsForm.codTempFlag.value;
 var delConfirm=document.aascUpsPackageOptionsForm.upsDelConfirm.value; 
 delConfirm=parseInt(delConfirm);
 
   if(delConfirm!="3" && codFlag=="Y")
    {
     alert("Please Select --Adult Signature Required-- For Delivery Confirmation.");
     document.aascUpsPackageOptionsForm.upsDelConfirm.focus();
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
    packageCount=document.aascUpsPackageOptionsForm.packageCount.value;
    checkBoxVal=document.aascUpsPackageOptionsForm.codTempFlag.value;
    
    var upsPackaging=document.aascUpsPackageOptionsForm.upsPackaging.options[document.aascUpsPackageOptionsForm.upsPackaging.selectedIndex].value;    
    var upsDelConfirm=document.aascUpsPackageOptionsForm.upsDelConfirm.options[document.aascUpsPackageOptionsForm.upsDelConfirm.selectedIndex].value;
    length=parseInt(document.aascUpsPackageOptionsForm.upsPackaging.length);
    for(var i=0; i<length; i++)
    {
       if(document.aascUpsPackageOptionsForm.upsPackaging.options[i].value==upsPackaging)
       {
         document.aascUpsPackageOptionsForm.upsPackaging.options[i].selected=true;
         window.opener.document.getElementById('pkgingID'+packageCount).value=document.aascUpsPackageOptionsForm.upsPackaging.options[document.aascUpsPackageOptionsForm.upsPackaging.selectedIndex].value;
       }
    }
                  
 if(document.aascUpsPackageOptionsForm.upsPackaging.options[document.aascUpsPackageOptionsForm.upsPackaging.selectedIndex].text != 'Customer Supplied Package')
       {

       var nameLength=parseInt(window.opener.document.getElementById('dimensionNameID'+packageCount).length);
       for(var i=0; i<nameLength; i++)
       {
             if(window.opener.document.getElementById('dimensionNameID'+packageCount).options[i].text == 'Standard Dimension')
        {
          window.opener.document.getElementById('dimensionNameID'+packageCount).options[i].selected=true;
          window.opener.document.getElementById('dimButtonID'+packageCount).disabled=true;
        }
       }
         window.opener.document.getElementById('dimensionNameID'+packageCount).disabled = true;      
       }
       else
       {
       
       var nameLength=parseInt(window.opener.document.getElementById('dimensionNameID'+packageCount).length);
       
        var dimensionNameHidden=window.opener.document.getElementById('dimensionNameID'+packageCount).options[window.opener.document.getElementById('dimensionNameID'+packageCount).selectedIndex].text;           
      
      var dimensionValueHidden=window.opener.document.getElementById('dimensionNameID'+packageCount).options[window.opener.document.getElementById('dimensionNameID'+packageCount).selectedIndex].value;           
         
      var defaultDimeName=window.opener.document.getElementById('defaultDimeNameID'+packageCount).value;
 
        var selectedDimValue="";
        if (((dimensionNameHidden==defaultDimeName)&& (dimensionNameHidden!='Standard Dimension')) )
        {
        
          selectedDimValue=defaultDimeName;
        }
        else
        {
         
         if ((dimensionNameHidden=='Standard Dimension'))
         {
         selectedDimValue=defaultDimeName;
         }
         else
         {
           selectedDimValue =dimensionNameHidden;
         }
        }
        
        
       for(var i=0; i<nameLength; i++)
       {
       
       
        if(window.opener.document.getElementById('dimensionNameID'+packageCount).options[i].text == selectedDimValue)
        {
          window.opener.document.getElementById('dimensionNameID'+packageCount).options[i].selected=true;
          window.opener.document.getElementById('dimButtonID'+packageCount).disabled=false;
        }
       }
         window.opener.document.getElementById('dimensionNameID'+packageCount).disabled = false; 
       
       }
    
    
       
    if(chkDeliveryConfirmation()==false)
    {
      document.aascUpsPackageOptionsForm.upsDelConfirm.focus();
      return false;
    }
      
    if(checkBoxVal=="Y")
    {
      var upsCodCode=document.aascUpsPackageOptionsForm.upsCodCode.options[document.aascUpsPackageOptionsForm.upsCodCode.selectedIndex].value;
      var upsCodFundsCode=document.aascUpsPackageOptionsForm.upsCodFundsCode.options[document.aascUpsPackageOptionsForm.upsCodFundsCode.selectedIndex].value;
      var upsCodCurrCode=document.aascUpsPackageOptionsForm.upsCodCurrCode.options[document.aascUpsPackageOptionsForm.upsCodCurrCode.selectedIndex].value;
      
      var upsCodAmt= document.aascUpsPackageOptionsForm.upsCodAmt.value;

      if(upsCodAmt>=1)
      {
          window.opener.document.getElementById('chCODID'+packageCount).value="Y";
      }
      else
      {
          window.opener.document.getElementById('chCODID'+packageCount).value="N";
      }

      window.opener.document.getElementById('codAmtID'+packageCount).value=document.aascUpsPackageOptionsForm.upsCodAmt.value;
                  
      length=parseInt(document.aascUpsPackageOptionsForm.upsCodCode.length);
      for(var i=0; i<length; i++)
      {
        if(document.aascUpsPackageOptionsForm.upsCodCode.options[i].value==upsCodCode)
        {
           document.aascUpsPackageOptionsForm.upsCodCode.options[i].selected=true;
           window.opener.document.getElementById('codTypeID'+packageCount).value=document.aascUpsPackageOptionsForm.upsCodCode.options[document.aascUpsPackageOptionsForm.upsCodCode.selectedIndex].value;
        }
      }
       
      //////////////////////////upsCodFundsCode///////////////////////////////////////
      length=parseInt(document.aascUpsPackageOptionsForm.upsCodFundsCode.length);
      for(var i=0; i<length; i++)
      {
        if(document.aascUpsPackageOptionsForm.upsCodFundsCode.options[i].value==upsCodFundsCode)
        {
           document.aascUpsPackageOptionsForm.upsCodFundsCode.options[i].selected=true;
           window.opener.document.getElementById('codFundsCodeID'+packageCount).value=document.aascUpsPackageOptionsForm.upsCodFundsCode.options[document.aascUpsPackageOptionsForm.upsCodFundsCode.selectedIndex].value;
        }
      }
       
      //////////////////////////upsCodCurrCode///////////////////////////////////////
      length=parseInt(document.aascUpsPackageOptionsForm.upsCodCurrCode.length);
      for(var i=0; i<length; i++)
      {
        if(document.aascUpsPackageOptionsForm.upsCodCurrCode.options[i].value==upsCodCurrCode)
        {
           document.aascUpsPackageOptionsForm.upsCodCurrCode.options[i].selected=true;
           window.opener.document.getElementById('codCurrCodeID'+packageCount).value=document.aascUpsPackageOptionsForm.upsCodCurrCode.options[document.aascUpsPackageOptionsForm.upsCodCurrCode.selectedIndex].value;
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
     length=parseInt(document.aascUpsPackageOptionsForm.upsDelConfirm.length);
     for(var i=0; i<length; i++)
     {
        if(document.aascUpsPackageOptionsForm.upsDelConfirm.options[i].value==upsDelConfirm)
        {
         document.aascUpsPackageOptionsForm.upsDelConfirm.options[i].selected=true;
         window.opener.document.getElementById('delConfirmID'+packageCount).value=document.aascUpsPackageOptionsForm.upsDelConfirm.options[document.aascUpsPackageOptionsForm.upsDelConfirm.selectedIndex].value;
        }
     }
     
     if(checkCodAmt()==false)
     {
        return false;
     }
    }//end of if ship method is not disabled( before ship/save)
  
   closeWindow();
}//end of the method.

function closeWindow()
{
  if(window.opener.document.getElementById('shipMethodId').disabled!=true)
  {  
    if(chkDeliveryConfirmation()==false)
    {
       document.aascUpsPackageOptionsForm.upsDelConfirm.focus();
       return false;
    }
    if(checkCodAmt()==false)
    {
       return false;
    }  
    window.close();
  }
  else
  {
     window.close();
  }
  
}

function saveDetails()
{
    if(window.opener.document.getElementById('shipMethodId').disabled!=true)
    {
     var packageCount;
     var point=".";
     var pos1;
     var decimalNum;
     var floatingNum;
     var length;
     var checkBoxVal;
     packageCount=document.aascUpsPackageOptionsForm.packageCount.value;
     checkBoxVal=document.aascUpsPackageOptionsForm.codTempFlag.value;
     if(checkBoxVal=="Y")
     {   
       var upsCodAmt= document.aascUpsPackageOptionsForm.upsCodAmt.value;

       if(upsCodAmt >=1)
       {
         window.opener.document.getElementById('chCODID'+packageCount).value="Y";
       }
       else
       {
         window.opener.document.getElementById('chCODID'+packageCount).value="N";
       }

       window.opener.document.getElementById('codAmtID'+packageCount).value=document.aascUpsPackageOptionsForm.upsCodAmt.value;
       window.opener.document.getElementById('codTypeID'+packageCount).value=document.aascUpsPackageOptionsForm.upsCodCode.value;
       window.opener.document.getElementById('codFundsCodeID'+packageCount).value=document.aascUpsPackageOptionsForm.upsCodFundsCode.value;
       window.opener.document.getElementById('codCurrCodeID'+packageCount).value=document.aascUpsPackageOptionsForm.upsCodCurrCode.value;           
     }
     else
     {
       window.opener.document.getElementById('chCODID'+packageCount).value="N";
       window.opener.document.getElementById('codAmtID'+packageCount).value="";
       window.opener.document.getElementById('codTypeID'+packageCount).value="";
       window.opener.document.getElementById('codFundsCodeID'+packageCount).value="";
       window.opener.document.getElementById('codCurrCodeID'+packageCount).value="";
     }
         
     window.opener.document.getElementById('pkgingID'+packageCount).value=document.aascUpsPackageOptionsForm.upsPackaging.value;
     window.opener.document.getElementById('delConfirmID'+packageCount).value=document.aascUpsPackageOptionsForm.upsDelConfirm.value;
    }
    window.close();
 
}

//added for online help

function openNewWindow (URL, windowName, windowOptions)
{
  var window = getWindow (URL, windowName, windowOptions);
}

function getWindow(URL, windowName, windowOptions)
{
   var newWindow = open (URL, windowName, windowOptions)
   newWindow.focus();
   return window;
}
